package cn.hsa.phar.pharapply.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.phar.pharapply.bo.PharApplyBO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDTO;
import cn.hsa.module.phar.pharapply.dto.PharApplyDetailDTO;
import cn.hsa.module.phar.pharapply.service.PharApplyService;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.phar.pharapply.service.impl
 * @class_name: PharApplyServiceImpl
 * @Description: 领药申请服务实现层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/30 21:10
 * @Company: 创智和宇
 **/
@HsafRestPath("/service/stro/pharApply")
@Slf4j
@Service("pharApplyService_provider")
public class PharApplyServiceImpl extends HsafService implements PharApplyService {

    /**
     * 领药申请bo层
     */
    @Resource
    private PharApplyBO pharApplyBO;

    @Resource
    private StroStockBO stroStockBO;

    /**
     * @Method: queryPage()
     * @Description: 分页显示领药申请的信息
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:49
     * @Return: PharApplyDTO领药申请的数据传输对象集合
     */
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(Map map) {
        PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
        PageDTO pageDTO = pharApplyBO.queryPage(pharApplyDTO);
        return WrapperResponse.success(pageDTO);
    }

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
    @Override
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    public WrapperResponse<Boolean> insert(Map map) {
        PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
        try {
            // 参数校验
            if (pharApplyDTO== null) {
                return WrapperResponse.error(400,"新增参数不能为空",null);
            }
            return WrapperResponse.success(pharApplyBO.insert(pharApplyDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: update()
     * @Description: 修改领药申请
     * @Param: pharApplyDTO领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:38
     * @Return: 影响的行数
     */
    @Override
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    public WrapperResponse<Boolean> update(Map map) {
        PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
        try {
            // 参数校验
            if (pharApplyDTO== null) {
                return WrapperResponse.error(400,"修改参数不能为空",null);
            }
            return WrapperResponse.success(pharApplyBO.update(pharApplyDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }

    }

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
    @Override
    @HsafRestPath(value = "/batchCancelled", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateBatchCancelled(Map map) {
        PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
        return WrapperResponse.success(pharApplyBO.updateBatchCancelled(pharApplyDTO));
    }

    /**
     * @Method: pharCheck()
     * @Description: 批量审核
     * @Param 1.pharApplyDTO:领药申请的数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 16:54
     * @Return: boolean
     */

    @Override
    @HsafRestPath(value = "/batchCheck", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updateBatchCheck(Map map) {
        PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
        try {
            // 参数校验
            if (pharApplyDTO== null) {
                return WrapperResponse.error(400,"批量审核参数不能为空",null);
            }
            return WrapperResponse.success(pharApplyBO.updateBatchCheck(pharApplyDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }

    }

    /**
     * @Method: pharApplyDetail()
     * @Description: 显示药品请领明细表
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/28 20:11
     * @Return: PageDTO
     */
    @Override
    @HsafRestPath(value = "/pharApplyDetail", method = RequestMethod.GET)
    public WrapperResponse<List<PharApplyDetailDTO>> pharApplyDetail(Map map) {
        PharApplyDetailDTO pharApplyDetailDTO = MapUtils.get(map, "pharApplyDetailDTO");
        return WrapperResponse.success(pharApplyBO.pharApplyDetail(pharApplyDetailDTO));
    }

    /**
     * @Method: getById()
     * @Description: 根据领药申请的主键id 查找数据
     * @Param: pharApplyDetailDTO 药品请领明细表数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/31 10:39
     * @Return: PharApplyDTO
     */
    @Override
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    public WrapperResponse<PharApplyDTO> getById(Map map) {
        PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
        return WrapperResponse.success(pharApplyBO.getById(pharApplyDTO));
    }

    /**
     * @Method: queryStockNum()
     * @Description: 领药申请时 申请数量需要和库存数量做一个对比
     * @Param:  PharApplyDetailDTO库存明细数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 15:48
     * @Return:  stroStockDetailDTO库存明细数据传输对象
     */
    @Override
    @HsafRestPath(value = "/queryStockNum", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryStockNum(Map map) {
        StroStockDTO stroStockDTO = MapUtils.get(map, "stroStockDTO");
        PageDTO pageDTO =pharApplyBO.queryStockNum(stroStockDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method: queryStatus()
     * @Description: 查看当前领药申请的审核标志
     * @Param: PharApplyDTO 领药申请数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/24 16:55
     * @Return:
     */
    @Override
    @HsafRestPath(value = "/queryStatus", method = RequestMethod.GET)
    public WrapperResponse<List<PharApplyDTO>> queryStatus(Map map) {
        PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
        return WrapperResponse.success(pharApplyBO.queryStatus(pharApplyDTO));
    }

    /**
     * @Method insertBatch
     * @Desrciption 批量新增领药单
       @params [map]
     * @Author chenjun
     * @Date   2020/9/23 10:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insertBatch(Map map) {
        return WrapperResponse.success(pharApplyBO.insertBatch(map));
    }

    /**
     * @Method: queryPageDeatil()
     * @Desciption: 根据出库库位来判断，领药申请的是材料还是药品
     * @Pramas: a
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/11/30
     * @Retrun: 药品/材料的分页数据传输对象
     */
    @Override
    @HsafRestPath(value = "/queryPageDetail", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPageDeatil(Map map) {
        PharApplyDetailDTO pharApplyDetailDTO = MapUtils.get(map, "pharApplyDetailDTO");
        return WrapperResponse.success(pharApplyBO.queryPageDetail(pharApplyDetailDTO));
    }


    /**
     * @Method printPharApply()
     * @Desrciption  打印领药申请单
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/20 10:49
     * @Return 领药申请数据传输对象
     **/
    @Override
    @HsafRestPath(value = "/printPharApply", method = RequestMethod.POST)
    public WrapperResponse<List<PharApplyDTO>> printPharApply(Map map) {
        PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
        return WrapperResponse.success(pharApplyBO.printPharApply(pharApplyDTO));
    }

    /**
    * @Menthod queryStroOutPharApply
    * @Desrciption 查询需要出库领药申请记录
    *
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 15:20
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryStroOutPharApply(Map map) {
      PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
      return WrapperResponse.success(pharApplyBO.queryStroOutPharApply(pharApplyDTO));
    }

    /**
    * @Menthod updatePharApply
    * @Desrciption 出库领药申请
    *b
    * @Param
    * [map]
    *
    * @Author jiahong.yang
    * @Date   2021/5/11 16:51
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> updatePharApply(Map map) {
      PharApplyDTO pharApplyDTO = MapUtils.get(map, "pharApplyDTO");
      return WrapperResponse.success(pharApplyBO.updatePharApply(pharApplyDTO));
    }
}
