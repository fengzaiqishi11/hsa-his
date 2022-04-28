package cn.hsa.module.phar.pharapply.dao;

import cn.hsa.module.phar.pharapply.dto.PharApplyDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO;
import cn.hsa.module.stro.purchase.dto.StroPurchaseDetailDTO;

import java.util.List;
import java.util.Map;


/**
 * @PackageName: cn.hsa.module.phar.pharapply.dao
 * @Class_name: PharApplyDAO
 * @Description: 药房领药申请数据访问层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/7 21:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface PharApplyDAO {

    /**
     * @Method: queryPage()
     * @Description: 分页显示领药申请的信息
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:49
     * @Return: PharApplyDTO领药申请的数据传输对象集合
     */
    List<PharApplyDTO> queryPage(PharApplyDTO pharApplyDTO);

    /**
     * @Method: insert()
     * @Description: 新增领药申请
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: 影响的行数
     */
    int insert(PharApplyDTO pharApplyDTO);

    /**
     * @Method: update()
     * @Description: 修改领药申请
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: 影响的行数
     */
    int update(PharApplyDTO pharApplyDTO);

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
    int updateBatchCancelled(PharApplyDTO pharApplyDTO);

    /**
     * @Method: batchCheck()
     * @Description: 批量审核
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return:
     */
    Integer updateBatchCheck(PharApplyDTO pharApplyDTO);


    /**
     * @Method: queryFlag()
     * @Description: 作废前 判断对应的审核标识状态
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/29 10:36
     * @Return:
     */
    List<PharApplyDTO> queryFlag(PharApplyDTO pharApplyDTO);

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
     * @Method printPharApply()
     * @Desrciption  打印领药申请单
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/20 10:49
     * @Return 领药申请数据传输对象
     **/
    List<PharApplyDTO> selectPharApplyByIds(PharApplyDTO pharApplyDTO);

    List<String> queryIsExitPharApply(PharApplyDTO pharApplyDTO);

    /**
    * @Menthod updateIsOut
    * @Desrciption 修改状态
    *
    * @Param
    * [pharApplyDTOS]
    *
    * @Author jiahong.yang
    * @Date   2021/5/12 16:56
    * @Return java.lang.Boolean
    **/
    Boolean updateIsOut(PharApplyDTO pharApplyDTO);

    /**
    * @Menthod updateIsOutByOrderNo
    * @Desrciption 修改状态
    *
    * @Param
    * [pharApplyDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/13 10:03
    * @Return java.lang.Boolean
    **/
    Boolean updateIsOutByOrderNo(PharApplyDTO pharApplyDTO);

    /**
    * @Menthod queryPharApplyDetailAll
    * @Desrciption 查询所有领药明细
    *
    * @Param
    * [pharApplyDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/18 11:10
    * @Return java.util.List<cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO>
    **/
    List<PharApplyDetailDTO> queryPharApplyDetailAll(PharApplyDTO pharApplyDTO);

    List<PharApplyDetailDTO> queryNeedSupplementMin(PharApplyDTO pharApplyDTO);

    List<PharApplyDetailDTO> queryNeedSupplementUp(PharApplyDTO pharApplyDTO);

    List<PharApplyDetailDTO> queryNeedSupplementDate(PharApplyDTO pharApplyDTO);
}
