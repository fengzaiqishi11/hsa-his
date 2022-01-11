package cn.hsa.module.center.cache.dao;

import cn.hsa.module.sys.code.dto.SysCodeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CacheDao {

    /**
     * 根据编码获取值域代码值
     * @param  codes 需要获取的码表值
     * @param  hospCode 医院编码
     * @Author: luonianxin
     * @Return: java.util.List
     **/
    List<SysCodeDTO> getByCode(@Param("codes") List<String> codes, @Param("hospCode") String hospCode);

}
