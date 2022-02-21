package cn.hsa.stro.stroout.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.phar.pharapply.dao.PharApplyDAO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDTO;
import cn.hsa.module.phar.pharapply.entity.StroOutDetail;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.stro.backstroconfirm.bo.BackStroConfirmBO;
import cn.hsa.module.stro.backstroconfirm.dao.BackStroConfirmDAO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroin.dao.StroInDAO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import cn.hsa.module.stro.stroout.bo.StroOutBO;
import cn.hsa.module.stro.stroout.dao.StroOutDAO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import cn.hsa.stro.storin.bo.impl.StroInBOImpl;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.stro.stroout.bo.impl
 * @Class_name: StroOutBoImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/25 14:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class StroOutBOImpl extends HsafBO implements StroOutBO {

    /**
     * 药库出库数据库访问接口
     */
    @Resource
    private StroOutDAO stroOutDAO;

    /**
     * 药库入库数据库访问接口
     */
    @Resource
    private StroInDAO stroInDAO;

    /**
     * 药库库存数据库访问接口
     */
    @Resource
    private StroStockBO stroStockBO;

    /**
     * 药库入库BO访问接口
     */
    @Resource
    private StroInBOImpl stroInBO;

    /**
     * 单据生成规则dubbo消费者接口
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    /**
     * 领药申请
     */
    @Resource
    private PharApplyDAO pharApplyDAO;

    @Resource
    private BackStroConfirmDAO backStroConfirmDAO;

    /**
     * @Method getById
     * @Desrciption 通过id查询
     * @Param [stroOutinDTO]
     * @Author liaojunjie
     * @Date 2020/7/30 19:21
     * @Return cn.hsa.module.stro.outinstro.dto.StroOutinDTO
     **/
    @Override
    public StroOutDTO getById(StroOutDTO stroOutDTO) {
        //首先通过ID查到出库单
        StroOutDTO byId = this.stroOutDAO.getById(stroOutDTO);
        //通过出库单查到出库明细
        List<StroOutDetailDTO> stroOutDetailDTOS = stroOutDAO.queryAllDetails(this.stroOutDAO.getById(stroOutDTO));
        //把出库明细塞进出库单中
        byId.setStroOutDetailDTOS(stroOutDetailDTOS);
        return byId;
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param [stroOutinDTO]
     * @Author liaojunjie
     * @Date 2020/7/30 19:21
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(StroOutDTO stroOutDTO) {
        // 康德需求之出库单添加名字过滤
        String itemNameKey = stroOutDTO.getItemNameKey();
        if (StringUtils.isNotEmpty(itemNameKey)){
            List<StroOutDetailDTO> stroOutDetails = stroOutDAO.queryAllDetails(stroOutDTO);
            if (!ListUtils.isEmpty(stroOutDetails)){
                List<String> inIds = stroOutDetails.stream().map(StroOutDetailDTO::getOutId).distinct().collect(Collectors.toList());
                stroOutDTO.setIds(inIds);
                stroOutDTO.setId("");
            }
        }

        PageHelper.startPage(stroOutDTO.getPageNo(), stroOutDTO.getPageSize());
        //查出页面大小数量的数据
        List<StroOutDTO> stroOutinDTOS = this.stroOutDAO.queryPage(stroOutDTO);
        if (!ListUtils.isEmpty(stroOutinDTOS)) {
            for (StroOutDTO item : stroOutinDTOS){
                if (StringUtils.isNotEmpty(itemNameKey)){
                    item.setItemNameKey(itemNameKey);
                }
                item.setStroOutDetailDTOS(stroOutDAO.queryAllDetails(item));
            }
        }
        return PageDTO.of(stroOutinDTOS);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有
     * @Param [stroOutinDTO]
     * @Author liaojunjie
     * @Date 2020/7/30 19:21
     * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutDTO>
     **/
    @Override
    public List<StroOutDTO> queryAll(StroOutDTO stroOutDTO) {
        return this.stroOutDAO.queryAll(stroOutDTO);
    }

    /**
     * @Method updateAuditCode
     * @Desrciption 审核/作废
     * @Param [stroOutDTO]
     * @Author liaojunjie
     * @Date 2020/7/30 19:21
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateAuditCode(StroOutDTO stroOutDTO) {
        List<StroOutDTO> queryResult = stroOutDAO.queryByIds(stroOutDTO);
        for (StroOutDTO outDTO : queryResult) {
            if (!(("0").equals(outDTO.getAuditCode()))) {
                throw new AppException("操作异常，请审核待审核状态的记录");
            }
        }
        //作废操作
        if (!("1").equals(stroOutDTO.getAuditCode())) {
//          List<String> collect = queryResult.stream().map(StroOutDTO::getInOrderNo).collect(Collectors.toList());
//          PharApplyDTO pharApplyDTO = new PharApplyDTO();
//          pharApplyDTO.setHospCode(stroOutDTO.getHospCode());
//          pharApplyDTO.setIds(collect);
//          pharApplyDTO.setIsOut("0");
//          pharApplyDTO.setOutStroId(stroOutDTO.getOutStockId());
//          pharApplyDAO.updateIsOutByOrderNo(pharApplyDTO);
          return this.stroOutDAO.updateAuditCode(stroOutDTO) > 0;
        } else {
//            //判断库存
//            this.judgeStock(stroOutDTO);
            //审核操作
            //将不同的审核类型分组

            //存储出库到科室的id
            List<String> fourIds = new ArrayList<>();
            //存储出库到药房的id
            List<String> fiveIds = new ArrayList<>();
            //存储同级调拨的id
            List<String> nineIds = new ArrayList<>();
            //存储整单出库的id
            List<String> tSixIds = new ArrayList<>();
            //循环不同的出库类型塞进对应的List中
            for (StroOutDTO outDTO : queryResult) {
                if ("4".equals(outDTO.getOutCode())) {
                    fourIds.add(outDTO.getId());
                }
                if ("5".equals(outDTO.getOutCode())) {
                    fiveIds.add(outDTO.getId());
                }
                if ("10".equals(outDTO.getOutCode())) {
                    nineIds.add(outDTO.getId());
                }
                if ("26".equals(outDTO.getOutCode())) {
                    nineIds.add(outDTO.getId());
                }
            }
            //出库类型为出库到科室的List
            List<StroStockDetailDTO> stockListFour = getStockList(stroOutDTO, fourIds, stroOutDTO.getHospCode());
            //出库类型为出库到药房的List
            List<StroStockDetailDTO> stockListFive = getStockList(stroOutDTO, fiveIds, stroOutDTO.getHospCode());
            //出库类型为同级调拨的List
            List<StroStockDetailDTO> stockListNine = getStockList(stroOutDTO, nineIds, stroOutDTO.getHospCode());
            //出库类型为整单出库的List
            List<StroStockDetailDTO> stockListTSix = getStockList(stroOutDTO, tSixIds, stroOutDTO.getHospCode());


            handleStock(stockListFour, "4", stroOutDTO.getHospCode());
            handleStock(stockListFive, "5", stroOutDTO.getHospCode());
            handleStock(stockListNine, "10", stroOutDTO.getHospCode());
            handleStock(stockListTSix, "26", stroOutDTO.getHospCode());

            stroOutDTO.setOkAuditCode("0");
            stroOutDAO.updateAuditCode(stroOutDTO);
            return true;
        }
    }

    /**
     * @Method getStockList
     * @Desrciption 获取到处理库存需要的拼接的List
     * @Param [ids, hospCode]
     * @Author liaojunjie
     * @Date 2020/8/30 16:43
     * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
     **/
    private List<StroStockDetailDTO> getStockList(StroOutDTO stroOutDTO, List<String> ids, String hospCode) {
        List<StroStockDetailDTO> stroStockDetailDTOList = new ArrayList<>();
        if (!ListUtils.isEmpty(ids)) {
          StroOutDTO stroOutDTO1 = new StroOutDTO();
          stroOutDTO1.setHospCode(hospCode);
          stroOutDTO1.setIds(ids);
          stroOutDTO1.setAuditId(stroOutDTO.getAuditId());
          stroOutDTO1.setAuditName(stroOutDTO.getAuditName());
          stroStockDetailDTOList = this.stroOutDAO.queryStockByIds(stroOutDTO1);
        }
        return stroStockDetailDTOList;
    }

    /**
     * @Method handleStock
     * @Desrciption 进行库存操作（处理出库科室、出库药房）
     * @Param [stroStockDetailDTOS, type, hospCode]
     * @Author liaojunjie
     * @Date 2020/8/30 16:44
     * @Return java.lang.Boolean
     **/
    private Boolean handleStock(List<StroStockDetailDTO> stroStockDetailDTOS, String type, String hospCode) {
        Map map = new HashMap<>();
        if (!ListUtils.isEmpty(stroStockDetailDTOS)) {
            map.put("hospCode", hospCode);
            map.put("type", type);
            map.put("sfBatchNo", true);
            map.put("stroStockDetailDTOList", stroStockDetailDTOS);
            return !ListUtils.isEmpty(stroStockBO.saveStock(map));
        } else {
            return false;
        }
    }

    /**
     * @Method save
     * @Desrciption 新增/编辑
     * @Param [stroOutDTO]
     * @Author liaojunjie
     * @Date 2020/7/30 19:21
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(StroOutDTO stroOutDTO) {
        // 校验接收单位是否有标识
        checkOutStrock(stroOutDTO.getInStockId(),stroOutDTO.getHospCode(),stroOutDTO.getStroOutDetailDTOS());
        //计算零售总金额、购买总金额、拆零总数量
        if (!ListUtils.isEmpty(stroOutDTO.getStroOutDetailDTOS())) {
            List<StroOutDetailDTO> stroOutDetailDTOS = stroOutDTO.getStroOutDetailDTOS();
            for (StroOutDetailDTO s : stroOutDetailDTOS) {
                /*if (s.getSplitRatio() != null && s.getSplitNum() != null) {
                    s.setNum(BigDecimal.valueOf(Math.floor(BigDecimalUtils.divide(s.getSplitNum(), s.getSplitRatio()).doubleValue())));
                }*/
                if (s.getBuyPrice() != null && s.getNum() != null) {
                    s.setBuyPriceAll(BigDecimalUtils.multiply(s.getBuyPrice(), s.getNum()));
                }
                if (s.getSellPrice() != null && s.getNum() != null) {
                    s.setSellPriceAll(BigDecimalUtils.multiply(s.getSellPrice(), s.getNum()));
                }
            }
        }

        //查看传过来的出库单是否具有id      如果有id 就进行修改操作
        if (StringUtils.isNotEmpty(stroOutDTO.getId())) {
            //存储修改数据时新增的出库明细
            List<StroOutDetailDTO> newUpdate = new ArrayList<>();
            //遍历循环出库单中的出库明细
            for (StroOutDetailDTO s : stroOutDTO.getStroOutDetailDTOS()) {
                //设置医院编码
                s.setHospCode(stroOutDTO.getHospCode());
                //设置ID
                s.setId(SnowflakeUtils.getId());
                //设置出入库ID（和出库单的单据号一致）
                s.setOutId(stroOutDTO.getId());
                newUpdate.add(s);
            }
            //删除曾经存的明细单中的数据
            this.stroOutDAO.deleteByStroOut(stroOutDTO);
            if (newUpdate.size() > 0) {
                this.stroOutDAO.insertDetails(newUpdate);
            }
            //最后对明细单进行更新
            this.stroOutDAO.update(stroOutDTO);
            return true;
        } else {
            //设置id
            String id = SnowflakeUtils.getId();
            stroOutDTO.setId(id);
            //设置单据号
            Map orderMap = new HashMap();
            orderMap.put("typeCode", "03");
            orderMap.put("hospCode", stroOutDTO.getHospCode());
            stroOutDTO.setOrderNo(baseOrderRuleService.getOrderNo(orderMap).getData());
            //设置创建时间
            stroOutDTO.setCrteTime(DateUtils.getNow());

            if (!ListUtils.isEmpty(stroOutDTO.getStroOutDetailDTOS())) {
                //需要循环给明细表塞入单据号和id
                for (StroOutDetailDTO s : stroOutDTO.getStroOutDetailDTOS()) {
                    //设置医院编码
                    s.setHospCode(stroOutDTO.getHospCode());
                    //设置id
                    s.setId(SnowflakeUtils.getId());
                    //设置出入库id
                    s.setOutId(id);
                }
                this.stroOutDAO.insertDetails(stroOutDTO.getStroOutDetailDTOS());

            }

            this.stroOutDAO.insert(stroOutDTO);
            return true;
        }
    }
    /**
     * @Meth: checkOutStrock
     * @Description: 保存之前校验，接受单位能否有权限接收所有项目
     * @Param: []
     * @return: void
     * @Author: zhangguorui
     * @Date: 2022/2/17
     */
    public void checkOutStrock(String inStockId,String hospCode,List<StroOutDetailDTO> stroOutinDetailDTOS){

        // 获得接收单位标识
        String loginTypeIdentity = backStroConfirmDAO.queryDeptById(inStockId, hospCode);
        if(StringUtils.isEmpty(loginTypeIdentity)) {
            throw new AppException("接受科室未配置药房药库类别标识");
        }
        List<String> types = new ArrayList<>();

        for (String loginType:loginTypeIdentity.split(",")) {
            if (loginType.equals("2")) {//中成药
                types.add("1");
            } else if (loginType.equals("3")) {//中草药
                types.add("2");
            } else if (loginType.equals("1")) {//西药
                types.add("0");
            } else if(loginType.contains("4")){ // 藏药
                types.add("3");
            } else if (loginType.equals("5") || loginType.equals("6") || loginType.equals("7")) {//材料
                types.add("material");
            } else {
                throw new AppException("没有该药房药库类别标识");
            }
        }
        // 根据item_code进行分组,药品是1 材料是2
        Map<String, List<StroOutDetailDTO>> itemlistMap = stroOutinDetailDTOS.stream().collect(Collectors.groupingBy(StroOutDetailDTO::getItemCode));
        for (Map.Entry<String, List<StroOutDetailDTO>> itemMap : itemlistMap.entrySet()) {
            String itemCode = itemMap.getKey();
            List<StroOutDetailDTO> value = itemMap.getValue();
            if ("2".equals(itemCode) && !types.contains("material")) { // 材料
                throw new AppException("接受单位没有设置材料标识，不能退材料");
            } else {
                List<String> errorType = backStroConfirmDAO.queryDrug(types, value);
                if (!ListUtils.isEmpty(errorType)) {
                    throw new AppException("接受单位没有设置药品标识");
                }
            }
        }


    }
    /**
     * @Method queryStock
     * @Desrciption 查询出页面上选择药品/材料所需要的参数
     * @Param [stroOutDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:08
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryStock(StroOutDTO stroOutDTO) {
//        目前查询的用的别人接口 后续可能还需要使用此方法
//        stroStockBO.queryAll();
//        PageHelper.startPage(stroOutDTO.getPageNo(), stroOutDTO.getPageSize());
//        // 查询所有
//        List<StroStockDTO> stroStockDTOS = stroOutDao.queryDrugStock(stroOutDTO);
//        for (StroStockDTO s : stroStockDTOS){
//            System.out.println(s);
//            //如果总数量不为空
//            if(!BigDecimalUtils.isZero(s.getNum())){
//                s.setBuyPrice(BigDecimalUtils.divide(s.getBuyPriceAll(),s.getNum()));
//                s.setSellPrice(BigDecimalUtils.divide(s.getSellPriceAll(),s.getNum()));
//            }
//            if(!BigDecimalUtils.isZero(s.getSplitNum())){
//                s.setSplitOnePrice(BigDecimalUtils.divide(s.getSplitPrice(),s.getSplitNum()));
//            }
//        }
//        return PageDTO.of(stroStockDTOS);
        return null;
    }

    /**
     * @Method insertWholeOut
     * @Desrciption 整单出库
     * @Param [stroOutDTO]
     * @Author liaojunjie
     * @Date 2020/12/3 9:30
     * @Return java.lang.Boolean
     **/
    @Override
    public StroOutDTO insertWholeOut(StroOutDTO stroOutDTO) {
        StroOutDTO stro = new StroOutDTO();
        if (stroOutDTO.getIgnoreNum()) {
            save(stroOutDTO);
        } else {
            stroOutDTO.setOrderNo(stroOutDTO.getInOrderNo());
            stro = queryWholeOut(stroOutDTO);
            stro.setOrderNo(null);
            if (!stro.getIsAudit()) {
                if (!stro.getIsNumAudit()) {
                    //插入操作
                    save(stro);
                }
            }
        }

        return stro;
    }

    /**
     * @Method queryWholeOut
     * @Desrciption 查询整单出库明细
     * 1.判断此单是否存在已审核的出库单
     * 2.判断此单中的药品（1）是否存在无库存的 （2）库存数量有变化的
     * @Param [stroOutDTO]
     * @Author liaojunjie
     * @Date 2020/12/3 9:30
     * @Return java.util.List<cn.hsa.module.phar.pharapply.entity.StroOut>
     **/
    @Override
    public StroOutDTO queryWholeOut(StroOutDTO stroOutDTO) {
        Integer integer = this.stroOutDAO.queryIsAuditNo(stroOutDTO);
        Integer integer1 = this.stroOutDAO.queryIsAuditNo1(stroOutDTO);
        //判断此单是否存在已审核的出库单
        if (integer > 0 || integer1 > 0) {
          stroOutDTO.setIsAudit(true);
        } else {
          stroOutDTO.setIsAudit(false);
        }
        //判断此入单中的货物(药品、材料)
        StroInDetailDTO stroInDetailDTO = new StroInDetailDTO();
        stroInDetailDTO.setHospCode(stroOutDTO.getHospCode());
        stroInDetailDTO.setOrderNo(stroOutDTO.getInOrderNo());
        List<StroInDetailDTO> stroInDetailDTOS = stroInDAO.queryStroInDetailAllByOrderNo(stroInDetailDTO);
        Map<String, StroInDetailDTO> oldDetails = new HashMap<>();
        for (int i = 0; i < stroInDetailDTOS.size(); i++) {
            String batchNo = stroInDetailDTOS.get(i).getBatchNo();
            String itemId = stroInDetailDTOS.get(i).getItemId();
            String key = batchNo + itemId;
            oldDetails.put(key, stroInDetailDTOS.get(i));
        }

        //设置库位
        for (StroInDetailDTO s : stroInDetailDTOS) {
            s.setBizId(stroOutDTO.getBizId());
        }
        //查询出当前库存中这些货物同库位同批号数量
        List<StroStockDetailDTO> stroStockDetailDTOS = this.stroOutDAO.queryStroOutNoDetail(stroInDetailDTOS);

        Map<String, StroStockDetailDTO> newDetails = new HashMap<>();
        for (int i = 0; i < stroStockDetailDTOS.size(); i++) {
            String batchNo = stroStockDetailDTOS.get(i).getBatchNo();
            String itemId = stroStockDetailDTOS.get(i).getItemId();
            String key = batchNo + itemId;
            newDetails.put(key, stroStockDetailDTOS.get(i));
        }
        // 对数据进行处理，拼接成stroOutDetail
        List<String> itemsNames = new ArrayList<>();
        List<StroOutDetailDTO> stroOutDetailDTOS = new ArrayList<>();
        for (String key : oldDetails.keySet()) {
            if (newDetails.containsKey(key)) {
                StroOutDetailDTO stroOutDetailDTO = new StroOutDetailDTO();
                BigDecimal splitNum;
                // 库存数量
                BigDecimal newSplitNum = newDetails.get(key).getSplitNum();
                // 出库数量
                BigDecimal oldSplitNum = oldDetails.get(key).getSplitNum();
                if (BigDecimalUtils.greaterZero(newSplitNum)) {
                    if (oldSplitNum.compareTo(newSplitNum) > 0) {
                      splitNum = newSplitNum;
                        itemsNames.add(oldDetails.get(key).getItemName());
                    } else {
                        splitNum = oldSplitNum;
                    }
                    stroOutDetailDTO.setItemId(oldDetails.get(key).getItemId());
                    stroOutDetailDTO.setItemCode(oldDetails.get(key).getItemCode());
                    stroOutDetailDTO.setItemName(oldDetails.get(key).getItemName());
                    stroOutDetailDTO.setSpec(oldDetails.get(key).getSpec());
                    stroOutDetailDTO.setSplitNum(splitNum);
                    stroOutDetailDTO.setNum(BigDecimalUtils.divide(splitNum,oldDetails.get(key).getSplitRatio()));
                    stroOutDetailDTO.setUnitCode(oldDetails.get(key).getUnitCode());
                    stroOutDetailDTO.setDosage(oldDetails.get(key).getDosage());
                    stroOutDetailDTO.setDosageUnitCode(oldDetails.get(key).getDosageUnitCode());
                    stroOutDetailDTO.setBuyPrice(oldDetails.get(key).getBuyPrice());
                    stroOutDetailDTO.setSellPrice(oldDetails.get(key).getSellPrice());
                    stroOutDetailDTO.setSplitPrice(oldDetails.get(key).getSplitPrice());
                    stroOutDetailDTO.setSplitRatio(oldDetails.get(key).getSplitRatio());
                    stroOutDetailDTO.setSplitUnitCode(oldDetails.get(key).getSplitUnitCode());
                    stroOutDetailDTO.setBatchNo(oldDetails.get(key).getBatchNo());
                    stroOutDetailDTO.setExpiryDate(oldDetails.get(key).getExpiryDate());
                    stroOutDetailDTO.setSellPriceAll(BigDecimalUtils.multiply(stroOutDetailDTO.getNum(), oldDetails.get(key).getSellPrice()));
                    stroOutDetailDTO.setBuyPriceAll(BigDecimalUtils.multiply(stroOutDetailDTO.getNum(), oldDetails.get(key).getBuyPrice()));
                }
                if (StringUtils.isNotEmpty(stroOutDetailDTO.getItemId())) {
                    stroOutDetailDTOS.add(stroOutDetailDTO);
                }
            } else {
                throw new AppException("库存数据异常！");
            }
        }
        if (!ListUtils.isEmpty(itemsNames)) {
            stroOutDTO.setIsNumAudit(true);
        } else {
            stroOutDTO.setIsNumAudit(false);
        }
        stroOutDTO.setItemNames(itemsNames);
        if(ListUtils.isEmpty(stroOutDetailDTOS)) {
          throw new AppException("库存耗尽，该单据中所有该批号项目库存为0,不可整单出库");
        }
        stroOutDTO.setStroOutDetailDTOS(stroOutDetailDTOS);
        return stroOutDTO;
    }
    /**
     * @Meth: queryStroOutDetail
     * @Description: 根据出库单查询出库明细
     * @Param: [stroOutDTO]
     * @return: java.util.List<cn.hsa.module.stro.stroout.dto.StroOutDetailDTO>
     * @Author: zhangguorui
     * @Date: 2021/12/14
     */
    @Override
    public List<StroOutDetailDTO> queryStroOutDetail(StroOutDetailDTO stroOutDetailDTO) {
        if (StringUtils.isEmpty(stroOutDetailDTO.getOutId()) && ListUtils.isEmpty(stroOutDetailDTO.getOutIds())){
            throw new AppException("请选择需要导出的单据");
        }
        return stroOutDAO.queryStroOutDetail(stroOutDetailDTO);
    }

    public void judgeStock(StroOutDTO stroOutDTO){

        TreeSet itemsNames = new TreeSet<>();
        List<StroOutDetailDTO> stroOutDetailDTOS = stroOutDAO.queryAllDetails(stroOutDTO);

        List<StroOutDetailDTO> mergeOldStroOutDetailDTOS = new ArrayList<>();
        // 先将所有明细根据 项目id（itemId）和 批号（batchNo）进行分组
        Map<String, List<StroOutDetailDTO>> stream = stroOutDetailDTOS.stream().collect(Collectors.groupingBy(a -> a.getItemId() + a.getBatchNo(), Collectors.toList()));
        // 循环map
        for (String key :stream.keySet()){
            List<StroOutDetailDTO> list = MapUtils.get(stream, key);
            // 如果list 大于1 则说明有多个相同项目和批号的记录  进行合计 值放入 index = 0 第一个 再放入mergeOld合并的老集合
            if (list.size() > 1 ){
                list.get(0).setNum(list.stream().map(StroOutDetailDTO::getNum).reduce(BigDecimal::add).get());
                list.get(0).setSplitNum(list.stream().map(StroOutDetailDTO::getSplitNum).reduce(BigDecimal::add).get());
            }
            list.get(0).setBizId(stroOutDTO.getOutStockId());
            mergeOldStroOutDetailDTOS.add(list.get(0));
        }
        // 根据项目id和批号变成map形式
        Map<String, StroOutDetailDTO> mergeOldStroOutDetailDTOSMap = mergeOldStroOutDetailDTOS.stream().collect(Collectors.toMap(a -> a.getItemId() + a.getBatchNo(), Function.identity()));
        //查询出当前库存中这些货物同库位同批号数量
        List<StroStockDetailDTO> stroStockDetailDTOS = this.stroOutDAO.queryDetailStock(mergeOldStroOutDetailDTOS);

        Map<String, BigDecimal> newDetails = new HashMap<>();

        for (int i = 0; i < stroStockDetailDTOS.size(); i++) {
            String batchNo = stroStockDetailDTOS.get(i).getBatchNo();
            String itemId = stroStockDetailDTOS.get(i).getItemId();
            String key =   itemId + batchNo;
            newDetails.put(key, stroStockDetailDTOS.get(i).getSplitNum());
        }
        StringBuilder message = new StringBuilder();
        for (String mergeOldkey:mergeOldStroOutDetailDTOSMap.keySet()){

            StroOutDetailDTO oldItem = MapUtils.get(mergeOldStroOutDetailDTOSMap, mergeOldkey);
            // 如果要出库数据 大于 库存数据
            if (BigDecimalUtils.compareTo(oldItem.getSplitNum(),newDetails.get(mergeOldkey)) > 0 ){
                message.append("批号(");
                message.append(oldItem.getBatchNo());
                message.append(")");
                message.append("【");
                message.append(oldItem.getItemName());
                message.append("】，");
            }
        }
        if (StringUtils.isNotEmpty(message.toString())) {
            throw new AppException(message.toString()+"库存不足");
        }
    }


}
