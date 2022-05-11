package cn.hsa.module.sys.code.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
@FeignClient(value = "hsa-sys")
public interface SysCodeService {

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    @GetMapping("/service/sys/code/getByCode")
    WrapperResponse<Map<String, List<SysCodeSelectDTO>>> getByCode(Map map);
    /**
     *  根据编码获取值域代码值优先从缓存获取
     * @Method: getCodeDetailsByCodeCache
     * @Description:
     * @Param: [code]
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    WrapperResponse<Map<String, List<SysCodeSelectDTO>>> getCodeDetailsByCodeCache(Map map);

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [baseCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/sys/code/queryCodePage")
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
    @GetMapping("/service/sys/code/queryCodeDetailPage")
    WrapperResponse<PageDTO> queryCodeDetailPage(Map map);

    /**
    * @Menthod queryCodeDetailAll
    * @Desrciption 获取全部值域代码值列表
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/10/15 14:22
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/service/sys/code/queryCodeDetailAll")
    WrapperResponse<List<SysCodeDetailDTO>> queryCodeDetailAll(Map map);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.SysCodeDTO>
     **/
    @GetMapping("/service/sys/code/getCodeById")
    WrapperResponse<SysCodeDTO> getCodeById(Map map);

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.code.dto.SysCodeDTO>
     **/
    @GetMapping("/service/sys/code/getCodeDetailById")
    WrapperResponse<SysCodeDetailDTO> getCodeDetailById(Map map);

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:18
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.SysCodeDetailDTO>
    **/
    @GetMapping("/service/sys/code/getMaxSeqno")
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
    @PostMapping("/service/sys/code/saveCode")
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
    @PostMapping("/service/sys/code/saveCodeDetail")
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
    @PostMapping("/service/sys/code/deleteCodes")
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
    @PostMapping("/service/sys/code/deleteCodeDetails")
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
    @GetMapping("/service/sys/code/getCodeTree")
    WrapperResponse<List<TreeMenuNode>> getCodeTree(Map map);

    /**
     * @Method getCodeData
     * @Desrciption 获取节点LIST
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/29 20:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("/service/sys/code/getCodeTree")
    WrapperResponse<List<TreeMenuNode>> getCodeData(Map map);

    /**
     * @Method queryFathers
     * @Desrciption 根据子代查询祖宗的编码(返回值包括子节点的值)
     * @Param
     * [map]
     * value:存储子节点的code,
     * hospCode:医院编码，
     * sysCodeDetailDTO：对象（需要在其中设置医院编码,设置需要查询的c_code）
     * @Author liaojunjie
     * @Date   2020/11/24 16:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    WrapperResponse<List<String>> queryFathers(Map map);


    /**
     * @Method queryNationCode()
     * @Desrciption  维护科室信息的国家编码
     * @Param hospCode医院编码 nationCode 国家编码值
     *
     * @Author fuhui
     * @Date   2020/10/30 0:32
     * @Return TreeMenuNode树集合
     **/
    @GetMapping("/service/sys/code/queryNationCode")
    List<TreeMenuNode> queryNationCode(Map map);

    /**
     * @Menthod getInsureDict
     * @Desrciption 获取医保字典信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/18 11:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/service/sys/code/getInsureDict")
    WrapperResponse getInsureDict(Map<String,String> param);

    /**
     * @Menthod: getCodeDetailByValue
     * @Desrciption: 根据值域代码值查询出单个具体的值域代码值对象
     * @Param: sysCodeDetailDTO[c_code, value] c_code值域代码 value代码值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-17 16:10
     * @Return: SysCodeDetailDTO
     **/
    @GetMapping("/service/sys/code/getCodeDetailByValue")
    WrapperResponse<SysCodeDetailDTO> getCodeDetailByValue(Map map);
    /**
     * @Author gory
     * @Description 启用作废的值域明细
     * @Date 2022/5/9 16:29
     * @Param [map]
     **/
    @GetMapping("/service/sys/code/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

}
