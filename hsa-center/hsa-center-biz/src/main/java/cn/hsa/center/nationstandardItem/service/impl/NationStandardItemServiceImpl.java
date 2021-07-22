package cn.hsa.center.nationstandardItem.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandardItem.bo.NationStandardItemBO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;
import cn.hsa.module.center.nationstandardItem.service.NationStandardItemService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准项目
 * <p>
 * 表说明：
 * (NationStandardItemDTO)表服务接口
 *
 * @author makejava
 * @since 2021-01-26 09:18:39
 */
@HsafRestPath("/service/center/nationStandardItem")
@Service("nationStandardItemService_provider")
public class NationStandardItemServiceImpl extends HsafService implements NationStandardItemService {


    @Resource
    private NationStandardItemBO nationStandardItemBO;

    /**
     * @Menthod getById
     * @Desrciption
     * @param nationStandardItemDTO 根据id查询国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO
     **/
    @Override
    public WrapperResponse<NationStandardItemDTO> getById(NationStandardItemDTO nationStandardItemDTO){
        return WrapperResponse.success(nationStandardItemBO.getById(nationStandardItemDTO));
    }


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardItemDTO 查询所有国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
     **/
    @Override
    public WrapperResponse<List<NationStandardItemDTO>> queryAll(NationStandardItemDTO nationStandardItemDTO){
        return WrapperResponse.success(nationStandardItemBO.queryAll(nationStandardItemDTO));
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardItemDTO 分页查询国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(NationStandardItemDTO nationStandardItemDTO){
        return WrapperResponse.success(nationStandardItemBO.queryPage(nationStandardItemDTO));
    }

    @Override
    public WrapperResponse<Boolean> saveNationStandardItem(Map map) {
        NationStandardItemDO nationStandardItemDO = null;
        try{
            nationStandardItemDO = MapUtils.get(map,"nationStandardItemDO");
            if(nationStandardItemDO == null){
                throw new AppException("参数不能为空");
            }
            if(StringUtils.isEmpty(nationStandardItemDO.getCode())){
                throw new AppException("项目编码不可为空");
            }
        }catch (AppException ae){
            WrapperResponse<Boolean> rt = WrapperResponse.fail(false);
            rt.setMessage(ae.getMessage());
            return rt;

        }

        if (StringUtils.isEmpty(nationStandardItemDO.getId())){
                nationStandardItemDO.setId(SnowflakeUtils.getId());
                nationStandardItemDO.setCrteTime(new Date());
                return WrapperResponse.success(nationStandardItemBO.saveNationStandardItem(nationStandardItemDO));
        }
        return  WrapperResponse.success(nationStandardItemBO.updateNationStandardItem(nationStandardItemDO));

    }

    /**
     * @Description: 更新国家标准材料信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/7 9:23
     * @Param dataMap 上传数据包容器主要包含一下三个基础项：
     *                 key为crteName,value为用户名，
     *                 key为crteId，value为用户id，
     *                 key为resultList,value为List<List<String>> 的数据集合
     **/
    @Override
    public void insertProcessedUploadData(Map<String, Object> dataMap) {
        nationStandardItemBO.saveProcessedUploadDataBatch(dataMap);
    }
}