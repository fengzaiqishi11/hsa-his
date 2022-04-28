package cn.hsa.module.phar.pharapply.dao;

import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharapply.dao
 * @class_name: PharApplyDetailDAO
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/29 9:44
 * @Company: 创智和宇
 **/
public interface PharApplyDetailDAO {

    Integer insert(List<PharApplyDetailDTO> list);

    /**
     * @Method: pharApplyDetai()
     * @Description: 分页查询领药申请明细数据
     * @Param: pharApplyDetailDTO领药申请数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/29 14:09
     * @Return:
     */
    List<PharApplyDetailDTO> pharApplyDetail(PharApplyDetailDTO pharApplyDetailDTO);

    /**
     * @Method: deletePadById
     * @Description: 对领药申请进行编辑 删除明细数据
     * @Param: 1、hospCode 编码
     * 2、list 封装要删除的明细数据集合
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/29 11:02
     * @Return:
     */
    int deletePadById(@Param("ids") List<PharApplyDetailDTO> ids, @Param("hospCode") String hospCode);

    /**
     * @Method: getById()
     * @Description: 根据领药申请的主键id 查找领药申请的明细的数据
     * @Param: 1、 id       领药申请的主键id
     * 2. hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/29 11:02
     * @Return:
     */
    List<PharApplyDetailDTO> getById(@Param("id") String id, @Param("hospCode") String hospCode);

    /**
     * @Method: deletePadById
     * @Description: 对领药申请进行编辑 新增明细数据
     * @Param: 1、hospCode 编码
     * 2、list 封装要新增的明细数据集合
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/29 11:02
     * @Return:
     */
    int update(@Param("list") List<PharApplyDetailDTO> list, @Param("hospCode") String hospCode);

    /**
     * @Method: deletePadById
     * @Description: 批量申请领药明细数据时,根据主键id关联申领单号 查询出所有的领药明细数据
     *
     * @Param: 1、hospCode 编码
     * 2、list 封装要新增的明细数据集合
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/29 11:02
     * @Return: PharApplyDetailDTO领药申请明细集合
     */
    List<PharApplyDetailDTO> queryDetailById(PharApplyDTO pharApplyDTO);

    /**
     * @Method: insertStroOutDetail()
     * @Description: 批量申请领药明细数据时,根据主键id关联申领单号 查询出所有的领药明细数据 把所有查询出来的明细数据 插入到出库明细表中
     *
     * @Param: 1、applyDetailDTOList 领药申请明细数据
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/29 11:02
     * @Return: int
     */
    int insertStroOutDetail(@Param("applyDetailDTOList") List<PharApplyDetailDTO> applyDetailDTOList);

    /**
     * @Method: queryStockNum()
     * @Description: 领药申请时 申请数量需要和库存数量做一个对比
     * @Param: PharApplyDetailDTO库存明细数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 15:48
     * @Return: PharApplyDetailDTO领药申请明细
     */
    List<StroStockDTO> queryStockNum(StroStockDTO stockDTO);

    /**
     * @Method: selectTotalPrice()
     * @Description: 计算每次请领的总金额 和零售总金额
     * @Param:  id：请领id  hospCode：医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/28 16:16
     * @Return: PharApplyDetailDTO领药申请明细
     */
    PharApplyDetailDTO selectTotalPrice(String id, String hospCode);

    boolean deleteApplyDetail(@Param("id") String id, @Param("hospCode") String hospCode);

    /**
     * @Method: queryAllDrug()
     * @Desciption: 查询药品信息
     * @Pramas: pharApplyDetailDTO领药申请明细数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/11/30
     * @Retrun: 药品的分页数据传输对象
     */
    List<PharApplyDetailDTO> queryAllDrug(PharApplyDetailDTO pharApplyDetailDTO);

    /**
    * @Menthod queryAll
    * @Desrciption 查询所有材料药品
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2021/3/31 9:34
    * @Return java.util.List<cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO>
    **/
    List<PharApplyDetailDTO> queryAll(PharApplyDetailDTO pharApplyDetailDTO);

    /**
     * @Method: queryAllMaterial()
     * @Desciption: 查询材料信息
     * @Pramas: pharApplyDetailDTO领药申请明细数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/11/30
     * @Retrun: 材料的分页数据传输对象
     */
    List<PharApplyDetailDTO> queryAllMaterial(PharApplyDetailDTO pharApplyDetailDTO);

    /**
    * @Menthod queryStroOutPharApply
    * @Desrciption 查询需要出库领药申请记录
    *
    * @Param
    * [pharApplyDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 15:25
    * @Return java.util.List<cn.hsa.module.phar.pharapply.dto.PharApplyDTO>
    **/
    List<PharApplyDTO> queryStroOutPharApply(PharApplyDTO pharApplyDTO);

    /**
    * @Menthod getDeptById
    * @Desrciption 根据科室id查询科室
    *
    * @Param
    * [baseDeptDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/5/12 9:09
    * @Return cn.hsa.module.base.dept.dto.BaseDeptDTO
    **/
    BaseDeptDTO getDeptById(BaseDeptDTO baseDeptDTO);

    /**
    * @Menthod queryStockApply
    * @Desrciption 查询领药申请明细库存是否足够
    *
    * @Param
    * [pharApplyDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/12/16 11:38
    * @Return java.lang.Boolean
    **/
    List<StroStockDTO> queryStockApply(PharApplyDetailDTO pharApplyDetailDTO);
    /**
     * @Meth: getDetail
     * @Description: 获得明细数据
     * @Param: [pharApplyDTO]
     * @return: java.util.List<cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO>
     * @Author: zhangguorui
     * @Date: 2022/4/11
     */
    List<PharApplyDetailDTO> getDetail(PharApplyDTO pharApplyDTO);
}
