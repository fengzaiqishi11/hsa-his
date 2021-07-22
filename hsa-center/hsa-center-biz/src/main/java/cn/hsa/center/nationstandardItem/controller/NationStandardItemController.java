package cn.hsa.center.nationstandardItem.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;
import cn.hsa.module.center.nationstandardItem.service.NationStandardItemService;
import cn.hsa.util.ExcelXlsReader;
import cn.hsa.util.ExcelXlsxReader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.datasource.controller
 * @Class_name: CenterDatasourceController
 * @Describe: 国家标准项目Controller
 * @Author: xingyu.xie
 * @Email: oubo@powersi.com.cn
 * @Date: 2021/1/26 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/nationStandardItem")
public class NationStandardItemController extends CenterBaseController {


    @Resource
    private NationStandardItemService nationStandardItemService;

    /**
     * @Menthod getById
     * @Desrciption
     * @param nationStandardItemDTO 根据id查询国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<NationStandardItemDTO> getById(NationStandardItemDTO nationStandardItemDTO){
        return nationStandardItemService.getById(nationStandardItemDTO);
    }


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardItemDTO 查询所有国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<NationStandardItemDTO>> queryAll(NationStandardItemDTO nationStandardItemDTO){
        return nationStandardItemService.queryAll(nationStandardItemDTO);
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardItemDTO 分页查询国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(NationStandardItemDTO nationStandardItemDTO){
        return nationStandardItemService.queryPage(nationStandardItemDTO);
    }

    /**
       * @Description: 保存国家标准材料信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 9:47
    **/
    @PostMapping("/saveNationStandardItem")
    public WrapperResponse<Boolean> saveNationStandardItem(@RequestBody NationStandardItemDO nationStandardItemDO){
        Map map = new HashMap();
        nationStandardItemDO.setCrteId(userId);
        nationStandardItemDO.setCrteName(userName);
        map.put("nationStandardItemDO",nationStandardItemDO);
        return nationStandardItemService.saveNationStandardItem(map);
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
                ExcelXlsReader excelXlsReader = new ExcelXlsReader(nationStandardItemService);
                excelXlsReader.setUserId(userId==null?"":userId);
                excelXlsReader.setUserName(userName==null?"":userName);
                rows = excelXlsReader.process(file, fileReadStep);
            }else if (fileName.endsWith(excel07Extention)) {
                ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader(nationStandardItemService);
                excelXlsxReader.setUserId("userId");
                excelXlsxReader.setUserName("userName");
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