package cn.hsa.stro.backstroconfirm.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.stro.backstroconfirm.bo.BackStroConfirmBO;
import cn.hsa.module.stro.backstroconfirm.dao.BackStroConfirmDAO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * @Package_name: cn.hsa.stro.returnstro.bo.impl
 * @Class_name: ReturnStroBoImpl
 * @Describe:
 * @Author: xingyu.xie
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/7/22 10:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BackStroConfirmBOImpl extends HsafBO implements BackStroConfirmBO {

    /**
     * 注入退库确认dao层接口
     */
    @Resource
    private BackStroConfirmDAO backStroConfirmDAO;

    @Resource
    private StroStockBO stroStockBO;




  /**
  * @Menthod queryBackOutinPage
  * @Desrciption  分页查询所有药房退库的记录
   * @param stroOutDTO 出入库数据传输对象
  * @Author xingyu.xie
  * @Date   2020/7/27 19:22
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryBackOutPage(StroOutDTO stroOutDTO) {
    //设置分页信息
    PageHelper.startPage(stroOutDTO.getPageNo(),stroOutDTO.getPageSize());

    //查询出所有的数据
    List<StroOutDTO> stroOutDTOS = backStroConfirmDAO.queryBackOutPage(stroOutDTO);

    //进行分页
    return PageDTO.of(stroOutDTOS);
  }


    /**
     * @Menthod queryBackOutinPage
     * @Desrciption  分页查询所有药房退库的记录
     * @param stroOutDTO 出入库数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/27 19:22
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryBackOutinPageyf(StroOutDTO stroOutDTO) {
        //设置分页信息
        PageHelper.startPage(stroOutDTO.getPageNo(),stroOutDTO.getPageSize());

        //查询出所有的数据
        List<StroOutDTO> stroOutDTOS = backStroConfirmDAO.queryBackOutinPageyf(stroOutDTO);

        if(!ListUtils.isEmpty(stroOutDTOS)) {
          for (StroOutDTO s:stroOutDTOS) {
            List<StroOutDetailDTO> stroOutDetailDTOS = backStroConfirmDAO.queryOutDetailByOutId(s);
            s.setStroOutDetailDTOS(stroOutDetailDTOS);
          }
        }

        //进行分页
        return PageDTO.of(stroOutDTOS);
    }
  /**
  * @Menthod queryOutinDetailByOutinId
  * @Desrciption   根据医院编码和出入库表orderNo去查询出入库明细的数据
   * @param stroOutDTO 出入库数据传输对象
  * @Author xingyu.xie
  * @Date   2020/8/9 23:09
  * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDetailDTO>
  **/
  @Override
  public List<StroOutDetailDTO> queryOutDetailByOutId(StroOutDTO stroOutDTO) {

    return backStroConfirmDAO.queryOutDetailByOutId(stroOutDTO);
  }

  /**
  * @Menthod updateAuditCode
  * @Desrciption  审核药房退库操作
   * @param stroOutDTO 出入库数据传输对象
  * @Author xingyu.xie
  * @Date   2020/7/27 19:21
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateOkAuditCode(StroOutDTO stroOutDTO) {
      List<String> ids = stroOutDTO.getIds();
      List<StroOutDTO> queryid = backStroConfirmDAO.queryByids(ids);
      for (StroOutDTO outDTO : queryid) {
          if (!(("0").equals(outDTO.getOkAuditCode()))) {
              throw new AppException("操作异常，请审核待审核状态的记录");
          }
      }
      int i = backStroConfirmDAO.updateOkAuditCode(stroOutDTO);
      List<StroStockDetailDTO> stroStockDetailDTOList = backStroConfirmDAO.queryOutDetailByOutIds(stroOutDTO);
      if (ListUtils.isEmpty(stroStockDetailDTOList)) {
          throw new AppException("操作异常，详细数据为空");
      }
      stroStockDetailDTOList.forEach(item->{
          //算出购进拆零单价
          item.setSplitBuyPrice(BigDecimalUtils.divide(item.getBuyPrice(),item.getSplitRatio()));
          //算出售出拆零单价
          item.setSplitSellPrice(BigDecimalUtils.divide(item.getSellPrice(),item.getSplitRatio()));
      });
      Map map = new HashMap();
      map.put("hospCode",stroOutDTO.getHospCode());
      map.put("type", EnumUtil.CRFS21.getKey());
      map.put("stroStockDetailDTOList",stroStockDetailDTOList);
      stroStockBO.saveStock(map);
      return true;
    }

    /**
     * @param stroOutDTO 出入库表数据传输对象
     * @Menthod insert
     * @Desrciption 新增
     * @Author xingyu.xie
     * @Date 2020/7/27 14:29
     * @Return boolean
     **/
    @Override
    public int insert(StroOutDTO stroOutDTO) {
        stroOutDTO.setOutCode(Constants.CRFS.YFTK);
        stroOutDTO.setAuditCode("0");
        stroOutDTO.setCrteTime(DateUtils.getNow());
        stroOutDTO.setOkAuditCode("0");
        List<StroOutDetailDTO> stroOutinDetailDTOS = stroOutDTO.getStroOutDetailDTOS();
        String id = SnowflakeUtils.getId();
        stroOutDTO.setId(id);
        BigDecimal buyPriceAll= BigDecimal.valueOf(0);
        BigDecimal sellPriceAll= BigDecimal.valueOf(0);
        if (!ListUtils.isEmpty(stroOutinDetailDTOS)) {
            for (int i = 0; i < stroOutinDetailDTOS.size(); i++) {
                stroOutinDetailDTOS.get(i).setOutId(id);
                stroOutinDetailDTOS.get(i).setId(SnowflakeUtils.getId());
                buyPriceAll= BigDecimalUtils.add(buyPriceAll, stroOutinDetailDTOS.get(i).getBuyPriceAll());
                sellPriceAll=  BigDecimalUtils.add(sellPriceAll, stroOutinDetailDTOS.get(i).getSellPriceAll());
            }
            backStroConfirmDAO.insertList(stroOutinDetailDTOS);
        }
        stroOutDTO.setBuyPriceAll(buyPriceAll);
        stroOutDTO.setSellPriceAll(sellPriceAll);
        return backStroConfirmDAO.insert(stroOutDTO);
    }

    /**
     * @param stroOutDTO 出入库表数据传输对象
     * @Menthod update
     * @Desrciption 更新
     * @Author xingyu.xie
     * @Date 2020/7/27 14:29
     * @Return boolean
     **/
    @Override
    public int update(StroOutDTO stroOutDTO) {
        List<StroOutDetailDTO> stroOutinDetailDTOS = stroOutDTO.getStroOutDetailDTOS();
        String id = stroOutDTO.getId();
//        String code= stroOutDTO.getOrderNo();
        backStroConfirmDAO.deleteById(id, stroOutDTO.getHospCode());
        BigDecimal buyPriceAll= BigDecimal.valueOf(0);
        BigDecimal sellPriceAll= BigDecimal.valueOf(0);
        if (!ListUtils.isEmpty(stroOutinDetailDTOS)) {
            for (int i = 0; i < stroOutinDetailDTOS.size(); i++) {
                stroOutinDetailDTOS.get(i).setId(SnowflakeUtils.getId());
                stroOutinDetailDTOS.get(i).setOutId(id);
                buyPriceAll= BigDecimalUtils.add(buyPriceAll, stroOutinDetailDTOS.get(i).getBuyPriceAll());
                sellPriceAll=  BigDecimalUtils.add(sellPriceAll, stroOutinDetailDTOS.get(i).getSellPriceAll());
            }
            backStroConfirmDAO.insertList(stroOutinDetailDTOS);
        }
        stroOutDTO.setBuyPriceAll(buyPriceAll);
        stroOutDTO.setSellPriceAll(sellPriceAll);
        return backStroConfirmDAO.update(stroOutDTO);
    }

    /**
     * @param id 主键
     * @Menthod queryById
     * @Desrciption 通过ID查询单条数据
     * @Author xingyu.xie
     * @Date 2020/7/27 15:24
     * @Return java.util.List<cn.hsa.module.stro.outinstro.dto.StroOutinDetailDTO>
     **/
    @Override
    public StroOutDTO getById(String id) {
        return backStroConfirmDAO.getById(id);
    }

    @Override
    public int updateAuditCode(StroOutDTO stroOutDTO) {
        List<String> ids = stroOutDTO.getIds();
        String auditCode = stroOutDTO.getAuditCode();
        List<StroOutDTO> queryid = backStroConfirmDAO.queryByids(ids);
        for (StroOutDTO outDTO : queryid) {
            if (!(("0").equals(outDTO.getAuditCode()))) {
                throw new AppException("操作异常，请审核待审核状态的记录");
            }
        }
        String utAuditName = stroOutDTO.getAuditName();
        String utAuditId = stroOutDTO.getAuditId();
        String bizid = stroOutDTO.getBizId();

        List<StroOutDTO> stroOuts = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            StroOutDTO stroOut = new StroOutDTO();
            stroOut.setId(ids.get(i));
            stroOut.setAuditCode(auditCode);
            stroOut.setAuditName(utAuditName);
            stroOut.setAuditId(utAuditId);
            stroOut.setAuditTime(new Date());
            stroOuts.add(stroOut);
        }
        if (("1").equals(auditCode)) {
            List<StroStockDetailDTO> stroStockDetailDTOList = backStroConfirmDAO.queryOutDetailByOutIds(stroOutDTO);
            if (ListUtils.isEmpty(stroStockDetailDTOList)) {
                throw new AppException("操作异常，详细数据为空");
            }

            stroStockDetailDTOList.forEach(item -> {
                //算出购进拆零单价
                item.setSplitBuyPrice(BigDecimalUtils.divide(item.getBuyPrice(), item.getSplitRatio()));
                //算出售出拆零单价
                item.setSplitSellPrice(BigDecimalUtils.divide(item.getSellPrice(), item.getSplitRatio()));
                item.setBizId(bizid);

                //出库如果有批号，按批号出
                List<StroStockDetailDTO> detailDTOList = new ArrayList<>();
                detailDTOList.add(item);
                Map map = new HashMap();
                map.put("hospCode", stroOutDTO.getHospCode());
                if (!StringUtils.isEmpty(item.getBatchNo())) {
                    map.put("sfBatchNo", "true");
                }
                map.put("type", EnumUtil.CRFS6.getKey());
                map.put("stroStockDetailDTOList", detailDTOList);
                stroStockBO.saveStock(map);
            });
        }
        return backStroConfirmDAO.updateAuditCode(stroOuts);
    }
}
