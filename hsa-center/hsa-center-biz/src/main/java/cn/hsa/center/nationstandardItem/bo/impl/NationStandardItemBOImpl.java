package cn.hsa.center.nationstandardItem.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandardItem.bo.NationStandardItemBO;
import cn.hsa.module.center.nationstandardItem.dao.NationStandardItemDAO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDO;
import cn.hsa.module.center.nationstandardItem.entity.NationStandardItemDTO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准项目
 * <p>
 * 表说明：
 * (NationStandardItemDTO)表服务接口
 *
 * @author makejava
 * @since 2021-01-26 09:18:39
 */
@Component
public class NationStandardItemBOImpl extends HsafBO implements NationStandardItemBO {


    @Resource
    private NationStandardItemDAO nationStandardItemDAO;

    /**
     * @Menthod getById
     * @Desrciption
     * @param nationStandardItemDTO 根据id查询国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO
     **/
    @Override
    public NationStandardItemDTO getById(NationStandardItemDTO nationStandardItemDTO){
        return nationStandardItemDAO.getById(nationStandardItemDTO);
    }


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardItemDTO 查询所有国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
     **/
    @Override
    public List<NationStandardItemDTO> queryAll(NationStandardItemDTO nationStandardItemDTO){
        return nationStandardItemDAO.queryAll(nationStandardItemDTO);
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardItemDTO 分页查询国家标准项目
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardItemDTO>
     **/
    @Override
    public PageDTO queryPage(NationStandardItemDTO nationStandardItemDTO){
        PageHelper.startPage(nationStandardItemDTO.getPageNo(),nationStandardItemDTO.getPageSize());
        return PageDTO.of(nationStandardItemDAO.queryAll(nationStandardItemDTO));
    }


    /**
       * @Description: 保存国家标准项目信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 9:28
    **/
    @Override
    public Boolean saveNationStandardItem(NationStandardItemDO nationStandardItemDO) {
        return nationStandardItemDAO.saveNationStandardItem(nationStandardItemDO);
    }

    /**
       * @Description: 更新国家标准项目信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/7 9:29
    **/
    @Override
    public Boolean updateNationStandardItem(NationStandardItemDO nationStandardItemDO) {
        return nationStandardItemDAO.updateNationStandardItem(nationStandardItemDO);
    }

    /**
     * @Description: 更新国家标准材料信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/7 9:23
     * @Param dataMap 上传数据包容器主要包含一下三个基础项：
     *                 key为crteName,value为用户名，
     *                 key为crteId，value为用户id，
     *                 key为resultList,value为List<List<String>> 的数据集合
     **/
    @Override
    public Boolean saveProcessedUploadDataBatch(Map<String, Object> dataMap) {
            if(null == dataMap.get("crteName")
                    ||null == dataMap.get("crteId")
                    ||null == dataMap.get("resultList")){
                throw new AppException("存在必传参数为空,请检查");
            }
            String userName = (String) dataMap.get("crteName");
            String userId = (String) dataMap.get("crteId");
            List<List<String>> resultList = (List<List<String>>) dataMap.get("resultList");

            List<NationStandardItemDO> insertList = new ArrayList<>();

            try {
                Date now = DateUtils.getNow();
                for (List<String> item : resultList){

                    NationStandardItemDO nationStandardItemDO = new NationStandardItemDO();
                    if (StringUtils.isEmpty(item.get(0)) || StringUtils.isEmpty(item.get(1)) || StringUtils.isEmpty(item.get(2))
                            || StringUtils.isEmpty(item.get(3)) || StringUtils.isEmpty(item.get(4)) || StringUtils.isEmpty(item.get(5))
                            || StringUtils.isEmpty(item.get(6)) ){
                        throw new AppException("存在必填字段为空，请检查！");
                    }

                    // id
                    nationStandardItemDO.setId(SnowflakeUtils.getId());
                    // 项目名称
                    nationStandardItemDO.setName(item.get(0));
                    // 项目编码
                    nationStandardItemDO.setCode(item.get(1));
                    // 单位代码
                    nationStandardItemDO.setUnitCode(item.get(2));
                    // 项目内涵
                    nationStandardItemDO.setIntension(item.get(3));
                    // 除外内容
                    nationStandardItemDO.setExcept(item.get(4));
                    // 备注
                    nationStandardItemDO.setRemark(item.get(5));

                    // 创建人姓名
                    nationStandardItemDO.setCrteName(userName);
                    // 创建人id
                    nationStandardItemDO.setCrteId(userId);
                    // 是否有效
                    nationStandardItemDO.setIsValid(Constants.SF.S);
                    // 创建时间
                    nationStandardItemDO.setCrteTime(now);

                    insertList.add(nationStandardItemDO);
                }
                nationStandardItemDAO.saveNationStandardItemBatch(insertList);
                insertList.clear();
            }catch (IndexOutOfBoundsException | NullPointerException  e){
                throw new AppException("EXCEL数据格式错误，导入失败");
            }
            return true;
    }

}