package cn.hsa.module.insure.module.dao;

import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InsureGetInfoDAO {

    /**
     * @Method getSetlInfo
     * @Desrciption 结算清单信息
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-10 15:21
     * @Return cn.hsa.module.insure.module.dto.InsureSettleInfoDTO
     **/
    InsureSettleInfoDTO getSetlInfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method getOutSetlInfo
     * @Desrciption 结算清单信息
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-10 15:21
     * @Return cn.hsa.module.insure.module.dto.InsureSettleInfoDTO
     **/
    InsureSettleInfoDTO getOutSetlInfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryPayinfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:28
     * @Return java.util.List<cn.hsa.module.insure.module.dto.PayInfoDTO>
     **/
    List<InsureIndividualFundDTO> queryPayinfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryPayinfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:23
     * @Return java.util.List<cn.hsa.module.insure.module.dto.PayInfoDTO>
     **/
    List<OpspdiseInfoDTO> queryOpspdiseinfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method selectIsSetlleFee
     * @Desrciption  查询已经结算完成的费用明细集合
     * @Param
     *
     * @Author caoliang
     * @Date   2021/7/20 17:35
     * @Return
     **/
    List<Map<String, Object>> selectIsSetlleFee(Map<String, Object> map);

    /**
     * @Method queryDiseinfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:26
     * @Return java.util.List<cn.hsa.module.insure.module.dto.DiseInfoDTO>
     **/
    List<DiseInfoDTO> queryDiseinfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryIteminfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:26
     * @Return java.util.List<cn.hsa.module.insure.module.dto.ItemInfoDTO>
     **/
    List<ItemInfoDTO> queryIteminfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryOprninfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:26
     * @Return java.util.List<cn.hsa.module.insure.module.dto.OprninfoDTO>
     **/
    List<OprninfoDTO> queryOprninfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryIcuinfo
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 21:27
     * @Return java.util.List<cn.hsa.module.insure.module.dto.IcuInfoDTO>
     **/
    List<IcuInfoDTO> queryIcuinfo(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method getInsureCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 23:09
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO>
     **/
    InsureIndividualCostDTO queryInsureCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryOutCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:28
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    List<InptCostDTO> queryOutCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:28
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    List<InptCostDTO> queryInCost(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryAll
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 17:28
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
     **/
    List<InsureUploadCostDTO> queryAll(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryVisit
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryVisit(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInsureSettle
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InsureSettleInfoDTO>
     *
     * @return*/
    List<PayInfoDTO> queryInsureSettle(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method queryInsure
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.ItemInfoDTO>
     **/
    List<ItemInfoDTO> queryInsure(InsureSettleInfoDTO insureSettleInfoDTO);

    /**
     * @Method insertCost
     * @Desrciption
     * @Param
     * [InsureUpladCostDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    int insertCost(@Param("list")List<InsureUploadCostDTO> insureUploadCostList);

    /**
     * @Method insertSettleInfo
     * @Desrciption
     * @Param
     * [InsureUpladCostDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    int insertSettleInfo(InsureSetlInfoDTO insureSettleInfoDTO);

    String getSetlInfoLocal(InsureSettleInfoDTO insureSettleInfoDTO);
}
