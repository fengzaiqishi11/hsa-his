package cn.hsa.phar.inbackdrug.service.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharinbackdrug.bo.InBackDrugBO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.service.InBackDrugService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.phar.Inbackdrug
 * @Class_name: InBackDrugApiImpl
 * @Describe: 住院退药的Api实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 17:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/stro/inBackDrug")
@Service("inBackDrugService_provider")
public class InBackDrugServiceImpl extends HsafService implements InBackDrugService {


    @Resource
    InBackDrugBO inBackDrugBO;

    /**
    * @Method returnDrugBeHospitalized
    * @Desrciption 住院退药
    * @param map
    * @Author liuqi1
    * @Date   2020/8/25 10:31
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updateInHospitalBackDrug(Map<String,Object> map) {

        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map,"pharInWaitReceiveDTO");

        Boolean isSuccess = inBackDrugBO.updateInHospitalBackDrug(pharInWaitReceiveDTO);

        return WrapperResponse.success(isSuccess);
    }

    /**
    * @Method queryWaitReceiveGroupByDeptId
    * @Desrciption 按申请科室分组查询出待退药的信息
    * @param map
    * @Author liuqi1
    * @Date   2020/8/28 15:41
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
    **/
    @Override
    public WrapperResponse<List<PharInWaitReceiveDTO>> queryWaitReceiveGroupByDeptId(Map<String, Object> map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map,"pharInWaitReceiveDTO");
        List<PharInWaitReceiveDTO> pharInWaitReceiveDTOS = inBackDrugBO.queryWaitReceiveGroupByDeptId(pharInWaitReceiveDTO);
        return  WrapperResponse.success(pharInWaitReceiveDTOS);
    }

    /**
    * @Method queryWaitReceiveGroupByItemId
    * @Desrciption 按项目id分组查询出科室待退药的信息
    * @param map
    * @Author liuqi1
    * @Date   2020/8/28 15:41
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
    **/
    @Override
    public WrapperResponse<PageDTO> queryWaitReceiveGroupByItemIdPage(Map<String, Object> map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map,"pharInWaitReceiveDTO");
        PageDTO pageDTO = inBackDrugBO.queryWaitReceiveGroupByItemIdPage(pharInWaitReceiveDTO);
        return  WrapperResponse.success(pageDTO);
    }

    /**
    * @Method queryWaitReceiveByDeptId
    * @Desrciption 查询出申请科室的退药明细
    * @param map
    * @Author liuqi1
    * @Date   2020/8/28 15:41
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>>
    **/
    @Override
    public WrapperResponse<PageDTO> queryWaitReceiveByDeptIdPage(Map<String, Object> map) {

        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map,"pharInWaitReceiveDTO");
        PageDTO pageDTO = inBackDrugBO.queryWaitReceiveByDeptIdPage(pharInWaitReceiveDTO);
        return  WrapperResponse.success(pageDTO);
    }

    /**
     * @Method: getWaitReceiveByCost
     * @Description: 根据相关参数查询领药申请记录
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 14:00
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
     **/
    @Override
    public WrapperResponse<PharInWaitReceiveDTO> getWaitReceiveByCost(Map<String, Object> map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map,"pharInWaitReceiveDTO");
        return  WrapperResponse.success(inBackDrugBO.getWaitReceiveByCost(pharInWaitReceiveDTO));
    }

    /**
     * @Method getPharInReceiveList
     * @Desrciption 查询领药单列表
       @params [map]
     * @Author chenjun
     * @Date   2020/9/24 10:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO>
    **/
    @Override
    public WrapperResponse<List<PharInReceiveDTO>> getPharInReceiveList(Map map) {
        PharInReceiveDTO pharInReceiveDTO = MapUtils.get(map,"pharInReceiveDTO");
        return  WrapperResponse.success(inBackDrugBO.getPharInReceiveList(pharInReceiveDTO));
    }

    /**
    * @Menthod getPharInReceiveList1
    * @Desrciption 返回分页
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/6 20:20
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> getPharInReceiveList1(Map map) {
      PharInReceiveDTO pharInReceiveDTO = MapUtils.get(map,"pharInReceiveDTO");
      List<PharInReceiveDTO> pharInReceiveList = inBackDrugBO.getPharInReceiveList(pharInReceiveDTO);
      PageDTO of = PageDTO.of(pharInReceiveList);
      return  WrapperResponse.success(of);
    }

    /**
     * @Method getPharInReceiveListDetail
     * @Desrciption 查询领药单明细列表
       @params [map]
     * @Author chenjun
     * @Date   2020/9/24 10:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO>
    **/
    @Override
    public WrapperResponse<List<PharInReceiveDetailDTO>> getPharInReceiveDetailList(Map map) {
        PharInReceiveDetailDTO pharInReceiveDetailDTO = MapUtils.get(map,"pharInReceiveDetailDTO");
        return  WrapperResponse.success(inBackDrugBO.getPharInReceiveDetailList(pharInReceiveDetailDTO));
    }

    /**
    * @Menthod getPharInReceiveDetailList1
    * @Desrciption 返回分页
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/1/6 19:44
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> getPharInReceiveDetailList1(Map map) {
      PharInReceiveDetailDTO pharInReceiveDetailDTO = MapUtils.get(map,"pharInReceiveDetailDTO");
      List<PharInReceiveDetailDTO> pharInReceiveDetailList = inBackDrugBO.getPharInReceiveDetailList(pharInReceiveDetailDTO);
      PageDTO of = PageDTO.of(pharInReceiveDetailList);
      return  WrapperResponse.success(of);
    }

    @Override
    public WrapperResponse<List<PharInReceiveDTO>> queryAll(Map map) {
        PharInReceiveDTO pharInReceiveDTO = MapUtils.get(map,"pharInReceiveDTO");
        return  WrapperResponse.success(inBackDrugBO.queryAll(pharInReceiveDTO));
    }

    /**未退药查询
     * @Method queryBackDrugPage
     * @Desrciption
     * @param map
     * @Author liuqi1
     * @Date   2021/4/23 11:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryBackDrugPage(Map<String, Object> map) {
        PharInWaitReceiveDTO pharInWaitReceiveDTO = MapUtils.get(map,"pharInWaitReceiveDTO");
        PageDTO pageDTO = inBackDrugBO.queryBackDrugPage(pharInWaitReceiveDTO);
        return  WrapperResponse.success(pageDTO);
    }
}
