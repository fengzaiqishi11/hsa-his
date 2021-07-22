package cn.hsa.sync.material.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.advice.dao.SyncAdviceDAO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDTO;
import cn.hsa.module.sync.advice.dto.SyncAdviceDetailDTO;
import cn.hsa.module.sync.advice.service.SyncAdviceService;
import cn.hsa.module.sync.material.bo.SyncMaterialBO;
import cn.hsa.module.sync.material.dao.SyncMaterialDAO;
import cn.hsa.module.sync.material.dto.SyncMaterialDTO;
import cn.hsa.module.sync.synccode.service.SyncCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.sync.bmm.bo.impl
 * @Class_name: SyncMaterialManagementBOImpl
 * @Describe: 材料信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class SyncMaterialBOImpl extends HsafBO implements SyncMaterialBO {

    /**
     * @Menthod
     * @Desrciption 注入Dao层对象
     * @param null
     * @Author xingyu.xie
     * @Date   2020/7/8 15:41
     * @Return
     **/
    @Resource
    SyncMaterialDAO syncMaterialDAO;

    /**
     * 注入单据规则service层
     */
    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    /**
     * 注入单据规则service层
     */
    @Resource
    private SyncCodeService sysCodeService;

    @Resource
    private SyncAdviceService syncAdviceService;

    @Resource
    private SyncAdviceDAO syncAdviceDAO;

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询材料信息
     * @param syncMaterialDTO  主键ID List列表和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/8 15:41
     * @Return cn.hsa.module.sync.bmm.dto.SyncMaterialDTO
     **/
    @Override
    public SyncMaterialDTO getById(SyncMaterialDTO syncMaterialDTO) {
        return this.syncMaterialDAO.getById(syncMaterialDTO);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部材料信息
     * @Author xingyu.xie
     * @Date   2020/7/14 21:02
     * @Return java.util.List<cn.hsa.module.sync.bmm.dto.SyncMaterialDTO>
     **/
    @Override
    public List<SyncMaterialDTO> queryAll() {
        return this.syncMaterialDAO.queryAll();
    }

    /**
     * @Menthod queryPage
     * @Desrciption   根据数据对象分页查询材料信息
     * @param SyncMaterialDTO 材料信息数据对象
     * @Author xingyu.xie
     * @Date   2020/7/8 15:42
     * @Return cn.hsa.sync.PageDTO
     **/
    @Override
    public PageDTO queryPage(SyncMaterialDTO SyncMaterialDTO) {

        if(!StringUtils.isEmpty(SyncMaterialDTO.getBfcCode())){
            HashMap map = new HashMap();

            map.put("code","CLFL");
            WrapperResponse<List<TreeMenuNode>> codeTree = sysCodeService.getCodeData(map);
            String chidldrenIds = TreeUtils.getChidldrenIds(codeTree.getData(),
                    SyncMaterialDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            SyncMaterialDTO.setIds(list);
        }
        SyncMaterialDTO.setBfcCode("");

        // 设置分页信息
        PageHelper.startPage(SyncMaterialDTO.getPageNo(),SyncMaterialDTO.getPageSize());
        List<SyncMaterialDTO> syncMaterialDTOList = syncMaterialDAO.queryPage(SyncMaterialDTO);
        return  PageDTO.of(syncMaterialDTOList);
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改材料分类
     * @param syncMaterialDTO 材料分类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:38
     * @Return boolean
     **/
    @Override
    public boolean save(SyncMaterialDTO syncMaterialDTO){
        //判断名字不为空，根据名字生成五笔码和拼音码
        if (!StringUtils.isEmpty(syncMaterialDTO.getName())){

            syncMaterialDTO.setPym(PinYinUtils.toFirstPY(syncMaterialDTO.getName()));

            syncMaterialDTO.setWbm(WuBiUtils.getWBCode(syncMaterialDTO.getName()));
        }
        if (syncMaterialDTO.getPrice()==null){
            throw new AppException("单价不能为空！");
        }
        if (syncMaterialDTO.getSplitRatio()==null){
            throw new AppException("拆分比不能为空！");
        }
        syncMaterialDTO.setSplitPrice(BigDecimalUtils.divide(syncMaterialDTO.getPrice(),syncMaterialDTO.getSplitRatio()));
        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(syncMaterialDTO.getId())){


            WrapperResponse<String> orderNo = syncOrderRuleService.getOrderNo(Constants.ORDERRULE.CL);

            syncMaterialDTO.setCode(orderNo.getData());

            syncMaterialDTO.setId(SnowflakeUtils.getId());

            syncMaterialDTO.setCrteTime(DateUtils.getNow());

            return this.syncMaterialDAO.insert(syncMaterialDTO)>0;
        }else {
            SyncAdviceDetailDTO syncAdviceDetailDTO = new SyncAdviceDetailDTO();
            List<SyncAdviceDetailDTO> syncAdviceDetailDTOList = new ArrayList<>();
            //回写材料名称
            syncAdviceDetailDTO.setItemName(syncMaterialDTO.getName());
            //回写材料单价
            syncAdviceDetailDTO.setPrice(syncMaterialDTO.getPrice());
            //回写材料单位
            syncAdviceDetailDTO.setUnitCode(syncMaterialDTO.getUnitCode());
            //回写材料规格
            syncAdviceDetailDTO.setSpec(syncMaterialDTO.getSpec());
            //回写用药性质
            syncAdviceDetailDTO.setUseCode(syncMaterialDTO.getUseCode());
            // 写入材料编码
            syncAdviceDetailDTO.setItemCode(syncMaterialDTO.getCode());

            syncAdviceDetailDTOList.add(syncAdviceDetailDTO);
            Map map = new HashMap<>();

            map.put("syncAdviceDetailDTOList",syncAdviceDetailDTOList);
            syncAdviceService.updateSyncAdviceAndSyncAdviceDetailsByItemCode(map);
            return this.syncMaterialDAO.update(syncMaterialDTO)>0;
        }
    }

    /**
     * @Menthod updateList
     * @Desrciption  修改多条材料数据
     * @param syncMaterialDTOList 材料分类List
     * @Author xingyu.xie
     * @Date   2020/8/24 15:32
     * @Return boolean
     **/
    @Override
    public boolean updateList(List<SyncMaterialDTO> syncMaterialDTOList) {
        if (ListUtils.isEmpty(syncMaterialDTOList)){
            throw new AppException("修改数据为空");
        }
        syncMaterialDAO.updateList(syncMaterialDTOList);
        return true;
    }

    /**
     * @Menthod updateStatus
     * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个材料信息
     * @param syncMaterialDTO
     * @Author xingyu.xie
     * @Date   2020/7/8 15:43
     * @Return boolean
     **/
    @Override
    public boolean updateStatus(SyncMaterialDTO syncMaterialDTO) {

        if (Constants.SF.F.equals(syncMaterialDTO.getIsValid())){
            if (ListUtils.isEmpty(syncMaterialDTO.getIds())){
                throw new AppException("要作废的数据不能为空！");
            }

            if (StringUtils.isEmpty(syncMaterialDTO.getCode())){
                throw new AppException("项目编码不能为空！");
            }

            List<SyncMaterialDTO> syncMaterialDTOS = this.syncMaterialDAO.queryByIds(syncMaterialDTO);

            List<String> existStockMaterial = new ArrayList<>();

            syncMaterialDTOS.forEach(item->{

                // 判断材料存在哪些医嘱里
                List<SyncAdviceDTO> existMaterialInAdvice = this.syncAdviceDAO.isExistMaterialInAdvice(item);

                if (!ListUtils.isEmpty(existMaterialInAdvice)){

                    StringBuilder message = new StringBuilder();

                    for (SyncAdviceDTO syncAdviceDTO :existMaterialInAdvice){
                        message.append("【").append(syncAdviceDTO.getName()).append("（").append(syncAdviceDTO.getCode()).append("）】");
                    }

                    existStockMaterial.add("材料【"+item.getName()+"（"+item.getCode()+"）】已被医嘱"+message+"使用。");

                }

            });

            if (!ListUtils.isEmpty(existStockMaterial)){
                throw new AppException("作废失败,"+ Joiner.on("").join(existStockMaterial));
            }
        }
        return this.syncMaterialDAO.updateStatus(syncMaterialDTO)>0;
    }
}
