package cn.hsa.util;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: UploadByExcel
 * @Describe: 导入，读取excel文件，返回数据集合
 * @Author: liuqi1
 * @Eamil: liuqi1@powersi.com.cn
 * @Date: 2020/11/23 15:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
public class ExcelXlsxReader {

   private  ExcelResolveService service;


   /** 存储调用步长，默认100条调用一次 **/
   private Integer storageStep = 100;

   /** 总行数 **/
   private int totalRows = 0;

   /** 默认读取的开始行数 **/
   private AtomicInteger  startRowNumber =new AtomicInteger();

   private String userId;
   private String userName;

   private final int MAX_ALLOWED_IMPORT_ROWS = 10000;

   public void setUserId(String userId) {
        this.userId = userId;
    }
   public void setUserName(String userName){
        this.userName = userName;
   }
    /**
       * @Description: 处理上传的数据
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 19:52
    **/
    public int process(MultipartFile file,Integer storageStep) throws Exception {
        this.storageStep = storageStep;
        readExcel(file,startRowNumber.get());
        return totalRows;
    }
    public int process(MultipartFile file) throws Exception {
        this.storageStep = storageStep;
        readExcel(file,startRowNumber.get());
        return totalRows;
    }
    public ExcelXlsxReader(ExcelResolveService service){
        this.service = service;
    }
    /**
     * @Method readExcel
     * @Desrciption 读取excel文件，返回数据集合
     * @param file 文件
     * @param startRowNum 从哪行开始读(用于确定一行有多少列)
     * @Author luonianxin
     * @Date   2021/05/24 17:22
     **/
    public  void readExcel(MultipartFile file,Integer startRowNum) throws Exception{
        //行数据集合
        List<List<String>> rowList = new CopyOnWriteArrayList<>();
        Workbook workbook = null;
        InputStream fis = null;
        try {
            fis = file.getInputStream();
            workbook = new XSSFWorkbook(fis);
            this.totalRows = getTotalRows(workbook);
            if(totalRows > MAX_ALLOWED_IMPORT_ROWS) {
                throw new AppException("数据量超出限制,单次最多只能导入1w条,请分批次进行导入！");
            }
            //读取excel数据存入rowList中
            int count = (int)Math.ceil((totalRows*1.0)/storageStep);
            for (int c = 0; c < count; c++) {
                doResolveData(rowList, workbook,startRowNumber.get());
            }

            if(!rowList.isEmpty()){
                Map<String,Object> map = new HashMap<>(8);
                map.put("crteName",userName);
                map.put("crteId",userId);
                map.put("resultList",rowList);
                service.insertProcessedUploadData(map);
            }
        }catch (IOException ioe){
            log.error("解析文件出粗",ioe);
            throw new AppException("解析Excel文件发生错误，请联系管理员",ioe);
        }catch (AppException ae){
            log.error("解析文件出粗",ae);
            throw ae;
        } catch (Exception e) {
            log.error("解析文件出粗",e);
            throw new AppException("解析Excel文件发生错误，请联系管理员");
        }finally {
            close(fis);
        }
    }


    private int getTotalRows(Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        return sheet.getLastRowNum();
    }

    private  void doResolveData(List<List<String>> rowList, Workbook workbook,Integer startRowNum) {
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
                //这里不需要加一
                for (int k = 0; k < lastCellNum; k++) {
                    Cell cell = row.getCell(k);
                    String cellValue = getCellValue(cell);
                    cellList.add(cellValue);
                }

                rowList.add(cellList);
                int rn = startRowNumber.incrementAndGet();
                if((rn % storageStep) == 0){
                   Map<String,Object> map = new HashMap<>(8);
                   map.put("crteName",userName);
                   map.put("crteId",userId);
                   map.put("resultList",rowList);
                   service.insertProcessedUploadData(map);
                   log.error("执行rowList.clear().{} -------",rowList.size());
                    rowList.clear();
                   break;
                }
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
