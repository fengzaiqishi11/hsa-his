package cn.hsa.module.center.code.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.code.dto.CenterCodeDTO;
import cn.hsa.module.center.code.dto.CenterCodeDetailDTO;
import cn.hsa.module.center.code.dto.CenterCodeSelectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.base.code.bo
* @class_name: CenterCodeBO
* @Description: 值域代码BO接口
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:31
* @Company: 创智和宇
**/
@FeignClient(value = "hsa-center")
public interface CenterCodeService {

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    @GetMapping("/service/center/code/getByCode")
    WrapperResponse<Map<String, List<CenterCodeSelectDTO>>> getByCode(Map map);

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/center/code/queryCodePage")
    WrapperResponse<PageDTO> queryCodePage(Map map);

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [baseCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/center/code/queryCodeDetailPage")
    WrapperResponse<PageDTO> queryCodeDetailPage(Map map);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.CenterCodeDTO>
     **/
    @GetMapping("/service/center/code/getCodeById")
    WrapperResponse<CenterCodeDTO> getCodeById(Map map);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.CenterCodeDTO>
     **/
    @GetMapping("/service/center/code/getCodeDetailById")
    WrapperResponse<CenterCodeDetailDTO> getCodeDetailById(Map map);

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.code.dto.CenterCodeDetailDTO>
    **/
    @GetMapping("/service/center/code/getMaxSeqno")
    WrapperResponse<Integer> getMaxSeqno(Map map);

    /**
     * @Method: insertCode
     * @Description: 新增值域代码
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/code/saveCode")
    WrapperResponse<Boolean> saveCode(Map map);

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/code/saveCodeDetail")
    WrapperResponse<Boolean> saveCodeDetail(Map map);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/code/deleteCodes")
    WrapperResponse<Boolean> deleteCodes(Map map);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码值
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/code/deleteCodeDetails")
    WrapperResponse<Boolean> deleteCodeDetails(Map map);

    /**
     * @Method: getCodeTree
     * @Description: 根据编码获取值域代码值树形结构
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     *
     * @return*/
    @GetMapping("/service/center/code/getCodeTree")
    WrapperResponse<List<TreeMenuNode>> getCodeTree(Map map);
}
