package cn.hsa.emr.emrclassifytemplate.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrclassify.dao.EmrClassifyDAO;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;
import cn.hsa.module.emr.emrclassifytemplate.bo.EmrClassifyTemplateBO;
import cn.hsa.module.emr.emrclassifytemplate.dao.EmrClassifyTemplateDAO;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
public class EmrClassifyTemplateBOImpl extends HsafBO implements EmrClassifyTemplateBO {

    @Resource
    private EmrClassifyTemplateDAO emrClassifyTemplateDAO;

    @Resource
    private EmrClassifyDAO emrClassifyDAO;

    @Override
    public EmrClassifyTemplateDTO getById(EmrClassifyTemplateDTO emrClassifyTemplateDTO) {
        EmrClassifyTemplateDTO byId = emrClassifyTemplateDAO.getById(emrClassifyTemplateDTO);
        byte[] templateHtml = byId.getTemplateHtml();
        String newHtml = null;
        try {
            newHtml = new String(templateHtml,"utf-8");
            byId.setNoDealHtml(newHtml);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byId;
    }

    /**
     * @Method save 派发
     * @Desrciption
     * 1.如果曾经已经新增过，需要修改模板文件，并且把病历分类无效置为有效
     * 2.如果是第一次新增模板，新增模板、分类、分类元素
     * 3.如果是再次派发，更新模板、分类，删除分类元素再新增
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/10/9 14:53
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(EmrClassifyTemplateDTO emrClassifyTemplateDTO) {
        if(StringUtils.isNotEmpty(emrClassifyTemplateDTO.getDeptId())){
            String deptId = emrClassifyTemplateDTO.getDeptId();
            List<String> codes = emrClassifyTemplateDTO.getCodes();
                //查询该科室已经派发的现在有效的分类编码
                List<String> templates = emrClassifyTemplateDAO.queryTemplates(emrClassifyTemplateDTO);
                addDis(emrClassifyTemplateDTO,templates,codes,deptId);
//                updateDis(emrClassifyTemplateDTO,codes,deptId);
                deleteDis(emrClassifyTemplateDTO,templates,codes,deptId);
        }else {
            throw new AppException("请先选择需要派发的科室");
        }
        return true;
    }

    /**
     * @Method saveTemplate
     * @Desrciption 新增/修改模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/10/9 14:52
     * @Return java.lang.Boolean
     **/
    @Override
    public EmrClassifyTemplateDTO saveTemplate(EmrClassifyTemplateDTO emrClassifyTemplateDTO) {
        String noDealHtml = emrClassifyTemplateDTO.getNoDealHtml();
        try {
            if(StringUtils.isNotEmpty(noDealHtml) ){
                byte[] bytes = noDealHtml.getBytes("UTF-8");
                emrClassifyTemplateDTO.setTemplateHtml(bytes);
            }
        } catch (UnsupportedEncodingException e) {
            throw new AppException("html格式有误");
        }
        if(StringUtils.isNotEmpty(emrClassifyTemplateDTO.getId())){
            List<EmrClassifyTemplateDTO> list = new ArrayList<>();
            list.add(emrClassifyTemplateDTO);
            if(emrClassifyTemplateDAO.updateTemplate(list)){
                return emrClassifyTemplateDTO;
            } else {
                return null;
            }
        }else{
            String id = SnowflakeUtils.getId();
            emrClassifyTemplateDTO.setId(id);
            if(StringUtils.isEmpty(emrClassifyTemplateDTO.getDeptId())){
                emrClassifyTemplateDTO.setDeptId(null);
            }
            if(emrClassifyTemplateDAO.insert(emrClassifyTemplateDTO)>0){
                return emrClassifyTemplateDTO;
            }else{
                return null;
            }
        }
    }

    /**
     * @Method queryCheckCodes
     * @Desrciption 查询已经派发并且有效的模板
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:29
     * @Return java.util.List<java.lang.String>
     **/
    @Override
    public List<String> queryCheckCodes(EmrClassifyTemplateDTO emrClassifyTemplateDTO) {
        return emrClassifyTemplateDAO.queryCheckCodes(emrClassifyTemplateDTO);
    }

    /**
     * @Method addDis
     * @Desrciption 需要新增的模板
     * @Param
     * [emrClassifyTemplateDTO, templates, codes, deptId]
     * @Author liaojunjie
     * @Date   2020/12/16 11:30
     * @Return java.lang.Boolean
     **/
    // 两种情况  ===== 》1.曾经派发过 只是置为无效了  2.第一次派发
    public Boolean addDis(EmrClassifyTemplateDTO emrClassifyTemplateDTO, List templates, List codes, String deptId){
        // 查询此科室所有派发过的
        List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOS = emrClassifyTemplateDAO.queryAllTemplate(emrClassifyTemplateDTO);
        List<String> classified = emrClassifyTemplateDTOS.stream().map(EmrClassifyTemplateDTO::getClassifyCode).collect(Collectors.toList());

        // 限制条数,【修改这里】
        int pointsDataLimit = Constants.DZBL.NUM;
        // 取差集查出需要新增的模板中分类编码集合
        // addList存第一次派发的  classfied存已经存在的
        List addList = new ArrayList();
        addList.addAll(codes);
        addList.removeAll(classified);

        classified.retainAll(codes);
        if(!ListUtils.isEmpty(addList)){
            //第一次派发的
            emrClassifyTemplateDTO.setCodes(addList);
            emrClassifyTemplateDTO.setDeptId(null);
            //查询全院病历中对应编码的信息
            List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOS1 = emrClassifyTemplateDAO.queryTemplate(emrClassifyTemplateDTO);
            if(!ListUtils.isEmpty(emrClassifyTemplateDTOS1)){
                for(EmrClassifyTemplateDTO emr : emrClassifyTemplateDTOS1){
                    emr.setId(SnowflakeUtils.getId());
                    emr.setDeptId(deptId);
                    emr.setCrteId(emrClassifyTemplateDTO.getCrteId());
                    emr.setCrteName(emrClassifyTemplateDTO.getCrteName());
                    emr.setCrteTime(DateUtils.getNow());
                }

                int size = emrClassifyTemplateDTOS1.size();
                if(size > pointsDataLimit){
                    int part = size/pointsDataLimit;
                    for (int i = 0; i < part; i++) {
                        List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOS2 = emrClassifyTemplateDTOS1.subList(0, pointsDataLimit);
                        emrClassifyTemplateDAO.insertBatch(emrClassifyTemplateDTOS2);
                        emrClassifyTemplateDTOS1.subList(0,pointsDataLimit).clear();
                    }
                    if(!ListUtils.isEmpty(emrClassifyTemplateDTOS1)){
                        emrClassifyTemplateDAO.insertBatch(emrClassifyTemplateDTOS1);
                    }
                } else {
                    emrClassifyTemplateDAO.insertBatch(emrClassifyTemplateDTOS1);
                }


                //拿出全院病历最新的分类以及分类元素关系进行插入操作
                distribution(emrClassifyTemplateDTO, deptId);
            }else{
                throw new AppException("全院病历数据错误");
            }
        }

        //曾经派发过的
        // 1.更新模板文件  2.删除原先的分类 3.新增分类 4. 删除分类元素 5 新增分类元素
        //templates 存的是已经存在并且有效的
        if(!ListUtils.isEmpty(classified)){
            emrClassifyTemplateDTO.setCodes(classified);
            emrClassifyTemplateDTO.setDeptId(null);
            //查询出全院最新的模板文件
            List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOSS = emrClassifyTemplateDAO.queryTemplate(emrClassifyTemplateDTO);
            if(!ListUtils.isEmpty(emrClassifyTemplateDTOSS)){
                Map<String, EmrClassifyTemplateDTO> collect = emrClassifyTemplateDTOSS.stream().collect(Collectors.toMap(EmrClassifyTemplateDTO::getClassifyCode, Function.identity()));
                //查询当前派发的模板中已经存在的
                emrClassifyTemplateDTO.setCodes(classified);
                emrClassifyTemplateDTO.setDeptId(deptId);
                List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOS2 = emrClassifyTemplateDAO.queryAllTemplate(emrClassifyTemplateDTO);
                if(!ListUtils.isEmpty(emrClassifyTemplateDTOS2)){
                    for(EmrClassifyTemplateDTO emr : emrClassifyTemplateDTOS2){
                        if(collect.containsKey(emr.getClassifyCode())){
                            emr.setTemplateHtml(collect.get(emr.getClassifyCode()).getTemplateHtml());
                        }
                    }
                    //批量更新模板表
                    int size = emrClassifyTemplateDTOS2.size();
                    if(size > pointsDataLimit){
                        int part = size/pointsDataLimit;
                        for (int i = 0; i < part; i++) {
                            List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOS1 = emrClassifyTemplateDTOS2.subList(0, pointsDataLimit);
                            emrClassifyTemplateDAO.updateTemplate(emrClassifyTemplateDTOS1);
                            emrClassifyTemplateDTOS2.subList(0,pointsDataLimit).clear();
                        }
                        if(!ListUtils.isEmpty(emrClassifyTemplateDTOS2)){
                            if(emrClassifyTemplateDTOS2!=null&&emrClassifyTemplateDTOS2.size()>0) {
                                emrClassifyTemplateDAO.updateTemplate(emrClassifyTemplateDTOS2);
                            }
                        }
                    } else {
                        if(emrClassifyTemplateDTOS2!=null&&emrClassifyTemplateDTOS2.size()>0) {
                            emrClassifyTemplateDAO.updateTemplate(emrClassifyTemplateDTOS2);
                        }
                    }



                    if(emrClassifyTemplateDTOS2!=null&&emrClassifyTemplateDTOS2.size()>0) {
                        emrClassifyTemplateDAO.updateTemplate(emrClassifyTemplateDTOS2);
                    }
                }
            }
            //删除分类表
            emrClassifyTemplateDTO.setDeptId(deptId);
            List<EmrClassifyDTO> emrClassifyDTOS = queryClassifyFather(emrClassifyTemplateDTO);
            List<String> classifyCodes = emrClassifyDTOS.stream().map(EmrClassifyDTO::getCode).collect(Collectors.toList());
            emrClassifyTemplateDTO.setDeptId(deptId);
            emrClassifyTemplateDTO.setCodes(classifyCodes);
            emrClassifyTemplateDAO.deleteClassify(emrClassifyTemplateDTO);
            //删除分类元素表
            emrClassifyTemplateDTO.setDeptId(deptId);
            emrClassifyTemplateDTO.setCodes(classified);
            emrClassifyTemplateDAO.deleteClassifyElement(emrClassifyTemplateDTO);
            distribution(emrClassifyTemplateDTO,deptId);
        }

        return true;
    }

    /**
     * @Method deleteDis
     * @Desrciption 需要删除的模板
     * @Param
     * [emrClassifyTemplateDTO, templates, codes, deptId]
     * @Author liaojunjie
     * @Date   2020/12/16 11:30
     * @Return java.lang.Boolean
     **/
    public Boolean deleteDis(EmrClassifyTemplateDTO emrClassifyTemplateDTO, List templates, List codes,String deptId){
        // 取差集查出需要删除的模板id集合
        List deleteList = new ArrayList();
        deleteList.addAll(templates);

        templates.retainAll(codes);

        deleteList.removeAll(templates);

        if(!ListUtils.isEmpty(deleteList)){
            // 查出所有需要删除的分类的Code
            emrClassifyTemplateDTO.setCodes(deleteList);
            List<EmrClassifyDTO> emrClassifyDTOS = queryClassifyFather(emrClassifyTemplateDTO);
            List<String> collect = emrClassifyDTOS.stream().map(EmrClassifyDTO::getCode).collect(Collectors.toList());
            //所有分类制无效
            if(!ListUtils.isEmpty(collect)){
                emrClassifyTemplateDTO.setCodes(collect);
                emrClassifyTemplateDTO.setDeptId(deptId);
                emrClassifyTemplateDAO.updateClassify(emrClassifyTemplateDTO);
            }
        }
        return true;
    }

    /**
     * @Method distribution
     * @Desrciption 派发
     * @Param
     * [emrClassifyTemplateDTO, deptId]
     * @Author liaojunjie
     * @Date   2020/12/16 11:30
     * @Return java.lang.Boolean
     **/
    public Boolean distribution(EmrClassifyTemplateDTO emrClassifyTemplateDTO,String deptId){

        //拿出全院病历最新的现在在派发的分类以及分类元素关系
        emrClassifyTemplateDTO.setDeptId(null);
        List<EmrClassifyDTO> emrClassifyDTOS = queryClassifyFather(emrClassifyTemplateDTO);
        List<EmrClassifyElementDTO> emrClassifyElementDTOS = emrClassifyTemplateDAO.queryClassifyElement(emrClassifyTemplateDTO);

        emrClassifyTemplateDTO.setEmrClassifyDTOS(emrClassifyDTOS);

        //重新插入分类以及分类元素关系
        emrClassifyTemplateDTO.setDeptId(deptId);

        // 限制条数,【修改这里】
        int pointsDataLimit = Constants.DZBL.NUM;

        if(!ListUtils.isEmpty(emrClassifyDTOS)){
            for(EmrClassifyDTO emr : emrClassifyDTOS){
                emr.setId(SnowflakeUtils.getId());
                emr.setDeptId(deptId);
                emr.setCrteId(emrClassifyTemplateDTO.getCrteId());
                emr.setCrteName(emrClassifyTemplateDTO.getCrteName());
                emr.setCrteTime(DateUtils.getNow());
            }

            emrClassifyTemplateDTO.setEmrClassifyDTOS(emrClassifyDTOS);

            int size = emrClassifyDTOS.size();
            if(size > pointsDataLimit){
                int part = size/pointsDataLimit;
                for (int i = 0; i < part; i++) {
                    List<EmrClassifyDTO> emrClassifyDTOS1 = emrClassifyDTOS.subList(0, pointsDataLimit);

                    emrClassifyTemplateDTO.setEmrClassifyDTOS(emrClassifyDTOS1);
                    emrClassifyTemplateDAO.insertClassify(emrClassifyTemplateDTO);

                    emrClassifyDTOS.subList(0,pointsDataLimit).clear();
                }
                if(!ListUtils.isEmpty(emrClassifyDTOS)){
                    emrClassifyTemplateDTO.setEmrClassifyDTOS(emrClassifyDTOS);
                    emrClassifyTemplateDAO.insertClassify(emrClassifyTemplateDTO);
                }
            } else {
                emrClassifyTemplateDTO.setEmrClassifyDTOS(emrClassifyDTOS);
                emrClassifyTemplateDAO.insertClassify(emrClassifyTemplateDTO);
            }

        }

        if(!ListUtils.isEmpty(emrClassifyElementDTOS)) {
            for (EmrClassifyElementDTO emr : emrClassifyElementDTOS) {
                emr.setId(SnowflakeUtils.getId());
                emr.setDeptId(deptId);
                emr.setCrteId(emrClassifyTemplateDTO.getCrteId());
                emr.setCrteName(emrClassifyTemplateDTO.getCrteName());
                emr.setCrteTime(DateUtils.getNow());
            }
            int size = emrClassifyElementDTOS.size();
            if(size > pointsDataLimit){
                int part = size/pointsDataLimit;
                for (int i = 0; i < part; i++) {
                    List<EmrClassifyElementDTO> emrClassifyElementDTOS1 = emrClassifyElementDTOS.subList(0, pointsDataLimit);

                    emrClassifyTemplateDTO.setEmrClassifyElementDTOS(emrClassifyElementDTOS1);
                    emrClassifyTemplateDAO.insertClassifyElement(emrClassifyTemplateDTO);

                    emrClassifyElementDTOS.subList(0,pointsDataLimit).clear();
                }
                if(!ListUtils.isEmpty(emrClassifyElementDTOS)){
                    emrClassifyTemplateDTO.setEmrClassifyElementDTOS(emrClassifyElementDTOS);
                    emrClassifyTemplateDAO.insertClassifyElement(emrClassifyTemplateDTO);
                }
            } else {
                emrClassifyTemplateDTO.setEmrClassifyElementDTOS(emrClassifyElementDTOS);
                emrClassifyTemplateDAO.insertClassifyElement(emrClassifyTemplateDTO);
            }
        }
        return true;
    }

    /**
     * @Method queryClassifyFather
     * @Desrciption 查询分类父亲
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:30
     * @Return java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    //查询分类包括父亲一起带出来
    public List<EmrClassifyDTO> queryClassifyFather(EmrClassifyTemplateDTO emrClassifyTemplateDTO){

        //拿出所有分类编码
        List<String> classifyCode = emrClassifyTemplateDTO.getCodes();

        List<TreeMenuNode> resultMenuNodeList = new ArrayList();
        List<String> collect1 = new ArrayList<>();
        List<EmrClassifyDTO> emrClassifyDTOS1 = new ArrayList<>();
        if(!ListUtils.isEmpty(classifyCode)){
            // 拿出所有的编码
                emrClassifyTemplateDTO.setCodes(classifyCode);
                //生成分类树节点
                List<TreeMenuNode> treeMenuNodes = emrClassifyTemplateDAO.queryClassifyNode(emrClassifyTemplateDTO);

                Map map = new HashMap();

                //查询完整的分类树
                EmrClassifyDTO emrClassifyDTO = new EmrClassifyDTO();
                emrClassifyDTO.setHospCode(emrClassifyTemplateDTO.getHospCode());
                List<TreeMenuNode> emrClassifyTree = emrClassifyDAO.getValidEmrClassifyTree(emrClassifyDTO);
                if(treeMenuNodes.size() > 0){
                    for (int i = 0; i < treeMenuNodes.size(); i++) {
                        //循环递归找出
                        resultMenuNodeList = getMenuTreeByName(emrClassifyTree, treeMenuNodes.get(i),resultMenuNodeList,map);
                    }
                }
                for (TreeMenuNode node : treeMenuNodes){
                    resultMenuNodeList.add(node);
                }
                //如果最后生成的树节点集合
                if(!ListUtils.isEmpty(resultMenuNodeList)){
                    //最后找出所有需要复制的分类编码
                    collect1 = resultMenuNodeList.stream().map(TreeMenuNode::getId).collect(Collectors.toList());
                }

                if(!ListUtils.isEmpty(collect1)){
                    collect1.removeAll(classifyCode);
                    collect1.addAll(classifyCode);
                    emrClassifyTemplateDTO.setDeptId(null);
                    emrClassifyTemplateDTO.setCodes(collect1);
                    emrClassifyDTOS1= emrClassifyTemplateDAO.queryClassify(emrClassifyTemplateDTO);
                }

        }
        return emrClassifyDTOS1;
    }

    /**
     * @Method queryTemplateTree
     * @Desrciption 查询模板树
     * @Param
     * [emrClassifyTemplateDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:30
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    @Override
    public List<TreeMenuNode> queryTemplateTree(EmrClassifyTemplateDTO emrClassifyTemplateDTO){
        //传过来的只有医院编码
        List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOS = emrClassifyTemplateDAO.queryTemplate(emrClassifyTemplateDTO);
        //查出所有分类编码
        List<String> collect = emrClassifyTemplateDTOS.stream().map(EmrClassifyTemplateDTO::getClassifyCode).collect(Collectors.toList());
        List<TreeMenuNode> resultMenuNodeList = new ArrayList();

        if(!ListUtils.isEmpty(collect)){
            emrClassifyTemplateDTO.setCodes(collect);
            List<TreeMenuNode> treeMenuNodes = emrClassifyTemplateDAO.queryClassifyNode(emrClassifyTemplateDTO);

            Map map = new HashMap();

            //完整的分类树
            EmrClassifyDTO emrClassifyDTO = new EmrClassifyDTO();
            emrClassifyDTO.setHospCode(emrClassifyTemplateDTO.getHospCode());
            List<TreeMenuNode> emrClassifyTree = emrClassifyDAO.getValidEmrClassifyTree(emrClassifyDTO);
            if(treeMenuNodes.size() > 0){
                for (int i = 0; i < treeMenuNodes.size(); i++) {
                    //循环递归找出
                    resultMenuNodeList = getMenuTreeByName(emrClassifyTree, treeMenuNodes.get(i),resultMenuNodeList,map);
                }
                for (TreeMenuNode node : treeMenuNodes){
                    resultMenuNodeList.add(node);
                }

            }
        }
        TreeMenuNode parent = new TreeMenuNode();
        parent.setLabel("模板列表");
        parent.setId("EMR");
        parent.setParentId("-2");
        resultMenuNodeList.add(parent);
        return resultMenuNodeList;
    }

    /**
    * @Menthod queryTemplateAll
    * @Desrciption 根据码表修改值查询所调用的全部电子病历
    *
    * @Param
    * [emrClassifyTemplateDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/10/20 15:57
    * @Return java.util.List<cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO>
    **/
  @Override
  public List<EmrClassifyTemplateDTO> queryTemplateAll(EmrClassifyTemplateDTO emrClassifyTemplateDTO) {
    return emrClassifyTemplateDAO.queryAllTemplate(emrClassifyTemplateDTO);
  }

    /**
     * @Method getMenuTreeByName
     * @Desrciption 递归向上迭代树
     * @Param
     * [allMenuAndBtn, selectThemenu, resultMenuNodeList, map]
     * @Author liaojunjie
     * @Date   2020/12/16 11:31
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
  public List<TreeMenuNode> getMenuTreeByName(List<TreeMenuNode> allMenuAndBtn,TreeMenuNode selectThemenu,List<TreeMenuNode> resultMenuNodeList,Map map) {
        for (int j = 0; j < allMenuAndBtn.size(); j++) {
            //查出上级菜单
            if(selectThemenu.getParentId().equals("EMR")){
                break;
            }
            if(selectThemenu.getParentId().equals(allMenuAndBtn.get(j).getId())&&!selectThemenu.getParentId().equals("EMR")){
                if(!map.containsKey(allMenuAndBtn.get(j).getId())){
                    map.put(allMenuAndBtn.get(j).getId(),allMenuAndBtn.get(j));
                    resultMenuNodeList.add(allMenuAndBtn.get(j));
                }
                getMenuTreeByName(allMenuAndBtn,allMenuAndBtn.get(j),resultMenuNodeList,map);
            }
        }
        return resultMenuNodeList;
    }
}
