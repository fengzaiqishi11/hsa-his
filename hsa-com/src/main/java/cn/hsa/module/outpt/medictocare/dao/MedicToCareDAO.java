package cn.hsa.module.outpt.medictocare.dao;

import cn.hsa.module.outpt.medictocare.dto.MedicToCareDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author powersi
 * @create 2022-02-28 9:29
 * @desc
 **/
public interface MedicToCareDAO {


    List<MedicToCareDTO> queryMZPage(MedicToCareDTO medicToCareDTO);

    List<MedicToCareDTO> queryZYPage(MedicToCareDTO medicToCareDTO);

    MedicToCareDTO queryVisitById(MedicToCareDTO medicToCareDTO);

    void insertMedicDate(MedicToCareDTO medicToCareDTO);

    List<MedicToCareDTO> queryMedicToCareInfoPage(MedicToCareDTO medicToCareDTO);

    Map<String, Object> getMedicToCareInfoById(String id);

    Boolean updateMedicToCare(Map<String, Object> map);

    void insertOutPtInfo(OutptVisitDTO outptVisitDTO);

    String queryOrgCode(String hospCode);
}
