package cn.hsa.center.nationstandarddrug.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugZYDO;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugService;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugZYService;
import cn.hsa.util.ExcelXlsReader;
import cn.hsa.util.ExcelXlsxReader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.nationstandarddrug.controller
 * @Class_name: NationStandardDrugController
 * @Describe: 国家药品标准信息控制层
 * @Author: luonianxin
 * @Email: nianxin.luo@powersi.com
 * @Date: 2021/4/27 11:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/center/nationStandardDrugZY")
public class NationStandardDrugZYController extends CenterBaseController {

    @Resource
    private NationStandardDrugZYService nationStandardDrugZYService;

    /**
     * @Package_name: cn.hsa.center.nationstandarddrug.controller
     * @Class_name: NationStandardDrugController
     * @Describe: 分页查询国家标准药品信息（中药）
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/4/27 17:51
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    @GetMapping("/getZYPage")
    WrapperResponse<PageDTO> queryNationStandardDrugZYPage(NationStandardDrugZYDTO nationStandardDrugZYDTO){
        return nationStandardDrugZYService.queryNationStandardDrugZYPage(nationStandardDrugZYDTO) ;
    }

    /**
     * @Describe: 通过中药查询国家标准药品信息（中药s）
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/4/27 17:54
     **/
    @GetMapping("/getZYById")
    WrapperResponse<NationStandardDrugZYDTO> getZYById(NationStandardDrugZYDTO nationStandardDrugZYDTO){
        return nationStandardDrugZYService.getZYById(nationStandardDrugZYDTO);
    }

    /**
     *   保存国家标准中药品信息
     * @param nationStandardDrugZYDO
     * @return
     */
    @PostMapping("/saveNationStandardDrugZY")
    WrapperResponse<Boolean> saveNationStandardDrugZY(@RequestBody NationStandardDrugZYDO nationStandardDrugZYDO){
        Map map = new HashMap<>(4);
        map.put("nationStandardDrugZYDO",nationStandardDrugZYDO);
        return nationStandardDrugZYService.saveNationStandardDrugZY(map);
    }

    /**
     * @Description: 文件上传
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/6 17:22
     **/
    @PostMapping("/upLoad")
    public WrapperResponse<Boolean> insertUpLoad(@RequestParam MultipartFile file) {
        //excel2003扩展名
        final String excel03Extention = ".xls";
        //excel2007扩展名
        final String excel07Extention = ".xlsx";
        Integer fileReadStep = 1000;
        WrapperResponse<Boolean>  result = null;
        String fileName = file.getOriginalFilename();
        int rows = -1;
        try {
            //处理excel2003文件
            Long now = System.currentTimeMillis();
            logger.error("上传开始{} ms",now);
            if (fileName.endsWith(excel03Extention)) {
                ExcelXlsReader excelXlsReader = new ExcelXlsReader(nationStandardDrugZYService);
                excelXlsReader.setUserId(userId==null?"":userId);
                excelXlsReader.setUserName(userName==null?"":userName);
                rows = excelXlsReader.process(file, fileReadStep);
            }else if (fileName.endsWith(excel07Extention)) {
                ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader(nationStandardDrugZYService);
                excelXlsxReader.setUserId(userId==null?"":userId);
                excelXlsxReader.setUserName(userName==null?"":userName);
                rows = excelXlsxReader.process(file, fileReadStep);
            }else{
                throw new AppException("解析错误，文件类型或格式不对");
            }
            logger.error("处理结束{} ms",System.currentTimeMillis() - now);
            if(rows<0){
                throw new AppException("解析错误，数据为空");
            }
        }catch (AppException ae){
            result = WrapperResponse.fail(false);
            result.setMessage(ae.getMessage());
            return result;
        }catch (Exception e){
            logger.error("文件上传出错详情请联系管理员查看日志 .",e);
            result = WrapperResponse.fail(false);
            result.setMessage("文件解析出错，请联系系统管理员");
            return result;
        }
        return WrapperResponse.success(true);
    }
}
