package cn.hsa.oper.operInforecord.bo.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.base.bfc.service.BaseFinanceClassifyService;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.oper.operInforecord.bo.OperAccountTempBO;
import cn.hsa.module.oper.operInforecord.dao.OperAccountTempDAO;
import cn.hsa.module.oper.operInforecord.dao.OperAccountTempDetailDAO;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDTO;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *@Package_name: cn.hsa.oper.operInforecord.bo.impl
 *@Class_name: OperAccountTempBoImpl
 *@Describe: 手术补记账模板bo实现类
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/12/5 10:51
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OperAccountTempBOImpl extends HsafBO implements OperAccountTempBO {

    @Resource
    OperAccountTempDAO operAccountTempDAO;

    @Resource
    OperAccountTempDetailDAO operAccountTempDetailDAO;

    @Resource
    SysCodeService sysCodeService;

    @Resource
    BaseDrugService baseDrugService;

    @Resource
    BaseMaterialService baseMaterialService;

    @Resource
    BaseItemService baseItemService;

    @Resource
    BaseDeptService baseDeptService;

    /**
    * @Method queryOperAccountTempDTOPage
    * @Desrciption 手术模板分页查询
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 11:08
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryOperAccountTempDTOPage(OperAccountTempDTO operAccountTempDTO) {
        PageHelper.startPage(operAccountTempDTO.getPageNo(), operAccountTempDTO.getPageSize());
        List<OperAccountTempDTO> operAccountTempDTOS = operAccountTempDAO.queryOperAccountTempDTOPage(operAccountTempDTO);
        return PageDTO.of(operAccountTempDTOS);
    }

    /**
    * @Method queryOperAccountTempDetailDTOPage
    * @Desrciption 手术模板明细分页查询
    * @param operAccountTempDetailDTO
    * @Author liuqi1
    * @Date   2020/12/5 11:08
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryOperAccountTempDetailDTOPage(OperAccountTempDetailDTO operAccountTempDetailDTO) {
        PageHelper.startPage(operAccountTempDetailDTO.getPageNo(), operAccountTempDetailDTO.getPageSize());
        List<OperAccountTempDetailDTO> operAccountTempDetailDTOS = operAccountTempDetailDAO.queryOperAccountTempDetailDTOPage(operAccountTempDetailDTO);

        if (!ListUtils.isEmpty(operAccountTempDetailDTOS)){
            //补充单位信息
            operAccountTempDetailDTOS = updateNumCode(operAccountTempDetailDTO,operAccountTempDetailDTOS);

            //补充价格信息
            operAccountTempDetailDTOS = updatePrice(operAccountTempDetailDTO,operAccountTempDetailDTOS);

            //补充发药药房
            operAccountTempDetailDTOS = updatePharId(operAccountTempDetailDTO,operAccountTempDetailDTOS);
        }


        return PageDTO.of(operAccountTempDetailDTOS);
    }


    /**
     * @Method queryOperAccountTempDetailDTOPage
     * @Desrciption 手术模板明细分页查询
     * @param operAccountTempDetailDTO
     * @Author liuqi1
     * @Date   2020/12/5 11:08
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public List<OperAccountTempDetailDTO> queryOperAccountTempDetailDTO(OperAccountTempDetailDTO operAccountTempDetailDTO) {
        List<OperAccountTempDetailDTO> operAccountTempDetailDTOS = operAccountTempDetailDAO.queryOperAccountTempDetailDTOPage(operAccountTempDetailDTO);

        if (!ListUtils.isEmpty(operAccountTempDetailDTOS)){
            //补充单位信息
            operAccountTempDetailDTOS = updateNumCode(operAccountTempDetailDTO,operAccountTempDetailDTOS);

            //补充价格信息
            operAccountTempDetailDTOS = updatePrice(operAccountTempDetailDTO,operAccountTempDetailDTOS);

            //补充发药药房
            operAccountTempDetailDTOS = updatePharId(operAccountTempDetailDTO,operAccountTempDetailDTOS);


        }

        return operAccountTempDetailDTOS;
    }

    //补充发药药房
    private List<OperAccountTempDetailDTO> updatePharId(OperAccountTempDetailDTO operAccountTempDetailDTO, List<OperAccountTempDetailDTO> operAccountTempDetailDTOS) {
        ArrayList<String> pharIdList = new ArrayList<>();
        operAccountTempDetailDTOS.forEach((dto) ->{
            pharIdList.add(dto.getPharId());
        });
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> pharMap = new HashMap<>();
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setHospCode(operAccountTempDetailDTO.getHospCode());
        baseDeptDTO.setIds(pharIdList);
        map.put("hospCode",operAccountTempDetailDTO.getHospCode());
        map.put("baseDeptDTO",baseDeptDTO);
        List<BaseDeptDTO> deptByIds = baseDeptService.getDeptByIds(map);

        deptByIds.forEach(dept ->{
            pharMap.put(dept.getId(),dept);
        });

        for(int i = 0 ; i < operAccountTempDetailDTOS.size(); i++){
            if(StringUtils.isNotEmpty(operAccountTempDetailDTOS.get(i).getPharId())){
                BaseDeptDTO baseDept = (BaseDeptDTO) (pharMap.get(operAccountTempDetailDTOS.get(i).getPharId()));
                operAccountTempDetailDTOS.get(i).setPharName(baseDept == null ? "" : (baseDept.getName() == null ? "" : baseDept.getName() ));
            }
        }
        return operAccountTempDetailDTOS;
    }

    //补充价格信息
    private List<OperAccountTempDetailDTO> updatePrice(OperAccountTempDetailDTO operAccountTempDetailDTO, List<OperAccountTempDetailDTO> operAccountTempDetailDTOS) {

        ArrayList<String> drugIdList = new ArrayList<>();
        ArrayList<String> itemIdList = new ArrayList<>();
        ArrayList<String> materialIdList = new ArrayList<>();
        operAccountTempDetailDTOS.forEach((dto) ->{
            if(EnumUtil.XMLB1.getKey().equals(dto.getItemCode())){
                drugIdList.add(dto.getItemId());
            };
            if(EnumUtil.XMLB2.getKey().equals(dto.getItemCode())){
                materialIdList.add(dto.getItemId());
            };
            if(EnumUtil.XMLB3.getKey().equals(dto.getItemCode())){
                itemIdList.add(dto.getItemId());
            };

        });

        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> drugMap = new HashMap<>();
        HashMap<String, Object> itemMap = new HashMap<>();
        HashMap<String, Object> materialMap = new HashMap<>();
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
        BaseItemDTO baseItemDTO = new BaseItemDTO();

        if(!ListUtils.isEmpty(drugIdList)){}{
        baseDrugDTO.setHospCode(operAccountTempDetailDTO.getHospCode());
        baseDrugDTO.setIds(drugIdList);
        map.put("baseDrugDTO",baseDrugDTO);
        map.put("hospCode",operAccountTempDetailDTO.getHospCode());
        List<BaseDrugDTO> baseDrugDTOS = baseDrugService.queryAll(map).getData();
            baseDrugDTOS.forEach((drug) ->{
            drugMap.put(drug.getId(),drug);
            });
        }

        if(!ListUtils.isEmpty(materialIdList)){}{
        baseMaterialDTO.setHospCode(operAccountTempDetailDTO.getHospCode());
        baseMaterialDTO.setIds(materialIdList);
        map.put("baseMaterialDTO",baseMaterialDTO);
        List<BaseMaterialDTO> baseMaterialDTOS = baseMaterialService.queryBaseMaterialDTOs(map).getData();
         baseMaterialDTOS.forEach((material) ->{
            materialMap.put(material.getId(),material);
         });
        }
        if(!ListUtils.isEmpty(itemIdList)){}{
        baseItemDTO.setHospCode(operAccountTempDetailDTO.getHospCode());
        baseItemDTO.setIds(itemIdList);
        map.put("baseItemDTO",baseItemDTO);
        List<BaseItemDTO> baseItemDTOS = baseItemService.queryAll(map).getData();
            baseItemDTOS.forEach((item) ->{
            itemMap.put(item.getId(),item);
            });
        }
        for(int i =0 ; i < operAccountTempDetailDTOS.size(); i++){
            if(EnumUtil.XMLB1.getKey().equals(operAccountTempDetailDTOS.get(i).getItemCode())){
                BaseDrugDTO baseDrug = (BaseDrugDTO)drugMap.get(operAccountTempDetailDTOS.get(i).getItemId());
                operAccountTempDetailDTOS.get(i).setPrice(baseDrug.getPrice());
                operAccountTempDetailDTOS.get(i).setTotalPrice(baseDrug.getPrice().multiply(operAccountTempDetailDTOS.get(i).getNum()));
                operAccountTempDetailDTOS.get(i).setBigTypeCode(baseDrug.getBigTypeCode());
            };
            if(EnumUtil.XMLB2.getKey().equals(operAccountTempDetailDTOS.get(i).getItemCode())){
                BaseMaterialDTO baseMaterial = (BaseMaterialDTO)materialMap.get(operAccountTempDetailDTOS.get(i).getItemId());
                operAccountTempDetailDTOS.get(i).setPrice(baseMaterial.getPrice());
                operAccountTempDetailDTOS.get(i).setTotalPrice(baseMaterial.getPrice().multiply(operAccountTempDetailDTOS.get(i).getNum()));
            };
            if(EnumUtil.XMLB3.getKey().equals(operAccountTempDetailDTOS.get(i).getItemCode())){
                BaseItemDTO baseItem = (BaseItemDTO)itemMap.get(operAccountTempDetailDTOS.get(i).getItemId());
                operAccountTempDetailDTOS.get(i).setPrice(baseItem.getPrice());
                operAccountTempDetailDTOS.get(i).setTotalPrice(baseItem.getPrice().multiply(operAccountTempDetailDTOS.get(i).getNum()));
            };
        }
        return operAccountTempDetailDTOS;
    }

    //补充单位信息
    private List<OperAccountTempDetailDTO> updateNumCode(OperAccountTempDetailDTO operAccountTempDetailDTO,List<OperAccountTempDetailDTO> operAccountTempDetailDTOS) {
        ArrayList<String> numUnitCodeList = new ArrayList<>();
        operAccountTempDetailDTOS.forEach(dto ->{
            numUnitCodeList.add(dto.getNumUnitCode());
        });

        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        HashMap<String, Object> codeMap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        sysCodeDetailDTO.setHospCode(operAccountTempDetailDTO.getHospCode());
        sysCodeDetailDTO.setValueList(numUnitCodeList);
        sysCodeDetailDTO.setCode("DW");
        map.put("sysCodeDetailDTO",sysCodeDetailDTO);
        map.put("hospCode",operAccountTempDetailDTO.getHospCode());
        WrapperResponse<PageDTO> pageDTOWrapperResponse = sysCodeService.queryCodeDetailPage(map);
        List<SysCodeDetailDTO> sysCodeDetailDTOS = (List<SysCodeDetailDTO>) pageDTOWrapperResponse.getData().getResult();
        sysCodeDetailDTOS.forEach(code ->{
            codeMap.put(code.getValue(),code);
        });
        String reg = "[\\u4e00-\\u9fa5]+";
        for(int i = 0 ; i < operAccountTempDetailDTOS.size(); i++){
            SysCodeDetailDTO sysCode = (SysCodeDetailDTO) (codeMap.get(operAccountTempDetailDTOS.get(i).getNumUnitCode()));
            operAccountTempDetailDTOS.get(i).setNum(operAccountTempDetailDTOS.get(i).getNum().setScale(2, BigDecimal.ROUND_HALF_UP) );

            String numUnitCode = operAccountTempDetailDTOS.get(i).getNumUnitCode();
            if( numUnitCode != null && numUnitCode.matches(reg)){
                operAccountTempDetailDTOS.get(i).setCompany(operAccountTempDetailDTOS.get(i).getNum()+operAccountTempDetailDTOS.get(i).getNumUnitCode());
            }else{
                operAccountTempDetailDTOS.get(i).setCompany(operAccountTempDetailDTOS.get(i).getNum()+(sysCode==null?"":sysCode.getName()));
            }

        }
        return operAccountTempDetailDTOS;
    }

    /**
    * @Method insertOperAccountTempDTO
    * @Desrciption 手术模板、明细新增
    * @param operAccountTempDTO
    * @Author liuqi1
    * @Date   2020/12/5 11:08
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean insertOperAccountTempDTO(OperAccountTempDTO operAccountTempDTO) {
        List<OperAccountTempDetailDTO> listDetail = operAccountTempDTO.getListDetail();

        //为模板赋值
        String tempId = SnowflakeUtils.getId();// 模板 id
        Date nowTime = DateUtils.getNow();// 当前时间
        String name = operAccountTempDTO.getName();
        if(StringUtils.isNotEmpty(name)){
            operAccountTempDTO.setPym(PinYinUtils.toFirstPY(name));
            operAccountTempDTO.setWbm(WuBiUtils.getWBCode(name));

        }
        operAccountTempDTO.setId(tempId);
        operAccountTempDTO.setCrteTime(nowTime);

        // 为模板明细赋值
        for(OperAccountTempDetailDTO dto:listDetail){
            dto.setId(SnowflakeUtils.getId());//模板明细id
            dto.setHospCode(operAccountTempDTO.getHospCode());
            dto.setTempId(tempId);//模板id
            dto.setCrteId(operAccountTempDTO.getCrteId());
            dto.setCrteName(operAccountTempDTO.getCrteName());
            dto.setCrteTime(nowTime);
        }
      /*  OperAccountTempDTO isOperAccountTemp = operAccountTempDAO.queryOperAccountTempDTOById(operAccountTempDTO);
        if(isOperAccountTemp != null){
            return false;
        }*/
        int result = operAccountTempDAO.insertOperAccountTempDTO(operAccountTempDTO);
        int detailResult = operAccountTempDetailDAO.insertOperAccountTempDetailDTOBatch(listDetail);

        return (result > 0 && detailResult >0);
    }

    /**
    * @Menthod deleteOperAccountTempDetailByTempId
    * @Desrciption  根据模版id删除模版和明细
     * @param operAccountTempDTO
    * @Author xingyu.xie
    * @Date   2021/1/22 11:15
    * @Return boolean
    **/
    public Boolean deleteOperAccountTempDTOById(OperAccountTempDTO operAccountTempDTO){
        int i = operAccountTempDAO.deleteOperAccountTempDTOById(operAccountTempDTO);
        int i1 = operAccountTempDAO.deleteOperAccountTempDetailByTempId(operAccountTempDTO);
        return i>0 && i1>0;
    }


    /**
    * @Menthod deleteOperAccountTempDetailById
    * @Desrciption  保存模版内容和模版明细内容
     * @param operAccountTempDTO
    * @Author xingyu.xie
    * @Date   2021/1/22 11:16
    * @Return boolean
    **/
    public Boolean saveOperAccountTemp(OperAccountTempDTO operAccountTempDTO){

        List<OperAccountTempDetailDTO> listDetail = operAccountTempDTO.getListDetail();
        String name = operAccountTempDTO.getName();
        if (ListUtils.isEmpty(listDetail)){
            throw new AppException("模版明细不能为空!");
        }

        if (StringUtils.isEmpty(name)){
            throw new AppException("请输入模版名称");
        }

        operAccountTempDTO.setWbm(WuBiUtils.getWBCode(name));

        operAccountTempDTO.setPym(PinYinUtils.toFirstPY(name));

        if (operAccountTempDTO.getCrteTime() == null){

            operAccountTempDTO.setCrteTime(DateUtils.getNow());
        }

        // 如果模版id为空说明新增，生成id   不为空则修改直接删除原来的明细信息
        if (StringUtils.isEmpty(operAccountTempDTO.getId())){

            operAccountTempDTO.setId(SnowflakeUtils.getId());

        }else {

            operAccountTempDAO.deleteOperAccountTempDTOById(operAccountTempDTO);
            operAccountTempDAO.deleteOperAccountTempDetailByTempId(operAccountTempDTO);
        }



        for (OperAccountTempDetailDTO operAccountTempDetailDTO :listDetail){

            if (StringUtils.isEmpty(operAccountTempDetailDTO.getId())){
                operAccountTempDetailDTO.setId(SnowflakeUtils.getId());
            }
            operAccountTempDetailDTO.setTempId(operAccountTempDTO.getId());

            operAccountTempDetailDTO.setHospCode(operAccountTempDTO.getHospCode());

            operAccountTempDetailDTO.setCrteTime(operAccountTempDTO.getCrteTime());

            operAccountTempDetailDTO.setCrteId(operAccountTempDTO.getCrteId());

            operAccountTempDetailDTO.setCrteName(operAccountTempDTO.getCrteName());
        }

        int i1 = operAccountTempDAO.insertOperAccountTempDTO(operAccountTempDTO);

        int i = operAccountTempDetailDAO.insertOperAccountTempDetailDTOBatch(listDetail);

        return i1>0 && i>0;
    }


}
