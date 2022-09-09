package cn.hsa.module.phar.pharindistributedrug.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.phar.pharindistributedrug.bo
* @class_name: InDistributeDrugBO
* @Description: 住院发药BO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:49
* @Company: 创智和宇
**/
public interface InDistributeDrugBO {

    /**
     * @Method: getInRecivePage
     * @Description: 住院发药--申请记录
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:28
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getInRecivePage(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method: getSendInRecivePage
     * @Description: 住院发药记录
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 14:49
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getSendInRecivePage(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method: getInReviceDetailPage
     * @Description: 住院发药--取药明细单
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:54
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getInReviceDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
    * @Menthod getInReviceDetail
    * @Desrciption  住院配药的明细单打印 按医嘱类型和病人分组
     * @param pharInReceiveDetailDTO
    * @Author xingyu.xie
    * @Date   2020/12/25 9:47
    * @Return java.util.Map<java.lang.String,java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDetailDTO>>
    **/
     Map<String, List<PharInReceiveDetailDTO>> getInReviceDetail(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
     * @Method: getInSumReviceDetailPage
     * @Description: 住院发药--取药合计单
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 14:54
     * @Return: cn.hsa.base.PageDTO
     **/
    PageDTO getInSumReviceDetailPage(PharInReceiveDetailDTO pharInReceiveDetailDTO);

    /**
     * @Method: updateInDispense
     * @Description: 住院配药
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 15:54
     * @Return: java.lang.Boolean
     **/
    PharInReceiveDTO updateInDispense(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method: cancelInDispense
     * @Description: 取消配药
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/26 15:03
     * @Return: java.lang.Boolean
     **/
    Boolean updateCancelInDispense(PharInReceiveDTO pharInReceiveDTO);

    /**
     * @Method: updateInDistribute
     * @Description: 住院发药
     * @Param: [pharInReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/10 15:54
     * @Return: java.lang.Boolean
     **/
    Boolean updateInDistribute(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod queryPatientByOrder
    * @Desrciption 根据配药单号查询患者
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:15
    * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
    **/
    List<InptVisitDTO> queryPatientByOrder(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod queryDrugByOrderAndVisitId
    * @Desrciption 根据配药单号,就诊id查询病人配药
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/4/22 19:15
    * @Return java.util.List<cn.hsa.module.phar.pharindistributedrug.dto.PharInReceiveDTO>
    **/
    List<InptAdviceDTO> queryDrugByOrderAndVisitId(PharInReceiveDTO pharInReceiveDTO);

    /**
    * @Menthod updatePremedication
    * @Desrciption 选择性取消预配药
    *
    * @Param
    * [pharInReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/7/13 10:17
    * @Return java.lang.Boolean
    **/
    Boolean updatePremedication(PharInReceiveDTO pharInReceiveDTO);
    /**
     * @Menthod: queryDMDrugByOrderAndVisitId
     * @Desrciption: 根据就诊id查询精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    List<InptAdviceDTO> queryDMDrugByOrderAndVisitId(PharInReceiveDTO pharInReceiveDTO);
    /**
     * @Menthod: queryDMPatientByOrder
     * @Desrciption: 查询配药发药精麻处方
     * @Param: inptVisitDTO
     * @Author: yuelong.chen
     * @Email: yuelong.chen@powersi.com.cn
     * @Date: 2022-08-03 19:31
     * @Return:
     **/
    List<InptVisitDTO> queryDMPatientByOrder(PharInReceiveDTO pharInReceiveDTO);
}
