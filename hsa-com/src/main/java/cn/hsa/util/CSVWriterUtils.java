package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 CSV文件生成utils
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-31  09:25
 */
public class CSVWriterUtils {
        // 写出csv文件
        public static final void writeCsv(List<LinkedHashMap<String, Object>> listMap, String url, String fileName) {
            StringBuffer csvBody = new StringBuffer();
            BufferedWriter csvWtriter = null;
            try {
                delFile(url);
                if (listMap == null || listMap.isEmpty()) {
                    throw  new AppException("生成CSV文件失败,未获取到数据!");
                }
                //获取文件头部集合
                List<String> csvHead = new ArrayList<String>(listMap.get(0).keySet());
                // 第一行列名
                csvBody.append(String.join(",", csvHead)).append("\n");

                String cellData = null;
                for (Map<String, Object> map : listMap) {
                    for (String head : csvHead) {
                        if (map.get(head) instanceof String) {
                            cellData = map.get(head) == null ? "" : (String)map.get(head);
                            csvBody.append(cellData.replaceAll(",", " ").replaceAll("\n"," ").replaceAll("\r\n"," ")).append(",");
                        } else {
                            csvBody.append(map.get(head) == null ? "" : map.get(head)).append(",");
                        }
                    }
                    csvBody.deleteCharAt(csvBody.length() - 1).append("\n");
                }

                // 写入指定目录
                File csvFile = new File(url + "/" + fileName+".csv");
                File parent = csvFile.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }

                String flu_csv_file_encoding = "GBK";
                csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), flu_csv_file_encoding), 1024);
                csvWtriter.write(csvBody.toString());
                csvWtriter.flush();
            } catch(Exception e) {
                throw new AppException(e.getMessage());
            } finally {
                if (csvWtriter != null) {
                    try {
                        csvWtriter.close();
                    } catch (IOException e) {
                    }
                }
            }
    }

    /**
     * 判断文件是否存在,不存在需要创建
     * @param file
     */
    public static void checkFileExists(File file) {
        //当文件不存在时,自动创建（先生成文件,再写数据）
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void delFile(String url) {
        // 获取昨天或更久的文件夹
        File[] files = new File(url).listFiles();

        if (files!=null) {
            // 删除昨天或更久的文件夹
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
    }
    // 递归删除文件
    private static void delteFile(File file) {
        if(file.isFile()){
            file.delete();
        }
        else{
            File[] filearray = file.listFiles();
            if (filearray != null) {
                for (File f : filearray) {
                    if (f.isDirectory()) {
                        delteFile(f);
                    } else {
                        f.delete();
                    }
                }
                file.delete();
            }
        }
    }
}
