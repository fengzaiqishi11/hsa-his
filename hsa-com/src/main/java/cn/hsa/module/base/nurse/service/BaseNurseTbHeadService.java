package cn.hsa.module.base.nurse.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.nurse.service
 * @Class_name: BaseNurseTbHeadService
 * @Describe: 护理单据表头service
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/21 15:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-base")
public interface BaseNurseTbHeadService {
    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据表头
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/service/base/nurseTbHead/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method getTbHeadColumns
     * @Desrciption 获取动态表头列格式
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<BaseNurseTbHeadDTO>>
     **/
    @GetMapping("/service/base/nurseTbHead/getTbHeadColumns")
    WrapperResponse<List<BaseNurseTbHeadDTO>> getTbHeadColumns(Map map);

    /**
     * @Method saveTbHead
     * @Desrciption 护理单据表头格式新增/修改
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/nurseTbHead/saveTbHead")
    WrapperResponse<Boolean> saveTbHead(Map map);

    /**
     * @Method deleteTbHead
     * @Desrciption 护理单据表头格式删除
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/service/base/nurseTbHead/deleteTbHead")
    WrapperResponse<Boolean> deleteTbHead(Map map);

    /**
     * @Method getById
     * @Desrciption 根据主键获取唯一护理单据表头格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<BaseNurseTbHeadDTO>
     **/
    @GetMapping("/service/base/nurseTbHead/getById")
    WrapperResponse<BaseNurseTbHeadDTO> getById(Map map);

    /**
     * @Method querySeqNo
     * @Desrciption 查询护理单据下顺序号的最大值，自动填充前端
     * @Param baseNurseTbHeadDTO【bnoCode--护理单据】
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return WrapperResponse<Integer>
     **/
    @PostMapping("/service/base/nurseTbHead/querySeqNo")
    WrapperResponse<Integer> querySeqNo(Map map);

    /**
     * @Method queryTbHeadByBnoCode
     * @Desrciption 根据护理单据查询出所有表头List，供选择上级标题使用
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseTbHeadDTO>
     **/
    @PostMapping("/service/base/nurseTbHead/queryTbHeadByBnoCode")
    WrapperResponse<List<BaseNurseTbHeadDTO>> queryTbHeadByBnoCode(Map map);

    /**
     * @Method queryItemCode
     * @Desrciption 查询出所有的护理单据itemCode编码list，用于页面下拉选择项目编码
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return List<Map < String, String>>
     **/
    @PostMapping("/service/base/nurseTbHead/queryItemCode")
    WrapperResponse<List<Map<String, String>>> queryItemCode(Map map);
}
