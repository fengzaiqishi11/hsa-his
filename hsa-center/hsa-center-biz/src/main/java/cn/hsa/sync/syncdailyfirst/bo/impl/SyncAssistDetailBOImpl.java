package cn.hsa.sync.syncdailyfirst.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.syncassistdetail.bo.SyncAssistDetailBO;
import cn.hsa.module.center.syncassistdetail.dao.SyncAssistDetailDAO;
import cn.hsa.module.center.syncassistdetail.dto.SyncAssistDetailDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public class SyncAssistDetailBOImpl extends HsafBO implements SyncAssistDetailBO {

    @Resource
    private SyncAssistDetailDAO syncAssistDetailDAO;


    @Override
    public SyncAssistDetailDTO getById(Long id) {
        return syncAssistDetailDAO.getById(id);
    }

    @Override
    public List<SyncAssistDetailDTO> queryAll(SyncAssistDetailDTO syncAssistDetailDTO) {
        syncAssistDetailDTO.setIsValid("1");
        return syncAssistDetailDAO.queryAll(syncAssistDetailDTO);
    }

    @Override
    public boolean insert(List<SyncAssistDetailDTO> syncAssistDetailDTO) {
        List<SyncAssistDetailDTO> SyncAssistDetailDTOinserts = new ArrayList<>();
        List<SyncAssistDetailDTO> SyncAssistDetailDTOupdates = new ArrayList<>();
        for (int i = 0; i < syncAssistDetailDTO.size(); i++) {
            if (StringUtils.isEmpty(syncAssistDetailDTO.get(i).getId())) {
                syncAssistDetailDTO.get(i).setId(SnowflakeUtils.getId());
                syncAssistDetailDTO.get(i).setCrteTime(DateUtils.getNow());
                syncAssistDetailDTO.get(i).setIsValid("1");
                SyncAssistDetailDTO baseDailyfirst = new SyncAssistDetailDTO();
                baseDailyfirst = syncAssistDetailDTO.get(i);
                SyncAssistDetailDTOinserts.add(baseDailyfirst);
            } else {
                SyncAssistDetailDTO baseDailyfirst = new SyncAssistDetailDTO();
                baseDailyfirst = syncAssistDetailDTO.get(i);
                SyncAssistDetailDTOupdates.add(baseDailyfirst);
            }
        }
        if (!ListUtils.isEmpty(SyncAssistDetailDTOinserts)) {
            syncAssistDetailDAO.insertList(SyncAssistDetailDTOinserts);
        }
        if (!ListUtils.isEmpty(SyncAssistDetailDTOupdates)) {
            syncAssistDetailDAO.updateBatch(SyncAssistDetailDTOupdates);
        }
        return true;

    }


    @Override
    public int update(SyncAssistDetailDTO syncAssistDetailDTO) {
        return syncAssistDetailDAO.update(syncAssistDetailDTO);
    }


    @Override
    public int deleteById(Long id) {
        return syncAssistDetailDAO.deleteById(id);
    }
    @Override
    public int deleteBylist(SyncAssistDetailDTO syncAssistDetailDTO) {
        List<String> ids =syncAssistDetailDTO.getIds();
        List<SyncAssistDetailDTO> syncAssistDetailDTOS = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            SyncAssistDetailDTO baseDailyfirstCalc = new SyncAssistDetailDTO();
            baseDailyfirstCalc.setId(ids.get(i));
            baseDailyfirstCalc.setIsValid("0");
            syncAssistDetailDTOS.add(baseDailyfirstCalc);
        }


        return syncAssistDetailDAO.deleteBylist(syncAssistDetailDTOS);
    }
    @Override
    public PageDTO queryPage(SyncAssistDetailDTO syncAssistDetailDTO) {
        syncAssistDetailDTO.setIsValid("1");
        PageHelper.startPage(syncAssistDetailDTO.getPageNo(), syncAssistDetailDTO.getPageSize());

        // 查询所有
        List<SyncAssistDetailDTO> baseBedDTOList = syncAssistDetailDAO.queryAll(syncAssistDetailDTO);

        // 返回分页结果
        return PageDTO.of(baseBedDTOList);
    }
}
