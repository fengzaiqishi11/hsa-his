package cn.hsa.search.controller;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.search.service.NationStandardDrugService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("search")
public class NationStandardController {

    @Resource
    private NationStandardDrugService searchableDrugService;


    @PostMapping("/queryByCond")
    public PageDTO getCount(@RequestBody NationStandardDrugDTO searchParams){
        return searchableDrugService.searchByConditions(searchParams);
    }
}
