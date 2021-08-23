package cn.hsa.module.insure.inpt.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.bo
 * @Class_name: InsureNurseRecordBO
 * @Describe: 统一支付平台-护理生命记录bo
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-08-22 08:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureNurseRecordBO {

    /**
     * @Menthod: addInsureNurseRecord
     * @Desrciption: 统一支付平台-护理生命记录上传【4602】
     * @Param: visitId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-22 08:55
     * @Return:
     **/
    Map<String, Object> addInsureNurseRecord(Map<String, Object> map);
}
