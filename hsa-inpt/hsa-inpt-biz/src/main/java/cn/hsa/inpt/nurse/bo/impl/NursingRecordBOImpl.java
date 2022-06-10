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
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

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
     * 系统参数服务
     */
    @Resource
    private SysParameterService sysParameterService_consumer;

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

        // 获取需要分行的护理单据列表集合
        List<String> bnoCodeList = this.getBnoCodeList(nurseRecordDTO.getHospCode(), "HLD_BRANCH_LIST");

        //根据单据id查询单据类型
        BaseNurseOrderDTO baseNurseOrderDTO = this.getOrderDTO(nurseRecordDTO);

        List<InptNurseRecordDTO> addList = new ArrayList<>();
        List<InptNurseRecordDTO> editList = new ArrayList<>();
        List<InptNurseRecordDTO> deleteList = new ArrayList<>();
        Map<Integer, Object> groupNoFlagMap = new HashMap<>();

        for (InptNurseRecordDTO inptNurseRecordDTO : saveList) {
            if (StringUtils.isEmpty(inptNurseRecordDTO.getId())) {
                if (StringUtils.isNotEmpty(baseNurseOrderDTO.getCode()) && bnoCodeList.contains(baseNurseOrderDTO.getCode())){
                    if (inptNurseRecordDTO.getGroupNo() == null || inptNurseRecordDTO.getGroupNo() <= 0 ) {
                        throw new RuntimeException("保存的数据检测到没有组号，请刷新后重试");
                    }
                    //记录标志 时间，组号
                    String recordTime = inptNurseRecordDTO.getItem039() + ":00";
                    groupNoFlagMap.put(inptNurseRecordDTO.getGroupNo(), recordTime);
                }

                //新增
                this.handelParam(userId, userName, addList, inptNurseRecordDTO);
            } else {
                //编辑
                if (StringUtils.isNotEmpty(baseNurseOrderDTO.getCode()) && bnoCodeList.contains(baseNurseOrderDTO.getCode())) {
                    if (inptNurseRecordDTO.getGroupNo() == null || inptNurseRecordDTO.getGroupNo() <= 0) {
                        throw new RuntimeException("保存的数据检测到没有组号，请刷新后重试");
                    }
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

        // 判断新增的addList，数据是否合格。不同时间的组号相同的为不合格数据
        for (InptNurseRecordDTO dto : addList) {
            if (StringUtils.isNotEmpty(baseNurseOrderDTO.getCode()) && bnoCodeList.contains(baseNurseOrderDTO.getCode())) {
                // 日期+时间 2021-10-13 13:10:00
                Integer groupNo = dto.getGroupNo();
                String recordTime  = dto.getItem039() + ":00";
                if (!MapUtils.get(groupNoFlagMap, groupNo).equals(recordTime)){
                    throw new RuntimeException("【" + recordTime + "】与【" + MapUtils.get(groupNoFlagMap, groupNo) +"】组号冲突，请刷新后再试");
                }
            }
        }
        if (!ListUtils.isEmpty(addList)) {
            nursingRecordDAO.insert(addList);
        }
        if (!ListUtils.isEmpty(editList)) {
            if (StringUtils.isNotEmpty(baseNurseOrderDTO.getCode()) && bnoCodeList.contains(baseNurseOrderDTO.getCode())) {
                List<InptNurseRecordDTO> dtoList = this.splitNurse(editList);
                log.debug("重新插入的数据数据===: " + JSON.toJSONString(dtoList));

                nursingRecordDAO.delete(deleteList);
                nursingRecordDAO.insert(dtoList);
//                nursingRecordDAO.insert(editList);
            } else {
                nursingRecordDAO.edit(editList);
            }
        }
        return true;
    }

    /**
     *  @param editList
     * @return
     */
    private List<InptNurseRecordDTO> splitNurse(List<InptNurseRecordDTO> editList){
        List<InptNurseRecordDTO> result = new ArrayList<>();

        // 查询系统参数护理分割行量
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hospCode", editList.get(0).getHospCode());
        map.put("code", "HLJLD_BQJL_HHFG");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sysParameterDTO == null || StringUtils.isEmpty(sysParameterDTO.getValue())) {
            sysParameterDTO.setValue("10"); //默认10
        }
        int splitNum = Integer.parseInt(sysParameterDTO.getValue());

        // 根据组号查询同组的数据
/*        List<String> groupNoList = editList.stream().filter(inptNurseRecordDTO -> StringUtils.isNotEmpty(String.valueOf(inptNurseRecordDTO.getGroupNo()))).map(inptNurseRecordDTO -> String.valueOf(inptNurseRecordDTO.getGroupNo())).distinct().collect(Collectors.toList());
        map.put("groupNoList", groupNoList);
        map.put("nurseRecordDTO", editList.get(0));
        List<InptNurseRecordDTO> dtoList = nursingRecordDAO.queryNurseByGroup(map);
        if (ListUtils.isEmpty(dtoList)) {
            return result;
        }*/

        // listMap: hospCode_visitId_bnoId_groupNo
        Map<String, List<InptNurseRecordDTO>> listMap = editList.stream().collect(Collectors.groupingBy(inptNurseRecordDTO -> inptNurseRecordDTO.getHospCode() + "_" + inptNurseRecordDTO.getVisitId() + "_" + inptNurseRecordDTO.getBnoId() + "_" + inptNurseRecordDTO.getGroupNo()));
        for (String key : listMap.keySet()) {
            List<InptNurseRecordDTO> list = listMap.get(key);
            if (ListUtils.isEmpty(list)) {
                continue;
            }
            list = list.stream().sorted(Comparator.comparing(InptNurseRecordDTO::getGroupSeqNo)).collect(Collectors.toList());
            if (list.get(0) != null && !(1 == list.get(0).getGroupSeqNo())){
                throw new RuntimeException("请勾选同页同组的数据");
            }

            this.buildNurseRecord(result, splitNum, list);
        }
        return result;
    }

    /**
     * 构建分割的护理记录数据
     * @param result 返回数据所有，包含分割、无须分割的数组
     * @param splitNum 切割行数据量
     * @param list 页面传递的对象
     * @return
     */
    private void buildNurseRecord(List<InptNurseRecordDTO> result, int splitNum, List<InptNurseRecordDTO> list) {
        int count = 0;
        for (InptNurseRecordDTO nurseRecordDTO : list) {
            String item027 = nurseRecordDTO.getItem027();
            if (StringUtils.isEmpty(item027)) {
                result.add(nurseRecordDTO);
            } else {
                int length = item027.toCharArray().length;
                InptNurseRecordDTO dto = new InptNurseRecordDTO();
                // 同组未分组的
                if (StringUtils.isEmpty(item027) || length / splitNum < 1) {
                    BeanUtils.copyProperties(nurseRecordDTO, dto);
                    dto.setId(SnowflakeUtils.getId());
                    result.add(dto);
                } else {
                    // 同组已分组的的只对第一条进行处理，重新分组
                    if (nurseRecordDTO.getGroupSeqNo() == 1) {
                        count = length / splitNum;
                        if (length % splitNum > 0) {
                            count = count + 1;
                        }
                        for (int i = 0; i < count; i++) {
                            dto = new InptNurseRecordDTO();
                            BeanUtils.copyProperties(nurseRecordDTO, dto);
                            dto.setId(SnowflakeUtils.getId());
                            dto.setGroupSeqNo(i + 1);
                            if (i == (count - 1)) {
                                dto.setIsEnd(Constants.SF.S);
                            } else {
                                dto.setIsEnd(Constants.SF.F);
                            }

                            // 同组非第一条的数据处理
                            if (dto.getGroupSeqNo() != 1) {
                                String[] attributeNames = getAttributeNames(dto);
                                for (String attributeName : attributeNames) {
                                    if (attributeName.startsWith("item")) {
                                        // 分行的排除item027护理记录及item028签名
                                        if (!("item027".equals(attributeName) || "item028".equals(attributeName))) {
                                            setAttributeValue(dto, attributeName, null);
                                        }
                                    }
                                }
                            }

                            dto.setItem039(nurseRecordDTO.getItem001() + " " + nurseRecordDTO.getItem004());
                            // 最后一条截取全部
                            if (Constants.SF.S.equals(dto.getIsEnd())) {
                                dto.setItem040(nurseRecordDTO.getItem027().substring( i * splitNum));
                            } else {
                                dto.setItem040(nurseRecordDTO.getItem027().substring( i * splitNum, (i+1) * splitNum));
                            }
                            result.add(dto);
                        }
                    }
                }
            }
        }
    }

    private List<String> getBnoCodeList(String hospCode, String code) {
        List<String> bnoCodeList = new ArrayList<>();
        bnoCodeList.add("hljld");
        bnoCodeList.add("xsehljld");
        bnoCodeList.add("cqdchljld");
        bnoCodeList.add("chhljld");
        bnoCodeList.add("ckhljld");
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("code", code);
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sysParameterDTO != null && StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
            // 已配置
            bnoCodeList = new ArrayList<>(Arrays.asList(sysParameterDTO.getValue().split(",")));
        }
        return bnoCodeList;
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

            // 获取需要分行的护理单据列表集合
            List<String> bnoCodeList = this.getBnoCodeList(hospCode, "HLD_BRANCH_LIST");

            List<Integer> groupNoList = new ArrayList<>();

            //常规护理记录单、新生儿护理记录单、产前待产护理记录单、产后护理记录单
            if (bnoCodeList.contains(baseNurseOrderDTO.getCode())) {
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

        // 获取需要分行的护理单据列表集合
        List<String> bnoCodeList = this.getBnoCodeList(inptNurseRecordDTO.getHospCode(), "HLD_BRANCH_LIST");

        if (bnoCodeList.contains(orderDTO.getCode())) {
            inptNurseRecordDTO.setOrderFlag(Constants.SF.S);
        }

        // 血糖记录单排序
        if ("xtdjb".equals(orderDTO.getCode())) {
            inptNurseRecordDTO.setOrderFlag("2");
        }
        // 血运记录单排序
        if ("xyjld".equals(orderDTO.getCode())) {
            inptNurseRecordDTO.setOrderFlag("3");
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
        // 获取需要分行的护理单据列表集合
        List<String> bnoCodeList = this.getBnoCodeList(inptNurseRecordDTO.getHospCode(), "HLD_BRANCH_LIST");
        if (bnoCodeList.contains(orderDTO.getCode())) {
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
        String timeItemCode = null;
        String signatureItemCode = null;
        for (BaseNurseTbHeadDTO baseNurseTbHeadDTO : allTbHeadList) {
            //如果表头格式为日期格式，拼接小结时间为开始+结束时间
            if ("DATE".equals(baseNurseTbHeadDTO.getDateTypeCode())) {
                dateItemCode = baseNurseTbHeadDTO.getItemCode(); //item001
            }
            //如果表头格式为日期格式，拼接小结时间为开始+结束时间
            if ("TIME".equals(baseNurseTbHeadDTO.getDateTypeCode())) {
                timeItemCode = baseNurseTbHeadDTO.getItemCode(); //item001
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
        String infoDate = "";
        String infoTime = "";

        // 根据就诊id，护理单据规则查询
        Integer max = nursingRecordDAO.getMaxGroupNo(inptNurseRecordDTO);
        if (max == null) {
            max = 0;
        }

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
        info.setGroupNo(max + 1); // 设置组号
        info.setGroupSeqNo(1); // 组内序号默认为1
        info.setIsEnd(Constants.SF.S); // 是否末行，是
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

            // 获取当前汇总项目的汇总名称 量(300)->名称(输液)
            String num = itemCode.split("item")[1];
            Integer nameNum = Integer.parseInt(num) - 1;
            String nameItemCode = "";
            if (nameNum < 10) {
                nameItemCode = "item00" + String.valueOf(nameNum);
            } else {
                nameItemCode = "item0" + String.valueOf(nameNum);
            }

            //将汇总项目值合并到日间小结记录info中
            String[] infoNames = getAttributeNames(info);
            for (String infoName : infoNames) {
                // 设置汇总项值，以及汇总项目名称
                if (infoName.equals(itemCode)) {
                    setAttributeValue(info, nameItemCode, hours + "h小结");
                    setAttributeValue(info, infoName, Integer.toString(sum));
                }
                // 设置日期，为日间小结的截止日期的日期
                if (infoName.equals(dateItemCode) && StringUtils.isNotEmpty(dateItemCode)) {
                   infoDate = DateUtils.format(eTime, DateUtils.Y_M_D);
                    setAttributeValue(info, infoName, infoDate);
                }
                // 设置时间
                if (infoName.equals(timeItemCode) && StringUtils.isNotEmpty(timeItemCode)) {
                   infoTime = DateUtils.format(eTime, DateUtils.H_M);
                    setAttributeValue(info, infoName, infoTime);
                }
                // 设置签名
                if (infoName.equals(signatureItemCode) && StringUtils.isNotEmpty(signatureItemCode)) {
                    setAttributeValue(info, infoName, userId);
                }
            }
        }
        // 设置排序字段
        info.setItem039( infoDate+ " " + infoTime);

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
