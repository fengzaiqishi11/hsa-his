package cn.hsa.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: Constants
 * @Describe(描述):公共常量工具类
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/26 10:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface Constants {

    /**
     * 推送类型（MESSAGE）
     */
    class MESSAGETYPE {
        /**
         *  系统公告
         */
        public static final String ANNOUNCEMENT = "1";
        /**
         *  系统消息
         */
        public static final String SYSTEMMESSAGE = "2";
    }

    /**
     * 抽取类型（CQLX）
     */
    class CQLX {
        public static final String YFYKXH = "1"; // 药房药库消耗
        public static final String YFYKSSJXC = "2"; // 药房药库实时进销存
        public static final String AGYS = "3"; // 按供应商
        public static final String AGYSPZ = "4"; // 按供应商/品种
        public static final String AGYSPZPC = "5"; // 按供应商/品种批次
        public static final String AGYSRKDJ = "6"; // 按供应商/入库单据
        public static final String AGYSRKDJMX = "7"; // 按供应商/入库单据明细
        public static final String AGYSJFLB = "8"; // 按供应商/计费类别
        public static final String AGYSCLFL = "9"; // 按供应商/材料分类

        public static final String AYF = "10"; // 按药房
        public static final String AYFPZ = "11"; // 按药房/品种
        public static final String AYFPZPC = "12"; // 按药房/品种批次
        public static final String AYFCKDJ = "13"; // 按药房/出库单据
        public static final String AYFJFLB = "14"; // 按药房/计费类别
        public static final String AYFCKDJMX = "15"; // 按药房/出库单据明细
        public static final String AYFCLFL = "16"; // 按药房/材料分类

        public static final String AKS = "17"; // 按科室
        public static final String AKSPZ = "18"; // 按科室/品种
        public static final String AKSPZPC = "19"; // 按科室/品种批次
        public static final String AKSCKDJ = "20"; // 按科室/出库单据
        public static final String AKSCKDJMX = "21"; // 按科室/出库单据明细
        public static final String AKSJFLB = "22"; // 按科室/计费类别
        public static final String AKSCLFL = "23"; // 按科室/材料分类

        public static final String BSAPZ = "24"; // 报损/按品种
        public static final String BSAPZPC = "25"; // 报损/按品种批次
        public static final String BSAYWFL = "26"; // 报损/按业务单据
        public static final String BSAJFLB = "27"; // 报损/按计费类别

        public static final String KFPDHZ = "28"; // 库房盘点汇总
        public static final String KFTJHZ = "29"; // 库房盘点汇总



    }

    /**
     * 病人来源途径（LYTJ）
     */
    class LYTJ {
        public static final String ZCGH = "1"; // 正常挂号
        public static final String YYGH = "2"; // 预约挂号
        public static final String ZZJGH = "3"; // 自助机挂号
    }

    /**
     * 医保区划开头（YBQHKT）
     */
    class YBQHKT {
        public static final String GD = "44"; // 广州

    }

    class TechnologyStatus {
        public static final String NOTCONFIRMCOST = "0"; // 未确费
        public static final String CONFIRMCOST = "1"; // 已确费
        public static final String REFUNDCOST = "2"; // 申请退费
        public static final String ALREADYREFUND = "3"; // 已退费
    }
    /**
     * 颜色（color）
     */
    class COLOR {
        public static final String RED = "1"; // 红色
    }
    class LNDW{
        public static final String S = "1"; // 岁
        public static final String Y= "2"; // 月
        public static final String Z= "3"; // 周
        public static final String T = "4"; // 天
    }
    /**
     * 证件类别（ZJLB）
     */
    class ZJLB {
        public static final String JMSFZ = "01"; // 居民身份证
        public static final String JMHKB = "02"; // 居民户口簿
        public static final String HZ = "03"; // 护照
        public static final String JGZ = "04"; // 军官证
        public static final String JSZ = "05"; // 驾驶证
        public static final String GATXZ = "06"; // 港澳居民来往内地通行证
        public static final String TWTXZ = "07"; // 台湾居民来往内地通行证
        public static final String QT = "99"; // 其他
    }

    /**
     * 来源标志（LYBZ）
     */
    class LYBZ {
        public static final String GHKS = "0"; // 挂号科室
        public static final String ZJJZ = "1"; // 直接就诊
        public static final String FZTGH = "2"; // 分诊台挂号
        public static final String ZZSB = "3"; // 自助设备
        public static final String WX = "4"; // 微信
        public static final String ZFB = "5"; // 支付宝
    }

    /**
     * @Menthod SF
     * @Desrciption 是否（常量定义）
     * @Author Ou·Mr
     * @Date 2020/8/26 11:03
     */
    class SF{
        /**
         * 是
         */
        public static final String S = "1";
        /**
         * 否
         */
        public static final String F = "0";
    }

    /**
     * @Menthod SSZT
     * @Desrciption 手术状态（常量定义）
     */
    class OPER_STATUS_CHANGE{
        /** 手术申请取消 **/
        public static final String APPLY_CANCEL = "1";
        /** 已申请 **/
        public static final String APPLIED = "1";
        /** 已安排 **/
        public static final String ARRANGED = "2";
        /** 已完成 **/
        public static final String COMPLETED= "3";
        /** 已完成 **/
        public static final String ACCOUNTED= "4";
        /** 已开医嘱但未审核 **/
        public static final String MEDICAL_ORDERS_WRITENED_NOT_BEEN_CHECKED = "-2";

    }
    /**
     * @Menthod SF
     * @Desrciption 是否使用个人账户（常量定义）
     * @Author Ou·Mr
     * @Date 2020/8/26 11:03
     */
    class IS_USER_ACCOUNT{
        public static final String S = "1"; //是
        public static final String F = "0"; //否
        public static final String T = "3"; //自定义
    }

    /**
     * @Menthod JSZT
     * @Desrciption 结算状态（常量定义）
     * @Author Ou·Mr
     * @Date 2020/8/26 11:03
     */
    class JSZT{
        public static final String WJS = "0";//未结算
        public static final String YUJS = "1";//预结算
        public static final String YIJS = "2";//已结算
    }

    /**
     * @Menthod FYLYFS
     * @Desrciption 费用来源方式代码（常量定义）
     * @Author Ou·Mr
     * @Date 2020/8/26 11:03
     */
    class FYLYFS{
        public static final String CF = "0";//处方
        public static final String ZJHJSF = "1";//直接划价收费
        public static final String YFTYTF = "2";//药房退药退费
        public static final String DJTJF = "3";//动静态计费
        public static final String PS = "4";//皮试
        public static final String PSHYYP = "5";//皮试换药药品
        public static final String YZ = "6"; //医嘱
        public static final String CQJZ_YZ = "7"; //长期记账-医嘱
        public static final String BJZ = "8"; //补记帐
        public static final String QTFY = "9";//其他费用
        public static final String ZJJZ = "10";//直接就诊
        public static final String CQJZ_WC = "11";//长期记账-床位
        public static final String GHJZ = "12";//挂号就诊
    }

    /**
     * @Menthod YHFS
     * @Desrciption 优惠方式（常量定义）
     * @Author Ou·Mr
     * @Date 2020/8/26 11:03
     */
    class YHFS{
        public static final String ABL = "1"; //按比例
        public static final String AJE = "2"; //按金额
    }

    /**
     * @Menthod ZTBZ
     * @Desrciption 门诊费用状态标志（常量定义)
     * @Author Ou·Mr
     * @Date 2020/8/26 11:03
     */
    class ZTBZ{
        public static final String ZC = "0"; //正常
        public static final String BCH = "1";//被冲红
        public static final String CH = "2"; //冲红
    }
    /**
     * @Description: 就诊类型
     * @Author: zhangguorui
     * @Date: 2022/2/18
     */
    class VISITTYPE {
        public static final String OUTPT = "1"; // 门诊
        public static final String INPT = "2"; // 住院
    }
    /**
     * @Menthod ORDERRULE
     * @Desrciption  单据规则（常量定义）
     * @Author Ou·Mr
     * @Date 2020/8/27 19:13
     */
    class ORDERRULE{
        public static final String CG = "01";//采购单号生成规则
        public static final String RK = "02";//入库单号生成规则（药库）
        public static final String CK = "03";//出库单号生成规则（药库）
        public static final String TK = "04";//退库单号生成规则（药房）
        public static final String TH = "05";//退供应商号生成规则（药库）
        public static final String PD = "06";//盘盈盘亏单号生成规则
        public static final String BS = "07";//报损报溢单号生成规则
        public static final String TB = "08";//调拨出库单号生成规则
        public static final String CW = "9";//床位单号生成规则
        public static final String CD = "10";//菜单编号生成规则
        public static final String YZ = "11";//医嘱编码生成规则
        public static final String FYCK = "12";//发药窗口编码生成规则
        //public static final String PD = "13";//盘点单编码生成规则
        public static final String LY = "14";//领药单号生成规则（药房）
        public static final String YHZXTGX = "15";//用户子系统关系编码生成规则
        public static final String ZY = "16";//住院结算单号
        public static final String JZ = "17";//门诊就诊（划价收费）
        public static final String JZJS = "18";//科室编码生成规则
        public static final String CL = "22";//材料编码生成规则
        public static final String SCQY = "23";//生产企业编码生成规则
        public static final String YP = "24";//药品编码生成规则
        public static final String XM = "25";//项目编码生成规则
        public static final String YJJ = "31";//生产企业编码生成规则
        public static final String ZYH = "37";//生产住院号生成规则
        public static final String LYDJLX = "34";//领药单据规则生成规则
        public static final String GHDJ = "100";//挂号单据
        public static final String JZH = "101";//就诊号
        public static final String CFDH = "102";//处方单号
        public static final String RJJK = "39";//日结单号
        public static final String TJ = "46";//调价单据规则生成规则
        public static final String FY = "47";//门诊发药/住院发药
        public static final String YJ = "48";//医技
        public static final String TXM = "49";// 条形码
        public static final String YSYZ = "105";//医生站医嘱
        public static final String LCLJML = "119";//临床路径目录
        public static final String LCLJXM ="121";//临床路径项目
        public static final String LCLJJDMS ="122";//临床路径阶段描述
        public static final String LCJDMX = "120";//临床路径阶段明细
        public static final String YBJSQD = "130";// 医保结算清单

    }

    /**
     * @Menthod ZFLY
     * @Desrciption 支付来源
     * @Author Ou·Mr
     * @Date 2020/12/21 13:44
     */
    class ZFLY {
        public static final String HIS = "0"; //HIS
        public static final String WX = "1"; //微信
        public static final String ZFB = "2"; //支付宝
        public static final String ZJJ = "3"; //自助机
    }

    /**
     * @Menthod YDLX
     * @Desrciption 异动类型
     * @Author Ou·Mr
     * @Date 2020/12/21 13:44
     */
    class YDLX {
        public static final String AC = "0"; //安床
        public static final String HC = "1"; //换床
        public static final String BC = "2"; //包床
        public static final String ZK = "3"; //转科
        public static final String BCQX = "4"; //释放包床
        public static final String YCY = "5"; //预出院
        public static final String CYZH = "6"; //出院召回
        public static final String ZHC = "7"; //转换科
    }

    /**
     * @Menthod CWZT
     * @Desrciption 床位状态
     * @Author Ou·Mr
     * @Date 2020/12/21 13:44
     */
    class CWZT {
        public static final String ZC = "1"; //正常
        public static final String JC = "2"; //加床
        public static final String PC = "3"; //陪床
        public static final String XN = "4"; //虚拟
    }

    /**
     * @Menthod ZRY
     * @Desrciption 转入院标识
     * @Author Ou·Mr
     * @Date 2020/12/21 13:44
     */
    class ZRY {
        public static final String WKZYZ = "0"; //未开住院证
        public static final String YKZYZ = "1"; //已开住院证
        public static final String YRYDJ = "2"; //已入院登记
    }

    /**
     * @Menthod JZLB
     * @Desrciption 就诊类别
     * @Author Ou·Mr
     * @Date 2020/8/28 13:44
     */
    class JZLB{
        public static final String MZ = "01"; //门诊
        public static final String JZ = "02"; //急诊
    }


    /**
     * @Desrciption 划价收费redis key值（用于做划价收费结算，防止同一患者同时做同一操作）
     * @Author Ou·Mr
     * @Date 2020/8/28 9:07
     */
    String OUTPT_FEES_REDIS_KEY = "outpt_fees";

    /**
     * @Desrciption 住院结算redis key值（用于做住院结算，防止同一患者同时做同一操作）
     * @Author Ou·Mr
     * @Date 2020/8/28 9:07
     */
    String INPT_FEES_REDIS_KEY = "inpt_fees";
    /**
     * @Desrciption 门诊发药redis key值（用于做门诊发药，防止同一药房同时做同一操作）
     * @Author Ou·Mr
     * @Date 2020/8/28 9:07
     */
    String OUT_DISTRIBUTE_REDIS_KEY = "out_distribute";
    /**
     * @Desrciption 门诊发药redis key值（用于做门诊发药，防止同一药房同时做同一操作）
     * @Author Ou·Mr
     * @Date 2020/8/28 9:07
     */
    String INPT_DISTRIBUTE_REDIS_KEY = "inpt_distribute";
    /**
     * @Desrciption 医院优惠配置代码key
     * @Author Ou·Mr
     * @Date 2020/8/28 15:41
     */
    String HOSPCODE_DISCOUNTS_KEY = "JG_SF_SRFS";
    /**
     * @Desrciption 住院配药key
     * @Author zhangguorui
     * @Date 2021/11/22 15:41
     */
    String INPT_DISPENSE_REDIS_KEY = "inpt_dispense";

    /**
     * @Desrciption 住院发药退费组合key
     * @Author zhangguorui
     * @Date 2021/11/22 15:41
     */
    String INPT_DISPENSE_TF_REDIS_KEY = "inpt_dispense_tf";

    /**
     * @Desrciption 盘点key
     * @Author jiahong.yang
     * @Date 2021/11/22 15:41
     */
    String STRO_INVENTORY_TF_REDIS_KEY = "INVENTORY_ONLY";

    /**
     * @Desrciption 票据类型（0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院）
     * @Author Ou·Mr
     * @Date 2020/8/31 15:47
     */
    class PJLX{
        public static final String TY = "0"; //全院通用
        public static final String MZ = "1"; //门诊发票
        public static final String GH = "2"; //挂号发票
        public static final String MZTY = "3"; //门诊通用
        public static final String ZY = "4"; //住院
    }

    /**
     * @Desrciption 票据使用状态（使用状态代码：1、待用，2、在用，3、退领，4、作废，5、用完）
     * @Author Ou·Mr
     * @Date 2020/8/31 16:23
     */
    class PJSYZT{
        public static final String DY = "1"; //待用
        public static final String ZY = "2"; //在用
        public static final String TL = "3"; //退领
        public static final String ZF = "4"; //作废
        public static final String YW = "5"; //用完
    }

    /**
     * @Desrciption 票据明细使用状态 (0、已使用，1、退领，2、作废)
     * @Author Ou·Mr
     * @Date 2020/8/31 20:28
     */
    class PJMXSYZT{
        public static final String YSY = "0"; //已使用
        public static final String TL = "1"; //退领
        public static final String ZF = "2"; //作废
    }

    /**
     * @Desrciption 同步代码（中心平台使用）
     * @Author Ou·Mr
     * @Date 2020/9/2 9:04
     */
    class SYNCCODE{
        public static final String QL = "1"; //全量
        public static final String XZ = "2"; //选择数据同步
    }

    /**
     * @Desrciption 票据分单通用方法返回code标识
     * @Author Ou·Mr
     * @Date 2020/9/3 14:46
     */
    class PJFDRESULTCODE{
        public static final Integer ERROR  = -1; //分单失败
        public static final Integer WARN = 0; //不分单
        public static final Integer SUCCEED = 1; //分单失败
    }

    /**
     * @Desrciption 分单规则配置code（查询sys_paramter表code）
     * @Author Ou·Mr
     * @Date 2020/9/3 15:07
     */
    class FDGZCONFIGCODE{
        public static final String MZFP_FDFS = "MZFP_FDFS"; //查询分单规则是否启用（1：所有单据不分单，2：按分单参数设置分单）
        public static class MZFP_FDFS_VAL{
            public static final String N = "1"; //所有单据不分单
            public static final String Y = "2"; //按分单参数设置分单
        }
        public static final String MZFP_YPCF_FDFS = "MZFP_YPCF_FDFS"; //药品处方分单设置 只针对通过门诊医生站开出的西药处方和中草药处方有效
        public static final String MZFP_XMCF_FDFS = "MZFP_XMCF_FDFS"; //只对处置处方、检验单，检查单，材料有效
        public static final String MZFP_HJSF_FDFS = "MZFP_HJSF_FDFS"; //非处方费用分单规则
    }

    /**
     * @Desrciption 领药状态代码
     * @Author liaojiguang
     * @Date 2020/9/7 14:09
     */
    class FYZT{
        public static final String DL = "0"; //待领
        public static final String QL = "1"; //请领
        public static final String PY = "2"; //配药
        public static final String FY = "3"; //发药
    }

    /**
     * @Desrciption 退药状态代码
     * @Author liaojiguang
     * @Date 2020/9/7 14:09
     */
    class TYZT{
        public static final String YFY = "0"; //已发药
        public static final String TFWTY = "1"; //退药未退费
        public static final String TYYTF = "2"; //退药已退费
    }

    /**
     * @Desrciption 费用表退药状态代码
     * @Author liuqi1
     * @Date 2020/10/21 11:01
     */
    class COST_TYZT{
        public static final String YFY = "0"; //正常
        public static final String TFWTY = "1"; //已退费未退药
        public static final String TFYTY = "2"; //已退费已退药
        public static final String TFBTY = "3"; //已退费不退药
    }


    /**
     * @Desrciption 领药药状态代码
     * @Author Ou·Mr
     * @Date 2020/9/16 18:52
     */
    class LYZT{
        public static final String DL = "0"; //待领
        public static final String QL = "1"; //请领
        public static final String PY = "2"; //配药
        public static final String FY = "3"; //发药
    }

    /**
     * @Desrciption 项目类别
     * @Author Ou·Mr
     * @Date 2020/9/19 11:13
     */
    class XMLB{
        public static final String YP = "1";//药品
        public static final String CL = "2";//材料
        public static final String XM = "3";//项目
        public static final String YZML = "4";//医嘱目录
    }

    /**
     * @Desrciption 缴款类型代码：0、门诊挂号，1、门诊收费，2、住院
     * @Author zhongming
     * @Date 2020/9/19 11:13
     */
    class JKLX{
        public static final String MZGH = "0"; // 门诊挂号
        public static final String MZSF = "1"; // 门诊收费
        public static final String ZY = "2"; // 住院
    }

    /**
     * @Desrciption 支付方式
     * @Author zhongming
     * @Date 2020/9/19 11:13
     */
    class ZFFS{
        public static final String XJ = "1"; // 现金
        public static final String SK = "2"; // 刷卡
        public static final String ZP = "3"; // 支票
        public static final String ZZ = "4"; // 转帐
        public static final String WX = "5"; // 微信
        public static final String ZFB = "6"; // 支付宝

        public static final String GZ = "8"; // 挂账
    }

    /**
     * @Desrciption 签名状态
     * @Author zhongming
     * @Date 2020/9/19 11:13
     */
    class QMZT{
        public static final String WQM = "1"; // 未签名
        public static final String YQM = "2"; // 已签名
        public static final String QXQM = "3"; // 取消签名
    }

    /**
     * @Desrciption 病人状态
     * @Author Ou·Mr
     * @Date 2020/9/25 10:56
     */
    class BRZT{
        public static final String DR = "1"; //待入
        public static final String ZY = "2"; //在院
        public static final String GC = "3"; //挂床
        public static final String DJ = "4"; //冻结
        public static final String YCY = "5"; //预出院
        public static final String YJS = "6"; //预结算
        public static final String CY = "7"; //出院
        public static final String ZF = "8"; //作废
    }

    /**
     * @Desrciption 结算类型
     * @Author Ou·Mr
     * @Date 2020/9/28 10:56
     */
    class JSLX{
        public static final String CYJS = "0"; //出院结算
        public static final String ZTJS = "1"; //中途结算
        public static final String XSEJS = "2"; //新生儿结算
        public static final String QTJS = "3"; //其他结算
    }

    /**
     * @Desrciption 病人类型
     * @Author Ou·Mr
     * @Date 2020/9/28 10:56
     */
    class BRLX{
        public static final String PTBR = "0"; //普通病人
        public static final String SNYDBR = "12";//省内异地病人
        public static final String SWYDBR = "13";//省外异地病人
    }


    /**
     * @Desrciption 处方类别
     * @Author zengfeng
     * @Date 2020/9/29 15:47
     */
    class CFLB{
        public static final String XY = "1"; //西药
        public static final String CL = "2"; //材料
        public static final String ZCY = "3"; //中草药
        public static final String LIS = "4"; //LIS
        public static final String PACS = "5"; //PACS
        public static final String CZ = "6"; //处置
    }

    /**
     * @Desrciption 处方类型
     * @Author zengfeng
     * @Date 2020/9/29 15:47
     */
    class CFLX{
        public static final String PT = "1"; //普通
        public static final String JZ = "2"; //急诊
        public static final String EK = "3"; //儿科
        public static final String MJY = "4"; //麻、精一
        public static final String JE = "5"; //精二
        public static final String GZ = "6"; //贵重
        public static final String KFKPT = "7"; //普通（口服）
    }

    /**
     * @Desrciption 用药性质代码
     * @Author zengfeng
     * @Date 2020/9/29 15:47
     */
    class YYXZ{
        public static final String CG = "1"; //常规
        public static final String GRZB = "2"; //个人自备
        public static final String KSZB = "3"; //科室自备
        public static final String CYDY = "4"; //出院带药
    }

    /**
     * @Desrciption 出入方式
     * @Author liuqi1
     * @Date 2020/10/10 15:14
     */
    class CRFS{
        public static final String CGRK = "1"; //采购入库
        public static final String ZJRK = "2"; //直接入库
        public static final String TGYS = "3"; //退供应商
        public static final String CKDKS = "4"; //出库到科室
        public static final String CKDYF = "5"; //出库到药房
        public static final String YFTK = "6"; //药房退库
        public static final String PYPK = "7"; //盘盈盘亏
        public static final String BSBY = "8"; //报损报溢
        public static final String TJTB = "9"; //同级调拨（库房）
        public static final String YFRKQR = "20"; //药房入库确认
        public static final String YKTKQR = "21"; //药库退库确认
        public static final String TJTBQR = "22"; //同级调拨确认
        public static final String YFFY = "23"; //药房发药
        public static final String TJ = "24"; //调价
        public static final String YFTY = "25"; //药房退药
    }

    /**
     * @Desrciption 审核状态
     * @Author Ou·Mr
     * @Date 2020/9/28 10:56
     */
    class SHZT{
        public static final String WSH = "0";//未审核
        public static final String SHWC = "1";//审核完成
        public static final String ZF = "2";//作废
    }

    /**
     * @Package_name: cn.hsa.util
     * @class_name: Constants
     * @Description:  科室性质
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/15 11:19
     * @Company: 创智和宇
     **/
    class KSXZ{
        public static final String KSXZ1 = "1"; //门诊科室
        public static final String KSXZ2 = "2"; //住院科室
        public static final String KSXZ3 = "3"; //药库
        public static final String KSXZ4 = "4"; //门诊药房
        public static final String KSXZ5 = "5"; //住院药房
        public static final String KSXZ6 = "6"; //领药窗口
        public static final String KSXZ7 = "7"; //检验科室
        public static final String KSXZ8 = "8"; //影像科室
        public static final String KSXZ9 = "9"; //手术科室
        public static final String KSXZ10 = "10"; //输液室
        public static final String KSXZ11 = "11"; //分诊台
        public static final String KSXZ12 = "12"; //经管科室
        public static final String KSXZ13 = "13"; //材料库
        public static final String KSXZ14 = "14"; //材料药房
        public static final String KSXZ15 = "15"; //病区
        public static final String KSXZ16 = "16"; //虚拟科室
    }

    /**
     * @Desrciption 诊断类型
     * @Author zengfeng
     * @Date 2020/9/29 15:47
     */
    class ZDLX{
        public static final String MZZZD = "101"; //门诊主诊断
        public static final String MZCZD = "102"; //门诊次诊断
        public static final String MZZYZD ="103"; //门诊中医诊断
        public static final String ZYRYZD = "201"; //住院入院诊断
        public static final String ZYQZZD = "202"; //住院确诊诊断
        public static final String ZYCZD = "203"; //住院次诊断
        public static final String ZYCYZD = "204"; //住院出院诊断
    }

    class JBFL{
        public static final String LCJB = "1"; //ICD-10国家临床版疾病编码(ICD-10)
        public static final String ZLXT = "2"; //ICD-10国家临床版肿瘤形态学编码(M码)
        public static final String SSCZ ="3"; //ICD-9国家临床版手术操作编码(ICD-9-CM3)
        public static final String ZYBM = "4"; //中医病名
        public static final String ZYHZZD = "5"; //中医候诊诊断
        public static final String ZYZH = "6"; //中医证侯
    }

    /**
     * @Desrciption 医保配置KEY值
     * @Author Ou·Mr
     * @Date 2020/9/29 15:47
     */
    class INSURE{
        public static final String CONFIG_KEY = "INSURE-CONFIG"; //医保保存配置 KEY值
        public static final String SESSION_KEY = "INSURE-SESSION";//医保保存登录session KEY值
    }

    /**
     * @Package_name: cn.hsa.util
     * @class_name: Constants
     * @Description: 医嘱类别
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/3 17:16
     * @Company: 创智和宇
     **/
    class YZLB{
        public static final String YZLB1 = "1"; //药品
        public static final String YZLB2 = "2";//护理
        public static final String YZLB3 = "3";//LIS医技
        public static final String YZLB4 = "4";//膳食
        public static final String YZLB5 = "5";//手术
        public static final String YZLB6 = "6";//治疗
        public static final String YZLB7 = "7";//其他
        public static final String YZLB8 = "8";//输血
        public static final String YZLB9 = "9";//麻醉
        public static final String YZLB10 = "10";//文字医嘱
        public static final String YZLB11 = "11";//中草药
        public static final String YZLB12 = "12";//PACS医技
        public static final String YZLB13 = "13";//护嘱
        public static final String YZLB14 = "14";//小手术
        public static final String YZLB15 = "15";//材料
        public static final String YZLB17 = "17";//会诊
        public static final String YZLB19 = "19";//理疗
    }

    /**
     * @Desrciption 票据使用系统参数
     * @Author 廖继广
     * @Date 2020/11/05 19:53
     */
    class FBHB{
        public static final String FS_1 = "0"; // 全院通用
        public static final String FS_2 = "1"; // 门诊通用
        public static final String FS_3 = "2"; // 正常分
    }

    /**
     * @Desrciption 费用优惠类型
     * @Author Ou·Mr
     * @Date 2020/11/11 15:01
     */
    class PREFERENTIALTYPE{
        public static final String MZ = "0";//门诊
        public static final String ZY = "1";//住院
    }

    /**
     * @Desrciption 下发科室参数
     * @Author chenjun
     * @Date 2020/11/25 15:01
     */
    class CENTERSYNCKSCODE{
        public static final String DEPT_CODE = "1";//下发信息科室固定编码
        public static final String DEPT_NAME = "信息科";//下发信息科室固定名字
    }

    /**
     * @Desrciption 结算方式
     * @Author Ou·Mr
     * @Date 2020/11/29 13:59
     */
    class JSFS{
        public static final String PTJS = "01"; //01 普通结算
        public static final String BGJS = "02"; // 02 包干结算
    }

    /**
     * @Desrciption 医保结算标志
     * @Author Ou·Mr
     * @Date 2020/11/29 15:25
     */
    class YBJSZT{
        public static final String SS = "0"; //试算
        public static final String JS = "1"; //结算
    }

    /**
     * @Desrciption     门诊/住院明细收费项目查询（按结算时间/按费用发生时间）
     * @Author chenjun
     * @Date 2020/12/14 15:25
     */
    class SFXMCX{
        public static final String QBMX = "1"; //全部明细
        public static final String FBXM = "2"; //费别项目
        public static final String XM = "3"; //费别项目
    }

    /**
     * @Desrciption 护理执行卡打印过滤参数
     * @Author fuhui
     * @Date 2020/12/25 14:01
     */
    class PRINTFILTERPARAMETER{
        public static final String DSY_YYFS ="DSY_YYFS" ; // 大输液用法
        public static final String KF = "31" ; //口服用
        public static final String PSD = "43" ; //皮试单
        public static final List<String> WHD = new ArrayList<String>() {   // 雾化单用法
            private static final long serialVersionUID = 2686821839954113133L;

            {
                add("68");
                add("69");
                add("70");
                add("71");
            }
        };
        public static final List<String> JRZSD = new ArrayList<String>() {   // 皮下肌肉执行卡
            private static final long serialVersionUID = 7355485021319211083L;

            {
                add("2");
                add("3");
                add("6");
                add("7");
                add("11");
                add("12");
                add("13");
                add("14");
                add("21");
                add("22");
            }
        };
        public static final List<String> ZLHLZXK = new ArrayList<String>() {   // 治疗/护理
            private static final long serialVersionUID = 3667846165076290159L;

            {
                add("2");
                add("6");
            }
        };
        public static final String  YSZXK = "4";  // 饮食执行卡
        public static final String  LIS = "3" ; // Lis列表

    }
    /**
     * @Desrciption 打印类型方式
     * @Author fuhui
     * @Date 2020/12/25 14:01
     */
    class PRINTTYPE{
        public static final String PSD = "0"; // 皮试单
        public static final String SYPT = "1"; // 输液瓶贴
        public static final String JMSYK = "2"; // 静脉输液卡
        public static final String JMSYYLK = "3"; // 静脉输液一览卡
        public static final String JRZSD = "4"; // 住院眼科皮下/皮内/肌肉注射单
        public static final String ZLHLZXKCW = "5"; // 治疗护理执行卡按床位
        public static final String ZLHLZXKXM = "6"; // 治疗护理执行卡按项目
        public static final String YSZXK = "7"; // 饮食执行卡
        public static final String YZZXK = "8"; // 医嘱执行卡
        public static final String YZBGD = "9"; // 医嘱变更单
        public static final String YSZXD = "10"; // 医嘱执行单
        public static final String LIS = "11"; // Lis列表
        public static final String KFK = "12"; // 口服卡
        public static final String LGSYPT = "13"; // 留观瓶底输液
        public static final String TZZCD = "14"; // 停嘱转抄单
        public static final String WHZXK = "15"; // 雾化执行卡

    }

    /**
     * @Desrciption 床位状态代码：1、正常，2、加床，3、陪床，4、虚拟
     * @Author chenjun
     * @Date 2020/12/28 11:13
     */
    class CWZY{
        public static final String ZC = "1"; // 正常
        public static final String JC = "2"; // 加床
        public static final String PC = "3"; // 陪床
        public static final String XN = "4"; // 虚拟

    }

    class DZBL{
        public static final int NUM = 20; // 派发时一次执行条数
    }

    /**
     * @Desrciption 危急值状态代码：1、正常，2、有危急值 3、已处理危急值
     * @Author 付辉
     * @Date 20201/1/22 11:13
     */
    class WJZZT{
        public static final String ZC="0"; // 正常
        public static final String YWJZ="1"; // 有危急值
        public static final String YCLWJZ="2"; // 没有危急值
    }

    /**
     * @Desrciption LIS URL
     * @Author 陈俊
     * @Date 20201/1/22 11:13
     */
    class LISURL{
        public static final String DEPTURL="http://47.92.242.185:9030/interface/uploadDept"; // 科室接口
        public static final String DOCURL="http://47.92.242.185:9030/interface/uploadDoctor"; // 医生接口
        public static final String ITEMURL="http://47.92.242.185:9030/interface/uploadFeeitem"; // 项目接口
        public static final String JYSQURL="http://47.92.242.185:9030/interface/uploadBasePatient"; // 提交检验申请单接口
        public static final String JGHCDSFURL="http://47.92.242.185:9030/interface/getLisResult"; // 检验回传 第三方主动调取
        public static final String JGHCLISURL="http://47.92.242.185:9030/interface/returnResult"; // 回传检验结果接口(LIS主动推送)
        public static final String PDFURL="http://47.92.242.185:9030/report/pdf/"; // PDF报告查询接口
        public static final String HCZTURL="http://47.92.242.185:9030/dataadapterline/lis/returnState"; // 回传状态接口
        public static final String WJZLISURL="http://47.92.242.185:9030/interface/critical"; // 回传危机值结果信息（Lis主动推送)
        public static final String WJZDSFURL="http://47.92.242.185:9030/interface/critical"; // 提交危急值处理信息接口地址
    }

    /**
     * @Desrciption LIS URL
     * @Author 陈俊
     * @Date 20201/1/22 11:13
     */
    class PACSURL{
        public static final String DEPTURL="http://47.92.242.185:9528/ris-api/hisSyn/hisDept"; // 科室接口
        public static final String DOCURL="http://47.92.242.185:9528/ris-api/hisSyn/hisDoc"; // 医生接口
        public static final String ITEMURL="http://47.92.242.185:9528/ris-api/hisSyn/hisFeeitem"; // 项目接口
        public static final String SQDURL="http://47.92.242.185:9528/ris-api/hisSyn/hisApply"; // 提交检验申请单接口
        public static final String JGHCURL="http://172.29.1.2:8062/inter/TransactionPacs"; // 检验回传 第三方主动调取
    }

    class MEDICTYPE{
        public static final String LIS_KSTB="1";
        public static final String LIS_YSTB="2";
        public static final String LIS_SFXM="3";
        public static final String LIS_SQD="4";
        public static final String LIS_ZDHQ="5";
        public static final String LIS_TS="6";
        public static final String LIS_PDF="7";
        public static final String LIS_WJ_ZDHQ="8";
        public static final String LIS_WJ_TS="9";

        public static final String PACS_KSTB="41";
        public static final String PACS_YSTB="42";
        public static final String PACS_SFXM="43";
        public static final String PACS_SQD="44";
        public static final String PACS_TS="45";
    }


    /**
     * @Description: 消息类型
     * @Author: youxianlin
     * @Date: 2021/1/21 11:10
     **/
    class MSGTYPE{
        public static final String KCBZ = "1"; //库存不足
    }

    /**
     * @Description: 消息状态
     * @Author: youxianlin
     * @Date: 2021/1/21 11:10
     **/
    class MSGSTATUS{
        public static final String WFS = "1"; //未发送
        public static final String YFS = "2"; //已发送
        public static final String YCL = "3"; //已处理
    }

    /**
     * @Desrciption 基础数据、系统管理redis缓存
     * @Author 廖俊杰
     * @Date 2021/1/21 11:13
     */
    class REDISKEY{
        public static final String BED ="BED";
        public static final String ADVICE ="ADVICE";
        public static final String ADVICEDETAIL="ADVICEDETAIL";
        public static final String DEPT ="DEPT";
        public static final String RATE ="RATE";
        public static final String DISEASE ="DISEASE";
        public static final String ITEM ="ITEM";
        public static final String DRUG ="DRUG";
        public static final String MATERIAL ="MATERIAL";
        public static final String SPECIALCALC ="SPECIALCALC";
        public static final String DAILYFIRSTCALC ="DAILYFIRSTCALC";
        public static final String PARAMETER ="PARAMETER";
        /** 缓存中码表类型数据的后缀 **/
        public static final String CODEDETAIL ="CODEDETAIL";
        /** 中心端配置信息map的key名称 **/
        public static final String CENTER_GLOBAL_CONFIG_KEY = "CENTER_GLOBAL_CONFIG";
    }

    /**
     * @Desrciption 入院情况
     * @Author 廖俊杰
     * @Date 2021/1/21 11:13
     */
    class RYQK{
        public static final String W ="1";  //危
        public static final String J ="2";  //急
        public static final String YB ="3"; //一般
    }

    /**
     * @Desrciption 入院途径
     * @Author 廖俊杰
     * @Date 2021/1/21 11:13
     */
    class RYTJ{
        public static final String ZJRY ="0";       //直接入院
        public static final String JZ ="1";         //急诊
        public static final String MZ ="2";         //门诊
        public static final String QTYLJGZR ="3";   //其他医疗机构转入
        public static final String QT ="4";         //其他
    }

    class YBQY{
        public static final String HNS ="HNS";     //湖南省
    }

    /**
     * @Description: 统一支付平台
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/18 17:30
     * @Return
     */
    class TYZFPT{
        public static final String URL = "http://172.18.21.59:8089/upay-web/web/interfaceFactory/call";  // 统一支付平台地址
    }

    /**
     * @Description: 统一支付平台-出入院诊断
     * @Param:
     * @Author: luoyong
     * @Email:
     * @Date 2021/2/18 17:30
     * @Return
     */
    class CRYZD{
        public static final String RYZD = "1"; //入院诊断
        public static final String CYZD = "2"; //出院诊断
    }

    class UNIFIED_PAY_TYPE{
        public static final String XY = "101"; // 西药
        public static final String ZCY = "102"; // 中成药
        public static final String FWXX = "201"; //服务项目
        public static final String YYCL = "301"; // 医用材料
        public static final String ZYYP = "103"; // 中药饮片
        public static final String ZZJ = "104"; // 自制剂
        public static final String MZY = "105"; // 民族药
    }

    /**
     * @Menthod YKTZT
     * @Desrciption 一卡通状态
     * @Author luoyong
     * @Date 2021-05-11 16:20
     */
    class YKTZT{
        public static final String ZC = "0"; //正常
        public static final String GS = "1"; //挂失
        public static final String DJ = "2"; //冻结
        public static final String ZX = "3"; //注销
        public static final String QRGS = "4"; //确认挂失
        public static final String ZF = "5"; //作废
    }
    /**
     * @Describe: 检验检测类型(LIS/PACS)
     * @Author: luonianxin
     * @Date: 2021/5/19 14:17
     **/
    class InspectionType{
        /** LIS检验检测 **/
        public static final String LIS = "4";
        /** pacs影像 **/
        public static final String PACS = "5";
    }

    /**
     * @Describe: 分诊方式
     * @Author: luonianxin
     * @Date: 2021/5/19 14:17
     **/
    class FZFS{
        /** 自动分诊 **/
        public static final String AUTO = "1";
        /** 手动分诊 **/
        public static final String MANUAL = "2";
    }
    /**
     * @Describe: 分诊状态(FZZT) outpt_triage_visit.triage_start_code
     * @Author: luonianxin
     * @Date: 2021/5/19 14:17
     **/
    class FZZT{
        /** 未分诊 **/
        public static final String NOT_TRIAGE = "1";
        /** 已分诊 **/
        public static final String HAVE_BEEN_DIAGNOSED = "2";
        /** 已叫号 **/
        public static final String HAVE_BEEN_CALLED = "3";
        /** 已就诊 **/
        public static final String HAVE_BEEM_VISITED = "4";
    }
    /**
     * @Describe: 药品大类(YPDL)
     * @Author: zhangguorui
     * @Date: 2021/7/16 15:19
     **/
    class YPDL {
        /** 中成药 **/
        public static final String Chinese_Patent_Medicine = "1";
        /** 草药 **/
        public static final String Herbal_Medicine = "2";
        /** 西药 **/
        public static final String Western_Medicine = "0";
    }

    /**
     *  医技申请表单据状态(SQDZT)
     * @Author: zhangguorui
     * @Date: 2021/8/10 15:19
     **/
    class SQDZT {

        /** 保存(处方提交) **/
        public static final String Prescription_Submitted = "01";
        /** 结算待发送(结算) **/
        public static final String Settlement_To_Be_Sent = "02";
        /** 结算已发送(定时器发送) **/
        public static final String Settlement_Sent = "03";
        /** 退费待接收(退费等待相应) **/
        public static final String Refund_Waiting_For_Response = "05";
        /** 退费已接收(退费已相应,如果第三方没有相关接口那么退费时默认该状态) **/
        public static final String Refund_Received = "06";
        /** 已登记(第三方接口返回登记状态,登记后不允许退费,需要接口返回) **/
        public static final String Charge_Registered = "07";
        /** 完成(接收到结果) **/
        public static final String Charge_Completed = "08";
    }

    /**
     * 模板类型（MBLX）
     */
    class MBLX {
        public static final String QY = "0"; // 全院
        public static final String KS = "1"; // 科室
        public static final String GR = "2"; // 个人
    }

    class DZDLX {
        public static final String CX_PT = "1"; // 城乡普通
        public static final String CX_DBBX = "2"; // 城乡大病保险
        public static final String CX_YWSH = "3"; // 城乡意外伤害

        public static final String ZG_PT = "4"; // 职工普通
        public static final String ZG_SYBX = "5"; // 职工生育保险
        public static final String ZG_YWSH = "6"; // 职工意外伤害

        public static final String YZS = "7"; // 一站式对账单
    }

    class SBLX {
        public static final String CZJM_ZY = "1"; // 城镇职工住院
        public static final String CXJM_ZY = "2"; // 城乡居民住院
        public static final String LX_ZY = "3"; // 离休住院
        public static final String MZ = "4"; // 全部门诊
        public static final String YZS = "5"; // 一站式
        public static final String ZG_MZ = "6"; // 职工门诊
        public static final String CX_MZ = "7"; // 城乡门诊
        public static final String LX_MZ = "8"; // 离休门诊
    }

    /**
     * 会诊申请状态（HZZT）
     */
    class HZZT {
        public static final String BC = "0"; // 保存
        public static final String SH = "1"; // 审核
        public static final String WC = "2"; // 完成
        public static final String ZF = "3"; // 作废
    }

    /**
     * 会诊类型（HZLX）
     */
    class HZLX {
        public static final String CGHZ = "0"; // 常规会诊
        public static final String JHZ = "1"; // 急会诊
    }

    /**
     * 消息主题（MSG_TYPE）
     */
    class MSG_TYPE {
        public static final String MSG_XT = "1"; // 系统消息
        public static final String MSG_YZ = "2"; // 医嘱提醒
        public static final String MSG_BL = "3"; // 病历提醒
        public static final String MSG_SF = "4"; // 收费提醒
        public static final String MSG_AC = "5"; // 安床提醒
    }

    /**
     * 消息状态（MSGZT）
     */
    class MSGZT {
        public static final String MSG_WD = "1"; // 未读
        public static final String MSG_YD = "2"; // 已读
    }

    /**
     * 病历超时基准时间
     */
    class EMRTIME {
        public static final String EMRTIME_RY = "1"; // 入院时间
        public static final String EMRTIME_CY = "2"; // 出院时间
        public static final String EMRTIME_ZK = "3"; // 转科时间
        public static final String EMRTIME_SS = "4"; // 手术时间
        public static final String EMRTIME_YZ = "5"; // 医嘱时间
    }

    /**
     * 医嘱状态
     */
    class YZ_TYPE {
        public static final String YZ_TYPE_WTJ = "1"; // 未提交
        public static final String YZ_TYPE_YTJ = "2"; // 提交
        public static final String YZ_TYPE_JS = "3"; // 拒收
        public static final String YZ_TYPE_HS= "4"; // 核收
    }

    class MSG_TOPIC{
        public static final String producerTopicKey = "msg_product_topic";
        public static final String consumerTopicKey = "msg_consumer_topic";
    }

    public static class BLLX{
        public static final String YYJL = "adminfo";
        public static final String BCJL = "coursrinfo";
        public static final String SSJL = "oprninfo";
        public static final String SWJL = "dieinfo";
        public static final String BQQJ = "rescinfo";
        public static final String CYXJ = "dscginfo";
        public static final String HLJLD = "nurseinfo";
    }

    class SETTLECODE {
        public static final String WJS = "0";// 未结算
        public static final String SS = "1";// 试算
        public static final String YJS = "2";// 已结算
    }

    /**
     * 医养申请状态
     */
    class YYSQZT {
        public static final String YSQ = "0";// 已申请，待处理
        public static final String YJZ = "1";// 已接诊
        public static final String YJJ = "2";// 已拒绝
    }


    /**
     * 医院同步状态
     */
    class YYTBZT {
        public static final String CJYY = "0";// 创建医院
        public static final String CJSJK= "1";// 创建数据库
        public static final String PPSJY = "2";// 匹配数据源
        public static final String JCSJXF = "3";// 基础数据下发
        public static final String QBWC = "4";// 全部完成
    }

    /**
     * 优惠类别
     */
    class YHLB {
        public static final String CWFL = "1";// 财务分类
        public static final String YP = "2";// 药品
        public static final String XM = "3";// 项目
    }

    /**
     * @Desrciption 职工类型（护士）
     * @Author liuliyun
     * @Date 2022/04/11 10:02
     */
    class RYZW_NURSE{
        public static final String RYZW_NURSE_01 = "201"; //主任护师
        public static final String RYZW_NURSE_02 = "202";//副主任护师
        public static final String RYZW_NURSE_03 = "203";//主管护师
        public static final String RYZW_NURSE_04 = "204";//护士
        public static final String RYZW_NURSE_05 = "205";//实习护士
    }
    /**
     * @Desrciption 结算清单打印格式
     * @Author yuelong.chen
     * @Date 2022/06/30 10:02
     */
    class JSQD_PRINT{
        public static final String MXGSZR = "1"; //明细格式逐日打印
        public static final String MXGSHZ = "2";//明细格式汇总打印
        public static final String XMGSZR = "6";//项目格式逐日打印
        public static final String XMGSHZ = "5";//项目格式汇总打印
        public static final String JGGSZR = "3";//结构格式逐日打印
        public static final String JGGSHZ = "4";//结构格式汇总打印
        public static final String XMHZMX = "7";//项目汇总-明细打印
    }

    /**
     * @Desrciption 增值服务
     * @Author yuelong.chen
     * @Date 2022/07/27 10:02
     */
    class ZZFW {
        public static final String DEFAULT = ""; // 默认值
        public static final String DIP = "1"; // 默认值
        public static final String DRG = "2"; // 默认值
        public static final String DRGDIP = "3"; // 默认值
        public static final String HQMS = "4"; // HQMS
    }
}
