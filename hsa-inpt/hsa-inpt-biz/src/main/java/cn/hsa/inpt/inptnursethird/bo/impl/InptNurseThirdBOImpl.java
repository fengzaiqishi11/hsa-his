package cn.hsa.inpt.inptnursethird.bo.impl;

import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.inptnursethird.bo.InptNurseThirdBO;
import cn.hsa.module.inpt.inptnursethird.dao.InptNurseThirdDao;
import cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO;
import cn.hsa.module.inpt.inptnursethird.entity.InptNurseThirdDO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Package_name: cn.hsa.inpt.inptnursethird.bo.impl
 * @Class_name:: InptNurseThirdBOImpl
 * @Description:
 * @Author: ljh
 * @Date: 2020/9/16 11:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InptNurseThirdBOImpl implements InptNurseThirdBO {

    @Resource
    InptVisitDAO inptVisitDAO;
    @Resource
    private InptNurseThirdDao inptNurseThirdDao;

    /**
     * @Method insertList
     * @Param [inptNurseThirdDTO]
     * @description   新增
     * @author marong
     * @date 2020/10/31 15:14
     * @return int
     * @throws
     */
    @Override
    public int insertList(InptNurseThirdDTO inptNurseThirdDTO) {
        String visitid = inptNurseThirdDTO.getVisitId();
        List<InptNurseThirdDTO> list = inptNurseThirdDTO.getInptNurseThirds();
        String date =inptNurseThirdDTO.getQueryTime();
        String recordTime = DateUtils.format(list.get(0).getRecordTime(),"yyyy-MM-dd");
        String babyId = list.get(0).getBabyId();
        if (StringUtils.isNotEmpty(babyId)) {
            inptNurseThirdDTO.setBabyId(babyId);
        }
        if (!recordTime.equals(date)){
            inptNurseThirdDTO.setQueryTime(recordTime);
        }
        inptNurseThirdDao.deleteById(inptNurseThirdDTO);
        //三册单时间重复bug，本地无法复现，大致定位位置在本段代码，采取去除重复数据再进行保存操作 yuelong.chen 2022/06/09
        if(list.size() > 6){
            list = list.stream().
                    collect(Collectors.collectingAndThen(Collectors.toCollection(
                            ()->new TreeSet<>(Comparator.comparing(InptNurseThirdDTO::getRecordTime))),ArrayList::new));
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setId(SnowflakeUtils.getId());
            list.get(i).setVisitId(visitid);
            list.get(i).setCrteTime(DateUtils.getNow());
        }
        return inptNurseThirdDao.insertList(list);
    }

    /**
     * @Method queryAll
     * @Param [inptNurseThirdDTO]
     * @description
     * @author marong
     * @date 2020/10/31 15:15
     * @return java.util.List<cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO>
     * @throws
     */
    @Override
    public List<InptNurseThirdDTO> queryAll(InptNurseThirdDTO inptNurseThirdDTO)  {
        String now  = DateUtils.format(DateUtils.getNow(),"yyyy-MM-dd");

        String startTime = DateUtils.calculateDate("yyyy-MM-dd",-7);
        inptNurseThirdDTO.setEndTime(now);
        inptNurseThirdDTO.setStartTime(startTime);
        List<InptNurseThirdDTO> inptNurseThirdDTOS = inptNurseThirdDao.queryAll(inptNurseThirdDTO);
        return inptNurseThirdDTOS;
    }


    /**
     * @Method queryAllRecordTime
     * @Param [inptNurseThirdDTO]
     * @description   弹框查询
     * @author marong
     * @date 2020/10/30 11:44
     * @return java.util.List<cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO>
     * @throws
     */
    @Override
    public List<InptNurseThirdDTO> queryAllRecordTime(InptNurseThirdDTO inptNurseThirdDTO)  {
        Date date = DateUtil.stringToDate(inptNurseThirdDTO.getQueryTime(), "yyyy-MM-dd");
        String format = DateUtils.format(date, "yyyy-MM-dd");
        Date startDate = DateUtil.stringToDate(format, "yyyy-MM-dd");
        inptNurseThirdDTO.setStartDate(startDate);
        inptNurseThirdDTO.setEndDate(startDate);
        List<InptNurseThirdDTO> inptNurseThirdDTOS = inptNurseThirdDao.queryAllByTimeSlot(inptNurseThirdDTO);
        return  inptNurseThirdDTOS;
    }

    /**
     * @Method getPageList
     * @Param [inptNurseThirdDTO]
     * @description   获取分页信息
     * @author marong
     * @date 2020/10/29 10:21
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
     */
    @Override
    public List<Map<String, Object>> getPageList(InptNurseThirdDTO inptNurseThirdDTO)  {
        Date date = inptNurseThirdDTO.getInTime();
        Date toDate = inptNurseThirdDTO.getOutTime();
        if (toDate == null) {
            toDate = DateUtils.getNow();
        }
        int count1 = longOfTwoDate(date, toDate);
        Map<String, Object> page = new HashMap<String, Object>();
        List<Map<String, Object>> pageList = new ArrayList<>();
        double count = Double.parseDouble(String.valueOf(count1));
        if (count == 0) {
            count = 1;
        }
        for (int i = 0; i < Math.ceil(count / 7); i++) {
            page = new HashMap<>();
            page.put("value", i + 1);
            page.put("label", "第" +(i + 1)+"页");
            pageList.add(page);
        }
        return pageList;
    }

    /**
     * @Method queryAllByTimeSlot
     * @Param [inptNurseThirdDTO]
     * @description   查询三测单
     * @author marong
     * @date 2020/10/29 10:20
     * @return java.util.List<cn.hsa.module.inpt.inptnursethird.dto.InptNurseThirdDTO>
     * @throws
     */
    @Override
    public Map<String, Object> queryAllByTimeSlot(InptNurseThirdDTO inptNurseThirdDTO) {
        //查询住院病人三测单记录
        List<InptNurseThirdDTO> inptNurseThirdDTOS = getInptNurseThirdDTOS(inptNurseThirdDTO);
        // //查询住院病人做了手术的日期记录
        List<String> recordTimeList = inptNurseThirdDao.getRecordTime(inptNurseThirdDTO);
        Map<String, Object> retMap = new HashMap();
        // 返回页面总共住院天数
        Date inTime = inptNurseThirdDTO.getInTime();
        Date outTime = inptNurseThirdDTO.getOutTime();
        if (outTime == null) {
            outTime =DateUtils.getNow();
        }
        int inDays = longOfTwoDate(inTime, outTime);
        if (inDays == 0) {
            inDays = 1;
        }
        retMap.put("inDays", String.valueOf(inDays));
        retMap.put("endDay",String.valueOf((inptNurseThirdDTO.getPage())*7));
        retMap.put("startDay",String.valueOf((inptNurseThirdDTO.getPage()-1)*7+1));
        retMap.put("startDate",inptNurseThirdDTO.getStartDate());
        retMap.put("endDate",inptNurseThirdDTO.getEndDate());
        //获取 产后/术后天数 dayOps
        if (!ListUtils.isEmpty(inptNurseThirdDTOS)){
            String dayOps [] =  getDayOps(inptNurseThirdDTO,inptNurseThirdDTOS,recordTimeList);
            retMap.put("suhtime",dayOps);

            //获取 模拟值
            List<Map<String,Object>> analogValueList = getAnalogValueList(inptNurseThirdDTOS,inptNurseThirdDTO);
            retMap.put("analogValue",analogValueList);
        }

        return retMap;
    }

    private List<Map<String, Object>> getAnalogValueList(List<InptNurseThirdDTO> inptNurseThirdDTOS,InptNurseThirdDTO inptNurseThirdDTO) {
        List<Map<String,Object>> analogValueList = new ArrayList<>();
        for(InptNurseThirdDTO dto:inptNurseThirdDTOS){
            Map m = new HashMap();
            String timeSlotStr =DateUtils.format(dto.getTimeSlot(),"yyyy-MM-dd");
            Date timeSlot = DateUtils.parse(timeSlotStr,"yyyy-MM-dd");
            Date timeSlotAll = dto.getTimeSlot();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timeSlotAll);
            int i = DateUtil.daysBetweenDates(inptNurseThirdDTO.getInTime(),timeSlot);
            if(i==0){
                i=1;
            }

            // 体温
            BigDecimal temperature = dto.getTemperature();
            if(temperature==null){
                m.put("tiwen","");
            }else{
                BigDecimal a = null;
                if(new BigDecimal(temperature.intValue()).compareTo(temperature)==0){
                    a = temperature.setScale(0, BigDecimal.ROUND_HALF_UP);
                }else{
                    a =  temperature.setScale(1, BigDecimal.ROUND_HALF_UP);
                }
                if(a != null && a.intValue() == 0){
                    m.put("tiwen","");
                }else{
                    m.put("tiwen",a.toString());
                }
            }
            // 复测体温
            BigDecimal temperatureRetest=  dto.getTemperatureRetest();
            if(temperatureRetest==null){
                m.put("fctiwen","");
            }else{
                BigDecimal a = null;
                if(new BigDecimal(temperatureRetest.intValue()).compareTo(temperatureRetest)==0){
                    a =  temperatureRetest.setScale(0, BigDecimal.ROUND_HALF_UP);
                }else{
                    a =   temperatureRetest.setScale(1, BigDecimal.ROUND_HALF_UP);
                }
                if(a != null && a.intValue() == 0){
                    m.put("fctiwen","");
                }else{
                    m.put("fctiwen",a.toString());
                }
            }
            // 脉搏
            if( dto.getPulse() == null){
                m.put("manbo","");
            }else{
                BigDecimal a = null;
                BigDecimal pulse= new BigDecimal( dto.getPulse());
                if(new BigDecimal(pulse.intValue()).compareTo(pulse)==0){
                    a =  pulse.setScale(0, BigDecimal.ROUND_HALF_UP);
                }else{
                    a =   pulse.setScale(1, BigDecimal.ROUND_HALF_UP);
                }
                if(a != null && a.intValue() == 0){
                    m.put("manbo","");
                }else{
                    m.put("manbo",a.toString());
                }
            }
            // 心率
            if(dto.getHeartRate()==null){
                m.put("xinlv","");
            }else{
                BigDecimal heartRate= new BigDecimal(dto.getHeartRate()) ;
                BigDecimal a = null;
                if(new BigDecimal(heartRate.intValue()).compareTo(heartRate)==0){
                    a = heartRate.setScale(0, BigDecimal.ROUND_HALF_UP);
                }else{
                    a =   heartRate.setScale(1, BigDecimal.ROUND_HALF_UP);
                }
                if(a != null && a.intValue() == 0){
                    m.put("xinlv","");
                }else{
                    m.put("xinlv",a.toString());
                }
            }
            // 血糖
            BigDecimal bloodSugar=  dto.getBloodSugar();
            if(bloodSugar==null){
                m.put("bloodSugar","");
            }else{
                BigDecimal a = null;
                if(new BigDecimal(bloodSugar.intValue()).compareTo(bloodSugar)==0){
                    a =  bloodSugar.setScale(0, BigDecimal.ROUND_HALF_UP);
                }else{
                    a =   bloodSugar.setScale(1, BigDecimal.ROUND_HALF_UP);
                }
                if(a != null && a.intValue() == 0){
                    m.put("bloodSugar","");
                }else{
                    m.put("bloodSugar",a.toString());
                }
            }

            String breath = dto.getBreath()==null?"":dto.getBreath().toString();
            String isVentilator =   StringUtils.isEmpty( dto.getIsVentilator())?"": dto.getIsVentilator();

            if("1".equals(isVentilator)){
                breath = "®";
            }
            m.put("riqi",String.valueOf(timeSlot.getTime()));
            //m.put("ruyuantianshu",String.valueOf(i)); //入院天数

            m.put("shijian",String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
            if(StringUtils.isNotEmpty(breath) && "0".equals(breath)){
                m.put("huxi","");
            }else{
                m.put("huxi",breath);
            }

            m.put("peeNO", StringUtils.isEmpty( dto.getPeeCode())?"": dto.getPeeCode());
            m.put("shitNO", StringUtils.isEmpty( dto.getExcrementCode())?"": dto.getExcrementCode());

//            String weight  = dto.getWeight() == null?"":""+(dto.getWeight().setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue()); // 四舍五入取整
//            String height  = dto.getHeight() == null?"":""+(dto.getHeight().setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue()); // 四舍五入取整
            String weight  = dto.getWeight() == null?"":""+(dto.getWeight()); // 四舍五入取整
            String height  = dto.getHeight() == null?"":""+(dto.getHeight()); // 四舍五入取整
            m.put("weight", dto.getWeight()==null?"":weight);
            m.put("height",dto.getHeight()==null?"":height);
            if(StringUtils.isNotEmpty(dto.getAmBp()) ){
                m.put("bloodPressure", dto.getAmBp());
                m.put("amBp", dto.getAmBp());
            }else{
                m.put("amBp", "");
            }
            if(StringUtils.isNotEmpty(dto.getPmBp())){
                m.put("pmBp", dto.getPmBp());
                m.put("bloodPressure", dto.getPmBp());
            }else{
                m.put("pmBp", "");
            }
            if(StringUtils.isEmpty(dto.getAmBp()) && StringUtils.isEmpty(dto.getPmBp()) ){
                m.put("bloodPressure", "");
            }
            String inputQuantity  = dto.getIntake()==null?"":""+(dto.getIntake().setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue()); // 四舍五入取整
            m.put("inputQuantity", dto.getIntake()==null?"":inputQuantity);
            String outputQuantity  = dto.getOutput()==null?"":""+(dto.getOutput().setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue()); // 四舍五入取整
            m.put("outputQuantity", dto.getOutput()==null?"":outputQuantity);
            //其他出量(ml)
            String otherOutputQuantity  = dto.getOtherOutput()==null?"":""+(dto.getOtherOutput().setScale( 0, BigDecimal.ROUND_HALF_UP ).longValue()); // 四舍五入取整
            m.put("otherOutputQuantity", dto.getOtherOutput()==null?"":otherOutputQuantity);
            m.put("skinCode", dto.getSkinCode()==null?"":dto.getSkinCode());   //皮试结果
            m.put("remark", dto.getRemark()==null?"":dto.getRemark());   //其他备注
            m.put("drugAllergy", dto.getDrugAllergy()==null?"":dto.getDrugAllergy());   //药物过敏
            m.put("temperatureCode", StringUtils.isEmpty(dto.getTemperatureCode())?"":dto.getTemperatureCode()); //体温测量仪编码
            m.put("operation", StringUtils.isEmpty(dto.getThirtyFiveDownCode())?"":dto.getThirtyFiveDownCode());
            m.put("tiwenbeizhu40", StringUtils.isEmpty(dto.getFortyUpRemark())?"":dto.getFortyUpRemark());
            m.put("tiwenbeizhu35", StringUtils.isEmpty(dto.getThirtyFiveDownRemark())?"":dto.getThirtyFiveDownRemark());
            m.put("timeSolt", StringUtils.isEmpty(dto.getStartDay())?"":dto.getStartDay());
            m.put("isVentilator", StringUtils.isEmpty(dto.getIsVentilator())?"":dto.getIsVentilator());
            m.put("fortyUpCode", StringUtils.isEmpty(dto.getFortyUpCode())?"":dto.getFortyUpCode());
            m.put("thirtyFiveDownCode", StringUtils.isEmpty(dto.getThirtyFiveDownCode())?"":dto.getThirtyFiveDownCode());
            analogValueList.add(m);
        }
        return analogValueList;
    }


    /**
     * @Method saveInptNurseThird
     * @Param [inptNurseThirdDTO]
     * @description
     * @author marong
     * @date 2020/10/29 14:09
     * @return java.lang.Boolean
     * @throws
     */
    @Override
    public Boolean saveInptNurseThird(InptNurseThirdDTO inptNurseThirdDTO) {
        List<InptNurseThirdDTO> inptNurseThirds = inptNurseThirdDTO.getInptNurseThirds();
        int delResult = -1;
        int insertResult = -1;
        if (!ListUtils.isEmpty(inptNurseThirds)){
            Map<String,Object> map = new HashMap<>();
            InptNurseThirdDTO dto = inptNurseThirds.get(0);
            dto.setQueryTime(DateUtils.format(dto.getRecordTime(),"yyyy-MM-dd"));
            //先删除当天的三测单
            delResult = inptNurseThirdDao.deleteById(dto);
            //再保存三测单
            insertResult = inptNurseThirdDao.insertList(inptNurseThirds);
        }else{
            return false;
        }
        return delResult >= 0 && insertResult >= 0;
    }

    /**
     * @Method queryOperByVisitId
     * @Param [OperInfoRecordDTO]
     * @description   根据就诊visitId查询已完成手术列表
     * @author luoyong
     * @date 2020/10/29 9:53
     * @return List<OperInfoRecordDTO>
     * @throws
     */
    @Override
    public List<OperInfoRecordDTO> queryOperByVisitId(OperInfoRecordDTO operInfoRecordDTO) {
        if (StringUtils.isEmpty(operInfoRecordDTO.getVisitId())) {
            throw new RuntimeException("未选择就诊对象");
        }
        return inptNurseThirdDao.queryOperByVisitId(operInfoRecordDTO);
    }

    /**
     * @Menthod: queryInptThirdRecordByBatch
     * @Desrciption: 根据三测单节点时间点查询科室下在院病人列表
     * @Param: inptNurseThirdDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-17 16:56
     * @Return: List<InptNurseThirdDTO>
     **/
    @Override
    public List<InptNurseThirdDTO> queryInptThirdRecordByBatch(InptNurseThirdDTO inptNurseThirdDTO) {
        if (StringUtils.isEmpty(inptNurseThirdDTO.getQueryTime())){
            throw new RuntimeException("未选择三测单录入日期");
        }
        if (StringUtils.isEmpty(inptNurseThirdDTO.getSjd())) {
            throw new RuntimeException("未选择录入三测单时间");
        }
        Date date = DateUtil.stringToDate(inptNurseThirdDTO.getQueryTime(), "yyyy-MM-dd");
        String format = DateUtils.format(date, "yyyy-MM-dd");
        Date startDate = DateUtil.stringToDate(format, "yyyy-MM-dd");
        inptNurseThirdDTO.setStartDate(startDate);
        inptNurseThirdDTO.setEndDate(startDate);
        List<InptNurseThirdDTO> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(inptNurseThirdDTO.getIsQueryBaby()) && Constants.SF.S.equals(inptNurseThirdDTO.getIsQueryBaby())) {
            // 批量查询婴儿列表
            list = inptNurseThirdDao.queryInptThirdRecordByBabyBatch(inptNurseThirdDTO);
        } else {
            list = inptNurseThirdDao.queryInptThirdRecordByBatch(inptNurseThirdDTO);
        }
        return list;
    }

    /**
     * @Menthod: saveBatch
     * @Desrciption: 批量保存三测单
     * @Param: inptNurseThirdDTOS
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-04-19 15:59
     * @Return: boolean
     **/
    @Override
    public Boolean saveBatch(List<InptNurseThirdDTO> inptNurseThirdDTOS) {
        List<String> visitIds = inptNurseThirdDTOS.stream().map(InptNurseThirdDO::getBabyId).collect(Collectors.toList());
        // 过滤出婴儿id不为空的babyIds
        List<String> babyIds = inptNurseThirdDTOS.stream().filter(inptNurseThirdDTO -> StringUtils.isNotEmpty(inptNurseThirdDTO.getBabyId())).map(InptNurseThirdDTO::getBabyId).collect(Collectors.toList());
        List<InptNurseThirdDTO> addList = inptNurseThirdDTOS.stream().filter(inptNurseThirdDTO -> StringUtils.isEmpty(inptNurseThirdDTO.getId())).collect(Collectors.toList());
        List<InptNurseThirdDTO> editList = inptNurseThirdDTOS.stream().filter(inptNurseThirdDTO -> StringUtils.isNotEmpty(inptNurseThirdDTO.getId())).collect(Collectors.toList());
        String sjd = inptNurseThirdDTOS.get(0).getSjd(); //录入时间点
        String queryTime = inptNurseThirdDTOS.get(0).getQueryTime(); //录入日期
        String isQueryBaby = inptNurseThirdDTOS.get(0).getIsQueryBaby();//是否录入婴儿

        Date date = DateUtil.stringToDate(queryTime, "yyyy-MM-dd");
        String format = DateUtils.format(date, "yyyy-MM-dd");
        Date startDate = DateUtil.stringToDate(format, "yyyy-MM-dd");

        if (ListUtils.isEmpty(visitIds)) {
            throw new RuntimeException("批量录入三测单失败：录入患者为空");
        }

        //新增
        if (!ListUtils.isEmpty(addList)){
            //根据visitId、queryTime日期、sjd时间点，查询出是否存在对应
            InptNurseThirdDTO nurseThirdDTO = new InptNurseThirdDTO();
            nurseThirdDTO.setStartDate(startDate);
            nurseThirdDTO.setEndDate(startDate);
            nurseThirdDTO.setSjd(sjd);
            nurseThirdDTO.setVisitIds(visitIds);
            if (!ListUtils.isEmpty(babyIds)) {
                nurseThirdDTO.setBabyIds(babyIds);
            }
            nurseThirdDTO.setHospCode(inptNurseThirdDTOS.get(0).getHospCode());
            nurseThirdDTO.setDeptId(inptNurseThirdDTOS.get(0).getDeptId());

            List<InptNurseThirdDTO> resultList = new ArrayList<>();
            if (StringUtils.isNotEmpty(isQueryBaby) && Constants.SF.S.equals(isQueryBaby)) {
                resultList = inptNurseThirdDao.queryInptThirdRecordByBabyBatch(nurseThirdDTO);
            } else {
                resultList = inptNurseThirdDao.queryInptThirdRecordByBatch(nurseThirdDTO);
            }

            if (!ListUtils.isEmpty(resultList)){
                for (InptNurseThirdDTO dto : resultList) {
                    if (StringUtils.isNotEmpty(dto.getVisitId()) && visitIds.indexOf(dto.getVisitId()) != -1){
                        String msg = "患者【" + dto.getName() + "】在" + queryTime + "  " + sjd + "已存在三测单记录";
                        throw new RuntimeException(msg);
                    }
                }
            }

            for (InptNurseThirdDTO inptNurseThirdDTO : addList) {
                inptNurseThirdDTO.setId(SnowflakeUtils.getId());
                inptNurseThirdDTO.setCrteTime(DateUtils.getNow());
            }

            inptNurseThirdDao.insertList(addList);
        }

        //编辑
        if (!ListUtils.isEmpty(editList)){
            inptNurseThirdDao.updateList(editList);
        }

        return true;
    }

    /**
     * @Menthod: queryAllByVisitId
     * @Desrciption: 根据就诊id查询出患者在院期间所有护理三测单记录
     * @Param: visitId
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-22 10:02
     * @Return:
     **/
    @Override
    public List<InptNurseThirdDTO> queryAllByVisitId(Map<String, Object> map) {
        String visitId = MapUtils.get(map, "visitId");
        String hospCode = MapUtils.get(map, "hospCode");
        if (StringUtils.isEmpty(visitId)) {
            throw new RuntimeException("未传入需要查询的人员信息");
        }
        // 查询护理三测单数据
        List<InptNurseThirdDTO> inptNurseThirdDTOS = inptNurseThirdDao.queryAllByVisitId(map);

        InptNurseThirdDTO nurseThirdDTO = new InptNurseThirdDTO();
        nurseThirdDTO.setHospCode(hospCode);
        nurseThirdDTO.setVisitId(visitId);
        nurseThirdDTO.setIsRecentOper(Constants.SF.S);
        // 查询患者手术记录日期list
        List<String> operDateList = inptNurseThirdDao.getRecordTime(nurseThirdDTO);

        if (!ListUtils.isEmpty(inptNurseThirdDTOS)) {
            for (InptNurseThirdDTO inptNurseThirdDTO : inptNurseThirdDTOS) {
                // 计算住院天数
                Date inTime = inptNurseThirdDTO.getInTimeBatch();
                Date outTime = inptNurseThirdDTO.getOutTime() == null ? DateUtils.getNow() : inptNurseThirdDTO.getOutTime();
                int inDays = DateUtils.differentDays(inTime, outTime);
                if (inDays == 0) {
                    inDays = 1;
                }
                inptNurseThirdDTO.setInDays(inDays);

                // 计算手术天数
                if (!ListUtils.isEmpty(operDateList)) {
                    Date operDate = DateUtils.parse(operDateList.get(0), DateUtils.Y_M_D); // 最近手术日期
                    Date recordDate = inptNurseThirdDTO.getRecordDate(); // 三测单记录日期，yyyy-MM-dd
                    if (recordDate.before(operDate)) {
                        // 记录日期早于手术日期
                        inptNurseThirdDTO.setOperationDays(0);
                    } else {
                        int days = DateUtils.differentDays(operDate, recordDate);
                        if (days == 0) {
                            days = 1; //记录日期与手术日期当天，设置为1
                        }
                        inptNurseThirdDTO.setOperationDays(days);
                    }
                }
            }
        }
        return inptNurseThirdDTOS;
    }

    private String[] getDayOps(InptNurseThirdDTO inptNurseThirdDTO,List<InptNurseThirdDTO> inptNurseThirdDTOS,List<String> recordTimeList) {
        // 出院时间，在院情况出院时间未当前时间
        Date outTime = DateUtils.dateToDate(inptNurseThirdDTO.getOutTime());
        String dayOps [] = new String [7] ;
        for (int i = 0; i <7 ; i++) {
            InptNurseThirdDTO nurseThirdDTO = inptNurseThirdDTOS.get(i * 6);
            String timeSlot = DateUtils.format(nurseThirdDTO.getTimeSlot(),"yyyy-MM-dd");
            Date timeSlotDate = DateUtils.parse(timeSlot,"yyyy-MM-dd");
            if(ListUtils.isEmpty(recordTimeList)){
                dayOps[i] = "";
            }else if ((timeSlotDate.getTime() - outTime.getTime()) > 0){
                // 记录时间大于出院时间默认为空
                dayOps[i] = "";
            }else{
                //查找出小于护理记录时间的手术时间
                List<String> minList = new ArrayList<>();
                for (String date : recordTimeList){
                    int result = DateUtils.dateCompare(date,timeSlot,"yyyy-MM-dd");
                    if(result<0){
                        minList.add(date);
                    }
                }
                //没有找到小于护理记录时间的手术时间，术后天数为空
                if(ListUtils.isEmpty(minList)){
                    dayOps[i] = "";
                }else{
                    //如果小于护理记录时间的手术时间的size大于2时，取最后2条去计算术后天数
                    List<String> inTimeList = new ArrayList<>();
                    if(minList.size()>2){
                        inTimeList.add(minList.get(minList.size()-2));
                        inTimeList.add(minList.get(minList.size()-1));
                    }else{
                        inTimeList.addAll(minList);
                    }
                    String shts = "";
                    for (int j=0;j<inTimeList.size();j++){
                        Date inDate = DateUtils.parse(inTimeList.get(j),"yyyy-MM-dd");
                        int diff = DateUtil.daysBetweenDates(timeSlotDate,inDate);
                        // 术后天数只记录到术后第七天，之后不在记录
                        if (diff>0 && diff<8){
                            shts=shts+diff+",";
                        }
                    }
                    String[] array = shts.split(",");
                    if(array.length>1){
                        dayOps[i] = array[1]+"/"+array[0];
                    }else if(array.length>0){
                        dayOps[i] = array[0];
                    }

                }

            }
        }
        return dayOps;
    }


    private List<InptNurseThirdDTO> getInptNurseThirdDTOS(InptNurseThirdDTO inptNurseThirdDTO) {
        //开始时间
        Date startDate =  DateUtils.dateAdd(inptNurseThirdDTO.getInTime(),(inptNurseThirdDTO.getPage()-1)*7);
        //结束时间
        Date endDate = DateUtils.dateAdd(inptNurseThirdDTO.getInTime(),inptNurseThirdDTO.getPage()*7-1);

       /* int num = DateUtil.daysBetweenDates(DateUtil.getDate(),startDate);
        if(num < 7){
            endDate=DateUtil.getDate();
        }*/

        inptNurseThirdDTO.setStartDate(DateUtil.stringToDate(DateUtil.getDate(startDate,"yyyy-MM-dd")));
        inptNurseThirdDTO.setEndDate(DateUtil.stringToDate(DateUtil.getDate(endDate,"yyyy-MM-dd")));
        //查询
        List<InptNurseThirdDTO> inptNurseThirdDTOS = inptNurseThirdDao.queryAllByTimeSlot(inptNurseThirdDTO);
        return inptNurseThirdDTOS;
    }


    private static int longOfTwoDate(Date first, Date second)  {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(first);
        int cnt = 0;
        while (calendar.getTime().compareTo(second) < 0) {
            calendar.add(Calendar.DATE, 1);
            cnt++;
        }
        return cnt;
    }

    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }


}
