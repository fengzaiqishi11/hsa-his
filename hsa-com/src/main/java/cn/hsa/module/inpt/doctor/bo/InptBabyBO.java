package cn.hsa.module.inpt.doctor.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.doctor.bo
 * @Class_name: InptBabyBO
 * @Describe(描述):住院婴儿BO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/12/01 9:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptBabyBO {

    /**
     * @Menthod findByCondition
     * @Desrciption 根据查询条件查询住院婴儿信息
     * @param inptBabyDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/1 9:31
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptBabyDTO>
     */
    List<InptBabyDTO> findByCondition(InptBabyDTO inptBabyDTO);

    /**
     * @Menthod: queryByVisitId
     * @Desrciption: 根据就诊id查询出对应的新生婴儿列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: PageDTO
     **/
    PageDTO queryByVisitId(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod: saveBaby
     * @Desrciption: 根据就诊id查询出对应的新生婴儿列表
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: Boolean
     **/
    Boolean saveBaby(InptBabyDTO inptBabyDTO);

    /**
     * @Menthod: getById
     * @Desrciption: 根据婴儿id查询婴儿信息
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-19 15:25
     * @Return: InptBabyDTO
     **/
    InptBabyDTO getById(InptBabyDTO inptBabyDTO);

    /**
     * @Description: 查询婴儿总费用
     * @Param:
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date 2021/08/12 19:47
     * @Return
     */
    InptBabyDTO getBabyCost(Map parm);

    /**
     * @Menthod: updateBabyRegister
     * @Desrciption: 新生儿取消登记
     * @Param: inptBabyDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-08 11:30
     * @Return:
     **/
    Boolean updateBabyRegister(InptBabyDTO inptBabyDTO);
}
