package cn.hsa.module.emr.emrquality.dao;

import cn.hsa.module.emr.emrquality.dto.EmrQualityAgingDTO;
import cn.hsa.module.emr.emrquality.entity.EmrQualityAgingDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrquality.dao
 * @Class_name: EmrQualityAgingDAO
 * @Describe: 电子病历时效质控dao层
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/9/23 14:01
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrQualityAgingDAO {
    /**
     * @Menthod insertQualityRecord
     * @param  emrQualityAgingDO
     * @Describe: 新增时效记录
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/23 14:18
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    int insertQualityRecord(EmrQualityAgingDO emrQualityAgingDO);
    /**
     * @Menthod updateQualityRecord
     * @param  emrQualityAgingDO
     * @Describe: 更新时效记录
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/23 14:33
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    int updateQualityRecord(EmrQualityAgingDO emrQualityAgingDO);

    /**
     * @Menthod deleteEmrQualityAging
     * @param  map
     * @Describe:  删除时效记录
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/24 15:09
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    int deleteEmrQualityAging(Map map);

    /**
     * @Menthod queryEmrTemplateList
     * @param  map
     * @Describe: 查询病历模板
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/24 10:09
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<Map> queryEmrTemplateList(Map map);

    /**
     * @Menthod queryEmrQualityList
     * @param  emrQualityAgingDTO
     * @Describe: 查询时效质控列表
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/24 11：56
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<EmrQualityAgingDTO> queryEmrQualityList(EmrQualityAgingDTO emrQualityAgingDTO);

    /**
     * @Menthod queryEmrQualityListById
     * @param  emrQualityAgingDTO
     * @Describe: 通过时效提醒id查询时效质控列表
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 15：00
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<EmrQualityAgingDTO> queryEmrQualityListById(EmrQualityAgingDTO emrQualityAgingDTO);

    /**
     * @Menthod queryEmrQualityListByEmrCode
     * @param  emrQualityAgingDTO
     * @Describe: 通过病历模板编码查询
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/9/26 15：08
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    EmrQualityAgingDTO queryEmrQualityListByEmrCode(EmrQualityAgingDTO emrQualityAgingDTO);

    /**
     * @Menthod queryRyOrCyUnwriteEmr
     * @param  emrQualityAgingDTO
     * @Describe: 查询以入、出时间为基准时间的超时病历
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/11/29 14：16
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<EmrQualityAgingDTO> queryRyOrCyUnwriteEmr(EmrQualityAgingDTO emrQualityAgingDTO);

    /**
     * @Menthod queryTurnDeptUnwriteEmr
     * @param  emrQualityAgingDTO
     * @Describe: 查询转科为基准时间的超时病历
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/11/29 14：37
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<EmrQualityAgingDTO> queryTurnDeptUnwriteEmr(EmrQualityAgingDTO emrQualityAgingDTO);

    /**
     * @Menthod queryOperUnwritEmr
     * @param  emrQualityAgingDTO
     * @Describe: 查询以手术时间为基准时间的超时病历
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/11/29 14：15
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<EmrQualityAgingDTO> queryOperUnwritEmr(EmrQualityAgingDTO emrQualityAgingDTO);

    /**
     * @Menthod queryAdviceUnwritEmr
     * @param  emrQualityAgingDTO
     * @Describe: 查询以医嘱时间为基准时间的超时病历
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021/11/29 14：14
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    List<EmrQualityAgingDTO> queryAdviceUnwritEmr(EmrQualityAgingDTO emrQualityAgingDTO);
}
