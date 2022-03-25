package cn.hsa.module.insure.emr.dao;

import cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrDscginfoDAO
* @Deacription 医保电子病历上传-出院记录dao层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrDscginfoDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsureEmrDscginfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureEmrDscginfoDTO> select(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. InsureEmrDscginfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insert(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除
     * @Param
     *1. InsureEmrDscginfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int delete(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

    /**
     * @Menthod updateSelective()
     * @Desrciption 修改
     * @Param
     *1. InsureEmrDscginfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int updateSelective(Map map);
    /**
     * @Menthod queryById()
     * @Desrciption 查询单条
     * @Param
     *1. InsureEmrDscginfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureEmrDscginfoDTO queryById(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

}
