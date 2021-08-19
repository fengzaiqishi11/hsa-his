package cn.hsa.module.inpt.inptguarantee.dao;

import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 住院担保管理(InptGuarantee)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-10 15:01:34
 */
public interface InptGuaranteeDAO {

    /**
    * @Menthod queryById
    * @Desrciption 查询单个
    *
    * @Param
    * [inptGuarantee]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 16:40
    * @Return cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO
    **/
    InptGuaranteeDTO queryById(InptGuaranteeDTO inptGuarantee);

    /**
    * @Menthod queryAllInptGuarantee
    * @Desrciption 查询所有的
    *
    * @Param
    * [inptGuarantee]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 16:40
    * @Return java.util.List<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>
    **/
    List<InptGuaranteeDTO> queryAllInptGuarantee(InptGuaranteeDTO inptGuarantee);

    List<InptGuaranteeDTO> queryAllInptGuaranteeByIds(InptGuaranteeDTO inptGuarantee);


    /**
    * @Menthod insert
    * @Desrciption 新增
    *
    * @Param
    * [inptGuarantee]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 16:41
    * @Return int
    **/
    int insert(InptGuaranteeDTO inptGuarantee);


    /**
    * @Menthod update
    * @Desrciption 修改
    *
    * @Param
    * [inptGuarantee]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 16:41
    * @Return int
    **/
    int update(InptGuaranteeDTO inptGuarantee);

    /**
    * @Menthod updateAuditCode
    * @Desrciption 修改审核状态
    *
    * @Param
    * [inptGuarantee]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 16:41
    * @Return int
    **/
    int updateAuditCode(InptGuaranteeDTO inptGuarantee);

    /**
    * @Menthod updatePeopleGuaranteeBalance
    * @Desrciption 修改就诊表中得担保金额
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/8/12 9:06
    * @Return int
    **/
    int updatePeopleGuaranteeBalance(InptVisitDTO inptVisitDTO);

    /**
    * @Menthod getVisitById
    * @Desrciption 根据id查询就诊信息
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/8/12 9:33
    * @Return cn.hsa.module.inpt.doctor.dto.InptVisitDTO
    **/
    InptVisitDTO getVisitById(InptVisitDTO inptVisitDTO);

}
