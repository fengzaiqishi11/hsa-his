package cn.hsa.module.insure.clinica.dao;


import cn.hsa.module.insure.clinica.dto.CommentDTO;
import cn.hsa.module.insure.clinica.dto.InsureClinicalCheckoutDTO;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureClinicalCheckoutDAO
* @Deacription 临床检验报告信息表dao层
* @Author liuhuiming
* @Date 2022-05-07
* @Version 1.0
**/
public interface InsureClinicalCheckoutDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsureClinicalCheckoutDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureClinicalCheckoutDTO> queryPageInsureClinicalCheckout(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insertInsureClinicalCheckout(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO);

    /**
     * @Menthod updateSelective()
     * @Desrciption 修改
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int updateSelective(Map map);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureClinicalCheckoutDTO queryByUuid(Long uuid);

    List<CommentDTO> queryComment();

}
