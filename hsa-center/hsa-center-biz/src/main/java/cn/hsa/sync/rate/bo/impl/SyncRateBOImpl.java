package cn.hsa.sync.rate.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.rate.bo.SyncRateBO;
import cn.hsa.module.sync.rate.dao.SyncRateDAO;
import cn.hsa.module.sync.rate.dto.SyncRateDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;



/**
 * @PackageName: cn.hsa.base.rate.bo.impl
 * @Class_name: BaseRateBOImpl
 * @Description: 医嘱频率业务逻辑实现层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 15:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class SyncRateBOImpl extends HsafBO implements SyncRateBO {
    /*
     * 医嘱频率数据访问层
     */
    @Resource
    private SyncRateDAO syncRateDAO;
    /**
     * 单据号的生成规则服务层接口
     */
    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    /**
     * @Method getById()
     * @Description 查询医嘱频率
     * @Param map
     * 1、id：医嘱频率表主键ID
     * 2、hospCode 医院编码
     * @Author fuhui
     * @Date 2020/7/13 15:44
     * @Return SyncRateDTO
     **/

    @Override
    public SyncRateDTO getById(SyncRateDTO syncRateDTO) {
        return syncRateDAO.getById(syncRateDTO);
    }

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     * @Param 1、 SyncRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 15:44
     * @Return SyncRateDTO
     **/
    @Override
    public PageDTO queryPage(SyncRateDTO syncRateDTO) {
        // 设置分页信息
        PageHelper.startPage(syncRateDTO.getPageNo(), syncRateDTO.getPageSize());
        List<SyncRateDTO> syncRateDTOList = syncRateDAO.queryPage(syncRateDTO);
        return PageDTO.of(syncRateDTOList);
    }

    /**
     * @Method insert()
     * @Description 新增医嘱频率信息
     * @Param 1、SyncRateDTO：医嘱频率数据传输对象
     * @Author fuhui
     * @Date 2020/7/13 15:44
     * @Return boolean
     **/
    @Override
    public boolean insert(SyncRateDTO syncRateDTO) {
        WrapperResponse<String> rateCode = syncOrderRuleService.getOrderNo("19");
        syncRateDTO.setCode(rateCode.getData());
        //查询 数据表里面有多少条数据 然后自动生成顺序号
        int count = syncRateDAO.selectCount();
        syncRateDTO.setSeqNo(++count);
        syncRateDTO.setIsValid("1");
        return save(syncRateDTO);

    }

    /**
     * @Method update()
     * @Description 删除医嘱频率信息
     * @Param 1、SyncRateDTO：医嘱频率数据传输对象
     * @Author fuhui
     * @Date 2020/7/13 15:44
     * @Return boolean
     **/
    @Override
    public boolean update(SyncRateDTO syncRateDTO) {
        return save(syncRateDTO);
    }

    /**
     * @Method: 查询病区编码 提供给科室维护信息 住院时用
     * @Description:
     * @Param: hospCode医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/23 11:38
     * @Return:
     */
    @Override
    public boolean updateIsValid(SyncRateDTO syncRateDTO) {
        return syncRateDAO.updateIsValid(syncRateDTO) > 0;
    }


    @Override
    public List<SyncRateDTO> queryAll(SyncRateDTO syncRateDTO) {
        return syncRateDAO.queryAll(syncRateDTO);
    }

    /**
     * @Method: save()
     * @Description: 该方法主要用来保存医嘱频率信息维护修改和新增的信息
     * @Param: baseDeptDTO数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/24 14:17
     * @Return: baseDeptDTO数据传输对象集合
     */
    public boolean save(SyncRateDTO syncRateDTO) {
        List<SyncRateDTO> rateNameList = syncRateDAO.queryName(syncRateDTO);
        if (rateNameList.size() > 0 && rateNameList != null) {
            throw new RuntimeException("该频率名称已经存在:" + syncRateDTO.getName());
        }
        if (!StringUtils.isEmpty(syncRateDTO.getName())) {
            //生成拼音码
            syncRateDTO.setPym(PinYinUtils.toFirstPY(syncRateDTO.getName()));
            //生成五笔码
            syncRateDTO.setWbm(WuBiUtils.getWBCode(syncRateDTO.getName()));

        }
        //判断主键Id是否存在 如果存在 则是修改操作 否则就是新增操作
        if (StringUtils.isEmpty(syncRateDTO.getId())) {
            syncRateDTO.setId(SnowflakeUtils.getId());  // 设置主键id
            syncRateDTO.setCrteTime(DateUtils.getNow()); //设置操作时间
            return syncRateDAO.insert(syncRateDTO) > 0;
        } else {
            return syncRateDAO.update(syncRateDTO) > 0;
        }
    }
}
