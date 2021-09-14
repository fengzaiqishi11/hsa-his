package cn.hsa.inpt.inptadvancepay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.advancepay.bo.InptAdvancePayBO;
import cn.hsa.module.inpt.advancepay.dao.InptAdvancePayDAO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.inptadvancepay.bo.impl
 * @Class_name: InptAdvancePayBOImpl
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/9/8 16:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InptAdvancePayBOImpl extends HsafBO implements InptAdvancePayBO {

    @Resource
    private InptAdvancePayDAO inptAdvancePayDAO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private InptVisitDAO inptVisitDAO;

    /**
    * @Menthod getById
    * @Desrciption  查询单条预交金信息
     * @param inptAdvancePayDTO
    * @Author xingyu.xie
    * @Date   2020/9/14 8:46
    * @Return cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO
    **/
    @Override
    public InptAdvancePayDTO getById(InptAdvancePayDTO inptAdvancePayDTO) {
        return inptAdvancePayDAO.getById(inptAdvancePayDTO);
    }

    /**
     * @Method queryPatientInfoPage
     * @Desrciption 分页查询患者信息
     * @param inptVisitDTO
     * @Author xingyu.xie
     * @Date   2020/9/4 10:37
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    @Override
    public PageDTO queryPatientInfoPage(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
        List<InptVisitDTO> inptVisitDTOS = inptVisitDAO.queryInptVisitPageByMoney(inptVisitDTO);
        return PageDTO.of(inptVisitDTOS);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  根据实体筛选所有预交金信息
     * @param inptAdvancePayDTO
    * @Author xingyu.xie
    * @Date   2020/9/14 8:46
    * @Return java.util.List<cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO>
    **/
    @Override
    public List<InptAdvancePayDTO> queryAll(InptAdvancePayDTO inptAdvancePayDTO) {
        return inptAdvancePayDAO.queryAll(inptAdvancePayDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  分页并且根据实体筛选所有预交金信息
     * @param inptAdvancePayDTO
    * @Author xingyu.xie
    * @Date   2020/9/14 8:46
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPage(InptAdvancePayDTO inptAdvancePayDTO) {

        PageHelper.startPage(inptAdvancePayDTO.getPageNo(),inptAdvancePayDTO.getPageSize());

        List<InptAdvancePayDTO> inptAdvancePayDTOS = inptAdvancePayDAO.queryAll(inptAdvancePayDTO);

        return PageDTO.of(inptAdvancePayDTOS);
    }

    /**
    * @Menthod insert
    * @Desrciption  插入单条预交金信息
     * @param inptAdvancePayDTO
    * @Author xingyu.xie
    * @Date   2020/9/14 8:47
    * @Return boolean
    **/
    @Override
    public boolean insert(InptAdvancePayDTO inptAdvancePayDTO) {
        if (BigDecimalUtils.compareTo(inptAdvancePayDTO.getPrice(),BigDecimal.valueOf(0))<= 0 ){
            throw new AppException("预交金必须大于0");
        }
        // 查询患者是否已结算
       if (inptAdvancePayDAO.queryIsSettleByVisit(inptAdvancePayDTO) > 0 ) {
           throw new RuntimeException("该患者已经结算，无法进行预交金充值");
       }
        setDefaultValue(inptAdvancePayDTO);
        inptAdvancePayDAO.insert(inptAdvancePayDTO);
        updateTotalAdvance(inptAdvancePayDTO.getVisitId(),inptAdvancePayDTO.getHospCode());
        return true;
    }


    /**
    * @Menthod updateFlushingRed
    * @Desrciption  多条预交金冲红
     * 1.先将原纪录的状态置为被冲红状态、
     * 2。新增一条状态为冲红的记录,此记录的金额为原纪录金额负数，将原纪录完全冲掉
     * 3.部分退费新增一条状态为正常的记录,此记录的金额为,原纪录金额-退掉的金额,可以理解为退钱后,剩下的钱
     * 4.全部退费则不会新增
     * @param inptAdvancePayDTOList
    * @Author xingyu.xie
    * @Date   2020/9/14 8:47
    * @Return boolean
    **/
    @Override
    public boolean updateFlushingRed(List<InptAdvancePayDTO> inptAdvancePayDTOList) {

        if (ListUtils.isEmpty(inptAdvancePayDTOList)){
            throw new AppException("冲红记录不能为空");
        }

        for (InptAdvancePayDTO inptAdvancePayDTO:inptAdvancePayDTOList){

            String redName = inptAdvancePayDTO.getRedName();
            String redUserId = inptAdvancePayDTO.getRedUserId();

            InptAdvancePayDTO beRed = getById(inptAdvancePayDTO);
            if (BigDecimalUtils.compareTo(inptAdvancePayDTO.getRedPrice(),beRed.getPrice())>0){
                throw new AppException("错误，冲红金额不能大于预交金额!");
            }
            if (!Constants.ZTBZ.ZC.equals(beRed.getStatusCode())){
                throw new AppException("错误，该记录不是正常状态，不能冲红!");
            }
            if (!Constants.SF.F.equals(beRed.getIsSettle())){
                throw new AppException("该记录已结算，不能冲红!");
            }
            // liuliyun 20210508 预付金退费优化
            if(!Constants.ZFFS.XJ.equals(inptAdvancePayDTO.getPayCode())){
                if (!inptAdvancePayDTO.getPayCode().equals(beRed.getPayCode())){
                    throw new AppException("错误,只能以原付款方式或现金方式退费!");
                }
            }
            // 原记录设为3被冲红状态
            beRed.setStatusCode(Constants.ZTBZ.BCH);
            // 新增一条冲红记录
            InptAdvancePayDTO red = new InptAdvancePayDTO();
            BeanUtils.copyProperties(beRed,red);
            red.setId(SnowflakeUtils.getId());
            red.setVisitId(beRed.getVisitId());
            red.setStatusCode(Constants.ZTBZ.CH);
            red.setPrice(BigDecimalUtils.subtract(BigDecimal.ZERO,beRed.getPrice()));
            red.setCrteTime(DateUtils.getNow());
            red.setCrteName(redName);
            red.setCrteId(redUserId);
            red.setPayCode(inptAdvancePayDTO.getPayCode());
            // 将原记录的冲红id加进去
            beRed.setRedId(red.getId());
            // 如果是部分退费就要新增一条正常记录
            if (BigDecimalUtils.compareTo(inptAdvancePayDTO.getRedPrice(),beRed.getPrice())!=0){
                // 新增一条正常记录  预交金= 被冲红记录预交金-冲红预交金
                if (Constants.ZFFS.XJ.equals(inptAdvancePayDTO.getPayCode())) {
                    InptAdvancePayDTO normal = new InptAdvancePayDTO();
                    BeanUtils.copyProperties(beRed, normal);
                    this.setDefaultValue(normal);
                    // 预交金= 被冲红记录预交金-冲红预交金
                    normal.setPrice(BigDecimalUtils.subtract(beRed.getPrice(), inptAdvancePayDTO.getRedPrice()));
                    normal.setVisitId(beRed.getVisitId());
                    normal.setCrteName(redName);
                    normal.setCrteId(redUserId);
                    // 正常记录的冲红id为空
                    normal.setRedId("");
                    inptAdvancePayDAO.insert(normal);
                }else {
                    throw new AppException("非现金支付方式不支持部分退费");
                }
            }
            inptAdvancePayDAO.updateInptAdvancePay(beRed);
            inptAdvancePayDAO.insert(red);
            // 回写就诊表的累计预交金和累计余额字段
            this.updateTotalAdvance(beRed.getVisitId(),beRed.getHospCode());
        }
        return true;
    }

    /**
    * @Menthod updateInptAdvancePay
    * @Desrciption  修改单条预交金信息（有非空判断）
     * @param inptAdvancePayDTO
    * @Author xingyu.xie
    * @Date   2020/9/14 8:47
    * @Return boolean
    **/
    @Override
    public boolean updateInptAdvancePay(InptAdvancePayDTO inptAdvancePayDTO) {
        inptAdvancePayDAO.updateInptAdvancePay(inptAdvancePayDTO);
        updateTotalAdvance(inptAdvancePayDTO.getVisitId(),inptAdvancePayDTO.getHospCode());
        return true;
    }

    /**
    * @Menthod updateInptAdvancePayS
    * @Desrciption  修改单条预交金信息（无非空判断）
     * @param inptAdvancePayDTO
    * @Author xingyu.xie
    * @Date   2020/9/14 8:48
    * @Return boolean
    **/
    @Override
    public boolean updateInptAdvancePayS(InptAdvancePayDTO inptAdvancePayDTO) {
        inptAdvancePayDAO.updateInptAdvancePayS(inptAdvancePayDTO);
        updateTotalAdvance(inptAdvancePayDTO.getVisitId(),inptAdvancePayDTO.getHospCode());
        return true;
    }

    /**
    * @Menthod deleteById
    * @Desrciption  删除单条预交金信息
     * @param inptAdvancePayDTO
    * @Author xingyu.xie
    * @Date   2020/9/14 8:48
    * @Return boolean
    **/
    @Override
    public boolean deleteById(InptAdvancePayDTO inptAdvancePayDTO) {
        inptAdvancePayDAO.deleteById(inptAdvancePayDTO);
        updateTotalAdvance(inptAdvancePayDTO.getVisitId(),inptAdvancePayDTO.getHospCode());
        return true;
    }

    /**
     * @Menthod queryAdvancePay
     * @Desrciption 预交金查询接口
     * @param inptVisitDTO 住院病人dto
     * @Author luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2020/9/9 11:29
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryAdvancePay(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
        List<InptVisitDTO> list = inptAdvancePayDAO.queryAdvancePay(inptVisitDTO);
        return PageDTO.of(list);
    }

    /**
    * @Menthod updateTotalAdvance
    * @Desrciption  回写住院就诊表的累计预交金和累计余额
     * @param visitId 就诊ID
    * @Author xingyu.xie
    * @Date   2020/9/16 15:15
    * @Return void
    **/
    private void updateTotalAdvance(String visitId,String hospCode){
        // 回写住院就诊表的累计预交金和累计余额字段
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setId(visitId);
        inptVisitDTO.setHospCode(hospCode);
        InptAdvancePayDTO inptAdvancePayDTO = new InptAdvancePayDTO();
        inptAdvancePayDTO.setVisitId(visitId);
        inptAdvancePayDTO.setStatusCode(Constants.ZTBZ.ZC);
        inptAdvancePayDTO.setHospCode(hospCode);
        // 查询出所有状态为1正常的预交金记录
        List<InptAdvancePayDTO> inptAdvancePayDTOS = inptAdvancePayDAO.queryAll(inptAdvancePayDTO);
        BigDecimal totalAdvance = BigDecimal.valueOf(0);
        if (!ListUtils.isEmpty(inptAdvancePayDTOS)){
            // 循环计算累计预交金
            for (InptAdvancePayDTO item:inptAdvancePayDTOS){
                BigDecimal price = BigDecimalUtils.nullToZero(item.getPrice());
                totalAdvance = BigDecimalUtils.add(totalAdvance,price);
            }
        }
        InptVisitDTO inptVisitById = inptVisitDAO.getInptVisitById(inptVisitDTO);
        inptVisitById.setTotalAdvance(totalAdvance);
        // 累计余额 = 累计预交金 - 累计费用
        BigDecimal totalCost = BigDecimalUtils.nullToZero(inptVisitById.getTotalCost());
        inptVisitById.setTotalBalance(BigDecimalUtils.subtract(inptVisitById.getTotalAdvance(),totalCost));
        inptVisitDAO.updateInptVisit(inptVisitById);
    }

    /**
    * @Menthod setDefaultValue
    * @Desrciption   新增记录时设置默认值
     *               1.生成id,创建时间,状态标志，预交单号，是否结算
     * @param inptAdvancePayDTO
    * @Author xingyu.xie
    * @Date   2020/9/15 15:47
    * @Return void
    **/
    private void setDefaultValue(InptAdvancePayDTO inptAdvancePayDTO){
        Map map = new HashMap<>();
        inptAdvancePayDTO.setCrteTime(DateUtils.getNow());
        inptAdvancePayDTO.setId(SnowflakeUtils.getId());
        inptAdvancePayDTO.setStatusCode(Constants.ZTBZ.ZC);
        inptAdvancePayDTO.setIsSettle(Constants.SF.F);
        map.put("hospCode",inptAdvancePayDTO.getHospCode());
        map.put("typeCode", Constants.ORDERRULE.YJJ);
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
        inptAdvancePayDTO.setApOrderNo(orderNo.getData());
    }
}
