package cn.hsa.interf.outpt.controller;

import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.triage.dto.OutptTriageVisitDTO;
import cn.hsa.module.outpt.triage.service.OutptTriageVisitService;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.centermenu.controller
 * @Class_name: OutptTriageVisitController
 * @Describe: 门诊分诊病人接口提供
 * @Author: pengbo
 * @Email: pengbo@powersi.com
 * @Date: 2021/6/21 20:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("outpt/outptTriageVisit")
@Slf4j
public class OutptTriageVisitController {

    @Resource
    private OutptTriageVisitService outptTriageVisitService;
    @Value("${rsa.private.key}")
    private String privateKey;
    /**
     * @Menthod hisInferface
     * @Desrciption 分诊入口
     * @Author luonianxin
     * @Date   2021/5/12 9:12
     * @Return
     **/
    @RequestMapping(value="/hisInterface",method= RequestMethod.POST, produces= MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    public void hisInferface(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 签名
        String params = getRequestBody(request);
        if (StringUtils.isEmpty(params)) {
            throw new AppException("请求参数不能为空");
        }

        OutptTriageVisitDTO outptTriageVisitDTO  = JSONObject.parseObject(params, OutptTriageVisitDTO.class);

        // 签名
        if (StringUtils.isEmpty(outptTriageVisitDTO.getSign())) {
            throw new AppException("签名不能为空");
        }
        // 分诊台编码
        if (StringUtils.isEmpty(outptTriageVisitDTO.getTriageId())) {
            throw new AppException("分诊台编码不能为空");
        }
        String hospCode = outptTriageVisitDTO.getSign();
        // 解密医院编码
        try {
            hospCode = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(hospCode.getBytes()), privateKey);
        } catch (Exception e) {
            throw new AppException("签名入参错误,请联系管理员！" + e.getMessage() + "11-");
        }
        outptTriageVisitDTO.setSign(hospCode);
        outptTriageVisitDTO.setHospCode(hospCode);
        Map map = new HashMap();
        map.put("hospCode",hospCode);
        map.put("outptTriageVisitDTO",outptTriageVisitDTO);

        List<OutptTriageVisitDTO> list = outptTriageVisitService.queryOutptTriageVisit(map).getData();

        // 回写信息到调用方
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            out = response.getWriter();
            String data = "" ;
            if(ListUtils.isEmpty(list)){
                out.write("{'code':'-1','message':'暂无数据'}");
            }else {
                com.alibaba.fastjson.JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
                data = com.alibaba.fastjson.JSONObject.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
                out.write("{'code':'0','message':'查询成功','data':'" + data + "'}");
            }
            out.flush();
            log.info("传输给叫号系统的数据为: {}",data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
        }
    }

    /**
     * 获取request中数据
     * @param request
     * @return
     */
    private String  getRequestBody(HttpServletRequest request) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            StringBuffer jsonSb = new StringBuffer();
            String line;
            while((line = br.readLine()) != null){
                jsonSb.append(line);
            }

            return jsonSb.toString();
        } catch (Exception e) {
        }
        return null;
    }
}
