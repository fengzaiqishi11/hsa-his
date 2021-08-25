package cn.hsa.module.base.deptDrug.dao;

import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.deptDrug.dto.BaseDeptDrugStoreDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表名含义：
 * base：基础模块
 * dept：科室关联药房信息表
 * drugstore：药房(BaseDeptDrugstore)表数据库访问层
 *
 * @author fuhui
 * @since 2020-09-15 10:50:12
 */
public interface BaseDeptDrugStoreDAO {


    /**
     * @Method update()
     * @Desrciption  修改科室药房
     * @Param baseDeptDTO 科室维护信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/15 11:28
     * @Return 返回影响的行数
     **/
    int update(List<BaseDeptDrugStoreDTO> drugStoreList);

    /**
     * @Method updateIsValid()
     * @Desrciption  作废科室关联科室药房
     * @Param baseDeptDTO 科室维护信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/15 11:28
     * @Return 返回影响的行数
     **/
    int updateIsValid(BaseDeptDTO baseDeptDTO);

    /**
     * @Method insertDrugstore()
     * @Desrciption  新增科室关联领药药房对应的数据
     * @Param hospCode：医院编码 code：科室编码
     *
     * @Author fuhui
     * @Date   2020/9/18 16:34
     * @Return BaseDeptDrugStoreDTO科室关联领药药房的数据传输对象
     **/
    int insertDrugstore(@Param("drugStoreList") List<BaseDeptDrugStoreDTO> drugStoreList);

    /**
     * @Method queryBaseDeptDrugstore()
     * @Desrciption  根据科室编码和医院编码查询科室关联领药药房对应的数据
     * @Param hospCode：医院编码 code：科室编码
     *
     * @Author fuhui
     * @Date   2020/9/18 16:34
     * @Return BaseDeptDrugStoreDTO科室关联领药药房的数据传输对象
    **/
    List<BaseDeptDrugStoreDTO> queryBaseDeptDrugstore(@Param("hospCode") String hospCode, @Param("code") String code);

    /**
     * @Method deleteDrugStore()
     * @Desrciption  删除跟科室关联的领药药房
     * @Param  baseDeptDTO 科室维护信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/24 11:12
     * @Return  baseDeptDTO 科室维护信息数据传输对象
     **/
    void deleteDrugStore(BaseDeptDTO baseDeptDTO);

}