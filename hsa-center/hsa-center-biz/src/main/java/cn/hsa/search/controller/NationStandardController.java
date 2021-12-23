package cn.hsa.search.controller;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandarddrug.dao.NationStandardDrugDAO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.search.service.NationStandardDrugService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("search")
public class NationStandardController {

    @Resource
    private NationStandardDrugDAO nationStandardDrugDAO;
    @Resource
    private NationStandardDrugService searchableDrugService;



    @GetMapping("/initData")
    public String init() {

        return "total rows: "+ searchableDrugService.refreshDataOfElasticSearch();
    }

    @GetMapping("/queryByCond")
    public PageDTO findAll(@RequestParam("keyword")String keyword,@RequestParam(value = "provinceCode",required = false) String provinceCode){
        NationStandardDrugDTO param = new NationStandardDrugDTO();
        param.setKeyword(keyword);
        param.setProvinceCode(provinceCode);
            return searchableDrugService.searchByConditions(param);
    }


    @GetMapping("/one")
    public Object findOne(){
        NationStandardDrugDTO param = new NationStandardDrugDTO();
        param.setKeyword("sfadsf");
        return searchableDrugService.findById("aFbD230BFn6KF7vMZk4n");
    }

    @PostMapping("/update")
    public Object updateDocument(NationStandardDrugDTO updatedDocument){
        NationStandardDrugDTO param = new NationStandardDrugDTO();
        param.setKeyword("sfadsf");
        return searchableDrugService.save(updatedDocument);
    }


    @GetMapping("/getCount")
    public Object getCount(){
        return searchableDrugService.count();
    }
}
