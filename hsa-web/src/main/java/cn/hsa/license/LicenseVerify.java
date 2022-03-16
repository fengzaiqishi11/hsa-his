package cn.hsa.license;

import cn.hsa.util.DateUtils;
import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

/**
 * License校验类
 *
 * @author Black
 * @date: 2019年10月15日
 * @since 1.0.0
 */
public class LicenseVerify {
	private Logger logger = LoggerFactory.getLogger("upay_client_start");
	//全局证书验证标准，用于校验证书是否有效，定时器一天执行一次verify()方法
	public static int licenseValid = 0;

    //全局证书过期有效时间
    public static int licenseValidTime = 7;
    /**
     * 安装License证书
     * @author Black
     * @date: 2019年10月15日 16:26
     * @since 1.0.0
     */
    public synchronized LicenseContent install(LicenseVerifyParam param){
        LicenseContent result = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //1. 安装证书
        try{
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(initLicenseParam(param));
            licenseManager.uninstall();

            //1.判断文件是否存在，不存在，去服务下载，判断文件是否下载成果，下载失败抛异常
            File  file =  new File(param.getLicensePath());
            if (!file.exists()) {
                FileUtils.copyURLToFile(new URL(param.getDownloadPath()),file);
                if(!file.exists()){
                    logger.error("证书安装失败,证书文件不存在");
                    throw new RuntimeException("证书安装失败,证书文件不存在");
                }
            }
            try{
                result = licenseManager.install(file);
            }catch (Exception e){
                //如果是 过期或者时间不在有效期内 重新下载证书，再次注册
                if(e.getMessage().contains("exc.licenseIsNotYetValid") || e.getMessage().contains("exc.licenseHasExpired")){
                    FileUtils.copyURLToFile(new URL(param.getDownloadPath()),file);
                    result = licenseManager.install(file);
                }else{
                    logger.error("证书安装失败！",e);
                    throw new RuntimeException(e);
                }
            }

            logger.info(MessageFormat.format("证书安装成功，证书有效期：{0} - {1}",format.format(result.getNotBefore()),format.format(result.getNotAfter())));
            LicenseManagerHolder.getDate(result.getNotAfter());
        }catch (Exception e){
            logger.error("证书安装失败！",e);
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 校验License证书
     * @author Black
     * @date: 2019年10月15日 16:26
     * @since 1.0.0
     * @return boolean
     */
    public int verify(){
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //2. 校验证书
        try {
            LicenseContent licenseContent = licenseManager.verify();
            logger.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
            licenseValid = 0;
        }catch (Exception e){
            //如果是 过期,提醒十天后停止服务
            if(e.getMessage().contains("exc.licenseHasExpired")){
                Date notAfter = LicenseManagerHolder.getDate(null);
                int diff = DateUtils.differentDays(notAfter,DateUtils.getNow());
                if(diff < licenseValidTime){
                    logger.info(MessageFormat.format("证书已过期，证书有效期：{0} 止。您还可以临时使用 {1} 天，请及时更新证书。",format.format(notAfter), String.valueOf(licenseValidTime-diff)));
                    licenseValid = 1;
                }else if(diff == licenseValidTime){
                    licenseValid = 2;
                }else{
                    licenseValid = 9;
                }
            }else{
                //exc.licenseHasExpired:证书到期异常
                logger.error("证书校验失败！",e);
                licenseValid = 9;
            }
        }
        return licenseValid;
    }

    /**
     * 初始化证书生成参数
     * @author Black
     * @date: 2019年10月15日 10:56
     * @since 1.0.0
     * @param param License校验类需要的参数
     * @return LicenseParam
     */
    private LicenseParam initLicenseParam(LicenseVerifyParam param){
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class
                ,param.getPublicKeysStorePath()
                ,param.getPublicAlias()
                ,param.getStorePass()
                ,null);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,publicStoreParam
                ,cipherParam);
    }



}
