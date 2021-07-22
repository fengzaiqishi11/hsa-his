package cn.hsa.module.base.nurse.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;

import java.util.List;
import java.util.Map;

public interface BaseNurseOrderBO {
    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询所有护理单据(供下拉选择)
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseOrderDTO>
     **/
    List<BaseNurseOrderDTO> queryAll(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method getById
     * @Desrciption 查询单个护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return BaseNurseOrderDTO
     **/
    BaseNurseOrderDTO getById(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method save
     * @Desrciption 护理单据(新增 / 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return Boolean
     **/
    Boolean save(Map map);

    /**
     * @Method delete
     * @Desrciption 删除护理单据
     * @Param List<BaseNurseOrderDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return Boolean
     **/
    Boolean delete(BaseNurseOrderDTO baseNurseOrderDTOS);

    /**
     * @Method queryTbHeadByOrder
     * @Desrciption 根据护理单据查询出对应下的所有表头格式数据
     * @Param BaseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseTbHeadDTO>
     **/
    PageDTO queryTbHeadByOrder(BaseNurseOrderDTO baseNurseOrderDTO);
}
