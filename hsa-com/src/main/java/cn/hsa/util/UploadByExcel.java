package cn.hsa.util;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: UploadByExcel
 * @Describe: 导入，读取excel文件，返回数据集合
 * @Author: liuqi1
 * @Eamil: liuqi1@powersi.com.cn
 * @Date: 2020/11/23 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class UploadByExcel {

    /**
    * @Method readExcel
    * @Desrciption 读取excel文件，返回数据集合
    * @param file 文件
    * @Author liuqi1
    * @Date   2020/11/24 9:49
    * @Return java.util.Map<java.lang.String,java.lang.Object>
     *     isSuccess:ture or false(成功 或 失败)  boolean
     *     errorMessage:失败时返回的错误信息  String
     *     resultList:成功时返回数据集合 List<List<String>>
    **/
    public static Map<String, Object> readExcel(MultipartFile file){
        Map<String, Object> rsultMap = readExcel(file,null);
        return rsultMap;
    }

    /**
    * @Method readExcel
    * @Desrciption 读取excel文件，返回数据集合
    * @param file 文件
    * @param startRowNum 从哪行开始读(用于确定一行有多少列)
    * @Author liuqi1
    * @Date   2020/11/24 17:22
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    *     isSuccess:ture or false(成功 或 失败)  boolean
    *     errorMessage:失败时返回的错误信息  String
    *     resultList:成功时返回数据集合 List<List<String>>
    **/
    public static Map<String, Object> readExcel(MultipartFile file,Integer startRowNum){
        //结果
        Map<String, Object> rsultMap = new HashMap<>();
        //行数据集合
        List<List<String>> rowList = new ArrayList<>();

        String errorMessage = "";//错误提示
        Workbook workbook = null;
        String fileName = file.getOriginalFilename();

        //excel文件校验
        InputStream fis = null;
        try {
            fis = file.getInputStream();
            if (fileName.endsWith("XLS") || fileName.endsWith("xls") || fileName.endsWith("XLSX") || fileName.endsWith("xlsx")) {
                //文件大小超过5M
                long size = file.getSize();
                if(size > 5242880){
                    errorMessage = "资源解析失败,导入文件超过5M";
                }else{
                    if (fileName.endsWith("XLS") || fileName.endsWith("xls")){
                        workbook = new HSSFWorkbook(fis);
                    }else {
                        workbook = new XSSFWorkbook(fis);
                    }

                }

            }else{
                errorMessage = "文件格式不对,请选择xls或xlsx格式";
            }
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage = "读取Excel文件异常";
        }finally {
            close(fis);
        }

        //如果校验失败
        if(StringUtils.isNotEmpty(errorMessage)){
            rsultMap.put("isSuccess",false);
            rsultMap.put("resultMessage",errorMessage);
            return rsultMap;
        }

        try {
            //读取excel数据存入rowList中
            readExcelData(rowList, workbook,startRowNum);

            rsultMap.put("isSuccess",true);
            rsultMap.put("resultList",rowList);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(fis);
        }
        return rsultMap;
    }

    /**
     * @Method readExcel
     * @Desrciption 读取excel文件，返回数据集合
     * @param filePath 文件地址
     * @Author liuqi1
     * @Date   2020/11/24 9:49
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     *     isSuccess:ture or false(成功 或 失败)  boolean
     *     errorMessage:失败时返回的错误信息  String
     *     resultList:成功时返回数据集合 List<List<String>>
     **/
    public static Map<String, Object> readExcel(String filePath){
        Map<String, Object> rsultMap = readExcel(filePath,null);
        return rsultMap;
    }

    /**
    * @Method readExcel
    * @Desrciption 读取excel文件，返回数据集合
    * @param filePath 文件地址
    * @param startRowNum 从哪行开始读(用于确定一行有多少列)
    * @Author liuqi1
    * @Date   2020/11/24 17:25
    * @Return java.util.Map<java.lang.String,java.lang.Object>
     *     isSuccess:ture or false(成功 或 失败)  boolean
     *     errorMessage:失败时返回的错误信息  String
     *     resultList:成功时返回数据集合 List<List<String>>
    **/
    public static Map<String, Object> readExcel(String filePath,Integer startRowNum){
        Map<String, Object> rsultMap = new HashMap<>();

        FileInputStream input = null;
        try {
            File file = new File(filePath);

            input = new FileInputStream(file);
            //file转MultipartFile
            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
            //读取excel文件，返回数据集合
            rsultMap = readExcel(multipartFile,startRowNum);
        } catch (Exception e) {
            e.printStackTrace();
            rsultMap.put("resultMessage","读取Excel文件异常");
            rsultMap.put("isSuccess",false);
        }finally {
             close(input);
        }

        return rsultMap;
    }

    /**
    * @Method readExcelData
    * @Desrciption 读取excel数据存入rowList中
    * @param rowList 数据集合
    * @param workbook
    * @param startRowNum 从哪一行开始读
    * @Author liuqi1
    * @Date   2020/11/24 9:38
    * @Return void
    **/
    private static void readExcelData(List<List<String>> rowList, Workbook workbook,Integer startRowNum) {
        //列数据集合
        List<String> cellList;
//        //获取sheet页数
//        int sheetNum = workbook.getNumberOfSheets();
        // 只读第一页
        int sheetNum = 1 ;

        //从哪一行开始读,默认从0开始
        if(startRowNum == null || startRowNum < 0){
            startRowNum = 0;
        }
        for(int i = 0;i<sheetNum;i++){
            //读取一个sheet
            Sheet sheet = workbook.getSheetAt(i);
            //获取最后一行,总共有多少行数据
            int lastRowNum = sheet.getLastRowNum();
            //总共有多少列
            int lastCellNum = 0;

            //这里需要加一
            for(int l = startRowNum;l < lastRowNum + 1;l++){
                //读取一行
                Row row = (Row) sheet.getRow(l);
                if(row == null){
                    continue;
                }
                //列数据集合
                cellList = new ArrayList<>();

                if(l == startRowNum ){
                    //用第一行(表头)确定列数
                    lastCellNum = row.getLastCellNum();
                    continue;
                }
                int emptyCellCount = 0;
                //这里不需要加一
                for (int k = 0; k < lastCellNum; k++) {
                    Cell cell = row.getCell(k);
                    String cellValue = getCellValue(cell);
                    cellList.add(cellValue);
                    if(StringUtils.isEmpty(cellValue)){
                        emptyCellCount++;
                    }
                }
                // 判断是否整行都为空，如果是，则跳过此次添加操作
                if (emptyCellCount == cellList.size()) {
                    continue;
                }
                rowList.add(cellList);
            }
        }
    }


    /**
    * @Method getCellValue
    * @Desrciption 获取cell内容
    * @param cell
    * @Author liuqi1
    * @Date   2020/11/24 9:45
    * @Return java.lang.String
    **/
    private static String getCellValue(Cell cell) {
        String value = "";
        if(cell != null) {
            //以下是判断数据的类型
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC://数字
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if(date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        }else {
                            value = "";
                        }
                    }else {
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        // 不显示千位分割符，否则显示结果会变成类似1,234,567,890
                        numberFormat.setGroupingUsed(false);
                        value = numberFormat.format(cell.getNumericCellValue());
                        //value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: //字符串
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: //boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: //公式
                    switch (cell.getCachedFormulaResultType()){
                        case HSSFCell.CELL_TYPE_NUMERIC://数字
                            value = cell.getNumericCellValue() + "";
                            if(HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                if(date != null) {
                                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                }else {
                                    value = "";
                                }
                            }else {
                                value = new DecimalFormat("0.00").format(cell.getNumericCellValue());
                            }
                            break;
                        case HSSFCell.CELL_TYPE_STRING: //字符串
                            value = cell.getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN: //boolean
                            value = cell.getBooleanCellValue() + "";
                            break;
                        case HSSFCell.CELL_TYPE_BLANK: //空值
                            value = "";
                            break;
                        case HSSFCell.CELL_TYPE_ERROR: //故障
                            value = "非法字符";
                            break;
                        default:
                            value = "未知类型";
                            break;
                    }
                    break;
                case HSSFCell.CELL_TYPE_BLANK: //空值
                    value = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: //故障
                    value = "非法字符";
                    break;
                default:
                    value = "未知类型";
                    break;
            }
        }
        return value.trim();
    }

     //关闭流
     private static void close(InputStream in ){
            if(in !=null ){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
     }

    //关闭流
    private static void close(FileInputStream file ){
        if(file !=null ){
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
