package cn.hsa.insure.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: ResultBean
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 14:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class ResultBean extends HashMap {

    private static final long serialVersionUID = 1L;
    private String errCode = "0";
    private String errMsg;

    public ResultBean() {
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String toString() {
        return this.toJson();
    }

    public String toJson() {
        this.put("errCode", this.getErrCode());
        this.put("errMsg", this.getErrMsg());
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(this);
    }

    public static ResultBean fromJson(String sJson) {
        ResultBean ret = (ResultBean)(new Gson()).fromJson(sJson, ResultBean.class);
        ret.setErrCode((String)ret.get("errCode"));
        ret.setErrMsg((String)ret.get("errMsg"));
        return ret;
    }

}
