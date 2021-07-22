package cn.hsa.sync.syncsystem.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sync.syncsystem.bo.SyncSystemBO;
import cn.hsa.module.sync.syncsystem.dao.SyncSystemDAO;
import cn.hsa.module.sync.syncsystem.dto.SyncSystemDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class SyncSystemBOImpl extends HsafBO implements SyncSystemBO {

    @Resource
    private SyncSystemDAO syncSystemDAO;

    @Override
    public List<SyncSystemDTO> queryAll(SyncSystemDTO syncSystemDTO) {
        return syncSystemDAO.queryAll(syncSystemDTO);
    }

    @Override
    public PageDTO queryPage(SyncSystemDTO syncSystemDTO) {
        // 设置分页信息
        PageHelper.startPage(syncSystemDTO.getPageNo(), syncSystemDTO.getPageSize());

        // 查询所有
        List<SyncSystemDTO> list = syncSystemDAO.queryPage(syncSystemDTO);

        // 返回分页结果
        return PageDTO.of(list);
    }

    @Override
    public Boolean save(SyncSystemDTO syncSystemDTO) {
        //根据编码查询数据,校验编码不能重复
        if(syncSystemDAO.getCount(syncSystemDTO) > 0){
            throw new AppException("【"+syncSystemDTO.getCode() + "】系统编码已存在！");
        }
        //根据编码查询数据,校验编码不能重复
        if(syncSystemDAO.getCountByName(syncSystemDTO) > 0){
            throw new AppException("【"+syncSystemDTO.getName() + "】系统名称已存在！");
        }

        //处理拼音码
        if(!StringUtils.isEmpty(syncSystemDTO.getName())){
            syncSystemDTO.setPym(PinYinUtils.toFirstPY(syncSystemDTO.getName()));
        }
        //处理五笔码
        if(!StringUtils.isEmpty(syncSystemDTO.getName())){
            syncSystemDTO.setWbm(WuBiUtils.getWBCode(syncSystemDTO.getName()));
        }

        try {
            if(StringUtils.isEmpty(syncSystemDTO.getId()) && ListUtils.isEmpty(syncSystemDTO.getIds())){//新增
                syncSystemDTO.setId(SnowflakeUtils.getId());
                syncSystemDTO.setIsValid("1");
                syncSystemDAO.insert(syncSystemDTO);
            } else if(!ListUtils.isEmpty(syncSystemDTO.getIds())){//删除
                syncSystemDAO.updateSyncSystemByIsvalid(syncSystemDTO);
            } else {//修改
                syncSystemDAO.update(syncSystemDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public SyncSystemDTO getById(SyncSystemDTO syncSystemDTO) {
        return syncSystemDAO.getById(syncSystemDTO);
    }

    @Override
    public Integer querySystemSeqNo(SyncSystemDTO syncSystemDTO) {
        return syncSystemDAO.querySystemSeqNo(syncSystemDTO);
    }
}
