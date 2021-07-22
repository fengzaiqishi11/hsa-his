package cn.hsa.base.bw.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bw.bo.BaseWindowBO;
import cn.hsa.module.base.bw.dto.BaseWindowDTO;
import cn.hsa.module.base.bw.service.BaseWindowService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.base.bmm.service.impl
 * @Class_name: BaseWindowManagementServiceImpl
 * @Describe: 发药窗口Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/23 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/baseWindow")
@Slf4j
@Service("baseWindowService_provider")
public class BaseWindowServiceImpl extends HsafService implements BaseWindowService {
    @Resource
    BaseWindowBO baseWindowBO;

    /**
    * @Menthod getById
    * @Desrciption   根据主键id和医院编码查询发药窗口
     * @param map 发药窗口表主键
    * @Author xingyu.xie
    * @Date   2020/7/23 15:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseWindowDTO>
    **/
    @Override
    public WrapperResponse<BaseWindowDTO> getById(Map map) {
        return WrapperResponse.success(baseWindowBO.getById(MapUtils.get(map,"baseWindowDTO")));
    }

    /**
     * @Menthod queryAll
     * @Desrciption   查询全部
     * @param map 医院编码
     * @Author xingyu.xie
     * @Date   2020/7/23 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     *
     * @return*/
    @Override
    public WrapperResponse<List<BaseWindowDTO>> queryAll(Map map) {
        return WrapperResponse.success(baseWindowBO.queryAll(MapUtils.get(map,"baseWindowDTO")));
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象来筛选查询
     * @param map 生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 15:38
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(baseWindowBO.queryPage(MapUtils.get(map,"baseWindowDTO")));
    }

    /**
    * @Menthod insert
    * @Desrciption    新增发药窗口
    * @param map 生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 15:39
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        try {
            return WrapperResponse.success(baseWindowBO.insert(MapUtils.get(map, "baseWindowDTO")));
        }catch (Exception e){
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
    * @Menthod update
    * @Desrciption   修改发药窗口
     * @param map 生产企业分类数据对象
    * @Author xingyu.xie
    * @Date   2020/7/23 15:39
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        try {
            return WrapperResponse.success(baseWindowBO.update(MapUtils.get(map, "baseWindowDTO")));
        }catch (Exception e){
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
    * @Menthod delete
    * @Desrciption   删除一个或多个发药窗口
     * @param map 发药窗口表的主键列表
    * @Author xingyu.xie
    * @Date   2020/7/23 15:40
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> delete(Map map) {
        return WrapperResponse.success(baseWindowBO.delete(MapUtils.get(map,"baseWindowDTO")));
    }

    /**
     * @Menthod queryByDeptId
     * @Desrciption  查询当前登录科室的发药窗口
     * @param map 发药窗口表的数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/31 10:01
     * @Return java.util.List<cn.hsa.module.base.bw.dto.BaseWindowDTO>
     **/
    @Override
    public WrapperResponse<List<BaseWindowDTO>> queryByDeptId(Map map) {
        return WrapperResponse.success(baseWindowBO.queryByDeptId(MapUtils.get(map,"baseWindowDTO")));
    }
}
