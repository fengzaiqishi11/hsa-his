package cn.hsa.base.bpft.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bfc.bo.BaseFinanceClassifyBO;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bpft.bo.BasePreferentialBO;
import cn.hsa.module.base.bpft.dao.BasePreferentialDAO;
import cn.hsa.module.base.bpft.dto.BasePreferentialDTO;
import cn.hsa.module.base.bpft.dto.BasePreferentialTypeDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.bpft.bo.impl
 * @Class_name: BasePreferentialBoImpl
 * @Describe: 优惠配置业务逻辑实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/7/13 10:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BasePreferentialBOImpl extends HsafBO implements BasePreferentialBO {

    /**
     * 优惠配置信息数据库访问接口
     */
    @Resource
    BasePreferentialDAO basePreferentialDao;

    /**
     * 注入单据规则service层
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    //财务分类
    @Resource
    private BaseFinanceClassifyBO baseFinanceClassifyBO;

    /**
     * @Menthod getById()
     * @Desrciption
     * @Param 1. [map]
     * @Author jiahong.yang
     * @Date 2020/7/13 10:36
     * @Return cn.hsa.module.base.bpft.dto.BasePreferentialDTO
     **/
    @Override
    public BasePreferentialDTO getById(Map<String, Object> map) {
        return basePreferentialDao.getById(map);
    }


    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件分页查询优惠配置信息
     * @Param [1. BasePreferentialDTO]
     * @Author jiahong.yang
     * @Date 2020/7/14 14:38
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(BasePreferentialDTO basePreferentialDTO) {
        // 设置分页信息
        PageHelper.startPage(basePreferentialDTO.getPageNo(), basePreferentialDTO.getPageSize());

        // 根据条件查询所有
        List<BasePreferentialDTO> basePreferentialDTOS = basePreferentialDao.queryPage(basePreferentialDTO);

        return PageDTO.of(basePreferentialDTOS);
    }

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部优惠配置信息
     * @Param [basePreferentialDTO]
     * @Author jiahong.yang
     * @Date 2020/8/19 17:25
     * @Return java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
     **/
    @Override
    public List<BasePreferentialDTO> queryAll(BasePreferentialDTO basePreferentialDTO) {
        return basePreferentialDao.queryPage(basePreferentialDTO);
    }

    /**
     * @Method: queryPreferentials
     * @Description: 根据主表获取记录
     * @Param: [basePreferentialTypeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/7 12:02
     * @Return: java.util.List<cn.hsa.module.base.bpft.dto.BasePreferentialDTO>
     **/
    @Override
    public List<BasePreferentialDTO> queryPreferentials(BasePreferentialTypeDTO basePreferentialTypeDTO) {
        return basePreferentialDao.queryPreferentials(basePreferentialTypeDTO);
    }

    /**
     * @Menthod insert()
     * @Desrciption 新增一条优惠配置信息
     * @Param [1. basePreferentialDTO]
     * @Author jiahong.yang
     * @Date 2020/7/14 16:26
     * @Return boolean
     **/
    @Override
    public boolean insert(BasePreferentialDTO basePreferentialDTO) {
        basePreferentialDTO.setCrteTime(DateUtils.getNow());
        return basePreferentialDao.insert(basePreferentialDTO) > 0;
    }

    /**
     * @Menthod delete()
     * @Desrciption 根据id删除优惠配置信息
     * @Param [1. map]
     * @Author jiahong.yang
     * @Date 2020/7/14 16:26
     * @Return boolean
     **/
    @Override
    public boolean delete(BasePreferentialDTO basePreferentialDTO) {
        if (ListUtils.isEmpty(basePreferentialDTO.getIds())) {
            throw new AppException("所选删除数据为空");
        }
        return basePreferentialDao.delete(basePreferentialDTO) > 0;
    }

    /**
     * @Menthod update()
     * @Desrciption 变价修改优惠配置信息
     * @Param [1. basePreferentialDTO]
     * @Author jiahong.yang
     * @Date 2020/7/14 16:51
     * @Return boolean
     **/
    @Override
    public boolean update(BasePreferentialDTO basePreferentialDTO) {
        return basePreferentialDao.update(basePreferentialDTO) > 0;
    }

    /**
     * @Menthod queryBsplTypePage()
     * @Desrciption 分页查询优惠类型
     * @Param [1. basePreferentialTypeDTO]
     * @Author jiahong.yang
     * @Date 2020/8/6 9:48
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryBsplTypePage(BasePreferentialTypeDTO basePreferentialTypeDTO) {
        // 设置分页信息
        PageHelper.startPage(basePreferentialTypeDTO.getPageNo(), basePreferentialTypeDTO.getPageSize());

        // 根据条件查询所有
        List<BasePreferentialTypeDTO> basePreferentialDTOS = basePreferentialDao.queryBsplTypePage(basePreferentialTypeDTO);

        return PageDTO.of(basePreferentialDTOS);
    }

    @Override
    public List<BasePreferentialTypeDTO> queryBsplTypeAll(BasePreferentialTypeDTO basePreferentialTypeDTO) {
        // 根据条件查询所有
        List<BasePreferentialTypeDTO> basePreferentialDTOS = basePreferentialDao.queryBsplTypeAll(basePreferentialTypeDTO);
        return basePreferentialDTOS;
    }

    /**
     * @Menthod saveBsplType()
     * @Desrciption 增加和修改优惠类型
     * @Param [1. basePreferentialTypeDTO]
     * @Author jiahong.yang
     * @Date 2020/8/6 13:46
     * @Return boolean
     **/
    @Override
    public boolean saveBsplType(BasePreferentialTypeDTO basePreferentialTypeDTO) {
        if (StringUtils.isEmpty(basePreferentialTypeDTO.getName())) {
            throw new AppException("名称不能为空");
        }

        if (basePreferentialDao.queryNameIsExist(basePreferentialTypeDTO) > 0) {
            throw new AppException("名称重复");
        }

        //根据有没有id判断为增加还是修改
        if (StringUtils.isEmpty(basePreferentialTypeDTO.getId())) {
            //获取编码
            HashMap map = new HashMap();
            map.put("hospCode", basePreferentialTypeDTO.getHospCode());
            map.put("typeCode", "27");
            WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
            basePreferentialTypeDTO.setCode(orderNo.getData());
            //注入id
            basePreferentialTypeDTO.setId(SnowflakeUtils.getId());
            basePreferentialTypeDTO.setCrteTime(DateUtils.getNow());
            basePreferentialDao.insertBsplType(basePreferentialTypeDTO);
        } else {
            List<BasePreferentialDTO> list = basePreferentialTypeDTO.getBasePreferentialTypeDOList();
            basePreferentialDao.updateBsplType(basePreferentialTypeDTO);
            if (list.size() > 0) {
                //优惠配置信息的优惠类型编码全部修改为优惠类型修改后的编码
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setPfTypeCode(basePreferentialTypeDTO.getCode());
                    list.get(i).setHospCode(basePreferentialTypeDTO.getHospCode());
                }
                basePreferentialDao.updateAll(list);
            }
        }
        return true;
    }

    /**
     * @Menthod updateBsplTypeStatus()
     * @Desrciption 批量删除优惠类型
     * @Param [1. basePreferentialTypeDTO]
     * @Author jiahong.yang
     * @Date 2020/8/6 16:46
     * @Return boolean
     **/
    @Override
    public boolean updateBsplTypeStatus(BasePreferentialTypeDTO basePreferentialTypeDTO) {
        if (ListUtils.isEmpty(basePreferentialTypeDTO.getIds())) {
            throw new AppException("所选删除数据为空");
        }
        return basePreferentialDao.updateBsplTypeStatus(basePreferentialTypeDTO) > 0;
    }

    /**
     * @Method: calculatPreferential
     * @Description: 优惠处理 type->0或空:门诊,1:住院
     * 1.校验参数
     * 2.获取财务分类编码
     * 3.获取优惠配置明细
     * 4.判断是门诊还是住院
     *   按比列：优惠金额：总金额*优惠比例   优惠后总金额：总金额-优惠金额
     *   按金额  优惠金额：优惠金额         优惠后总金额：总金额-优惠金额
     *   不优惠  优惠金额：0               优惠后总金额：总金额-优惠金额
     * @Param: [list]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/10 10:38
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<Map<String, Object>> calculatPreferential(List<Map<String, Object>> costList) {
        if (ListUtils.isEmpty(costList)) {
            throw new AppException("优惠参数为空");
        }

        //返回集合
        List<Map<String, Object>> returnList = new ArrayList<>();

        for (Map<String, Object> map : costList) {

            String hospCode = MapUtils.get(map, "hospCode");
            String preferentialTypeId = MapUtils.get(map, "preferentialTypeId");
            String itemId = MapUtils.get(map, "itemId");
            String bfcId = MapUtils.get(map, "bfcId");
            String type = MapUtils.get(map, "type");//0或空:门诊,1:住院
            BigDecimal totalPrice = MapUtils.get(map, "totalPrice");

            if (StringUtils.isEmpty(hospCode)) {
                throw new AppException("医院编码为空");
            }
            if (StringUtils.isEmpty(preferentialTypeId)) {
                throw new AppException("优惠类型ID为空");
            }
            if (StringUtils.isEmpty(bfcId) && StringUtils.isEmpty(itemId)) {
                throw new AppException("财务分类ID、项目ID需要有一项不为空");
            }
            if (totalPrice == null) {
                throw new AppException("总金额为空");
            }

            //默认优惠0元
            map.put("preferentialPrice", BigDecimal.valueOf(0));
            map.put("realityPrice", totalPrice);

            //根据主键获取财务分类编码
            BaseFinanceClassifyDTO baseFinanceClassify = new BaseFinanceClassifyDTO();
            if (!StringUtils.isEmpty(bfcId)) {
                BaseFinanceClassifyDTO baseFinanceClassifyDTO = new BaseFinanceClassifyDTO();
                baseFinanceClassifyDTO.setHospCode(hospCode);
                baseFinanceClassifyDTO.setId(bfcId);
                baseFinanceClassify = baseFinanceClassifyBO.getById(baseFinanceClassifyDTO);
                if (baseFinanceClassify == null) {
                    throw new AppException("根据ID获取财务分类信息失败");
                }
            }

            //根据preferentialTypeId查询出base_preferential明细数据
            BasePreferentialTypeDTO basePreferentialTypeDTO = new BasePreferentialTypeDTO();
            basePreferentialTypeDTO.setHospCode(hospCode);
            basePreferentialTypeDTO.setId(preferentialTypeId);
            List<BasePreferentialDTO> preferentialTypeList = queryPreferentials(basePreferentialTypeDTO);

            if (!ListUtils.isEmpty(preferentialTypeList)) {
                //把preferentialList按照优惠类型type_code分组 1 财务分类 2药品 3项目
                for (BasePreferentialDTO dto : preferentialTypeList) {
                    //是否属于财务分类优惠，还是项目药品优惠
                    if (("1".equals(dto.getTypeCode()) && dto.getBizCode().equals(baseFinanceClassify.getCode()))
                            || (itemId.equals(dto.getItemId()))) {
                        //优惠总金额：项目总金额 * 优惠比列
                        BigDecimal preferentialPrice = BigDecimal.valueOf(0);
                        //优惠后总金额：项目总金额 - 优惠总金额
                        BigDecimal realityPrice = BigDecimal.valueOf(0);
                        if ("1".equals(type)) {//住院优惠
                            if ("1".equals(dto.getInCode())) {//按比列
                                preferentialPrice = BigDecimalUtils.multiply((new BigDecimal(1).subtract(dto.getInScale())), totalPrice);
                                realityPrice = BigDecimalUtils.subtract(totalPrice, preferentialPrice);

                                map.put("preferentialPrice", preferentialPrice);
                                map.put("realityPrice", realityPrice);
                                break;
                            } else if ("2".equals(dto.getInCode())) {//按金额
                                preferentialPrice = dto.getInScale();
                                realityPrice = BigDecimalUtils.subtract(totalPrice, preferentialPrice);

                                map.put("preferentialPrice", preferentialPrice);
                                map.put("realityPrice", realityPrice);
                                break;
                            } else { //不优惠
                                break;
                            }
                        } else { //门诊优惠
                            if ("1".equals(dto.getOutCode())) {//按比列
                                realityPrice = BigDecimalUtils.multiply(dto.getOutScale(), totalPrice);
                                // 四舍五入保留两位小数
                                realityPrice = realityPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                                preferentialPrice = BigDecimalUtils.subtract(totalPrice, realityPrice);

                                map.put("preferentialPrice", preferentialPrice);
                                map.put("realityPrice", realityPrice);
                                break;
                            } else if ("2".equals(dto.getOutCode())) {//按金额
                                preferentialPrice = dto.getOutScale();
                                realityPrice = BigDecimalUtils.subtract(totalPrice, preferentialPrice);

                                map.put("preferentialPrice", preferentialPrice);
                                map.put("realityPrice", realityPrice);
                                break;
                            } else { //不优惠
                                break;
                            }
                        }
                    }
                }
            }
            if (!MapUtils.isEmpty(map)) {
                returnList.add(map);
            }
        }
        return returnList;
    }
}
