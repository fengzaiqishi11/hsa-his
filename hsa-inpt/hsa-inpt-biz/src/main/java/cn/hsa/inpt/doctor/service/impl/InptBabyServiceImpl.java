package cn.hsa.inpt.doctor.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.bo.InptBabyBO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.service.InptBabyService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.doctor.service.impl
 * @Class_name: InptBabyServiceImpl
 * @Describe(描述):住院婴儿ServiceImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/01 10:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/inpt/inptBaby")
@Service("inptBabyService_provider")
public class InptBabyServiceImpl extends HsafService implements InptBabyService {

    @Resource
    private InptBabyBO inptBabyBO;

    /**
     * @Menthod findByCondition
     * @Desrciption 根据查询条件查询住院婴儿信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/1 10:06 
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptBabyDTO>
     */
    @Override
    public List<InptBabyDTO> findByCondition(Map<String,Object> param) {
        return inptBabyBO.findByCondition(MapUtils.get(param,"inptBabyDTO"));
    }

    /**
     * @Menthod: queryByVisitId
     * @Desrciption: 根据就诊id查询出对应的新生婴儿列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryByVisitId(Map map) {
        return WrapperResponse.success(inptBabyBO.queryByVisitId(MapUtils.get(map, "inptVisitDTO")));
    }

    /**
     * @Menthod: saveBaby
     * @Desrciption: 根据就诊id查询出对应的新生婴儿列表
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveBaby(Map map) {
        return WrapperResponse.success(inptBabyBO.saveBaby(MapUtils.get(map, "inptBabyDTO")));
    }

    /**
     * @Menthod: getById
     * @Desrciption: 根据婴儿id查询婴儿信息
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: InptBabyDTO
     **/
    @Override
    public WrapperResponse<InptBabyDTO> getById(Map map) {
        return WrapperResponse.success(inptBabyBO.getById(MapUtils.get(map, "inptBabyDTO")));
    }
}
