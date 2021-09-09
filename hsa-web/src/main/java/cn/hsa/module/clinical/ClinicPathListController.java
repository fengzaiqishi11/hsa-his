package cn.hsa.module.clinical;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;
import cn.hsa.module.clinical.clinicalpathlist.service.ClinicPathListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: ClinicPathListController
 * @Describe: 临床路径目录维护控制层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/clinicPathListController")
@Slf4j
public class ClinicPathListController extends BaseController {
   @Resource
   private ClinicPathListService clinicPathListService_consumer;

    @RequestMapping("getClinicPathById")
    public WrapperResponse<ClinicPathListDTO> getClinicPathById(Map map) {
      return clinicPathListService_consumer.getClinicPathById(map);
    }

    @RequestMapping("queryClinicPathAll")
    public WrapperResponse<List<ClinicPathListDTO>> queryClinicPathAll(Map map) {
      return clinicPathListService_consumer.queryClinicPathAll(map);
    }

    @RequestMapping("queryClinicPathPage")
    public WrapperResponse<PageDTO> queryClinicPathPage(Map map) {
      return clinicPathListService_consumer.queryClinicPathPage(map);
    }

    @RequestMapping("insertClinicPath")
    public WrapperResponse<Boolean> insertClinicPath(Map map) {
      return clinicPathListService_consumer.insertClinicPath(map);
    }

    @RequestMapping("updateClinicPath")
    public WrapperResponse<Boolean> updateClinicPath(Map map) {
      return clinicPathListService_consumer.updateClinicPath(map);
    }

    @RequestMapping("deleteClinicPathById")
    public WrapperResponse<Boolean> deleteClinicPathById(Map map) {
      return clinicPathListService_consumer.deleteClinicPathById(map);
    }
}
