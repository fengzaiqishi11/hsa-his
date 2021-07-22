package cn.hsa.sync.item.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.advice.dto.SyncAdviceDetailDTO;
import cn.hsa.module.sync.advice.service.SyncAdviceService;
import cn.hsa.module.sync.synccode.service.SyncCodeService;
import cn.hsa.module.sync.syncitem.bo.SyncItemBO;
import cn.hsa.module.sync.syncitem.dao.SyncItemDAO;
import cn.hsa.module.sync.syncitem.dto.SyncItemDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.base.bi.bo.impl
 * @Class_name:: BaseItemBOImpl
 * @Description: 项目管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/7/14 14:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class SyncItemBOImpl extends HsafBO implements SyncItemBO {

    /**
     * 项目管理数据库访问接口
     */
    @Resource
    private SyncItemDAO syncItemDAO;

    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    @Resource
    private SyncCodeService syncCodeService;

    @Resource
    private SyncAdviceService syncAdviceService;


    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param [BaseItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/16 9:26
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    @Override
    public SyncItemDTO getById(SyncItemDTO syncItemDTO) {
        return this.syncItemDAO.getById(syncItemDTO);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询全部项目信息(默认状态为有效)
     * @Param [centerItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 14:53
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPage(SyncItemDTO syncItemDTO) {


        if(!StringUtils.isEmpty(syncItemDTO.getTypeCode())){
            HashMap map = new HashMap();
            map.put("code","XMFL");
            List<TreeMenuNode> treeMenuNodeList = syncCodeService.getCodeData(map).getData();
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    syncItemDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            syncItemDTO.setIds(list);
        }
        syncItemDTO.setTypeCode("");

        PageHelper.startPage(syncItemDTO.getPageNo(), syncItemDTO.getPageSize());
        List<SyncItemDTO> centerItemDTOList = this.syncItemDAO.queryPage(syncItemDTO);
        return PageDTO.of(centerItemDTOList);

    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有项目
     * @Param [centerItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/18 11:54
     * @Return java.util.List<cn.hsa.module.base.bi.dto.centerItemDTO>
     **/
    @Override
    public List<SyncItemDTO> queryAll(SyncItemDTO syncItemDTO) {
        return this.syncItemDAO.queryAll(syncItemDTO);
    }

    /**
     * @Method save
     * @Desrciption 增加/修改单条项目信息
     * @Param [centerItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/24 16:58
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(SyncItemDTO syncItemDTO) {
        //首先对传入的编码进行判断是否存在
        SyncItemDTO b = new SyncItemDTO();
        b.setId(syncItemDTO.getId());
        try {
            b.setNationCode(syncItemDTO.getNationCode());
            if (!StringUtils.isEmpty(b.getNationCode()) && syncItemDAO.isCodeExist(b) > 0) {
                throw new AppException("国家编码重复");
            }
        } catch (NullPointerException e) {
            throw new AppException("国家编码为空");
        }

        //拼音码五笔码自动生成
        if (!StringUtils.isEmpty(syncItemDTO.getName())) {
            //设置名称拼音码
            //设置名称五笔码
            syncItemDTO.setNamePym(PinYinUtils.toFirstPY(syncItemDTO.getName()));
            syncItemDTO.setNameWbm(WuBiUtils.getWBCode(syncItemDTO.getName()));
        }

        if (!StringUtils.isEmpty(syncItemDTO.getAbbr())) {
            //设置简称拼音码
            //设置简称五笔码
            syncItemDTO.setAbbrPym(PinYinUtils.toFirstPY(syncItemDTO.getAbbr()));
            syncItemDTO.setAbbrWbm(WuBiUtils.getWBCode(syncItemDTO.getAbbr()));
        }

        if (StringUtils.isEmpty(syncItemDTO.getId())) {
            //设置id
            syncItemDTO.setId(SnowflakeUtils.getId());
            //设置创建时间
            syncItemDTO.setCrteTime(DateUtils.getNow());
            //自动生成药品编码
            String order = syncOrderRuleService.getOrderNo("25").getData();
            syncItemDTO.setCode(order);

            return this.syncItemDAO.insert(syncItemDTO) > 0;
        } else {
            //修改
            SyncAdviceDetailDTO syncAdviceDetailDTO = new SyncAdviceDetailDTO();
            List<SyncAdviceDetailDTO> syncAdviceDetailDTOList = new ArrayList<>();
            //回写材料名称
            syncAdviceDetailDTO.setItemName(syncItemDTO.getName());
            //回写材料单价
            syncAdviceDetailDTO.setPrice(syncItemDTO.getPrice());
            //回写材料单位
            syncAdviceDetailDTO.setUnitCode(syncItemDTO.getUnitCode());
            //回写材料规格
            syncAdviceDetailDTO.setSpec(syncItemDTO.getSpec());
            // 写入材料编码
            syncAdviceDetailDTO.setItemCode(syncItemDTO.getCode());

            syncAdviceDetailDTOList.add(syncAdviceDetailDTO);
            Map map = new HashMap<>();

            map.put("syncAdviceDetailDTOList",syncAdviceDetailDTOList);
            syncAdviceService.updateSyncAdviceAndSyncAdviceDetailsByItemCode(map);
            return this.syncItemDAO.update(syncItemDTO) > 0;
        }
    }

    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     * @Param [centerItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 14:54
     * @Return Boolean
     **/
    @Override
    public Boolean updateStatus(SyncItemDTO syncItemDTO) {
        if (!ListUtils.isEmpty(syncItemDTO.getIds())) {
            if (syncItemDTO.getIsValid().equals("0")) {
                syncItemDTO.setId(syncItemDTO.getIds().get(0));
                if (this.syncItemDAO.getById(syncItemDTO).getAdviceFlag() > 0) {
                    throw new AppException("医嘱中使用了该项目，无法作废");
                }
            }
        } else {
            throw new AppException("操作失败");
        }
        return this.syncItemDAO.updateStatus(syncItemDTO) > 0;
    }
}
