package cn.hsa.inpt.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.InptAdviceFeeCheckBO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: hsa.inpt.nurse.bo.impl
 *@Class_name: InptAdviceFeeCheckBOImpl
 *@Describe: 医嘱费用核对BO实现类
 *@Author: LiaoJiGuang
 *@Eamil: jiguang.liao@powersi.com
 *@Date: 2020/9/15 09:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InptAdviceFeeCheckBOImpl extends HsafBO implements InptAdviceFeeCheckBO {

    @Resource
    private InptAdviceDAO inptAdviceDAO;

    /**
    * @Method queryInptAdviceCheckPage
    * @Desrciption 医嘱核对分页查询
    * @param inptAdviceDTO
    * @Author LiaoJiGuang
    * @Date   2020/9/15 09:55
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryInptAdviceCheckPage(InptAdviceDTO inptAdviceDTO) {

        // 设置分页
        PageHelper.startPage(inptAdviceDTO.getPageNo(),inptAdviceDTO.getPageSize());

        // 查询
        List<InptAdviceDTO> list = inptAdviceDAO.queryInptAdviceFeeCheckPage(inptAdviceDTO);
        if (!ListUtils.isEmpty(list)) {
            for (InptAdviceDTO entity:list) {
                Integer num = inptAdviceDAO.getExecNum(entity);
                entity.setExecNum(num == null ? "0" : num.toString());
            }
        }
        return PageDTO.of(list);
    }

    /**
     * @Method queryInptPatientPage
     * @Desrciption 医嘱费用核对 - 获取本科室住院在院人员的患者信息
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 14:14
     * @Return  cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInptPatientPage(InptAdviceDTO inptAdviceDTO) {
        // 设置分页
        PageHelper.startPage(inptAdviceDTO.getPageNo(),inptAdviceDTO.getPageSize());

        // 查询
        List<InptVisitDTO> list = inptAdviceDAO.queryInptPatientPage(inptAdviceDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method updateAdviceFeeCheck
     * @Desrciption 医嘱费用核对
     * @param map 住院费用实体DTO
     * @Author LiaoJiGuang
     * @Date   2020/9/17 11:39
     * @Return Boolean
     **/
    @Override
    public Boolean updateAdviceFeeCheck(Map map) {
        List<InptCostDTO> list = MapUtils.get(map,"list");
        inptAdviceDAO.updateAdviceFeeCheck(list);

        InptAdviceDTO inptAdvice = new InptAdviceDTO();
        inptAdvice.setHospCode(map.get("hospCode").toString());
        inptAdvice.setIatIdList((List)map.get("adviceIdList"));
        inptAdvice.setIsCheck(Constants.SF.S);
        inptAdviceDAO.updateInptAdviceBatchByIds(inptAdvice);
        return true;
    }

    /**
     * @Method queryInptAdviceFeeInfoPage
     * @Desrciption 医嘱费用分页查询
     * @param inptAdviceDTO
     * @Author LiaoJiGuang
     * @Date   2020/9/15 10:31
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInptAdviceFeeInfoPage(InptAdviceDTO inptAdviceDTO) {
        String str[] = inptAdviceDTO.getIatIds().split(",");
        List<String> list = Arrays.asList(str);
        inptAdviceDTO.setIatIdList(list);

        // 设置分页
        PageHelper.startPage(inptAdviceDTO.getPageNo(),inptAdviceDTO.getPageSize());

        // 查询
        List<InptCostDTO> selectList = inptAdviceDAO.queryInptAdviceFeeInfoPage(inptAdviceDTO);
        return PageDTO.of(selectList);
    }
}