package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.dao.InsureEmrRescinfoDAO;
import cn.hsa.module.insure.emr.bo.InsureEmrRescinfoBO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO;
import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrRescinfoBOImpl
* @Deacription 医保电子病历上传-病情抢救记录服务层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Component
public class InsureEmrRescinfoBOImpl extends HsafBO implements InsureEmrRescinfoBO {

     @Autowired
     private InsureEmrRescinfoDAO insureEmrRescinfoDAO;

     @Override
     public PageDTO queryInsureEmrRescinfoListByPage(InsureEmrRescinfoDTO insureEmrRescinfoDTO) {
          // 设置分页信息
          PageHelper.startPage(insureEmrRescinfoDTO.getPageNo(), insureEmrRescinfoDTO.getPageSize());
          List<InsureEmrRescinfoDTO> insureEmrRescinfoList = insureEmrRescinfoDAO.select(insureEmrRescinfoDTO);
          return PageDTO.of(insureEmrRescinfoList);
     }

     @Override
     public boolean saveInsureEmrRescinfo(InsureEmrRescinfoDTO insureEmrRescinfoDTO) {
          int insert = insureEmrRescinfoDAO.insert(insureEmrRescinfoDTO);
          return insert > 0;
     }

     @Override
     public boolean updateInsureEmrRescinfo(Map map) {
          int insert = insureEmrRescinfoDAO.updateSelective(map);
          return insert > 0;
     }

     @Override
     public boolean deleteInsureEmrRescinfoByIds(InsureEmrRescinfoDTO insureEmrRescinfoDTO) {
          int insert = insureEmrRescinfoDAO.delete(insureEmrRescinfoDTO);
          return insert > 0;
     }

     @Override
     public InsureEmrRescinfoDTO queryInsureEmrRescinfoById(InsureEmrRescinfoDTO insureEmrRescinfoDTO) {
          return insureEmrRescinfoDAO.queryByUuid(insureEmrRescinfoDTO.getUuid());
     }

}
