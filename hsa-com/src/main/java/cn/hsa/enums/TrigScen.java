package cn.hsa.enums;

import lombok.Getter;

/**
 * 明细审核分析服务 触发场景
 */
@Getter
public enum TrigScen {
    TRIG_SCEN_1("1", "事前-门诊挂号"),
    TRIG_SCEN_2("2", "事前-门诊收费登记"),
    TRIG_SCEN_3("3", "事前-住院登记"),
    TRIG_SCEN_4("4", "事前-住院收费登记"),
    TRIG_SCEN_5("5", "事前-住院执行医嘱"),
    TRIG_SCEN_6("6", "事中-门诊结算"),
    TRIG_SCEN_7("7", "事中-门诊预结算"),
    TRIG_SCEN_8("8", "事中-住院结算"),
    TRIG_SCEN_9("9", "事中-住院预结算"),
    TRIG_SCEN_10("10", "事中-购药划卡");

    private String code;
    private String name;

    TrigScen(String code, String name) {
        this.code = code;
        this.name = name;
    }
}