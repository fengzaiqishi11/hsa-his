package cn.hsa.outpt.infusionRegister.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.fees.dao.OutptCostDAO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.infusionRegister.bo.OutptInfusionRegisterBO;
import cn.hsa.module.outpt.infusionRegister.dao.OutptInfusionRegisterDAO;
import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.infusionRegister.bo.impl
 * @Class_name:: OutptInfusionRegisterBOImpl
 * @Description: 门诊输液登记BO实现层
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptInfusionRegisterBOImpl implements OutptInfusionRegisterBO {

    @Resource
    private OutptInfusionRegisterDAO outptInfusionRegisterDAO;

    @Resource
    private OutptCostDAO outptCostDAO;

    //码表服务
    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页《未登记》查询患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(OutptInfusionRegisterDTO outptInfusionRegisterDTO) {
        //设置分页信息
        PageHelper.startPage(outptInfusionRegisterDTO.getPageNo(), outptInfusionRegisterDTO.getPageSize());

        //获取用药方式list参数值
        Map map = new HashMap();
        //根据系统配置的输液参数值，查询出为用法为输液的List用法
        List<String> usageCodeList = findInfusionParam("DSY_YYFS", outptInfusionRegisterDTO.getHospCode());

        map.put("usageCodeList", usageCodeList);
        map.put("outptInfusionRegisterDTO", outptInfusionRegisterDTO);

        //条件筛选
//        List<OutptVisitDTO> outptVisitDTOS = outptInfusionRegisterDAO.queryPage(map);
//        List<OutptVisitDTO> outptVisitDTOS = outptInfusionRegisterDAO.queryPageByVisit(map);
        List<OutptVisitDTO> outptVisitDTOS = outptInfusionRegisterDAO.queryPageNoRegister(map);
        return PageDTO.of(outptVisitDTOS);
    }

    /**
     * @Menthod: queryPageByInfu()
     * @Desrciption: 根据条件分页查询《已登记》患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPageByInfu(OutptInfusionRegisterDTO outptInfusionRegisterDTO) {
        //设置分页信息
        PageHelper.startPage(outptInfusionRegisterDTO.getPageNo(), outptInfusionRegisterDTO.getPageSize());

        /*//获取用药方式list参数值
        Map map = new HashMap();
        //根据系统配置的输液参数值，查询出为用法为输液的List用法
        List<String> usageCodeList = findInfusionParam("DSY_YYFS", outptInfusionRegisterDTO.getHospCode());

        map.put("usageCodeList", usageCodeList);
        map.put("outptInfusionRegisterDTO", outptInfusionRegisterDTO);

        //条件筛选
        List<OutptVisitDTO> outptVisitDTOS = outptInfusionRegisterDAO.queryPageByInfu(map);*/
        List<OutptVisitDTO> outptVisitDTOS = outptInfusionRegisterDAO.queryRegisterInfo(outptInfusionRegisterDTO);
        return PageDTO.of(outptVisitDTOS);
    }

    /**
     * @Menthod: save()
     * @Desrciption: 输液登记
     * @Param: map (outptVisitDTO-门诊就诊DTO, crteId, crteName)
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: Boolean
     **/
    @Override
    public Boolean save(Map map) {
//        OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
        List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOS = MapUtils.get(map, "outptPrescribeDetailsDTOS");
        //判断是否登记--根据处方明细id
        /*int count = outptInfusionRegisterDAO.getCountById(outptVisitDTO);
        if (count > 0) {
            throw new RuntimeException("该患者已经进行过登记");
        }
        List<OutptInfusionRegisterDTO> dtoList = outptInfusionRegisterDAO.getByOptIds(outptVisitDTO.getOpdIds());
        for (OutptInfusionRegisterDTO dto : dtoList) {
            for (String opdId : outptVisitDTO.getOpdIds()) {
                if (opdId.equals(dto.getOpdId())) {
                    throw new RuntimeException("该处方中的[" + dto.getItemName() + "]药品已进行过登记了");
                }
            }
        }*/
        //判断需要登记的处方明细id登记次数是否达到本院执行次数
        List<OutptInfusionRegisterDTO> execList = outptInfusionRegisterDAO.queryExecNum(map);
        //未登记情况下直接去登记
        if (!ListUtils.isEmpty(execList)) {
            Map<String, List<OutptInfusionRegisterDTO>> listMap = execList.stream().collect(Collectors.groupingBy(item -> item.getOpdId()));
            for (OutptPrescribeDetailsDTO detailsDTO : outptPrescribeDetailsDTOS) {
                List<OutptInfusionRegisterDTO> dtoList = listMap.get(detailsDTO.getId());
                if(!ListUtils.isEmpty(dtoList)) {
                    if (detailsDTO.getExecNum() <= dtoList.size()) {
                        throw new RuntimeException("【" + detailsDTO.getContent() + "】登记次数已经达到本院执行次数");
                    }
                }
            }
        }

        //输液登记
        List<OutptInfusionRegisterDTO> list = new ArrayList();
        /*for (String opdId : outptVisitDTO.getOpdIds()) {
            OutptInfusionRegisterDTO outptInfusionRegisterDTO = new OutptInfusionRegisterDTO();
            outptInfusionRegisterDTO.setId(SnowflakeUtils.getId());//登记id
            outptInfusionRegisterDTO.setHospCode(outptVisitDTO.getHospCode());//医院编码
            outptInfusionRegisterDTO.setOpdId(opdId);//处方明细id
            outptInfusionRegisterDTO.setVisitId(outptVisitDTO.getId());//就诊id
            outptInfusionRegisterDTO.setCrteId(MapUtils.get(map, "crteId"));//创建人id
            outptInfusionRegisterDTO.setCrteName(MapUtils.get(map, "crteName"));//创建人name
            outptInfusionRegisterDTO.setCrteTime(DateUtils.getNow());//创建时间
            list.add(outptInfusionRegisterDTO);
        }*/
        for (OutptPrescribeDetailsDTO detailsDTO : outptPrescribeDetailsDTOS) {
            OutptInfusionRegisterDTO outptInfusionRegisterDTO = new OutptInfusionRegisterDTO();
            outptInfusionRegisterDTO.setId(SnowflakeUtils.getId());//登记id
            outptInfusionRegisterDTO.setHospCode(detailsDTO.getHospCode());//医院编码
            outptInfusionRegisterDTO.setOpdId(detailsDTO.getId());//处方明细id
            outptInfusionRegisterDTO.setVisitId(detailsDTO.getVisitId());//就诊id
            outptInfusionRegisterDTO.setCrteId(MapUtils.get(map, "crteId"));//创建人id
            outptInfusionRegisterDTO.setCrteName(MapUtils.get(map, "crteName"));//创建人name
            outptInfusionRegisterDTO.setCrteTime(DateUtils.getNow());//创建时间
            list.add(outptInfusionRegisterDTO);
        }

        return outptInfusionRegisterDAO.saveByBatch(list) > 0;
    }

    /**
     * @Menthod: getByVisitId()
     * @Desrciption: 根据患者visitId分页查询出对应的处方明细列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO getByVisitId(OutptVisitDTO outptVisitDTO) {
        //设置分页信息
        PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());

        //获取用药方式list参数值
        Map map = new HashMap();
        //根据系统配置的输液参数值，查询出为用法为输液的List用法
        List<String> usageCodeList = findInfusionParam("DSY_YYFS", outptVisitDTO.getHospCode());

        map.put("usageCodeList", usageCodeList);
        map.put("outptVisitDTO", outptVisitDTO);

        List<OutptPrescribeDetailsDTO> list = outptInfusionRegisterDAO.getByVisitId(map);

        //过滤登记次数足够的数据
        if (!ListUtils.isEmpty(list)){
            list = list.stream().filter(outptPrescribeDetailsDTO -> outptPrescribeDetailsDTO.getRegisterNum() < outptPrescribeDetailsDTO.getExecNum()).collect(Collectors.toList());
        }
        return PageDTO.of(list);
    }

    /**
     * @Menthod: queryCost()
     * @Desrciption: 根据患者visitId分页查询出对应的费用列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.sys.PageDTO
     **/
    @Override
    public PageDTO queryCost(OutptVisitDTO outptVisitDTO) {
        PageHelper.startPage(outptVisitDTO.getPageNo(), outptVisitDTO.getPageSize());
        OutptCostDTO outptCostDTO = new OutptCostDTO();
        outptCostDTO.setVisitId(outptVisitDTO.getId());
        outptCostDTO.setHospCode(outptVisitDTO.getHospCode());
        List<OutptCostDTO> outptCostDTOS = outptCostDAO.findByCondition(outptCostDTO);
        return PageDTO.of(outptCostDTOS);
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
