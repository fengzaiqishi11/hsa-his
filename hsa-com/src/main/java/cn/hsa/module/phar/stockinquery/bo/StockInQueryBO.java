package cn.hsa.module.phar.stockinquery.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.phar.pharapply.dto.StroOutDTO;
import cn.hsa.module.phar.pharapply.entity.StroOutDetail;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.stro.outinstro.stockinquery.bo
 * @class_name: StockInQueryBO
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/25 15:54
 * @Company: 创智和宇
 **/
public interface StockInQueryBO {

    /**
     * @Method: queryPage()
     * @Description: 分页查询所有的药品出库与确认信息
     * @Param: stroOutinDTO 药房药库数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:11
     * @Return: PageDTO
     */
    PageDTO queryPage(StroOutDTO stroOutDTO );

    /**
     * @Method: batchCheck()
     * @Description: 批量入库审核药房数据信息
     * @Param: stroOutinDTO 药房药库数据传输对象
     * 1.ids: 批量审核id list集合
     * 2. hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:13
     * @Return: boolean
     */
    boolean updateBatchCheck(StroOutDTO stroOutDTO );

    /**
     * @Method: stockInDetail()
     * @Description: 药房入库明细信息
     * @Param: stroOutinDetailDTO出入库明细数据传输DTO对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/26 20:08
     * @Return:
     */
    List<StroOutDetail> queryStockInDetail(StroOutDTO stroOutDTO );
}
