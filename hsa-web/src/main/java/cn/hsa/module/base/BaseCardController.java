package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.card.dto.BaseCardDTO;
import cn.hsa.module.base.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.base.card.entity.BaseCardChangeDO;
import cn.hsa.module.base.card.entity.BaseCardRechargeChangeDO;
import cn.hsa.module.base.card.service.BaseCardService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseCardController
 * @Describe: 一卡通controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-11 15:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RequestMapping("/web/base/baseCard")
@Slf4j
@RestController
public class BaseCardController extends BaseController {

    /**
     * 一卡通管理服务
     */
    @Resource
    private BaseCardService baseCardService_consumer;

    /**
     * @Menthod: queryCardPage
     * @Desrciption: 分页查询一卡通列表数据
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: PageDTO
     **/
    @GetMapping("/queryCardPage")
    public WrapperResponse<PageDTO> queryCardPage(BaseCardDTO baseCardDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseCardDTO.setHospCode(hospCode);
        baseCardDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardDTO", baseCardDTO);
        return baseCardService_consumer.queryCardPage(map);
    }

    /**
     * @Menthod: saveCard
     * @Desrciption: 发卡
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    @PostMapping("/saveCard")
    public WrapperResponse<Boolean> saveCard(@RequestBody BaseCardDTO baseCardDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseCardDTO.setHospCode(hospCode);
        baseCardDTO.setHospCode(sysUserDTO.getHospCode());
        //baseCardDTO.setCrteId(userId);
        baseCardDTO.setCrteId(sysUserDTO.getId());
        //baseCardDTO.setCrteName(userName);
        baseCardDTO.setCrteName(sysUserDTO.getName());
        baseCardDTO.setCrteTime(DateUtils.getNow());
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardDTO", baseCardDTO);
        return baseCardService_consumer.saveCard(map);
    }

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 状态更改---1挂失，2冻结，3注销，4确认挂失，5作废
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    @PostMapping("/updateStatusCode")
    public WrapperResponse<Boolean> updateStatusCode(@RequestBody BaseCardDTO baseCardDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseCardDTO.setHospCode(hospCode);
        baseCardDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(baseCardDTO.getId())){
            throw new RuntimeException("未选择需要操作的人员");
        }
        if (StringUtils.isEmpty(baseCardDTO.getStatusCode())){
            throw new RuntimeException("未选择操作");
        }
        Map map = new HashMap();
        // 一卡通异动表
        BaseCardChangeDO baseCardChangeDO = new BaseCardChangeDO();
        baseCardChangeDO.setHospCode(baseCardDTO.getHospCode());
        baseCardChangeDO.setProfileId(baseCardDTO.getProfileId());
        baseCardChangeDO.setCardId(baseCardDTO.getId());
        baseCardChangeDO.setStatusCode(baseCardDTO.getStatusCode());
        baseCardChangeDO.setCrteId(sysUserDTO.getId());
        baseCardChangeDO.setCrteName(sysUserDTO.getName());
        baseCardChangeDO.setCrteTime(DateUtils.getNow());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardDTO", baseCardDTO);
        map.put("baseCardChangeDO", baseCardChangeDO);
        return baseCardService_consumer.updateStatusCode(map);
    }

    /**
     * @Menthod: resetPwd
     * @Desrciption: 密码重置
     * @Param: baseCardDTO
     * @Author: caoliang
     * @Email: caoliang@powersi.com.cn
     * @Date: 2021-06-03 10:20
     * @Return: Boolean
     **/
    @PostMapping("/resetPwd")
    public WrapperResponse<Boolean> updatePwd(@RequestBody BaseCardDTO baseCardDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseCardDTO.setHospCode(hospCode);
        baseCardDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(baseCardDTO.getId())){
            throw new RuntimeException("未选择需要操作的人员");
        }
        if (StringUtils.isEmpty(baseCardDTO.getStatusCode())){
            throw new RuntimeException("未选择操作");
        }
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardDTO", baseCardDTO);
        return baseCardService_consumer.updatePwd(map);
    }

    /**
     * @Menthod: getCardByProId
     * @Desrciption: 根据档案id查询出所有发卡记录
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: List<BaseCardDTO>
     **/
    @GetMapping("/getCardByProId")
    public WrapperResponse<List<BaseCardDTO>> getCardByProId(BaseCardDTO baseCardDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseCardDTO.setHospCode(hospCode);
        baseCardDTO.setHospCode(sysUserDTO .getHospCode());
        if (StringUtils.isEmpty(baseCardDTO.getProfileId())){
            throw new RuntimeException("未选择档案人员信息");
        }
        Map map = new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO .getHospCode());
        map.put("baseCardDTO", baseCardDTO);
        return baseCardService_consumer.getCardByProId(map);
    }


    /**
     * @Menthod: saveInCharge
     * @Desrciption: 一卡通充值
     * @Param: baseCardRechargeChangeDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date: 2021-08-06 10:10
     * @Return: Boolean
     **/
    @NoRepeatSubmit
    @GetMapping("/saveInCharge")
    public WrapperResponse<Boolean> saveInCharge(BaseCardRechargeChangeDO baseCardRechargeChangeDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req,res);
        baseCardRechargeChangeDO.setCrteId(sysUserDTO.getId());
        baseCardRechargeChangeDO.setCrteName(sysUserDTO.getName());
        baseCardRechargeChangeDO.setCrteTime(DateUtils.getNow());
        baseCardRechargeChangeDO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(baseCardRechargeChangeDO.getProfileId())){
            throw new RuntimeException("未选择档案人员信息");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardRechargeChangeDO", baseCardRechargeChangeDO);
        return baseCardService_consumer.saveInCharge(map);
    }

    /**
     * @Menthod: saveCardRefund
     * @Desrciption: 一卡通退费
     * @Param: baseCardRechargeChangeDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com.cn
     * @Date: 2021-08-06 10:13
     * @Return: Boolean
     **/
    @NoRepeatSubmit
    @GetMapping("/saveCardRefund")
    public WrapperResponse<Boolean> saveCardRefund(BaseCardRechargeChangeDO baseCardRechargeChangeDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req,res);
        baseCardRechargeChangeDO.setHospCode(sysUserDTO.getHospCode());
        baseCardRechargeChangeDO.setCrteId(sysUserDTO.getId());
        baseCardRechargeChangeDO.setCrteName(sysUserDTO.getName());
        baseCardRechargeChangeDO.setCrteTime(DateUtils.getNow());
        if (StringUtils.isEmpty(baseCardRechargeChangeDO.getProfileId())){
            throw new RuntimeException("未选择档案人员信息");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardRechargeChangeDO", baseCardRechargeChangeDO);
        return baseCardService_consumer.saveCardRefund(map);
    }

    /**
     * @Menthod: getCardChargeInfoByProId
     * @Desrciption: 根据档案id查询一卡通信息
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-06 16:20
     * @Return: BaseCardRechargeChangeDTO
     **/
    @GetMapping("/getCardChargeInfoByProId")
    public WrapperResponse<BaseCardRechargeChangeDTO> getCardChargeInfoByProId(BaseCardDTO baseCardDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req,res);
        baseCardDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(baseCardDTO.getProfileId())){
            throw new RuntimeException("未选择档案人员信息");
        }
        if (StringUtils.isEmpty(baseCardDTO.getId())){
            throw new RuntimeException("未选择档案人员信息");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO .getHospCode());
        map.put("profileId", baseCardDTO.getProfileId());
        map.put("id",baseCardDTO.getId());
        return baseCardService_consumer.getRechargeChangeInfo(map);
    }

    /**
     * @Menthod: getCardChargeInfoList
     * @Desrciption: 查询一卡通充值退费记录
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-07 14:05
     * @Return: List<BaseCardRechargeChangeDTO>
     **/
    @GetMapping("/getCardChargeInfoList")
    public WrapperResponse<PageDTO> getCardChargeInfoList(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req,res);
        baseCardRechargeChangeDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(baseCardRechargeChangeDTO.getProfileId())){
            throw new RuntimeException("未选择档案人员信息");
        }
        if (StringUtils.isEmpty(baseCardRechargeChangeDTO.getCardId())){
            throw new RuntimeException("未选择档案人员信息");
        }
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardRechargeChangeDTO", baseCardRechargeChangeDTO);
        return baseCardService_consumer.getRechargeChangeInfoList(map);
    }

    /**
     * @Menthod: queryPaitentPage
     * @Desrciption: 分页查询档案信息
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-07 16:25
     * @Return: PageDTO
     **/
    @GetMapping("/queryPaitentPage")
    public WrapperResponse<PageDTO> queryPaitentPage(BaseCardDTO baseCardDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req,res);
        baseCardDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardDTO", baseCardDTO);
        return baseCardService_consumer.queryPaitentPage(map);
    }


    /**
     * @Menthod: queryPaitentCardRechargeInfoList
     * @Desrciption: 分页查询一卡通异动信息
     * @Param: baseCardRechargeChangeDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-10 16:49
     * @Return: PageDTO
     **/
    @GetMapping("/queryPaitentCardRechargeInfoList")
    public WrapperResponse<List<BaseCardRechargeChangeDTO>> queryPaitentCardRechargeInfoList(BaseCardRechargeChangeDTO cardRechargeChangeDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req,res);
        cardRechargeChangeDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseCardRechargeChangeDTO", cardRechargeChangeDTO);
        return baseCardService_consumer.queryPaitentCardRechargeInfoList(map);
    }

}
