package cn.hsa.platform.dao;

import cn.hsa.platform.domain.SmsRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信记录的Dao
 * @author unkown
 *
 */

@Mapper
public interface SmsRecordDao extends BaseMapper<SmsRecord> {


}
