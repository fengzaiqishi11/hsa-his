package cn.hsa.module.outpt.fees.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Description: 线上支付信息推送表：
 * @author： 医保开发二部-湛康
 * @date： 2022-06-20 09:25:28
 */
@Data
public class PayOnlineInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 就诊ID
	 */
	private String visitId;
	/**
	 * 结算ID
	 */
	private String settleId;
	/**
	 * 和ORG_001的his_cust_list内的his_cust_type是同样的意思
	 */
	private String hisCustType;
	/**
	 * 和ORG_001his_cust_list内的his_cust_type是同样的意思
	 */
	private String hisCustId;
	/**
	 * 应用类型 01 门诊 02 住院 03 挂号 04 线上
	 */
	private String appType;
	/**
	 * 医疗机构的订单号，必填，通过这个单号来回查推送
	 */
	private String medOrgOrd;
	/**
	 * 内部机构编号，以用来区分分院或者基卫，如果为空则默认为本院该字典需由定点机构提供
	 */
	private String subOrgCode;
	/**
	 * 只有pushType未支付单，且状态为已退款或已部分退款的时候才需要进行填写
	 */
	private String refundReason;
	/**
	 * 省平台订单号，如果自行上传了省医保移动支付中心的情况下才需要进行填写
	 */
	private String payOrdId;
	/**
	 * pushType类型为HOSPITAL_PAYMENT时必填  MERCHANT_WAIT_PAY 待支付 MERCHANT_PAID 已支付 MERCHANT_PART_REFUNDED 已部分退款 MERCHANT_REFUNDED 已全额退款 MERCHANT_CLOSED 已取消
	 */
	private String orderStatus;
	/**
	 * 机构国家统一编码，必填
	 */
	private String orgCode;
	/**
	 * 用户姓名，必填
	 */
	private String patientName;
	/**
	 * 用户证件类型，必填
	 */
	private String idType;
	/**
	 * 用户证件号，必填
	 */
	private String idNo;
	/**
	 * 只有当pushType为医药单，且状态为已支付的情况下才需要填写此取药地址进行取药信息推送
	 */
	private String takeMedicineLoc;
	/**
	 * HOSPITAL_APPOINTMENT 挂号单 HOSPITAL_CHECK检查单 HOSPITAL_MEDICINE医药单 HOSPITAL_PAYMENT支付单
	 */
	private String pushType;
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 */
	private String scheduledTime;
	/**
	 * 当有“自定义页面时”，可进行传入，用户点击详情时将进入此自定义详情
	 */
	private String redirectUrl;
	/**
	 * 当推送类型为“支付单”时必填，当为“退款通知”时传“退款金额”，待结算或已结算请传“费用总额”
	 */
	private String amount;
	/**
	 * 当推送类型是支付单或检查单或挂号单类型时候才需要填写
	 */
	private String deptName;
	/**
	 * 当推送类型是支付单或检查单或挂号单类型时候才需要填写
	 */
	private String deptCode;
	/**
	 * 当推送类型是支付单或检查单或挂号单类型时候才需要填写
	 */
	private String deptLoc;
	/**
	 * 当推送类型是支付单或检查单或挂号单类型时候才需要填写
	 */
	private String drName;
	/**
	 * 当推送类型是支付单或检查单或挂号单类型时候才需要填写
	 */
	private String drLvName;
	/**
	 * 当推送类型是支付单或检查单或挂号单类型时候才需要填写
	 */
	private String drLvNo;
	/**
	 * 当推送类型是医药单或挂号单时，才需要填写此候诊号，推送提醒用户排队情况
	 */
	private String waitingNum;
	/**
	 * 只有当推送类型是检查单时，才需要填写
	 */
	private String checkItem;
	/**
	 * 当推送的状态为已支付或已退款或已部分退款类型的状态时候才需要填写
	 */
	private String createTime;
	/**
	 * 当推送的状态为已支付或已退款或已部分退款类型的状态时，才需要填写
	 */
	private String updateTime;
	/**
	 * 区域平台交易跟踪号，在ORG_003接口会写入给到机构，机构需要进行存储，需要进行线上自费退款时会用到
	 */
	private String ampTraceId;
	/**
	 * 平台结算跟踪号，在ORG_003接口会写入给到机构，机构需要进行存储，需要进行线上自费退款时会用到
	 */
	private String traceId;
	/**
	 * 该值与接口ORG_002(查询待结算费用列表与明细) 的返回值org_trace_no一样
	 */
	private String orgTraceNo;
	/**
	 * 结算退款ID
	 */
	private String redSettleId;

	private String docTraceNo;

	private String callSn;



	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public String getVisitId() {
		return visitId;
	}

	public void setSettleId(String settleId) {
		this.settleId = settleId;
	}

	public String getSettleId() {
		return settleId;
	}

	public void setHisCustType(String hisCustType) {
		this.hisCustType = hisCustType;
	}

	public String getHisCustType() {
		return hisCustType;
	}

	public void setHisCustId(String hisCustId) {
		this.hisCustId = hisCustId;
	}

	public String getHisCustId() {
		return hisCustId;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppType() {
		return appType;
	}

	public void setMedOrgOrd(String medOrgOrd) {
		this.medOrgOrd = medOrgOrd;
	}

	public String getMedOrgOrd() {
		return medOrgOrd;
	}

	public void setSubOrgCode(String subOrgCode) {
		this.subOrgCode = subOrgCode;
	}

	public String getSubOrgCode() {
		return subOrgCode;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setPayOrdId(String payOrdId) {
		this.payOrdId = payOrdId;
	}

	public String getPayOrdId() {
		return payOrdId;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setTakeMedicineLoc(String takeMedicineLoc) {
		this.takeMedicineLoc = takeMedicineLoc;
	}

	public String getTakeMedicineLoc() {
		return takeMedicineLoc;
	}

	public void setPushType(String pushType) {
		this.pushType = pushType;
	}

	public String getPushType() {
		return pushType;
	}

	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getScheduledTime() {
		return scheduledTime;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmount() {
		return amount;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptLoc(String deptLoc) {
		this.deptLoc = deptLoc;
	}

	public String getDeptLoc() {
		return deptLoc;
	}

	public void setDrName(String drName) {
		this.drName = drName;
	}

	public String getDrName() {
		return drName;
	}

	public void setDrLvName(String drLvName) {
		this.drLvName = drLvName;
	}

	public String getDrLvName() {
		return drLvName;
	}

	public void setDrLvNo(String drLvNo) {
		this.drLvNo = drLvNo;
	}

	public String getDrLvNo() {
		return drLvNo;
	}

	public void setWaitingNum(String waitingNum) {
		this.waitingNum = waitingNum;
	}

	public String getWaitingNum() {
		return waitingNum;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}

	public String getCheckItem() {
		return checkItem;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setAmpTraceId(String ampTraceId) {
		this.ampTraceId = ampTraceId;
	}

	public String getAmpTraceId() {
		return ampTraceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setOrgTraceNo(String orgTraceNo) {
		this.orgTraceNo = orgTraceNo;
	}

	public String getOrgTraceNo() {
		return orgTraceNo;
	}

	public void setRedSettleId(String redSettleId) {
		this.redSettleId = redSettleId;
	}

	public String getRedSettleId() {
		return redSettleId;
	}
}
