package cn.hsa.module.outpt.lis.dao;

import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;

import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.lis.dao
 * @Class_name: PacsDao
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-11 15:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PacsDao {

    List<BaseDeptDTO> getPacsDeptList(Map map);

    List<SysUserDTO> getPacsDocList(Map map);

    List<BaseAdviceDTO> getPacsItemList(Map map);

    List<Map> getPacsPrescribe(Map map);

    List<Map> getPacsAdvice(Map map);

    Integer insertPacsInspectResult(Map map);
}