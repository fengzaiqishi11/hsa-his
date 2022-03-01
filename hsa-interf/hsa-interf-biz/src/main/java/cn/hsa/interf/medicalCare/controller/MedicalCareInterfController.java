package cn.hsa.interf.medicalCare.controller;

import cn.hsa.base.BaseController;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.medicalCare.service.MedicalCareInterfService;
import cn.hsa.util.MapUtils;
import com.alibaba.fastjson.JSON;
import kafka.utils.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;

/**
 * @Package_name: cn.hsa.interf.medicalCare.controller
 * @Class_name: MedicalCareInterfController
 * @Describe: 医养转换his接口controller
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2022-02-28 11:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/

@Slf4j
@RestController
@RequestMapping("/interf/medicalCare")
public class MedicalCareInterfController extends BaseController {

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCa+uU4fxL5Kc8u8gjeSBr5G0jKV0b8Qlo0i5sfh9kCpyNNxa7Oh/WjySZwvcIifKXz3M7Be9eJ4nYQgsxQvnOnS1zCZosce9gKAmnIafjAnxP2TU5rP7qLxmSvAY6Dk6xstr9sI6L5ZqIrDOw/gN32R6UHXtbx5NcKpnrVnb2p7wIDAQAB";
    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJr65Th/Evkpzy7yCN5IGvkbSMpXRvxCWjSLmx+H2QKnI03Frs6H9aPJJnC9wiJ8pfPczsF714nidhCCzFC+c6dLXMJmixx72AoCachp+MCfE/ZNTms/uovGZK8BjoOTrGy2v2wjovlmoisM7D+A3fZHpQde1vHk1wqmetWdvanvAgMBAAECgYEAlTqbdzAdE+CnR9v0oBysJEuaZoNtrb8yXsBCqTTEyCIqA1wjO2l9JK2unwaJJW4C2fM2/uWBoJp39uQLWoBYmSVyWktWxYS7AqPLhjR5Qo7aGIVjXF702GDTc5rNRB6wyzffOb74njtegbKoFbFCxjwNCG2z2zdrO/CPvFK+3XkCQQD/1vas04HDOcDNLA+KSJLexm+YZOUiC/w0HNfvgQqqEsNWnUGN25AXAHgP0RvYhsBy+CKwUnvfWc99ro0oYSKrAkEAmxPBCH2T1bkj2KVDGUmvbqeICAG3FFOAiNEkedlCKbzNKEEWmtkv6IsFkzCQxi0QGY2y+s6hbFyrmcFB31a1zQJBAIDd04S8SOmARrhSEEXURn6GL6HweGYM6W2Kdc5DDo8aQmB3d5Sv2sVrTA6TgtYvEfMXP8nW0mRvhY9GA/4p518CQBj2xx5MyN2g3ipkADcz62/OvFE9pXE6N/jOYibTWJlLcWNHhxIKYHH43z7glI2yd2MFsog5dzbwfBdWSvpfBQkCQGasVndBM6qdk4AmQZAEcPB0qCaGYZPo/eTdqXNeEk2X2cbCf1JtPpPYpL9NLiCrL6vthoE6vXURzXP0XUT2isk=";

    @Resource
    private MedicalCareInterfService medicalCareInterfService_consumer;

    /**
     * @Menthod: getVisitInfoRecord
     * @Desrciption: 获取医转养就诊信息
     * @Param: hospCode：医院编码，medical_info_id：就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2022-02-28 11:57
     * @Return:
     **/
    @GetMapping("/getVisitInfoRecord")
    public WrapperResponse<Map<String, Object>> getVisitInfoRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Optional.ofNullable(MapUtils.get(map, "hospCode")).orElseThrow(() -> new RuntimeException("未传入医院编码"));
        try {
            hospCode = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(hospCode.getBytes()), PRIVATE_KEY);
        } catch (Exception e) {
            throw new RuntimeException("签名入参解密错误，请联系管理员！" + e.getMessage());
        }
        String visitId = MapUtils.get(map, "medical_info_id");
        Optional.ofNullable(visitId).orElseThrow(() -> new RuntimeException("未传入就诊人就诊id"));
        map.put("hospCode", hospCode);
        return medicalCareInterfService_consumer.getVisitInfoRecord(map);
    }

    public static void main1(String[] args) {
        try {
            Map<String, Object> map = RSAUtil.genKeyPair();
            System.out.println("public_key = " + RSAUtil.getPublicKey(map)); // MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCa+uU4fxL5Kc8u8gjeSBr5G0jKV0b8Qlo0i5sfh9kCpyNNxa7Oh/WjySZwvcIifKXz3M7Be9eJ4nYQgsxQvnOnS1zCZosce9gKAmnIafjAnxP2TU5rP7qLxmSvAY6Dk6xstr9sI6L5ZqIrDOw/gN32R6UHXtbx5NcKpnrVnb2p7wIDAQAB
            System.out.println("private_key = " + RSAUtil.getPrivateKey(map)); // MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJr65Th/Evkpzy7yCN5IGvkbSMpXRvxCWjSLmx+H2QKnI03Frs6H9aPJJnC9wiJ8pfPczsF714nidhCCzFC+c6dLXMJmixx72AoCachp+MCfE/ZNTms/uovGZK8BjoOTrGy2v2wjovlmoisM7D+A3fZHpQde1vHk1wqmetWdvanvAgMBAAECgYEAlTqbdzAdE+CnR9v0oBysJEuaZoNtrb8yXsBCqTTEyCIqA1wjO2l9JK2unwaJJW4C2fM2/uWBoJp39uQLWoBYmSVyWktWxYS7AqPLhjR5Qo7aGIVjXF702GDTc5rNRB6wyzffOb74njtegbKoFbFCxjwNCG2z2zdrO/CPvFK+3XkCQQD/1vas04HDOcDNLA+KSJLexm+YZOUiC/w0HNfvgQqqEsNWnUGN25AXAHgP0RvYhsBy+CKwUnvfWc99ro0oYSKrAkEAmxPBCH2T1bkj2KVDGUmvbqeICAG3FFOAiNEkedlCKbzNKEEWmtkv6IsFkzCQxi0QGY2y+s6hbFyrmcFB31a1zQJBAIDd04S8SOmARrhSEEXURn6GL6HweGYM6W2Kdc5DDo8aQmB3d5Sv2sVrTA6TgtYvEfMXP8nW0mRvhY9GA/4p518CQBj2xx5MyN2g3ipkADcz62/OvFE9pXE6N/jOYibTWJlLcWNHhxIKYHH43z7glI2yd2MFsog5dzbwfBdWSvpfBQkCQGasVndBM6qdk4AmQZAEcPB0qCaGYZPo/eTdqXNeEk2X2cbCf1JtPpPYpL9NLiCrL6vthoE6vXURzXP0XUT2isk=
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
//        String a = "1000001";
//        String encrypt = RSAUtil.encryptByPublicKey(a.getBytes(), "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCa+uU4fxL5Kc8u8gjeSBr5G0jKV0b8Qlo0i5sfh9kCpyNNxa7Oh/WjySZwvcIifKXz3M7Be9eJ4nYQgsxQvnOnS1zCZosce9gKAmnIafjAnxP2TU5rP7qLxmSvAY6Dk6xstr9sI6L5ZqIrDOw/gN32R6UHXtbx5NcKpnrVnb2p7wIDAQAB");
//        System.out.println("encrypt = " + encrypt);

        String s = "gk2+e7hDJjGp+EbfiJlmo9be8glaAnIvEpyUZnIdm6lYR0cUD6Yn/PK2lKGiAwvkPGilNuFsEd3uQg7HkmAh3JOZbVr/NOE1bhANzXFFFOwLz/r0FV41MKQr2yc9X10gbMcG77nVEuLY4ai5Par2ZGU+h3VWW5ZpF4fEqkeyg+Q=";
        String b = RSAUtil.decryptByPrivateKey(Base64.decodeBase64(s.getBytes()), "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJr65Th/Evkpzy7yCN5IGvkbSMpXRvxCWjSLmx+H2QKnI03Frs6H9aPJJnC9wiJ8pfPczsF714nidhCCzFC+c6dLXMJmixx72AoCachp+MCfE/ZNTms/uovGZK8BjoOTrGy2v2wjovlmoisM7D+A3fZHpQde1vHk1wqmetWdvanvAgMBAAECgYEAlTqbdzAdE+CnR9v0oBysJEuaZoNtrb8yXsBCqTTEyCIqA1wjO2l9JK2unwaJJW4C2fM2/uWBoJp39uQLWoBYmSVyWktWxYS7AqPLhjR5Qo7aGIVjXF702GDTc5rNRB6wyzffOb74njtegbKoFbFCxjwNCG2z2zdrO/CPvFK+3XkCQQD/1vas04HDOcDNLA+KSJLexm+YZOUiC/w0HNfvgQqqEsNWnUGN25AXAHgP0RvYhsBy+CKwUnvfWc99ro0oYSKrAkEAmxPBCH2T1bkj2KVDGUmvbqeICAG3FFOAiNEkedlCKbzNKEEWmtkv6IsFkzCQxi0QGY2y+s6hbFyrmcFB31a1zQJBAIDd04S8SOmARrhSEEXURn6GL6HweGYM6W2Kdc5DDo8aQmB3d5Sv2sVrTA6TgtYvEfMXP8nW0mRvhY9GA/4p518CQBj2xx5MyN2g3ipkADcz62/OvFE9pXE6N/jOYibTWJlLcWNHhxIKYHH43z7glI2yd2MFsog5dzbwfBdWSvpfBQkCQGasVndBM6qdk4AmQZAEcPB0qCaGYZPo/eTdqXNeEk2X2cbCf1JtPpPYpL9NLiCrL6vthoE6vXURzXP0XUT2isk=");
        System.out.println("b = " + b);

    }
}
