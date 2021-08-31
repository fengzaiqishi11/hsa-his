package cn.hsa.module.center;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;
import cn.hsa.module.center.nationstandardmaterial.service.NationStandardMaterialService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.ListUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.datasource.controller
 * @Class_name: CenterDatasourceController
 * @Describe: 国家标准材料Controller
 * @Author: xingyu.xie
 * @Email: oubo@powersi.com.cn
 * @Date: 2021/1/26 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("web/center/nationStandardMaterial")
public class NationStandardMaterialController extends BaseController {


    @Resource
    private NationStandardMaterialService nationStandardMaterialService_consumer;

    /**
     * @Menthod getById
     * @Desrciption
     * @param nationStandardMaterialDTO 根据id查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<NationStandardMaterialDTO> getById(NationStandardMaterialDTO nationStandardMaterialDTO){
        return nationStandardMaterialService_consumer.getById(nationStandardMaterialDTO);
    }


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardMaterialDTO 查询所有国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<NationStandardMaterialDTO>> queryAll(NationStandardMaterialDTO nationStandardMaterialDTO){
        return nationStandardMaterialService_consumer.queryAll(nationStandardMaterialDTO);
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardMaterialDTO 分页查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(NationStandardMaterialDTO nationStandardMaterialDTO){
        return nationStandardMaterialService_consumer.queryPage(nationStandardMaterialDTO);
    }

    /**
       * @Describe: 保存国家标准材料信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/6 16:42
    **/
    @PostMapping("/saveMaterial")
    public WrapperResponse<Boolean> saveMaterial(@RequestBody NationStandardMaterialDO nationStandardMaterialDO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        nationStandardMaterialDO.setCrteId(sysUserDTO.getId());
        nationStandardMaterialDO.setCrteName(sysUserDTO.getName());
        map.put("nationStandardMaterialDO", nationStandardMaterialDO);
        return null;
    }
}