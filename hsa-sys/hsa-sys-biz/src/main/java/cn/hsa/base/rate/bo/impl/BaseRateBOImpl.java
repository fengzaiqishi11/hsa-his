package cn.hsa.base.rate.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.rate.bo.BaseRateBO;
import cn.hsa.module.base.rate.dao.BaseRateDAO;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class BaseRateBOImpl extends HsafBO implements BaseRateBO {

    /**
     * 医嘱频率数据访问层
     */
    @Resource
    private BaseRateDAO baseRateDAO;

    /**
     * 单据号的生成规则服务层接口
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private RedisUtils redisUtils;


    /**
     * @Method getById()
     * @Description 查询医嘱频率
     * @Param map
     * 1、id：医嘱频率表主键ID
     * 2、hospCode 医院编码
     * @Author fuhui
     * @Date 2020/7/13 15:44
     * @Return baseRateDTO
     **/

    @Override
    public BaseRateDTO getById(BaseRateDTO baseRateDTO) {
        baseRateDTO.setIsValid(Constants.SF.S);
        return baseRateDAO.getById(baseRateDTO);
    }

    /**
     * @Method queryPage()
     * @Description 分页查医嘱频率信息
     * @Param 1、 baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 15:44
     * @Return baseRateDTO
     **/
    @Override
    public PageDTO queryPage(BaseRateDTO baseRateDTO) {
        // 设置分页信息
        PageHelper.startPage(baseRateDTO.getPageNo(), baseRateDTO.getPageSize());
        List<BaseRateDTO> baseRateDTOList = baseRateDAO.queryPage(baseRateDTO);
        return PageDTO.of(baseRateDTOList);
    }

    /**
     * @Method insert()
     * @Description 新增医嘱频率信息
     * @Param 1、baseRateDTO：医嘱频率数据传输对象
     * @Author fuhui
     * @Date 2020/7/13 15:44
     * @Return boolean
     **/
    @Override
    public boolean insert(BaseRateDTO baseRateDTO) {

        Map map = new HashMap();
        // 医嘱频率编码生成规则
        map.put("hospCode", baseRateDTO.getHospCode());
        map.put("typeCode", "19");
        WrapperResponse<String> rateCode = baseOrderRuleService.getOrderNo(map);
        baseRateDTO.setIsValid(Constants.SF.S);
        baseRateDTO.setCode(rateCode.getData());
        return save(baseRateDTO);

    }

    /**
     * @Method update()
     * @Description 删除医嘱频率信息
     * @Param 1、baseRateDTO：医嘱频率数据传输对象
     * @Author fuhui
     * @Date 2020/7/13 15:44
     * @Return boolean
     **/
    @Override
    public boolean update(BaseRateDTO baseRateDTO) {
        return save(baseRateDTO);
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
    public boolean updateIsValid(BaseRateDTO baseRateDTO) {


        String isValid = baseRateDTO.getIsValid();
        baseRateDTO.setIsValid(null);
        List<BaseRateDTO> baseRateDTOS = baseRateDAO.queryAll(baseRateDTO);
        baseRateDTO.setIsValid(isValid);
        baseRateDAO.updateIsValid(baseRateDTO) ;

        if(Constants.SF.F.equals(baseRateDTO.getIsValid())){
//            cacheOperate(null,baseRateDTOS,false);
        }else {
//            cacheOperate(null,baseRateDTOS,true);
        }
        return true;
    }

    /**
     * @Method queryAll()
     * @Description 查询全部医嘱频率
     * @Param 1、baseRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/

    @Override
    public List<BaseRateDTO> queryAll(BaseRateDTO baseRateDTO)
    {
        baseRateDTO.setIsValid(Constants.SF.S);
        return baseRateDAO.queryAll(baseRateDTO);
    }

    /**
     * @Method getByRateCode()
     * @Desrciption 根据频率编码查询医嘱频率信息
     * @Param hospCode医院编码, code:频率编码
     * @Author fuhui
     * @Date 2020/10/22 17:37
     * @Return 频率id
     **/
    @Override
    public String getByRateCode(BaseRateDTO baseRateDTO) {
        return baseRateDAO.getByRateCode(baseRateDTO);
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
    public boolean save(BaseRateDTO baseRateDTO) {
        List<BaseRateDTO> rateCodeList = baseRateDAO.queryCode(baseRateDTO);
        List<BaseRateDTO> rateNameList = baseRateDAO.queryName(baseRateDTO);
        if (rateCodeList.size() > 0 && rateCodeList != null) {
            throw new RuntimeException("该频率编码已经存在:" + baseRateDTO.getCode());
        }
        if (rateNameList.size() > 0 && rateNameList != null) {
            throw new RuntimeException("该频率名称已经存在:" + baseRateDTO.getName());
        }
        if (!StringUtils.isEmpty(baseRateDTO.getName())) {
            //生成拼音码
            baseRateDTO.setPym(PinYinUtils.toFirstPY(baseRateDTO.getName()));
            //生成五笔码
            baseRateDTO.setWbm(WuBiUtils.getWBCode(baseRateDTO.getName()));

        }
        //判断主键Id是否存在 如果存在 则是修改操作 否则就是新增操作
        if (StringUtils.isEmpty(baseRateDTO.getId())) {
            //查询 数据表里面有多少条数据 然后自动生成顺序号
            Integer count = baseRateDAO.selectCount(baseRateDTO.getHospCode());
            baseRateDTO.setSeqNo(++count);
            baseRateDTO.setId(SnowflakeUtils.getId());  // 设置主键id
            baseRateDTO.setCrteTime(DateUtils.getNow()); //设置操作时间
            baseRateDAO.insert(baseRateDTO);
            // 存入缓存
//            cacheOperate(baseRateDTO,null,true);
            return true;
        } else {
            baseRateDAO.update(baseRateDTO);
            // 缓存操作 -- 只有有效的时候才进行操作
            if(Constants.SF.S.equals(baseRateDTO.getIsValid())){
//                cacheOperate(baseRateDTO,null,true);
            }
            return true;
        }
    }

    /**
     * @Method cacheOperate
     * @Desrciption 缓存操作
     * @Param
     * [baseRateDTO, operation]
     * @Author liaojunjie
     * @Date   2021/1/14 16:05
     * @Return void
     **/
    public void cacheOperate(BaseRateDTO baseRateDTO,List<BaseRateDTO> baseRateDTOS, Boolean operation){
        if (baseRateDTO != null) {
            String key = StringUtils.createKey("rate", baseRateDTO.getHospCode(), baseRateDTO.getId());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, baseRateDTO);
            }
        }

        if (!ListUtils.isEmpty(baseRateDTOS)) {
            for (BaseRateDTO rate : baseRateDTOS) {
                String key = StringUtils.createKey("rate", rate.getHospCode(), rate.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, rate);
                }
            }
        }
    }
}
