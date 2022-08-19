package cn.hsa.module.center.admdvs.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import java.util.List;
import java.util.Map;

public interface CenterInsureAdmdvsService {




    WrapperResponse<PageDTO> queryAdmdvsInfoPage(Map<String,Object> map);

    /**
     * queryAdmdvsInfo()查询医保地区划
     * @Param insuplcAdmdvs:医保区划
     * @author yuelong.chen
     * @Date   2021/12/15 21:03
     * @return java.util.List
     **/
    WrapperResponse<List<Map<String,Object>>> queryAdmdvsInfo(Map<String,String> map);

    /**
     * @Author gory
     * @Description 查询医保区划
     * @Date 2022/6/6 15:58
     * @Param [map]
     **/
    WrapperResponse<List<Map<String,Object>>> queryAdmdvs(Map<String, Object> map);
}
