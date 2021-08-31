package cn.hsa.module.outpt.outptclassifyclasses.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptclassifyclasses.service
 * @Class_name: OutptClassifyClassesBO
 * @Describe: 挂号类别排班service层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/8/11 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptClassifyClassesService {


    /**
     * @Menthod getById
     * @Desrciption  通过ID查询单条数据
     * @param map 主键id
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO
     **/
    @GetMapping("/web/outpt/outptClassifyClasses/getClassifyClassesById")
    WrapperResponse<OutptClassifyClassesDTO> getClassifyClassesById(Map map);


    /**
     * @Menthod queryDoctorByOuptClassify
     * @Desrciption  通过挂号类别id拿到拥有此挂号类别科室操作权限的医生职工
     * @param map
     * @Author xingyu.xie
     * @Date   2020/10/26 16:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/web/outpt/outptClassifyClasses/mergeSelectDorctorAndAllDoctor")
    WrapperResponse<Map> queryDoctorByOuptClassify(Map map);

    /**
     * @Menthod mergeSelectDorctorAndAllDoctor
     * @Desrciption 将某一挂号号别排班记录下的已选医生和该科室下的所有记录合并
     * @param map
     * @Author xingyu.xie
     * @Date   2020/8/19 19:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>>
     **/
    @GetMapping("/web/outpt/outptClassifyClasses/mergeSelectDorctorAndAllDoctor")
    WrapperResponse<Map> mergeSelectDorctorAndAllDoctor(Map map);

    /**
     * @Menthod queryAll
     * @Desrciption  查询所有的挂号类别排班记录
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    @GetMapping("/web/outpt/outptClassifyClasses/queryClassifyClassesAll")
    WrapperResponse<List<OutptClassifyClassesDTO>> queryClassifyClassesAll(Map map);


    /**
     * @Menthod queryPage
     * @Desrciption  通过实体作为筛选分页条件查询挂号类别排班
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:36
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    @GetMapping("/web/outpt/outptClassifyClasses/queryClassifyClassesPage")
    WrapperResponse<PageDTO> queryClassifyClassesPage(Map map);


    /**
     * @Menthod queryClassesDoctorByClassifyClassesId
     * @Desrciption 通过挂号类别排班表的id去查询医生排班记录
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     **/
    @GetMapping("/web/outpt/outptClassifyClasses/queryClassesDoctorByClassifyClassesId")
    WrapperResponse<List<OutptClassesDoctorDTO>> queryClassesDoctorByClassifyClassesId(Map map);

    /**
     * @Menthod queryClassesDoctorAllGroupByCyId
     * @Desrciption 1.通过科室id进行分组
     *              2.通过挂号类别id分组
     *              3.通过星期几进行分组
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     *
     * @return*/
    @GetMapping("/web/outpt/outptClassifyClasses/queryClassesDoctorAllGroupByCyId")
    WrapperResponse<Map<String, Map<String, Map<String, List<OutptClassesDoctorDTO>>>>> queryClassesDoctorAllGroupByCyId(Map map);

    /**
     * @Menthod insertClassifyClasses
     * @Desrciption  新增多条挂号类别排班记录和多条医生排班记录
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    @PostMapping("/web/outpt/outptClassifyClasses/insertClassifyClassesAndClassesDoctor")
    WrapperResponse<Boolean> insertClassifyClassesAndClassesDoctor(Map map);


    /**
     * @Menthod updateClassifyClasses
     * @Desrciption  修改单条挂号类别排班记录和修改多条医生排班记录
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    @PostMapping("/web/outpt/outptClassifyClasses/updateClassifyClassesAndClassesDoctor")
    WrapperResponse<Boolean> updateClassifyClassesAndClassesDoctor(Map map);

    /**
     * @Menthod updateStatus
     * @Desrciption  作废和启用多条挂号排班数据
     * @param map
     * @Author xingyu.xie
     * @Date   2020/8/11 9:42
     * @Return boolean
     **/
    @PostMapping("/web/outpt/outptClassifyClasses/updateStatus")
    WrapperResponse<Boolean> updateStatus(Map map);

    /**
     * 查询分诊方式代码值
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/21 10:56
     **/
    WrapperResponse<Map<String,List<Map<String,Object>>>> getFZFSCodeDetailByCode(Map map);
}
