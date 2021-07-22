package cn.hsa.module.center.outptprofilefile.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.profilefile.bo
 * @Class_name:: OutptProfileFileBO
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/19 17:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptProfileFileBO {

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:00
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO
     **/
    OutptProfileFileDTO getById(OutptProfileFileDTO centerProfileFileDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询所有个人档案
     * @Param [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:00
     * @Return java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>
     **/
    List<OutptProfileFileDTO> queryAll(OutptProfileFileDTO centerProfileFileDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询个人档案
     * @Param [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:00
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(OutptProfileFileDTO centerProfileFileDTO);

    /**
     * @Method save
     * @Desrciption 保存/新增个人档案 统一接口
     * @Param [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:00
     * @Return java.lang.Boolean
     **/
    OutptProfileFileExtendDTO save(OutptProfileFileDTO centerProfileFileDTO);

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时只做判断身份证是否重复
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:00
     * @Return java.lang.Boolean
     **/
    Boolean isCertNoExist(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method getExtendByProfileId
     * @Desrciption 通过档案id和医院编码查询档案从表记录
     * @Param [outptProfileFileExtendDTO]
     * @Author liaojunjie
     * @Date 2020/9/22 16:51
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO
     **/
    OutptProfileFileExtendDTO getExtendByProfileId(OutptProfileFileExtendDTO outptProfileFileExtendDTO);

    /**
     * @Method getAddressTree
     * @Desrciption 获取码表地址树
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/11/12 11:10
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getAddressTree(OutptProfileFileDTO outptProfileFileDTO);

    List<OutptProfileFileDTO> getByIds(Map<String, Object> paramMap);
}
