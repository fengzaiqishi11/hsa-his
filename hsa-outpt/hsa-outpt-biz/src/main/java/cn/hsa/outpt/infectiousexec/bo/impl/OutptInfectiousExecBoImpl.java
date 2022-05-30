package cn.hsa.outpt.infectiousexec.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.infectious.bo.OutptInfectiousDiseaExecBO;
import cn.hsa.module.outpt.infectious.dao.OutptInfectiousDiseaDao;
import cn.hsa.module.outpt.infectious.dto.InfectiousDiseaseDto;
import cn.hsa.module.outpt.infectious.entity.InfectiousDiseaseDO;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.outpt.infectiousexec.bo.impl
 * @Class_name:: OutptInfectiousExecBoImpl
 * @Description: 传染病执行BO实现类
 * @Author: liuliyun
 * @Date: 2021/4/21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptInfectiousExecBoImpl implements OutptInfectiousDiseaExecBO {
    @Resource
    private OutptInfectiousDiseaDao outptInfectiousDiseaDao;


    @Override
    public Boolean saveOutptInfectiousInfo(InfectiousDiseaseDO infectiousDiseaseDO) {
        return outptInfectiousDiseaDao.insertInfectiousRecord(infectiousDiseaseDO)>0;
    }

    @Override
    public Boolean updateOutptInfectiousInfo(InfectiousDiseaseDO infectiousDiseaseDO) {
        return outptInfectiousDiseaDao.updateInfectiousRecord(infectiousDiseaseDO)>0;
    }

    @Override
    public List<InfectiousDiseaseDO> findInfectiousById(InfectiousDiseaseDO infectiousDiseaseDO) {
        return outptInfectiousDiseaDao.findInfectiousById(infectiousDiseaseDO);
    }

    @Override
    public PageDTO findInfectiousPage(InfectiousDiseaseDto infectiousDiseaseDto) {
        PageHelper.startPage(infectiousDiseaseDto.getPageNo(),infectiousDiseaseDto.getPageSize());
        List<InfectiousDiseaseDO> infectiousDiseaseDtos= outptInfectiousDiseaDao.findInfectiousPage(infectiousDiseaseDto);
        return PageDTO.of(infectiousDiseaseDtos);

    }

    @Override
    public List<InfectiousDiseaseDto> findInfectiousListByIds(InfectiousDiseaseDto infectiousDiseaseDto) {
        return outptInfectiousDiseaDao.selectByPrimaryKeys(infectiousDiseaseDto);
    }
}
