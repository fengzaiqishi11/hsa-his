package cn.hsa.module.stro;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.stro.stock.dto.ItemProfitStatisticsDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import cn.hsa.module.stro.stock.service.StroStockService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.stro
 * @Class_name: StroStockController
 * @Describe: 库存查询控制层
 * @Author: zengfeng
 * @Eamil: zengfeng@powersi.com.cn
 * @Date: 2020/7/23 14:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/stro/strostock")
@Slf4j
public class StroStockController extends BaseController {
    /**
     * 药库查询dubbo消费者接口
     */
    @Resource
    private StroStockService stroStockService_consumer;

    @Resource
    private BaseDeptService baseDeptService_consumer;

    /**
     * @Method getById()
     * @Description 通过ID查询单条数据
     * @Param
     * 1、id：主键ID
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<StroStockDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<StroStockDTO> getById(String id, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("id",id);
        return stroStockService_consumer.queryById(map);
    }
    /**
     * @Method queryAll()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * stroStockDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<StroStockDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<StroStockDTO>> queryAll(StroStockDTO stroStockDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDTO.setHospCode(sysUserDTO.getHospCode());
        stroStockDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroStockDTO);
        return stroStockService_consumer.queryAll(map);
    }
    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件分页查询
     *
     * @Param
     * stroStockDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(StroStockDTO stroStsockDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStsockDTO.setHospCode(sysUserDTO.getHospCode());
        stroStsockDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        stroStsockDTO.setLoginDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
        stroStsockDTO.setLoginTypeIdentity(sysUserDTO.getLoginBaseDeptDTO().getTypeIdentity());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroStsockDTO);
        return stroStockService_consumer.queryPage(map);
    }
    /**
     * @Meth: queryAllStockPage
     * @Description: 查询全院的库存
     * @Param: [stroStsockDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    @GetMapping("/queryAllStockPage")
    public WrapperResponse<PageDTO> queryAllStockPage(StroStockDTO stroStockDTO,
                                                      HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroStockDTO",stroStockDTO);
        return stroStockService_consumer.queryAllStockPage(map);
    }
    /**
     * @Method queryPageByOutptOrInpt
     * @Desrciption 提供给门诊医生站、住院医生站的药品查询接口
     * @Param [stroStockDTO]
     * @Author zhangguorui
     * @Date   2021/7/16 14:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @GetMapping("/queryPageByOutptOrInpt")
    public WrapperResponse<PageDTO> queryPageByOutptOrInpt(StroStockDTO stroStockDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroStockDTO",stroStockDTO);
        return stroStockService_consumer.queryPageByOutptOrInpt(map);
    }
    /**
     * @Method queryPage()
     * @Description 通过实体作为筛选条件分页查询
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPageDetail")
    public WrapperResponse<PageDTO> queryPageDetail(StroStockDetailDTO stroStockDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDetail.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(stroStockDetail.getBizId())) {
            stroStockDetail.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
            stroStockDetail.setLoginDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
            stroStockDetail.setLoginTypeIdentity(sysUserDTO.getLoginBaseDeptDTO().getTypeIdentity());
        } else { //如果自己传入库位ID,那么就是用传入的库位信息
            BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
            baseDeptDTO.setId(stroStockDetail.getBizId());
            baseDeptDTO.setHospCode(stroStockDetail.getHospCode());
            Map map = new HashMap();
            map.put("hospCode", stroStockDetail.getHospCode());
            map.put("baseDeptDTO", baseDeptDTO);
            BaseDeptDTO deptDTO = baseDeptService_consumer.getById(map).getData();
            stroStockDetail.setLoginDeptType(deptDTO.getTypeCode());
            stroStockDetail.setLoginTypeIdentity(deptDTO.getTypeIdentity());
        }
        if (stroStockDetail.getIsMun() == null) {
            stroStockDetail.setIsMun(false);
        }
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroStockDetail);
        return stroStockService_consumer.queryPageDetail(map);
    }

    /**
     * @Method: queryPageDetailCancleNo
     * @Description: 批号合并查询
     * @Param: [stroStockDetail]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/3/4 15:05
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPageDetailCancleNo")
    public WrapperResponse<PageDTO> queryPageDetailCancleNo(StroStockDetailDTO stroStockDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDetail.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(stroStockDetail.getBizId())) {
            stroStockDetail.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
            stroStockDetail.setLoginDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
            stroStockDetail.setLoginTypeIdentity(sysUserDTO.getLoginBaseDeptDTO().getTypeIdentity());
        } else { //如果自己传入库位ID,那么就是用传入的库位信息
            BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
            baseDeptDTO.setId(stroStockDetail.getBizId());
            baseDeptDTO.setHospCode(stroStockDetail.getHospCode());
            Map map = new HashMap();
            map.put("hospCode", stroStockDetail.getHospCode());
            map.put("baseDeptDTO", baseDeptDTO);
            BaseDeptDTO deptDTO = baseDeptService_consumer.getById(map).getData();
            stroStockDetail.setLoginDeptType(deptDTO.getTypeCode());
            stroStockDetail.setLoginTypeIdentity(deptDTO.getTypeIdentity());
        }
        if (stroStockDetail.getIsMun() == null) {
            stroStockDetail.setIsMun(false);
        }
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroStockDetail);
        return stroStockService_consumer.queryPageDetailCancleNo(map);
    }

    /**
     * @Method: queryStockDetails
     * @Description:分页获取库存明细
     * @Param: [stroStockDetail]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/5 14:30
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryStockDetails")
    public WrapperResponse<PageDTO> queryStockDetails(StroStockDetailDTO stroStockDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDetail.setHospCode(sysUserDTO.getHospCode());
        stroStockDetail.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        stroStockDetail.setLoginDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
        stroStockDetail.setLoginTypeIdentity(sysUserDTO.getLoginBaseDeptDTO().getTypeIdentity());
        if (stroStockDetail.getIsMun() == null) {
            stroStockDetail.setIsMun(true);
        }
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroStockDetail);
        return stroStockService_consumer.queryStockDetails(map);
    }
    /**
     * @Meth: queryAllStockDetails
     * @Description: 查询所有库存明细
     * @Param: [stroStockDetail, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/26
     */
    @GetMapping("/queryAllStockDetails")
    public WrapperResponse<PageDTO> queryAllStockDetails(StroStockDetailDTO stroStockDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDetail.setHospCode(sysUserDTO.getHospCode());
        if (stroStockDetail.getIsMun() == null) {
            stroStockDetail.setIsMun(true);
        }
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("stroStockDetail",stroStockDetail);
        return stroStockService_consumer.queryAllStockDetails(map);
    }
    /**
     * @Method queryAllDetail()
     * @Description 通过实体作为筛选条件查询
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return WrapperResponse<List<StroStockDetailDTO>>
     **/
    @GetMapping("/queryAllDetail")
    public WrapperResponse<List<StroStockDetailDTO>> queryAllDetail(StroStockDetailDTO stroStockDetail, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDetail.setHospCode(sysUserDTO.getHospCode());
        stroStockDetail.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroStockDetail);
        return stroStockService_consumer.queryAllDetail(map);
    }

    /**
     * @Method update()
     * @Description 更新
     *
     * @Param
     * StroStockDetailDTO 实例对象
     *
     * @Author lj
     * @Date 2020/7/30 20:53
     * @Return  WrapperResponse<Boolean>
     **/
    @PostMapping("/update")
    WrapperResponse<Boolean> update(@RequestBody StroStockDTO stroStockDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStockDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroStockDTO);
        return stroStockService_consumer.update(map);
    }

    /**库存效期查询
    * @Method queryValidityWarningPage
    * @Desrciption
    * @param stroStsockDTO
    * @Author liuqi1
    * @Date   2021/4/22 15:24
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryValidityWarningPage")
    public WrapperResponse<PageDTO> queryValidityWarningPage(StroStockDTO stroStsockDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        stroStsockDTO.setHospCode(sysUserDTO.getHospCode());
        stroStsockDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
        stroStsockDTO.setLoginDeptType(sysUserDTO.getLoginBaseDeptDTO().getTypeCode());
        stroStsockDTO.setLoginTypeIdentity(sysUserDTO.getLoginBaseDeptDTO().getTypeIdentity());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("bean",stroStsockDTO);
        return stroStockService_consumer.queryValidityWarningPage(map);
    }


    /**
    * @Menthod queryStockByBatchAll
    * @Desrciption 查询库存明细中批号汇总
    *
    * @Param
    * [stroStsockDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/6/2 14:44
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping("/queryStockByBatchAll")
    public WrapperResponse<PageDTO> queryStockByBatchAll(StroStockDetailDTO stroStockDetailDTO, HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO sysUserDTO = getSession(req, res);
      stroStockDetailDTO.setHospCode(sysUserDTO.getHospCode());
      stroStockDetailDTO.setBizId(sysUserDTO.getLoginBaseDeptDTO().getId());
      Map map = new HashMap();
      map.put("hospCode",sysUserDTO.getHospCode());
      map.put("stroStockDetailDTO",stroStockDetailDTO);
      return stroStockService_consumer.queryStockByBatchAll(map);
    }

    /**
     *  查询药品和材料的利润统计报表
     * @Author liudawen
     * @Param [stroStockDetailDTO, req, res]
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     * @Throws
     * @Date 2022/4/18 17:30
     **/
    @GetMapping("/queryDrugAndMaterialProfit")
    public WrapperResponse<PageDTO> queryDrugAndMaterialProfit(ItemProfitStatisticsDTO itemProfitStatisticsDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        itemProfitStatisticsDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("itemProfitStatisticsDTO",itemProfitStatisticsDTO);
        return stroStockService_consumer.queryDrugAndMaterialProfit(map);
    }
    /**
     * @Author gory
     * @Description 同步过期数量
     * @Date 2022/9/7 9:30
     * @Param [req, res]
     **/
    @PostMapping("/updateOccupyByExpire")
    public WrapperResponse<Boolean> updateOccupyByExpire(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        return stroStockService_consumer.updateOccupyByExpire(map);
    }
}
