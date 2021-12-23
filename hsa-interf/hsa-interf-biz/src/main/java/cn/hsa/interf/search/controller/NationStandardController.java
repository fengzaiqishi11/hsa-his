package cn.hsa.interf.search.controller;

import cn.hsa.base.PageDTO;
import cn.hsa.interf.search.service.NationStandardDrugService;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
