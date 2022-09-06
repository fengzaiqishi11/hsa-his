package cn.hsa.module.base.profileFile.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.profileFile.bo
 * @Class_name: BaseProfileFileBO
 * @Describe: 本地档案bo
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-30 14:39
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface BaseProfileFileBO {
    /**
     * @Menthod: save
     * @Desrciption: 保存/新增个人档案
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-30 15:38
     * @Return: Boolean
     **/
    OutptProfileFileDTO save(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Menthod: queryPage
     * @Desrciption: 分页查询所有queryPage
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-07 11:12
     * @Return: PageDTO
     **/
    PageDTO queryPage(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method isCertNoExist
     * @Desrciption  暂时只做判断身份证是否重复
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean isCertNoExist(OutptProfileFileDTO outptProfileFileDTO);

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return OutptProfileFileDTO
     **/
    OutptProfileFileDTO getById(OutptProfileFileDTO outptProfileFileDTO);

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

    List<OutptProfileFileDTO> queryBaseProfileByIds(OutptProfileFileDTO outptProfileFileDTO);

    List<OutptProfileFileDTO> queryAll(OutptProfileFileDTO outptProfileFileDTO);
    /**
     * @Method isCertNoExist
     * @Desrciption 删除档案
     * @Param
     * [id]
     * @Author yuelong.chen
     * @Date   2021/11/23 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean deleteProfileFile(Map map);

    /**
     * @Method queryCertNoIsExist
     * @Desrciption 校验新增修改档案时，身份证号是否重复
     * @Param outptProfileFileDTO
     * @Author liuliyun
     * @Date   2022/09/02 9:43
     * @Return Boolean
     **/
    Boolean queryCertNoIsExist(OutptProfileFileDTO outptProfileFileDTO);
}
