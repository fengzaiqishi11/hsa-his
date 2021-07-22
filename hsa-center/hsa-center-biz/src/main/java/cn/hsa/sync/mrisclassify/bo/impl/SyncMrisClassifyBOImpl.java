package cn.hsa.sync.mrisclassify.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sync.financeclassify.bo.SyncFinanceClassifyBO;
import cn.hsa.module.sync.financeclassify.dto.SyncFinanceClassifyDTO;
import cn.hsa.module.sync.mrisclassify.bo.SyncMrisClassifyBO;
import cn.hsa.module.sync.mrisclassify.dao.SyncMrisClassifyDAO;
import cn.hsa.module.sync.mrisclassify.dto.SyncMrisClassifyDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Package_name: cn.hsa.sync.bmm.bo.impl
 * @Class_name: SyncMrisClassifyManagementBOImpl
 * @Describe: 病案费用归类信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class SyncMrisClassifyBOImpl extends HsafBO implements SyncMrisClassifyBO {

    /**
    * @Menthod
    * @Desrciption 注入Dao层对象
     * @param null
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return
    **/
    @Resource
    private SyncMrisClassifyDAO syncMrisClassifyDAO;

    @Resource
    private SyncFinanceClassifyBO syncFinanceClassifyBO;


    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询病案费用归类信息
     * @param syncMrisClassifyDTO  主键ID 医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return cn.hsa.module.sync.bmm.dto.SyncMrisClassifyDTO
    **/
    @Override
    public SyncMrisClassifyDTO getById(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        return this.syncMrisClassifyDAO.getById(syncMrisClassifyDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部病案费用归类信息
     * @param syncMrisClassifyDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/14 21:02
    * @Return java.util.List<cn.hsa.module.sync.bmm.dto.SyncMrisClassifyDTO>
    **/
    @Override
    public List<SyncMrisClassifyDTO> queryAll(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        return this.syncMrisClassifyDAO.queryAll(syncMrisClassifyDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象分页查询病案费用归类信息
     * @param syncMrisClassifyDTO 病案费用归类信息数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:42
    * @Return cn.hsa.sync.PageDTO
    **/
    @Override
    public PageDTO queryPage(SyncMrisClassifyDTO syncMrisClassifyDTO) {

        // 设置分页信息
        PageHelper.startPage(syncMrisClassifyDTO.getPageNo(),syncMrisClassifyDTO.getPageSize());

        List<SyncMrisClassifyDTO> syncMrisClassifyDTOList = syncMrisClassifyDAO.queryAll(syncMrisClassifyDTO);

        SyncFinanceClassifyDTO bfc = new SyncFinanceClassifyDTO();

        PageDTO of = PageDTO.of(syncMrisClassifyDTOList);
        List<SyncMrisClassifyDTO> list = (List<SyncMrisClassifyDTO>) of.getResult();
        if (!ListUtils.isEmpty(list)){
            // 循环根据每条病案费用归类里的财务编码查询财务分类名称，拼成逗号分割的String类型
            for (SyncMrisClassifyDTO item:list){

                String bfcCodes = item.getBfcCodes();
                List<String> strings = Arrays.asList(bfcCodes.split(","));
                bfc.setIds(strings);
                // 根据财务分类编码列表查出所有财务分类名称
                List<SyncFinanceClassifyDTO> syncFinanceClassifyDTOList = syncFinanceClassifyBO.queryAll(bfc);
                List<String> collect = syncFinanceClassifyDTOList.stream().map(SyncFinanceClassifyDTO::getName).collect(Collectors.toList());
                String join = Joiner.on(",").join(collect);
                item.setBfcNames(join);
            }
        }
        return  of;
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改病案费用归类
     * @param SyncMrisClassifyDTO 病案费用归类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:38
     * @Return boolean
     **/
    @Override
    public boolean save(SyncMrisClassifyDTO SyncMrisClassifyDTO){

        if (syncMrisClassifyDAO.isCodeExist(SyncMrisClassifyDTO)>0){
            throw new AppException("操作失败，病案费用代码重复");
        }

        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(SyncMrisClassifyDTO.getId())){

            SyncMrisClassifyDTO.setId(SnowflakeUtils.getId());

            SyncMrisClassifyDTO.setCrteTime(DateUtils.getNow());

            return this.syncMrisClassifyDAO.insert(SyncMrisClassifyDTO)>0;
        }else {
            return this.syncMrisClassifyDAO.updateSyncMrisClassify(SyncMrisClassifyDTO)>0;
        }
    }

    /**
    * @Menthod updateSyncMrisClassifyS
    * @Desrciption  修改单条病案费用归类数据（有判空条件）
     * @param syncMrisClassifyDTO
    * @Author xingyu.xie
    * @Date   2020/9/17 15:17
    * @Return boolean
    **/
    @Override
    public boolean updateSyncMrisClassifyS(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        return syncMrisClassifyDAO.updateSyncMrisClassifyS(syncMrisClassifyDTO)>0;
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个病案费用归类信息
     * @param syncMrisClassifyDTO
    * @Author xingyu.xie
    * @Date   2020/7/17 15:43
    * @Return boolean
    **/
    @Override
    public boolean updateStatus(SyncMrisClassifyDTO syncMrisClassifyDTO) {
        return this.syncMrisClassifyDAO.updateStatus(syncMrisClassifyDTO)>0;
    }
}
