package cn.hsa.module.phar.pharoutdistributedrug.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeAllDetailDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.bo
* @class_name: OutDistributeDrugBO
* @Description: 门诊发药BO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:47
* @Company: 创智和宇
**/
public interface OutDistributeDrugBO {

    /**
     * @Method: getOutRecivePage
     * @Description: 获取待领列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 17:40
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getOutRecivePage(PharOutReceiveDTO pharOutReceiveDTO);

    /**
    * @Menthod queryOutDistributedByIds
    * @Desrciption 根据ids查询所有的配药单数据
     * @param pharOutReceiveDetailDTO
    * @Author xingyu.xie
    * @Date   2020/10/28 21:46
    * @Return java.util.List<cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDetailDTO>
    **/
    List<PharOutReceiveDetailDTO> queryOutDistributedByIds(PharOutReceiveDetailDTO pharOutReceiveDetailDTO);

    /**
     * @Method: getOutDistributePage
     * @Description: 获取发药列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:29
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getOutDistributePage(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: getOutReciveDetailPage
     * @Description: 获取待领明细列表
     * @Param: [pharOutReceiveDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/2 17:40
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getOutReciveDetailPage(PharOutReceiveDetailDTO pharOutReceiveDetailDTO);

    /**
     * @Method: getOutDistributeDetailPage
     * @Description: 获取发药明细列表
     * @Param: [pharOutReceiveDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 16:42
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getOutDistributeDetailPage(PharOutReceiveDetailDTO pharOutReceiveDetailDTO);

    /**
     * @Method: dispense
     * @Description: 门诊配药
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 10:59
     * @Return: java.lang.Boolean
     **/
    PharOutReceiveDTO updateOutDispense(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: updateOutEnabelDispense
     * @Description: 取消配药
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 14:54
     * @Return: java.lang.Boolean
     **/
    Boolean updateOutEnabelDispense(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: outDistribute
     * @Description: 门诊发药
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/3 11:16
     * @Return: java.lang.Boolean
     **/
    Boolean updateOutDistribute(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: getOrderList
     * @Description: 获取所有处方列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/7 14:18
     * @Return: java.util.List<java.util.Map>
     **/
    List<Map> getOrderList(PharOutReceiveDTO pharOutReceiveDTO);

    /**
     * @Method: getUserList
     * @Description: 获取配药人列表
     * @Param: [pharOutReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/12 15:49
     * @Return: java.util.List<java.util.Map>
     **/
    List<Map> getUserList(PharOutReceiveDTO pharOutReceiveDTO);

    /**
    * @Menthod queryPharOutDistributeAllDetailDTO
    * @Desrciption
    *
    * @Param
    * [pharOutReceiveDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/27 11:43
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryPharOutDistributeAllDetailDTO(PharOutDistributeAllDetailDTO pharOutDistributeAllDetailDTO);

    /**
    * @Menthod queryPharInDistributeAllDetailDTO
    * @Desrciption 查询住院发药信息
    *
    * @Param
    * [pharInDistributeAllDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/1 14:14
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryPharInDistributeAllDetailDTO(PharInDistributeAllDetailDTO pharInDistributeAllDetailDTO);


    /**
    * @Menthod getPrescribePrint
    * @Desrciption 获取处方单打印
    *
    * @Param
    * [pharOutReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/15 15:55
    * @Return java.util.List<cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO>
    **/
    List<OutptPrescribeDTO> getPrescribePrint(PharOutReceiveDTO pharOutReceiveDTO);
}
