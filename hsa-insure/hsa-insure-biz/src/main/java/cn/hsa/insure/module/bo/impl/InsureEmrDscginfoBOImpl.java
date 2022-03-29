package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.dao.InsureEmrDscginfoDAO;
import cn.hsa.module.insure.emr.bo.InsureEmrDscginfoBO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO;
import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrDscginfoBOImpl
* @Deacription 医保电子病历上传-出院记录服务层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Component
public class InsureEmrDscginfoBOImpl extends HsafBO implements InsureEmrDscginfoBO {

     @Autowired
     private InsureEmrDscginfoDAO insureEmrDscginfoDAO;

     @Override
     public PageDTO queryInsureEmrDscginfoListByPage(InsureEmrDscginfoDTO insureEmrDscginfoDTO) {
          // 设置分页信息
          PageHelper.startPage(insureEmrDscginfoDTO.getPageNo(), insureEmrDscginfoDTO.getPageSize());
          List<InsureEmrDscginfoDTO> insureEmrDscginfoList = insureEmrDscginfoDAO.select(insureEmrDscginfoDTO);
          return PageDTO.of(insureEmrDscginfoList);
     }

     @Override
     public boolean saveInsureEmrDscginfo(InsureEmrDscginfoDTO insureEmrDscginfoDTO) {
          int insert = insureEmrDscginfoDAO.insert(insureEmrDscginfoDTO);
          return insert > 0;
     }

     @Override
     public boolean updateInsureEmrDscginfo(Map map) {
          int insert = insureEmrDscginfoDAO.updateSelective(map);
          return insert > 0;
     }

     @Override
     public boolean deleteInsureEmrDscginfoByIds(InsureEmrDscginfoDTO insureEmrDscginfoDTO) {
          int insert = insureEmrDscginfoDAO.delete(insureEmrDscginfoDTO);
          return insert > 0;
     }

     @Override
     public InsureEmrDscginfoDTO queryInsureEmrDscginfoById(InsureEmrDscginfoDTO insureEmrDscginfoDTO) {
          return insureEmrDscginfoDAO.queryByUuid(insureEmrDscginfoDTO.getUuid());
     }

}
