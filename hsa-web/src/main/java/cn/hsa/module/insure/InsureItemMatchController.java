package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.dto.InsureItemDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.UploadByExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name: InsureItemMatchController
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/7 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/insure/insureItemMatch")
@Slf4j
public class InsureItemMatchController extends BaseController {

    @Resource
    private InsureItemMatchService insureItemMatchService_consumer;

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("queryPage")
    public WrapperResponse<PageDTO> queryPage(InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        return insureItemMatchService_consumer.queryPage(map);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>>
     **/
    @PostMapping("queryAll")
    public WrapperResponse<List<InsureItemMatchDTO>> queryAll(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        return insureItemMatchService_consumer.queryAll(map);
    }

    /**
     * @Method addItemMatch
     * @Desrciption 项目生成
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("addItemMatch")
    public WrapperResponse<Boolean> addItemMatch(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(insureItemMatchDTO.getInsureRegCode())) {
            throw new AppException("医保机构编码不能为空！");
        }

        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        insureItemMatchDTO.setCrteId(sysUserDTO.getId());
        insureItemMatchDTO.setCode(sysUserDTO.getCode());
        insureItemMatchDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        return insureItemMatchService_consumer.addItemMatch(map);
    }

    /**
     * @Method deleteItemMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("deleteItemMatch")
    public WrapperResponse<Boolean> deleteItemMatch(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);

        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        insureItemMatchDTO.setCrteId(sysUserDTO.getId());
        insureItemMatchDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        return insureItemMatchService_consumer.deleteItemMatch(map);
    }

    /**
     * @Method addDownload
     * @Desrciption 将下载后的数据导入项目匹配表
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:03
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("addDownload")
    public WrapperResponse<Boolean> addDownload(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        insureItemMatchDTO.setCode(sysUserDTO.getCode());
        Map map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        return insureItemMatchService_consumer.addDownload(map);
    }
    /**
     * @Method autoMatch()
     * @Desrciption  长沙医保匹配：自动匹配，根据名称进行匹配
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/1/27 10:02 
     * @Return 
    **/
    @PostMapping("/autoMatch")
    public WrapperResponse<Boolean> autoMatch(@RequestBody Map<String,Object>map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        return this.insureItemMatchService_consumer.insertAutoMatch(map);
    }

    /**
     * @Method queryHospItem()
     * @Desrciption  展示医院端自己的项目匹配内容
     * @Param itemName:项目名称 itemCode:项目类型
     *
     * @Author fuhui
     * @Date   2021/1/27 10:13
     * @Return
    **/
    @GetMapping("/queryHospItem")
    public WrapperResponse<PageDTO>  queryHospItem(@RequestParam Map map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return this.insureItemMatchService_consumer.queryHospItem(map);
    }

    /**
     * @Method addCsDownLaod
     * @Desrciption   长沙医保下载：根据项目编码，开始时间下载数据
     * @Param  startDate:开始时间 也是医保端那边已经处理好匹配数据，如果时间选择当天或者最近，有可能返回空数据
     *         downLoadType:下载类型：01药品目录 02：诊疗项目信息 03：医疗服务设施信息
     *         04：费用类别信息 05：病种信息 06：项目对照信息  07：病种分型信息
     *
     * @Author fuhui
     * @Date   2021/1/27 10:30
     * @Return
    **/
    @PostMapping("/insertInsureItem")
    public WrapperResponse<Boolean> insertInsureItem(@RequestBody InsureItemDTO insureItemDTO, HttpServletRequest req, HttpServletResponse res){

        SysUserDTO sysUserDTO = getSession(req, res);
        // 医院编码
        insureItemDTO.setHospCode(sysUserDTO.getHospCode());
        insureItemDTO.setCrteName(sysUserDTO.getName());
        insureItemDTO.setCrteId(sysUserDTO.getId());
        // 操作员编码
        insureItemDTO.setCode(sysUserDTO.getCode());
        Map map = new HashMap<>(4);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureItemDTO",insureItemDTO);
        return this.insureItemMatchService_consumer.insertInsureItem(map);
    }

    /**
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用长沙医保返回回来的项目数据
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     *
     * @Author fuhui
     * @Date   2021/1/27 11:17
     * @Return
    **/
    @GetMapping("/queryInsureItemAll")
    public WrapperResponse<PageDTO> queryInsureItemAll(InsureItemDTO insureItemDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        // 医院编码
        insureItemDTO.setHospCode(sysUserDTO.getHospCode());
        // 操作员编码
        insureItemDTO.setCode(sysUserDTO.getCode());
        Map map = new HashMap<>(4);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureItemDTO",insureItemDTO);
        return this.insureItemMatchService_consumer.queryInsureItemAll(map);
    }

    /**
     * @Method handMatch
     * @Desrciption  处理手动匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 14:25
     * @Return
    **/
    @PostMapping("/handMatch")
    public WrapperResponse<Boolean> insertHandMatch(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);

            if(!map.isEmpty() && StringUtils.isEmpty(map.get("hospIllnessId")==null?"":map.get("hospIllnessId").toString())){
                    throw new AppException("手动匹配时,未选择左侧表格数据");
            }
            if(!map.isEmpty() && StringUtils.isEmpty(map.get("insureIllnessCode")==null?"":map.get("insureIllnessCode").toString())){
                throw new AppException("手动匹配时,未选择右侧表格数据");
            }
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("crteId",sysUserDTO.getId());
        map.put("crteName",sysUserDTO.getName());
        return this.insureItemMatchService_consumer.insertHandMatch(map);
    }

    /**
     * @Method updateInsureMatch
     * @Desrciption  根据项目id,取消医保匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/30 14:10
     * @Return
    **/
    @PostMapping("/cancelMatch")
    public WrapperResponse<Boolean> deleteInsureMatch( @RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(StringUtils.isEmpty(map.get("id").toString())){
            throw new AppException("请先选择一条数据做取消匹配操作");
        }
        if(Constants.SF.F.equals(map.get("isMatch").toString())){
            throw new AppException("请先选择已匹配的数据做取消匹配操作");
        }
        map.put("hospCode",sysUserDTO.getHospCode());
        return this.insureItemMatchService_consumer.deleteInsureMatch(map);
    }


    /**
     * @Method uploadItem
     * @Desrciption 医保统一支付平台;项目对照上传
     * @Param insureItemDTO
     * @Author fuhui
     * @Date 2021/3/15 17:21
     * @Return Boolean
     **/
    @PostMapping("/uploadItem")
    public WrapperResponse<Integer> uploadItem(@RequestBody InsureItemDTO insureItemDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureItemDTO.setHospCode(sysUserDTO.getHospCode());
        insureItemDTO.setCrteId(sysUserDTO.getId());
        insureItemDTO.setCrteName(sysUserDTO.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureItemDTO", insureItemDTO);
        return this.insureItemMatchService_consumer.uploadItem(map);
    }



    /**
     * @Method deleteInsureItemMatch
     * @Desrciption 医保统一支付平台;删除匹配的项目
     * @Param insureItemDTO
     * @Author fuhui
     * @Date 2021/3/15 17:21
     * @Return Boolean
     **/
    @PostMapping("/deleteInsureItemMatch")
    public WrapperResponse<Boolean> deleteInsureItemMatch(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureItemMatchDTO.setHospCode(sysUserDTO.getHospCode());
        insureItemMatchDTO.setCrteId(sysUserDTO.getId());
        insureItemMatchDTO.setCrteName( sysUserDTO.getName());
        insureItemMatchDTO.setIsItemCancel(true);
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO", insureItemMatchDTO);
        return this.insureItemMatchService_consumer.deleteInsureItemMatch(map);
    }

    /**
     * @Method updateUplaodInsureItem
     * @Desrciption  项目对照撤销
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/28 10:23
     * @Return
    **/
    @PostMapping("/updateUplaodInsureItem")
    public WrapperResponse<Integer> updateUplaodInsureItem(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        insureItemMatchDTO.setHospCode( sysUserDTO.getHospCode());
        insureItemMatchDTO.setCrteId(sysUserDTO.getId());
        insureItemMatchDTO.setCrteName(sysUserDTO.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode",  sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO", insureItemMatchDTO);
        return this.insureItemMatchService_consumer.updateUplaodInsureItem(map);
    }
    
    /**
     * @Method updateInsureItemMatch
     * @Desrciption   修改医保匹配项目
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/4/8 16:50
     * @Return 
    **/
    @PostMapping("/updateInsureItemMatch")
    public WrapperResponse<Boolean> updateInsureItemMatch(@RequestBody InsureItemMatchDTO insureItemMatchDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode",  sysUserDTO.getHospCode());
        insureItemMatchDTO.setHospCode( sysUserDTO.getHospCode());
        map.put("insureItemMatchDTO", insureItemMatchDTO);
        return this.insureItemMatchService_consumer.updateInsureItemMatch(map);
        
    }

    /**
     * @Method importInsureItemMatch
     * @Desrciption   导入医保匹配数据
     * @Param
     *
     * @Author 廖继广
     * @Date   2021/05/20 05:20
     * @Return
     **/
    @PostMapping("/updateInsureItemMatchInfo")
    public WrapperResponse<Boolean> updateInsureItemMatchInfo(@RequestParam MultipartFile file,String insureRegCode, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(insureRegCode)) {
            throw new AppException("医保机构不能为空");
        }

        Map<String, Object> stringObjectMap = UploadByExcel.readExcel(file);
        Boolean flag = (Boolean) stringObjectMap.get("isSuccess");
        List<List<String>> resultList = (List<List<String>>) stringObjectMap.get("resultList");
        if(!flag){
            throw new AppException("解析错误，文件类型或格式不对");
        }
        if (ListUtils.isEmpty(resultList)){
            throw new AppException("解析错误，数据为空");
        }
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("resultList",resultList);
        map.put("crteName",sysUserDTO.getName());
        map.put("crteId",sysUserDTO.getId());
        map.put("insureRegCode",insureRegCode);
      return this.insureItemMatchService_consumer.updateInsureItemMatchInfo(map);
    }
}
