package cn.hsa.util;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 *@Package_name: cn.hsa.util
 *@Class_name: DownloadFileUtil
 *@Describe: 文件下载
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/11/25 11:31
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class DownloadFileUtil {


    /**
    * @Method downloadFile
    * @Desrciption 文件下载
    * @param sourPath 源路径
    * @param fileName 文件名
    * @param response
    * @Author liuqi1
    * @Date   2020/11/25 13:52
    * @Return void
    **/
    public static void downloadFile(String sourPath,String fileName, HttpServletResponse response){
        String filePath = sourPath + "\\" + fileName;

        downloadFile(filePath,response);
    }

    /**
    * @Method downloadFile
    * @Desrciption 文件下载
    * @param filePath 文件路径
    * @param response
    * @Author liuqi1
    * @Date   2020/11/25 13:47
    * @Return void
    **/
    public static void downloadFile(String filePath, HttpServletResponse response){
        InputStream in = null;
        OutputStream out = null;
        try {
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
            // 取得文件名。
            String filename = file.getName();

            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");

            //获取文件输入流
            in = new FileInputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                //将缓冲区的数据输出到客户端浏览器
                out.write(buffer,0,len);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            close(in);
            close(out);
        }
    }

    //关闭流
    private static void close(InputStream fis ){
        if(fis !=null ){
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //关闭流
    private static void close(OutputStream os ){
        if(os !=null ){
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
