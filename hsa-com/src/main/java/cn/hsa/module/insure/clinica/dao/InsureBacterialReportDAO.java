package cn.hsa.module.insure.clinica.dao;

import cn.hsa.module.insure.clinica.dto.InsureBacterialReportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureBacterialReportDAO
* @Deacription 细菌培养报告记录信息表dao层
* @Author liuhuiming
* @Date 2022-05-09
* @Version 1.0
**/
@Mapper
public interface InsureBacterialReportDAO {

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询
     * @Param
     * 1. InsureBacterialReportDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return
     **/
    List<InsureBacterialReportDTO> queryPageInsureBacterialReport(InsureBacterialReportDTO insureBacterialReportDTO);

    /**
     * @Menthod insert()
     * @Desrciption 新增
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int insertInsureBacterialReport(InsureBacterialReportDTO insureBacterialReportDTO);

    /**
     * @Menthod updateSelective()
     * @Desrciption 修改
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    int updateSelective(Map map);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    InsureBacterialReportDTO queryByUuid(Long uuid);

    /**
     * @Menthod queryByUuid()
     * @Desrciption 查询单条
     * @Param
     *1. insureEmrAdminfoDTO  参数数据对象
     * @Author liuhuiming
     * @Date   2022/3/25 17:02
     * @Return int
     **/
    List<InsureBacterialReportDTO> queryByMdtrtSn(String mdtrtSn);
}
