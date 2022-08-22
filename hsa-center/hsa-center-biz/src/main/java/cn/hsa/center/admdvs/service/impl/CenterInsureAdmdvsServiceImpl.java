package cn.hsa.center.admdvs.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.admdvs.bo.CenterInsureAdmdvsBO;
import cn.hsa.module.center.admdvs.service.CenterInsureAdmdvsService;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("insureDictService_provider")
public class CenterInsureAdmdvsServiceImpl extends HsafService implements CenterInsureAdmdvsService {

    @Resource
    private CenterInsureAdmdvsBO centerInsureAdmdvsBO;


    /**
     * 查询医保地区划
     * @param map queryAdmdvsInfo()
     * @author 廖继广
     * @Date 2021/12/02 21:03
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public WrapperResponse<PageDTO> queryAdmdvsInfoPage(Map<String,Object> map) {
        InsureDictDTO dictDTO = MapUtils.get(map,"insureDictDTO");
        dictDTO.setHospCode("1000001");
        return WrapperResponse.success(centerInsureAdmdvsBO.queryAdmdvsInfoPage(dictDTO));
    }

    /**
     * 查询医保地区划
     * @param map insuplcAdmdvs:医保区划
     *
     * @author yuelong.chen
     * @Date   2021/12/15 21:03
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/
    @Override
    public WrapperResponse<List<Map<String,Object>>> queryAdmdvsInfo(Map<String,String> map) {
        return WrapperResponse.success(centerInsureAdmdvsBO.queryAdmdvsInfo(map));
    }

    @Override
    public WrapperResponse<List<Map<String,Object>>> queryAdmdvs(Map<String, Object> map) {
        return WrapperResponse.success(centerInsureAdmdvsBO.queryAdmdvs(map));
    }
}
