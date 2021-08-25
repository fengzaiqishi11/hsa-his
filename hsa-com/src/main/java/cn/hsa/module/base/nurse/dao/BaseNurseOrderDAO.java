package cn.hsa.module.base.nurse.dao;

import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.nurse.dao
 * @Class_name: BaseNurseOrderDAO
 * @Describe: 护理单据dao
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/17 15:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface BaseNurseOrderDAO {
    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseOrderDTO>
     **/
    List<BaseNurseOrderDTO> queryPage(BaseNurseOrderDTO baseNurseOrderDTO);

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
     * @Method insert
     * @Desrciption 新增护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return BaseNurseOrderDTO
     **/
    void insert(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method update
     * @Desrciption 修改护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return BaseNurseOrderDTO
     **/
    void update(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method delete
     * @Desrciption 删除护理单据
     * @Param List<BaseNurseOrderDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return Boolean
     **/
    int delete(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method getByCode
     * @Desrciption 判断单据是否存在
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return Boolean
     **/
    int getByCode(BaseNurseOrderDTO baseNurseOrderDTO);

    /**
     * @Method queryTbHeadByOrder
     * @Desrciption 根据护理单据查询出对应下的所有表头格式数据
     * @Param BaseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseTbHeadDTO>
     **/
    List<BaseNurseTbHeadDTO> queryTbHeadByOrder(BaseNurseOrderDTO baseNurseOrderDTO);
}
