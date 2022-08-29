package cn.hsa.module.insure.emr.dao;

import cn.hsa.module.insure.emr.dto.InsureEmrDieinfoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrDieinfoDAO
* @Deacription 医保电子病历上传-死亡记录dao层
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureEmrDieinfoDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsureEmrDieinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureEmrDieinfoDTO> select(InsureEmrDieinfoDTO insureEmrDieinfoDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. InsureEmrDieinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insert(InsureEmrDieinfoDTO insureEmrDieinfoDTO);

    /**
     * @Menthod delete()
     * @Desrciption 删除
     * @Param
     *1. InsureEmrDieinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int delete(InsureEmrDieinfoDTO insureEmrDieinfoDTO);

    /**
     * @Menthod updateSelective()
     * @Desrciption 修改
     * @Param
     *1. InsureEmrDieinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int updateSelective(Map map);

    /**
     * 更新死亡信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:40
     * @return int
     */
    int updateDieSelective(Map map);
    /**
     * @Menthod queryById()
     * @Desrciption 查询单条
     * @Param
     *1. InsureEmrDieinfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    List<InsureEmrDieinfoDTO> queryByMdtrtSn(@Param("mdtrtSn")String mdtrtSn, @Param("mdtrtId")String mdtrtId);

    /**
     * 查询医保电子病历死亡信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 17:07
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDieinfoDTO
     */
    InsureEmrDieinfoDTO queryInfoByMdtrtSn(@Param("mdtrtSn")String mdtrtSn);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureEmrDieinfoDTO queryByUuid(Long uuid);

    void deleteByMap(Map<String, Object> map);

    void insertList(List<InsureEmrDieinfoDTO> dieInfoList);
}
