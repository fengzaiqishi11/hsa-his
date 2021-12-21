package cn.hsa.search.controller;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandarddrug.dao.NationStandardDrugDAO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.search.service.NationStandardDrugService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
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
        NationStandardDrugDTO param = new NationStandardDrugDTO();
        PageHelper.startPage(1,10);
        List<NationStandardDrugDTO>list = nationStandardDrugDAO.queryNationStandardDrugPage(param);
        PageDTO pageDTO = PageDTO.of(list);
        long total = pageDTO.getTotal();
        int totalPages = (int) Math.ceil(total/200);

        for(int i=1;i<totalPages;i++){
            NationStandardDrugDTO p = new NationStandardDrugDTO();
            PageHelper.startPage(i+1,200);
            list = nationStandardDrugDAO.queryNationStandardDrugPage(p);
            searchableDrugService.saveAll(list);
        }
        return "total rows: "+ total;
    }

    @GetMapping("/queryByCond")
    public PageDTO findAll(@RequestParam("keyword")String keyword,@RequestParam(value = "provinceCode",required = false) String provinceCode){
        NationStandardDrugDTO param = new NationStandardDrugDTO();
        param.setKeyword(keyword);
        param.setProvinceCode(provinceCode);
            return searchableDrugService.searchByConditions(param);
    }

    @GetMapping("/all")
    public Iterator<NationStandardDrugDO> findAll2(){
        NationStandardDrugDTO param = new NationStandardDrugDTO();
        param.setKeyword("sfadsf");
        return searchableDrugService.findAll().iterator();
    }

    @GetMapping("/one")
    public Object findOne(){
        NationStandardDrugDTO param = new NationStandardDrugDTO();
        param.setKeyword("sfadsf");
        return searchableDrugService.findById("1391000864816771072");
    }
}
