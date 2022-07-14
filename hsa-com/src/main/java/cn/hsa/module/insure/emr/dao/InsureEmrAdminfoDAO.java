package cn.hsa.module.insure.emr.dao;

import cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrUnifiedDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
* @ClassName InsureEmrAdminfoDAO
* @Deacription 医保电子病历上传-入院信息dao层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrAdminfoDAO  {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureEmrAdminfoDTO> select(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insert(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int delete(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

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
     * @Menthod queryById()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureEmrAdminfoDTO queryByMdtrtSn(@Param("mdtrtSn")String mdtrtSn,@Param("mdtrtId")String mdtrtId);


    /**
     * @Menthod queryById()
     * @Desrciption 电子病历上传-患者列表查询
     * @Param
     *1. InsureEmrCoursrinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    List<InsureEmrUnifiedDTO> queryInsureUnifiedEmrInfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureEmrAdminfoDTO queryByUuid(Long uuid);

    void deleteByMap(Map map);
}