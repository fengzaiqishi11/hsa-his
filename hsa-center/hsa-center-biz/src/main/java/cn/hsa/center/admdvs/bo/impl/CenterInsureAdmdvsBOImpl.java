package cn.hsa.center.admdvs.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.admdvs.bo.CenterInsureAdmdvsBO;
import cn.hsa.module.center.admdvs.dao.CenterInsureAdmdvsDAO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 *  获取医保划区信息BO业务层
 * @author luonianxin
 * @date 2022-01-07
 */
@Component
@Slf4j
public
class CenterInsureAdmdvsBOImpl extends HsafBO implements CenterInsureAdmdvsBO {

    @Resource
    private CenterInsureAdmdvsDAO centerInsureAdmdvsDAO;


    @Override
    public PageDTO queryAdmdvsInfoPage(InsureDictDTO dictDTO) {
        PageHelper.startPage(dictDTO.getPageNo(),  dictDTO.getPageSize());
        List<Map<String,Object>> dictDTOList = centerInsureAdmdvsDAO.queryAdmdvsInfoPage(dictDTO);
        return PageDTO.of(dictDTOList);
    }

    /**
     *  查询医保地区划
     * @param map insuplcAdmdvs:医保区划
     *
     * @Author yuelong.chen
     * @Date   2021/12/15 21:03
     * @return java.util.Map
     **/
    @Override
    public List<Map<String,Object>> queryAdmdvsInfo(Map map) {
        String hospCode = "1000001";
        List<Map<String, Object>> list = centerInsureAdmdvsDAO.queryAdmdvsInfo(hospCode);
        return list;
    }
}
