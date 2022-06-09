package cn.hsa.base.bd.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bd.bo.BaseDiseaseBO;
import cn.hsa.module.base.bd.dao.BaseDiseaseDAO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseRuleDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.insure.module.service.InsureDiseaseMatchService;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.base.jbxx.bo.impl
 * @Class_name: BaseDiseaseBOImpl
 * @Describe: 疾病管理逻辑实现层
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/7 16:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseDiseaseBOImpl extends HsafBO implements BaseDiseaseBO{

    /**
     * 疾病管理数据库访问接口
     */
    @Resource
    private BaseDiseaseDAO baseDiseaseDAO;

    @Resource
    SysCodeService sysCodeService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private InsureDiseaseMatchService insureDiseaseMatchService_consumer;

    @Resource
    AyncDiseaseMatch ayncDiseaseMatch;
    /**
     * @Method getById
     * @Desrciption 通过id获取疾病信息
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/16 9:27
     * @Return cn.hsa.module.base.bd.dto.BaseDiseaseDTO
     **/
    @Override
    public BaseDiseaseDTO getById(BaseDiseaseDTO baseDiseaseDTO) {
        return this.baseDiseaseDAO.getById(baseDiseaseDTO);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询全部疾病信息(默认状态为有效)
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 8:53
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseDiseaseDTO baseDiseaseDTO) {


        if(!StringUtils.isEmpty(baseDiseaseDTO.getTypeCode())){
            HashMap map = new HashMap();
            map.put("hospCode",baseDiseaseDTO.getHospCode());
            map.put("code","JBFL");
            List<TreeMenuNode> treeMenuNodeList = sysCodeService.getCodeData(map).getData();
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    baseDiseaseDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseDiseaseDTO.setIds(list);
        }
        baseDiseaseDTO.setTypeCode("");
        // 基础疾病信息查询需要根据是否有效过滤查询 2021-12-31 lly
        if (StringUtils.isNotEmpty(baseDiseaseDTO.getBaseQuery())&&"1".equals(baseDiseaseDTO.getBaseQuery())){

        }else {
            // 其他查询只查询有效疾病信息
            baseDiseaseDTO.setIsValid(Constants.SF.S);
        }

        //设置分页信息
        PageHelper.startPage(baseDiseaseDTO.getPageNo(), baseDiseaseDTO.getPageSize());
        List<BaseDiseaseDTO> BaseDiseaseDTOList = this.baseDiseaseDAO.queryPage(baseDiseaseDTO);
        return PageDTO.of(BaseDiseaseDTOList);
    }

    @Override
    public List<BaseDiseaseDTO> queryAll(BaseDiseaseDTO baseDiseaseDTO) {
        List<BaseDiseaseDTO> baseDiseaseDTOS = this.baseDiseaseDAO.queryAll(baseDiseaseDTO);
        return baseDiseaseDTOS;
    }

    /**
     * @Method save
     * @Desrciption 增加/修改单条疾病信息
     * @Param
     * [baseDiseaseDTO]
     * @Author liaojunjie
     * @Date   2020/7/24 16:58
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(BaseDiseaseDTO baseDiseaseDTO) {
        //判断疾病编码、附加编码、国家编码是否已经存在
        BaseDiseaseDTO b = new BaseDiseaseDTO();
        b.setId(baseDiseaseDTO.getId());
        b.setHospCode(baseDiseaseDTO.getHospCode());
        try{
            b.setCode(baseDiseaseDTO.getCode());
            if(!StringUtils.isEmpty(b.getCode())&& baseDiseaseDAO.isCodeExist(b)>0){
                throw new AppException("疾病编码重复");
            }
        }catch (NullPointerException e){
            throw new AppException("疾病编码为空");
        }
        try{
            b.setCode("");
            b.setNationCode(baseDiseaseDTO.getNationCode());
            if(!StringUtils.isEmpty(b.getNationCode())&& baseDiseaseDAO.isCodeExist(b)>0){
                throw new AppException("国家编码重复");
            }
        }catch (NullPointerException e){
            throw new AppException("国家编码为空");
        }
        try{
            b.setNationCode("");
            b.setAttachCode(baseDiseaseDTO.getAttachCode());
            if(!StringUtils.isEmpty(b.getAttachCode())&& baseDiseaseDAO.isCodeExist(b)>0){
                throw new AppException("附加编码重复");
            }
        }catch (NullPointerException e){
            throw new AppException("附加编码为空");
        }

        //拼音码五笔码自动生成
        if (!StringUtils.isEmpty(baseDiseaseDTO.getName())){
            baseDiseaseDTO.setPym(PinYinUtils.toFirstPY(baseDiseaseDTO.getName()));
            baseDiseaseDTO.setWbm(WuBiUtils.getWBCode(baseDiseaseDTO.getName()));
        }

        if(StringUtils.isEmpty(baseDiseaseDTO.getId())){
            //设置id
            baseDiseaseDTO.setId(SnowflakeUtils.getId());
            //设置创建时间
            baseDiseaseDTO.setCrteTime(DateUtils.getNow());

            this.baseDiseaseDAO.insert(baseDiseaseDTO);
            // 存入缓存
//            cacheOperate(baseDiseaseDTO,null,true);
            return true;
        }else{
            //修改
            this.baseDiseaseDAO.update(baseDiseaseDTO);
            // 缓存操作 -- 只有有效的时候才进行操作
            if(Constants.SF.S.equals(baseDiseaseDTO.getIsValid())){
//                cacheOperate(baseDiseaseDTO,null,true);
            }
            return true;
        }
    }

    /**
     * @Method updateStatus()
     * @Description 删除单/多条疾病信息(修改状态为无效)
     *
     * @Param
     * [baseDiseaseDTO]
     *
     * @Author liaojunjie
     * @Date 2020/7/8 9:03
     * @Return Boolean
     **/
    @Override
    public Boolean updateStatus(BaseDiseaseDTO baseDiseaseDTO) {
        //缓存操作
        List<BaseDiseaseDTO> baseDiseaseDTOS = new ArrayList<>();
        String isValid = baseDiseaseDTO.getIsValid();
        if(Constants.SF.S.equals(isValid)){
            baseDiseaseDTO.setIsValid(Constants.SF.F);
        } else {
            baseDiseaseDTO.setIsValid(Constants.SF.S);
        }
        baseDiseaseDTOS = baseDiseaseDAO.queryAll(baseDiseaseDTO);

        baseDiseaseDTO.setIsValid(isValid);
        this.baseDiseaseDAO.updateStatus(baseDiseaseDTO);

        if(Constants.SF.F.equals(baseDiseaseDTO.getIsValid())){
//            cacheOperate(null,baseDiseaseDTOS,false);
        }else {
//            cacheOperate(null,baseDiseaseDTOS,true);
        }
        return true;
    }

    /**
    * @Method getDeptByIds
    * @Param [baseDiseaseDTO]
    * @description   多个id查询
    * @author marong
    * @date 2020/10/15 15:14
    * @return java.util.List<cn.hsa.module.base.bd.dto.BaseDiseaseDTO>
    * @throws
    */
    @Override
    public List<BaseDiseaseDTO> getDiseaseByIds(BaseDiseaseDTO baseDiseaseDTO) {
        return  baseDiseaseDAO.getDiseaseByIds(baseDiseaseDTO);
    }

    /**
     * @Method queryDiseaseRule()
     * @Description 根据疾病id分页获取对应的疾病规则列表
     * @Param [baseDiseaseDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryDiseaseRule(BaseDiseaseDTO baseDiseaseDTO) {
        PageHelper.startPage(baseDiseaseDTO.getPageNo(), baseDiseaseDTO.getPageSize());
        List<BaseDiseaseRuleDTO> list = baseDiseaseDAO.queryDiseaseRule(baseDiseaseDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method saveDiseaseRule()
     * @Description 新增/修改疾病规则
     * @Param [baseDiseaseRuleDTOS]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return Boolean
     **/
    @Override
    public Boolean saveDiseaseRule(Map map) {
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");
        String hospCode = MapUtils.get(map, "hospCode");
        List<BaseDiseaseRuleDTO> baseDiseaseRuleDTOS = MapUtils.get(map, "baseDiseaseRuleDTOS");

        List<BaseDiseaseRuleDTO> addList = new ArrayList<>();
        List<BaseDiseaseRuleDTO> editList = new ArrayList<>();

        for (BaseDiseaseRuleDTO diseaseRuleDTO : baseDiseaseRuleDTOS) {
            if (StringUtils.isEmpty(diseaseRuleDTO.getId())){
                //新增
                diseaseRuleDTO.setId(SnowflakeUtils.getId());
                diseaseRuleDTO.setHospCode(hospCode);
                diseaseRuleDTO.setIsValid(Constants.SF.S);
                diseaseRuleDTO.setCrteId(userId);
                diseaseRuleDTO.setCrteName(userName);
                diseaseRuleDTO.setCrteTime(DateUtils.getNow());
                addList.add(diseaseRuleDTO);
            }else {
                //编辑
                editList.add(diseaseRuleDTO);
            }
        }

        try {
            //新增
            if (!ListUtils.isEmpty(addList)){
                baseDiseaseDAO.insertDiseaseRule(addList);
            }
            //编辑
            if (!ListUtils.isEmpty(editList)){
                baseDiseaseDAO.updateDiseaseRule(editList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @Method delDiseaseRule()
     * @Description 删除疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return WrapperResponse<Boolean>
     **/
    @Override
    public Boolean updateDiseaseRule(BaseDiseaseRuleDTO baseDiseaseRuleDTO) {
        return baseDiseaseDAO.updateDiseaseRuleIsValid(baseDiseaseRuleDTO) > 0;
    }

    /**
     * @Method queryDiseaseRuleByDid()
     * @Description 检查质控疾病规则
     * @Param [baseDiseaseRuleDTO]
     * @Author luoyong
     * @Date 2020-11-26 14:32
     * @Return List<BaseDiseaseRuleDTO>
     **/
    @Override
    public List<BaseDiseaseRuleDTO> queryDiseaseRuleByDid(BaseDiseaseDTO baseDiseaseDTO) {
        return baseDiseaseDAO.queryDiseaseRuleByDid(baseDiseaseDTO);
    }

    /**
     * @param map
     * @Method insertInsureDiseaseMatch
     * @Desrciption 医保统一支付平台： 同步疾病数据到医保疾病匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    @Override
    public Boolean insertInsureDiseaseMatch(Map<String, Object> map) {
        BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
        String hospCode = MapUtils.get(map,"hospCode");
        String crteId = MapUtils.get(map,"crteId");
        String crteName = MapUtils.get(map,"crteName");
        String crteTime = MapUtils.get(map,"crteTime");

        String insureRegCode = MapUtils.get(map,"regCode");
        baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setIsValid(Constants.SF.S);
        baseDiseaseDTO.setIsNationCode(true);
        List<BaseDiseaseDTO> baseDiseaseDTOList = baseDiseaseDAO.queryAll(baseDiseaseDTO);
        InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
        insureDiseaseMatchDTO.setInsureRegCode(insureRegCode);
        insureDiseaseMatchDTO.setHospCode(hospCode);
        map.put("insureDiseaseMatchDTO" ,insureDiseaseMatchDTO);
        insureDiseaseMatchService_consumer.deleteDiseaseMatch(map);
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = new ArrayList<>();
        InsureDiseaseMatchDTO diseaseMatchDTO = null;
        for(BaseDiseaseDTO diseaseDTO : baseDiseaseDTOList){
            diseaseMatchDTO = new InsureDiseaseMatchDTO();
            diseaseMatchDTO.setId(SnowflakeUtils.getId());
            diseaseMatchDTO.setHospCode(hospCode);
            diseaseMatchDTO.setTypeCode(diseaseDTO.getTypeCode());
            diseaseMatchDTO.setInsureRegCode(insureRegCode);
            diseaseMatchDTO.setHospIllnessId(diseaseDTO.getId());
            diseaseMatchDTO.setHospIllnessCode(diseaseDTO.getCode());
            diseaseMatchDTO.setHospIllnessName(diseaseDTO.getName());
            diseaseMatchDTO.setInsureIllnessId(null);
            diseaseMatchDTO.setInsureIllnessCode(diseaseDTO.getNationCode());
            diseaseMatchDTO.setInsureIllnessName(diseaseDTO.getName());
            diseaseMatchDTO.setIsMatch(Constants.SF.S);
            diseaseMatchDTO.setIsTrans(Constants.SF.S);
            diseaseMatchDTO.setAuditCode(Constants.SF.S);
            diseaseMatchDTO.setRemark(null);
            diseaseMatchDTO.setCrteId(crteId);
            diseaseMatchDTO.setCrteName(crteName);
            diseaseMatchDTO.setCrteTime(DateUtils.getNow());
            diseaseMatchDTO.setTreatmentCode(null);
            diseaseMatchDTO.setOperNum(null);
            diseaseMatchDTO.setUnoperNum(null);
            insureDiseaseMatchDTOList.add(diseaseMatchDTO);
        }
        if(!ListUtils.isEmpty(insureDiseaseMatchDTOList)){
            map.put("insureDiseaseMatchDTOList",insureDiseaseMatchDTOList);
            insureDiseaseMatchService_consumer.insertBatchDisease(map).getData();
        }
        return true;
    }

    @Override
    public PageDTO queryUnifiedPage(BaseDiseaseDTO baseDiseaseDTO) {
        PageHelper.startPage(baseDiseaseDTO.getPageNo(),baseDiseaseDTO.getPageSize());
        List<BaseDiseaseDTO> baseDiseaseDTOList = this.baseDiseaseDAO.queryUnifiedPage(baseDiseaseDTO);
        return PageDTO.of(baseDiseaseDTOList);
    }

    public void cacheOperate(BaseDiseaseDTO baseDiseaseDTO,List<BaseDiseaseDTO> baseDiseaseDTOS, Boolean operation){
        if (baseDiseaseDTO != null) {
            String key = StringUtils.createKey("disease", baseDiseaseDTO.getHospCode(), baseDiseaseDTO.getId());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, baseDiseaseDTO);
            }
        }

        if (!ListUtils.isEmpty(baseDiseaseDTOS)) {
            for (BaseDiseaseDTO disease : baseDiseaseDTOS) {
                String key = StringUtils.createKey("disease", disease.getHospCode(), disease.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, disease);
                }
            }
        }
    }

    @Override
    public List<BaseDiseaseDTO> queryAllInfectious(BaseDiseaseDTO baseDiseaseDTO) {
        return baseDiseaseDAO.queryAllInfectious(baseDiseaseDTO);
    }

    @Override
    public Boolean updateDisease(BaseDiseaseDTO baseDiseaseDTO) {
        //设置拆分List大小
        int limitLength = 50;
        try {
            log.info("开始执行任务");
            //获取所有匹配好的数据
            List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = baseDiseaseDAO.getDiseaseIsMatch(baseDiseaseDTO);
            //拆分数据
            List<List<InsureDiseaseMatchDTO>> splitList = ListUtils.splitList(insureDiseaseMatchDTOList, limitLength);
            //循环调用异步线程
            for (List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS : splitList) {
                //异步线程需要再调整，直接同步调用速度处理速度也还能接受，后面再维护
//                ayncDiseaseMatch.executeAsyncTask(insureDiseaseMatchDTOS,baseDiseaseDTO.getHospCode());
                baseDiseaseDAO.updateDiseaseMatch(insureDiseaseMatchDTOS,baseDiseaseDTO.getHospCode());
            }
            log.info("任务执行完成");
        } catch (Exception e) {
            log.error("执行更新任务出错");
            throw e;
        }
        return true;
    }
}
