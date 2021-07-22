package cn.hsa.inpt.compositequery.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.inpt.compositequery.bo.CompositeQueryBO;
import cn.hsa.module.inpt.compositequery.dao.CompositeQueryDAO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CompositeQueryBOImpl extends HsafBO implements CompositeQueryBO {

    @Resource
    private CompositeQueryDAO compositeQueryDAO;

    @Resource
    private BaseDeptService baseDeptService_consumer;

    /**
     * @Method queryAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryAll(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> inptVisitDTOList = compositeQueryDAO.queryAll(inptVisitDTO);

        //补充病区名和科室名  by  marong
        /***********************************************/
        List<String> deptIds = new ArrayList<String>();
        inptVisitDTOList.forEach(visit ->{
            deptIds.add(visit.getInWardId());
            deptIds.add(visit.getInDeptId());
        });
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setHospCode(inptVisitDTO.getHospCode());
        baseDeptDTO.setIds(deptIds);
        Map map = new HashMap();
        map.put("hospCode", inptVisitDTO.getHospCode());
        map.put("baseDeptDTO",baseDeptDTO);
        List<BaseDeptDTO> deptList = baseDeptService_consumer.getDeptByIds(map);
        ConcurrentHashMap<String, BaseDeptDTO> deptMapListById = new ConcurrentHashMap<>();
        deptList.forEach(dept ->{
            if(!StringUtils.isEmpty(dept.getId())){
                deptMapListById.put(dept.getId(),dept);
                deptMapListById.put(dept.getCode(),dept);
            }
        });
        for(int i=0; i<inptVisitDTOList.size(); i++){
            if(!StringUtils.isEmpty(inptVisitDTOList.get(i).getInWardId())){
                inptVisitDTOList.get(i).setWardName(deptMapListById.get(inptVisitDTOList.get(i).getInWardId()).getName());
            }
            if(StringUtils.isEmpty(inptVisitDTOList.get(i).getInDeptName())){
                inptVisitDTOList.get(i).setInDeptName(deptMapListById.get(inptVisitDTOList.get(i).getInDeptId()).getName());
            }
        }
        /***********************************************/
        return PageDTO.of(inptVisitDTOList);
    }

    /**
     * @Method queryInptVisit
     * @Desrciption 查询患者基本信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return InptVisitDTO
     **/
    @Override
    public InptVisitDTO queryInptVisit(InptVisitDTO inptVisitDTO) {
        if (StringUtils.isEmpty(inptVisitDTO.getId())){
            throw new RuntimeException("未选择患者");
        }
        return compositeQueryDAO.queryInptVisit(inptVisitDTO);
    }

    /**
     * @Method queryAdvance
     * @Desrciption 查询患者预交金信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> queryAdvance(InptVisitDTO inptVisitDTO) {
        if (StringUtils.isEmpty(inptVisitDTO.getId())){
            throw new RuntimeException("未选择患者");
        }
        return compositeQueryDAO.queryAdvance(inptVisitDTO);
    }

    /**
     * @Method queryDisease
     * @Desrciption 查询患者诊断信息
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/14 16:54
     * @Return List<Map<String, Object>>
     **/
    @Override
    public List<Map<String, Object>> queryDisease(InptVisitDTO inptVisitDTO) {
        if (StringUtils.isEmpty(inptVisitDTO.getId())){
            throw new RuntimeException("未选择患者");
        }
        return compositeQueryDAO.queryDisease(inptVisitDTO);
    }

    /**
     * @Method queryVisitsByCondition
     * @Param [inptVisitDTO]
     * @description   条件查询住院病人
     * @author marong
     * @date 2020/9/22 18:58
     * @return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     * @throws
     */
    @Override
    public List<InptVisitDTO> queryVisitsByCondition(InptVisitDTO inptVisitDTO) {
        return compositeQueryDAO.queryVisitsByCondition(inptVisitDTO);
    }
}
