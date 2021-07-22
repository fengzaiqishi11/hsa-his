package cn.hsa.base.ba.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.ba.bo.BaseAdviceBO;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.ba.service.BaseAdviceService;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.inventory.service.impl
 * @Class_name: BaseAdviceServiceImpl
 * @Describe: 医嘱信息Service接口实现层（提供给dubbo调用）
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/base/baseAdvice")
@Slf4j
@Service("baseAdviceService_provider")
public class BaseAdviceServiceImpl extends HsafService implements BaseAdviceService {

    @Resource
    BaseAdviceBO baseAdviceBO;

    /**
     * @Menthod getById
     * @Desrciption   根据主键id查询医嘱信息
     * @param map 医嘱信息表主键
     * @Author xingyu.xie
     * @Date   2020/7/14 15:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @Override
    public WrapperResponse<BaseAdviceDTO> getById(Map map) {
        BaseAdviceDTO dto = baseAdviceBO.getById(MapUtils.get(map,"baseAdviceDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod queryAdviceDetrailByItemCode
     * @Desrciption   根据项目编码itemCode查询医嘱详细信息
     * @param map id 医嘱信息表主键ID
     * @Author xingyu.xie
     * @Date   2020/7/14 16:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseAdviceDTO>
     **/
    @Override
    public WrapperResponse<List<BaseAdviceDetailDTO>> queryAllAdviceDetail(Map map){
        return WrapperResponse.success(baseAdviceBO.queryAllAdviceDetail(MapUtils.get(map,"baseAdviceDetailDTO")));
    }

    /**
    * @Menthod queryItemsByAdviceCode
    * @Desrciption  根据医嘱编码查询其所有的项目详情
     * @param map  医嘱编码
    * @Author xingyu.xie
    * @Date   2020/7/15 14:06
    * @Return java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryItemsByAdviceCode(Map map) {
        return WrapperResponse.success(baseAdviceBO.queryItemsByAdviceCode(MapUtils.get(map,"baseAdviceDTO")));
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部医嘱信息
     * @param map 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/15 20:36
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.inventory.dto.BaseAdviceDTO>>
    **/
    @Override
    public WrapperResponse<List<BaseAdviceDTO>> queryAll(Map map) {
        return WrapperResponse.success(baseAdviceBO.queryAll(MapUtils.get(map,"baseAdviceDTO")));
    }

    /**
     * @Menthod queryPage
     * @Desrciption   根据数据对象来筛选查询
     * @param map 医嘱数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:38
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PageDTO dto = baseAdviceBO.queryPage(MapUtils.get(map,"baseAdviceDTO"));
        return WrapperResponse.success(dto);
    }

    /**
     * @Menthod insert
     * @Desrciption    新增医嘱信息
     * @param map 医嘱数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> insert(Map map) {
        return WrapperResponse.success(baseAdviceBO.insert(MapUtils.get(map,"baseAdviceDTO")));
    }

    /**
     * @Menthod update
     * @Desrciption   修改医嘱信息
     * @param map 医嘱数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 15:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> update(Map map) {
        return WrapperResponse.success(baseAdviceBO.update(MapUtils.get(map,"baseAdviceDTO")));
    }

    /**
     * @Menthod updateStatus
     * @Desrciption   删除一个或多个医嘱信息
     * @param map 医嘱信息表的主键列表
     * @Author xingyu.xie
     * @Date   2020/7/14 15:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateStatus(Map map) {

        return WrapperResponse.success(baseAdviceBO.updateStatus(MapUtils.get(map,"baseAdviceDTO")));
    }

    /**
    * @Menthod queryItemAndMaterialPage
    * @Desrciption  将项目表和材料表的数据一起查出来，并转换为医嘱详细的数据传输对象
     * @param map 医嘱详细数据传输对象,医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/8/6 13:54
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryItemAndMaterialPage(Map map) {
        return WrapperResponse.success(baseAdviceBO.queryItemAndMaterialPage(MapUtils.get(map,"baseAdviceDetailDTO")));
    }

    /**
     * @Menthod updateBaseAdviceAndBaseAdviceDetailsByItemCode
     * @Desrciption 更新材料或项目的单价或者
     * @param map 医院编码hospCode baseAdviceDetailDTO
     * 必填：1.医院编码（hospCode） 2.项目或材料编码（itemCode）
     * 选填：1.单价（price） 2.名称（itemName） 3.单位代码（unitCode） 4.规格（spec） 5.用药性质（useCode）
     * @Author xingyu.xie
     * @Date   2020/9/4 14:41
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateBaseAdviceAndBaseAdviceDetailsByItemCode(Map map){
        return WrapperResponse.success(baseAdviceBO.updateBaseAdviceAndBaseAdviceDetailsByItemCode(MapUtils.get(map,"baseAdviceDetailDTOList")));
    }

    /**
     * @Method: getBaseAdvices
     * @Description: 根据医嘱ID,就诊ID获取医嘱目录列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 16:41
     * @Return: java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>
     **/
    @Override
    public WrapperResponse<List<BaseAdviceDTO>> getBaseAdvices(Map map) {
        return WrapperResponse.success(baseAdviceBO.getBaseAdvices(MapUtils.get(map,"adviceIds"),MapUtils.get(map,"hospCode"),MapUtils.get(map,"visitId")));
    }

    /**
    * @Method queryItemAndMaterialAndDrugPage
    * @Param [map]
    * @description   查询和项目和材料和药品三合一表
    * @author marong
    * @date 2020/9/29 15:44
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    * @throws
    */
    @Override
    public WrapperResponse<PageDTO> queryItemAndMaterialAndDrugPage(Map map) {
        return WrapperResponse.success(baseAdviceBO.queryItemAndMaterialAndDrugPage(MapUtils.get(map,"baseAdviceDetailDTO")));
    }

    /**
     * @Method: getBaseAdviceByCode
     * @Description: 根据编码获取医嘱目录信息
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/3 10:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDTO>>
     **/
    @Override
    public WrapperResponse<BaseAdviceDTO> getBaseAdviceByCode(Map map) {
        return WrapperResponse.success(baseAdviceBO.getBaseAdviceByCode(MapUtils.get(map,"baseAdviceDTO")));
    }

    /**
    * @Method queryOperationNameList
    * @Param [map]
    * @description   获取手术医嘱
    * @author marong
    * @date 2020/12/1 9:05
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO>>
    * @throws
    */
    @Override
    public WrapperResponse<List<BaseAdviceDTO>> queryOperationNameList(Map<String, Object> map) {
        BaseAdviceDTO baseAdviceDTO = MapUtils.get(map,"baseAdviceDTO");
        return WrapperResponse.success(baseAdviceBO.queryOperationNameList(baseAdviceDTO));
    }

    /**
    * @Menthod generateAdviceByItem
    * @Desrciption  根据项目生成医嘱
     * @param map
    * @Author xingyu.xie
    * @Date   2020/12/8 9:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.String>
    **/
    @Override
    public WrapperResponse<List<BaseItemDTO>> generateAdviceByItem(Map<String, Object> map) {
        return WrapperResponse.success(baseAdviceBO.insertGenerateAdviceByItem(map));
    }

    /**
     * @Method getOperationNamePage
     * @Param [baseAdviceDTO]
     * @description   查询手术医嘱分页数据展示
     * @author Mr.Liao
     * @date 2020/12/18 14:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     */
    @Override
    public WrapperResponse<PageDTO> getOperationNamePage(Map<String, Object> map) {
        return WrapperResponse.success(baseAdviceBO.getOperationNamePage(MapUtils.get(map,"baseAdviceDTO")));
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @throws
     * @Method queryDetailByAdviceCode
     * @Param [baseAdviceDTO]
     * @description 根据CODE获取医嘱目录明细
     * @author pengbo
     * @date 2021/04/01 14:38
     */
    @Override
    public WrapperResponse<PageDTO> queryDetailByAdviceCode(Map<String, Object> map) {
        return WrapperResponse.success(baseAdviceBO.queryDetailByAdviceCode(MapUtils.get(map,"baseAdviceDTO")));
    }

    /**合管条码打印查询
     * @Method queryPipePrintPage
     * @Desrciption
     * @param map
     * @Author liuqi1
     * @Date   2021/4/25 11:32
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPipePrintPage(Map map) {
        PageDTO dto = baseAdviceBO.queryPipePrintPage(MapUtils.get(map,"paramMap"));
        return WrapperResponse.success(dto);
    }

    /**合管条码打印更新
     * @Method updateWithPipePrint
     * @Desrciption
     * @param map
     * @Author liuqi1
     * @Date   2021/4/26 11:53
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateWithPipePrint(Map map) {
        Boolean result = baseAdviceBO.updateWithPipePrint(MapUtils.get(map, "paramMap"));
        return WrapperResponse.success(result);
    }
}
