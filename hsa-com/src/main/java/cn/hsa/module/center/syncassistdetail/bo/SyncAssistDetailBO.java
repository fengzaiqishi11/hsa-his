package cn.hsa.module.center.syncassistdetail.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.syncassistdetail.dto.SyncAssistDetailDTO;

import java.util.List;


public interface SyncAssistDetailBO {
    /**
     * @Method getById()
     * @Description 查询
     * @Param
     * id
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<SyncAssistDetailDTO>
     **/
    SyncAssistDetailDTO getById(Long id);
    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     * @Param
     * SyncAssistDetailDTO baseDailyfirstCalcDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<SyncAssistDetailDTO>>
     **/
    List<SyncAssistDetailDTO> queryAll(SyncAssistDetailDTO baseDailyfirstCalcDTO);
    /**
     * @Method insert()
     * @Description 更新
     * @Param
     * List<SyncAssistDetailDTO> aseDailyfirstCalcDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/
    boolean insert(List<SyncAssistDetailDTO> aseDailyfirstCalcDTO);
    /**
     * @Method deleteById()
     * @Description 删除
     * @Param
     * SyncAssistDetailDTO baseDailyfirstCalcDTO
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/
    int update(SyncAssistDetailDTO baseDailyfirstCalcDTO);
    /**
     * @Method insert()
     * @Description 更新
     * @Param
     * id
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    int deleteById(Long id);
    /**
     * @Method deleteById()
     * @Description 删除
     * @Param
     * Map map
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<Boolean>
     **/

    int  deleteBylist(SyncAssistDetailDTO baseDailyfirstCalcDTO);
    /**
     * @Method queryPage()
     * @Description 分页查询
     * @Param
     * Map map
     * @Author ljh
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/

    PageDTO queryPage(SyncAssistDetailDTO baseDailyfirstCalcDTO);
}
