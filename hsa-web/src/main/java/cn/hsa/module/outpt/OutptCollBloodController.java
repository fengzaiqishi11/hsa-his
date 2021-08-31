package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.outpt.collblood.service.OutptCollBloodService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
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
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptCollBloodController
 * @Describe: 门诊采血时间录入controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-28 11:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@Slf4j
@RequestMapping("/web/outpt/outptCollBlood")
public class OutptCollBloodController extends BaseController {

    /**
     * 门诊采血服务
     */
    @Resource
    private OutptCollBloodService outptCollBloodService_consumer;

    /**
     * @Menthod: queryCollBlood
     * @Desrciption: 查询门诊采血列表数据
     * @Param: medicalApplyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: PageDTO
     **/
    @GetMapping("/queryCollBlood")
    public WrapperResponse<PageDTO> queryCollBlood(MedicalApplyDTO medicalApplyDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        medicalApplyDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalApplyDTO", medicalApplyDTO);
        return outptCollBloodService_consumer.queryCollBlood(map);
    }

    /**
     * @Menthod: saveCollBlood
     * @Desrciption: 保存门诊采血数据
     * @Param: medicalApplyDTOList
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-28 15:18
     * @Return: Boolean
     **/
    @PostMapping("/saveCollBlood")
    public WrapperResponse<Boolean> saveCollBlood(@RequestBody List<MedicalApplyDTO> medicalApplyDTOList,HttpServletRequest req, HttpServletResponse res) {
        if (ListUtils.isEmpty(medicalApplyDTOList)) {
            throw new RuntimeException("未选择需要保存的数据");
        }
        SysUserDTO userDTO = getSession(req, res) ;
        medicalApplyDTOList.forEach(item -> {
            item.setHospCode(userDTO.getHospCode());
            if (StringUtils.isEmpty(item.getCollBloodId())){
                item.setCollBloodId(userDTO.getId());
            }
            if (StringUtils.isEmpty(item.getCollBloodName())){
                item.setCollBloodName(userDTO.getName());
            }
            if (item.getCollBloodTime() == null ){
                item.setCollBloodTime(DateUtils.getNow());
            }
        });

        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalApplyDTOList", medicalApplyDTOList);
        return outptCollBloodService_consumer.saveCollBlood(map);
    }
}
