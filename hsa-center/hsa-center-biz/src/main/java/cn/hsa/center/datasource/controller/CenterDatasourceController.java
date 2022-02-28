package cn.hsa.center.datasource.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.datasource.dto.CenterHospitalDatasourceDTO;
import cn.hsa.module.center.datasource.entity.CenterDatasourceDO;
import cn.hsa.module.center.datasource.entity.CenterHospitalDatasourceDO;
import cn.hsa.module.center.datasource.service.CenterDatasourceService;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.TableStructureSyncDTO;
import cn.hsa.util.DBUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.DriverManager;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.datasource.controller
 * @Class_name: CenterDatasourceController
 * @Describe: 医院数据源Controller
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/centerDatasource")
public class CenterDatasourceController extends CenterBaseController {

    @Resource
    private CenterDatasourceService centerDatasourceService_consumer;


    /**
     * @Menthod addCenterDatasource
     * @Desrciption
     * @param centerDatasourceDO 新增数据源
     * @Author Ou·Mr
     * @Date 2020/8/4 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 成功/失败
     */
    @PostMapping(value = "/addCenterDatasource")
    public WrapperResponse addCenterDatasource(@RequestBody CenterDatasourceDO centerDatasourceDO){
        centerDatasourceDO.setCrteId(userId);
        centerDatasourceDO.setCrteName(userName);
        return centerDatasourceService_consumer.addCenterDatasource(centerDatasourceDO);
    }

    /**
     * @Method connectionCenterDatasource
     * @Desrciption 连接数据库
       @params [centerDatasourceDO]
     * @Author chenjun
     * @Date   2020-12-10 10:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
    **/
    @PostMapping(value = "/connectionCenterDatasource")
    public WrapperResponse connectionCenterDatasource(@RequestBody CenterDatasourceDO centerDatasourceDO){
        try {
            Class.forName(centerDatasourceDO.getDriverName());
            DriverManager.getConnection(centerDatasourceDO.getUrl(), centerDatasourceDO.getUsername(), centerDatasourceDO.getPassword());
        } catch (Exception e) {
            throw new AppException("数据库连接异常！"+ e.getMessage());
        }
         return WrapperResponse.success("测试连接成功。",null);
    }

    /**
     * @Menthod editCenterDatasource
     * @Desrciption 编辑数据源
     * @param centerDatasourceDO 编辑数据源参数
     * @Author Ou·Mr
     * @Date 2020/8/4 9:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 成功/失败
     */
    @PutMapping(value = "/editCenterDatasource")
    public WrapperResponse editCenterDatasource(@RequestBody CenterDatasourceDO centerDatasourceDO){
        try {
            Class.forName(centerDatasourceDO.getDriverName());
            DriverManager.getConnection(centerDatasourceDO.getUrl(), centerDatasourceDO.getUsername(), centerDatasourceDO.getPassword());
        } catch (Exception e) {
            throw new AppException("保存失败,请检查数据库配置参数!");
        }
        return centerDatasourceService_consumer.editCenterDatasource(centerDatasourceDO);
    }


    /**
     * @Menthod queryCenterDatasource
     * @Desrciption 分页查询数据源信息
     * @param centerDatasourceDTO 查询条件queryCenterHospitalPage
     * @Author Ou·Mr
     * @Date 2020/8/4 9:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 分页结果集
     */
    @GetMapping(value = "/queryCenterDatasourcePage")
    public WrapperResponse<PageDTO> queryCenterDatasourcePage(CenterDatasourceDTO centerDatasourceDTO,BindingResult verifyResult){
        return centerDatasourceService_consumer.queryCenterDatasourcePage(centerDatasourceDTO);
    }

    /**
     * @Menthod queryHospCodePage
     * @Desrciption 分页查询医院机构信息
     * @param centerHospitalDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 10:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/queryHospCodePage")
    public WrapperResponse<PageDTO> queryHospCodePage(CenterHospitalDatasourceDTO centerHospitalDatasourceDTO){
        return centerDatasourceService_consumer.queryHospCodePage(centerHospitalDatasourceDTO);
    }

    /**
     * @Menthod delCenterDatasource
     * @Desrciption 删除数据源
     * @param ids 数据源ids
     * @Author Ou·Mr
     * @Date 2020/8/4 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @DeleteMapping(value = "/delCenterDatasource")
    public WrapperResponse delCenterDatasource(@Param("ids")String ids){
        if(StringUtils.isEmpty(ids)){
            return WrapperResponse.error(WrapperResponse.FAIL,"参数错误",null);
        }
        return centerDatasourceService_consumer.delCenterDatasource(ids);
    }

    /**
     * @Menthod delDatasourceHospCode
     * @Desrciption 删除数据源关联的医院信息
     * @param id 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 成功/失败
     */
    @DeleteMapping("/delHospitalDatasource")
    public WrapperResponse delHospitalDatasource(String id){
        if(StringUtils.isEmpty(id)){
            return WrapperResponse.error(WrapperResponse.FAIL,"参数错误",null);
        }
        return centerDatasourceService_consumer.delHospitalDatasource(id);
    }

    /**
     * @Menthod addHospitalDatasource
     * @Desrciption 批量新增医院关联数据源
     * @param centerDatasourceDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 11:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 成功/失败
     */
    @PostMapping(value = "/addHospitalDatasource")
    public WrapperResponse addHospitalDatasource(@RequestBody CenterDatasourceDTO centerDatasourceDTO){
        String code = centerDatasourceDTO.getCode();
        if (StringUtils.isEmpty(code)){
            return WrapperResponse.error(WrapperResponse.FAIL,"参数错误。",null);
        }
        List<CenterHospitalDatasourceDO> centerHospitalDatasourceDOS = centerDatasourceDTO.getCenterHospitalDatasourceDOS();
        if (centerHospitalDatasourceDOS != null && !centerHospitalDatasourceDOS.isEmpty()){
            for (int i=0;i<centerHospitalDatasourceDOS.size();i++){
                centerHospitalDatasourceDOS.get(i).setCrteId(this.userId);//创建人id
                centerHospitalDatasourceDOS.get(i).setCrteName(this.userName);//创建人姓名
                centerHospitalDatasourceDOS.get(i).setCrteTime(new Date());//创建时间
                centerHospitalDatasourceDOS.get(i).setDsCode(code);//数据源id
                centerHospitalDatasourceDOS.get(i).setId(SnowflakeUtils.getId());//id
            }
            centerDatasourceDTO.setCenterHospitalDatasourceDOS(centerHospitalDatasourceDOS);
        }
        return centerDatasourceService_consumer.addHospitalDatasource(centerDatasourceDTO);
    }

    /**
     * @Menthod queryCenterHospitalPage
     * @Desrciption  分页查询医院信息
     * @param centerHospitalDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/5 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/queryCenterHospitalPage")
    public WrapperResponse<PageDTO> queryCenterHospitalPage(CenterHospitalDTO centerHospitalDTO){
        return centerDatasourceService_consumer.queryCenterHospitalPage(centerHospitalDTO);
    }


    /**
     * @Menthod deleteCenterHospital
     * @Desrciption  删除数据源绑定的信息
     * @param ids id集合 查询条件
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @GetMapping(value = "/deleteCenterHospital")
    public WrapperResponse deleteCenterHospital(@RequestParam(value="ids") String ids){
        return centerDatasourceService_consumer.deleteCenterHospital(ids);
    }

    /**
     * @Menthod deleteCenterHospital
     * @Desrciption  根据所选数据源列表 对数据进行字段同步
     * @param
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @PostMapping(value = "/tableStructureSync")
    public WrapperResponse tableStructureSync(@RequestBody TableStructureSyncDTO tableStructureSyncDTO){
        return centerDatasourceService_consumer.tableStructureSync(tableStructureSyncDTO);
    }

    /**
     * @Description: 更新版本更新的确认标识
     * @Param: []
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    @PostMapping(value = "/updateIsGuide")
    public WrapperResponse updateIsGuide(){
        return centerDatasourceService_consumer.updateIsGuide();
    }

}
