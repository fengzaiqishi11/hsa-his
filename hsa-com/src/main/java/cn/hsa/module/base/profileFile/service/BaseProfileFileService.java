package cn.hsa.module.base.profileFile.service;

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
 * @Package_name: cn.hsa.module.base.proFilefile.service
 * @Class_name: BaseProfileFileService
 * @Describe: 本地档案服务service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-30 14:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "his-base")
public interface BaseProfileFileService {

    /**
     * @Menthod: save
     * @Desrciption: 保存/新增个人档案
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-30 15:38
     * @Return: Boolean
     **/
    @PostMapping("/service/base/baseProfileFile/save")
    WrapperResponse<OutptProfileFileDTO> save(Map map);

    /**
     * @Menthod: queryPage
     * @Desrciption: 分页查询所有queryPage
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-07 11:12
     * @Return: PageDTO
     **/
    @GetMapping("/service/base/baseProfileFile/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Menthod: queryBaseProfileByIds
     * @Desrciption: 条件查询档案列表
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-07 11:12
     * @Return: PageDTO
     **/
    @GetMapping("/service/base/baseProfileFile/queryBaseProfileByIds")
    WrapperResponse<List<OutptProfileFileDTO>> queryBaseProfileByIds(Map map);

    /**
     * @Method isCertNoExist
     * @Desrciption  暂时只做判断身份证是否重复
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/base/baseProfileFile/isCertNoExist")
    WrapperResponse<Boolean> isCertNoExist(Map map);

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return OutptProfileFileDTO
     **/
    @GetMapping("/service/base/baseProfileFile/getById")
    WrapperResponse<OutptProfileFileDTO> getById(Map map);

    /**
     * @Method getAddressTree
     * @Desrciption 获取码表地址树
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/11/12 11:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("/service/base/baseProfileFile/getAddressTree")
    WrapperResponse<List<TreeMenuNode>>getAddressTree(Map map);

    @GetMapping("/service/base/baseProfileFile/queryAll")
    WrapperResponse<List<OutptProfileFileDTO>> queryAll(Map map);

    /**
     * @Method isCertNoExist
     * @Desrciption 删除档案
     * @Param[id]
     * @Author yuelong.chen
     * @Date   2021/11/23 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/base/baseProfileFile/deleProfileFile")
    WrapperResponse<Boolean> deleteProfileFile(Map map);


    /**
     * @Method queryCertNoIsExist
     * @Desrciption  新增修改档案时判断身份证是否重复
     * @Param [outptProfileFileDTO]
     * @Author liuliyun
     * @Date   2022/09/02 9:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/service/base/baseProfileFile/queryCertNoIsExist")
    WrapperResponse<Boolean> queryCertNoIsExist(Map map);
}
