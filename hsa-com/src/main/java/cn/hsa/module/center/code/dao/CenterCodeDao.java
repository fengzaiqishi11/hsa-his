package cn.hsa.module.center.code.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.code.dto.CenterCodeDTO;
import cn.hsa.module.center.code.dto.CenterCodeDetailDTO;
import cn.hsa.module.center.code.dto.CenterCodeSelectDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @Package_name: cn.hsa.module.base.code.dao
* @class_name: CenterCodeDao
* @Description: 值域代码DAO接口
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:40
* @Company: 创智和宇
**/
public interface CenterCodeDao {

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    List<CenterCodeSelectDTO> getByCode(@Param("codes") List<String> codes);

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    List<CenterCodeDTO> queryCodePage(CenterCodeDTO centerCodeDTO);

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [baseCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    List<CenterCodeDetailDTO> queryCodeDetailPage(CenterCodeDetailDTO centerCodeDetailDTO);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.CenterCodeDTO>
     **/
    CenterCodeDTO getCodeById(@Param("id") String id);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.CenterCodeDTO>
     **/
    CenterCodeDetailDTO getCodeDetailById(@Param("id") String id);

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * [code, hospCode]
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:25
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
    int insertCode(CenterCodeDTO centerCodeDTO);

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int insertCodeDetail(CenterCodeDetailDTO centerCodeDetailDTO);

    /**
     * @Method: updateCode
     * @Description: 更新值域代码
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:09
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int updateCode(CenterCodeDTO centerCodeDTO);

    /**
     * @Method: updateCode
     * @Description: 更新值域代码值
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:09
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int updateCodeDetail(CenterCodeDetailDTO centerCodeDetailDTO);

    /**
    * @Menthod updateCodeDetailByCode
    * @Desrciption 根据值域主表修改从表
    *
    * @Param
    * [code, oldCode]
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 11:10
    * @Return int
    **/
    int updateCodeDetailByCode(@Param("code") String code, @Param("oldCode") String oldCode);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码
     * @Param: [ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int deleteCodes(CenterCodeDTO centerCodeDTO);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码值
     * @Param: [ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int deleteCodeDetails(CenterCodeDetailDTO centerCodeDetailDTO);

    /**
    * @Method: getCodeCountByCode
    * @Description: 根据编码查询数量
    * @Param: code
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/7/17 16:13
    * @Return: int
    **/
    int getCodeCount(@Param("code") String code, @Param("id") String id);

    /**
     * @Method: getCodeCountByCode
     * @Description: 根据编码查询数量
     * @Param: code
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/17 16:13
     * @Return: int
     **/
    int getCodeDetailCount(@Param("code") String code, @Param("id") String id, @Param("value") String value);

    /**
     * @Method: getCodeTree
     * @Description: 根据code查询树值域
     * @Param: [code]
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/18 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.CenterCodeDTO>
     **/
    List<TreeMenuNode> getCodeTree(@Param("code") String code);
}
