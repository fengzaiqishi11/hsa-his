package cn.hsa.module.insure.emr.dao;

import cn.hsa.module.insure.emr.dto.InsureEmrOprninfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrOprninfoDAO
* @Deacription 医保电子病历上传-手术记录dao层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrOprninfoDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsureEmrOprninfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureEmrOprninfoDTO> select(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. InsureEmrOprninfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insert(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除
     * @Param
     *1. InsureEmrOprninfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int delete(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

    /**
     * @Menthod updateSelective()
     * @Desrciption 修改
     * @Param
     *1. InsureEmrOprninfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int updateSelective(Map map);
    /**
     * @Menthod queryById()
     * @Desrciption 查询单条
     * @Param
     *1. InsureEmrOprninfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureEmrOprninfoDTO queryById(@Param("mdtrtSn")String mdtrtSn, @Param("mdtrtId")String mdtrtId);

    void deleteByMap(Map<String, Object> map);

    void insertList(List<InsureEmrOprninfoDTO> operationInfoList);
}
