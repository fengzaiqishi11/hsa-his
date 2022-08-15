package cn.hsa.module.center.centerfunction.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.centerfunction.dto.CenterFunctionDTO;
import cn.hsa.module.center.centerfunction.dto.CenterFunctionDetailDTO;
import cn.hsa.module.center.centerfunction.entity.CenterFunctionDetailDo;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.base.code.bo
* @class_name: CenterFunctionBO
* @Description: 值域代码BO接口
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:31
* @Company: 创智和宇
**/
public interface CenterFunctionBO {

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/

    Map<String, List<CenterFunctionDetailDTO>> getByCode(String code);

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO queryCodePage(CenterFunctionDTO centerFunctionDTO);

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [baseCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO queryCodeDetailPage(CenterFunctionDetailDTO centerFunctionDetailDTO);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.CenterFunctionDTO>
     **/
    CenterFunctionDTO getCodeById(String id);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.CenterFunctionDTO>
     **/
    CenterFunctionDetailDTO getCodeDetailById(String id);

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:19
    * @Return int
    **/
    int getMaxSeqno(String code);

    /**
     * @Method: insertCode
     * @Description: 新增值域代码
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean saveCode(CenterFunctionDTO centerFunctionDTO);

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean saveCodeDetail(CenterFunctionDetailDTO centerFunctionDetailDTO);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean deleteCodes(CenterFunctionDTO centerFunctionDTO);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码值
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean deleteCodeDetails(CenterFunctionDetailDTO centerFunctionDetailDTO);

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    List<TreeMenuNode> getCodeTree(String code);
}
