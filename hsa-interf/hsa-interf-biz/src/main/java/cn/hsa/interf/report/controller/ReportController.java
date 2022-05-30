package cn.hsa.interf.report.controller;

import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.interf.report.bo.ReportBO;
import cn.hsa.module.interf.report.service.ReportService;
import cn.hsa.util.Constants;
import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 报表控制层
 */
@RestController
@RequestMapping("/interf/report")
@Slf4j
public class ReportController {

    @Resource
    private ReportService reportService;

    static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);//表示忽略大小写

    @Value("${rsa.private.key}")
    private String privateKey;

    @GetMapping("/getReportData")
    public JSONObject getReportData(@RequestParam Map<String,Object> map){
        map.put("hospCode","1000001");
        map.remove("startDate");
        map.remove("endDate");
        Long start = System.currentTimeMillis();
        String data = getJSONDataFromFile();
        JSONArray result = JSON.parseArray(data);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",result);
        Long cost = System.currentTimeMillis()-start;
        System.err.println("=-=report接口测试用时" +cost+" ms");
        return jsonObject;
    }

    @GetMapping("/getReportDataBySQL")
    public JSONObject getReportDataBySQL(@RequestParam Map<String,Object> map){
        Optional.ofNullable(map.get("hospCode")).orElseThrow(()-> new AppException("医院编码解析失败"));

        String hospCode =  (String)map.get("hospCode");
        // 解密医院编码
        try {
            hospCode = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(hospCode.getBytes()), privateKey);
        } catch (Exception e) {
            throw new AppException("签名入参错误,请联系管理员！" + e.getMessage() + "11-");
        }
        map.put("hospCode",hospCode);
        map.remove("startDate");
        map.remove("endDate");
        Long start  = System.currentTimeMillis();
        Integer pageSize = Integer.valueOf(String.valueOf(map.get("pageSize") == null? 10:map.get("pageSize")));
        PageDTO pageDTO = reportService.queryByStrSQL(map).getData();
        Integer totalRows = pageDTO.getTotal().intValue();
        int total = (int)Math.ceil(totalRows/pageSize)+ (totalRows % pageSize == 0 ? 0:1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",pageDTO.getResult());
        jsonObject.put("count",totalRows);
        jsonObject.put("total",total);
        Long end = System.currentTimeMillis();
        Long cost = end -start;
        System.err.println("==-==report time consuming"+cost +" ms");
        return jsonObject;
    }

    private String getJSONDataFromFile(){
        String line = null;
        BufferedReader reader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try{
            InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("testData.json");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while((line = reader.readLine())!= null){
                stringBuffer.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();

    }
}
