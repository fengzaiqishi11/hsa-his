package cn.hsa.base.bmc.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bfc.bo.BaseFinanceClassifyBO;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.base.bmc.bo.BaseMrisClassifyBO;
import cn.hsa.module.base.bmc.dao.BaseMrisClassifyDAO;
import cn.hsa.module.base.bmc.dto.BaseMrisClassifyDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Package_name: cn.hsa.base.bmm.bo.impl
 * @Class_name: BaseMrisClassifyManagementBOImpl
 * @Describe: 病案费用归类信息业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseMrisClassifyBOImpl extends HsafBO implements BaseMrisClassifyBO {

    /**
    * @Menthod
    * @Desrciption 注入Dao层对象
     * @param null
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return
    **/
    @Resource
    private BaseMrisClassifyDAO baseMrisClassifyDAO;

    @Resource
    private BaseFinanceClassifyBO baseFinanceClassifyBO;


    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询病案费用归类信息
     * @param baseMrisClassifyDTO  主键ID 医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return cn.hsa.module.base.bmm.dto.BaseMrisClassifyDTO
    **/
    @Override
    public BaseMrisClassifyDTO getById(BaseMrisClassifyDTO baseMrisClassifyDTO) {
        return this.baseMrisClassifyDAO.getById(baseMrisClassifyDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部病案费用归类信息
     * @param baseMrisClassifyDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/14 21:02
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseMrisClassifyDTO>
    **/
    @Override
    public List<BaseMrisClassifyDTO> queryAll(BaseMrisClassifyDTO baseMrisClassifyDTO) {
        return this.baseMrisClassifyDAO.queryAll(baseMrisClassifyDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象分页查询病案费用归类信息
     * @param baseMrisClassifyDTO 病案费用归类信息数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:42
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPage(BaseMrisClassifyDTO baseMrisClassifyDTO) {

        String hospCode = baseMrisClassifyDTO.getHospCode();
        // 设置分页信息
        PageHelper.startPage(baseMrisClassifyDTO.getPageNo(),baseMrisClassifyDTO.getPageSize());

        List<BaseMrisClassifyDTO> baseMrisClassifyDTOList = baseMrisClassifyDAO.queryAll(baseMrisClassifyDTO);

        BaseFinanceClassifyDTO bfc = new BaseFinanceClassifyDTO();

        PageDTO of = PageDTO.of(baseMrisClassifyDTOList);
        List<BaseMrisClassifyDTO> list = (List<BaseMrisClassifyDTO>) of.getResult();
        if (!ListUtils.isEmpty(list)){
            // 循环根据每条病案费用归类里的财务编码查询财务分类名称，拼成逗号分割的String类型
            for (BaseMrisClassifyDTO item:list){
                bfc.setHospCode(hospCode);
                String bfcCodes = item.getBfcCodes();
                if (StringUtils.isNotEmpty(bfcCodes)){
                    List<String> strings = Arrays.asList(bfcCodes.split(","));
                    bfc.setIds(strings);
                    // 根据财务分类编码列表查出所有财务分类名称
                    List<BaseFinanceClassifyDTO> baseFinanceClassifyDTOList = baseFinanceClassifyBO.queryAll(bfc);
                    List<String> collect = baseFinanceClassifyDTOList.stream().map(BaseFinanceClassifyDTO::getName).collect(Collectors.toList());
                    String join = Joiner.on(",").join(collect);
                    item.setBfcNames(join);
                }
            }
        }
        return  of;
    }

    /**
     * @Menthod save
     * @Desrciption  新增或修改病案费用归类
     * @param BaseMrisClassifyDTO 病案费用归类数据参数对象
     * @Author xingyu.xie
     * @Date   2020/7/25 11:38
     * @Return boolean
     **/
    @Override
    public boolean save(BaseMrisClassifyDTO BaseMrisClassifyDTO){

        if (baseMrisClassifyDAO.isCodeExist(BaseMrisClassifyDTO)>0){
            throw new AppException("操作失败，病案费用代码重复");
        }

        //id为空就是新增，不为空就是修改
        if (StringUtils.isEmpty(BaseMrisClassifyDTO.getId())){

            BaseMrisClassifyDTO.setId(SnowflakeUtils.getId());

            BaseMrisClassifyDTO.setCrteTime(DateUtils.getNow());

            return this.baseMrisClassifyDAO.insert(BaseMrisClassifyDTO)>0;
        }else {
            return this.baseMrisClassifyDAO.updateBaseMrisClassify(BaseMrisClassifyDTO)>0;
        }
    }

    /**
    * @Menthod updateBaseMrisClassifyS
    * @Desrciption  修改单条病案费用归类数据（有判空条件）
     * @param baseMrisClassifyDTO
    * @Author xingyu.xie
    * @Date   2020/9/17 15:17
    * @Return boolean
    **/
    @Override
    public boolean updateBaseMrisClassifyS(BaseMrisClassifyDTO baseMrisClassifyDTO) {
        return baseMrisClassifyDAO.updateBaseMrisClassifyS(baseMrisClassifyDTO)>0;
    }

    /**
    * @Menthod updateStatus
    * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个病案费用归类信息
     * @param baseMrisClassifyDTO
    * @Author xingyu.xie
    * @Date   2020/7/17 15:43
    * @Return boolean
    **/
    @Override
    public boolean updateStatus(BaseMrisClassifyDTO baseMrisClassifyDTO) {
        return this.baseMrisClassifyDAO.updateStatus(baseMrisClassifyDTO)>0;
    }
}
