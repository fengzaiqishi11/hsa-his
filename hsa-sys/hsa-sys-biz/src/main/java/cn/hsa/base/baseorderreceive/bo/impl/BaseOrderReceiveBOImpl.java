package cn.hsa.base.baseorderreceive.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.baseorderreceive.bo.BaseOrderReceiveBO;
import cn.hsa.module.base.baseorderreceive.dao.BaseOrderReceiveDAO;
import cn.hsa.module.base.baseorderreceive.dto.BaseOrderReceiveDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Package_name: cn.hsa.base.bmm.bo.impl
 * @Class_name: BaseOrderReceiveManagementBOImpl
 * @Describe: 病案费用归类信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/09/17 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseOrderReceiveBOImpl extends HsafBO implements BaseOrderReceiveBO {

    /**
    * @Menthod
    * @Desrciption 注入Dao层对象
     * @param null
    * @Author xingyu.xie
    * @Date   2020/09/17 15:41
    * @Return
    **/
    @Resource
    BaseOrderReceiveDAO baseOrderReceiveDAO;

    /**
     * 注入单据规则service层
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询病案费用归类信息
     * @param baseOrderReceiveDTO  主键ID 医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 15:41
    * @Return cn.hsa.module.base.bmm.dto.BaseOrderReceiveDTO
    **/
    @Override
    public BaseOrderReceiveDTO getById(BaseOrderReceiveDTO baseOrderReceiveDTO) {
        return this.baseOrderReceiveDAO.getById(baseOrderReceiveDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部病案费用归类信息
     * @param baseOrderReceiveDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/09/17 21:02
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseOrderReceiveDTO>
    **/
    @Override
    public List<BaseOrderReceiveDTO> queryAll(BaseOrderReceiveDTO baseOrderReceiveDTO) {
        return this.baseOrderReceiveDAO.queryAll(baseOrderReceiveDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象分页查询病案费用归类信息
     * @param baseOrderReceiveDTO 病案费用归类信息数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 15:42
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPage(BaseOrderReceiveDTO baseOrderReceiveDTO) {
        // 设置分页信息
        PageHelper.startPage(baseOrderReceiveDTO.getPageNo(),baseOrderReceiveDTO.getPageSize());

        List<BaseOrderReceiveDTO> baseOrderReceiveDTOList = baseOrderReceiveDAO.queryAll(baseOrderReceiveDTO);
        return  PageDTO.of(baseOrderReceiveDTOList);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改领药单据
     * @param BaseOrderReceiveDTO 领药单据数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:38
     * @Return boolean
     **/
    @Override
    public boolean save(BaseOrderReceiveDTO BaseOrderReceiveDTO){

        if (baseOrderReceiveDAO.isCodeExist(BaseOrderReceiveDTO)>0){
            throw new AppException("操作失败，名称重复");
        }
        if (baseOrderReceiveDAO.codeExist(BaseOrderReceiveDTO)>0 ){
            throw new AppException("操作失败，编码重复");
        }

        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(BaseOrderReceiveDTO.getId())){

            BaseOrderReceiveDTO.setId(SnowflakeUtils.getId());

            BaseOrderReceiveDTO.setCrteTime(DateUtils.getNow());

            return this.baseOrderReceiveDAO.insert(BaseOrderReceiveDTO)>0;
        }else {
            return this.baseOrderReceiveDAO.updateBaseOrderReceive(BaseOrderReceiveDTO)>0;
        }
    }

    /**
    * @Menthod updateBaseOrderReceiveS
    * @Desrciption  修改单条病案费用归类数据（有判空条件）
     * @param baseOrderReceiveDTO
    * @Author xingyu.xie
    * @Date   2020/9/17 15:17
    * @Return boolean
    **/
    @Override
    public boolean updateBaseOrderReceiveS(BaseOrderReceiveDTO baseOrderReceiveDTO) {
        return baseOrderReceiveDAO.updateBaseOrderReceiveS(baseOrderReceiveDTO)>0;
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个病案费用归类信息
     * @param baseOrderReceiveDTO
    * @Author xingyu.xie
    * @Date   2020/09/17 15:43
    * @Return boolean
    **/
    @Override
    public boolean updateStatus(BaseOrderReceiveDTO baseOrderReceiveDTO) {
        return this.baseOrderReceiveDAO.updateStatus(baseOrderReceiveDTO)>0;
    }

}
