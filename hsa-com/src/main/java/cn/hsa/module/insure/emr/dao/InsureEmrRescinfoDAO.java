package cn.hsa.module.insure.emr.dao;

import cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrRescinfoDAO
* @Deacription 医保电子病历上传-病情抢救记录dao层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrRescinfoDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsureEmrRescinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureEmrRescinfoDTO> select(InsureEmrRescinfoDTO insureEmrRescinfoDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. InsureEmrRescinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insert(InsureEmrRescinfoDTO insureEmrRescinfoDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除
     * @Param
     *1. InsureEmrRescinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int delete(InsureEmrRescinfoDTO insureEmrRescinfoDTO);

    /**
     * @Menthod updateSelective()
     * @Desrciption 修改
     * @Param
     *1. InsureEmrRescinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int updateSelective(Map map);

    int updateRescSelective(Map map);
    /**
     * @Menthod queryById()
     * @Desrciption 查询单条
     * @Param
     *1. InsureEmrRescinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    List<InsureEmrRescinfoDTO> queryByMdtrtSn(@Param("mdtrtSn")String mdtrtSn, @Param("mdtrtId")String mdtrtId);

    InsureEmrRescinfoDTO queryInfoByTemplateId(@Param("templateId")String templateId);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureEmrRescinfoDTO queryByUuid(Long uuid);

    void deleteByMap(Map<String, Object> map);

    void insertList(List<InsureEmrRescinfoDTO> rescInfoList);
}
