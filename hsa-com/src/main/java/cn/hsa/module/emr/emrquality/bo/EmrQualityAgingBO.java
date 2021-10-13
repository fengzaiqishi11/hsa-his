package cn.hsa.module.emr.emrquality.bo;

import cn.hsa.module.emr.emrquality.dto.EmrQualityAgingDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.bo
 * @Class_name: EmrQualityAgingBO
 * @Describe: 电子病历时效质控bo层
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/23 14:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrQualityAgingBO {
    /**
     * @Menthod insertEmrQualityAging
     * @Desrciption 新增时效质控记录
     * @param emrQualityAgingDTO
     * @Author liuliyun
     * @Date   2021/9/24 10:46
     * @Return boolean
     **/
    boolean insertEmrQualityAging(EmrQualityAgingDTO emrQualityAgingDTO);

    /**
     * @Menthod updateEmrQualityAging
     * @Desrciption 更新时效质控记录
     * @param emrQualityAgingDTO
     * @Author liuliyun
     * @Date   2021/9/24 10:46
     * @Return boolean
     **/
    boolean updateEmrQualityAging(EmrQualityAgingDTO emrQualityAgingDTO);

    /**
     * @Menthod deleteEmrQualityAging
     * @Desrciption 删除时效质控记录
     * @param map
     * @Author liuliyun
     * @Date   2021/9/24 14:44
     * @Return boolean
     **/
    boolean deleteEmrQualityAging(Map map);

    /**
     * @Menthod queryEmrTemplateList
     * @Desrciption 查询
     * @param map
     * @Author liuliyun
     * @Date   2021/9/24 10:49
     * @Return List<Map>
     **/
    List<Map> queryEmrTemplateList(Map map);


    /**
     * @Menthod queryEmrQualityList
     * @Desrciption 查询时效质控list
     * @param map
     * @Author liuliyun
     * @Date   2021/9/24 11:58
     * @Return PageDTO
     **/
    List<EmrQualityAgingDTO> queryEmrQualityList(Map map);

    /**
     * @Menthod queryEmrQualityListById
     * @Desrciption 根据id查询时效质控list
     * @param map
     * @Author liuliyun
     * @Date   2021/9/26 15:02
     * @Return PageDTO
     **/
    List<EmrQualityAgingDTO> queryEmrQualityListById(Map map);
}
