package cn.hsa.outpt.outptexecutioncardprint.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.executioncardprint.bo.OutptExecutionCardPrintBO;
import cn.hsa.module.outpt.executioncardprint.dao.OutptExecutionCardPrintDAO;
import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import cn.hsa.module.phar.pharoutdistributedrug.dao.OutDistributeDrugDAO;
import cn.hsa.module.phar.pharoutdistributedrug.dto.PharOutReceiveDTO;
import cn.hsa.module.phar.pharoutdistributedrug.entity.PharOutReceiveDetailDO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.outpt.outptexecutioncardprint.bo.impl
 * @Class_name:: OutptExecutionCardPrintBOImpl
 * @Description: 执行卡打印业务逻辑实现层
 * @Author: zhangxuan
 * @Date: 2020-08-18 10:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptExecutionCardPrintBOImpl extends HsafBO implements OutptExecutionCardPrintBO {
    /**
     *执行卡打印数据库访问接口
     */
    @Resource
    OutptExecutionCardPrintDAO outptExecutionCardPrintDAO;

    //码表服务
    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Method queryPage
     * @Desrciption  根据条件分页查询执行卡打印数据
     * @Param
     * outptExecutionCardPrintDTO
     * @Author zhangxuan
     * @Date   2020-08-18 10:42
     * @update caoliang
     * @Date 2021-5-25 9:37
     * @Return outptExecutionCardPrintDTOS
    **/
    @Override
    public PageDTO queryPage(OutptInfusionRegisterDTO outptInfusionRegisterDTO) {
        String typeCode = outptInfusionRegisterDTO.getTypeCode();
        if (StringUtils.isEmpty(typeCode)){
            throw new RuntimeException("请选择需要打印的执行卡单据类型");
        }
        // 由于输液瓶贴、静脉输液卡、输液一览卡、留观输液瓶贴为同一组数据，需根据不同的打印单据类型过滤打印状态，增加一个是否共享（isShared）字段来区分
        if (StringUtils.isNotEmpty(typeCode) && ("1".equals(typeCode) || "2".equals(typeCode) || "3".equals(typeCode) || "4".equals(typeCode) || "5".equals(typeCode) || "6".equals(typeCode))){
            outptInfusionRegisterDTO.setIsShared(Constants.SF.S);
        } else {
            outptInfusionRegisterDTO.setIsShared(Constants.SF.F);
        }
        //根据执行卡单据类型查询码表对应的用药类型List
        Map map1 = new HashMap<>();
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setCode("HLZXK");
        //皮试单
        if ("4".equals(typeCode)){
            sysCodeDetailDTO.setValue("1"); //设置成输液执行卡的用法
            outptInfusionRegisterDTO.setSkinPrintFlag("1"); //
        } else {
            sysCodeDetailDTO.setValue(typeCode);
        }
        sysCodeDetailDTO.setHospCode(outptInfusionRegisterDTO.getHospCode());
        map1.put("hospCode",outptInfusionRegisterDTO.getHospCode());
        map1.put("sysCodeDetailDTO",sysCodeDetailDTO);
        WrapperResponse<List<SysCodeDetailDTO>> listWrapperResponse = sysCodeService_consumer.queryCodeDetailAll(map1);
        List<SysCodeDetailDTO> data = listWrapperResponse.getData();
        String value = data.get(0).getRemark();
        List<String> usageCodeList = new ArrayList<>();
        if (StringUtils.isNotEmpty(value)){
            value = value.replace("'","");
            outptInfusionRegisterDTO.setUsageCode(value);
        }

        //设置分页信息
        PageHelper.startPage(outptInfusionRegisterDTO.getPageNo(),outptInfusionRegisterDTO.getPageSize());
        //根据条件查询所得
        List<OutptInfusionRegisterDTO> outptInfusionRegisterDTOS = outptExecutionCardPrintDAO.queryPage(outptInfusionRegisterDTO);
        return PageDTO.of(outptInfusionRegisterDTOS);
    }
    /**
     * @Method update
     * @Desrciption  打印后更改打印状态
     * @Param
     * outptInfusionRegisterDTO
     * @Author zhangxuan
     * @Date   2020-08-26 14:01
     * @update caoliang
     * @Date 2021-5-25 10:25
     * @Return int
    **/
    @Override
    public Boolean update(OutptInfusionRegisterDTO outptInfusionRegisterDTO) {
        if (ListUtils.isEmpty(outptInfusionRegisterDTO.getIds())){
            throw new RuntimeException("未选择打印的记录");
        }
        if (StringUtils.isNotEmpty(outptInfusionRegisterDTO.getTypeCode())
                && ("1".equals(outptInfusionRegisterDTO.getTypeCode())
                || "2".equals(outptInfusionRegisterDTO.getTypeCode())
                || "3".equals(outptInfusionRegisterDTO.getTypeCode())
                || "4".equals(outptInfusionRegisterDTO.getTypeCode())
                || "5".equals(outptInfusionRegisterDTO.getTypeCode())
                || "6".equals(outptInfusionRegisterDTO.getTypeCode()))){
            /**
             * 拼接打印标志：由于输液瓶贴(1)、输液治疗卡(2)、输液一栏卡(3)、皮下肌肉执行卡(5)、皮试单(5)、雾化执行卡(6)共用同一条数据，打印标志需根据不同的类型设置不同的打印标志
             *  isPrint(mapToString): {'1':'0'; '2':'0'; '3':'0'; '4':'0'; '5':'0'} 未打印状态
             *  isPrint(mapToString): {'1':'1'; '2':'1'; '3':'1'; '4':'1'; '5':'1'} 已打印状态
             */
            List<OutptInfusionRegisterDTO> list =  outptExecutionCardPrintDAO.queryByIds(outptInfusionRegisterDTO);
            if (!ListUtils.isEmpty(list)){
                for (OutptInfusionRegisterDTO outptInfusionRegisterDTO2 : list) {
                    String isPrintJson = outptInfusionRegisterDTO2.getIsPrint();
                    if ("0".equals(isPrintJson)){
                        Map<String, String> m = new HashMap<>();
                        m.put(outptInfusionRegisterDTO.getTypeCode(), isPrintJson);
                        isPrintJson = JSON.toJSONString(m);
                    }
                    Map<String, String> m = (Map) JSON.parse(isPrintJson);
                    m.put(outptInfusionRegisterDTO.getTypeCode(), Constants.SF.S);
                    outptInfusionRegisterDTO2.setIsPrint(JSON.toJSONString(m));
                }
                return outptExecutionCardPrintDAO.updatePrintFlagByType(list);
            }else {
                return false;
            }
        } else {
            outptInfusionRegisterDTO.setIsPrint(Constants.SF.S);
            if(!ListUtils.isEmpty(outptInfusionRegisterDTO.getIds())){
                return outptExecutionCardPrintDAO.update(outptInfusionRegisterDTO);
            } else {
                return false;
            }
        }
        /*return outptExecutionCardPrintDAO.update(outptInfusionRegisterDTO) > 0;*/
    }

    /**
     * @Method queryPage
     * @Desrciption  根据门诊配药打印输液执行卡
     * @Param outptExecutionCardPrintDTO
     * @Author liuliyun
     * @Date   2021-11-04 10:42
     * @Return outptExecutionCardPrintDTOS
     **/
    @Override
    public  List<OutptInfusionRegisterDTO> queryInfusionRegisterList(PharOutReceiveDTO pharOutReceiveDTO) {
        //根据领药申请ID获取领药申请明细
        List<PharOutReceiveDetailDO> pharOutReceiveDetailDOList = outptExecutionCardPrintDAO.getOutReceiveDetailsById(pharOutReceiveDTO);
        if (pharOutReceiveDetailDOList!=null && pharOutReceiveDetailDOList.size()>0){
            List<String> ids =new ArrayList<>();
            for (PharOutReceiveDetailDO pharOutReceiveDetailDO:pharOutReceiveDetailDOList){
                ids.add(pharOutReceiveDetailDO.getOpdId());
            }
            OutptInfusionRegisterDTO outptInfusionRegisterDTO =new OutptInfusionRegisterDTO();
            outptInfusionRegisterDTO.setTypeCode("2");
            outptInfusionRegisterDTO.setHospCode(pharOutReceiveDTO.getHospCode());
            outptInfusionRegisterDTO.setIds(ids);
            if (outptInfusionRegisterDTO.getIds()==null ||outptInfusionRegisterDTO.getIds().size() ==0){
                throw new RuntimeException("处方明细id不能为空");
            }
            //根据执行卡单据类型查询码表对应的用药类型List
            Map map1 = new HashMap<>();
            SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
            sysCodeDetailDTO.setCode("HLZXK");
            sysCodeDetailDTO.setValue("2");
            sysCodeDetailDTO.setHospCode(outptInfusionRegisterDTO.getHospCode());
            map1.put("hospCode",outptInfusionRegisterDTO.getHospCode());
            map1.put("sysCodeDetailDTO",sysCodeDetailDTO);
            WrapperResponse<List<SysCodeDetailDTO>> listWrapperResponse = sysCodeService_consumer.queryCodeDetailAll(map1);
            List<SysCodeDetailDTO> data = listWrapperResponse.getData();
            String value = data.get(0).getRemark();
            if (StringUtils.isNotEmpty(value)){
                value = value.replace("'","");
                outptInfusionRegisterDTO.setUsageCode(value);
            }
            //根据条件查询所得
            List<OutptInfusionRegisterDTO> outptInfusionRegisterDTOS = outptExecutionCardPrintDAO.queryInfusionRegisterList(outptInfusionRegisterDTO);
            return outptInfusionRegisterDTOS;
        }else {
            return  new ArrayList<>();
        }

    }

}
