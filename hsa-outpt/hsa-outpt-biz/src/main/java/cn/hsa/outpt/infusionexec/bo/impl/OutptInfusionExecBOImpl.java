package cn.hsa.outpt.infusionexec.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.infusionExec.bo.OutptInfusionExecBO;
import cn.hsa.module.outpt.infusionExec.dao.OutptInfusionExecDAO;
import cn.hsa.module.outpt.infusionRegister.dao.OutptInfusionRegisterDAO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.prescribeExec.dto.OutptPrescribeExecDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package_name: cn.hsa.outpt.infusionexec.bo.impl
 * @Class_name:: OutptInfusionExecBOImpl
 * @Description: 门诊输液执行BO实现类
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/13 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptInfusionExecBOImpl extends HsafBO implements OutptInfusionExecBO {

    @Resource
    private OutptInfusionExecDAO outptInfusionExecDAO;

    @Resource
    private OutptInfusionRegisterDAO outptInfusionRegisterDAO;

    //码表服务
    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页查询输液列表
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: cn.hsa.sys.PageDTO
     **/
    @Override
    public PageDTO queryPage(OutptPrescribeDetailsDTO outptPrescribeDetailsDTO) {
        //校验时间格式正确性
        String startTime = outptPrescribeDetailsDTO.getStartTime();
        String endTime = outptPrescribeDetailsDTO.getEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(startTime);
        } catch (Exception e) {
            outptPrescribeDetailsDTO.setStartTime("");
        }
        try {
            sdf.parse(endTime);
        } catch (Exception e) {
            outptPrescribeDetailsDTO.setEndTime("");
        }

        //获取用药方式list参数值
        Map map = new HashMap();
        //根据系统配置的输液参数值，查询出为用法为输液的List用法
        List<String> usageCodeList = findInfusionParam("DSY_YYFS", outptPrescribeDetailsDTO.getHospCode());

        //设置分页
        PageHelper.startPage(outptPrescribeDetailsDTO.getPageNo(), outptPrescribeDetailsDTO.getPageSize());
        map.put("usageCodeList", usageCodeList);
        map.put("outptPrescribeDetailsDTO", outptPrescribeDetailsDTO);

        //查询输液列表
//        List<OutptPrescribeExecDTO> list = outptInfusionExecDAO.queryPage(map);
        List<OutptPrescribeExecDTO> list = outptInfusionExecDAO.queryExec(map);
        return PageDTO.of(list);
    }

    /**
     * @Menthod: update()
     * @Desrciption: 输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: Boolean
     **/
    @Override
    public Boolean update(Map map) {
        //更新《处方明细表》执行信息
        int a = outptInfusionExecDAO.updatePreDet(map);
        //更新《处方执行表》执行信息
        int b = outptInfusionExecDAO.updatePreExec(map);
        //更新《输液登记表》执行信息
        int c = outptInfusionExecDAO.updateInfuReg(map);
        if (a + b + c <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @Menthod: remove()
     * @Desrciption: 取消输液执行
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: Boolean
     **/
    @Override
    public Boolean remove(Map map) {
        return outptInfusionExecDAO.removeBatchExec(map) > 0;
    }

    /**
     * @Menthod: updateExec()
     * @Desrciption: 执行签名(签名 、 取消)
     * @Param: List<OutptPrescribeExecDTO>
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/13 15:33
     * @Return: Boolean
     **/
    @Override
    public Boolean updateExec(Map map) {
        List<OutptPrescribeExecDTO> execList = MapUtils.get(map, "execList");
        List<OutptPrescribeExecDTO> cancelList = MapUtils.get(map, "cancelList");
        boolean flag = false;
        execList.forEach(outptPrescribeExecDTO -> {
            if (StringUtils.isEmpty(outptPrescribeExecDTO.getOpdId())) {
                throw new RuntimeException("未选择需要执行签名的处方明细单");
            }
        });
        cancelList.forEach(outptPrescribeExecDTO -> {
            if (StringUtils.isEmpty(outptPrescribeExecDTO.getOpdId())) {
                throw new RuntimeException("未选择需要取消执行的处方明细单");
            }
        });
        if (!ListUtils.isEmpty(execList)) {
            try {
                //更新《处方明细表》执行信息
                outptInfusionExecDAO.updatePreDetBatch(execList);
                //更新《处方执行表》执行信息
                outptInfusionExecDAO.updatePreExecBatch(execList);
                //更新《输液登记表》执行信息
                outptInfusionExecDAO.updateInfuRegBatch(execList);
                flag = true;
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
            }
        }
        if (!ListUtils.isEmpty(cancelList)) {
            try {
                //删除《处方明细表》执行信息
                outptInfusionExecDAO.deletePreDetBatch(cancelList);
                //删除《处方执行表》执行信息
                outptInfusionExecDAO.deletePreExecBatch(cancelList);
                //删除《输液登记表》执行信息
                outptInfusionExecDAO.deleteInfuRegBatch(cancelList);
                flag = true;
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
            }
        }

        return flag;
    }

    /**
     * @Method: 获取用药方式为输液的参数
     * @Param: code:输液参数编码，hospCode:医院编码
     * @Author: luoyong 输液参数
     */
    private List<String> findInfusionParam(String code, String hospCode) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        List<String> list = new ArrayList<>();
        //查询数据库配置的输液用法值
        String value = outptInfusionRegisterDAO.findInfusionParam(code, hospCode);
        if (StringUtils.isNotEmpty(value)) {
            list = new ArrayList<>(Arrays.asList(value.split(",")));
        }

        //加入雾化用法
        Map map1 = new HashMap<>();
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setCode("HLZXK");
        sysCodeDetailDTO.setValue("6"); //雾化执行卡类型
        sysCodeDetailDTO.setHospCode(hospCode);
        map1.put("hospCode",hospCode);
        map1.put("sysCodeDetailDTO",sysCodeDetailDTO);
        WrapperResponse<List<SysCodeDetailDTO>> listWrapperResponse = sysCodeService_consumer.queryCodeDetailAll(map1);
        List<SysCodeDetailDTO> data = listWrapperResponse.getData();
        String str = data.get(0).getRemark();
        if (StringUtils.isNotEmpty(str)){
            str = str.replace("'","");
            List<String> stringList = new ArrayList<>(Arrays.asList(str.split(",")));
            list.addAll(stringList);
        }
        return list;
    }
}
