package cn.hsa.center.admdvs.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.admdvs.bo.CenterInsureAdmdvsBO;
import cn.hsa.module.center.admdvs.dao.CenterInsureAdmdvsDAO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.util.RedisUtils;
import cn.hutool.core.util.ObjectUtil;
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

    @Resource
    RedisUtils redisUtils;


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

    /**
     * @Method selectItemInfo
     * @Desrciption 查询医保区划
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 15:13
     * @Return
     **/
    public List<Map<String, Object>> queryAdmdvs(Map<String, Object> map) {
        if(ObjectUtil.isEmpty(map.get("upAdmdvsCode"))){
            map.put("admdvsCode","0000");
        }
        String key = "admdvs_code_" + map.get("hospCode") + "_" + map.get("upAdmdvsCode");
        if (redisUtils.hasKey(key)) {
            return redisUtils.get(key);
        }
        List<Map<String, Object>> list = centerInsureAdmdvsDAO.queryAdmdvs(map);
        redisUtils.set(key,list);
        return list;
    }
}
