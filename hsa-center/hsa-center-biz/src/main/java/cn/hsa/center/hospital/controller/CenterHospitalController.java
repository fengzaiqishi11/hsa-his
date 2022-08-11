package cn.hsa.center.hospital.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import cn.hsa.module.center.parameter.service.CenterParameterService;
import cn.hsa.util.DateUtils;
import cn.hsa.util.FileUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name:: CenterHospitalController
 * @Description: 医院信息管理控制层
 * @Author: zhangxuan
 * @Date: 2020-07-30 10:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/hospital")
@Slf4j
public class CenterHospitalController extends CenterBaseController {

    @Value("${rsa.public.key}")
    private String rsaPublicKey;
    /**
     * 医院信息维护接口
     */
    @Resource
    private CenterHospitalService centerHospitalService;

    @Resource
    private CenterParameterService centerParameterService;

    /**
     * @Method
     * @Desrciption  根据医院编码查询
     * @Param
     *  id(主键),code（医院编码）
     * @Author zhangxuan
     * @Date   2020-07-30 11:01
     * @Return centerHospitalDTO
     **/
    @GetMapping("/getByHospCode")
    public WrapperResponse<CenterHospitalDTO> getByHospCode(String hospCode){
        return centerHospitalService.getByHospCode(hospCode);
    }
    /**
     * @Method
     * @Desrciption  根据id查询
     * @Param
     *  id(主键)
     * @Author zhangxuan
     * @Date   2020-08-28 11:01
     * @Return centerHospitalDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<CenterHospitalDTO> getById(CenterHospitalDTO centerHospitalDTO){
        return centerHospitalService.getById(centerHospitalDTO);
    }
    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [CenterHospitalDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(CenterHospitalDTO centerHospitalDTO){
        return centerHospitalService.queryPage(centerHospitalDTO);
    }
    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     * @Param
     * [1. CenterHospitalDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<CenterHospitalDTO>> queryAll(CenterHospitalDTO centerHospitalDTO){
        return this.centerHospitalService.queryAll(centerHospitalDTO);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1.[CenterHospitalDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody CenterHospitalDTO centerHospitalDTO){
        centerHospitalDTO.setCrteId(userId);
        centerHospitalDTO.setCrteName(userName);
        String strStartDate = DateUtils.format(centerHospitalDTO.getStartDate(),DateUtils.Y_M_DH_M_S);
        String strEndDate = DateUtils.format(centerHospitalDTO.getEndDate(),DateUtils.Y_M_DH_M_S);
        String strStartDate2Encrypted = strStartDate+'&'+centerHospitalDTO.getCode();
        String strEndDate2Encrypted = strEndDate+'&'+centerHospitalDTO.getCode();
        try {
            strStartDate = RSAUtil.encryptByPublicKey(strStartDate2Encrypted.getBytes(), rsaPublicKey);
            strEndDate = RSAUtil.encryptByPublicKey(strEndDate2Encrypted.getBytes(), rsaPublicKey);
            centerHospitalDTO.setEncryptStartDate(strStartDate);
            centerHospitalDTO.setEncryptEndDate(strEndDate);
        } catch (Exception e) {
            throw new AppException("获取服务时间失败,原因："+e.getMessage());
        }
        return centerHospitalService.insert(centerHospitalDTO);
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除参数
     * @Param
     * 1. [ids] 主键id
     * @Author zhangxuan
     * @Date   2020/7/28 16:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody CenterHospitalDTO centerHospitalDTO){
        return centerHospitalService.delete(centerHospitalDTO);
    }

    /**
     * @Method
     * @Desrciption 修改医院信息
     * @Param
     * centerHospatilDTO
     * @Author zhangxuan
     * @Date   2020-07-30 10:52
     * @Return centerHospitalDTO
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody CenterHospitalDTO centerHospitalDTO){
        return centerHospitalService.update(centerHospitalDTO);
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
    public WrapperResponse<String> uploadFile(@RequestParam("picture") MultipartFile file) {
        WrapperResponse<Map<String, CenterParameterDTO>> wr = centerParameterService.getParameterByCodeList("FILE_UPLOAD_PATH", "FILE_UPLOAD_URL");
        Map<String, CenterParameterDTO> map = getData(wr);
        CenterParameterDTO pathDto = MapUtils.get(map, "FILE_UPLOAD_PATH");
        if (pathDto == null) {
            throw new AppException("中心端系统参数未配置文件上传地址");
        }
        CenterParameterDTO urlDto = MapUtils.get(map, "FILE_UPLOAD_URL");
        if (urlDto == null) {
            throw new AppException("中心端系统参数未配置文件访问地址");
        }

        String filePath = FileUtils.uploadFile(file, pathDto.getValue(), "");
        return WrapperResponse.success(urlDto.getValue() + "/" + filePath);
    }

    @GetMapping(value = "/getHospUrl")
    public WrapperResponse getHospUrl(CenterHospitalDTO centerHospitalDTO){
        WrapperResponse<Map<String, CenterParameterDTO>> wr = centerParameterService.getParameterByCodeList("WEB_URL");
        Map<String, CenterParameterDTO> map = getData(wr);
        CenterParameterDTO pathDto = MapUtils.get(map, "WEB_URL");
        String url = "";
        if(pathDto == null){
            url = request.getScheme() + "://" +request.getServerName() + ":" + request.getServerPort() + "/his-web/?hospCode=";
        }else{
            url = pathDto.getValue();
        }
        String rsaKey = "";
        try {
            rsaKey = RSAUtil.encryptByPublicKey(centerHospitalDTO.getCode().getBytes(), rsaPublicKey);
            rsaKey = URLEncoder.encode(rsaKey);
        } catch (Exception e) {
            throw new AppException("获取访问地址失败,原因："+e.getMessage());
        }
        url = url + rsaKey;
        return WrapperResponse.success(url);
    }



    /**
     * @Menthod queryCenterSyncFlows()
     * @Desrciption  查询某一医院同步信息
     * @Param
     * [1. CenterHospitalDTO]
     * @Author zhangxuan
     * @Date   2022/3/21 14:45
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryCenterSyncFlows")
    public WrapperResponse<List<CenterSyncFlowDto>> queryCenterSyncFlows(CenterSyncFlowDto centerSyncFlowDto){
        return this.centerHospitalService.queryCenterSyncFlows(centerSyncFlowDto);
    }

    /**
     * @Menthod auditHosp()
     * @Desrciption 审核医院
     * @Param
     * 1.[CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/3/21 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    @PostMapping("/auditHosp")
    public WrapperResponse<Boolean> updateAudit(@RequestBody CenterHospitalDTO centerHospitalDTO){
        centerHospitalDTO.setCrteId(userId);
        centerHospitalDTO.setCrteName(userName);
        return centerHospitalService.updateAudit(centerHospitalDTO);
    }

    /**
     * @Menthod updateRootBase()
     * @Desrciption 审核医院
     * @Param
     * 1.[CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/3/21 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    @PostMapping("/updateRootBase")
    public WrapperResponse<Boolean> updateRootBase(@RequestBody CenterRootDatabaseBO centerRootDatabaseBO){
        return centerHospitalService.updateRootBase(centerRootDatabaseBO);
    }

    /**
     * @Menthod findRootBase()
     * @Desrciption 审核医院
     * @Param
     * 1.[CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/3/21 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     **/
    @GetMapping("/findRootBase")
    public WrapperResponse<CenterRootDatabaseBO> findRootBase(CenterRootDatabaseBO centerRootDatabaseBO){
        return centerHospitalService.findRootBase(centerRootDatabaseBO);
    }


    @GetMapping("/triggerSchedulingManual")
    public WrapperResponse<Boolean> triggerSchedulingManual(){
        CenterRootDatabaseBO centerRootDatabaseBO = new CenterRootDatabaseBO();
        return centerHospitalService.triggerSchedulingManual(centerRootDatabaseBO);
    }
    /**
     *   中心端统一修改管理员账号密码
     * @param modifiedInfo 修改参数信息，传递过来的修改参数，必填参数有：
     *                     hospCode 医院编码
     *                     accountName 账户名
     *                     newPassword 新帐户密码
     *
     *
     * @return
     */
    @PostMapping("/updatePasswordOfAdministratorUnified")
    public WrapperResponse<Boolean>  updatePasswordOfAdministratorUnified(@RequestBody  Map<String,String> modifiedInfo){

        if(checkRequiredParams(modifiedInfo)){
            modifiedInfo.put("newPasswordByMd5",modifiedInfo.get("newPassword"));
            return centerHospitalService.updatePasswordOfAdministratorUnified(modifiedInfo);
        }else{
            return WrapperResponse.error(HttpStatus.NO_CONTENT.value(), "部分必填参数未提交，请检查",false);
        }
    }

    private boolean checkRequiredParams(Map<String,String> params){
        return params.containsKey("hospCode") && !StringUtils.isEmpty(params.get("hospCode"))
                && params.containsKey("accountName") && params.containsKey("newPassword");
    }
}
