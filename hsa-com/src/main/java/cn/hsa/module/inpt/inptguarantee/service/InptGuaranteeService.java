package cn.hsa.module.inpt.inptguarantee.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * 住院担保管理(InptGuarantee)表服务接口
 *
 * @author makejava
 * @since 2021-08-10 15:01:34
 */
@FeignClient(value = "hsa-inpt")
public interface InptGuaranteeService {

    /**
    * @Menthod queryById
    * @Desrciption 查询单个保证金信息
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:56
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>
    **/
    WrapperResponse<InptGuaranteeDTO> queryById(Map map);

    /**
    * @Menthod queryAllInptGuarantee
    * @Desrciption 查询所有保证金
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:56
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.inptguarantee.dto.InptGuaranteeDTO>>
    **/
    WrapperResponse<List<InptGuaranteeDTO>> queryAllInptGuarantee(Map map);

    /**
    * @Menthod queryPageInptGuarantee
    * @Desrciption 分页查询所有保证金
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:56
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    WrapperResponse<PageDTO> queryPageInptGuarantee(Map map);

    /**
    * @Menthod save
    * @Desrciption 保存
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:56
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> save(Map map);

    /**
    * @Menthod updateAuditCode
    * @Desrciption 修改状态
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/8/10 15:57
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    WrapperResponse<Boolean> updateAuditCode(Map map);

}
