package cn.hsa.sys.log.bo.impl;

import cn.hsa.module.sys.log.bo.HisLogInfoCzBO;
import cn.hsa.module.sys.log.dao.HisLogInfoCzDao;
import cn.hsa.module.sys.log.dto.HisLogInfoCzDTO;
import cn.hsa.module.sys.log.dto.HisLogInfoYcDTO;
import cn.hsa.module.sys.log.dto.LogFileDTO;
import cn.hsa.module.sys.log.dto.LogQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package_name:cn.hsa.module.sys.log.bo
 * @Class_name:hisLogInfoCz
 * @Project_name:hsa-his
 * @Describe: 操作日志BO
 * @Author: zibo.yuan
 * @Date: 2020/12/1 11:10
 * @Email: zibo.yuan@powersi.com.cn
 */
@Component
@Slf4j
public class HisLogInfoCzBOImpl implements HisLogInfoCzBO {
    @Resource
    HisLogInfoCzDao hisLogInfoCzDao;

    /***
     * @Description 新增正常日志
     * @param hisLogInfoCzDTO
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: java.lang.Boolean
     **/
    @Override
    public Boolean saveLogCz(HisLogInfoCzDTO hisLogInfoCzDTO) {
        if (hisLogInfoCzDTO != null) {
            return hisLogInfoCzDao.saveLogCz(hisLogInfoCzDTO) > 0;
        }
        return false;
    }

    /**
    * @Method queryLogInfoWithFile
    * @Desrciption 从日志文件中获得日志信息
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/10 15:21
    * @Return java.util.List<cn.hsa.module.sys.log.dto.HisLogInfoYcDTO>
    **/
    @Override
    public List<HisLogInfoYcDTO> queryLogInfoWithFile(HisLogInfoYcDTO hisLogInfoYcDTO) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //取得用户工作路径下的日志文件路径
        String fileUrl = System.getProperty("user.dir")+"/logs/";

        String queryDate = "";// 查询日期：查询哪天的日志
        if(hisLogInfoYcDTO.getQueryDate() != null){
            queryDate = sdf.format(hisLogInfoYcDTO.getQueryDate());
        }else{
            Date date = new Date();
            queryDate = sdf.format(date);
        }

        // 后缀
        String suffix = ".log";
        //取哪个系统的日志文件
        String systemName = hisLogInfoYcDTO.getQuerySystem() == null?"hsa-web":hisLogInfoYcDTO.getQuerySystem();
        //文件名称
        String fileName = systemName + "." + queryDate + suffix;
        //日志文件本地位置
        String filePath = fileUrl + systemName + "/" + fileName;
        //获取文件
        File file = new File(filePath);

        List<HisLogInfoYcDTO> rList = new ArrayList<>();
        HisLogInfoYcDTO dto = new HisLogInfoYcDTO();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String str = null;

            //逐行读取
            StringBuilder sb = new StringBuilder();
            while ((str=br.readLine())!=null){
                if(StringUtils.isEmpty(str)){
                    continue;
                }
                //取时间
                String logDate= null;

                if(str.indexOf(".")!=-1){
                    logDate= str.substring(0, str.indexOf("."));
                }

                if(!isDate(logDate)){
                    //如果没有日志时间，日志信息叠加
                    sb.append(str);
                    continue;
                }
                //为上个日志对象赋日志信息
                if(sb.length() > 0){
                    dto.setLogText(sb.toString());
                }

                sb = new StringBuilder();

                //取traceId
                Integer signLeftIndex = str.indexOf("[") + 1;
                Integer signRightIndex = str.indexOf("]");
                String systemAndTraceId = str.substring(signLeftIndex, signRightIndex);
                String traceId = "";
                if(StringUtils.isNotEmpty(systemAndTraceId)){
                    Integer i = systemAndTraceId.indexOf("-");
                    traceId = systemAndTraceId.substring(systemAndTraceId.indexOf("-",i+1) +1);
                }

                //取日志级别
                Integer i = str.indexOf("]");
                String subStr = str.substring(str.indexOf("]",i+1)+2);
                Integer blankIndex = subStr.indexOf(" ");
                String logLevel = subStr.substring(0, blankIndex);

                //取剩余其它信息
                String infoText = subStr.substring(blankIndex+1);
                sb.append(infoText);

                dto = new HisLogInfoYcDTO();
                dto.setLogDate(logDate);
                dto.setTraceId(traceId);
                dto.setLogLevel(logLevel);
                dto.setLogText(sb.toString());

                rList.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return rList;
    }

    /**
    * @Method isDate
    * @Desrciption 判断是否日期
    * @param date
    * @Author liuqi1
    * @Date   2020/12/11 15:15
    * @Return java.lang.Boolean
    **/
    private Boolean isDate(String date){
        if(StringUtils.isEmpty(date)){
            return false;
        }
        try{
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateformat.parse(date);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    /**
     * 读取指定路径下所有文件
     *
     * @param path
     *            文件路径
     * @param
     *
     * @return
     */
    public List<String> readFile(String path, String fileNameParam) {
        List<String> listFile = new ArrayList<String>();
        if (StringUtils.isBlank(path)) {
            return listFile;
        }
        File file = new File(path);
        // 获取指定路径下所有文件名
        String[] fileParam = file.list();
        //判断是否存在日志文件
        if (fileParam == null) {
            return listFile;
        }
        // 循环取出符合条件的文件名字，并放入集合
        for (int i = 0; i < fileParam.length; i++) {
            String[] str = fileParam[i].split("\\.");
            if (str.length > 2 && str[0].contains(fileNameParam)) {
                listFile.add(str[2]);
            }else if("allFile".equals(fileNameParam)){//读取全部文件
                listFile.add(fileParam[i]);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todayDate = sdf.format(date);
        listFile.add(todayDate);
        return listFile;
    }

//    /**
//     * 根据分页参数以及查询条件 获取日志信息
//     * @param logQueryPojo 查询条件
//     * @param page  分页参数
//     * @return
//     */
//    public Map<String, Object> logPaging(LogQueryDTO logQueryPojo, PageDTO page) {
//        Map<String, Object> logInfoMap = new HashMap<String, Object>();
//
//        List<LogFileDTO> retList = new ArrayList<LogFileDTO>();
//        try {
//            //根据条件读取日志文件
//            List<LogFileDTO> logRecordList=readAppointedLineNumber(logQueryPojo);
//
//            //赋值page对象
//            page.setTotalResult(logRecordList.size());
//
//            //根据page对象返回list
//            int endNumber = logRecordList.size() - page.getCurrentResult();
//            //没有足够的记录数，会出现负数
//            int differNumber = endNumber - page.getPageSize();
//            int startNumber = differNumber < 0 ? 0 : differNumber;
//
//            for(;startNumber < endNumber; startNumber ++) {
//                retList.add(logRecordList.get(startNumber));
//            }
//        } catch (Exception e) {
//            log.error("日志文件读取失败,错误信息为："+e.getMessage());
//        }
//
//        logInfoMap.put("logRecord", retList);
//        logInfoMap.put("page", page);
//
//        return logInfoMap;
//    }

    /**
     * 根据参数，读取相应的日志记录数
     *
     * @throws Exception
     */
    public List<LogFileDTO> readAppointedLineNumber(LogQueryDTO logQueryPojo) throws Exception {
        //根据不同的运行模式查询存储log4j日志的路径判断
        String filePath = "";
//        if ("center".equals(clientUtils.getRunMode())) {
//            filePath = "/app/logs/hygeia_client/" + clientUtils.getHospitalID() +"/";
//        }else if ("hospital".equals(clientUtils.getRunMode())) {
//            filePath = "c:\\hygeia_client\\logs\\";
//        }
        // 判断是否今天,并拼接出文件路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todayDate = sdf.format(date);

        String logFileCreateDate = logQueryPojo.getLogFileCreateDate();
        if (todayDate.equals(logFileCreateDate) || logFileCreateDate.equals("")) {
            logQueryPojo.setFilePath(filePath+logQueryPojo.getLogFileType()+".log");
        } else {
            logQueryPojo.setFilePath(filePath+logQueryPojo.getLogFileType()
                    + ".log." + logFileCreateDate);
        }

        //FileReader in = null;
        LineNumberReader reader = null;
        InputStreamReader in = null;
        try {
            List<LogFileDTO> list=new ArrayList<LogFileDTO>();
            try{
                //File sourceFile = new File(logQueryPojo.getFilePath());
                //in = new FileReader(sourceFile);
                in = new InputStreamReader(new FileInputStream(logQueryPojo.getFilePath()),"UTF-8");
            }catch(Exception e ){
                log.error(logQueryPojo.getFilePath()+"文件不存在！");
                return list;
            }
            reader = new LineNumberReader(in);

            String line = null;

            // 从开始行开始循环获取，并保存
            LogFileDTO logFilePojo = new LogFileDTO();
            boolean fileNullSign = true;
            while ((line = reader.readLine()) != null) {
                fileNullSign = false;
                //判断是否包含时间，如果包含就分割保存，不存在就只保存详细信息
                if(checkout(line)){
                    //判断pojo对象里面是否已经有值，其实就是防止添加一条空值
                    if( !StringUtils.isEmpty(logFilePojo.getLogDate()) ) {
                        //调用检测条件
                        if( checkCondition(logQueryPojo,logFilePojo) ) {
                            list.add(logFilePojo);
                        }
                        logFilePojo = new LogFileDTO();
                    }
                    String[] strArray=line.split("\\,");
                    logFilePojo.setLogDate(strArray[0]);
                    //截取日志级别 比如日志内容：2017-04-01 13:28:09,311 [INFO ][http-bio-8080-exec-1]
                    String loglevel = strArray[1].substring(4, 11);
                    //日志级别
                    logFilePojo.setLogRank(loglevel);

                    //防止与时间同行的日志信息中存在很多逗号 空格，导致丢失数据，不能有上面注释的方法写
                    int index = line.indexOf(loglevel)+loglevel.length();
                    String logInfo = line.substring(index, line.length());
                    if(index == line.length()) {
                        logFilePojo.setLogContent("");
                    }else {
                        logFilePojo.setLogContent(logInfo);
                    }

                }else{
                    String logInfo = logFilePojo.getLogContent() + line.toString();
                    //xml 显示进行处理
                    logInfo = logInfo.replace("<", "&lt;");
                    logInfo = logInfo.replace(">", "&gt;") +"&#10;";

                    logFilePojo.setLogContent(logInfo);
                }
            }
            //调用检测条件
            if(!fileNullSign && checkCondition(logQueryPojo,logFilePojo) ) {
                //将本次查询的最后一条记录添加进去
                list.add(logFilePojo);
            }
            return list;
        } catch (Exception e) {
            throw e;
        }finally{
            if (reader != null) {
                reader.close();
            }
            if (in != null) {
                in.close();
            }
        }

    }

    /**
     * 日志查询检测条件是否满足
     * @param logQueryPojo 日志查询条件
     * @param logFilePojo 日志信息
     * @return
     * boolean
     * true 符合
     */
    private boolean checkCondition(LogQueryDTO logQueryPojo, LogFileDTO logFilePojo) {
        /**
         * 因为读取日志文件现在是reader.readLine()这个方式读取，所有不管怎么样都会去读取完日志信息，不考虑性能问题
         * 在每次添加到返回的list的时候调用此方法进行效验
         */
        /*
         * 判断时间
         * 时间段查询只支持一天内的时间段，所以我们判断开始时间为00:00:00 结束时间为23:59:59结尾的 不进行效验
         */
        String startTime = logQueryPojo.getStartTime();
        String endTime = logQueryPojo.getEndTime();
        if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            if(!startTime.endsWith("00:00:00") || !endTime.endsWith("23:59:59")) {
                //判断是否在时间段内  这里都是string类型，不进行转换Date了，直接用string移除—:空格 转int比较大小
                long startTimeInt = Long.valueOf( startTime.replace("-", "").replace(":", "").replaceAll(" ", "").trim() );
                long endTimeInt = Long.valueOf( endTime.replace("-", "").replace(":", "").replaceAll(" ", "").trim() );
                long checkDateInt = Long.valueOf( logFilePojo.getLogDate().replace("-", "").replace(":", "").replaceAll(" ", "").trim() );
                //日志时间 大于开始时间或者大于结束时间 ，返回false
                if(startTimeInt >= checkDateInt || checkDateInt >= endTimeInt) {
                    return false;
                }
            }
        }

        //判断日志级别，日志级别一定存在值的不进行判断为空
        String logLevel = logQueryPojo.getLogLevel().trim();
        if( !"ALL".equals(logLevel) ) {
            //日志级别不是查询全部不为空的情况，并且不包含查询的级别，就返回false
            if( logFilePojo.getLogRank().indexOf(logQueryPojo.getLogLevel()) == -1) {
                return false;
            }
        }
        //判断是否包含查询参数
        String queryStr = logQueryPojo.getQueryStr();
        if(StringUtils.isNotEmpty(queryStr)) {
            if( logFilePojo.getLogContent().indexOf(queryStr) == -1) {
                return false;
            }
        }

        return true;
    }


    /**
     * 获取文件内容的日志总记录数
     *
     * @param path 文件地址
     * @return
     * list  list.size 为总行数,list.get(i) 为第i条日志信息在文件中对应的行数
     * @throws Exception
     */
    public List getTotalLines(String path) {
        try {
            List list = new ArrayList();
            //读取文件,先判断是否存在
            File file = new File(path);
            if(!file.exists()) {
                //throw new ApusException("日志文件不存在!");
                //logger.debug("日志文件不存在,日志文件的路径为："+path);
                return list;
            }
            FileReader in = new FileReader(file);
            LineNumberReader reader = new LineNumberReader(in);
            String s = reader.readLine();
            int lines = 0;//文件中行数
            while (s != null) {
                //如果当前行存在日期,表示为新的日志信息，记录行数。
                if(checkout(s) ) {
                    list.add(lines);
                }
                lines++;
                s = reader.readLine();
            }
            reader.close();
            in.close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException("文件读取失败,错误信息为："+e.getMessage());

        }

    }

    /**
     * 正则表达式过滤时间
     * @param strParam
     * @return
     */
    public boolean checkout(String strParam) {
        String pattern = "^((((1[6-9]|[2-9]\\d)\\d{2})-(1[02]|0?[13578])-([12]\\d|3[01]|0?[1-9]))|(((1[6-9]|[2-9]\\d)\\d{2})-(1[012]|0?[13456789])-([12]\\d|30|0?[1-9]))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(1\\d|2[0-8]|0?[1-9]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)).*";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(strParam);
        return m.matches();
    }



}
