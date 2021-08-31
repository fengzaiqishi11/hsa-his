package cn.hsa.sync.drug.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.synccode.service.SyncCodeService;
import cn.hsa.module.sync.syncdrug.bo.SyncDrugBO;
import cn.hsa.module.sync.syncdrug.dao.SyncDrugDAO;
import cn.hsa.module.sync.syncdrug.dto.SyncDrugDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.drug.bo.impl
 * @Class_name:: CenterDrugBOImpl
 * @Description: 项目管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/8/6 8:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class SyncDrugBOImpl extends HsafBO implements SyncDrugBO {

    /**
     * 药品管理数据库访问接口
     */
    @Resource
    private SyncDrugDAO syncDrugDAO;

    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    @Resource
    SyncCodeService syncCodeService;


    /**
     * @Method getById
     * @Desrciption
     * @Param
     * [centerDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/6 8:53
     * @Return cn.hsa.module.center.drug.dto.CenterDrugDTO
     **/
    @Override
    public SyncDrugDTO getById(SyncDrugDTO centerDrugDTO) {
        return this.syncDrugDAO.getById(centerDrugDTO);
    }

    /**
     * @Method queryPage
     * @Desrciption
     * @Param
     * [centerDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/6 8:53
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(SyncDrugDTO centerDrugDTO) {

        if(!StringUtils.isEmpty(centerDrugDTO.getTypeCode())){
            HashMap map = new HashMap();
            map.put("code","YPFL");
            List<TreeMenuNode> treeMenuNodeList = syncCodeService.getCodeData(map).getData();
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    centerDrugDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            centerDrugDTO.setIds(list);
        }
        centerDrugDTO.setTypeCode("");


        //设置分页信息
        PageHelper.startPage(centerDrugDTO.getPageNo(), centerDrugDTO.getPageSize());
        List<SyncDrugDTO> centerDrugDTOList = this.syncDrugDAO.queryPage(centerDrugDTO);
        return PageDTO.of(centerDrugDTOList);

    }

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [centerDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/6 8:54
     * @Return java.util.List<cn.hsa.module.center.drug.dto.CenterDrugDTO>
     **/
    @Override
    public List<SyncDrugDTO> queryAll(SyncDrugDTO centerDrugDTO) {
        return this.syncDrugDAO.queryAll(centerDrugDTO);
    }

    /**
     * @Method save
     * @Desrciption
     * @Param
     * [centerDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/6 8:54
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(SyncDrugDTO syncDrugDTO) {

        if(!StringUtils.isEmpty(syncDrugDTO.getUsualName())) {
            //设置通用名拼音码
            //设置通用名五笔码
            syncDrugDTO.setUsualPym(PinYinUtils.toFirstPY(syncDrugDTO.getUsualName()));
            syncDrugDTO.setUsualWbm(WuBiUtils.getWBCode(syncDrugDTO.getUsualName()));
        }
        if(!StringUtils.isEmpty(syncDrugDTO.getGoodName())) {
            //设置商品名拼音码
            //设置商品名五笔码
            syncDrugDTO.setGoodPym(PinYinUtils.toFirstPY(syncDrugDTO.getGoodName()));
            syncDrugDTO.setGoodWbm(WuBiUtils.getWBCode(syncDrugDTO.getGoodName()));
        }

        //  增加药品大类：0 西药，1 成药，2 草药
        if (StringUtils.isNotEmpty(syncDrugDTO.getTypeCode())) {
            syncDrugDTO.setBigTypeCode(syncDrugDTO.getTypeCode().substring(0, 1));
        }

        //计算拆零单价
        if(syncDrugDTO.getSplitRatio() != null && syncDrugDTO.getPrice() != null){
            if(BigDecimalUtils.greater(syncDrugDTO.getSplitRatio(),new BigDecimal(0))){
                BigDecimal divide = BigDecimalUtils.divide(syncDrugDTO.getPrice(), syncDrugDTO.getSplitRatio());
                syncDrugDTO.setSplitPrice(divide);
            }else{
                throw new AppException("拆分比设置错误");
            }
        }

        if(StringUtils.isEmpty(syncDrugDTO.getId())){
            //设置id
            syncDrugDTO.setId(SnowflakeUtils.getId());
            //设置创建时间
            syncDrugDTO.setCrteTime(DateUtils.getNow());
            //设置药品编码
            String order = syncOrderRuleService.getOrderNo("24").getData();
            syncDrugDTO.setCode(order);


            return this.syncDrugDAO.insert(syncDrugDTO)>0;
        }else{
            //修改
            return this.syncDrugDAO.update(syncDrugDTO)>0;
        }
    }


    /**
     * @Method updateStatus
     * @Desrciption
     * @Param
     * [syncDrugDTO]
     * @Author liaojunjie
     * @Date   2020/8/6 8:54
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateStatus(SyncDrugDTO syncDrugDTO) {
        return this.syncDrugDAO.updateStatus(syncDrugDTO)>0;
    }
}
