package cn.hsa.stro.storin.bo.impl;

import ch.qos.logback.core.util.TimeUtil;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroin.bo.StroInBO;
import cn.hsa.module.stro.stroin.dao.StroInDAO;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import cn.hsa.module.stro.stroin.dto.StroInDetailDTO;
import cn.hsa.module.stro.stroin.dto.StroInRecordDTO;
import cn.hsa.module.stro.stroin.entity.StroInDetailDO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.stro.stroout.dao.StroOutDAO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.store.outinStore.bo.impl
 * @Class_name: StroStroInBoImpl
 * @Describe:  药库入库业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/22 10:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class StroInBOImpl extends HsafBO implements StroInBO {

  /**
   * 药库出入库数据库访问接口
   */
  @Resource
  private StroInDAO stroInDao;

  /**
   * 药库出库数据库访问接口
   */
  @Resource
  private StroOutDAO stroOutDAO;

  /**
   * 药库入库BO访问接口
   */
  @Resource
  private StroInBOImpl stroInBO;

  /**
   * 单据生成规则dubbo消费者接口
   */

  @Resource
  private StroStockBO stroStockBO;
  /**
   * 单据生成规则dubbo消费者接口
   */
  @Resource
  private BaseOrderRuleService baseOrderRuleService;

  @Resource
  private BaseDrugService baseDrugService_consumer;

  @Resource
  private BaseMaterialService baseMaterialService_customer;
  @Resource
  private SysParameterService sysParameterService_consumer;

  /**
  * @Menthod getById()
  * @Desrciption 根据主键和医院编码查询入库信息
  *
  * @Param
  * [stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/25 14:04
  * @Return cn.hsa.module.stro.outinstro.dto.StroInDTO
  **/
  @Override
  public StroInDTO getById(StroInDTO stroInDTO) {
    StroInDTO byId = stroInDao.getById(stroInDTO);
    if(byId == null){
      return null;
    }
    StroInDetailDTO stroInDetailDTO = new StroInDetailDTO();
    stroInDetailDTO.setHospCode(byId.getHospCode());
    stroInDetailDTO.setInId(byId.getId());
    stroInDetailDTO.setItemName(stroInDTO.getItemNameKey());
    List<StroInDetailDTO> stroInDetailDTOS = stroInDao.queryStroInDetailAll(stroInDetailDTO);
    if(byId == null){
      throw new AppException("数据错误");
    }
    byId.setStroInDetailDTOS(stroInDetailDTOS);
    return byId;
  }

  /**
  * @Menthod queryStroInPage()
  * @Desrciption  根据条件分页查询入库信息
  *
  * @Param
  * [1. stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/25 13:59
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryStroInPage(StroInDTO stroInDTO) {


    if(StringUtils.isEmpty(stroInDTO.getInCode())) {
      throw new AppException("出入库类型为空");
    }
    // 康德需求之根据药品名称过滤掉入库单
    String itemNameKey = stroInDTO.getItemNameKey();
    if (StringUtils.isNotEmpty(itemNameKey)){
      StroInDetailDTO stroInDetailDTO = new StroInDetailDTO();
      stroInDetailDTO.setHospCode(stroInDTO.getHospCode());
      stroInDetailDTO.setItemName(itemNameKey);
      List<StroInDetailDTO> stroInDetailDTOS = stroInDao.queryStroInDetailAll(stroInDetailDTO);
      if (!ListUtils.isEmpty(stroInDetailDTOS)){
        List<String> inIds = stroInDetailDTOS.stream().map(StroInDetailDO::getInId).collect(Collectors.toList());
        stroInDTO.setIds(inIds);
      }
    }
    PageHelper.startPage(stroInDTO.getPageNo(),stroInDTO.getPageSize());

    List<StroInDTO> stroInDTOS= stroInDao.queryStroInPage(stroInDTO);

    for (int i = 0; i < stroInDTOS.size(); i++) {
      StroOutDTO stroOutDTO = new StroOutDTO();
      stroOutDTO.setHospCode(stroInDTOS.get(i).getHospCode());
      stroOutDTO.setInOrderNo(stroInDTOS.get(i).getOrderNo());
      Integer integer = this.stroOutDAO.queryIsAuditNo(stroOutDTO);
      Integer integer1 = this.stroOutDAO.queryIsAuditNo1(stroOutDTO);
      if(integer > 0 || integer1 > 0) {
        stroInDTOS.get(i).setIsWholeSuppOut("1");
      } else {
        stroInDTOS.get(i).setIsWholeSuppOut("0");
      }
      StroInDetailDTO stroInDetailDTO = new StroInDetailDTO();
      stroInDetailDTO.setHospCode(stroInDTOS.get(i).getHospCode());
      stroInDetailDTO.setInId(stroInDTOS.get(i).getId());
      stroInDetailDTO.setItemName(itemNameKey);
      List<StroInDetailDTO> stroInDetailDTOS = stroInDao.queryStroInDetailAll(stroInDetailDTO);
      stroInDTOS.get(i).setStroInDetailDTOS(stroInDetailDTOS);
    }

    return PageDTO.of(stroInDTOS);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询全部入库信息
  *
  * @Param
  * [1. stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/8/27 16:44
  * @Return java.util.List<cn.hsa.module.stro.stroin.dto.StroInDTO>
  **/
  @Override
  public List<StroInDTO> queryAll(StroInDTO stroInDTO) {
    return stroInDao.queryAll(stroInDTO);
  }

  /**
  * @Menthod save ()
  * @Desrciption 保存增加或修改入库信息
  *
  * @Param
  * [1. stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/25 14:10
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean save(StroInDTO stroInDTO) {
    // 进行有效期校验，退供应商不进行校验
    if(!Constants.CRFS.TGYS.equals(stroInDTO.getInCode())){
      validExpiryDate(stroInDTO.getStroInDetailDTOS());
    }

    StroInDTO stroInDTO1 = checkMoney(stroInDTO);

    List<StroInDetailDTO> stroInDetailDTOS = stroInDTO1.getStroInDetailDTOS();

    //新增建立一个新增的明细List: addStroInDetailDTOS
    List<StroInDetailDTO> addStroInDetailDTOS = new ArrayList<>();

    //根据stroInDTO对象有没有id来判断是增加还是修改  没有id:新增 有:修改
    if(StringUtils.isEmpty(stroInDTO.getId())){
      //是增加就添加医院编码，创建人id,创建人名称，单据号
      stroInDTO.setId(SnowflakeUtils.getId());
      Map map = new HashMap();
      if(Constants.CRFS.ZJRK.equals(stroInDTO.getInCode())){//直接入库
        map.put("typeCode", "02");//生成的单据类型，直接入库单据号生成规则
      } else if(Constants.CRFS.TJTB.equals(stroInDTO.getInCode())) {// 平级出库
        map.put("typeCode", "08");
      } else{//退供应商
        map.put("typeCode", "05");
      }
      map.put("hospCode", stroInDTO.getHospCode());
      String data = baseOrderRuleService.getOrderNo(map).getData();
      stroInDTO.setOrderNo(data);
      //给插入的入库明细数据添加id，医院编码，出入库
      for (int i = 0; i < stroInDetailDTOS.size(); i++) {
        stroInDetailDTOS.get(i).setId(SnowflakeUtils.getId());
        stroInDetailDTOS.get(i).setHospCode(stroInDTO.getHospCode());
        stroInDetailDTOS.get(i).setInId(stroInDTO.getId());
        addStroInDetailDTOS.add(stroInDetailDTOS.get(i));
      }
      stroInDTO.setCrteTime(DateUtils.getNow());
      //添加入库单据信息
      stroInDao.insertStroIn(stroInDTO);

      //批量插入入库明细
      if(!ListUtils.isEmpty(addStroInDetailDTOS)){
        stroInDao.insertStroInDetail(addStroInDetailDTOS);
      }
    } else{
      //新增建立一个修改的的明细List: editStroInDetailDTOS
      List<StroInDetailDTO> editStroInDetailDTOS = new ArrayList<>();
      //修改入库单据信息
      stroInDao.updateStroIn(stroInDTO);
      //如果被移除的数据不为空，则执行删除
      if(!ListUtils.isEmpty(stroInDTO.getIds())){
        stroInDao.deleteStroInDetail(stroInDTO);
      }
      //根据id是否为空进行修改和增加分类
      for (int i = 0; i < stroInDetailDTOS.size(); i++) {
        if(StringUtils.isEmpty(stroInDetailDTOS.get(i).getId())){
          stroInDetailDTOS.get(i).setId(SnowflakeUtils.getId());
          stroInDetailDTOS.get(i).setHospCode(stroInDTO.getHospCode());
          stroInDetailDTOS.get(i).setInId(stroInDTO.getId());
          addStroInDetailDTOS.add(stroInDetailDTOS.get(i));
        }else{
          editStroInDetailDTOS.add(stroInDetailDTOS.get(i));
        }
      }
      //批量增加
      if(!ListUtils.isEmpty(addStroInDetailDTOS)){
        stroInDao.insertStroInDetail(addStroInDetailDTOS);
      }
      //批量修改
      if(!ListUtils.isEmpty(editStroInDetailDTOS)){
        stroInDao.updateStroInDetail(editStroInDetailDTOS);
      }
    }
    return true;
  }
  /**
   * @Meth: validExpiryDate
   * @Description: 校验有效期是否过期
   * @Param: [stroInDetailDTOS]
   * @return: void
   * @Author: zhangguorui
   * @Date: 2021/10/14
   */
  private void validExpiryDate(List<StroInDetailDTO> stroInDetailDTOS) {
    // 查看入库明细是否为空
    if (ListUtils.isEmpty(stroInDetailDTOS)){
      throw new AppException("入库明细不能为空");
    }
    // 校验有效期
    for (StroInDetailDTO stroInDetailDTO : stroInDetailDTOS){
      if ( DateUtils.getNow().compareTo(stroInDetailDTO.getExpiryDate())>0){
          throw new AppException("入库失败："+stroInDetailDTO.getItemName()+"有效期小于或等于当前时间");
      }
    }
  }

  /**
  * @Menthod checkMoney
  * @Desrciption 计算总价格
  *
  * @Param
  * [stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/14 20:44
  * @Return cn.hsa.module.stro.stroin.dto.StroInDTO
  **/
  public StroInDTO checkMoney(StroInDTO stroInDTO){
    List<StroInDetailDTO> stroInDetailDTOS = stroInDTO.getStroInDetailDTOS();
    BigDecimal sellPriceAll = BigDecimal.valueOf(0);
    BigDecimal buyPriceAll = BigDecimal.valueOf(0);
    for(StroInDetailDTO s : stroInDetailDTOS){
      //计算拆零数量
      if(s.getSplitRatio() == null){
        s.setSplitRatio(BigDecimal.valueOf(1));
      }
      s.setNum(BigDecimalUtils.divide(s.getSplitNum(), s.getSplitRatio()));
      //计算单个药品/材料购进总价格
      s.setBuyPriceAll(BigDecimalUtils.multiply(s.getNum(),s.getBuyPrice()));
      //计算单个药品/材料零售总价格
      s.setSellPriceAll(BigDecimalUtils.multiply(s.getNum(),s.getSellPrice()));
      //计算所有药品/材料的零售总价格
      sellPriceAll = BigDecimalUtils.add(sellPriceAll , s.getSellPriceAll());
      //计算所有药品/材料购进总价格
      buyPriceAll = BigDecimalUtils.add(buyPriceAll , s.getBuyPriceAll());
    }
    stroInDTO.setSellPriceAll(sellPriceAll);
    stroInDTO.setBuyPriceAll(buyPriceAll);
    return stroInDTO;
  }


  /**
  * @Menthod updateAuditCode
  * @Desrciption
   *  1.判断是审核入库单据还是作废入库单据 状态1: 审核， 2：作废
   *  2.入库审核时判断库存是否存在
   *  3.如果存在则进行库存的计算，不存在重新生成一条库存数据
   *  4.插入台账信息
   *  5.根据操作科室判断回写材料库还是药库的数据
   *  6.修改该入库订单的审核状态
  * @Param
  * [stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/20 11:32
  * @Return java.lang.Boolean
  **/
  public Boolean updateAuditCode(StroInDTO stroInDTO) {
    if(ListUtils.isEmpty(stroInDTO.getIds())){
      throw new AppException("没有选择数据");
    }
    Integer exitAudit = stroInDao.isExitAudit(stroInDTO);
    if(exitAudit > 0) {
      throw new AppException("只能审核和作废未审核的数据");
    }
    StroInDTO stroInDTO1 = new StroInDTO();
    stroInDTO1.setIds(stroInDTO.getIds());
    stroInDTO1.setHospCode(stroInDTO.getHospCode());
    stroInDTO1.setAuditCode("0");
    stroInDTO1.setInCode(stroInDTO.getInCode());
    List<StroInDTO> stroInDTOS = stroInDao.queryStroInPage(stroInDTO1);
    // 储存直接入库单
    List<String> stroInListIds = new ArrayList<>();
    // 储存退供应商
    List<String> returnSuppIds = new ArrayList<>();
    // 储存平级出库
    List<String> parallelDeliveryIds = new ArrayList<>();
    // 查看系统参数：是否检查验收合格
    Map sysParamMap = new HashMap();
    sysParamMap.put("hospCode", stroInDTO.getHospCode());
    sysParamMap.put("code", "SHOW_ACCEPTANCE"); // 医保限制用药默认医保机构编码
    WrapperResponse<SysParameterDTO> parameterByCode = sysParameterService_consumer.getParameterByCode(sysParamMap);
    String showAcceptance;
    if (null == parameterByCode) {
      throw new AppException("获取系统参数:SHOW_ACCEPTANCE失败");
    } else {
      SysParameterDTO data = parameterByCode.getData() == null ? new SysParameterDTO() : parameterByCode.getData();
      showAcceptance = data.getValue();
    }
    //只有未审核状态才能进行作废,和审核
    if("2".equals(stroInDTO.getAuditCode())){
      stroInDTO.setAuditId(null);
      stroInDTO.setAuditName(null);
    }
    // 当为入库审核的时候
    if("1".equals(stroInDTO.getAuditCode())){
      for(StroInDTO item : stroInDTOS) {
        // 当验收参数开启，并且单据为不入库时，直接过滤掉该单据
        if(Constants.SF.S.equals(showAcceptance) && Constants.SF.F.equals(item.getAcceptanceResult())){
          continue;
        }
        if("2".equals(item.getInCode())){
          // 直接入库
          stroInListIds.add(item.getId());
        }
        if("3".equals(item.getInCode())) {
          //退供应商
          returnSuppIds.add(item.getId());
        }
        if("9".equals(item.getInCode())) {
          // 平级出库
          parallelDeliveryIds.add(item.getId());
        }
      }
      // 如果直接入库id不为空
      if(!ListUtils.isEmpty(stroInListIds)) {
        // 获取同级调拨明细
        List<StroStockDetailDTO> stroInStockDetailDTOS = queryStroInDetailByOrder(stroInDTO,stroInListIds, stroInDTO.getHospCode(),"2");
        // 入库库存
        handleStock(stroInStockDetailDTOS,"2",stroInDTO.getHospCode());
        //回写药品和材料最近购进单价
        updateDrugorMa(stroInDTO, stroInStockDetailDTOS);
      }
      // 如果退供应商id不为空
      if(!ListUtils.isEmpty(returnSuppIds)) {
        // 退供应商明细
        List<StroStockDetailDTO> returnSuppStockS = queryStroInDetailByOrder(stroInDTO,returnSuppIds, stroInDTO.getHospCode(),"3");
        handleStock(returnSuppStockS,"3",stroInDTO.getHospCode());
      }
      // 如果平级出库id不为空
      if(!ListUtils.isEmpty(parallelDeliveryIds)) {
        // 获取同级调拨明细
        List<StroStockDetailDTO> returnSuppStockS = queryStroInDetailByOrder(stroInDTO,parallelDeliveryIds, stroInDTO.getHospCode(),"9");
        handleStock(returnSuppStockS,"9",stroInDTO.getHospCode());
      }
    }
    stroInDao.updateAuditCode(stroInDTO);
    return true;
  }

  /**
  * @Menthod handleStock
  * @Desrciption 入库存
  *
  * @Param
  * [ids, hospCode]
  *
  * @Author jiahong.yang
  * @Date   2021/4/20 9:16
  * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
  **/
  public Boolean handleStock(List<StroStockDetailDTO> stroStockDetailDTOS, String type, String hospCode){
    if(ListUtils.isEmpty(stroStockDetailDTOS)) {
      throw new AppException("数据为空");
    }
    Map map = new HashMap();
    map.put("type", type);
    map.put("hospCode",hospCode);
    map.put("stroStockDetailDTOList",stroStockDetailDTOS);
    if("3".equals(type) || "9".equals(type)) {
      map.put("sfBatchNo", true);
    }
    List<StroInvoicingDTO> stroInvoicingDTOS = stroStockBO.saveStock(map);
    return true;
  }
  /**
  * @Menthod queryStroInDetailByOrder
  * @Desrciption 查询入库明细
  *
  * @Param
  * [ids, hospCode]
  *
  * @Author jiahong.yang
  * @Date   2021/4/20 8:55
  * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
  **/
  public List<StroStockDetailDTO> queryStroInDetailByOrder(StroInDTO stroInDTO1,List<String> ids,String hospCode,String type) {
    StroInDTO stroInDTO = new StroInDTO();
    stroInDTO.setIds(ids);
    stroInDTO.setHospCode(hospCode);
    stroInDTO.setInCode(type);
    stroInDTO.setAuditId(stroInDTO1.getAuditId());
    stroInDTO.setAuditName(stroInDTO1.getAuditName());
    List<StroStockDetailDTO> stroStockDetailDTOS = new ArrayList<>();
    if("2".equals(type)) {
       stroStockDetailDTOS = stroInDao.queryStroInDetailByOrder(stroInDTO);
    } else {
       stroStockDetailDTOS = stroInDao.queryStroInDetailByOrderOut(stroInDTO);
    }
    for (int i = 0; i < stroStockDetailDTOS.size(); i++) {
      //算出购进拆零单价
      if(stroStockDetailDTOS.get(i).getSplitRatio() == null) {
        stroStockDetailDTOS.get(i).setSplitRatio(BigDecimal.valueOf(1));
      }
      stroStockDetailDTOS.get(i).setSplitBuyPrice(BigDecimalUtils.divide(stroStockDetailDTOS.get(i).getBuyPrice(),stroStockDetailDTOS.get(i).getSplitRatio()));
      //算出售出拆零单价
      stroStockDetailDTOS.get(i).setSplitSellPrice(BigDecimalUtils.divide(stroStockDetailDTOS.get(i).getSellPrice(),stroStockDetailDTOS.get(i).getSplitRatio()));
    }
    return stroStockDetailDTOS;
  }

  /**
  * @Menthod updateDrugorMA
  * @Desrciption 入库时回写药品和材料的价格
  *
  * @Param
  * [stroStockDetailDTOS]
  *
  * @Author jiahong.yang
  * @Date   2020/9/2 15:38
  * @Return vode
  **/
  public void updateDrugorMa(StroInDTO stroInDTO,List<StroStockDetailDTO> stroStockDetailDTOS){
    //新建集合用来存储需要修改的药品/材料
    List<BaseDrugDTO> baseDrugDTOS = new ArrayList<>();
    List<BaseMaterialDTO> baseMaterialDTOS = new ArrayList<>();
    Map map = new HashMap();
    map.put("hospCode",stroInDTO.getHospCode());

    for(StroStockDetailDTO s : stroStockDetailDTOS){
      StroInRecordDTO stroInRecordDtO = new StroInRecordDTO();
      // 项目id(药品/材料)
      stroInRecordDtO.setItemId(s.getItemId());
      // 项目类别
      stroInRecordDtO.setItemCode(s.getItemCode());
      stroInRecordDtO.setHospCode(s.getHospCode());
      StroInRecordDTO stroInRecordDTO = stroInDao.queryStroInRecord(stroInRecordDtO);
      if(StringUtils.isEmpty(String.valueOf(stroInRecordDTO.getAvgBuyPrice()))){
        throw  new AppException("入库记录表查询出错");
      }
      if("1".equals(s.getItemCode())){
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setId(s.getItemId());
        baseDrugDTO.setHospCode(s.getHospCode());
        baseDrugDTO.setPrice(null);
        baseDrugDTO.setSplitRatio(null);
        baseDrugDTO.setSplitPrice(null);
        //回写药品最近购进单价
        baseDrugDTO.setLastBuyPrice(s.getBuyPrice());
        //回写药品最近拆零购进单价
        baseDrugDTO.setLastSplitBuyPrice(s.getSplitPrice());
        //回写药品平均购进单价
        baseDrugDTO.setAvgBuyPrice(stroInRecordDTO.getAvgBuyPrice());
        //回写药品平均零售单价
        baseDrugDTO.setAvgSellPrice(BigDecimalUtils.divide(s.getSellPriceAll(),s.getNum()));
        baseDrugDTO.setNdan(s.getNdan());
        baseDrugDTO.setProdCode(s.getProdCode());
        baseDrugDTOS.add(baseDrugDTO);
      } else if("2".equals(s.getItemCode())){
        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setId(s.getItemId());
        baseMaterialDTO.setHospCode(s.getHospCode());
        //回写材料最近购进单价
        baseMaterialDTO.setLastBuyPrice(s.getBuyPrice());
        //回写材料最近拆零购进单价
        baseMaterialDTO.setLastSplitBuyPrice(s.getSplitPrice());
        //回写材料平均购进单价
        baseMaterialDTO.setAvgBuyPrice(stroInRecordDTO.getAvgBuyPrice());
        baseMaterialDTO.setProdCode(s.getProdCode());
        //回写材料平均零售单价
        baseMaterialDTO.setAvgSellPrice(BigDecimalUtils.divide(s.getSellPriceAll(),s.getNum()));
        baseMaterialDTOS.add(baseMaterialDTO);
      }
    }
      if(!ListUtils.isEmpty(baseDrugDTOS)){
        map.put("baseDrugDTOs",baseDrugDTOS);
        baseDrugService_consumer.updateAllById(map);
      }
      if(!ListUtils.isEmpty(baseMaterialDTOS)){
        map.put("baseMaterialDTOList",baseMaterialDTOS);
        baseMaterialService_customer.updateList(map);
      }
  }

  @Override
  public List<StroInDetailDTO> queryStroInDetailAll(StroInDetailDTO stroStroInDetailDTO) {
    return stroInDao.queryStroInDetailAll(stroStroInDetailDTO);
  }

  /**
  * @Menthod queryDrugPage()
  * @Desrciption  获取全部药品或者材料填充下拉表单
  *
  * @Param
  * [baseDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/29 17:47
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryDrugorMaterialPage(Map map) {
    if(MapUtils.get(map,"pageNo") == null || MapUtils.get(map,"pageSize") == null) {
      PageHelper.startPage(1, 10);
    } else {
      PageHelper.startPage(Integer.parseInt(MapUtils.get(map,"pageNo")), Integer.parseInt(MapUtils.get(map,"pageSize")));
    }
    String loginDeptType = MapUtils.get(map,"loginDeptType");
    String loginTypeIdentity = MapUtils.get(map, "loginTypeIdentity");
    String hospCode = MapUtils.get(map,"hospCode");
    String keyword = MapUtils.get(map,"keyword");

    Set<String> set = new HashSet();
     for(String  type:loginTypeIdentity.split(",")) {
       if("5".equals(type) || "6".equals(type) || "7".equals(type)){
         set.add("1");
       } else if("1".equals(type) || "2".equals(type) || "3".equals(type) || "4".equals(type)){
         set.add("2");
       } else{
         throw new AppException("当前科室未配置药房药库类别标识");
       }
     }
    //如果科室性质为 13即为材料  3即为药品
    if(set.size() == 1 && set.contains("1")){
      List<BaseDrugDTO> baseMaterialDTOS = queryMaterial(loginTypeIdentity, hospCode, keyword);
      return PageDTO.of(baseMaterialDTOS);
    } else if(set.size() == 1 && set.contains("2")){
      List<BaseDrugDTO> baseDrugDTOS = queryDrug(loginTypeIdentity, hospCode, keyword);
      return PageDTO.of(baseDrugDTOS);
    } else if (set.size() == 2){
      List<String> strings = new ArrayList<>();
      for (String loginType:loginTypeIdentity.split(",")) {
        if(loginType.contains("1")) {
          strings.add("0");
        } else if(loginType.contains("2")){
          strings.add("1");
        } else if(loginType.contains("3")){
          strings.add("2");
        } else if(loginType.contains("4")){
          strings.add("3");
        }
      }
      BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
      baseDrugDTO.setIsValid("1");
      baseDrugDTO.setHospCode(hospCode);
      baseDrugDTO.setKeyword(keyword);
      baseDrugDTO.setBigTypeCodeLsit(strings);
      List<BaseDrugDTO> baseDrugDTOS = stroInDao.queryMandB(baseDrugDTO);
      return PageDTO.of(baseDrugDTOS);
    }{
      throw new AppException("当前科室未配置药房药库类别标识");
    }
  }

  /**
  * @Menthod queryMaterial
  * @Desrciption 查询材料
  *
  * @Param
  * [loginTypeIdentity, hospCode, keyword]
  *
  * @Author jiahong.yang
  * @Date   2020/12/4 9:31
  * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
  **/
  public List<BaseDrugDTO> queryMaterial(String loginTypeIdentity,String hospCode,String keyword){
    BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
    baseMaterialDTO.setIsValid("1");
    baseMaterialDTO.setHospCode(hospCode);
    baseMaterialDTO.setKeyword(keyword);
    List<BaseDrugDTO> baseMaterialDTOList = stroInDao.queryMaterialPage(baseMaterialDTO);
    return baseMaterialDTOList;
  }

  /**
  * @Menthod queryDrug
  * @Desrciption 查询药品
  *
  * @Param
  * [loginTypeIdentity, hospCode, keyword]
  *
  * @Author jiahong.yang
  * @Date   2020/12/4 9:31
  * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
  **/
  public List<BaseDrugDTO> queryDrug(String loginTypeIdentity,String hospCode,String keyword){
    List<String> strings = new ArrayList<>();
    for (String loginType:loginTypeIdentity.split(",")) {
      if(loginType.contains("1")) {
        strings.add("0");
      } else if(loginType.contains("2")){
        strings.add("1");
      } else if(loginType.contains("3")){
        strings.add("2");
      } else if(loginType.contains("4")){
        strings.add("3");
      }
    }
    BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
    baseDrugDTO.setIsValid("1");
    baseDrugDTO.setHospCode(hospCode);
    baseDrugDTO.setKeyword(keyword);
    baseDrugDTO.setBigTypeCodeLsit(strings);
    List<BaseDrugDTO> BaseDrugDTOList = this.stroInDao.queryDrugPage(baseDrugDTO);
    return BaseDrugDTOList;
  }
  /**
  * @Menthod queryMaterialPage
  * @Desrciption 获取全部材料填充下拉表单
  *
  * @Param
  * [baseMaterialDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/7/29 17:47
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryMaterialPage(BaseMaterialDTO baseMaterialDTO) {
    // 设置分页信息
    PageHelper.startPage(baseMaterialDTO.getPageNo(),baseMaterialDTO.getPageSize());
    List<BaseDrugDTO> baseMaterialDTOList = stroInDao.queryMaterialPage(baseMaterialDTO);
    return  PageDTO.of(baseMaterialDTOList);
  }

  /**
   * @Method insertBySameLevel
   * @Desrciption 出库同级调拨的时候往入库插入记录
   * @Param
   * [stroOutDTO]
   * @Author liaojunjie
   * @Date   2020/11/2 14:33
   * @Return java.lang.Boolean
   **/
  @Override
  public Boolean insertBySameLevel(List<StroOutDTO> stroOutDTOS) {
    if(ListUtils.isEmpty(stroOutDTOS)){

    }else{
      //是增加就添加医院编码，创建人id,创建人名称，单据号
      for (int i = 0; i <stroOutDTOS.size() ; i++) {
        stroOutDTOS.get(i).setId(SnowflakeUtils.getId());
        Map map = new HashMap();
        map.put("typeCode", "02");
        map.put("hospCode", stroOutDTOS.get(i).getHospCode());
        String data = baseOrderRuleService.getOrderNo(map).getData();
        stroOutDTOS.get(i).setOrderNo(data);
        List<StroOutDetailDTO> stroOutDetailDTOS = stroOutDTOS.get(i).getStroOutDetailDTOS();
        List<StroOutDetailDTO> addStroDetail = new ArrayList<>();
        //给插入的入库明细数据添加id，医院编码，出入库
        for (int j = 0; j < stroOutDetailDTOS.size(); j++) {
          stroOutDetailDTOS.get(j).setId(SnowflakeUtils.getId());
          stroOutDetailDTOS.get(j).setHospCode(stroOutDTOS.get(i).getHospCode());
          stroOutDetailDTOS.get(j).setInId(stroOutDTOS.get(i).getId());
          addStroDetail.add(stroOutDetailDTOS.get(j));
        }

        stroOutDTOS.get(i).setCrteTime(DateUtils.getNow());
        //添加入库单据信息
        stroInDao.insertStro(stroOutDTOS.get(i));

        //批量插入入库明细
        if(!ListUtils.isEmpty(addStroDetail)){
          stroInDao.insertStroDetail(stroOutDetailDTOS);
        }
      }
    }
    return true;
  }

  /**
  * @Menthod queryWholeOut
  * @Desrciption 供应商整单出库
  *
  * @Param
  * [stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/8 11:15
  * @Return cn.hsa.module.stro.stroin.dto.StroInDTO
  **/
  @Override
  public StroInDTO queryWholeSuppOut(StroInDTO stroInDTO) {
    StroOutDTO stroOutDTO = new StroOutDTO();
    stroOutDTO.setHospCode(stroInDTO.getHospCode());
    stroOutDTO.setInOrderNo(stroInDTO.getInOrderNo());
    Integer integer = this.stroOutDAO.queryIsAuditNo(stroOutDTO);
    Integer integer1 = this.stroOutDAO.queryIsAuditNo1(stroOutDTO);
    //判断此单是否存在已审核的出库单
    if (integer > 0 || integer1 > 0) {
      stroInDTO.setIsAudit(true);
    } else {
      stroInDTO.setIsAudit(false);
    }
    //判断此入单中的货物(药品、材料)
    StroInDetailDTO stroInDetailDTO = new StroInDetailDTO();
    stroInDetailDTO.setHospCode(stroInDTO.getHospCode());
    stroInDetailDTO.setOrderNo(stroInDTO.getInOrderNo());
    List<StroInDetailDTO> stroInDetailDTOS = stroInDao.queryStroInDetailAllByOrderNo(stroInDetailDTO);
    Map<String, StroInDetailDTO> oldDetails = new HashMap<>();
    for (int i = 0; i < stroInDetailDTOS.size(); i++) {
      String batchNo = stroInDetailDTOS.get(i).getBatchNo();
      String itemId = stroInDetailDTOS.get(i).getItemId();
      String key = batchNo + itemId;
      oldDetails.put(key, stroInDetailDTOS.get(i));
    }

    //设置库位
    for (StroInDetailDTO s : stroInDetailDTOS) {
      s.setBizId(stroInDTO.getBizId());
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
    List<StroInDetailDTO> stroInDetailDTOS1 = new ArrayList<>();
    for (String key : oldDetails.keySet()) {

      if (newDetails.containsKey(key)) {
        StroInDetailDTO stroInDetailDTO1 = new StroInDetailDTO();
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
          stroInDetailDTO1.setItemId(oldDetails.get(key).getItemId());
          stroInDetailDTO1.setItemCode(oldDetails.get(key).getItemCode());
          stroInDetailDTO1.setItemName(oldDetails.get(key).getItemName());
          stroInDetailDTO1.setSpec(oldDetails.get(key).getSpec());
          stroInDetailDTO1.setSplitNum(newSplitNum);
          stroInDetailDTO1.setNum(BigDecimalUtils.divide(splitNum,oldDetails.get(key).getSplitRatio()));
          stroInDetailDTO1.setUnitCode(oldDetails.get(key).getUnitCode());
          stroInDetailDTO1.setDosage(oldDetails.get(key).getDosage());
          stroInDetailDTO1.setDosageUnitCode(oldDetails.get(key).getDosageUnitCode());
          stroInDetailDTO1.setBuyPrice(oldDetails.get(key).getBuyPrice());
          stroInDetailDTO1.setSellPrice(oldDetails.get(key).getSellPrice());
          stroInDetailDTO1.setSplitPrice(oldDetails.get(key).getSplitPrice());
          stroInDetailDTO1.setSplitRatio(oldDetails.get(key).getSplitRatio());
          stroInDetailDTO1.setSplitUnitCode(oldDetails.get(key).getSplitUnitCode());
          stroInDetailDTO1.setBatchNo(oldDetails.get(key).getBatchNo());
          stroInDetailDTO1.setExpiryDate(oldDetails.get(key).getExpiryDate());
          stroInDetailDTO1.setSellPriceAll(BigDecimalUtils.multiply(stroInDetailDTO1.getNum(), oldDetails.get(key).getSellPrice()));
          stroInDetailDTO1.setBuyPriceAll(BigDecimalUtils.multiply(stroInDetailDTO1.getNum(), oldDetails.get(key).getBuyPrice()));
        }
        if (StringUtils.isNotEmpty(stroInDetailDTO1.getItemId())) {
          stroInDetailDTOS1.add(stroInDetailDTO1);
        }
      } else {
        throw new AppException("库存数据异常！");
      }
    }
    if (!ListUtils.isEmpty(itemsNames)) {
      stroInDTO.setIsNumAudit(true);
    } else {
      stroInDTO.setIsNumAudit(false);
    }
    stroInDTO.setItemNames(itemsNames);
    if(ListUtils.isEmpty(stroInDetailDTOS1)) {
      throw new AppException("库存耗尽，该单据中所有该批号项目库存为0,不可整单出库");
    }
    stroInDTO.setStroInDetailDTOS(stroInDetailDTOS1);
    return stroInDTO;
  }

  /**
  * @Menthod insertWholeSuppOut
  * @Desrciption 新增整单出库供应商
  *
  * @Param
  * [stroInDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/8 14:23
  * @Return cn.hsa.module.stro.stroin.dto.StroInDTO
  **/
  @Override
  public StroInDTO insertWholeSuppOut(StroInDTO stroInDTO) {
    StroInDTO stro = new StroInDTO();
    if (stroInDTO.getIgnoreNum()) {
      save(stroInDTO);
    } else {
      stroInDTO.setOrderNo(stroInDTO.getInOrderNo());
      stro = queryWholeSuppOut(stroInDTO);
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
  * @Menthod queryStroinDetail
  * @Desrciption 查询明细数据
  *
  * @Param
  * [stroInDetailDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/3/29 10:03
  * @Return java.util.List<cn.hsa.module.stro.stroin.dto.StroInDetailDTO>
  **/
  @Override
  public List<StroInDetailDTO> queryStroinDetail(StroInDetailDTO stroInDetailDTO) {
    return stroInDao.queryStroInDetailAll(stroInDetailDTO);
  }
  /**
   * @Meth: queryStroinDetailForExprot
   * @Description: 查询明细为了批量导出
   * @Param: [stroInDetailDTO]
   * @return: java.util.List<cn.hsa.module.stro.stroin.dto.StroInDetailDTO>
   * @Author: zhangguorui
   * @Date: 2021/9/17
   */
  @Override
  public List<StroInDetailDTO> queryStroinDetailForExprot(StroInDetailDTO stroInDetailDTO) {
    Optional.ofNullable(stroInDetailDTO.getHospCode()).orElseThrow(()-> new AppException("医院编码不能为空"));
    if (ListUtils.isEmpty(stroInDetailDTO.getInIds())){
      throw new AppException("请先勾选入库单据");
    }
    // 通过入库id 查询供应商名称
    List<StroInDTO> stroInDTOS = stroInDao.queryStroInPageForExprot(stroInDetailDTO);
    // 转化成map，格式为 <id,name> 供应商
    Map<String, String> supplierMap = stroInDTOS.stream().collect(Collectors.toMap(StroInDTO::getId, StroInDTO::getName));
    if (MapUtils.isEmpty(supplierMap)){
      throw new AppException("所选的入库单据查询出来的供应商为空");
    }
    // 查询明细
    List<StroInDetailDTO> resultList = stroInDao.queryStroinDetailForExprot(stroInDetailDTO);
    // 遍历设置供应商名称
    resultList.stream().forEach(item ->{
      item.setSupplierName(supplierMap.get(item.getInId()));
    });
    return resultList;
  }

  /**
   * @param stroInDTO
   * @Menthod updateStroInFk()
   * @Desrciption 修改财务付款状态
   * @Param [baseDrugDTO]
   * @Author pengbo
   * @Date 2022/04/19 17:36
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   */
  @Override
  public Boolean updateStroInFk(StroInDTO stroInDTO) {

    // 0未付款，1已付款
    if (stroInDTO != null && "1".equals(stroInDTO.getFkStatusCode())){
      stroInDTO.setFkdid("");
      stroInDTO.setFkTime(new Date());
    }else{
      stroInDTO.setFkrId(null);
      stroInDTO.setFkdid(null);
      stroInDTO.setFkRemark(stroInDTO.getFkrName()+"已取消付款");
      stroInDTO.setFkrName(null);
    }

    return stroInDao.updateStroInFk(stroInDTO)>0;
  }


}
