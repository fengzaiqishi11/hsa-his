package cn.hsa.emr.emrclassifyelement.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrclassifyelement.bo.EmrClassifyElementBO;
import cn.hsa.module.emr.emrclassifyelement.dao.EmrClassifyElementDAO;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;
import cn.hsa.module.emr.emrclassifyelement.entity.EmrClassifyElementDO;
import cn.hsa.module.emr.emrelement.dao.EmrElementDAO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.emr.emrclassifyelement.bo.impl
 * @Class_name: EmrClassifyElementBOImpl
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/9/27 14:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class EmrClassifyElementBOImpl extends HsafBO implements EmrClassifyElementBO {

    @Resource
    private EmrClassifyElementDAO emrClassifyElementDAO;

    @Resource
    private EmrElementDAO emrElementDAO;


    /**
    * @Menthod queryAll
    * @Desrciption  根据条件筛选电子病历文档元素管理表中的数据
     * @param emrClassifyElementDTO
    * @Author xingyu.xie
    * @Date   2020/9/28 10:19
    * @Return java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO>
    **/
    @Override
    public List<EmrClassifyElementDTO> queryAll(EmrClassifyElementDTO emrClassifyElementDTO){
       return emrClassifyElementDAO.queryAll(emrClassifyElementDTO);
    }


    /**
    * @Menthod save
    * @Desrciption  修改文档分类已勾选的元素节点
     * @param emrClassifyElementDTO
    * @Author xingyu.xie
    * @Date   2020/9/28 10:18 
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean save(EmrClassifyElementDTO emrClassifyElementDTO) {
        List<String> codes = emrClassifyElementDTO.getCodes();

        List<EmrClassifyElementDTO> insertList = new ArrayList<>();
        // 先删除掉此文档分类下的所有元素节点
        emrClassifyElementDAO.deleteByClassinfoCode(emrClassifyElementDTO);

        if (!ListUtils.isEmpty(codes)){

            for (String code: codes){
                emrClassifyElementDTO.setElementCode(code);
                EmrClassifyElementDTO insert = new EmrClassifyElementDTO();
                BeanUtils.copyProperties(emrClassifyElementDTO,insert);
                insert.setId(SnowflakeUtils.getId());
                insert.setCrteTime(DateUtils.getNow());
                insertList.add(insert);
            }
        }
        // 再将所勾选的所有节点重新插入
        if (!ListUtils.isEmpty(insertList)){
            emrClassifyElementDAO.insertList(insertList);
        }
        return null;
    }

    /**
    * @Menthod queryTreeByEmrClassify
    * @Desrciption  根据文档分类已选择的元素分类节点生成树
     * @param emrClassifyElementDTO
    * @Author xingyu.xie
    * @Date   2020/9/28 10:18 
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    @Override
    public List<TreeMenuNode> queryTreeByEmrClassify(EmrClassifyElementDTO emrClassifyElementDTO) {

        String hospCode = emrClassifyElementDTO.getHospCode();
        List<EmrClassifyElementDTO> emrClassifyElementDTOList = emrClassifyElementDAO.queryAll(emrClassifyElementDTO);
        List<String> collect = new ArrayList<>();
        if (!ListUtils.isEmpty(emrClassifyElementDTOList)){
            collect = emrClassifyElementDTOList.stream()
                    .map(EmrClassifyElementDO::getElementCode).collect(Collectors.toList());
        }
        if(collect.size()>0){
            EmrElementDTO emrElementDTO = new EmrElementDTO();
            emrElementDTO.setHospCode(hospCode);
            emrElementDTO.setCodes(collect);
            if(StringUtils.isNotEmpty(emrClassifyElementDTO.getDeptId())){
                emrElementDTO.setDeptId(emrClassifyElementDTO.getDeptId());
            }
            return emrElementDAO.getEmrElementTreeByCodes(emrElementDTO);
        }else {
            return new ArrayList<TreeMenuNode>();
        }

    }

    /**
    * @Menthod queryTreeIsAble
    * @Desrciption  根据文档分类code查询元素分类树和已勾选的节点
     * @param emrClassifyElementDTO
    * @Author xingyu.xie
    * @Date   2020/9/28 10:18 
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    @Override
    public List<TreeMenuNode> queryTreeIsAble(EmrClassifyElementDTO emrClassifyElementDTO){
        List<TreeMenuNode> treeMenuNodes = emrClassifyElementDAO.queryTreeIsAble(emrClassifyElementDTO);
        List<EmrClassifyElementDTO> emrClassifyElementDTOS = emrClassifyElementDAO.queryCheckElement(emrClassifyElementDTO);
        Map<String, EmrClassifyElementDTO> collect = emrClassifyElementDTOS.stream().collect(Collectors.toMap(EmrClassifyElementDTO::getElementCode, Function.identity()));
        for(TreeMenuNode treeMenuNode: treeMenuNodes) {
            if (collect.containsKey(treeMenuNode.getElementCode())) {
                treeMenuNode.setIsAble(true);
            } else {
                treeMenuNode.setIsAble(false);
            }
        }
        return treeMenuNodes;
    }

    /**
    * @Menthod queryEmrClassifyCodesByElementCodes
    * @Desrciption  根据元素编码查询出所有的文档分类编码
     * @param map 医院编码，元素编码list
    * @Author xingyu.xie
    * @Date   2020/10/9 11:29 
    * @Return java.util.List<java.lang.String>
    **/
    @Override
    public List<String> queryEmrClassifyCodesByElementCodes(Map map) {
        Object hospCode = MapUtils.get(map, "hospCode");
        Object list = MapUtils.get(map, "list");
        if (StringUtils.isEmpty((String) hospCode)){
            throw new AppException("医院编码不能为空");
        }
        if (ListUtils.isEmpty((List) list)){
            throw new AppException("元素编码列表不能为空");
        }
        return emrClassifyElementDAO.queryEmrClassifyCodesByElementCodes(map);
    }
}
