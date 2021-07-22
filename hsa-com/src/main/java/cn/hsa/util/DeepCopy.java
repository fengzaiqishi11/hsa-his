package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

/**
 *@Package_name: cn.hsa.util
 *@Class_name: DeepCopy
 *@Describe: 深度复制
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/7 14:50
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
public class DeepCopy {

    /**
    * @Method deepCopy
    * @Desrciption 深度复制
    * @param src
    * @Author liuqi1
    * @Date   2020/9/7 14:52
    * @Return java.util.List<T>
    **/
    public static <T> List<T> deepCopy(List<T> src) {
        try {
            //写入字节流
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);

            //返回生成的新对象
            List<T> dest = (List<T>) in.readObject();
            return dest;
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new AppException(e.getMessage());
        }

    }

    /**
    * @Method deepCopy
    * @Desrciption  深度复制
    * @param src
    * @Author liuqi1
    * @Date   2020/9/7 14:59
    * @Return T
    **/
    public static <T> T deepCopy(T src) {
        try {
            //写入字节流
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);

            //分配内存，写入原始对象，生成新对象
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);

            //返回生成的新对象
            T dest = (T) in.readObject();
            return dest;
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new AppException(e.getMessage());
        }

    }

}
