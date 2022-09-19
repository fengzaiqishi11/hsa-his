package cn.hsa.base.ba.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.bo.BaseAdviceBO;
import cn.hsa.module.base.ba.dao.BaseAdviceDAO;
import cn.hsa.module.base.ba.dao.BaseAdviceDetailDAO;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.modify.dao.BaseModifyTraceDAO;
import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.base.ba.bo.impl
 * @Class_name: BaseAdviceBOImpl
 * @Describe: 医嘱信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 15:06大是的发生的
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseAdviceBOImpl extends HsafBO implements BaseAdviceBO {

    /**
     * 注入医嘱dao层对象
     */
    @Resource
    BaseAdviceDAO baseAdviceDAO;

    /**
     * 注入医嘱详细dao层对象
     */
    @Resource
    BaseAdviceDetailDAO baseAdviceDetailDAO;

    /**
     * 注入单据生成service层
     */
    @Resource
    BaseOrderRuleService baseOrderRuleService;

    @Resource
    SysCodeService sysCodeService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private BaseModifyTraceDAO baseModifyTraceDAO;

    /**
     * @param baseAdviceDTO 主键ID 等参数
     * @Menthod getById
     * @Desrciption 根据主键Id等参数查询医嘱信息
     * @Author xingyu.xie
     * @Date 2020/7/14 15:41
     * @Return cn.hsa.module.base.bmm.dto.BaseMaterialDTO
     **/
    @Override
    public BaseAdviceDTO getById(BaseAdviceDTO baseAdviceDTO) {
        BaseAdviceDTO byId = this.baseAdviceDAO.getById(baseAdviceDTO);
        if(StringUtils.isNotEmpty(byId.getTypeCode())){
            Map map = new HashMap();
            SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
            sysCodeDetailDTO.setHospCode(baseAdviceDTO.getHospCode());
            sysCodeDetailDTO.setCode("YZLB");
            map.put("sysCodeDetailDTO",sysCodeDetailDTO);
            map.put("hospCode",baseAdviceDTO.getHospCode());
            map.put("value",byId.getTypeCode());
            List<String> fathers = sysCodeService.queryFathers(map).getData();
            byId.setFathers(fathers);
        }
        return byId;
    }

    /**
     * @param baseAdviceDTO
     * @Menthod queryItemsByAdviceCode
     * @Desrciption 根据医嘱的code医嘱编码 查询出此医嘱的医嘱详细的数据
     * @Author xingyu.xie
     * @Date 2020/8/6 13:53
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryItemsByAdviceCode(BaseAdviceDTO baseAdviceDTO) {
        // 设置分页信息
        PageHelper.startPage(baseAdviceDTO.getPageNo(), baseAdviceDTO.getPageSize());

        //查询所有  通过医嘱编码
        List<BaseAdviceDetailDTO> baseAdviceDetailDTOS = baseAdviceDetailDAO.queryItemByAdviceCode(baseAdviceDTO);

        //返回分页结果
        return PageDTO.of(baseAdviceDetailDTOS);
    }

    /**
     * @param baseAdviceDTO 医院编码
     * @Menthod queryAll
     * @Desrciption 查询全部医嘱信息
     * @Author xingyu.xie
     * @Date 2020/7/14 21:04
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
     **/
    @Override
    public List<BaseAdviceDTO> queryAll(BaseAdviceDTO baseAdviceDTO) {
        return this.baseAdviceDAO.queryPage(baseAdviceDTO);
    }

    /**
     * @param baseAdviceDetailDTO 项目编码，材料编码等参数
     * @Menthod queryAllAdviceDetail
     * @Desrciption 根据项目编码查询医嘱详细
     * @Author xingyu.xie
     * @Date 2020/7/14 16:05
     * @Return cn.hsa.module.base.bfc.dto.BaseAdviceDTO
     **/
    @Override
    public List<BaseAdviceDetailDTO> queryAllAdviceDetail(BaseAdviceDetailDTO baseAdviceDetailDTO) {
        return baseAdviceDetailDAO.queryAll(baseAdviceDetailDTO);
    }


    /**
     * @param baseAdviceDTO 医嘱信息数据对象
     * @Menthod queryPage
     * @Desrciption 根据数据对象分页查询医嘱信息
     * @Author xingyu.xie
     * @Date 2020/7/14 15:42
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseAdviceDTO baseAdviceDTO) {

        if (!StringUtils.isEmpty(baseAdviceDTO.getTypeCode())) {
            HashMap map = new HashMap();
            map.put("hospCode", baseAdviceDTO.getHospCode());
            map.put("code", "YZFL");
            List<TreeMenuNode> treeMenuNodeList = sysCodeService.getCodeData(map).getData();
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    baseAdviceDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseAdviceDTO.setIds(list);
        }
        baseAdviceDTO.setTypeCode("");

        // 设置分页信息
        PageHelper.startPage(baseAdviceDTO.getPageNo(), baseAdviceDTO.getPageSize());
        List<BaseAdviceDTO> baseAdviceDTOList = baseAdviceDAO.queryPage(baseAdviceDTO);
        return PageDTO.of(baseAdviceDTOList);
    }

    /**
     * @param baseAdviceDTO 医嘱信息数据对象
     * @Menthod insert
     * @Desrciption 新增单条医嘱信息和多条医嘱详细信息
     * @Author xingyu.xie
     * @Date 2020/7/14 15:42
     * @Return boolean
     **/
    @Override
    public boolean insert(BaseAdviceDTO baseAdviceDTO) {
        BaseModifyTraceDTO baseModifyTraceDTO = new BaseModifyTraceDTO();
        baseAdviceDTO.setId(SnowflakeUtils.getId());
        baseAdviceDTO.setCrteTime(DateUtils.getNow());
        //修改生成状态列表
        List<String> codeList = new ArrayList<>();
        // 根据单据规则生成医嘱编码
        Map map = new HashMap();
        map.put("hospCode", baseAdviceDTO.getHospCode());
        map.put("typeCode", Constants.ORDERRULE.YZ);
        String orderNo = baseOrderRuleService.getOrderNo(map).getData();
        map.remove("typeCode");
        baseAdviceDTO.setCode(orderNo);
        //根据name生成拼音码和五笔码
        if (!StringUtils.isEmpty(baseAdviceDTO.getName())) {

            baseAdviceDTO.setPym(PinYinUtils.toFirstPY(baseAdviceDTO.getName()));
            baseAdviceDTO.setWbm(WuBiUtils.getWBCode(baseAdviceDTO.getName()));
        }
        //医嘱主表的总金额
        BigDecimal totalPriceAdvice = BigDecimal.valueOf(0);
        //判断要插入医嘱明细的列表不为空
        List<BaseAdviceDetailDTO> baseAdviceDetailDTOList = baseAdviceDTO.getBaseAdviceDetailDTOList();

        if (!ListUtils.isEmpty(baseAdviceDetailDTOList)) {

            // 通过项目编码itemCode将其分组，来判断有无重复的项目
            isExitSameAdviceDetail(baseAdviceDetailDTOList);

            // 循环插入
            for (BaseAdviceDetailDTO item : baseAdviceDetailDTOList) {

                if (item.getPrice()==null){
                    throw new AppException("单价不能为空！");
                }

                if (item.getNum()==null){
                    throw new AppException("数量不能为空！");
                }

                item.setId(SnowflakeUtils.getId());
                //根据数量和单价计算总金额
                BigDecimal totalPrice = BigDecimalUtils.multiply(item.getPrice(), item.getNum());

                item.setTotalPrice(totalPrice);

                totalPriceAdvice = BigDecimalUtils.add(totalPriceAdvice, totalPrice);

                item.setAdviceCode(baseAdviceDTO.getCode());
                codeList.add(item.getItemCode());
            }
            baseAdviceDetailDAO.insert(baseAdviceDetailDTOList);
            //存入缓存
//            cacheDetailOperate(baseAdviceDetailDTOList,baseAdviceDTO.getHospCode(),true);
            // 医嘱修改，写入异动记录
            baseModifyTraceDTO.setId(SnowflakeUtils.getId());
            baseModifyTraceDTO.setHospCode(baseAdviceDTO.getHospCode());
            baseModifyTraceDTO.setTableName("base_advice_detail");
            baseModifyTraceDTO.setUpdtId(baseAdviceDTO.getCrteId());
            baseModifyTraceDTO.setUpdtName(baseAdviceDTO.getCrteName());
            Map<String, Object> conentMap = new HashMap<>();
            conentMap.put("before", "-");
            conentMap.put("after", baseAdviceDetailDTOList);
            baseModifyTraceDTO.setUpdtConent(JSONObject.toJSONString(conentMap));
            baseModifyTraceDAO.insert(baseModifyTraceDTO);
        }
        baseAdviceDTO.setPrice(totalPriceAdvice);
        int insert = this.baseAdviceDAO.insert(baseAdviceDTO);
        this.updateGenerateStutas(codeList,baseAdviceDTO.getHospCode(),Constants.SF.S);
        // 存入缓存
//        cacheOperate(baseAdviceDTO,null,true);
        // 医嘱修改，写入异动记录
        baseModifyTraceDTO.setId(SnowflakeUtils.getId());
        baseModifyTraceDTO.setHospCode(baseAdviceDTO.getHospCode());
        baseModifyTraceDTO.setTableName("base_advice");
        baseModifyTraceDTO.setUpdtId(baseAdviceDTO.getCrteId());
        baseModifyTraceDTO.setUpdtName(baseAdviceDTO.getCrteName());
        Map<String, Object> conentMap = new HashMap<>();
        conentMap.put("before", "-");
        conentMap.put("after", baseAdviceDetailDTOList);
        baseModifyTraceDTO.setUpdtConent(JSONObject.toJSONString(conentMap));
        baseModifyTraceDAO.insert(baseModifyTraceDTO);

        return insert > 0;
    }

    /**
     * @param baseAdviceDTO 医嘱信息数据对象
     * @Menthod update
     * @Desrciption 修改单条医嘱信息和多条医嘱详细信息
     * @Author xingyu.xie
     * @Date 2020/7/14 15:43
     * @Return boolean
     **/
    @Override
    public boolean update(BaseAdviceDTO baseAdviceDTO) {
        BaseModifyTraceDTO baseModifyTraceDTO = new BaseModifyTraceDTO();
        BaseAdviceDTO oldAdvice = baseAdviceDAO.getById(baseAdviceDTO);
        //要修改或者插入的列信息（医嘱详细）
        List<BaseAdviceDetailDTO> baseAdviceDetailDTOList = baseAdviceDTO.getBaseAdviceDetailDTOList();

        //name生成拼音码和五笔码
        if (!StringUtils.isEmpty(baseAdviceDTO.getName())) {

            baseAdviceDTO.setPym(PinYinUtils.toFirstPY(baseAdviceDTO.getName()));
            baseAdviceDTO.setWbm(WuBiUtils.getWBCode(baseAdviceDTO.getName()));
        }

        //医嘱主表的总金额
        BigDecimal totalPriceAdvice = BigDecimal.valueOf(0);

        if (!ListUtils.isEmpty(baseAdviceDetailDTOList)) {

            // 通过项目编码itemCode将其分组，来判断有无重复的项目
            isExitSameAdviceDetail(baseAdviceDetailDTOList);

            List<BaseAdviceDetailDTO> insertList = new ArrayList<>();

            List<BaseAdviceDetailDTO> updateList = new ArrayList<>();
            //修改生成状态列表
            List<String> codeList = new ArrayList<>();
            for (BaseAdviceDetailDTO item : baseAdviceDetailDTOList) {

                if (item.getPrice()==null){
                    throw new AppException("单价不能为空！");
                }

                if (item.getNum()==null){
                    throw new AppException("数量不能为空！");
                }

                //根据数量和单价计算总金额
                BigDecimal totalPrice = BigDecimalUtils.multiply(item.getPrice(), item.getNum());

                item.setTotalPrice(totalPrice);

                totalPriceAdvice = BigDecimalUtils.add(totalPriceAdvice, totalPrice);

                //没有id则是要插入的数据
                if (StringUtils.isEmpty(item.getId())) {

                    item.setId(SnowflakeUtils.getId());

                    item.setAdviceCode(baseAdviceDTO.getCode());

                    item.setHospCode(baseAdviceDTO.getHospCode());

                    insertList.add(item);
                    //有id就是要修改的数据
                } else {
                    updateList.add(item);
                }
                codeList.add(item.getItemCode());
            }

            //明细数据缓存处理
            BaseAdviceDetailDTO bad = new BaseAdviceDetailDTO();
            bad.setHospCode(baseAdviceDTO.getHospCode());
            bad.setAdviceCode(baseAdviceDTO.getCode());
            List<BaseAdviceDetailDTO> baseAdviceDetailDTOS = baseAdviceDetailDAO.queryPage(bad);
            if(!ListUtils.isEmpty(baseAdviceDetailDTOS)){
//                cacheDetailOperate(baseAdviceDetailDTOS,bad.getHospCode(),false);
            }
            List allList = new ArrayList();
            allList.addAll(insertList);
            allList.addAll(updateList);
            if(!ListUtils.isEmpty(allList)){
//                cacheDetailOperate(allList,bad.getHospCode(),true);
                this.updateGenerateStutas(codeList,baseAdviceDTO.getHospCode(),Constants.SF.S);
            }

            if (!ListUtils.isEmpty(insertList)) {
                baseAdviceDetailDAO.insert(insertList);
            }
            if (!ListUtils.isEmpty(updateList)) {
                baseAdviceDetailDAO.update(updateList);
            }

            baseModifyTraceDTO.setId(SnowflakeUtils.getId());
            baseModifyTraceDTO.setHospCode(baseAdviceDTO.getHospCode());
            baseModifyTraceDTO.setTableName("base_advice_detail");
            baseModifyTraceDTO.setUpdtId(baseAdviceDTO.getCrteId());
            baseModifyTraceDTO.setUpdtName(baseAdviceDTO.getCrteName());
            Map<String, Object> conentMap = new HashMap<>();
            conentMap.put("before", insertList);
            conentMap.put("after", baseAdviceDetailDTOList);
            baseModifyTraceDTO.setUpdtConent(JSONObject.toJSONString(conentMap));
            baseModifyTraceDAO.insert(baseModifyTraceDTO);
        }

        // 要删除的数据(医嘱详细)
        if (!ListUtils.isEmpty(baseAdviceDTO.getIds())) {
            // 根据删除详细表的ids查出所有要删除的详细数据，从总价中减去
            BaseAdviceDetailDTO baseAdviceDetailDTO = new BaseAdviceDetailDTO();
            baseAdviceDetailDTO.setIds(baseAdviceDTO.getIds());
            baseAdviceDetailDTO.setHospCode(baseAdviceDTO.getHospCode());
            // 删除的详细数据
            List<BaseAdviceDetailDTO> baseAdviceDetailDTOS = baseAdviceDetailDAO.queryPage(baseAdviceDetailDTO);
            //修改生成状态列表
            List<String> codeList = new ArrayList<>();
            baseAdviceDetailDAO.delete(baseAdviceDTO);

            for (BaseAdviceDetailDTO item : baseAdviceDetailDTOS) {
                totalPriceAdvice = BigDecimalUtils.subtract(totalPriceAdvice, item.getTotalPrice());
                codeList.add(item.getItemCode());
            }
            this.updateGenerateStutas(codeList,baseAdviceDTO.getHospCode(),Constants.SF.F);
        }

        //修改医嘱信息
        baseAdviceDTO.setPym(PinYinUtils.toFirstPY(baseAdviceDTO.getName()));
        baseAdviceDTO.setWbm(WuBiUtils.getWBCode(baseAdviceDTO.getName()));
        int update = this.baseAdviceDAO.update(baseAdviceDTO);
        // 缓存操作 -- 只有有效的时候才进行操作
        if(Constants.SF.S.equals(baseAdviceDTO.getIsValid())){
            baseAdviceDTO.setIds(null);
//            cacheOperate(baseAdviceDTO,null,true);
        }

        // 医嘱修改，写入异动记录
        baseModifyTraceDTO.setId(SnowflakeUtils.getId());
        baseModifyTraceDTO.setHospCode(baseAdviceDTO.getHospCode());
        baseModifyTraceDTO.setTableName("base_advice");
        baseModifyTraceDTO.setUpdtId(baseAdviceDTO.getCrteId());
        baseModifyTraceDTO.setUpdtName(baseAdviceDTO.getCrteName());
        Map<String, Object> conentMap = new HashMap<>();
        conentMap.put("before", oldAdvice);
        conentMap.put("after", baseAdviceDetailDTOList);
        baseModifyTraceDTO.setUpdtConent(JSONObject.toJSONString(conentMap));
        baseModifyTraceDAO.insert(baseModifyTraceDTO);

        return update > 0;
    }

    /**
     * @param baseAdviceDTO
     * @Menthod updateStatus
     * @Desrciption 根据主键ID等参数，删除一个或多个医嘱信息
     * @Author xingyu.xie
     * @Date 2020/7/14 15:43
     * @Return boolean
     **/
    @Override
    public boolean updateStatus(BaseAdviceDTO baseAdviceDTO) {

        List<BaseAdviceDTO> baseAdviceDTOS = new ArrayList<>();
        String isValid = baseAdviceDTO.getIsValid();
        if(Constants.SF.S.equals(isValid)){
            baseAdviceDTO.setIsValid(Constants.SF.F);
        } else {
            baseAdviceDTO.setIsValid(Constants.SF.S);
        }
        baseAdviceDTOS = baseAdviceDAO.queryAll(baseAdviceDTO);

        baseAdviceDTO.setIsValid(isValid);
        int i = this.baseAdviceDAO.updateStatus(baseAdviceDTO);
        //缓存操作
        if(Constants.SF.F.equals(baseAdviceDTO.getIsValid())){
//            cacheOperate(null,baseAdviceDTOS,false);
        }else {
//            cacheOperate(null,baseAdviceDTOS,true);
        }
        return true;
    }

    /**
     * @param baseAdviceDetailDTO 医嘱详细数据传输对象
     * @Menthod queryItemAndMaterialPage
     * @Desrciption 将项目表和材料表的数据一起查出来，并转换为医嘱详细的数据传输对象
     * @Author xingyu.xie
     * @Date 2020/8/6 13:48
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryItemAndMaterialPage(BaseAdviceDetailDTO baseAdviceDetailDTO) {
        // 设置分页信息
        PageHelper.startPage(baseAdviceDetailDTO.getPageNo(), baseAdviceDetailDTO.getPageSize());
        List<BaseAdviceDetailDTO> baseAdviceDetailDTOS = new ArrayList<>();
        if("1".equals(baseAdviceDetailDTO.getQueryCode())){
            //查询出有库存的数据
            baseAdviceDetailDTOS = baseAdviceDetailDAO.queryItemAndMaterialStockPage(baseAdviceDetailDTO);
        }else{
            baseAdviceDetailDTOS = baseAdviceDetailDAO.queryItemAndMaterialPage(baseAdviceDetailDTO);
        }

        return PageDTO.of(baseAdviceDetailDTOS);
    }


    /**
     * @param baseAdviceDetailDTOList 项目或材料的
     * @Menthod updateBaseAdviceAndBaseAdviceDetailsByItemCode
     * @Desrciption 更新材料或项目的单价或者
     * 必填：1.医院编码（hospCode） 2.项目或材料编码（itemCode）
     * 选填：1.单价（price） 2.名称（itemName） 3.单位代码（unitCode） 4.规格（spec） 5.用药性质（useCode）
     * @Author xingyu.xie
     * @Date 2020/9/4 14:41
     * @Return boolean
     **/
    @Override
    public boolean updateBaseAdviceAndBaseAdviceDetailsByItemCode(List<BaseAdviceDetailDTO> baseAdviceDetailDTOList) {

        if (ListUtils.isEmpty(baseAdviceDetailDTOList)) {
            throw new AppException("回写的项目或材料不能为空！");
        }

        for (BaseAdviceDetailDTO baseAdviceDetailDTO : baseAdviceDetailDTOList) {
            //只根据项目编码itemCode 和医院编码查询
            BaseAdviceDetailDTO query = new BaseAdviceDetailDTO();

            query.setItemCode(baseAdviceDetailDTO.getItemCode());

            query.setHospCode(baseAdviceDetailDTO.getHospCode());

            // 根据材料和项目的code编码，查询出所有此code的医嘱详细数据（每个code在一个医嘱内不允许重复）
            List<BaseAdviceDetailDTO> baseAdviceDetailDTOS = baseAdviceDetailDAO.queryBaseAdviceByItemCode(query);
            // 不为空则修改医嘱详细和医嘱
            if (!ListUtils.isEmpty(baseAdviceDetailDTOS)) {

                List<String> codeList = new ArrayList<>();
                //以key 医嘱主表的code ，value医嘱主表的改变的价格的形势存储
                Map<String, BigDecimal> priceDifferenceMap = new HashMap<>();

                for (BaseAdviceDetailDTO item : baseAdviceDetailDTOS) {
                    // 单价不为空则重新计算总金额
                    if (baseAdviceDetailDTO.getPrice()!=null){

                        codeList.add(item.getAdviceCode());
                        // 价格改变后，乘以数量后的新的总价格
                        BigDecimal num = BigDecimalUtils.nullToZero(item.getNum());
                        BigDecimal updateTotalPrice = BigDecimalUtils.multiply(baseAdviceDetailDTO.getPrice(), num);
                        // 新的总价格和以前总价格的差价
                        BigDecimal totalPrice = BigDecimalUtils.nullToZero(item.getTotalPrice());
                        BigDecimal priceDifference = BigDecimalUtils.subtract(updateTotalPrice,totalPrice);
                        // 设置医嘱详细的总价格为新的总价格
                        item.setTotalPrice(updateTotalPrice);
                        // 设置医嘱详细的单价为新的单价
                        item.setPrice(baseAdviceDetailDTO.getPrice());
                        // 将差价放入差价map
                        priceDifferenceMap.put(item.getAdviceCode(), priceDifference);
                    }
                    // 名称不为空的回写
                    if (StringUtils.isNotEmpty(baseAdviceDetailDTO.getItemName())){
                        item.setItemName(baseAdviceDetailDTO.getItemName());
                    }
                    // 规格不为空的回写
                    if (StringUtils.isNotEmpty(baseAdviceDetailDTO.getSpec())){
                        item.setSpec(baseAdviceDetailDTO.getSpec());
                    }
                    // 用药性质不为空则回写
                    if (StringUtils.isNotEmpty(baseAdviceDetailDTO.getUseCode())){
                        item.setUseCode(baseAdviceDetailDTO.getUseCode());
                    }
                    // 单位不为空则回写
                    if (StringUtils.isNotEmpty(baseAdviceDetailDTO.getUnitCode())){
                        item.setUnitCode(baseAdviceDetailDTO.getUnitCode());
                    }

                }

                List<BaseAdviceDTO> baseAdviceDTOList = baseAdviceDAO.queryByCodes(codeList,baseAdviceDetailDTO.getHospCode());

                if(!ListUtils.isEmpty(baseAdviceDTOList)){

                    for (BaseAdviceDTO baseAdviceDTO : baseAdviceDTOList) {
                        //通过code拿到差价map里医嘱详细改变后的差价，然后与医嘱价格相加，得到改变后的价格
                        BigDecimal price = BigDecimalUtils.nullToZero(baseAdviceDTO.getPrice());
                        BigDecimal add = BigDecimalUtils.add(price, MapUtils.get(priceDifferenceMap, baseAdviceDTO.getCode()));
                        // 设置价格
                        baseAdviceDTO.setPrice(add);

                    }
                    baseAdviceDAO.updateList(baseAdviceDTOList);
                }
                baseAdviceDetailDAO.update(baseAdviceDetailDTOS);

//                cacheOperate(null,baseAdviceDTOList,true);
            }
        }
        return true;
    }

    /**
     * @Method: getBaseAdvices
     * @Description: 根据条件获取医嘱目录
     * @Param: [inptAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/14 17:28
     * @Return: java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
     **/
    @Override
    public List<BaseAdviceDTO> getBaseAdvices(List<String> adviceIds, String hospCode, String visitId) {
        if(ListUtils.isEmpty(adviceIds)) {
            throw new AppException("医嘱ID为空");
        }
        if(StringUtils.isEmpty(visitId)) {
            throw new AppException("住院就诊ID为空");
        }
        return baseAdviceDAO.getBaseAdvices(adviceIds, hospCode, visitId);
    }

    /**
    * @Method queryItemAndMaterialAndDrugPage
    * @Param [baseAdviceDetailDTO]
    * @description   查询和项目和材料和药品三合一表
    * @author marong
    * @date 2020/9/29 15:46
    * @return cn.hsa.base.PageDTO
    * @throws
    */
    @Override
    public PageDTO queryItemAndMaterialAndDrugPage(BaseAdviceDetailDTO baseAdviceDetailDTO) {
        // 设置分页信息
        PageHelper.startPage(baseAdviceDetailDTO.getPageNo(), baseAdviceDetailDTO.getPageSize());
        List<BaseAdviceDetailDTO> baseAdviceDetailDTOS = baseAdviceDetailDAO.queryItemAndMaterialAndDrugPage(baseAdviceDetailDTO);
        return PageDTO.of(baseAdviceDetailDTOS);
    }

    private void isExitSameAdviceDetail(List<BaseAdviceDetailDTO> baseAdviceDetailDTOList) {
        // 通过项目或材料编码将其分组，来判断有无重复的项目
        try {
            int collect = baseAdviceDetailDTOList.stream()
                    .collect(Collectors.groupingBy(a -> a.getItemCode(), Collectors.counting()))
                    .entrySet().stream().filter(entry -> entry.getValue() > 1).map(entry -> entry.getKey())
                    .collect(Collectors.toList()).size();
            if (collect > 0) {
                throw new AppException("操作失败，医嘱项目中存在重复项目，请检查！");
            }
        } catch (Exception e) {
            throw new AppException("项目编码为空，联系管理员处理！");
        }
    }

    @Override
    public BaseAdviceDTO getBaseAdviceByCode(BaseAdviceDTO baseAdviceDTO) {
        if(baseAdviceDTO == null) {
            throw new AppException("参数为空");
        }
        if(StringUtils.isEmpty(baseAdviceDTO.getCode())) {
            throw new AppException("医嘱目录编码为空");
        }
        return baseAdviceDAO.baseAdviceDTO(baseAdviceDTO);
    }

    /**
    * @Method queryOperationNameList
    * @Param [baseAdviceDTO]
    * @description   获取手术医嘱
    * @author marong
    * @date 2020/12/1 9:11
    * @return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
    * @throws
    */
    @Override
    public List<BaseAdviceDTO> queryOperationNameList(BaseAdviceDTO baseAdviceDTO) {
        List<BaseAdviceDTO> baseAdviceDAOS = baseAdviceDAO.queryOperationNameList(baseAdviceDTO);
        return baseAdviceDAOS;
    }

    /**
    * @Menthod generateAdviceByItem
    * @Desrciption  根据项目生成医嘱
     * @param map
    * @Author xingyu.xie
    * @Date   2020/12/8 9:56
    * @Return java.lang.String
    **/
    @Override
    public List<BaseItemDTO> insertGenerateAdviceByItem(Map<String, Object> map){
      // 拿取必要参数
      String hospCode = MapUtils.get(map,"hospCode");
      List<BaseItemDTO> baseItemDTOList = MapUtils.get(map,"baseItemDTOList");
      String userName = MapUtils.get(map,"userName");
      String userId = MapUtils.get(map,"userId");
      BaseAdviceDTO baseAdviceDTO1 = MapUtils.get(map,"baseAdviceDTO");
      // 是否检查医嘱中存在项目
//      Boolean checkFlag = MapUtils.get(map, "checkFlag");
      // 定义变量
      // 新生成的医嘱主表列表
      List<BaseAdviceDTO> baseAdviceDTOList = new ArrayList<>();
      // 已存在医嘱的项目列表
      List<BaseItemDTO> baseItemIsExist = new ArrayList<>();
      // 新生成的医嘱附表列表
      List<BaseAdviceDetailDTO> baseAdviceDetailDTOList = new ArrayList<>();
      //修改生成状态列表
      List<String> codeList = new ArrayList<>();
      // 具体业务操作
      for (BaseItemDTO item :baseItemDTOList){
          BaseAdviceDTO baseAdviceDTO = new BaseAdviceDTO();
          BaseAdviceDetailDTO baseAdviceDetailDTO = new BaseAdviceDetailDTO();
          // 项目名
          baseAdviceDTO.setName(item.getName());
          // 项目名
          baseAdviceDTO.setOtherName(item.getOtherName());
          //医院编码
          baseAdviceDTO.setHospCode(hospCode);
          // 项目编码（查询哪些医嘱含有这个项目用）
          baseAdviceDTO.setItemCode(item.getCode());
          /*需求更改，无需提示*/
//          List<BaseAdviceDTO> existItemInAdvice = baseAdviceDAO.isExistItemInAdvice(item);
//          if (!ListUtils.isEmpty(existItemInAdvice) && checkFlag ){
//              item.setAdviceDTOList(existItemInAdvice);
//              baseItemIsExist.add(item);
//          }else {
              // 根据单据规则生成医嘱编码
          Map mapCode = new HashMap();
          mapCode.put("hospCode", baseAdviceDTO.getHospCode());
          mapCode.put("typeCode", Constants.ORDERRULE.YZ);
          String orderNo = baseOrderRuleService.getOrderNo(mapCode).getData();//根据规则生成编码
          // 项目价格
          item.setPrice(BigDecimalUtils.nullToZero(item.getPrice()));

          baseAdviceDTO.setId(SnowflakeUtils.getId());// 生成id
          baseAdviceDTO.setCode(orderNo);//设置编码
          baseAdviceDTO.setPrice(item.getPrice());//价格
          baseAdviceDTO.setUnitCode(item.getUnitCode());//单位
          baseAdviceDTO.setIsCost(Constants.SF.S);//是否计费
          baseAdviceDTO.setIsStopMyself(Constants.SF.F);//是否停自己
          baseAdviceDTO.setIsStopSame(Constants.SF.F);//是否停同类
          baseAdviceDTO.setIsStopSameNot(Constants.SF.F);//是否停非同类
          baseAdviceDTO.setWbm(WuBiUtils.getWBCode(item.getName()));// 五笔码
          baseAdviceDTO.setPym(PinYinUtils.toFirstPY(item.getName()));// 拼音码
          baseAdviceDTO.setIsValid(Constants.SF.S);// 有效
          baseAdviceDTO.setCrteName(userName);// 创建人
          baseAdviceDTO.setCrteId(userId); //创建人id
          baseAdviceDTO.setCrteTime(DateUtils.getNow()); //创建时间
          baseAdviceDTO.setUnionNationCode(item.getNationCode());//医嘱联合国家编码
          baseAdviceDTO.setUnionNationName(item.getNationName());//医嘱联合国家编码名称
          baseAdviceDTO.setTypeCode(baseAdviceDTO1.getTypeCode());//医嘱类别
          if(StringUtils.isNotEmpty(baseAdviceDTO1.getTypeCode()) &&
                  (Constants.YZLB.YZLB3.equals(baseAdviceDTO1.getTypeCode())||Constants.YZLB.YZLB12.equals(baseAdviceDTO1.getTypeCode()))){
              baseAdviceDTO.setTechnologyCode(baseAdviceDTO1.getTechnologyCode());//医技类别
          }
          baseAdviceDTOList.add(baseAdviceDTO);//加入新增列表


          baseAdviceDetailDTO.setId(SnowflakeUtils.getId());//id
          baseAdviceDetailDTO.setAdviceCode(orderNo);//医嘱编码
          baseAdviceDetailDTO.setItemName(item.getName());//项目名
          baseAdviceDetailDTO.setHospCode(hospCode);//医院编码
          baseAdviceDetailDTO.setNum(BigDecimal.ONE);// 数量
          baseAdviceDetailDTO.setTypeCode(Constants.XMLB.XM);// 项目类别  1.药品 2.材料 3.项目
          baseAdviceDetailDTO.setItemCode(item.getCode());// 项目编码
          baseAdviceDetailDTO.setUnitCode(item.getUnitCode());// 单位
          baseAdviceDetailDTO.setPrice(item.getPrice());// 价格
          baseAdviceDetailDTO.setTotalPrice(item.getPrice());// 总价格
          baseAdviceDetailDTO.setSpec(item.getSpec());//规格
          baseAdviceDetailDTO.setIsAloneCost(Constants.SF.F);//是否数量独立计费
          baseAdviceDetailDTO.setIsAppointRate(Constants.SF.F);//是否指定频率
          baseAdviceDetailDTO.setIsFristCost(Constants.SF.F);// 是否仅首次计费
          baseAdviceDetailDTOList.add(baseAdviceDetailDTO); // 加入新增列表
          codeList.add(item.getCode());// 项目编码
//          }
      }
        if (!ListUtils.isEmpty(baseAdviceDTOList) && ListUtils.isEmpty(baseItemIsExist) ){
            baseAdviceDAO.insertList(baseAdviceDTOList);
            //缓存操作
//            cacheOperate(null,baseAdviceDTOList,true);
        }
        if (!ListUtils.isEmpty(baseAdviceDetailDTOList) && ListUtils.isEmpty(baseItemIsExist) ){
            baseAdviceDetailDAO.insert(baseAdviceDetailDTOList);
            /*修改生成状态*/
            this.updateGenerateStutas(codeList,hospCode,Constants.SF.S);
            //缓存操作
//            cacheDetailOperate(baseAdviceDetailDTOList,hospCode,true);
        }

      return baseItemIsExist;
    }
    /**公共方法，修改生成状态
     * code 需要修改的项目编码
     * key 需要修改的生成状态
     * */
    public void updateGenerateStutas(List<String> code,String hospCode,String key){
        if(ListUtils.isEmpty(code)){
            return;
        }
        switch (key){
            case Constants.SF.S:
                baseAdviceDetailDAO.updateGenerateStutas(code,hospCode,key);
                break;
            case Constants.SF.F:
                List<String> list = baseAdviceDetailDAO.queryBaseAdviceDetailByItemCode(code, hospCode);
                code.removeAll(list);
                if(!ListUtils.isEmpty(code)){
                    baseAdviceDetailDAO.updateGenerateStutas(code,hospCode,key);
                }
                break;
            default:
                break;
        }
    }
    /**
     * @Method getOperationNamePage
     * @Param [baseAdviceDTO]
     * @description   查询手术医嘱分页数据展示
     * @author Mr.Liao
     * @date 2020/12/18 14:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    @Override
    public PageDTO getOperationNamePage(BaseAdviceDTO baseAdviceDTO) {
        PageHelper.startPage(baseAdviceDTO.getPageNo(), baseAdviceDTO.getPageSize());
        return  PageDTO.of(baseAdviceDAO.getOperationNamePage(baseAdviceDTO));

    }

    /**
     * @param baseAdviceDTO
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     * @Method queryDetailByAdviceCode
     * @Param [baseAdviceDTO]
     * @description 根据CODE获取医嘱目录明细
     * @author pengbo
     * @date 2021/04/01 14:38
     */
    @Override
    public PageDTO queryDetailByAdviceCode(BaseAdviceDTO baseAdviceDTO) {
        PageHelper.startPage(baseAdviceDTO.getPageNo(), baseAdviceDTO.getPageSize());
        return  PageDTO.of(baseAdviceDetailDAO.queryDetailByAdviceCode(baseAdviceDTO));
    }

    /**合管条码打印查询
    * @Method queryPipePrintPage
    * @Desrciption
    * @param paramMap  queryType 值为mz查询门诊数据，zy查询住院数据,其余的查询全院数据
    * @Author   liuqi1,luonianxin
    * @Date   2021/4/25 11:37
    * @Return java.util.Map
    **/
    @Override
    public PageDTO queryPipePrintPage(Map<String,Object> paramMap) {
        int pageNo = Integer.parseInt(MapUtils.get(paramMap, "pageNo")==null?"1":MapUtils.get(paramMap, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(paramMap, "pageSize")==null?"10":MapUtils.get(paramMap, "pageSize"));
        // 设置分页信息
        PageHelper.startPage(pageNo, pageSize);

        List<Map<String, Object>> list = null;
        String queryType = MapUtils.get(paramMap, "queryType");
        List<String> documentStatusList = getStatusListNeed2Query(MapUtils.get(paramMap, "isValid"));
        paramMap.put("statList",documentStatusList);
        list = baseAdviceDAO.queryPtPipePrintPage(paramMap);

        return PageDTO.of(list);
    }

    /**
     *  根据作废状态 查询单据类型
     * @param isValid
     * @return 医技申请单据状态
     */
    private List<String> getStatusListNeed2Query(String isValid){
        List<String> list = new ArrayList<>(8);
        // 作废状态 --- 退费单据
        if(Constants.SF.S.equals(isValid)){
            list.add(Constants.SQDZT.Refund_Waiting_For_Response);
            list.add(Constants.SQDZT.Refund_Received);
            return list;
        }
        // 非作废状态 --- 非退费单据
        if(Constants.SF.F.equals(isValid)){
            list.add(Constants.SQDZT.Prescription_Submitted);
            list.add(Constants.SQDZT.Settlement_To_Be_Sent);
            list.add(Constants.SQDZT.Settlement_Sent);
            list.add(Constants.SQDZT.Charge_Completed);
            list.add(Constants.SQDZT.Charge_Registered);
            return list;
        }
        list.add(Constants.SQDZT.Prescription_Submitted);
        list.add(Constants.SQDZT.Settlement_To_Be_Sent);
        list.add(Constants.SQDZT.Settlement_Sent);
        list.add(Constants.SQDZT.Charge_Completed);
        list.add(Constants.SQDZT.Charge_Registered);
        list.add(Constants.SQDZT.Refund_Waiting_For_Response);
        list.add(Constants.SQDZT.Refund_Received);
        return list;
    }
    /**合管条码打印更新
     * @Method updateWithPipePrint
     * @Desrciption
     * @param paramMap
     * @Author liuqi1
     * @Date   2021/4/26 11:54
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateWithPipePrint(Map<String, Object> paramMap) {
        paramMap.put("printTime", DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_DH_M_S));

        int count = baseAdviceDAO.updateWithPipePrint(paramMap);
        return count>0;
    }

    /**
     * @Menthod: 取消合管
     * @Desrciption: updateCancelMerge
     * @Param: paramMap：{
     *     mergeIds：合管主ids
     * }
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-01-06 11:47
     * @Return:
     **/
    @Override
    public Boolean updateCancelMerge(Map<String, Object> paramMap) {
        List<String> mergeIds = MapUtils.get(paramMap, "mergeIds");
        if (ListUtils.isEmpty(mergeIds)) {
            throw new RuntimeException("未选择需要取消合管的数据");
        }
        // 根据合管ids查询出医技申请集合
        List<MedicalApplyDTO> medicalApplyDTOS = baseAdviceDAO.queryMedicApplyByMergeIds(paramMap);
        if (ListUtils.isEmpty(medicalApplyDTOS)) {
            throw new RuntimeException("未查询到相关数据");
        }
        String msg = "";
        Map map = new HashMap();
        map.put("hospCode", MapUtils.get(paramMap, "hospCode"));
        map.put("typeCode", "49");
        for (MedicalApplyDTO medicalApplyDTO : medicalApplyDTOS) {
            if (medicalApplyDTO.getPrintTime() != null || Integer.parseInt(medicalApplyDTO.getPrintTimes() == null ? "0" : medicalApplyDTO.getPrintTimes()) > 0) {
                msg += medicalApplyDTO.getContent()+ ",";
            }
            medicalApplyDTO.setIsMerge(Constants.SF.F);
            medicalApplyDTO.setMergeId(medicalApplyDTO.getId());
            medicalApplyDTO.setBarCode(baseOrderRuleService.getOrderNo(map).getData());
        }
        if (StringUtils.isNotEmpty(msg)) {
            msg = msg.substring(0, msg.length()-1);
            throw new RuntimeException("【" + msg + "】已进行合管打印，不可取消合管");
        }
        return baseAdviceDAO.updateCancelMerge(medicalApplyDTOS) > 0;
    }

    /**
     * @Menthod: updateMergePipePrint
     * @Desrciption: 合管打印
     * @Param: paramMap：{ mergeIds：合管主ids }
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-01-06 17:11
     * @Return:
     **/
    @Override
    public List<MedicalApplyDTO> updateMergePipePrint(Map<String, Object> paramMap) {
        List<String> mergeIds = MapUtils.get(paramMap, "mergeIds");
        if (ListUtils.isEmpty(mergeIds)) {
            throw new RuntimeException("未选择需要合管打印的数据");
        }
        // 根据合管ids查询出医技申请集合
        List<MedicalApplyDTO> medicalApplyDTOS = baseAdviceDAO.queryMedicApplyByMergeIds(paramMap);
        if (ListUtils.isEmpty(medicalApplyDTOS)) {
            throw new RuntimeException("未查询到相关数据");
        }

        // 入库操作打印的数据
        List<MedicalApplyDTO> medicalApplyDTOList = new ArrayList<>();
        // 医技_容器_标本不为空的医技集合，用于分组区分是否能合管
        List<MedicalApplyDTO> mergeList = new ArrayList<>();
        // 是否存在已合管打印的提示语
        String isMergeMsg = "";
        // 所有医技类型组合，格式：就诊id_医技_容器_标本
        List<String> yjlxStrList = new ArrayList<>();
        //
        Map<String, List<MedicalApplyDTO>> yjlxStrMap = new HashMap<>();

        for (MedicalApplyDTO medicalApplyDTO : medicalApplyDTOS) {
            if (medicalApplyDTO.getPrintTime() != null || Integer.parseInt(medicalApplyDTO.getPrintTimes() == null ? "0" : medicalApplyDTO.getPrintTimes()) > 0) {
                isMergeMsg += medicalApplyDTO.getContent()+ ",";
            }
            // lis医技、医技类型不为空、容器类型不为空、标本类型不为空
            if (StringUtils.isNotEmpty(medicalApplyDTO.getTypeCode())
                    && Constants.CFLB.LIS.equals(medicalApplyDTO.getTypeCode())
                    && StringUtils.isNotEmpty(medicalApplyDTO.getTechnologyCode())
                    && StringUtils.isNotEmpty(medicalApplyDTO.getContainerCode())
                    && StringUtils.isNotEmpty(medicalApplyDTO.getSpecimenCode())) {
                mergeList.add(medicalApplyDTO);
                String str = medicalApplyDTO.getVisitId() + "_" + medicalApplyDTO.getTechnologyCode() + "_" + medicalApplyDTO.getContainerCode() + "_" + medicalApplyDTO.getSpecimenCode();
                if (!yjlxStrList.contains(str)) {
                    yjlxStrList.add(str);
                }
            } else {
                // 不合管
                medicalApplyDTOList.add(medicalApplyDTO);
            }
        }
        if (StringUtils.isNotEmpty(isMergeMsg)) {
            isMergeMsg = isMergeMsg.substring(0, isMergeMsg.length()-1);
            throw new RuntimeException("【" + isMergeMsg + "】已进行合管打印，不可进行合管打印");
        }

        // 合管的数据处理
        if (!ListUtils.isEmpty(mergeList)) {
            yjlxStrMap = mergeList.stream().collect(Collectors.groupingBy(medicalApplyDTO -> medicalApplyDTO.getVisitId() + "_" + medicalApplyDTO.getTechnologyCode() + "_" + medicalApplyDTO.getContainerCode() + "_" + medicalApplyDTO.getSpecimenCode()));
        }
        if (!ListUtils.isEmpty(yjlxStrList)) {
            for (String yjlxStr : yjlxStrList) {
                List<MedicalApplyDTO> applyDTOList = yjlxStrMap.get(yjlxStr);
                if (!ListUtils.isEmpty(applyDTOList)) {
                    for (MedicalApplyDTO medicalApplyDTO : applyDTOList) {
                        medicalApplyDTO.setMergeId(applyDTOList.get(0).getId());
                        medicalApplyDTO.setBarCode(applyDTOList.get(0).getBarCode());
                        medicalApplyDTO.setIsMerge(Constants.SF.S);
                        medicalApplyDTOList.add(medicalApplyDTO);
                    }
                }
            }
        }
        // 更新合管数据
        baseAdviceDAO.updateCancelMerge(medicalApplyDTOList);

        // 合管后的mergeIdList
        List<String> mergeIdList = medicalApplyDTOList.stream().map(MedicalApplyDTO::getMergeId).distinct().collect(Collectors.toList());
        paramMap.put("mergeIds", mergeIdList);
        paramMap.put("ids", mergeIdList);

        // 更新打印时间、打印次数
        paramMap.put("printTime", DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_DH_M_S));
        baseAdviceDAO.updateWithPipePrint(paramMap);

        // 返回前台合管打印后的数据，用于展示
        paramMap.put("isGroupBy", Constants.SF.S);
        List<MedicalApplyDTO> result = baseAdviceDAO.queryMedicApplyByMergeIds(paramMap);
        return result;
    }


    public void cacheOperate(BaseAdviceDTO baseAdviceDTO,List<BaseAdviceDTO>baseAdviceDTOS, Boolean operation){
        if (baseAdviceDTO != null) {
            String key = StringUtils.createKey("advice", baseAdviceDTO.getHospCode(), baseAdviceDTO.getId());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, baseAdviceDTO);
            }
        }
        if (!ListUtils.isEmpty(baseAdviceDTOS)) {
            for (BaseAdviceDTO ba : baseAdviceDTOS) {
                String key = StringUtils.createKey("advice", ba.getHospCode(), ba.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, ba);
                }
            }
        }
    }

    public void cacheDetailOperate(List<BaseAdviceDetailDTO> baseAdviceDetailDTOS,String hospCode, Boolean operation){
        if(!ListUtils.isEmpty(baseAdviceDetailDTOS)){
            for(BaseAdviceDetailDTO badd : baseAdviceDetailDTOS){
                String key = StringUtils.createKey("adviceDetail", hospCode, badd.getId());
                if(redisUtils.hasKey(key)){
                    redisUtils.del(key);
                }
                if(operation){
                    redisUtils.set(key,badd);
                }
            }
        }
    }

}
