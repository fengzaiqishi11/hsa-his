package cn.hsa.module.base.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.nurse.bo
 * @Class_name: BaseNurseTbHeadBO
 * @Describe: 护理单据表头BO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/21 15:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseNurseTbHeadBO {
    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据表头
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(BaseNurseTbHeadDTO baseNurseTbHeadDTO);

    /**
     * @Method getTbHeadColumns
     * @Desrciption 获取动态表头列格式
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return List<BaseNurseTbHeadDTO>
     **/
    List<BaseNurseTbHeadDTO> getTbHeadColumns(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method saveTbHead
     * @Desrciption 护理单据表头格式新增/修改
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return Boolean
     **/
    Boolean saveTbHead(Map map);

    /**
     * @Method deleteTbHead
     * @Desrciption 护理单据表头格式删除
     * @Param List<BaseNurseTbHeadDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return Boolean
     **/
    Boolean deleteTbHead(List<BaseNurseTbHeadDTO> baseNurseTbHeadDTOS);

    /**
     * @Method getById
     * @Desrciption 根据主键获取唯一护理单据表头格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return BaseNurseTbHeadDTO
     **/
    BaseNurseTbHeadDTO getById(BaseNurseTbHeadDTO baseNurseTbHeadDTO);

    /**
     * @Method querySeqNo
     * @Desrciption 查询护理单据下顺序号的最大值，自动填充前端
     * @Param baseNurseTbHeadDTO【bnoCode--护理单据】
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return Integer
     **/
    Integer querySeqNo(BaseNurseTbHeadDTO baseNurseTbHeadDTO);

    /**
     * @Method queryTbHeadByBnoCode
     * @Desrciption 根据护理单据查询出所有表头List，供选择上级标题使用
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseTbHeadDTO>
     **/
    List<BaseNurseTbHeadDTO> queryTbHeadByBnoCode(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method queryItemCode
     * @Desrciption 查询出所有的护理单据itemCode编码list，用于页面下拉选择项目编码
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return List<Map < String, String>>
     **/
    List<Map<String, String>> queryItemCode(BaseNurseOrderDTO baseNurseOrderDTO);
}
