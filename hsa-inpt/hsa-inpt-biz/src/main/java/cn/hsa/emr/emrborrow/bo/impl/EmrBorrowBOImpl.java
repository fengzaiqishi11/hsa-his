package cn.hsa.emr.emrborrow.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.emr.emrborrow.bo.EmrBorrowBO;
import cn.hsa.module.emr.emrborrow.dao.emrBorrowDAO;
import cn.hsa.module.emr.emrborrow.dto.EmrBorrowDTO;
import cn.hsa.module.emr.emrpatient.dao.EmrPatientDAO;
import cn.hsa.module.emr.emrpatient.dto.EmrPatientDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.emr.emrborrow.bo.impl
 * @Class_name: EmrBorrowBOImpl
 * @Describe: 病历借阅
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2021/8/23 14:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrBorrowBOImpl extends HsafBO implements EmrBorrowBO {
    @Resource
    private emrBorrowDAO emrBorrowDAO;
    @Override
    public boolean insertEmrBorrow(EmrBorrowDTO emrBorrowDTO) {
        if (emrBorrowDTO!=null){
            emrBorrowDTO.setId(SnowflakeUtils.getId());
            emrBorrowDTO.setCrteTime(new Date());
            emrBorrowDTO.setIsReturn(Constants.SF.F);
            emrBorrowDTO.setBorrowDuration(3);
        }
        return emrBorrowDAO.insert(emrBorrowDTO);
    }

    @Override
    public boolean updateEmrBorrow(EmrBorrowDTO emrBorrowDTO) {
        return  emrBorrowDAO.updateEmrBorrow(emrBorrowDTO);

    }

    @Override
    public EmrBorrowDTO getEmrBorrow(EmrBorrowDTO emrBorrowDTO) {
        return  emrBorrowDAO.getEmrBorrowInfo(emrBorrowDTO);
    }

    @Override
    public PageDTO queryEmrBorrowList(EmrBorrowDTO emrBorrowDTO) {
        PageHelper.startPage(emrBorrowDTO.getPageNo(),emrBorrowDTO.getPageSize());
        List<EmrBorrowDTO> emrBorrowDOS = emrBorrowDAO.queryEmrBorrowList(emrBorrowDTO);
        return PageDTO.of(emrBorrowDOS);
    }
}
