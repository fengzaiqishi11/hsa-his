package cn.hsa.module.insure.module.dao;

import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureItemMatch.dao
 * @Class_name: InsureItemMatchDAO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/7 10:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureItemMatchDAO {

    /**
     * @Method queryPageOrAll
     * @Desrciption 查询所有(包含分页)
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 11:07
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     **/
    List<InsureItemMatchDTO> queryPageOrAll(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @Method insertHospDrug
     * @Desrciption 插入医院药品信息
     * @Param
     * [baseDrugDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 11:07
     * @Return java.lang.Boolean
     **/
    Boolean insertHospDrug(@Param("list") List<BaseDrugDTO> baseDrugDTOS);

    /**
     * @Method updateHospDrug
     * @Desrciption 修改医院药品信息
     * @Param
     * [baseDrugDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 11:07
     * @Return java.lang.Boolean
     **/
    Boolean updateHospDrug(@Param("list") List<BaseDrugDTO> baseDrugDTOS);

    /**
     * @Method insertHospItem
     * @Desrciption 新增项目信息
     * @Param
     * [baseItemDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 11:08
     * @Return java.lang.Boolean
     **/
    Boolean insertHospItem(@Param("list") List<BaseItemDTO> baseItemDTOS);

    /**
     * @Method updateHospItem
     * @Desrciption 修改项目信息
     * @Param
     * [baseItemDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 11:08
     * @Return java.lang.Boolean
     **/
    Boolean updateHospItem(@Param("list") List<BaseItemDTO> baseItemDTOS);

    /**
     * @Method insertHospMaterial
     * @Desrciption 新增材料信息
     * @Param
     * [baseMaterialDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 11:08
     * @Return java.lang.Boolean
     **/
    Boolean insertHospMaterial(@Param("list") List<BaseMaterialDTO> baseMaterialDTOS);

    /**
     * @Method updateHospMaterial
     * @Desrciption 修改材料信息
     * @Param
     * [baseMaterialDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 11:08
     * @Return java.lang.Boolean
     **/
    Boolean updateHospMaterial(@Param("list") List<BaseMaterialDTO> baseMaterialDTOS);

    /**
     * @Method deleteHospItem
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param
     * [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date   2020/12/1 13:59
     * @Return java.lang.Boolean
     **/
    Boolean deleteHospItem(InsureItemMatchDTO insureItemMatchDTO);


    /**
     * @Method updateInsureItemMatchS
     * @Desrciption 将匹配好的数据写入表
     * @Param
     * [insureItemMatchDTOS]
     * @Author liaojunjie
     * @Date   2020/12/16 11:09
     * @Return java.lang.Boolean
     **/
    Boolean updateInsureItemMatchS(@Param("list") List<InsureItemMatchDTO> insureItemMatchDTOS);




    /**
     * @Method queryInsureMatchItem
     * @Desrciption  医保费用上传 查询费用匹配的数据
     * @Param hospCode:医院编码
     *        xmid：项目id
     *        insureOrgCode：医保机构编码
     *        itemCode：项目分类
     *
     * @Author fuhui
     * @Date   2020/11/14 11:42
     * @Return
    **/
    InsureItemMatchDTO queryInsureMatchItem(Map<String, Object> costMap);

    /**
     * @Method queryDrugAndProdcut()
     * @Desrciption  根据项目id 查询出药品和对应的生产厂家
     * @Param
     *        xmid：项目id
     *        hospCode:医院编码
     *        itemCode：项目分类
     * @Author fuhui
     * @Date   2020/11/14 11:49
     * @Return
    **/
    BaseDrugDTO queryDrugAndProdcut(Map<String, Object> costMap);

    BaseMaterialDTO queryMaterialAndProduct(Map<String, Object> costMap);
    
    /**
     * @Menthod queryByHospItemId
     * @Desrciption 根据医院项目id查询匹配信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/13 15:19 
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     */
    List<InsureItemMatchDTO> queryByHospItemId(Map<String,Object> param);

    /**
     * @Method handMatch
     * @Desrciption  处理手动匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 14:25
     * @Return
     **/
    Boolean insertHandMatch(Map<String,Object> map);


    /**
     * @Method updateInsureMatch
     * @Desrciption  根据项目id,取消医保匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/30 14:10
     * @Return
     **/
    Boolean deleteInsureMatch(Map<String,Object> map);

    /**
     * @Method autoMatch()
     * @Desrciption  长沙医保匹配：根据名称进行项目匹配
     * @Param
     *
     * @Author fuhui
     * @Date   2021/1/27 10:02
     * @Return
     **/
    Integer insertMatchItem(@Param("insureItemMatchDTOList") List<InsureItemMatchDTO> insureItemMatchDTOList);

    /**
     * @Method queryItemIsMatch
     * @Desrciption  在进行匹配之前，需要确定是否之前已经做了匹配
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/5 9:30
     * @Return
    **/
    List<InsureItemMatchDTO> queryItemIsMatch(Map<String, Object> map);

    /**
     * @Description: 根据医保中心编码查询所有项目匹配数据
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/25 11:38
     * @Return
     */
    List<InsureItemMatchDTO> selectAllInsureItemMatchs(Map<String, Object> map);

    /**
     * @param insureItemMatchDTO
     * @Method deleteInsureItemMatch
     * @Desrciption 医保通一支付平台：删除匹配数据
     * @Param
     * @Author fuhui
     * @Date 2021/3/28 12:50
     * @Return
     */
    Boolean deleteInsureItemMatch(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * 获取
     * @param itemMatchParam
     * @return
     */
    List<InsureItemMatchDTO> queryInsureMatchItemByItems(Map<String, Object> itemMatchParam);

    /**
     * @Method deleteBatchInusreItemMatch
     * @Desrciption  医保统一支付平台：目录对照撤销
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/29 15:39
     * @Return
    **/
    void deleteBatchInusreItemMatch(@Param("insureItemMatchDTOList") List<InsureItemMatchDTO> insureItemMatchDTOList);

    /**
     * @Method updateBatchInsureItem
     * @Desrciption  上传成功以后 修改是否传输标识
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/8 10:18
     * @Return
    **/
    void modifyInsureItem(@Param("list") List<InsureItemMatchDTO> insureItemDTOList);

    Boolean updateInsureItemMatch(InsureItemMatchDTO itemMatchDTO);

    List<InsureItemMatchDTO> selectById(InsureItemMatchDTO itemMatchDTO);

    Boolean deleteUnifiedInsureItemMatch(InsureItemMatchDTO insureItemMatchDTO);

    InsureItemMatchDTO selectInsureItemMatch(InsureItemMatchDTO insureItemMatchDTO);

    /**
     * @Method updateInsureItemMatchInfo
     * @Desrciption   导入医保匹配数据
     * @Param
     *
     * @Author 廖继广
     * @Date   2021/05/20 05:20
     * @Return
     **/
    int updateInsureItemMatchInfo(List<Map<String, String>> inputList);

    /**
     * @Method queryAllDrugsInfo
     * @Desrciption   查询所有有效的药品信息
     * @Param
     * @Author 廖继广
     * @Date   2021/07/12 15:39
     * @Return
     **/
    List<BaseDrugDTO> queryAllDrugsInfo(Map selectMap);

    /**
     * @Method queryAllDrugsInfo
     * @Desrciption   查询所有有效的项目信息
     * @Param
     * @Author 廖继广
     * @Date   2021/07/12 15:39
     * @Return
     **/
    List<BaseItemDTO> queryAllItemsInfo(Map selectMap);

    /**
     * @Method queryAllMaterialsInfo
     * @Desrciption   查询所有有效的材料信息
     * @Param
     * @Author 廖继广
     * @Date   2021/07/12 15:39
     * @Return
     **/
    List<BaseMaterialDTO> queryAllMaterialsInfo(Map selectMap);

    /**
     * 查询所有未匹配的项目
     * @param insureItemMatchDTO
     * @return
     */
    List<Map<String, Object>> queryUnMacthAllPage(InsureItemMatchDTO insureItemMatchDTO);

    List<InsureItemMatchDTO> queryUnMacthAllDtoPage(InsureItemMatchDTO insureItemMatchDTO);

    int deleteInsureItemInfos(InsureItemMatchDTO insureItemMatchDTO);

    List<BaseMaterialDTO> queryAllInsureMaterialsInfo(Map selectMap);

    /**
     * 根据医保注册编码和医院项目编码查询数据
     * @param selectMap
     * @Author 医保开发二部-湛康
     * @Date 2022-04-12 15:37
     * @return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     */
    List<InsureItemMatchDTO> selectItemsByParams(Map selectMap);

    /**
     * @param itemMatchDTO
     * @return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     * @method queryByHospItemId
     * @author wang'qiao
     * @date 2022/6/21 21:28
     * @description 根据hospItemId查询项目信息
     **/
    List<InsureItemMatchDTO> queryByHospItemIdIsItemId(InsureItemMatchDTO itemMatchDTO);
}
