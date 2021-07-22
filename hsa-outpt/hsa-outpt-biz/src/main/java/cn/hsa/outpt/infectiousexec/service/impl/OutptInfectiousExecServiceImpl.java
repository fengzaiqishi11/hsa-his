package cn.hsa.outpt.infectiousexec.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.infectious.bo.OutptInfectiousDiseaExecBO;
import cn.hsa.module.outpt.infectious.dao.OutptInfectiousDiseaDao;
import cn.hsa.module.outpt.infectious.dto.InfectiousDiseaseDto;
import cn.hsa.module.outpt.infectious.entity.InfectiousDiseaseDO;
import cn.hsa.module.outpt.infectious.service.OutptInfectiousDiseaExecService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.infectiousexec.service.impl
 * @Class_name:: OutptInfectiousExecServiceImpl
 * @Description: 传染病执行service实现类
 * @Author: liuliyun
 * @Date: 2021/4/21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/outpt/infectiousExec")
@Service("outptInfectiousExecService_provider")
public class OutptInfectiousExecServiceImpl extends HsafService implements OutptInfectiousDiseaExecService {
    @Resource
    private OutptInfectiousDiseaExecBO outptInfectiousDiseaExecBO;
    @Resource
    private  OutptInfectiousDiseaDao outptInfectiousDiseaDao;
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        InfectiousDiseaseDO infectiousDiseaseDO= MapUtils.get(map, "outptInfectiousDiseaDao");
        if(infectiousDiseaseDO==null|| StringUtils.isEmpty(infectiousDiseaseDO.getId())){
            //主表主键
            infectiousDiseaseDO.setId(SnowflakeUtils.getId());
            infectiousDiseaseDO.setReportNo(SnowflakeUtils.getId());
            //新增
            return WrapperResponse.success(outptInfectiousDiseaExecBO.saveOutptInfectiousInfo(infectiousDiseaseDO));
        }else{
            //修改
            return WrapperResponse.success(outptInfectiousDiseaExecBO.updateOutptInfectiousInfo(infectiousDiseaseDO));
        }

    }

    @Override
    public WrapperResponse<List<InfectiousDiseaseDO>> queryInfoById(Map map) {
        InfectiousDiseaseDO infectiousDiseaseDO= MapUtils.get(map, "infectiousDiseaseDO");
        List<InfectiousDiseaseDO> infectiousDiseaseDOS=outptInfectiousDiseaExecBO.findInfectiousById(infectiousDiseaseDO);
        return  WrapperResponse.success(infectiousDiseaseDOS);
    }

    @Override
    public WrapperResponse<PageDTO> queryInfectiousPage(Map map) {
        InfectiousDiseaseDto infectiousDiseaseDto= MapUtils.get(map, "infectiousDiseaseDto");
        return  WrapperResponse.success(outptInfectiousDiseaExecBO.findInfectiousPage(infectiousDiseaseDto));
    }

    @Override
    public WrapperResponse InfectiousDiseaseAudit(Map map) {
        InfectiousDiseaseDto infectiousDiseaseDto= MapUtils.get(map, "infectiousDiseaseDto");
       List<InfectiousDiseaseDto> infectiousDiseaseDOS= outptInfectiousDiseaDao.selectByPrimaryKeys(infectiousDiseaseDto);
        if (!infectiousDiseaseDOS.isEmpty()){
            List<String> ids = new ArrayList<String>();
            infectiousDiseaseDOS.stream().forEach(infectiousDiseaseDO -> {
                if (Constants.SHZT.WSH.equals(infectiousDiseaseDO.getAuditStatusCode())){
                    ids.add(infectiousDiseaseDO.getId());
                }
            });
            if (!ids.isEmpty()){
                String[] idsParam = new String[ids.size()];
                ids.toArray(idsParam);
                infectiousDiseaseDto.setIds(idsParam);//ids
                infectiousDiseaseDto.setAuditStatusCode(Constants.SHZT.SHWC);//审核状态 = 审核完成
                infectiousDiseaseDto.setAuditTime(new Date());//审核时间
                outptInfectiousDiseaDao.updateByPrimaryKey(infectiousDiseaseDto);
                return WrapperResponse.success("操作成功。",null);
            }
            return WrapperResponse.fail("没有需要审核信息。",null);
        }else{
            return WrapperResponse.fail("未找到传染病信息。",null);
        }
    }
}
