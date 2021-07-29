package cn.hsa.center.outptprofilefile.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.center.outptprofilefile.bo.OutptProfileFileBO;
import cn.hsa.module.center.outptprofilefile.dao.OutptProfileFileDAO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @Package_name: cn.hsa.center.outptprofilefile.bo.impl
 * @Class_name:: OutptProfileFileBOImpl
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/19 19:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptProfileFileBOImpl extends HsafBO implements OutptProfileFileBO {

    @Resource
    private OutptProfileFileDAO outptProfileFileDAO;

    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过ID查询个人档案
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:02
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO
     **/
    @Override
    public OutptProfileFileDTO getById(OutptProfileFileDTO outptProfileFileDTO) {
        return outptProfileFileDAO.getById(outptProfileFileDTO);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有个人档案
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:02
     * @Return java.util.List<cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO>
     **/
    @Override
    public List<OutptProfileFileDTO> queryAll(OutptProfileFileDTO outptProfileFileDTO) {
        return outptProfileFileDAO.queryAll(outptProfileFileDTO);
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询个人档案
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:02
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(OutptProfileFileDTO outptProfileFileDTO) {
        PageHelper.startPage(outptProfileFileDTO.getPageNo(), outptProfileFileDTO.getPageSize());
        List<OutptProfileFileDTO> outptProfileFileDTOS = outptProfileFileDAO.queryPage(outptProfileFileDTO);
        return PageDTO.of(outptProfileFileDTOS);
    }

    /**
     * @Method save
     * @Desrciption 保存/新增个人档案 统一接口
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:02
     * @Return java.lang.Boolean
     **/
    @Override
    public OutptProfileFileExtendDTO save(OutptProfileFileDTO outptProfileFileDTO) {
        //校验身份证格式是否正确
        checkCard(outptProfileFileDTO.getCertNo(), outptProfileFileDTO.getCertCode());
        if(StringUtils.isNotEmpty(outptProfileFileDTO.getType())){
            String type = outptProfileFileDTO.getType();
            OutptProfileFileExtendDTO extendDTO = null;
            if (StringUtils.isNotEmpty(type)) {
                if (type.equals("0")) {
                    if(StringUtils.isEmpty(outptProfileFileDTO.getId())){
                        //判断身份证是否存在
                        if (isCertNoExist(outptProfileFileDTO)) {
                            throw new AppException("证件号重复，请重新输入");
                        }
                        extendDTO = insert(outptProfileFileDTO);
                    }else{
                        extendDTO = update(outptProfileFileDTO);
                    }
                } else if (type.equals("1") || type.equals("2")) {
                    //判断此身份证是否建档
                    OutptProfileFileDTO newFile = outptProfileFileDAO.isCertNoExistS(outptProfileFileDTO);
                    if(newFile != null){
                        newFile.setType(type);
                        newFile.setHospCode(outptProfileFileDTO.getHospCode());
                        extendDTO = update(newFile);
                    }else{
                        extendDTO = insert(outptProfileFileDTO);
                    }
                }
            }
            return extendDTO;
        }else {
            throw new AppException("请传建档类型(type)");
        }
    }

    /**
     * @Method insert
     * @Desrciption 新增档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/9/22 16:50
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO
     **/
    public OutptProfileFileExtendDTO insert(OutptProfileFileDTO outptProfileFileDTO) {
        //设置ID
        String id = SnowflakeUtils.getId();
        outptProfileFileDTO.setId(id);
        //新增一条主表数据
        outptProfileFileDAO.insert(outptProfileFileDTO);
        //新增一条从表数据
        OutptProfileFileExtendDTO extendDTO = addExtend(outptProfileFileDTO);
        return extendDTO;
    }

    /**
     * @Method update
     * @Desrciption  修改档案
     * @Param
     * [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/9/22 16:50
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO
     **/
    public OutptProfileFileExtendDTO update(OutptProfileFileDTO outptProfileFileDTO){
        OutptProfileFileExtendDTO outptProfileFileExtendDTO = new OutptProfileFileExtendDTO();
        outptProfileFileExtendDTO.setHospCode(outptProfileFileDTO.getHospCode());
        outptProfileFileExtendDTO.setProfileId(outptProfileFileDTO.getId());
        List<OutptProfileFileExtendDTO> outptProfileFileExtendDTOS = outptProfileFileDAO.queryExtend(outptProfileFileExtendDTO);
        OutptProfileFileExtendDTO extendDTO = null;
        if (ListUtils.isEmpty(outptProfileFileExtendDTOS)) {
            //新增一条从表记录
            extendDTO = addExtend(outptProfileFileDTO);
        } else {
            //更新从表数据
            extendDTO = updateExtend(outptProfileFileDTO);
        }
        //只有在档案管理才进行档案主表修改
        if("0".equals(outptProfileFileDTO.getType())){
            outptProfileFileDAO.update(outptProfileFileDTO);
        }
        return extendDTO;
    }

    /**
     * @Method addExtend
     * @Desrciption 增加从表数据
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:02
     * @Return java.lang.Boolean
     **/
    OutptProfileFileExtendDTO addExtend(OutptProfileFileDTO outptProfileFileDTO) {
        OutptProfileFileExtendDTO outptProfileFileExtendDTO = new OutptProfileFileExtendDTO();
        //设置从表ID
        outptProfileFileExtendDTO.setId(SnowflakeUtils.getId());
        //设置从表档案ID
        outptProfileFileExtendDTO.setProfileId(outptProfileFileDTO.getId());
        //设置从表医院编码
        outptProfileFileExtendDTO.setHospCode(outptProfileFileDTO.getHospCode());
        //设置从表创建人ID
        outptProfileFileExtendDTO.setCrteId(outptProfileFileDTO.getCrteId());
        //设置从表创建人姓名
        outptProfileFileExtendDTO.setCrteName(outptProfileFileDTO.getCrteName());
        //设置从表创建时间
        outptProfileFileExtendDTO.setCrteTime(DateUtils.getNow());

        if (StringUtils.isEmpty(outptProfileFileDTO.getType())) {
            throw new AppException("请传建档类型(type)");
        }

        // 1.住院  2.门诊 3.建档
        if ("1".equals(outptProfileFileDTO.getType())) {
            //初始化设置累计门诊次数
            outptProfileFileExtendDTO.setTotalOut(0);
            //设置累计住院次数
            outptProfileFileExtendDTO.setTotalIn(1);
            //设置住院最后时间
            outptProfileFileExtendDTO.setInptLastVisitTime(DateUtils.getNow());
        } else if ("2".equals(outptProfileFileDTO.getType())) {
            //初始化设置住院次数
            outptProfileFileExtendDTO.setTotalIn(0);
            //设置累计门诊次数
            outptProfileFileExtendDTO.setTotalOut(1);
            //设置门诊最后时间
            outptProfileFileExtendDTO.setOutptLastVisitTime(DateUtils.getNow());
        }else{
            //初始化设置累计门诊次数
            outptProfileFileExtendDTO.setTotalOut(0);
            //初始化设置住院次数
            outptProfileFileExtendDTO.setTotalIn(0);
        }

        // 不管何种方式 只要是新增的从表数据都给他生成住院号和门诊号
        // 调用本地的规则服务，生成住院号和门诊号
        Map inMap = new HashMap();
        inMap.put("hospCode", outptProfileFileDTO.getHospCode());
        inMap.put("typeCode", "36");
        String inProfile = baseOrderRuleService_consumer.getOrderNo(inMap).getData();
        Map outMap = new HashMap();
        outMap.put("hospCode", outptProfileFileDTO.getHospCode());
        outMap.put("typeCode", "104");
        String outProfile = baseOrderRuleService_consumer.getOrderNo(outMap).getData();
//        String inProfile = syncOrderRuleService.getOrderNo("36").getData();
//        String outProfile = syncOrderRuleService.getOrderNo("104").getData();
        outptProfileFileExtendDTO.setInProfile(inProfile);
        outptProfileFileExtendDTO.setOutProfile(outProfile);
        Integer integer = outptProfileFileDAO.insertExtend(outptProfileFileExtendDTO);

        OutptProfileFileExtendDTO extendDTO = new OutptProfileFileExtendDTO();
        if (integer > 0) {
            extendDTO.setInProfile(inProfile);
            extendDTO.setOutProfile(outProfile);
            extendDTO.setProfileId(outptProfileFileExtendDTO.getProfileId());
        } else {
            throw new AppException("新增档案从表数据失败");
        }
        return extendDTO;
    }

    /**
     * @Method updateExtend
     * @Desrciption 更新从表信息
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/9/15 20:31
     * @Return java.lang.Boolean
     **/
    OutptProfileFileExtendDTO updateExtend(OutptProfileFileDTO outptProfileFileDTO) {
        OutptProfileFileExtendDTO outptProfileFileExtendDTO = new OutptProfileFileExtendDTO();

        outptProfileFileExtendDTO.setProfileId(outptProfileFileDTO.getId());

        outptProfileFileExtendDTO.setHospCode(outptProfileFileDTO.getHospCode());

        OutptProfileFileExtendDTO extendByProfileId = outptProfileFileDAO.getExtendByProfileId(outptProfileFileExtendDTO);

        if (StringUtils.isEmpty(outptProfileFileDTO.getType())) {
            throw new AppException("请传建档类型(type)");
        }

        if ("1".equals(outptProfileFileDTO.getType())) {
            //设置累计住院次数+1
            extendByProfileId.setTotalIn(extendByProfileId.getTotalIn() + 1);

            extendByProfileId.setInptLastVisitTime(DateUtils.getNow());
        } else if ("2".equals(outptProfileFileDTO.getType())) {
            //设置累计门诊次数+1
            extendByProfileId.setTotalOut(extendByProfileId.getTotalOut() + 1);

            extendByProfileId.setOutptLastVisitTime(DateUtils.getNow());
        }
        Integer integer = outptProfileFileDAO.updateExtend(extendByProfileId);

        OutptProfileFileExtendDTO extendDTO = new OutptProfileFileExtendDTO();
        if (integer > 0) {
            extendDTO.setInProfile(extendByProfileId.getInProfile());
            extendDTO.setOutProfile(extendByProfileId.getOutProfile());
            extendDTO.setProfileId(extendByProfileId.getProfileId());
        } else {
            throw new AppException("修改档案从表数据失败");
        }
        return extendDTO;
    }

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时只可判断档案表是否有该条身份证对应的档案信息
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:03
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean isCertNoExist(OutptProfileFileDTO outptProfileFileDTO) {
        return outptProfileFileDAO.isCertNoExist(outptProfileFileDTO) != null ? true: false;
    }

    /**
     * @Method getExtendByProfileId
     * @Desrciption 通过档案id和医院编码获取档案从表信息
     * @Param
     * [outptProfileFileExtendDTO]
     * @Author liaojunjie
     * @Date   2020/9/22 16:52
     * @Return cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO
     **/
    @Override
    public OutptProfileFileExtendDTO getExtendByProfileId(OutptProfileFileExtendDTO outptProfileFileExtendDTO) {
        return outptProfileFileDAO.getExtendByProfileId(outptProfileFileExtendDTO);
    }

    /**
     * @Method checkCard
     * @Desrciption 校验身份证格式
     * @Param
     * [cardNo]
     * @Author liaojunjie
     * @Date   2020/9/22 16:50
     * @Return boolean
     **/
    public boolean checkCard(String cardNo, String certCode) {
        if (StringUtils.isEmpty(cardNo)) {
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
        return outptProfileFileDAO.getAddressTree(outptProfileFileDTO);
    }

    @Override
    public List<OutptProfileFileDTO> getByIds(Map<String, Object> paramMap) {
        return outptProfileFileDAO.getByIds(paramMap);
    }
}
