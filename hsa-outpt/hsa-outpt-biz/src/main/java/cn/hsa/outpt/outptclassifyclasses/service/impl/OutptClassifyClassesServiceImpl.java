package cn.hsa.outpt.outptclassifyclasses.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.outptclassifyclasses.bo.OutptClassifyClassesBO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.outptclassifyclasses.service.OutptClassifyClassesService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptclassifyclasses.service.impl
 * @Class_name: OutptClassifyClassesServiceImpl
 * @Describe: 挂号类别排班service层(提供给dubbo调用)
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/8/11 10:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/outpt/outptClassifyClasses")
@Service("outptClassifyClassesService_provider")
public class OutptClassifyClassesServiceImpl extends HsafService implements OutptClassifyClassesService {


    /**
     * 注入挂号类别排班bo层
     */
    @Resource
    OutptClassifyClassesBO outptClassifyClassesBO;

    /**
     * @Menthod getById
     * @Desrciption  通过ID查询单条数据
     * @param map 主键id
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO
     **/
    public WrapperResponse<OutptClassifyClassesDTO> getClassifyClassesById(Map map){
        return WrapperResponse.success(outptClassifyClassesBO.getClassifyClassesById(MapUtils.get(map,"outptClassifyClassesDTO")));
    }

    /**
     * @Menthod queryDoctorByOuptClassify
     * @Desrciption  通过挂号类别id拿到拥有此挂号类别科室操作权限的医生职工
     * @param map
     * @Author xingyu.xie
     * @Date   2020/10/26 16:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @PostMapping("/web/outpt/outptClassifyClasses/mergeSelectDorctorAndAllDoctor")
    public WrapperResponse<Map> queryDoctorByOuptClassify(Map map){
        return WrapperResponse.success(outptClassifyClassesBO
                .queryDoctorByOuptClassify(MapUtils.get(map,"workTypeCode"),
                        MapUtils.get(map,"cyId"),
                        MapUtils.get(map,"hospCode")));
    }

    /**
     * @Menthod mergeSelectDorctorAndAllDoctor
     * @Desrciption 将某一挂号号别排班记录下的已选医生和该科室下的所有记录合并
     * @param map
     * @Author xingyu.xie
     * @Date   2020/8/19 19:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>>
     **/
    @PostMapping("/web/outpt/outptClassifyClasses/mergeSelectDorctorAndAllDoctor")
    public WrapperResponse<Map> mergeSelectDorctorAndAllDoctor(Map map){
        return WrapperResponse.success(outptClassifyClassesBO
                .mergeSelectDorctorAndAllDoctor(MapUtils.get(map,"workTypeCode"),
                        MapUtils.get(map,"csId"),
                        MapUtils.get(map,"id"),
                        MapUtils.get(map,"cyId"),
                        MapUtils.get(map,"hospCode")));
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询所有的挂号类别排班记录
     * @Author xingyu.xie
     * @Date   2020/8/11 9:40
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    public WrapperResponse<List<OutptClassifyClassesDTO>> queryClassifyClassesAll(Map map){
        return WrapperResponse.success(outptClassifyClassesBO.queryClassifyClassesAll(MapUtils.get(map,"outptClassifyClassesDTO")));
    }


    /**
     * @Menthod queryPage
     * @Desrciption  通过实体作为筛选分页条件查询挂号类别排班
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:36
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO>
     **/
    public WrapperResponse<PageDTO> queryClassifyClassesPage(Map map){
        return WrapperResponse.success(outptClassifyClassesBO.queryClassifyClassesPage(MapUtils.get(map,"outptClassifyClassesDTO")));
    }


    /**
     * @Menthod queryClassesDoctorByClassifyClassesId
     * @Desrciption 通过挂号类别排班表的id去查询医生排班记录
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     **/
    public WrapperResponse<List<OutptClassesDoctorDTO>> queryClassesDoctorByClassifyClassesId(Map map){
        return WrapperResponse.success(outptClassifyClassesBO.queryClassesDoctorByClassifyClassesId(MapUtils.get(map,"outptClassifyClassesDTO")));
    }

    /**
     * @Menthod queryClassesDoctorAllGroupByCyId
     * @Desrciption 查询所有医生排班记录并且通过挂号类别名称分组
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:35
     * @Return java.util.List<cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassesDoctorDTO>
     *
     * @return*/
    public WrapperResponse<Map<String, Map<String, Map<String, List<OutptClassesDoctorDTO>>>>> queryClassesDoctorAllGroupByCyId(Map map){
        return WrapperResponse.success(outptClassifyClassesBO.queryClassesDoctorAllGroupByCyId(MapUtils.get(map,"outptClassifyClassesDTO")));
    }

    /**
     * @Menthod insertClassifyClasses
     * @Desrciption  新增多条挂号类别排班记录和多条医生排班记录
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    public WrapperResponse<Boolean> insertClassifyClassesAndClassesDoctor(Map map){
        return WrapperResponse.success(outptClassifyClassesBO.insertClassifyClassesAndClassesDoctor(MapUtils.get(map,"outptClassifyClassesDTO")));
    }


    /**
     * @Menthod updateClassifyClasses
     * @Desrciption  修改单条挂号类别排班记录和修改多条医生排班记录
     * @param map 挂号类别排班数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/11 9:41
     * @Return boolean
     **/
    public WrapperResponse<Boolean> updateClassifyClassesAndClassesDoctor(Map map){
        return WrapperResponse.success(outptClassifyClassesBO.updateClassifyClassesAndClassesDoctor(MapUtils.get(map,"outptClassifyClassesDTO")));
    }

    /**
     * @Menthod updateStatus
     * @Desrciption  作废和启用多条挂号排班数据
     * @param map
     * @Author xingyu.xie
     * @Date   2020/8/11 9:42
     * @Return boolean
     **/
    public WrapperResponse<Boolean> updateStatus(Map map){
        return WrapperResponse.success(outptClassifyClassesBO.updateStatus(MapUtils.get(map,"outptClassifyClassesDTO")));
    }

    /**
     * 查询分诊方式代码值
     *
     * @param map
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/21 10:56
     */
    @Override
    public WrapperResponse<Map<String, List<Map<String, Object>>>> getFZFSCodeDetailByCode(Map map) {
        map.put("hospCode",map.get("hospCode"));
        return WrapperResponse.success(outptClassifyClassesBO.getFZFSCodeDetailByCode(map));
    }
}
