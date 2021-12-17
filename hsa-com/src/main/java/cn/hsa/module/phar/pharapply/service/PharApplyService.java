package cn.hsa.module.phar.pharapply.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharapply.dto.PharApplyDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.phar.pharapply.service
 * @class_name: PharApplyService
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 21:09
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-stro")
public interface PharApplyService {
    /**
     * @Method: queryPage()
     * @Description: 分页显示领药申请的信息
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:49
     * @Return: PharApplyDTO领药申请的数据传输对象集合
     */
    @GetMapping("/web/stro/pharApply/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method: insert()
     * @Description: 新增领药申请
     * @Param: 1、pharApplyDTO领药申请的数据传输对象
     * 2、pharApplyDetailDTOS领药申请数据明细集合
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: 影响的行数
     */
    @PostMapping("/web/stro/pharApply/insert")
    WrapperResponse<Boolean> insert(Map map);

    /**
     * @Method: update()
     * @Description: 修改领药申请
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: 影响的行数
     */
    @PostMapping("/web/stro/pharApply/update")
    WrapperResponse<Boolean> update(Map map);

    /**
     * @Method: batchCancelled()
     * @Description: 批量作废
     * @Param: 1.hospCode医院编码
     * 2.id主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return: boolean
     */
    @PostMapping("/web/stro/pharApply/batchCancelled")
    WrapperResponse<Boolean> updateBatchCancelled(Map map);

    /**
     * @Method: batchCheck()
     * @Description: 批量审核
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return: boolean
     */
    @PostMapping("/web/stro/pharApply/batchCheck")
    WrapperResponse<Boolean> updateBatchCheck(Map map);

    /**
     * @Method: pharApplyDetail()
     * @Description: 显示药品请领明细表
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 20:11
     * @Return: PageDTO
     */
    @GetMapping("/web/stro/pharApply/pharApplyDetail")
    WrapperResponse<List<PharApplyDetailDTO>> pharApplyDetail(Map map);

    /**
     * @Method: getById()
     * @Description: 显示药品请领明细表
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 20:11
     * @Return: pharApplyDTO:领药申请的数据传输对象
     */
    @GetMapping("/web/stro/pharApply/getById")
    WrapperResponse<PharApplyDTO> getById(Map map);

    /**
     * @Method: queryStockNum()
     * @Description: 领药申请时 申请数量需要和库存数量做一个对比
     * @Param:  PharApplyDetailDTO库存明细数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 15:48
     * @Return:  stroStockDetailDTO库存明细数据传输对象
     */
    @GetMapping("/web/stro/pharApply/queryStockNum")
    WrapperResponse<PageDTO> queryStockNum(Map map);


    /**
     * @Method: queryStatus()
     * @Description: 查看当前领药申请的审核标志
     * @Param: PharApplyDTO 领药申请数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 16:55
     * @Return:
     */
    @GetMapping("/web/stro/pharApply/queryStatus")
    WrapperResponse<List<PharApplyDTO>> queryStatus(Map map);

    @GetMapping("/web/stro/pharApply/insertBatch")
    WrapperResponse<Boolean> insertBatch(Map map);


    /**
     * @Method: queryPageDeatil()
     * @Desciption: 根据出库库位来判断，领药申请的是材料还是药品
     * @Pramas: a
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/11/30
     * @Retrun: 药品/材料的分页数据传输对象
     */
    @GetMapping("/web/stro/pharApply/queryPageDetail")
    WrapperResponse<PageDTO> queryPageDeatil(Map map);

    /**
     * @Method printPharApply()
     * @Desrciption  打印领药申请单
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/20 10:49
     * @Return 领药申请数据传输对象
     **/
    @PostMapping("/web/stro/pharApply/printPharApply")
    WrapperResponse<List<PharApplyDTO>> printPharApply(Map map);

    /**
    * @Menthod queryStroOutPharApply
    * @Desrciption 查询需要出库领药申请记录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 15:19
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/web/stro/pharApply/queryStroOutPharApply")
    WrapperResponse<PageDTO> queryStroOutPharApply(Map map);

    /**
    * @Menthod updatePharApply
    * @Desrciption 出库领药申请
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 16:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/web/stro/pharApply/updatePharApply")
    WrapperResponse<Boolean> updatePharApply(Map map);

    /**
    * @Menthod applyOrderByminOrUp
    * @Desrciption 根据库存上下限生成领药申请单
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/12/15 15:03
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/web/stro/pharApply/applyOrderByminOrUp")
    WrapperResponse<Boolean> insertapplyOrderByminOrUp(Map map);

    /**
    * @Menthod queryStockApply
    * @Desrciption 查询领药申请明细库存是否足够
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/12/16 11:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @GetMapping("/web/stro/pharApply/queryStockApply")
    WrapperResponse<Map> queryStockApply(Map map);
}
