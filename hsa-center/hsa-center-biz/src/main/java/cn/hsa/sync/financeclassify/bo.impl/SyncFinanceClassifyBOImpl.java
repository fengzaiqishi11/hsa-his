package cn.hsa.sync.financeclassify.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sync.financeclassify.bo.SyncFinanceClassifyBO;
import cn.hsa.module.sync.financeclassify.dao.SyncFinanceClassifyDAO;
import cn.hsa.module.sync.financeclassify.dto.SyncFinanceClassifyDTO;
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
public class SyncFinanceClassifyBOImpl extends HsafBO implements SyncFinanceClassifyBO {
    /**
     * 财务分类数据库访问接口
     */
    @Resource
    private SyncFinanceClassifyDAO syncFinanceClassifyDAO;



    /**
     * @Method getById()
     * @Description 根据主键ID查询财务分类信息
     *
     * @Param
     * 1、id：财务分类信息表主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return SyncFinanceClassifyDTO
     **/
    @Override
    public SyncFinanceClassifyDTO getById(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        return syncFinanceClassifyDAO.getById(syncFinanceClassifyDTO);
    }

    /**
    * @Menthod queryTree
    * @Desrciption  查询财务分类树状不显示末级
    * @Author xingyu.xie
    * @Date   2020/7/20 22:14
    * @Return java.util.List<cn.hsa.base.TreeMenuNode>
    **/
    @Override
    public List<TreeMenuNode> queryTree(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        return syncFinanceClassifyDAO.queryTree(syncFinanceClassifyDTO);
    }


    /**
    * @Menthod queryDropDownEnd
    * @Desrciption  查询财务分类最末级做下拉框数据
    * @Author xingyu.xie
    * @Date   2020/7/18 10:39
    * @Return java.util.List<cn.hsa.module.base.bfc.dto.SyncFinanceClassifyDTO>
    **/
    @Override
    public List<SyncFinanceClassifyDTO> queryDropDownEnd() {
        return syncFinanceClassifyDAO.queryDropDownEnd();
    }

    /**
     * @Method queryPage()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、centerFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPage(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {


        if(!StringUtils.isEmpty(syncFinanceClassifyDTO.getCode())){

            SyncFinanceClassifyDTO quey = new SyncFinanceClassifyDTO();

            quey.setIsValid(syncFinanceClassifyDTO.getIsValid());

            List<TreeMenuNode> treeMenuNodeList = queryTree(quey);

            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    syncFinanceClassifyDTO.getCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            if (list.size()==1){
                list.add("empty");
            }
            list.remove(list.indexOf(syncFinanceClassifyDTO.getCode()));

            syncFinanceClassifyDTO.setIds(list);
        }
        syncFinanceClassifyDTO.setCode("");

        // 设置分页信息
        PageHelper.startPage(syncFinanceClassifyDTO.getPageNo(), syncFinanceClassifyDTO.getPageSize());

        // 查询所有
        List<SyncFinanceClassifyDTO> syncFinanceClassifyDTOList = syncFinanceClassifyDAO.queryAll(syncFinanceClassifyDTO);

        // 返回分页结果
        return PageDTO.of(syncFinanceClassifyDTOList);
    }

    /**
     * @Method queryAll()
     * @Description 分页查询财务分类信息
     *
     * @Param
     * 1、centerFinanceClassifyDTO：财务分类数据参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    @Override
    public List<SyncFinanceClassifyDTO> queryAll(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {

        return syncFinanceClassifyDAO.queryAll(syncFinanceClassifyDTO);
    }


    /**
    * @Menthod save
    * @Desrciption  新增或修改财务分类
     * @param syncFinanceClassifyDTO 财务分类数据参数对象
    * @Author xingyu.xie
    * @Date   2020/7/25 11:38
    * @Return boolean
    **/
    @Override
    public boolean save(SyncFinanceClassifyDTO syncFinanceClassifyDTO){

        //判断名字不为空，根据名字生成五笔码和拼音码
        if (!StringUtils.isEmpty(syncFinanceClassifyDTO.getName())){

                syncFinanceClassifyDTO.setPym(PinYinUtils.toFirstPY(syncFinanceClassifyDTO.getName()));

                syncFinanceClassifyDTO.setWbm(WuBiUtils.getWBCode(syncFinanceClassifyDTO.getName()));
        }
        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(syncFinanceClassifyDTO.getId())){

            syncFinanceClassifyDTO.setCode(CreateCode(syncFinanceClassifyDTO.getUpCode()));

            syncFinanceClassifyDTO.setId(SnowflakeUtils.getId());

            syncFinanceClassifyDTO.setCrteTime(DateUtils.getNow());

            return this.syncFinanceClassifyDAO.insert(syncFinanceClassifyDTO)>0;
        }else {

            SyncFinanceClassifyDTO upCode = new SyncFinanceClassifyDTO();
            BeanUtils.copyProperties(syncFinanceClassifyDTO,upCode);
            upCode.setUpCode(upCode.getCode());
            List<SyncFinanceClassifyDTO> baseFinanceClassifyDTOList = syncFinanceClassifyDAO.queryByUpCode(upCode);
            if (baseFinanceClassifyDTOList.size()>0 && Constants.SF.S.equals(upCode.getIsEnd())){
                throw new AppException("该财务分类存在子节点，无法变为末级");
            }
            return this.syncFinanceClassifyDAO.update(syncFinanceClassifyDTO)>0;
        }
    }

    /**
     * @Method updateStatus()
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
    public boolean updateStatus(SyncFinanceClassifyDTO syncFinanceClassifyDTO) {
        // 作废其子节点
        List<String> codes = syncFinanceClassifyDTO.getCodes();
        if (Constants.SF.F.equals(syncFinanceClassifyDTO.getIsValid())){
            if(ListUtils.isEmpty(codes)){
                throw new AppException("要作废的记录的编码不能为空");
            }
            List<String> deleteCodes = new ArrayList<>();
            for (String code:codes){
                SyncFinanceClassifyDTO quey = new SyncFinanceClassifyDTO();

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
            syncFinanceClassifyDTO.setCodes(deleteCodes);
        }

        return  syncFinanceClassifyDAO.updateDownNodeStatus(syncFinanceClassifyDTO)>0;
    }



    /**
     * @Menthod CreateCode
     * @Desrciption 生成code 如果传入空值upcode则根据ABCD的字母顺序生成，如果传入非空值，例如A 则是A01,A02的顺序生成
     * @param upCode
     * @Author xingyu.xie
     * @Date   2020/9/15 11:04
     * @Return java.lang.String
     **/
    private String CreateCode(String upCode){

        SyncFinanceClassifyDTO query = new SyncFinanceClassifyDTO();
        query.setUpCode(upCode);
        List<SyncFinanceClassifyDTO> syncFinanceClassifyDTOList = syncFinanceClassifyDAO.queryByUpCode(query);
        List<Integer> isExitCode = new ArrayList<>();
        if (!ListUtils.isEmpty(syncFinanceClassifyDTOList)){

            for (SyncFinanceClassifyDTO item:syncFinanceClassifyDTOList){

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
