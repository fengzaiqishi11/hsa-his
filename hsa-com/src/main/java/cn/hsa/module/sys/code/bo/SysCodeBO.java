package cn.hsa.module.sys.code.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.base.code.bo
* @class_name: SysCodeBO
* @Description: 值域代码BO接口
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:31
* @Company: 创智和宇
**/
public interface SysCodeBO {

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    Map<String, List<SysCodeSelectDTO>> getByCode(String code, String hospCode);

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO queryCodePage(SysCodeDTO sysCodeDTO);

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [baseCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    PageDTO queryCodeDetailPage(SysCodeDetailDTO sysCodeDetailDTO);

    /**
    * @Menthod queryCodeDetailAll
    * @Desrciption 获取全部值域代码值列表
    *
    * @Param
    * [sysCodeDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/10/15 14:23
    * @Return cn.hsa.base.PageDTO
    **/
    List<SysCodeDetailDTO> queryCodeDetailAll(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.SysCodeDTO>
     **/
    SysCodeDTO getCodeById(String id, String hospCode);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.SysCodeDTO>
     **/
    SysCodeDetailDTO getCodeDetailById(String id, String hospCode);

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
    int getMaxSeqno(String code,String hospCode);

    /**
     * @Method: insertCode
     * @Description: 新增值域代码
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean saveCode(SysCodeDTO sysCodeDTO);

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean saveCodeDetail(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean deleteCodes(SysCodeDTO sysCodeDTO);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码值
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean deleteCodeDetails(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    List<TreeMenuNode> getCodeTree(String code, String hospCode);

    /**
     * @Method queryNationCode()
     * @Desrciption  维护科室信息的国家编码
     * @Param hospCode医院编码 nationCode 国家编码值
     *
     * @Author fuhui
     * @Date   2020/10/30 0:32
     * @Return TreeMenuNode树集合
     **/
    List<TreeMenuNode> queryNationCode(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Method queryFathersCode
     * @Desrciption  查询码表数（用于级联）
     * @Param
     * [sysCodeDetailDTO]
     * @Author liaojunjie
     * @Date   2020/11/25 10:16
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> queryFathersCode(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Method queryFathers
     * @Desrciption 根据子代查询祖宗的编码(返回值包括子代)
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/11/25 10:17
     * @Return java.util.List<java.lang.String>
     **/
    List<String> queryFathers(Map map);

    /**
     * @Method: getInsureDictByCode
     * @Description: 根据医保值域代码获取值域代码值
     * @Param: [map]
     * @Author: liaojiguang
     * @Email: 847025096@qq.com
     * @Date: 2020/12/17 21:31
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.util.List<InsureDictDTO>>>
     **/
    Map<String, List<InsureDictDTO>> getInsureDictByCode(String code, String hospCode);

    /**
     * @Menthod: getCodeDetailByValue
     * @Desrciption: 根据值域代码值查询出单个具体的值域代码值对象
     * @Param: sysCodeDetailDTO[c_code, value] c_code值域代码 value代码值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-17 16:10
     * @Return: SysCodeDetailDTO
     **/
    SysCodeDetailDTO getCodeDetailByValue(SysCodeDetailDTO sysCodeDetailDTO);
    /**
     * @Author gory
     * @Description 启用作废的值域明细
     * @Date 2022/5/9 16:31
     * @Param [sysCodeDetailDTO]
     **/
    Boolean updateStatus(SysCodeDetailDTO sysCodeDetailDTO);
}
