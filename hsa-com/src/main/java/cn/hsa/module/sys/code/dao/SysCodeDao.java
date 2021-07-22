package cn.hsa.module.sys.code.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
* @Package_name: cn.hsa.module.base.code.dao
* @class_name: SysCodeDao
* @Description: 值域代码DAO接口
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:40
* @Company: 创智和宇
**/
public interface SysCodeDao {

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    List<SysCodeDTO> getByCode(@Param("codes") List<String> codes, @Param("hospCode") String hospCode);

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    List<SysCodeDTO> queryCodePage(SysCodeDTO sysCodeDTO);

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [baseCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    List<SysCodeDetailDTO> queryCodeDetailPage(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.SysCodeDTO>
     **/
    SysCodeDTO getCodeById(@Param("id") String id, @Param("hospCode") String hospCode);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.SysCodeDTO>
     **/
    SysCodeDetailDTO getCodeDetailById(@Param("id") String id, @Param("hospCode") String hospCode);

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
    int insertCode(SysCodeDTO sysCodeDTO);

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int insertCodeDetail(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Method: updateCode
     * @Description: 更新值域代码
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:09
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int updateCode(SysCodeDTO sysCodeDTO);

    /**
     * @Method: updateCode
     * @Description: 更新值域代码值
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:09
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int updateCodeDetail(SysCodeDetailDTO sysCodeDetailDTO);

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
    int updateCodeDetailByCode(@Param("code") String code,@Param("oldCode")String oldCode,@Param("hospCode") String hospCode);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码
     * @Param: [ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int deleteCodes(SysCodeDTO sysCodeDTO);

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码值
     * @Param: [ids]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    int deleteCodeDetails(SysCodeDetailDTO sysCodeDetailDTO);

    /**
    * @Method: getCodeCountByCode
    * @Description: 根据编码查询数量
    * @Param: code
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/7/17 16:13
    * @Return: int
    **/
    int getCodeCount(@Param("code") String code, @Param("hospCode") String hospCode, @Param("id") String id);

    /**
     * @Method: getCodeCountByCode
     * @Description: 根据编码查询数量
     * @Param: code
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/17 16:13
     * @Return: int
     **/
    int getCodeDetailCount(@Param("code") String code, @Param("hospCode") String hospCode, @Param("id") String id, @Param("value") String value);

    /**
     * @Method: getCodeTree
     * @Description: 根据code查询树值域
     * @Param: [code]
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/18 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.SysCodeDTO>
     **/
    List<TreeMenuNode> getCodeTree(@Param("code") String code, @Param("hospCode") String hospCode);


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

    List<TreeMenuNode> queryFathersCode(SysCodeDetailDTO sysCodeDetailDTO);

    /**
     * @Method: getInsureDictByCode
     * @Description: 根据医保值域代码获取值域代码值
     * @Param: [map]
     * @Author: liaojiguang
     * @Email: 847025096@qq.com
     * @Date: 2020/12/17 21:31
     * @Return:<java.lang.String,java.util.List<InsureDictDTO>
     **/
    List<InsureDictDTO> getInsureDictByCode(@Param("codes") List<String> codes, @Param("hospCode") String hospCode);

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
}
