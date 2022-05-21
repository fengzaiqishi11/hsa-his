package cn.hsa.module.inpt.inregister.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.inptvisit.bo
 * @Class_name: InptVisitBO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/8 14:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptVisitBO {

    /**
     * @Method: updateVisit
     * @Description: 更新住院病人表
     * @Param: [inptVisitDO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/14 15:40
     * @Return: boolean
     **/
    boolean updateInptVisit(InptVisitDTO inptVisitDTO);

    /**
     * @Method: getVisitByAdviceId
     * @Description: 通过医嘱ID获取住院病人信息
     * @Param: [adviceIds, hospCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 9:17
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **/
    List<InptVisitDTO> getVisitByAdviceId(List<String> adviceIds, String hospCode);

    /**
     * @Method queryRegisteredPage
     * @Desrciption 查询已就诊的记录
     * @Param
     * [inptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:08
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryRegisteredPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryUnregisteredPage
     * @Desrciption 查询未就诊但门诊需要转入院的记录
     * @Param
     * [inptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:08
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryUnregisteredPage(OutptVisitDTO outptVisitDTO);

    /**
     * @Method save
     * @Desrciption 新增、编辑入院登记
     * @Param
     * [inptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:08
     * @Return java.lang.Boolean
     **/
    String save(InptVisitDTO inptVisitDTO);
    /**
     * @Method queryPrintInpt
     * @Desrciption 查询打印住院证
     * @Param [inptVisitDTO]
     * @Author yuelong.chen
     * @Date 2021/11/22 16:08
     * @Return cn.hsa.base.PageDTO
     **/
     OutptVisitDTO queryPrintInpt(Map map);

    /**
     * @Method queryByCertNo
     * @Desrciption 新增入院登记病人住院状态查询
     * @Param
     * [String]
     * @Author caoliang
     * @Date   2021/6/25 17:08
     * @Return InptVisitDTO
     *
     * @return*/
    InptVisitDTO queryByCertNo(InptVisitDTO inptVisitDTO);

    /**
     * @Method cancelRegister
     * @Desrciption 取消入院登记
     * @Param
     * [inptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/24 20:26
     * @Return java.lang.Boolean
     **/
    Boolean deleteRegister(InptVisitDTO inptVisitDTO);

    /**
     * @Method: updateInptVisitAmount
     * @Description: 回写就诊表金额，余额
     * @Param: [inptVisitDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 14:47
     * @Return: Boolean
     **/
    Boolean updateInptVisitAmount(List<InptVisitDTO> visitDTOList);

    /**
     * @Method queryInptVisitById()
     * @Desrciption  根据就诊id 查询医保病人的医保登记号 医疗机构编码
     * @Param id:就诊id hospCode:医院编码
     *
     * @Author fuhui
     * @Date   2020/11/16 16:17
     * @Return  InptVisitDTO 住院病人数据传输对象
     **/
   InptVisitDTO getInptVisitById(InptVisitDTO inptVisitDTO);

    /**
     * @Method queryInsureRegisterPage
     * @Desrciption 获取医保登记人员信息
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/22 14:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
     **/
    PageDTO queryInsureRegisterPage(InptVisitDTO inptVisitDTO);

    /**
     * @Method saveInsureInRegister
     * @Desrciption 医保入院登记
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/12/21 20:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean saveInsureInRegister(InptVisitDTO inptVisitDTO);

    /**
     * @Description: 获取选择的农合患者信息保存到医院本地数据库
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/19 19:26
     * @Return
     */
    Boolean saveNHInsureData(InptVisitDTO inptVisitDTO);

    /**
     * @Method deleteInsureRegister
     * @Desrciption  取消医保入院登记
     * @Param [map]
     * @Author 廖继广
     * @Date   2020/12/21 18:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    Boolean deleteInsureRegister(InptVisitDTO inptVisitDTO);

    /**
    * @Menthod saveInsureInRegisterEMD
    * @Desrciption  电子凭证医保登记
     * @param inptVisitDTO
    * @Author xingyu.xie
    * @Date   2021/3/1 14:33
    * @Return java.lang.Boolean
    **/
    Boolean saveInsureInRegisterEMD(InptVisitDTO inptVisitDTO);

    /**
     * @Method updateInsureInptRegister
     * @Desrciption 修改医保入院登记信息
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date   2020/11/02 19:32
     * @Return java.lang.Boolean
     **/
    Boolean updateInsureInptRegister(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod queryPatients
     * @Desrciption  在院病人信息查询
     * @param paramMap
     * @Author pengbo
     * @Date   2021/3/5 14:45
     * @return PageDTO
     **/
    PageDTO queryPatients(Map<String, Object> paramMap);

    /**
     * @Description: 获取当前医院药品。材料。项目
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/21 19:50
     * @Return
     */
	Boolean saveNHInsureDrugItemData(Map map);
    /**
     * @param inptVisitDTO
     * @Description: 查询当前科室病人以及以前转科的病人用于退费管理查询，
     * @Param:
     * @Author: pengbo
     * @Date 2021/4/28 19:53
     * @Return
     */
    PageDTO queryInptVisitAndZkPage(InptVisitDTO inptVisitDTO);

    /*  *//**
     * @Method: inRegisterByArtificial
     * @Description: 手工登记入院
     * @Param: map
     * @Author: liaojiguang
     * @Email: 847025096@qq.com
     * @Date: 2021/3/18 9:17
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **//*
    Boolean inRegisterByArtificial(InptVisitDTO inptVisitDTO);*/


    Boolean updateUplod(InptVisitDTO inptVisitDTO);
}
