package cn.hsa.phar.outdistributedrug.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.phar.pharoutdistributedrug.bo.OutDistributeDrugBO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.service.OutDistributeDrugService;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.phar.outdistributedrug.service
* @class_name: OutDistributeDrugServiceImpl
* @Description: 门诊发药service实现类
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:52
* @Company: 创智和宇
**/
@Slf4j
@HsafRestPath("/service/stro/outDistributeDrug")
@Service("outDistributeDrugService_provider")
public class OutDistributeDrugServiceImpl extends HsafService implements OutDistributeDrugService {

    @Resource
    private OutDistributeDrugBO outDistributeDrugBO;

    /**
     * @Method: getOutRecivePage
     * @Description: 获取待领列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 17:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getOutRecivePage(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.getOutRecivePage(MapUtils.get(map,"pharOutReceiveDTO")));
    }


    /**
    * @Menthod getOutRecivePage
    * @Desrciption 根据ids查询所有的配药单数据
     * @param map
    * @Author xingyu.xie
    * @Date   2020/10/28 21:47
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<List<PharOutReceiveDetailDTO>> queryOutDistributedByIds(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.queryOutDistributedByIds(MapUtils.get(map,"pharOutReceiveDetailDTO")));
    }

    /**
     * @Method: getOutDistributePage
     * @Description: 获取发药列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getOutDistributePage(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.getOutDistributePage(MapUtils.get(map,"pharOutReceiveDTO")));
    }

    /**
     * @Method: getOutReciveDetailPage
     * @Description: 获取待领明细列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 17:33
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getOutReciveDetailPage(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.getOutReciveDetailPage(MapUtils.get(map,"pharOutReceiveDetailDTO")));
    }

    /**
     * @Method: getOutDistributeDetailPage
     * @Description: 获取发药明细列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:42
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> getOutDistributeDetailPage(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.getOutDistributeDetailPage(MapUtils.get(map,"pharOutReceiveDetailDTO")));
    }

    /**
     * @Method: dispense
     * @Description: 门诊配药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 10:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<List<StroStockDetailDTO>> outDispense(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.updateOutDispense(MapUtils.get(map,"pharOutReceiveDTO")));
    }

    /**
     * @Method: outEnableDispense
     * @Description: 取消配药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 14:53
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> outEnableDispense(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.updateOutEnabelDispense(MapUtils.get(map,"pharOutReceiveDTO")));
    }

    /**
     * @Method: outDistribute
     * @Description: 门诊发药
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 11:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> outDistribute(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.updateOutDistribute(MapUtils.get(map,"pharOutReceiveDTO")));
    }

    /**
     * @Method: getOrderList
     * @Description: 获取所有处方列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/7 14:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     **/
    @Override
    public WrapperResponse<List<Map>> getOrderList(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.getOrderList(MapUtils.get(map,"pharOutReceiveDTO")));
    }

    /**
     * @Method: getUserList
     * @Description: 获取配药人列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 15:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
     **/
    @Override
    public WrapperResponse<List<Map>> getUserList(Map map) {
        return WrapperResponse.success(outDistributeDrugBO.getUserList(MapUtils.get(map,"pharOutReceiveDTO")));
    }

    /**
    * @Menthod queryPharOutDistributeAllDetailDTO
    * @Desrciption 查询门诊发药信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/27 11:43
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPharOutDistributeAllDetailDTO(Map map) {
      return WrapperResponse.success(outDistributeDrugBO.queryPharOutDistributeAllDetailDTO(MapUtils.get(map,"pharOutDistributeAllDetailDTO")));
    }

    /**
    * @Menthod queryPharInDistributeAllDetailDTO
    * @Desrciption 查询住院发药信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/6/1 14:09
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryPharInDistributeAllDetailDTO(Map map) {
      return WrapperResponse.success(outDistributeDrugBO.queryPharInDistributeAllDetailDTO(MapUtils.get(map,"pharInDistributeAllDetailDTO")));
    }

    /**
    * @Menthod getPrescribePrint
    * @Desrciption 获取处方单打印
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/6/15 15:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
    **/
    @Override
    public WrapperResponse<List<OutptPrescribeDTO>> getPrescribePrint(Map map) {
      return WrapperResponse.success(outDistributeDrugBO.getPrescribePrint(MapUtils.get(map,"pharOutReceiveDTO")));
    }
}
