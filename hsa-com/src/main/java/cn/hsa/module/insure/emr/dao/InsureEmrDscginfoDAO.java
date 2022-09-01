package cn.hsa.module.insure.emr.dao;

import cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO;
import cn.hsa.module.insure.emr.entity.InsureEmrDscginfoDO;
import org.apache.ibatis.annotations.Param;

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
     * 插入出院小结
     * @param insureEmrDscginfoDO
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 11:05
     * @return int
     */
    int insertSelective(InsureEmrDscginfoDO insureEmrDscginfoDO);


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
     * 更新出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:51
     * @return int
     */
    int updateDscgSelective(Map map);
    /**
     * @Menthod queryById()
     * @Desrciption 查询单条
     * @Param
     *1. InsureEmrDscginfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    List<InsureEmrDscginfoDTO> queryByMdtrtSn(@Param("mdtrtSn")String mdtrtSn, @Param("mdtrtId")String mdtrtId);

    /**
     * 查询出院小结信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:37
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO
     */
    InsureEmrDscginfoDTO queryInfoByMdtrtSn(@Param("mdtrtSn")String mdtrtSn);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureEmrDscginfoDTO queryByUuid(Long uuid);

    void deleteByMap(Map<String, Object> map);

    void insertList(List<InsureEmrDscginfoDTO> dscgoInfoList);

}
