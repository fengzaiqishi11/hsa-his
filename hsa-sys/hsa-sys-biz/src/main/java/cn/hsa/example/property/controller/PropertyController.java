//package cn.hsa.example.property.controller;
//
//import cn.hsa.hsaf.core.framework.web.WrapperResponse;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 配置热更新
// *
// * 通过在Class上添加@RefreshScope注解，以及在需要动态修改的属性上使用@Value注解引入，
// * 可以通过配置中心下发该配置的值并及时生效，而无需重启应用
// */
//@RestController
//@RequestMapping("/web/property")
//@RefreshScope
//public class PropertyController {
//
//    /**
//     * 通过结合@RefreshScope和@Value注解，定义可配置参数，可以在配置中心进行配置，通过配置推送实现热更新
//     */
//    @Value("${test.name:tao}")
//    private String name;
//
//    @GetMapping("/getProperty")
//    public WrapperResponse<String> getProperty() {
//        return WrapperResponse.success(name);
//    }
//}