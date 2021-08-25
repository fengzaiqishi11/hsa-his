package cn.hsa.inpt.doctor.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.bo.InptAdviceTempBO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceTempDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailTempDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceTempDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *@Package_name: cn.hsa.doctor.advice.bo.impl
 *@Class_name: InptAdviceTempBOImpl
 *@Describe: 医嘱模板业务层
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020-10-20 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InptAdviceTempBOImpl extends HsafBO implements InptAdviceTempBO {

    @Resource
    InptAdviceTempDAO inptAdviceTempDAO;

    /**
     * @Method queryInptAdviceTemp
     * @Desrciption 医嘱模板分页查询
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInptAdviceTempPage(InptAdviceTempDTO inptAdviceTempDTO) {
        PageHelper.startPage(inptAdviceTempDTO.getPageNo(),inptAdviceTempDTO.getPageSize());
        List<InptAdviceTempDTO> inptAdviceTempDTOList = inptAdviceTempDAO.queryInptAdviceTemp(inptAdviceTempDTO);
        return PageDTO.of(inptAdviceTempDTOList);
    }


    /**
     * @Method queryInptAdviceTemp
     * @Desrciption 医嘱模板查询
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public List<InptAdviceTempDTO> queryInptAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO) {
        return inptAdviceTempDAO.queryInptAdviceTemp(inptAdviceTempDTO);
    }

    /**
     * @Method queryInptAdviceDetailTemp
     * @Desrciption 医嘱模板明细查询
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public List<InptAdviceDetailTempDTO> queryInptAdviceDetailTemp(InptAdviceTempDTO inptAdviceTempDTO) {
        return inptAdviceTempDAO.queryInptAdviceDetailTemp(inptAdviceTempDTO);
    }

    /**
    * @Method saveAdciceTemp
    * @Desrciption 保存医嘱模板
    * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/10/20 19:46:53
    * @Return java.lang.Boolean
    **/
    @Override
    public boolean saveAdciceTemp(InptAdviceTempDTO inptAdviceTempDTO) {
        //是否有效
        inptAdviceTempDTO.setIsValid(Constants.SF.S);
        //生成拼音码
        if (StringUtils.isEmpty(inptAdviceTempDTO.getPym())) {
            inptAdviceTempDTO.setPym(PinYinUtils.toFirstPY(inptAdviceTempDTO.getName()));
        }
        //生成五笔码
        if (StringUtils.isEmpty(inptAdviceTempDTO.getWbm())) {
            inptAdviceTempDTO.setWbm(WuBiUtils.getWBCode(inptAdviceTempDTO.getName()));
        }
        if(StringUtils.isEmpty(inptAdviceTempDTO.getId())){
            //主表主键
            inptAdviceTempDTO.setId(SnowflakeUtils.getId());
            //新增模板
            inptAdviceTempDAO.insertInptAdviceTemp(inptAdviceTempDTO);
        }else{
            //个人模板
            if(!"2".equals(inptAdviceTempDTO.getTypeCode())){
                //检查数据状态
                this.checkData(inptAdviceTempDTO);
            }

            //修改模板
            inptAdviceTempDAO.updateAdviceTemp(inptAdviceTempDTO);
            //删除模板明细
            inptAdviceTempDAO.deletePrescribeTempDetail(inptAdviceTempDTO);
        }
        List<InptAdviceDetailTempDTO> inptAdviceDetailTempDTOList = inptAdviceTempDTO.getInptAdviceDetailTempDTOList();
        for(InptAdviceDetailTempDTO inptAdviceDetailTempDTO : inptAdviceDetailTempDTOList){
            //主键
            inptAdviceDetailTempDTO.setId(SnowflakeUtils.getId());
            //模板ID
            inptAdviceDetailTempDTO.setIatId(inptAdviceTempDTO.getId());
            //医院编码
            inptAdviceDetailTempDTO.setHospCode(inptAdviceTempDTO.getHospCode());
        }
        //生成组号
        inptAdviceDetailTempDTOList = this.buildAdviceTempGroupNo(inptAdviceDetailTempDTOList);
        //新增模板明细
        inptAdviceTempDAO.insertBathInptAdviceDetailTemp(inptAdviceDetailTempDTOList);
        return true;
    }

    /**
     * 检查是否是否可以修改、审核、作废
     * @param inptAdviceTempDTO
     * @return
     */
    public boolean checkData(InptAdviceTempDTO inptAdviceTempDTO){
        InptAdviceTempDTO inptAdviceTempDTOOld =  inptAdviceTempDAO.getInptAdviceTemp(inptAdviceTempDTO);
        if(inptAdviceTempDTOOld == null){
            throw new AppException("未找到该数据");
        }
//        else if(Constants.SHZT.SHWC.equals(inptAdviceTempDTOOld.getAuditCode()) && !"2".equals(inptAdviceTempDTOOld.getTypeCode())){
//            throw new AppException("数据已审核，不能修改");
//        }
        else if(Constants.SHZT.ZF.equals(inptAdviceTempDTOOld.getAuditCode()) && !"2".equals(inptAdviceTempDTOOld.getTypeCode())){
            throw new AppException("数据已作废，不能在修改");
        }
        return true;
    }


    /**
     * 判断是否有联录数据，生成组号
     * @param inptAdviceDetailTempDTOList
     * @return
     */
    public List<InptAdviceDetailTempDTO> buildAdviceTempGroupNo(List<InptAdviceDetailTempDTO> inptAdviceDetailTempDTOList){
        List<InptAdviceDetailTempDTO> outptGroupNoList = inptAdviceDetailTempDTOList;
        //组号
        int groupNo = 0;
        //组内序号
        int groupSeqNo = 1;
        boolean isTrue = true;
        for(int i = 0; i < inptAdviceDetailTempDTOList.size() ; i++){
            if(i == 0){
                outptGroupNoList.get(i).setGroupNo(0);
                outptGroupNoList.get(i).setGroupSeqNo(0);
            }else{
                //用法是否同上
                if("0".equals(outptGroupNoList.get(i).getUsageCode())){
                    //判断是否是连续联录，多条序号不需要添加
                    if(isTrue){
                        groupNo = groupNo + 1;
                        isTrue = false;
                        //序号(修改第一条序号)
                        outptGroupNoList.get(i-1).setGroupSeqNo(groupSeqNo);
                        //组号修改第一条组号)
                        outptGroupNoList.get(i-1).setGroupNo(groupNo);
                    }
                    groupSeqNo = groupSeqNo + 1;
                    outptGroupNoList.get(i).setGroupNo(groupNo);
                    //序号
                    outptGroupNoList.get(i).setGroupSeqNo(groupSeqNo);
                    //频率
                    outptGroupNoList.get(i).setRateId(outptGroupNoList.get(i-1).getRateId());
                    //执行科室
                    outptGroupNoList.get(i).setExecDeptId(outptGroupNoList.get(i-1).getExecDeptId());
                    //用药性质
                    outptGroupNoList.get(i).setUseCode(outptGroupNoList.get(i-1).getUseCode());
                    //使用天数
                    outptGroupNoList.get(i).setUseDays(outptGroupNoList.get(i-1).getUseDays());
                    // 用法
                    outptGroupNoList.get(i).setUsageCode(outptGroupNoList.get(i-1).getUsageCode());
                }else{
                    isTrue = true;
                    outptGroupNoList.get(i).setGroupNo(0);
                    outptGroupNoList.get(i).setGroupSeqNo(0);
                    groupSeqNo = 1;
                }
            }
        }
        return outptGroupNoList;
    }

    /**
     * @Method updateAdviceTemp
     * @Desrciption 审核、删除医嘱模板
     * @param inptAdviceTempDTO 医嘱模板信息
     * @Author zengfeng
     * @Date   2020/9/27 14:44
     * @Return boolean
     **/
    @Override
    public boolean updateAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO) {
        //检查数据状态
        this.checkData(inptAdviceTempDTO);
        return inptAdviceTempDAO.updateAdviceTemp(inptAdviceTempDTO) > 0;
    }
}
