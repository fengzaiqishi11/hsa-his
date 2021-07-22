package cn.hsa.module.outpt.outptclassifyclasses.dao;


import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 星期，使用1....7 表示星期1-7
 * 分诊台ID：科室表中科室性质为分诊台的科室ID(OutptClassifyClasses)表数据库访问层
 *
 * @author xingyu.xie
 * @since 2020-08-11 09:03:15
 */
public interface OutptClassifyClassesDAO {

    /**
     * @Menthod getById
     * @Desrciption  通过ID查询单条数据
     * @param outptClassifyClasses 主键id
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO
     **/
    OutptClassifyClassesDTO getClassifyClassesById(OutptClassifyClassesDTO outptClassifyClasses);

    /**
     * @Menthod queryAll
     * @Desrciption  查询所有的挂号类别排班记录
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    List<OutptClassifyClassesDTO> queryClassifyClassesAll(OutptClassifyClassesDTO outptClassifyClasses);


    /**
     * @Menthod queryPage
     * @Desrciption  通过实体作为筛选分页条件查询挂号类别排班
     * @param outptClassifyClasses 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:36
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    List<OutptClassifyClassesDTO> queryClassifyClassesPage(OutptClassifyClassesDTO outptClassifyClasses);


    /**
     * @Menthod queryClassesDoctorByClassifyClassesId
     * @Desrciption 通过挂号类别排班表的id去查询医生排班记录
     * @param outptClassifyClasses 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     **/
    List<OutptClassesDoctorDTO> queryClassesDoctorByClassifyClassesId(OutptClassifyClassesDTO outptClassifyClasses);

    /**
     * @Menthod queryClassesDoctorAll
     * @Desrciption 查询全部的挂号医生排班记录
     * @param outptClassifyClasses 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     **/
    List<OutptClassesDoctorDTO> queryClassesDoctorAll(OutptClassifyClassesDTO outptClassifyClasses);

    List<Map>queryDoctorStartToEndTime(Map map);

    /**
     * @Menthod isClassifyClassesExist
     * @Desrciption  通过id和挂号类别id以及星期数来判断是否重复
     * @param outptClassifyClasses 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return int
     **/
    int isClassifyClassesExist(OutptClassifyClassesDTO outptClassifyClasses);



    /**
     * @Menthod insertClassifyClasses
     * @Desrciption  新增多条挂号类别排班记录
     * @param outptClassifyClassesDTOList 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return int
     **/
    int insertClassifyClasses(@Param("list") List<OutptClassifyClassesDTO> outptClassifyClassesDTOList);

    /**
     * @Menthod insertClassesDoctor
     * @Desrciption  新增多条医生排班记录
     * @param outptClassesDoctorDTOList 医生排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return int
     **/
    int insertClassesDoctor(@Param("list") List<OutptClassesDoctorDTO> outptClassesDoctorDTOList);

    /**
     * @Menthod updateClassesDoctor
     * @Desrciption  修改多条医生排班记录
     * @param outptClassesDoctorDTOList 医生排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return int
     **/
    int updateClassesDoctor(@Param("list") List<OutptClassesDoctorDTO> outptClassesDoctorDTOList);

    /**
     * @Menthod updateClassifyClasses
     * @Desrciption  修改单条挂号类别排班记录
     * @param outptClassifyClasses 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return int
     **/
    int updateClassifyClasses(OutptClassifyClassesDTO outptClassifyClasses);

    /**
     * @Menthod deleteClassifyClassesById
     * @Desrciption  删除单条挂号类别排班记录
     * @param outptClassifyClasses
     * @Author xingyu.xie
     * @Date   2020/8/11 9:42
     * @Return int
     **/
    int updateStatus(OutptClassifyClassesDTO outptClassifyClasses);

    /**
     * @Menthod updateStatusDoctor
     * @Desrciption  根据ccid启用和作废多条挂号类别医生排班记录
     * @param outptClassifyClasses
     * @Author xingyu.xie
     * @Date   2020/8/21 17:20
     * @Return int
     **/
    int updateStatusDoctor(OutptClassifyClassesDTO outptClassifyClasses);

    /**
     * @Menthod deleteClassesDoctorByClassifyClassesId
     * @Desrciption  删除多条医生排班记录
     * @param outptClassifyClasses
     * @Author xingyu.xie
     * @Date   2020/8/11 9:42
     * @Return int
     **/
    int deleteClassesDoctorById(OutptClassifyClassesDTO outptClassifyClasses);

    List<Map<String,Object>> getFZFSCodeDetailByCode(Map<String,Object> map);

    /**
       * 根据班次类别ID，班次ID,与星期查询班次排班信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/25 14:45
    **/
    OutptClassifyClassesDTO getOutptClassifyClassesByCyIdAndCsIdAndWeeks(OutptClassifyClassesDTO params);
}