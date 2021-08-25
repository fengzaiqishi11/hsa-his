package cn.hsa.center.nationstandardmaterial.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.center.nationstandardmaterial.bo.NationStandardMaterialBO;
import cn.hsa.module.center.nationstandardmaterial.dao.NationStandardMaterialDAO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDO;
import cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 表名含义：
 * base：基础模块
 * material：国家标准材料
 * <p>
 * 表说明：
 * (NationStandardMaterialDTO)表服务接口
 *
 * @author makejava
 * @since 2021-01-26 09:18:39
 */
@Component
public class NationStandardMaterialBOImpl extends HsafBO implements NationStandardMaterialBO {


    @Resource
    private NationStandardMaterialDAO nationStandardMaterialDAO;

    /**
     * @Menthod getById
     * @Desrciption
     * @param nationStandardMaterialDTO 根据id查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO
     **/
    @Override
    public NationStandardMaterialDTO getById(NationStandardMaterialDTO nationStandardMaterialDTO){
        return nationStandardMaterialDAO.getById(nationStandardMaterialDTO);
    }


    /**
     * @Menthod queryAll
     * @Desrciption
     * @param nationStandardMaterialDTO 查询所有国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    @Override
    public List<NationStandardMaterialDTO> queryAll(NationStandardMaterialDTO nationStandardMaterialDTO){
        return nationStandardMaterialDAO.queryAll(nationStandardMaterialDTO);
    }


    /**
     * @Menthod queryPage
     * @Desrciption
     * @param nationStandardMaterialDTO 分页查询国家标准材料
     * @Author xingyu.xie
     * @Date   2021/1/26 9:46
     * @Return java.util.List<cn.hsa.module.center.nationstandardmaterial.entity.NationStandardMaterialDTO>
     **/
    @Override
    public PageDTO queryPage(NationStandardMaterialDTO nationStandardMaterialDTO){
        PageHelper.startPage(nationStandardMaterialDTO.getPageNo(),nationStandardMaterialDTO.getPageSize());
        return PageDTO.of(nationStandardMaterialDAO.queryAll(nationStandardMaterialDTO));
    }

    @Override
    public boolean insertUpLoad(Map map) {

        String userName = (String) map.get("crteName");
        String userId = (String) map.get("crteId");
        List<List<String>> resultList = (List<List<String>>) map.get("resultList");

        List<NationStandardMaterialDO> insertList = new ArrayList<>();

        try {
            for (List<String> item : resultList){

                NationStandardMaterialDO nationStandardMaterialDO = new NationStandardMaterialDO();

                if (StringUtils.isEmpty(item.get(0)) || StringUtils.isEmpty(item.get(1)) || StringUtils.isEmpty(item.get(2))
                        || StringUtils.isEmpty(item.get(3)) || StringUtils.isEmpty(item.get(4)) || StringUtils.isEmpty(item.get(5))
                        || StringUtils.isEmpty(item.get(6)) || StringUtils.isEmpty(item.get(7) )|| StringUtils.isEmpty(item.get(8))
                        || StringUtils.isEmpty(item.get(9)) || StringUtils.isEmpty(item.get(10)) ){
                    throw new AppException("存在必填字段为空，请检查！");
                }

                nationStandardMaterialDO.setId(SnowflakeUtils.getId());
                // 用户姓名
                nationStandardMaterialDO.setCrteName(userName);
                // 用户id
                nationStandardMaterialDO.setCrteId(userId);
                // 材料编码
                nationStandardMaterialDO.setCode(item.get(0));
                // 材料分类1级
                nationStandardMaterialDO.setTypeCode1(item.get(1));
                // 材料分类2级
                nationStandardMaterialDO.setTypeCode2(item.get(2));
                // 材料分类3级
                nationStandardMaterialDO.setTypeCode3(item.get(3));
                // 医保名称
                nationStandardMaterialDO.setInsuranceName(item.get(4));
                // 材质
                nationStandardMaterialDO.setMaterialQuality(item.get(5));
                // 特征
                nationStandardMaterialDO.setFeatures(item.get(6));
                // 注册证号
                nationStandardMaterialDO.setRegCertNo(item.get(7));
                // 材料名称
                nationStandardMaterialDO.setName(item.get(8));
                // 生产厂家
                nationStandardMaterialDO.setProd(item.get(9));
                // 规格
                nationStandardMaterialDO.setSpec(item.get(10));
                // 是否有效
                nationStandardMaterialDO.setIsValid(item.get(12));
                insertList.add(nationStandardMaterialDO);
            }
            nationStandardMaterialDAO.saveBatch(insertList);
            insertList.clear();
        }catch (IndexOutOfBoundsException | NullPointerException  e){
            throw new AppException("EXCEL数据格式错误，导入失败");
        }

        return true;
    }

    /**
       * @Describe: 保存国家材料标准信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/6 17:48
    **/
    @Override
    public Boolean saveNationStandardMaterial(NationStandardMaterialDO nationStandardMaterialDO) {
        return nationStandardMaterialDAO.saveNationStandardMaterial(nationStandardMaterialDO);
    }

    /**
       * @Description: 更新国家标准材料信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/6 17:47
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
    **/
    @Override
    public Boolean updateNationStandardMaterial(NationStandardMaterialDO nationStandardMaterialDO) {
        return nationStandardMaterialDAO.updateNationStandardMaterial(nationStandardMaterialDO);
    }

}