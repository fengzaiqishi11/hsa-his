package cn.hsa.module.phar.stockinquery.dao;

import cn.hsa.module.phar.pharapply.dto.StroOutDTO;
import cn.hsa.module.phar.pharapply.entity.StroOut;
import cn.hsa.module.phar.pharapply.entity.StroOutDetail;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.stro.outinstro.stockinquery.dao
 * @class_name: StockInQueryDAO
 * @Description: 药房入库确认与查询数据访问层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/25 15:54
 * @Company: 创智和宇
 **/
public interface StockInQueryDAO {

    /**
     * @Method: queryPage()
     * @Description: 分页查询所有的药品出库与确认信息
     * @Param: StroOut 药房药库数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:11
     * @Return:
     */
    List<StroOutDTO> queryPage(StroOutDTO stroOutDTO);
    
    /**
     * @Method: batchCheck()
     * @Description: 批量入库审核药房数据信息
     * @Param:  StroOut 药房药库数据传输对象
     * 1.ids: 批量审核id list集合
     * 2. hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/25 16:13
     * @Return: 
     */
    int updateBatchCheck(StroOut stroOut);

    /**
     * @Method: stockInDetail()
     * @Description: 查询药房入库明细信息
     * @Param: stroOutinDetailDTO出入库明细数据传输DTO对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/26 20:08
     * @Return:
     */
    List<StroOutDetail> queryStockInDetail(StroOutDTO stroOutDTO);

    /**
     * @Method: queryBatch()
     * @Description: 批量查询药品出入库的审核标识
     * @Param: StroOut药房药库数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/27 19:48
     * @Return:
     */
    List<StroOutDTO> queryBatch(StroOutDTO stroOutDTO);

    /**
     * @Method: queryDeatilById()
     * @Description: 根据入库id 查询入库明细数据 更新库存信息
     * @Param: StroOut药房药库数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/7/27 19:48
     * @Return:
     */
    List<StroStockDetailDTO> queryDeatilById(StroOutDTO stroOutDTO);
}
