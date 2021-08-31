package cn.hsa.module.oper.operpatientrecord.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operpatientrecord.dto.OperPatientRecordDTO;

import java.util.Map;

/**
   * @Package_name: cn.hsa.module.oper.operpatientrecord.bo
   * @Class_name: OperPatientRecordBO 手术病人信息查询业务模型
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/23 13:44
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
public interface OperPatientRecordBO {

    /**
       * @Author: luonianxin
       * @param operPatientRecordDTO  手术病人信息传输模型
       * @Date: 2021/4/23 13:45
       * @Return: PageDTO 分页数据传输模型
    **/
    PageDTO queryOperPatientPage(OperPatientRecordDTO operPatientRecordDTO);
    /**
       * @Author: luonianxin
       * @param operPatientRecordDTO  手术病人信息传输模型
       * @Date: 2021/5/11 13:45
       * @Return: PageDTO 分页数据传输模型
    **/
    PageDTO queryOperPatientRecords(OperPatientRecordDTO operPatientRecordDTO);

    /***
       *  查询非手术疾病的信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/24 15:16
    **/
    PageDTO getNonSurgicalDiseasesInfo(Map<String,String> params);

}
