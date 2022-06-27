package cn.hsa.module.center.authorization.dao;


import cn.hsa.module.center.authorization.entity.CenterInterceptUrlRecordDO;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 *   中心端需要拦截的接口地址记录表数据库访问层
 */
@Mapper
public interface CenterInterceptUrlRecordDAO {

  /**
   *  查询所有需要拦截的接口地址信息
   * @return 接口地址列表
   */
  List<CenterInterceptUrlRecordDO> queryAllCenterInterceptUrlRecords();
}
