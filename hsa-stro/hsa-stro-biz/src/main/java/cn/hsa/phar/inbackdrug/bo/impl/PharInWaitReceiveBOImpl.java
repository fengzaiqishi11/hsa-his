package cn.hsa.phar.inbackdrug.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.phar.pharinbackdrug.bo.PharInWaitReceiveBO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInReceiveDAO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInReceiveDetailDAO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInWaitReceiveDAO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.phar.inbackdrug.bo.impl
 *@Class_name: PharInWaitReceiveBOImpl
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 19:24
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class PharInWaitReceiveBOImpl extends HsafBO implements PharInWaitReceiveBO {

    @Resource
    PharInWaitReceiveDAO pharInWaitReceiveDao;
    @Resource
    PharInReceiveDAO pharInReceiveDAO;
    @Resource
    PharInReceiveDetailDAO pharInReceiveDetailDAO;

    /**
    * @Method insertPharInWaitBatch
    * @Desrciption 住院待领批量新增
    * @param pharInWaitReceiveDTOs
    * @Author liuqi1
    * @Date   2020/9/3 19:26
    * @Return int
    **/
    @Override
    public Boolean insertPharInWaitBatch(List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs) {
        int count = pharInWaitReceiveDao.insertPharInWaitReceiveBatch(pharInWaitReceiveDTOs);
        return count>0;
    }

    /**
    * @Method queryPharInWaitReceive
    * @Desrciption 根据条件查询出待领信息集合
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/9/7 13:47
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    @Override
    public List<PharInWaitReceiveDTO> queryPharInWaitReceive(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryPharInWaitReceive(pharInWaitReceiveDTO);
        return pharInWaitReceiveDTOS;
    }

    @Override
    public List<PharInWaitReceiveDTO> queryPharInWaitReceives(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryPharInWaitReceivess(pharInWaitReceiveDTO);
        return pharInWaitReceiveDTOS;
    }

    @Override
    public List<PharInWaitReceiveDTO> queryPharInWaitReceiveToIsBack(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryPharInWaitReceiveToIsBack(pharInWaitReceiveDTO);
        return pharInWaitReceiveDTOS;
    }

    /**
    * @Menthod queryPharInWaitReceivePage
    * @Desrciption 根据条件分页查询出待领信息集合
    *
    * @Param
    * [1. pharInWaitReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/9/8 16:50
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    @Override
    public PageDTO queryPharInWaitReceivePage(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
      // 设置分页信息
      PageHelper.startPage(pharInWaitReceiveDTO.getPageNo(),pharInWaitReceiveDTO.getPageSize());

      List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = pharInWaitReceiveDao.queryPharInWaitReceivePage(pharInWaitReceiveDTO);

      return  PageDTO.of(pharInWaitReceiveDTOS);
    }

    /**
     * @Menthod updateInWaitStatusByWrIds
     * @Desrciption 修改待领表状态
     *
     * @Param
     * [pharInWaitReceiveDTO]
     *
     * @Author jiahong.yang
     * @Date   2021/5/6 16:23
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateInWaitStatus(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        pharInWaitReceiveDao.updatePharInWaitReceiveBatch(pharInWaitReceiveDTO);
        return true;
    }

    /**
    * @Menthod updateInWaitStatusByWrIds
    * @Desrciption 修改待领表状态
    *
    * @Param
    * [pharInWaitReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/6 16:23
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateInWaitStatusByWrIds(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
      pharInWaitReceiveDao.updateInWaitStatusByWrIds(pharInWaitReceiveDTO);
      return true;
    }

  /**
    * @Menthod updateUrgentMedicine
    * @Desrciption 批量修改为紧急领药
    *
    * @Param
    * [pharInWaitReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/1/22 11:06
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateUrgentMedicine(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
      pharInWaitReceiveDao.updateUrgentMedicine(pharInWaitReceiveDTO);
      return null;
    }

  @Override
    public List<PharInWaitReceiveDTO> queryPharInWaitReceiveApply(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        return pharInWaitReceiveDao.queryPharInWaitReceiveApply(pharInWaitReceiveDTO);
    }

    @Override
    public Boolean deletePharInReceive(PharInReceiveDTO pharInReceiveDTO) {
        pharInReceiveDAO.deletePharInReceive(pharInReceiveDTO);
        return true;
    }

    @Override
    public Boolean deletePharInReceiveDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO) {
        pharInReceiveDAO.deletePharInReceiveDetail(pharInReceiveDetailDTO);
        return true;
    }

    /**
    * @Method updateCostIdBatch
    * @Desrciption 批量更新待领表的费用明细id
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/11/9 20:27
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateCostIdBatch(List<InptCostDTO> inptCostDTOs) {
        int i = pharInWaitReceiveDao.updateCostIdBatch(inptCostDTOs);
        return i>0;
    }

    @Override
    public List<Map<String, Object>> queryAllVisit(PharInWaitReceiveDTO pharInWaitReceiveDTO) {
        return pharInWaitReceiveDao.queryAllVisit(pharInWaitReceiveDTO);
    }
}
