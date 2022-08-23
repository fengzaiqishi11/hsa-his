package cn.hsa.module.outpt.outinInvoice.dao;

import cn.hsa.module.inpt.fees.entity.InptSettleInvoiceDO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceDTO;
import cn.hsa.module.outpt.fees.entity.OutptSettleInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinPartInvoiceDO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: cn.hsa.module.outpt.fee.dao
 * @Class_name: InvoiceDAO
 * @Description: 发票管理数据访问层
 * @Author: liaojiguang
 * @Email: jiguang.liao@powersi.com.cn
 * @Date: 2020/8/17 08:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutinInvoiceDAO {

    /**
     * @Method: getInvoiceReceivePage
     * @Description: 分页显示所有发票领用正常信息
     * @Param: invoiceDTO 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/17 08:38
     * @Return: 发票领用信息的数据传输对象集合
     */
    List<OutinInvoiceDTO> getInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    List<OutinInvoiceDTO> getInvoiceList(OutinInvoiceDTO outinInvoiceDTO);

    void updateOutinInvoiceStatus(OutinInvoiceDTO outinInvoiceDTO);

    void insertOutinInvoiceDetail(List<OutinInvoiceDTO> list);

    /**
     * @Method: checkInvoice
     * @Description: 检查发票号码是否被使用，大于0被使用
     * @Param: outinInvoiceDTO 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/24 17:38
     * @Return: 行数
     */
    int checkInvoice(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Method: insertOutinInvoice
     * @Description: 新增一条发票领用信息
     * @Param: outinInvoiceDTO 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/24 17:38
     * @Return: 影响行数
     */
    int insertOutinInvoice(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Method: checkInvoiceDetailStatus
     * @Description: 检查退领发票是否用完或者退领
     * @Param: outinInvoiceDTO 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/25 09:16
     * @Return: 行数
     */
    int checkInvoiceStatus(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Method: checkInvoiceDetailStatus
     * @Description: 判断退领开始号码是否等于当前号码
     * @Param: outinInvoiceDTO 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/25 09:17
     * @Return: 行数
     */
    int checkInvoiceStartNum(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Method: checkInvoiceDetailStatus
     * @Description: 判断退领结束号码是否等于截止号码
     * @Param: outinInvoiceDTO 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/25 09:17
     * @Return: 行数
     */
    int checkInvoiceEndNum(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Method: insertOutinInvoiceDetailBatch
     * @Description: 批量插入发票明细表数据
     * @Param: list 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/25 09:17
     * @Return: 影响行数
     */
    int insertOutinInvoiceDetailBatch(List<OutinInvoiceDetailDO> insertList);

    /**
     * @Method: getOutinInvoiceById
     * @Description: 根据id + hospCode 获取实体信息
     * @Param: list 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/25 09:17
     * @Return: 行数
     */
    OutinInvoiceDO getOutinInvoiceById(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Method: updateOutinInvoice
     * @Description: 更新发票主表信息
     * @Param: list 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/25 09:17
     * @Return: 影响行数
     */
    int updateOutinInvoice(OutinInvoiceDO outinInvoiceDO);

    /**
     * @Method: updateOutinInvoice
     * @Description: 检查当前作废号码段是否有号码已使用
     * @Param: list 数据传输对象
     * @Author: liaojiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/25 09:17
     * @Return: 行数
     */
    int checkInvoiceNumSegment(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod: getOutptRegisterInvoicePage
     * @Desrciption: 根据条件获取门诊挂号票据数据
     * @Param: [map]
     * @Author: lioajiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/26 10:00
     * @Return: list集合对象
     **/
    List<OutinInvoiceDTO> queryOutptRegisterInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod: getOutptInvoicePage
     * @Desrciption: 根据条件获取门诊结算发票票据数据
     * @Param: [map]
     * @Author: lioajiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/26 10:00
     * @Return: list集合对象
     **/
    List<OutinInvoiceDTO> queryOutptInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod: getHospInvoicePage
     * @Desrciption: 根据条件获取住院结算发票票据数据
     * @Param: [map]
     * @Author: lioajiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/26 10:00
     * @Return: list集合对象
     **/
    List<OutinInvoiceDTO> queryHospInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod: getHospInvoicePage
     * @Desrciption: 根据条件获取用户信息
     * @Param: [map]
     * @Author: lioajiguang
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2020/8/26 10:00
     * @Return: 用户实体对象
     **/
    Map<String,Object> getUserNameByUserId(Map map);

    /**
     * @Menthod getInvoiceDetailPage()
     * @Desrciption   根据条件分页查询发票领用详细信息
     * @Param 发票ID,医院编码
     * @Param
     * @Author liaojiguang
     * @Date   2020/09/02 10:04
     * @Return 集合对象
     **/
    List<OutinInvoiceDetailDO> queryInvoiceDetailPage(OutinInvoiceDetailDO outinInvoiceDetailDO);

    /**
     * @Menthod queryRegiestItemInfoByParams()
     * @Desrciption   获取挂号发票费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    List<Map<String, Object>> queryRegiestItemInfoByParams(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryOutptItemInfoByParams()
     * @Desrciption   获取门诊发票费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    List<Map<String, Object>> queryOutptItemInfoByParams(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryChangeDetails()
     * @Desrciption 获取费用明细信息
     * @Param outinInvoiceDTO
     * @Author caoliang
     * @Date 2021/05/20 10:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < Map < String, Object>>>
     **/
    List<Map<String, Object>> queryChangeDetails(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryOutinItemInfoByParams()
     * @Desrciption   获取住院发票费用明细信息（sql未写）
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    List<Map<String, Object>> queryOutinItemInfoByParams(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod insertOutinInvoiceDetail
     * @Desrciption  新增发票明细信息
     * @param outinInvoiceDetailDO 发票明细参数
     * @Author Ou·Mr
     * @Date 2020/9/14 14:30
     * @Return int 受影响的行数
     */
    int insertOutinInvoiceDetail(OutinInvoiceDetailDO outinInvoiceDetailDO);

    /**
     * @Menthod queryRegiestItemInfoByParams()
     * @Desrciption   获取挂号发票费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    List<Map<String, Object>> queryRegiestItemInfoDetail(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryOutptItemInfoByParams()
     * @Desrciption   获取门诊发票费用明细信息
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    List<Map<String, Object>> queryOutptItemInfoDetail(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryOutinItemInfoByParams()
     * @Desrciption   获取住院发票费用明细信息（sql未写）
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    List<Map<String, Object>> queryOutinItemInfoDetail(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryOutinInvoiceForUpdate
     * @Desrciption  查询发票信息并且锁定数据 for update
     * @param outinInvoiceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/14 15:43 
     * @Return java.util.List<cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO>
     */
    List<OutinInvoiceDTO> queryOutinInvoiceForUpdate(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod updateOutinInvoiceDetail
     * @Desrciption  作废明细
     * @param map 查询条件
     * @Author liaojiguang
     * @Date 2020/9/14 15:43
     * @Return boolean
     */
    int updateOutinInvoiceDetail(Map<String, Object> map);

    /**
     * @Menthod updatePrintChecklist()
     * @Desrciption  打印清单
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return int
     **/
    Integer updatePrintChecklist(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryInactiveOutinInvoices()
     * @Desrciption  获取待用发票
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/08/26 10:00
     * @Return java.util.List
     **/
    List<OutinInvoiceDTO> queryInactiveOutinInvoices(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod getInvocieDetailInfo()
     * @Desrciption  获取待发票详情信息
     * @Param [map]
     * @Author liaojiguang
     * @Date   2020/11/18 11:10
     * @Return java.util.List
     **/
    OutinInvoiceDetailDO getInvocieDetailInfo(Map<String, Object> map);

    /**
     * @Menthod updateSettleInvoice
     * @Desrciption  更新发票结算数据
     * @Param [selectEntity]
     * @Author liaojiguang
     * @Date   2020/11/18 11:10
     * @Return java.util.List
     **/
    Boolean updateSettleInvoice(OutinInvoiceDO outinInvoiceDO);

    /**
     * @Menthod updateRegisterSettleInvoice
     * @Desrciption  更新挂号发票结算数据
     * @Param [selectEntity]
     * @Author liaojiguang
     * @Date   2020/11/18 11:10
     * @Return java.util.List
     **/
    Boolean updateRegisterSettleInvoice(OutinInvoiceDO selectEntity);

    /**
     * @Menthod queryOutptCurrencyInvoicePage()
     * @Desrciption  获取门诊通用发票
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/12/08 10:00
     * @Return java.util.List
     **/
    List<OutinInvoiceDTO> queryOutptCurrencyInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryCurrencyInvoicePage()
     * @Desrciption  获取通用发票
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/12/08 10:00
     * @Return java.util.List
     **/
    List<OutinInvoiceDTO> queryCurrencyInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryAllInvoicePage()
     * @Desrciption  获取所有发票信息
     * @Param [outinInvoiceDTO]
     * @Author liaojiguang
     * @Date   2020/12/08 10:00
     * @Return java.util.List
     **/
    List<OutinInvoiceDTO> queryAllInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Description: 获取门诊发票信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/9 9:59
     * @Return
     */
    List<OutinInvoiceDTO> queryMZInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Description: 获取住院发票信息
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/9 10:00
     * @Return
     */
    List<OutinInvoiceDTO> queryZYInvoicePage(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod updateInvoiceDetailById
     * @Desrciption 更新发票详情
     * @param outinInvoiceDetailDO  入参
     * @Author liaojiguang
     * @Date   2020/12/28 10:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    Boolean updateInvoiceDetailById(OutinInvoiceDetailDO outinInvoiceDetailDO);

    /**
     * @Menthod getUsedInvoiceByUseId
     * @Desrciption 获取当前登录人的在用发票
     * @param outinInvoiceDTO  入参
     * @Author liaojiguang
     * @Date   2020/12/28 10:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    List<OutinInvoiceDTO> getUsedInvoiceByUseId(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod updateInvoiceUseInfo
     * @Desrciption 清空发票号段使用人，状态改为待用状态
     * @param map  [crteId] 当前日结缴款人 [hospCode] 医院编码
     * @Author liaojiguang
     * @Date   2020/12/28 17:28
     * @Return java.lang.Boolean
     **/
    Boolean updateInvoiceUseInfo(Map<String, Object> map);

    /**
     * @Menthod updateInptSettleInvoice
     * @Desrciption 住院发票处理
     * @param updateEntity
     * @Author liaojiguang
     * @Date   2020/12/28 17:28
     * @Return java.lang.Boolean
     **/
    Boolean updateInptSettleInvoice(OutinInvoiceDTO updateEntity);

    /**
     * @Menthod getSettleInvoiceById
     * @Desrciption 获取住院结算发票
     * @param updateEntity
     * @Author liaojiguang
     * @Date   2020/12/28 17:28
     * @Return java.lang.Boolean
     **/
    InptSettleInvoiceDO getSettleInvoiceById(OutinInvoiceDTO updateEntity);

    /**
     * @Menthod getInptSettleInfoById
     * @Desrciption 获取结算总金额
     * @param entity
     * @Author liaojiguang
     * @Date   2020/12/28 17:28
     * @Return java.lang.Boolean
     **/
    BigDecimal getInptSettleInfoById(InptSettleInvoiceDO entity);

    Boolean insertInptSettleInvoice(InptSettleInvoiceDO entity);

    OutptSettleInvoiceDO getOutptSettleInvoiceById(OutptSettleInvoiceDTO outptSettleInvoiceDTO);

    BigDecimal getOutptSettleInfoById(OutptSettleInvoiceDO entity);

    Boolean insertOutptSettleInvoice(OutptSettleInvoiceDO entity);

    /**
     * @Description: 查询费用清单（门诊），例如：材料费：200.00 西药费： 890.00
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/15 10:36
     * @Return
     */
	List<OutptCostDTO> getCostInventory(Map<String, Object> map);
	/**
	 * @Description: 查询费用清单(住院)，例如：材料费：200.00 西药费： 890.00
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/1/20 19:20
	 * @Return
	 */
    List<OutptCostDTO> getZyCostInventory(Map<String, Object> map);

    int updateOutptSettleInvoiceInfo(OutptSettleInvoiceDTO outptSettleInvoiceDTO);

    /**
     * @Description: 查询门诊挂号数据
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/11 10:04
     * @Return
     */
    List<OutptCostDTO> getMZGHCostInventory(Map<String, Object> map);

    /**
     * @Description: 查询门诊处方费用明细
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/23 14:32
     * @Return
     */
	List<Map<String, Object>> queryOutptCFItemInfoDetail(OutinInvoiceDTO outinInvoiceDTO);

	/**
	 * @Description: 根据结算id查询费用计费类别
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/24 15:30
	 * @Return
	 */
	List<Map<String, Object>> queryOutptBfcInfo(OutinInvoiceDTO outinInvoiceDTO);

	/**
	 * @Description: 根据结算id查询处方费用明细
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/24 16:27
	 * @Return
	 */
    List<Map<String, Object>> queryInptCFBfcInfo(OutinInvoiceDTO outinInvoiceDTO);

	int checkOutinInvoiceExistence(OutinInvoiceDTO outinInvoiceDTO);

	/**
	 * @Description: 查询同一使用人同一票据类型在用状态发票数
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/6/3 10:28
	 * @Return
	 */
	int selectKYFPs(OutinInvoiceDTO dto);

	/**
	 * @Description: 获取发票分单后的发票金额
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/8/18 19:36
	 * @Return
	 */
	List<Map<String, Object>> queryOutptsettleInvoiceConent(OutinInvoiceDTO outinInvoiceDTO);

    List<OutinPartInvoiceDO> queryPartInvoicePrint(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod queryOutptItemInfoDetailDate()
     * @Desrciption   获取门诊费用清单最大日期和最小日期
     * @Param outinInvoiceDTO
     * @Author liaojiguang
     * @Date   2020/09/04 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
     **/
    Map<String, Object> queryOutptItemInfoDetailDate(OutinInvoiceDTO outinInvoiceDTO);
}