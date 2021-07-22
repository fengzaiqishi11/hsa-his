package cn.hsa.module.base.nurse.dao;

import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.nurse.dao
 * @Class_name: BaseNurseTbHeadDAO
 * @Describe: 护理单据表头DAO
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/21 15:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseNurseTbHeadDAO {
    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据表头
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseTbHeadDTO>
     **/
    List<BaseNurseTbHeadDTO> queryPage(BaseNurseTbHeadDTO baseNurseTbHeadDTO);

    /**
     * @Method queryTbHeadByCode
     * @Desrciption 根据护理单据表头code查询出所有的动态表头列
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return List<BaseNurseTbHeadDTO>
     **/
    List<BaseNurseTbHeadDTO> queryTbHeadByCode(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method getByCode
     * @Desrciption 根据表头编码code查询出是否存在
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return int
     **/
    int getByCode(BaseNurseTbHeadDTO baseNurseTbHeadDTO);

    /**
     * @Method insert
     * @Desrciption 新增动态表头格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return void
     **/
    void insert(BaseNurseTbHeadDTO baseNurseTbHeadDTO);

    /**
     * @Method update
     * @Desrciption 修改动态表头格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return void
     **/
    void update(BaseNurseTbHeadDTO baseNurseTbHeadDTO);

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
     * @Method querySonTbHead
     * @Desrciption 先判断是否存在子节点
     * @Param codeList
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return BaseNurseTbHeadDTO
     **/
    List<BaseNurseTbHeadDTO> querySonTbHead(Map map);

    /**
     * @Method deleteTbHead
     * @Desrciption 删除表头节点
     * @Param list
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return BaseNurseTbHeadDTO
     **/
    int deleteTbHead(@Param("list") List<BaseNurseTbHeadDTO> baseNurseTbHeadDTOS);

    List<Map<String,Object>> queryNurseName(Map<String,Object> map);

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
     * @Desrciption 查询出该护理单据下已经使用了的项目编码
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<String>
     **/
    List<String> queryItemCode(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method getByItemCode
     * @Desrciption 根据项目编码itemCode查询出是否存在
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return int
     **/
    int getByItemCode(BaseNurseTbHeadDTO baseNurseTbHeadDTO);
}
