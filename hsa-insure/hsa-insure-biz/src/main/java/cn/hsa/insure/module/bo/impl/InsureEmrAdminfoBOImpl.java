package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.dao.InsureEmrAdminfoDAO;
import cn.hsa.module.insure.emr.bo.InsureEmrAdminfoBO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO;
import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrAdminfoBOImpl
* @Deacription 医保电子病历上传-入院信息服务层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Component
public class InsureEmrAdminfoBOImpl extends HsafBO implements InsureEmrAdminfoBO {

     @Autowired
     private InsureEmrAdminfoDAO insureEmrAdminfoDAO;


     @Override
     public PageDTO queryInsureEmrAdminfoListByPage(InsureEmrAdminfoDTO insureEmrAdminfoDTO) {
          // 设置分页信息
          PageHelper.startPage(insureEmrAdminfoDTO.getPageNo(), insureEmrAdminfoDTO.getPageSize());
          List<InsureEmrAdminfoDTO> InsureEmrAdminfoList = insureEmrAdminfoDAO.select(insureEmrAdminfoDTO);
          return PageDTO.of(InsureEmrAdminfoList);
     }

     @Override
     public boolean saveInsureEmrAdminfo(InsureEmrAdminfoDTO insureEmrAdminfoDTO) {
          int insert = insureEmrAdminfoDAO.insert(insureEmrAdminfoDTO);
          return insert > 0;
     }

     @Override
     public boolean updateInsureEmrAdminfo(Map map) {
          int insert = insureEmrAdminfoDAO.updateSelective(map);
          return insert > 0;
     }

     @Override
     public boolean deleteInsureEmrAdminfoByIds(InsureEmrAdminfoDTO insureEmrAdminfoDTO) {
          int insert = insureEmrAdminfoDAO.delete(insureEmrAdminfoDTO);
          return insert > 0;
     }

     @Override
     public InsureEmrAdminfoDTO queryInsureEmrAdminfoById(InsureEmrAdminfoDTO insureEmrAdminfoDTO) {
          return insureEmrAdminfoDAO.queryById(insureEmrAdminfoDTO.getMdtrtSn(),insureEmrAdminfoDTO.getMdtrtId());
     }

}
