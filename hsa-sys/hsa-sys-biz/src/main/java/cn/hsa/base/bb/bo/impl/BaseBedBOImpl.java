package cn.hsa.base.bb.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bb.bo.BaseBedBO;
import cn.hsa.module.base.bb.dao.BaseBedDao;
import cn.hsa.module.base.bb.dao.BaseBedItemDao;
import cn.hsa.module.base.bb.dto.BaseBedDTO;
import cn.hsa.module.base.bb.dto.BaseBedItemDTO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bor.bo.BaseOrderRuleBO;
import cn.hsa.module.base.dept.dao.BaseDeptDAO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.commons.beanutils.PropertyUtils.copyProperties;

/**
 * @Package_name: cn.hsa.base.bb.bo.impl
 * @Class_name:: BaseBedBOImpl
 * @Description: 床位bo层
 * @Author: ljh
 * @Date: 2020/8/6 10:35
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j

public class BaseBedBOImpl extends HsafBO implements BaseBedBO {
    /**
    床位dao
     */
    @Resource
    private BaseBedDao baseBedDao;
    /**
    床位明细表dao
     */
    @Resource
    private BaseBedItemDao baseBedItemDao;
    /**
    单据号bo
     */
    @Resource
    private BaseOrderRuleBO baseOrderRuleBO;

    @Resource
    private BaseDeptDAO baseDeptDAO;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @Menthod queryAll
     * @Desrciption  查询
     * @param baseBedDTO
     * @Author ljh
     * @Date   2020/8/6 10:36
     * @Return java.util.List<cn.hsa.module.base.bb.dto.BaseBedDTO>
     **/
    @Override
    public List<BaseBedDTO> queryAll(BaseBedDTO baseBedDTO) {
        if("1".equals(baseBedDTO.getVisitFlag())){
            BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
            baseDeptDTO.setHospCode(baseBedDTO.getHospCode());
            baseDeptDTO.setId(baseBedDTO.getDeptId());
            baseDeptDTO.setIsValid("1");
            List<BaseDeptDTO> deptList = baseDeptDAO.queryAllByBed(baseDeptDTO);
            baseBedDTO.setDeptCode(deptList.get(0).getCode());
        }
        return this.baseBedDao.queryAll(baseBedDTO);
    }

    /**
     * @Menthod insert
     * @Desrciption 新增 床位名称和顺序号递增,判断床位名称是否存在
     * @param baseBedDTO
     * @Author ljh
     * @Date   2020/8/6 10:36
     * @Return boolean
     **/
    @Override
    public boolean insert(BaseBedDTO baseBedDTO) throws Exception {
//        if (ListUtils.isEmpty(baseBedDTO.getBaseBedItemDTO())) {
//            throw new AppException("床位收费信息为空");
//        }
        if (baseBedDTO.getIsBatch()) {
            if (StringUtils.isEmpty(String.valueOf(baseBedDTO.getMax())) && StringUtils.isEmpty(String.valueOf(baseBedDTO.getMin()))) {
                throw new AppException("最大值或最小值为空");
            }
            if (baseBedDTO.getMax() < baseBedDTO.getMin()) {
                throw new AppException("最大值比最小值为小");
            }
            List<BaseBedDTO> baseBedS = new ArrayList<>();
            int min = baseBedDTO.getMin();
            int max = baseBedDTO.getMax();
            List<BaseBedItemDTO> baseBedItemlist = baseBedDTO.getBaseBedItemDTO();
            for (int i = min; i <= max; i++) {
                BaseBedDTO baseBed = new BaseBedDTO();
                copyProperties(baseBed, baseBedDTO);
                baseBed.setId(SnowflakeUtils.getId());
                baseBed.setIsValid("1");
                if(StringUtils.isEmpty(baseBed.getStatusCode())){
                    baseBed.setStatusCode("1");
                }
                baseBed.setCrteTime(new Date());
                String code = baseOrderRuleBO.updateOrderNo(baseBedDTO.getHospCode(), "9");
                baseBed.setCode(code);
                baseBed.setSeqNo(i);
                baseBed.setName(i + "床");
                baseBedS.add(baseBed);

                    for (int j = 0; j < baseBedItemlist.size(); j++) {
                        baseBedItemlist.get(j).setBedCode(code);
                        baseBedItemlist.get(j).setId(SnowflakeUtils.getId());
                    }
                baseBedItemDao.insertList(baseBedItemlist);
            }
            baseBedDTO.setBaseBedDTOS(baseBedS);
            baseBedDao.insertList(baseBedS);
//            cacheOperate(baseBedDTO,true,true);
        } else {
            baseBedDTO.setId(SnowflakeUtils.getId());
            baseBedDTO.setIsValid("1");
            if(StringUtils.isEmpty(baseBedDTO.getStatusCode())){
                baseBedDTO.setStatusCode("1");
            }
            baseBedDTO.setCrteTime(new Date());
            String code = baseOrderRuleBO.updateOrderNo(baseBedDTO.getHospCode(), "9");
            baseBedDTO.setCode(code);
            baseBedDTO.setSeqNo(baseBedDao.getMaxSeq(baseBedDTO) + 1);
            List<BaseBedItemDTO> baseBedItemlist = baseBedDTO.getBaseBedItemDTO();
                List<BaseBedItemDTO> baseBedItems = new ArrayList<>();
                for (int i = 0; i < baseBedItemlist.size(); i++) {
                    baseBedItemlist.get(i).setBedCode(code);
                    baseBedItemlist.get(i).setId(SnowflakeUtils.getId());
                    baseBedItems.add(baseBedItemlist.get(i));
                }
                baseBedItemDao.insertList(baseBedItems);

            baseBedDao.insert(baseBedDTO);
//            cacheOperate(baseBedDTO,true,false);

        }
        // 存入缓存
        return true;
    }

    /**
    * @Method insertBaseBedDTO
    * @Desrciption 新增床位
    * @param baseBedDTO
    * @Author liuqi1
    * @Date   2020/10/22 22:27
    * @Return boolean
    **/
    @Override
    public boolean insertBaseBedDTO(BaseBedDTO baseBedDTO){
        baseBedDao.insert(baseBedDTO);
        // 存入缓存
//        cacheOperate(baseBedDTO,true,false);
        return true;
    }

    /**
     * @Menthod update
     * @Desrciption 更新
     * @param baseBedDTO
     * @Author ljh
     * @Date   2020/8/6 10:36
     * @Return int
     **/
    @Override
    public int update(BaseBedDTO baseBedDTO) {
        if (baseBedDao.queryVisitIdIsExist(baseBedDTO)> 0) {
            throw new AppException("该床位已被使用");
        }else{
//            if (baseBedDao.queryDeptCodeSeqNoIsExist(baseBedDTO)> 0) {
//            throw new AppException("该科室这个顺序号已被使用");
//          }else
            String code = baseBedDTO.getCode();
            baseBedDao.deleteBycode(code, baseBedDTO.getHospCode());
            List<BaseBedItemDTO> baseBedItemlist = baseBedDTO.getBaseBedItemDTO();
            List<BaseBedItemDTO> baseBedItems = new ArrayList<>();
            if (!ListUtils.isEmpty(baseBedItemlist)) {
                for (int i = 0; i < baseBedItemlist.size(); i++) {
                    baseBedItemlist.get(i).setBedCode(code);
                    baseBedItemlist.get(i).setId(SnowflakeUtils.getId());
                    baseBedItems.add(baseBedItemlist.get(i));
                }
                baseBedItemDao.insertList(baseBedItemlist);
            }
            int update = this.baseBedDao.update(baseBedDTO);
            // 缓存操作 -- 只有有效的时候才进行操作
            if(Constants.SF.S.equals(baseBedDTO.getIsValid())){
//              cacheOperate(baseBedDTO,true,false);
            }
            return update;
        }
    }

    /**
     * @Method updateVisit
     * @Desrciption 批量更新床位占用
       @params [baseBedDTO]
     * @Author chenjun
     * @Date   2020/9/11 10:13
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateVisit(BaseBedDTO baseBedDTO) {
        return baseBedDao.updateVisit(baseBedDTO);
    }

    /**
     * @Method deleteById
     * @Desrciption 删除床位信息（虚拟床位）
       @params [baseBedDTO]
     * @Author chenjun
     * @Date   2020/9/11 10:12
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean deleteById(BaseBedDTO baseBedDTO) {
        if (baseBedDao.queryVisitIdIsExist(baseBedDTO)> 0) {
            throw new AppException("该床位已被使用");
        }else{
            return baseBedDao.deleteByDto(baseBedDTO);
        }
    }

    /**
     * @Method queryBedItemAll
     * @Desrciption 查询明细信息
       @params [baseBedItemDTO]
     * @Author chenjun
     * @Date   2020/9/11 10:12
     * @Return java.util.List<cn.hsa.module.base.bb.dto.BaseBedItemDTO>
    **/
    @Override
    public List<BaseBedItemDTO> queryBedItemAll(BaseBedItemDTO baseBedItemDTO) {
        return baseBedItemDao.queryAllByBedCode(baseBedItemDTO);
    }

    /**
     * @Method getMaxSeq
     * @Desrciption 获取当前科室下床位的最大顺序号
     * @Param
     * [baseBedItemDTO]
     * @Author liaojunjie
     * @Date   2020/12/17 20:42
     * @Return java.lang.String
     **/
    @Override
    public Integer getMaxSeq(BaseBedDTO baseBedDTO) {
        return baseBedDao.getMaxSeq(baseBedDTO);
    }

    /**
     * @Menthod updateStatus
     * @Desrciption 更新状态
     * @param baseBedDTO
     * @Author ljh
     * @Date   2020/8/6 10:36
     * @Return int
     **/
    @Override
    public int updateStatus(BaseBedDTO baseBedDTO) {

        if (baseBedDao.queryVisitIdIsExist(baseBedDTO)> 0) {
            throw new AppException("该床位已被使用");
        }
//            List<String> ids=baseBedDTO.getIds();
//            String hospCode=baseBedDTO.getHospCode();
//            String isValid =baseBedDTO.getIsValid();
//            List<BaseBedDTO> baseBeds = new ArrayList<>();
//            for (int i = 0; i < ids.size(); i++) {
//                BaseBedDTO baseBed = new BaseBedDTO();
//                baseBed.setHospCode(hospCode);
//                baseBed.setId(ids.get(i));
//                baseBed.setIsValid(isValid);
//                baseBeds.add(baseBed);
        String isValid = baseBedDTO.getIsValid();
        baseBedDTO.setIsValid(null);
        List<BaseBedDTO> baseBedDTOS = baseBedDao.queryAll(baseBedDTO);
        baseBedDTO.setBaseBedDTOS(baseBedDTOS);
        baseBedDTO.setIsValid(isValid);
        int i = baseBedDao.updateStatus(baseBedDTO);
        if(Constants.SF.F.equals(baseBedDTO.getIsValid())){
//            cacheOperate(baseBedDTO,false,true);
        }else {
//            cacheOperate(baseBedDTO,true,true);
        }
        return i;
    }

    /**
     * @Menthod queryPage
     * @Desrciption 分页
     * @param baseBedDTO
     * @Author ljh
     * @Date   2020/8/6 10:35
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseBedDTO baseBedDTO) {
        PageHelper.startPage(baseBedDTO.getPageNo(), baseBedDTO.getPageSize());
        // 查询所有
        List<BaseBedDTO> baseBedDTOList = baseBedDao.queryAll(baseBedDTO);

        // 返回分页结果
        return PageDTO.of(baseBedDTOList);
    }

    /**
     * @Menthod itemqueryPage
     * @Desrciption 分页
     * @param baseBedItemDTO
     * @Author ljh
     * @Date   2020/8/6 10:35
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO itemqueryPage(BaseBedItemDTO baseBedItemDTO) {
        PageHelper.startPage(baseBedItemDTO.getPageNo(), baseBedItemDTO.getPageSize());
        // 查询所有
        List<Map<String, Object>> baseBedDTOList = baseBedItemDao.queryAll(baseBedItemDTO);

        // 返回分页结果
        return PageDTO.of(baseBedDTOList);
    }

    @Override
    public BaseBedDTO getById(Map map) {
        return baseBedDao.queryById(MapUtils.getVS(map, "id"), MapUtils.getVS(map, "hospCode"));
    }

    @Override
    public List<BaseItemDTO> queryBedItem(BaseBedDTO baseBedDTO) {
        return baseBedDao.queryBedItem(baseBedDTO);
    }

    public void cacheOperate(BaseBedDTO baseBedDTO, Boolean operation, Boolean batch) {
        if (batch) {
            for (BaseBedDTO bed : baseBedDTO.getBaseBedDTOS()) {
                String key = StringUtils.createKey("bed", bed.getHospCode(), bed.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, bed);
                }
            }
        } else {
            String key = StringUtils.createKey("bed", baseBedDTO.getHospCode(), baseBedDTO.getId());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, baseBedDTO);
            }

        }
    }
}
