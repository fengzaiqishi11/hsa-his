package cn.hsa.interf.healthInfo.controller;

import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.healthInfo.service.*;
import cn.hsa.util.MapUtils;
import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.print.attribute.HashAttributeSet;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 浏阳接口通用Controller
 * @author liudawen
 * @date 2022/5/20
 */
@RequestMapping("/interf/healthInfo")
@RestController
@Slf4j
public class HealthInfoUniversalController {

    @Value("${rsa.private.key}")
    private String privateKey;
    /**
     * 字典标准映射
     */
    @Resource
    private DictStandardMapService dictStandardMapService;
    /**
     * 实验室检验报告
     */
    @Resource
    private LabSurveyReportService labSurveyReportService;
    /**
     * 本地字典
     */
    @Resource
    private LocalBasicDictService localBasicDictService;
    /**
     * 医学影像
     */
    @Resource
    private MedicalImageService medicalImageService;
    /**
     * 体检业务
     */
    @Resource
    private PhysBusinessService physBusinessService;
    /**
     *  住院
     */
    @Resource
    private HealthInptInfoService healthInptInfoService;
    /**
     * 门诊
     */
    @Resource
    private HealthOutptInfoService healthOutptInfoService;
    /**
     * 病案
     */
    @Resource
    private HealthMrisInfoService healthMrisInfoService;
    /**
     * 诊断
     */
    @Resource
    private HealthDiagnoseInfoService healthDiagnoseInfoService;
    /**
     * 手术
     */
    @Resource
    private HealthOperInfoService healthOperInfoService;
    /**
     * 财务报告
     */
    @Resource
    private HealthSettleInfoService healthSettleInfoService;
    /**
     * 药房药库及药品类统计分析
     */
    @Resource
    private DrugBusinessService drugBusinessService;

    /**
     * 查询实验室报告相关数据接口
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 9:34
     **/
    @GetMapping("/getLabSurveyReport")
    public WrapperResponse getLabSurveyReport(@RequestParam Map map){
        return universalMethodInvoke(map,LabSurveyReportService.class,labSurveyReportService);
    }

    /**
     * 查询本地字典与标准值域映射相关数据接口
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 11:01
     **/
    @GetMapping("/getDictStandardMap")
    public WrapperResponse getDictStandardMap(@RequestParam Map map){
        return universalMethodInvoke(map,DictStandardMapService.class,dictStandardMapService);
    }

    /**
     * 查询本地字典相关数据接口
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 11:02
     **/
    @GetMapping("/getLocalBasicDict")
    public WrapperResponse getLocalBasicDict(@RequestParam Map map){
        return universalMethodInvoke(map,LocalBasicDictService.class,localBasicDictService);
    }

    /**
     * 查询医学影像报告相关数据接口
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 11:02
     **/
    @GetMapping("/getMedicalImage")
    public WrapperResponse getMedicalImage(@RequestParam Map map){
        return universalMethodInvoke(map,MedicalImageService.class,medicalImageService);
    }

    /**
     * 查询体检业务相关数据接口
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 11:02
     **/
    @GetMapping("/getPhysBusiness")
    public WrapperResponse getPhysBusiness(@RequestParam Map map){
        return universalMethodInvoke(map,PhysBusinessService.class,physBusinessService);
    }

    /**
     * 查询门诊业务相关数据接口
     * @Author liuliyun
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 16:52
     **/
    @GetMapping("/getOutptInfo")
    public WrapperResponse getOutptInfo(@RequestParam Map map){
        return universalMethodInvoke(map,HealthOutptInfoService.class,healthOutptInfoService);
    }

    /**
     * 查询住院业务相关数据接口
     * @Author liuliyun
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 16:52
     **/
    @GetMapping("/getInptInfo")
    public WrapperResponse getInptInfo(@RequestParam Map map){
        return universalMethodInvoke(map,HealthInptInfoService.class,healthInptInfoService);
    }

    /**
     * 查询病案首页业务相关数据接口
     * @Author liuliyun
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 16:56
     **/
    @GetMapping("/getMrisInfo")
    public WrapperResponse getMrisInfo(@RequestParam Map map){
        return universalMethodInvoke(map,HealthMrisInfoService.class,healthMrisInfoService);
    }

    /**
     * 查询诊断信息业务相关数据接口
     * @Author liuliyun
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 16:56
     **/
    @GetMapping("/getDiagnoseInfo")
    public WrapperResponse getDiagnoseInfo(@RequestParam Map map){
        return universalMethodInvoke(map,HealthDiagnoseInfoService.class,healthDiagnoseInfoService);
    }

    /**
     * 查询手术业务相关数据接口
     * @Author liuliyun
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 16:57
     **/
    @GetMapping("/getOperInfo")
    public WrapperResponse getOperInfo(@RequestParam Map map){
        return universalMethodInvoke(map,HealthOperInfoService.class,healthOperInfoService);
    }

    /**
     * 查询结算业务相关数据接口
     * @Author liuliyun
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 16:57
     **/
    @GetMapping("/getSettleInfo")
    public WrapperResponse getSettleInfo(@RequestParam Map map){
        return universalMethodInvoke(map,HealthSettleInfoService.class,healthSettleInfoService);
    }

    /**
     * 查询药品库存，使用情况业务相关数据接口
     * @Author liudawen
     * @Param [map]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 11:02
     **/
    @GetMapping("/getDrugBusiness")
    public WrapperResponse getDrugBusiness(@RequestParam Map map){
        return universalMethodInvoke(map,DrugBusinessService.class,drugBusinessService);
    }

    /**
     * 接口服务方法统一调用入口
     * map：请求接口的参数
     * clazz：请求的服务类
     * service：服务类的注入bean
     * @Author liudawen
     * @Param [map, clazz, service]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Throws
     * @Date 2022/5/20 10:20
     **/
    private WrapperResponse universalMethodInvoke(Map map,Class<?> clazz,Object service) {
        // 医院机构his编码密文
        String hospCode = MapUtils.get(map, "hospCode");
        // 请求的方法名
        String method = MapUtils.get(map, "method");
        // 查询的开始日期
        String startTime = MapUtils.get(map, "startTime");
        // 查询的结束日期
        String endTime = MapUtils.get(map, "endTime");
        // 这个入参根据自身接口的需要传入，由于我写的sql查询关联的医疗机构编码参数没有写死， 所以需要传入一个参数编码查询医保机构编码
        String sysParameter = MapUtils.get(map, "sysParameter");

        // 参数断言，断言不成立则抛出异常
        Assert.notBlank(hospCode, () -> new AppException("医院编码密文不能为空"));
        Assert.notBlank(method, () -> new AppException("请求接口方法名不能为空"));
        Assert.notBlank(startTime, () -> new AppException("查询的开始日期不能为空"));
        Assert.notEmpty(endTime, () -> new AppException("查询的结束日期不能为空"));
        try{
            hospCode = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(hospCode.getBytes()), privateKey);
            map.put("hospCode",hospCode);
            Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(clazz);
            Map<String, Method> methodMap = Arrays.stream(allDeclaredMethods).collect(Collectors.toMap(Method::getName, m -> m));
            Assert.isTrue(methodMap.containsKey(method),() -> new AppException(clazz.getName()+"不存在【" + method + "】方法"));
            return (WrapperResponse) methodMap.get(method).invoke(service,map);
        }catch(Exception e){
            throw new AppException(e.getMessage(),e);
        }
    }
}
