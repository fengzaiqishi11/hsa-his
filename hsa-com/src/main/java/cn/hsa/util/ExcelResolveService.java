package cn.hsa.util;

import java.util.List;
import java.util.Map;

/**
   * @Class_name: LargeExcelUpload
   * @Description:  大文件上传数据库使用接口
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/7 13:24
**/

public interface ExcelResolveService {
    /**
       * @Description: 处理上传的数据
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 13:29
     * @param dataMap 解析后的数据
     **/
   void insertProcessedUploadData(Map<String,Object> dataMap);
}
