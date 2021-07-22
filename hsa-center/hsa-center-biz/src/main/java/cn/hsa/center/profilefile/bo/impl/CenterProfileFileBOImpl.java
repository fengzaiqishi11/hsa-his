package cn.hsa.center.profilefile.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.profilefile.bo.CenterProfileFileBO;
import cn.hsa.module.center.profilefile.dao.CenterProfileFileDAO;
import cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.center.profilefile.bo.impl
 * @Class_name: OutptProfileFileBOImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/8/11 9:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class CenterProfileFileBOImpl extends HsafBO implements CenterProfileFileBO {

    /**
     * 个人档案数据库访问接口
     */
    @Resource
    private CenterProfileFileDAO centerProfileFileDAO;

    /**
     * @Method getById
     * @Desrciption 通过id查询
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:54
     * @Return cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO
     **/
    @Override
    public CenterProfileFileDTO getById(CenterProfileFileDTO centerProfileFileDTO) {
        return centerProfileFileDAO.getById(centerProfileFileDTO);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:54
     * @Return java.util.List<cn.hsa.module.center.profilefile.dto.CenterProfileFileDTO>
     **/
    @Override
    public List<CenterProfileFileDTO> queryAll(CenterProfileFileDTO centerProfileFileDTO) {
        return centerProfileFileDAO.queryAll(centerProfileFileDTO);
    }

    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param
     * [centerProfileFileDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:55
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(CenterProfileFileDTO centerProfileFileDTO) {
        PageHelper.startPage(centerProfileFileDTO.getPageNo(), centerProfileFileDTO.getPageSize());
        List<CenterProfileFileDTO> centerProfileFileDTOS = centerProfileFileDAO.queryPage(centerProfileFileDTO);
        return PageDTO.of(centerProfileFileDTOS);
    }

}
