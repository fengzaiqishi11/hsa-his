package cn.hsa.stro.purchase.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bspl.dto.BaseSupplierDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.stro.purchase.bo.StroPurchaseBO;
import cn.hsa.module.stro.purchase.dao.StroPurchaseDAO;
import cn.hsa.module.stro.purchase.dao.StroPurchaseDetailDAO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO;
import cn.hsa.module.stro.purchase.entity.StroPurchaseDO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.stro.purchase.bo.impl
 * @Class_name: StroPurchaseBOImpl
 * @Describe: 药品采购 BoImpl 业务实现层
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/23 15:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class StroPurchaseBOImpl extends HsafBO implements StroPurchaseBO {

    @Resource
    private StroPurchaseDAO stroPurchaseDAO;//采购计划 DAO

    @Resource
    private StroPurchaseDetailDAO stroPurchaseDetailDAO;//采购计划明细 DAO

    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * @Menthod queryStroPurchasePage
     * @Desrciption  分页查询采购计划信息
     * @param stroPurchaseDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return PageDTO 采购信息
     */
    @Override
    public PageDTO queryStroPurchasePage(StroPurchaseDTO stroPurchaseDTO) {
        PageHelper.startPage(stroPurchaseDTO.getPageNo(),stroPurchaseDTO.getPageSize());
        List<StroPurchaseDTO> stroPurchaseDTOList = stroPurchaseDAO.queryStroPurchaseList(stroPurchaseDTO);
        for (int i = 0; i < stroPurchaseDTOList.size(); i++) {
          StroPurchaseDetailDTO stroPurchaseDetailDTO = new StroPurchaseDetailDTO();
          stroPurchaseDetailDTO.setHospCode(stroPurchaseDTO.getHospCode());
          stroPurchaseDetailDTO.setPurchaseId(stroPurchaseDTOList.get(i).getId());
          List<StroPurchaseDetailDTO> stroPurchaseDetailDTOS = stroPurchaseDetailDAO.queryStroPurchaseDetailList(stroPurchaseDetailDTO);
          stroPurchaseDTOList.get(i).setStroPurchaseDetails(stroPurchaseDetailDTOS);
        }
        return PageDTO.of(stroPurchaseDTOList);
    }

    /**
     * @Menthod queryStroPurchaseDetailPage
     * @Desrciption  分页查询采购计划明细信息
     * @param stroPurchaseDetailDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return PageDTO 采购明细信息
     */
    @Override
    public List<StroPurchaseDetailDTO> queryStroPurchaseDetailPage(StroPurchaseDetailDTO stroPurchaseDetailDTO) {
        List<StroPurchaseDetailDTO> stroPurchaseDetailDTOList = stroPurchaseDetailDAO.queryStroPurchaseDetailList(stroPurchaseDetailDTO);
        return stroPurchaseDetailDTOList;
    }

    /**
     * @Menthod addStroPurchaseAndDetail
     * @Desrciption  采购明细信息
     * @param stroPurchase 采购信息参数
     * @param stroPurchaseDetails 采购明细信息参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 成功/失败
     */
    @Override
    public Boolean addStroPurchaseAndDetail(StroPurchaseDO stroPurchase, List<StroPurchaseDetailDTO> stroPurchaseDetails) {
        //新增采购计划
        String uuid = SnowflakeUtils.getId();
        BigDecimal buyPriceAll = new BigDecimal(0);//进购总金额
        BigDecimal sellPriceAll = new BigDecimal(0);//零售总金额
        if (!stroPurchaseDetails.isEmpty() && stroPurchaseDetails.size() > 0){
            //批量新增,赋值药库id
            List<StroPurchaseDetailDTO> stroPurchaseDetailParam = new ArrayList<StroPurchaseDetailDTO>();
            for (int i = 0;i < stroPurchaseDetails.size();i++){
                if (StringUtils.isNotEmpty(stroPurchaseDetails.get(i).getItemId())){
                    stroPurchaseDetails.get(i).setId(SnowflakeUtils.getId());//id
                    stroPurchaseDetails.get(i).setPurchaseId(uuid);//采购id
                    stroPurchaseDetails.get(i).setHospCode(stroPurchase.getHospCode());//医院编码
                  StroPurchaseDetailDTO stroPurchaseDetail = stroPurchaseDetails.get(i);
                    //计算进购总金额 进购单价 * 数量
                    buyPriceAll = BigDecimalUtils.add(buyPriceAll,BigDecimalUtils.multiply(stroPurchaseDetail.getBuyPrice(),BigDecimal.valueOf(stroPurchaseDetail.getNum())));
                    //buyPriceAll = buyPriceAll.add(stroPurchaseDetail.getBuyPrice().multiply(BigDecimal.valueOf(stroPurchaseDetail.getNum())));
                    //计算零售总金额 零售单价 * 数量
                    sellPriceAll = BigDecimalUtils.add(sellPriceAll,BigDecimalUtils.multiply(stroPurchaseDetail.getSellPrice(),BigDecimal.valueOf(stroPurchaseDetail.getNum())));
                    //sellPriceAll = sellPriceAll.add(stroPurchaseDetail.getSellPrice().multiply(BigDecimal.valueOf(stroPurchaseDetail.getNum())));
                    stroPurchaseDetailParam.add(stroPurchaseDetail);
                }
            }
            if (!stroPurchaseDetailParam.isEmpty()){
                stroPurchaseDetailDAO.addBatchDetail(stroPurchaseDetailParam);
            }
        }
        Map<String,String> param = new HashMap<String,String>();
        param.put("hospCode",stroPurchase.getHospCode());
        param.put("typeCode",Constants.ORDERRULE.CG);
        WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(param);
        stroPurchase.setOrderNo(orderNo.getData());//单据号
        stroPurchase.setAuditCode(Constants.SHZT.WSH);//审核状态未审核
        stroPurchase.setId(uuid);
        stroPurchase.setBuyPriceAll(buyPriceAll);//进购总金额
        stroPurchase.setSellPriceAll(sellPriceAll);//零售总金额
        stroPurchaseDAO.insert(stroPurchase);
        return true;
    }

    /**
     * @Menthod editStroPurchaseAndDetail
     * @Desrciption  编辑采购信息、采购明细信息
     * @param stroPurchase 采购信息参数
     * @param stroPurchaseDetails 采购明细参数
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 成功/失败
     */
    @Override
    public Boolean editStroPurchaseAndDetail(StroPurchaseDO stroPurchase, List<StroPurchaseDetailDTO> stroPurchaseDetails) {
        BigDecimal buyPriceAll = new BigDecimal(0);//进购总金额
        BigDecimal sellPriceAll = new BigDecimal(0);//零售总金额
        if (!stroPurchaseDetails.isEmpty() && stroPurchaseDetails.size()>0){
            String[] ids = {stroPurchase.getId()};
            stroPurchaseDetailDAO.delDetailByPurchaseId(ids);//删除采购明细信息
            //批量新增,赋值药库id
            List<StroPurchaseDetailDTO> stroPurchaseDetailParam = new ArrayList<StroPurchaseDetailDTO>();
            for (int i = 0;i < stroPurchaseDetails.size();i++){
                if (StringUtils.isNotEmpty(stroPurchaseDetails.get(i).getItemId())){
                    stroPurchaseDetails.get(i).setId(SnowflakeUtils.getId());//id
                    stroPurchaseDetails.get(i).setPurchaseId(stroPurchase.getId());//采购id
                    stroPurchaseDetails.get(i).setHospCode(stroPurchase.getHospCode());//医院编码
                    StroPurchaseDetailDTO stroPurchaseDetail = stroPurchaseDetails.get(i);
                    //计算进购总金额 进购单价 * 数量
                    buyPriceAll = buyPriceAll.add(stroPurchaseDetail.getBuyPrice().multiply(BigDecimal.valueOf(stroPurchaseDetail.getNum())));
                    //计算零售总金额 零售单价 * 数量
                    sellPriceAll = sellPriceAll.add(stroPurchaseDetail.getSellPrice().multiply(BigDecimal.valueOf(stroPurchaseDetail.getNum())));
                    stroPurchaseDetailParam.add(stroPurchaseDetail);
                }
            }
            if (!stroPurchaseDetailParam.isEmpty()){
                //批量创建采购明细信息
                stroPurchaseDetailDAO.addBatchDetail(stroPurchaseDetailParam);
            }
        }
        stroPurchase.setBuyPriceAll(buyPriceAll);//进购总金额
        stroPurchase.setSellPriceAll(sellPriceAll);//零售总金额
        stroPurchaseDAO.updateByPrimaryKeySelective((StroPurchaseDTO) stroPurchase);//编辑采购计划
        return true;
    }

    /**
     * @Menthod delStroPurchaseAndDetail
     * @Desrciption  删除采购信息、明细信息
     * @param ids 采购id
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 成功/失败
     */
    @Override
    public Boolean delStroPurchaseAndDetail(String ids,String hospCode) {
        String[] idArr = ids.split(",");
        List<StroPurchaseDTO> stroPurchaseDTOList = stroPurchaseDAO.selectByPrimaryKeys(idArr,hospCode);
        if (!stroPurchaseDTOList.isEmpty()){
            List<String> invoke = new ArrayList<String>(); //启用的数据
            List<String> invalid = new ArrayList<String>(); //作废数据
            for (StroPurchaseDTO stroPurchaseDTO : stroPurchaseDTOList){
                 if (Constants.SHZT.ZF.equals(stroPurchaseDTO.getAuditCode())){
                     //启用
                     invoke.add(stroPurchaseDTO.getId());
                 }else {
                     //作废
                     invalid.add(stroPurchaseDTO.getId());
                 }
            }
            StroPurchaseDTO stroPurchaseDTO = new StroPurchaseDTO();
            if (!invoke.isEmpty()){
                //启用
                stroPurchaseDTO.setAuditCode(Constants.SHZT.WSH);//审核状态 = 未审核
                String[] idsParam = new String[invoke.size()];
                invoke.toArray(idsParam);
                stroPurchaseDTO.setIds(idsParam);
            }
            if (!invalid.isEmpty()){
                //作废
                stroPurchaseDTO.setAuditCode(Constants.SHZT.ZF);//审核状态 = 作废
                String[] idsParam = new String[invalid.size()];
                invalid.toArray(idsParam);
                stroPurchaseDTO.setIds(idsParam);
            }
            //批量删除采购计划
            stroPurchaseDAO.updateByPrimaryKeySelective(stroPurchaseDTO);
            //批量删除采购计划明细
            //stroPurchaseDetailDAO.delDetailByPurchaseId(idArr);
        }
        return true;
    }

    /**
     * @Menthod queryBaseDrugByPage
     * @Desrciption  查询药品表信息（分页）
     * @param baseDrugDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 成功/失败
     */
    @Override
    public PageDTO queryBaseDrugByPage(BaseDrugDTO baseDrugDTO) {
        PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
        List<BaseDrugDTO> baseDrugDTOList = stroPurchaseDAO.queryBaseDrugByPage(baseDrugDTO);
        return PageDTO.of(baseDrugDTOList);
    }

    /**
     * @Menthod queryMaterialPage
     * @Desrciption  查询材料信息（分页）
     * @param baseMaterialDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    @Override
    public PageDTO queryMaterialPage(BaseMaterialDTO baseMaterialDTO) {
        PageHelper.startPage(baseMaterialDTO.getPageNo(),baseMaterialDTO.getPageSize());
        List<BaseMaterialDTO> baseMaterialDTOList = stroPurchaseDAO.queryMaterialPage(baseMaterialDTO);
        return PageDTO.of(baseMaterialDTOList);
    }

    /**
     * @Menthod queryDrugMaterialPage
     * @Desrciption  查询材料、药品信息（分页）
     * @param baseDrugDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    @Override
    public PageDTO queryDrugMaterialPage(BaseDrugDTO baseDrugDTO) {
      PageHelper.startPage(baseDrugDTO.getPageNo(), baseDrugDTO.getPageSize());

      Set<String> set = new HashSet();
      for (String type : baseDrugDTO.getLoginTypeIdentity().split(",")) {
        if ("5".equals(type) || "6".equals(type) || "7".equals(type)) {
          set.add("1");
        } else if ("1".equals(type) || "2".equals(type) || "3".equals(type) || "4".equals(type)) {
          set.add("2");
        } else {
          throw new AppException("当前科室未配置药房药库类别标识");
        }
      }
      //值包含材料
      if (set.size() == 1 && set.contains("1")) {
        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setIsValid("1");
        baseMaterialDTO.setHospCode(baseDrugDTO.getHospCode());
        baseMaterialDTO.setKeyword(baseDrugDTO.getKeyword());
        baseMaterialDTO.setBizId(baseDrugDTO.getBizId());
        List<BaseMaterialDTO> baseMaterialDTOS = stroPurchaseDAO.queryMaterialPage(baseMaterialDTO);
        return PageDTO.of(baseMaterialDTOS);
        //只包含药品
      } else {
        List<BaseDrugDTO> baseDrugDTOS = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        //材料和药品都包含
        for (String loginType:baseDrugDTO.getLoginTypeIdentity().split(",")) {
          if(loginType.contains("1")) {
            strings.add("0");
          } else if(loginType.contains("2")){
            strings.add("1");
          } else if(loginType.contains("3")){
            strings.add("2");
          } else if(loginType.contains("4")){ // 藏药
              strings.add("3");
          }
        }
        baseDrugDTO.setBigTypeCodeLsit(strings);
        if (set.size() == 1 && set.contains("2")){
          //只查询药品
          baseDrugDTOS = stroPurchaseDAO.queryBaseDrugByPage(baseDrugDTO);
        } else {
          //药品材料都查询
          baseDrugDTOS = stroPurchaseDAO.queryDrugMaterialPage(baseDrugDTO);
        }
        return PageDTO.of(baseDrugDTOS);
      }
    }

    /**
     * @Menthod queryBaseSupplierAll
     * @Desrciption  查询供应商信息
     * @param baseSupplierDTO 查询条件
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return 结果集
     */
    @Override
    public List<BaseSupplierDTO> queryBaseSupplierAll(BaseSupplierDTO baseSupplierDTO) {
        return stroPurchaseDAO.queryBaseSupplierAll(baseSupplierDTO);
    }

    /**
     * @Menthod editStroPurchaseAudit
     * @Desrciption 采购计划审核
     * @param stroPurchaseDTO stroPurchaseDTO
     * @Author Ou·Mr
     * @Date 2020/10/16 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse editStroPurchaseAudit(StroPurchaseDTO stroPurchaseDTO) {
        List<StroPurchaseDTO> stroPurchaseDTOList = stroPurchaseDAO.selectByPrimaryKeys(stroPurchaseDTO.getIds(),stroPurchaseDTO.getHospCode());
        if (!stroPurchaseDTOList.isEmpty()){
            List<String> ids = new ArrayList<String>();
            stroPurchaseDTOList.stream().forEach(stroPurchaseDTO1 -> {
                if (Constants.SHZT.WSH.equals(stroPurchaseDTO1.getAuditCode())){
                    ids.add(stroPurchaseDTO1.getId());
                }
            });
            if (!ids.isEmpty()){
                String[] idsParam = new String[ids.size()];
                ids.toArray(idsParam);
                stroPurchaseDTO.setIds(idsParam);//ids
                stroPurchaseDTO.setAuditCode(Constants.SHZT.SHWC);//审核状态 = 审核完成
                stroPurchaseDTO.setAuditTime(new Date());//审核时间
                stroPurchaseDAO.updateByPrimaryKeySelective(stroPurchaseDTO);
                return WrapperResponse.success("操作成功。",null);
            }
            return WrapperResponse.fail("没有需要审核信息。",null);
        }else{
            return WrapperResponse.fail("未找到采购计划信息。",null);
        }
    }

    /**
    * @Menthod SupplementStroStock
    * @Desrciption 根据库存消耗量，自动生成采购计划 按下限或按上限
    *
    * @Param
    * [map]  1.methodFlag   1:按时间内消耗量生成  2：按上下限生成
    *
    * @Author jiahong.yang
    * @Date   2020/11/26 16:35
    * @Return java.lang.Boolean
    **/
    public Boolean addSupplementStroStock(Map map){
      String methodFlag = MapUtils.get(map, "methodFlag");
      List<StroPurchaseDetailDTO> stroPurchaseDetailDTOS = new ArrayList<>();
      String flag = MapUtils.get(map, "flag");
      if("1".equals(methodFlag)){
        stroPurchaseDetailDTOS = stroPurchaseDAO.addSupplementStroByDate(map);
        for (int i = 0; i < stroPurchaseDetailDTOS.size(); i++) {
            if(stroPurchaseDetailDTOS.get(i).getNum() >= 0){
              stroPurchaseDetailDTOS.remove(i);
            } else {
              stroPurchaseDetailDTOS.get(i).setNum(Math.abs(stroPurchaseDetailDTOS.get(i).getNum()));
            }
        }
      } else {
        if("1".equals(flag)){
          stroPurchaseDetailDTOS = stroPurchaseDAO.queryNeedSupplement(map);
        } else {
          stroPurchaseDetailDTOS = stroPurchaseDAO.queryNeedSupplementUp(map);
        }
      }
      Map<String, List<StroPurchaseDetailDTO>> collect = stroPurchaseDetailDTOS.stream().collect(Collectors.groupingBy(a->a.getSupplierId()+a.getBizId()));
      if(MapUtils.isEmpty(collect)){
        return true;
      }
      for (String key : collect.keySet()) {
        BigDecimal sellPriceAll = BigDecimal.valueOf(0);
        BigDecimal buyPriceAll = BigDecimal.valueOf(0);
        List<StroPurchaseDetailDTO> stroPhDetailDtosBySup = collect.get(key);
        StroPurchaseDTO stroPurchaseDTO = new StroPurchaseDTO();
        //加入医院编码
        stroPurchaseDTO.setHospCode(MapUtils.get(map, "hospCode"));
        //加入供应商id
        stroPurchaseDTO.setSupplierId(stroPhDetailDtosBySup.get(0).getSupplierId());
        //加入库房id
        stroPurchaseDTO.setBizId(stroPhDetailDtosBySup.get(0).getBizId());
        //加入主键id
        stroPurchaseDTO.setId(SnowflakeUtils.getId());
        //加入创建时间
        stroPurchaseDTO.setCrteId(MapUtils.get(map, "crtId"));
        //加入创建人
        stroPurchaseDTO.setCrteName(MapUtils.get(map, "crtName"));
        //审核状态未审核
        stroPurchaseDTO.setAuditCode("0");
        //创建时间
        stroPurchaseDTO.setCrteTime(DateUtils.getNow());
        //生成采购单据
        Map<String,String> param = new HashMap<String,String>();
        param.put("hospCode",MapUtils.get(map, "hospCode"));
        param.put("typeCode",Constants.ORDERRULE.CG);
        WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(param);
        //加入采购单据
        stroPurchaseDTO.setOrderNo(orderNo.getData());

        for (int i = 0; i < stroPhDetailDtosBySup.size(); i++) {
          sellPriceAll = BigDecimalUtils.add(sellPriceAll,stroPhDetailDtosBySup.get(i).getSellPriceAll());
          buyPriceAll = BigDecimalUtils.add(buyPriceAll,stroPhDetailDtosBySup.get(i).getBuyPriceAll());
          stroPhDetailDtosBySup.get(i).setId(SnowflakeUtils.getId());
          stroPhDetailDtosBySup.get(i).setPurchaseId(stroPurchaseDTO.getId());
          stroPhDetailDtosBySup.get(i).setSeqNo(i);
        }
        // 零售总金额
        stroPurchaseDTO.setSellPriceAll(sellPriceAll);
        // 购进总金额
        stroPurchaseDTO.setBuyPriceAll(buyPriceAll);
        //插入采购明细
        stroPurchaseDetailDAO.addBatchDetail(stroPhDetailDtosBySup);
        //插入采购主表
        stroPurchaseDAO.insert(stroPurchaseDTO);
      }
      return true;
    }
}
