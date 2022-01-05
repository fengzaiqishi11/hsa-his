package cn.hsa.module.search;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugService;
import cn.hsa.module.interf.search.service.SearchableNationStandardDrugService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/web/search/baseDrug")
public class NationStandardController extends BaseController {

    @Resource
    private SearchableNationStandardDrugService searchableDrugService;
    /**
     * <p>原中心端查询接口,
     * <p>根据系统参数 WHETHER_TO_USE_ES 进行判断是走原中心端搜索接口还是es搜索接口
     */
    @Resource
    private NationStandardDrugService centerNationStandardDrugService_consumer;

    /** 系统公共参数查询服务 **/
    @Resource
    private SysParameterService sysParameterService_consumer;
    /**
     *  医院编码与省份编码(一般不会轻易改变,可以用作缓存)对应关系缓存容器
     */
    private static final ConcurrentMap<String,String>  hospCodeProvinceCodeMapping = new ConcurrentHashMap<>(120);

    /**
     *  根据搜索条件查询国标药品信息(中西药)
     * @param queryCondition 搜索查询条件
     * @return  cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/queryNationDrugByCond")
    public WrapperResponse<PageDTO> queryByCond(NationStandardDrugDTO queryCondition){
        HttpSession session = getCurrentRequest().getSession();
        SysUserDTO sysUserDTO = (SysUserDTO) session.getAttribute("SESSION_USER_INFO");;
        queryCondition.setHospCode(sysUserDTO.getHospCode());
        queryCondition.setProvinceCode(getProvinceCodeParameterObtainedFromTheCache(sysUserDTO.getHospCode()));
        Map mapPatamater = new HashMap();
        mapPatamater.put("hospCode", sysUserDTO.getHospCode());
        // 查询 医院所在省份代码
        mapPatamater.put("code", "WHETHER_TO_USE_ES");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapPatamater).getData();
        if(null == sysParameterDTO){
            return centerNationStandardDrugService_consumer.queryNationStandardDrugPage(queryCondition);
        }

        return searchableDrugService.searchByConditions(queryCondition);
    }

    /**
     *  根据医院编码获取医院所在省份代码,优先从缓存获取
     * @param hospCode 医院编码
     * @return java.lang.String
     */
    private String getProvinceCodeParameterObtainedFromTheCache(String hospCode){
        String provinceCode = Optional.ofNullable(hospCodeProvinceCodeMapping.get(hospCode)).orElseGet(
                ()->{
                    Map mapPatamater = new HashMap();
                    mapPatamater.put("hospCode", hospCode);
                    // 查询 医院所在省份代码
                    mapPatamater.put("code", "GJSDML_SF");
                    SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(mapPatamater).getData();
                    if(null == sysParameterDTO){
                        throw new AppException("未查询到医院代码为：【 "+hospCode+" 】的`国家三大目录贯标省份`系统编码参数,请维护参数后再查询！");
                    }
                    hospCodeProvinceCodeMapping.put(hospCode,sysParameterDTO.getValue());
                    return sysParameterDTO.getValue();
                }
        );
        return provinceCode;
    }

    /**
     *  使得医院编码与省份代码对应关系缓存失效
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/invalidateProvinceCodeCache")
    public WrapperResponse<Boolean> invalidateProvinceCodeCache(){
        hospCodeProvinceCodeMapping.clear();
        return WrapperResponse.success(Boolean.TRUE);
    }

    public HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new IllegalStateException("当前线程中不存在 Request 上下文");
        }
        return attrs.getRequest();
    }
}
