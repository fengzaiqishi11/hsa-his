package cn.hsa.module.outpt.lis.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.lis.bo
 * @Class_name: PacsBO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-11 15:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PacsBO {

    Boolean getPacsDeptList(Map map);

    Boolean getPacsDocList(Map map);

    Boolean getPacsItemDocList(Map map);

    Boolean savePacsInspectApply(Map map);

    Integer SavePacsInspectCallback(Map map);
}
