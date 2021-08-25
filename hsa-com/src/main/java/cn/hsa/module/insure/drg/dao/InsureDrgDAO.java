package cn.hsa.module.insure.drg.dao;

import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.insure.drg.dao
 * @class_name: InsureDrgDAO
 * @project_name: hsa-his
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 19:06
 * @Company: 创智和宇
 **/
public interface InsureDrgDAO {

    InsureIndividualVisitDTO queryInsureIndividualVisit(InsureIndividualVisitDTO insureIndividualVisitDTO);

    List<InsureIndividualVisitDTO> queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO);
}
