package cn.hsa.module.inpt.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;

import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt.nurse.bo
 *@Class_name: BackCostByInputBO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/10 11:34
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BackCostByInputBO {

    /**
    * @Method queryBackCostInfoPage
    * @Desrciption 住院退费费用分页查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/10 11:47
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryBackCostInfoPage(InptCostDTO inptCostDTO);

    /**
     * @Method queryBackCostInfoPage
     * @Desrciption 住院退费费用分页查询
     * @param map 参数
     * @Author luonianxin
     * @Date   2021/06/02
     **/
    PageDTO querySurgeryBackCostInfoPage(Map<String,Object> map);

    /**
    * @Method saveBackCostWithInpt
    * @Desrciption 住院退费保存
    * @param map
    * @Author liuqi1
    * @Date   2020/9/10 14:44
    * @Return java.lang.Boolean
    **/
    Boolean saveBackCostWithInpt(Map<String,Object> map);
    /**
     * @Method updateFeeDate
     * @Desrciption 费用改变
     * @param
     * @Author yuelong.chen
     * @Date   2021/11/25 14:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    Boolean updateFeeDate(Map<String,Object> map);

}
