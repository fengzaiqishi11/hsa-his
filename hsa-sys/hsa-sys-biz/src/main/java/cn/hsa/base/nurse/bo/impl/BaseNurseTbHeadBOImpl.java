package cn.hsa.base.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.base.nurse.bo.BaseNurseTbHeadBO;
import cn.hsa.module.base.nurse.dao.BaseNurseTbHeadDAO;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;
import cn.hsa.module.inpt.nurse.dto.InptNurseRecordDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.base.nurse.bo.impl
 * @Class_name: BaseNurseTbHeadBOImpl
 * @Describe: 护理单据表头bo实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/21 15:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@Component
public class BaseNurseTbHeadBOImpl extends HsafBO implements BaseNurseTbHeadBO {

    @Resource
    private BaseNurseTbHeadDAO baseNurseTbHeadDAO;

    /**
     * @Method queryPage
     * @Desrciption 分页查询所有护理单据表头
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseNurseTbHeadDTO baseNurseTbHeadDTO) {
        PageHelper.startPage(baseNurseTbHeadDTO.getPageNo(), baseNurseTbHeadDTO.getPageSize());
        List<BaseNurseTbHeadDTO> list = baseNurseTbHeadDAO.queryPage(baseNurseTbHeadDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method getTbHeadColumns
     * @Desrciption 获取动态表头列格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return List<BaseNurseTbHeadDTO>
     **/
    @Override
    public List<BaseNurseTbHeadDTO> getTbHeadColumns(BaseNurseOrderDTO baseNurseOrderDTO) {
        baseNurseOrderDTO.setIsValid(Constants.SF.S);
        List<BaseNurseTbHeadDTO> result = new ArrayList<>();
        //查询出对应护理单据下的所有表头列
        List<BaseNurseTbHeadDTO> baseNurseTbHeadDTOS = baseNurseTbHeadDAO.queryTbHeadByCode(baseNurseOrderDTO);

        //数据转换
        if (!ListUtils.isEmpty(baseNurseTbHeadDTOS)) {
            List<BaseNurseTbHeadDTO> list = new ArrayList<>();
            for (BaseNurseTbHeadDTO item : baseNurseTbHeadDTOS) {
                //排除没有上级表头的护理单据格式，用于按upCode分组
                if (!StringUtils.isEmpty(item.getUpCode())) {
                    list.add(item);
                }

                //将数据来源方式为自定义下拉(sourceCode=1)的值转换成页面所需的List<Map>数组
                if ("1".equals(item.getSourceCode()) && StringUtils.isNotEmpty(item.getSourceValue())) {
                    List arrayList = new ArrayList();
                    String[] sourceValues = item.getSourceValue().split("/");
                    for (String sourceValue : sourceValues) {
                        Map<String, String> m = new HashMap<>();
                        m.put("code", sourceValue);
                        m.put("name", sourceValue);
                        arrayList.add(m);
                    }
                    //将转换后的数据来源方式值赋值给sourceValueList
                    item.setSourceValueList(arrayList);
                }

                //将数据来源方式为自定义SQL(sourceCode=2)的护士签名的值转换
                if ("2".equals(item.getSourceCode()) && StringUtils.isNotEmpty(item.getSourceValue())) {
                    String nurseNameSql = item.getSourceValue();
                    nurseNameSql = nurseNameSql.replace("?", item.getHospCode());
                    Map map = new HashMap();
                    map.put("sql", nurseNameSql);
                    List<Map> nurseNameList = baseNurseTbHeadDAO.queryNurseName(map);
                    item.setSourceValueList(nurseNameList);
                }
            }

            //根据上级表头编码code将护理单据下所有表头列分组(map: upCode -> List<BaseNurseTbHeadDTO>)
            Map<String, List<BaseNurseTbHeadDTO>> map = list.stream().collect(Collectors.groupingBy(item -> item.getUpCode()));

            for (BaseNurseTbHeadDTO nurseTbHeadDTO : baseNurseTbHeadDTOS) {
                List<BaseNurseTbHeadDTO> dtos = map.get(nurseTbHeadDTO.getCode());
                if (!ListUtils.isEmpty(dtos)) {
                    //有子节点，设置子节点
                    nurseTbHeadDTO.setChildren(dtos);
                    // 判断子集是否包含是否分行
                    boolean isBranch = getChildrenIsBranch(nurseTbHeadDTO);
                    if (isBranch){
                        nurseTbHeadDTO.setIsBranch("2");
                    } else {
                        nurseTbHeadDTO.setIsBranch("0");
                    }
                }
                //将根级菜单存入返回的表头动态列中
                if (StringUtils.isEmpty(nurseTbHeadDTO.getUpCode())) {
                    result.add(nurseTbHeadDTO);
                }
            }
        }
        return result;
    }

    private boolean getChildrenIsBranch(BaseNurseTbHeadDTO nurseTbHeadDTO) {
        int num = 0;
        List<BaseNurseTbHeadDTO> dtos = nurseTbHeadDTO.getChildren();
        if (!ListUtils.isEmpty(dtos)) {
            for (BaseNurseTbHeadDTO dto : dtos) {
                if (!ListUtils.isEmpty(dto.getChildren())) {
                    this.getChildrenIsBranch(dto);
                } else {
                    if (StringUtils.isNotEmpty(dto.getIsBranch()) && "1".equals(dto.getIsBranch())) {
                        num++;
                    }
                }
            }
        }
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] args) {
        BaseNurseOrderDTO a = new BaseNurseOrderDTO();
        a.setHospCode("1000001");
        String sql = "select id,code, name from sys_user where is_in_job = '1' and hosp_code = '?'";
        sql = sql.replace("?", a.getHospCode());
        System.out.println("sql = " + sql);
    }

    /**
     * @Method saveTbHead
     * @Desrciption 护理单据表头格式新增/修改
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return Boolean
     **/
    @Override
    public Boolean saveTbHead(Map map) {
        String userId = (String) map.get("userId");
        String userName = (String) map.get("userName");
        BaseNurseTbHeadDTO baseNurseTbHeadDTO = (BaseNurseTbHeadDTO) map.get("baseNurseTbHeadDTO");
        //当数据来源方式为护士人员信息时，设置护士人员sql语句值到资源值中
        if ("2".equals(baseNurseTbHeadDTO.getSourceCode())) {
            String sql = "select id,code,name from sys_user where is_in_job = '1' and status_code = '0' and work_type_code like '20%' and hosp_code = '?'";
            baseNurseTbHeadDTO.setSourceValue(sql);
        }
        try {
            if (StringUtils.isEmpty(baseNurseTbHeadDTO.getBnoCode())) {
                throw new RuntimeException("未选择护理单据类型");
            }
            int count = baseNurseTbHeadDAO.getByCode(baseNurseTbHeadDTO);
            if (count > 0) {
                throw new RuntimeException("【" + baseNurseTbHeadDTO.getCode() + "】护理表头格式已经存在");
            }
            //判断页面选择的项目编码是否已被使用
            if (StringUtils.isNotEmpty(baseNurseTbHeadDTO.getItemCode())) {
                if (baseNurseTbHeadDAO.getByItemCode(baseNurseTbHeadDTO) > 0) {
                    throw new RuntimeException("【" + baseNurseTbHeadDTO.getItemCode() + "】该项目编码已经被使用");
                }
            }
            if (StringUtils.isEmpty(baseNurseTbHeadDTO.getId())) {
                //新增
                baseNurseTbHeadDTO.setId(SnowflakeUtils.getId());
                baseNurseTbHeadDTO.setIsValid(Constants.SF.S);
                baseNurseTbHeadDTO.setCrteId(userId);
                baseNurseTbHeadDTO.setCrteName(userName);
                baseNurseTbHeadDTO.setCrteTime(DateUtils.getNow());
                baseNurseTbHeadDAO.insert(baseNurseTbHeadDTO);
            } else {
                //修改
                baseNurseTbHeadDAO.update(baseNurseTbHeadDTO);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * @Method deleteTbHead
     * @Desrciption 护理单据表头格式删除
     * @Param List<BaseNurseTbHeadDTO>
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return Boolean
     **/
    @Override
    public Boolean deleteTbHead(List<BaseNurseTbHeadDTO> baseNurseTbHeadDTOS) {
        List<String> list = new ArrayList();
        for (BaseNurseTbHeadDTO baseNurseTbHeadDTO : baseNurseTbHeadDTOS) {
            list.add(baseNurseTbHeadDTO.getCode());
        }
        //先判断是否存在子节点
        Map map = new HashMap();
        map.put("codeList", list);
        map.put("hospCode", baseNurseTbHeadDTOS.get(0).getHospCode());
        map.put("bnoCode", baseNurseTbHeadDTOS.get(0).getBnoCode());
        List<BaseNurseTbHeadDTO> dtoList = baseNurseTbHeadDAO.querySonTbHead(map);
        if (!ListUtils.isEmpty(dtoList)) {
            throw new RuntimeException("该节点存在子节点，请先删除子节点");
        }
        //删除节点
        int count = baseNurseTbHeadDAO.deleteTbHead(baseNurseTbHeadDTOS);
        return count > 0;
    }

    /**
     * @Method getById
     * @Desrciption 根据主键获取唯一护理单据表头格式
     * @Param baseNurseTbHeadDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return BaseNurseTbHeadDTO
     **/
    @Override
    public BaseNurseTbHeadDTO getById(BaseNurseTbHeadDTO baseNurseTbHeadDTO) {
        return baseNurseTbHeadDAO.getById(baseNurseTbHeadDTO);
    }

    /**
     * @Method querySeqNo
     * @Desrciption 查询护理单据下顺序号的最大值，自动填充前端
     * @Param baseNurseTbHeadDTO【bnoCode--护理单据】
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return Integer
     **/
    @Override
    public Integer querySeqNo(BaseNurseTbHeadDTO baseNurseTbHeadDTO) {
        return baseNurseTbHeadDAO.querySeqNo(baseNurseTbHeadDTO);
    }

    /**
     * @Method queryTbHeadByBnoCode
     * @Desrciption 根据护理单据查询出所有表头List，供选择上级标题使用
     * @Param baseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 15:20
     * @Return List<BaseNurseTbHeadDTO>
     **/
    @Override
    public List<BaseNurseTbHeadDTO> queryTbHeadByBnoCode(BaseNurseOrderDTO baseNurseOrderDTO) {
        return baseNurseTbHeadDAO.queryTbHeadByBnoCode(baseNurseOrderDTO);
    }

    /**
     * @Method queryItemCode
     * @Desrciption 查询出所有的护理单据itemCode编码list，用于页面下拉选择项目编码
     * @Param BaseNurseOrderDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/22 10:29
     * @Return List<Map < String, String>>
     **/
    @Override
    public List<Map<String, String>> queryItemCode(BaseNurseOrderDTO baseNurseOrderDTO) {
        //查询出该护理单据下已经使用了的项目编码
        List<String> list = baseNurseTbHeadDAO.queryItemCode(baseNurseOrderDTO);

        InptNurseRecordDTO nurseRecordDTO = new InptNurseRecordDTO();
        String[] names = getAttributeNames(nurseRecordDTO);
        List<String> itemCodes = new ArrayList<>();
        List<Map<String, String>> result = new ArrayList<>();
        for (String itemName : names) {
            if (itemName.startsWith("item")) {
                itemCodes.add(itemName);
            }
        }

        if (!ListUtils.isEmpty(list)) {
            itemCodes.removeAll(list);
        }

        for (String itemCode : itemCodes) {
            Map<String, String> map = new HashMap<>();
            map.put("label", itemCode);
            map.put("value", itemCode);
            result.add(map);
        }
        return result;
    }

    //根据实体类的所有属性名数组
    private String[] getAttributeNames(Object obj) {
        ArrayList<Field> list = new ArrayList();
        Class aClass = obj.getClass();
        while (aClass != null) {
            list.addAll(Arrays.asList(aClass.getDeclaredFields()));
            aClass = aClass.getSuperclass();
        }
        String[] attributeNames = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {
            attributeNames[i] = list.get(i).getName();
        }
        return attributeNames;
    }
}

