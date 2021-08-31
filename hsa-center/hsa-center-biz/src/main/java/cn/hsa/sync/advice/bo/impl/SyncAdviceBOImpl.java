package cn.hsa.sync.advice.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.advice.bo.SyncAdviceBO;
import cn.hsa.module.sync.advice.dao.SyncAdviceDAO;
import cn.hsa.module.sync.advice.dao.SyncAdviceDetailDAO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDetailDTO;
import cn.hsa.module.sync.synccode.service.SyncCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.sync.ba.bo.impl
 * @Class_name: SyncAdviceBOImpl
 * @Describe: 医嘱信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class SyncAdviceBOImpl extends HsafBO implements SyncAdviceBO {

    /**
     * 注入医嘱dao层对象
     */
    @Resource
    SyncAdviceDAO syncAdviceDAO;

    /**
     * 注入医嘱详细dao层对象
     */
    @Resource
    SyncAdviceDetailDAO syncAdviceDetailDAO;

    /**
     * 注入单据生成service层
     */
    @Resource
    SyncOrderRuleService syncOrderRuleService;

    @Resource
    SyncCodeService syncCodeService;


    /**
     * @Menthod getById
     * @Desrciption 根据主键Id等参数查询医嘱信息
     * @param syncAdviceDTO  主键ID 等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 15:41
     * @Return cn.hsa.module.sync.bmm.dto.SyncMaterialDTO
     **/
    @Override
    public SyncAdviceDTO getById(SyncAdviceDTO syncAdviceDTO) {
        return this.syncAdviceDAO.getById(syncAdviceDTO);
    }

    /**
     * @Menthod queryItemsByAdviceCode
     * @Desrciption  根据医嘱的code医嘱编码 查询出此医嘱的医嘱详细的数据
     * @param syncAdviceDTO
     * @Author xingyu.xie
     * @Date   2020/8/6 13:53
     * @Return cn.hsa.sync.PageDTO
     **/
    @Override
    public PageDTO queryItemsByAdviceCode(SyncAdviceDTO syncAdviceDTO) {
        // 设置分页信息
        PageHelper.startPage(syncAdviceDTO.getPageNo(),syncAdviceDTO.getPageSize());

        //查询所有  通过医嘱编码
        List<SyncAdviceDetailDTO> syncAdviceDetailDTOS = syncAdviceDetailDAO.queryItemByAdviceCode(syncAdviceDTO);

        //返回分页结果
        return PageDTO.of(syncAdviceDetailDTOS);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部医嘱信息
     * @param syncAdviceDTO 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 21:04
     * @Return java.util.List<cn.hsa.module.sync.ba.dto.SyncAdviceDTO>
     **/
    @Override
    public List<SyncAdviceDTO> queryAll(SyncAdviceDTO syncAdviceDTO) {
        return this.syncAdviceDAO.queryPage(syncAdviceDTO);
    }

    /**
     * @param syncAdviceDetailDTO 项目编码，材料编码等参数
     * @Menthod queryAllAdviceDetail
     * @Desrciption 根据项目编码查询医嘱详细
     * @Author xingyu.xie
     * @Date 2020/7/14 16:05
     * @Return cn.hsa.module.sync.bfc.dto.SyncAdviceDTO
     **/
    public List<SyncAdviceDetailDTO> queryAllAdviceDetail(SyncAdviceDetailDTO syncAdviceDetailDTO) {
        return syncAdviceDetailDAO.queryAll(syncAdviceDetailDTO);
    }

    /**
     * @Menthod queryPage
     * @Desrciption   根据数据对象分页查询医嘱信息
     * @param syncAdviceDTO 医嘱信息数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:42
     * @Return cn.hsa.sync.PageDTO
     **/
    @Override
    public PageDTO queryPage(SyncAdviceDTO syncAdviceDTO) {

        if(!StringUtils.isEmpty(syncAdviceDTO.getTypeCode())){
            HashMap map = new HashMap();
            map.put("code","YZLB");
            List<TreeMenuNode> treeMenuNodeList = syncCodeService.getCodeData(map).getData();
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    syncAdviceDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            syncAdviceDTO.setIds(list);
        }
        syncAdviceDTO.setTypeCode("");

        // 设置分页信息
        PageHelper.startPage(syncAdviceDTO.getPageNo(),syncAdviceDTO.getPageSize());
        List<SyncAdviceDTO> syncAdviceDTOList = syncAdviceDAO.queryPage(syncAdviceDTO);
        return  PageDTO.of(syncAdviceDTOList);
    }

    /**
     * @Menthod insert
     * @Desrciption   新增单条医嘱信息和多条医嘱详细信息
     * @param syncAdviceDTO 医嘱信息数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:42
     * @Return boolean
     **/
    @Override
    public boolean insert(SyncAdviceDTO syncAdviceDTO) {
        syncAdviceDTO.setId(SnowflakeUtils.getId());
        syncAdviceDTO.setCrteTime(DateUtils.getNow());
        // 根据单据规则生成医嘱编码
        String orderNo = syncOrderRuleService.getOrderNo(Constants.ORDERRULE.YZ).getData();
        syncAdviceDTO.setCode(orderNo);

        //name生成拼音码和五笔码
        if (!StringUtils.isEmpty(syncAdviceDTO.getName())){

            syncAdviceDTO.setPym(PinYinUtils.toFirstPY(syncAdviceDTO.getName()));
            syncAdviceDTO.setWbm(WuBiUtils.getWBCode(syncAdviceDTO.getName()));
        }
        //医嘱主表的总金额
        BigDecimal totalPriceAdvice = BigDecimal.valueOf(0);
        //判断要插入医嘱明细的列表不为空
        List<SyncAdviceDetailDTO> syncAdviceDetailDTOList = syncAdviceDTO.getSyncAdviceDetailDTOList();

        if (!ListUtils.isEmpty(syncAdviceDetailDTOList)){

            // 通过项目编码itemCode将其分组，来判断有无重复的项目
            isExitSameAdviceDetail(syncAdviceDetailDTOList);

            // 循环插入
            for (SyncAdviceDetailDTO item : syncAdviceDetailDTOList) {
                item.setId(SnowflakeUtils.getId());
                //根据数量和单价计算总金额
                BigDecimal totalPrice = BigDecimalUtils.multiply(item.getPrice(), item.getNum());

                item.setTotalPrice(totalPrice);

                totalPriceAdvice = BigDecimalUtils.add(totalPriceAdvice,totalPrice);

                item.setAdviceCode(syncAdviceDTO.getCode());
            }
            syncAdviceDetailDAO.insert(syncAdviceDetailDTOList);
        }
        syncAdviceDTO.setPrice(totalPriceAdvice);
        return this.syncAdviceDAO.insert(syncAdviceDTO)>0;
    }

    /**
     * @Menthod update
     * @Desrciption   修改单条医嘱信息和多条医嘱详细信息
     * @param syncAdviceDTO 医嘱信息数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:43
     * @Return boolean
     **/
    @Override
    public boolean update(SyncAdviceDTO syncAdviceDTO) {
        //要修改或者插入的列信息（医嘱详细）
        List<SyncAdviceDetailDTO> syncAdviceDetailDTOList = syncAdviceDTO.getSyncAdviceDetailDTOList();

        //name生成拼音码和五笔码
        if (!StringUtils.isEmpty(syncAdviceDTO.getName())){

            syncAdviceDTO.setPym(PinYinUtils.toFirstPY(syncAdviceDTO.getName()));
            syncAdviceDTO.setWbm(WuBiUtils.getWBCode(syncAdviceDTO.getName()));
        }

        //医嘱主表的总金额
        BigDecimal totalPriceAdvice = BigDecimal.valueOf(0);

        if (!ListUtils.isEmpty(syncAdviceDetailDTOList)){

            // 通过项目编码itemCode将其分组，来判断有无重复的项目
            isExitSameAdviceDetail(syncAdviceDetailDTOList);

            List<SyncAdviceDetailDTO> insertList = new ArrayList<>();

            List<SyncAdviceDetailDTO> updateList = new ArrayList<>();

            for (SyncAdviceDetailDTO item : syncAdviceDetailDTOList){

                //根据数量和单价计算总金额
                BigDecimal totalPrice = BigDecimalUtils.multiply(item.getPrice(), item.getNum());

                item.setTotalPrice(totalPrice);

                totalPriceAdvice = BigDecimalUtils.add(totalPriceAdvice,totalPrice);

                //没有id则是要插入的数据
                if (StringUtils.isEmpty(item.getId())){

                    item.setId(SnowflakeUtils.getId());

                    item.setAdviceCode(syncAdviceDTO.getCode());

                    insertList.add(item);
                    //有id就是要修改的数据
                }else {
                    updateList.add(item);
                }
            }

            if (!ListUtils.isEmpty(insertList)){
                syncAdviceDetailDAO.insert(insertList);
            }
            if (!ListUtils.isEmpty(updateList)){
                syncAdviceDetailDAO.update(updateList);
            }
        }

        // 要删除的数据(医嘱详细)
        if (!ListUtils.isEmpty(syncAdviceDTO.getIds())) {
            // 根据删除详细表的ids查出所有要删除的详细数据，从总价中减去
            SyncAdviceDetailDTO syncAdviceDetailDTO = new SyncAdviceDetailDTO();
            syncAdviceDetailDTO.setIds(syncAdviceDTO.getIds());
            // 删除的详细数据
            List<SyncAdviceDetailDTO> syncAdviceDetailDTOS = syncAdviceDetailDAO.queryPage(syncAdviceDetailDTO);
            syncAdviceDetailDAO.delete(syncAdviceDTO);
            for (SyncAdviceDetailDTO item: syncAdviceDetailDTOS){
                totalPriceAdvice = BigDecimalUtils.subtract(totalPriceAdvice,item.getTotalPrice());
            }
        }

        //修改医嘱信息
        syncAdviceDTO.setPym(PinYinUtils.toFirstPY(syncAdviceDTO.getName()));
        syncAdviceDTO.setWbm(WuBiUtils.getWBCode(syncAdviceDTO.getName()));
        return this.syncAdviceDAO.update(syncAdviceDTO)>0;
    }

    /**
     * @Menthod updateStatus
     * @Desrciption   根据主键ID等参数，删除一个或多个医嘱信息
     * @param syncAdviceDTO
     * @Author xingyu.xie
     * @Date   2020/7/14 15:43
     * @Return boolean
     **/
    @Override
    public boolean updateStatus(SyncAdviceDTO syncAdviceDTO) {
        return this.syncAdviceDAO.updateStatus(syncAdviceDTO)>0;
    }

    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption   将项目表和材料表的数据一起查出来，并转换为医嘱详细的数据传输对象
     * @param syncAdviceDetailDTO 医嘱详细数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/6 13:48
     * @Return cn.hsa.sync.PageDTO
     **/
    @Override
    public PageDTO queryItemAndMaterialPage(SyncAdviceDetailDTO syncAdviceDetailDTO) {
        // 设置分页信息
        PageHelper.startPage(syncAdviceDetailDTO.getPageNo(),syncAdviceDetailDTO.getPageSize());
        List<SyncAdviceDetailDTO> syncAdviceDetailDTOS = syncAdviceDetailDAO.queryItemAndMaterialPage(syncAdviceDetailDTO);
        return  PageDTO.of(syncAdviceDetailDTOS);
    }


    /**
     * @param syncAdviceDetailDTOList 项目或材料的
     * @Menthod updateSyncAdviceAndSyncAdviceDetailsByItemCode
     * @Desrciption 更新材料或项目的单价或者
     * 必填：1.医院编码（hospCode） 2.项目或材料编码（itemCode）
     * 选填：1.单价（price） 2.名称（itemName） 3.单位代码（unitCode） 4.规格（spec） 5.用药性质（useCode）
     * @Author xingyu.xie
     * @Date 2020/9/4 14:41
     * @Return boolean
     **/
    @Override
    public boolean updateSyncAdviceAndSyncAdviceDetailsByItemCode(List<SyncAdviceDetailDTO> syncAdviceDetailDTOList) {

        if (ListUtils.isEmpty(syncAdviceDetailDTOList)) {
            throw new AppException("回写的项目或材料不能为空！");
        }

        for (SyncAdviceDetailDTO syncAdviceDetailDTO : syncAdviceDetailDTOList) {
            //只根据项目编码itemCode 和医院编码查询
            SyncAdviceDetailDTO query = new SyncAdviceDetailDTO();

            query.setItemCode(syncAdviceDetailDTO.getItemCode());

            // 根据材料和项目的code编码，查询出所有此code的医嘱详细数据（每个code在一个医嘱内不允许重复）
            List<SyncAdviceDetailDTO> syncAdviceDetailDTOS = syncAdviceDetailDAO.queryPage(query);
            // 不为空则修改医嘱详细和医嘱
            if (!ListUtils.isEmpty(syncAdviceDetailDTOS)) {

                List<String> codeList = new ArrayList<>();
                //以key 医嘱主表的code ，value医嘱主表的改变的价格的形势存储
                Map<String, BigDecimal> priceDifferenceMap = new HashMap<>();

                for (SyncAdviceDetailDTO item : syncAdviceDetailDTOS) {
                    // 单价不为空则重新计算总金额
                    if (syncAdviceDetailDTO.getPrice()!=null){

                        codeList.add(item.getAdviceCode());
                        // 价格改变后，乘以数量后的新的总价格
                        BigDecimal num = BigDecimalUtils.nullToZero(item.getNum());
                        BigDecimal updateTotalPrice = BigDecimalUtils.multiply(syncAdviceDetailDTO.getPrice(), num);
                        // 新的总价格和以前总价格的差价
                        BigDecimal totalPrice = BigDecimalUtils.nullToZero(item.getTotalPrice());
                        BigDecimal priceDifference = BigDecimalUtils.subtract(updateTotalPrice,totalPrice);
                        // 设置医嘱详细的总价格为新的总价格
                        item.setTotalPrice(updateTotalPrice);
                        // 设置医嘱详细的单价为新的单价
                        item.setPrice(syncAdviceDetailDTO.getPrice());
                        // 将差价放入差价map
                        priceDifferenceMap.put(item.getAdviceCode(), priceDifference);
                    }
                    // 名称不为空的回写
                    if (StringUtils.isNotEmpty(syncAdviceDetailDTO.getItemName())){
                        item.setItemName(syncAdviceDetailDTO.getItemName());
                    }
                    // 规格不为空的回写
                    if (StringUtils.isNotEmpty(syncAdviceDetailDTO.getSpec())){
                        item.setSpec(syncAdviceDetailDTO.getSpec());
                    }
                    // 用药性质不为空则回写
                    if (StringUtils.isNotEmpty(syncAdviceDetailDTO.getUseCode())){
                        item.setUseCode(syncAdviceDetailDTO.getUseCode());
                    }
                    // 单位不为空则回写
                    if (StringUtils.isNotEmpty(syncAdviceDetailDTO.getUnitCode())){
                        item.setUnitCode(syncAdviceDetailDTO.getUnitCode());
                    }

                }

                List<SyncAdviceDTO> syncAdviceDTOList = syncAdviceDAO.queryByCodes(codeList);

                if(!ListUtils.isEmpty(syncAdviceDTOList)){

                    for (SyncAdviceDTO syncAdviceDTO : syncAdviceDTOList) {
                        //通过code拿到差价map里医嘱详细改变后的差价，然后与医嘱价格相加，得到改变后的价格
                        BigDecimal price = BigDecimalUtils.nullToZero(syncAdviceDTO.getPrice());
                        BigDecimal add = BigDecimalUtils.add(price, MapUtils.get(priceDifferenceMap, syncAdviceDTO.getCode()));
                        // 设置价格
                        syncAdviceDTO.setPrice(add);

                    }

                }

                syncAdviceDetailDAO.update(syncAdviceDetailDTOS);

                syncAdviceDAO.updateList(syncAdviceDTOList);
            }
        }
        return true;
    }

    private void isExitSameAdviceDetail(List<SyncAdviceDetailDTO> syncAdviceDetailDTOList){
        // 通过医嘱编码AdviceCode将其分组，来判断有无重复的项目
        try {
            int collect = syncAdviceDetailDTOList.stream()
                    .collect(Collectors.groupingBy(a -> a.getItemCode(), Collectors.counting()))
                    .entrySet().stream().filter(entry -> entry.getValue() > 1).map(entry -> entry.getKey())
                    .collect(Collectors.toList()).size();
            if (collect>0){
                throw new AppException("操作失败，医嘱项目中存在重复项目，请检查！");
            }
        }catch (Exception e){
            throw new AppException("项目编码为空，联系管理员处理！");
        }
    }
}
