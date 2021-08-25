package cn.hsa.base.bor.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.bo.BaseOrderRuleBO;
import cn.hsa.module.base.bor.dao.BaseOrderRuleDAO;
import cn.hsa.module.base.bor.dto.BaseOrderRuleDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Package_name: cn.hsa.base.bor.bo.impl
 * @Class_name: BaseOrderRuleBOImpl
 * @Description: 单据生成规则业务逻辑实现层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/07/13 20:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseOrderRuleBOImpl extends HsafBO implements BaseOrderRuleBO {
    /**
     * 单据生成规则数据库访问接口
     */
    @Resource
    private BaseOrderRuleDAO baseOrderRuleDAO;

    /**
     * 开启事务
     */
    @Resource
    private ResourceTransactionManager transactionManager;

    /**
     * @Method 创建锁
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/9 10:31
     * @Return
     **/
    private Lock lock = new ReentrantLock();

    /**
     * @Method getById()
     * @Description 根据主键ID查询
     *
     * @Param
     * 1、id：主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return BaseOrderRuleDTO
     **/
    @Override
    public BaseOrderRuleDTO getById(Long id) {
        return baseOrderRuleDAO.getById(id);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseOrderRuleDTO baseOrderRuleDTO) {
        baseOrderRuleDTO.setIsVlid("1");
        // 设置分页信息
        PageHelper.startPage(baseOrderRuleDTO.getPageNo(), baseOrderRuleDTO.getPageSize());

        // 查询所有
        List<BaseOrderRuleDTO> baseOrderRuleDTOList = baseOrderRuleDAO.queryAll(baseOrderRuleDTO);

        // 返回分页结果
        return PageDTO.of(baseOrderRuleDTOList);
    }

    /**
     * @Method insert()
     * @Description 新增单条
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return baseOrderRuleDTO
     **/
    @Override
    public boolean insert(BaseOrderRuleDTO baseOrderRuleDTO) {
        BaseOrderRuleDTO baseOrderRule = new BaseOrderRuleDTO();
        baseOrderRule.setHospCode(baseOrderRuleDTO.getHospCode());
        baseOrderRule.setTypeCode(baseOrderRuleDTO.getTypeCode());
        List<BaseOrderRuleDTO> baseOrderRules = baseOrderRuleDAO.queryAll(baseOrderRule);
        if (!(ListUtils.isEmpty(baseOrderRules))) {
            throw new AppException("单据类型重复");
        }
        baseOrderRuleDTO.setId(SnowflakeUtils.getId());
        baseOrderRuleDTO.setCrteTime(new Date());
        return baseOrderRuleDAO.insert(baseOrderRuleDTO) > 0;
    }

    /**
     * @Method update()
     * @Description 修改单条
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return baseOrderRuleDTO
     **/
    @Override
    public boolean update(BaseOrderRuleDTO baseOrderRuleDTO) {
        return baseOrderRuleDAO.update(baseOrderRuleDTO) > 0;
    }

    /**
     * @Method updateIsValid()
     * @Description 单个或者批量更改有效标识
     *
     * @Param
     * 1、ids：主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return boolean
     **/
    @Override
    public boolean delete(List ids) {
        return baseOrderRuleDAO.delete(ids) > 0;
    }

    /**
     * @Method 根据医院编码、单据类型获取下一个单据号
     * @Description
     *
     * @Param
     * 1、hospCode：医院编码
     * 2、typeCode：单据类型
     *
     * @Author zhongming
     * @Date 2020/7/13 21:23
     * @Return 下一个单据号
     **/
    @Override
    public String updateOrderNo(String hospCode, String typeCode) {
        // 获取单据生成规则
        BaseOrderRuleDTO dto = baseOrderRuleDAO.getByHcAndTc(hospCode, typeCode);

        // 未获取到单据生成规则
        if (dto == null) {
            throw new AppException("获取单据生成规则失败：医院编码【" + hospCode + "】，单据类型【" + typeCode + "】，未查询到有效数据");
        }

        // 新单号
        String orderNo = "";

        // 0 业务不连续单号（创建独立事务，独立事务提交）
        if ("0".equals(dto.getIsContinuity())) {
            TransactionStatus status = null;
            try {
                // 开启独立新事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                status = transactionManager.getTransaction(def);

                // 获取单据生成规则（行锁）
                dto = baseOrderRuleDAO.getByRowLock(hospCode, typeCode);

                // 独立事务中获取订单号
                orderNo = genOrderNo(dto);

                // 提交独立事务
                transactionManager.commit(status);
            } catch (Exception e) {
                if (status != null) {
                    transactionManager.rollback(status);
                }
                throw new AppException("获取单据号失败：医院编码【" + hospCode + "】，单据类型【" + typeCode + "】，" + e.getMessage());
            }
        }
        // 1 业务连续单号（共用业务事务）
        else {
            // 非独立事务中获取订单号
            orderNo = genOrderNo(dto);
        }
        return dto.getPrefix() + orderNo;
    }

    /**
     * @Method 根据医院编码、单据类型生成下一个单据号
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/7/13 23:01
     * @Return 
     **/

    private String genOrderNo(BaseOrderRuleDTO dto) {
        String format = dto.getFormat();

        // 未配置单据生成规则
        if (StringUtils.isEmpty(format)) {
            throw new AppException("获取单据号失败：单据生成规则为空");
        }

        // 匹配时间格式
        String dateFormat = "";
        if (format.startsWith("yyyymmddhhmmss")) {
            dateFormat = "yyyyMMddHHmmss";
        } else if (format.startsWith("yyyymmddhhmm")) {
            dateFormat = "yyyyMMddHHmm";
        } else if (format.startsWith("yyyymmddhh")) {
            dateFormat = "yyyyMMddHH";
        } else if (format.startsWith("yyyymmdd")) {
            dateFormat = "yyyyMMdd";
        } else if (format.startsWith("yyyymm")) {
            dateFormat = "yyyyMM";
        } else if (format.startsWith("yyyy")) {
            dateFormat = "yyyy";
        }

        lock.lock();
        try {
            // 新单据号
            String orderNo = "";
            String seq = "";
            // 不是按照时间格式来进行格式化
            if (StringUtils.isEmpty(dateFormat)) {
                String currNo = StringUtils.isEmpty(dto.getCurrNo()) ? format : dto.getCurrNo();
                orderNo = String.format("%0"+ currNo.length() +"d", Integer.parseInt(currNo) + 1);
            } else {
                String ymd = DateUtils.format(dateFormat);
                //是否有值
                if(StringUtils.isNotEmpty(dto.getCurrNo())){
                    String currNoYmd = dto.getCurrNo().substring(0, dateFormat.length());
                    //是否当天
                    if(!ymd.equals(currNoYmd)){
                        seq = format.substring(dateFormat.length());
                    }else{
                        seq = dto.getCurrNo().substring(dateFormat.length());
                    }
                }else{
                    seq = format.substring(dateFormat.length());
                }
                //判断当前单号是否为当天
                orderNo = ymd + String.format("%0"+ seq.length() +"d", Integer.parseInt(seq) + 1);
            }

            if (StringUtils.isEmpty(orderNo)) {
                throw new AppException("获取单据号失败：生成单据号为空");
            }

            // 更新最新单据号
            baseOrderRuleDAO.updateCurrNo(dto.getId(), orderNo);
            return orderNo;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}

