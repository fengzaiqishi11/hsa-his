package cn.hsa.base.pym;

/**
 * @Package_ame: cn.hsa.base
 * @Class_name: hsa-his
 * @Description:
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/12/6  16:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

import cn.hsa.module.base.pym.service.PymService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("base/pym")
@Slf4j
public class Pym {
    @Resource
    private PymService pymService;

    @GetMapping("updatePym")
    public Boolean updatePym(String hospCode){
        Map map = new HashMap();
        map.put("hospCode",hospCode);
        return pymService.updatePym(map);
    }

}
