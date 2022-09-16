package cn.hsa.module.stro.adjust.dao;

import cn.hsa.module.stro.adjust.dto.StroAdjustDTO;
import cn.hsa.module.stro.adjust.dto.StroAdjustDetailDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stroin.dto.StroInDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.stro.adjust.dao
 *@Class_name: StroAdjustDao
 *@Describe: 药品调价
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-07-31 14:56:01
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface StroAdjustDao {

    /**
    * @Method getStroAdjustDaoById
    * @Desrciption 单个查询
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 18:56
    * @Return cn.hsa.module.stro.adjust.entity.StroAdjustDO
    **/
    StroAdjustDTO getStroAdjustDtoById(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method queryStroAdjustDaoPage
    * @Desrciption  分页查询
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 18:56
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
    **/
    List<StroAdjustDTO> queryStroAdjustDtoPage(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method queryStroAdjustDtos
    * @Desrciption 根据条件查询
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/9/8 10:35
    * @Return java.util.List<cn.hsa.module.stro.adjust.dto.StroAdjustDTO>
    **/
    List<StroAdjustDTO> queryStroAdjustDtos(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method insertStroAdjustDao
    * @Desrciption 单个新增
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 18:56
    * @Return int
    **/
    int insertStroAdjustDto(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method updateStroAdjustDao
    * @Desrciption 调价修改
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/1 18:57
    * @Return int
    **/
    int updateStroAdjustDto(StroAdjustDTO stroAdjustDTO);

    /**
    * @Method updateStroAdjustDto
    * @Desrciption 批量修改(通过id集合修改审核状态)
    * @param stroAdjustDTO
    * @Author liuqi1
    * @Date   2020/8/4 9:06
    * @Return int
    **/
    int updateStroAdjustDtoByIds(StroAdjustDTO stroAdjustDTO);

    /**
    * @Menthod updateAdjustPriceById
    * @Desrciption 回写调价主表金额信息
    *
    * @Param
    * [stroAdjustDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 14:42
    * @Return int
    **/
    int updateAdjustPriceById(@Param("list") List<StroAdjustDTO> stroAdjustDTOS);

    /**
     * @Method selectJudgeStoOutDruag
     * @Desrciption 查看出库表和明细表里面是否有未审核的药品
     * @Param [itemList]
     * @Author zhangguorui
     * @Date   2021/8/3 10:08
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    List<Map<String,String>> selectJudgeStoOutDruag(@Param("itemList") List<String> itemList,@Param("hospCode") String hospCode);
    /**
     * @Method selectJudgePhrDruag
     * @Desrciption 查看采购表和明细表里面是否有未审核的药品
     * @Param [itemList]
     * @Author zhangguorui
     * @Date   2021/8/3 10:08
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    List<Map<String, String>> selectJudgePhrDruag(@Param("itemList") List<String> itemList,@Param("hospCode") String hospCode);
    /**
     * @Method selectJudgeStoInDruag
     * @Desrciption 查看入库表和明细表里面是否有未审核的药品
     * @Param [druagList, hospCode]
     * @Author zhangguorui
     * @Date   2021/8/3 15:57
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    List<Map<String, String>> selectJudgeStoInDruag(@Param("itemList") List<String> itemList,@Param("hospCode") String hospCode);
    /**
     * @Method selectJudgeInventoryDruag
     * @Desrciption 查看盘点表和明细表里面是否有未审核的药品
     * @Param [druagList, hospCode]
     * @Author zhangguorui
     * @Date   2021/8/3 16:15
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    List<Map<String, String>> selectJudgeInventoryDruag(@Param("itemList") List<String> itemList,@Param("hospCode") String hospCode);
    /**
     * @Method selectJudgeIncdecDruag
     * @Desrciption 查看报损表以及明细表中是否包含未审核药品
     * @Param [druagList, hospCode]
     * @Author zhangguorui
     * @Date   2021/8/3 16:20
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    List<Map<String, String>> selectJudgeIncdecDruag(@Param("itemList") List<String> itemList,@Param("hospCode") String hospCode);
    /**
     * @Method selectJudgeconfirmDruag
     * @Desrciption 查看退库确认
     * @Param [druagList, hospCode]
     * @Author zhangguorui
     * @Date   2021/8/3 17:13
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     */
    List<Map<String, String>> selectJudgeconfirmDruag(@Param("itemList") List<String> itemList, @Param("hospCode") String hospCode,
                                                      @Param("deptConfirm") String deptConfirm);
    /**
     * @Meth: getParameterValue
     * @Description: 获得系统参数
     * @Param: [hospCode, code]
     * @return: java.util.List<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
     * @Author: zhangguorui
     * @Date: 2021/11/8
     */
    List<SysParameterDTO> getParameterValue(String hospCode, String[] code);
    /**
     * @Meth: updateAdjustDTOPriceById
     * @Description: 根据id回写价格
     * @Param: [stroAdjustDTO]
     * @return: void
     * @Author: zhangguorui
     * @Date: 2022/4/14
     */
    void updateAdjustDTOPriceById(StroAdjustDTO stroAdjustDTO);
    /**
     * @Author gory
     * @Description 回写入库明细单
     * @Date 2022/9/14 15:32
     * @Param [stroAdjustDetailDTOs, sourceId]
     **/
    void updateStroInDetail(@Param("list") List<StroAdjustDetailDTO> stroAdjustDetailDTOs,
                            @Param("sourceId") String sourceId);
    /**
     * @Author gory
     * @Description 回写入库单据主表
     * @Date 2022/9/14 15:58
     * @Param [stroAdjustDetailDTOs, sourceId]
     **/
    void updateStroIn(@Param("sourceId") String sourceId);
    /**
     * @Author gory 更新入库单明细
     * @Description
     * @Date 2022/9/14 16:16
     * @Param [stroAdjustDetailDTOs, sourceId]
     **/
    void updateStroInDetailByBefore(@Param("sourceId") String sourceId);
    /**
     * @Author gory
     * @Description 通过sourceID删除调价单
     * @Date 2022/9/15 11:38
     * @Param [stroAdjustDTO]
     **/
    void deleteAdjustBySourceId(StroAdjustDTO stroAdjustDTO);
    /**
     * @Author gory
     * @Description 删除明细
     * @Date 2022/9/15 11:50
     * @Param [stroAdjustDTO]
     **/
    void deleteAdjustDetailBySourceId(StroAdjustDTO stroAdjustDTO);
    /**
     * @Author gory
     * @Description 当入库单作废的时候，同时作废生成的调价单
     * @Date 2022/9/16 9:08
     * @Param [stroInDTO]
     **/
    void updateAdjustDTOBySourceIds(StroInDTO stroInDTO);
}
