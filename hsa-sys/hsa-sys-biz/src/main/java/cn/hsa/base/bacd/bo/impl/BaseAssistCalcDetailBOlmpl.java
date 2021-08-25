package cn.hsa.base.bacd.bo.impl;//package cn.hsa.base.bacd.bo.impl;
//
//import cn.hsa.base.PageDTO;
//import cn.hsa.hsaf.core.framework.HsafBO;
//import cn.hsa.module.base.syncassist.dto.SyncassistDetailDTO;
//import cn.hsa.module.base.bacd.bo.BaseAssistCalcDetailBO;
//import cn.hsa.module.base.syncassist.dao.SyncassistDetailDao;
//import cn.hsa.util.SnowflakeUtils;
//import com.github.pagehelper.PageHelper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//
///**
// * @author ljh
// * @date 2020/07/09.
// */
//@Component
//@Slf4j
//public class BaseAssistCalcDetailBOlmpl extends HsafBO implements BaseAssistCalcDetailBO {
//    @Resource
//    private SyncassistDetailDao baseAssistCalcDetailDao;
//    /**
//     * 通过ID查询单条数据
//     *
//     * @param id 主键
//     * @return 实例对象
//     */
//    @Override
//    public SyncassistDetailDTO queryById(Long id) {
//        return this.baseAssistCalcDetailDao.queryById(id);
//    }
//
//    /**
//     * 通过实体作为筛选条件查询
//     *
//     * @param SyncassistDetailDTO 实例对象
//     * @return 对象列表
//     */
//    @Override
//    public List<SyncassistDetailDTO> queryAll(SyncassistDetailDTO SyncassistDetailDTO) {
//        return this.baseAssistCalcDetailDao.queryAll(SyncassistDetailDTO);
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param SyncassistDetailDTO 实例对象
//     * @return 影响行数
//     */
//    @Override
//    public int insert(SyncassistDetailDTO SyncassistDetailDTO) {
//        SyncassistDetailDTO.setId(SnowflakeUtils.getId());
//        SyncassistDetailDTO.setCrteTime(new Date());
//        return this.baseAssistCalcDetailDao.insert(SyncassistDetailDTO);
//    }
//
//    /**
//     * 修改数据
//     *
//     * @param SyncassistDetailDTO 实例对象
//     * @return 影响行数
//     */
//    @Override
//    public int update(SyncassistDetailDTO SyncassistDetailDTO) {
//        return this.baseAssistCalcDetailDao.update(SyncassistDetailDTO);
//    }
//
//    /**
//     * 通过主键删除数据
//     *
//     * @param id 主键
//     * @return 影响行数
//     */
//    @Override
//    public int deleteById(Long id) {
//        return this.baseAssistCalcDetailDao.deleteById(id);
//    }
//
//    /**
//     * @param SyncassistDetailDTO
//     * @Method queryPage()
//     * @Description 分页查询财务分类信息
//     * @Param 1、baseFinanceClassifyDTO：财务分类数据参数对象
//     * @Author zhongming
//     * @Date 2020/7/1 20:55
//     * @Return PageDTO
//     */
//    @Override
//    public PageDTO queryPage(SyncassistDetailDTO SyncassistDetailDTO) {
//        PageHelper.startPage(SyncassistDetailDTO.getPageNo(), SyncassistDetailDTO.getPageSize());
//
//        // 查询所有
//        List<SyncassistDetailDTO> baseBedDTOList = baseAssistCalcDetailDao.queryAll(SyncassistDetailDTO);
//
//        // 返回分页结果
//        return PageDTO.of(baseBedDTOList);
//    }
//}
