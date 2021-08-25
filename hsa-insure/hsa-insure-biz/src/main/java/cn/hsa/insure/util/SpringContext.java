package cn.hsa.insure.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: SpringContext
 * @Describe(描述): 获取Spring base实例
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/10 15:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    
    /**
     * @Menthod setApplicationContext
     * @Desrciption  实现ApplicationContextAware接口的回调方法，设置Spring上下文环境
     * @param applicationContext 环境实例
     * @Author Ou·Mr
     * @Date 2020/11/10 15:52 
     * @Return void
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * @Menthod getApplicationContext
     * @Desrciption  获得spring上下文环境
     * @Author Ou·Mr
     * @Date 2020/11/10 15:53 
     * @Return org.springframework.context.ApplicationContext 上下文环境实例
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * @Menthod getBean
     * @Desrciption  根据名称获取bean实例
     * @param name 实例名称
     * @Author Ou·Mr
     * @Date 2020/11/10 15:54
     * @Return java.lang.Object bean的实例对象
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
}
