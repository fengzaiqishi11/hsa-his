package cn.hsa.base.dept.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.dept.bo.BaseDeptBO;
import cn.hsa.module.base.dept.dao.BaseDeptDAO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.deptDrug.dao.BaseDeptDrugStoreDAO;
import cn.hsa.module.base.deptDrug.dto.BaseDeptDrugStoreDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.base.dept.bo
 * @class_name: BaseDeptBOImpl
 * @Description: 科室信息维护业务逻辑实现层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/16 14:56
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class BaseDeptBOImpl extends HsafBO implements BaseDeptBO {
    /**
     * 科室信息维护数据访问层
     */
    @Resource
    private BaseDeptDAO baseDeptDAO;

    /**
     * 科室关联领药药房的数据访问层
     */
    @Resource
    private BaseDeptDrugStoreDAO baseDeptDrugstoreDAO;
    /**
     * 单据号的生成规则服务层接口
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    /**
     * 获取码表的服务层接口
     */
    @Resource
    private SysCodeService sysCodeService_consumer;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @Method: queryPage()
     * @Description: 分页带参数查询科室信息
     * @Param: baseDeptDTO
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:05
     * @Return: 科室维护信息数据传输对象list集合
     */
    @Override
    public PageDTO queryPage(BaseDeptDTO baseDeptDTO) {

        if(!StringUtils.isEmpty(baseDeptDTO.getCode())){
            List<TreeMenuNode> treeMenuNodeList = getDeptTree(baseDeptDTO.getHospCode());
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    baseDeptDTO.getCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseDeptDTO.setDeptIdList(list);
        }
        baseDeptDTO.setCode("");
        // 设置分页信息
        PageHelper.startPage(baseDeptDTO.getPageNo(), baseDeptDTO.getPageSize());
        List<BaseDeptDTO> deptDTOList = baseDeptDAO.queryPage(baseDeptDTO);
        return PageDTO.of(deptDTOList);
    }

    /**
     * @Method: insert()
     * @Description: 新增科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:08
     * @Return: boolean
     */
    @Override
    public boolean insert(BaseDeptDTO baseDeptDTO) {
        Map map = new HashMap();
        // 科室编码生成规则
        map.put("hospCode", baseDeptDTO.getHospCode());
        map.put("typeCode", "18");
        WrapperResponse<String> deptCode = baseOrderRuleService.getOrderNo(map);
        baseDeptDTO.setIsValid(Constants.SF.S);
        baseDeptDTO.setCode(deptCode.getData());
        return save(baseDeptDTO);
    }

    /**
     * @Method: update()
     * @Description: 修改科室信息维护
     * @Param: baseDeptDTO 科室维护信息数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:08
     * @Return: boolean
     */
    @Override
    public boolean update(BaseDeptDTO baseDeptDTO) {
        return save(baseDeptDTO);
    }

    /**
     * @Method: updateIsValid()
     * @Description: 根据主键id修改科室信息有效标识符
     *    1.需要判断信息科和已经用到的科室不能被修改
     * @Param: id: 科室信息表主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/16 15:32
     * @Return: booleans
     */
    @Override
    public boolean updateIsValid(BaseDeptDTO baseDeptDTO) {
        if(Constants.SF.F.equals(baseDeptDTO.getIsValid())) {
            List<String> childList = new ArrayList<>();
            List<String> stringList;
            List<String> sysUserList;
            List<BaseDeptDTO> deptDTOList = baseDeptDAO.queryDeptByIdList(baseDeptDTO);
            List<TreeMenuNode> deptTree = this.getDeptTree(baseDeptDTO.getHospCode());
            for(int i=0;i<deptDTOList.size();i++){
                List<String> childTreeNodeList = findChildTreeNode(deptTree,deptDTOList.get(i).getCode() , childList);
                if(!ListUtils.isEmpty(childTreeNodeList)){
                    throw new AppException("该【"+deptDTOList.get(i).getName()+"】科室存在子级科室,不能作废");
                }
                if(Constants.CENTERSYNCKSCODE.DEPT_CODE.equals(deptDTOList.get(i).getCode())){
                    throw new AppException("中心平台下发的【"+deptDTOList.get(i).getName()+"】不允许被作废");
                }
                stringList = baseDeptDAO.selectSysUserSystemCode(deptDTOList.get(i));
                if(!ListUtils.isEmpty(stringList)){
                    throw new AppException("该【"+deptDTOList.get(i).getName()+"】科室已经被【"+stringList+"】用户绑定为操作科室,不能进行作废操作");
                }
                sysUserList = baseDeptDAO.selectSysUser(deptDTOList.get(i));
                if(!ListUtils.isEmpty(sysUserList)){
                    throw new AppException("该【"+deptDTOList.get(i).getName()+"】科室已经被【"+sysUserList+"】用户绑定为所属科室,不能进行作废操作");
                }
            }
        }
        //缓存操作
        List<BaseDeptDTO> baseDeptDTOS = new ArrayList<>();
        String isValid = baseDeptDTO.getIsValid();
        if(Constants.SF.S.equals(isValid)){
            baseDeptDTO.setIsValid(Constants.SF.F);
        } else {
            baseDeptDTO.setIsValid(Constants.SF.S);
        }
        baseDeptDTOS =  baseDeptDAO.queryAll(baseDeptDTO);

        baseDeptDTO.setIsValid(isValid);
        baseDeptDAO.updateIsValid(baseDeptDTO) ;

        if(Constants.SF.F.equals(baseDeptDTO.getIsValid())){
//            cacheOperate(null,baseDeptDTOS,false);
        }else {
//            cacheOperate(null,baseDeptDTOS,true);
        }
        List<String> deptCodeList = baseDeptDAO.queryDeptCodeByIds(baseDeptDTO);
        baseDeptDTO.setDeptCodeList(deptCodeList);
        return baseDeptDrugstoreDAO.updateIsValid(baseDeptDTO)>0;
    }


    /**
     * @Method findChildTreeNode()
     * @Desrciption  根据节点查询出对应的所有子节点
     * @Param treeMenuNodeList：所有的树结构数据
     *        nationCode：要查询的子节点
     *        nationList：查询结果集
     * @Author fuhui
     * @Date   2020/11/24 19:43
     * @Return
     **/
    private List<String> findChildTreeNode(List<TreeMenuNode> treeMenuNodeList, String code,List<String> nationList ) {
        if(!ListUtils.isEmpty(treeMenuNodeList)){
            for(TreeMenuNode node:treeMenuNodeList){
                if(!StringUtils.isEmpty(node.getParentId())){
                    if(code.equals(node.getParentId())){
                        nationList.add(node.getParentId());
                        findParentTreeNode(treeMenuNodeList,node.getId(),nationList);
                    }
                }
            }
            return nationList;
        }else{
            throw new AppException("科室树数据为空");
        }
    }

    /**
     * @Method: queryAll()
     * @Description: 下拉框查询科室维护信息s
     * @Param: 1、baseDeptDTO 科室维护数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/18 11:14
     * @Return: BaseDeptDTO
     */
    @Override
    public List<BaseDeptDTO> queryAll(BaseDeptDTO baseDeptDTO) {
        //科室性质
        if (StringUtils.isNotEmpty(baseDeptDTO.getFlag()) && "1".equals(baseDeptDTO.getFlag())){
            List<String> list = new ArrayList<>();
            list.add(Constants.KSXZ.KSXZ1);//门诊科室
            list.add(Constants.KSXZ.KSXZ2);//住院科室
            baseDeptDTO.setDeptTypeCodeList(list);
        }
        //检验科室
        if (StringUtils.isNotEmpty(baseDeptDTO.getFlag()) && "7".equals(baseDeptDTO.getFlag())){
            List<String> list = new ArrayList<>();
            list.add(Constants.KSXZ.KSXZ7);//检验科室
            list.add(Constants.KSXZ.KSXZ8);//影像科室
            baseDeptDTO.setDeptTypeCodeList(list);
        }
        return baseDeptDAO.queryAll(baseDeptDTO);
    }


    /**
     * @Method: getCodeTree()
     * @Description: 科室列表树
     * @Param: hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @Override
    public List<TreeMenuNode> getDeptTree(String hospCode) {
        return baseDeptDAO.getDeptTree(hospCode,Constants.SF.S);
    }

    /**
     * @Method: getOutDept()
     * @Description: 查询出库库位
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @Override
    public List<BaseDeptDTO> getOutDept(String hospCode) {
        return baseDeptDAO.getOutDept(hospCode,Constants.SF.S);
    }

    /**
     * @Method: getById()
     * @Description: 根据科室id 查询科室信息
     * @Param:
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/22 23:18
     * @Return: TreeMenuNode树实体类集合
     */
    @Override
    public BaseDeptDTO getById(BaseDeptDTO baseDeptDTO) {
        BaseDeptDTO deptDTO = baseDeptDAO.getById(baseDeptDTO);
        // 根据科室返回回来的科室性质，查询对应科室性质 所有的科室
        List<BaseDeptDTO> typeCodeListDept = baseDeptDAO.getDeptByTypeCode(deptDTO);
        deptDTO.setTypeCodeListDept(typeCodeListDept);
        Map map =new HashMap();
        map.put("hospCode",baseDeptDTO.getHospCode());
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setHospCode(baseDeptDTO.getHospCode());
        map.put("sysCodeDetailDTO",sysCodeDetailDTO);

        // 处理国家编码 三级联动
        List<TreeMenuNode> treeMenuNodeList = sysCodeService_consumer.queryNationCode(map);
        if(!StringUtils.isEmpty(deptDTO.getNationCode())){
            List<String> nationList = new ArrayList<>();
            List<String> stringList= findParentTreeNode(treeMenuNodeList, deptDTO.getNationCode(),nationList);
            deptDTO.setNationList(stringList);
        }
        // 处理类别标识
        String typeIdentity = deptDTO.getTypeIdentity();
        if(!StringUtils.isEmpty(typeIdentity)){
            deptDTO.setTypeIdentityList(Arrays.asList(typeIdentity.split(",")));
        }
        // 1门诊科室  2.住院科室  9，手术科室
        if(Constants.KSXZ.KSXZ1.equals(deptDTO.getTypeCode()) || Constants.KSXZ.KSXZ2.equals(deptDTO.getTypeCode()) || Constants.KSXZ.KSXZ9.equals(deptDTO.getTypeCode()) ){
            List<BaseDeptDrugStoreDTO> storeDTOList = baseDeptDrugstoreDAO.queryBaseDeptDrugstore(baseDeptDTO.getHospCode(), baseDeptDTO.getCode());
            if(!ListUtils.isEmpty(storeDTOList)){
                deptDTO.setDrugStoreList(storeDTOList);
            }
        }
        return deptDTO;
    }

    /**
     * 登录时查询部门信息方法
     *
     * @param baseDeptDTO 实体类参数
     * @return baseDeptDTO  部门基础信息
     * @Date: 2021/9/26 19:05
     */
    @Override
    public BaseDeptDTO getSingleBaseDeptInfoById(BaseDeptDTO baseDeptDTO) {
        BaseDeptDTO deptDTO = baseDeptDAO.getById(baseDeptDTO);
        return deptDTO;
    }

    /**
     * @Method findParentTreeNode()
     * @Desrciption  根据子节点查询出所有的父节点
     * @Param treeMenuNodeList：所有的树结构数据
     *        nationCode：要查询的子节点
     *        nationList：查询结果集
     * @Author fuhui
     * @Date   2020/11/24 19:43
     * @Return
    **/
    private List<String> findParentTreeNode(List<TreeMenuNode> treeMenuNodeList, String nationCode,List<String> nationList ) {
        if(!ListUtils.isEmpty(treeMenuNodeList)){
            for(TreeMenuNode node:treeMenuNodeList){
                if(nationCode.equals(node.getId())){
                    nationList.add(nationCode);
                    if(!StringUtils.isEmpty(node.getParentId())){
                        findParentTreeNode(treeMenuNodeList,node.getParentId(),nationList);
                    }
                }
            }
            return nationList;
        }else{
            throw new AppException("国家编码查询出来数据为空");
        }
    }

    /**
     * @Method getDeptTypeCode
     * @Desrciption  查询科室性质的方法
     * @Param baseDeptDTO 科室维护信息数据传输对象
     *
     * @Author fuhui
     * @Date   2020/9/16 9:55
     * @Return baseDeptDTO 科室维护信息数据传输对象
     **/
    @Override
    public List<BaseDeptDTO> getDeptTypeCode(BaseDeptDTO baseDeptDTO) {

        baseDeptDTO.setIsValid(Constants.SF.S);
        if("1".equals(baseDeptDTO.getFlag())){
          return baseDeptDAO.getDeptTypeIdentity(baseDeptDTO);
        }
        return baseDeptDAO.getDeptTypeCode(baseDeptDTO);
    }
    @Override
    public List<BaseDeptDTO> getPharInfo(BaseDeptDTO baseDeptDTO) {

        baseDeptDTO.setIsValid(Constants.SF.S);
        if(Constants.SF.S.equals(baseDeptDTO.getMultiPharFlag())){
            return baseDeptDAO.getPharInfoOfOperDept(baseDeptDTO);
        }
        return  baseDeptDAO.getPharInfo(baseDeptDTO);
    }

    /**
    * @Method getDeptInfoByLoginDeptId
    * @Desrciption 根据登录科室id获得匹配的发药药房信息
    * @param baseDeptDTO
    * @Author liuqi1
    * @Date   2020/12/3 9:45
    * @Return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    **/
    @Override
    public List<BaseDeptDTO> getDeptInfoByLoginDeptId(BaseDeptDTO baseDeptDTO) {
        // 多药房接口
        if("1".equals(baseDeptDTO.getMultiPharFlag())) {
          List<String> drugstoreCodes = baseDeptDAO.getDrugstoreCode(baseDeptDTO);
          if(ListUtils.isEmpty(drugstoreCodes)) {
            return  null;
          }
          StringBuffer sb = new StringBuffer();
          for (String str:drugstoreCodes) {
            sb.append(str).append(",");
          }
          String keywordStr = sb.deleteCharAt(sb.length() - 1).toString();
          baseDeptDTO.setCode(keywordStr);
          List<BaseDeptDTO> newDeptInfoByLoginDeptId = baseDeptDAO.getNewDeptInfoByLoginDeptId(baseDeptDTO);
          return newDeptInfoByLoginDeptId;
        }
        return baseDeptDAO.getDeptInfoByLoginDeptId(baseDeptDTO);
    }

    /**
     * @Method getDeptByIds
     * @Param [baseDeptDTO]
     * @description   多个id查询
     * @author marong
     * @date 2020/10/15 13:46
     * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
     * @throws
     */
    @Override
    public List<BaseDeptDTO> getDeptByIds(BaseDeptDTO baseDeptDTO) {
        return baseDeptDAO.getDeptByIds(baseDeptDTO);
    }

    /**
    * @Method getDeptByCodes
    * @Param [baseDeptDTO]
    * @description   多个code查询
    * @author marong
    * @date 2020/10/15 17:00
    * @return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    * @throws
    */
    @Override
    public List<BaseDeptDTO> getDeptByCodes(BaseDeptDTO baseDeptDTO) {
        return baseDeptDAO.getDeptByCodes(baseDeptDTO);
    }

    /**
     * @Method getNationCode
     * @Desrciption  查询国家编码
     * @Param
     *
     * @Author fuhui
     * @Date   2020/10/29 13:49
     * @Return TreeMenuNode树实体类集合
     **/
    @Override
    public List<TreeMenuNode> getNationCodeTree(Map map) {
        List<TreeMenuNode> treeMenuNodeList = sysCodeService_consumer.queryNationCode(map);
        return treeMenuNodeList;

    }

    @Override
    public List<BaseDeptDTO> queryNotId(BaseDeptDTO baseDeptDTO) {
        return baseDeptDAO.queryNotId(baseDeptDTO);
    }

    /**
     * @Method: save()
     * @Description: 该方法主要用来保存科室信息维护修改和新增的信息
     * @Param: baseDeptDTO数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/24 14:17
     * @Return: baseDeptDTO数据传输对象集合
     */
    public boolean save(BaseDeptDTO baseDeptDTO) {
        baseDeptDTO.setIsValid(Constants.SF.S);
        List<BaseDeptDTO> deptCodeList = baseDeptDAO.queryCode(baseDeptDTO);
        List<BaseDeptDTO> baseDeptDTOList = baseDeptDAO.queryName(baseDeptDTO);
        List<BaseDeptDrugStoreDTO> drugStoreList = baseDeptDTO.getDrugStoreList();
        if (deptCodeList != null && deptCodeList.size()>0) {
            //说明编码已经存在
            throw new AppException("该科室编码已经存在" + baseDeptDTO.getCode());
        }
        //  科室名称已经存在
        if(baseDeptDTOList.size() >0 && baseDeptDTOList !=null){
            throw new AppException("该科室名称已经存在" + baseDeptDTO.getName());
        }
       else {

           if(!StringUtils.isEmpty(baseDeptDTO.getName())){
               //生成拼音码
               baseDeptDTO.setPym(PinYinUtils.toFirstPY(baseDeptDTO.getName()));
               //生成五笔码
               baseDeptDTO.setWbm(WuBiUtils.getWBCode(baseDeptDTO.getName()));
           }
            // 类别标识
            if(!ListUtils.isEmpty(baseDeptDTO.getTypeIdentityList())){
                String join = String.join(",", baseDeptDTO.getTypeIdentityList());
                baseDeptDTO.setTypeIdentity(join);
            }
            //判断主键Id是否存在 如果存在 则是修改操作 否则就是新增操作
            if (StringUtils.isEmpty(baseDeptDTO.getId())) {
                baseDeptDTO.setId(SnowflakeUtils.getId());  // 设置主键id
                baseDeptDTO.setCrteTime(DateUtils.getNow()); //设置操作时间
                baseDeptDAO.insert(baseDeptDTO);
                // 存入缓存
//                cacheOperate(baseDeptDTO,null,true);

                //判断药房类别代码是否为空  如果不为空  则说明是新增科室关联领药药房
                if (saveDrugStroe(baseDeptDTO, drugStoreList)){
                    return baseDeptDrugstoreDAO.insertDrugstore(drugStoreList) > 0;
                }
            } else {
                BaseDeptDTO dto = baseDeptDAO.getById(baseDeptDTO);
                if(Constants.CENTERSYNCKSCODE.DEPT_CODE.equals(dto.getCode())){
                    throw new AppException("中心平台下发的【"+dto.getName()+"】不允许被修改");
                }

                baseDeptDAO.update(baseDeptDTO) ;
                // 缓存操作 -- 只有有效的时候才进行操作
                if(Constants.SF.S.equals(baseDeptDTO.getIsValid())){
//                    cacheOperate(baseDeptDTO,null,true);
                }
                //匹配了医保科室之后如果结算清单还是卫健委编码就更新结算清单的科室
                if(StringUtils.isNotEmpty(baseDeptDTO.getCatyCode()) && StringUtils.isNotEmpty(baseDeptDTO.getNationCode())){
                    //入院科室
                    baseDeptDAO.updateSetlAdmCaty(baseDeptDTO);
                    //出院科室
                    baseDeptDAO.updateSetlDscgCaty(baseDeptDTO);
                    //转院科室
                    baseDeptDAO.updateSetlRefldeptDept(baseDeptDTO);
                }

                baseDeptDrugstoreDAO.deleteDrugStore(baseDeptDTO);
                if (saveDrugStroe(baseDeptDTO, drugStoreList)){
                    return baseDeptDrugstoreDAO.insertDrugstore(drugStoreList) > 0;
                }
            }
        }
       return true;
    }
    /**
     * @Method saveDrugStroe()
     * @Desrciption  当是修改操作时，需要先删除原来科室关联领药药房的数据，因为Id和创建时间需要重新生成
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/19 15:33
     * @Return
    **/
    private boolean saveDrugStroe(BaseDeptDTO baseDeptDTO, List<BaseDeptDrugStoreDTO> drugStoreList) {
        if (!ListUtils.isEmpty(baseDeptDTO.getDrugStoreList())) {
            for (int i = 0; i < drugStoreList.size(); i++) {
                drugStoreList.get(i).setId(SnowflakeUtils.getId());
                drugStoreList.get(i).setHospCode(baseDeptDTO.getHospCode());
                drugStoreList.get(i).setDeptCode(baseDeptDTO.getCode());
                drugStoreList.get(i).setIsValid(Constants.SF.S);
                drugStoreList.get(i).setCrteId(baseDeptDTO.getCrteId());
                drugStoreList.get(i).setCrteName(baseDeptDTO.getCrteName());
                drugStoreList.get(i).setCrteTime(DateUtils.getNow());
            }
            return true;
        }
        return false;
    }
    /**
     * @Menthod insertUpLoad
     * @Desrciption  科室导入功能
     * @param map
     * @Author fuhui
     * @Date   2021/1/9 13:05
     * @Return boolean
     **/
    @Override
    public boolean insertUpLoad(Map map){

        String hospCode = (String) map.get("hospCode");
        String userName = (String) map.get("crteName");
        String userId = (String) map.get("crteId");
        Date nowDate = DateUtils.getNow();
        List<List<String>> resultList = (List<List<String>>) map.get("resultList");

        // 拿取系统码表列表
        HashMap sysCodeMap = new HashMap();
        sysCodeMap.put("hospCode",hospCode);
        sysCodeMap.put("code","KSXZ,YFLBBS,SF");
        WrapperResponse<Map<String, List<SysCodeSelectDTO>>> byCode = sysCodeService_consumer.getByCode(sysCodeMap);

        Map<String, List<SysCodeSelectDTO>> data = byCode.getData();

        List<SysCodeSelectDTO> ksxz = data.get("KSXZ");
        List<SysCodeSelectDTO> typeIdentity = data.get("YFLBBS");
        List<SysCodeSelectDTO> sf = data.get("SF");
        List<BaseDeptDTO> insertList = new ArrayList<>();
        Map<String, String> ksxzMap = ksxz.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> sfMap = sf.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> typeIdentityMap = typeIdentity.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        int count =1;
        try {
            for (List<String> item : resultList){
                BaseDeptDTO baseDeptDTO =new BaseDeptDTO();
                // 主键id
                baseDeptDTO.setId(SnowflakeUtils.getId());
                // 医院编码
                baseDeptDTO.setHospCode(hospCode);
                // 科室性质
                String ksxzName = item.get(0);
                if(StringUtils.isEmpty(ksxzName)){
                    throw new AppException("导入第【"+count+"】条数据的科室性质不能为空");
                }
                ksxzName = ksxzName.trim();
                if(!ksxzMap.containsKey(ksxzName)){
                    SysCodeDetailDTO ksxzCodeDetail = new SysCodeDetailDTO();
                    Map codeMap = new HashMap();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("code", "KSXZ");
                    Integer maxSeq = sysCodeService_consumer.getMaxSeqno(codeMap).getData();
                    String maxSeqValue = Integer.toString(maxSeq + 1);
                    ksxzCodeDetail.setHospCode(hospCode);
                    ksxzCodeDetail.setCode("KSXZ");
                    ksxzCodeDetail.setName(ksxzName);
                    ksxzCodeDetail.setValue(maxSeqValue);
                    ksxzCodeDetail.setSeqNo(maxSeqValue);
                    ksxzCodeDetail.setIsValid(Constants.SF.S);
                    ksxzCodeDetail.setCrteId(userId);
                    ksxzCodeDetail.setCrteName(userName);
                    codeMap.clear();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("sysCodeDetailDTO", ksxzCodeDetail);
                    sysCodeService_consumer.saveCodeDetail(codeMap);

                    baseDeptDTO.setTypeCode(maxSeqValue);
                    ksxzMap.put(ksxzName,maxSeqValue);
                }
                else{
                    baseDeptDTO.setTypeCode(ksxzMap.get(ksxzName));
                }
                // 国家编码
                baseDeptDTO.setNationCode(item.get(1));
                // 科室编码
                if(StringUtils.isEmpty(item.get(2))){
                    throw new AppException("导入第【"+count+"】条数据的科室编码不能为空");
                }
                else{
                    baseDeptDTO.setCode(item.get(2).trim());
                }

                // 科室名称
                if(StringUtils.isEmpty(item.get(3))){
                    throw new AppException("导入第【"+count+"】条数据的科室名称不能为空");
                }
                else{
                    baseDeptDTO.setName(item.get(3).trim());
                }

                // 病区编码
                String bqbm = ksxzMap.get(item.get(0));
                if(Constants.KSXZ.KSXZ2.equals(bqbm)){
                    if(StringUtils.isEmpty(item.get(4))){
                        throw new AppException("当导入科室性质是住院科室时,导入第【"+count+"】条数据的病区编码不能为空");
                    }else{
                        baseDeptDTO.setWardCode(item.get(4).trim());
                    }
                }
                // 联系电话
                baseDeptDTO.setPhone(item.get(5));
                // 上级科室 如果不是虚拟科室则上级科室不能为空
                if(!Constants.KSXZ.KSXZ16.equals(ksxzMap.get(item.get(0)))){
                    if(StringUtils.isEmpty(item.get(6))){
                        throw new AppException("导入第【"+count+"】条数据的科室上级编码不能为空");
                    }else{
                        baseDeptDTO.setUpDeptCode(item.get(6).trim());
                    }
                }else{
                    if(!StringUtils.isEmpty(item.get(6))){
                        baseDeptDTO.setUpDeptCode(item.get(6).trim());
                    }
                    else{
                        baseDeptDTO.setUpDeptCode(null);
                    }
                }
                // 经管科室编码
                baseDeptDTO.setMgDeptCode(item.get(7));
                // 科室介绍
                baseDeptDTO.setIntro(item.get(8));
                // 科室位置
                baseDeptDTO.setPlace(item.get(9));
                // 科室备注
                baseDeptDTO.setRemark(item.get(10));

                // 有拼音码和五笔码就直接塞入，无就根据名字自动生成
                if (StringUtils.isNotEmpty(item.get(11))){
                    baseDeptDTO.setPym(item.get(11));
                }else if (StringUtils.isNotEmpty(item.get(3))){
                    baseDeptDTO.setPym(PinYinUtils.toFirstPY(item.get(3)));
                }

                if (StringUtils.isNotEmpty(item.get(12))){
                    baseDeptDTO.setWbm(item.get(12));
                }else if (StringUtils.isNotEmpty(item.get(3))){
                    baseDeptDTO.setWbm(WuBiUtils.getWBCode(item.get(3)));
                }
                // 是否有效
                if(StringUtils.isEmpty(item.get(2))){
                    throw new AppException("导入第【"+count+"】条数据的科室有效性不能为空");
                }
                else{
                    baseDeptDTO.setIsValid(sfMap.get(item.get(13).trim()));
                }

                // 药房类别标识
                if(Constants.KSXZ.KSXZ3.equals(ksxzMap.get(item.get(0)).trim()) ||
                Constants.KSXZ.KSXZ4.equals(ksxzMap.get(item.get(0)).trim()) ||
                Constants.KSXZ.KSXZ5.equals(ksxzMap.get(item.get(0)).trim()) ||
                Constants.KSXZ.KSXZ13.equals(ksxzMap.get(item.get(0)).trim()) ||
                Constants.KSXZ.KSXZ14.equals(ksxzMap.get(item.get(0)).trim()) ){
                    if(StringUtils.isEmpty(typeIdentityMap.get(item.get(14).trim()))){
                        throw new AppException("当科室性质是药房药库的时，导入第【"+count+"】条数据的药房类别标识不能为空");
                    }else{
                        baseDeptDTO.setTypeIdentity(typeIdentityMap.get(item.get(14).trim()));
                    }
                }
                // 创建信息
                baseDeptDTO.setCrteTime(nowDate);
                baseDeptDTO.setCrteName(userName);
                baseDeptDTO.setCrteId(userId);
                insertList.add(baseDeptDTO);
                count++;
            }
        }catch (IndexOutOfBoundsException | NullPointerException e){
            throw new AppException("EXCEL数据格式错误，导入失败");
        }

        return this.baseDeptDAO.insertList(insertList);

    }

    /**
     * @param deptDTOList
     * @Method updateBatchDept
     * @Desrciption 科室上传以后 修改上传状态
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 16:04
     * @Return
     */
    @Override
    public boolean updateBatchDept(List<BaseDeptDTO> deptDTOList) {
        return baseDeptDAO.updateBatchDept(deptDTOList);
    }

    /**
     * @Method cacheOperate
     * @Desrciption 缓存操作
     * @Param
     * [baseDeptDTO, operation]
     * @Author liaojunjie
     * @Date   2021/1/14 15:56
     * @Return void
     **/
    public void cacheOperate(BaseDeptDTO baseDeptDTO,List<BaseDeptDTO>baseDeptDTOS, Boolean operation){
        if (baseDeptDTO != null) {
            String key = StringUtils.createKey("dept", baseDeptDTO.getHospCode(), baseDeptDTO.getId());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, baseDeptDTO);
            }
        }

        if (!ListUtils.isEmpty(baseDeptDTOS)) {
            for (BaseDeptDTO dept : baseDeptDTOS) {
                String key = StringUtils.createKey("dept", dept.getHospCode(), dept.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, dept);
                }
            }
        }

    }

    /**
     * @Menthod: uploadDeptInfo
     * @Desrciption: 统一支付平台【3401】-科室信息上传
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-13 16:01
     * @Return: List<BaseDeptDTO>
     **/
    @Override
    public List<BaseDeptDTO> queryBaseDeptByInsure(BaseDeptDTO baseDeptDTO) {
        List<BaseDeptDTO> list = baseDeptDAO.queryBaseDeptByInsure(baseDeptDTO);
        return list;
    }
}
