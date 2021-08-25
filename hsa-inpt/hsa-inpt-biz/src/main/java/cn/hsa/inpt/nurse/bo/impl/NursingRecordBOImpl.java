package cn.hsa.inpt.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.base.nurse.dto.BaseNurseOrderDTO;
import cn.hsa.module.base.nurse.dto.BaseNurseTbHeadDTO;
import cn.hsa.module.base.nurse.service.BaseNurseOrderService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.NursingRecordBO;
import cn.hsa.module.inpt.nurse.dao.NursingRecordDAO;
import cn.hsa.module.inpt.nurse.dto.InptNurseRecordDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Package_name: cn.hsa.inpt.nurse.bo.impl
 * @Class_name: NursingRecordBOImpl
 * @Describe: 护理记录bo实现类
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2020/9/16 9:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@Component
public class NursingRecordBOImpl extends HsafBO implements NursingRecordBO {

    /**
     * 护理记录单DAO
     */
    @Resource
    private NursingRecordDAO nursingRecordDAO;

    /**
     * 护理单据
     */
    @Resource
    private BaseNurseOrderService baseNurseOrderService_consumer;

    /**
     * @Method save
     * @Desrciption 保存护理记录(新增 、 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(Map map) {
        List<InptNurseRecordDTO> saveList = MapUtils.get(map, "saveList");
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");

        List<InptNurseRecordDTO> addList = new ArrayList<>();
        List<InptNurseRecordDTO> editList = new ArrayList<>();

        //查询所护理单据下所有表头
        List<BaseNurseTbHeadDTO> nurseTBhead = nursingRecordDAO.findAllNurseTBhead(saveList.get(0));
        String scoreItemCode = null;
        for (BaseNurseTbHeadDTO baseNurseTbHeadDTO : nurseTBhead) {
            if ("总分".equals(baseNurseTbHeadDTO.getName()) || baseNurseTbHeadDTO.getName().startsWith("总")) {
                scoreItemCode = baseNurseTbHeadDTO.getItemCode();
            }
        }

        for (InptNurseRecordDTO inptNurseRecordDTO : saveList) {
            if (StringUtils.isEmpty(inptNurseRecordDTO.getBnoId())) {
                throw new RuntimeException("未选择护理单据类型");
            } else if (StringUtils.isEmpty(inptNurseRecordDTO.getVisitId())) {
                throw new RuntimeException("未选择就诊人");
            } else {
                //总分计算（Morse跌倒评测表 + Caprini静脉血栓评分表）
                if ("morse".equals(inptNurseRecordDTO.getBnoCode()) || "caprini".equals(inptNurseRecordDTO.getBnoCode())) {
                    handleTotalScore(inptNurseRecordDTO, scoreItemCode);
                }
                if (StringUtils.isEmpty(inptNurseRecordDTO.getId())) {
                    //新增
//                InptNurseRecordDTO addDTO = new InptNurseRecordDTO();
//                addDTO = addParams(addDTO, inptNurseRecordDTO);
                    handelParam(userId, userName, addList, inptNurseRecordDTO);
                } else {
                    //修改
                    editList.add(inptNurseRecordDTO);
                }
            }
        }

        //新增
        int addCount = 0;
        if (!ListUtils.isEmpty(addList)) {
            addCount = nursingRecordDAO.insert(addList);
        }
        //修改
        int editCount = 0;
        if (!ListUtils.isEmpty(editList)) {
            editCount = nursingRecordDAO.edit(editList);
        }
        if ((addCount + editCount) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Method saveByGroup
     * @Desrciption 分组拆分保存护理记录(新增 、 编辑)
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean saveByGroup(Map map) {
        List<InptNurseRecordDTO> saveList = MapUtils.get(map, "saveList");
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");

        if (ListUtils.isEmpty(saveList)) {
            throw new RuntimeException("未选择需要保存的数据");
        }

        InptNurseRecordDTO nurseRecordDTO = saveList.get(0);
        if (StringUtils.isEmpty(nurseRecordDTO.getBnoId())) {
            throw new RuntimeException("未选择护理单据类型");
        }
        if (StringUtils.isEmpty(nurseRecordDTO.getVisitId())) {
            throw new RuntimeException("未选择就诊人");
        }

        //根据单据id查询单据类型
        BaseNurseOrderDTO baseNurseOrderDTO = this.getOrderDTO(nurseRecordDTO);

        List<InptNurseRecordDTO> addList = new ArrayList<>();
        List<InptNurseRecordDTO> editList = new ArrayList<>();
        List<InptNurseRecordDTO> deleteList = new ArrayList<>();

        for (InptNurseRecordDTO inptNurseRecordDTO : saveList) {
            if (StringUtils.isEmpty(inptNurseRecordDTO.getId())) {
                //新增
                this.handelParam(userId, userName, addList, inptNurseRecordDTO);
            } else {
                //编辑
                if (("hljld".equals(baseNurseOrderDTO.getCode()) || "xsehljld".equals(baseNurseOrderDTO.getCode())  || "cqdchljld".equals(baseNurseOrderDTO.getCode())  || "chhljld".equals(baseNurseOrderDTO.getCode())) && StringUtils.isNotEmpty(baseNurseOrderDTO.getCode())) {
                    //护理记录单，编辑前先删除对应的同组号数据
                    if (deleteList.indexOf(inptNurseRecordDTO.getGroupNo()) == -1) {
                        deleteList.add(inptNurseRecordDTO);
                    }
                    handelParam(userId, userName, editList, inptNurseRecordDTO);
                } else {
                    editList.add(inptNurseRecordDTO);
                }
            }
        }

        if (!ListUtils.isEmpty(addList)) {
            nursingRecordDAO.insert(addList);
        }
        if (!ListUtils.isEmpty(editList)) {
            if (("hljld".equals(baseNurseOrderDTO.getCode()) || "xsehljld".equals(baseNurseOrderDTO.getCode())  || "cqdchljld".equals(baseNurseOrderDTO.getCode())  || "chhljld".equals(baseNurseOrderDTO.getCode())) && StringUtils.isNotEmpty(baseNurseOrderDTO.getCode())) {
                nursingRecordDAO.delete(deleteList);
                nursingRecordDAO.insert(editList);
            } else {
                nursingRecordDAO.edit(editList);
            }
        }
        return true;
    }

    private void handelParam(String userId, String userName, List<InptNurseRecordDTO> addList, InptNurseRecordDTO inptNurseRecordDTO) {
        inptNurseRecordDTO.setId(SnowflakeUtils.getId());
        inptNurseRecordDTO.setCrteId(userId);
        inptNurseRecordDTO.setCrteName(userName);
        inptNurseRecordDTO.setCrteTime(DateUtils.getNow());
        inptNurseRecordDTO.setIsValid(Constants.SF.S); //有效
        inptNurseRecordDTO.setIsDaySum(Constants.SF.F); //不日间小结
        addList.add(inptNurseRecordDTO);
    }

    /**
     * 根据单据id查询单据类型
     *
     * @param nurseRecordDTO
     * @return BaseNurseOrderDTO
     */
    private BaseNurseOrderDTO getOrderDTO(InptNurseRecordDTO nurseRecordDTO) {
        BaseNurseOrderDTO baseNurseOrderDTO = new BaseNurseOrderDTO();
        baseNurseOrderDTO.setHospCode(nurseRecordDTO.getHospCode());
        baseNurseOrderDTO.setId(nurseRecordDTO.getBnoId());
        baseNurseOrderDTO.setIsValid(Constants.SF.S);
        Map orderMap = new HashMap();
        orderMap.put("hospCode", nurseRecordDTO.getHospCode());
        orderMap.put("baseNurseOrderDTO", baseNurseOrderDTO);
        return baseNurseOrderService_consumer.getById(orderMap).getData();
    }

    //处理总分
    private void handleTotalScore(InptNurseRecordDTO inptNurseRecordDTO, String scoreItemCode) {
        //总分计算（Morse跌倒评测表 + Caprini静脉血栓评分表）
        Integer totalScore = 0;
        String[] attributeNames = getAttributeNames(inptNurseRecordDTO);
        for (String attributeName : attributeNames) {
            if (attributeName.startsWith("item")) {
                String value = (String) getAttributeValue(attributeName, inptNurseRecordDTO);
                if (StringUtils.isEmpty(value)) {
                    value = "0";
                }
                if (StringUtils.isNotEmpty(value)) {
                    try {
                        totalScore += Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        //过滤掉签名、日期等非数值项目
                        continue;
                    }
                }
                //过滤掉总分本身项目
                if (attributeName.equals(scoreItemCode) && StringUtils.isNotEmpty(value)) {
                    totalScore -= Integer.parseInt(value);
                }
            }
        }
        //将总分设置到新增记录中
        setAttributeValue(inptNurseRecordDTO, scoreItemCode, totalScore.toString());
    }

    /**
     * @Method delete
     * @Desrciption 删除护理记录
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean delete(Map map) {
        List<InptNurseRecordDTO> list = MapUtils.get(map, "delList");
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");
        String hospCode = MapUtils.get(map, "hospCode");

        //过滤掉页面新增还未保存的数据
        List<InptNurseRecordDTO> delList = new ArrayList<>();
        for (InptNurseRecordDTO nurseRecordDTO : list) {
            if (StringUtils.isNotEmpty(nurseRecordDTO.getId())) {
                delList.add(nurseRecordDTO);
            }
        }

        if (!ListUtils.isEmpty(delList)) {
            //根据单据id查询单据类型
            BaseNurseOrderDTO baseNurseOrderDTO = this.getOrderDTO(delList.get(0));

            List<Integer> groupNoList = new ArrayList<>();

            //常规护理记录单、新生儿护理记录单、产前待产护理记录单、产后护理记录单
            if ("hljld".equals(baseNurseOrderDTO.getCode()) || "xsehljld".equals(baseNurseOrderDTO.getCode()) || "cqdchljld".equals(baseNurseOrderDTO.getCode()) || "chhljld".equals(baseNurseOrderDTO.getCode())) {
                //实际需要删除的库中数据
                List<InptNurseRecordDTO> dtoList = new ArrayList<>();

                for (InptNurseRecordDTO nurseRecordDTO : delList) {
                    //删除同组的
                    if (groupNoList.indexOf(nurseRecordDTO.getGroupNo()) == -1) {
                        groupNoList.add(nurseRecordDTO.getGroupNo());
                    }
                    //删除日间小结的
                    if ("1".equals(nurseRecordDTO.getIsDaySum())) {
                        dtoList.add(nurseRecordDTO);
                    }
                }

                Map groupMap = new HashMap();
                groupMap.put("hospCode", hospCode);
                groupMap.put("groupNoList", groupNoList);
                groupMap.put("nurseRecordDTO", delList.get(0));

                //根据组号查询
                if (!ListUtils.isEmpty(groupNoList)) {
                    dtoList.addAll(nursingRecordDAO.queryNurseByGroup(groupMap));
                }
                if (!ListUtils.isEmpty(dtoList)) {
                    for (InptNurseRecordDTO nurseRecordDTO : dtoList) {
                        nurseRecordDTO.setIsValid(Constants.SF.F);
                        nurseRecordDTO.setDeleteId(userId);
                        nurseRecordDTO.setDeleteName(userName);
                        nurseRecordDTO.setDeleteTime(DateUtils.getNow());
                    }
                    //删除
                    nursingRecordDAO.updateIsValid(dtoList);
                }
            } else {
                for (InptNurseRecordDTO inptNurseRecordDTO : delList) {
                    inptNurseRecordDTO.setIsValid(Constants.SF.F);
                    inptNurseRecordDTO.setDeleteId(userId);
                    inptNurseRecordDTO.setDeleteName(userName);
                    inptNurseRecordDTO.setDeleteTime(DateUtils.getNow());
                }
                //删除
                nursingRecordDAO.updateIsValid(delList);
            }
        }
        return true;
    }

    /**
     * @Method queryNursingRecord
     * @Desrciption 分页查询护理单记录
     * @Param InptNurseRecordDTO inptNurseRecordDTO
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryNursingRecord(InptNurseRecordDTO inptNurseRecordDTO) {
//        inptNurseRecordDTO.setPageSize(10000000);
        PageHelper.startPage(inptNurseRecordDTO.getPageNo(), inptNurseRecordDTO.getPageSize());
        //根据护理单据id查询护理单据
        BaseNurseOrderDTO orderDTO = this.getOrderDTO(inptNurseRecordDTO);
        if ("hljld".equals(orderDTO.getCode()) || "xsehljld".equals(orderDTO.getCode()) || "cqdchljld".equals(orderDTO.getCode()) || "ckhljld".equals(orderDTO.getCode())) {
            inptNurseRecordDTO.setOrderFlag(Constants.SF.S);
        }
        List<InptNurseRecordDTO> list = nursingRecordDAO.queryNursingRecord(inptNurseRecordDTO);
        return PageDTO.of(list);
    }

    /**
     * @Method queryNurseRecordByPrint
     * @Desrciption 护理记录打印接口
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/16 9:04
     * @Return List<InptNurseRecordDTO>
     **/
    @Override
    public List<InptNurseRecordDTO> queryNurseRecordByPrint(InptNurseRecordDTO inptNurseRecordDTO) {
        //根据护理单据id查询护理单据
        BaseNurseOrderDTO orderDTO = this.getOrderDTO(inptNurseRecordDTO);
        if ("hljld".equals(orderDTO.getCode()) || "xsehljld".equals(orderDTO.getCode()) || "cqdchljld".equals(orderDTO.getCode()) || "chhljld".equals(orderDTO.getCode())) {
            inptNurseRecordDTO.setOrderFlag(Constants.SF.S);
        }
        return nursingRecordDAO.queryNursingRecord(inptNurseRecordDTO);
    }

    /**
     * @Method getValue
     * @Desrciption 获取分割参数
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Map<String, String>
     **/
    @Override
    public Map<String, Object> getValue(Map map) {
        String code = "HLJLD_BQJL_HHFG";
        map.put("code", code);
        Map<String, Object> m = nursingRecordDAO.getValue(map);
        String value = MapUtils.get(m, "value");
        Integer num = 0;
        if (StringUtils.isNotEmpty(value)) {
            num = Integer.parseInt(value);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("value", num);
        return res;
    }

    /**
     * @Method getMaxGroupNo
     * @Desrciption 获取当前人员最大组号
     * @Param inptNurseRecordDTO-护理记录
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return Integer
     **/
    @Override
    public Integer getMaxGroupNo(InptNurseRecordDTO inptNurseRecordDTO) {
        return nursingRecordDAO.getMaxGroupNo(inptNurseRecordDTO);
    }

    /**
     * @Method addDaySummary
     * @Desrciption 添加日间小结
     * @Param map
     * @Author: luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date: 2020/9/17 14:46
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean addDaySummary(Map map) {
        InptNurseRecordDTO inptNurseRecordDTO = MapUtils.get(map, "inptNurseRecordDTO");
        String userId = MapUtils.get(map, "userId");
        String userName = MapUtils.get(map, "userName");
        String hospCode = MapUtils.get(map, "hospCode");

        //查询单据是否存在
        int count = nursingRecordDAO.findNurseOrder(inptNurseRecordDTO);
        if (count < 1) {
            throw new RuntimeException("该护理单据不存在");
        }

        //查询日间小结时间段内所有非日间小结的护理记录(isDaySum = '0')
        List<InptNurseRecordDTO> inptNurseRecordDTOS = nursingRecordDAO.queryNursingRecord(inptNurseRecordDTO);
        if (inptNurseRecordDTOS == null || inptNurseRecordDTOS.size() <= 0) {
            throw new RuntimeException("该时间段内没有护理记录");
        }

        //查询出护理单据下所有需要汇总的表头
        List<BaseNurseTbHeadDTO> nurseTbHeadList = nursingRecordDAO.findNurseTbHead(inptNurseRecordDTO);
        if (nurseTbHeadList == null || nurseTbHeadList.size() <= 0) {
            throw new RuntimeException("该护理单据没有可进行日间小结的项目");
        }

        //查询出护理单据下所有的表头
        List<BaseNurseTbHeadDTO> allTbHeadList = nursingRecordDAO.findAllNurseTBhead(inptNurseRecordDTO);
        String dateItemCode = null;
        String signatureItemCode = null;
        for (BaseNurseTbHeadDTO baseNurseTbHeadDTO : allTbHeadList) {
            //如果表头格式为日期格式，拼接小结时间为开始+结束时间
            if ("DATE".equals(baseNurseTbHeadDTO.getDateTypeCode())) {
                dateItemCode = baseNurseTbHeadDTO.getItemCode(); //item001
            }
            //如果表头资源值为下拉自定义SQL，设置小结记录签名人为当前登陆人
            if ("2".equals(baseNurseTbHeadDTO.getSourceCode())) {
                signatureItemCode = baseNurseTbHeadDTO.getItemCode(); //item025
            }
        }

        //计算日间小结小时差
        String startTime = inptNurseRecordDTO.getStartTime();
        String endTime = inptNurseRecordDTO.getEndTime();
        Date sTime = DateUtils.parse(startTime, DateUtils.Y_M_DH_M_S);
        Date eTime = DateUtils.parse(endTime, DateUtils.Y_M_DH_M_S);
        int hours = (int) ((eTime.getTime() - sTime.getTime()) / (1000 * 60 * 60));

        //生成日间小结护理记录dto, 进行赋值
        InptNurseRecordDTO info = new InptNurseRecordDTO();
        info.setId(SnowflakeUtils.getId());
        info.setHospCode(hospCode);
        info.setVisitId(inptNurseRecordDTO.getVisitId()); //就诊id
        if (StringUtils.isNotEmpty(inptNurseRecordDTO.getBabyId())) {
            info.setBabyId(inptNurseRecordDTO.getBabyId()); // 婴儿id
        }
        info.setBnoId(inptNurseRecordDTO.getBnoId()); //护理单据id
        info.setIsDaySum(Constants.SF.S); //是日间小结
        info.setIsValid(Constants.SF.S); //是否有效
        info.setDaySumId(userId); //日间小结人ID
        info.setDaySumName(userName); //日间小结姓名
        info.setDaySumTime(DateUtils.getNow()); //日间小结时间
        info.setCrteId(userId); //创建人id
        info.setCrteName(userName); //创建人名称
        info.setCrteTime(DateUtils.getNow()); //创建人时间

        //汇总日间小结项目值(合并表头需要汇总is_sum='1'的)
        //所有的汇总表头配置信息
        for (BaseNurseTbHeadDTO nurseTbheadDTO : nurseTbHeadList) {
            String itemCode = nurseTbheadDTO.getItemCode(); //表头编码 -- 项目编号itme_001
            String itemName = nurseTbheadDTO.getName(); //表头名称 -- 出量

            int sum = 0;
            //日间小结时间段内的护理记录
            for (InptNurseRecordDTO nurseRecordDTO : inptNurseRecordDTOS) {
                //获取实体类的所有属性名数组
                String[] attributeNames = getAttributeNames(nurseRecordDTO);
                for (String name : attributeNames) {
                    if (name.equals(itemCode)) {
                        String value = (String) getAttributeValue(name, nurseRecordDTO);
                        if (StringUtils.isEmpty(value)) {
                            value = "0";
                        }
                        try {
                            sum += Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            throw new RuntimeException(itemName + "不为数值类型, 不能进行日间小结");
                        }
                    }
                }
            }
            //将汇总项目值合并到日间小结记录info中
            String[] infoNames = getAttributeNames(info);
            for (String infoName : infoNames) {
                if (infoName.equals(itemCode)) {
                    setAttributeValue(info, infoName, Integer.toString(sum));
                }
                if (infoName.equals(dateItemCode) && StringUtils.isNotEmpty(dateItemCode)) {
                    String value = startTime + "/" + endTime;
                    setAttributeValue(info, infoName, value);
                }
                if (infoName.equals(signatureItemCode) && StringUtils.isNotEmpty(signatureItemCode)) {
                    setAttributeValue(info, infoName, userId);
                }
            }
        }

        //插入日间小结护理记录到库中
        try {
            nursingRecordDAO.addDaySummary(info);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有住院病人
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryAll(InptVisitDTO inptVisitDTO) {
        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<InptVisitDTO> inptVisitDTOList = nursingRecordDAO.queryAll(inptVisitDTO);
//        List<Map<String,Object>> inptVisitDTOList = nursingRecordDAO.queryAll(inptVisitDTO);
        return PageDTO.of(inptVisitDTOList);
    }
    /**
     * @Method queryAll
     * @Desrciption 查询所有住院病人,预交金缴纳情况
     * @Param inptVisitDTO-住院病人DTO
     * @Author luoyong
     * @Eamil: luoyong@powersi.com.cn
     * @Date 2020/9/11 16:45
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public  List<Map<String,Object>>  queryAcceptGold(InptVisitDTO inptVisitDTO) {
//        PageHelper.startPage(inptVisitDTO.getPageNo(), inptVisitDTO.getPageSize());
        List<Map<String,Object>> inptVisitDTOList = nursingRecordDAO.queryAcceptGold(inptVisitDTO);
        return inptVisitDTOList;
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

    //根据属性名set属性值 (类对象，属性名，属性值)
    private void setAttributeValue(Object obj, String name, Object value) {
        Class<?> aClass = obj.getClass();
        try {
            Field field = aClass.getDeclaredField(name);
            field.setAccessible(true); //避免调用时的安全检查
            field.set(obj, value);
        } catch (Exception e) {
            aClass = aClass.getSuperclass();
            try {
                Field superField = aClass.getDeclaredField(name);
                superField.setAccessible(true);
                superField.set(obj, value);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //根据属性名获取对应的属性值
    private Object getAttributeValue(String name, Object obj) {
        try {
            String firstLetter = name.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + name.substring(1);
            Method method = obj.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(obj, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

}
