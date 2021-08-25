package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: FileUtils
 * @Description:
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/09/02 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class FileUtils {
    /**
     * @Method 上传文件，根据上传File对象
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/9/2 15:40
     * @Return
     **/
    public static String uploadFile(MultipartFile file, String rootPath, String hospCode) {
        try {
            String dirSuffix = hospCode + "/" + DateUtils.format(DateUtils.YMD);
            rootPath += "/" + dirSuffix;
            File rootFile = new File(rootPath);
            if (!rootFile.exists() || !rootFile.isDirectory()) {
                rootFile.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            writeFile(file.getInputStream(), rootPath + "/" + fileName);
            return dirSuffix + "/" + fileName;
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }

    /**
     * @Method 写入文件
     * @Description 
     * 
     * @Param 
     * 
     * @Author zhongming
     * @Date 2020/12/7 17:44
     * @Return 
     **/
    private static void writeFile(InputStream fis, String filePath) {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 1024];
            int byteread = 0;
            while ((byteread = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, byteread);
            }
        } catch(Exception e) {
            throw new AppException(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                }
                fos = null;
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {

                }
                fis = null;
            }
        }
    }

    /**
     * @Method: readFile()
     * @Description: 读取下载回来的文件
     * @Param: filePathName：文件路径
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2021/1/14 15:05
     * @Return: 读取文件返回的字符串
     */
    public static String readFile(String filePathName) {
        if(StringUtils.isEmpty(filePathName)){
            throw new AppException("读取文件路径为空");
        }
        FileInputStream fileReader =null;
        BufferedReader bufferedReader =null;
        String readStr ="";
        String hasRead="";
        try {
            fileReader =new FileInputStream(filePathName);
            bufferedReader = new BufferedReader(new InputStreamReader(fileReader,"GBK"));
            StringBuilder stringBuilder = new StringBuilder();
            while ((hasRead=bufferedReader.readLine()) !=null){
                readStr = stringBuilder.append(hasRead+"\n").toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return readStr;
    }

}
