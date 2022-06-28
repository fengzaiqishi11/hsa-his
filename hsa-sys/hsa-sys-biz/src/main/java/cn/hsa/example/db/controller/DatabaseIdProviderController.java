//package cn.hsa.example.db.controller;
//
//import cn.hsa.example.db.service.DatabaseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 测试数据库兼容性方案
// * <p>
// * 通过mybatis的DatabaseIdProvider方案，通过在Mybatis的xml文件中，使用不同的databaseId标签，区分不同的数据库
// */
//@RestController
//@RequestMapping("/web/db")
//public class DatabaseIdProviderController {
//
//    @Autowired
//    private DatabaseService databaseService;
//
//    @GetMapping("/exists")
//    public String selectTime() {
//        return databaseService.selectTime();
//    }
//}
