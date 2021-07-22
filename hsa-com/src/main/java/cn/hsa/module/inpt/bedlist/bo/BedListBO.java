package cn.hsa.module.inpt.bedlist.bo;

import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.bedlist.bo
 * @Class_name: BedListBo
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/9/8 15:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BedListBO {

    /**
     * @Method queryPage
     * @Desrciption 分页查询床位信息
     * @params [inptVisitDTO]
     * @Author chenjun
     * @Date 2020/9/8 15:25
     * @Return cn.hsa.base.PageDTO
     **/
    List<InptVisitDTO> queryPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryWaitVisitAll
     * @Desrciption 查询待安床病人
     * @params [inptVisitDTO]
     * @Author chenjun
     * @Date 2020/9/10 11:34
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryWaitVisitAll(InptVisitDTO inptVisitDTO);

    /**
     * @Method 床位变动接口
     * @Description
     * 1、安床 = '0'
     * 2、换床 = '1'
     * 3、包床 = '2'
     * 4、转科 = '3'
     * 5、包床取消 = '4'
     * 6、预出院 = '5'
     * 7、出院召回/召回费用 = '6'
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/25 11:03
     * @Return
     **/
    Boolean saveBedChange(Map map);

    /**
     * @Method 根据病区查询科室信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/30 9:43
     * @Return
     **/
    List<Map<String, Object>> queryDeptByWardId(Map map);
}
