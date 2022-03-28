package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.emr.dao.InsureEmrDieinfoDAO;
import cn.hsa.module.insure.emr.bo.InsureEmrDieinfoBO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.insure.emr.dto.InsureEmrDieinfoDTO;
import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrDieinfoBOImpl
* @Deacription 医保电子病历上传-死亡记录服务层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
@Component
public class InsureEmrDieinfoBOImpl extends HsafBO implements InsureEmrDieinfoBO {

     @Autowired
     private InsureEmrDieinfoDAO insureEmrDieinfoDAO;

     @Override
     public PageDTO queryInsureEmrDieinfoListByPage(InsureEmrDieinfoDTO insureEmrDieinfoDTO) {
          // 设置分页信息
          PageHelper.startPage(insureEmrDieinfoDTO.getPageNo(), insureEmrDieinfoDTO.getPageSize());
          List<InsureEmrDieinfoDTO> insureEmrDieinfoList = insureEmrDieinfoDAO.select(insureEmrDieinfoDTO);
          return PageDTO.of(insureEmrDieinfoList);
     }

     @Override
     public boolean saveInsureEmrDieinfo(InsureEmrDieinfoDTO insureEmrDieinfoDTO) {
          int insert = insureEmrDieinfoDAO.insert(insureEmrDieinfoDTO);
          return insert > 0;
     }

     @Override
     public boolean updateInsureEmrDieinfo(Map map) {
          int insert = insureEmrDieinfoDAO.updateSelective(map);
          return insert > 0;
     }

     @Override
     public boolean deleteInsureEmrDieinfoByIds(InsureEmrDieinfoDTO insureEmrDieinfoDTO) {
          int insert = insureEmrDieinfoDAO.delete(insureEmrDieinfoDTO);
          return insert > 0;
     }

     @Override
     public InsureEmrDieinfoDTO queryInsureEmrDieinfoById(InsureEmrDieinfoDTO insureEmrDieinfoDTO) {
          return insureEmrDieinfoDAO.queryById(insureEmrDieinfoDTO);
     }

}
