package cn.hsa.base.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.base.nurse.bo.BaseNurseOrderBO;
import cn.hsa.module.base.nurse.dao.BaseNurseOrderDAO;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.base.nurse.bo.impl
 * @Class_name: BaseNurseOrderBOImpl
 * @Describe: 护理单据bo实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/17 15:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@Component
public class BaseNurseOrderBOImpl extends HsafBO implements BaseNurseOrderBO {

    @Resource
    private BaseNurseOrderDAO baseNurseOrderDAO;

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseNurseOrderDTO baseNurseOrderDTO) {
        PageHelper.startPage(baseNurseOrderDTO.getPageNo(), baseNurseOrderDTO.getPageSize());
        List<BaseNurseOrderDTO> list = baseNurseOrderDAO.queryPage(baseNurseOrderDTO);
        //转换指定科室列表类型，String -> List
        list.forEach(item -> {
            if (StringUtils.isNotEmpty(item.getDeptIds())) {
                item.setDeptList(Arrays.asList(item.getDeptIds().split(",")));
            }
        });
        return PageDTO.of(list);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有护理单据(供下拉选择)
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseOrderDTO>
     **/
    @Override
    public List<BaseNurseOrderDTO> queryAll(BaseNurseOrderDTO baseNurseOrderDTO) {
        return baseNurseOrderDAO.queryAll(baseNurseOrderDTO);
    }

    /**
     * @Method getById
     * @Desrciption 查询单个护理单据
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return BaseNurseOrderDTO
     **/
    @Override
    public BaseNurseOrderDTO getById(BaseNurseOrderDTO baseNurseOrderDTO) {
        return baseNurseOrderDAO.getById(baseNurseOrderDTO);
    }

    /**
     * @Method save
     * @Desrciption 护理单据(新增 / 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return Boolean
     **/
    @Override
    public Boolean save(Map map) {
        String userId = (String) map.get("userId");
        String userName = (String) map.get("userName");
        BaseNurseOrderDTO baseNurseOrderDTO = (BaseNurseOrderDTO) map.get("baseNurseOrderDTO");

        String deptIds = "";
        if (!ListUtils.isEmpty(baseNurseOrderDTO.getDeptList())) {
            for (int i = 0; i < baseNurseOrderDTO.getDeptList().size(); i++) {
                deptIds += baseNurseOrderDTO.getDeptList().get(i);
                if (i < baseNurseOrderDTO.getDeptList().size() - 1) {
                    deptIds += ",";
                }
            }
        }
        baseNurseOrderDTO.setDeptIds(deptIds);

        //判断是否存在
        int count = baseNurseOrderDAO.getByCode(baseNurseOrderDTO);
        if (count > 0) {
            throw new RuntimeException("【" + baseNurseOrderDTO.getCode() + "】护理单据已存在");
        }
        try {
            if (StringUtils.isEmpty(baseNurseOrderDTO.getId())) {
                //新增
                baseNurseOrderDTO.setId(SnowflakeUtils.getId());
                baseNurseOrderDTO.setIsValid(Constants.SF.S);
                baseNurseOrderDTO.setCrteId(userId);
                baseNurseOrderDTO.setCrteName(userName);
                baseNurseOrderDTO.setCrteTime(DateUtils.getNow());
                baseNurseOrderDAO.insert(baseNurseOrderDTO);
            } else {
                //修改
                baseNurseOrderDAO.update(baseNurseOrderDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @Method delete
     * @Desrciption 删除护理单据
     * @Param List<BaseNurseOrderDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return Boolean
     **/
    @Override
    public Boolean delete(BaseNurseOrderDTO baseNurseOrderDTO) {
        /*BaseNurseOrderDTO nurseOrderDAOById = baseNurseOrderDAO.getById(baseNurseOrderDTO);
        if (nurseOrderDAOById == null) {
            throw new RuntimeException("护理单据不存在");
        }*/
        int num = baseNurseOrderDAO.delete(baseNurseOrderDTO);
        return num > 0;
    }

    /**
     * @Method queryTbHeadByOrder
     * @Desrciption 根据护理单据查询出对应下的所有表头格式数据
     * @Param BaseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryTbHeadByOrder(BaseNurseOrderDTO baseNurseOrderDTO) {
        PageHelper.startPage(baseNurseOrderDTO.getPageNo(), baseNurseOrderDTO.getPageSize());
        List<BaseNurseTbHeadDTO> list = baseNurseOrderDAO.queryTbHeadByOrder(baseNurseOrderDTO);
        return PageDTO.of(list);
    }
}
