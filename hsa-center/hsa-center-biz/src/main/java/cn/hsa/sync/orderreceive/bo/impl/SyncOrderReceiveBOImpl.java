package cn.hsa.sync.orderreceive.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.orderreceive.bo.SyncOrderReceiveBO;
import cn.hsa.module.sync.orderreceive.dao.SyncOrderReceiveDAO;
import cn.hsa.module.sync.orderreceive.dto.SyncOrderReceiveDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Package_name: cn.hsa.sync.bmm.bo.impl
 * @Class_name: SyncOrderReceiveManagementBOImpl
 * @Describe: 病案费用归类信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/09/17 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class SyncOrderReceiveBOImpl extends HsafBO implements SyncOrderReceiveBO {

    /**
    * @Menthod
    * @Desrciption 注入Dao层对象
     * @param null
    * @Author xingyu.xie
    * @Date   2020/09/17 15:41
    * @Return
    **/
    @Resource
    SyncOrderReceiveDAO syncOrderReceiveDAO;

    /**
     * 注入单据规则service层
     */
    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询病案费用归类信息
     * @param syncOrderReceiveDTO  主键ID 医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/09/17 15:41
    * @Return cn.hsa.module.sync.bmm.dto.SyncOrderReceiveDTO
    **/
    @Override
    public SyncOrderReceiveDTO getById(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        return this.syncOrderReceiveDAO.getById(syncOrderReceiveDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部病案费用归类信息
     * @param syncOrderReceiveDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/09/17 21:02
    * @Return java.util.List<cn.hsa.module.sync.bmm.dto.SyncOrderReceiveDTO>
    **/
    @Override
    public List<SyncOrderReceiveDTO> queryAll(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        return this.syncOrderReceiveDAO.queryAll(syncOrderReceiveDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象分页查询病案费用归类信息
     * @param syncOrderReceiveDTO 病案费用归类信息数据对象
    * @Author xingyu.xie
    * @Date   2020/09/17 15:42
    * @Return cn.hsa.sync.PageDTO
    **/
    @Override
    public PageDTO queryPage(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        // 设置分页信息
        PageHelper.startPage(syncOrderReceiveDTO.getPageNo(),syncOrderReceiveDTO.getPageSize());

        List<SyncOrderReceiveDTO> syncOrderReceiveDTOList = syncOrderReceiveDAO.queryAll(syncOrderReceiveDTO);
        return  PageDTO.of(syncOrderReceiveDTOList);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改病案费用归类
     * @param SyncOrderReceiveDTO 病案费用归类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:38
     * @Return boolean
     **/
    @Override
    public boolean save(SyncOrderReceiveDTO SyncOrderReceiveDTO){

        if (syncOrderReceiveDAO.isCodeExist(SyncOrderReceiveDTO)>0){
            throw new AppException("操作失败，名称重复");
        }
        if (syncOrderReceiveDAO.codeExist(SyncOrderReceiveDTO)>0 ){
            throw new AppException("操作失败，编码重复");
        }
        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(SyncOrderReceiveDTO.getId())){
                SyncOrderReceiveDTO.setId(SnowflakeUtils.getId());
                SyncOrderReceiveDTO.setCrteTime(DateUtils.getNow());
                return this.syncOrderReceiveDAO.insert(SyncOrderReceiveDTO)>0;
        } else {
            return this.syncOrderReceiveDAO.updateSyncOrderReceive(SyncOrderReceiveDTO)>0;
        }
    }

    /**
    * @Menthod updateSyncOrderReceiveS
    * @Desrciption  修改单条病案费用归类数据（有判空条件）
     * @param syncOrderReceiveDTO
    * @Author xingyu.xie
    * @Date   2020/9/17 15:17
    * @Return boolean
    **/
    @Override
    public boolean updateSyncOrderReceiveS(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        return syncOrderReceiveDAO.updateSyncOrderReceiveS(syncOrderReceiveDTO)>0;
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个病案费用归类信息
     * @param syncOrderReceiveDTO
    * @Author xingyu.xie
    * @Date   2020/09/17 15:43
    * @Return boolean
    **/
    @Override
    public boolean updateStatus(SyncOrderReceiveDTO syncOrderReceiveDTO) {
        return this.syncOrderReceiveDAO.updateStatus(syncOrderReceiveDTO)>0;
    }

}
