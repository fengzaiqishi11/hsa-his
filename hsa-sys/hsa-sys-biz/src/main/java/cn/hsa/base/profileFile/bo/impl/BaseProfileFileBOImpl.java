package cn.hsa.base.profileFile.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.profileFile.bo.BaseProfileFileBO;
import cn.hsa.module.base.profileFile.dao.BaseProfileFileDAO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileExtendDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public Boolean save(OutptProfileFileDTO outptProfileFileDTO) {
        //校验身份证格式是否正确
        this.checkCard(outptProfileFileDTO.getCertNo());
        if (StringUtils.isNotEmpty(outptProfileFileDTO.getType())) {
            String type = outptProfileFileDTO.getType();
            if (StringUtils.isNotEmpty(type)) {
                if ("0".equals(type)) {
                    if (StringUtils.isEmpty(outptProfileFileDTO.getId())) {
                        //判断身份证是否存在
                        if (isCertNoExist(outptProfileFileDTO)) {
                            throw new AppException("身份证号重复，请重新输入");
                        }
                        return insert(outptProfileFileDTO) > 0;
                    } else {
                        return update(outptProfileFileDTO) > 0;
                    }
                } else if (type.equals("1") || type.equals("2")) {
                    //判断此身份证是否建档
                    OutptProfileFileDTO newFile = baseProfileFileDAO.isCertNoExistS(outptProfileFileDTO);
                    if (newFile != null) {
                        newFile.setType(type);
                        newFile.setHospCode(outptProfileFileDTO.getHospCode());
                        return update(outptProfileFileDTO) > 0;
                    } else {
                        return insert(outptProfileFileDTO) > 0;
                    }
                }
            }
        } else {
            throw new AppException("请传建档类型(type)");
        }
        return false;
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

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时只可判断档案表是否有该条身份证对应的档案信息
     * @Param [outptProfileFileDTO]
     * @Author liaojunjie
     * @Date 2020/8/27 13:03
     * @Return java.lang.Boolean
     **/
    public Boolean isCertNoExist(OutptProfileFileDTO outptProfileFileDTO) {
        return baseProfileFileDAO.isCertNoExist(outptProfileFileDTO) != null ? true : false;
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
    private Integer update(OutptProfileFileDTO outptProfileFileDTO) {
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
                //门诊
                param.setTotalOut(profileFileDTO.getTotalOut() == null ? 1 : profileFileDTO.getTotalOut() + 1);
                param.setOutptLastVisitTime(DateUtils.getNow());
                return baseProfileFileDAO.update(param);
            } else if ("1".equals(outptProfileFileDTO.getType()) && StringUtils.isNotEmpty(outptProfileFileDTO.getType())){
                // 档案表已存在病人类型
                if (StringUtils.isNotEmpty(profileFileDTO.getPatientCode())){
                    param.setPatientCode(profileFileDTO.getPatientCode());
                }
                if (StringUtils.isNotEmpty(profileFileDTO.getPreferentialTypeId())){
                    param.setPreferentialTypeId(profileFileDTO.getPreferentialTypeId());
                }
                //住院
                param.setTotalIn(profileFileDTO.getTotalIn() == null ? 1 : profileFileDTO.getTotalIn() + 1);
                param.setInptLastVisitTime(DateUtils.getNow());
                return baseProfileFileDAO.update(param);
            } else {
                // 只有档案管理(type=0)才修改档案的基本信息，门诊/住院不修改
                return baseProfileFileDAO.update(outptProfileFileDTO);
            }
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
    private Integer insert(OutptProfileFileDTO outptProfileFileDTO) {
        if(StringUtils.isEmpty(outptProfileFileDTO.getId())){
            outptProfileFileDTO.setId(SnowflakeUtils.getId());
        }
        outptProfileFileDTO.setIsValid(Constants.SF.S);
        //新增
        if ("2".equals(outptProfileFileDTO.getType()) && StringUtils.isNotEmpty(outptProfileFileDTO.getType())){
            //门诊
            outptProfileFileDTO.setTotalOut(1);
            outptProfileFileDTO.setOutptLastVisitTime(DateUtils.getNow());
        } else if ("3".equals(outptProfileFileDTO.getType()) && StringUtils.isNotEmpty(outptProfileFileDTO.getType())){
            //住院
            outptProfileFileDTO.setTotalIn(1);
            outptProfileFileDTO.setInptLastVisitTime(DateUtils.getNow());
        }
        outptProfileFileDTO.setPym(PinYinUtils.toFirstPY(outptProfileFileDTO.getName()));
        outptProfileFileDTO.setWbm(WuBiUtils.getWBCode(outptProfileFileDTO.getName()));
        return baseProfileFileDAO.insert(outptProfileFileDTO);
    }

    /**
     * @Method checkCard
     * @Desrciption 校验身份证格式
     * @Param [cardNo]
     * @Author liaojunjie
     * @Date 2020/9/22 16:50
     * @Return boolean
     **/
    private boolean checkCard(String cardNo) {
        if (StringUtils.isEmpty(cardNo)) {
            throw new AppException("身份证号为空，请检查后重新输入");
        } else {
            Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
            if (cardNo.length() == 15) {
                Matcher isNum = pattern.matcher(cardNo);
                if (!isNum.matches()) {
                    throw new AppException("身份证号15位时只能为纯数字，请检查后重新输入");
                }
            } else if (cardNo.length() == 18) {
                Matcher isNum = pattern.matcher(cardNo.substring(0, 17));
                Boolean flag = cardNo.substring(17, 18) == "X" ? true : false;
                if (!isNum.matches() && !flag) {
                    throw new AppException("身份证号18位时只能前17位为纯数字，最后1位为大写英文字母X，请检查后重新输入");
                }
            } else {
                throw new AppException("身份证号只能为15位或者18位，请检查后重新输入");
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
}
