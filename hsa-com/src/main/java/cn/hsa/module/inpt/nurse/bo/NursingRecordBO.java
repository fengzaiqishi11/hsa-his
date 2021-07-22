package cn.hsa.module.inpt.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.dto.InptNurseRecordDTO;

import java.util.List;
import java.util.Map;

public interface NursingRecordBO {
    /**
     * @Method save
     * @Desrciption 保存护理记录(新增 、 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return java.lang.Boolean
     **/
    Boolean save(Map map);

    /**
     * @Method saveByGroup
     * @Desrciption 分组拆分保存护理记录(新增 、 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return java.lang.Boolean
     **/
    Boolean saveByGroup(Map map);

    /**
     * @Method delete
     * @Desrciption 删除护理记录
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return java.lang.Boolean
     **/
    Boolean delete(Map map);

    /**
     * @Method queryNursingRecord
     * @Desrciption 分页查询护理单记录
     * @Param InptNurseRecordDTO inptNurseRecordDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryNursingRecord(InptNurseRecordDTO inptNurseRecordDTO);

    /**
     * @Method addDaySummary
     * @Desrciption 添加日间小结
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return java.lang.Boolean
     **/
    Boolean addDaySummary(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryAll(InptVisitDTO inptVisitDTO);
    /**
     * @Method queryAcccpeGold
     * @Desrciption 查询所有住院病人，预交金缴纳情况
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2021/6/16 13:45
     * @Return
     **/
   List<Map<String,Object>> queryAcceptGold(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryNurseRecordByPrint
     * @Desrciption 护理记录打印接口
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return List<InptNurseRecordDTO>
     **/
    List<InptNurseRecordDTO> queryNurseRecordByPrint(InptNurseRecordDTO inptNurseRecordDTO);

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
     * @Method getMaxGroupNo
     * @Desrciption 获取当前人员最大组号
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Integer
     **/
    Integer getMaxGroupNo(InptNurseRecordDTO inptNurseRecordDTO);
}
