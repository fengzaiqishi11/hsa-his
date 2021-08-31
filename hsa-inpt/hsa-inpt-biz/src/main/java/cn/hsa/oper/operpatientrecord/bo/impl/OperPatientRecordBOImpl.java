package cn.hsa.oper.operpatientrecord.bo.impl;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.oper.operpatientrecord.bo.OperPatientRecordBO;
import cn.hsa.module.oper.operpatientrecord.dao.OperPatientRecordDAO;
import cn.hsa.module.oper.operpatientrecord.dto.OperPatientRecordDTO;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
   * @Package_name: cn.hsa.oper.operpatientrecord.bo.impl
   * @Class_name: OperPatientRecordBOImpl
   * @Describe: 手术病人信息查询实现类
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/23 14:04
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/

@Component
@Slf4j
public class OperPatientRecordBOImpl extends HsafBO implements OperPatientRecordBO {

    @Resource
   private OperPatientRecordDAO operPatientRecordDAO;
    /**
       * @Method queryOperPatientPage
       * @Describe: 手术病人分页查询
       * @Author: luonianxin
       * @Date: 2021/4/23 14:01
       * @Return: cn.hsa.base.PageDTO
    **/

    @Override
    public PageDTO queryOperPatientPage(OperPatientRecordDTO operPatientRecordDTO) {
        // 设置下一个请求为分页请求
        PageHelper.startPage(operPatientRecordDTO.getPageNo(),operPatientRecordDTO.getPageSize());
        List<OperPatientRecordDTO> operPatientRecordDTOList = operPatientRecordDAO.queryOperPatientRecordAll(operPatientRecordDTO);
        return PageDTO.of(operPatientRecordDTOList);
    }

    /**
       * @Method queryOperPatientPage
       * @Describe: 手术病人分页查询
       * @Author: luonianxin
       * @Date: 2021/4/23 14:01
       * @Return: cn.hsa.base.PageDTO
    **/

    @Override
    public PageDTO queryOperPatientRecords(OperPatientRecordDTO operPatientRecordDTO) {
        List<OperPatientRecordDTO> operPatientRecordDTOList = null;
        // 设置下一个请求为分页请求
        PageHelper.startPage(operPatientRecordDTO.getPageNo(),operPatientRecordDTO.getPageSize());
        final  String costedOperationStatus = "4";
        if(costedOperationStatus.equals(operPatientRecordDTO.getStatusCode())){
            operPatientRecordDTO.setStatusCode(null);
            // 0 或者为空表示不记账，1表示已记账
            operPatientRecordDTO.setIsCost("1");
            operPatientRecordDTOList = operPatientRecordDAO.queryOperPatientRecords(operPatientRecordDTO);
        }else{
            operPatientRecordDTOList = operPatientRecordDAO.queryOperPatientRecords(operPatientRecordDTO);
        }
        return PageDTO.of(operPatientRecordDTOList);
    }

    /***
     *  查询非手术疾病的信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/24 15:16
     **/
    @Override
    public PageDTO getNonSurgicalDiseasesInfo(Map<String,String> params) {
        Object strPageNo = params.get("pageNo");
        Object strPageSize = params.get("pageSize");
        PageHelper.startPage((int)strPageNo,(int)strPageSize);
        List<Map<String,String>> dataList = operPatientRecordDAO.getNonSurgicalDiseasesInfo(params);
        return PageDTO.of(dataList);
    }
}
