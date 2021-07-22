package cn.hsa.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.tools.ant.util.DateUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
 
/**
 * 生成压缩文件 （zip，rar 格式）
 */
public class ZipWriterUtils {
 
    /**
     * 递归压缩文件
     * @param output ZipOutputStream 对象流
     * @param file 压缩的目标文件流
     * @param childPath 条目目录
     */
    private static void zip(ZipOutputStream output,File file,String childPath){
        FileInputStream input = null;
        try {
            // 文件为目录
            if (file.isDirectory()) {
                // 得到当前目录里面的文件列表
                File list[] = file.listFiles();
                childPath = childPath + (childPath.length() == 0 ? "" : "/")
                        + file.getName();
                // 循环递归压缩每个文件
                for (File f : list) {
                    zip(output, f, childPath);
                }
            } else {
                // 压缩文件
                childPath = (childPath.length() == 0 ? "" : childPath + "/")
                        + file.getName();
                output.putNextEntry(new ZipEntry(childPath));
                input = new FileInputStream(file);
                int readLen = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
                    output.write(buffer, 0, readLen);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 关闭流
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
 
    }
 
    /**
     * 压缩文件（文件夹）
     * @param url 目标文件流
     * @throws Exception
     */
    public static String zipFile(String url) throws Exception {
        String outputFileUrl = url+ DateUtils.format(new Date(),"yyyyMMddHHmmss")+".zip";
        // 输出流
        FileOutputStream outputStream = new FileOutputStream( outputFileUrl );
        // 压缩输出流
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
        zip(out, new File(url),"");
        out.flush();
        out.close();

        return outputFileUrl;
    }
 
}