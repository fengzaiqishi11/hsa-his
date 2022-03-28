package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.dao.InsureEmrDiseinfoDAO;
import cn.hsa.module.insure.emr.bo.InsureEmrDiseinfoBO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.insure.emr.dto.InsureEmrDiseinfoDTO;
import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrDiseinfoBOImpl
* @Deacription 医保电子病历上传-诊断信息服务层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Component
public class InsureEmrDiseinfoBOImpl extends HsafBO implements InsureEmrDiseinfoBO {

     @Autowired
     private InsureEmrDiseinfoDAO insureEmrDiseinfoDAO;

     @Override
     public PageDTO queryInsureEmrDiseinfoListByPage(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO) {
          // 设置分页信息
          PageHelper.startPage(insureEmrDiseinfoDTO.getPageNo(), insureEmrDiseinfoDTO.getPageSize());
          List<InsureEmrDiseinfoDTO> insureEmrDiseinfoList = insureEmrDiseinfoDAO.select(insureEmrDiseinfoDTO);
          return PageDTO.of(insureEmrDiseinfoList);
     }

     @Override
     public boolean saveInsureEmrDiseinfo(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO) {
          int insert = insureEmrDiseinfoDAO.insert(insureEmrDiseinfoDTO);
          return insert > 0;
     }

     @Override
     public boolean updateInsureEmrDiseinfo(Map map) {
          int insert = insureEmrDiseinfoDAO.updateSelective(map);
          return insert > 0;
     }

     @Override
     public boolean deleteInsureEmrDiseinfoByIds(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO) {
          int insert = insureEmrDiseinfoDAO.delete(insureEmrDiseinfoDTO);
          return insert > 0;
     }

     @Override
     public InsureEmrDiseinfoDTO queryInsureEmrDiseinfoById(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO) {
          return insureEmrDiseinfoDAO.queryById(insureEmrDiseinfoDTO.getMdtrtSn(),insureEmrDiseinfoDTO.getMdtrtId());
     }

}
