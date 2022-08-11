package cn.hsa.base.profileFile.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.bo.BaseOrderRuleBO;
import cn.hsa.module.base.profileFile.bo.BaseProfileFileBO;
import cn.hsa.module.base.profileFile.dao.BaseProfileFileDAO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.sys.parameter.dao.SysParameterDAO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @Package_name: cn.hsa.base.profileFile.bo.impl
 * @Class_name: BaseProfileFileBOImpl
 * @Describe: 本地档案服务bo实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-04-30 14:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseProfileFileBOImpl extends HsafBO implements BaseProfileFileBO {

    @Resource
    private BaseProfileFileDAO baseProfileFileDAO;

    @Resource
    private SysParameterDAO sysParameterDAO;

    @Resource
    private BaseOrderRuleBO baseOrderRuleBO;

    /**
     * @Menthod: save
     * @Desrciption: 保存/新增个人档案
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-30 15:38
     * @Return: OutptProfileFileExtendDTO
     **/
    @Override
    public OutptProfileFileDTO save(OutptProfileFileDTO outptProfileFileDTO) {
        //校验身份证格式是否正确
        this.checkCard(outptProfileFileDTO.getCertNo(), outptProfileFileDTO.getCertCode());
        if (StringUtils.isNotEmpty(outptProfileFileDTO.getType())) {
            String type = outptProfileFileDTO.getType();
            if ("0".equals(type)) {
                if (StringUtils.isEmpty(outptProfileFileDTO.getId())) {
                    //判断身份证是否存在
                    if (isCertNoExist(outptProfileFileDTO)) {
                        throw new AppException("身份证号重复，请重新输入");
                    }
                    outptProfileFileDTO = insert(outptProfileFileDTO);
                } else {
                    outptProfileFileDTO = update(outptProfileFileDTO);
                }
            } else if (type.equals("1") || type.equals("2")) {
                if (Constants.ZJLB.QT.equals(outptProfileFileDTO.getCertCode())) {
                    outptProfileFileDTO = insert(outptProfileFileDTO);
                } else {
                    //判断此身份证是否建档
                    OutptProfileFileDTO newFile = baseProfileFileDAO.isCertNoExistS(outptProfileFileDTO);
                    if (newFile != null) {
                        newFile.setType(type);
                        newFile.setHospCode(outptProfileFileDTO.getHospCode());
                        outptProfileFileDTO = update(outptProfileFileDTO);
                    } else {
                        outptProfileFileDTO = insert(outptProfileFileDTO);
                    }
                }
            }
        } else {
            throw new AppException("请传建档类型(type)");
        }
        return outptProfileFileDTO;
    }

    /**
     * @Menthod: queryPage
     * @Desrciption: 分页查询所有queryPage
     * @Param: outptProfileFileDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-07 11:12
     * @Return: PageDTO
     **/
    @Override
    public PageDTO queryPage(OutptProfileFileDTO outptProfileFileDTO) {
        outptProfileFileDTO.setIsValid(Constants.SF.S);
        PageHelper.startPage(outptProfileFileDTO.getPageNo(), outptProfileFileDTO.getPageSize());
        List<OutptProfileFileDTO> list = baseProfileFileDAO.queryPage(outptProfileFileDTO);
        return PageDTO.of(list);
    }

    @Override
    public List<OutptProfileFileDTO> queryBaseProfileByIds(OutptProfileFileDTO outptProfileFileDTO) {
        outptProfileFileDTO.setIsValid(Constants.SF.S);
        return baseProfileFileDAO.queryPage(outptProfileFileDTO);
    }

    @Override
    public List<OutptProfileFileDTO> queryAll(OutptProfileFileDTO outptProfileFileDTO) {
        return baseProfileFileDAO.queryAll(outptProfileFileDTO);
    }


    /**
     * @Method isCertNoExist
     * @Desrciption 暂时只可判断档案表是否有该条身份证对应的档案信息
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:03
     * @Return java.lang.Boolean
     **/
    public Boolean isCertNoExist(OutptProfileFileDTO outptProfileFileDTO) {
        if (Constants.ZJLB.QT.equals(outptProfileFileDTO.getCertCode())) {
            // 证件类别为其他，直接返回false
            return false;
        } else {
            return baseProfileFileDAO.isCertNoExist(outptProfileFileDTO) != null ? true : false;
        }
    }

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:56
     * @Return OutptProfileFileDTO
     **/
    @Override
    public OutptProfileFileDTO getById(OutptProfileFileDTO outptProfileFileDTO) {
        return baseProfileFileDAO.getById(outptProfileFileDTO);
    }

    /**
     * @Method update
     * @Desrciption 修改档案
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/9/22 16:50
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO
     **/
    private OutptProfileFileDTO update(OutptProfileFileDTO outptProfileFileDTO) {
        try{
            OutptProfileFileDTO profileFileDTO = baseProfileFileDAO.queryById(outptProfileFileDTO);
        if (profileFileDTO == null) {
            //新增
            return insert(outptProfileFileDTO);
        } else {
            // 门诊/住院只修改档案相关就诊信息，不修改基本信息
            OutptProfileFileDTO param = new OutptProfileFileDTO();
            param.setId(profileFileDTO.getId());
            param.setHospCode(profileFileDTO.getHospCode());
            //更新状态
            if ("2".equals(outptProfileFileDTO.getType()) && StringUtils.isNotEmpty(outptProfileFileDTO.getType())){
                // 档案表已存在病人类型
                if (StringUtils.isNotEmpty(profileFileDTO.getPatientCode())){
                    param.setPatientCode(profileFileDTO.getPatientCode());
                }
                if (StringUtils.isNotEmpty(profileFileDTO.getPreferentialTypeId())){
                    param.setPreferentialTypeId(profileFileDTO.getPreferentialTypeId());
                }
                if (StringUtils.isEmpty(profileFileDTO.getOutProfile())){
                    // 门诊档案号
                    String outProfile = baseOrderRuleBO.updateOrderNo(outptProfileFileDTO.getHospCode(), "104");
                    param.setOutProfile(outProfile);
                }
                //门诊
                // update 2022-03-04 luoyong 入院登记时写入住院次数到就诊表
                int totalOut = profileFileDTO.getTotalOut() == null ? 1 : profileFileDTO.getTotalOut() + 1;
                param.setTotalOut(totalOut);
                profileFileDTO.setTotalOut(totalOut);
                param.setOutptLastVisitTime(DateUtils.getNow());
                baseProfileFileDAO.update(param);
            } else if ("1".equals(outptProfileFileDTO.getType()) && StringUtils.isNotEmpty(outptProfileFileDTO.getType())){
                // 档案表已存在病人类型
                if (StringUtils.isNotEmpty(profileFileDTO.getPatientCode())){
                    param.setPatientCode(profileFileDTO.getPatientCode());
                }
                if (StringUtils.isNotEmpty(profileFileDTO.getPreferentialTypeId())){
                    param.setPreferentialTypeId(profileFileDTO.getPreferentialTypeId());
                }
                if (StringUtils.isEmpty(profileFileDTO.getInProfile())){
                    // 住院病案号
                    String inProfile ="";
                    // 是否开启自定义住院号
                    SysParameterDTO sysParameterDTO = sysParameterDAO.getParameterByCode(outptProfileFileDTO.getHospCode(), "BAH_SF");
                    if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue()) && "1".equals(sysParameterDTO.getValue())) {
                        inProfile = baseOrderRuleBO.updateOrderNo(outptProfileFileDTO.getHospCode(), "361");
                    } else {
                        inProfile = baseOrderRuleBO.updateOrderNo(outptProfileFileDTO.getHospCode(), "36");
                    }
                    param.setInProfile(inProfile);
                }
                //住院
                // update 2022-03-04 luoyong 入院登记时写入住院次数到就诊表
                int totalIn = profileFileDTO.getTotalIn() == null ? 1 : profileFileDTO.getTotalIn() + 1;
                param.setTotalIn(totalIn);
                profileFileDTO.setTotalIn(totalIn);
                param.setInptLastVisitTime(DateUtils.getNow());
                baseProfileFileDAO.update(param);
            } else {
                // 只有档案管理(type=0)才修改档案的基本信息，门诊/住院不修改
                baseProfileFileDAO.update(outptProfileFileDTO);
            }
            return profileFileDTO;
        }
        }catch (Exception appException){
            if(appException.getMessage().contains("TooManyResultsException")){
                throw new AppException("档案存在重复的身份证号！");
            }
           throw appException;
        }
    }

    /**
     * @Method insert
     * @Desrciption 新增档案
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/9/22 16:50
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO
     **/
    private OutptProfileFileDTO insert(OutptProfileFileDTO outptProfileFileDTO) {
        outptProfileFileDTO.setId(SnowflakeUtils.getId());
        outptProfileFileDTO.setIsValid(Constants.SF.S);
        // 住院病案号
        String inProfile ="";
        // 是否开启自定义住院号
        SysParameterDTO sysParameterDTO = sysParameterDAO.getParameterByCode(outptProfileFileDTO.getHospCode(), "BAH_SF");
        if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue()) && "1".equals(sysParameterDTO.getValue())) {
            inProfile = baseOrderRuleBO.updateOrderNo(outptProfileFileDTO.getHospCode(), "361");
        } else {
            inProfile = baseOrderRuleBO.updateOrderNo(outptProfileFileDTO.getHospCode(), "36");
        }
        outptProfileFileDTO.setInProfile(inProfile);
        // 门诊档案号
        String outProfile = baseOrderRuleBO.updateOrderNo(outptProfileFileDTO.getHospCode(), "104");
        outptProfileFileDTO.setOutProfile(outProfile);
        //新增
        if ("2".equals(outptProfileFileDTO.getType()) && StringUtils.isNotEmpty(outptProfileFileDTO.getType())){
            //门诊
            if (outptProfileFileDTO.getTotalOut() == null) {
                outptProfileFileDTO.setTotalOut(1);
            }
            if (outptProfileFileDTO.getOutptLastVisitTime() == null) {
                outptProfileFileDTO.setOutptLastVisitTime(DateUtils.getNow());
            }
        } else if ("1".equals(outptProfileFileDTO.getType()) && StringUtils.isNotEmpty(outptProfileFileDTO.getType())){
            //住院
            if (outptProfileFileDTO.getTotalIn() == null) {
                outptProfileFileDTO.setTotalIn(1);
            }
            if (outptProfileFileDTO.getInptLastVisitTime() == null) {
                outptProfileFileDTO.setInptLastVisitTime(DateUtils.getNow());
            }
        }
        outptProfileFileDTO.setPym(PinYinUtils.toFirstPY(outptProfileFileDTO.getName()));
        outptProfileFileDTO.setWbm(WuBiUtils.getWBCode(outptProfileFileDTO.getName()));
        // 档案表入库
        baseProfileFileDAO.insert(outptProfileFileDTO);
        return outptProfileFileDTO;
    }

    /**
     * @Method checkCard
     * @Desrciption 校验身份证格式
     * @Param [cardNo]
     * @Author liaojunjie
     * @Date 2020/9/22 16:50
     * @Return boolean
     **/
    private boolean checkCard(String cardNo, String certCode) {
        if (StringUtils.isEmpty(cardNo) && !Constants.ZJLB.QT.equals(certCode)) {
            throw new AppException("证件号为空，请检查后重新输入");
        } else if (StringUtils.isEmpty(certCode)) {
            throw new AppException("证件类型不能为空，请检查后重新输入");
        } else {
            if (Constants.ZJLB.JMSFZ.equals(certCode)) { // 居民身份证
                Pattern pattern = compile("^-?\\d+(\\.\\d+)?$");
                if(cardNo.length() == 15){
                    Matcher isNum = pattern.matcher(cardNo);
                    if(!isNum.matches()){
                        throw new AppException("身份证号15位时只能为纯数字，请检查后重新输入");
                    }
                }else if(cardNo.length() == 18){
                    Matcher isNum = pattern.matcher(cardNo.substring(0,17));
                    Boolean flag = cardNo.substring(17,18) == "X"?true:false;
                    if(!isNum.matches() && !flag){
                        throw new AppException("身份证号18位时只能前17位为纯数字，最后1位为大写英文字母X，请检查后重新输入");
                    }
                }else{
                    throw new AppException("身份证号只能为15位或者18位，请检查后重新输入");
                }
            } else if (Constants.ZJLB.JMHKB.equals(certCode)) { // 户口本
                Pattern pattern = compile("\\\\d{9}");
                Matcher matcher = pattern.matcher(cardNo);
                if (!matcher.matches()) {
                    throw new AppException("居民户口薄格式有误，请检查后重新出入");
                }
            }else if (Constants.ZJLB.GATXZ.equals(certCode)) { // 港澳通行证
                Pattern pattern = compile("^[HMhm]{1}([0-9]{10}|[0-9]{8})$");
                Matcher matcher = pattern.matcher(cardNo);
                if (!matcher.matches()) {
                    throw new AppException("港澳通行证格式有误，请检查后重新出入");
                }
            } else if (Constants.ZJLB.TWTXZ.equals(certCode)) { // 台湾通行证
                Pattern pattern = compile("^\\d{8}|^[a-zA-Z0-9]{10}|^\\d{18}$");
                Matcher matcher = pattern.matcher(cardNo);
                if (!matcher.matches()) {
                    throw new AppException("台湾通行证格式有误，请检查后重新出入");
                }
            } else if (Constants.ZJLB.JGZ.equals(certCode)) { // 军官证
                Pattern pattern = compile("^[\\\\u4E00-\\\\u9FA5](字第)([0-9a-zA-Z]{4,8})(号?)$");
                Matcher matcher = pattern.matcher(cardNo);
                if (!matcher.matches()) {
                    throw new AppException("军官证格式有误，请检查后重新出入");
                }
            } else if (Constants.ZJLB.JSZ.equals(certCode)) { // 驾驶证
                Pattern pattern = compile("\\\\d{12}$");
                Matcher matcher = pattern.matcher(cardNo);
                if (!matcher.matches()) {
                    throw new AppException("驾驶证格式有误，请检查后重新出入");
                }
            } else if (Constants.ZJLB.HZ.equals(certCode)) { // 护照
                Pattern pattern = compile("^([a-zA-z]|[0-9]){5,17}$");
                Matcher matcher = pattern.matcher(cardNo);
                if (!matcher.matches()) {
                    throw new AppException("护照格式有误，请检查后重新出入");
                }
            }
        }
        return true;
    }

    /**
     * @Method getAddressTree
     * @Desrciption 获取码表地址树
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/11/12 11:18
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    @Override
    public List<TreeMenuNode> getAddressTree(OutptProfileFileDTO outptProfileFileDTO){
        return baseProfileFileDAO.getAddressTree(outptProfileFileDTO);
    }

    /**
     * @Method isCertNoExist
     * @Desrciption 删除档案
     * @Param
     * [id]
     * @Author yuelong.chen
     * @Date   2021/11/23 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean deleteProfileFile(Map map) {
        String profileId = MapUtils.get(map,"id");
        //查询是否之前就诊过
        OutptProfileFileDTO outptProfileFileDTO = baseProfileFileDAO.queryProfileFile(profileId);
        if(StringUtils.isEmpty(outptProfileFileDTO.getId())||outptProfileFileDTO == null){
            throw new AppException("未找到病人信息，请刷新页面！");
        }
        if((null != outptProfileFileDTO.getTotalOut()&&outptProfileFileDTO.getTotalOut()>1)//判断第一次门诊
                ||(null != outptProfileFileDTO.getTotalIn()&&outptProfileFileDTO.getTotalIn()>1)//判断第一次住院
                ||(null != outptProfileFileDTO.getTotalOut()//判断有过一次门诊情况且来第一次住院，也说明已经在医院就诊过
                &&null != outptProfileFileDTO.getTotalIn()
                &&(outptProfileFileDTO.getTotalOut()+outptProfileFileDTO.getTotalIn())>1)){
            throw new AppException("当前档案病人已经就诊，不允许删除！");
        }
        if(!"0".equals(baseProfileFileDAO.queryPatient(profileId,outptProfileFileDTO.getCertNo()))){
                throw new AppException("当前档案病人正在就诊，不允许删除！");
        }
        return baseProfileFileDAO.deleteProfileFile(map);
    }

}
