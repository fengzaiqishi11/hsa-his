package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.daily.dto.OutinDailyDTO;
import cn.hsa.module.outpt.daily.service.OutinDailyService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptDaliyController
 * @Description: 门诊日结缴款
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/24 10:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/outpt/outptDaliy")
@Slf4j
public class OutptDailyController extends BaseController {
    @Resource
    private OutinDailyService outinDaliyService_consumer;

    /**
     * @Method 日结缴款 - 主表信息
     * @Description 
     * 
     * @Param
     * 1、开始时间：startTime
     * 2、结束时间：endTime
     * 3、是否缴款确认：isOk
     * 
     * @Author zhongming
     * @Date 2020/9/24 11:09
     * @Return 
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        // 缴款类型合法性校验
        checkTypeCode(outinDailyDTO);
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        if(!"all".equals(outinDailyDTO.getQueryType())){
            //查询类型不是all时，查询自已的数据
            outinDailyDTO.setCrteId(userDTO.getId());
        }

        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);
        return outinDaliyService_consumer.queryPage(map);
    }

    /**
     * @Method 生成待确认日结缴款
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @GetMapping("/insert")
    public WrapperResponse<Boolean> insert(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        // 缴款类型合法性校验
        checkTypeCode(outinDailyDTO);
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        outinDailyDTO.setCrteId(userDTO.getId());
        outinDailyDTO.setCrteName(userDTO.getName());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);
        return outinDaliyService_consumer.insert(map);
    }

    /**
     * @Method 确认缴款
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @GetMapping("/update")
    public WrapperResponse<Boolean> update(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outinDailyDTO.getDailyNo())) {
            throw new AppException("缴款单号不能为空");
        }
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        // 缴款确认信息
        outinDailyDTO.setOkId(userDTO.getId());
        outinDailyDTO.setOkName(userDTO.getName());
        outinDailyDTO.setOkTime(new Date());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);
        return outinDaliyService_consumer.update(map);
    }

    /**
     * @Method 取消缴款
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @GetMapping("/delete")
    public WrapperResponse<Boolean> delete(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outinDailyDTO.getDailyNo())) {
            throw new AppException("缴款单号不能为空");
        }
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        outinDailyDTO.setCrteId(userDTO.getId());

        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);
        return outinDaliyService_consumer.delete(map);
    }

    /**
     * @Method 获取最后一次缴款信息
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 14:54
     * @Return
     **/
    @GetMapping("/getLastDaily")
    public WrapperResponse<OutinDailyDTO> getLastDaily(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        // 缴款类型合法性校验
        checkTypeCode(outinDailyDTO);
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        outinDailyDTO.setCrteId(userDTO.getId());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);
        return outinDaliyService_consumer.getLastDaily(map);
    }
    
    /**
     * @Method 缴款类型合法性校验
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/11/2 16:11
     * @Return 
     **/
    private void checkTypeCode(OutinDailyDTO outinDailyDTO) {
        if (StringUtils.isEmpty(outinDailyDTO.getTypeCode())) {
            throw new AppException("缴款类型不能为空");
        }
        if (!Constants.JKLX.MZGH.equals(outinDailyDTO.getTypeCode()) &&
                !Constants.JKLX.MZSF.equals(outinDailyDTO.getTypeCode()) &&
                !Constants.JKLX.ZY.equals(outinDailyDTO.getTypeCode())) {
            throw new AppException("缴款类型不合法");
        }
    }

    /**
     * @Method 日结缴款 - 缴款报表
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 11:09
     * @Return
     **/
    @GetMapping("/getOutinDaily")
    public WrapperResponse<Map<String, Object>> getOutinDaily(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outinDailyDTO.getDailyNo())) {
            throw new AppException("缴款单号不能为空");
        }
        // 缴款类型合法性校验
        checkTypeCode(outinDailyDTO);
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        outinDailyDTO.setCrteId(userDTO.getId());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);

        WrapperResponse<Map<String, Object>> wr = outinDaliyService_consumer.getOutinDaily(map);
        // 医院名称
        wr.getData().put("hospName", userDTO.getHospName());
        return wr;
    }

    /**
     * @Method 日结缴款 - 结算明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 11:09
     * @Return
     **/
    @GetMapping("/querySettle")
    public WrapperResponse<PageDTO> querySettle(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outinDailyDTO.getDailyNo())) {
            throw new AppException("缴款单号不能为空");
        }
        // 缴款类型合法性校验
        checkTypeCode(outinDailyDTO);
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        outinDailyDTO.setCrteId(userDTO.getId());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);
        return outinDaliyService_consumer.querySettle(map);
    }

    /**
     * @Method 日结缴款 - 预交金明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 11:09
     * @Return
     **/
    @GetMapping("/queryAdvancePay")
    public WrapperResponse<PageDTO> queryAdvancePay(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outinDailyDTO.getDailyNo())) {
            throw new AppException("缴款单号不能为空");
        }
        // 缴款类型合法性校验
        checkTypeCode(outinDailyDTO);
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        outinDailyDTO.setCrteId(userDTO.getId());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);
        return outinDaliyService_consumer.queryAdvancePay(map);
    }

    /**
     * @Method 日结缴款 - 预交金冲抵明细
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/24 11:09
     * @Return
     **/
    @GetMapping("/queryAdvancePayCd")
    public WrapperResponse<PageDTO> queryAdvancePayCd(OutinDailyDTO outinDailyDTO,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outinDailyDTO.getDailyNo())) {
            throw new AppException("缴款单号不能为空");
        }
        // 缴款类型合法性校验
        checkTypeCode(outinDailyDTO);
        SysUserDTO userDTO = getSession(req, res) ;
        outinDailyDTO.setHospCode(userDTO.getHospCode());
        outinDailyDTO.setCrteId(userDTO.getId());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outinDailyDTO", outinDailyDTO);
        return outinDaliyService_consumer.queryAdvancePayCd(map);
    }
}
