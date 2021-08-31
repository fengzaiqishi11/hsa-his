package cn.hsa.module.outpt.infusionExec.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.infusionExec.bo
 * @Class_name:: OutptInfusionExecBO
 * @Description: 门诊输液执行BO接口层
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/13 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptInfusionExecBO {

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询输液列表
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.sys.PageDTO
     **/
    PageDTO queryPage(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO);

    /**
     * @Menthod: update()
     * @Desrciption: 输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: Boolean
     **/
    Boolean update(Map map);

    /**
     * @Menthod: remove()
     * @Desrciption: 取消输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: Boolean
     **/
    Boolean remove(Map map);

    /**
     * @Menthod: updateExec()
     * @Desrciption: 执行签名(签名 、 取消)
     * @Param: List<OutptPrescribeExecDTO>
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: Boolean
     **/
    Boolean updateExec(Map map);
}
