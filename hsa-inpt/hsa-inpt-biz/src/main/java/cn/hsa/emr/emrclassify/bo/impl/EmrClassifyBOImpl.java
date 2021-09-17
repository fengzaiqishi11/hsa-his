package cn.hsa.emr.emrclassify.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrclassify.bo.EmrClassifyBO;
import cn.hsa.module.emr.emrclassify.dao.EmrClassifyDAO;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.emr.emrclassify.bo.impl
 * @Class_name: EmrClassifyBOImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/27 10:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrClassifyBOImpl extends HsafBO implements EmrClassifyBO {

    @Resource
    private EmrClassifyDAO emrClassifyDAO;

    /**
     * @Method getByIdOrCode
     * @Desrciption 通过id或者code进行查询
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:13
     * @Return cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO
     **/
    @Override
    public EmrClassifyDTO getByIdOrCode(EmrClassifyDTO emrClassifyDTO) {
        List<EmrElementDTO> list = emrClassifyDAO.getEmrClassifyRecordCode(emrClassifyDTO);
        EmrClassifyDTO dto = emrClassifyDAO.getByIdOrCode(emrClassifyDTO);
        dto.setRecordList(list);
        return dto;
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:13
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(EmrClassifyDTO emrClassifyDTO) {
        if(!StringUtils.isEmpty(emrClassifyDTO.getCode())){
            List<TreeMenuNode> treeMenuNodeList = getEmrClassifyTree(emrClassifyDTO);
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    emrClassifyDTO.getCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            emrClassifyDTO.setCodes(list);
        }
        emrClassifyDTO.setCode("");
        // 设置分页信息
        PageHelper.startPage(emrClassifyDTO.getPageNo(), emrClassifyDTO.getPageSize());
        return PageDTO.of(emrClassifyDAO.queryPageOrAll(emrClassifyDTO));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:13
     * @Return java.util.List<cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO>
     **/
    @Override
    public List<EmrClassifyDTO> queryAll(EmrClassifyDTO emrClassifyDTO) {
        return emrClassifyDAO.queryPageOrAll(emrClassifyDTO);
    }

    /**
     * @Method save
     * @Desrciption 修改、新增电子病历分类
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:13
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(EmrClassifyDTO emrClassifyDTO) {
        if(StringUtils.isNotEmpty(emrClassifyDTO.getName())){
            emrClassifyDTO.setPym(PinYinUtils.toFirstPY(emrClassifyDTO.getName()));
            emrClassifyDTO.setWbm(WuBiUtils.getWBCode(emrClassifyDTO.getName()));
        }
        //如果id为空则为新增，不为空则为修改
        if(StringUtils.isEmpty(emrClassifyDTO.getId())){
            return insert(emrClassifyDTO);
        } else {
            return update(emrClassifyDTO);
        }
    }

    /**
     * @Method insert
     * @Desrciption 新增分类
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:13
     * @Return java.lang.Boolean
     **/
    public Boolean insert(EmrClassifyDTO emrClassifyDTO) {
        // 1.如果无up_code 直接通过 2.如果有 up_code 查询 此 up_code 下 的分类是否有重名的
        if(!emrClassifyDTO.getUpCode().equals("-1")){
            EmrClassifyDTO emr = new EmrClassifyDTO();
            emr.setName(emrClassifyDTO.getName());
            emr.setUpCode(emrClassifyDTO.getUpCode());
            emr.setHospCode(emrClassifyDTO.getHospCode());
            List<EmrClassifyDTO> emrClassifyDTOS = emrClassifyDAO.queryPageOrAll(emr);
            if (!ListUtils.isEmpty(emrClassifyDTOS)) {
                throw new AppException("分类名称重复");
            }
        }
        // 生成编码
        String code = createCode(emrClassifyDTO.getUpCode(), emrClassifyDTO.getHospCode());
        emrClassifyDTO.setCode(code);
        emrClassifyDTO.setId(SnowflakeUtils.getId());

        return emrClassifyDAO.insert(emrClassifyDTO) > 0;
    }

    /**
     * @Method update
     * @Desrciption 修改分类
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:14
     * @Return java.lang.Boolean
     **/
    public Boolean update(EmrClassifyDTO emrClassifyDTO) {
        //判断修改的元素是不是父级节点
        //如果此分类不是末级，并且现在传过来是需要置无效
        if("0".equals(emrClassifyDTO.getIsEnd()) && "0".equals(emrClassifyDTO.getIsValid())){
            //查询数据库的有效状态
            EmrClassifyDTO byIdOrCode = emrClassifyDAO.getByIdOrCode(emrClassifyDTO);
            //如果状态不一样,则修改该父节点下所有子节点的有效状态
            if(!byIdOrCode.getIsValid().equals(emrClassifyDTO.getIsValid())){

                //查出该父节点下所有子节点的值
                List<TreeMenuNode> emrClassifyTree = getEmrClassifyTree(emrClassifyDTO);
                String chidldrenCodes = TreeUtils.getChidldrenIds(emrClassifyTree, emrClassifyDTO.getCode());
                if(!StringUtils.isEmpty(chidldrenCodes)){
                    //给所有子节点修改状态
                    String[] split = chidldrenCodes.split(",");
                    List<String> list = new ArrayList<>(Arrays.asList(split));
                    EmrClassifyDTO emr = new EmrClassifyDTO();
                    emr.setHospCode(emrClassifyDTO.getHospCode());
                    emr.setCodes(list);
                    emr.setIsValid(emrClassifyDTO.getIsValid());
                    emrClassifyDAO.updateList(emr);
                }
            }
        }
        return emrClassifyDAO.updateEmrClassifyS(emrClassifyDTO) > 0;
    }

    /**
     * @Method getEmrClassifyTree
     * @Desrciption 获取电子病历分类树
     * @Param
     * [emrClassifyDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:14
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    @Override
    public List<TreeMenuNode> getEmrClassifyTree(EmrClassifyDTO emrClassifyDTO) {
        List<TreeMenuNode> emrClassifyTree = new ArrayList<>();
        if(StringUtils.isNotEmpty(emrClassifyDTO.getIsValid())){
             emrClassifyTree = emrClassifyDAO.getValidEmrClassifyTree(emrClassifyDTO);
        }else{
             emrClassifyTree = emrClassifyDAO.getEmrClassifyTree(emrClassifyDTO);
        }
        TreeMenuNode parent = new TreeMenuNode();
        parent.setLabel("分类列表");
        parent.setId("EMR");
        parent.setParentId("-2");
        parent.setTemplateHtml("");
        emrClassifyTree.add(parent);
        return emrClassifyTree;
    }

    @Override
    public Integer getMaxCode(EmrClassifyDTO emrClassifyDTO) {
        EmrClassifyDTO e = this.emrClassifyDAO.getMaxCode(emrClassifyDTO);
        if(e ==null){
            return 0;
        } else {
            return e.getSeq();
        }
    }

    /**
     * @Method createCode
     * @Desrciption 编码生成
     * @Param
     * [upCode, hospCode]
     * @Author liaojunjie
     * @Date   2020/12/16 11:14
     * @Return java.lang.String
     **/
    private String createCode(String upCode,String hospCode){
        EmrClassifyDTO query = new EmrClassifyDTO();
        query.setUpCode(upCode);
        query.setHospCode(hospCode);
        List<EmrClassifyDTO> emrClassifyDTOS = emrClassifyDAO.queryByUpCode(query);
        List<Integer> isExitCode = new ArrayList<>();
        if (!ListUtils.isEmpty(emrClassifyDTOS)){

            for (EmrClassifyDTO item:emrClassifyDTOS){
                isExitCode.add(Integer.parseInt(item.getCode().replace(upCode,"")));
            }
        }
        if (ListUtils.isEmpty(isExitCode)){
            if (upCode==null || "".equals(upCode) || "-1".equals(upCode)){
                return "EMR01";
            }else {
                return upCode+String.format("%02d", 1);
            }
        }else {
            Collections.sort(isExitCode, (o1, o2) -> o2-o1);
            Integer code = isExitCode.get(0);
                if (code == 99){
                    throw new AppException("此级别编码已满，无法新增！");
                }else {
                    code +=1;
                }
                return upCode+String.format("%02d",code);
            }
        }

    @Override
    public List<EmrClassifyDTO> getEmrClassifyCode(Map map) {
        return emrClassifyDAO.getEmrClassifyCode(map);
    }
}
