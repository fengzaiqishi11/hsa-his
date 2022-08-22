package cn.hsa.module.center.admdvs.dao;

import cn.hsa.module.insure.module.dto.InsureDictDTO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 *  中心端医院与划区对应关系数据库访问层
 * @author luonianxin
 * @date 2022-01-06
 */
public interface CenterInsureAdmdvsDAO {

    /**
     *  分页查询医保地区划
     * @Param insuplcAdmdvs:医保区划
     *
     * @author fuhui
     * @date   2021/12/15 21:03
     * @return java.util.Map
     **/
    @MapKey("id")
    List<Map<String, Object>> queryAdmdvsInfoPage(InsureDictDTO dictDTO);
    /**
     *   查询医保地区划
     * @param hospCode:医保区划
     * @author yuelong.chen
     * @date   2021/12/15 21:03
     * @return java.util.List
     **/
    @MapKey("admdvsCode")
    List<Map<String,Object>> queryAdmdvsInfo(String hospCode);

    /**
     * @Method updateInsureGetInfo
     * @Desrciption  查询医保区划
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 20:54
     * @Return
     **/
    List<Map<String, Object>> queryAdmdvs(Map<String, Object> map);
}
