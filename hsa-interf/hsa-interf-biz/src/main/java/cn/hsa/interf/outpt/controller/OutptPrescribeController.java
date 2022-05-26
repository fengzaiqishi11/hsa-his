package cn.hsa.interf.outpt.controller;

import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.interf.outpt.dto.YjRcDTO;
import cn.hsa.module.interf.outpt.service.OutptPrescribeService;
import cn.hsa.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.centermenu.controller
 * @Class_name: OutptPrescribeController
 * @Describe: 门诊处方接口提供
 * @Author: zengfeng
 * @Email: zengfeng@powersi.com
 * @Date: 2021/5/10 20:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("outpt/outptPrescribe")
@Slf4j
public class OutptPrescribeController{
  @Resource
  private OutptPrescribeService outptPrescribeService;
  @Value("${rsa.private.key}")
  private String privateKey;
  /**
   * @Menthod hisInferface
   * @Desrciption 云净接口入口
   * @Author zengfeng
   * @Date   2021/5/12 9:12
   * @Return
   **/
  @PostMapping("/hisInferface")
  public WrapperResponse<List<Map<String, Object>>> hisInferface(@RequestBody YjRcDTO yjRcDTO) throws Exception {
      System.out.println(yjRcDTO);
      // 签名
      if (StringUtils.isEmpty(yjRcDTO.getSign())) {
        throw new AppException("签名不能为空");
      }
      // 业务
      if (StringUtils.isEmpty(yjRcDTO.getYwid())) {
        throw new AppException("业务ID不能为空");
      }
      // 用户
      if (StringUtils.isEmpty(yjRcDTO.getYhid())) {
        throw new AppException("用户ID不能为空");
      }
      String hospCode = yjRcDTO.getSign();
      // 解密医院编码
      try {
          hospCode = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(hospCode.getBytes()), privateKey);
      } catch (Exception e) {
        throw new AppException("签名入参错误,请联系管理员！" + e.getMessage() + "11-");
      }
      yjRcDTO.setSign(hospCode);
      yjRcDTO.setHospCode(hospCode);
      Map map = new HashMap();
      map.put("hospCode",hospCode);
      map.put("yjRcDTO",yjRcDTO);
      return outptPrescribeService.hisInferface(map);
  }

//    public static void main(String[] args) throws Exception {
//      String a = "0005";
//        String map = RSAUtil.encryptByPublicKey(a.getBytes(),"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAImeWoemAQT+RVoYCV2Zwkp/Wd8tZJdLekP8fUMzXnAKIiveAjM9vIBFkozx6FflCTzUuRIWhytyq0Lf25+XGPECAwEAAQ==/x9QzNecAoiK94CMz28gEWSjPHoV+UJPNS5EhaHK3KrQt/bn5cY8QIDAQABAkARx46kpdLN5Vfqat6S5DGQ1GE1DzVGwq6aJ/269+EEkmlgLJGq0J2PFQEozWcfTZyvqDFZxaOc5fjGl7n9q4AdAiEA3qyShES5SNCSwJCG+0x+vPNY5YGKo95654te8A+Ef9sCIQCeNwEt09dvjaMHv+49LR6kLBGUjk8xzgYD28U70yY6IwIhAJIkKLTudbw4R1hignSDq9pOy9U0w8zwwzEb418ikA9pAiBc9MJLk6CLGTOFNR4bcWwEVyQJHUeoYnykPbZ3PMrD8wIgUHstQTolAIfvDSNJtt5f7XAZDDqI+HXh+cjR2Tj49B0=");
//        System.out.println(map);
////        map = URLEncoder.encode(map);
//        System.out.println(map);
//  }

    public static void main(String[] args) {
      String hospCode = "f1VbyR34XnWpSi5Xh/BeGQmUAzZf4OCY4NaOpriCk+Zx6qoQrJDPLzuu9KKbCCuTLs0kggEQtVMD9OKaP2IQ3A==";
        try {
            hospCode = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(hospCode.getBytes()), "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAiZ5ah6YBBP5FWhgJXZnCSn9Z3y1kl0t6Q/x9QzNecAoiK94CMz28gEWSjPHoV+UJPNS5EhaHK3KrQt/bn5cY8QIDAQABAkARx46kpdLN5Vfqat6S5DGQ1GE1DzVGwq6aJ/269+EEkmlgLJGq0J2PFQEozWcfTZyvqDFZxaOc5fjGl7n9q4AdAiEA3qyShES5SNCSwJCG+0x+vPNY5YGKo95654te8A+Ef9sCIQCeNwEt09dvjaMHv+49LR6kLBGUjk8xzgYD28U70yY6IwIhAJIkKLTudbw4R1hignSDq9pOy9U0w8zwwzEb418ikA9pAiBc9MJLk6CLGTOFNR4bcWwEVyQJHUeoYnykPbZ3PMrD8wIgUHstQTolAIfvDSNJtt5f7XAZDDqI+HXh+cjR2Tj49B0=");
        } catch (Exception e) {
            throw new AppException("签名入参错误,请联系管理员！" + e.getMessage() + "11-");
        }
        System.out.println(hospCode);
    }


}

