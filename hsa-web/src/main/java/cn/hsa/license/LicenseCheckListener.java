package cn.hsa.license;

import lombok.SneakyThrows;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;

/**
 * 
 * @ClassName: LicenseCheckListener 
 * @Description: 在项目启动时安装证书
 * @author: Black
 * @date: 2019年10月22日 下午2:32:02
 */
@Component
public class LicenseCheckListener implements ApplicationListener<ContextRefreshedEvent> {
	private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 证书subject
     */
    @Value("${license.subject}")
    private String subject;

    /**
     * 公钥别称
     */
    @Value("${license.publicAlias}")
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    @Value("${license.storePass}")
    private String storePass;

    /**
     * 证书生成路径
     */
    @Value("${license.licensePath}")
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    @Value("${license.publicKeysStorePath}")
    private String publicKeysStorePath;

    /**
     * 证书subject
     */
    @Value("${license.downloadPath}")
    private String downloadPath;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //root application context 没有parent
        if(StringUtils.isNotBlank(licensePath)){
            logger.info("类加载的根路径:"+this.getClass().getResource("/").getPath());
            logger.info("++++++++ 开始安装证书 ++++++++");

            LicenseVerifyParam param = getParam();
            LicenseVerify licenseVerify = new LicenseVerify();
            //安装证书
//            licenseVerify.install(param);

            logger.info("++++++++ 证书安装结束 ++++++++");
        } else {
             throw new RuntimeException("++++++++ 证书配置错误 ++++++++");
        }

    }

    public LicenseVerifyParam getParam() throws MalformedURLException {
        LicenseVerifyParam param = new LicenseVerifyParam();
        //证书subject
        param.setSubject(subject);
        //公钥别称
        param.setPublicAlias(publicAlias);
        //访问公钥库的密码
        param.setStorePass(storePass);
        //证书生成路径
        param.setLicensePath(licensePath);
        //密钥库存储路径
        param.setPublicKeysStorePath(publicKeysStorePath);
        param.setDownloadPath(downloadPath);
        return param;
    }
}
