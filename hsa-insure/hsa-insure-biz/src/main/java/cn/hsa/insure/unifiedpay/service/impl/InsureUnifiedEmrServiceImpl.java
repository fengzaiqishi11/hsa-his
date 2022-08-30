package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.bo.InsureUnifiedEmrBO;
import cn.hsa.module.insure.emr.service.InsureUnifiedEmrService;
import lombok.extern.slf4j.Slf4j;

import cn.hsa.module.insure.emr.dto.*;
import cn.hsa.module.insure.emr.service.InsureUnifiedEmrService;
import cn.hsa.util.MapUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InsureUnifiedEmrServiceImpl
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/3/25 13:37
 * @Version 1.0
 **/


@Slf4j
@HsafRestPath("/service/insure/insureUnifiedEmr")
@Service("insureUnifiedEmrService_provider")
public class InsureUnifiedEmrServiceImpl implements InsureUnifiedEmrService {

    @Resource
    private InsureUnifiedEmrBO insureUnifiedEmrBO;


    @Override
    public WrapperResponse<PageDTO> queryInsureUnifiedEmrInfo(Map<String, Object> map) {
        // 查询 联合 结算表 主，关联 医保就诊表，电子病历入院记录表
        return WrapperResponse.success(insureUnifiedEmrBO.queryInsureUnifiedEmrInfo(MapUtils.get(map,"insureEmrUnifiedDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrDetailDTO> queryInsureUnifiedEmrDetail(Map<String, Object> map) {
        // 根据 his就诊id，医保登记id，人员编号
        // 查询 患者基本展示信息
        // 查询 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        return WrapperResponse.success(insureUnifiedEmrBO.queryInsureUnifiedEmrDetail(MapUtils.get(map,"insureEmrUnifiedDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrAdminfoDTO> updateInsureUnifiedEmrAdminfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrAdminfo(MapUtils.get(map,"insureEmrAdminfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrDiseinfoDTO> updateInsureUnifiedEmrDiseinfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrDiseinfo(MapUtils.get(map,"insureEmrDiseinfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrCoursrinfoDTO> updateInsureUnifiedEmrCoursrinfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrCoursrinfo(MapUtils.get(map,"insureEmrCoursrinfoDTO")));
    }

    @Override
    public WrapperResponse updateInsureUnifiedEmrOprninfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrOprninfo(MapUtils.get(map,"insureEmrOprninfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrRescinfoDTO> updateInsureUnifiedEmrRescinfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrRescinfo(MapUtils.get(map,"insureEmrRescinfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrDieinfoDTO> updateInsureUnifiedEmrDieinfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrDieinfo(MapUtils.get(map,"insureEmrDieinfoDTO")));
    }

    @Override
    public WrapperResponse<InsureEmrDscginfoDTO> updateInsureUnifiedEmrDscginfo(Map<String, Object> map) {
        // 根据uuid 判断记录是否存在，不存在则新增，存在则修改
        return WrapperResponse.success(insureUnifiedEmrBO.updateInsureUnifiedEmrDscginfo(MapUtils.get(map,"insureEmrDscginfoDTO")));
    }

    @Override
    public void updateInsureUnifiedEmrUpload(Map<String, Object> map) {
        // 根据 his就诊id，医保登记id，人员编号
        // 查询 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        // 组装 报文 调用医保接口
        List<InsureEmrUnifiedDTO> insureEmrUnifiedDTOList = MapUtils.get(map,"insureEmrUnifiedDTOList");
        for(InsureEmrUnifiedDTO insureEmrUnifiedDTO:insureEmrUnifiedDTOList){
            insureEmrUnifiedDTO.setHospCode(MapUtils.get(map,"hospCode").toString());
            insureUnifiedEmrBO.updateInsureUnifiedEmrUpload(insureEmrUnifiedDTO);
        }
    }

    @Override
    public void updateInsureUnifiedEmrSync(Map<String, Object> map) {
        // 提供给电子病历系统，做数据初始化
        // 初始化 入院记录，诊断记录，病程记录，手术记录，抢救记录，死亡记录，出院小结
        insureUnifiedEmrBO.updateInsureUnifiedEmrSync(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public void export(HttpServletRequest req, Map<String, Object> map) {
        List<InsureEmrUnifiedDTO> list = insureUnifiedEmrBO.export(MapUtils.get(map,"insureEmrUnifiedDTO"));

    }

    @Override
    public List<InsureEmrDscginfoDTO> queryInsureUnifiedEmrDscginfo(Map map) {
        return insureUnifiedEmrBO.queryInsureUnifiedEmrDscginfo(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public List<InsureEmrDieinfoDTO> queryInsureUnifiedEmrDieinfo(Map map) {
        return insureUnifiedEmrBO.queryInsureUnifiedEmrDieinfo(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public List<InsureEmrRescinfoDTO> queryInsureUnifiedEmrRescinfo(Map map) {
        return insureUnifiedEmrBO.queryInsureUnifiedEmrRescinfo(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public List<InsureEmrOprninfoDTO> queryInsureUnifiedEmrOprninfo(Map map) {
        return insureUnifiedEmrBO.queryInsureUnifiedEmrOprninfo(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public List<InsureEmrCoursrinfoDTO> queryInsureUnifiedEmrCoursrinfo(Map map) {
        return insureUnifiedEmrBO.queryInsureUnifiedEmrCoursrinfo(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public List<InsureEmrDiseinfoDTO> queryInsureUnifiedEmrDiseinfo(Map map) {
        return insureUnifiedEmrBO.queryInsureUnifiedEmrDiseinfo(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    @Override
    public InsureEmrAdminfoDTO queryInsureUnifiedEmrAdminfo(Map map) {
        return insureUnifiedEmrBO.queryInsureUnifiedEmrAdminfo(MapUtils.get(map,"insureEmrUnifiedDTO"));
    }

    /**
     * 根据就诊ID查询入院信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 14:26
     * @return cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO
     */
    @Override
    public InsureEmrAdminfoDTO queryAdmInfoByMdtrtSn(Map paramMap) {
      String mdtrtSn = MapUtils.get(paramMap,"mdtrtSn");
      return insureUnifiedEmrBO.queryAdmInfoByMdtrtSn(mdtrtSn);
    }

    /**
     * 根据就诊ID查询首次病程信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 16:05
     * @return cn.hsa.module.insure.emr.dto.InsureEmrCoursrinfoDTO
     */
    @Override
    public InsureEmrCoursrinfoDTO queryCoursrInfoByMdtrtSn(Map paramMap) {
      String mdtrtSn = MapUtils.get(paramMap,"mdtrtSn");
      return insureUnifiedEmrBO.queryCoursrInfoByMdtrtSn(mdtrtSn);
    }

    /**
     * 查询死亡记录信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 17:20
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDieinfoDTO
     */
    @Override
    public InsureEmrDieinfoDTO queryDieInfoByMdtrtSn(Map paramMap) {
      String mdtrtSn = MapUtils.get(paramMap,"mdtrtSn");
      return insureUnifiedEmrBO.queryDieInfoByMdtrtSn(mdtrtSn);
    }

    /**
     * 查询出院信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:30
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO
     */
    @Override
    public InsureEmrDscginfoDTO queryDscgInfoByMdtrtSn(Map paramMap) {
      String mdtrtSn = MapUtils.get(paramMap,"mdtrtSn");
      return insureUnifiedEmrBO.queryDscgInfoByMdtrtSn(mdtrtSn);
    }

    /**
     * 保存电子病历手术信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 20:01
     * @return cn.hsa.module.insure.emr.dto.InsureEmrOprninfoDTO
     */
    @Override
    public InsureEmrOprninfoDTO queryOprnInfoByTemplateId(Map paramMap) {
      String emrTemplateId = MapUtils.get(paramMap,"emrTemplateId");
      return insureUnifiedEmrBO.queryOprnInfoByTemplateId(emrTemplateId);
    }

    /**
     * 根据病历编号查询抢救信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:50
     * @return cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO
     */
    @Override
    public InsureEmrRescinfoDTO queryRescInfoByTemplateId(Map paramMap) {
      String emrTemplateId = MapUtils.get(paramMap,"emrTemplateId");
      return insureUnifiedEmrBO.queryRescInfoByTemplateId(emrTemplateId);
    }

  /**
     * 根据MdtrtSn更新入院信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 14:34
     * @return int
     */
    @Override
    public int updateAdmSelectiveByMdtrtSn(Map map) {
      return insureUnifiedEmrBO.updateAdmSelectiveByMdtrtSn(map);
    }

    /**
     * 根据mdtrtsn更新首次病程信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:04
     * @return int
     */
    @Override
    public int updateCoursrSelectiveByMdtrtSn(Map map) {
      return insureUnifiedEmrBO.updateCoursrSelectiveByMdtrtSn(map);
    }

    /**
     * 根据mdtrtsn更新死亡信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:37
     * @return int
     */
    @Override
    public int updateDieSelectiveByMdtrtSn(Map map) {
      return insureUnifiedEmrBO.updateDieSelectiveByMdtrtSn(map);
    }

    /**
     *  更新出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:48
     * @return int
     */
    @Override
    public int updateDscgSelectiveByMdtrtSn(Map map) {
      return insureUnifiedEmrBO.updateDscgSelectiveByMdtrtSn(map);
    }

    /**
     * 更新手术信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 20:25
     * @return int
     */
    @Override
    public int updateOprnSelectiveByTemplateId(Map map) {
      return insureUnifiedEmrBO.updateOprnSelectiveByTemplateId(map);
    }

    @Override
    public int updateRescSelectiveByTemplateId(Map map) {
      return insureUnifiedEmrBO.updateRescSelectiveByTemplateId(map);
    }

  /**
     * 插入医保电子病历入院信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 15:03
     * @return int
     */
    @Override
    public int insertAdminfo(Map map) {
      return insureUnifiedEmrBO.insertAdminfo(map);
    }

    /**
     * 插入医保电子病历首次病程记录
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 16:23
     * @return int
     */
    @Override
    public int insertCoursrinfo(Map map) {
      return insureUnifiedEmrBO.insertCoursrinfo(map);
    }

    /**
     * 插入医保电子病历死亡记录
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 8:48
     * @return int
     */
    @Override
    public int insertDieinfo(Map map) {
      return insureUnifiedEmrBO.insertDieinfo(map);
    }

    /**
     * 插入出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:58
     * @return int
     */
    @Override
    public int insertDscgInfo(Map map) {
      return insureUnifiedEmrBO.insertDscgInfo(map);
    }

    /**
     * 插入手术信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:09
     * @return int
     */
    @Override
    public int insertOprnInfo(Map map) {
      return insureUnifiedEmrBO.insertOprnInfo(map);
    }

    @Override
    public int insertRescInfo(Map map) {
      return insureUnifiedEmrBO.insertRescInfo(map);
    }
}
