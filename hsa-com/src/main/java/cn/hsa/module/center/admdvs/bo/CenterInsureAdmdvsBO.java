package cn.hsa.module.center.admdvs.bo;
import cn.hsa.base.PageDTO;

import cn.hsa.module.insure.module.dto.InsureDictDTO;


import java.util.List;
import java.util.Map;

public interface CenterInsureAdmdvsBO {




    PageDTO queryAdmdvsInfoPage(InsureDictDTO dictDTO);

    /**
     *  查询医保地区划 queryAdmdvsInfo()
     * @param map 医保区划
     *
     * @author yuelong.chen
     * @Date   2021/12/15 21:03
     * @return java.util.List
     **/
    List<Map<String,Object>> queryAdmdvsInfo(Map map);

    /**
     * @Author gory
     * @Description 查询医保区划
     * @Date 2022/6/6 16:00
     * @Param [map]
     **/
    List<Map<String,Object>> queryAdmdvs(Map<String, Object> map);
}
