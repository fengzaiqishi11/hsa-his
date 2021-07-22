package cn.hsa.inpt.nurse.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.nurse.bo.BackCostSureByInptBO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.service.PharInWaitReceiveService;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.inpt.nurse.bo.impl
 *@Class_name: BackCostSureWithInptBOImpl
 *@Describe: 住院退费
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 16:08
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BackCostSureByInptBOImpl extends HsafBO implements BackCostSureByInptBO {

    @Resource
    InptCostDAO inptCostDAO;
    @Resource
    PharInWaitReceiveService pharInWaitReceiveService_consumer;

    /**
    * @Method queryBackCostSurePage
    * @Desrciption 退费确认分页查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 17:19
    * @Return java.lang.Boolean
    **/
    @Override
    public PageDTO queryBackCostSurePage(InptCostDTO inptCostDTO) {
        PageHelper.startPage(inptCostDTO.getPageNo(), inptCostDTO.getPageSize());
        List<InptCostDTO> inptCostDTOS = inptCostDAO.queryBackCostSurePage(inptCostDTO);

        return PageDTO.of(inptCostDTOS);
    }

    @Override
    public PageDTO queryOutpatientSurgeryCostPage(InptCostDTO inptCostDTO) {
        PageHelper.startPage(inptCostDTO.getPageNo(), inptCostDTO.getPageSize());
        List<InptCostDTO> resultList = new ArrayList<>();
        List<InptCostDTO> inptCostDTOS = inptCostDAO.queryOutpatientSurgeryCostPage(inptCostDTO);
        resultList.addAll(inptCostDTOS);
        inptCostDTOS = inptCostDAO.queryBackCostSurePage(inptCostDTO);
        resultList.addAll(inptCostDTOS);
        return PageDTO.of(resultList);
    }

    /**queryStockItemInfoPage
    * @Method updateBackCostSure
    * @Desrciption 退费确认
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 17:20
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateBackCostSure(InptCostDTO inptCostDTO) {
        if(inptCostDTO == null || ListUtils.isEmpty(inptCostDTO.getIds())){
            throw new AppException("退费确认失败，缺少参数");
        }
        String hospCode = inptCostDTO.getHospCode();//医院编码
        List<InptCostDTO> inptCostDTOS = inptCostDAO.queryInptCostPage(inptCostDTO);

        //1、根据费用集合信息获得待领集合信息
        PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
        pharInWaitReceiveDTO.setHospCode(hospCode);
        pharInWaitReceiveDTO.setCostIds(inptCostDTO.getIds());
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs = pharInWaitReceiveService_consumer.queryPharInWaitReceives(HandParamMap(hospCode,"pharInWaitReceiveDTO",pharInWaitReceiveDTO)).getData();

        for(InptCostDTO dto:inptCostDTOS) {
            if (!Constants.COST_TYZT.TFYTY.equals(dto.getBackCode()) && !Constants.COST_TYZT.TFBTY.equals(dto.getBackCode())) {
                for (PharInWaitReceiveDTO receiveDTO:pharInWaitReceiveDTOs) {
                    if (dto.getId().equals(receiveDTO.getCostId())) {
                        if (Constants.FYZT.QL.equals(receiveDTO.getStatusCode())) {
                            throw new AppException("请先在领药申请处取消预配药,再退费确认");
                        } else if (Constants.FYZT.PY.equals(receiveDTO.getStatusCode())) {
                            throw new AppException("请先去药房取消配药,再退费确认");
                        } else if (Constants.FYZT.FY.equals(receiveDTO.getStatusCode())) {
                            throw new AppException("请先去药房退药,再退费确认");
                        }
                    }
                }
            }
        }
        inptCostDTO.setIsOk(Constants.SF.S);// 是否确费
        inptCostDTO.setOkTime(DateUtils.getNow()); // 确费时间
        return inptCostDAO.updateInptCostBatchWithBackCost(inptCostDTO) > 0;
    }

    /**
     * @Method HandParamMap
     * @Desrciption 封装service查询参数
     * @param hospCode 医院编码
     * @param dtoName 参数名称
     * @param object 参数值
     * @Author liuqi1
     * @Date   2020/9/10 14:48
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    private Map<String,Object> HandParamMap(String hospCode, String dtoName, Object object){
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        map.put(dtoName, object);

        return map;
    }
}
