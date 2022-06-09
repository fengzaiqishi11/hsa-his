package cn.hsa.base.bmm.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dao.BaseAdviceDAO;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.ba.service.BaseAdviceService;
import cn.hsa.module.base.bmm.bo.BaseMaterialBO;
import cn.hsa.module.base.bmm.dao.BaseMaterialDAO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bp.dao.BaseProductDAO;
import cn.hsa.module.base.bp.dto.BaseProductDTO;
import cn.hsa.module.base.modify.dao.BaseModifyTraceDAO;
import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.base.bmm.bo.impl
 * @Class_name: BaseMaterialManagementBOImpl
 * @Describe: 材料信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseMaterialBOImpl extends HsafBO implements BaseMaterialBO {

    /**
    * @Menthod
    * @Desrciption 注入Dao层对象
     * @param null
    * @Author xingyu.xie
    * @Date   2020/7/8 15:41
    * @Return
    **/
    @Resource
    private BaseMaterialDAO baseMaterialDAO;

    /**
     * 注入单据规则service层
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    //生产厂家服务
    @Resource
    private BaseProductDAO baseProductDAO;

    /**
     * 注入码表service层
     */
    @Resource
    private SysCodeService sysCodeService;


    @Resource
    private BaseAdviceService baseAdviceService;

    @Resource
    private BaseAdviceDAO baseAdviceDAO;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureItemMatchService insureItemMatchService_consumer;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    @Resource
    private BaseModifyTraceDAO baseModifyTraceDAO;
    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询材料信息
     * @param baseMaterialDTO  主键ID List列表和医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/8 15:41
    * @Return cn.hsa.module.base.bmm.dto.BaseMaterialDTO
    **/
    @Override
    public BaseMaterialDTO getById(BaseMaterialDTO baseMaterialDTO,String type) {
        BaseMaterialDTO byId = this.baseMaterialDAO.getById(baseMaterialDTO);
        if(StringUtils.isNotEmpty(byId.getTypeCode())){
            Map map = new HashMap();
            SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
            sysCodeDetailDTO.setHospCode(baseMaterialDTO.getHospCode());
            sysCodeDetailDTO.setCode(type);
            map.put("sysCodeDetailDTO",sysCodeDetailDTO);
            map.put("hospCode",baseMaterialDTO.getHospCode());
            map.put("value",byId.getTypeCode());
            List<String> fathers = sysCodeService.queryFathers(map).getData();
            byId.setFathers(fathers);
        }
        return byId;
    }

    /**
     * @Method: getByCode
     * @Description:根据编码获取材料信息
     * @Param: [baseMaterialDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 16:18
     * @Return: cn.hsa.module.base.bmm.dto.BaseMaterialDTO
     **/
    @Override
    public BaseMaterialDTO getByCode(BaseMaterialDTO baseMaterialDTO) {
        return this.baseMaterialDAO.getByCode(baseMaterialDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部材料信息
     * @param baseMaterialDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/14 21:02
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    @Override
    public List<BaseMaterialDTO> queryAll(BaseMaterialDTO baseMaterialDTO) {
        return this.baseMaterialDAO.queryAll(baseMaterialDTO);
    }

    /**
    * @Method queryBaseMaterialDTOs
    * @Desrciption 查询出符合条件的材料信息
    * @param baseMaterialDTO
    * @Author liuqi1
    * @Date   2020/9/3 15:15
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
    **/
    @Override
    public List<BaseMaterialDTO> queryBaseMaterialDTOs(BaseMaterialDTO baseMaterialDTO) {
        List<BaseMaterialDTO> baseMaterialDTOS = baseMaterialDAO.queryBaseMaterialDTOs(baseMaterialDTO);
        return baseMaterialDTOS;
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象分页查询材料信息
     * @param BaseMaterialDTO 材料信息数据对象
    * @Author xingyu.xie
    * @Date   2020/7/8 15:42
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPage(BaseMaterialDTO BaseMaterialDTO) {

        if(!StringUtils.isEmpty(BaseMaterialDTO.getBfcCode())){
            HashMap map = new HashMap();
            map.put("hospCode",BaseMaterialDTO.getHospCode());
            map.put("code", Constants.ORDERRULE.CL);
            WrapperResponse<List<TreeMenuNode>> codeTree = sysCodeService.getCodeData(map);
            String chidldrenIds = TreeUtils.getChidldrenIds(codeTree.getData(),
                    BaseMaterialDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            BaseMaterialDTO.setIds(list);
        }
        BaseMaterialDTO.setBfcCode("");

        // 设置分页信息
        PageHelper.startPage(BaseMaterialDTO.getPageNo(),BaseMaterialDTO.getPageSize());
        List<BaseMaterialDTO> baseMaterialDTOList = baseMaterialDAO.queryPage(BaseMaterialDTO);
        if(!ListUtils.isEmpty(baseMaterialDTOList)){
            for (BaseMaterialDTO material:baseMaterialDTOList){
                material.setContentType("诊疗项目");
            }
        }
        return  PageDTO.of(baseMaterialDTOList);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改材料分类
     * @param baseMaterialDTO 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:38
     * @Return boolean
     **/
    @Override
    public boolean save(BaseMaterialDTO baseMaterialDTO){
        /**
         * 材料操作时，当选择的国家标准材料的厂家在库中没有维护时，需自动插入一条生产厂家记录
         *
         */
        //根据厂家名称，查询是否存在生产厂家
        List<BaseProductDTO> dtoList = baseProductDAO.getByName(baseMaterialDTO);

        BaseModifyTraceDTO baseModifyTraceDTO = new BaseModifyTraceDTO();
        if(StringUtils.isEmpty(baseMaterialDTO.getProdCode())){
            if (ListUtils.isEmpty(dtoList)){
                //不存在则新增一条厂家信息
                BaseProductDTO baseProductDTO = new BaseProductDTO();
                baseProductDTO.setId(SnowflakeUtils.getId()); //主键
                HashMap map = new HashMap();
                map.put("hospCode",baseMaterialDTO.getHospCode());
                map.put("typeCode","23"); //自动生成厂家编码
                WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
                baseProductDTO.setCode(orderNo.getData()); //厂家编码
                baseProductDTO.setName(baseMaterialDTO.getProdName()); //厂家名
                if (StringUtils.isNotEmpty(baseMaterialDTO.getProdName())) {
                    baseProductDTO.setPym(PinYinUtils.toFirstPY(baseMaterialDTO.getProdName())); //拼音码
                    baseProductDTO.setWbm(WuBiUtils.getWBCode(baseMaterialDTO.getProdName())); //五笔码
                }
                baseProductDTO.setCrteTime(DateUtils.getNow()); //创建时间
            } else {
                baseMaterialDTO.setProdCode(dtoList.get(0).getCode()); //赋值给材料表中
            }
        }
        //判断名字不为空，根据名字生成五笔码和拼音码
        if (!StringUtils.isEmpty(baseMaterialDTO.getName())){

                baseMaterialDTO.setPym(PinYinUtils.toFirstPY(baseMaterialDTO.getName()));

                baseMaterialDTO.setWbm(WuBiUtils.getWBCode(baseMaterialDTO.getName()));
        }
        if (baseMaterialDTO.getPrice()==null){
           throw new AppException("单价不能为空！");
        }
        if (baseMaterialDTO.getSplitRatio()==null){
            throw new AppException("拆分比不能为空！");
        }
        baseMaterialDTO.setSplitPrice(BigDecimalUtils.divide(baseMaterialDTO.getPrice(),baseMaterialDTO.getSplitRatio()));
        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(baseMaterialDTO.getId())){
            // 如果编码为空则自动生成
            if(StringUtils.isEmpty(baseMaterialDTO.getCode())){
                HashMap map = new HashMap();
                map.put("hospCode",baseMaterialDTO.getHospCode());
                map.put("typeCode","22");
                WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);

                baseMaterialDTO.setCode(orderNo.getData());
            }
            baseMaterialDTO.setId(SnowflakeUtils.getId());

            baseMaterialDTO.setCrteTime(DateUtils.getNow());

            if ( this.baseMaterialDAO.isCodeExist(baseMaterialDTO)>0){
                throw new AppException("编码【"+baseMaterialDTO.getCode()+"】的材料已存在，请检查！");
            }
            if(null == baseMaterialDTO.getIsValid() ){
                baseMaterialDTO.setIsValid(Constants.SF.S);
            }

            // 材料修改，写入异动记录
            baseModifyTraceDTO.setId(SnowflakeUtils.getId());
            baseModifyTraceDTO.setHospCode(baseMaterialDTO.getHospCode());
            baseModifyTraceDTO.setTableName("base_material");
            baseModifyTraceDTO.setUpdtId(baseMaterialDTO.getCrteId());
            baseModifyTraceDTO.setUpdtName(baseMaterialDTO.getCrteName());
            Map<String, Object> conentMap = new HashMap<>();
            conentMap.put("before", "-");
            conentMap.put("after", baseMaterialDTO);
            baseModifyTraceDTO.setUpdtConent(JSONObject.toJSONString(conentMap));
            baseModifyTraceDAO.insert(baseModifyTraceDTO);


            int insert = this.baseMaterialDAO.insert(baseMaterialDTO);
            // 存入缓存
//            cacheOperate(baseMaterialDTO,null,true);
            return insert > 0;
        }else {
            BaseMaterialDTO oldMat = baseMaterialDAO.getById(baseMaterialDTO);
            BaseAdviceDetailDTO baseAdviceDetailDTO = new BaseAdviceDetailDTO();
            List<BaseAdviceDetailDTO> baseAdviceDetailDTOList = new ArrayList<>();
            //回写材料名称
            baseAdviceDetailDTO.setItemName(baseMaterialDTO.getName());
            //回写材料单价
            baseAdviceDetailDTO.setPrice(baseMaterialDTO.getPrice());
            //回写材料单位
            baseAdviceDetailDTO.setUnitCode(baseMaterialDTO.getUnitCode());
            //回写材料规格
            baseAdviceDetailDTO.setSpec(baseMaterialDTO.getSpec());
            //回写用药性质
            baseAdviceDetailDTO.setUseCode(baseMaterialDTO.getUseCode());
            // 写入材料编码
            baseAdviceDetailDTO.setItemCode(baseMaterialDTO.getCode());
            baseAdviceDetailDTO.setHospCode(baseMaterialDTO.getHospCode());
            baseAdviceDetailDTOList.add(baseAdviceDetailDTO);
            Map map = new HashMap<>();
            map.put("hospCode",baseMaterialDTO.getHospCode());
            map.put("baseAdviceDetailDTOList",baseAdviceDetailDTOList);
            baseAdviceService.updateBaseAdviceAndBaseAdviceDetailsByItemCode(map);
            int update = this.baseMaterialDAO.update(baseMaterialDTO);
            // 缓存操作 -- 只有有效的时候才进行操作
            if(Constants.SF.S.equals(baseMaterialDTO.getIsValid())){
//                cacheOperate(baseMaterialDTO,null,true);
            }

            // 材料异动记录 写入异动记录
            baseModifyTraceDTO.setId(SnowflakeUtils.getId());
            baseModifyTraceDTO.setHospCode(baseMaterialDTO.getHospCode());
            baseModifyTraceDTO.setTableName("base_material");
            baseModifyTraceDTO.setUpdtId(baseMaterialDTO.getCrteId());
            baseModifyTraceDTO.setUpdtName(baseMaterialDTO.getCrteName());
            Map<String, Object> conentMap = new HashMap<>();
            conentMap.put("before", oldMat);
            conentMap.put("after", baseMaterialDTO);
            String jsonObject= JSONObject.toJSONString(conentMap);
            baseModifyTraceDTO.setUpdtConent(jsonObject);
            baseModifyTraceDAO.insert(baseModifyTraceDTO);

            return update > 0;
        }
    }

    /**
    * @Menthod updateList
    * @Desrciption  修改多条材料数据
     * @param baseMaterialDTOList 材料分类List
    * @Author xingyu.xie
    * @Date   2020/8/24 15:32
    * @Return boolean
    **/
    @Override
    public boolean updateList(List<BaseMaterialDTO> baseMaterialDTOList) {
        if (ListUtils.isEmpty(baseMaterialDTOList)){
            throw new AppException("修改数据为空");
        }
        baseMaterialDAO.updateList(baseMaterialDTOList);
        return true;
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个材料信息
     * @param baseMaterialDTO
    * @Author xingyu.xie
    * @Date   2020/7/8 15:43
    * @Return boolean
    **/
    @Override
    public boolean updateStatus(BaseMaterialDTO baseMaterialDTO) {

        if (Constants.SF.F.equals(baseMaterialDTO.getIsValid())){
            if (ListUtils.isEmpty(baseMaterialDTO.getIds())){
                throw new AppException("要作废的数据不能为空！");
            }

            if (StringUtils.isEmpty(baseMaterialDTO.getCode())){
                throw new AppException("项目编码不能为空！");
            }

            List<BaseMaterialDTO> baseMaterialDTOS = this.baseMaterialDAO.queryByIds(baseMaterialDTO);

            List<String> existStockMaterial = new ArrayList<>();

//            baseMaterialDTOS.forEach(item->{
//                if (item.getStockFlag()>0){
//                    existStockMaterial.add(item.getName()+"（"+item.getCode()+"）");
//                }
//
//            });
//
//            if (!ListUtils.isEmpty(existStockMaterial)){
//                throw new AppException("作废失败,材料【"+ Joiner.on(",").join(existStockMaterial)+"】在库存中已存在。");
//            }
//
//            existStockMaterial.clear();

            baseMaterialDTOS.forEach(item->{

                // 判断材料存在哪些医嘱里
                List<BaseAdviceDTO> existMaterialInAdvice = this.baseAdviceDAO.isExistMaterialInAdvice(item);

                if (!ListUtils.isEmpty(existMaterialInAdvice)){

                    StringBuilder message = new StringBuilder();

                    for (BaseAdviceDTO baseAdviceDTO :existMaterialInAdvice){
                        message.append("【").append(baseAdviceDTO.getName()).append("（").append(baseAdviceDTO.getCode()).append("）】");
                    }

                    existStockMaterial.add("材料【"+item.getName()+"（"+item.getCode()+"）】已被医嘱"+message+"使用。");
                }

            });

            if (!ListUtils.isEmpty(existStockMaterial)){
                throw new AppException("作废失败,"+ Joiner.on("").join(existStockMaterial));
            }
        }


        List<BaseMaterialDTO> baseMaterialDTOS = new ArrayList<>();
        String isValid = baseMaterialDTO.getIsValid();
        if(Constants.SF.S.equals(isValid)){
            baseMaterialDTO.setIsValid(Constants.SF.F);
        } else {
            baseMaterialDTO.setIsValid(Constants.SF.S);
        }
        baseMaterialDTOS = baseMaterialDAO.queryAll(baseMaterialDTO);

        baseMaterialDTO.setIsValid(isValid);
        int i = this.baseMaterialDAO.updateStatus(baseMaterialDTO);

        if(Constants.SF.F.equals(baseMaterialDTO.getIsValid())){
//            cacheOperate(null,baseMaterialDTOS,false);
        }else {
//            cacheOperate(null,baseMaterialDTOS,true);
        }

        return i>0;
    }

    /**
    * @Menthod insertUpLoad
    * @Desrciption  材料导入功能
     * @param map
    * @Author xingyu.xie
    * @Date   2021/1/9 13:05
    * @Return boolean
    **/
    @Override
    public boolean insertUpLoad(Map map){

        String hospCode = (String) map.get("hospCode");
        String userName = (String) map.get("crteName");
        String userId = (String) map.get("crteId");
        Date nowDate = DateUtils.getNow();

        List<List<String>> resultList = (List<List<String>>) map.get("resultList");

       // 拿取系统码表列表
        HashMap sysCodeMap = new HashMap();
        sysCodeMap.put("hospCode",hospCode);
        sysCodeMap.put("code","CLFL,DW,YYXZ,SF");
        WrapperResponse<Map<String, List<SysCodeSelectDTO>>> byCode = sysCodeService.getByCode(sysCodeMap);

        Map<String, List<SysCodeSelectDTO>> data = byCode.getData();

        List<SysCodeSelectDTO> clfl = data.get("CLFL");
        List<SysCodeSelectDTO> dw = data.get("DW");
        List<SysCodeSelectDTO> yyxz = data.get("YYXZ");
        List<SysCodeSelectDTO> SF = data.get("SF");

        Map<String, String> materialMap = clfl.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue,(v1,v2)->v2));
        Map<String, String> unitCodeMap = dw.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue,(v1,v2)->v2));
        Map<String, String> useCodeMap = yyxz.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue,(v1,v2)->v2));
        Map<String, String> sfMap = SF.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue,(v1,v2)->v2));

        List<BaseMaterialDTO> insertList = new ArrayList<>();

        try {
            for (List<String> item : resultList){

//                // 生成材料编码
//                HashMap ruleMap = new HashMap();
//                ruleMap.put("hospCode",hospCode);
//                ruleMap.put("typeCode","22");
//                WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(ruleMap);
//                String code = orderNo.getData();

                BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();

                if (StringUtils.isEmpty(item.get(0)) || StringUtils.isEmpty(item.get(1)) || StringUtils.isEmpty(item.get(2))
                        || StringUtils.isEmpty(item.get(3)) || StringUtils.isEmpty(item.get(4)) || StringUtils.isEmpty(item.get(5))
                        || StringUtils.isEmpty(item.get(6)) || StringUtils.isEmpty(item.get(7) )|| StringUtils.isEmpty(item.get(8))
                        || StringUtils.isEmpty(item.get(9)) || StringUtils.isEmpty(item.get(10)) ){
                    throw new AppException("存在必填字段为空，请检查！");
                }

                baseMaterialDTO.setId(SnowflakeUtils.getId());
                // 医院编码
                baseMaterialDTO.setHospCode(hospCode);
                // 财务分类code
                baseMaterialDTO.setBfcCode(item.get(0));
                // 材料编码
                baseMaterialDTO.setCode(item.get(1));
                // 材料分类（码表）
                baseMaterialDTO.setTypeCode(materialMap.get(item.get(2).trim()));
                // 材料名
                baseMaterialDTO.setName(item.get(3));
                // 材料单价
                baseMaterialDTO.setPrice(BigDecimalUtils.convert(item.get(4)));
                // 材料规格
                baseMaterialDTO.setSpec(item.get(5));
                // 材料单位
                baseMaterialDTO.setUnitCode(unitCodeMap.get(item.get(6).trim()));
                // 材料拆分比
                baseMaterialDTO.setSplitRatio(BigDecimalUtils.convert(item.get(7)));
                // 材料拆零单价
                baseMaterialDTO.setSplitPrice(BigDecimalUtils.convert(item.get(8)));
                // 材料拆零单位
                baseMaterialDTO.setSplitUnitCode(unitCodeMap.get(item.get(9).trim()));
                // 材料是否有效
                baseMaterialDTO.setIsValid(sfMap.get(item.get(10)));
                // 材料最近购进单价
                baseMaterialDTO.setLastBuyPrice(BigDecimalUtils.convert(item.get(11)));
                // 材料最近拆零购进单价
                baseMaterialDTO.setLastSplitBuyPrice(BigDecimalUtils.convert(item.get(12)));
                // 材料平均购进价
                baseMaterialDTO.setAvgBuyPrice(BigDecimalUtils.convert(item.get(13)));
                // 材料平均零售价
                baseMaterialDTO.setAvgSellPrice(BigDecimalUtils.convert(item.get(14)));
                // 材料备注
                baseMaterialDTO.setRemark(item.get(15));
                // 材料用药性质
                baseMaterialDTO.setUseCode(useCodeMap.get(item.get(16).trim()));
                // 材料是否补记账
                baseMaterialDTO.setIsSuppCurtain(sfMap.get(item.get(17)));
                // 材料生产企业编码
                baseMaterialDTO.setProdCode(item.get(18));
                // 材料注册证号
                baseMaterialDTO.setRegCertNo(item.get(19));

                // 有拼音码和五笔码就直接塞入，无就根据名字自动生成
                if (StringUtils.isNotEmpty(item.get(20))){
                    baseMaterialDTO.setPym(item.get(20));
                }else if (StringUtils.isNotEmpty(item.get(3))){
                    baseMaterialDTO.setPym(PinYinUtils.toFirstPY(item.get(3)));
                }

                if (StringUtils.isNotEmpty(item.get(21))){
                    baseMaterialDTO.setWbm(item.get(21));
                }else if (StringUtils.isNotEmpty(item.get(3))){
                    baseMaterialDTO.setWbm(WuBiUtils.getWBCode(item.get(3)));
                }
                // 创建信息
                baseMaterialDTO.setCrteTime(nowDate);
                baseMaterialDTO.setCrteName(userName);
                baseMaterialDTO.setCrteId(userId);

                insertList.add(baseMaterialDTO);
            }
        }catch (IndexOutOfBoundsException | NullPointerException  e){
            throw new AppException("EXCEL数据格式错误，导入失败");
        }catch (AppException e){
            throw e;
        }catch (Exception e){
            throw new AppException("EXCEL数据格式错误，导入失败");
        }

        return this.baseMaterialDAO.insertList(insertList)>0;

    }


    /**
     * @param map
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步材料数据到医保匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    @Override
    public Boolean insertInsureMaterialMatch(Map<String, Object> map) {
        /*map.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if(sys ==null || !Constants.SF.S.equals(sys.getValue())){
            throw new AppException("请先配置走医保统一支付平台,再进行药品同步操作");
        }*/
        String insureRegCode = MapUtils.get(map,"regCode");

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(MapUtils.get(map,"hospCode")); //医院编码
        configDTO.setRegCode(insureRegCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        Map configMap = new LinkedHashMap();
        configMap.put("hospCode", MapUtils.get(map,"hospCode"));
        configMap.put("insureConfigurationDTO", configDTO);
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
        if (StringUtils.isEmpty(isUnifiedPay) || !"1".equals(isUnifiedPay)) {
            throw new AppException("请先配置走医保统一支付平台,再进行药品医保同步操作");
        }

        String crteId =  MapUtils.get(map,"crteId");
        String crteName =  MapUtils.get(map,"crteName");
        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        baseMaterialDTO.setIsValid(Constants.SF.S);
        baseMaterialDTO.setHospCode(MapUtils.get(map,"hospCode"));
        baseMaterialDTO.setIsNationCode(true);
        // 医院端的药品数据
        List<BaseMaterialDTO> baseMaterialDTOList = baseMaterialDAO.queryAll(baseMaterialDTO);

        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(MapUtils.get(map,"hospCode"));
        insureItemMatchDTO.setInsureRegCode(MapUtils.get(map,"insureRegCode"));
        insureItemMatchDTO.setIsValid(Constants.SF.S);
        insureItemMatchDTO.setIsItemCancel(false);
        insureItemMatchDTO.setIsTrans(Constants.SF.S);
        insureItemMatchDTO.setHospItemType(Constants.XMLB.CL);
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        // 医保匹配表的药品数据集合
        insureItemMatchService_consumer.deleteInsureItemMatch(map).getData();
        List<BaseMaterialDTO> matchMaterialDTOList = new ArrayList<>();
        if(!ListUtils.isEmpty(baseMaterialDTOList)){
            for(BaseMaterialDTO materialDTO: baseMaterialDTOList){
                    materialDTO.setHospItemId(materialDTO.getId());
                    materialDTO.setId(SnowflakeUtils.getId());
                    materialDTO.setInsureRegCode(insureRegCode); // 医疗机构编码
                    materialDTO.setInsureItemName(materialDTO.getName()); // 医保中心项目名称
                    materialDTO.setItemCode(Constants.UNIFIED_PAY_TYPE.YYCL); // 项目类别标志
                    materialDTO.setHospItemType(Constants.XMLB.CL);
                    materialDTO.setInsureItemCode(materialDTO.getNationCode()); // 医保中心项目编码
                    materialDTO.setInsureItemType(Constants.UNIFIED_PAY_TYPE.YYCL); // 医保中心项目类别
                    materialDTO.setInsureItemUnitCode(null); // 医保中心项目单位
                    materialDTO.setInsureItemPrepCode(null); // 医保中心项目剂型
                    materialDTO.setInsureItemPrice(null); // 医保中心项目价格
                    materialDTO.setDeductible(null); // 自费比例
                    materialDTO.setStandardCode(null); // 本位码
                    materialDTO.setCheckPrice(null); // 限价
                    materialDTO.setIsMatch(Constants.SF.F); // 是否匹配
                    materialDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    materialDTO.setIsTrans(Constants.SF.F); // 是否传输
                    materialDTO.setLoseDate(null); // 生效日期
                    materialDTO.setTakeDate(null); // 生效日期
                    materialDTO.setCrteTime(DateUtils.getNow());
                    materialDTO.setCrteId(crteId);
                    materialDTO.setCrteName(crteName);
                    matchMaterialDTOList.add(materialDTO);

            }
            if(!ListUtils.isEmpty(matchMaterialDTOList)){
                baseMaterialDAO.insertInsureMatch(matchMaterialDTOList);
            }

        }
        return true;
    }

    @Override
    public PageDTO queryUnifiedPage(BaseMaterialDTO baseMaterialDTO) {
        PageHelper.startPage(baseMaterialDTO.getPageNo(),baseMaterialDTO.getPageSize());
        List<BaseMaterialDTO> baseMaterialDTOList =baseMaterialDAO.queryUnifiedPage(baseMaterialDTO);
        return PageDTO.of(baseMaterialDTOList);
    }

    /**
     * @param map 材料信息数据传输对象List
     * @Menthod updateNationCodeById
     * @Desrciption 批量修改材料信息
     * @Author pengbo
     * @Date 2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public boolean updateNationCodeById(Map map) {
        BaseMaterialDTO baseMaterialDTO = (BaseMaterialDTO) map.get("baseMaterialDTO");
        if(baseMaterialDTO == null || StringUtils.isEmpty(baseMaterialDTO.getId())){
            throw  new RuntimeException("材料信息不能为空");
        }
        return this.baseMaterialDAO.updateNationCodeById(baseMaterialDTO)>0;
    }

    public void cacheOperate(BaseMaterialDTO baseMaterialDTO,List<BaseMaterialDTO> baseMaterialDTOS, Boolean operation){
        if (baseMaterialDTO != null) {
            String key = StringUtils.createKey("material", baseMaterialDTO.getHospCode(), baseMaterialDTO.getId());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, baseMaterialDTO);
            }
        }

        if (!ListUtils.isEmpty(baseMaterialDTOS)) {
            for (BaseMaterialDTO material : baseMaterialDTOS) {
                String key = StringUtils.createKey("material", material.getHospCode(), material.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, material);
                }
            }
        }
    }
}
