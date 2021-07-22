package cn.hsa.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author powersi
 */
@Data
@Slf4j
public class ExcelXlsReader implements HSSFListener {

    private int minColums = -1;

    private POIFSFileSystem fs;

    /**
      *  数据保存服务
    **/
    private ExcelResolveService processor;

    /**
     * 总行数
     */
    private int totalRows=0;

    /**
     * 上一行row的序号
     */
    private int lastRowNumber;

    /**
     * 上一单元格的序号
     */
    private int lastColumnNumber;

    /**
     * 是否输出formula，还是它对应的值
     */
    private boolean outputFormulaValues = true;

    /**
     * 用于转换formulas
     */
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;

    /**
     * excel2003工作簿
     * **/
    private HSSFWorkbook stubWorkbook;

    private SSTRecord sstRecord;

    private FormatTrackingHSSFListener formatListener;

    private final HSSFDataFormatter formatter = new HSSFDataFormatter();

    /**
     * 文件的绝对路径
     */
    private String filePath = "";

    //表索引
    private int sheetIndex = 0;

    private BoundSheetRecord[] orderedBSRs;

    @SuppressWarnings("unchecked")
    private ArrayList boundSheetRecords = new ArrayList();

    private int nextRow;

    private int nextColumn;

    private boolean outputNextStringRecord;

    //当前行
    private int curRow = 0;

    /** 存储一行记录所有单元格的容器**/
    private List<String> cellList = new ArrayList<String>();


    /** 存储触发阈值默认为100 **/
    private Integer storageStep = 100;

    /** 存储多行数据的容器 **/
    private List<List<String>> resolvedRowDataList = new ArrayList<List<String>>(storageStep);

    /** 用户名 **/
    private String userName;
    /** 用户id **/
    private String userId;


    /**
     * 判断整行是否为空行的标记
     */
    private boolean flag = false;

    @SuppressWarnings("unused")
    private String sheetName;

    public ExcelXlsReader(ExcelResolveService service) {
        this.processor = service;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }


    /**
       * @Description: 根据传入的步长创建容器并处理数据
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 16:52
    **/
    public int process(MultipartFile file,Integer storageStep) throws Exception {
        resolvedRowDataList = new ArrayList<List<String>>(storageStep);
        this.setStorageStep(storageStep);
        return process(file.getInputStream());
    }
    /**
     * 只遍历excel第一个sheet
     *
     * @param inputStream 上传的文件输入流
     * @throws Exception
     */
    public int process(InputStream inputStream) throws Exception {
        this.fs = new POIFSFileSystem(inputStream);
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
        formatListener = new FormatTrackingHSSFListener(listener);
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        if (outputFormulaValues) {
            request.addListenerForAllRecords(formatListener);
        } else {
            workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(formatListener);
            request.addListenerForAllRecords(workbookBuildingListener);
        }
        factory.processWorkbookEvents(request, fs);
        //返回该excel文件的总行数，不包括首列和空行
        return totalRows;
    }

    /**
     * HSSFListener 监听方法，处理Record
     * 处理每个单元格
     * @param record
     */
    @SuppressWarnings("unchecked")
    @Override
    public void processRecord(Record record) {
        int thisRow = -1;
        int thisColumn = -1;
        String thisStr = null;
        String value = null;
        switch (record.getSid()) {
            case BoundSheetRecord.sid:
                boundSheetRecords.add(record);
                break;
            case BOFRecord.sid: //开始处理每个sheet
                BOFRecord br = (BOFRecord) record;
                if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
                    //如果有需要，则建立子工作簿
                    if (workbookBuildingListener != null && stubWorkbook == null) {
                        stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
                    }

                    if (orderedBSRs == null) {
                        orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
                    }
                    sheetName = orderedBSRs[sheetIndex].getSheetname();
                    sheetIndex++;
                }
                break;
            case SSTRecord.sid:
                sstRecord = (SSTRecord) record;
                break;
            case BlankRecord.sid: //单元格为空白
                BlankRecord brec = (BlankRecord) record;
                thisRow = brec.getRow();
                thisColumn = brec.getColumn();
                thisStr = "";
                cellList.add(thisColumn, thisStr);
                break;
            // 单元格为布尔类型
            case BoolErrRecord.sid:
                BoolErrRecord berec = (BoolErrRecord) record;
                thisRow = berec.getRow();
                thisColumn = berec.getColumn();
                thisStr = berec.getBooleanValue() + "";
                cellList.add(thisColumn, thisStr);
                // 如果里面某个单元格含有值，则标识该行不为空行
                checkRowIsNull(thisStr);
                break;
            // 单元格为公式类型
            case FormulaRecord.sid:
                FormulaRecord frec = (FormulaRecord) record;
                thisRow = frec.getRow();
                thisColumn = frec.getColumn();
                if (outputFormulaValues) {
                    if (Double.isNaN(frec.getValue())) {
                        outputNextStringRecord = true;
                        nextRow = frec.getRow();
                        nextColumn = frec.getColumn();
                    } else {
                        thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
                    }
                } else {
                    thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
                }
                cellList.add(thisColumn, thisStr);
                //如果里面某个单元格含有值，则标识该行不为空行
                checkRowIsNull(thisStr);
                break;
            //单元格中公式的字符串
            case StringRecord.sid:
                if (outputNextStringRecord) {
                    StringRecord srec = (StringRecord) record;
                    thisStr = srec.getString();
                    thisRow = nextRow;
                    thisColumn = nextColumn;
                    outputNextStringRecord = false;
                }
                break;
            case LabelRecord.sid:
                LabelRecord lrec = (LabelRecord) record;
                curRow = thisRow = lrec.getRow();
                thisColumn = lrec.getColumn();
                value = lrec.getValue().trim();
                value = value.equals("") ? "" : value;
                cellList.add(thisColumn, value);
                checkRowIsNull(value);  //如果里面某个单元格含有值，则标识该行不为空行
                break;
            case LabelSSTRecord.sid: //单元格为字符串类型
                LabelSSTRecord lsrec = (LabelSSTRecord) record;
                curRow = thisRow = lsrec.getRow();
                thisColumn = lsrec.getColumn();
                if (sstRecord == null) {
                    cellList.add(thisColumn, "");
                } else {
                    value = sstRecord.getString(lsrec.getSSTIndex()).toString().trim();
                    value = value.equals("") ? "" : value;
                    cellList.add(thisColumn, value);
                    checkRowIsNull(value);  //如果里面某个单元格含有值，则标识该行不为空行
                }
                break;
            case NumberRecord.sid: //单元格为数字类型
                NumberRecord numrec = (NumberRecord) record;
                curRow = thisRow = numrec.getRow();
                thisColumn = numrec.getColumn();

                //第一种方式
                //value = formatListener.formatNumberDateCell(numrec).trim();//这个被写死，采用的m/d/yy h:mm格式，不符合要求

                //第二种方式，参照formatNumberDateCell里面的实现方法编写
                Double valueDouble=((NumberRecord)numrec).getValue();
                String formatString=formatListener.getFormatString(numrec);
                if (formatString.contains("m/d/yy")){
                    formatString="yyyy-MM-dd hh:mm:ss";
                }
                int formatIndex=formatListener.getFormatIndex(numrec);
                value=formatter.formatRawCellContents(valueDouble, formatIndex, formatString).trim();

                value = value.equals("") ? "" : value;
                //向容器加入列值
                cellList.add(thisColumn, value);
                //如果里面某个单元格含有值，则标识该行不为空行
                checkRowIsNull(value);
                break;
            default:
                break;
        }

        //遇到新行的操作
        if (thisRow != -1 && thisRow != lastRowNumber) {
            lastColumnNumber = -1;
        }

        //空值的操作
        if (record instanceof MissingCellDummyRecord) {
            MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
            curRow = thisRow = mc.getRow();
            thisColumn = mc.getColumn();
            cellList.add(thisColumn, "");
        }

        //更新行和列的值
        if(thisRow > -1) {
            lastRowNumber = thisRow;
        }
        if(thisColumn > -1) {
            lastColumnNumber = thisColumn;
        }

        //行结束时的操作
        if (record instanceof LastCellOfRowDummyRecord) {
            if (minColums > 0) {
                //列值重新置空
                if (lastColumnNumber == -1) {
                    lastColumnNumber = 0;
                }
            }
            lastColumnNumber = -1;
            //该行不为空行且该行不是第一行，发送（第一行为列名，不需要）
            if (flag&&curRow!=0) {
                // 保存一行数据到容器
                resolvedRowDataList.add(cellList);
                totalRows++;
            }
            //清空容器
            cellList = new ArrayList<>();
            flag=false;
        }
        if ((curRow % storageStep) == 0 ){
            // 到达保存条件时 触发调用保存服务
            Long t = System.currentTimeMillis();
            log.debug("达到保存值 {} 开始保存数据",t);
            if((curRow >= totalRows) && !resolvedRowDataList.isEmpty()){
                Map<String,Object> map = new HashMap<>(8);
                map.put("crteName",userName);
                map.put("crteId",userId);
                map.put("resultList",resolvedRowDataList);
                processor.insertProcessedUploadData(map);
                log.debug("数据保存完毕，共耗时 {}，ms",System.currentTimeMillis() - t);
                resolvedRowDataList.clear();
            }
        }
        if((curRow >= totalRows) && !resolvedRowDataList.isEmpty()){
            Long t = System.currentTimeMillis();
            Map<String,Object> map = new HashMap<>(8);
            map.put("crteName",userName);
            map.put("crteId",userId);
            map.put("resultList",resolvedRowDataList);
            processor.insertProcessedUploadData(map);
            log.debug("数据保存完毕，共耗时 {}，ms",System.currentTimeMillis() - t);
            resolvedRowDataList.clear();
        }
    }

    /**
     * 如果里面某个单元格含有值，则标识该行不为空行
     * @param value 单元格的值
     */
    public void checkRowIsNull(String value){
        if (value != null && !"".equals(value)) {
            flag = true;
        }
    }
}