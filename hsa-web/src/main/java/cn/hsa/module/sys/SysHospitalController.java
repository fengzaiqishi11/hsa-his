package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.syshospital.service.SysHospitalService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.FileUtils;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys
 * @Class_name:: sysHospitalController
 * @Description: 医院信息管理控制层
 * @Author: zhangxuan
 * @Date: 2020-07-30 10:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/sys/hospital")
@Slf4j
public class SysHospitalController extends BaseController {
    /**
     * 医院信息维护dubbo消费者接口
     */
    @Resource
    private SysHospitalService sysHospitalService_consumer;

    /**
     * 系统管理dubbo消费者接口
     */
    @Resource
    private SysParameterService sysParameterService_consumer;
/**
 * @Method
 * @Desrciption  根据id和医院编码查询
 * @Param
 *  id(主键),code（医院编码）
 * @Author zhangxuan
 * @Date   2020-07-30 11:01
 * @Return sysHospitalDTO
**/
    @GetMapping("/getByHospCode")
    public WrapperResponse<CenterHospitalDTO> getByHospCode(CenterHospitalDTO centerHospitalDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        centerHospitalDTO.setCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("centerHospitalDTO",centerHospitalDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return sysHospitalService_consumer.getByHospCode(map);
    }
    /**
     * @Method
     * @Desrciption 修改医院信息
     * @Param
     * SysHospatilDTO
     * @Author zhangxuan
     * @Date   2020-07-30 10:52
     * @Return sysHospitalDTO
    **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody CenterHospitalDTO centerHospitalDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        centerHospitalDTO.setCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("centerHospitalDTO",centerHospitalDTO);
        map.put("hospCode",sysUserDTO.getHospCode());
        return sysHospitalService_consumer.update(map);
    }
    /**
     * @Method
     * @Desrciption
     * @Param
     * 1.MultipartFile
     * @Author zhangxuan
     * @Date   2020-09-02 20:22
     * @Return  WrapperResponse
    **/
    // 提交上传文件
    @RequestMapping("/upload")
    public WrapperResponse<String> uploadFile(@RequestParam("picture") MultipartFile file, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map paramMap = new HashMap();
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        paramMap.put("codeList", new String[]{"FILE_UPLOAD_PATH", "FILE_UPLOAD_URL"});
        WrapperResponse<Map<String, SysParameterDTO>> wr = sysParameterService_consumer.getParameterByCodeList(paramMap);
        Map<String, SysParameterDTO> map = getData(wr);
        SysParameterDTO pathDto = MapUtils.get(map, "FILE_UPLOAD_PATH");
        if (pathDto == null) {
            throw new AppException("医院编码【" + sysUserDTO.getHospCode() + "】未配置文件上传地址");
        }
        SysParameterDTO urlDto = MapUtils.get(map, "FILE_UPLOAD_URL");
        if (urlDto == null) {
            throw new AppException("医院编码【" + sysUserDTO.getHospCode() + "】未配置文件访问地址");
        }

        String filePath = FileUtils.uploadFile(file, pathDto.getValue(), sysUserDTO.getHospCode());
        return WrapperResponse.success(urlDto.getValue() + "/" + filePath);
    }
}
