package cn.hsa.insure.util;

import cn.hsa.module.insure.module.entity.InsureDictDO;
import cn.hsa.util.SnowflakeUtils;

import java.util.HashMap;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: Constant
 * @Describe(描述): 医保常量类
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/04 9:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface Constant {

    /**
     * @Desrciption 湘潭医保功能号
     * @Author Ou·Mr
     * @Date 2020/11/4 9:08
     */
    class Xiangtan{

        public static class CONFIG {
            public static final String KEY_STORE_CLIENT_PATH = "C:/keystore/client.p12";
            public static final String KEY_STORE_TRUST_PATH = "C:/keystore/client.truststore";
            public static final String KEY_STORE_TYPE_JKS = "jks";
            public static final String KEY_STORE_TYPE_P12 = "PKCS12";
            public static final String SCHEME_HTTPS = "https";
            public static final String KEY_STORE_PASSWORD = "Phealth8";
            public static final String KEY_STORE_TRUST_PASSWORD = "Phealth8";
            public static final int PORT = 9031;
        }

        //门诊
        public static class OUTPT{
            public static final String BIZH110001 = "bizh110001"; //门诊取人员信息
            public static final String BIZH110104 = "bizh110104"; //门诊费用上传并结算
            public static final String BIZH110103 = "bizh110103"; //退费时提取门诊业务信息
            public static final String BIZH110105 = "bizh110105"; //门诊指静脉比对接口
        }
        //购药
        public static class BUY{
            public static final String BIZH110001 = "bizh110001"; //购药收费时取人员信息
            public static final String BIZH110104 = "bizh110104"; //购药收费
            public static final String BIZH110103 = "bizh110103"; //退费时提取门诊业务信息

        }
        //住院
        public static class INPT{
            public static final String BIZH120001 = "bizh120001"; //入院登记时取人员信息
            public static final String BIZH120103 = "bizh120103"; //入院登记
            public static final String BIZH120102 = "bizh120102"; //入院登记后取业务信息
            public static final String BIZH120002 = "bizh120002"; //校验并保存费用信息
            public static final String BIZH120005 = "bizh120005"; //校验并保存费用信息
            public static final String BIZH120003 = "bizh120003"; //校验并计算费用信息
            public static final String BIZH120104 = "bizh120104"; //入院登记信息修改
            public static final String BIZH120109 = "bizh120109"; //取消入院登记
            public static final String BIZH120004 = "bizh120004"; //删除住院业务费用明细
            public static final String BIZH120107 = "bizh120107"; //取消出院结算
            public static final String BIZH120106 = "bizh120106"; //出院结算
            public static final String REMOTE_BIZC131201 = "Remote_BIZC131201"; //通过个人标识取人员信息
            public static final String REMOTE_BIZC131204 = "Remote_BIZC131204"; //校验保存异地就医入院信息
            public static final String REMOTE_BIZC131251 = "Remote_BIZC131251"; //提取异地就医个人业务信息
            public static final String REMOTE_BIZC131252 = "Remote_BIZC131252"; //校验保存异地住院费用明细信息
            public static final String REMOTE_BIZC131253 = "Remote_BIZC131253"; //提取已保存的费用明细信息
            public static final String REMOTE_BIZC131254 = "Remote_BIZC131254"; //通过药品编码提取需退费及已退费的药品项目信息
            public static final String REMOTE_BIZC131255 = "Remote_BIZC131255"; //异地就医住院费用计算
            public static final String REMOTE_BIZC131256 = "Remote_BIZC131256"; //异地就医住院出院结算
            public static final String REMOTE_BIZC131206 = "Remote_BIZC131206"; //取消住院登记
            public static final String REMOTE_BIZC131259 = "Remote_BIZC131259"; //取消出院结算
            public static final String REMOTE_BIZC200101 = "Remote_BIZC200101"; //提取异地住院业务结算信息
            public static final String REMOTE_BIZC131210 = "Remote_BIZC131210"; //提取异地就医人员封顶线
            public static final String REMOTE_BIZC131274 = "Remote_BIZC131274"; //删除异地就医住院业务上传明细
        }
        //病案
        public static class MRIS{
            public static final String BIZC131271 = "BIZC131271"; //提取待录入医嘱人员信息
            public static final String BIZC320004 = "BIZC320004"; //病案首页录入
            public static final String BIZC320005 = "BIZC320005"; //病案首页查询
            public static final String BIZC320006 = "BIZC320006"; //病案首页删除
            public static final String BIZC320007 = "BIZC320007"; //DRG分组结果查询
            public static final String BIZC320008 = "BIZC320008"; //DRG分组调用
            public static final String REMOTE_BIZD000001 = "Remote_BIZD000001"; //专家评审-病程记录上传接口
            public static final String REMOTE_BIZD000002 = "Remote_BIZD000002"; //专家评审-病程记录删除接口
        }
        //医嘱
        public static class ADVICE{
            public static final String BIZC131271 = "BIZC131271"; //提取待录入医嘱人员信息
            public static final String BIZC320001 = "BIZC320001"; //医嘱录入
            public static final String BIZC320002 = "BIZC320002"; //医嘱查询
            public static final String BIZC320003 = "BIZC320003"; //医嘱删除
        }
        //其他信息
        public static class RESTS{
            public static final String BIZH150001 = "bizh150001"; //身份证密码修改接口
            public static final String BIZH410005 = "bizh410005"; //基金状态查询接口
            public static final String BIZC320007 = "BIZC320007"; //基金状态查询接口
            public static final String BIZH120205 = "bizh120205"; //码表服务接口
            public static final String LOGIN = "sys0001";//登录功能号
            public static final String [] DICTVALUE ={"aka130","bka006","aae140",
                    "bka035","bac001","aaa157","aac148","aac013","aka063","aaa027","aaa157"}; // 需要更新的医保码表
        }
        public static class MATCH{
            public static final String BIZC110118 = "BIZC110118"; //项目疾病匹配接口
        }
        //湘潭字典码值
        public static class DICT{
            public static final String BCBZ_SS = "0"; //保存标志-试算
            public static final String BCBZ_JS = "1"; //保存标志-结算
            public static final String BCBZ_YDJS = "3";//保存标志-异地结算
            public static final String JSBZ_SF = "0"; //结算标志-收费
            public static final String JSBZ_TF = "1"; //结算标志-退费
            public static final String YWCJ = "1"; //业务场景阶段 (1：业务开始有且仅能为1)
        }


        //默认值
        public static class DEFAULTVAL{
            public static final String SY_AKA130 = "52";//生育业务类型
            public static final String SY_BKA006 = "521";//生育待遇类型
            public static final String DBZ_BKA006 = "121";//单病种待遇类型
            public static final HashMap<String,String> BKA066 = new HashMap<String,String>(){{
                put("1","A");//治愈
                put("2","B");//好转
                put("3","D");//未愈
                put("4","E");//死亡
                put("9","F");//其他
            }};
       }

        // 湘潭医保字典生成Code、下載码表生成有规则Code标识
        public static final HashMap<String,Object> DictCode = new HashMap<String,Object>(){{
            //业务类型
            put("aka130",new HashMap<String,String>(){{
                put("10","YWLX_GY");//购药
                put("11","YWLX_MZ");//普通门诊
                put("12","YWLX_ZY");//普通住院
                put("13","YWLX_MZ");//门诊特殊病
                put("14","YWLX_ZY");//家庭病床
                put("16","YWLX_MZ");//特治特检
                put("17","YWLX_ZY");//急诊留观
                put("18","YWLX_MZ");//大病特药
                put("51","YWLX_MZ");//生育门诊
                put("52","YWLX_ZY");//生育住院
                put("42","YWLX_ZY");//工伤住院
            }});
            //待遇类型
            put("bka006",new HashMap<String,String>(){{
                put("100","DYLX_GY");//药店购药
                put("101","DYLX_GY");//外配处方购药
                put("110","DYLX_MZ");//普通门诊
                put("111","DYLX_MZ");//意外伤害门诊
                put("112","DYLX_MZ");//门诊急诊
                put("113","DYLX_MZ");//生育门诊
                put("114","DYLX_MZ");//实质性治疗(门诊)
                put("115","DYLX_MZ");//门诊理疗
                put("116","DYLX_MZ");//日间手术
                put("120","DYLX_ZY");//普通住院
                put("121","DYLX_ZY");//单病种包干
                put("122","DYLX_ZY");//意外伤害住院
                put("123","DYLX_ZY");//康复治疗
                put("128","DYLX_ZY");//居民生育平产
                put("129","DYLX_ZY");//居民生育剖宫产
                put("125","DYLX_ZY");//强制精神病
                put("12S","DYLX_ZY");//失能病人住院
                put("12X","DYLX_ZY");//实质性治疗(住院)
                put("131","DYLX_MZ");//门诊特殊病
                put("191","DYLX_MZ");//门诊慢性病
                put("140","DYLX_ZY");//家庭病床
                put("170","DYLX_ZY");//急诊留观
                put("180","DYLX_MZ");//特药业务
                put("511","DYLX_MZ");//生育门诊
                put("521","DYLX_ZY");//生育住院
            }});
            //自定义码表信息
            put("dictCodeArr",new InsureDictDO[]{
                    new InsureDictDO(SnowflakeUtils.getId(),null,null,"JSLX","电脑号","aac001","检索类型",null,null,null,null,null,null,null,null,null),
                    new InsureDictDO(SnowflakeUtils.getId(),null,null,"JSLX","社会保障号码","aac002","检索类型",null,null,null,null,null,null,null,null,null),
                    new InsureDictDO(SnowflakeUtils.getId(),null,null,"JSLX","社保卡号","bka100","检索类型",null,null,null,null,null,null,null,null,null),
                    new InsureDictDO(SnowflakeUtils.getId(),null,null,"JSLX","电子社保卡二维码","qrcode","检索类型",null,null,null,null,null,null,null,null,null),
                    new InsureDictDO(SnowflakeUtils.getId(),null,null,"YWLX_ZY","工伤住院","42","工伤住院",null,null,null,null,null,null,null,null,null),
            });
        }};
    }

    /**
     * @Desrciption 医保服务功能编码
     * @Author Ou·Mr
     * @Date 2020/11/17 20:52
     */
    class FUNCTION{
        public static final String BIZC110118 = "110118"; //项目疾病匹配接口
        public static final String BIZH120002 = "120002"; //住院校验保存费用信息（传输方式一）
        public static final String BIZH120005 = "120005"; //校验并保存费用信息（传输方式二）
        public static final String REMOTE_BIZC131252 = "131252"; //校验保存异地住院费用明细信息
        public static final String BZIH120205="120205";          // 码表服务接口
        public static final String BIZH110001 = "110001"; //获取人员信息
        public static final String FC_2002 = "2002";//门诊医保试算
        public static final String FC_2003 = "2003";//门诊医保结算
        public static final String FC_3003 = "3003";//住院校验并计算费用信息
        public static final String FC_3011 = "3011";//住院结算
        public static final String FC_3020 = "3020";//异地住院结算
        public static final String FC_3010 = "3010";//删除住院已传输费用


        public static final String BIZC131271 = "131271"; //提取待录入医嘱人员信息
        public static final String BIZC320004 = "320004"; //病案信息上传
        public static final String BIZC320005 = "320005"; //病案首页查询
        public static final String BIZC320006 = "320006"; //删除病案首页

        public static final String BIZH110104 = "110104"; // 门诊费用上传并结算
        public static final String BIZH110103 = "110103"; // 退费时提取门诊业务信息
        public static final String BIZH120003 = "120003"; // 校验并计算费用信息
        public static final String BIZH120106 = "120106"; // 出院结算
        public static final String REMOTE_BIZC200101 = "200101"; // 提取异地住院业务结算信息

        public static final String BIZH120103 = "120103"; // 入院登记
        public static final String BIZH120104 = "120104"; // 修改入院登记
        public static final String BIZH120109 = "120109"; // 取消入院登记
        public static final String REMOTE_BIZC131204 = "131204"; // 异地医保入院登记入院登记
        public static final String REMOTE_BIZC131206 = "131206"; // 取消异地医保入院登记
        public static final String REMOTE_BIZC131259 = "131259"; //医保取消异地出院结算
        public static final String BIZH120107 = "120107"; // 医保取消出院结算
        public static final String BIZH120102 = "120102"; // 获取住院业务信息
        public static final String REMOTE_BIZC131251 = "131251"; // 获取异地住院业务信息
        public static final String REMOTE_BIZC131274 = "131274"; // 删除异地住院业务信息
        public static final String BIZH120001 = "120001"; // 住院获取个人信息
        public static final String REMOTE_BIZC131201 = "131201"; // 住院获取异地个人信息
        public static final String REMOTE_BIZC131255 = "131255"; // 异地就医费用试算
        public static final String BIZH120004 = "120004"; // 删除住院费用明细
        public static final String Remote_BIZC131252 = "131252"; // 异地就医费用上传
        public static final String BIZH120205 = "120205"; // 医保码表请求服务接口
        public static final String BIZC320007 = "320007";  // DRG 分组调用
        public static final String BIZC320003 = "320003";  // DRG 分组调用
        public static final String BIZC320001 = "320001";  // 医嘱录入上传
        public static final String BIZC300001 = "300001";  // 工伤医嘱录入上传

        /*电子凭证*/
        public static final String FC_EMD_11001 = "EMD11001";//门诊电子凭证获取个人信息
        public static final String FC_EMD_11002 = "EMD11002";//门诊电子凭证患者费用上传
        public static final String FC_EMD_11003 = "EMD11003";//门诊电子凭证撤销费用明细上传
        public static final String FC_EMD_11004 = "EMD11004";//门诊电子凭证费用退费
        public static final String FC_EMD_11005 = "EMD11005";//门诊电子凭证获取支付结果

        public static final String FC_EMD_12001 = "EMD12001";//住院电子凭证获取个人信息
        public static final String FC_EMD_12002 = "EMD12002";//住院电子凭证患者费用上传
        public static final String FC_EMD_12003 = "EMD12003";//住院电子凭证撤销费用明细上传
        public static final String FC_EMD_12004 = "EMD12004";//住院电子凭证费用退费
        public static final String FC_EMD_12005 = "EMD12005";//住院电子凭证获取支付结果
    }

    class ChangSha{
        //项目匹配
        public static class DOWNLOADITEM{
            public static final String YPML ="01";// 药品目录
            public static final String ZLXMXX ="02";//诊疗项目信息
            public static final String YLFWSSXX ="03";// 医疗服务设施信息
            public static final String FYLBXX ="04";// 费用类别信息
            public static final String BZXX ="05";// 病种信息
            public static final String XMDZXX ="06";// 项目对照信息
            public static final String BZFXXX ="07";// 病种分型信息
        }

        //长沙市医保功能号
        //门诊
        public static class OUTPT{
            public static final String CS_1805 ="1805"; // 门诊/住院个人结算信息查询
            public static final String CS_1807="1807"; //  读取人员信息
            public static final String CS_2710 ="2710"; //  两病门诊/门特/门诊别收费预结算
            public static final String CS_2720="2720"; // 两病门诊/门特/门诊 收费结算
            public static final String CS_2320="2320"; //撤销费用
            public static final String CS_2430="2430"; // 结算取消
            public static final String CS_2240="2240"; // 取消登记
        }
        //住院
        public static class INPT{
            public static final String CS_1805 ="1805"; // 门诊/住院个人结算信息查询
            public static final String CS_1807="1807"; //  读取人员信息
            public static final String CS_2420 ="2420"; //住院预结算
            public static final String CS_2410 ="2410"; // 住院结算
            public static final String CS_2210="2210"; // 住院登记
            public static final String CS_2230="2230"; // 住院登记修改
        }
        //其他功能号
        public static class RESTS{
            public static final String CS_3460 ="3460"; // 医院项目对照上报撤销
            public static final String CS_3110 ="3100"; // 医院审批信息上报
            public static final String CS_1300="1300"; //  批量数据查询下载
            public static final String CS_1806="1806"; // 医院审批信息查询
            public static final String CS_2310="2310"; // 处方明细上报
            public static final String CS_1907="1907"; // 根据二维码取电子社保卡信息
            public static final String CS_1863 ="1863"; // 查询两病药品比例信息
            public static final String CS_3451="3451"; // 医院目录对照上报
        }

        public static class DefaultValue{
            /**
             * 审批类别
             */
            public static final HashMap<String,String> AKC170 = new HashMap<String,String>(){{
                put("17","特殊门诊审批");
                put("20","城居两病定点审批");
            }};
            /**
             * 病种分型
             */
            public static final HashMap<String,String> AKA129 = new HashMap<String,String>(){{
                put("0022","专科手术");
                put("005","结核");
                put("001","普通");
                put("002","肿瘤病");
                put("003","单列");
                put("004","肝炎");
                put("0021","椎间盘");
                put("0014","普通(14)");
                put("000","非病种分型");
                put("0016","肿瘤病(14)");
                put("0017","普通(14厅级)");
                put("006","三万");
                put("007","三万五");
                put("008","两万");
                put("009","普通（厅级公务员）");
                put("0010","肿瘤病（厅级公务员）");
                put("99","所有病种分型");
                put("0018","肿瘤病(14厅级)");
                put("0019","三万五(14)");
                put("0020","单列(14)");
                put("0023","尿毒症");
                put("0024","精神分裂症（非长住）");
            }};
            /**
             * 医疗类别
             */
            public static final HashMap<String,String> aka130 = new HashMap<String,String>(){{
                put("11","普通门诊");
                put("14","门诊特病");
                put("18","城职付费单病种");
                put("19","城居付费单病种");
                put("20","单病种门诊");
                put("21","普通住院");
                put("23","单病种住院");
                put("31","灵活就业生育");
                put("51","生育门诊");
                put("52","节育门诊");
                put("53","生育住院");
                put("54","节育住院");
                put("60","城居急诊抢救");
                put("61","城居普通住院");
                put("62","城居单病种住院）");
                put("63","城居门诊特病");
                put("64","所有病种分型");
                put("66","青少年意外伤害门诊");
                put("68","城居大学生门诊统筹");
                put("69","城居门诊统筹");
                put("72","城居生育门诊");
                put("73","城居生育住院");
                put("75","意外伤害住院");
                put("77","城居两病门诊");
                put("17","职工意外伤害住院");
            }};
            /**
             * 人员类别
             */
            public static final HashMap<String,String> akc021 = new HashMap<String,String>(){{
                put("11","在职待遇");
                put("21","退休待遇");
                put("31","离休待遇");
                put("33","伤残军人待遇");
                put("41","非从业居民");
                put("42","老年");
                put("43","未成年");
                put("44","婴幼儿");
                put("45","中小学生");
                put("46","大学生");
                put("47","新农合");
                put("48","扶贫对象");
            }};
        }
    }

    class hainan{
        //门诊
        public static class OUTPT{
            public static final String ecQuery="ec.query"; // 电子凭证解码
            public static final String hosQueryInsu="hos.query.insu"; // 参保人信息查询
            public static final String hosUploadFee ="hos.upload.fee"; // 费用上传
            public static final String hosRevokeOrder="hos.revoke.order"; //  费用撤销
            public static final String hosRefundSetl ="hos.refund.setl"; //  医保退费
        }

        public static class FUNCTION{
            public static final String ecQuery = "ec.query";//电子凭证二维码解码
            public static final String iotGainEcQrcode = "iot.gain.ec.qrcode";//获取身份信息/刷卡核身接口
            public static final String hosQueryInsu = "hos.query.insu";//参保人员信息查询
            public static final String hosUploadFee = "hos.upload.fee";//费用明细上传
            public static final String hosRevokeOrder = "hos.revoke.order";//撤销费用明细上传
            public static final String hosRefundSetl = "hos.refund.setl";//医保退费
        }

        //用码业务类型
        public static class BusinessType{
            //医院
            public static class Hospital{
                public static final String GH = "01101"; // 挂号
                public static final String ZYJD = "01102"; // 住院建档
                public static final String RYDJ = "01103"; // 入院登记
                public static final String JNYJJ = "01104"; //  缴纳预缴金
                public static final String WZ = "01201"; //  问诊
                public static final String YYJC = "01202"; // 预约检查
                public static final String JC = "01203"; // 检查
                public static final String ZL = "01204"; // 治疗
                public static final String JS = "01301"; //  结算
                public static final String QY = "01302"; //  取药
                public static final String QBG = "01303"; // 取报告
                public static final String DYPJHQD = "01304"; // 打印票据和清单
                public static final String BLCLFY = "01305"; // 病历材料复印
            }
            //药店
            public static class PharmacyStore{
                public static final String YDGY = "02121"; // 药店购药
                public static final String XZWGCF = "02122"; // 下载外购处方
            }
            //医疗类APP
            public static class APP{
                public static final String SSRZ = "03131"; // 医疗类 APP 线上身份认证
                public static final String XSJS = "01102"; // 医疗类 APP 线上结算
            }
        }
        // 门诊诊断类别
        public static class medType{
            public static final String XYZDJ = "1"; // 西医诊断
            public static final String ZYZBZD = "2"; // 中医主病诊断
            public static final String ZYZZZD = "3"; // 中医主证诊断
        }
        //海南码表翻译
        public static class DICT{
            //收费项目种类
            public static final HashMap<String,String> chrgitmType = new HashMap<String,String>(){{
                put("1","1");//药品
                put("3","2");//诊疗项目
                put("2","3");//服务设施
            }};
            //收费类别
            public static final HashMap<String,String> chrgType = new HashMap<String,String>(){{
                put("L01","11");//西药
                put("M01","12");//中成药
                put("N01","13");//中草药
                put("D01","21");//检查费
                put("D01","22");//特殊检查费
                put("E03","23");//输氧费
                put("G01","24");//手术费
                put("H01","25");//化验费
                put("J02","26");//输血费
                put("C01","27");//诊查费
                put("E01","31");//治疗费
                put("E01","32");//特殊治疗费
                put("F01","33");//护理费
                put("B01","34");//床位费
                put("I01","35");//取暖费
                put("I01","41");//医疗服务费
                put("G02","42");//麻醉费
                put("J03","43");//安装器官
                put("D01","44");//产前检查费
                put("I01","45");//新生儿费
                put("E04","46");//理疗费
                put("J06","47");//高质耗材
                put("D02","48");//放射费
                put("I01","49");//核医费
                put("E02","50");//注射费
                put("A02","51");//病理费
                put("E07","52");//抢救费
                put("I01","91");//其他费用
                put("A01","92");//挂号费
                put("J01","94");//材料费
                put("C01","95");//一般诊疗费
                put("E15","53");//中医诊疗
            }};
        }

    }


    /**
     * @Menthod 湖南省医保
     * @Author Ou·Mr
     * @Date 2021/1/29 9:52
     */
    class HuNanSheng{
        //门诊
        public static class OUTPT{
            public static final String BIZC131101 = "BIZC131101";//普通门诊提取人员信息
            public static final String BIZC131110 = "BIZC131110";//退改费取个人信息、业务信息
            public static final String BIZC131111 = "BIZC131111";//退改费取费用信息
            public static final String BIZC131104 = "BIZC131104";//校验计算并保存录入的费用明细信息（含改费）
            public static final String BIZC200102 = "BIZC200102";//提取门诊业务结算信息
        }
        //购药
        public static class BUY{
            public static final String BIZC131101 = "BIZC131101";//药店购药提取人员信息
            public static final String BIZC131110 = "BIZC131110";//退改费取个人信息、业务信息
            public static final String BIZC131111 = "BIZC131111";//退改费取费用信息
            public static final String BIZC131104 = "BIZC131104";//校验计算并保存录入的费用明细信息（含改费）
            public static final String BIZC200102 = "BIZC200102";//提取购药业务结算信息

        }
        //住院
        public static class INPT{
            public static final String BIZC131201 = "BIZC131201";//住院入院登记：通过个人标识取人员信息
            public static final String BIZC131204 = "BIZC131204";//住院入院登记：校验保存普通住院入院信息
            public static final String BIZC131271 = "BIZC131271";//提取在院业务信息：提取个人业务信息
            public static final String BIZC131205 = "BIZC131205";//住院信息修改：校验保存住院信息修改
            public static final String BIZC200024 = "BIZC200024";//住院信息修改：校验保存住院诊断信息
            public static final String BIZC110101 = "BIZC110101";//住院费用录入：校验医院项目或药品是否匹配
            public static final String BIZC131272 = "BIZC131272";//住院费用录入：校验保存住院费用明细信息
            public static final String BIZC131274 = "BIZC131274";//住院费用删除：删除已上传的费用明细信息
            public static final String BIZC131253 = "BIZC131253";//住院费用录入：提取已保存的费用明细信息
            public static final String BIZC131254 = "BIZC131254";//住院费用录入：通过药品编码提取需退费及已退费的药品项目的信息
            public static final String BIZC131255 = "BIZC131255";//住院费用录入：住院费用计算
            public static final String BIZC131256 = "BIZC131256";//住院出院结算：住院出院结算
            public static final String BIZC131275 = "BIZC131275";//住院出院结算：提取生育特殊情况申请审核信息
            public static final String BIZC200101 = "BIZC200101";//提取普通住院业务结算信息
            public static final String BIZC200111 = "BIZC200111";//提取工伤住院业务结算信息
            public static final String BIZC131206 = "BIZC131206";//取消住院登记
            public static final String BIZC131259 = "BIZC131259";//取消出院结算
            public static final String REMOTE_BIZC131201 = "Remote_BIZC131201";//异地就医住院入院登记：通过个人标识取人员信息
            public static final String REMOTE_BIZC131204 = "Remote_BIZC131204";//异地就医住院入院登记：校验保存异地就医入院信息
            public static final String REMOTE_BIZC131251 = "Remote_BIZC131251";//提取异地就医在院业务信息：提取异地就医个人业务信息
            public static final String REMOTE_BIZC131252 = "Remote_BIZC131252";//异地就医住院费用录入：校验保存住院费用明细信息
            public static final String REMOTE_BIZC131253 = "Remote_BIZC131253";//异地就医住院费用录入：提取已保存的费用明细信息
            public static final String REMOTE_BIZC131254 = "Remote_BIZC131254";//异地就医住院费用录入：通过药品编码提取需退费及已退费的药品项目的信息
            public static final String REMOTE_BIZC131255 = "Remote_BIZC131255";//异地就医住院费用录入：异地就医住院费用计算
            public static final String REMOTE_BIZC131256 = "Remote_BIZC131256";//异地就医住院出院结算：异地就医住院出院结算
            public static final String REMOTE_BIZC131206 = "Remote_BIZC131206";//异地就医取消住院登记：取消住院登记
            public static final String REMOTE_BIZC131259 = "Remote_BIZC131259";//异地就医取消出院结算：取消出院结算
            public static final String REMOTE_BIZC200101 = "Remote_BIZC200101";//提取异地住院业务结算信息
            public static final String REMOTE_BIZC131210 = "Remote_BIZC131210";//提取异地就医人员封顶线
            public static final String Remote_BIZC200017 = "Remote_BIZC200017";//提取异地疾病信息
            public static final String REMOTE_BIZC131274 = "Remote_BIZC131274";//住院费用删除：删除已上传的费用明细信息
        }
        //病案
        public static class MRIS{
            public static final String BIZC131271 = "BIZC131271";//提取待录入病案首页人员信息
            public static final String BIZC320004 = "BIZC320004";//病案首页录入
            public static final String BIZC320005 = "BIZC320005";//病案首页查询
            public static final String BIZC320006 = "BIZC320006";//病案首页删除
        }
        //医嘱
        public static class ADVICE{
            public static final String BIZC131271 = "BIZC131271";//提取待录入医嘱人员信息
            public static final String BIZC300001 = "BIZC300001";//医嘱录入、医嘱删除（删除单条医嘱）
            public static final String BIZC300002 = "BIZC300002";//医嘱查询
            public static final String BIZC320003 = "BIZC320003";//医嘱删除（删除单条医嘱）
        }
        //其他信息
        public static class RESTS{
            public static final String LOGIN = "0";//登录
            public static final String BIZC320007 = "BIZC320007";//医审申诉反馈：申诉反馈
            public static final String BIZC200900 = "BIZC200900";//读IC卡
            public static final String BIZC200301 = "BIZC200301";//提取业务或费用信息：提取大类费用信息、提取明细费用信息
            public static final String BIZC200017 = "BIZC200017";//提取业务或费用信息：获取中心疾病目录信息
        }
        public static class MATCH{
            public static final String BIZC110118 = "BIZC110118"; //项目药品匹配接口
            public static final String BIZC110125 = "BIZC110125"; //疾病接口

        }
    }


    /**
     * 统一支付平台
     */
    class UnifiedPay {

        /**
         * 新医保磁卡类型
         */
        public static class CKLX{
            public static final String DZPZ = "01"; // 电子凭证
            public static final String SFZ = "02"; // 身份证号码
            public static final String YDSBK = "03"; // 异地社保卡
            public static final String AM = "04"; // 澳门证件类型
            public static final String XG = "05"; // 香港证件类型
            public static final String BDSBK = "06"; // 本地社保卡
            public static final String GAT = "17"; // 港澳台

        }

        public static class YWLX {
            public static final String PTMZ = "11"; // 普通门诊
            public static final String GH = "12"; // 门诊挂号
            public static final String JZ = "13"; // 急诊
            public static final String MZMXB = "14"; // 门诊慢性病(特门)
            public static final String MZTC = "15"; // 门诊统筹
            public static final String MZTSB = "16"; // 门诊特殊病
            public static final String PTZY = "21"; // 普通住院
            public static final String PTZYWZM = "2101"; // 普通住院（未转码）
            public static final String MZLB = "9901"; // 门诊两病
            public static final String DBZZY = "2102"; // 单病种住院
            public static final String DBTY = "9904"; // 大病特药
            public static final String SYPCJM = "2106"; // 生育平产(居民)
            public static final String SYPGCJM = "2107"; // 生育剖宫产(居民)
            public static final String YWSHMZ = "9903"; // 意外伤害门诊
            public static final String ZLJB = "9912"; // 重流疾病
            public static final String SWJXZL = "9913"; // 市外继续治疗
            public static final String YQJJ = "9902"; // 院前急救
            public static final String MZQJ = "9914"; // 门诊抢救
            public static final String JSBZY = "9903"; // 精神病住院
            public static final String ZLMZ = "9917"; // 重流门诊
            public static final String GWYTJ = "9918"; // 公务员体检
            public static final String JBYLSYMZ = "9918"; // 基本医疗生育门诊
            public static final String ZZRSMZ = "9921"; // 终止妊娠门诊
            public static final String ZZRSZY = "9931"; // 终止妊娠住院
            public static final String DXSWSMZ = "9920"; // 大学生外伤门诊
            public static final String WSZY = "22"; // 外伤住院
            public static final String ZWZZZY = "23"; // 转外诊治住院
            public static final String SYMZ = "51"; // 生育门诊
            public static final String SYZY = "52"; // 生育住院
            public static final String JBYLSYZY = "54"; // 基本医疗生育门诊
            public static final String QTSYMZ = "9932"; // 其他生育门诊
            public static final String GSMZ = "41"; // 工伤门诊
        }



        /**
         * 新医保电子凭证扫码读卡政策
         */
        public static class QrCodePolicy{
            public static final String HN = "1"; // 湖南
            public static final String GD = "2"; // 广东
        }

        public static class HuNan{
            public static final String UP_5261 = "5261"; // 人员慢特病限额查询
            public static final String icdLimitCalc_1 = "基金支付限额";
            public static final String icdLimitCalc_2 = "符合范围限额";
            public static final String icdLimitCalc_3 = "医疗费总额限额";
            public static final String icdLimitCalc_4 = "基金支付定额";
            // 1 基金支付限额 ,2 符合范围限额;3 医疗费总额限额4 基金支付定额
        }



        /**
         * 新医保基金支付总额计算方式 - 各个省份计算方式不同
         */
        public static class calculation{
            public static final String HN = "1"; //  基金支付总额不包括个人账户
            public static final String GD = "2"; //  基金支付总额包括个人账户
        }

        public static class DOWNLOADTYPE {
            public static final String XY = "101"; // 西药
            public static final String ZCY = "102"; // 中成药
            public static final String FWXX = "201"; //服务项目
            public static final String YYCL = "301"; // 医用材料
            public static final String ZYYP = "103"; // 中药饮片
            public static final String ZZJ = "104"; // 自制剂
            public static final String MZY = "105"; // 民族药
        }

        /**
         * 项目类别
         */
        public static final HashMap<String,String> LISTTYPE= new HashMap<String,String>(){{
            put("101","西药");
            put("102","中成药");
            put("103","中药饮片");
            put("104","自制剂");
            put("105","民族药");
            put("201","服务项目");
            put("301","医用材料");
        }};

        /**
         * 疾病类别
         */
        public static final HashMap<String,String> JBLIST= new HashMap<String,String>(){{
            put("1307","疾病与诊断目录下载");
            put("1308","手术操作目录下载");
            put("1309","门诊慢特病种目录下载");
            put("1310","按病种付费病种目录下载");
            put("1311","日间手术治疗病种目录下载");

            put("1313","肿瘤形态学目录下载");
            put("1314","中医疾病目录下载");
            put("1315","中医证候目录下载");
        }};

        /**
         * 自动匹配方式
         */
        public static class MATCHCODE {
            public static final String GJBM = "1"; //国家编码
            public static final String MC = "2";  // 名称
            public static final String BWM ="4"; // 药品本位码
        }

        public static class OUTPT {
            public static final String UP_2201 = "2201"; // 门诊挂号
            public static final String UP_2202 = "2202"; // 门诊挂号撤销
            public static final String UP_2203 = "2203"; //门诊就诊信息上传
            public static final String UP_2204 = "2204"; // 门诊费用明细信息上传
            public static final String UP_2205 = "2205"; // 门诊费用明细信息撤销
            public static final String UP_2206 = "2206"; // 门诊预结算
            public static final String UP_2207 = "2207"; // 门诊结算
            public static final String UP_2208 = "2208"; // 门诊结算撤销
            public static final String UP_2601 = "2601"; // 交易冲正
            public static final String UP_3201 = "3201"; // 医药机构费用结算对总账
            public static final String UP_3202 = "3202"; // 医药机构费用结算对明细账
            public static final String UP_5261 = "5261"; // 结算单查询
            public static final String UP_5262 = "5262"; // 结算信息查询
            public static final String UP_5265 = "5265"; // 结算单下载
            public static final String UP_5260 = "5260"; // 广州结算单下载
            public static final String UP_5269 = "5269"; // 异地门诊结算单下载
            public static final String UP_5270 = "5270"; // 异地住院结算单下载
            public static final String UP_4301 = "4301"; // 门急诊诊疗记录
            public static final String UP_4302 = "4302"; // 急诊留观手术及抢救信息
            public static final String UP_3203 = "3203"; // 清算申请
            public static final String UP_3204 = "3204"; // 清算撤销
        }

        public static class INPT {
            public static final String UP_2301 = "2301"; // 住院费用明细上传
            public static final String UP_2302 = "2302"; // 住院费用明细撤销
            public static final String UP_2303 = "2303";// 住院预结算
            public static final String UP_2304 = "2304"; // 住院结算
            public static final String UP_2305 = "2305"; // 住院结算撤销

            public static final String UP_4401 = "4401"; // 住院病案首页信息
            public static final String UP_4402 = "4402"; // 住院医嘱记录

            public static final String UP_4601 = "4601"; // 输血信息上传
            public static final String UP_4602 = "4602"; // 护理操作生命体征测量记录
        }

        public static class REGISTER {
            public static final String UP_1101= "1101"; // 人员信息获取
            public static final String UP_3262= "3262"; // 异地清分结果确认
            public static final String UP_3261= "3261"; // 异地清分结果确认
            public static final String UP_3260= "3260"; // 5.1.1.1提取异地清分明细
            public static final String UP_1201 = "1201"; // 获取医疗机构信息
            public static final String UP_1901 = "1901"; // 获取码表字典
            public static final String UP_2001 = "2001"; // 获取人员待遇信息
            public static final String UP_1304 = "1304"; // 民族药品目录查询
            public static final String UP_1312 = "1312"; // 医保目录信息查询
            public static final String UP_1318 = "1318"; // 医保目录限价信息查询
            public static final String UP_1316 = "1316"; // 医疗目录与医保目录匹配信息查询
            public static final String UP_1317 = "1317"; // 医药机构目录匹配信息查询
            public static final String UP_1319 = "1319"; // 医保目录先自付比例信息查询

            public static final String UP_4206 = "4206"; // 自费病人门诊就医信息删除


            public static final String UP_5101 = "5101"; // 科室信息查询
            public static final String UP_5401 = "5401"; // 项目互认信息查询
            public static final String UP_5102 = "5102"; // 医执人员信息查询
            public static final String UP_5261 = "5261"; // 结算查询
            public static final String UP_2401 = "2401"; // 入院办理
            public static final String UP_2402 = "2402"; // 出院登记
            public static final String UP_2403 = "2403";// 住院信息变更
            public static final String UP_2404 = "2404"; // 入院办理撤销
            public static final String UP_2405 = "2405"; // 出院办理撤销
            public static final String UP_2406 = "2406"; // 就医特殊属性上传
            public static final String UP_2407 = "2407"; // 就医特殊属性查询
            public static final String UP_2601 = "2601"; // 冲正交易
            public static final String UP_9162 = "9162"; // 对象
            public static final String UP_3301 = "3301"; // 项目对照上传
            public static final String UP_3302 = "3302"; // 项目对照撤销
            public static final String UP_2501 = "2501"; // 转院备案
            public static final String UP_2502 = "2502"; // 转院备案撤销
            public static final String UP_2503 = "2503"; // 人员慢特病备案
            public static final String UP_2504 = "2504"; // 人员慢特病备案撤销
            public static final String UP_2505 = "2505"; // 人员定点备案
            public static final String UP_2506 = "2506"; // 人员定点病备案撤销
            public static final String UP_2507 = "2507"; // 人员意外伤害备案
            public static final String UP_2699 = "2699"; // 生育备案申请
            public static final String UP_5301 = "5301"; // 人员慢特病备案查询
            public static final String UP_5302 = "5302"; // 人员定点备案查询
            public static final String UP_5303 = "5303"; // 在院信息查询
            public static final String UP_5304 = "5304"; // 转院信息查询
            public static final String UP_5402 = "5402"; // 报告明细信息查询
            public static final String UP_5364 = "5364"; // 门诊两病备案查询

            public static final String UP_5264= "5264"; // 告知单查询

            public static final String UP_3401 = "3401"; // 科室信息上传
            public static final String UP_3401A = "3401A"; // 批量科室信息上传
            public static final String UP_3402 = "3402"; // 科室信息变更
            public static final String UP_3403 = "3403"; //科室信息撤销


            public static final String UP_5201 = "5201"; // 就诊信息查询
            public static final String UP_5202 = "5202"; // 诊断信息查询
            public static final String UP_2579 = "2579"; //门诊选点改点
            public static final String UP_5203 = "5203"; // 结算信息查询
            public static final String UP_5204 = "5204"; // 费用明细查询
            public static final String UP_5205 = "5205"; // 人员慢特病用药记录查询
            public static final String UP_5206 = "5206"; // 人员累计信息查询
            public static final String UP_2562 = "2562"; // 门诊两病备案
            public static final String UP_2563 = "2563"; // 门诊两病备案撤销

            public static final String UP_100001= "100001"; // 政策项查询

            public static final String UP_7101 = "7101"; // 电子处方上传
            public static final String UP_4701 = "4701"; // 电子病历上传
            public static final String UP_4101 = "4101"; // 医疗保障基金结算清单
            public static final String UP_4101A = "4101A"; // 医疗保障基金结算清单新
            public static final String UP_4102 = "4102"; // 医疗保障基金结算清单状态修改

            public static final String UP_4501= "4501"; // 临床检查报告记录
            public static final String UP_4502= "4502"; // 临床检验报告记录
            public static final String UP_4503= "4503"; // 细菌培养报告记录
            public static final String UP_4504= "4504"; // 药敏记录报告记录
            public static final String UP_4505= "4505"; // 病理检查报告记录

            public static final String UP_5369 = "5369"; // 6.3.1.3个人账户扣减
            public static final String UP_5368 = "5368"; // 账户余额信息查询
            public static final String UP_4201 = "4201"; // 自费病人费用信息上传
            public static final String UP_4201A = "4201A"; // 自费病人住院费用明细信息上传
            public static final String UP_4204 = "4204"; // 自费病人费用信息上传
            public static final String UP_4202 = "4202"; // 自费病人住院就诊和诊断信息上传
            public static final String UP_4205 = "4205"; // 自费病人门诊就医信息上传
            public static final String UP_4203 = "4203"; // 自费病人就诊以及费用明细上传完成
            public static final String UP_4207 = "4207"; // 自费病人就医费用明细查询
            public static final String UP_4208 = "4208"; // 自费病人就医就诊信息查询
            public static final String UP_4209 = "4209"; // 自费病人就医诊断信息查询
            public static final String UP_4261 = "4261"; // 自费病人明细信息上传
            public static final String UP_4262 = "4262"; // 自费病人明细信息对账
            public static final String UP_4263 = "4263"; // 自费病人零报金额不符查询
            public static final String UP_6201 = "6201"; // 线上费用明细上传
            public static final String UP_6301 = "6301"; // 医保订单结算结果查询
            public static final String UP_6401 = "6401"; // 费用明细上传撤销
            public static final String UP_6203 = "6203"; // 医保退费
        }

        public static class DOWNLOAD {
            public static final String UP_1301 = "1301"; // 西药中成药目录下载
            public static final String UP_1302 = "1302"; // 中药饮片目录下载
            public static final String UP_1303 = "1303"; // 医疗机构制剂目录下载
            public static final String UP_1304 = "1304"; // 民族药品目录查询
            public static final String UP_1305 = "1305"; // 医疗服务项目目录下载
            public static final String UP_1306 = "1306"; // 医用耗材目录下载
            public static final String UP_1307 = "1307"; // 疾病与诊断目录下载

            public static final String UP_1308 = "1308"; // 手术操作目录下载
            public static final String UP_1309 = "1309"; // 按病种付费病种目录下载
            public static final String UP_1310 = "1310"; // 按病种付费病种目录下载
            public static final String UP_1311 = "1311"; // 日间手术治疗病种目录下载
            public static final String UP_1313 = "1313"; // 肿瘤形态学目录下载
            public static final String UP_1314 = "1314"; // 中医证候目录下载
            public static final String UP_1315 = "1315"; // 中医证候目录下载
        }

        public static class CARD {
            public static final String UP_1602 = "1602"; // 身份证密码校验
            public static final String UP_1603 = "1603"; // 修改身份证密码
        }

        //库存管理
        public static class KCGL {
            public static final String UP_3501 = "3501"; // 商品盘存上传
            public static final String UP_3502 = "3502"; // 商品库存变更
            public static final String UP_3503 = "3503"; // 商品采购
            public static final String UP_3504 = "3504"; // 商品采购退货
            public static final String UP_3505 = "3505"; // 商品销售
            public static final String UP_3506 = "3506"; // 商品销售退货
            public static final String UP_3507 = "3507"; // 商品信息删除
        }
        // 招采
        public static class ZC {
            public static final String UP_8101 = "8101"; // 接口连通性测试
            public static final String UP_8102 = "8102"; // 获取token

            public static final String UP_8201 = "8201"; // 获取医院库房列表
            public static final String UP_8202 = "8202"; // 药品获取挂网目录列表
            public static final String UP_8501 = "8501"; // 药品库存查询
            public static final String UP_8502 = "8502"; // 药品库存上传变更
            public static final String UP_8503 = "8503"; // 药品销售列表查询
            public static final String UP_8504 = "8504"; // 药品销售
            public static final String UP_8505 = "8505"; // 药品销售退货
            public static final String UP_8506 = "8506"; // 耗材库存查询
            public static final String UP_8507 = "8507"; // 耗材库存上传变更
            public static final String UP_8508 = "8508"; // 耗材销售列表查询
            public static final String UP_8509 = "8509"; // 耗材销售
            public static final String UP_8510 = "8510"; // 耗材销售退货


        }

        public static class XZLX {
            public static final String CZZG = "310"; // 城镇职工
            public static final String CXJM = "390"; // 城乡居民
            public static final String LX = "340"; // 离休
        }

        public static class ISMAN {
            public static final String S = "1"; // 是
            public static final String F = "0"; // 否
        }

        public static class ZKZT {
            public static final String WZK = "0"; // 未质控
            public static final String ZKWWC = "1"; // 质控未完成
            public static final String ZKWC = "2"; // 质控完成
        }

        public static class ZKLX {
            public static final String DRG = "1"; // DRG
            public static final String DIP = "2"; // DIP
        }
        /*医保编码为 甘肃：620102 海南的：460100*/
        public static class YBBMQZ {
            public static final String HN = "46"; // 海南医保前缀
            public static final String GS = "62"; // 甘肃医保前缀
            public static final String GD = "44"; // 广东医保前缀
        }
        /*明细审核接口编码*/
        public static class MXSHBM {
            public static final String SQSH = "3101"; // 事前审核
            public static final String SZSH = "3102"; // 事中审核
            public static final String SQSH_HAINAN = "3660"; // 海南事前审核
            public static final String SZSH_HAINAN = "3661"; // 海南事中审核
        }
    }



}
