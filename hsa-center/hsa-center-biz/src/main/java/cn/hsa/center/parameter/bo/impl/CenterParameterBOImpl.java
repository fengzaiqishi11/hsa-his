package cn.hsa.center.parameter.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.parameter.bo.CenterParameterBO;
import cn.hsa.module.center.parameter.dao.CenterParameterDAO;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import cn.hsa.util.PinYinUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.data.bo.impl
 * @Class_name: centerSupplierBOImpl
 * @Describe: 参数业务逻辑实现层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 16:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class CenterParameterBOImpl extends HsafBO implements CenterParameterBO {

    /**
     * 参数数据库访问接口
     */
    @Resource
    private CenterParameterDAO centerParameterDao;
    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询参数信息
     * @Param
     * 1. centerParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 17:02
     * @Return cn.hsa.center.PageDTO
     **/
    @Override
    public PageDTO queryPage(CenterParameterDTO centerParameterDTO) {
        // 设置分页信息
        PageHelper.startPage(centerParameterDTO.getPageNo(), centerParameterDTO.getPageSize());
        // 根据条件查询所
        List<CenterParameterDTO> centerParameterDTOS = centerParameterDao.queryPage(centerParameterDTO);
        return PageDTO.of(centerParameterDTOS);
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有参数信息
     *
     * @Param
     * [centerParameterDTO]
     * @Author zhangxuan
     * @Date   2020/7/28 15:31
     * @Return java.util.List<cn.hsa.module.center.parameter.dto.centerParameterDTO>
     **/
    @Override
    public List<CenterParameterDTO> queryAll(CenterParameterDTO centerParameterDTO) {
        List<CenterParameterDTO> centerParameterDTOS = centerParameterDao.queryAll(centerParameterDTO);
        return centerParameterDTOS;
    }


    /**
     * @Menthod insert()
     * @Desrciption 新增参数
     * @Param
     * 1. centerParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:53
     * @Return int
     **/
    @Override
    public boolean insert(CenterParameterDTO centerParameterDTO) {
            return save(centerParameterDTO);
    }

    /**
     * @Menthod deleteSuppelier()
     * @Desrciption 删除参数
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/7/28 15:57
     * @Return int
     **/
    @Override
    public boolean delete(CenterParameterDTO centerParameterDTO) {
            return centerParameterDao.delete(centerParameterDTO) > 0;
    }

    /**
     * @Menthod update()
     * @Desrciption 修改参数信息
     * @Param
     * 1. centerParameterDTO  参数数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/28 15:58
     * @Return int
     **/
    @Override
    public boolean update(CenterParameterDTO centerParameterDTO) {
        return save(centerParameterDTO);
    }

    /**
     * @Method: save()
     * @Description: 该方法主要用来保存参数信息维护修改和新增的信息
     * @Param: centerParameterDTO数据传输对象
     * @Author: zhangxuan
     * @Date: 2020/8/13 14:17
     * @Return: int
     */
    private boolean save(CenterParameterDTO centerParameterDTO) {
        if (centerParameterDao.queryCodeIsExist(centerParameterDTO)> 0) {
            throw new AppException("该参数编码已经存在" + centerParameterDTO.getCode());
        } else if(centerParameterDao.queryNameIsExist(centerParameterDTO)> 0) {
            throw new AppException("该参数名字已经存在" + centerParameterDTO.getName());
        }
        //生成拼音码
        if (StringUtils.isEmpty(centerParameterDTO.getPym())) {
            centerParameterDTO.setPym(PinYinUtils.toFirstPY(centerParameterDTO.getName()));
        }
        //生成五笔码
        if (StringUtils.isEmpty(centerParameterDTO.getWbm())) {
            centerParameterDTO.setWbm(PinYinUtils.toFirstPY(centerParameterDTO.getName()));
        }

        //判断主键id是否存在，如果存在则是修改，否则就是新增
        if (StringUtils.isEmpty(centerParameterDTO.getId())) {
            centerParameterDTO.setId(SnowflakeUtils.getId());
            return centerParameterDao.insert(centerParameterDTO) > 0;
        } else {
            return centerParameterDao.update(centerParameterDTO) > 0;
        }
    }

    /**
     * @Method: getParameterByCode
     * @Description: 根据编码获取参数
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/25 14:24
     * @Return: cn.hsa.module.center.parameter.dto.CenterParameterDTO
     **/
    @Override
    public CenterParameterDTO getParameterByCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new AppException("编码为空");
        }
        return centerParameterDao.getParameterByCode(code);
    }

    /**
     * @Method: getParameterByCode
     * @Description: 根据编码获取参数
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/25 14:24
     * @Return: cn.hsa.module.center.parameter.dto.CenterParameterDTO
     **/
    @Override
    public Map<String, CenterParameterDTO> getParameterByCodeList(String... codeList) {
        if (codeList == null || codeList.length == 0) {
            throw new AppException("编码为空");
        }
        return centerParameterDao.getParameterByCodeList(codeList);
    }
}
