package cn.hsa.module.base.ba.dao;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dao
 * @Class_name: BaseAdviceDAO
 * @Describe: 医嘱信息数据访问层接口
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseAdviceDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id查询医嘱信息
     * @param baseAdviceDTO  主键ID等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 15:31
     * @Return baseAdviceDTO
     **/
    BaseAdviceDTO getById(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod queryByCodes
     * @Desrciption 根据主键IdList查询医嘱信息
     * @param codeList  编码的list列表
     * @Author xingyu.xie
     * @Date   2020/7/14 15:31
     * @Return baseAdviceDTO
     **/
    List<BaseAdviceDTO> queryByCodes(@Param("list") List<String> codeList,String hospCode);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部医嘱信息
     * @param baseAdviceDTO 医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return java.util.List<cn.hsa.module.base.bad.dto.baseAdviceDTO>
     **/
    List<BaseAdviceDTO> queryAll(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod queryPage
     * @Desrciption 分页查询医嘱信息
     * @param baseAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return java.util.List<cn.hsa.module.base.bad.dto.baseAdviceDTO>
     **/
    List<BaseAdviceDTO> queryPage(BaseAdviceDTO baseAdviceDTO);
    /**
     * @Menthod insert
     * @Desrciption 新增单条医嘱信息
     * @param baseAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    int insert(BaseAdviceDTO baseAdviceDTO);

    /**
    * @Menthod insertList
    * @Desrciption  批量新增医嘱信息
     * @param baseAdviceDTOList
    * @Author xingyu.xie
    * @Date   2020/12/8 11:13
    * @Return int
    **/
    int insertList(@Param("list") List<BaseAdviceDTO> baseAdviceDTOList);

    /**
     * @Menthod update
     * @Desrciption 修改单条医嘱信息
     * @param baseAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    int update(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条医嘱信息
     * @param baseAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    int updateList(@Param("list") List<BaseAdviceDTO> baseAdviceDTO);

    /**
     * @Menthod delete
     * @Desrciption   根据主键ID等参数删除医嘱信息
     * @param baseAdviceDTO  医嘱信息表主键id列表等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    int updateStatus(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Method: getBaseAdvices
     * @Description: 根据条件获取医嘱目录
     * @Param: [inptAdviceDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/14 17:28
     * @Return: java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
     **/
    List<BaseAdviceDTO> getBaseAdvices(List<String> adviceIds, String hospCode, String visitId);

    BaseAdviceDTO baseAdviceDTO(BaseAdviceDTO baseAdviceDTO);

    /**
    * @Method queryOperationNameList
    * @Param [baseAdviceDTO]
    * @description   获取手术医嘱
    * @author marong
    * @date 2020/12/1 9:12
    * @return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
    * @throws
    */
    List<BaseAdviceDTO> queryOperationNameList(BaseAdviceDTO baseAdviceDTO);

    /**
    * @Menthod isExistItemInAdvice
    * @Desrciption  通过项目编码查询出此项目存在哪些医嘱里
     * @param baseItemDTO
    * @Author xingyu.xie
    * @Date   2020/12/8 11:52
    * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
    **/
    List<BaseAdviceDTO> isExistItemInAdvice(BaseItemDTO baseItemDTO);

    /**
     * @Menthod isExistItemInAdvice
     * @Desrciption  通过材料编码查询出此材料存在哪些医嘱里
     * @param baseMaterialDTO
     * @Author xingyu.xie
     * @Date   2020/12/8 11:52
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
     **/
    List<BaseAdviceDTO> isExistMaterialInAdvice(BaseMaterialDTO baseMaterialDTO);

    List<BaseAdviceDTO> getOperationNamePage(BaseAdviceDTO baseAdviceDTO);

    /**合管条码打印查询
    * @Method queryPtPipePrintPage
    * @Desrciption
    * @param paramMap
    * @Author liuqi1
    * @Date   2021/5/10 19:45
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> queryPtPipePrintPage(Map<String,Object> paramMap);

    /**合管条码打印更新
    * @Method updateWithPipePrint
    * @Desrciption
    * @param paramMap
    * @Author liuqi1
    * @Date   2021/4/26 11:55
    * @Return int
    **/
    int updateWithPipePrint(Map<String,Object> paramMap);
}
