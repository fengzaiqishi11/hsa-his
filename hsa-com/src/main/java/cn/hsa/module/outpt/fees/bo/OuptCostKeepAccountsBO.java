package cn.hsa.module.outpt.fees.bo;

import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;

import java.util.List;
import java.util.Map;

/**
 * @author luonianxin
 */
public interface OuptCostKeepAccountsBO {

    /**
       * 门诊手术补记账
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/26 20:19
    **/
    Boolean insertOutptCostBatch(List<OutptCostDTO> outptCostDTOList);
    /**
       * 更新门诊病人的手术费用
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/31 13:42
    **/
    Boolean updateOperInfoRecord(Map<String,Object> updateParams);
}
