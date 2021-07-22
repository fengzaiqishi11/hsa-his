package cn.hsa.insure.util;

import org.springframework.util.Base64Utils;

public class UUIDDesUtils {
    public UUIDDesUtils() {
    }

    public static String generatKey(String uuid) {
        String password = "phealth8";
        String subUuid = uuid.substring(uuid.length() - 7, uuid.length());
        String desStr = Base64Utils.encodeToString(DesUtils.encrypt(subUuid.getBytes(), password));
        return desStr;
    }
}
