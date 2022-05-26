package cn.hsa.center.outptprofilefile.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.outptprofilefile.bo.OutptProfileFileBO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.util.TreeUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.outptprofilefile.impl
 * @Class_name:: OutptProfileFileServiceImpl
 * @Description: 
 * @Author: liaojunjie
 * @Date: 2020/8/19 16:46 
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/center/outptProfileFile")
@Slf4j
@Service("outptProfileFileService_provider")
public class OutptProfileFileServiceImpl extends HsafService implements OutptProfileFileService {
    @Resource
    private OutptProfileFileBO outptProfileFileBO;

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>
     **/
    @Override
    public WrapperResponse<OutptProfileFileDTO> getById(OutptProfileFileDTO outptProfileFileDTO) {
        return  WrapperResponse.success(outptProfileFileBO.getById(outptProfileFileDTO));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>>
     **/
    @Override
    public WrapperResponse<List<OutptProfileFileDTO>> queryAll(OutptProfileFileDTO outptProfileFileDTO) {
        return WrapperResponse.success(outptProfileFileBO.queryAll(outptProfileFileDTO));
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(OutptProfileFileDTO outptProfileFileDTO) {
        return WrapperResponse.success(outptProfileFileBO.queryPage(outptProfileFileDTO));
    }

    /**
     * @Method save
     * @Desrciption 保存/新增个人档案 统一接口
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<OutptProfileFileExtendDTO> save(OutptProfileFileDTO outptProfileFileDTO) {
        return WrapperResponse.success(outptProfileFileBO.save(outptProfileFileDTO));
    }

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时只做判断身份证是否重复
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 13:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> isCertNoExist(OutptProfileFileDTO outptProfileFileDTO) {
        return WrapperResponse.success(outptProfileFileBO.isCertNoExist(outptProfileFileDTO));
    }

    /**
     * @Method getExtendByProfileId
     * @Desrciption 通过档案ID和医院编码拿从表数据
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:01
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO>
     **/
    @Override
    public WrapperResponse<OutptProfileFileExtendDTO> getExtendByProfileId(OutptProfileFileExtendDTO outptProfileFileDTO) {
        return WrapperResponse.success(outptProfileFileBO.getExtendByProfileId(outptProfileFileDTO));
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
    public WrapperResponse<List<TreeMenuNode>> getAddressTree(OutptProfileFileDTO outptProfileFileDTO) {
        return WrapperResponse.success(TreeUtils.buildByRecursive(outptProfileFileBO.getAddressTree(outptProfileFileDTO), "-1"));
    }

    @Override
    public WrapperResponse<List<OutptProfileFileDTO>> getByIds(Map<String, Object> paramMap) {
        return WrapperResponse.success(outptProfileFileBO.getByIds(paramMap));
    }


}
