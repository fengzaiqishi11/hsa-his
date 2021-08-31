package cn.hsa.inpt.doctor.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.inpt.doctor.bo.InptBabyBO;
import cn.hsa.module.inpt.doctor.dao.InptBabyDAO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.doctor.bo.impl
 * @Class_name: InptBabyBOImpl
 * @Describe(描述): 住院婴儿BOImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/01 9:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class InptBabyBOImpl extends HsafBO implements InptBabyBO {

    @Resource
    private InptBabyDAO inptBabyDAO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * @Menthod findByCondition
     * @Desrciption 根据查询条件查询住院婴儿信息
     * @param inptBabyDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/1 9:31
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptBabyDTO>
     */
    @Override
    public List<InptBabyDTO> findByCondition(InptBabyDTO inptBabyDTO) {
        return inptBabyDAO.findByCondition(inptBabyDTO);
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
    public PageDTO queryByVisitId(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptBabyDTO> list = inptBabyDAO.queryByVisitId(inptVisitDTO);
        return PageDTO.of(list);
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
    public Boolean saveBaby(InptBabyDTO inptBabyDTO) {
        if(StringUtils.isEmpty(inptBabyDTO.getCode())) {
            // 获取编码，根据票据规则生成
            Map map = new HashMap<>();
            map.put("hospCode", inptBabyDTO.getHospCode());
            //婴儿票据规则编码
            map.put("typeCode", "29");
            WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(map);
            inptBabyDTO.setCode(orderNo.getData());
        }
        // 校验婴儿编号是否相同
        InptBabyDTO dto = inptBabyDAO.queryByCode(inptBabyDTO);
        if (dto != null) {
            throw new RuntimeException("婴儿【" + inptBabyDTO.getCode() + "】编号已存在");
        }
        if (StringUtils.isEmpty(inptBabyDTO.getId())) {
            // 新增
            inptBabyDTO.setId(SnowflakeUtils.getId());
            inptBabyDAO.insertBaby(inptBabyDTO);
        } else {
            // 编辑
            inptBabyDAO.updateBaby(inptBabyDTO);
        }
        return true;
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
    public InptBabyDTO getById(InptBabyDTO inptBabyDTO) {
        return inptBabyDAO.getById(inptBabyDTO);
    }


    /**
     * @Description: 查询婴儿总费用
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/08/12 19:49
     * @Return
     */
    @Override
    public InptBabyDTO getBabyCost(Map param) {
        return inptBabyDAO.getBabyCost(param);
    }
}
