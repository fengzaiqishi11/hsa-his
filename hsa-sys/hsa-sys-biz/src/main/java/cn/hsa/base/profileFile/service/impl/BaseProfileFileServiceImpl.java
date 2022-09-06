package cn.hsa.base.profileFile.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.profileFile.bo.BaseProfileFileBO;
import cn.hsa.module.base.profileFile.service.BaseProfileFileService;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.profileFile.service.impl
 * @Class_name: BaseProfileFileServiceImpl
 * @Describe: 本地档案服务service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-30 14:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Service("baseProfileFileService_provider")
@Slf4j
@HsafRestPath("/service/base/baseProfileFile")
public class BaseProfileFileServiceImpl extends HsafService implements BaseProfileFileService {

    @Resource
    private BaseProfileFileBO baseProfileFileBO;

    /**
     * @Menthod: save
     * @Desrciption: 保存/新增个人档案
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-30 15:38
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<OutptProfileFileDTO> save(Map map) {
        return WrapperResponse.success(baseProfileFileBO.save(MapUtils.get(map, "outptProfileFileDTO")));
    }

    /**
     * @Menthod: queryPage
     * @Desrciption: 分页查询所有queryPage
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-07 11:12
     * @Return: PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(baseProfileFileBO.queryPage(MapUtils.get(map, "outptProfileFileDTO")));
    }

    @Override
    public WrapperResponse<List<OutptProfileFileDTO>> queryBaseProfileByIds(Map map) {
        return WrapperResponse.success(baseProfileFileBO.queryBaseProfileByIds(MapUtils.get(map, "outptProfileFileDTO")));
    }

    /**
     * @Method isCertNoExist
     * @Desrciption  暂时只做判断身份证是否重复
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> isCertNoExist(Map map) {
        return WrapperResponse.success(baseProfileFileBO.isCertNoExist(MapUtils.get(map, "outptProfileFileDTO")));
    }

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return OutptProfileFileDTO
     **/
    @Override
    public WrapperResponse<OutptProfileFileDTO> getById(Map map) {
        return WrapperResponse.success(baseProfileFileBO.getById(MapUtils.get(map, "outptProfileFileDTO")));
    }

    /**
     * @Method getAddressTree
     * @Desrciption  获取码表地址树
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/11/12 11:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> getAddressTree(Map map) {
        return WrapperResponse.success(TreeUtils.buildByRecursive(baseProfileFileBO.getAddressTree(MapUtils.get(map, "outptProfileFileDTO")), "-1"));
    }

    @Override
    public WrapperResponse<List<OutptProfileFileDTO>> queryAll(Map map) {
        return WrapperResponse.success(baseProfileFileBO.queryAll(MapUtils.get(map, "outptProfileFileDTO")));
    }

    /**
     * @Method isCertNoExist
     * @Desrciption 删除档案
     * @Param[id]
     * @Author yuelong.chen
     * @Date   2021/11/23 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteProfileFile(Map map) {
        return WrapperResponse.success(baseProfileFileBO.deleteProfileFile(map));
    }

    /**
     * @Method queryCertNoIsExist
     * @Desrciption  新增修改档案时判断身份证是否重复
     * @Param [outptProfileFileDTO]
     * @Author liuliyun
     * @Date   2022/09/02 9:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>s
     **/
    @Override
    public WrapperResponse<Boolean> queryCertNoIsExist(Map map) {
        return WrapperResponse.success(baseProfileFileBO.queryCertNoIsExist(MapUtils.get(map, "outptProfileFileDTO")));
    }
}
