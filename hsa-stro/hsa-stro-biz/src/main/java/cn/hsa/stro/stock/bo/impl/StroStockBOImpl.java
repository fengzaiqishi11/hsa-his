package cn.hsa.stro.stock.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;

import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dao.StroStockDao;
import cn.hsa.module.stro.stock.dao.StroStockDetailDao;
import cn.hsa.module.stro.stock.dto.ItemProfitStatisticsDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroin.dto.StroInRecordDTO;
import cn.hsa.module.stro.stroinvoicing.dao.StroInvoicingDAO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ljh
 * @date 2020/07/27.
 */
@Component
@Slf4j
public class StroStockBOImpl extends HsafBO implements StroStockBO {

    @Resource
    StroStockDao stroStockDao;
    @Resource
    StroStockDetailDao stroStockDetailDao;
    //台账
    @Resource
    StroInvoicingDAO stroInvoicingDAO;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StroStockDTO queryById(String id, String hospCode) {
        return stroStockDao.queryById(id, hospCode);
    }

    /**
     * 通过id查询单个库存详细
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public StroStockDetailDTO getStroStockDetailById(String id, String hospCode) {
        return stroStockDetailDao.getStroStockDetailById(id, hospCode);
    }

    /**
     * 通过实体作为筛选条件查询
     *
     * @param cxStroStockDTO 实例对象
     * @return 对象列表
     */
    @Override
    public List<StroStockDTO> queryAll(StroStockDTO cxStroStockDTO) {
        return stroStockDao.queryAlldrug(cxStroStockDTO);
    }

    @Override
    public PageDTO queryPage(StroStockDTO stroStockDTO) {
        List<StroStockDTO> list = null;
        PageHelper.startPage(stroStockDTO.getPageNo(), stroStockDTO.getPageSize());

        //根据科室类别区分是中药还是西药还是材料
        String loginTypeIdentity = stroStockDTO.getLoginTypeIdentity();
        if(StringUtils.isEmpty(loginTypeIdentity)) {
          throw new AppException("当前科室未配置药房药库类别标识");
        }
        List<String> types = new ArrayList<>();
        for (String loginType:loginTypeIdentity.split(",")) {
            if (loginType.equals("2")) {//中成药
                types.add("1");
            } else if (loginType.equals("3")) {//中草药
                types.add("2");
            } else if (loginType.equals("1")) {//西药
                types.add("0");
            } else if(loginType.contains("4")){ // 藏药
                types.add("3");
            } else if (loginType.equals("5") || loginType.equals("6") || loginType.equals("7")) {//材料
                stroStockDTO.setTypeIdentity("5");
            } else {
                throw new AppException("没有该药房药库类别标识");
            }
        }
        stroStockDTO.setTypes(types);
        if(StringUtils.isEmpty(stroStockDTO.getLoginTypeIdentity())){
           list = stroStockDao.queryAll(stroStockDTO);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDTO.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDTO.getTypes())) {
            list = stroStockDao.queryAll(stroStockDTO);
        } else if (cn.hsa.util.StringUtils.isEmpty(stroStockDTO.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDTO.getTypes())) {
            list = stroStockDao.queryAlldrug(stroStockDTO);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDTO.getTypeIdentity()) && ListUtils.isEmpty(stroStockDTO.getTypes())) {
            list = stroStockDao.queryAllmaterial(stroStockDTO);
        }
        // 返回分页结果
        return PageDTO.of(list);
    }

    @Override
    public PageDTO queryPageDetail(StroStockDetailDTO stroStockDetail) {
        List<StroStockDetailDTO> list = null;
        PageHelper.startPage(stroStockDetail.getPageNo(), stroStockDetail.getPageSize());
        //根据科室类别区分是中药还是西药还是材料
        String loginTypeIdentity = stroStockDetail.getLoginTypeIdentity();
        if (!cn.hsa.util.StringUtils.isEmpty(loginTypeIdentity)) {
            List<String> types = new ArrayList<>();
            for (String loginType:loginTypeIdentity.split(",")) {
                if (loginType.equals("2")) {//中成药
                    types.add("1");
                } else if (loginType.equals("3")) {//中草药
                    types.add("2");
                } else if (loginType.equals("1")) {//西药
                    types.add("0");
                } else if (loginType.equals("4")) {// 藏药
                    types.add("3");
                }else if (loginType.equals("5") || loginType.equals("6") || loginType.equals("7")) {//材料
                    stroStockDetail.setTypeIdentity("5");
                }
            }
            stroStockDetail.setTypes(types);
        }
        if(StringUtils.isEmpty(stroStockDetail.getLoginTypeIdentity())){
            list = stroStockDetailDao.queryAll(stroStockDetail);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAll(stroStockDetail);
        } else if (cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllDrug(stroStockDetail);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllMaterial(stroStockDetail);
        }
        // 返回分页结果
        return PageDTO.of(list);
    }

    @Override
    public PageDTO queryPageDetailCancleNo(StroStockDetailDTO stroStockDetail) {
        List<StroStockDetailDTO> list = null;
        PageHelper.startPage(stroStockDetail.getPageNo(), stroStockDetail.getPageSize());
        //根据科室类别区分是中药还是西药还是材料
        String loginTypeIdentity = stroStockDetail.getLoginTypeIdentity();
        if (!cn.hsa.util.StringUtils.isEmpty(loginTypeIdentity)) {
            List<String> types = new ArrayList<>();
            for (String loginType:loginTypeIdentity.split(",")) {
                if (loginType.equals("2")) {//中成药
                    types.add("1");
                } else if (loginType.equals("3")) {//中草药
                    types.add("2");
                } else if (loginType.equals("1")) {//西药
                    types.add("0");
                } else if (loginType.equals("4")) {//藏药
                    types.add("3");
                } else if (loginType.equals("5") || loginType.equals("6") || loginType.equals("7")) {//材料
                    stroStockDetail.setTypeIdentity("5");
                }
            }
            stroStockDetail.setTypes(types);
        }
        if(StringUtils.isEmpty(stroStockDetail.getLoginTypeIdentity())){
            list = stroStockDetailDao.queryAllCancleNo(stroStockDetail);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllCancleNo(stroStockDetail);
        } else if (cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllDrugCancleNo(stroStockDetail);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllMaterialCancleNo(stroStockDetail);
        }
        // 返回分页结果
        return PageDTO.of(list);
    }

    /**
     * @Method: queryStockDetails
     * @Description: 分页获取库存明细
     * @Param: [stroStockDetail]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/5 14:26
     * @Return: java.util.List<cn.hsa.module.stro.stock.dto.StroStockDetailDTO>
     **/
    @Override
    public PageDTO queryStockDetails(StroStockDetailDTO stroStockDetail) {
        List<StroStockDetailDTO> list = null;
        PageHelper.startPage(stroStockDetail.getPageNo(), stroStockDetail.getPageSize());
        //根据科室类别区分是中药还是西药还是材料
        String loginTypeIdentity = stroStockDetail.getLoginTypeIdentity();
        if (!cn.hsa.util.StringUtils.isEmpty(loginTypeIdentity)) {
            List<String> types = new ArrayList<>();
            for (String loginType:loginTypeIdentity.split(",")) {
                if (loginType.equals("2")) {//中成药
                    types.add("1");
                } else if (loginType.equals("3")) {//中草药
                    types.add("2");
                } else if (loginType.equals("1")) {//西药
                    types.add("0");
                } else if (loginType.equals("4")) {//藏药
                    types.add("3");
                } else if (loginType.equals("5") || loginType.equals("6") || loginType.equals("7")) {//材料
                    stroStockDetail.setTypeIdentity("5");
                }
            }
            stroStockDetail.setTypes(types);
        }
        if(StringUtils.isEmpty(stroStockDetail.getLoginTypeIdentity())){
          list = stroStockDetailDao.queryAll(stroStockDetail);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAll(stroStockDetail);
        } else if (cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllDrug(stroStockDetail);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllMaterial(stroStockDetail);
        }
        // 返回分页结果
        return PageDTO.of(list);
    }


    @Override
    public List<StroStockDetailDTO> queryAllDetail(StroStockDetailDTO stroStockDetail) {
        return stroStockDetailDao.queryAllDrug(stroStockDetail);
    }

    /**
     * @Method: update
     * @Description: 修改库存上限下限
     * @Param: [stroStockDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/13 9:58
     * @Return: int
     **/
    @Override
    public int update(StroStockDTO stroStockDTO) {
        if (BigDecimalUtils.compareTo(stroStockDTO.getStockOccupy(),BigDecimal.valueOf(0)) < 0) {
            throw new AppException("占用库存数不能小于0");
        }
        if (BigDecimalUtils.compareTo(stroStockDTO.getStockOccupy(),stroStockDTO.getSplitNum()) > 0) {
            throw new AppException("占用库存数不能大于拆零总数量");
        }
        return stroStockDao.updateMaxMin(stroStockDTO);
    }

    /**
     * @Menthod saveStock
     * @Desrciption 保存库存信息
     * @param map {hospCode：医院编码  type: 1:采购入库 2：直接入库 3：退供应商 4：出库到科室 5：出库到药房 6：药房退库 7：盘盈盘亏 8：报损报溢 9：同级调拨（库房） 10：同级调拨（药房） 24:调价管理
     *            stroStockDetailDTOList: 库存明细 + 拆零单位、规格、剂型}
     * @Author zengfeng
     * @Date   2020/8/5 9:12
     * @Return 是否成功
     **/
    @Override
    public List<StroInvoicingDTO> saveStock(Map map) {
        synchronized (StroStockBOImpl.class) {
            List<StroInvoicingDTO> invoicingList = null;
            try {
                //检查参数
                checkPar(map);
                if(EnumUtil.CRFS1.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS2.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS20.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS21.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS22.getKey().equals(map.get("type").toString())){
                    //入库
                    invoicingList = this.saveInStock(map);
                } else if(EnumUtil.CRFS3.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS4.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS5.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS6.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS9.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS10.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS23.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS27.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS26.getKey().equals(map.get("type").toString())){
                    //出库
                    invoicingList = this.saveOutStock(map);
                } else if(EnumUtil.CRFS7.getKey().equals(map.get("type").toString())
                        || EnumUtil.CRFS8.getKey().equals(map.get("type").toString())) {
                    //盘点/报损
                    invoicingList = this.saveInventoryStock(map);
                } else if (EnumUtil.CRFS24.getKey().equals(map.get("type").toString())) {
                  // 调价进销存添加
                  invoicingList = this.insertStroInvoicing(map);
                } else if (EnumUtil.CRFS25.getKey().equals(map.get("type").toString()) || EnumUtil.CRFS28.getKey().equals(map.get("type").toString())) {
                  // 药房退药
                  invoicingList = this.inventorySurplus(map);
                }
                //核对库存数量
                this.checkStock(map);
            } catch (Exception e) {
                throw new AppException("出入库失败:"+e.getMessage());
            }
            return invoicingList;
        }
    }

    /**
     * 保存入库库存
     *      1：判断库存表是否存在
     *      2：没有新增、有修改
     *      3：库存明细直接插入信息
     * @param map
     * @return
     */
    public List<StroInvoicingDTO> saveInStock(Map map){
        List<StroStockDetailDTO> stroStockDetailDTOList = (List<StroStockDetailDTO>) map.get("stroStockDetailDTOList");
        //台账集合
        List<StroInvoicingDTO> stroInvoicingDTOList = new ArrayList<>();
        //医院编码
        String hospCode = map.get("hospCode").toString();
        // 入库记录
        List<StroInRecordDTO> stroInRecordDTOS = new ArrayList<>();

        for(StroStockDetailDTO stroStockDetailDTO : stroStockDetailDTOList){
            //修改库存数量
            List<StroStockDTO> stroStockDTOUpdate = new ArrayList<>();
            //新增库存数量
            List<StroStockDTO> stroStockDTOAdd = new ArrayList<>();

            //拆零数量如果出现小数四舍五入
            stroStockDetailDTO.setSplitNum(BigDecimal.valueOf(Math.round(stroStockDetailDTO.getSplitNum().doubleValue())));

            // 根据拆零数量查询出数量  保留四位小数
            stroStockDetailDTO.setNum(BigDecimalUtils.divide(stroStockDetailDTO.getSplitNum(),stroStockDetailDTO.getSplitRatio()));

            StroInvoicingDTO stroInvoicingDTO = new StroInvoicingDTO();
            //医院
            stroStockDetailDTO.setHospCode(hospCode);
            //新增主键
            stroStockDetailDTO.setId(SnowflakeUtils.getId());

            //获取库存主表信息
            StroStockDTO stroStockDTO = stroStockDao.getStock(stroStockDetailDTO);
            //是否存在库存
            if(stroStockDTO != null) {
                //上期/期初数量
                stroInvoicingDTO.setUpSurplusNum(BigDecimalUtils.divide(stroStockDTO.getSplitNum(),stroStockDTO.getSplitRatio()));
                //上期/期初购进总金额
                stroInvoicingDTO.setUpBuyPriceAll(stroStockDTO.getBuyPriceAll());
                //上期/期初零售总金额
                stroInvoicingDTO.setUpSellPriceAll(stroStockDTO.getSellPriceAll());
                //库存拆零总数量
                stroStockDTO.setSplitNum(BigDecimalUtils.add(stroStockDTO.getSplitNum(), stroStockDetailDTO.getSplitNum()));
                //库存总数量
                stroStockDTO.setNum(BigDecimalUtils.divide(stroStockDTO.getSplitNum(),stroStockDTO.getSplitRatio()));
                //购进价总价格(上期购进总金额+本期购进单价*数量)
                stroStockDTO.setBuyPriceAll(BigDecimalUtils.add(BigDecimalUtils.multiply(stroStockDetailDTO.getNum(),stroStockDetailDTO.getBuyPrice()), stroStockDTO.getBuyPriceAll()));
                if(EnumUtil.CRFS24.getKey().equals(map.get("type").toString())){
                  // 如果是调价则用总数量 * 金额
                  stroStockDTO.setSellPriceAll(BigDecimalUtils.multiply(stroStockDTO.getNum(),stroStockDetailDTO.getSellPrice()));
                } else {
                  //零售总价格(上期购进总金额+本期零售单价*数量)
                  stroStockDTO.setSellPriceAll(BigDecimalUtils.add(BigDecimalUtils.multiply(stroStockDetailDTO.getNum(),stroStockDetailDTO.getSellPrice()), stroStockDTO.getSellPriceAll()));
                }
                //拆零单价
                stroStockDTO.setSplitPrice(stroStockDTO.getSellPriceAll());
                stroStockDTOUpdate.add(stroStockDTO);
            }else{
                stroStockDTO = new StroStockDTO();
                //主表赋值
                stroStockDTO = this.setDetailValue(stroStockDTO, stroStockDetailDTO);
                stroStockDTOAdd.add(stroStockDTO);
            }
            //获取批次数量
            StroStockDetailDTO StockDetailBatchNoNum = stroStockDao.getStockDetailBatchNoNum(stroStockDetailDTO);
            //批号结余数量
            stroInvoicingDTO.setBatchSurplusNum(BigDecimalUtils.add(stroStockDetailDTO.getNum(),
                    StockDetailBatchNoNum==null?BigDecimal.valueOf(0):StockDetailBatchNoNum.getNum()));
            //入库数量
            stroInvoicingDTO.setNum(stroStockDetailDTO.getNum());
            //拆零数量stro/stroInvoicing
            stroInvoicingDTO.setSplitNum(stroStockDetailDTO.getSplitNum());
            //结余数量
            stroInvoicingDTO.setSurplusNum(stroStockDTO.getNum());
            //出入库方式
            stroInvoicingDTO.setOutinCode(map.get("type").toString());
            //本期/期末数量
            stroInvoicingDTO.setSurplusNum(stroStockDTO.getNum());
            //本期/期末购进总金额
            stroInvoicingDTO.setBuyPriceAll(stroStockDTO.getBuyPriceAll());
            //本期/期末零售总金额
            stroInvoicingDTO.setSellPriceAll(stroStockDTO.getSellPriceAll());
            //时间戳
            stroStockDetailDTO.setCrteTimeStamp(System.nanoTime());
            // 库存单价
            stroInvoicingDTO.setNewPrice(stroStockDetailDTO.getSellPrice());
            // 库存拆零单价
            stroInvoicingDTO.setNewSplitPrice(stroStockDetailDTO.getSplitPrice());
            //台账数据
            stroInvoicingDTOList.add(this.setInvoicingValue(stroInvoicingDTO, stroStockDetailDTO));

            if(!EnumUtil.CRFS24.getKey().equals(map.get("type").toString())) {
                //更新库存主表数量
                if(!ListUtils.isEmpty(stroStockDTOUpdate)){
                    stroStockDao.updateStock(stroStockDTOUpdate);
                }
                //插入库存主表
                if(!ListUtils.isEmpty(stroStockDTOAdd)){
                    stroStockDao.insertStock(stroStockDTOAdd);
                }
            }
            // 如果是入库，插入入库记录
            if(EnumUtil.CRFS2.getKey().equals(map.get("type").toString())){
              StroInRecordDTO stroInRecordDtO = new StroInRecordDTO();
              // 主键
              stroInRecordDtO.setId(SnowflakeUtils.getId());
              // 医院编码
              stroInRecordDtO.setHospCode(stroStockDetailDTO.getHospCode());
              //库存id
              stroInRecordDtO.setStockId(stroStockDTO.getId());
              // 库存明细id
              stroInRecordDtO.setStockDetailId(stroStockDetailDTO.getId());
              // 项目id(药品/材料)
              stroInRecordDtO.setItemId(stroStockDetailDTO.getItemId());
              // 项目类别
              stroInRecordDtO.setItemCode(stroStockDetailDTO.getItemCode());
              // 购进价
              stroInRecordDtO.setBuyPrice(stroStockDetailDTO.getBuyPrice());
              // 入库数量
              stroInRecordDtO.setNum(stroStockDetailDTO.getNum());
              // 零售价
              stroInRecordDtO.setSellPrice(stroStockDetailDTO.getSellPrice());
              // 创建时间
              stroInRecordDtO.setCrteTime(DateUtils.getNow());
              stroInRecordDTOS.add(stroInRecordDtO);
            }
        }
        if(!EnumUtil.CRFS24.getKey().equals(map.get("type").toString())) {
            //入库插入明细
            if(!ListUtils.isEmpty(stroStockDetailDTOList)){
                stroStockDao.insertStockDetail(stroStockDetailDTOList);
            }
        }
        if(EnumUtil.CRFS2.getKey().equals(map.get("type").toString()) && !ListUtils.isEmpty(stroInRecordDTOS)){
            stroStockDao.insertStroStock(stroInRecordDTOS);
        }
        //保存台账
        if (!ListUtils.isEmpty(stroInvoicingDTOList)) {
            stroInvoicingDAO.insertInvoicing(stroInvoicingDTOList);
        }
        return stroInvoicingDTOList;
    }

    /**
     * 保存出库数据
     *  1：判断库存表是否存在数据
     *  2：判断库存大小
     *  3：扣减库存主表数量
     *  4：减少库存明细表数量（先进先出减少）
     * @param map
     * @return
     */
    public List<StroInvoicingDTO> saveOutStock(Map map){
        String sfBatchNo = null;
        List<StroStockDetailDTO> stroStockDetailDTOList = (List<StroStockDetailDTO>) map.get("stroStockDetailDTOList");
        String hospCode = map.get("hospCode").toString();
        //台账
        List<StroInvoicingDTO> invoicingList = new ArrayList<>();
        //盘点是否启动批号
        if(map.get("sfBatchNo") != null){
            sfBatchNo = map.get("sfBatchNo").toString();
        }

        for(StroStockDetailDTO stroStockDetailDTO : stroStockDetailDTOList){
            // 退供应商 、退库， 盘点单出库 不用过滤有效期
            if ("3".equals(map.get("type")) ||"7".equals(map.get("type")) ||"8".equals(map.get("type")) ||"6".equals(map.get("type")) ){
                stroStockDetailDTO.setIsExpiryDate("1");
            }
            //拆零数量如果出现小数四舍五入
            stroStockDetailDTO.setSplitNum(BigDecimal.valueOf(Math.round(stroStockDetailDTO.getSplitNum().doubleValue())));

            // 根据拆零数量查询出数量  保留四位小数
            stroStockDetailDTO.setNum(BigDecimalUtils.divide(stroStockDetailDTO.getSplitNum(),stroStockDetailDTO.getSplitRatio()));
            stroStockDetailDTO.setHospCode(hospCode);
            //获取库存信息
            StroStockDTO stroStockDTO = stroStockDao.getStock(stroStockDetailDTO);
            if(stroStockDTO == null){
                throw new AppException("没有库存信息，请先入库");
            }
            //校验库存不足,抛异常
            if(BigDecimalUtils.compareTo(stroStockDTO.getSplitNum(),stroStockDetailDTO.getSplitNum())<0){
                throw new AppException(stroStockDetailDTO.getItemName()+",库存数量不足");
            }

            List<StroStockDetailDTO> stockDetailOutList = new ArrayList<>();
            List<StroStockDTO> stroStockDTOUpdate = new ArrayList<>();

            //是否启用批号出库
            stroStockDetailDTO.setSfBatchNo(sfBatchNo);
            //获取库存明细减少数量
            Map stroStockDetai = this.getOutDetailDTO(stroStockDetailDTO);
            //库存明细
            stockDetailOutList.addAll((Collection<? extends StroStockDetailDTO>) stroStockDetai.get("stockDetailOutList"));
            //台账记录
            List<StroStockDetailDTO> invoicingOutList = (List<StroStockDetailDTO>) stroStockDetai.get("invoicingOutList");
            //出库台账明细
            invoicingList.addAll(this.getStroInvoicingDTOList(invoicingOutList,stroStockDetailDTO,map,stroStockDTO.getSplitNum(), stroStockDTO));

            //库存拆零总数量
            stroStockDTO.setSplitNum(BigDecimalUtils.subtract(stroStockDTO.getSplitNum(), stroStockDetailDTO.getSplitNum()));
            // 根据拆零数量算出数量
            stroStockDTO.setNum(BigDecimalUtils.divide(stroStockDTO.getSplitNum(),stroStockDTO.getSplitRatio()));
            //购进价总价格=上期金额-出库数量对应的购进总金额
            stroStockDTO.setBuyPriceAll(BigDecimalUtils.multiply(stroStockDTO.getNum(),stroStockDTO.getBuyPrice()));
            //零售总价格
            stroStockDTO.setSellPriceAll(BigDecimalUtils.multiply(stroStockDTO.getNum(),stroStockDTO.getSellPrice()));
            //拆零总价格
            stroStockDTO.setSplitPrice(stroStockDTO.getSellPriceAll());
            //库存主表
            stroStockDTOUpdate.add(stroStockDTO);

            //更新库存主表数量
            if (!ListUtils.isEmpty(stroStockDTOUpdate)) {
                stroStockDao.updateStock(stroStockDTOUpdate);
            }
            //更新库存明细数量
            if (!ListUtils.isEmpty(stockDetailOutList)) {
                stroStockDao.updateStockDetail(stockDetailOutList);
            }
        }
        //保存台账
        if (!ListUtils.isEmpty(invoicingList)) {
            stroInvoicingDAO.insertInvoicing(invoicingList);
        }
        return invoicingList;
    }

    /**
     * @Method: getStroStock
     * @Description: 校验库存
     * @Param: [map] 参数：itemId:项目ID,itemCode:项目编码,stockNum:需要占用库存数量
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/12 17:19
     * @Return: java.lang.Boolean true:库存足够，false:库存不足
     **/
    @Override
    public Boolean getStroStock(Map map) {
        if(MapUtils.isEmpty(map)) {
            throw new AppException("参数为空");
        }
        //校验参数
        if(cn.hsa.util.StringUtils.isEmpty(MapUtils.getString(map, "itemId"))) {
            throw new AppException("项目ID不能为空");
        }
        if(cn.hsa.util.StringUtils.isEmpty(MapUtils.getString(map, "itemCode"))) {
            throw new AppException("项目编码不能为空");
        }
        if(cn.hsa.util.StringUtils.isEmpty(MapUtils.getString(map, "bizId"))) {
            throw new AppException("库房ID不能为空");
        }
        if(cn.hsa.util.StringUtils.isEmpty(MapUtils.getString(map, "hospCode"))) {
            throw new AppException("医院编码不能为空");
        }
        if(cn.hsa.util.StringUtils.isEmpty(MapUtils.getString(map, "stockNum"))) {
            throw new AppException("数量不能为空");
        }

        Integer num = stroStockDao.getStroStock(map);
        if (num == null) {
          if(!StringUtils.isEmpty(MapUtils.getString(map, "itemName"))){
            throw new AppException(MapUtils.getString(map, "itemName") + "库存不存在,或药品已过期");
          } else {
            throw new AppException( "库存不存在,或已经过期");
          }

        }
        return num>=0;
    }

    /**
     * @Method: checkStock
     * @Description: 根据库存明细表更新库存主表数量
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/8 13:57
     * @Return: void
     **/
    private void checkStock(Map map) {
        //获取现在数据库中的库存明细数量
        List<StroStockDetailDTO> stroStockDetailDTOList = (List<StroStockDetailDTO>) map.get("stroStockDetailDTOList");
        if (!ListUtils.isEmpty(stroStockDetailDTOList)) {
            stroStockDao.updateStockByDetail(stroStockDetailDTOList);
        }
    }

    /**
     * @Method: updateStockOccupy
     * @Description: 占用库存/解除占用库存(stockNum为负数)
     * @Param: [list] 参数：itemId:项目ID,itemCode:项目编码,stockNum:需要占用库存数量,hospCode:医院编码,bizId:库位ID
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/14 8:58
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean updateStockOccupy(List<Map<String, Object>> list) {
        if (ListUtils.isEmpty(list)) {
            throw new AppException("参数为空");
        }
        return stroStockDao.updateStockOccupy(list)>0;
    }

    /**
     * 盘点单出入库
     *
     * @param map
     * @return
     */
    public List<StroInvoicingDTO> saveInventoryStock(Map map){
        //台账
        List<StroInvoicingDTO> invoicingList = new ArrayList<>();
        List<StroStockDetailDTO> stroStockDTOIn = new ArrayList<>();
        List<StroStockDetailDTO> stroStockDTOOut = new ArrayList<>();

        List<StroStockDetailDTO> stroStockDetailDTOList = (List<StroStockDetailDTO>) map.get("stroStockDetailDTOList");
        for(StroStockDetailDTO stroStockDetailDTO : stroStockDetailDTOList){
            //小于0为出库，大于0为入库
            if(BigDecimalUtils.compareTo(stroStockDetailDTO.getNum(), BigDecimal.valueOf(0)) < 0){
                stroStockDetailDTO.setNum(BigDecimalUtils.multiply(stroStockDetailDTO.getNum(), BigDecimal.valueOf(-1)));
                stroStockDetailDTO.setSplitNum(BigDecimalUtils.multiply(stroStockDetailDTO.getSplitNum(), BigDecimal.valueOf(-1)));
                stroStockDTOOut.add(stroStockDetailDTO);
            }else{
                stroStockDTOIn.add(stroStockDetailDTO);
            }
        }

        if(!ListUtils.isEmpty(stroStockDTOIn)){
            map.put("stroStockDetailDTOList",stroStockDTOIn);
            // 盘点盘盈操作
            invoicingList = this.inventorySurplus(map);
        }

        if(!ListUtils.isEmpty(stroStockDTOOut)){
            map.put("stroStockDetailDTOList",stroStockDTOOut);
            //出库操作
            invoicingList = this.saveOutStock(map);
        }
        return invoicingList;
    }

    /**
    * @Menthod InventorySurplus
    * @Desrciption 盘点盘盈,药房退库
    * 盘盈，报溢
    * 1.修改库存明细数量：查出库存明细中该项目的最新批号,往该批号加上盘盈的数量，计算金额的变化
    * 2.修改库存数量： 查出该项目的库存总数量  加上盘盈的数量，计算金额变化
    * 3.新增台账信息
    * 药房退药：
    * 1.发药数据带过来的库存明细id查出库存明细表数据，直接对该条数据进行库存数量的计算，然后进行更新
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2021/4/29 16:10
    * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
    **/
    List<StroInvoicingDTO> inventorySurplus(Map map) {
      String sfBatchNo = null;
      List<StroStockDetailDTO> stroStockDetailDTOList = (List<StroStockDetailDTO>) map.get("stroStockDetailDTOList");
      //台账集合
      List<StroInvoicingDTO> stroInvoicingDTOList = new ArrayList<>();
      //医院编码
      String hospCode = map.get("hospCode").toString();
      // 修改库存明细集合
      List<StroStockDetailDTO> stroStockDetailDTOS = new ArrayList<>();
      //盘点是否启动批号
      if(map.get("sfBatchNo") != null){
        sfBatchNo = map.get("sfBatchNo").toString();
      }
      // 循环
      for (StroStockDetailDTO stroStockDetailDTO: stroStockDetailDTOList) {
        //修改库存数量
        List<StroStockDTO> stroStockDTOUpdate = new ArrayList<>();

        //拆零数量如果出现小数四舍五入取整
        stroStockDetailDTO.setSplitNum(BigDecimal.valueOf(Math.round(stroStockDetailDTO.getSplitNum().doubleValue())));

        // 根据拆零数量查询出数量  保留四位小数
        stroStockDetailDTO.setNum(BigDecimalUtils.divide(stroStockDetailDTO.getSplitNum(),stroStockDetailDTO.getSplitRatio()));

        StroInvoicingDTO stroInvoicingDTO = new StroInvoicingDTO();
        //医院
        stroStockDetailDTO.setHospCode(hospCode);

        //获取库存主表信息
        StroStockDTO stroStockDTO = stroStockDao.getStock(stroStockDetailDTO);

        //是否存在库存
        if(stroStockDTO != null) {
          stroStockDetailDTO.setSfBatchNo(sfBatchNo);
          // 查出该项目在库存明细表中的所
          List<StroStockDetailDTO> stockDetail = new ArrayList<>();
          // 最新入库的库存明细记录
          StroStockDetailDTO maxStockDetailDTO = new StroStockDetailDTO();
          if(EnumUtil.CRFS25.getKey().equals(map.get("type").toString()) || EnumUtil.CRFS28.getKey().equals(map.get("type").toString())) {
            maxStockDetailDTO = stroStockDetailDao.getStroStockDetailById(stroStockDetailDTO.getId(),hospCode);
          } else {
            stockDetail = stroStockDao.getStockDetail1(stroStockDetailDTO);
            if(ListUtils.isEmpty(stockDetail)) {
              throw new AppException(stroStockDetailDTO.getItemName() + "未找到库存信息");
            }
            maxStockDetailDTO = stockDetail.get(stockDetail.size() - 1);
          }
          //获取批号数量
          StroStockDetailDTO StockDetailBatchNoNum = stroStockDao.getStockDetailBatchNoNum(maxStockDetailDTO);
          //批号结余数量
          stroInvoicingDTO.setBatchSurplusNum(BigDecimalUtils.add(stroStockDetailDTO.getNum(),
            StockDetailBatchNoNum==null?BigDecimal.valueOf(0):StockDetailBatchNoNum.getNum()));
          // 计算该批号盘盈后的拆零数量
          maxStockDetailDTO.setSplitNum(BigDecimalUtils.add(stroStockDetailDTO.getSplitNum(),maxStockDetailDTO.getSplitNum()));
          // 根据拆零数量反推出数量
          maxStockDetailDTO.setNum(BigDecimalUtils.divide(maxStockDetailDTO.getSplitNum(),maxStockDetailDTO.getSplitRatio()));
          // 计算零售总金额
          maxStockDetailDTO.setSellPriceAll(BigDecimalUtils.multiply(maxStockDetailDTO.getNum(),maxStockDetailDTO.getSellPrice()));
          // 计算购进总金额
          maxStockDetailDTO.setBuyPriceAll(BigDecimalUtils.multiply(maxStockDetailDTO.getNum(),maxStockDetailDTO.getBuyPrice()));

          //上期/期初数量
          stroInvoicingDTO.setUpSurplusNum(BigDecimalUtils.divide(stroStockDTO.getSplitNum(),stroStockDTO.getSplitRatio()));
          //上期/期初购进总金额
          stroInvoicingDTO.setUpBuyPriceAll(stroStockDTO.getBuyPriceAll());
          //上期/期初零售总金额
          stroInvoicingDTO.setUpSellPriceAll(stroStockDTO.getSellPriceAll());
          // 批号
          stroInvoicingDTO.setBatchNo(maxStockDetailDTO.getBatchNo());
          // 计算该项目盘盈后的库存总拆零数量
          stroStockDTO.setSplitNum(BigDecimalUtils.add(stroStockDetailDTO.getSplitNum(),stroStockDTO.getSplitNum()));
          // 根据拆零数量反推出数量
          stroStockDTO.setNum(BigDecimalUtils.divide(stroStockDTO.getSplitNum(),stroStockDTO.getSplitRatio()));
          // 计算购进总金额
          stroStockDTO.setBuyPriceAll(BigDecimalUtils.multiply(stroStockDTO.getNum(),stroStockDTO.getBuyPrice()));
          // 计算零售总金额
          stroStockDTO.setSellPriceAll(BigDecimalUtils.multiply(stroStockDTO.getNum(),stroStockDTO.getSellPrice()));

          stroStockDTOUpdate.add(stroStockDTO);
          //入库数量
          stroInvoicingDTO.setNum(stroStockDetailDTO.getNum());
          //结余数量
          stroInvoicingDTO.setSurplusNum(stroStockDTO.getNum());
          //拆零数量
          stroInvoicingDTO.setSplitNum(stroStockDetailDTO.getSplitNum());
          //出入库方式
          stroInvoicingDTO.setOutinCode(map.get("type").toString());
          //本期/期末数量
          stroInvoicingDTO.setSurplusNum(stroStockDTO.getNum());
          //本期/期末购进总金额
          stroInvoicingDTO.setBuyPriceAll(stroStockDTO.getBuyPriceAll());
          //本期/期末零售总金额
          stroInvoicingDTO.setSellPriceAll(stroStockDTO.getSellPriceAll());
          //时间戳
          stroStockDetailDTO.setCrteTimeStamp(System.nanoTime());
          // 库存单价
          stroInvoicingDTO.setNewPrice(stroStockDetailDTO.getNewPrice());
          // 库存拆零单价
          stroInvoicingDTO.setNewSplitPrice(stroStockDetailDTO.getNewSplitPrice());
          StroInvoicingDTO stroInvoicingDTO1 = this.setInvoicingValue(stroInvoicingDTO, stroStockDetailDTO);
          // 批号
          stroInvoicingDTO1.setBatchNo(maxStockDetailDTO.getBatchNo());
          // 有效期
          stroInvoicingDTO1.setExpiryDate(maxStockDetailDTO.getExpiryDate());
          //台账数据
          stroInvoicingDTOList.add(stroInvoicingDTO1);
          // 把该库存明细加入修改列表
          stroStockDetailDTOS.add(maxStockDetailDTO);
          //修改库存明细数据
          if (!ListUtils.isEmpty(stroStockDetailDTOS)) {
            stroStockDao.updateStockDetail(stroStockDetailDTOS);
          }
          //更新库存主表数量
          if(!ListUtils.isEmpty(stroStockDTOUpdate)){
            stroStockDao.updateStock(stroStockDTOUpdate);
          }
        } else {
          throw new AppException(stroStockDetailDTO.getItemName() + "库存不存在");
        }
      }
      //保存台账
      if (!ListUtils.isEmpty(stroInvoicingDTOList)) {
        stroInvoicingDAO.insertInvoicing(stroInvoicingDTOList);
      }
      return stroInvoicingDTOList;
    }

    /**
     * @Menthod insertStroInvoicing
     * @Desrciption  新增调价进销存
     *  1.查询各项目的库存明细信息
     *  2.根据库位id给查询出的库存明细进行分组
     *  3.循环遍历库存明细，分别给各库位每条明细库存，添加进销存记录
     *  4.调价中的进销存的数据 ，库存单价和零售单价都是最新的单价。零售单价也是如此
     * @Param
     * []
     *
     * @Author jiahong.yang
     * @Date   2021/5/7 9:58
     * @Return boolean
     **/
    List<StroInvoicingDTO>  insertStroInvoicing(Map map) {
      List<StroStockDetailDTO> stroStockDetailDTOList = (List<StroStockDetailDTO>) map.get("stroStockDetailDTOList");
      //台账集合
      List<StroInvoicingDTO> stroInvoicingDTOList = new ArrayList<>();
      //医院编码
      String hospCode = map.get("hospCode").toString();
      String sfdeptFilter = MapUtils.getString(map,"sfdeptFilter","0");
      for (StroStockDetailDTO stroStockDetailDTO : stroStockDetailDTOList) {
        stroStockDetailDTO.setSfdeptFilter(sfdeptFilter);
        // 查出该项目id的所有库存明细信息
        List<StroStockDetailDTO> stroStockDetailDTOS = stroStockDao.queryStoclDetailByItemIds(stroStockDetailDTO);
        // 根据库位id进行分组
        Map<String, List<StroStockDetailDTO>> collect = stroStockDetailDTOS.stream().collect(Collectors.groupingBy(o -> o.getBizId()));
        for (String key : collect.keySet()) {
          StroStockDetailDTO queryStock = new StroStockDetailDTO();
          // 医院编码
          queryStock.setHospCode(hospCode);
          // 项目id
          queryStock.setItemId(stroStockDetailDTO.getItemId());
          // 库位id
          queryStock.setBizId(key);
          // 项目编码
          queryStock.setItemCode(stroStockDetailDTO.getItemCode());
          // 获取该库位库存主表信息
          StroStockDTO stroStockDTO = stroStockDao.getStock(queryStock);
          if(stroStockDTO == null) {
            throw  new AppException("库存不存在");
          }
          // 获取同库位下该项目的库存明细
          List<StroStockDetailDTO> stroStockDetailDTOS1 = collect.get(key);
//          Map<String, BigDecimal> sumBatchNum = stroStockDetailDTOS1.stream().collect(Collectors.groupingBy(StroStockDetailDTO::getBatchNo,
//            Collectors.mapping(StroStockDetailDTO::getNum, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
          // 初始化本期零售总金额
          BigDecimal sellPriceAll = stroStockDTO.getSellPriceAll();
          for (StroStockDetailDTO itemStockDetail : stroStockDetailDTOS1) {
            // 进销存目标id
            itemStockDetail.setInvoicingTargetId(key);
            StroInvoicingDTO stroInvoicingDTO = new StroInvoicingDTO();
            //上期/期初数量
            stroInvoicingDTO.setUpSurplusNum(stroStockDTO.getNum());
            //上期/期初购进总金额
            stroInvoicingDTO.setUpBuyPriceAll(stroStockDTO.getBuyPriceAll());
            //上期/期初零售总金额
            stroInvoicingDTO.setUpSellPriceAll(sellPriceAll);
            //本期/期末数量
            stroInvoicingDTO.setSurplusNum(stroStockDTO.getNum());
            //本期/本期购进总金额
            stroInvoicingDTO.setBuyPriceAll(stroStockDTO.getBuyPriceAll());
            // 调价差
            BigDecimal divide = BigDecimalUtils.subtract(stroStockDetailDTO.getSellPrice(), stroStockDTO.getSellPrice());
            // 调价前后该明细金额变化
            BigDecimal multiply = BigDecimalUtils.multiply(itemStockDetail.getNum(), divide);
            // 记录本期零售总金额，同时作为下条记录的上期零售总金额,  库存零售总金额 + 调价后该该条明细变化的金额
            sellPriceAll = BigDecimalUtils.add(sellPriceAll,multiply);
            //本期/本期零售总金额
            stroInvoicingDTO.setSellPriceAll(sellPriceAll);
            // 数量
            stroInvoicingDTO.setNum(stroStockDetailDTO.getNum());
            // 拆零数量
            stroInvoicingDTO.setSplitNum(stroStockDetailDTO.getSplitNum());
            // 批号结余数量
            stroInvoicingDTO.setBatchSurplusNum(itemStockDetail.getNum());
//            if(sumBatchNum.containsKey(itemStockDetail.getBatchNo())){
//              // 批号结余数量
//              stroInvoicingDTO.setBatchSurplusNum(sumBatchNum.get(itemStockDetail.getBatchNo()));
//            }
            //出入库方式
            stroInvoicingDTO.setOutinCode(map.get("type").toString());
            // 插入其他参数
            StroInvoicingDTO newStroInvoicing = this.setInvoicingValue(stroInvoicingDTO, itemStockDetail);
            // 调价后单价
            newStroInvoicing.setSellPrice(stroStockDetailDTO.getNewPrice());
            //创建人ID
            newStroInvoicing.setCrteId(stroStockDetailDTO.getCrteId());
            // 创建时间
            newStroInvoicing.setCrteTime(DateUtils.getNow());
            // 单号
            newStroInvoicing.setOrderNo(stroStockDetailDTO.getOrderNo());
            //创建人
            newStroInvoicing.setCrteName(stroStockDetailDTO.getCrteName());
            // 时间戳
            newStroInvoicing.setCrteTimeStamp(System.nanoTime());
            // 拆零单价
            newStroInvoicing.setSplitPrice(stroStockDetailDTO.getNewSplitPrice());
            // 库存拆零单价 -- new_split_price
            newStroInvoicing.setNewSplitPrice(stroStockDetailDTO.getNewSplitPrice());
            // 库存单价 -- new_price
            newStroInvoicing.setNewPrice(stroStockDetailDTO.getNewPrice());
            stroInvoicingDTOList.add(newStroInvoicing);
          }
        }
      }
      if(!ListUtils.isEmpty(stroInvoicingDTOList)) {
        // 插入补记帐信息
        stroInvoicingDAO.insertInvoicing(stroInvoicingDTOList);
      }
      return stroInvoicingDTOList;
    }
    /**
     * 库存主表赋值
     * @param stroStockDTO 库存主表DTO
     * @param stroStockDetailDTO 库存明细DTO
     * @return
     */
    public StroStockDTO setDetailValue(StroStockDTO stroStockDTO, StroStockDetailDTO stroStockDetailDTO){
        //主键
        stroStockDTO.setId(SnowflakeUtils.getId());
        //库房ID
        stroStockDTO.setBizId(stroStockDetailDTO.getBizId());
        //医院编号
        stroStockDTO.setHospCode(stroStockDetailDTO.getHospCode());
        //项目类型代码
        stroStockDTO.setItemCode(stroStockDetailDTO.getItemCode());
        //项目ID
        stroStockDTO.setItemId(stroStockDetailDTO.getItemId());
        //项目名称
        stroStockDTO.setItemName(stroStockDetailDTO.getItemName());
        //规格
        stroStockDTO.setSpec(stroStockDetailDTO.getSpec());
        //剂型代码
        stroStockDTO.setPrepCode(stroStockDetailDTO.getPrepCode());
        //单位
        stroStockDTO.setUnitCode(stroStockDetailDTO.getUnitCode());
        //拆零单位
        stroStockDTO.setSplitUnitCode(stroStockDetailDTO.getSplitUnitCode());
        //库存总数量
        stroStockDTO.setNum(stroStockDetailDTO.getNum());
        //库存拆零总数量
        stroStockDTO.setSplitNum(stroStockDetailDTO.getSplitNum());
        //购进价总价格
        stroStockDTO.setBuyPriceAll(stroStockDetailDTO.getBuyPriceAll());
        //零售总价格
        stroStockDTO.setSellPriceAll(stroStockDetailDTO.getSellPriceAll());
        //拆零总价格
        stroStockDTO.setSplitPrice(stroStockDetailDTO.getSplitPrice());
        //拆分比
        stroStockDTO.setSplitRatio(stroStockDetailDTO.getSplitRatio());
        return stroStockDTO;
    }

    /**
     * 进销存台账赋值
     * @param stroInvoicingDTO 进销存主表DTO
     * @param stroStockDetailDTO 库存明细DTO
     * @return
     */
    public StroInvoicingDTO setInvoicingValue(StroInvoicingDTO stroInvoicingDTO, StroStockDetailDTO stroStockDetailDTO){
        //主键
        stroInvoicingDTO.setId(SnowflakeUtils.getId());
        //医院编号
        stroInvoicingDTO.setHospCode(stroStockDetailDTO.getHospCode());
        //单据号
        stroInvoicingDTO.setOrderNo(stroStockDetailDTO.getOrderNo());
        //库房ID
        stroInvoicingDTO.setBizId(stroStockDetailDTO.getBizId());
        // 进销存目标id
        stroInvoicingDTO.setInvoicingTargetId(stroStockDetailDTO.getInvoicingTargetId());
        // 进销存目标名称
        stroInvoicingDTO.setInvoicingTargetName(stroStockDetailDTO.getInvoicingTargetName());
        //项目类型代码
        stroInvoicingDTO.setItemCode(stroStockDetailDTO.getItemCode());
        //项目ID
        stroInvoicingDTO.setItemId(stroStockDetailDTO.getItemId());
        //项目名称
        stroInvoicingDTO.setItemName(stroStockDetailDTO.getItemName());
        // 单位
        stroInvoicingDTO.setUnitCode(stroStockDetailDTO.getUnitCode());
        //拆零单位
        stroInvoicingDTO.setSplitUnitCode(stroStockDetailDTO.getSplitUnitCode());
        //拆分比
        stroInvoicingDTO.setSplitRatio(stroStockDetailDTO.getSplitRatio());
        //有效期
        stroInvoicingDTO.setExpiryDate(stroStockDetailDTO.getExpiryDate());
        //创建人ID
        stroInvoicingDTO.setCrteId(stroStockDetailDTO.getCrteId());
        //创建人
        stroInvoicingDTO.setCrteName(stroStockDetailDTO.getCrteName());
        //零售价
        stroInvoicingDTO.setSellPrice(stroStockDetailDTO.getSellPrice());
        //购进价
        stroInvoicingDTO.setBuyPrice(stroStockDetailDTO.getBuyPrice());
        //拆零单价
        stroInvoicingDTO.setSplitPrice(stroStockDetailDTO.getSplitPrice());
        //批号
        stroInvoicingDTO.setBatchNo(stroStockDetailDTO.getBatchNo());
        //当前单位（没有默认大单位）
        if(StringUtils.isEmpty(stroStockDetailDTO.getCurrUnitCode())){
            stroInvoicingDTO.setCurrUnitCode(stroStockDetailDTO.getUnitCode());
        }else{
            stroInvoicingDTO.setCurrUnitCode(stroStockDetailDTO.getCurrUnitCode());
        }
        //处方ID
        stroInvoicingDTO.setOpId(stroStockDetailDTO.getOpId());
        //处方明细ID
        stroInvoicingDTO.setOpdId(stroStockDetailDTO.getOpdId());
        //费用ID
        stroInvoicingDTO.setCostId(stroStockDetailDTO.getCostId());
        //规格
        stroInvoicingDTO.setSpec(stroStockDetailDTO.getSpec());
        //剂量
        stroInvoicingDTO.setDosage(stroStockDetailDTO.getDosage());
        //剂量单位代码
        stroInvoicingDTO.setDosageUnitCode(stroStockDetailDTO.getDosageUnitCode());
        //中药付数
        stroInvoicingDTO.setChineseDrugNum(stroStockDetailDTO.getChineseDrugNum());
        //用法代码
        stroInvoicingDTO.setUsageCode(stroStockDetailDTO.getUsageCode());
        //用药性质代码
        stroInvoicingDTO.setUseCode(stroStockDetailDTO.getUseCode());
        //领药申请明细ID
        stroInvoicingDTO.setIrdId(stroStockDetailDTO.getIrdId());
        //医嘱ID
        stroInvoicingDTO.setAdviceId(stroStockDetailDTO.getAdviceId());
        //医嘱组号
        stroInvoicingDTO.setGroupNo(stroStockDetailDTO.getGroupNo());
        //就诊ID
        stroInvoicingDTO.setVisitId(stroStockDetailDTO.getVisitId());
        //婴儿ID
        stroInvoicingDTO.setBabyId(stroStockDetailDTO.getBabyId());
        // 发药明细汇总id
        stroInvoicingDTO.setDistributeAllDetailId(stroStockDetailDTO.getDistributeAllDetailId());
        //库存明细ID
        stroInvoicingDTO.setStockDetailId(stroStockDetailDTO.getId());
        //时间戳
        stroInvoicingDTO.setCrteTimeStamp(stroStockDetailDTO.getCrteTimeStamp());
        // 发药批次明细
        stroInvoicingDTO.setStroStockDetailDTOS(stroStockDetailDTO.getStroStockDetailDTOS());
        return stroInvoicingDTO;
    }


    /**
     * 判断获取库存明细更新数量（sfBatchNo：0不按批次：先进先出原则 1按批次：出库具体批次  ）
     * @param stroStockDetailDTO
     * @return
     */
    public Map getOutDetailDTO(StroStockDetailDTO stroStockDetailDTO) {
        //返回值
        Map storMap = new HashMap();
        //库存扣减明细
        List<StroStockDetailDTO> stockDetailOutList = new ArrayList<>();
        //库存扣减明细(台账)
        List<StroStockDetailDTO> invoicingOutList = new ArrayList<>();
        //扣减数量
        BigDecimal num = stroStockDetailDTO.getNum();
        //扣减拆零数量
        BigDecimal splitNum = stroStockDetailDTO.getSplitNum();
        //获取库存明细表
        List<StroStockDetailDTO> stroStockDetailOutList = stroStockDao.getStockDetail(stroStockDetailDTO);

        //（出库先进先出：明细表数量一条一条减少）
        for(StroStockDetailDTO stroStockDetailOut : stroStockDetailOutList){
            StroStockDetailDTO invoicingOut = new StroStockDetailDTO();
            try {
                // 把查出来的库存数据复制给 invoicingOut对象
                BeanUtils.copyProperties(invoicingOut,stroStockDetailOut);
            } catch (IllegalAccessException e) {
                throw new AppException("复制数据错误");
            } catch (InvocationTargetException e) {
                throw new AppException("复制数据错误");
            }
            //主键
            invoicingOut.setId(stroStockDetailOut.getId());
            //批号
            invoicingOut.setBatchNo(stroStockDetailOut.getBatchNo());
            stroStockDetailDTO.setBatchNo(stroStockDetailOut.getBatchNo());
            //判断出库数量是否大于库存明细批次的数量
            if(BigDecimalUtils.compareTo(splitNum, stroStockDetailOut.getSplitNum()) > 0) {
                //台账记账数量
                invoicingOut.setNum(stroStockDetailOut.getNum());
                //台账记账拆零数量
                invoicingOut.setSplitNum(stroStockDetailOut.getSplitNum());
                //获取台账同批号库存结余数量
                invoicingOutList = this.getInvoicingOutList(invoicingOut, stroStockDetailDTO, invoicingOutList);

                //出库数量减掉，在计算下条
                num = BigDecimalUtils.subtract(num, stroStockDetailOut.getNum());
                //出库数量减掉，在计算下条
                splitNum = BigDecimalUtils.subtract(splitNum, stroStockDetailOut.getSplitNum());
                //库存明细赋值0，不够
                stroStockDetailOut.setNum(BigDecimal.valueOf(0));
                //库存明细赋值0，不够
                stroStockDetailOut.setSplitNum(BigDecimal.valueOf(0));
                //库存明细赋值0，不够
                stroStockDetailOut.setSellPriceAll(BigDecimal.valueOf(0));
                //库存明细赋值0，不够
                stroStockDetailOut.setBuyPriceAll(BigDecimal.valueOf(0));
                //库存明细赋值0，不够
                stockDetailOutList.add(stroStockDetailOut);
            } else {
                //台账记账数量
                invoicingOut.setNum(num);
                //台账记账拆零数量
                invoicingOut.setSplitNum(splitNum);
                //获取台账同批号库存结余数量
                invoicingOutList = this.getInvoicingOutList(invoicingOut, stroStockDetailDTO, invoicingOutList);
                //库存明细剩余拆零数量
                stroStockDetailOut.setSplitNum(BigDecimalUtils.subtract(stroStockDetailOut.getSplitNum(), splitNum));
                //库存明细剩余数量
                stroStockDetailOut.setNum(BigDecimalUtils.divide(stroStockDetailOut.getSplitNum(), stroStockDetailOut.getSplitRatio()));
                //库存明细剩余总价格
                stroStockDetailOut.setSellPriceAll(BigDecimalUtils.multiply(stroStockDetailOut.getNum(), stroStockDetailOut.getSellPrice()));
                //库存明细剩余总价格
                stroStockDetailOut.setBuyPriceAll(BigDecimalUtils.multiply(stroStockDetailOut.getNum(),stroStockDetailOut.getBuyPrice()));
                stockDetailOutList.add(stroStockDetailOut);

                num = BigDecimal.valueOf(0);
                splitNum = BigDecimal.valueOf(0);
                break;
            }
        }
        if (num.compareTo(BigDecimal.valueOf(0)) > 0 || splitNum.compareTo(BigDecimal.valueOf(0)) > 0) {
            throw new AppException(stroStockDetailDTO.getItemName()+"["+stroStockDetailDTO.getBatchNo()+"]库存不足或者该药品已经过期");
        }
        //库存明细表
        storMap.put("stockDetailOutList",stockDetailOutList);
        //台账明细表
        storMap.put("invoicingOutList", invoicingOutList);
        return storMap;
    }

    /**
     * 获取判断同一个批号库存扣减数量
     * @param invoicingOut 实际扣减库存数据
     * @param stroStockDetailDTO 调用接口传入库存明细数量
     * @param invoicingOutList 台账list
     * @return
     */
    public List<StroStockDetailDTO> getInvoicingOutList(StroStockDetailDTO invoicingOut, StroStockDetailDTO stroStockDetailDTO,
                                                        List<StroStockDetailDTO> invoicingOutList ){
      boolean flag = true;
        if (!ListUtils.isEmpty(invoicingOutList)) {
            //判断台账数据是否生成
            for(StroStockDetailDTO stockDetailOut : invoicingOutList){
                //判断是否存在
                if(stockDetailOut.getBatchNo().equals(invoicingOut.getBatchNo())){
                    // 获取该批号下的批次信息
                    List<StroStockDetailDTO> stroStockDetailDTOS = stockDetailOut.getStroStockDetailDTOS();
                    // 添加参数细信息
                    StroStockDetailDTO newInvoicingOut = setParam(invoicingOut, stroStockDetailDTO);
                    // 把该批次信息添加到该批号下面
                    if(ListUtils.isEmpty(stroStockDetailDTOS)) {
                      stroStockDetailDTOS = new ArrayList<>();
                      stroStockDetailDTOS.add(newInvoicingOut);
                    }else {
                      stroStockDetailDTOS.add(newInvoicingOut);
                    }
                    stockDetailOut.setStroStockDetailDTOS(stroStockDetailDTOS);
                    //数量相加
                    stockDetailOut.setNum(BigDecimalUtils.add(invoicingOut.getNum(), stockDetailOut.getNum()));
                    //拆零数量数量相加
                    stockDetailOut.setSplitNum(BigDecimalUtils.add(invoicingOut.getSplitNum(), stockDetailOut.getSplitNum()));
                    flag = false;
                    break;
                }
            }
        }
        if (flag) {
            List<StroStockDetailDTO> stroStockDetailDTOS = invoicingOut.getStroStockDetailDTOS();
            StroStockDetailDTO newInvoicingOut = setParam(invoicingOut, stroStockDetailDTO);
            if(ListUtils.isEmpty(stroStockDetailDTOS)) {
              stroStockDetailDTOS = new ArrayList<>();
              stroStockDetailDTOS.add(newInvoicingOut);
            }else {
              stroStockDetailDTOS.add(newInvoicingOut);
            }
            invoicingOut.setStroStockDetailDTOS(stroStockDetailDTOS);
            invoicingOutList.add(invoicingOut);
        }
        return invoicingOutList;
    }

    /**
    * @Menthod setParam
    * @Desrciption 设置参数
    *
    * @Param
    * [invoicingOut, stroStockDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/25 9:51
    * @Return cn.hsa.module.stro.stock.dto.StroStockDetailDTO
    **/
    public StroStockDetailDTO setParam(StroStockDetailDTO invoicingOut,StroStockDetailDTO stroStockDetailDTO) {
        //订单号
        invoicingOut.setOrderNo(stroStockDetailDTO.getOrderNo());
        //创建人
        invoicingOut.setCrteName(stroStockDetailDTO.getCrteName());
        // 进销存目标id
        invoicingOut.setInvoicingTargetId(stroStockDetailDTO.getInvoicingTargetId());
        // 进销存目标名称
        invoicingOut.setInvoicingTargetName(stroStockDetailDTO.getInvoicingTargetName());
        //创建人ID
        invoicingOut.setCrteId(stroStockDetailDTO.getCrteId());
        //拆分比
        invoicingOut.setSplitRatio(stroStockDetailDTO.getSplitRatio());
        //处方ID
        invoicingOut.setOpId(stroStockDetailDTO.getOpId());
        //处方明细ID
        invoicingOut.setOpdId(stroStockDetailDTO.getOpdId());
        //费用ID
        invoicingOut.setCostId(stroStockDetailDTO.getCostId());
        //规格
        invoicingOut.setSpec(stroStockDetailDTO.getSpec());
        //剂量
        invoicingOut.setDosage(stroStockDetailDTO.getDosage());
        //剂量单位代码
        invoicingOut.setDosageUnitCode(stroStockDetailDTO.getDosageUnitCode());
        //中药付数
        invoicingOut.setChineseDrugNum(stroStockDetailDTO.getChineseDrugNum());
        //用法代码
        invoicingOut.setUsageCode(stroStockDetailDTO.getUsageCode());
        //用药性质代码
        invoicingOut.setUseCode(stroStockDetailDTO.getUseCode());
        //领药申请明细ID
        invoicingOut.setIrdId(stroStockDetailDTO.getIrdId());
        //医嘱ID
        invoicingOut.setAdviceId(stroStockDetailDTO.getAdviceId());
        //医嘱组号
        invoicingOut.setGroupNo(stroStockDetailDTO.getGroupNo());
        //就诊ID
        invoicingOut.setVisitId(stroStockDetailDTO.getVisitId());
        //婴儿ID
        invoicingOut.setBabyId(stroStockDetailDTO.getBabyId());
        // 发药明细汇总ID
        invoicingOut.setDistributeAllDetailId(stroStockDetailDTO.getDistributeAllDetailId());
        // 新价格（库存价格）
        invoicingOut.setNewPrice(invoicingOut.getSellPrice());
        // 原价格
        invoicingOut.setSellPrice(stroStockDetailDTO.getSellPrice());
        // 新拆零单价（库存拆零单价）
        invoicingOut.setNewSplitPrice(invoicingOut.getSplitPrice());
        // 原拆零单价
        invoicingOut.setSplitPrice(stroStockDetailDTO.getSplitPrice());
        //当前单位
        if (!StringUtils.isEmpty(stroStockDetailDTO.getCurrUnitCode())) {
          invoicingOut.setCurrUnitCode(stroStockDetailDTO.getCurrUnitCode());
        } else {
          invoicingOut.setCurrUnitCode(stroStockDetailDTO.getUnitCode());
        }
        //时间戳
        invoicingOut.setCrteTimeStamp(System.nanoTime());
        StroStockDetailDTO batchStroDetail = new StroStockDetailDTO();
        try {
          BeanUtils.copyProperties(batchStroDetail,invoicingOut);
        } catch (IllegalAccessException e) {
          throw new AppException("复制数据错误");
        } catch (InvocationTargetException e) {
          throw new AppException("复制数据错误");
        }
        return batchStroDetail;
    }

    /**
     * 获取出库台账明细数据
     * @param invoicingOutList 出库明细
     * @param stroStockDetailDTO 库存明细
     * @param map 传入Map
     * @param stroSplitNum 库存数量
     * @param stroStockDTO 库存主表
     * @return
     */
    public List getStroInvoicingDTOList(List<StroStockDetailDTO> invoicingOutList, StroStockDetailDTO stroStockDetailDTO,
                                        Map map, BigDecimal stroSplitNum, StroStockDTO stroStockDTO){
        //台账
        List<StroInvoicingDTO> invoicingList = new ArrayList<>();
        //台账记录出库记录
        for(StroStockDetailDTO stroStockDetail : invoicingOutList){
            stroStockDetailDTO.setBatchNo(stroStockDetail.getBatchNo());
            //获取批次数量
            StroStockDetailDTO StockDetailBatchNoNum = stroStockDao.getStockDetailBatchNoNum(stroStockDetailDTO);
            StroInvoicingDTO stroInvoicingDTO = new StroInvoicingDTO();
            // 设置库存价格
            stroInvoicingDTO.setNewPrice(stroStockDetail.getNewPrice());
            // 设置库存拆零价格
            stroInvoicingDTO.setNewSplitPrice(stroStockDetail.getNewSplitPrice());
            // 设置原价格
            stroInvoicingDTO.setSellPrice(stroStockDetail.getSellPrice());
            // 设置原拆零价格
            stroInvoicingDTO.setSplitPrice(stroStockDetail.getSplitPrice());
            //出库数量
            stroInvoicingDTO.setNum(BigDecimalUtils.multiply(stroStockDetail.getNum(), BigDecimal.valueOf(-1)));
            //拆零数量
            stroInvoicingDTO.setSplitNum(BigDecimalUtils.multiply(stroStockDetail.getSplitNum(), BigDecimal.valueOf(-1)));
            //库存结余拆零数量
            stroSplitNum = BigDecimalUtils.add(stroSplitNum, stroStockDetail.getSplitNum());
            //库存结余数量
            stroInvoicingDTO.setSurplusNum(BigDecimalUtils.divide(stroSplitNum, stroStockDetail.getSplitRatio()));
            //批号结余数量
            stroInvoicingDTO.setBatchSurplusNum(BigDecimalUtils.subtract(StockDetailBatchNoNum==null?BigDecimal.valueOf(0):StockDetailBatchNoNum.getNum(),
                    stroStockDetail.getNum()));
            //出入库方式
            stroInvoicingDTO.setOutinCode(map.get("type").toString());
            //台账数据
            invoicingList.add(this.setInvoicingValue(stroInvoicingDTO, stroStockDetail));
        }
        //获取进销存数量、价格等
        invoicingList = this.getPriceAllList(invoicingList, stroStockDTO);
        return invoicingList;
    }

    /**
     * 获取出库台账明细数据
     * @param invoicingOutList 出库明细
     * @param stroStockDTO 传入Map
     * @return
     */
    public List getPriceAllList(List<StroInvoicingDTO> invoicingOutList, StroStockDTO stroStockDTO){
        BigDecimal splitNum = BigDecimal.valueOf(0);
        BigDecimal num = BigDecimal.valueOf(0);
        BigDecimal buyPriceAll = BigDecimal.valueOf(0);
        BigDecimal sellPriceAll = BigDecimal.valueOf(0);
        //台账记录出库记录
        for(StroInvoicingDTO stroInvoicingDTO : invoicingOutList){
            //上期/期初数量
            stroInvoicingDTO.setUpSurplusNum(BigDecimalUtils.add(stroStockDTO.getNum(), num));
            //上期/期初购进总金额
            stroInvoicingDTO.setUpBuyPriceAll(BigDecimalUtils.add(stroStockDTO.getBuyPriceAll(), buyPriceAll));
            //上期/期初零售总金额
            stroInvoicingDTO.setUpSellPriceAll(BigDecimalUtils.add(stroStockDTO.getSellPriceAll(), sellPriceAll));

            //批次累计拆零数量
            splitNum = BigDecimalUtils.add(stroInvoicingDTO.getSplitNum(), splitNum);

            num = BigDecimalUtils.divide(splitNum,stroInvoicingDTO.getSplitRatio());

            //批次累计购进总金额
            buyPriceAll = BigDecimalUtils.multiply(stroInvoicingDTO.getBuyPrice(), num);
            //批次累计零售总金额
            sellPriceAll = BigDecimalUtils.multiply(stroStockDTO.getSellPrice(), num);

            //本期/期末数量
            stroInvoicingDTO.setSurplusNum(BigDecimalUtils.add(stroStockDTO.getNum(), num));
            //本期/期末购进总金额
            stroInvoicingDTO.setBuyPriceAll(BigDecimalUtils.add(stroStockDTO.getBuyPriceAll(), buyPriceAll));
            //本期/期末零售总金额
            stroInvoicingDTO.setSellPriceAll(BigDecimalUtils.add(stroStockDTO.getSellPriceAll(), sellPriceAll));
        }
        return invoicingOutList;
    }

    /**
     * @Menthod checkPar
     * @Desrciption 检查参数是否为空
     * @param map {hospCode：医院编码  stroStockDetailDTOList: 库存明细}
     * @Author zengfeng
     * @Date   2020/8/5 9:
     * @Return 是否成功
     **/
    @Override
    public boolean checkPar(Map map){
        if(StringUtils.isEmpty(String.valueOf(map.get("hospCode")))){
            throw new AppException("医院编码不能为空");
        }
        if(StringUtils.isEmpty(String.valueOf(map.get("type")))){
            throw new AppException("请传入库存类型");
        }
        List<StroStockDetailDTO> stroStockDetailDTOList = (List<StroStockDetailDTO>) map.get("stroStockDetailDTOList");
        if(ListUtils.isEmpty(stroStockDetailDTOList)){
            throw new AppException("库存明细数据集合不能为空");
        }
        for (StroStockDetailDTO detailDTO:stroStockDetailDTOList) {
            if (detailDTO == null) {
                throw new AppException("库存明细数据对象不能为空");
            }
            if (detailDTO.getSplitRatio() == null) {
                throw new AppException("库存明细数据拆分比不能为空");
            }
            if (detailDTO.getNum() == null) {
                throw new AppException("库存明细数据数量不能为空");
            }
            if (detailDTO.getSplitNum() == null) {
                throw new AppException("库存明细数据拆零数量不能为空");
            }
            if (StringUtils.isEmpty(detailDTO.getBizId())) {
                throw new AppException("库存明细数据库位ID不能为空");
            }
            if (StringUtils.isEmpty(detailDTO.getItemId())) {
                throw new AppException("库存明细数据项目ID不能为空");
            }
            if (StringUtils.isEmpty(detailDTO.getItemCode())) {
                throw new AppException("库存明细数据项目类型不能为空");
            }
            if(!EnumUtil.CRFS7.getKey().equals(map.get("type").toString()) && !EnumUtil.CRFS8.getKey().equals(map.get("type").toString())) {
                if (detailDTO.getNum().compareTo(BigDecimal.valueOf(0)) < 0) {
                    throw new AppException("库存明细数量不能小于0");
                }
                if (detailDTO.getSplitNum().compareTo(BigDecimal.valueOf(0)) < 0) {
                    throw new AppException("库存明细拆零数量不能小于0");
                }
            }
            BigDecimal jsNum = BigDecimalUtils.divide(detailDTO.getSplitNum(), detailDTO.getSplitRatio()).setScale(2,BigDecimal.ROUND_HALF_UP);
            if (Math.abs(detailDTO.getNum().subtract(jsNum).doubleValue()) > 0.1) {
                throw new AppException("库存明细拆零数量和数量不对等");
            }
        }
        return true;
    }

    /**
     *
     * @Descrition: 库存过期更新对应的药品、材料的占用库存数据
     * @Pramas: param
     * @Author: fuhui
     * @mail: 3277857701@qq.com
     * @Date: 2020/11/29
     * @Retrun: null
     */
    @Override
    public boolean updateOccupy(String param) {
        List<StroStockDetailDTO> stockDetailDTOList = stroStockDetailDao.queryAllStroStock(param);
        if (ListUtils.isEmpty(stockDetailDTOList)) {
            throw new AppException("库存明细表里面无数据");
        }
        // 获取当前时间
        String formatNow = DateUtils.format(DateUtils.getNow(), "yyyy-MM-dd");
        List<StroStockDTO> stroStockDTOList = new ArrayList<>();
        for (StroStockDetailDTO stockDTO : stockDetailDTOList) {
            if(stockDTO.getExpiryDate() ==null){
                continue;
            }else {
                StroStockDTO stroStockDTO = new StroStockDTO();
                String expireDate = DateUtils.format(stockDTO.getExpiryDate(),"yyyy-MM-dd");
                // 如果有效期 = 当前时间
                if (formatNow.equals(expireDate)) {
                    stroStockDTO.setHospCode(stroStockDTO.getHospCode()); // 医院编码
                    stroStockDTO.setStockOccupy(stroStockDTO.getNum());  // 药品/材料 过期数量
                    stroStockDTO.setItemId(stroStockDTO.getItemId());  // 项目Id
                    stroStockDTO.setItemCode(stroStockDTO.getItemCode());  //项目编码
                    stroStockDTO.setBizId(stroStockDTO.getBizId()); // 库房ID（药库/药房）
                    stroStockDTOList.add(stroStockDTO);
                }
            }
        }
        return stroStockDao.updateOccupy(stockDetailDTOList);
    }

    /**
    * @Menthod inserStockByTime
    * @Desrciption
    *
    * @Param
    * [hospCode]
    *
    * @Author jiahong.yang
    * @Date   2021/12/13 16:23
    * @Return boolean
    **/
    @Override
    public boolean insertStockByTime(StroStockDTO stroStockDTO) {
      if(stroStockDTO.getCrteTime() == null) {
        throw new AppException("时间参数为空");
      }
      // 如果手动生成 生成上个月得库存
      if("1".equals(stroStockDTO.getLongStockflag())) {
        Calendar c = Calendar.getInstance();
        c.setTime(stroStockDTO.getCrteTime());
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH,c.getActualMaximum(Calendar.DAY_OF_MONTH));//设置为最后一天
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        stroStockDTO.setCrteTime(c.getTime());
      }
      // 转化为年月
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
      String stockTime = sdf.format(stroStockDTO.getCrteTime());
      stroStockDTO.setStockTime(stockTime);
      // 查找当月数据有没有生成如果生成不再生成
      List<StroStockDTO> stockTimeItems = stroStockDao.queryStockTimeAll(stroStockDTO);
      if(!ListUtils.isEmpty(stockTimeItems)) {
        throw new AppException("该月底库存已经生成");
      }
      // 查找当月月底库存
      List<StroStockDTO> stroStockDTOS = stroStockDao.queryStockAll(stroStockDTO);
      for(StroStockDTO item : stroStockDTOS) {
        item.setCrteTime(DateUtils.getNow());
        item.setStockTime(stockTime);
        item.setId(SnowflakeUtils.getId());
      }
      stroStockDao.insertStockTime(stroStockDTOS);
      return true;
    }

     /**库存效期查询
     * @Method queryValidityWarningPage
     * @Desrciption
     * @param stroStockDTO
     * @Author liuqi1
     * @Date   2021/4/22 14:51
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryValidityWarningPage(StroStockDTO stroStockDTO) {
        List<StroStockDTO> list = null;
        PageHelper.startPage(stroStockDTO.getPageNo(), stroStockDTO.getPageSize());
        // update by zhangguorui 当不输入预警天数时，不要设置默认的30天，而是把全部的查询出来
        if(StringUtils.isNotEmpty(stroStockDTO.getWarDays())){
            Integer warDays = Integer.parseInt(stroStockDTO.getWarDays());
            //根据效期天数，计算出最大预警效期日期
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DAY_OF_MONTH, warDays);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String endDate = format.format(now.getTime());
            try {
                Date parse = format.parse(endDate);
                stroStockDTO.setWarDate(parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        list = stroStockDao.queryValidityWarningPage(stroStockDTO);
        return PageDTO.of(list);
    }

    /**
    * @Menthod queryStockByBatchAll
    * @Desrciption 查询库存明细中批号汇总
    *
    * @Param
    * [stroStockDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/2 14:54
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryStockByBatchAll(StroStockDetailDTO stroStockDetailDTO) {
      PageHelper.startPage(stroStockDetailDTO.getPageNo(), stroStockDetailDTO.getPageSize());
      return PageDTO.of(stroStockDao.queryStockByBatchAll(stroStockDetailDTO));
    }
    /**
     * @Method queryPageByOutptOrInpt
     * @Desrciption 提供给门诊医生站、住院医生站的药品查询接口
     * @Param [stroStockDTO]
     * @Author zhangguorui
     * @Date   2021/7/16 14:43
     * @Return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO queryPageByOutptOrInpt(StroStockDTO stroStockDTO) {
        PageHelper.startPage(stroStockDTO.getPageNo(), stroStockDTO.getPageSize());
        List<StroStockDTO> list = new ArrayList<>();
        // 封装药品类别map
        Map map = new HashMap();
        //中成药
        map.put("2",Constants.YPDL.Chinese_Patent_Medicine);
        map.put("4",Constants.YPDL.Chinese_Patent_Medicine);
        // 草药
        map.put("3",Constants.YPDL.Herbal_Medicine);
        // 西药
        map.put("1",Constants.YPDL.Western_Medicine);
        // 材料
        map.put("5","5");
        map.put("6","5");
        map.put("7","5");
        // 根据药房区分是中药还是西药还是材料
        // 如果bizId不为空，那么通过查询bizId获得科室类别
        String typeIdentity = "";
        if (StringUtils.isEmpty(stroStockDTO.getBizId())){
            typeIdentity = stroStockDao.getTypeIdentityByBizId(stroStockDTO.getBizId(),stroStockDTO.getHospCode());
        }
        List<String> types = new ArrayList<>();
        if (StringUtils.isNotEmpty(typeIdentity)){
            for (String type : typeIdentity.split(",")){
                String value = String.valueOf(map.get(type));
                if (StringUtils.isEmpty(value)){
                    throw new AppException("没有该药房药库类别标识");
                }else {
                    types.add(value);
                }
            }
        }
        stroStockDTO.setTypes(types);
        if(StringUtils.isEmpty(stroStockDTO.getLoginTypeIdentity())){
            list = stroStockDao.queryAll(stroStockDTO);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDTO.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDTO.getTypes())) {
            list = stroStockDao.queryAll(stroStockDTO);
        } else if (cn.hsa.util.StringUtils.isEmpty(stroStockDTO.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDTO.getTypes())) {
            list = stroStockDao.queryAlldrug(stroStockDTO);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDTO.getTypeIdentity()) && ListUtils.isEmpty(stroStockDTO.getTypes())) {
            list = stroStockDao.queryAllmaterial(stroStockDTO);
        }
        // 返回分页结果
        return PageDTO.of(list);
    }
    /**
     * @Meth: queryAllStockPage
     * @Description: 查询全院库存
     * @Param: [stroStockDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    @Override
    public PageDTO queryAllStockPage(StroStockDTO stroStockDTO) {
        List<StroStockDTO> list = new ArrayList<>();
        PageHelper.startPage(stroStockDTO.getPageNo(), stroStockDTO.getPageSize());
        // 通过选择的科室id 查询科室类别
        //根据科室类别区分是中药还是西药还是材料
        List<String> types = new ArrayList<>();
        String bizId = stroStockDTO.getBizId();
        if (StringUtils.isEmpty(bizId) || "all".equals(bizId)) {// 选择的库房或者药房为空，那么查询全院的库存
            stroStockDTO.setBizId("");
            types = Arrays.asList("1","2","0");
            stroStockDTO.setTypes(types);
            if ("0".equals(stroStockDTO.getResultType())) { // 材料
                list = stroStockDao.queryAllmaterial(stroStockDTO);
            } else if ("1".equals(stroStockDTO.getResultType())){ // 查询药品
                list = stroStockDao.queryAlldrug(stroStockDTO);
            } else { // 查询材料、药品
                list = stroStockDao.queryAll(stroStockDTO);
            }
        } else {
            String loginTypeIdentity =  stroStockDao.getTypeIdentityByBizId(stroStockDTO.getBizId(),stroStockDTO.getHospCode());
            if (StringUtils.isEmpty(loginTypeIdentity)){
                throw new AppException("药房药库类别标识为空");
            }
            for (String loginType : loginTypeIdentity.split(",")) {
                if (loginType.equals("2") || loginType.equals("4")) {//中成药
                    types.add("1");
                } else if (loginType.equals("3")) {//中草药
                    types.add("2");
                } else if (loginType.equals("1")) {//西药
                    types.add("0");
                } else if (loginType.equals("4")) {//藏药
                    types.add("3");
                } else if (loginType.equals("5") || loginType.equals("6") || loginType.equals("7")) {//材料
                    stroStockDTO.setTypeIdentity("5");
                } else {
                    throw new AppException("没有该药房药库类别标识");
                }
            }
            stroStockDTO.setTypes(types);
            if (!ListUtils.isEmpty(stroStockDTO.getTypes())) {
                list = stroStockDao.queryAlldrug(stroStockDTO);
            } else if (ListUtils.isEmpty(stroStockDTO.getTypes())) {
                list = stroStockDao.queryAllmaterial(stroStockDTO);
            }
        }
        // 返回分页结果
        return PageDTO.of(list);
    }
    /**
     * @Meth: queryAllStockDetails
     * @Description: 查询全部库存明细
     * @Param: [stroStockDetail]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    @Override
    public PageDTO queryAllStockDetails(StroStockDetailDTO stroStockDetail) {
        List<StroStockDetailDTO> list = new ArrayList<>();
        PageHelper.startPage(stroStockDetail.getPageNo(), stroStockDetail.getPageSize());
        // 通过选择的科室id 查询科室类别
        //根据科室类别区分是中药还是西药还是材料
        String loginTypeIdentity = stroStockDao.getTypeIdentityByBizId(stroStockDetail.getBizId(), stroStockDetail.getHospCode());
        stroStockDetail.setLoginTypeIdentity(loginTypeIdentity);
        if (!StringUtils.isEmpty(loginTypeIdentity)) {
            List<String> types = new ArrayList<>();
            for (String loginType : loginTypeIdentity.split(",")) {
                if (loginType.equals("2")) {//中成药
                    types.add("1");
                } else if (loginType.equals("3")) {//中草药
                    types.add("2");
                } else if (loginType.equals("1")) {//西药
                    types.add("0");
                } else if (loginType.equals("4")) {//藏药
                    types.add("3");
                } else if (loginType.equals("5") || loginType.equals("6") || loginType.equals("7")) {//材料
                    stroStockDetail.setTypeIdentity("5");
                }
            }
            stroStockDetail.setTypes(types);
        }
        if (StringUtils.isEmpty(stroStockDetail.getLoginTypeIdentity())) {
            list = stroStockDetailDao.queryAll(stroStockDetail);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAll(stroStockDetail);
        } else if (cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && !ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllDrug(stroStockDetail);
        } else if (!cn.hsa.util.StringUtils.isEmpty(stroStockDetail.getTypeIdentity()) && ListUtils.isEmpty(stroStockDetail.getTypes())) {
            list = stroStockDetailDao.queryAllMaterial(stroStockDetail);
        }
        // 返回分页结果
        return PageDTO.of(list);
    }

    /**
     * 根据科室、医生和日期统计药品和材料的利润信息
     * @Author liudawen
     * @Param [itemProfitStatisticsDTO]
     * @Return cn.hsa.base.PageDTO
     * @Throws
     * @Date 2022/4/19 16:22
     **/
    @Override
    public PageDTO queryDrugAndMaterialProfit(ItemProfitStatisticsDTO itemProfitStatisticsDTO) {
        // 1：门诊，2：住院
        String bizCode = itemProfitStatisticsDTO.getBizCode();
        // 1:业务类型/项目/医生  2:业务类型/科室
        String sumCode = itemProfitStatisticsDTO.getSumCode();
        if (StringUtils.isEmpty(bizCode)){
            throw new AppException("请选择需要查询的业务类型");
        }
        if (StringUtils.isEmpty(sumCode)){
            throw new AppException("请选择需要汇总的方式");
        }

        List<ItemProfitStatisticsDTO> list = new ArrayList<>();
        PageHelper.startPage(itemProfitStatisticsDTO.getPageNo(),itemProfitStatisticsDTO.getPageSize());
        switch (bizCode){
            // 查询门诊药品和材料利润统计信息
            case "1" : list = stroStockDetailDao.queryMZDrugAndMaterialProfit(itemProfitStatisticsDTO); break;
            // 查询住院药品和材料利润统计信息
            case "2" : list = stroStockDetailDao.queryZYDrugAndMaterialProfit(itemProfitStatisticsDTO); break;
        }
        PageDTO pageDTO = PageDTO.of(list);
        return pageDTO;
    }

}
