package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.center.outptprofilefile.service.OutptProfileFileService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name: CenterProfileFileController
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/11 9:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/center/outptProfileFile")
@Slf4j
public class OutptProfileFileController extends BaseController {

    /**
     * 个人档案维护dubbo消费者接口
     */
    @Resource
    private OutptProfileFileService outptProfileFileService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<OutptProfileFileDTO> getById(OutptProfileFileDTO outptProfileFileDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptProfileFileDTO.setHospCode(userDTO.getHospCode());
        return outptProfileFileService_consumer.getById(outptProfileFileDTO);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<OutptProfileFileDTO>> queryAll(OutptProfileFileDTO outptProfileFileDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptProfileFileDTO.setHospCode(userDTO.getHospCode());
        return outptProfileFileService_consumer.queryAll(outptProfileFileDTO);
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptProfileFileDTO outptProfileFileDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptProfileFileDTO.setHospCode(userDTO.getHospCode());
        return outptProfileFileService_consumer.queryPage(outptProfileFileDTO);
    }

    /**
     * @Method save
     * @Desrciption 保存/新增个人档案 统一接口
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<OutptProfileFileExtendDTO> save(@RequestBody OutptProfileFileDTO outptProfileFileDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptProfileFileDTO.setHospCode(userDTO.getHospCode());
        outptProfileFileDTO.setCrteId(userDTO.getId());
        outptProfileFileDTO.setCrteName(userDTO.getName());
        outptProfileFileDTO.setCrteTime(DateUtils.getNow());
        return outptProfileFileService_consumer.save(outptProfileFileDTO);
    }

    /**
     * @Method isCertNoExist
     * @Desrciption  暂时只做判断身份证是否重复
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/isCertNoExist")
    public WrapperResponse<Boolean> isCertNoExist(OutptProfileFileDTO outptProfileFileDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptProfileFileDTO.setHospCode(userDTO.getHospCode());
        return outptProfileFileService_consumer.isCertNoExist(outptProfileFileDTO);
    }

    /**
     * @Method getAddressTree
     * @Desrciption  获取地址树
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/11/12 11:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("/getAddressTree")
    public WrapperResponse<List<TreeMenuNode>> getAddressTree(OutptProfileFileDTO outptProfileFileDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptProfileFileDTO.setHospCode(userDTO.getHospCode());
        return outptProfileFileService_consumer.getAddressTree(outptProfileFileDTO);
    }

}
