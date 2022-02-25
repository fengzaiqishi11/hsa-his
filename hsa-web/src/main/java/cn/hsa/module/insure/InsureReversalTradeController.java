package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.report.business.dto.ReportReturnDataDTO;
import cn.hsa.module.report.business.service.ReportDataDownLoadService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.insure
 *@Class_name: insureReversalTradeController
 *@Describe: 医院医保冲正交易
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 10:56
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/reversalTrade")
@Slf4j
public class InsureReversalTradeController extends BaseController {

    @Resource
    private InsureUnifiedPayReversalTradeService insureUnifiedPayReversalTradeService_consumer;

    @Resource
    private ReportDataDownLoadService reportDataDownLoadService_consumer;

    /**冲正交易查询
    * @Method queryReversalTradePage
    * @Desrciption 
    * @param insureReversalTradeDTO
    * @Author liuqi1
    * @Date   2021/4/12 20:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryReversalTradePage")
    public WrapperResponse<PageDTO> queryReversalTradePage(InsureReversalTradeDTO insureReversalTradeDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureReversalTradeDTO",insureReversalTradeDTO);
        insureReversalTradeDTO.setHospCode( sysUserDTO.getHospCode());
        return insureUnifiedPayReversalTradeService_consumer.queryReversalTradePage(map);
    }

    /**医保统一支付平台：冲正交易
    * @Method UP_2601
    * @Desrciption 
    * @param insureReversalTradeDTO
    * @Author liuqi1
    * @Date   2021/4/12 11:46
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateReversalTrade")
    public WrapperResponse<Boolean> UP_2601(@RequestBody InsureReversalTradeDTO insureReversalTradeDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        map.put("insureReversalTradeDTO",insureReversalTradeDTO);

        return  insureUnifiedPayReversalTradeService_consumer.updateUP_2601(map);
    }


    /**医药机构费用结算对总账数据查询
     * @Method queryDataWith3201
     * @Desrciption
     * @param paraMap
     * @Author liuqi1
     * @Date   2021/4/13 20:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @GetMapping("/queryDataWith5261")
    public WrapperResponse<PageDTO> queryDataWith5261(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());

        WrapperResponse wrapperResponse = insureUnifiedPayReversalTradeService_consumer.queryDataWith5261(paraMap);

        return wrapperResponse;
    }

    /**医药机构费用结算对总账数据查询
    * @Method queryDataWith3201
    * @Desrciption
    * @param paraMap
    * @Author liuqi1
    * @Date   2021/4/16 11:25
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryDataWith3201")
    public WrapperResponse<PageDTO> queryDataWith3201(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());

        WrapperResponse wrapperResponse = insureUnifiedPayReversalTradeService_consumer.queryDataWith3201(paraMap);

        return wrapperResponse;
    }

    /**医药机构费用结算对总账
    * @Method invoking3201
    * @Desrciption 
    * @param paraMap
    * @Author liuqi1
    * @Date   2021/4/16 17:31
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @PostMapping("/invoking3201")
    public WrapperResponse<Map<String,Object>> invoking3201(@RequestBody Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());

        WrapperResponse wrapperResponse = insureUnifiedPayReversalTradeService_consumer.updateUP_3201(paraMap);

        return wrapperResponse;
    }

    /**医药机构费用结算对明细账
    * @Method invoking3202
    * @Desrciption 
    * @param paraMap
    * @Author liuqi1
    * @Date   2021/4/16 17:31
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @PostMapping("/invoking3202")
    public WrapperResponse<Map<String,Object>> invoking3202(@RequestBody Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        WrapperResponse wrapperResponse = insureUnifiedPayReversalTradeService_consumer.updateUP_3202(paraMap);
        return wrapperResponse;
    }


    /**结算单信息查询接口调用
     * @Method invoking5261
     * @Desrciption
     * @param paraMap
     * @Author liuqi1
     * @Date   2021/4/13 20:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @PostMapping("/invoking5261")
    public WrapperResponse<Map<String,Object>> invoking5261(@RequestBody Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());

        WrapperResponse wrapperResponse = insureUnifiedPayReversalTradeService_consumer.updateUP_5261(paraMap);

        return wrapperResponse;
    }


    /**结算信息查询接口调用
    * @Method invoking5262
    * @Desrciption
    * @param paraMap
    * @Author liuqi1
    * @Date   2021/4/14 17:07
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @PostMapping("/invoking5262")
    public WrapperResponse<Map<String,Object>> invoking5262(@RequestBody Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());

        WrapperResponse wrapperResponse = insureUnifiedPayReversalTradeService_consumer.updateUP_5262(paraMap);

        return wrapperResponse;
    }


    /**结算单下载接口调用
    * @Method invoking5265
    * @Desrciption 
    * @param paraMap
    * @param response
    * @Author liuqi1
    * @Date   2021/4/20 11:01
    * @Return void
    **/
    @GetMapping("/invoking5265")
    public void invoking5265(@RequestParam Map<String,Object> paraMap, HttpServletResponse response, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        WrapperResponse<Map<String, Object>> mapWrapperResponse = insureUnifiedPayReversalTradeService_consumer.updateUP_5265(paraMap);
        Map<String, Object> resultMap = mapWrapperResponse.getData();
        String s = String.valueOf(resultMap.get("list"));
        byte[] bytes2 = s.getBytes(StandardCharsets.ISO_8859_1);
        downloadPDF(response,bytes2);
    }

    /**pdf流文件下载
    * @Method downloadPDF
    * @Desrciption
    * @param response
    * @param byt
    * @Author liuqi1
    * @Date   2021/4/20 11:02
    * @Return void
    **/
    public void downloadPDF(HttpServletResponse response, byte[] byt) {
        try {
            BufferedInputStream bis = null;
            OutputStream os = null;
            FileInputStream fileInputStream = null;
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/pdf"); // word格式
            response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode("结算单","utf-8") + DateUtils.format(DateUtils.getNow(),DateUtils.YMDHMS)+".pdf");
            try {
                InputStream input = new ByteArrayInputStream(byt);
                byte[] buff = new byte[1024];
                bis = new BufferedInputStream(input);
                os = response.getOutputStream();

                while (bis.read(buff) != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                }

                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            logger.info("错误信息", e);
        }
    }

    /**
     * @Method queryStatementInfo
     * @Desrciption 对账单查询打印
     * @param paraMap
     * @Author liaojiguang
     * @Date   2021/10/21 09:01
     * @Return
     **/
    @GetMapping("/queryStatementInfo")
    public WrapperResponse<Map<String,Object>> queryStatementInfo(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        paraMap.put("statistician",sysUserDTO.getName());
        return insureUnifiedPayReversalTradeService_consumer.queryStatementInfo(paraMap);
    }

    /**
     * @Method queryDeclareInfos
     * @Desrciption 清算申报报表
     * @param paraMap
     * @Author liaojiguang
     * @Date   2021/10/21 09:01
     * @Return
     **/
    @GetMapping("/queryDeclareInfosPage")
    public WrapperResponse<PageDTO> queryDeclareInfosPage(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        WrapperResponse<PageDTO> pageDto = insureUnifiedPayReversalTradeService_consumer.queryDeclareInfosPage(paraMap);
        return pageDto;
    }

    /**
     * @Method querySumDeclareInfosPage
     * @Desrciption 清算申报合计报表
     * @param paraMap
     * @Author liaojiguang
     * @Date   2021/10/21 09:01
     * @Return
     **/
    @GetMapping("/querySumDeclareInfosPage")
    public WrapperResponse<PageDTO> querySumDeclareInfosPage(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        WrapperResponse<PageDTO> pageDto = insureUnifiedPayReversalTradeService_consumer.querySumDeclareInfosPage(paraMap);
        return pageDto;
    }

    /**
     * @Method querySumDeclareInfoPrint
     * @Desrciption 清算申报合计报表打印
     * @param paraMap
     * @Author liaojiguang
     * @Date   2021/10/21 09:01
     * @Return
     **/
    @GetMapping("/querySumDeclareInfoPrint")
    public WrapperResponse<Map<String,Object>> querySumDeclareInfoPrint(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode",sysUserDTO.getHospCode());
        paraMap.put("crteId",sysUserDTO.getId());
        paraMap.put("crteName",sysUserDTO.getName());
        WrapperResponse<Map<String,Object>> resultMap = insureUnifiedPayReversalTradeService_consumer.querySumDeclareInfoPrint(paraMap);
        return resultMap;
    }

    /**
     * @Method querySumDeclareInfoPrints
     * @Desrciption 清算申报合计报表打印
     * @param paraMap
     * @Author liuhuiming
     * @Date   2022/2/21 09:01
     * @Return
     **/
    @GetMapping("/querySumDeclareInfoPrints")
    public WrapperResponse<ReportReturnDataDTO> querySumDeclareInfoPrints(@RequestParam Map<String,Object> paraMap, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        paraMap.put("hospCode", sysUserDTO.getHospCode());
        paraMap.put("crteId", sysUserDTO.getId());
        paraMap.put("crteName", sysUserDTO.getName());
        WrapperResponse<Map<String, Object>> result = insureUnifiedPayReversalTradeService_consumer.querySumDeclareInfoPrint(paraMap);
        result.getData().put("hospCode", sysUserDTO.getHospCode());
        result.getData().put("crterId", sysUserDTO.getId());
        result.getData().put("crterName", sysUserDTO.getName());
        result.getData().put("declaraType", MapUtils.get(paraMap, "declaraType"));
        result.getData().put("businessType", "settleDeclareSheetProcess");
        WrapperResponse<ReportReturnDataDTO> resultDTO = reportDataDownLoadService_consumer.saveBuild(result.getData());
        return resultDTO;
    }

    /**
     * @Method downLoadSettleInfo
     * @Desrciption  HIS结算单
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 11:42
     * @Return
     **/
    @PostMapping("/downLoadSettleInfo")
    public WrapperResponse<Map<String,Object>> downLoadSettleInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        return insureUnifiedPayReversalTradeService_consumer.downLoadSettleInfo(map);
    }

}
