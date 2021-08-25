package cn.hsa.base.bp.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bp.bo.BaseProductBO;
import cn.hsa.module.base.bp.dao.BaseProductDAO;
import cn.hsa.module.base.bp.dto.BaseProductDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * @Package_name: cn.hsa.base.bmm.bo.impl
 * @Class_name: BaseProductManagementBOImpl
 * @Describe: 生产企业信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseProductBOImpl extends HsafBO implements BaseProductBO {

    /**
    * @Menthod
    * @Desrciption 注入Dao层对象
     * @param null
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return
    **/
    @Resource
    BaseProductDAO baseProductDAO;

    /**
     * 注入单据规则service层
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询生产企业信息
     * @param baseProductDTO  主键ID 医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return cn.hsa.module.base.bmm.dto.BaseProductDTO
    **/
    @Override
    public BaseProductDTO getById(BaseProductDTO baseProductDTO) {
        return this.baseProductDAO.getById(baseProductDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部生产企业信息
     * @param baseProductDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/14 21:02
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseProductDTO>
    **/
    @Override
    public List<BaseProductDTO> queryAll(BaseProductDTO baseProductDTO) {
        return this.baseProductDAO.queryPage(baseProductDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象分页查询生产企业信息
     * @param BaseProductDTO 生产企业信息数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:42
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPage(BaseProductDTO BaseProductDTO) {
        // 设置分页信息
        PageHelper.startPage(BaseProductDTO.getPageNo(),BaseProductDTO.getPageSize());

        List<BaseProductDTO> baseProductDTOList = baseProductDAO.queryPage(BaseProductDTO);
        return  PageDTO.of(baseProductDTOList);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改生产企业
     * @param BaseProductDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:38
     * @Return boolean
     **/
    @Override
    public boolean save(BaseProductDTO BaseProductDTO){

        //判断名字不为空，根据名字生成五笔码和拼音码
        if (!StringUtils.isEmpty(BaseProductDTO.getName())){

                BaseProductDTO.setPym(PinYinUtils.toFirstPY(BaseProductDTO.getName()));

                BaseProductDTO.setWbm(WuBiUtils.getWBCode(BaseProductDTO.getName()));
        }
        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(BaseProductDTO.getId())){

            HashMap map = new HashMap();
            map.put("hospCode",BaseProductDTO.getHospCode());
            map.put("typeCode", Constants.ORDERRULE.SCQY);
            WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);

            BaseProductDTO.setCode(orderNo.getData());

            BaseProductDTO.setId(SnowflakeUtils.getId());

            BaseProductDTO.setCrteTime(DateUtils.getNow());

            return this.baseProductDAO.insert(BaseProductDTO)>0;
        }else {
            return this.baseProductDAO.update(BaseProductDTO)>0;
        }
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个生产企业信息
     * @param baseProductDTO
    * @Author xingyu.xie
    * @Date   2020/7/17 15:43
    * @Return boolean
    **/
    @Override
    public boolean updateStatus(BaseProductDTO baseProductDTO) {
        return this.baseProductDAO.updateStatus(baseProductDTO)>0;
    }
}
