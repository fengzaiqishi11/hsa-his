package cn.hsa.module.phar;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.phar.pharapply.dto.StroOutDTO;
import cn.hsa.module.phar.pharapply.entity.StroOutDetail;
import cn.hsa.module.phar.stockinquery.service.StockInQueryService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro
 * @class_name: stockInQueryController
 * @Description: 药房入库查询与确认
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/25 15:42
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/stro/stockInQuery")
public class StockInQueryController extends BaseController {

    /**
     * 入库查询与确认service层消费者接口
     */
    @Resource
    private StockInQueryService stockInQueryService_consumer;
    
    /**
     * @Method: queryPage()
     * @Description: 分页查询入库查询与确认信息
     * @Param: stroOut 药房药库出入库数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 15:56
     * @Return: PageDTO
     */
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map=new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            stroOutDTO.setInStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        map.put("stroOutDTO", stroOutDTO);
        return this.stockInQueryService_consumer.queryPage(map);
    }

    /**
     * @Method: updateBatchCheck()
     * @Description: 批量入库审核药房数据信息
     * @Param: stroOut 药房药库数据传输对象
     * 1.ids: 批量审核id list集合
     * 2. hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:13
     * @Return: boolean
     */
    @PostMapping("/batchCheck")
    @NoRepeatSubmit
    public WrapperResponse<Boolean> updateBatchCheck(@RequestBody StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        /**
         * 判断批量审核参数是否为空
         */
        if(stroOutDTO.getOrderNos().size()<1){
            throw new AppException("要审核的数据为空");
        }
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map=new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        stroOutDTO.setOkAuditId(sysUserDTO.getId());
        if (sysUserDTO.getLoginBaseDeptDTO() != null) {
            stroOutDTO.setInStockId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        stroOutDTO.setOkAuditName(sysUserDTO.getName());
        map.put("stroOutDTO", stroOutDTO);
        return this.stockInQueryService_consumer.updateBatchCheck(map);
    }
    /**
     * @Method: queryStockInDetail()
     * @Description: 药房入库明细信息
     * @Param:  stroOutinDetailDTO出入库明细数据传输DTO对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/26 20:08
     * @Return:
     */
    @GetMapping("/stockInDetail")
    public WrapperResponse<List<StroOutDetail>>queryStockInDetail(StroOutDTO stroOutDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map=new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        stroOutDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("stroOutDTO", stroOutDTO);
        return this.stockInQueryService_consumer.queryStockInDetail(map);
    }

}
