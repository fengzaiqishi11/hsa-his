package cn.hsa.sync.syncassist.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.syncassist.bo.SyncassistBO;
import cn.hsa.module.center.syncassist.dao.SyncassistDao;
import cn.hsa.module.center.syncassist.dao.SyncassistDetailDao;
import cn.hsa.module.center.syncassist.dto.SyncassistDTO;
import cn.hsa.module.center.syncassist.dto.SyncassistDetailDTO;
import cn.hsa.module.center.syncorderrule.bo.SyncOrderRuleBO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.module.base.syncassist.bo
 * @Class_name: BaseAssistCalcBOlmpl
 * @Description: 业务逻辑实现层
 * @Author: ljh
 * @Email:
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class SyncassistBOlmpl extends HsafBO implements SyncassistBO {
    @Resource
    private SyncassistDao baseAssistCalcDao;
    @Resource
    private SyncassistDetailDao baseAssistCalcDetailDao;
    @Resource
    private SyncOrderRuleBO syncOrderRuleBO;



    /**
     * @Menthod queryAll
     * @Desrciption  查询
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:47
     * @Return java.util.List<cn.hsa.module.base.syncassist.dto.SyncassistDTO>
     **/
    @Override
    public List<SyncassistDTO> queryAll(SyncassistDTO syncassistDTO) {
        return baseAssistCalcDao.queryAll(syncassistDTO);
    }

    /**
     * @Menthod insert
     * @Desrciption 新增
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:47
     * @Return int
     **/
    @Override

    public int insert(SyncassistDTO syncassistDTO) {
        syncassistDTO.setId(SnowflakeUtils.getId());
        syncassistDTO.setCrteTime(new Date());
        String code = syncOrderRuleBO.updateOrderNo("9");
        syncassistDTO.setCode(code);
        List<SyncassistDetailDTO> baseAssistCalcDetaillist = syncassistDTO.getSyncAssistCalcDetailDO();
        if(!(ListUtils.isEmpty(baseAssistCalcDetaillist ))){
        for (int i = 0; i < baseAssistCalcDetaillist.size(); i++) {
            baseAssistCalcDetaillist.get(i).setAcCode(code);
            baseAssistCalcDetaillist.get(i).setId(SnowflakeUtils.getId());
            baseAssistCalcDetaillist.get(i).setCrteTime(new Date());
        }
            baseAssistCalcDetailDao.insertList(baseAssistCalcDetaillist);
        }
        if (baseAssistCalcDao.queryNameExist(syncassistDTO) > 0) {
            throw new AppException("该计费名称已被使用" + syncassistDTO.getName());
        } else {
            return baseAssistCalcDao.insert(syncassistDTO);
        }
    }
    /**
     * @Menthod update
     * @Desrciption 更新
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:48
     * @Return int
     **/
    @Override
    public int update(SyncassistDTO syncassistDTO) {

        String code = syncassistDTO.getCode();
        baseAssistCalcDetailDao.deleteBycode(code);
        List<SyncassistDetailDTO> baseAssistCalcDetailDTOlist = syncassistDTO.getSyncAssistCalcDetailDO();
        if (!(ListUtils.isEmpty(baseAssistCalcDetailDTOlist))) {
            for (int i = 0; i < baseAssistCalcDetailDTOlist.size(); i++) {
                baseAssistCalcDetailDTOlist.get(i).setAcCode(code);
                baseAssistCalcDetailDTOlist.get(i).setId(SnowflakeUtils.getId());
                baseAssistCalcDetailDTOlist.get(i).setCrteTime(new Date());
            }}
        baseAssistCalcDetailDao.insertList(baseAssistCalcDetailDTOlist);

        return baseAssistCalcDao.update(syncassistDTO);
    }
    /**
     * @Menthod deleteById
     * @Desrciption 删除
     * @param id
     * @Author ljh
     * @Date   2020/8/6 10:49
     * @Return int
     **/
    @Override
    public int deleteById(Long id) {
        return baseAssistCalcDao.deleteById(id);
    }

    /**
     * @Menthod queryPage
     * @Desrciption  分页
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:49
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(SyncassistDTO syncassistDTO) {
        PageHelper.startPage(syncassistDTO.getPageNo(), syncassistDTO.getPageSize());

        // 查询所有
        List<SyncassistDTO> syncassistDTOlist = baseAssistCalcDao.queryAll(syncassistDTO);
        // 返回分页结果
        return PageDTO.of(syncassistDTOlist);
    }

    /**
     * @Method queryPage()
     * @Description 分页
     *
     * @Param
     * SyncassistDetailDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    @Override
    public PageDTO detailqueryPage(SyncassistDetailDTO baseAssistCalcDetailDTO) {
        PageHelper.startPage(baseAssistCalcDetailDTO.getPageNo(), baseAssistCalcDetailDTO.getPageSize());

        // 查询所有
        List<Map<String,Object>>  baseBedDTOList = baseAssistCalcDetailDao.queryallcode(baseAssistCalcDetailDTO);

        // 返回分页结果
        return PageDTO.of(baseBedDTOList);
    }
    /**
     * @Menthod deleteByIdlist
     * @Desrciption 删除
     * @param syncassistDTO
     * @Author ljh
     * @Date   2020/8/6 10:50
     * @Return int
     **/
    @Override
    public int updateStatus(SyncassistDTO syncassistDTO) {
        List<String> ids = syncassistDTO.getIds();
        String isvalid = syncassistDTO.getIsValid();
        List<SyncassistDTO> syncassistDTOS = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            SyncassistDTO baseAssistCalc = new SyncassistDTO();
            baseAssistCalc.setId(ids.get(i));
            baseAssistCalc.setIsValid(isvalid);
            syncassistDTOS.add(baseAssistCalc);
        }
        return baseAssistCalcDao.updateStatus(syncassistDTOS);
    }
}