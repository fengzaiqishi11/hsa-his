package cn.hsa.center.nationstandardmaterial.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;
import cn.hsa.module.center.nationstandardmaterial.service.NationStandardMaterialService;
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
 * @Describe: 国家标准材料Controller
 * @Author: xingyu.xie
 * @Email: oubo@powersi.com.cn
 * @Date: 2021/1/26 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/nationStandardMaterial")
public class NationStandardMaterialController extends CenterBaseController {


    @Resource
    private NationStandardMaterialService nationStandardMaterialService;

    /**
     * @Menthod getById
     * @Desrciption 根据id查询国家标准材料
     * @param nationStandardMaterialDTO  数据传输对象
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<NationStandardMaterialDTO> getById(NationStandardMaterialDTO nationStandardMaterialDTO){
        return nationStandardMaterialService.getById(nationStandardMaterialDTO);
    }


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardMaterialDTO 查询所有国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<NationStandardMaterialDTO>> queryAll(NationStandardMaterialDTO nationStandardMaterialDTO){
        return nationStandardMaterialService.queryAll(nationStandardMaterialDTO);
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardMaterialDTO 分页查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(NationStandardMaterialDTO nationStandardMaterialDTO){
        return nationStandardMaterialService.queryPage(nationStandardMaterialDTO);
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
                ExcelXlsReader excelXlsReader = new ExcelXlsReader(nationStandardMaterialService);
                excelXlsReader.setUserId(userId==null?"":userId);
                excelXlsReader.setUserName(userName==null?"":userName);
                rows = excelXlsReader.process(file, fileReadStep);
            }else if (fileName.endsWith(excel07Extention)) {
                ExcelXlsxReader excelXlsxReader = new ExcelXlsxReader(nationStandardMaterialService);
                excelXlsxReader.setUserId(userId ==null?"":userId);
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

    /**
       * @Describe: 保存或更新国家材料标准信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/6 17:51
    **/
    @PostMapping("/saveNationStandardMaterial")
    public WrapperResponse<Boolean> saveNationStandardMaterial(@RequestBody NationStandardMaterialDO nationStandardMaterialDO){
        Map map = new HashMap(4);
        nationStandardMaterialDO.setCrteId(userId);
        nationStandardMaterialDO.setCrteName(userName);
        map.put("nationStandardMaterialDO",nationStandardMaterialDO);
        return nationStandardMaterialService.saveNationStandardMaterial(map);
    }
}