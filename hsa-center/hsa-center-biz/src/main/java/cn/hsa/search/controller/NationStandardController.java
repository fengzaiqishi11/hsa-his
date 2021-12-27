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
    private NationStandardDrugService searchableDrugService;



    @GetMapping("/initWesternDrugData")
    public String initWesternDrugData() {

        return "total rows: "+ searchableDrugService.refreshWesternDrugDataOfElasticSearch();
    }
    @GetMapping("/initTCMData")
    public String initTCMData() {

        return "total rows: "+ searchableDrugService.refreshTCMDrugDataOfElasticSearch();
    }


    @GetMapping("/getCount")
    public Object getCount(@RequestParam(name="flag") int flag){
        return searchableDrugService.dataCountOfElasticSearch(flag);
    }
}
