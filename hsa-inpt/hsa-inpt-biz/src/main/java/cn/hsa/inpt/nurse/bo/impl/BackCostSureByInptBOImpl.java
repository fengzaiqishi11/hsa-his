package cn.hsa.inpt.nurse.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.AddAccountByInptBO;
import cn.hsa.module.inpt.nurse.bo.BackCostSureByInptBO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.service.PharInWaitReceiveService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    private RedisUtils redisUtils;

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
        return inptCostDAO.updateNewBackCost(inptCostDTO) > 0;
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

    /**
     * @Method updateCancelBackCost
     * @Desrciption 取消退费
     * @param inptCostDTO
     * @Author liuliyun
     * @Date   2021/11/10 20:16
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateCancelBackCost(InptCostDTO inptCostDTO) {
        if(inptCostDTO == null || ListUtils.isEmpty(inptCostDTO.getIds())){
            throw new AppException("取消退费失败，缺少参数");
        }
        String hospCode = inptCostDTO.getHospCode();//医院编码
        List<InptCostDTO> inptCostDTOS = inptCostDAO.queryInptCostPage(inptCostDTO);
        if (inptCostDTOS!=null && inptCostDTOS.size()>0) {
            //生成redis结算Key，医院编码 + 证件号码 + 划价收费KEY
            String visitId = inptCostDTOS.get(0).getVisitId();
            String key = new StringBuilder(hospCode).append("TFQX").append(StringUtils.isEmpty(visitId)).append(Constants.INPT_FEES_REDIS_KEY).toString();
            if (StringUtils.isNotEmpty(redisUtils.get(key))) {
                throw new AppException("当前患者正在取消退费,请稍后操作");
            }
            try {
                // 使用redis锁病人，并设置自动过期时间600秒，防止异常情况没有取消退费成功且redis不会自动清除的问题
                redisUtils.set(key, visitId, 600);
                //1、根据费用集合信息获得待领集合信息
                PharInWaitReceiveDTO pharInWaitReceiveDTO = new PharInWaitReceiveDTO();
                pharInWaitReceiveDTO.setHospCode(hospCode);
                pharInWaitReceiveDTO.setCostIds(inptCostDTO.getIds());
                List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs = pharInWaitReceiveService_consumer.queryPharInWaitReceives(HandParamMap(hospCode, "pharInWaitReceiveDTO", pharInWaitReceiveDTO)).getData();
                List<InptCostDTO> cancelIds = new ArrayList<>(); // 待领药费用列表
                List<InptCostDTO> waitInptcost = new ArrayList<>(); // 已领药费用列表
                List<InptCostDTO> pYInptcost = new ArrayList<>(); // 已配药费用列表
                List<InptCostDTO> fYInptcost = new ArrayList<>(); // 已发药费用列表
                for (InptCostDTO dto : inptCostDTOS) {
                    if (StringUtils.isNotEmpty(dto.getIsOk()) && Constants.SF.S.equals(dto.getIsOk())) {
                        throw new AppException("取消退费提示：有已确费数据,不能进行取消退费");
                    }
                    // if (Constants.COST_TYZT.TFBTY.equals(dto.getBackCode())) {
                    for (PharInWaitReceiveDTO receiveDTO : pharInWaitReceiveDTOs) {
                        if (dto.getId().equals(receiveDTO.getCostId())) {
                            if (Constants.FYZT.QL.equals(receiveDTO.getStatusCode())) {
                                waitInptcost.add(dto);
                            } else if (Constants.FYZT.PY.equals(receiveDTO.getStatusCode())) {
                                pYInptcost.add(dto);
                            } else if (Constants.FYZT.FY.equals(receiveDTO.getStatusCode())) {
                                fYInptcost.add(dto);
                            } else if (Constants.FYZT.DL.equals(receiveDTO.getStatusCode())) {
                                dto.setIsOk(Constants.SF.S);
                                dto.setOkTime(DateUtils.getNow());
                                cancelIds.add(dto);
                            }
                        }
                    }
                    // }
                }
                if (waitInptcost != null && waitInptcost.size() > 0) {
                    String message = getErrorMessage(waitInptcost);
                    if (StringUtils.isNotEmpty(message)) {
                        throw new AppException("取消退费提示：" + message + "已做领药申请，不能取消退费");
                    }
                }
                if (pYInptcost != null && pYInptcost.size() > 0) {
                    String message = getErrorMessage(pYInptcost);
                    if (StringUtils.isNotEmpty(message)) {
                        throw new AppException("取消退费提示：" + message + "已配药，不能取消退费");
                    }
                }
                if (fYInptcost != null && fYInptcost.size() > 0) {
                    String message = getErrorMessage(fYInptcost);
                    if (StringUtils.isNotEmpty(message)) {
                        throw new AppException("取消退费提示：" + message + "已发药，不能取消退费");
                    }
                }
                List<InptCostDTO> normal = new ArrayList<>();
                // 取消退费
                if (cancelIds != null && cancelIds.size() > 0) {
                    for (InptCostDTO dto : cancelIds) {
                        InptCostDTO redDto = DeepCopy.deepCopy(dto);//深度复制
                        redDto.setIsOk(Constants.SF.S); //是否确费 未确费
                        redDto.setOldCostId(dto.getId());
                        redDto.setId(SnowflakeUtils.getId());
                        redDto.setCrteTime(DateUtils.getNow());
                        redDto.setBackNum(BigDecimal.valueOf(0));
                        redDto.setTotalNum(dto.getBackNum());
                        redDto.setTotalPrice(BigDecimalUtils.multiply(dto.getPrice(), dto.getBackNum()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        redDto.setRealityPrice(BigDecimalUtils.multiply(dto.getPrice(), dto.getBackNum()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        redDto.setStatusCode(Constants.ZTBZ.ZC);//状态标志 正常
                        redDto.setBackCode(Constants.COST_TYZT.YFY);
                        normal.add(redDto);
                    }
                    // 将原退费信息确费
                    inptCostDAO.updateInptCostBatch(cancelIds);
                    if (!ListUtils.isEmpty(normal)) {
                        //新增取消退费费用记录
                        if (!ListUtils.isEmpty(normal)) {
                            inptCostDAO.insertInptCostBatch(normal);
                        }
                        // 获取已退费的领药记录
                        PharInWaitReceiveDTO backReceiveDTO = new PharInWaitReceiveDTO();
                        backReceiveDTO.setHospCode(hospCode);
                        backReceiveDTO.setCostIds(inptCostDTO.getIds());
                        List<PharInWaitReceiveDTO> backReceiveDTOList = pharInWaitReceiveService_consumer.queryPharInWaitReceiveToIsBack(HandParamMap(hospCode, "pharInWaitReceiveDTO", backReceiveDTO)).getData();
                        if (backReceiveDTOList != null && backReceiveDTOList.size() > 0) {
                            //有负的待领信息才需要新增一条待领记录
                            for (PharInWaitReceiveDTO dto : backReceiveDTOList) {
                                dto.setOldWrId(dto.getId());
                                dto.setCrteTime(DateUtils.getNow());
                                dto.setIsBack(Constants.SF.F);//是否需要退药 用于区分是正常的还是退费的
                                for (InptCostDTO d : normal) {
                                    if (dto.getHospCode().equals(d.getHospCode()) && dto.getCostId().equals(d.getOldCostId())) {
                                        dto.setNum(BigDecimalUtils.multiply(dto.getNum(), BigDecimal.valueOf(-1)));
                                        dto.setSplitNum(BigDecimalUtils.multiply(dto.getSplitNum(), BigDecimal.valueOf(-1)));
                                        dto.setTotalPrice(BigDecimalUtils.multiply(dto.getTotalPrice(), BigDecimal.valueOf(-1)));
                                        dto.setIsBack(Constants.SF.F);
                                        dto.setCurrUnitCode(d.getTotalNumUnitCode());
                                        dto.setCostId(d.getId());
                                        break;
                                    }
                                }
                                dto.setId(SnowflakeUtils.getId());
                            }
                            if (!ListUtils.isEmpty(backReceiveDTOList)) {
                                //新增待领记录
                                inptCostDAO.insertPharInWaitReceiveBatch(backReceiveDTOList);
                            }
                        }
                    }
                } else {
                    throw new AppException("取消退费提示: 没有需要取消退费的费用，不能进行取消退费");
                }
            } catch (Exception e) {
                throw e;
            } finally {
                redisUtils.del(key);//删除key
            }
        } else {
            throw new AppException("取消退费提示：没有要取消退费的费用，不能取消退费");
        }
        return false;
    }

    public String getErrorMessage(List<InptCostDTO> inptCostDTOS){
        String message ="";
        StringBuffer stringBuffer =new StringBuffer();
        for (InptCostDTO dto:inptCostDTOS){
            stringBuffer.append(dto.getItemName()+",");
        }
        message =stringBuffer.toString();
        return message;
    }
}
