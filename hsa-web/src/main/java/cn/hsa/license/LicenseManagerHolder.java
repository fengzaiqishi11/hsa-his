package cn.hsa.license;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

import java.util.Date;

/**
 * LicenseManager的单例
 *
 * @author Black
 * @date: 2019年10月16日
 * @since 1.0.0
 */
public class LicenseManagerHolder {

    private static volatile LicenseManager LICENSE_MANAGER;

    public static LicenseManager getInstance(LicenseParam param){
        if(LICENSE_MANAGER == null){
            synchronized (LicenseManagerHolder.class){
                if(LICENSE_MANAGER == null){
                    LICENSE_MANAGER = new CustomLicenseManager(param);
                }
            }
        }

        return LICENSE_MANAGER;
    }

    private static volatile Date notAfter;

    public static Date getDate(Date date) {
        if (notAfter == null) {
            synchronized (LicenseManagerHolder.class){
                if(notAfter == null){
                    notAfter = date;
                }
            }
        }
        return notAfter;
    }
}
