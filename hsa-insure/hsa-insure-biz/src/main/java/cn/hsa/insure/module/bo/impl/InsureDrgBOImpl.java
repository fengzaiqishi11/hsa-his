package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.module.insure.drg.bo.InsureDrgBO;
import cn.hsa.module.insure.drg.dao.InsureDrgDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @class_name: InsureDrgBOImpl
 * @project_name: hsa-his
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date:2020/12/10 18:47
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class InsureDrgBOImpl extends HsafBO implements InsureDrgBO {

    @Resource
    private InsureDrgDAO insureDrgDAO;

    @Resource
    private Transpond transpond;

    /**
     * @Method: queryInsureIndividualVisit()
     * @Descrition: 分页查询医保就诊表信息
     * @Pramas: insureIndividualVisitDTO数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: insureIndividualVisitDTO数据对象集合
     */
    @Override
    public WrapperResponse<PageDTO> queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        insureIndividualVisitDTO = insureDrgDAO.queryInsureIndividualVisit(insureIndividualVisitDTO);
        if(insureIndividualVisitDTO !=null){
            Map<String, Object> httpParam = new HashMap<String, Object>();

            httpParam.put("hospCode",insureIndividualVisitDTO.getHospCode());
            httpParam.put("function_id",Constant.Xiangtan.RESTS.BIZC320007);
            httpParam.put("akb020",insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码
            httpParam.put("aaz217",insureIndividualVisitDTO.getMedicalRegNo()); // 就医登记号
            httpParam.put("aac001",insureIndividualVisitDTO.getAac001()); // 个人电脑号
            /*调用医保统一访问接口*/
            Map<String, Object>resultMap = transpond.to(insureIndividualVisitDTO.getHospCode(), "RC0044", Constant.FUNCTION.BIZC320007, httpParam);
        }

        return null;
    }

    /**
     * @Method: queryInsureIndividualVisit()
     * @Descrition: 分页查询医保就诊表信息
     * @Pramas: insureIndividualVisitDTO数据传输对象
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/12/10
     * @Retrun: insureIndividualVisitDTO数据对象集合
     */
    @Override
    public PageDTO queryInsureIndividualVisit(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        PageHelper.startPage(insureIndividualVisitDTO.getPageNo(),insureIndividualVisitDTO.getPageSize());
        List<InsureIndividualVisitDTO> individualVisitDTOList= insureDrgDAO.queryPage(insureIndividualVisitDTO);
        return PageDTO.of(individualVisitDTOList);
    }



}
