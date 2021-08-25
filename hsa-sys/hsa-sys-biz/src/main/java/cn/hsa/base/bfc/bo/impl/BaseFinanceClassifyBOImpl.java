package cn.hsa.base.bfc.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bfc.bo.BaseFinanceClassifyBO;
import cn.hsa.module.base.bfc.dao.BaseFinanceClassifyDAO;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Package_ame: cn.hsa.base.bfc.bo.impl
 * @Class_name: BaseFinanceClassifyBOImpl
 * @Description: 财务分类业务逻辑实现层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseFinanceClassifyBOImpl extends HsafBO implements BaseFinanceClassifyBO {
    /**
     * 财务分类数据库访问接口
     */
    @Resource
    private BaseFinanceClassifyDAO baseFinanceClassifyDAO;

    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return BaseFinanceClassifyDTO
     **/
    @Override
    public BaseFinanceClassifyDTO getById(BaseFinanceClassifyDTO baseFinanceClassifyDTO) {
        return baseFinanceClassifyDAO.getById(baseFinanceClassifyDTO);
    }

    /**
    * @Menthod queryTree
    * @Desrciption  查询财务分类树状不显示末级
     * @param baseFinanceClassifyDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/20 22:14
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    @Override
    public List<TreeMenuNode> queryTree(BaseFinanceClassifyDTO baseFinanceClassifyDTO) {
        List<TreeMenuNode> treeMenuNodeList = baseFinanceClassifyDAO.queryTree(baseFinanceClassifyDTO);
        TreeMenuNode parent = new TreeMenuNode();
        parent.setLabel("财务分类");
        parent.setId("-1");
        parent.setParentId("-2");
        treeMenuNodeList.add(parent);
        return treeMenuNodeList;
    }

    /**
    * @Menthod queryDropDownEnd
    * @Desrciption  查询财务分类最末级做下拉框数据
     * @param hospCode 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/18 10:39
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO>
    **/
    @Override
    public List<BaseFinanceClassifyDTO> queryDropDownEnd(String hospCode) {
        return baseFinanceClassifyDAO.queryDropDownEnd(hospCode);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseFinanceClassifyDTO baseFinanceClassifyDTO) {

        if(!StringUtils.isEmpty(baseFinanceClassifyDTO.getCode())){

            BaseFinanceClassifyDTO quey = new BaseFinanceClassifyDTO();

            quey.setHospCode(baseFinanceClassifyDTO.getHospCode());

            quey.setIsValid(baseFinanceClassifyDTO.getIsValid());

            List<TreeMenuNode> treeMenuNodeList = queryTree(quey);
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    baseFinanceClassifyDTO.getCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            //移除节点本身
            if (list.size()==1){
                list.add("empty");
            }
            list.remove(list.indexOf(baseFinanceClassifyDTO.getCode()));
            baseFinanceClassifyDTO.setIds(list);
        }
        baseFinanceClassifyDTO.setCode("");

        // 设置分页信息
        PageHelper.startPage(baseFinanceClassifyDTO.getPageNo(), baseFinanceClassifyDTO.getPageSize());

        // 查询所有
        List<BaseFinanceClassifyDTO> baseFinanceClassifyDTOList = baseFinanceClassifyDAO.queryAll(baseFinanceClassifyDTO);

        // 返回分页结果
        return PageDTO.of(baseFinanceClassifyDTOList);
    }

    /**
     * @Method queryAll()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、baseFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    @Override
    public List<BaseFinanceClassifyDTO> queryAll(BaseFinanceClassifyDTO baseFinanceClassifyDTO) {
        return baseFinanceClassifyDAO.queryAll(baseFinanceClassifyDTO);
    }


    /**
    * @Menthod save
    * @Desrciption  新增或修改财务分类
     * @param baseFinanceClassifyDTO 财务分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/25 11:38
    * @Return boolean
    **/
    @Override
    public boolean save(BaseFinanceClassifyDTO baseFinanceClassifyDTO){

        if (baseFinanceClassifyDAO.isNameExist(baseFinanceClassifyDTO)>0){
            throw new AppException("该财务分类名已存在,请重新输入");
        }
        //判断名字不为空，根据名字生成五笔码和拼音码
        if (!StringUtils.isEmpty(baseFinanceClassifyDTO.getName())){

                baseFinanceClassifyDTO.setPym(PinYinUtils.toFirstPY(baseFinanceClassifyDTO.getName()));


                baseFinanceClassifyDTO.setWbm(WuBiUtils.getWBCode(baseFinanceClassifyDTO.getName()));

        }
        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(baseFinanceClassifyDTO.getId())){

            baseFinanceClassifyDTO.setCode(CreateCode(baseFinanceClassifyDTO.getUpCode(),baseFinanceClassifyDTO.getHospCode()));

            baseFinanceClassifyDTO.setId(SnowflakeUtils.getId());

            baseFinanceClassifyDTO.setCrteTime(DateUtils.getNow());

            return this.baseFinanceClassifyDAO.insert(baseFinanceClassifyDTO)>0;
        }else {
            BaseFinanceClassifyDTO upCode = new BaseFinanceClassifyDTO();
            BeanUtils.copyProperties(baseFinanceClassifyDTO,upCode);
            upCode.setUpCode(upCode.getCode());
            List<BaseFinanceClassifyDTO> baseFinanceClassifyDTOList = baseFinanceClassifyDAO.queryByUpCode(upCode);
            if (baseFinanceClassifyDTOList.size()>0 && Constants.SF.S.equals(upCode.getIsEnd())){
                throw new AppException("该财务分类存在子节点，无法变为末级");
            }
            return this.baseFinanceClassifyDAO.update(baseFinanceClassifyDTO)>0;
        }
    }

    /**
     * @Method updateIsValid()
     * @Description 单个或者批量更改有效标识
     *
     * @Param
     * 1、id：财务分类信息表主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return boolean
     **/
    @Override
    public boolean updateStatus(BaseFinanceClassifyDTO baseFinanceClassifyDTO) {
        // 作废其子节点
        List<String> codes = baseFinanceClassifyDTO.getCodes();
        if (Constants.SF.F.equals(baseFinanceClassifyDTO.getIsValid())){
            if(ListUtils.isEmpty(codes)){
                throw new AppException("要作废的记录的编码不能为空");
            }
            List<String> deleteCodes = new ArrayList<>();
            for (String code:codes){
                BaseFinanceClassifyDTO quey = new BaseFinanceClassifyDTO();

                quey.setHospCode(baseFinanceClassifyDTO.getHospCode());

                quey.setIsValid(Constants.SF.S);

                List<TreeMenuNode> treeMenuNodeList = queryTree(quey);
                String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList, code);
                String[] split = chidldrenIds.split(",");
                List<String> list = new ArrayList<>(Arrays.asList(split));
                deleteCodes.addAll(list);
            }
            if (ListUtils.isEmpty(deleteCodes)){
                throw new AppException("要作废的记录的编码不能为空");
            }
            baseFinanceClassifyDTO.setCodes(deleteCodes);
        }

        return  baseFinanceClassifyDAO.updateDownNodeStatus(baseFinanceClassifyDTO)>0;
    }

    /**
    * @Menthod isNameExist
    * @Desrciption  判断财务分类名是否重复
     * @param baseFinanceClassifyDTO 财务分类数据传输对象
    * @Author xingyu.xie
    * @Date   2020/11/25 16:44
    * @Return boolean
    **/
    public boolean isNameExist(BaseFinanceClassifyDTO baseFinanceClassifyDTO){
        if (baseFinanceClassifyDAO.isNameExist(baseFinanceClassifyDTO)>0){
            return false;
        }
        return true;
    }

    /**
    * @Menthod CreateCode
    * @Desrciption 生成code 如果传入空值upcode则根据ABCD的字母顺序生成，如果传入非空值，例如A 则是A01,A02的顺序生成
     * @param upCode
     * @param hospCode
    * @Author xingyu.xie
    * @Date   2020/9/15 11:04
    * @Return java.lang.String
    **/
    private String CreateCode(String upCode,String hospCode){

        BaseFinanceClassifyDTO query = new BaseFinanceClassifyDTO();
        query.setUpCode(upCode);
        query.setHospCode(hospCode);
        List<BaseFinanceClassifyDTO> baseFinanceClassifyDTOList = baseFinanceClassifyDAO.queryByUpCode(query);
        List<Integer> isExitCode = new ArrayList<>();
        if (!ListUtils.isEmpty(baseFinanceClassifyDTOList)){

            for (BaseFinanceClassifyDTO item:baseFinanceClassifyDTOList){

                if (upCode==null || "".equals(upCode) || "-1".equals(upCode)){
                    isExitCode.add((int)(item.getCode().charAt(0)));
                }else {
                    isExitCode.add(Integer.parseInt(item.getCode().replace(upCode,"")));
                }
            }
        }
        if (ListUtils.isEmpty(isExitCode)){
            if (upCode==null || "".equals(upCode) || "-1".equals(upCode)){
                return "A";
            }else {
                return upCode+String.format("%02d", 1);
            }
        }else {
            Collections.sort(isExitCode, (o1, o2) -> o2-o1);
            Integer code = isExitCode.get(0);
            if (upCode==null || "".equals(upCode) || "-1".equals(upCode)){
                if (code == 90){
                    throw new AppException("此级别编码已满，无法新增！");
                }else {
                    code +=1;
                }
               char c = (char)code.intValue();
                return String.valueOf(c);
            }else {
                if (code == 99){
                    throw new AppException("此级别编码已满，无法新增！");
                }else {
                    code +=1;
                }
                return upCode+String.format("%02d",code);
            }
        }
    }
}
