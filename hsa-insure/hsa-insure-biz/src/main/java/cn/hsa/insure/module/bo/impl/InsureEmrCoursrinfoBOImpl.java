package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.dao.InsureEmrCoursrinfoDAO;
import cn.hsa.module.insure.emr.bo.InsureEmrCoursrinfoBO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.insure.emr.dto.InsureEmrCoursrinfoDTO;
import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrCoursrinfoBOImpl
* @Deacription 医保电子病历上传-病程记录服务层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Component
public class InsureEmrCoursrinfoBOImpl extends HsafBO implements InsureEmrCoursrinfoBO {

     @Autowired
     private InsureEmrCoursrinfoDAO insureEmrCoursrinfoDAO;


     @Override
     public PageDTO queryInsureEmrCoursrinfoListByPage(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO) {
          // 设置分页信息
          PageHelper.startPage(insureEmrCoursrinfoDTO.getPageNo(), insureEmrCoursrinfoDTO.getPageSize());
          List<InsureEmrCoursrinfoDTO> insureEmrCoursrinfoList = insureEmrCoursrinfoDAO.select(insureEmrCoursrinfoDTO);
          return PageDTO.of(insureEmrCoursrinfoList);
     }

     @Override
     public boolean saveInsureEmrCoursrinfo(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO) {
          int insert = insureEmrCoursrinfoDAO.insert(insureEmrCoursrinfoDTO);
          return insert > 0;
     }

     @Override
     public boolean updateInsureEmrCoursrinfo(Map map) {
          int insert = insureEmrCoursrinfoDAO.updateSelective(map);
          return insert > 0;
     }

     @Override
     public boolean deleteInsureEmrCoursrinfoByIds(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO) {
          int insert = insureEmrCoursrinfoDAO.delete(insureEmrCoursrinfoDTO);
          return insert > 0;
     }

     @Override
     public InsureEmrCoursrinfoDTO queryInsureEmrCoursrinfoById(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO) {
          return insureEmrCoursrinfoDAO.queryByUuid(insureEmrCoursrinfoDTO.getUuid());
     }

}
