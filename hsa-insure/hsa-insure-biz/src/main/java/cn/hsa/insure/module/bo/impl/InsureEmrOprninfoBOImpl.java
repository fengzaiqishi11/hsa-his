package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.dao.InsureEmrOprninfoDAO;
import cn.hsa.module.insure.emr.bo.InsureEmrOprninfoBO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.insure.emr.dto.InsureEmrOprninfoDTO;
import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrOprninfoBOImpl
* @Deacription 医保电子病历上传-手术记录服务层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Component
public class InsureEmrOprninfoBOImpl extends HsafBO implements InsureEmrOprninfoBO {

     @Autowired
     private InsureEmrOprninfoDAO insureEmrOprninfoDAO;

     @Override
     public PageDTO queryInsureEmrOprninfoListByPage(InsureEmrOprninfoDTO insureEmrOprninfoDTO) {
          // 设置分页信息
          PageHelper.startPage(insureEmrOprninfoDTO.getPageNo(), insureEmrOprninfoDTO.getPageSize());
          List<InsureEmrOprninfoDTO> insureEmrOprninfoList = insureEmrOprninfoDAO.select(insureEmrOprninfoDTO);
          return PageDTO.of(insureEmrOprninfoList);
     }

     @Override
     public boolean saveInsureEmrOprninfo(InsureEmrOprninfoDTO insureEmrOprninfoDTO) {
          int insert = insureEmrOprninfoDAO.insert(insureEmrOprninfoDTO);
          return insert > 0;
     }

     @Override
     public boolean updateInsureEmrOprninfo(Map map) {
          int insert = insureEmrOprninfoDAO.updateSelective(map);
          return insert > 0;
     }

     @Override
     public boolean deleteInsureEmrOprninfoByIds(InsureEmrOprninfoDTO insureEmrOprninfoDTO) {
          int insert = insureEmrOprninfoDAO.delete(insureEmrOprninfoDTO);
          return insert > 0;
     }

     @Override
     public InsureEmrOprninfoDTO queryInsureEmrOprninfoById(InsureEmrOprninfoDTO insureEmrOprninfoDTO) {
          return insureEmrOprninfoDAO.queryByUuid(insureEmrOprninfoDTO.getUuid());
     }

}
