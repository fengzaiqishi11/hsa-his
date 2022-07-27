package cn.hsa.insure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//

/**
 * @Package_ame: cn.hsa.base.config
 * @Class_name: SwaggerConfig
 * @Description: Api接口文档生成工具
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Configuration
//@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
public class SwaggerConfig extends WebMvcConfigurationSupport {

//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))//这是注意的代码
//                .apis(RequestHandlerSelectors.basePackage("cn.hsa"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("云HIS接口文档")
//                .description("云HIS接口文档")
//                .termsOfServiceUrl("https://www.powersi.com/")
//                .version("2.0")
//                .build();
//    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
