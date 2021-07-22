package cn.hsa.sync.product.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.product.bo.SyncProductBO;
import cn.hsa.module.sync.product.dao.SyncProductDAO;
import cn.hsa.module.sync.product.dto.SyncProductDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
public class SyncProductBOImpl extends HsafBO implements SyncProductBO {

    /**
    * @Menthod
    * @Desrciption 注入Dao层对象
     * @param null
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return
    **/
    @Resource
    SyncProductDAO syncProductDAO;

    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询生产企业信息
     * @param syncProductDTO  主键ID 医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return cn.hsa.module.base.bmm.dto.CenterProductDTO
    **/
    @Override
    public SyncProductDTO getById(SyncProductDTO syncProductDTO) {
        return this.syncProductDAO.getById(syncProductDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部生产企业信息
     * @param syncProductDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/14 21:02
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.CenterProductDTO>
    **/
    @Override
    public List<SyncProductDTO> queryAll(SyncProductDTO syncProductDTO) {
        return this.syncProductDAO.queryPage(syncProductDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象分页查询生产企业信息
     * @param SyncProductDTO 生产企业信息数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:42
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPage(SyncProductDTO SyncProductDTO) {
        // 设置分页信息
        PageHelper.startPage(SyncProductDTO.getPageNo(), SyncProductDTO.getPageSize());

        List<SyncProductDTO> syncProductDTOList = syncProductDAO.queryPage(SyncProductDTO);
        return  PageDTO.of(syncProductDTOList);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改生产企业
     * @param SyncProductDTO 生产企业数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:38
     * @Return boolean
     **/
    @Override
    public boolean save(SyncProductDTO SyncProductDTO){



        //判断名字不为空，根据名字生成五笔码和拼音码
        if (!StringUtils.isEmpty(SyncProductDTO.getName())){

                SyncProductDTO.setPym(PinYinUtils.toFirstPY(SyncProductDTO.getName()));

                SyncProductDTO.setWbm(WuBiUtils.getWBCode(SyncProductDTO.getName()));
        }
        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(SyncProductDTO.getId())){

            WrapperResponse<String> orderNo = syncOrderRuleService.getOrderNo(Constants.ORDERRULE.SCQY);

            SyncProductDTO.setCode(orderNo.getData());

            SyncProductDTO.setId(SnowflakeUtils.getId());

            SyncProductDTO.setCrteTime(DateUtils.getNow());

            return this.syncProductDAO.insert(SyncProductDTO)>0;
        }else {
            return this.syncProductDAO.update(SyncProductDTO)>0;
        }
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个生产企业信息
     * @param syncProductDTO
    * @Author xingyu.xie
    * @Date   2020/7/17 15:43
    * @Return boolean
    **/
    @Override
    public boolean updateStatus(SyncProductDTO syncProductDTO) {
        return this.syncProductDAO.updateStatus(syncProductDTO)>0;
    }
}
