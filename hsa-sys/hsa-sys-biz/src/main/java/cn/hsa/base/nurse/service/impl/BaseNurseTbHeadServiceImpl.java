package cn.hsa.base.nurse.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.nurse.bo.BaseNurseTbHeadBO;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;
import cn.hsa.module.base.nurse.service.BaseNurseTbHeadService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.nurse.service.impl
 * @Class_name: BaseNurseTbHeadServiceImpl
 * @Describe: 护理单据表头service实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/21 15:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/base/nurseTbHead")
@Service("baseNurseTbHeadService_provider")
public class BaseNurseTbHeadServiceImpl extends HsafService implements BaseNurseTbHeadService {

    @Resource
    private BaseNurseTbHeadBO baseNurseTbHeadBO;

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据表头
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(baseNurseTbHeadBO.queryPage(MapUtils.get(map, "baseNurseTbHeadDTO")));
    }

    /**
     * @Method getTbHeadColumns
     * @Desrciption 获取动态表头列格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < BaseNurseTbHeadDTO>>
     **/
    @Override
    public WrapperResponse<List<BaseNurseTbHeadDTO>> getTbHeadColumns(Map map) {
        return WrapperResponse.success(baseNurseTbHeadBO.getTbHeadColumns(MapUtils.get(map, "baseNurseOrderDTO")));
    }

    /**
     * @Method saveTbHead
     * @Desrciption 护理单据表头格式新增/修改
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveTbHead(Map map) {
        return WrapperResponse.success(baseNurseTbHeadBO.saveTbHead(map));
    }

    /**
     * @Method deleteTbHead
     * @Desrciption 护理单据表头格式删除
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> deleteTbHead(Map map) {
        return WrapperResponse.success(baseNurseTbHeadBO.deleteTbHead(MapUtils.get(map, "baseNurseTbHeadDTOS")));
    }

    /**
     * @Method getById
     * @Desrciption 根据主键获取唯一护理单据表头格式
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<BaseNurseTbHeadDTO>
     **/
    @Override
    public WrapperResponse<BaseNurseTbHeadDTO> getById(Map map) {
        return WrapperResponse.success(baseNurseTbHeadBO.getById(MapUtils.get(map, "baseNurseTbHeadDTO")));
    }

    /**
     * @Method querySeqNo
     * @Desrciption 查询护理单据下顺序号的最大值，自动填充前端
     * @Param baseNurseTbHeadDTO【bnoCode--护理单据】
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return WrapperResponse<Integer>
     **/
    @Override
    public WrapperResponse<Integer> querySeqNo(Map map) {
        return WrapperResponse.success(baseNurseTbHeadBO.querySeqNo(MapUtils.get(map, "baseNurseTbHeadDTO")));
    }

    /**
     * @Method queryTbHeadByBnoCode
     * @Desrciption 根据护理单据查询出所有表头List，供选择上级标题使用
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseTbHeadDTO>
     **/
    @Override
    public WrapperResponse<List<BaseNurseTbHeadDTO>> queryTbHeadByBnoCode(Map map) {
        return WrapperResponse.success(baseNurseTbHeadBO.queryTbHeadByBnoCode(MapUtils.get(map, "baseNurseOrderDTO")));
    }

    /**
     * @Method queryItemCode
     * @Desrciption 查询出所有的护理单据itemCode编码list，用于页面下拉选择项目编码
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return List<Map < String, String>>
     **/
    @Override
    public WrapperResponse<List<Map<String, String>>> queryItemCode(Map map) {
        return WrapperResponse.success(baseNurseTbHeadBO.queryItemCode(MapUtils.get(map, "baseNurseOrderDTO")));
    }
}
