package cn.hsa.center.nationstandarddrug.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.nationstandarddrug.bo.NationStandardDrugZYBO;
import cn.hsa.module.center.nationstandarddrug.dao.NationStandardDrugZYDAO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugZYDO;
import cn.hsa.module.elasticsearch.HsaElasticsearchRepository;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author luonianxin
 */
@Component
@Slf4j
public class NationStandardDrugZYBOImpl implements NationStandardDrugZYBO {

    @Resource
    private NationStandardDrugZYDAO nationStandardDrugZYDAO;

    @Resource
    private HsaElasticsearchRepository elasticsearchRepository;
    @Override
    public PageDTO queryNationStandardDrugZYPage(NationStandardDrugZYDTO nationStandardDrugZYDTO) {
        PageHelper.startPage(nationStandardDrugZYDTO.getPageNo(),nationStandardDrugZYDTO.getPageSize());
        List<NationStandardDrugZYDTO> nationStandardDrugZYDTOList = nationStandardDrugZYDAO.queryNationStandardDrugZYPage(nationStandardDrugZYDTO);
        return PageDTO.of(nationStandardDrugZYDTOList);
    }

    @Override
    public NationStandardDrugZYDTO getZYById(NationStandardDrugZYDTO nationStandardDrugZYDTO) {
        return nationStandardDrugZYDAO.getZYById(nationStandardDrugZYDTO);
    }

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

        List<NationStandardDrugZYDO> insertList = new ArrayList<>();

        try {
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(DateUtils.getNow());

            for (List<String> item : resultList){
                NationStandardDrugZYDO nationStandardDrugZYDO = new NationStandardDrugZYDO();
                if (StringUtils.isEmpty(item.get(0)) || StringUtils.isEmpty(item.get(2)) || StringUtils.isEmpty(item.get(11))){
                    throw new AppException("存在必填字段为空，请检查！出错记录是："+item.toString());
                }
                // id
                nationStandardDrugZYDO.setId(SnowflakeUtils.getId());
                // 药品编码
                nationStandardDrugZYDO.setCode(item.get(0));
                // 中药饮片名称
                nationStandardDrugZYDO.setDecoctionName(item.get(1));
                // 药材名称
                nationStandardDrugZYDO.setGoodName(item.get(2));
                // 注册剂型
                nationStandardDrugZYDO.setFunctionType(item.get(3));
                // 药材科来源
                nationStandardDrugZYDO.setDrugsSourceSubject(item.get(4));
                // 药材种来源
                nationStandardDrugZYDO.setDrugsSourceRace(item.get(5));
                // 药用部位
                nationStandardDrugZYDO.setPharmacyPosition(item.get(6));
                // 炮制方法
                nationStandardDrugZYDO.setProcessingMethod(item.get(7));
                // 性味与归经
                nationStandardDrugZYDO.setSexualTaste(item.get(8));
                // 功能与主治
                nationStandardDrugZYDO.setFunctionIndications(item.get(9));
                // 用法与用量
                nationStandardDrugZYDO.setUsageDosage(item.get(10));
                // 医保支付政策
                nationStandardDrugZYDO.setInsurancePolicy(item.get(11));
                // 是否有效
                nationStandardDrugZYDO.setIsValid(Constants.SF.S);
                // 创建人ID
                nationStandardDrugZYDO.setCrteId(userId);
                // 创建人姓名
                nationStandardDrugZYDO.setCrteName(userName);
                // 创建时间
                nationStandardDrugZYDO.setCrteTime(now);



                insertList.add(nationStandardDrugZYDO);
            }
            nationStandardDrugZYDAO.saveNationStandardDrugZYBatch(insertList);
            insertList.clear();
        }catch (IndexOutOfBoundsException | NullPointerException  e){
            throw new AppException("EXCEL数据格式错误，导入失败");
        }
        return true;
    }

    @Override
    public Boolean saveNationStandardDrugZY(NationStandardDrugZYDO nationStandardDrugZYDO) {
        if(null == nationStandardDrugZYDO)
        {
            throw new AppException("参数不能为空！");
        }

        if(StringUtils.isEmpty(nationStandardDrugZYDO.getId())){
            nationStandardDrugZYDO.setId(SnowflakeUtils.getId());
            nationStandardDrugZYDO.setCrteTime(DateUtils.format(new Date(),DateUtils.Y_M_DH_M_S));
            nationStandardDrugZYDO.setCrteName("管理员");
            nationStandardDrugZYDO.setCrteId("8888");
            // 更新ES搜索数据
            nationStandardDrugZYDO.setWbm(WuBiUtils.getWBCodeSplitWithWhiteSpace(nationStandardDrugZYDO.getGoodName()));
            nationStandardDrugZYDO.setPym(PinYinUtils.toFirstPYWithWhiteSpace(nationStandardDrugZYDO.getGoodName()));
            try {
                elasticsearchRepository.save(nationStandardDrugZYDO);
            }catch (Exception ignored) {
                // 确保es对正常业务无影响
            }
            nationStandardDrugZYDO.setPym(PinYinUtils.toFullPY(nationStandardDrugZYDO.getGoodName()));
            nationStandardDrugZYDO.setWbm(WuBiUtils.getWBCode(nationStandardDrugZYDO.getGoodName()));
            return nationStandardDrugZYDAO.saveNationStandardDrugZY(nationStandardDrugZYDO) > 0;
        }
        try {
            // 更新ES搜索数据
            if (Constants.SF.S.equals(nationStandardDrugZYDO.getIsValid())) {
                nationStandardDrugZYDO.setWbm(WuBiUtils.getWBCodeSplitWithWhiteSpace(nationStandardDrugZYDO.getGoodName()));
                nationStandardDrugZYDO.setPym(PinYinUtils.toFirstPYWithWhiteSpace(nationStandardDrugZYDO.getGoodName()));
                elasticsearchRepository.save(nationStandardDrugZYDO);
            }else{
                // 药品设置为无效后将其从ES中删除
                elasticsearchRepository.delete(nationStandardDrugZYDO);
            }
        }catch (Exception ignored) {
                // 确保es对正常业务无影响
        }
        nationStandardDrugZYDO.setPym(PinYinUtils.toFullPY(nationStandardDrugZYDO.getGoodName()));
        nationStandardDrugZYDO.setWbm(WuBiUtils.getWBCode(nationStandardDrugZYDO.getGoodName()));
        return nationStandardDrugZYDAO.updateNationStandardDrugZY(nationStandardDrugZYDO) > 0;
    }
}
