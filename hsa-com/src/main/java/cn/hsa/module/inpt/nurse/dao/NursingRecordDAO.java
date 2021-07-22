package cn.hsa.module.inpt.nurse.dao;

import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.dto.InptNurseRecordDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface NursingRecordDAO {
    /**
     * @Method insert
     * @Desrciption 新增护理记录
     * @Param List<InptNurseRecordDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return int
     **/
    int insert(@Param("addList") List<InptNurseRecordDTO> addList);

    /**
     * @Method edit
     * @Desrciption 编辑护理记录
     * @Param List<InptNurseRecordDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return int
     **/
    int edit(@Param("editList") List<InptNurseRecordDTO> editList);

    /**
     * @Method updateIsValid
     * @Desrciption 删除护理记录
     * @Param List<InptNurseRecordDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return int
     **/
    int updateIsValid(@Param("delList") List<InptNurseRecordDTO> delList);

    /**
     * @Method queryNursingRecord
     * @Desrciption 分页查询护理单记录
     * @Param InptNurseRecordDTO inptNurseRecordDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return List<InptNurseRecordDTO>
     **/
    List<InptNurseRecordDTO> queryNursingRecord(InptNurseRecordDTO inptNurseRecordDTO);

    /**
     * @Method queryNurseOrder
     * @Desrciption 查询护理单据
     * @Param InptNurseRecordDTO inptNurseRecordDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return int
     **/
    int findNurseOrder(InptNurseRecordDTO inptNurseRecordDTO);

    /**
     * @Method findNurseTbHead
     * @Desrciption 查询出护理单据类型下所有需要汇总的表头
     * @Param InptNurseRecordDTO inptNurseRecordDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return BaseNurseTbheadDTO--护理单据表头格式dto
     **/
    List<BaseNurseTbHeadDTO> findNurseTbHead(InptNurseRecordDTO inptNurseRecordDTO);

    /**
     * @Method addDaySummary
     * @Desrciption 插入单个护理记录
     * @Param InptNurseRecordDTO inptNurseRecordDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return int
     **/
    int addDaySummary(InptNurseRecordDTO inptNurseRecordDTO);

    List<InptVisitDTO> queryAll(InptVisitDTO inptVisitDTO);
//    List<Map<String,Object>> queryAll(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryAcceptGold
     * @Desrciption 查询病人信息，预交金缴纳情况
     * @Param InptNurseRecordDTO inptNurseRecordDTO
     * @Author: lizihuan
     * @Date: 2021/6/16 13:36
     * @Return int
     **/
    List<Map<String,Object>> queryAcceptGold(InptVisitDTO inptVisitDTO);

    /**
     * @Method findAllNurseTBhead
     * @Desrciption 查询出护理单据下所有的表头
     * @Param InptNurseRecordDTO inptNurseRecordDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return List<BaseNurseTbHeadDTO>
     **/
    List<BaseNurseTbHeadDTO> findAllNurseTBhead(InptNurseRecordDTO inptNurseRecordDTO);

    /**
     * @Method getValue
     * @Desrciption 获取分割参数
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Map<String,String>
     **/
    Map<String, Object> getValue(Map map);

    /**
     * @Method: getMaxGroupNo
     * @Description: 获取当前人员最大组号
     * @Param: nurseRecordDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2021-04-12 11:23
     * @Return Integer
     */
    Integer getMaxGroupNo(InptNurseRecordDTO nurseRecordDTO);

    int delete(@Param("deleteList") List<InptNurseRecordDTO> deleteList);

    List<InptNurseRecordDTO> queryNurseByGroup(Map map);
}
