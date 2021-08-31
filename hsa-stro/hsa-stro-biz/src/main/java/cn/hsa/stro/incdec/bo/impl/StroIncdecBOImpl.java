package cn.hsa.stro.incdec.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.stro.incdec.bo.StroIncdecBO;
import cn.hsa.module.stro.incdec.dao.StroIncdecDao;
import cn.hsa.module.stro.incdec.dao.StroIncdecDetailDao;
import cn.hsa.module.stro.incdec.dto.StroIncdecDTO;
import cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 *@Package_name: cn.hsa.stro.incdec.bo
 *@Class_name: StroIncdecBOImpl
 *@Describe: 药库报损
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/11 10:34
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class StroIncdecBOImpl extends HsafBO implements StroIncdecBO {

    @Resource
    StroIncdecDao stroIncdecDao;

    @Resource
    StroIncdecDetailDao stroIncdecDetailDao;

    @Resource
    private StroStockBO stroStockBO;

    @Resource
    BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * @Method queryStroIncdecDaoPage
     * @Desrciption  分页查询
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroIncdecDTO>
     **/
    @Override
    public PageDTO queryStroIncdecDTOPage(StroIncdecDTO stroIncdecDTO) {
        PageHelper.startPage(stroIncdecDTO.getPageNo(), stroIncdecDTO.getPageSize());
        List<StroIncdecDTO> stroIncdecDTOS = stroIncdecDao.queryStroIncdecDTOPage(stroIncdecDTO);

        if(!ListUtils.isEmpty(stroIncdecDTOS)){
            StroIncdecDTO pdto = new StroIncdecDTO();
            List<String> ids = new ArrayList<>();

            for(StroIncdecDTO dto:stroIncdecDTOS){
                pdto = new StroIncdecDTO();
                ids = new ArrayList<>();

                ids.add(dto.getId());
                pdto.setHospCode(dto.getHospCode());
                pdto.setIds(ids);
                List<StroIncdecDetailDTO> stroIncdecDetailDTOS = stroIncdecDetailDao.queryStroIncdecDetailDTOPage(pdto);

                dto.setStroIncdecDetailDTOs(stroIncdecDetailDTOS);
            }
        }

        return PageDTO.of(stroIncdecDTOS);
    }

    /**
     * @Method queryStroIncdecDTOById
     * @Desrciption 单个查询调价单
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return cn.hsa.module.stro.adjust.dto.StroIncdecDTO
     **/
    @Override
    public StroIncdecDTO queryStroIncdecDTOById(StroIncdecDTO stroIncdecDTO) {
        StroIncdecDTO stroAdjustDtoById = stroIncdecDao.getStroIncdecDTOById(stroIncdecDTO);

        List<String> ids = new ArrayList<>();
        ids.add(stroIncdecDTO.getId());
        stroIncdecDTO.setIds(ids);
        List<StroIncdecDetailDTO> stroIncdecDetailDTOS = stroIncdecDetailDao.queryStroIncdecDetailDTOPage(stroIncdecDTO);

        stroAdjustDtoById.setStroIncdecDetailDTOs(stroIncdecDetailDTOS);
        return stroAdjustDtoById;
    }

    /**
     * @Method queryStroIncdecDetailDtoPage
     * @Desrciption 获得报损明细
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryStroIncdecDetailDtoPage(StroIncdecDTO stroIncdecDTO) {
        PageHelper.startPage(stroIncdecDTO.getPageNo(), stroIncdecDTO.getPageSize());
        List<StroIncdecDetailDTO> stroIncdecDetailDTOS = stroIncdecDetailDao.queryStroIncdecDetailDTOPage(stroIncdecDTO);
        return PageDTO.of(stroIncdecDetailDTOS);
    }

    /**
    * @Method queryStroIncdecDetailDtos
    * @Desrciption 根据条件查询出报损明细
    * @param stroIncdecDTO
    * @Author liuqi1
    * @Date   2020/9/8 13:43
    * @Return java.util.List<cn.hsa.module.stro.incdec.dto.StroIncdecDetailDTO>
    **/
    @Override
    public List<StroIncdecDetailDTO> queryStroIncdecDetailDtos(StroIncdecDTO stroIncdecDTO) {
        List<StroIncdecDetailDTO> stroIncdecDetailDTOS = stroIncdecDetailDao.queryStroIncdecDetailDTOs(stroIncdecDTO);
        return stroIncdecDetailDTOS;
    }

    /**
     * @Method insertStroIncdecDao
     * @Desrciption 新增或更新报损信息
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return int
     **/
    @Override
    public boolean insertOrUpdateStroIncdecDTO(StroIncdecDTO stroIncdecDTO) {
        Boolean isSuccess = false;//操作成功标示，默认false：失败
        Date nowTime = DateUtils.getNow();
        String id = SnowflakeUtils.getId();


        if(StringUtils.isEmpty(stroIncdecDTO.getId())){
            //新增
            HashMap map = new HashMap();
            map.put("hospCode", stroIncdecDTO.getHospCode());
            map.put("typeCode", Constants.ORDERRULE.BS);
            WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(map);

            stroIncdecDTO.setOrderNo(orderNo.getData());
            stroIncdecDTO.setId(id);
            stroIncdecDTO.setCrteTime(nowTime);
            stroIncdecDTO.setAuditCode("0");
            stroIncdecDao.insertStroIncdecDTO(stroIncdecDTO);

            //更新报损明细数据
            updateStroIncdecDetailDTO(stroIncdecDTO);

            isSuccess = true;
        }else{
            StroIncdecDTO stroIncdecDTOById = stroIncdecDao.getStroIncdecDTOById(stroIncdecDTO);
            if(!"0".equals(stroIncdecDTOById.getAuditCode())){
                //如果单据不处于未审核状态，不允许更新
                throw new AppException("操作失败,单据已审核");
            }

            //更新
            stroIncdecDao.updateStroIncdecDTO(stroIncdecDTO);

            //先删除后新增
            stroIncdecDetailDao.deleteStroIncdecDetailDTO(stroIncdecDTO);
            //更新报损明细数据
            updateStroIncdecDetailDTO(stroIncdecDTO);
            isSuccess = true;
        }

        return isSuccess;
    }

    //更新报损明细数据
    private void updateStroIncdecDetailDTO(StroIncdecDTO stroIncdecDTO){
        List<StroIncdecDetailDTO> lists = stroIncdecDTO.getStroIncdecDetailDTOs();
        List<StroIncdecDetailDTO> listInsert = new ArrayList<>();

        for(StroIncdecDetailDTO dto:lists){
            if(StringUtils.isNotEmpty(dto.getItemId()) && StringUtils.isNotEmpty(dto.getItemName())){
                if(StringUtils.isEmpty(dto.getProfitLossType())) {
                  throw new AppException("损益类型不能为空");
                }
                dto.setId(SnowflakeUtils.getId());
                dto.setHospCode(stroIncdecDTO.getHospCode());
                dto.setAdjustId(stroIncdecDTO.getId());
                listInsert.add(dto);
            }
        }
        //批量新增报损明细
        stroIncdecDetailDao.insertStroIncdecDetailDTO(listInsert);
    }

    /**
     * @Method updateStroIncdecDao
     * @Desrciption 批量审核或作废报损
     * @param stroIncdecDTO
     * @Author liuqi1
     * @Date   2020/8/11 9:25
     * @Return int
     **/
    @Override
    public boolean updateOrCancelStroIncdecDTO(StroIncdecDTO stroIncdecDTO) {
        if(StringUtils.isEmpty(stroIncdecDTO.getAuditCode())){
            return false;
        }

        List<StroIncdecDTO> stroIncdecDTOS = stroIncdecDao.queryStroIncdecDTOs(stroIncdecDTO);
        stroIncdecDTOS.forEach(dto -> {
            if(!"0".equals(dto.getAuditCode())){
                //如果单据不处于未审核状态，不允许更新
                throw new AppException("操作失败,单据已审核");
            }
        });

        stroIncdecDTO.setAuditTime(DateUtils.getNow());
        Boolean isSuccess = false;
        if("1".equals(stroIncdecDTO.getAuditCode())){
            //审核
            stroIncdecDao.updateStroIncdecDTOByIds(stroIncdecDTO);

            //获得库存明细数据
            List<StroStockDetailDTO> stroStockDetailDTOS = stroIncdecDetailDao.queryStroStockDetailDTOs(stroIncdecDTO);
            for(StroStockDetailDTO item: stroStockDetailDTOS) {
              if("0".equals(item.getProfitLossType())) {
                item.setNum(BigDecimalUtils.multiply(item.getNum(), BigDecimal.valueOf(-1)));
                item.setSplitNum(BigDecimalUtils.multiply(item.getSplitNum(), BigDecimal.valueOf(-1)));
              }
            }
            if (!ListUtils.isEmpty(stroStockDetailDTOS)) {
                Map map = new HashMap();
                map.put("type",Constants.CRFS.BSBY);
                map.put("sfBatchNo", true);
                map.put("hospCode",stroIncdecDTO.getHospCode());
                map.put("stroStockDetailDTOList",stroStockDetailDTOS);
                //更新库存
                stroStockBO.saveStock(map);
            }

            isSuccess = true;
        }else if("2".equals(stroIncdecDTO.getAuditCode())){
            //作废
            stroIncdecDao.updateStroIncdecDTOByIds(stroIncdecDTO);
            isSuccess = true;
        }

        return isSuccess;
    }
}
