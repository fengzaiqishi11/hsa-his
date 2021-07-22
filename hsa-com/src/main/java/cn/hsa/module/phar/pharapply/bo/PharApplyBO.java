package cn.hsa.module.phar.pharapply.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.phar.pharapply.bo
 * @class_name: PharApplyBO
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/28 17:00
 * @Company: 创智和宇
 **/
public interface PharApplyBO {
    /**
     * @Method: queryPage()
     * @Description: 分页显示领药申请的信息
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:49
     * @Return: PharApplyDTO领药申请的数据传输对象集合
     */
    PageDTO queryPage(PharApplyDTO pharApplyDTO);

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
    boolean insert(PharApplyDTO pharApplyDTO);

    /**
     * @Method: update()
     * @Description: 修改领药申请
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: 影响的行数
     */
    boolean update(PharApplyDTO pharApplyDTO);

    /**
     * @Method: batchCancelled()
     * @Description: 批量作废
     * @Param: 1.hospCode医院编码
     * 2.id主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return:
     */
    boolean updateBatchCancelled(PharApplyDTO pharApplyDTO);

    /**
     * @Method: batchCheck()
     * @Description: 批量审核
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return:
     */
    boolean updateBatchCheck(PharApplyDTO pharApplyDTO);

    /**
     * @Method: pharApplyDetail()
     * @Description: 显示药品请领明细表
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 20:11
     * @Return:
     */
    List<PharApplyDetailDTO> pharApplyDetail(PharApplyDetailDTO pharApplyDetailDTO);

    /**
     * @Method: getById()
     * @Description: 查询领药申请的信息
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * 1.id:主键id
     * 2.hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/31 9:16
     * @Return:
     */
    PharApplyDTO getById(PharApplyDTO pharApplyDTO);


    /**
     * @Method: queryStockNum()
     * @Description: 领药申请时 申请数量需要和库存数量做一个对比
     * @Param:  PharApplyDetailDTO库存明细数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 15:48
     * @Return:  stroStockDetailDTO库存明细数据传输对象
     */
    PageDTO queryStockNum(StroStockDTO stroStockDTO);

    /**
     * @Method: queryStatus()
     * @Description: 查看当前领药申请的审核标志
     * @Param: PharApplyDTO 领药申请数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 16:55
     * @Return:
     */
    List<PharApplyDTO> queryStatus(PharApplyDTO pharApplyDTO);

    /**
     * @Method insertBatch
     * @Desrciption 批量新增
       @params [map]
     * @Author chenjun
     * @Date   2020/9/23 10:29
     * @Return java.lang.Boolean
    **/
    Boolean insertBatch(Map map);

    /**
     * @Method: queryPageDeatil()
     * @Desciption: 根据出库库位来判断，领药申请的是材料还是药品
     * @Pramas: a
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/11/30
     * @Retrun: 药品/材料的分页数据传输对象
     */
    PageDTO queryPageDetail(PharApplyDetailDTO pharApplyDetailDTO);


    /**
     * @Method printPharApply()
     * @Desrciption  打印领药申请单
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/20 10:49
     * @Return 领药申请数据传输对象
     **/
    List<PharApplyDTO> printPharApply(PharApplyDTO pharApplyDTO);

    /**
    * @Menthod queryStroOutPharApply
    * @Desrciption 查询需要出库领药申请记录
    *
    * @Param
    * [pharApplyDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 15:20
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryStroOutPharApply(PharApplyDTO pharApplyDTO);

    /**
    * @Menthod updatePharApply
    * @Desrciption 出库领药申请
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 16:51
    * @Return java.lang.Boolean
    **/
    Boolean updatePharApply(PharApplyDTO pharApplyDTO);

}
