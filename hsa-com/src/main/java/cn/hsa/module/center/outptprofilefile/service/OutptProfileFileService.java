package cn.hsa.module.center.outptprofilefile.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.profilefile.service
 * @Class_name:: OutptProfileFileService
 * @Description: 
 * @Author: liaojunjie
 * @Date: 2020/8/19 19:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-center")
public interface OutptProfileFileService {

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>
     **/
    @GetMapping("/service/center/outptProfileFile/getById")
    WrapperResponse<OutptProfileFileDTO> getById(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询所有个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>>
     **/
    @GetMapping("/service/center/outptProfileFile/queryAll")
    WrapperResponse<List<OutptProfileFileDTO>> queryAll(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/center/outptProfileFile/queryPage")
    WrapperResponse<PageDTO> queryPage(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method save
     * @Desrciption 保存/新增个人档案 统一接口
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/outptProfileFile/save")
    WrapperResponse<OutptProfileFileExtendDTO> save(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时只做判断身份证是否重复
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/center/outptProfileFile/isCertNoExist")
    WrapperResponse<Boolean> isCertNoExist(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method getExtendByProfileId
     * @Desrciption 通过档案ID和医院编码拿从表数据
     * @Param
     * [outptProfileFileExtendDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO>
     **/
    @GetMapping("/service/center/outptProfileFile/getExtendByProfileId")
    WrapperResponse<OutptProfileFileExtendDTO> getExtendByProfileId(OutptProfileFileExtendDTO outptProfileFileExtendDTO);

    /**
     * @Method getAddressTree
     * @Desrciption 获取码表地址树
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/11/12 11:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    WrapperResponse<List<TreeMenuNode>>getAddressTree(OutptProfileFileDTO outptProfileFileDTO);

    @GetMapping("/service/center/outptProfileFile/getByIds")
    WrapperResponse<List<OutptProfileFileDTO>> getByIds(Map<String, Object> paramMap);
}
