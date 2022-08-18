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
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
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

    @Resource
    InptVisitDAO inptVisitDAO;

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
        // 时间不能为空
//        if (inptCostDTO.getStartTime() == null) {
//            throw new RuntimeException("退费确认查询起始时间不能为空");
//        }
//        if (inptCostDTO.getStopTime() == null) {
//            throw new RuntimeException("退费确认查询结束时间不能为空");
//        }
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
                    if (Constants.COST_TYZT.TFYTY.equals(dto.getBackCode())){
                        throw new AppException("取消退费提示:"+dto.getItemName()+"已退药,不能进行取消退费");
                    }
                    // if (Constants.COST_TYZT.TFBTY.equals(dto.getBackCode())) {
                    for (PharInWaitReceiveDTO receiveDTO : pharInWaitReceiveDTOs) {
                        if (dto.getId().equals(receiveDTO.getCostId())) {
                            if (Constants.FYZT.QL.equals(receiveDTO.getStatusCode())) {
                                waitInptcost.add(dto);
                            } else if (Constants.FYZT.PY.equals(receiveDTO.getStatusCode())) {
                                pYInptcost.add(dto);
                            } else if (Constants.FYZT.FY.equals(receiveDTO.getStatusCode())) {
                                dto.setBackCode(Constants.COST_TYZT.TFYTY);
                                dto.setIsOk(Constants.SF.S);
                                dto.setOkTime(DateUtils.getNow());
                                cancelIds.add(dto);
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
                        throw new AppException("取消退费提示：" + message + "请先去取消预配药,再进行取消退费");
                    }
                }
                if (pYInptcost != null && pYInptcost.size() > 0) {
                    String message = getErrorMessage(pYInptcost);
                    if (StringUtils.isNotEmpty(message)) {
                        throw new AppException("取消退费提示：" + message + "请先去取消配药，再进行取消退费");
                    }
                }
//                if (fYInptcost != null && fYInptcost.size() > 0) {
//                    String message = getErrorMessage(fYInptcost);
//                    if (StringUtils.isNotEmpty(message)) {
//                        throw new AppException("取消退费提示：" + message + "已发药，不能取消退费");
//                    }
//                }
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
                        List<String> deleteIds =new ArrayList<>();
                        List<PharInWaitReceiveDTO> newReceiveDTOList =new ArrayList<>();
                        List<PharInReceiveDetailDTO> newPharInReceiveDetailList=new ArrayList<>();
                        List<PharInDistributeAllDetailDTO> newPharInDistributeAllDetailList=new ArrayList<>();
                        List<PharInDistributeDetailDTO> newPharInDistributeDetailList=new ArrayList<>();
                        List<PharInWaitReceiveDTO> backReceiveDTOList = pharInWaitReceiveService_consumer.queryPharInWaitReceiveToIsBack(HandParamMap(hospCode, "pharInWaitReceiveDTO", backReceiveDTO)).getData();
                        if (backReceiveDTOList != null && backReceiveDTOList.size() > 0) {
                            //有负的待领信息才需要新增一条待领记录
                            for (PharInWaitReceiveDTO dto : backReceiveDTOList) {
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
                                /** 已发药状态的退费
                                * 1、新增一条正的发药记录
                                * 2、找到原始发药记录将数量、拆零数量、总金额减去新增的发药记录更新，如全退删除原始纪录
                                * 3、删除负的发药记录
                                */
                                if (Constants.FYZT.FY.equals(dto.getStatusCode())){
                                    deleteIds.add(dto.getId());
                                    Map param = new HashMap();
                                    param.put("hospCode",hospCode);
                                    param.put("oldWrId",dto.getOldWrId());
                                    // 获取原始发药记录
                                    PharInWaitReceiveDTO ReceiveDTO = inptCostDAO.queryPharInWaitReceiveOrg(param);
                                    if (ReceiveDTO!=null){
                                        PharInWaitReceiveDTO inWaitReceiveParam =new PharInWaitReceiveDTO();
                                        inWaitReceiveParam.setId(ReceiveDTO.getId());
                                        inWaitReceiveParam.setHospCode(hospCode);
                                        inWaitReceiveParam.setNum(BigDecimalUtils.subtract(ReceiveDTO.getNum(), dto.getNum()));
                                        inWaitReceiveParam.setSplitNum(BigDecimalUtils.subtract(ReceiveDTO.getSplitNum(), dto.getSplitNum()));
                                        inWaitReceiveParam.setTotalPrice(BigDecimalUtils.subtract(ReceiveDTO.getTotalPrice(), dto.getTotalPrice()));
                                        if (BigDecimalUtils.isZero(inWaitReceiveParam.getNum())){
                                            // 全退删除原始发药记录
                                            PharInWaitReceiveDTO inWaitReceiveDTO = new PharInWaitReceiveDTO();
                                            inWaitReceiveDTO.setHospCode(hospCode);
                                            List<String> orgId =new ArrayList<>();
                                            orgId.add(inWaitReceiveParam.getId());
                                            inWaitReceiveDTO.setIds(orgId);
                                            inptCostDAO.deletePharInWaitReceive(inWaitReceiveDTO);
                                        }else {
                                            inptCostDAO.updatePharInWaitReceiveOrg(inWaitReceiveParam);
                                        }
                                    }
                                }
                                dto.setId(SnowflakeUtils.getId());
                                if (Constants.FYZT.FY.equals(dto.getStatusCode())) {
                                    // 获取原始领药明细
                                    Map pharInReceiveParam = new HashMap();
                                    pharInReceiveParam.put("hospCode", hospCode);
                                    pharInReceiveParam.put("wrId", dto.getOldWrId());
                                    PharInReceiveDetailDTO pharInReceiveDetailDTO = inptCostDAO.getPharInReceiveDetailByWrId(pharInReceiveParam);
                                    if (pharInReceiveDetailDTO != null) {
                                        PharInReceiveDetailDTO inReceiveDetailDTO = new PharInReceiveDetailDTO();
                                        inReceiveDetailDTO.setId(pharInReceiveDetailDTO.getId());
                                        inReceiveDetailDTO.setHospCode(hospCode);
                                        inReceiveDetailDTO.setNum(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getNum(), dto.getNum()));
                                        inReceiveDetailDTO.setSplitNum(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getSplitNum(), dto.getSplitNum()));
                                        inReceiveDetailDTO.setTotalPrice(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getTotalPrice(), dto.getTotalPrice()));
                                        // 更新或删除领药明细
                                        if (BigDecimalUtils.isZero(inReceiveDetailDTO.getNum())) {
                                            inptCostDAO.deletePharInReceiveDetailOrg(inReceiveDetailDTO);
                                        } else {
                                            inptCostDAO.updatePharInReceiveDetailOrg(inReceiveDetailDTO);
                                        }
                                        // 新增领药明细
                                        PharInReceiveDetailDTO newPharInReceiveDetailDTO = new PharInReceiveDetailDTO();
                                        newPharInReceiveDetailDTO = DeepCopy.deepCopy(pharInReceiveDetailDTO);//深度复制
                                        newPharInReceiveDetailDTO.setId(SnowflakeUtils.getId());
                                        newPharInReceiveDetailDTO.setHospCode(hospCode);
                                        newPharInReceiveDetailDTO.setWrId(dto.getId());
                                        newPharInReceiveDetailDTO.setNum(dto.getNum());
                                        newPharInReceiveDetailDTO.setSplitNum(dto.getSplitNum());
                                        newPharInReceiveDetailDTO.setTotalPrice(dto.getTotalPrice());
                                        newPharInReceiveDetailList.add(newPharInReceiveDetailDTO);
                                        Map distributeDetailAll = new HashMap();
                                        distributeDetailAll.put("hospCode", hospCode);
                                        distributeDetailAll.put("receiveId", pharInReceiveDetailDTO.getReceiveId());
                                        distributeDetailAll.put("id", pharInReceiveDetailDTO.getId());
                                        PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO = inptCostDAO.queryAllPharDistributeOrg(distributeDetailAll);
                                        PharInDistributeDetailDTO pharInDistributeDetailDTO = inptCostDAO.getPharInDistributeDetailByWrId(distributeDetailAll);
                                        if (pharInDistributeAllDetailDTO != null) {
                                            PharInDistributeAllDetailDTO phar = new PharInDistributeAllDetailDTO();
                                            phar.setId(pharInDistributeAllDetailDTO.getId());
                                            phar.setHospCode(hospCode);
                                            phar.setNum(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getNum(), dto.getNum()));
                                            phar.setSplitNum(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getSplitNum(), dto.getSplitNum()));
                                            phar.setTotalPrice(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getTotalPrice(), dto.getTotalPrice()));
                                            // 更新或删除药房住院分批发药明细
                                            if (BigDecimalUtils.isZero(phar.getNum())) {
                                                inptCostDAO.deleteInDistributeAllDetailOrg(phar);
                                            } else {
                                                inptCostDAO.updateAllPharDistributeOrg(phar);
                                            }
                                            // 新增药房住院分批发药明细
                                            PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO1 = new PharInDistributeAllDetailDTO();
                                            pharInDistributeAllDetailDTO1 = DeepCopy.deepCopy(pharInDistributeAllDetailDTO);//深度复制
                                            pharInDistributeAllDetailDTO1.setId(SnowflakeUtils.getId());
                                            pharInDistributeAllDetailDTO1.setHospCode(hospCode);
                                            pharInDistributeAllDetailDTO1.setIrdId(newPharInReceiveDetailDTO.getId());
                                            pharInDistributeAllDetailDTO1.setNum(dto.getNum());
                                            pharInDistributeAllDetailDTO1.setSplitNum(dto.getSplitNum());
                                            pharInDistributeAllDetailDTO1.setTotalPrice(dto.getTotalPrice());
                                            newPharInDistributeAllDetailList.add(pharInDistributeAllDetailDTO1);
                                        }
                                        if (pharInDistributeDetailDTO != null) {
                                            PharInDistributeDetailDTO phar = new PharInDistributeDetailDTO();
                                            phar.setId(pharInDistributeDetailDTO.getId());
                                            phar.setHospCode(hospCode);
                                            phar.setNum(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getNum(), dto.getNum()));
                                            phar.setSplitNum(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getSplitNum(), dto.getSplitNum()));
                                            phar.setTotalPrice(BigDecimalUtils.subtract(pharInReceiveDetailDTO.getTotalPrice(), dto.getTotalPrice()));
                                            // 更新或删除发药明细
                                            if (BigDecimalUtils.isZero(phar.getNum())) {
                                                inptCostDAO.deleteInDistributeDetailOrg(phar);
                                            } else {
                                                inptCostDAO.updatePharInDistributeDetailOrg(phar);
                                            }
                                            // 新增发药明细
                                            PharInDistributeDetailDTO pharInDistributeDetailDTO1 = new PharInDistributeDetailDTO();
                                            pharInDistributeDetailDTO1 = DeepCopy.deepCopy(pharInDistributeDetailDTO);//深度复制
                                            pharInDistributeDetailDTO1.setId(SnowflakeUtils.getId());
                                            pharInDistributeDetailDTO1.setHospCode(hospCode);
                                            pharInDistributeDetailDTO1.setIrdId(newPharInReceiveDetailDTO.getId());
                                            pharInDistributeDetailDTO1.setNum(dto.getNum());
                                            pharInDistributeDetailDTO1.setSplitNum(dto.getSplitNum());
                                            pharInDistributeDetailDTO1.setTotalPrice(dto.getTotalPrice());
                                            newPharInDistributeDetailList.add(pharInDistributeDetailDTO1);
                                        }
                                    }
                                }
                                dto.setOldWrId(null);
                                newReceiveDTOList.add(dto);
                            }
                            if (!ListUtils.isEmpty(newReceiveDTOList)) {
                                //新增待领记录
                                inptCostDAO.insertPharInWaitReceiveBatch(newReceiveDTOList);
                            }

                            if (!ListUtils.isEmpty(newPharInReceiveDetailList)) {
                                // 新增领药明细
                                inptCostDAO.insertPharInReceiveDetail(newPharInReceiveDetailList);
                            }
                            if (!ListUtils.isEmpty(newPharInDistributeAllDetailList)) {
                                // 新增药房住院分批发药明细
                                inptCostDAO.insertInDistributeAllDetail(newPharInDistributeAllDetailList);
                            }
                            if (!ListUtils.isEmpty(newPharInDistributeDetailList)) {
                                // 新增发药明细
                                inptCostDAO.insertInDistributeDetail(newPharInDistributeDetailList);
                            }
                            if (deleteIds!=null &&deleteIds.size()>0) {
                                // 删除退药记录
                                PharInWaitReceiveDTO inWaitReceiveDTO = new PharInWaitReceiveDTO();
                                inWaitReceiveDTO.setHospCode(hospCode);
                                inWaitReceiveDTO.setIds(deleteIds);
                                inptCostDAO.deletePharInWaitReceive(inWaitReceiveDTO);
                            }
                        }
                    }
                    // 取消退费后更新就诊表中的总费用（lly 2022-08-15 13:42 start）
                    InptVisitDTO inptVisitDTO =new InptVisitDTO();
                    inptVisitDTO.setId(visitId);
                    inptVisitDTO.setHospCode(hospCode);
                    inptVisitDAO.updateTotalCost(inptVisitDTO);
                    // 取消退费后更新就诊表中的总费用（lly 2022-08-15 13:42 end）
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
