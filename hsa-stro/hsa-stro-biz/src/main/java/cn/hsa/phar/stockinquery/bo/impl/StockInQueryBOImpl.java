package cn.hsa.phar.stockinquery.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.phar.pharapply.dto.StroOutDTO;
import cn.hsa.module.phar.pharapply.entity.StroOutDetail;
import cn.hsa.module.phar.stockinquery.bo.StockInQueryBO;
import cn.hsa.module.phar.stockinquery.dao.StockInQueryDAO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.phar.stockinquery.bo
 * @class_name: StockInQueryBOImpl
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/25 16:24
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class StockInQueryBOImpl extends HsafBO implements StockInQueryBO {

    /**
     * 药房入库确认与查询数据访问层
     */
    @Resource
    private StockInQueryDAO stockInQueryDAO;

    @Resource
    private StroStockBO stroStockBO;

    @Override
    public PageDTO queryPage(StroOutDTO stroOutDTO) {
        // 设置分页信息
        PageHelper.startPage(stroOutDTO.getPageNo(), stroOutDTO.getPageSize());
        // 只有出库审核以后  才能入库确认
        stroOutDTO.setAuditCode(Constants.SF.S);
        List<StroOutDTO> stroOutDTOS = stockInQueryDAO.queryPage(stroOutDTO);
        return PageDTO.of(stroOutDTOS);

    }

    /**
     * @Method: batchCheck()
     * @Description: 批量入库审核药房数据信息
     * @Param: stroOutDTO 药房药库数据传输对象
     * 1.ids: 批量审核id list集合
     * 2. hospCode 医院编码
     *
     * 1.先判断审核标识的状态  审核标识分为  0未审核 、1已审核 、2作废、
     *    1已审核 、2作废、 抛出对应的错误异常、 只对1进行程序处理
     *    2. 更新数据标识
     *    3.更新库存明细
     *    4.药品进销存台帐表
     *
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:13
     * @Return:
     */
    public boolean updateBatchCheck(StroOutDTO stroOutDTO) {
        stroOutDTO.setAuditCode(Constants.SF.S);
        List<StroOutDTO> outinDTOList = stockInQueryDAO.queryBatch(stroOutDTO);
        // 储存药房入库确认
        List<String> stroInListIds = new ArrayList<>();
        // 储存科室入库确认
        List<String> stroInDeptListIds = new ArrayList<>();
        // 储存同级调拨
        List<String> sameAllocationIds = new ArrayList<>();
        for(int i=0;i<outinDTOList.size();i++){
          if(!Constants.SHZT.WSH.equals(outinDTOList.get(i).getOkAuditCode())){
                throw new AppException("本次批量审核已存在审核的单据号:"+outinDTOList.get(i).getOrderNo());
            }
          // 同级调拨
          if ("10".equals(outinDTOList.get(i).getOutCode())) {
            sameAllocationIds.add(outinDTOList.get(i).getId());
          }
          // 出库到药房
          if ("5".equals(outinDTOList.get(i).getOutCode())) {
            stroInListIds.add(outinDTOList.get(i).getId());
          }
          // 出库到科室
          if ("4".equals(outinDTOList.get(i).getOutCode())) {
            stroInDeptListIds.add(outinDTOList.get(i).getId());
          }
        }
        if(!ListUtils.isEmpty(sameAllocationIds)) {
          // 查询同级调拨的数据
          List<StroStockDetailDTO> stroStockDetailDTOS = queryStroOutDetailById(stroOutDTO,sameAllocationIds, stroOutDTO.getHospCode(), "10");
          // 同级调拨确认
          handleStock(stroStockDetailDTOS,"22",stroOutDTO.getHospCode());
        }
        if(!ListUtils.isEmpty(stroInListIds)) {
          // 查询药房入库确认的数据
          List<StroStockDetailDTO> stroStockDetailDTOS = queryStroOutDetailById(stroOutDTO,stroInListIds, stroOutDTO.getHospCode(), "5");
          // 药房入库确认
          handleStock(stroStockDetailDTOS,"20",stroOutDTO.getHospCode());
        }
        if(!ListUtils.isEmpty(stroInDeptListIds)) {
          // 查询科室入库确认的数据
          List<StroStockDetailDTO> stroStockDetailDTOS = queryStroOutDetailById(stroOutDTO,stroInDeptListIds, stroOutDTO.getHospCode(), "4");
          // 科室入库确认
          handleStock(stroStockDetailDTOS,"20",stroOutDTO.getHospCode());
        }
        stroOutDTO.setOkAuditTime(DateUtils.getNow());
        stroOutDTO.setOkAuditCode(Constants.SF.S);
        // 回写审核状态
        return stockInQueryDAO.updateBatchCheck(stroOutDTO) > 0;
    }

    /**
    * @Menthod queryStroInDetailByOrder
    * @Desrciption 获取入库明细数据，转化为出入库存明细
    *
    * @Param
    * [ids, hospCode, type]
    *
    * @Author jiahong.yang
    * @Date   2021/4/21 15:39
    * @Return java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
    **/
    public List<StroStockDetailDTO> queryStroOutDetailById(StroOutDTO stroOutDTO1, List<String> ids,String hospCode,String type) {
      StroOutDTO stroOutDTO = new StroOutDTO();
      stroOutDTO.setOrderNos(ids);
      stroOutDTO.setHospCode(hospCode);
      stroOutDTO.setOutCode(type);
      stroOutDTO.setOkAuditName(stroOutDTO1.getOkAuditName());
      stroOutDTO.setOkAuditId(stroOutDTO1.getOkAuditName());
      List<StroStockDetailDTO> stroStockDetailDTOList= stockInQueryDAO.queryDeatilById(stroOutDTO);
      if(ListUtils.isEmpty(stroStockDetailDTOList)){
        throw new AppException("要审核的的明细数据为空");
      }
      stroStockDetailDTOList.forEach(item->{
        if(item.getSplitRatio() ==null ){
          throw new AppException("拆分比为空");
        }
        else{
          //算出购进拆零单价
          item.setSplitBuyPrice(BigDecimalUtils.divide(item.getBuyPrice(),item.getSplitRatio()));
          //算出售出拆零单价
          item.setSplitSellPrice(BigDecimalUtils.divide(item.getSellPrice(),item.getSplitRatio()));
        }
      });
      return stroStockDetailDTOList;
    }

    /**
    * @Menthod handleStock
    * @Desrciption 入库存
    *
    * @Param
    * [stroStockDetailDTOS, type, hospCode]
    *
    * @Author jiahong.yang
    * @Date   2021/4/21 15:44
    * @Return java.lang.Boolean
    **/
    public Boolean handleStock(List<StroStockDetailDTO> stroStockDetailDTOS, String type, String hospCode){
      Map map= new HashMap<>();
      map.put("hospCode", hospCode);
      map.put("type", type);
      map.put("stroStockDetailDTOList", stroStockDetailDTOS);
      stroStockBO.saveStock(map);
      return  true;
    }
    /**
     * @Method: stockInDetail()
     * @Description: 药房入库明细信息
     * @Param: stroOutinDetailDTO出入库明细数据传输DTO对象
     * @Author: fuhui
     * @Email: 3277857701@qq.comsss
     * @Date: 2020/7/26 20:08
     * @Return:
     */
    @Override
    public List<StroOutDetail> queryStockInDetail(StroOutDTO stroOutDTO) {
        return stockInQueryDAO.queryStockInDetail(stroOutDTO);
    }
}
