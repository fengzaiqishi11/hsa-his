package cn.hsa.interf.lis.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.lis.bo.LisPacsSyncBO;
import cn.hsa.module.interf.lis.dao.LisPacsSyncDAO;
import cn.hsa.util.MapUtils;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Lis/Pacs系统同步BO实现类
 * @author liudawen
 * @date 2022/9/1
 */
@Slf4j
@Component
public class LisPacsSyncBOImpl extends HsafBO implements LisPacsSyncBO {

    @Resource
    private LisPacsSyncDAO lisPacsSyncDAO;

    /*============================Pacs影像检查BO业务逻辑部分==========================================*/
    /**
     * 获取门诊pacs检查申请单，并修改其申请单状态为已发送
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<java.util.Map>
     * @Throws
     * @Date 2022/9/1 10:22
     **/
    @Override
    public List<Map> updateOutptPacsApply(Map map) {
        // 查询document_sta 申请状态为已结算的门诊pacs影像检查申请单信息
        List<Map> outptPacsApplyList = lisPacsSyncDAO.listOutptPacsApply(map);
        // 如果不为空则根据申请单id更新申请状态为已发送状态
        if(!CollectionUtils.isEmpty(outptPacsApplyList)){
            // 已发送申请单
            String hospCode = MapUtil.getStr(map, "hospCode");
            updateApplyDocumentSta(outptPacsApplyList,hospCode,"05");
        }
        return outptPacsApplyList;
    }

    /**
     * 获取住院pacs检查申请单，并修改其申请单状态为已发送
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<java.util.Map>
     * @Throws
     * @Date 2022/9/1 10:23
     **/
    @Override
    public List<Map> updateInptPacsApply(Map map) {
        // 查询document_sta 申请状态为已结算的住院pacs影像检查申请单信息
        List<Map> inptPacsApplyList = lisPacsSyncDAO.listInptPacsApply(map);
        // 如果不为空则根据申请单id更新申请状态为已发送状态
        if(!CollectionUtils.isEmpty(inptPacsApplyList)){
            // 已发送申请单
            String hospCode = MapUtil.getStr(map, "hospCode");
            updateApplyDocumentSta(inptPacsApplyList,hospCode,"05");
        }
        return inptPacsApplyList;
    }

    /**
     * 保存pacs影像检查结果，并修改其申请单状态为已接收结果
     * @Author liudawen
     * @Param [map]
     * @Return java.lang.Boolean
     * @Throws
     * @Date 2022/9/1 11:15
     **/
    @Override
    public Boolean savePacsResult(Map map) {
        String hospCode = MapUtil.getStr(map, "hospCode");
        List<Map> pacsResult = MapUtils.get(map, "pacsResult");
        Assert.notEmpty(pacsResult ,()-> new AppException("pacs影像检查结果为空"));
        updateApplyDocumentSta(pacsResult,hospCode,"08");
        return lisPacsSyncDAO.insertPacsResult(pacsResult) > 0;
    }

    /**
     * 修改申请单的状态
     * @Author liudawen
     * @Param [mapList, hospCode, documentSta]
     * @Return java.lang.Boolean
     * @Throws
     * @Date 2022/9/5 9:12
     **/
    public Boolean updateApplyDocumentSta(List<Map> mapList,String hospCode,String documentSta){
        return lisPacsSyncDAO.updateApplyDocumentSta(mapList, hospCode,documentSta) > 0;
    }
    /*============================LIS检验BO业务逻辑部分==========================================*/

    /**
     * 获取科室字典信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<java.util.Map>
     * @Throws
     * @Date 2022/9/6 15:40
     **/
    @Override
    public List<Map> getLisDept(Map map) {
        return lisPacsSyncDAO.listLisDept(map);
    }

    /**
     * 获取医生字典信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<java.util.Map>
     * @Throws
     * @Date 2022/9/6 15:40
     **/
    @Override
    public List<Map> getLisDoctor(Map map) {
        return lisPacsSyncDAO.listLisDoctor(map);
    }

    /**
     * 获取收费项目字典信息
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<java.util.Map>
     * @Throws
     * @Date 2022/9/6 15:40
     **/
    @Override
    public List<Map> getLisAdvice(Map map) {
        return lisPacsSyncDAO.listLisAdvice(map);
    }

    /**
     * 获取lis检验申请单信息，并修改其申请单状态为已发送
     * @Author liudawen
     * @Param [map]
     * @Return java.util.List<java.util.Map>
     * @Throws
     * @Date 2022/9/6 15:40
     **/
    @Override
    public List<Map> updateLisApply(Map map) {
        List<Map> lisApply = lisPacsSyncDAO.listLisApply(map);
        // 如果不为空则根据申请单id更新申请状态为已发送状态
        if(!CollectionUtils.isEmpty(lisApply)){
            // 已发送申请单
            String hospCode = MapUtil.getStr(map, "hospCode");
            updateApplyDocumentSta(lisApply,hospCode,"05");
        }
        return lisApply;
    }

    /**
     * 保存lis检验结果，并修改其申请单状态为已接收结果
     * @Author liudawen
     * @Param [map]
     * @Return java.lang.Boolean
     * @Throws
     * @Date 2022/9/6 15:40
     **/
    @Override
    public Boolean saveLisResult(Map map) {
        String hospCode = MapUtil.getStr(map, "hospCode");
        List<Map> lisResult = MapUtils.get(map, "hisLisResult");
        Assert.notEmpty(lisResult ,()-> new AppException("lis检验结果为空"));
        updateApplyDocumentSta(lisResult,hospCode,"08");
        return lisPacsSyncDAO.insertLisResult(lisResult) > 0;
    }
}
