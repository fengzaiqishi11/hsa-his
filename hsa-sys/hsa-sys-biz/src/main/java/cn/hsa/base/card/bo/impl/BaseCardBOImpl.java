package cn.hsa.base.card.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.card.bo.BaseCardBO;
import cn.hsa.module.base.card.dao.BaseCardDAO;
import cn.hsa.module.base.card.dto.BaseCardDTO;
import cn.hsa.module.base.card.dto.BaseCardRechargeChangeDTO;
import cn.hsa.module.base.card.entity.BaseCardChangeDO;
import cn.hsa.module.base.card.entity.BaseCardRechargeChangeDO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.card.bo.impl
 * @Class_name: BaseCardBOImpl
 * @Describe: 一卡通bo实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-05-11 16:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseCardBOImpl extends HsafBO implements BaseCardBO {

    @Resource
    private BaseCardDAO baseCardDAO;

    /**
     * 值域码表服务
     */
    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Menthod: queryCardPage
     * @Desrciption: 分页查询一卡通列表数据
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: PageDTO
     **/
    @Override
    public PageDTO queryCardPage(BaseCardDTO baseCardDTO) {
        PageHelper.startPage(baseCardDTO.getPageNo(), baseCardDTO.getPageSize());
        List<BaseCardDTO> list = baseCardDAO.queryCardPage(baseCardDTO);
        return PageDTO.of(list);
    }

    /**
     * @Menthod: getCardByProId
     * @Desrciption: 根据档案id查询出所有发卡记录
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: List<BaseCardDTO>
     **/
    @Override
    public List<BaseCardDTO> getCardByProId(BaseCardDTO baseCardDTO) {
        List<BaseCardDTO> list = baseCardDAO.getCardByProId(baseCardDTO);
        return list;
    }

    /**
     * @Menthod: saveCard
     * @Desrciption: 发卡
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    @Override
    public Boolean saveCard(BaseCardDTO baseCardDTO) {
        // 校验同类型卡号不能相同
        if (StringUtils.isEmpty(baseCardDTO.getCardTypeCode())) {
            throw new RuntimeException("未选择IC卡类型");
        }
        if (StringUtils.isEmpty(baseCardDTO.getCardNo())) {
            throw new RuntimeException("未输入IC卡卡号");
        }

        Map map = new HashMap<>();
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setHospCode(baseCardDTO.getHospCode());
        sysCodeDetailDTO.setCode("YKTLX");
        sysCodeDetailDTO.setValue(baseCardDTO.getCardTypeCode());
        map.put("hospCode", baseCardDTO.getHospCode());
        map.put("sysCodeDetailDTO", sysCodeDetailDTO);
        SysCodeDetailDTO result = sysCodeService_consumer.getCodeDetailByValue(map).getData();

        BaseCardDTO cardDTO = baseCardDAO.queryCardByNo(baseCardDTO);
        if (cardDTO != null) {
            throw new RuntimeException(result.getName() + "卡号【" + baseCardDTO.getCardNo() + "】已经存在，请更换");
        }

        if (StringUtils.isEmpty(baseCardDTO.getId())) {
            baseCardDTO.setId(SnowflakeUtils.getId());
        }
        baseCardDTO.setStatusCode(Constants.YKTZT.ZC);
        return baseCardDAO.insertCard(baseCardDTO) > 0;
    }

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 状态更改---1挂失，2冻结，3注销，4确认挂失，5作废
     * @Param: baseCardDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-11 16:20
     * @Return: Boolean
     **/
    @Override
    public Boolean updateStatusCode(BaseCardDTO baseCardDTO, BaseCardChangeDO baseCardChangeDO) {
        baseCardChangeDO.setId(SnowflakeUtils.getId());
        int temp = baseCardDAO.insertBaseCardChange(baseCardChangeDO); // 一卡通异动表记录
        if (temp == 0) {
            throw new AppException("保存一卡通异动数据异常，请联系管理员");
        }
        return baseCardDAO.updateStatusCode(baseCardDTO) > 0;
    }

    /**
     * @Menthod: resetPwd
     * @Desrciption: 密码重置
     * @Param: baseCardDTO
     * @Author: caoliang
     * @Email: caoliang@powersi.com.cn
     * @Date: 2021-06-03 10:20
     * @Return: Boolean
     **/
    @Override
    public Boolean updatePwd(BaseCardDTO baseCardDTO) {
        return baseCardDAO.updatePwd(baseCardDTO) > 0;
    }


    /**
     * @Menthod: saveInCharge
     * @Desrciption: 一卡通充值
     * @Param: cardRechargeChangeDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-06 11:29
     * @Return: Boolean
     **/
    @Override
    public Boolean saveInCharge(BaseCardRechargeChangeDO cardRechargeChangeDO) {
        Map param =new HashMap();
        param.put("hospCode",cardRechargeChangeDO.getHospCode());
        param.put("profileId",cardRechargeChangeDO.getProfileId());
        param.put("id",cardRechargeChangeDO.getCardId());
        BaseCardRechargeChangeDO beforeChange =baseCardDAO.findCardRechargeInfoById(param);
        if (beforeChange!=null){
            cardRechargeChangeDO.setStartBalance(beforeChange.getEndBalance());
            BigDecimal end= BigDecimalUtils.add(beforeChange.getEndBalance(),cardRechargeChangeDO.getPrice());
            cardRechargeChangeDO.setEndBalance(end);
        }else {
            cardRechargeChangeDO.setStartBalance(new BigDecimal(0));
            cardRechargeChangeDO.setEndBalance(cardRechargeChangeDO.getPrice());
        }
        if (StringUtils.isEmpty(cardRechargeChangeDO.getId())) {
            cardRechargeChangeDO.setId(SnowflakeUtils.getId());
        }
        if (baseCardDAO.insertBaseCardRechargeChange(cardRechargeChangeDO) > 0) {
            BaseCardDTO baseCardDTO =new BaseCardDTO();
            baseCardDTO.setHospCode(cardRechargeChangeDO.getHospCode());
            baseCardDTO.setId(cardRechargeChangeDO.getCardId());
            baseCardDTO.setAccountBalance(cardRechargeChangeDO.getEndBalance());
            // 更新一卡通表账户余额
            baseCardDAO.updateCardAccountBalance(baseCardDTO);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Menthod: saveCardRefund
     * @Desrciption: 一卡通退费
     * @Param: cardRechargeChangeDO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2021-08-06 11:32
     * @Return: Boolean
     **/
    @Override
    public Boolean saveCardRefund(BaseCardRechargeChangeDO cardRechargeChangeDO) {
        Map param =new HashMap();
        param.put("hospCode",cardRechargeChangeDO.getHospCode());
        param.put("profileId",cardRechargeChangeDO.getProfileId());
        param.put("id",cardRechargeChangeDO.getCardId());
        BaseCardRechargeChangeDO beforeChange =baseCardDAO.findCardRechargeInfoById(param);
        if (beforeChange!=null){
            cardRechargeChangeDO.setStartBalance(beforeChange.getEndBalance());
            if (BigDecimalUtils.greater(beforeChange.getEndBalance(),cardRechargeChangeDO.getPrice()) || BigDecimalUtils.equalTo(beforeChange.getEndBalance(),cardRechargeChangeDO.getPrice())) {
                BigDecimal end = BigDecimalUtils.subtract(beforeChange.getEndBalance(), cardRechargeChangeDO.getPrice());
                cardRechargeChangeDO.setEndBalance(end);
                cardRechargeChangeDO.setPrice(BigDecimalUtils.negate(cardRechargeChangeDO.getPrice()));
            }else {
                throw new AppException("退费金额不能大于一卡通余额！");
            }
        }else {
            throw new AppException("一卡通余额为0，不能进行退费！");
        }
        if (StringUtils.isEmpty(cardRechargeChangeDO.getId())) {
            cardRechargeChangeDO.setId(SnowflakeUtils.getId());
        }
        if (baseCardDAO.insertBaseCardRechargeChange(cardRechargeChangeDO) > 0) {
            BaseCardDTO baseCardDTO =new BaseCardDTO();
            baseCardDTO.setId(cardRechargeChangeDO.getCardId());
            baseCardDTO.setAccountBalance(cardRechargeChangeDO.getEndBalance());
            baseCardDTO.setHospCode(cardRechargeChangeDO.getHospCode());
            // 更新一卡通表账户余额
            baseCardDAO.updateCardAccountBalance(baseCardDTO);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BaseCardRechargeChangeDTO getRechargeChangeInfo(Map map) {
        return baseCardDAO.findCardRechargeInfoById(map);
    }

    @Override
    public PageDTO getRechargeChangeInfoList(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO) {
        PageHelper.startPage(baseCardRechargeChangeDTO.getPageNo(), baseCardRechargeChangeDTO.getPageSize());
        return PageDTO.of(baseCardDAO.findCardRechargeInfoList(baseCardRechargeChangeDTO));
    }

    /**
     * @Menthod: queryPaitentPage
     * @Desrciption: 分页查询档案信息
     * @Param: baseCardDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-07 16:25
     * @Return: PageDTO
     **/
    @Override
    public PageDTO queryPaitentPage(BaseCardDTO baseCardDTO) {
        PageHelper.startPage(baseCardDTO.getPageNo(), baseCardDTO.getPageSize());
        List<BaseCardDTO> list = baseCardDAO.queryPaitentPage(baseCardDTO);
        return PageDTO.of(list);
    }

    /**
     * @Menthod: queryPaitentCardRechargeInfoList
     * @Desrciption: 分页查询一卡通异动信息
     * @Param: baseCardRechargeChangeDTO
     * @Author: liuliyun
     * @Email: liuliyun@powersi.com
     * @Date: 2021-08-10 16:30
     * @Return: PageDTO
     **/
    @Override
    public PageDTO queryPaitentCardRechargeInfoList(BaseCardRechargeChangeDTO baseCardRechargeChangeDTO) {
        PageHelper.startPage(baseCardRechargeChangeDTO.getPageNo(), baseCardRechargeChangeDTO.getPageSize());
        List<BaseCardRechargeChangeDTO> list = baseCardDAO.queryPaitentCardRechargeInfoList(baseCardRechargeChangeDTO);
        return PageDTO.of(list);
    }
}
