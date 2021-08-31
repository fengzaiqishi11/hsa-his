package cn.hsa.module.outpt.outptclassifyclasses.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptclassifyclasses.bo
 * @Class_name: OutptClassifyClassesBO
 * @Describe: 挂号类别排班业务逻辑层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/8/11 10:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptClassifyClassesBO {

    /**
     * @Menthod getById
     * @Desrciption  通过ID查询单条数据
     * @param outptClassifyClassesDTO 主键id
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO
     **/
    OutptClassifyClassesDTO getClassifyClassesById(OutptClassifyClassesDTO outptClassifyClassesDTO);

    /**
     * @Menthod queryDoctorByOuptClassify
     * @Desrciption   通过挂号类别id拿到拥有此挂号类别科室操作权限的医生职工
     * @param workTypeCode 职工类型列表
     * @param cyId 挂号类别id
     * @param hospCode 医院编码
     * @Author xingyu.xie
     * @Date   2020/10/26 16:35
     * @Return java.util.Map
     **/
    Map queryDoctorByOuptClassify(List<String> workTypeCode,String cyId,String hospCode);

    /**
     * @Menthod mergeSelectDorctorAndAllDoctor
     * @Desrciption  将某一挂号号别排班记录下的已选医生和该科室下的所有记录合并
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    Map mergeSelectDorctorAndAllDoctor(List<String> workTypeCode,String csId, String id,String cyId,String hospCode);

    /**
     * @Menthod queryAll
     * @Desrciption  查询所有的挂号类别排班记录
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    List<OutptClassifyClassesDTO> queryClassifyClassesAll(OutptClassifyClassesDTO outptClassifyClassesDTO);


    /**
     * @Menthod queryPage
     * @Desrciption  通过实体作为筛选分页条件查询挂号类别排班
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:36
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    PageDTO queryClassifyClassesPage(OutptClassifyClassesDTO outptClassifyClassesDTO);


    /**
     * @Menthod queryClassesDoctorByClassifyClassesId
     * @Desrciption 通过挂号类别排班表的id去查询医生排班记录
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     **/
    List<OutptClassesDoctorDTO> queryClassesDoctorByClassifyClassesId(OutptClassifyClassesDTO outptClassifyClassesDTO);

    /**
     * @Menthod queryClassesDoctorAllGroupByCyId
     * @Desrciption 1.通过科室id进行分组
     *              2.通过挂号类别id分组
     *              3.通过星期几进行分组
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     *
     * @return*/
    Map<String, Map<String, Map<String, List<OutptClassesDoctorDTO>>>> queryClassesDoctorAllGroupByCyId(OutptClassifyClassesDTO outptClassifyClassesDTO);

    /**
     * @Menthod insertClassifyClasses
     * @Desrciption  新增多条挂号类别排班记录和多条医生排班记录
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    boolean insertClassifyClassesAndClassesDoctor(OutptClassifyClassesDTO outptClassifyClassesDTO);


    /**
     * @Menthod updateClassifyClasses
     * @Desrciption  修改单条挂号类别排班记录和修改多条医生排班记录
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    boolean updateClassifyClassesAndClassesDoctor(OutptClassifyClassesDTO outptClassifyClassesDTO);

    /**
     * @Menthod updateStatus
     * @Desrciption  作废和启用多条挂号排班数据
     * @param outptClassifyClassesDTO
     * @Author xingyu.xie
     * @Date   2020/8/11 9:42
     * @Return boolean
     **/
    boolean updateStatus(OutptClassifyClassesDTO outptClassifyClassesDTO);

    /**  查询分诊方式 **/
    Map<String,List<Map<String,Object>>> getFZFSCodeDetailByCode(Map<String,Object> map);
}
