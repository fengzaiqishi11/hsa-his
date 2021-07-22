package cn.hsa.module.inpt.individualInpatient.dao;



import cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO;

import java.util.Map;

public interface InsureIndividualInpatientDAO {


    InsureIndividualInpatientDTO queryInpatient(Map<String, Object> params);
}
