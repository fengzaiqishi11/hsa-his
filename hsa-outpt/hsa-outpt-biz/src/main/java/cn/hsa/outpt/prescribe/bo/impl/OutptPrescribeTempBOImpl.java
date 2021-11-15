package cn.hsa.outpt.prescribe.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.prescribe.bo.OutptPrescribeTempBO;
import cn.hsa.module.outpt.prescribe.dao.OutptPrescribeTempDAO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDetailDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 *@Package_name: cn.hsa.outpt.prescribe.bo.impl
 *@Class_name: OutptPrescribeTempBOImpl
 *@Describe: 处方模板
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 15:10
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptPrescribeTempBOImpl extends HsafBO implements OutptPrescribeTempBO {

    @Resource
    OutptPrescribeTempDAO outptPrescribeTempDAO;

    /**
    * @Method queryOutptPrescribeTempDTOPage
    * @Desrciption 分页查询
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 15:14
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryOutptPrescribeTempDTOPage(OutptPrescribeTempDTO outptPrescribeTempDTO) {
        PageHelper.startPage(outptPrescribeTempDTO.getPageNo(),outptPrescribeTempDTO.getPageSize());
        List<OutptPrescribeTempDTO> outptPrescribeTempDTOS = outptPrescribeTempDAO.queryOutptPrescribeTempDTOPage(outptPrescribeTempDTO);
        return PageDTO.of(outptPrescribeTempDTOS);
    }

    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板明细信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO) {
        PageHelper.startPage(outptPrescribeTempDTO.getPageNo(),outptPrescribeTempDTO.getPageSize());
        List<OutptPrescribeTempDetailDTO> outptPrescribeTempDetailDTOList = outptPrescribeTempDAO.queryOutptPrescribeTempDetails(outptPrescribeTempDTO);
        return PageDTO.of(outptPrescribeTempDetailDTOList);
    }

    /**
     * @Method updateOutptPrescribeTempDTO
     * @Desrciption 修改模板信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/19 14:44
     * @Return boolean
     **/
    @Override
    public boolean updateOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO) {
      OutptPrescribeTempDTO outptPrescribeTempDTOOld =  outptPrescribeTempDAO.getOutptPrescribeTempDTOById(outptPrescribeTempDTO);
      if(Constants.SHZT.ZF.equals(outptPrescribeTempDTOOld.getAuditCode()) && !"2".equals(outptPrescribeTempDTOOld.getTypeCode())){
        throw new AppException("数据已作废，不能在修改");
      }
      return outptPrescribeTempDAO.updateOutptPrescribeTempDTO(outptPrescribeTempDTO) > 0;
    }


    /**
     * @Menthod queryOutptPrescribeTempDTO
     * @Desrciption  查询模板信息
     * @param outptPrescribeTempDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDTO>
     **/
    @Override
    public List<OutptPrescribeTempDTO> queryOutptPrescribeTempDTO(OutptPrescribeTempDTO outptPrescribeTempDTO) {
        return outptPrescribeTempDAO.queryOutptPrescribeTempDTO(outptPrescribeTempDTO);
    }

    /*
     * @Menthod savePrescribeTemp
     * @Desrciption  保存处方模板信息
     * @param outptPrescribeDTO 处方明细
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return boolean
     */
    @Override
    public boolean savePrescribeTemp(OutptPrescribeTempDTO outptPrescribeTempDTO) {
        //是否有效
        outptPrescribeTempDTO.setIsValid(Constants.SF.S);
        //生成拼音码
        if (StringUtils.isEmpty(outptPrescribeTempDTO.getPym())) {
            outptPrescribeTempDTO.setPym(PinYinUtils.toFirstPY(outptPrescribeTempDTO.getName()));
        }
        //生成五笔码
        if (StringUtils.isEmpty(outptPrescribeTempDTO.getWbm())) {
            outptPrescribeTempDTO.setWbm(WuBiUtils.getWBCode(outptPrescribeTempDTO.getName()));
        }
        if(StringUtils.isEmpty(outptPrescribeTempDTO.getId())){
            //主表主键
            outptPrescribeTempDTO.setId(SnowflakeUtils.getId());
            //新增模板
            outptPrescribeTempDAO.insertOutptPrescribeTempDTO(outptPrescribeTempDTO);
        }else{
            //个人模板自己可以修改
            if(!"2".equals(outptPrescribeTempDTO.getTypeCode())){
                //检查数据状态
                this.checkData(outptPrescribeTempDTO);
            }
            //修改模板
            outptPrescribeTempDAO.updateOutptPrescribeTempDTO(outptPrescribeTempDTO);
            //删除模板明细
            outptPrescribeTempDAO.deletePrescribeTempDetail(outptPrescribeTempDTO);
        }
        List<OutptPrescribeTempDetailDTO> outptPrescribeTempDetailDTOList = outptPrescribeTempDTO.getOutptPrescribeTempDetailDTOs();
        for(OutptPrescribeTempDetailDTO outptPrescribeTempDetailDTO : outptPrescribeTempDetailDTOList){
            //主键
            outptPrescribeTempDetailDTO.setId(SnowflakeUtils.getId());
            //模板ID
            outptPrescribeTempDetailDTO.setOptId(outptPrescribeTempDTO.getId());
            //医院编码
            outptPrescribeTempDetailDTO.setHospCode(outptPrescribeTempDTO.getHospCode());
        }
        //生成组号
        outptPrescribeTempDetailDTOList = this.buildOutptPrescribeGroupNo(outptPrescribeTempDetailDTOList);
        //新增模板明细
        outptPrescribeTempDAO.insertOutptPrescribeTempDetail(outptPrescribeTempDetailDTOList);
        return true;
    }


    /**
     * 判断是否有联录数据，生成组号
     * @param outptPrescribeTempDetailDTOList
     * @return
     */
    public List<OutptPrescribeTempDetailDTO> buildOutptPrescribeGroupNo(List<OutptPrescribeTempDetailDTO> outptPrescribeTempDetailDTOList){
        List<OutptPrescribeTempDetailDTO> outptGroupNoList = outptPrescribeTempDetailDTOList;
        //组号
        int groupNo = 0;
        //组内序号
        int groupSeqNo = 1;
        boolean isTrue = true;
        for(int i = 0; i < outptPrescribeTempDetailDTOList.size() ; i++){
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
     * 检查是否是否可以修改、审核、作废
     * @param outptPrescribeTempDTO
     * @return
     */
    public boolean checkData(OutptPrescribeTempDTO outptPrescribeTempDTO){
        OutptPrescribeTempDTO outptPrescribeTempDTOOld =  outptPrescribeTempDAO.getOutptPrescribeTempDTOById(outptPrescribeTempDTO);
        if(Constants.SHZT.SHWC.equals(outptPrescribeTempDTOOld.getAuditCode()) && !"2".equals(outptPrescribeTempDTOOld.getTypeCode())){
            throw new AppException("数据已审核，不能修改");
        }else if(Constants.SHZT.ZF.equals(outptPrescribeTempDTOOld.getAuditCode()) && !"2".equals(outptPrescribeTempDTOOld.getTypeCode())){
            throw new AppException("数据已作废，不能在修改");
        }
        return true;
    }

    /**
     * @Method updateTempAudit
     * @Desrciption 模板取消审核
     * @param outptPrescribeTempDTO
     * @Author liuliyun
     * @Date   2021/11/10 17:16
     * @Return boolean
     **/
    @Override
    public boolean updateTempAudit(OutptPrescribeTempDTO outptPrescribeTempDTO) {
        OutptPrescribeTempDTO outptPrescribeTempDTOOld =  outptPrescribeTempDAO.getOutptPrescribeTempDTOById(outptPrescribeTempDTO);
        if(Constants.SHZT.ZF.equals(outptPrescribeTempDTOOld.getAuditCode()) && !"2".equals(outptPrescribeTempDTOOld.getTypeCode())){
            throw new AppException("数据已作废，不能在修改");
        }
        return outptPrescribeTempDAO.updateTempAudit(outptPrescribeTempDTO) > 0;
    }

}
