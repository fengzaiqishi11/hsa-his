package cn.hsa.module.interf.phys.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

public interface PhysRegBO {

    /**
     * @Description: 获取体检者登记信息
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    WrapperResponse<Map> saveRegPhysInfo(Map map);
    /**
     * @Method addVisitByPhys
     * @Desrciption 新增登记一次就要 同步一次就诊的数据
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/7/14 14:19
     * @Return java.lang.Boolean
     */
    Boolean addVisitByPhys(Map map);
    /**
     * @Method addOrUpdateOutptCost
     * @Desrciption 批量插入费用表
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/7/14 16:07
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    Boolean addOrUpdateOutptCost(Map map);

    /**
     * @Description: 同步体检收费组合到项目表
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-11-25
     */
    Boolean insertPhysGroup(Map map);

    /**
     * @Description: 插入退费申请
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-11-25
     */
    Boolean insertReturn(Map map);
}
