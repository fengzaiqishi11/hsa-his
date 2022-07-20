package cn.hsa.phar.pharapply.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.phar.pharapply.bo.PharApplyBO;
import cn.hsa.module.phar.pharapply.dao.PharApplyDAO;
import cn.hsa.module.phar.pharapply.dao.PharApplyDetailDAO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO;
import cn.hsa.module.phar.pharapply.entity.StroOutDetail;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInReceiveDAO;
import cn.hsa.module.phar.pharinbackdrug.dao.PharInReceiveDetailDAO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInReceiveDetailDTO;

import cn.hsa.module.stro.stock.dao.StroStockDao;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroout.dao.StroOutDAO;
import cn.hsa.module.stro.stroout.dto.StroOutDTO;
import cn.hsa.module.stro.stroout.dto.StroOutDetailDTO;

import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Package_name: cn.hsa.phar.pharapply.bo.impl
 * @class_name: PharApplyBOImpl
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/28 17:35
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class PharApplyBOImpl extends HsafBO implements PharApplyBO {
    /**
     * 领药申请数据访问层
     */
    @Resource
    private PharApplyDAO pharApplyDAO;

    /**
     * 领药申请明细数据访问层
     */
    @Resource
    private PharApplyDetailDAO pharApplyDetailDAO;

    /**
     * 单据号的生成规则服务层接口
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private PharInReceiveDAO pharInReceiveDAO;

    @Resource
    private PharInReceiveDetailDAO pharInReceiveDetailDAO;

    @Resource
    private StroOutDAO stroOutDAO;

    @Resource
    StroStockDao stroStockDAO;




    /**
     * @Method: queryPage()
     * @Description: 分页显示领药申请的信息
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:49
     * @Return: PharApplyDTO领药申请的数据传输对象集合
     */
    @Override
    public PageDTO queryPage(PharApplyDTO pharApplyDTO) {
        PageHelper.startPage(pharApplyDTO.getPageNo(), pharApplyDTO.getPageSize());
        List<PharApplyDTO> pharApplyDTOList = pharApplyDAO.queryPage(pharApplyDTO);
        return PageDTO.of(pharApplyDTOList);

    }

    /**
     * @Method: insert()
     * @Description: 新增领药申请
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * <p>
     * 1.新增药品申领按钮 先确定单据类型  出库库位
     * 2.确定要申请的药品明细  是否需要判断申请的申领数量和库存数量的比较
     * 如果大于就提示错误消息  否则允许申请
     * 3.点击保存按钮  更新药品申领 药品申领详细信息的数据库信息（更新药品申领的审核代码标识）
     * 4.修改按钮  ：对未审核的药品信息进行编辑操作
     * 未审核确认的药品申领信息 都可以进行编辑操作 一旦审核完成 就不能进行任何的操作
     * 5.作废：是针对未确认的药品申领信息 点击作废按钮 则表格不显示此申领信息（更新药品申领的审核代码标识）
     * （是否需要）同时删除对应的明细药品申请信息
     * 6.审核药品领药信息
     * （更新药品申领的审核代码标识）
     * 新增药品申领明细数据
     * 7.更新库存、台账表
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: boolean
     */
    @Override
    public boolean insert(PharApplyDTO pharApplyDTO) {
        //领药申请的主键id
        pharApplyDTO.setId(SnowflakeUtils.getId());
        //领药申请的创建时间
        pharApplyDTO.setCrteTime(DateUtils.getNow());
        //根据单据类型的生成规则 生成单据号
        Map map =new HashMap();
        map.put("hospCode", pharApplyDTO.getHospCode());
        map.put("typeCode", Constants.ORDERRULE.LY);
        WrapperResponse<String> response = baseOrderRuleService.getOrderNo(map);
        pharApplyDTO.setOrderNo(response.getData());
        //默认为未审核
        pharApplyDTO.setAuditStatus(Constants.SF.F);
        //获取明细集合
        List<PharApplyDetailDTO> padList = pharApplyDTO.getAddDetailList();
        if(!ListUtils.isEmpty(padList)){
            for (int i = 0; i < padList.size(); i++) {
                //生成申请领药明细表主键id
                padList.get(i).setId(SnowflakeUtils.getId());
                //生成申请领药明细医院编码
                padList.get(i).setHospCode(pharApplyDTO.getHospCode());
                // 计算拆零数量
                padList.get(i).setSplitNum(BigDecimalUtils.multiply(padList.get(i).getNum(),padList.get(i).getSplitRatio()));
                //购进总金额
                padList.get(i).setBuyPriceAll(BigDecimalUtils.multiply(padList.get(i).getNum() ,padList.get(i).getBuyPrice() ));
                // 零售总金额
                padList.get(i).setSellPriceAll(BigDecimalUtils.multiply(padList.get(i).getNum(),  padList.get(i).getSellPrice()));
                //生成申请领药明细表申领单号id  关联领药申请主键id
                padList.get(i).setApplyId(pharApplyDTO.getId());
            }
            //批量插入领药明细表
            Integer count = pharApplyDetailDAO.insert(padList);
            if (count <= 0 || count == null) {
                throw new AppException("领药申请失败");
            }
            //计算这一批的请领的总购进总金额 和 总零售总金额
            PharApplyDetailDTO pharApplyDetailDTO =pharApplyDetailDAO.selectTotalPrice(pharApplyDTO.getId(),pharApplyDTO.getHospCode());
            pharApplyDTO.setSellPriceAll(pharApplyDetailDTO.getTotalSellPrice());
            pharApplyDTO.setBuyPriceAll(pharApplyDetailDTO.getTotalBuyPrice());
            pharApplyDTO.setIsOut("0");
            return pharApplyDAO.insert(pharApplyDTO) > 0;
        }else{
            throw new AppException("新增领药明细数据为空");
        }
    }

    /**
     * @Method: update()
     * @Description: 修改领药申请
     * 1.领药申请的修改只能对未审核的数据进行修改
     * 1.修改领药申请 需要判断领药申请的明细信息
     * 2. 是增加了明细数据 还是 修改了明细数据 还是删除了明细数据
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: 影响的行数
     */

    @Override
    public boolean update(PharApplyDTO pharApplyDTO) {
        PharApplyDTO applyDAOById = pharApplyDAO.getById(pharApplyDTO);
        List<PharApplyDetailDTO> detailList = pharApplyDTO.getDetailList();
        if(applyDAOById !=null){
            if ("1".equals(applyDAOById.getAuditStatus())) {
                throw new AppException("本次编辑领药申请的数据,已经审核,单据号为:" + applyDAOById .getOrderNo());
            }
            if ("2".equals(applyDAOById.getAuditStatus())) {
                throw new AppException("本次编辑领药申请的数据,已经作废,单据号为:" + applyDAOById.getOrderNo());
            }
            boolean flag = pharApplyDetailDAO.deleteApplyDetail(pharApplyDTO.getId(), pharApplyDTO.getHospCode());
            if(flag){
                if(!ListUtils.isEmpty(detailList)){
                    for (int i = 0; i < detailList.size(); i++) {
                        //生成申请领药明细表主键id
                        detailList.get(i).setId(SnowflakeUtils.getId());
                        //生成申请领药明细医院编码
                        detailList.get(i).setHospCode(pharApplyDTO.getHospCode());
                        //生成申请领药明细表申领单号id  关联领药申请主键id
                        detailList.get(i).setApplyId(pharApplyDTO.getId());
                    }
                }
                //默认为未审核
                pharApplyDetailDAO.insert(detailList);
                //计算这一批的请领的总购进总金额 和 总零售总金额
                PharApplyDetailDTO pharApplyDetailDTO =pharApplyDetailDAO.selectTotalPrice(pharApplyDTO.getId(),pharApplyDTO.getHospCode());
                pharApplyDTO.setSellPriceAll(pharApplyDetailDTO.getTotalSellPrice());
                pharApplyDTO.setBuyPriceAll(pharApplyDetailDTO.getTotalBuyPrice());
                int count = pharApplyDAO.update(pharApplyDTO);
                if (count < 0) {
                    throw new AppException("修改领药数据失败");
                }
            }else{
                throw new AppException("修改领药明细数据失败");
            }
        }
       return false;
    }

    /**
     * @Method: batchCancelled()
     * @Description: 批量作废
     * 1.批量作废前 做查询操作
     * 先判断审核标识的状态 只有状态码为0的才能作废  审核标识分为  0未审核 、1已审核 、2作废、
     * 2.修改 领药申请的状态标识
     * @Param: 1.hospCode医院编码
     * 2.id主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return:
     */
    @Override
    public boolean updateBatchCancelled(PharApplyDTO pharApplyDTO) {
        //批量作废前 先判断审核代码标识
        List<PharApplyDTO> applyDTOList = pharApplyDAO.queryFlag(pharApplyDTO);
        if(!ListUtils.isEmpty(applyDTOList)){
            for (int i = 0; i < applyDTOList.size(); i++) {
                if (Constants.SHZT.SHWC.equals(applyDTOList.get(i).getAuditStatus()) && StringUtils.isEmpty(pharApplyDTO.getLimitFlag())) {
                    throw new AppException("批量作废失败:" + applyDTOList.get(i).getOrderNo() + ":单据号已经审核");
                }
                if (Constants.SHZT.ZF.equals(applyDTOList.get(i).getAuditStatus())) {
                    throw new AppException("批量作废失败:" + applyDTOList.get(i).getOrderNo() + ":单据号已经作废");
                }
            }
            pharApplyDTO.setAuditTime(DateUtils.getNow());  // 作废审核时间
            return pharApplyDAO.updateBatchCancelled(pharApplyDTO) > 0;
        }else{
            throw new AppException("请勾选要作废的数据");
        }
    }

    /**
     * @Method: batchCheck()
     * @Description: 批量审核
     *   1.审核成功之前 需要对审核状态做判断  只能对未审核的进行审核
     *   2.批量审核成功以后 就需要把领药申请的数据插入到药品出库，药品
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return:
     */
    @Override
    public boolean updateBatchCheck(PharApplyDTO pharApplyDTO) {
        //批量审核前 先判断审核代码标识
        List<PharApplyDTO> pharApplyDTOList = pharApplyDAO.queryFlag(pharApplyDTO);
        if(!ListUtils.isEmpty(pharApplyDTOList)){
            pharApplyDTOList.stream().forEach(applyDTO->{
                if(Constants.SHZT.SHWC.equals(applyDTO.getAuditStatus())){
                    throw new AppException("本次批量审核的数据,已存在审核的数据,单据号为:"+applyDTO.getOrderNo());
                }
                if(Constants.SHZT.ZF.equals(applyDTO.getAuditStatus())){
                    throw new AppException("本次批量审核的数据,已存在作废的数据,单据号为:"+applyDTO.getOrderNo());
                }
            });
            pharApplyDTO.setAuditTime(DateUtils.getNow());
            pharApplyDTO.setAuditStatus(Constants.SHZT.SHWC);
            Integer count = pharApplyDAO.updateBatchCheck(pharApplyDTO);
            // 审核成功以后 把领药申请， 申请明细的数据分别插入到 出库 出库明细中
            if (count <=0 || count == null) {
                throw new AppException("批量审核申请领药失败");
            }
            return true;
        }else{
            throw new AppException("批量审核的数据为空");
        }

    }
    /**
     * @Method: pharApplyDetail()
     * @Description: 显示药品请领明细表
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 20:11
     * @Return:
     */
    @Override
    public List<PharApplyDetailDTO> pharApplyDetail(PharApplyDetailDTO pharApplyDetailDTO) {
        return pharApplyDetailDAO.pharApplyDetail(pharApplyDetailDTO);
    }

    /**
     * @Method: getById()
     * @Description: 查询领药申请的信息
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * 1.id:主键id
     * 2.hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/31 9:16
     * @Return:
     */
    public PharApplyDTO getById(PharApplyDTO pharApplyDTO) {
        PharApplyDTO applyDTO = pharApplyDAO.getById(pharApplyDTO);
        // List<PharApplyDetailDTO> detailDTOS = pharApplyDetailDAO.getById(pharApplyDTO.getId(), pharApplyDTO.getHospCode());
        pharApplyDTO.setOutStroId(applyDTO.getOutStroId());
        List<PharApplyDetailDTO> detailDTOS = pharApplyDetailDAO.getDetail(pharApplyDTO);
        applyDTO.setPharApplyDetailDTOS(detailDTOS);
        return applyDTO;
    }

    /**
     * @Method: queryStockNum()
     * @Description: 领药申请时 申请数量需要和库存数量做一个对比
     * @Param:  PharApplyDetailDTO库存明细数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 15:48
     * @Return:  pharApplyDetailDTODTO库存明细数据传输对象
     */
    @Override
    public PageDTO queryStockNum(StroStockDTO stroStockDTO) {
        PageHelper.startPage(stroStockDTO.getPageNo(),stroStockDTO.getPageSize());
        List<StroStockDTO> stroStockDTOList = pharApplyDetailDAO.queryStockNum(stroStockDTO);
        return PageDTO.of(stroStockDTOList);
    }

    /**
     * @Method: queryStatus()
     * @Description: 查看当前领药申请的审核标志
     * @Param: PharApplyDTO 领药申请数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 16:55
     * @Return:
     */
    @Override
    public List<PharApplyDTO> queryStatus(PharApplyDTO pharApplyDTO) {
        return pharApplyDAO.queryFlag(pharApplyDTO);
    }

    @Override
    public Boolean insertBatch(Map map) {
        List<PharInReceiveDTO> inReceiveList = MapUtils.get(map, "inReceiveList");
        List<PharInReceiveDetailDTO> inReceiveDetailList = MapUtils.get(map, "inReceiveDetailList");
        pharInReceiveDAO.insertPharInReceives(inReceiveList);
        pharInReceiveDetailDAO.insertPharInReceiveDetails(inReceiveDetailList);
        return true;
    }

    /**
     * @Method: queryPageDeatil()
     * @Desciption: 根据出库库位来判断，领药申请的是材料还是药品
     * @Pramas: a
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/11/30
     * @Retrun: 药品/材料的分页数据传输对象
     */
//    @Override
//    public PageDTO queryPageDetail1(PharApplyDetailDTO pharApplyDetailDTO) {
//        List<PharApplyDetailDTO> list;
//        String loginDeptType = pharApplyDetailDTO.getLoginDeptType();
//        PageHelper.startPage(pharApplyDetailDTO.getPageNo(), pharApplyDetailDTO.getPageSize());
//        if ((Constants.KSXZ.KSXZ3.equals(loginDeptType)) || (Constants.KSXZ.KSXZ4.equals(loginDeptType)) || (Constants.KSXZ.KSXZ5.equals(loginDeptType))) {
//            //根据科室类别区分是中药还是西药
//            String loginTypeIdentity = pharApplyDetailDTO.getLoginTypeIdentity();
//            if (!cn.hsa.util.StringUtils.isEmpty(loginTypeIdentity)) {
//                if (loginTypeIdentity.contains("2") && loginTypeIdentity.contains("3")) {//中药
//                    pharApplyDetailDTO.setTypeIdentity("1");
//                } else if (loginTypeIdentity.contains("2")) {//中成药
//                    pharApplyDetailDTO.setTypeIdentity("2");
//                } else if (loginTypeIdentity.contains("3")) {//中草药
//                    pharApplyDetailDTO.setTypeIdentity("3");
//                } else if (loginTypeIdentity.contains("1")) {//西药
//                    pharApplyDetailDTO.setTypeIdentity("4");
//                }
//            }
//            list = pharApplyDetailDAO.queryAllDrug(pharApplyDetailDTO);
//        } else if ((Constants.KSXZ.KSXZ13.equals(loginDeptType)) || (Constants.KSXZ.KSXZ14.equals(loginDeptType))) {
//            list = pharApplyDetailDAO.queryAllMaterial(pharApplyDetailDTO);
//        } else {
//            throw new AppException("当前登录用户操作科室科室性质非药房药库");
//        }
//        // 返回分页结果
//        return PageDTO.of(list);
//    }

    @Override
    public PageDTO queryPageDetail(PharApplyDetailDTO pharApplyDetailDTO) {
      List<PharApplyDetailDTO> list = null;
      String loginTypeIdentity = pharApplyDetailDTO.getLoginTypeIdentity();
      PageHelper.startPage(pharApplyDetailDTO.getPageNo(), pharApplyDetailDTO.getPageSize());
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
            pharApplyDetailDTO.setTypeIdentity("5");
          }
        }
        pharApplyDetailDTO.setTypes(types);
      }
      if(StringUtils.isEmpty(pharApplyDetailDTO.getLoginTypeIdentity())){
        list = pharApplyDetailDAO.queryAll(pharApplyDetailDTO);
      } else if (!cn.hsa.util.StringUtils.isEmpty(pharApplyDetailDTO.getTypeIdentity()) && !ListUtils.isEmpty(pharApplyDetailDTO.getTypes())) {
        list = pharApplyDetailDAO.queryAll(pharApplyDetailDTO);
      } else if (cn.hsa.util.StringUtils.isEmpty(pharApplyDetailDTO.getTypeIdentity()) && !ListUtils.isEmpty(pharApplyDetailDTO.getTypes())) {
        list = pharApplyDetailDAO.queryAllDrug(pharApplyDetailDTO);
      } else if (!cn.hsa.util.StringUtils.isEmpty(pharApplyDetailDTO.getTypeIdentity()) && ListUtils.isEmpty(pharApplyDetailDTO.getTypes())) {
        list = pharApplyDetailDAO.queryAllMaterial(pharApplyDetailDTO);
      }
      // 返回分页结果
      return PageDTO.of(list);
    }

    /**
     * @Method printPharApply()
     * @Desrciption  打印领药申请单
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/20 10:49
     * @Return 领药申请数据传输对象
     **/
    @Override
    public List<PharApplyDTO> printPharApply(PharApplyDTO pharApplyDTO) {
        List<PharApplyDTO> dtoList = pharApplyDAO.selectPharApplyByIds(pharApplyDTO);
        for(int i=0;i<dtoList.size();i++){
            List<PharApplyDetailDTO> detailDTOList = pharApplyDetailDAO.getById(dtoList.get(i).getId(), pharApplyDTO.getHospCode());
            dtoList.get(i).setPharApplyDetailDTOS(detailDTOList);
        }
        return dtoList;
    }

    /**
    * @Menthod queryStroOutPharApply
    * @Desrciption 查询需要出库领药申请记录
    *
    * @Param
    * [pharApplyDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 15:25
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryStroOutPharApply(PharApplyDTO pharApplyDTO) {
      PageHelper.startPage(pharApplyDTO.getPageNo(), pharApplyDTO.getPageSize());
      List<PharApplyDTO> pharApplyDTOS = pharApplyDetailDAO.queryStroOutPharApply(pharApplyDTO);
      if (pharApplyDTOS!=null&&pharApplyDTOS.size()>0) {
          for (PharApplyDTO item : pharApplyDTOS) {
              PharApplyDetailDTO pharApplyDetailDTO = new PharApplyDetailDTO();
              pharApplyDetailDTO.setApplyId(item.getId());
              pharApplyDetailDTO.setHospCode(pharApplyDTO.getHospCode());
              List<PharApplyDetailDTO> pharApplyDetailDTOS = pharApplyDetailDAO.pharApplyDetail(pharApplyDetailDTO);
              item.setPharApplyDetailDTOS(pharApplyDetailDTOS);
          }
      }
      return PageDTO.of(pharApplyDTOS);
    }

    /**
    * @Menthod updatePharApply
    * @Desrciption 出库领药申请
    *
    * @Param
    * [pharApplyDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 16:52
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updatePharApply(PharApplyDTO pharApplyDTO) {
      List<PharApplyDTO> pharApplyDTOS = pharApplyDAO.selectPharApplyByIds(pharApplyDTO);
      if(ListUtils.isEmpty(pharApplyDTOS)) {
        throw new AppException("没有需要出库得数据");
      }
      for(PharApplyDTO pharApplyDTO1:pharApplyDTOS) {
        if("1".equals(pharApplyDTO1.getIsOut())) {
          throw new AppException("单据号为" + pharApplyDTO1.getOrderNo() + "的数据已经出库");
        }
      }
        //出库主表信息
        List<StroOutDTO> stroOutDTOS = new ArrayList<>();
        List<StroOutDetailDTO> stroOutDetailDTOS = new ArrayList<>();
        // 获取领药明细
        for(PharApplyDTO item :pharApplyDTOS) {
          PharApplyDetailDTO pharApplyDetailDTO = new PharApplyDetailDTO();
          pharApplyDetailDTO.setApplyId(item.getId());
          pharApplyDetailDTO.setHospCode(pharApplyDTO.getHospCode());
          List<PharApplyDetailDTO> pharApplyDetailDTOS = pharApplyDetailDAO.pharApplyDetail(pharApplyDetailDTO);
          pharApplyDTO.setPharApplyDetailDTOS(pharApplyDetailDTOS);
          // 组装出库信息
          StroOutDTO stroOutDTO = new StroOutDTO();
          // 主键
          stroOutDTO.setId(SnowflakeUtils.getId());
          // 医院编码
          stroOutDTO.setHospCode(item.getHospCode());
          stroOutDTO.setInStockId(item.getInStroId());
          stroOutDTO.setOutStockId(item.getOutStroId());
          // 单号
          Map orderMap = new HashMap();
          orderMap.put("typeCode", "03");
          orderMap.put("hospCode", stroOutDTO.getHospCode());
          stroOutDTO.setOrderNo(baseOrderRuleService.getOrderNo(orderMap).getData());
          // 购进总金额
          stroOutDTO.setBuyPriceAll(item.getBuyPriceAll());
          // 零售总金额
          stroOutDTO.setSellPriceAll(item.getSellPriceAll());
          // 原领药单号
          stroOutDTO.setInOrderNo(item.getOrderNo());
          // 创建人
          stroOutDTO.setCrteId(pharApplyDTO.getCrteId());
          // 创建人姓名
          stroOutDTO.setCrteName(pharApplyDTO.getCrteName());
          // 创建时间
          stroOutDTO.setCrteTime(pharApplyDTO.getCrteTime());
          // 备注
          stroOutDTO.setRemark(item.getRemark());
          // 审核状态
          stroOutDTO.setAuditCode("0");
          BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
          baseDeptDTO.setHospCode(item.getHospCode());
          baseDeptDTO.setId(item.getInStroId());
          BaseDeptDTO deptById = pharApplyDetailDAO.getDeptById(baseDeptDTO);
          if(StringUtils.isEmpty(deptById.getId())) {
            throw new AppException("未查询到入库单位");
          }
          if("4".equals(deptById.getTypeCode()) || "5".equals(deptById.getTypeCode()) || "14".equals(deptById.getTypeCode())) {
            // 出库到药房
            stroOutDTO.setOutCode("5");
          } else {
            // 出库到科室
            stroOutDTO.setOutCode("4");
          }
          // 拼装出库明细数据
          List<StroOutDetailDTO> stroOutDetails = assembleStroDetail(stroOutDTO, pharApplyDetailDTOS);
          stroOutDTOS.add(stroOutDTO);
          stroOutDetailDTOS.addAll(stroOutDetails);
        }

        if(!ListUtils.isEmpty(stroOutDetailDTOS) && !ListUtils.isEmpty(stroOutDTOS)) {
          stroOutDAO.insertStroOutAll(stroOutDTOS);
          stroOutDAO.insertDetails(stroOutDetailDTOS);
        }
        pharApplyDTO.setIsOut("1");
        pharApplyDAO.updateIsOut(pharApplyDTO);
        return true;
    }

  /**
    * @Menthod assembleStroDetail
    * @Desrciption 组装出库明细
    *
    * @Param
    * [stroOutDTO, pharApplyDetailDTOS]
    *
    * @Author jiahong.yang
    * @Date   2021/5/12 10:38
    * @Return java.util.List<cn.hsa.module.phar.pharapply.entity.StroOutDetail>
    **/
    public List<StroOutDetailDTO> assembleStroDetail(StroOutDTO stroOutDTO, List<PharApplyDetailDTO> pharApplyDetailDTOS) {
      List<StroOutDetailDTO> stroOutDetails = new ArrayList<>();
      for(PharApplyDetailDTO pharApplyDetailDTO:pharApplyDetailDTOS) {
        List<StroStockDetailDTO> outDetailDTO = getOutDetailDTO(stroOutDTO, pharApplyDetailDTO);
        for (StroStockDetailDTO stockDetailDTO: outDetailDTO) {
            StroOutDetailDTO stroOutDetail = new StroOutDetailDTO();
            // 主键
            stroOutDetail.setId(SnowflakeUtils.getId());
            // 医院编码
            stroOutDetail.setHospCode(stockDetailDTO.getHospCode());
            // 出库主键id
            stroOutDetail.setOutId(stroOutDTO.getId());
            // 项目类别
            stroOutDetail.setItemCode(stockDetailDTO.getItemCode());
            // 项目id
            stroOutDetail.setItemId(stockDetailDTO.getItemId());
            // 项目名称
            stroOutDetail.setItemName(stockDetailDTO.getItemName());
            // 数量
            stroOutDetail.setNum(stockDetailDTO.getNum());
            // 单位代码
            stroOutDetail.setUnitCode(stockDetailDTO.getUnitCode());
            // 剂量
            stroOutDetail.setDosage(pharApplyDetailDTO.getDosage());
            // 剂量单位
            stroOutDetail.setDosageUnitCode(pharApplyDetailDTO.getDosageUnitCode());
            // 购进价
            stroOutDetail.setBuyPrice(stockDetailDTO.getBuyPrice());
            // 购进总金额
            stroOutDetail.setBuyPriceAll(stockDetailDTO.getBuyPriceAll());
            // 零售价
            stroOutDetail.setSellPrice(stockDetailDTO.getSellPrice());
            // 零售总金额
            stroOutDetail.setSellPriceAll(stockDetailDTO.getSellPriceAll());
            // 拆零单价
            stroOutDetail.setSplitPrice(stockDetailDTO.getSplitPrice());
            // 拆零数量
            stroOutDetail.setSplitNum(stockDetailDTO.getSplitNum());
            // 拆分比
            stroOutDetail.setSplitRatio(stockDetailDTO.getSplitRatio());
            // 拆零单位
            stroOutDetail.setSplitUnitCode(stockDetailDTO.getSplitUnitCode());
            // 规格
            stroOutDetail.setSpec(pharApplyDetailDTO.getSpec());
            // 批号
            stroOutDetail.setBatchNo(stockDetailDTO.getBatchNo());
            // 有效期
            stroOutDetail.setExpiryDate(stockDetailDTO.getExpiryDate());
            stroOutDetails.add(stroOutDetail);
        }
      }
      return stroOutDetails;
    }

    /**
    * @Menthod getOutDetailDTO
    * @Desrciption 获取库存明细
    *
    * @Param
    * [stroStockDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 9:24
    * @Return java.util.Map
    **/
    public List<StroStockDetailDTO> getOutDetailDTO(StroOutDTO stroOutDTO,PharApplyDetailDTO pharApplyDetailDTO) {
        StroStockDetailDTO stroStockDetailDTO = new StroStockDetailDTO();
        // 项目id
        stroStockDetailDTO.setItemId(pharApplyDetailDTO.getItemId());
        // 项目类型
        stroStockDetailDTO.setItemCode(pharApplyDetailDTO.getItemCode());
        // 批号
        stroStockDetailDTO.setBatchNo(pharApplyDetailDTO.getBatchNo());
        // 库位id
        stroStockDetailDTO.setBizId(stroOutDTO.getOutStockId());
        // 医院编码
        stroStockDetailDTO.setHospCode(pharApplyDetailDTO.getHospCode());
        //库存扣减明细
        List<StroStockDetailDTO> stockDetailOutList = new ArrayList<>();
        //扣减数量
        BigDecimal num = pharApplyDetailDTO.getNum();
        //扣减拆零数量
        BigDecimal splitNum = pharApplyDetailDTO.getSplitNum();
        //获取库存明细表
        List<StroStockDetailDTO> stroStockDetailOutList = stroStockDAO.getStockDetail(stroStockDetailDTO);
        if(ListUtils.isEmpty(stroStockDetailOutList)) {
          throw new AppException(pharApplyDetailDTO.getItemName() + "库存不足");
        }
        //（出库先进先出：明细表数量一条一条减少）
        for(StroStockDetailDTO stroStockDetailOut : stroStockDetailOutList){
          //判断出库数量是否大于库存明细批次的数量
          if(BigDecimalUtils.compareTo(splitNum, stroStockDetailOut.getSplitNum()) > 0) {
            //出库数量减掉，在计算下条
            num = BigDecimalUtils.subtract(num, stroStockDetailOut.getNum());
            //出库数量减掉，在计算下条
            splitNum = BigDecimalUtils.subtract(splitNum, stroStockDetailOut.getSplitNum());
            //库存不够，全部出库
            stockDetailOutList.add(stroStockDetailOut);
          } else {
            //库存出库拆零数量
            stroStockDetailOut.setSplitNum(splitNum);
            //库存出库数量
            stroStockDetailOut.setNum(BigDecimalUtils.divide(splitNum, stroStockDetailOut.getSplitRatio()));
            //库存出库总价格
            stroStockDetailOut.setSellPriceAll(BigDecimalUtils.multiply(stroStockDetailOut.getNum(), stroStockDetailOut.getSellPrice()));
            //库存出库总价格
            stroStockDetailOut.setBuyPriceAll(BigDecimalUtils.multiply(stroStockDetailOut.getNum(),stroStockDetailOut.getBuyPrice()));
            stockDetailOutList.add(stroStockDetailOut);

            num = BigDecimal.valueOf(0);
            splitNum = BigDecimal.valueOf(0);
            break;
          }
        }
        if (splitNum.compareTo(BigDecimal.valueOf(0)) > 0) {
          throw new AppException("[" + pharApplyDetailDTO.getItemName()+ "]库存不足");
        }
        return stockDetailOutList;
  }

    /**
    * @Menthod applyOrderByminOrUp
    * @Desrciption 根据库存上下限生成领药申请单
    *
    * @Param
    * [pharApplyDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/12/15 15:07
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean insertapplyOrderByminOrUp(PharApplyDTO pharApplyDTO) {
      List<PharApplyDetailDTO> pharApplyDetailDTOS = new ArrayList<>();
      // limitFlag  1: 按下限生成  2：按上限生成 3:按消耗量生成,按消耗量生成 会重复生成
      if("1".equals(pharApplyDTO.getLimitFlag())){
        pharApplyDetailDTOS = pharApplyDAO.queryNeedSupplementMin(pharApplyDTO);
      } else if ("2".equals(pharApplyDTO.getLimitFlag())){
        pharApplyDetailDTOS = pharApplyDAO.queryNeedSupplementUp(pharApplyDTO);
      } else {
          pharApplyDetailDTOS = pharApplyDAO.queryNeedSupplementDate(pharApplyDTO);
          if (ListUtils.isEmpty(pharApplyDetailDTOS)){
              throw new AppException("这段时间无消耗");
          }
          Iterator<PharApplyDetailDTO> pharApplyIterators = pharApplyDetailDTOS.iterator();
          // 只生产负的
          while (pharApplyIterators.hasNext()){
              PharApplyDetailDTO applyDetailDTO = pharApplyIterators.next();
              if(BigDecimalUtils.lessZero(applyDetailDTO.getNum())){
                  applyDetailDTO.setNum(applyDetailDTO.getNum().abs());
              } else {
                  pharApplyIterators.remove();
              }
          }
      }
      if(ListUtils.isEmpty(pharApplyDetailDTOS)) {
        throw new AppException("没有需要生成的库存");
      }
      pharApplyDTO.setId(SnowflakeUtils.getId());
      Map map =new HashMap();
      map.put("hospCode", pharApplyDTO.getHospCode());
      map.put("typeCode", Constants.ORDERRULE.LY);
      WrapperResponse<String> response = baseOrderRuleService.getOrderNo(map);
      // 设置领药申请主表参数
      pharApplyDTO.setOrderNo(response.getData());
      pharApplyDTO.setAuditStatus("0");
      BigDecimal sellPriceAll = BigDecimal.valueOf(0);
      BigDecimal buyPriceAll = BigDecimal.valueOf(0);
      PharApplyDetailDTO item = new PharApplyDetailDTO();
      // 设置领药申请明细表参数
      for (int i = 0; i < pharApplyDetailDTOS.size(); i++) {
        item = pharApplyDetailDTOS.get(i);
        item.setApplyId(pharApplyDTO.getId());
        item.setId(SnowflakeUtils.getId());
        // 拆零数量向上取整
        BigDecimal splitNum = BigDecimalUtils.multiply(item.getNum(),item.getSplitRatio());
        BigDecimal bigDecimal = splitNum.setScale(0, BigDecimal.ROUND_UP);
        item.setSplitNum(bigDecimal);
        // 重新计算数量
        item.setNum(BigDecimalUtils.divide(bigDecimal,item.getSplitRatio()));
        sellPriceAll = BigDecimalUtils.add(sellPriceAll,item.getSellPriceAll());
        buyPriceAll = BigDecimalUtils.add(buyPriceAll,item.getBuyPriceAll());
      }
      pharApplyDTO.setBuyPriceAll(buyPriceAll);
      pharApplyDTO.setSellPriceAll(sellPriceAll);
      pharApplyDTO.setIsOut("0");
      pharApplyDetailDAO.insert(pharApplyDetailDTOS);
      pharApplyDAO.insert(pharApplyDTO);
      return true;
    }

    /**
    * @Menthod queryStockApply
    * @Desrciption 查询领药申请明细库存是否足够
    *
    * @Param
    * [pharApplyDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/12/16 11:19
    * @Return java.lang.Boolean
    **/
    @Override
    public Map queryStockApply(PharApplyDTO pharApplyDTO) {
      if(ListUtils.isEmpty(pharApplyDTO.getIds())) {
        throw new AppException("待审核数据为空");
      }
      Map check = new HashMap();
      StringBuilder message = new StringBuilder();
      AtomicInteger itemNum = new AtomicInteger();
      for (String id : pharApplyDTO.getIds()) {
        pharApplyDTO.setId(id);
        PharApplyDetailDTO pharApplyDetailDTO = new PharApplyDetailDTO();
        // 根据id查询领药申请主表
        PharApplyDTO byId = pharApplyDAO.getById(pharApplyDTO);
        // 查询明细数据
        pharApplyDetailDTO.setApplyId(id);
        pharApplyDetailDTO.setHospCode(byId.getHospCode());
        List<PharApplyDetailDTO> pharApplyDetailDTOS = pharApplyDetailDAO.pharApplyDetail(pharApplyDetailDTO);
        if(ListUtils.isEmpty(pharApplyDetailDTOS)) {
          throw new AppException("领药数据为空");
        }
        for(PharApplyDetailDTO item : pharApplyDetailDTOS) {
          item.setBizId(byId.getOutStroId());
          // 校验库存
          if(ListUtils.isEmpty(pharApplyDetailDAO.queryStockApply(item))) {
            itemNum.getAndIncrement();
            check.put("flag",true);
            if(itemNum.get() <= 6) {
              message.append("【");
              message.append(item.getItemName());
              message.append("】,");
            }
          }
        }
        check.put("message",message);
      }
      return check;
    }

}
