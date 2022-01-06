package cn.hsa.center.cache.controller;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.cache.service.CacheService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/center/cache")
@Slf4j
public class CacheController {

    @Resource
    private CacheService cacheService;

    @GetMapping("/refreshCodeDetailsCache/{hospCodes}")
    public WrapperResponse<Boolean> refreshCodeDetailsCacheByHospCode(@PathVariable("hospCodes") String strHospCodes){
        Optional.ofNullable(strHospCodes).orElseThrow(()-> new AppException("必传医院编码不能为空,多个医院之间以','分割"));
        String[] hospCodes = strHospCodes.split(",");
        Boolean result = Boolean.TRUE;
        String retMsg = "";
        for(String hospCode : hospCodes){
            Map<String,String> params = new HashMap<>();
            params.put("hospCode",hospCode);
            try{
                result &= cacheService.refreshCodeDetailsCacheByHospCode(params).getData();
            }catch(Exception e){
                log.error("更新缓存出现异常: 医院编码为：{}",hospCode,e);
                result = Boolean.FALSE;
                retMsg = e.getMessage();
            }
        }
        return WrapperResponse.success(retMsg,result);
    }

    /**
     *  刷新数据源缓存信息
     * @return java.lang.Boolean
     */
    @GetMapping("/refreshCenterHospitalDatasource")
    public WrapperResponse<Boolean> refreshCenterHospitalDatasource(){
        return cacheService.refreshCenterHospitalDatasource();
    }
}
