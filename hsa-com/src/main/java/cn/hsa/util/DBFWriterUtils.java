package cn.hsa.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 DBF导出
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-29  09:18
 */
public class DBFWriterUtils {

	public static void writerDbf(List<LinkedHashMap<String, Object>> list,String url,String fileName) throws AppException {
		//判断是否为空
		if(list == null || list.isEmpty()){
			return ;
		}
		DBFWriter writer = null ;
		FileOutputStream fos = null ;
		try {
		//获取数据表头
		List<String> keys = new ArrayList<String>(list.get(0).keySet());
		DBFField fields[] = new DBFField[keys.size()];
		for (int i = 0; i < keys.size(); i++) {
			fields[i] = new DBFField();
			fields[i].setName(keys.get(i));
			//判断当前列是什么数据类型
			if ((list.get(0).get(keys.get(i))) instanceof Boolean) {
				fields[i].setType(DBFDataType.CHARACTER);
				fields[i].setLength(254);
			}
			else if ((list.get(0).get(keys.get(i))) instanceof Date) {
				fields[i].setType(DBFDataType.DATE);
				fields[i].setLength(8);
			}
			else if ((list.get(0).get(keys.get(i))) instanceof Number) {
				fields[i].setType(DBFDataType.NUMERIC);
				fields[i].setLength(32);
			}
			else  {
				fields[i].setType(DBFDataType.CHARACTER);
				fields[i].setLength(254);
			}
		}
		url = url+"\\"+fileName+".dbf";
		//生成dbf文件
		checkFileExists(new File(url));
		fos = new FileOutputStream(url);
		writer = new DBFWriter(fos, Charset.defaultCharset());
		writer.setFields(fields);
		writer.setCharactersetName("GBK");
		//将数据body写入文件
		Object[] rowData = null;
		for (int i = 0; i < list.size(); i++) {
			rowData = new Object[keys.size()];
			for (int j = 0; j < keys.size(); j++) {
				rowData[j] = list.get(i).get(keys.get(j));
			}
			writer.addRecord(rowData);
		}
		}catch (Exception e) {
			throw new AppException("生成DBF文件出错,原因："+e.getMessage());
		}finally {
			writer.close();
			try {
				fos.close();
			} catch (IOException e) {
				throw new  AppException("生成DBF文件出错,原因："+e.getMessage());
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

		// 删除昨天或更久的文件夹
		for (File file : files)
			delteFile(file);
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