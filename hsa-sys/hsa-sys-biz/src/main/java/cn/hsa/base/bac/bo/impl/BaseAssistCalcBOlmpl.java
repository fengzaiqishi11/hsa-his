package cn.hsa.base.bac.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bac.bo.BaseAssistCalcBO;
import cn.hsa.module.base.bac.dao.BaseAssistCalcDao;
import cn.hsa.module.base.bac.dao.BaseAssistCalcDetailDao;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDTO;
import cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO;
import cn.hsa.module.base.bor.bo.BaseOrderRuleBO;
import cn.hsa.module.base.bspc.bo.BaseSpecialCalcBO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_ame: cn.hsa.module.base.syncassist.bo
 * @Class_name: BaseAssistCalcBOlmpl
 * @Description: 业务逻辑实现层
 * @Author: ljh
 * @Email:
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class BaseAssistCalcBOlmpl extends HsafBO implements BaseAssistCalcBO {
    /**
     * 辅助计费dao
     **/
    @Resource
    private BaseAssistCalcDao baseAssistCalcDao;
    /**
     * 辅助计费明细dao
     **/
    @Resource
    private BaseAssistCalcDetailDao baseAssistCalcDetailDao;
    /**
     * 单据bo
     **/
    @Resource
    private BaseOrderRuleBO baseOrderRuleBO;

    @Resource
    private BaseSpecialCalcBO baseSpecialCalcBO;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @Menthod queryById
     * @Desrciption  查询
     * @param id
     * @Author ljh
     * @Date   2020/8/6 10:47
     * @Return cn.hsa.module.base.syncassist.dto.SyncassistDTO
     **/
    @Override
    public BaseAssistCalcDTO queryById(String id, String hospCode) {
        BaseAssistCalcDTO baseAssistCalcDTO = baseAssistCalcDao.queryById(id, hospCode);
        return baseAssistCalcDTO;
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:47
     * @Return java.util.List<cn.hsa.module.base.syncassist.dto.SyncassistDTO>
     **/
    @Override
    public List<BaseAssistCalcDTO> queryAll(BaseAssistCalcDTO baseAssistCalcDTO) {
        return baseAssistCalcDao.queryAll(baseAssistCalcDTO);
    }

    /**
     * @Menthod insert
     * @Desrciption 新增
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:47
     * @Return int
     **/
    @Override

    public int insert(BaseAssistCalcDTO baseAssistCalcDTO) {
        baseAssistCalcDTO.setId(SnowflakeUtils.getId());
        baseAssistCalcDTO.setCrteTime(new Date());
        String code = baseOrderRuleBO.updateOrderNo(baseAssistCalcDTO.getHospCode(), "9");
        baseAssistCalcDTO.setCode(code);
        List<BaseAssistCalcDetailDTO> baseAssistCalcDetaillist=baseAssistCalcDTO.getBaseAssistCalcDetailDO();
        if(!(ListUtils.isEmpty(baseAssistCalcDetaillist ))){
        for (int i = 0; i < baseAssistCalcDetaillist.size(); i++) {
            baseAssistCalcDetaillist.get(i).setAcCode(code);
            baseAssistCalcDetaillist.get(i).setId(SnowflakeUtils.getId());
            baseAssistCalcDetaillist.get(i).setCrteTime(new Date());
        }
            baseAssistCalcDetailDao.insertList(baseAssistCalcDetaillist);
        }

        return baseAssistCalcDao.insert(baseAssistCalcDTO);
    }
    /**
     * @Menthod update
     * @Desrciption 更新
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:48
     * @Return int
     **/
    @Override
    public int update(BaseAssistCalcDTO baseAssistCalcDTO) {

        String code= baseAssistCalcDTO.getCode();
        baseAssistCalcDetailDao.deleteBycode(code, baseAssistCalcDTO.getHospCode());
        List<BaseAssistCalcDetailDTO> baseAssistCalcDetaillist = baseAssistCalcDTO.getBaseAssistCalcDetailDO();
        if (!(ListUtils.isEmpty(baseAssistCalcDetaillist))) {
            for (int i = 0; i < baseAssistCalcDetaillist.size(); i++) {
                baseAssistCalcDetaillist.get(i).setAcCode(code);
                baseAssistCalcDetaillist.get(i).setId(SnowflakeUtils.getId());
                baseAssistCalcDetaillist.get(i).setCrteTime(new Date());
            }
            baseAssistCalcDetailDao.insertList(baseAssistCalcDetaillist);

        }

        return baseAssistCalcDao.update(baseAssistCalcDTO);
    }
    /**
     * @Menthod deleteById
     * @Desrciption 删除
     * @param id
     * @Author ljh
     * @Date   2020/8/6 10:49
     * @Return int
     **/
    @Override
    public int deleteById(String id, String hospCode) {
        return baseAssistCalcDao.deleteById(id, hospCode);
    }

    /**
     * @Menthod queryPage
     * @Desrciption  分页
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:49
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseAssistCalcDTO baseAssistCalcDTO) {
        if (!StringUtils.isEmpty(baseAssistCalcDTO.getDeptCode())) {
            Map map = new HashMap();
           map.put("hospCode",baseAssistCalcDTO.getHospCode());
            String chidldrenIds = TreeUtils.getChidldrenIds(baseSpecialCalcBO.getDeptTree(map),
                    baseAssistCalcDTO.getDeptCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseAssistCalcDTO.setIds(list);
        }
        PageHelper.startPage(baseAssistCalcDTO.getPageNo(), baseAssistCalcDTO.getPageSize());

        // 查询所有
        List<BaseAssistCalcDTO> baseAssistCalclist = baseAssistCalcDao.queryAll(baseAssistCalcDTO);

        // 返回分页结果
        return PageDTO.of(baseAssistCalclist);
    }

    /**
     * @Method queryPage()
     * @Description 分页
     *
     * @Param
     * SyncassistDetailDTO
     *
     * @Author ljh
     * @Date 2020/7/1 20:55
     * @Return PageDTO
     **/
    @Override
    public PageDTO detailqueryPage(BaseAssistCalcDetailDTO baseAssistCalcDetailDTO) {
        PageHelper.startPage(baseAssistCalcDetailDTO.getPageNo(), baseAssistCalcDetailDTO.getPageSize());

        // 查询所有
        List<BaseAssistCalcDetailDTO>  baseBedDTOList = baseAssistCalcDetailDao.queryallcode(baseAssistCalcDetailDTO);

        // 返回分页结果
        return PageDTO.of(baseBedDTOList);
    }
    /**
     * @Menthod deleteByIdlist
     * @Desrciption 删除
     * @param baseAssistCalcDTO
     * @Author ljh
     * @Date   2020/8/6 10:50
     * @Return int
     **/
    @Override
    public int updateStatus(BaseAssistCalcDTO baseAssistCalcDTO) {
       List<String>ids= baseAssistCalcDTO.getIds();
       String hospCode =baseAssistCalcDTO.getHospCode();
        String isValid =baseAssistCalcDTO.getIsValid();
        List<BaseAssistCalcDTO> baseAssistCalcs = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            BaseAssistCalcDTO baseAssistCalc = new BaseAssistCalcDTO();
            baseAssistCalc.setId(ids.get(i));
            baseAssistCalc.setIsValid(isValid);
            baseAssistCalc.setHospCode(hospCode);
            baseAssistCalcs.add(baseAssistCalc);
        }
        return baseAssistCalcDao.deleteByIdlist(baseAssistCalcs);
    }

    /**
     * @Method: queryAssists
     * @Description: 查询辅助计费
     * @Param: [baseAssistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 14:14
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDTO>
     **/
    @Override
    public List<BaseAssistCalcDTO> queryAssists(BaseAssistCalcDTO baseAssistCalcDTO) {
        if (baseAssistCalcDTO == null) {
            throw new AppException("参数为空");
        }
        return baseAssistCalcDao.queryAssists(baseAssistCalcDTO);
    }

    /**
     * @Method: queryAssistDetails
     * @Description: 根据编码获取辅助计费明细
     * @Param: [baseAssistCalcDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/21 15:31
     * @Return: java.util.List<cn.hsa.module.base.bac.dto.BaseAssistCalcDetailDTO>
     **/
    @Override
    public List<BaseAssistCalcDetailDTO> queryAssistDetails(BaseAssistCalcDTO baseAssistCalcDTO) {
        if (baseAssistCalcDTO == null) {
            throw new AppException("参数为空");
        }
        return baseAssistCalcDetailDao.queryAssistDetails(baseAssistCalcDTO);
    }
}
