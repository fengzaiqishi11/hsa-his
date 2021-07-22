package cn.hsa.module.base.ba.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.bo
 * @Class_name: BaseAdviceBO
 * @Describe: 医嘱信息业务逻辑实现层接口
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseAdviceBO {
    /**
     * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询医嘱信息
     * @param baseAdviceDTO  主键ID List列表和医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return cn.hsa.module.base.bfc.dto.BaseAdviceDTO
     **/
    BaseAdviceDTO getById(BaseAdviceDTO baseAdviceDTO);


    /**
     * @Menthod getAdviceDetrailByItemCode
     * @Desrciption 根据项目编码查询医嘱详细
     * @param baseAdviceDetailDTO  项目编码，材料编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return cn.hsa.module.base.bfc.dto.BaseAdviceDTO
     **/
    List<BaseAdviceDetailDTO> queryAllAdviceDetail(BaseAdviceDetailDTO baseAdviceDetailDTO);

    /**
     * @Menthod queryItemsByAdviceCode
     * @Desrciption   根据医嘱编码查询医嘱详细表，再根据项目编码查询所有项目
     * @param baseAdviceDTO  医嘱编码,医院编码等信息
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bad.dto.BaseAdviceDTO>
     **/
    PageDTO queryItemsByAdviceCode(BaseAdviceDTO baseAdviceDTO);


    /**
     * @Menthod queryPage
     * @Desrciption   查询所有医嘱信息
     * @param baseAdviceDTO  医院编码
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bad.dto.BaseAdviceDTO>
     **/
    List<BaseAdviceDTO> queryAll(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod queryPage
     * @Desrciption   分页查询医嘱信息
     * @param baseAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:04
     * @Return java.util.List<cn.hsa.module.base.bad.dto.BaseAdviceDTO>
     **/
    PageDTO queryPage(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod insert
     * @Desrciption   新增单条医嘱信息
     * @param baseAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    boolean insert(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod update
     * @Desrciption   修改单条医嘱信息
     * @param baseAdviceDTO  医嘱分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    boolean update(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod updateStatus
     * @Desrciption   根据主键ID和医院编码等参数删除医嘱信息
     * @param baseAdviceDTO  医嘱信息表主键id列表，医院编码等参数
     * @Author xingyu.xie
     * @Date   2020/7/14 16:05
     * @Return int
     **/
    boolean updateStatus(BaseAdviceDTO baseAdviceDTO);

    /**
     * @Menthod queryItemAndMaterialPage
     * @Desrciption 查询项目和材料表二合一数据
     * @param baseAdviceDetailDTO 医嘱详细数据传输对象
     * @Author xingyu.xie
     * @Date   2020/8/4 16:21
     * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseItemAndMaterialDTO>
     **/
    PageDTO queryItemAndMaterialPage(BaseAdviceDetailDTO baseAdviceDetailDTO);

    /**
     * @Menthod updateBaseAdviceAndBaseAdviceDetailsByItemCode
     * @Desrciption 更新材料或项目的单价或者
     * 必填：1.医院编码（hospCode） 2.项目或材料编码（itemCode）
     * 选填：1.单价（price） 2.名称（itemName） 3.单位代码（unitCode） 4.规格（spec） 5.用药性质（useCode）
     * @param baseAdviceDetailDTOList 项目或材料的
     * @Author xingyu.xie
     * @Date   2020/9/4 14:41
     * @Return boolean
     **/
     boolean updateBaseAdviceAndBaseAdviceDetailsByItemCode(List<BaseAdviceDetailDTO> baseAdviceDetailDTOList);

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

    /**
    * @Method queryItemAndMaterialAndDrugPage
    * @Param [baseAdviceDetailDTO]
    * @description   查询和项目和材料和药品三合一表
    * @author marong
    * @date 2020/9/29 15:45
    * @return cn.hsa.base.PageDTO
    * @throws
    */
    PageDTO queryItemAndMaterialAndDrugPage(BaseAdviceDetailDTO baseAdviceDetailDTO);

    BaseAdviceDTO getBaseAdviceByCode(BaseAdviceDTO baseAdviceDTO);

    /**
    * @Method queryOperationNameList
    * @Param [baseAdviceDTO]
    * @description   获取手术医嘱
    * @author marong
    * @date 2020/12/1 9:11
    * @return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
    * @throws
    */
    List<BaseAdviceDTO> queryOperationNameList(BaseAdviceDTO baseAdviceDTO);

    /**
    * @Menthod generateAdviceByItem
    * @Desrciption  根据项目生成医嘱
     * @param map
    * @Author xingyu.xie
    * @Date   2020/12/8 9:49
    * @Return java.lang.String
    **/
    List<BaseItemDTO> insertGenerateAdviceByItem(Map<String, Object> map);

    /**
     * @Method getOperationNamePage
     * @Param [baseAdviceDTO]
     * @description   查询手术医嘱分页数据展示
     * @author Mr.Liao
     * @date 2020/12/18 14:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    PageDTO getOperationNamePage(BaseAdviceDTO baseAdviceDTO);

    /**
     * @param baseAdviceDTO
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     * @Method queryDetailByAdviceCode
     * @Param [baseAdviceDTO]
     * @description 根据CODE获取医嘱目录明细
     * @author pengbo
     * @date 2021/04/01 14:38
     */
    PageDTO queryDetailByAdviceCode(BaseAdviceDTO baseAdviceDTO);

    /**合管条码打印查询
    * @Method queryPipePrintPage
    * @Desrciption
    * @param paramMap
    * @Author liuqi1
    * @Date   2021/4/25 11:33
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryPipePrintPage(Map<String,Object> paramMap);

    /**合管条码打印更新
    * @Method updateWithPipePrint
    * @Desrciption
    * @param paramMap
    * @Author liuqi1
    * @Date   2021/4/26 11:54
    * @Return java.lang.Boolean
    **/
    Boolean updateWithPipePrint(Map<String,Object> paramMap);
}
