package cn.hsa.stro.adjust.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.ba.service.BaseAdviceService;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.stro.adjust.bo.StroAdjustBO;
import cn.hsa.module.stro.adjust.dao.StroAdjustDao;
import cn.hsa.module.stro.adjust.dao.StroAdjustDetailDao;
import cn.hsa.module.stro.adjust.dto.StroAdjustDTO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 *@Package_name: cn.hsa.module.stro.adjust.bo
 *@Class_name: StroAdjustBOImpl
 *@Describe: 药品调价业务实现类
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/1 19:36
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class StroAdjustBOImpl extends HsafBO implements StroAdjustBO {

    @Resource
    StroAdjustDao stroAdjustDao;

    @Resource
    StroAdjustDetailDao stroAdjustDetailDao;

    @Resource
    BaseAdviceService baseAdviceService_provider;

    @Resource
    private StroStockBO stroStockBO;

    @Resource
    BaseOrderRuleService baseOrderRuleService_consumer;

    @Resource
    SysCodeService sysCodeService_consumer;

    /**
    * @Method queryStroAdjustDoPage
    * @Desrciption 分页查询
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 21:21
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryStroAdjustDtoPage(StroAdjustDTO stroAdjustDTO) {
        PageHelper.startPage(stroAdjustDTO.getPageNo(), stroAdjustDTO.getPageSize());
        List<StroAdjustDTO> list = stroAdjustDao.queryStroAdjustDtoPage(stroAdjustDTO);

        List<String> ids = new ArrayList<>();
        StroAdjustDTO pdto = new StroAdjustDTO();
        if(!ListUtils.isEmpty(list)){
            for(StroAdjustDTO dto:list){
                pdto = new StroAdjustDTO();
                ids = new ArrayList<>();
                ids.add(dto.getId());
                pdto.setHospCode(dto.getHospCode());
                pdto.setIds(ids);
                List<StroAdjustDetailDTO> stroAdjustDetailDTOS = stroAdjustDetailDao.queryStroAdjustDetailDtoPage(pdto);
                dto.setStroAdjustDetailDTOs(stroAdjustDetailDTOS);
            }
        }
        return PageDTO.of(list);
    }

    /**
    * @Menthod queryStroAdjustDtoAll
    * @Desrciption 查询所有调价单 根据id
    *
    * @Param
    * [stroAdjustDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 15:48
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
    **/
    @Override
    public List<StroAdjustDTO> queryStroAdjustDtoAll(StroAdjustDTO stroAdjustDTO) {
      List<StroAdjustDTO> list = stroAdjustDao.queryStroAdjustDtoPage(stroAdjustDTO);

      List<String> ids = new ArrayList<>();
      StroAdjustDTO pdto = new StroAdjustDTO();
      if(!ListUtils.isEmpty(list)){
        for(StroAdjustDTO dto:list){
          pdto = new StroAdjustDTO();
          ids = new ArrayList<>();
          ids.add(dto.getId());
          pdto.setHospCode(dto.getHospCode());
          pdto.setIds(ids);
          List<StroAdjustDetailDTO> stroAdjustDetailDTOS = stroAdjustDetailDao.queryStroAdjustDetailDtoPage(pdto);
          dto.setStroAdjustDetailDTOs(stroAdjustDetailDTOS);
        }
      }

      return list;
    }

  /**
     * @Method queryStroAdjustDtoById
     * @Desrciption 单个查询调价单
     * @param stroAdjustDTO
     * @Author liuqi1
     * @Date   2020/8/5 11:07
     * @Return cn.hsa.module.stro.adjust.dto.StroAdjustDTO
     **/
    @Override
    public StroAdjustDTO queryStroAdjustDtoById(StroAdjustDTO stroAdjustDTO) {
        StroAdjustDTO stroAdjustDtoById = stroAdjustDao.getStroAdjustDtoById(stroAdjustDTO);

        List<String> ids = new ArrayList<>();
        ids.add(stroAdjustDTO.getId());
        stroAdjustDTO.setIds(ids);
        List<StroAdjustDetailDTO> stroAdjustDetailDTOS = stroAdjustDetailDao.queryStroAdjustDetailDtoPage(stroAdjustDTO);

        stroAdjustDtoById.setStroAdjustDetailDTOs(stroAdjustDetailDTOS);
        return stroAdjustDtoById;
    }

    /**
    * @Method queryStroAdjustDetailDtoPage
    * @Desrciption 获得调价明细
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/4 10:14
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryStroAdjustDetailDtoPage(StroAdjustDTO stroAdjustDTO) {
        PageHelper.startPage(stroAdjustDTO.getPageNo(), stroAdjustDTO.getPageSize());
        List<StroAdjustDetailDTO> list = stroAdjustDetailDao.queryStroAdjustDetailDtoPage(stroAdjustDTO);
        return PageDTO.of(list);
    }

    /**
    * @Method queryStroAdjustDetailDtos
    * @Desrciption 根据条件查询出调价明细
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/9/8 13:57
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO>
    **/
    @Override
    public List<StroAdjustDetailDTO> queryStroAdjustDetailDtos(StroAdjustDTO stroAdjustDTO) {
        List<StroAdjustDetailDTO> stroAdjustDetailDTOS = stroAdjustDetailDao.queryStroAdjustDetailDTOs(stroAdjustDTO);
        return stroAdjustDetailDTOS;
    }

    /**
    * @Method insertStroAdjustDo
    * @Desrciption 新增或更新调价信息
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 21:22
    * @Return boolean
    **/
    @Override
    public boolean insertOrUpdateStroAdjustDto(StroAdjustDTO stroAdjustDTO) {
        Boolean isSuccess = false;//操作成功标示，默认false：失败
        Date nowTime = DateUtils.getNow();
        String id = SnowflakeUtils.getId();


        if(StringUtils.isEmpty(stroAdjustDTO.getId())){
            HashMap map = new HashMap();
            map.put("hospCode", stroAdjustDTO.getHospCode());
            map.put("typeCode", Constants.ORDERRULE.TJ);
            WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(map);

            //新增
           stroAdjustDTO.setOrderNo(orderNo.getData());
           stroAdjustDTO.setId(id);
           stroAdjustDTO.setCrteTime(nowTime);
           stroAdjustDTO.setAuditCode("0");
           stroAdjustDao.insertStroAdjustDto(stroAdjustDTO);

            //更新调价明细数据
            updateStroAdjustDetailDTO(stroAdjustDTO);

           isSuccess = true;
       }else{
            StroAdjustDTO stroAdjustDtoById = stroAdjustDao.getStroAdjustDtoById(stroAdjustDTO);
            if(!"0".equals(stroAdjustDtoById.getAuditCode())){
                //如果单据不处于未审核状态，不允许更新
                throw new AppException("操作失败,单据已审核");
            }

            //更新
           stroAdjustDao.updateStroAdjustDto(stroAdjustDTO);

           //先删除后新增
           stroAdjustDetailDao.deleteStroAdjustDetailDTO(stroAdjustDTO);
           //更新调价明细数据
           updateStroAdjustDetailDTO(stroAdjustDTO);
           isSuccess = true;
       }

        return isSuccess;
    }

    /**
    * @Method updateStroAdjustDetailDTO
    * @Desrciption 更新调价明细数据
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/4 20:53
    * @Return void
    **/
    private void updateStroAdjustDetailDTO(StroAdjustDTO stroAdjustDTO){
        // 调价是否过滤掉科室库存，注意我们虽然没有科室库存的概念，但是出库到科室确认之后，库存表中的biz_id就是科室的
        Map<String, String> sfdeptFilterMap = this.getParameterValue(stroAdjustDTO.getHospCode(),
                new String[]{"SF_DEPTFILTER"});
        String sfdeptFilter = MapUtils.get(sfdeptFilterMap, "SF_DEPTFILTER", "0");
        List<StroAdjustDetailDTO> lists = stroAdjustDTO.getStroAdjustDetailDTOs();
        //根据项目id进行分组，filter是为了防止空指针
        Map<String, List<StroAdjustDetailDTO>> listsMap = lists.stream().filter(x -> StringUtils.isNotEmpty(x.getItemId()))
                .collect(Collectors.groupingBy(StroAdjustDetailDTO::getItemId));
        // 得到项目id集合
        List<String> items = lists.stream().map(StroAdjustDetailDTO::getItemId).collect(Collectors.toList());
        //根据项目id查询所有库存
        List<StroStockDTO> stroStockDTOS = stroAdjustDetailDao.queryStockByItemIds(stroAdjustDTO.getHospCode(),items,sfdeptFilter);
        //根据项目id进行分组，项目id作为key
        Map<String, List<StroStockDTO>> stroStocksMap = stroStockDTOS.stream().filter(x -> StringUtils.isNotEmpty(x.getItemId()))
                .collect(Collectors.groupingBy(StroStockDTO::getItemId));
        // 插入的明细集合
        List<StroAdjustDetailDTO> listInsert = new ArrayList<>();
        // 调价单调价前总金额
        BigDecimal sumBeforePrice = BigDecimal.valueOf(0);
        // 调价单调价后总金额
        BigDecimal sumAfterPrice = BigDecimal.valueOf(0);
        //遍历map,同一个药，不同的库位
        for (Map.Entry<String, List<StroStockDTO>> entry : stroStocksMap.entrySet()) {
            String itemId = entry.getKey();
            List<StroStockDTO> value = entry.getValue();
            if (listsMap.containsKey(itemId)) {
                // 取调价前后的金额
                List<StroAdjustDetailDTO> stroAdjustDetailDTOS = listsMap.get(itemId);
                StroAdjustDetailDTO adjustDetailDTO = stroAdjustDetailDTOS.get(0);
                // 封装数据
                for (StroStockDTO x : value) {
                    StroAdjustDetailDTO newStroAdjustDetailDTO = new StroAdjustDetailDTO();
                    BeanUtils.copyProperties(adjustDetailDTO,newStroAdjustDetailDTO);
                    newStroAdjustDetailDTO.setNum(x.getNum());
                    newStroAdjustDetailDTO.setSplitNum(x.getSplitNum());
                    newStroAdjustDetailDTO.setId(SnowflakeUtils.getId());
                    newStroAdjustDetailDTO.setAdjustId(stroAdjustDTO.getId());
                    newStroAdjustDetailDTO.setBizId(x.getBizId());
                    newStroAdjustDetailDTO.setHospCode(stroAdjustDTO.getHospCode());
                    listInsert.add(newStroAdjustDetailDTO);
                    // 调价前总金额
                    sumBeforePrice = BigDecimalUtils.add(sumBeforePrice,BigDecimalUtils.multiply(x.getNum(),newStroAdjustDetailDTO.getBeforePrice()));
                    // 调价后总金额
                    sumAfterPrice = BigDecimalUtils.add(sumAfterPrice,BigDecimalUtils.multiply(x.getNum(),newStroAdjustDetailDTO.getAfterPrice()));
                }
            }
        }

        //批量新增调价明细
        stroAdjustDetailDao.insertStroAdjustDetailDTO(listInsert);
        // 回写主表总金额
        // 调价单调价前总金额
        stroAdjustDTO.setAfterPrice(sumAfterPrice);
        // 调价单调价后总金额
        stroAdjustDTO.setBeforePrice(sumBeforePrice);
        // 回写主表总价格
        stroAdjustDao.updateAdjustDTOPriceById(stroAdjustDTO);

    }

    /**
    * @Method updateStroAdjustDo
    * @Desrciption 批量审核或作废调价
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 21:22
    * @Return boolean
    **/
    @Override
    public boolean updateOrCancelStroAdjustDto(StroAdjustDTO stroAdjustDTO) {
        if(StringUtils.isEmpty(stroAdjustDTO.getAuditCode())){
            return false;
        }
        List<StroAdjustDTO> stroAdjustDTOS = stroAdjustDao.queryStroAdjustDtos(stroAdjustDTO);
        stroAdjustDTOS.forEach(dto -> {
            if(!"0".equals(dto.getAuditCode())){
                //如果单据不处于未审核状态，不允许更新
                throw new AppException("操作失败,单据已审核");
            }
        });
        stroAdjustDTO.setAuditTime(DateUtils.getNow());
        Boolean isSuccess = false;
        if("1".equals(stroAdjustDTO.getAuditCode())){
            //根据调价单id集合获得调价明细集合
            List<StroAdjustDetailDTO> stroAdjustDetailDTOs = stroAdjustDetailDao.queryStroAdjustDetailDTOs(stroAdjustDTO);
            //查询调价明细转为库存明细
            List<StroStockDetailDTO> stroStockDetailDTOS = stroAdjustDetailDao.queryAdjustStroStockDetailDTOs(stroAdjustDTO);
            stroStockDetailDTOS.stream().forEach(detial->{
                detial.setNum(BigDecimal.valueOf(0));
                detial.setSplitNum(BigDecimal.valueOf(0));
            });
            // 校验药品是否存在同级调拨、退供应商、平级出库等未审核完成的情况
            judgeAdjustDruag(stroStockDetailDTOS);
            // 调价是否过滤掉科室库存，注意我们虽然没有科室库存的概念，但是出库到科室确认之后，库存表中的biz_id就是科室的
            Map<String, String> sfdeptFilterMap = this.getParameterValue(stroAdjustDTO.getHospCode(),
                    new String[]{"SF_DEPTFILTER"});
            String sfdeptFilter = MapUtils.get(sfdeptFilterMap, "SF_DEPTFILTER", "0");

            Map<String,Object> jxcMap = new HashMap<>();
            jxcMap.put("type", Constants.CRFS.TJ);
            jxcMap.put("sfBatchNo", true);
            jxcMap.put("hospCode",stroAdjustDTO.getHospCode());
            jxcMap.put("stroStockDetailDTOList",stroStockDetailDTOS);
            jxcMap.put("sfdeptFilter",sfdeptFilter);
            stroStockBO.saveStock(jxcMap);
            //更新库存
            stroAdjustDetailDao.adjustUpdateStock(stroAdjustDetailDTOs,sfdeptFilter);
            stroAdjustDetailDao.adjustUpdateStockDetail(stroAdjustDetailDTOs,sfdeptFilter);

            //审核
            stroAdjustDao.updateStroAdjustDtoByIds(stroAdjustDTO);
            // 回写调价单数据
            for (StroAdjustDTO stroAdjustDTO1 : stroAdjustDTOS){
                // 该调价单下所有明细数据
                List<StroAdjustDetailDTO> stroAdjustDetailDTOList= stroAdjustDetailDao.queryStroAdjustDetailById(stroAdjustDTO1);
                //先删除后新增
                stroAdjustDetailDao.deleteStroAdjustDetailDTO(stroAdjustDTO1);
                stroAdjustDTO1.setStroAdjustDetailDTOs(stroAdjustDetailDTOList);
                updateStroAdjustDetailDTO(stroAdjustDTO1);
                // 如果来源方式为入库调价，回写入库单数据
                if("0".equals(stroAdjustDTO1.getAdjustCode())){
                    stroAdjustDTO1.setOperateStatus("1");
                    updateStroIn(stroAdjustDTO1);
                }
            }
            List<BaseAdviceDetailDTO> baseAdviceDetailDTOList = new ArrayList<>();
            List<StroAdjustDetailDTO> drugList = new ArrayList<>();
            List<StroAdjustDetailDTO> materialList = new ArrayList<>();
            for(StroAdjustDetailDTO dto:stroAdjustDetailDTOs){
                //如果是材料，需要更新医嘱表的价格
                if("2".equals(dto.getItemCode())){
                    BaseAdviceDetailDTO badto = new BaseAdviceDetailDTO();
                    badto.setHospCode(dto.getHospCode());
                    badto.setItemCode(dto.getMaterialCode());
                    badto.setPrice(dto.getAfterPrice());
                    baseAdviceDetailDTOList.add(badto);
                    materialList.add(dto);
                } else {
                  drugList.add(dto);
                }
            }
            if(baseAdviceDetailDTOList.size() > 0){
                Map<String,Object> m = new HashMap<>();
                m.put("hospCode", stroAdjustDTO.getHospCode());
                m.put("baseAdviceDetailDTOList",baseAdviceDetailDTOList);
                //更新医嘱
                baseAdviceService_provider.updateBaseAdviceAndBaseAdviceDetailsByItemCode(m);
            }
            //更新药品零售价
            if(!ListUtils.isEmpty(drugList)){
            stroAdjustDetailDao.adjustUpdateDrug(drugList);
          }
            // 更新材料价格
            if(!ListUtils.isEmpty(materialList)){
            stroAdjustDetailDao.adjustUpdateMaterial(materialList);
          }
          isSuccess = true;
        }else if("2".equals(stroAdjustDTO.getAuditCode())){
            //作废
            stroAdjustDao.updateStroAdjustDtoByIds(stroAdjustDTO);
            isSuccess = true;
        }
        return isSuccess;
    }
    /**
     * @Author gory
     * @Description 回写入库单
     * @Date 2022/9/14 15:02
     * @Param [stroAdjustDTO]
     **/
    private void updateStroIn(StroAdjustDTO stroAdjustDTO) {
        List<StroAdjustDetailDTO> stroAdjustDetailDTOs = stroAdjustDTO.getStroAdjustDetailDTOs();
        if ("1".equals(stroAdjustDTO.getOperateStatus())){// 如果是入库审核，则回写入库单为最新的零售价
            // 更新入库单明细: 售价 + 零售总金额 + 拆零单价,暂存状态改成未审核状态
            stroAdjustDao.updateStroInDetail(stroAdjustDetailDTOs,stroAdjustDTO.getSourceId());
            // 更新入库单: 零售总金额
            stroAdjustDao.updateStroIn(stroAdjustDTO.getSourceId());
        }
    }

    /**
     * @Method judgeAdjustDruag
     * @Desrciption 校验是否能够审核
     * @Param [stroStockDetailDTOS]
     * @Author zhangguorui
     * @Date   2021/8/3 9:21
     * @Return void
     */
    private void judgeAdjustDruag(List<StroStockDetailDTO> stroStockDetailDTOS) {
        Optional.ofNullable(stroStockDetailDTOS).orElseThrow(() -> new AppException("调价的药品不能为空"));
        String hospCode = stroStockDetailDTOS.get(0).getHospCode();
        // 取出药品id
        List<String> druagList = stroStockDetailDTOS.stream().map(StroStockDetailDTO::getItemId).collect(Collectors.toList());
        // 判断同级调拨、出库到科室、出库到药房（未审核的数据中，是否存在调价单中的项目，如果存在则不允许审核调价单，并给出提示）--出库表、库存明细表
        // Map 返回的字段有 outCode 出库方式,itemId 项目id,itemName 项目名称
        List<Map<String, String>> stoMap = stroAdjustDao.selectJudgeStoOutDruag(druagList, hospCode);
        // 判断采购入库
        List<Map<String, String>> phrMap = stroAdjustDao.selectJudgePhrDruag(druagList, hospCode);
        // 判断直接入库、同级调拨确认、退供应商、平级出库
        List<Map<String, String>> stoInMap = stroAdjustDao.selectJudgeStoInDruag(druagList, hospCode);
        // 判断盘盈盘亏
        List<Map<String, String>> inventoryMap = stroAdjustDao.selectJudgeInventoryDruag(druagList, hospCode);
        // 判断报损报益
        List<Map<String, String>> incdecMap = stroAdjustDao.selectJudgeIncdecDruag(druagList, hospCode);
        // 入库确认、退库确认
        // 是否过滤掉科室确认 1是需要科室确认  其他是不需要
        Map<String, String> deptConfirmMap = this.getParameterValue(hospCode,
                new String[]{"DEPT_CONFIRM"});
        String deptConfirm = MapUtils.get(deptConfirmMap, "DEPT_CONFIRM", "0");
        List<Map<String, String>> confirmMap = stroAdjustDao.selectJudgeconfirmDruag(druagList, hospCode,deptConfirm);
        // 把上面所有的map 全部封装到 resultMapList中，做一个全部汇总提示
        List<Map<String,String>> resultMapList = new ArrayList<>();
        resultMapList.addAll(stoMap);
        resultMapList.addAll(phrMap);
        resultMapList.addAll(stoInMap);
        resultMapList.addAll(inventoryMap);
        resultMapList.addAll(incdecMap);
        resultMapList.addAll(confirmMap);
        if(!ListUtils.isEmpty(resultMapList)){
            // 查询码表 获得出入方式
            SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
            sysCodeDetailDTO.setCode("CRFS");
            sysCodeDetailDTO.setHospCode(hospCode);
            Map queryCodeMap = new HashMap();
            queryCodeMap.put("sysCodeDetailDTO",sysCodeDetailDTO);
            queryCodeMap.put("hospCode",hospCode);
            // 调用值域service 获得所有的出入方式的值
            WrapperResponse<List<SysCodeDetailDTO>> listWrapperResponse = sysCodeService_consumer.queryCodeDetailAll(queryCodeMap);
            List<SysCodeDetailDTO> codeList = listWrapperResponse.getData();
            // 过滤码表 value 作为key, name作为value
            Map<String, String> codeMap = codeList.stream().collect(Collectors.toMap(SysCodeDetailDTO::getValue, SysCodeDetailDTO::getName));
            // 根据出入库方式进行分组
            Map<String, List<Map<String, String>>> messageMap = resultMapList.stream().collect(Collectors.groupingBy(item -> item.get("outCode")));
            // 最后的异常信息
            StringBuffer errorMessage = new StringBuffer("审核失败，");
            messageMap.entrySet().forEach(item->{
                List<Map<String, String>> value = item.getValue();
                List<String> itemName = value.stream().map(map -> map.get("itemName")).distinct().collect(Collectors.toList());
                String message = itemName.toString();
                // 出入方式
                String crfs = codeMap.get(item.getKey());
                if ("退库确认".equals(crfs)){
                    errorMessage.append("退库确认或者入库确认中 "+message+" 还未审核。");
                }else{
                    errorMessage.append(crfs+"中 "+message+" 还未审核。");
                }

            });
            throw new AppException(errorMessage.toString());
        }
    }

    /**
     * 获取系统参数
     *
     * @param hospCode
     * @param code
     * @return
     */
    public Map<String, String> getParameterValue(String hospCode, String[] code) {
        List<SysParameterDTO> list = stroAdjustDao.getParameterValue(hospCode, code);
        Map<String, String> retMap = new HashMap<>();
        if (!MapUtils.isEmpty(list)) {
            for (SysParameterDTO hit : list) {
                retMap.put(hit.getCode(), hit.getValue());
            }
        }
        return retMap;
    }
    /**
    * @Menthod updateAdjustDetail
    * @Desrciption 回写调价单   调价总库存得总数量总金额
    *
    * @Param
    * [stroAdjustDetailDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 11:39
    * @Return void
    **/
    public void updateAdjustDetail(List<StroAdjustDTO> stroAdjustDTOS,String sfdeptFilter) {
        for (StroAdjustDTO stroAdjustDTO: stroAdjustDTOS) {
            List ids = new ArrayList();
            ids.add(stroAdjustDTO.getId());
            stroAdjustDTO.setIds(ids);
            // 该调价单下所有明细数据
            List<StroAdjustDetailDTO> stroAdjustDetailDTOs = stroAdjustDetailDao.queryStroAdjustDetailDTOs(stroAdjustDTO);
            if(ListUtils.isEmpty(stroAdjustDetailDTOs)) {
                throw new AppException("调价单明细不能为空");
            }
            Map<String, List<StroAdjustDetailDTO>> collect = stroAdjustDetailDTOs.stream().collect(Collectors.groupingBy(StroAdjustDetailDTO::getItemId));
            for(String key: collect.keySet()) {
                List<StroAdjustDetailDTO> stroAdjustDetailDTOS = collect.get(key);
                if(stroAdjustDetailDTOS.size() >= 2) {
                    throw new AppException("调价单中有重复的项目");
                }
            }
            // 调价单调价前总金额
            BigDecimal sumBeforePrice = BigDecimal.valueOf(0);
            // 调价单调价后总金额
            BigDecimal sumAfterPrice = BigDecimal.valueOf(0);
            for (StroAdjustDetailDTO stroAdjustDetailDTO :stroAdjustDetailDTOs) {
                List<StroStockDTO> stroStockDTOS = stroAdjustDetailDao.queryStockSumNum(stroAdjustDetailDTO);
                if(!ListUtils.isEmpty(stroStockDTOS)) {
                    stroAdjustDetailDTO.setNum(stroStockDTOS.get(0).getNum());
                    stroAdjustDetailDTO.setSplitNum(stroStockDTOS.get(0).getSplitNum());
                    // 调价前总金额
                    sumBeforePrice = BigDecimalUtils.add(sumBeforePrice,BigDecimalUtils.multiply(stroAdjustDetailDTO.getNum(),stroAdjustDetailDTO.getBeforePrice()));
                    // 调价后总金额
                    sumAfterPrice = BigDecimalUtils.add(sumAfterPrice,BigDecimalUtils.multiply(stroAdjustDetailDTO.getNum(),stroAdjustDetailDTO.getAfterPrice()));
                }
            }
            // 调价单调价前总金额
            stroAdjustDTO.setAfterPrice(sumAfterPrice);
            // 调价单调价后总金额
            stroAdjustDTO.setBeforePrice(sumBeforePrice);
            // 回写明细表中的数量
            stroAdjustDetailDao.updateAdjustDetailNum(stroAdjustDetailDTOs);
        }
        // 回写主表总价格
        stroAdjustDao.updateAdjustPriceById(stroAdjustDTOS);
    }
}
