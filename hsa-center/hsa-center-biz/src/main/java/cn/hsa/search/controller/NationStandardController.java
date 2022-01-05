package cn.hsa.search.controller;

import cn.hsa.search.service.NationStandardDrugService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/center/search")
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
