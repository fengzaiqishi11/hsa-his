package cn.hsa.sync.disease.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sync.synccode.service.SyncCodeService;
import cn.hsa.module.sync.syncdisease.bo.SyncDiseaseBO;
import cn.hsa.module.sync.syncdisease.dao.SyncDiseaseDAO;
import cn.hsa.module.sync.syncdisease.dto.SyncDiseaseDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.disease.bo.impl
 * @Class_name:: CenterDiseaseBOImpl
 * @Description: 疾病管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/8/6 8:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class SyncDiseaseBOImpl extends HsafBO implements SyncDiseaseBO {

    /**
     * 疾病管理数据库访问接口
     */
    @Resource
    private SyncDiseaseDAO syncDiseaseDAO;

    @Resource
    SyncCodeService syncCodeService;

    /**
     * @Method getById
     * @Desrciption 通过id获取疾病信息
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 9:27
     * @Return cn.hsa.module.base.bd.dto.BaseDiseaseDTO
     **/
    @Override
    public SyncDiseaseDTO getById(SyncDiseaseDTO syncDiseaseDTO) {
        return this.syncDiseaseDAO.getById(syncDiseaseDTO);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询全部疾病信息(默认状态为有效)
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPage(SyncDiseaseDTO syncDiseaseDTO) {
        if(!StringUtils.isEmpty(syncDiseaseDTO.getTypeCode())){
            HashMap map = new HashMap();
            map.put("code","JBFL");
            List<TreeMenuNode> treeMenuNodeList = syncCodeService.getCodeData(map).getData();
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    syncDiseaseDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            syncDiseaseDTO.setIds(list);
        }
        syncDiseaseDTO.setTypeCode("");

        PageHelper.startPage(syncDiseaseDTO.getPageNo(), syncDiseaseDTO.getPageSize());
        List<SyncDiseaseDTO> centerDiseaseDTOList = this.syncDiseaseDAO.queryPage(syncDiseaseDTO);
        return PageDTO.of(centerDiseaseDTOList);
    }

    @Override
    public List<SyncDiseaseDTO> queryAll(SyncDiseaseDTO syncDiseaseDTO) {
        return this.syncDiseaseDAO.queryAll(syncDiseaseDTO);
    }

    /**
     * @Method save
     * @Desrciption 增加/修改单条疾病信息
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/24 16:58
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(SyncDiseaseDTO syncDiseaseDTO) {
        //判断疾病编码、附加编码、国家编码是否已经存在
        SyncDiseaseDTO b = new SyncDiseaseDTO();
        b.setId(syncDiseaseDTO.getId());
        try{
            b.setCode(syncDiseaseDTO.getCode());
            if(!StringUtils.isEmpty(b.getCode())&& syncDiseaseDAO.isCodeExist(b)>0){
                throw new AppException("疾病编码重复");
            }
        }catch (NullPointerException e){
            throw new AppException("疾病编码为空");
        }
        try{
            b.setCode("");
            b.setNationCode(syncDiseaseDTO.getNationCode());
            if(!StringUtils.isEmpty(b.getNationCode())&& syncDiseaseDAO.isCodeExist(b)>0){
                throw new AppException("国家编码重复");
            }
        }catch (NullPointerException e){
            throw new AppException("国家编码为空");
        }
        try{
            b.setNationCode("");
            b.setAttachCode(syncDiseaseDTO.getAttachCode());
            if(!StringUtils.isEmpty(b.getAttachCode())&& syncDiseaseDAO.isCodeExist(b)>0){
                throw new AppException("附加编码重复");
            }
        }catch (NullPointerException e){
            throw new AppException("附加编码为空");
        }

        //拼音码五笔码自动生成
        if (!StringUtils.isEmpty(syncDiseaseDTO.getName())){
            syncDiseaseDTO.setPym(PinYinUtils.toFirstPY(syncDiseaseDTO.getName()));
            syncDiseaseDTO.setWbm(WuBiUtils.getWBCode(syncDiseaseDTO.getName()));
        }

        if(StringUtils.isEmpty(syncDiseaseDTO.getId())){
            //设置id
            syncDiseaseDTO.setId(SnowflakeUtils.getId());
            //设置创建时间
            syncDiseaseDTO.setCrteTime(DateUtils.getNow());
            return this.syncDiseaseDAO.insert(syncDiseaseDTO)>0;
        }else{
            //修改
            return this.syncDiseaseDAO.update(syncDiseaseDTO)>0;
        }
    }

    /**
     * @Method updateStatus()
     * @Description 修改用户状态
     *
     * @Param
     * [syncDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 9:03
     * @Return Boolean
     **/
    @Override
    public Boolean updateStatus(SyncDiseaseDTO syncDiseaseDTO) {
        return this.syncDiseaseDAO.updateStatus(syncDiseaseDTO)>0;
    }
}
