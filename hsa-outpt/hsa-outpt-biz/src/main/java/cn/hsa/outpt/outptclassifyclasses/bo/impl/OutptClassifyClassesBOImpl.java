package cn.hsa.outpt.outptclassifyclasses.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.classes.bo.OutptClassesBO;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;
import cn.hsa.module.outpt.outptclassify.bo.OutptClassifyBO;
import cn.hsa.module.outpt.outptclassify.dto.OutptClassifyDTO;
import cn.hsa.module.outpt.outptclassifyclasses.bo.OutptClassifyClassesBO;
import cn.hsa.module.outpt.outptclassifyclasses.dao.OutptClassifyClassesDAO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.outptclassifyclasses.bo.impl
 * @Class_name: OutptClassifyClassesBOImpl
 * @Describe: 挂号类别排班业务逻辑层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/8/11 10:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptClassifyClassesBOImpl extends HsafBO implements OutptClassifyClassesBO {

    @Resource
    OutptClassifyClassesDAO outptClassifyClassesDAO;

    @Resource
    SysUserService sysUserService;

    @Resource
    OutptClassifyBO outptClassifyBO;

    @Resource
    OutptClassesBO outptClassesBO;

    /**
     * @Menthod getById
     * @Desrciption  通过ID查询单条数据
     * @param outptClassifyClassesDTO 主键id
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO
     **/
    public OutptClassifyClassesDTO getClassifyClassesById(OutptClassifyClassesDTO outptClassifyClassesDTO){
        return outptClassifyClassesDAO.getClassifyClassesById(outptClassifyClassesDTO);
    }

    /**
     * @Menthod queryDoctorByOuptClassify
     * @Desrciption   通过挂号类别id拿到拥有此挂号类别科室操作权限的医生职工
     * @param workTypeCode 职工类型列表
     * @param cyId 挂号类别id
     * @param hospCode 医院编码
     * @Author xingyu.xie
     * @Date   2020/10/26 16:33
     * @Return java.util.Map
     **/
    public Map queryDoctorByOuptClassify(List<String> workTypeCode,String cyId,String hospCode){
        // 通过挂号类别id拿到挂号类别的科室和科室id
        OutptClassifyDTO outptClassifyDTO = new OutptClassifyDTO();
        outptClassifyDTO.setId(cyId);
        outptClassifyDTO.setHospCode(hospCode);
        OutptClassifyDTO byId = this.outptClassifyBO.getById(outptClassifyDTO);

        // 再通过科室ID拿到可操作当前科室的职工类型（workTypeCode）为10开头所有用户信息
        Map map = new HashMap();
        map.put("workTypeCode",workTypeCode);
        map.put("hospCode",hospCode);
        map.put("deptId",byId.getDeptId());
        WrapperResponse<List<SysUserDTO>> listWrapperResponse1 = sysUserService.queryUserByOperationDeptId(map);
        List<SysUserDTO> sysUserDTOList = listWrapperResponse1.getData();

        Map returnMap = new HashMap();
        returnMap.put("deptName",byId.getDeptName());
        returnMap.put("deptId",byId.getDeptId());
        returnMap.put("classifyName",byId.getName());
        returnMap.put("outptClassesDoctorDTOList",sysUserDTOList);
        return returnMap;
    }


    /**
     * @Menthod mergeSelectDorctorAndAllDoctor
     * @Desrciption
     * @param workTypeCode 员工类型List
     * @param id 挂号类别排班id
     * @param cyId 挂号类别id
     * @param hospCode 医院编码
     * @Author xingyu.xie
     * @Date   2020/9/1 10:59
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     **/
    public Map mergeSelectDorctorAndAllDoctor(List<String> workTypeCode,String csId, String id,String cyId,String hospCode){

        // 通过挂号类别id拿到挂号类别的科室和科室id
        OutptClassifyDTO outptClassifyDTO = new OutptClassifyDTO();
        outptClassifyDTO.setId(cyId);
        outptClassifyDTO.setHospCode(hospCode);
        OutptClassifyDTO byId = this.outptClassifyBO.getById(outptClassifyDTO);

        // 再通过科室ID拿到可操作当前科室的职工类型（workTypeCode）为10开头所有用户信息
        Map map = new HashMap();
        map.put("workTypeCode",workTypeCode);
        map.put("hospCode",hospCode);
        map.put("deptId",byId.getDeptId());
        WrapperResponse<List<SysUserDTO>> listWrapperResponse1 = sysUserService.queryUserByOperationDeptId(map);
        List<SysUserDTO> sysUserDTOList = listWrapperResponse1.getData();
        OutptClassifyClassesDTO outptClassifyClassesDTO = new OutptClassifyClassesDTO();

        outptClassifyClassesDTO.setId(id);

        outptClassifyClassesDTO.setCyId(cyId);

        outptClassifyClassesDTO.setCsId(csId);

        outptClassifyClassesDTO.setHospCode(hospCode);
        // 通过排班id和挂号类别id和班次id来验证是否是已进行过排班
        List<OutptClassifyClassesDTO> outptClassifyClassesDTOS = outptClassifyClassesDAO.queryClassifyClassesAll(outptClassifyClassesDTO);

        List<OutptClassesDoctorDTO> outptClassesDoctorDTOS = new ArrayList<>();
        // 为空则是此排班没有医生记录
        if (!ListUtils.isEmpty(outptClassifyClassesDTOS)){
            // 不为空则查询 此排班记录的医生
            outptClassesDoctorDTOS = outptClassifyClassesDAO.queryClassesDoctorByClassifyClassesId(outptClassifyClassesDTO);
        }
        HashMap<String,OutptClassesDoctorDTO> userMap = new HashMap<>();

        List<OutptClassesDoctorDTO> outptClassesDoctorDTOList = new ArrayList<>();

        if (!ListUtils.isEmpty(sysUserDTOList)){
            // 对该挂号类别对应科室下的所有医生进行循环

            for (SysUserDTO allDoctor :sysUserDTOList){

                OutptClassesDoctorDTO outptClassesDoctorDTO = new OutptClassesDoctorDTO();

                outptClassesDoctorDTO.setDoctorName(allDoctor.getName());

                outptClassesDoctorDTO.setDoctorId(allDoctor.getId());

                userMap.put(allDoctor.getId(),outptClassesDoctorDTO);

            }
        }
        if (!ListUtils.isEmpty(outptClassesDoctorDTOS)){

            for (OutptClassesDoctorDTO item:outptClassesDoctorDTOS){

                if (userMap.containsKey(item.getDoctorId())){

                    OutptClassesDoctorDTO outptClassesDoctorDTO = userMap.get(item.getDoctorId());
                    outptClassesDoctorDTO.setId(item.getId());
                    outptClassesDoctorDTO.setClinicId(item.getClinicId());
                    outptClassesDoctorDTO.setRegisterNum(item.getRegisterNum());

                }else {
                    userMap.put(item.getDoctorId(),item);
                }
            }

        }
        if (userMap.size()>0){

            for (String key:userMap.keySet()){

                outptClassesDoctorDTOList.add(userMap.get(key));

            }
        }
        Map returnMap = new HashMap();
        returnMap.put("deptName",byId.getDeptName());
        returnMap.put("deptId",byId.getDeptId());
        returnMap.put("classifyName",byId.getName());
        returnMap.put("outptClassesDoctorDTOList",outptClassesDoctorDTOList);
        return returnMap;
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询所有的挂号类别排班记录
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    public List<OutptClassifyClassesDTO> queryClassifyClassesAll(OutptClassifyClassesDTO outptClassifyClassesDTO){
        return outptClassifyClassesDAO.queryClassifyClassesAll(outptClassifyClassesDTO);
    }


    /**
     * @Menthod queryPage
     * @Desrciption  通过实体作为筛选分页条件查询挂号类别排班
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:36
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    public PageDTO queryClassifyClassesPage(OutptClassifyClassesDTO outptClassifyClassesDTO){
        PageHelper.startPage(outptClassifyClassesDTO.getPageNo(),outptClassifyClassesDTO.getPageSize());
        String weeks = outptClassifyClassesDTO.getWeeks();
        // 将传入的weeks按逗号分割为list
        if (StringUtils.isNotEmpty(weeks)){
            outptClassifyClassesDTO.setWeekList(Arrays.asList(weeks.split(",")));
        }else {
            outptClassifyClassesDTO.setWeekList(Arrays.asList("empty"));
        }
        List<OutptClassifyClassesDTO> outptClassifyClassesDTOS = outptClassifyClassesDAO.queryClassifyClassesPage(outptClassifyClassesDTO);

        return PageDTO.of(outptClassifyClassesDTOS);
    }


    /**
     * @Menthod queryClassesDoctorByClassifyClassesId
     * @Desrciption 通过挂号类别排班表的id去查询医生排班记录
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     **/
    public List<OutptClassesDoctorDTO> queryClassesDoctorByClassifyClassesId(OutptClassifyClassesDTO outptClassifyClassesDTO){
        return outptClassifyClassesDAO.queryClassesDoctorByClassifyClassesId(outptClassifyClassesDTO);
    }

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
    public Map<String, Map<String, Map<String, List<OutptClassesDoctorDTO>>>> queryClassesDoctorAllGroupByCyId(OutptClassifyClassesDTO outptClassifyClassesDTO){

        List<OutptClassesDoctorDTO> outptClassesDoctorDTOS = outptClassifyClassesDAO.queryClassesDoctorAll(outptClassifyClassesDTO);
        // 先通过科室id将所有的医生排班分组
        Map<String,Map<String,Map<String, List<OutptClassesDoctorDTO>>>> mapMap = new HashMap<>();
        try {
            Map<String, List<OutptClassesDoctorDTO>> collect = outptClassesDoctorDTOS.stream().collect(Collectors.groupingBy(OutptClassesDoctorDTO::getDeptId, Collectors.toList()));
            // 再通过挂号类别id将同一科室下的医生排班进行分组
            for (String deptId: collect.keySet()){
                Map<String,Map<String, List<OutptClassesDoctorDTO>>> mapMap1 = new HashMap<>();
                List<OutptClassesDoctorDTO> outptClassesDoctorDTOS1 = collect.get(deptId);
                Map<String, List<OutptClassesDoctorDTO>> collect1 = outptClassesDoctorDTOS1.stream().collect(Collectors.groupingBy(OutptClassesDoctorDTO::getCyId, Collectors.toList()));
                //最后通过星期几将其分组
                for (String cyId:collect1.keySet()){
                    List<OutptClassesDoctorDTO> outptClassesDoctorDTOList = collect1.get(cyId);
                    Map<String, List<OutptClassesDoctorDTO>> collect2 = outptClassesDoctorDTOList.stream().collect(Collectors.groupingBy(OutptClassesDoctorDTO::getWeeks, Collectors.toList()));
                    mapMap1.put(cyId,collect2);
                }
                mapMap.put(deptId,mapMap1);
            }
        }catch (Exception e){
            throw new AppException("已排班的挂号类别不存在,请重新设置！");
        }


        return mapMap;
    }

    /**
     * @Menthod insertClassifyClasses
     * @Desrciption  新增多条挂号类别排班记录和多条医生排班记录
     * 1.
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    public boolean insertClassifyClassesAndClassesDoctor(OutptClassifyClassesDTO outptClassifyClassesDTO){

        String hospCode = outptClassifyClassesDTO.getHospCode();
        String classifyName = outptClassifyClassesDTO.getClassifyName();

        String classesName = outptClassifyClassesDTO.getClassesName();

        String cyId = outptClassifyClassesDTO.getCyId();

        String csId = outptClassifyClassesDTO.getCsId();
        if (StringUtils.isEmpty(cyId)){
            throw new AppException("挂号类别id不能为空");
        }
        if (StringUtils.isEmpty(csId)){
            throw new AppException("班次id不能为空");
        }

        //存储医生排班的数据记录
        List<OutptClassesDoctorDTO> outptClassesDoctorDTOList = outptClassifyClassesDTO.getOutptClassesDoctorDTOList();
        // 拿到选择的星期
        List<String> weekList = outptClassifyClassesDTO.getWeekList();
        // 挂号号别排班要插入的记录
        List<OutptClassifyClassesDTO> inserClassifyList = new ArrayList<>();
        // 医生排班要插入的记录
        List<OutptClassesDoctorDTO> inserDoctortList = new ArrayList<>();

        // 查询此挂号类别已进行排班的星期数
        OutptClassifyClassesDTO dto = new OutptClassifyClassesDTO();
        // 查重条件
        dto.setHospCode(outptClassifyClassesDTO.getHospCode());

        dto.setCyId(outptClassifyClassesDTO.getCyId());

        dto.setCsId(outptClassifyClassesDTO.getCsId());

        dto.setWeekList(weekList);

        dto.setIsValid("1");

        List<OutptClassifyClassesDTO> outptClassifyClassesDTOS = outptClassifyClassesDAO.queryClassifyClassesPage(dto);

        if (!ListUtils.isEmpty(outptClassifyClassesDTOS)){
            List<String> isExitWeeks = new ArrayList<>();
            for (OutptClassifyClassesDTO item :outptClassifyClassesDTOS){
                isExitWeeks.add(item.getWeeks());
            }
            List<String> isExitWeeksName = this.changeWeeksToWeeksName(isExitWeeks);

            throw new AppException("挂号类别【"+classifyName+"】" +
                    "在【"+Joiner.on("，").join(isExitWeeksName)+"】的【"+classesName+"】已存在排班记录，无法新增，请检查！");
        }
        // 根据医生id查询出此医生在此挂号类别下已进行排班的时间段
        if (!ListUtils.isEmpty(outptClassesDoctorDTOList)){
            // 根据班次id查询出班次时间
            OutptClassesDTO query = new OutptClassesDTO();
            query.setId(outptClassifyClassesDTO.getCsId());
            query.setHospCode(hospCode);
            OutptClassesDTO byId = outptClassesBO.getById(query);
            Date newStartDate = DateUtils.parse(byId.getStartDate(),"HH:mm");
            Date newEndDate = DateUtils.parse(byId.getEndDate(),"HH:mm");

            outptClassesDoctorDTOList.forEach(item->{
                Map doctorMap = new HashMap();
                doctorMap.put("hospCode",hospCode);
                doctorMap.put("doctorId",item.getDoctorId());
                doctorMap.put("cyId",cyId);
                doctorMap.put("weekList",weekList);
                // 根据医生id，星期列表，挂号类别id 查询此医生在此挂号类别的星期几的排班时间list 列表
                List<Map> maps = outptClassifyClassesDAO.queryDoctorStartToEndTime(doctorMap);

                if (!ListUtils.isEmpty(maps)){
                    List<String> isExitweek = new ArrayList();
                    //判断同一医生在新班次的时间段是否跟同一挂号类别下的此医生已排班记录的时间段重复
                    for (Map map: maps){
                        Date startDate = DateUtils.parse(MapUtils.get(map, "startDate"),"HH:mm");
                        Date endDate = DateUtils.parse(MapUtils.get(map, "endDate"),"HH:mm");
                        if (DateUtils.betweenDate(startDate,endDate,newStartDate)
                                || DateUtils.betweenDate(startDate,endDate,newEndDate)
                                || DateUtils.betweenDate(newStartDate,newEndDate,startDate)
                                || DateUtils.betweenDate(newStartDate,newEndDate,endDate)){
                            isExitweek.add(MapUtils.get(map, "weeks"));
                        }
                    }

                    if (!ListUtils.isEmpty(isExitweek)){
                        // 重复星期去重
                        List<String> collect = isExitweek.stream().distinct().collect(Collectors.toList());

                        List<String> weeksToWeeksName = this.changeWeeksToWeeksName(collect);

                        throw new AppException("【"+item.getDoctorName()+"】"+"在【"+classifyName+"】的【"+
                                Joiner.on("，").join(weeksToWeeksName)+"】已存在排班，并与当前排班时间冲突，请检查");
                    }

                }
            });
        }

        if (!ListUtils.isEmpty(weekList)){
            // 必填的星期不为空则根据多选的星期几循环创建挂号排班记录对象
            weekList.forEach(item->{

                String id = SnowflakeUtils.getId();
                OutptClassifyClassesDTO insertClassifyDTO = new OutptClassifyClassesDTO();
                // id
                insertClassifyDTO.setId(id);
                // 医院编码
                insertClassifyDTO.setHospCode(outptClassifyClassesDTO.getHospCode());
                // 班次ID
                insertClassifyDTO.setCsId(outptClassifyClassesDTO.getCsId());
                // 挂号类别ID
                insertClassifyDTO.setCyId(outptClassifyClassesDTO.getCyId());
                // 分诊台ID
                insertClassifyDTO.setTriageId(outptClassifyClassesDTO.getTriageId());
                // 分诊方式代码
                insertClassifyDTO.setTriageCode(outptClassifyClassesDTO.getTriageCode());
                // 挂号科室ID
                insertClassifyDTO.setDeptId(outptClassifyClassesDTO.getDeptId());
                // 星期几
                insertClassifyDTO.setWeeks(item);
                // 限号数
                insertClassifyDTO.setRegisterNum(outptClassifyClassesDTO.getRegisterNum());
                // 是否叫号
                insertClassifyDTO.setIsCall(outptClassifyClassesDTO.getIsCall());
                // 创建人ID
                insertClassifyDTO.setCrteId(outptClassifyClassesDTO.getCrteId());
                // 创建用户名
                insertClassifyDTO.setCrteName(outptClassifyClassesDTO.getCrteName());
                // 创建时间
                insertClassifyDTO.setCrteTime(outptClassifyClassesDTO.getCrteTime());
                // 加入新增列表
                inserClassifyList.add(insertClassifyDTO);

                // 为每个挂号排班记录对象数据添加相同的医生排班记录
                if (!ListUtils.isEmpty(outptClassesDoctorDTOList)){

                    outptClassesDoctorDTOList.forEach(itemCalassesDoctor->{

                        String classesDoctorId = SnowflakeUtils.getId();
                        OutptClassesDoctorDTO insertDoctorDTO = new OutptClassesDoctorDTO();
                        // 挂号类别医生排班表ID
                        insertDoctorDTO.setId(classesDoctorId);
                        // 医院编码
                        insertDoctorDTO.setHospCode(outptClassifyClassesDTO.getHospCode());
                        // 类别排班ID
                        insertDoctorDTO.setCcId(id);
                        // 医生ID
                        insertDoctorDTO.setDoctorId(itemCalassesDoctor.getDoctorId());
                        // 限号数
                        insertDoctorDTO.setRegisterNum(itemCalassesDoctor.getRegisterNum());
                        // 分诊室ID
                        insertDoctorDTO.setClinicId(itemCalassesDoctor.getClinicId());
                        // 加入新增列表
                        inserDoctortList.add(insertDoctorDTO);

                    });
                }
            });
        }else {
            // 没有星期数据传过来
            throw new AppException("请选择星期");
        }

        if (!ListUtils.isEmpty(inserClassifyList)){

            int i = outptClassifyClassesDAO.insertClassifyClasses(inserClassifyList);

            if (i != inserClassifyList.size()){
                throw new AppException("新增异常，请重试");
            }

        }

        if (!ListUtils.isEmpty(inserDoctortList)){
            int i1 = outptClassifyClassesDAO.insertClassesDoctor(inserDoctortList);

            if (i1 != inserDoctortList.size()){
                throw new AppException("新增异常，请重试");
            }
        }


        return true;
    }


    /**
     * @Menthod updateClassifyClasses
     * @Desrciption  修改单条挂号类别排班记录和修改多条医生排班记录
     * @param outptClassifyClassesDTO 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    @Override
    public boolean updateClassifyClassesAndClassesDoctor(OutptClassifyClassesDTO outptClassifyClassesDTO){

        String hospCode = outptClassifyClassesDTO.getHospCode();

        String classifyName = outptClassifyClassesDTO.getClassifyName();

        OutptClassifyClassesDTO query = outptClassifyClassesDAO.getClassifyClassesById(outptClassifyClassesDTO);

        String ccId = query.getId();

        // 如果挂号类别id和原来的不一样，则说明修改了挂号类别，直接删除全部原来的医生排班数据
        String cyId = outptClassifyClassesDTO.getCyId();
        if (StringUtils.isEmpty(cyId)){
            throw new AppException("挂号类别id不能为空");
        }
        if (!cyId.equals(query.getCyId())){
            OutptClassifyClassesDTO update = new OutptClassifyClassesDTO();
            update.setHospCode(outptClassifyClassesDTO.getHospCode());
            update.setIsValid(Constants.SF.F);
            List<String> idA = new ArrayList<>();
            idA.add(query.getId());
            update.setIds(idA);
            outptClassifyClassesDAO.updateStatusDoctor(update);
        }
        //要修改或新增的医生排班记录
        List<OutptClassesDoctorDTO> outptClassesDoctorDTOList = outptClassifyClassesDTO.getOutptClassesDoctorDTOList();
        //要删除的医生排班记录
        List<String> ids = outptClassifyClassesDTO.getIds();
        //要新增的医生排班记录
        List<OutptClassesDoctorDTO> insertList =  new ArrayList<>();
        //要修改的医生排班记录
        List<OutptClassesDoctorDTO> updateList =  new ArrayList<>();

        // 有id则是修改的，没有id则是要新增的医生挂号排班数据
        if (!ListUtils.isEmpty(outptClassesDoctorDTOList)){

            outptClassesDoctorDTOList.forEach(item->{

                if (StringUtils.isEmpty(item.getId())){
                    String id = SnowflakeUtils.getId();

                    item.setCcId(outptClassifyClassesDTO.getId());

                    item.setId(id);

                    item.setHospCode(outptClassifyClassesDTO.getHospCode());

                    insertList.add(item);

                }else {

                    item.setHospCode(outptClassifyClassesDTO.getHospCode());

                    updateList.add(item);
                }


            });

            if (!ListUtils.isEmpty(insertList)){

                outptClassifyClassesDAO.insertClassesDoctor(insertList);

            }

            if (!ListUtils.isEmpty(updateList)){

                outptClassifyClassesDAO.updateClassesDoctor(updateList);

            }
        }

        // 删除的医生排班列表
        if (!ListUtils.isEmpty(ids)){
            OutptClassifyClassesDTO delete = new OutptClassifyClassesDTO();

            BeanUtils.copyProperties(outptClassifyClassesDTO,delete);

            delete.setIsValid(Constants.SF.F);

            outptClassifyClassesDAO.deleteClassesDoctorById(delete);
        }

        // 拿到选择的星期
        List<String> weekList = outptClassifyClassesDTO.getWeekList();

        if (weekList.size()!=1){
            throw new AppException("星期数异常");
        }

        // 查询此挂号类别已进行排班的星期数
        OutptClassifyClassesDTO dto = new OutptClassifyClassesDTO();
        // 查重条件
        dto.setId(outptClassifyClassesDTO.getId());

        dto.setHospCode(outptClassifyClassesDTO.getHospCode());

        dto.setCyId(outptClassifyClassesDTO.getCyId());

        dto.setCsId(outptClassifyClassesDTO.getCsId());

        dto.setWeeks(weekList.get(0));

        dto.setIsValid(Constants.SF.S);

        int classifyClassesExist = outptClassifyClassesDAO.isClassifyClassesExist(dto);

        String weekName = "";
        switch (dto.getWeeks()){
            case "1" :
                weekName = "星期一";
                break;
            case "2" :
                weekName = "星期二";
                break;
            case "3" :
                weekName = "星期三";
                break;
            case "4" :
                weekName = "星期四";
                break;
            case "5" :
                weekName = "星期五";
                break;
            case "6" :
                weekName = "星期六";
                break;
            case "7" :
                weekName = "星期日";
                break;
        }

        if (classifyClassesExist>0){

            throw new AppException("挂号类别【"+outptClassifyClassesDTO.getClassifyName()+"】" +
                    "在【"+weekName+"】的【"+outptClassifyClassesDTO.getClassesName()+"】已存在排班记录，无法修改，请检查！");
        }

        // 根据医生id查询出此医生在此挂号类别下已进行排班的时间段
        if (!ListUtils.isEmpty(outptClassesDoctorDTOList)){
            // 根据班次id查询出班次时间
            OutptClassesDTO queryClasses = new OutptClassesDTO();
            queryClasses.setId(outptClassifyClassesDTO.getCsId());
            queryClasses.setHospCode(hospCode);
            OutptClassesDTO byId = outptClassesBO.getById(queryClasses);
            Date newStartDate = DateUtils.parse(byId.getStartDate(),"HH:mm");
            Date newEndDate = DateUtils.parse(byId.getEndDate(),"HH:mm");

            outptClassesDoctorDTOList.forEach(item->{
                Map doctorMap = new HashMap();
                doctorMap.put("hospCode",hospCode);
                doctorMap.put("doctorId",item.getDoctorId());
                doctorMap.put("cyId",cyId);
                doctorMap.put("weekList",weekList);
                doctorMap.put("ccId",ccId);// 根据排班id排除当前排班记录的医生

                // 根据医生id，星期列表，挂号类别id 查询此医生在此挂号类别的星期几的排班时间list 列表
                List<Map> maps = outptClassifyClassesDAO.queryDoctorStartToEndTime(doctorMap);

                if (!ListUtils.isEmpty(maps)){
                    List<String> isExitweek = new ArrayList();

                    for (Map map: maps){
                        Date startDate = DateUtils.parse(MapUtils.get(map, "startDate"),"HH:mm");
                        Date endDate = DateUtils.parse(MapUtils.get(map, "endDate"),"HH:mm");
                        if (DateUtils.betweenDate(startDate,endDate,newStartDate)
                                || DateUtils.betweenDate(startDate,endDate,newEndDate)
                                || DateUtils.betweenDate(newStartDate,newEndDate,startDate)
                                || DateUtils.betweenDate(newStartDate,newEndDate,endDate)){
                            isExitweek.add(MapUtils.get(map, "weeks"));
                        }
                    }

                    if (!ListUtils.isEmpty(isExitweek)){
                        // 重复星期去重
                        List<String> collect = isExitweek.stream().distinct().collect(Collectors.toList());

                        List<String> weeksToWeeksName = this.changeWeeksToWeeksName(collect);

                        throw new AppException("【"+item.getDoctorName()+"】"+"在【"+classifyName+"】的【"+
                                Joiner.on("，").join(weeksToWeeksName)+"】已存在排班，并与当前排班时间冲突，请检查");
                    }

                }
            });
        }


        return outptClassifyClassesDAO.updateClassifyClasses(outptClassifyClassesDTO)>0;
    }

    /**
     * @Menthod updateStatus
     * @Desrciption  作废和启用多条挂号排班数据
     * @param outptClassifyClassesDTO
     * @Author xingyu.xie
     * @Date   2020/8/11 9:42
     * @Return boolean
     **/
    public boolean updateStatus(OutptClassifyClassesDTO outptClassifyClassesDTO){
        if (ListUtils.isEmpty(outptClassifyClassesDTO.getIds())){
            throw new AppException("要删除的数据不能为空");
        }
        outptClassifyClassesDAO.updateStatusDoctor(outptClassifyClassesDTO);
        return outptClassifyClassesDAO.updateStatus(outptClassifyClassesDTO)>0;

    }

    /**
     * 查询分诊方式
     *
     * @param map
     **/
    @Override
    public Map<String, List<Map<String, Object>>> getFZFSCodeDetailByCode(Map<String, Object> map) {
        Map<String,List<Map<String,Object>>> result = new HashMap<>();
        List<Map<String,Object>> list = outptClassifyClassesDAO.getFZFSCodeDetailByCode(map);
        result.put(MapUtils.get(map,"code"),list);
        return result;
    }

    private List<String> changeWeeksToWeeksName(List<String> weeks){
        List<String> isExitWeeksName = new ArrayList<>();

        Collections.sort(weeks);
        for (String week:weeks){
            switch (week){
                case "1" :
                    isExitWeeksName.add("星期一");
                    break;
                case "2" :
                    isExitWeeksName.add("星期二");
                    break;
                case "3" :
                    isExitWeeksName.add("星期三");
                    break;
                case "4" :
                    isExitWeeksName.add("星期四");
                    break;
                case "5" :
                    isExitWeeksName.add("星期五");
                    break;
                case "6" :
                    isExitWeeksName.add("星期六");
                    break;
                case "7" :
                    isExitWeeksName.add("星期日");
                    break;
            }
        }
        return isExitWeeksName;
    }
}
