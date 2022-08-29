package cn.hsa.module.insure.emr.dao;


import cn.hsa.module.insure.emr.dto.InsureEmrCoursrinfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrCoursrinfoDAO
* @Deacription 医保电子病历上传-病程记录dao层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrCoursrinfoDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsureEmrCoursrinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureEmrCoursrinfoDTO> select(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. InsureEmrCoursrinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insert(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除
     * @Param
     *1. InsureEmrCoursrinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int delete(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

    /**
     * @Menthod updateSelective()
     * @Desrciption 修改
     * @Param
     *1. InsureEmrCoursrinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int updateSelective(Map map);

    /**
     * 更新首次病程信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:13
     * @return int
     */
    int updateSelectiveByMdtrtSn(Map map);
    /**
     * @Menthod queryById()
     * @Desrciption 查询单条
     * @Param
     *1. InsureEmrCoursrinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    List<InsureEmrCoursrinfoDTO> queryByMdtrtSn(@Param("mdtrtSn")String mdtrtSn, @Param("mdtrtId")String mdtrtId);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. uuid  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    InsureEmrCoursrinfoDTO queryByUuid(Long uuid);


    void deleteByMap(Map<String, Object> map);

    void insertList(List<InsureEmrCoursrinfoDTO> coursrinfoList);

    InsureEmrCoursrinfoDTO queryInfoByMdtrtSn(@Param("mdtrtSn")String mdtrtSn);
}
