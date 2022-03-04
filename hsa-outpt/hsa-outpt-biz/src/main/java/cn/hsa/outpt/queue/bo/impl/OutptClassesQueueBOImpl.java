package cn.hsa.outpt.queue.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.classes.dto.OutptClassesDTO;
import cn.hsa.module.outpt.classes.service.OutptClassesService;
import cn.hsa.module.outpt.outptclassifyclasses.dao.OutptClassifyClassesDAO;
import cn.hsa.module.outpt.outptclassifyclasses.dto.OutptClassifyClassesDTO;
import cn.hsa.module.outpt.prescribe.dao.OutptDoctorPrescribeDAO;
import cn.hsa.module.outpt.queue.bo.OutptClassesQueueBO;
import cn.hsa.module.outpt.queue.dao.OutptClassesQueueDao;
import cn.hsa.module.outpt.queue.dao.OutptDoctorQueueDao;
import cn.hsa.module.outpt.queue.dto.OutptClassesQueueDto;
import cn.hsa.module.outpt.queue.dto.OutptDoctorQueueDto;
import cn.hsa.module.outpt.register.dao.OutptDoctorRegisterDao;
import cn.hsa.module.outpt.register.dao.OutptRegisterDAO;
import cn.hsa.module.outpt.register.dto.OutptDoctorRegisterDto;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.outpt.queue.bo.impl
 * @Class_name: OutptClassesQueueBOImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 15:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptClassesQueueBOImpl implements OutptClassesQueueBO {

    @Resource
    private OutptClassesQueueDao outptClassesQueueDao;
    @Resource
    private OutptDoctorQueueDao outptDoctorQueueDao;
    @Resource
    private OutptDoctorRegisterDao outptDoctorRegisterDao;
    @Resource
    private OutptClassesService outptClassesService;
    @Resource
    private OutptRegisterDAO outptRegisterDAO;

    /**
     * 处方管理数据库访问接口
     */
    @Resource
    private OutptDoctorPrescribeDAO outptDoctorPrescribeDAO;

    @Override
    public PageDTO queryPage(OutptClassesQueueDto outptClassesQueueDto) {
        // 设置分页信息
        PageHelper.startPage(outptClassesQueueDto.getPageNo(), outptClassesQueueDto.getPageSize());
        // 根据条件查询所
        List<OutptClassesQueueDto> outptClassesDTOS = outptClassesQueueDao.queryPage(outptClassesQueueDto);
        return PageDTO.of(outptClassesDTOS);
    }

    @Override
    public List<Map> queryClassesDoctor(OutptClassifyClassesDTO outptClassifyClassesDTO) {
        return outptClassesQueueDao.queryClassesDoctor(outptClassifyClassesDTO);
    }

    private void checkBcdl(Map tem){
        String id =  MapUtils.get(tem, "id");
        tem.put("id","");
        //查询班次队友表outpt_classes_queue
        List<OutptClassesQueueDto> list = outptClassesQueueDao.queryClassesQueueByParam(tem);
        if(!ListUtils.isEmpty(list)){
            list.forEach(dto -> {
                if(StringUtils.isEmpty(id)){
                    throw new AppException("已存在相同的班次队列不能再添加！");
                }else if(!dto.getId().equals(id)){
                    throw new AppException("已存在相同的班次队列不能再添加！");
                }
            });
        }
    }



    @Override
    public boolean saveClassesQueue(OutptClassesQueueDto outptClassesQueueDto) {
        //验证是否已存在
        Map tem = new HashMap();
        tem.put("hospCode", outptClassesQueueDto.getHospCode());
        tem.put("cyId", outptClassesQueueDto.getCyId());
        tem.put("csId", outptClassesQueueDto.getCsId());
        tem.put("deptId", outptClassesQueueDto.getDeptId());
        tem.put("queueDate", outptClassesQueueDto.getQueueDate());

        Date dlrqDate = DateUtils.parse(DateUtils.format(outptClassesQueueDto.getQueueDate(), "yyyy-MM-dd"), "yyyy-MM-dd");
        Date now = DateUtils.parse(DateUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
        if (dlrqDate.getTime() < now.getTime()) {
            throw new AppException("班次队列日期不能小于当前日期！");
        }
//        Map parmMap = new HashMap();
//        parmMap.put("hospCode", outptClassesQueueDto.getHospCode());
//        parmMap.put("cyId", outptClassesQueueDto.getCyId());
//        parmMap.put("csId", outptClassesQueueDto.getCsId());
//        parmMap.put("weeks", DateUtils.getWeeksDayOfName());
//        //校验排班类别是否存在，如果不校验排班类别 outpt_classes_queue -> cc_id会为空。
//        Map<String,Object> classesQueueMap =  outptClassesQueueDao.queryClassifyClassesByCyAndCs(tem);
//        if (classesQueueMap == null ) {
//            throw new AppException("请在挂号排班类别里面提前配置对应班次!");
//        }
//
//        outptClassesQueueDto.setCcId((String)classesQueueMap.get("id"));

        List<OutptDoctorQueueDto> doctorList = outptClassesQueueDto.getDoctorQueueList();
        if(StringUtils.isEmpty(outptClassesQueueDto.getId())){ //新增
            checkBcdl(tem);
            outptClassesQueueDto.setId(SnowflakeUtils.getId());
            Map map = new HashMap(7);
            map.put("hospCode",outptClassesQueueDto.getHospCode());
            map.put("cyId",outptClassesQueueDto.getCyId());
            map.put("csId",outptClassesQueueDto.getCsId());
            map.put("weeks",DateUtils.getWeeksDayOfName());
            map = outptClassesQueueDao.queryClassifyClasses(map);
            outptClassesQueueDto.setCcId(map == null? null:MapUtils.get(map,"id"));
            outptClassesQueueDao.insertClassesQueue(outptClassesQueueDto);

            buildDocList(outptClassesQueueDto, doctorList);
        }else{ //修改
            Map mapParam = new HashMap();
            mapParam.put("hospCode", outptClassesQueueDto.getHospCode());
            mapParam.put("cqId", outptClassesQueueDto.getId());
            List<OutptRegisterDTO> outptRegisterList = outptRegisterDAO.queryAll(mapParam);
            if(!ListUtils.isEmpty(outptRegisterList)){
                throw new AppException("该坐诊队列已经有挂号记录，不可修改");
            }

            buildDocList(outptClassesQueueDto, doctorList);
            //修改班次队列
            outptClassesQueueDao.updateClassesQueue(outptClassesQueueDto);
            OutptDoctorQueueDto outptDoctorQueueDto = new OutptDoctorQueueDto();
            outptDoctorQueueDto.setHospCode(outptClassesQueueDto.getHospCode());
            outptDoctorQueueDto.setCqId(outptClassesQueueDto.getId());
            List<OutptDoctorQueueDto> doctorQueueListOld = outptDoctorQueueDao.queryAll(outptDoctorQueueDto);
            if(!ListUtils.isEmpty(doctorQueueListOld)){
                Map docMapParams = new HashMap();
                docMapParams.put("hospCode", outptClassesQueueDto.getHospCode());
                List<String> notDocDelIds =  outptDoctorQueueDao.queryDoctorQueueNotDel(docMapParams);
                //比较 notDocDelIds 和 doctorQueueListOld 看doctorQueueListOld是否有不能删除得医生队列数据
                if(!ListUtils.isEmpty(notDocDelIds)){
                    notDocDelIds.forEach(docId -> {
                        doctorQueueListOld.forEach(dto -> {
                            if(docId.equals(dto.getId())){
                                throw new AppException("有已经在用的医生队列数据，无法修改");
                            }
                        });
                    });
                }
                //删除医生原来的医生队列数据
                List<String> docQueueIds = doctorQueueListOld.stream().map(OutptDoctorQueueDto::getId).collect(Collectors.toList());
                outptDoctorQueueDao.deleteDoctorQueue(docQueueIds);
                outptDoctorQueueDao.deleteDoctorRegister(docQueueIds);
            }
        }
        //插入医生队列
        if(!ListUtils.isEmpty(doctorList)){
            outptClassesQueueDao.insertDoctorQueue(doctorList);
            //插入号源明细
            try {
                addYsHy(doctorList, outptClassesQueueDto);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void buildDocList(OutptClassesQueueDto outptClassesQueueDto, List<OutptDoctorQueueDto> doctorList) {
        if (!ListUtils.isEmpty(doctorList)) {
            List<String> doctorIdList = new ArrayList<>(15);
            doctorList.forEach(dto ->{
                doctorIdList.add(dto.getDoctorId());
            });
            Map<String,Object> param = new HashMap<>(8);
            param.put("hospCode",outptClassesQueueDto.getHospCode());
            param.put("queueDate",outptClassesQueueDto.getQueueDateTem());
            param.put("classId",outptClassesQueueDto.getCsId());
            param.put("doctorIdList",doctorIdList);
            param.put("id",outptClassesQueueDto.getId());
            // 校验医生当天是否存在队列之中
            List<Map<String,Object>> doctorQueueExistList = outptClassesQueueDao.queryDoctorQueueOfTodayByClassifyIdDoctorId(param);
            if(!ListUtils.isEmpty(doctorQueueExistList)) {
                throw new AppException("医生【"+doctorQueueExistList.get(0).get("doctorName")+"】"
                        +doctorQueueExistList.get(0).get("queueDate")+"日,已在挂号类别【"
                        +doctorQueueExistList.get(0).get("classOfShift")
                        +"】存在【"+doctorQueueExistList.get(0).get("className")+"】队列数据") ;
            }
            //处理医生队列数据
            doctorList.forEach(dto -> {
                //校验参数
//                checkData(dto);
                dto.setId(SnowflakeUtils.getId());
                dto.setHospCode(outptClassesQueueDto.getHospCode());
                dto.setCqId(outptClassesQueueDto.getId());
                dto.setIsValid("1");
            });
        }
    }

    /**
     * 保存队列
     * @param outptClassesQueueDto
     * @return
     */
    @Override
    public boolean saveProduceQueue(OutptClassesQueueDto outptClassesQueueDto) {
        //参数输入
        Map temmap = new HashMap();
        temmap.put("hospCode",outptClassesQueueDto.getHospCode());
        temmap.put("queueStartDate",outptClassesQueueDto.getQueueStartDate());
        temmap.put("queueEndDate",outptClassesQueueDto.getQueueEndDate());
        temmap.put("crteId",outptClassesQueueDto.getCrteId());
        temmap.put("crteName",outptClassesQueueDto.getCrteName());

        //获取系统参数
        Map<String, String> mapParameter = this.getParameterValue(outptClassesQueueDto.getHospCode() , new String[]{"SCDL_TS"});
        int ts = Integer.valueOf(MapUtils.getVS(mapParameter, "SCDL_TS", "7"));

        if(!DateUtils.dateCompareDay(outptClassesQueueDto.getQueueStartDate(), outptClassesQueueDto.getQueueEndDate(), ts)){
            throw new AppException("最多只能生成"+ts+"天数据");
        }

        //查询操作科室性质
//        HisKsxx ksxx = hisKsxxService.findKsxxByid(users.getCurrentLoginDeptid());
//        if(ksxx !=null && "ZM25".equals(ksxx.getKsxz())){//科室性质为分诊台
//            temmap.put("fztid",ksxx.getUuid());
//        }
        //生成队列
        addQueue(temmap);
        return true;
    }

    /**
     * 获取系统参数
     * @param hospCode
     * @param code
     * @return
     */
    public Map<String, String> getParameterValue(String hospCode, String[] code) {
        List<SysParameterDTO> list = outptDoctorPrescribeDAO.getParameterValue(hospCode, code);
        Map<String, String> retMap = new HashMap<>();
        if (!MapUtils.isEmpty(list)) {
            for (SysParameterDTO hit : list) {
                retMap.put(hit.getCode(), hit.getValue());
            }
        }
        return retMap;
    }

    @Override
    public boolean deleteQueue(OutptClassesQueueDto outptClassesQueueDto) {
        Map map = new HashMap();
        map.put("ids", outptClassesQueueDto.getIds());
        map.put("hospCode", outptClassesQueueDto.getHospCode());
        List<OutptClassesQueueDto> list = outptClassesQueueDao.queryClassesQueueParam(map);
        if(ListUtils.isEmpty(list)){
            throw new AppException("没有查询到对应坐诊队列，无法删除");
        }
        list.forEach(dto -> {
            Date queueDate = dto.getQueueDate();
            int l = DateUtils.dateCompare(DateUtils.format(queueDate,"yyyy-MM-dd")
                    ,DateUtils.format(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd");
            if(l<0){
                throw new AppException("不能删除今天以前的坐诊队列数据！");
            }
        });
        List<Map> outptRegisterList = outptClassesQueueDao.queryRegisterByCqId(outptClassesQueueDto);
        if(!ListUtils.isEmpty(outptRegisterList)){
            throw new AppException("该坐诊队列已经被挂号，不能删除！");
        }
        Map classQueueMap = new HashMap();
        classQueueMap.put("hospCode", outptClassesQueueDto.getHospCode());
        classQueueMap.put("ids", outptClassesQueueDto.getIds());
        outptClassesQueueDao.deleteClassesQueueByIds(classQueueMap);
        outptDoctorQueueDao.deleteDoctorQueueByCqids(outptClassesQueueDto.getIds());
        return true;
    }

    private void addQueue(Map paramMap){
        try{
            String crteName = MapUtils.get(paramMap,"crteName");//操作人
            String crteId = MapUtils.get(paramMap,"crteId");//操作人ID
            String hospCode = MapUtils.get(paramMap,"hospCode");//医院编码
            /*
             * 查询出要删除的医生队列数据
             * 1、业务日期内
             * 2、 配置的 或者 手动且无效的
             * 3、不存在分诊就诊表
             * 4、不存在号源明细表里
             */
            List<Map> delList = outptDoctorQueueDao.queryDoctorQueueDel(paramMap);
            /*
             * 查询出不能删除的医生队列数据
             * 1、在挂号表里已存在的
             * 2、在号源明细表里存在
             */
            List<String> notDelList = outptDoctorQueueDao.queryDoctorQueueNotDel(paramMap);

            //遍历俩个集合查找出真正要删除的医生队列数据
            List<String> realDelList = new ArrayList<>();
            delList.forEach(map -> {
                //医生队列ID
                String id = MapUtils.get(map,"id");
                if(!notDelList.contains(id)){//不包含就得删除
                    realDelList.add(id);
                }
            });

            //删除医生队列表数据和删除医生号源明细表数据
            if(!ListUtils.isEmpty(realDelList)){
                //删除医生队列表
                outptDoctorQueueDao.deleteDoctorQueue(realDelList) ;
                //删除号源明细表
                outptDoctorQueueDao.deleteDoctorRegister(realDelList);
            }

            /*
             * 删除班次队列数据
             * 1、业务日期内
             * 2、配置的 或者 手动且无效的
             * 3、不存在医生队列
             * 4、不存在挂号表里
             */
            outptClassesQueueDao.deleteClassesQueue(paramMap);


            //计算出队列起止日期之间星期
            List<Date> dateList = getBetweenDates(toDate(paramMap,"queueStartDate",false)
                    ,toDate(paramMap,"queueEndDate",false));

            List<String> xqList = new ArrayList<>();

            if(!ListUtils.isEmpty(dateList)){
                dateList.forEach(date -> {
                    String dates = DateUtils.format(date,DateUtils.Y_M_D);
                    String xq = DateUtils.dateToWeek(dates);
                    if(!xqList.contains(xq)){
                        xqList.add(xq);
                    }
                });
                paramMap.put("xq",xqList);
            }else{
                throw new AppException("请先选择起止日期！");
            }


            /**
             * 查询所有满足条件的排班班次数据
             *  1、业务日期内
             *  2、星期在队列起止日期所包含的星期集合（list<string>）
             */
            List<Map> listBcData = outptClassesQueueDao.queryClassifyClassesData(paramMap);
            Map<String, List<Map>> mapBcData =  groupList(listBcData,new String[]{"weeks"});

            Map queryMap = new HashMap();
            OutptClassesDTO outptClassesDTOQuery = new OutptClassesDTO();
            outptClassesDTOQuery.setHospCode(hospCode);
            queryMap.put("hospCode", hospCode);
            queryMap.put("outptClassesDTO", outptClassesDTOQuery);
            WrapperResponse<List<OutptClassesDTO>> response = outptClassesService.queryAll(queryMap);
            List<OutptClassesDTO> classList = response.getData();
            Map<String, OutptClassesDTO> classMap = classList.stream().collect(Collectors.toMap(OutptClassesDTO::getId, dto -> dto));

            /**
             * 查询所有满足条件的排班班次医生数据
             *  1、业务日期内
             */
            List<Map> listBcYsData = outptClassesQueueDao.queryClassesDoctorData(paramMap);
            //将排班班次医生数据按照班次分组
            Map<String,List<Map>> mapBcYsData = groupList(listBcYsData,new String[]{"cc_id"});

            /*
             * 查询所有的排班班次时间
             * 1、yybm
             */
//            List<Map> listBcSjData = hisBcdlMapper.queryHisBc02Data(paramMap);


            /**
             * 查询所有满足条件的班次队列数据
             *  1、业务日期内
             *  2、有效的
             */
            Map tem = new HashMap();
            tem.put("queueStartDate",MapUtils.get(paramMap,"queueStartDate"));
            tem.put("queueEndDate",MapUtils.get(paramMap,"queueEndDate"));
            tem.put("hospCode",MapUtils.get(paramMap,"hospCode"));
            tem.put("isValid","1");
            List<OutptClassesQueueDto> hisBcdlList = outptClassesQueueDao.queryClassesQueueParam(tem);
            //将班次队列数据按照 dlrq,bclbid,ghpzid,ksid分组
            Map<String,List<OutptClassesQueueDto>> hisBcdlMap = hisBcdlList.stream().collect(
                    Collectors.groupingBy(hisBcdl -> DateUtils.format(hisBcdl.getQueueDate(),DateUtils.Y_M_D)
                            +"|"+hisBcdl.getCsId()+"|"+hisBcdl.getCyId()+"|"+hisBcdl.getDeptId()
                    ));


            /**
             * 查询所有满足条件的医生队列数据
             * 1、业务日期内
             * 2、有效的
             */
            List<Map> hisYsdlList =outptDoctorQueueDao.queryDoctorQueueByParam(tem);
            //将医生队列数据按照bclbid，ysid分组
            Map<String,List<Map>> hisYsdlMap = groupList(hisYsdlList,new String[]{"cq_id","doctor_id"});


            //存放班次队列数据集合
            List<Map> bcdlList = new ArrayList<>();
            //存放医生队列数据集合
            List<Map> ysdlList = new ArrayList<>();
            //遍历时间，封装好班次队列数据和医生队列数据
            if(!ListUtils.isEmpty(dateList)){

                dateList.forEach(date -> {
                    String dates = DateUtils.format(date,DateUtils.Y_M_D);
                    String xq = DateUtils.dateToWeek(dates);
                    //遍历排班班次信息
                    List<Map> listBcData1 = (List<Map>) MapUtils.get(mapBcData,xq);
                    //遍历list1 取出dates在ksrq和jsrq之间
                    if(!ListUtils.isEmpty(listBcData1)) {
                        listBcData1.forEach(map -> {

                            String bcdlid = null; //存放班次队列ID，生成医生队列时需要班次队列ID
                            String bcid = MapUtils.get(map,"id");//排班班次ID

                            /*------------------------组装要生成的班次队列----start------------------*/
                            /*
                             * 先生成班次信息
                             * 判断dlrq,bclbid,ghpzid,ksid是否已存在班次队列
                             */
                            String  bclbid = MapUtils.get(map,"cs_id");//班次类别ID
                            String  ghpzid = MapUtils.get(map,"cy_id");//挂号配置ID
                            String  ksid =  MapUtils.get(map,"dept_id");//科室ID
                            String ksmc =   MapUtils.get(map,"dept_name");

                            String key = dates+"|"+bclbid+"|"+ghpzid+"|"+ksid;
                            List<OutptClassesQueueDto> temList = (List<OutptClassesQueueDto>) MapUtils.get(hisBcdlMap,key);
                            if(!ListUtils.isEmpty(temList)){
                                //已经存在班次队列就不需要新增
                                bcdlid= temList.get(0).getId();

                            }else{
                                //需要新增班次队列
                                bcdlid = SnowflakeUtils.getId();
                                Map m = new HashMap();
                                m = creatBcDlMap(bcdlid,hospCode,dates,map);
                                m.put("crte_id",crteId);
                                m.put("crte_name",crteName);
                                bcdlList.add(m);
                                //组装坐诊对列对应班次时间map
                            }
                            /*------------------------组装要生成的班次队列----end-------------------------*/


                            /*------------------------组装要生成的医生队列----start------------------------*/
                            /**
                             *从所有的排班医生数据找出排班班次配置的医生数据
                             */
                            List<Map> bcYsList = (List<Map>) MapUtils.get(mapBcYsData,bcid);
                            if(!ListUtils.isEmpty(bcYsList)){
                                for (Map map1:bcYsList){
                                    String ysid = MapUtils.get(map1,"doctor_id");
                                    String key1 = bcdlid +"|"+ysid;
                                    List<Map> temList1 = (List<Map>) MapUtils.get(hisYsdlMap,key1);

                                    if(ListUtils.isEmpty(temList1)){
                                        //只有医生队列下没有该班次队列和医生id，才能新增
                                        Map m = creatYsDlMap(bcdlid,hospCode,dates,map1);
                                        ysdlList.add(m);
                                    }
                                }
                            }

                            /*------------------------组装要生成的医生队列----end---------------------------*/

                        });
                    }
                });
            }

            List<OutptClassesDTO> bcdlBcsjList = new ArrayList<>();
            bcdlList.forEach(dto -> {
                OutptClassesDTO outptClassesDTO = classMap.get(dto.get("cs_id"));
                OutptClassesDTO outptClassesDTONew = new OutptClassesDTO();
                outptClassesDTONew.setDlId((String) dto.get("id"));
                outptClassesDTONew.setStartDate(outptClassesDTO.getStartDate());
                outptClassesDTONew.setEndDate(outptClassesDTO.getEndDate());
                bcdlBcsjList.add(outptClassesDTONew);
            });
            Map<String, OutptClassesDTO> dlBcMap = bcdlBcsjList.stream().collect(Collectors.toMap(OutptClassesDTO::getDlId, dto -> dto));

            //插入班次队列数据
            if(!ListUtils.isEmpty(bcdlList)){
                outptClassesQueueDao.insertClassesQueueBatch(bcdlList);
            }
            //插入医生队列数据和号源明细
            if(!ListUtils.isEmpty(ysdlList)){
                outptDoctorQueueDao.insertDoctorQueueBatch(ysdlList);
                //根据医生队列数据处理号源明细
                OutptClassesQueueDto outptClassesQueueDto = new OutptClassesQueueDto();
                outptClassesQueueDto.setCrteId(crteId);
                outptClassesQueueDto.setCrteName(crteName);
                outptClassesQueueDto.setHospCode(hospCode);
                List<OutptDoctorQueueDto> dataList = new ArrayList<>();
                ysdlList.forEach(map -> {
                    OutptDoctorQueueDto dto = new OutptDoctorQueueDto();
                    dto.setId((String) map.get("id"));
                    dto.setHospCode((String) map.get("hospCode"));
                    dto.setCqId((String) map.get("cq_id"));
                    dto.setDoctorId((String) map.get("doctor_id"));
                    dto.setDoctorName((String) map.get("doctor_name"));
                    dto.setIsValid((String) map.get("is_valid"));
                    dto.setRegisterNum(Integer.parseInt((String)map.get("register_num")));
                    String cq_id = (String) map.get("cq_id");
                    dto.setClassStartDate(dlBcMap.getOrDefault(cq_id, new OutptClassesDTO()).getStartDate());
                    dto.setClassEndDate(dlBcMap.getOrDefault(cq_id, new OutptClassesDTO()).getEndDate());
                    dataList.add(dto);
                });
                //获取系统参数
                Map<String, String> mapParameter = this.getParameterValue(outptClassesQueueDto.getHospCode() , new String[]{"GH_FSD_SF"});
                String sfkq = MapUtils.getVS(mapParameter, "GH_FSD_SF", "0");
                //开启
                if(StringUtils.isNotEmpty(sfkq) && "1".equals(sfkq)){
                    addYsHy(dataList,outptClassesQueueDto);
                }
            }

        }catch (Exception e){
            throw new AppException("生成坐诊队列失败！",e);
        }
    };

    private Map creatBcDlMap(String bcdlid,String hospCode,String dates,Map map){
        Map m = new HashMap();
        String register_num =  String.valueOf(map.get("register_num"));
        if(StringUtils.isEmpty(register_num)){
            register_num = "0";
        }
        m.put("id",bcdlid);
        m.put("hospCode",hospCode);
        m.put("queue_date",dates);
        m.put("cs_id",MapUtils.get(map,"cs_id"));
        m.put("cc_id",MapUtils.get(map,"id"));
        m.put("cy_id",MapUtils.get(map,"cy_id"));
        m.put("triage_id",MapUtils.get(map,"triage_id"));
        m.put("triage_code",MapUtils.get(map,"triage_code"));
        m.put("register_num", register_num);
        m.put("gen_code","1");
        m.put("dept_id",MapUtils.get(map, "dept_id"));
        m.put("is_valid","1");
        m.put("crte_time",new Date());
        return  m;
    }

    /**
     *封装医生队列数据
     */
    private Map creatYsDlMap(String bcdlid,String hospCode,String dates,Map map){
        Map m = new HashMap();
        String register_num =  String.valueOf(map.get("register_num"));
        if(StringUtils.isEmpty(register_num)){
            register_num = "0";
        }
        m.put("id", SnowflakeUtils.getId());
        m.put("hospCode",hospCode);
        m.put("cq_id",bcdlid);
        m.put("doctor_id",MapUtils.get(map,"doctor_id"));
        m.put("doctor_name",MapUtils.get(map,"doctor_name"));
        m.put("is_valid","1");
        m.put("register_num",register_num);
        m.put("clinic_id",MapUtils.get(map,"clinic_id"));
        return  m;
    }

    private Map<String,List<Map>> groupList(List<Map> list,String[] keys){
        Map<String, List<Map>> groupMap = new HashMap<>();
        if(keys == null ||keys.length == 0){
            return null;
        }
        Map keyMap = new HashMap();
        for (Map map : list){
            String key = "";
            for (int i =0;i<keys.length;i++){
                key+= MapUtils.get(map,keys[i],"")+"|";
            }
            key = key.substring(0,key.length()-1);
            if(keyMap.containsKey(key)){
                groupMap.get(key).add(map);
            }else{
                List<Map> tem = new ArrayList<>();
                tem.add(map);
                groupMap.put(key,tem);
            }
            keyMap.put(key,"");
        }
        return groupMap;
    }

    private List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        //添加或减去指定的时间给定日历领域，基于日历的规则。例如，从日历当前的时间减去5天，您就可以通过
        tempStart.add(Calendar.DAY_OF_YEAR, 0);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.add(Calendar.DAY_OF_YEAR, 1);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    private Date toDate(Map map, String key, Boolean isDefault) {
        return map.get(key) == null || org.apache.commons.lang3.StringUtils.isBlank(map.get(key).toString()) ? (isDefault ? new Date() : null)
                : toDate(map.get(key).toString());
    }

    private Date toDate(String strDate) {
        try {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            return sdfDate.parse(strDate);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return null;
    }

    private void addYsHy(List<OutptDoctorQueueDto> dataList, OutptClassesQueueDto outptClassesQueueDto) throws ParseException {

        //号源明细集合
        List<OutptDoctorRegisterDto> yshymxList = new ArrayList<>();
        OutptDoctorRegisterDto outptDoctorRegisterDto = null;
        //获取医院的中午午休时间，去掉中午午休时间
//        Map map1 = new HashMap();
//        map1.put("yybm",outptClassesQueueDto.getHospCode());
//        Map retMap = hisHosorgService.queryHisHosorgByParam(map1);
//        String zwkssj = UtilFunc.getString(retMap,"wxkssj");
//        String zwjssj = UtilFunc.getString(retMap,"wxjssj");
//        if(UtilFunc.isEmpty(zwkssj)){
//            zwkssj = ConstantUtil.HOSP_WXKSSJ_DEFAULT;
//        }
//        if(UtilFunc.isEmpty(zwjssj)){
//            zwjssj = ConstantUtil.HOSP_WXJSSJ_DEFAULT;
//        }
        String zwkssj = "12:00:00";
        String zwjssj = "14:00:00";
        for (OutptDoctorQueueDto outptDoctorQueueDto : dataList){
            /*
             *遍历医生队列数据，取出上班开始时间和上班结束时间，
             * 计算每个医生拥有的时间段（30分钟为一个）
             */
            //上班开始时间
            String sbkssj = outptDoctorQueueDto.getClassStartDate();
            //上班结束时间
            String sbjssj = outptDoctorQueueDto.getClassEndDate();
            if(StringUtils.isEmpty(sbkssj) || StringUtils.isEmpty(sbjssj)){
                continue;
            }
            if(sbkssj.length()==5){
                sbkssj = sbkssj+":00";
            }
            if(sbjssj.length()==5){
                sbjssj = sbjssj+":00";
            }
            //分每30分钟为一段
            long calc = getTime(sbkssj,sbjssj,30);

            long step = 0;
            if((dateCompare(sbkssj,zwjssj,DateUtils.H_M_S)==-1)&&(dateCompare(sbjssj,zwkssj,DateUtils.H_M_S)==1)){
                //上班开始时间小于医院规定午休开始时间
                // yzb  注释掉，此处写死4次会导致不管坐诊是时间的长短，都会只按4个时间端进行拆分号源、
                //step = 4;
                step = getTime(zwkssj,zwjssj,30);
                calc = calc -step;
            }

//

            //时间段无法添加号源
            if(calc<=0){
                break;
            }

            //获取号源总数量
            int hyzsl = 0;
            int mzxhsl = outptDoctorQueueDto.getRegisterNum();
//            int yyxhsl = UtilFunc.stringToInt(UtilFunc.getString(map,"yyxhsl"));
            //号源总数量等于门诊限号数量
            hyzsl = mzxhsl;

            //计算每个时段的平摊的号源
            Long[] ptzsl = new Long[Integer.parseInt(String.valueOf(calc))];//总数量
//            Long[] ptyyzsl = new Long[Integer.parseInt(String.valueOf(calc))];//预约数量
            Long[] ptdtzsl = new Long[Integer.parseInt(String.valueOf(calc))];//当天数量
            for (int i = 0;i<calc;i++){
                ptzsl[i] = hyzsl/calc;
//                ptyyzsl[i] = yyxhsl/calc;
            }
            //号源总数量，除不尽就将剩下的平摊到数组里
            int j = (int) (hyzsl%calc);
            if(j>0){
                for (int k=0;k<j;k++){
                    ptzsl[k] = ptzsl[k]+1;
                }
            }
            //号源预约数量，除不尽就将剩下的平摊到数组里
//            int a = (int) (yyxhsl%calc);
//            if(a>0){
//                for (int k=0;k<a;k++){
//                    ptyyzsl[k] = ptyyzsl[k]+1;
//                }
//            }
            //计算当天的号源数量
            for (int i = 0;i<calc;i++){
                ptdtzsl[i] = ptzsl[i];
            }

            int pxh = 0;
            int step2 =0;
            //封装号源明细数据
            for (int i = 0;i<calc;i++){

                if(ptzsl[i]==0){
                    continue;
                }
                //号源时间 按照班次开始时间和限号数量推算就诊时间
                String hysj = "";
                int day = (i+step2)*30;
                int day1 = (i+1+step2)*30;

                //号源时间 按照班次开始时间和限号数量推算就诊时间区间的结束时间
                String hyjdkssj = dateAdd(sbkssj,Calendar.MINUTE,day,DateUtils.H_M_S);
                //号源时间 按照班次开始时间和限号数量推算就诊时间区间的结束时间
                String hyjdjssj = dateAdd(sbkssj,Calendar.MINUTE,day1,DateUtils.H_M_S);

                //如果开始时间在中午午休之内
                if(dateCompare(hyjdkssj,zwkssj,DateUtils.H_M_S)>=0 && dateCompare(hyjdkssj,zwjssj,DateUtils.H_M_S)<1){
                    hyjdkssj = dateAdd(hyjdkssj,Calendar.MINUTE,120,DateUtils.H_M_S);
                    hyjdjssj = dateAdd(hyjdjssj,Calendar.MINUTE,120,DateUtils.H_M_S);
                    step2 =Integer.parseInt(String.valueOf(step));
                }

                //处理最后的时间为00:00:00
                if(dateCompare(hyjdjssj,"00:00:00",DateUtils.H_M_S)==0){
                    if(hyjdjssj.length()>5){
                        hyjdjssj = sbjssj;
                    }else{
                        hyjdjssj = sbjssj+":00";
                    }
                }
                hysj = hyjdkssj;

                //每分钟的号源
                long sjdmxjg = 30/ptzsl[i];

                //封装预约明细
//                for (int k = 0; k <ptyyzsl[i] ; k++) {
//                    pxh+=1;
//                    hymxMap = createMap(map,pxh,hysj,hyjdkssj,
//                            hyjdjssj,ptzsl[i],"0");
//                    yshymxList.add(hymxMap);
//                    hysj =  DateUtil.dateAdd(hysj,Calendar.MINUTE,Integer.valueOf(String.valueOf(sjdmxjg)),DateUtil.TIME_FORMAT);
//                }

                //封装当天
                for (int k = 0; k <ptdtzsl[i] ; k++) {
                    pxh+=1;
                    outptDoctorRegisterDto = createMap(outptDoctorQueueDto,pxh,hysj,hyjdkssj,
                            hyjdjssj,ptzsl[i],"1",outptClassesQueueDto);
                    yshymxList.add(outptDoctorRegisterDto);
                    hysj =  dateAdd(hysj,Calendar.MINUTE,Integer.valueOf(String.valueOf(sjdmxjg)),DateUtils.H_M_S);
                }
            }
        }
        //插入号源明细
        if(!ListUtils.isEmpty(yshymxList)){
            int toIndex = 1000;
            for(int i = 0; i < yshymxList.size(); i += 1000){
                if (i + 1000 > yshymxList.size()) {
                    toIndex = yshymxList.size() - i;
                }
                List newList = yshymxList.subList(i, i + toIndex);
                outptClassesQueueDao.insertDoctorRegisterBatch(newList);
            }

        }

    }

    //封装明细
    private OutptDoctorRegisterDto createMap(OutptDoctorQueueDto outptDoctorQueueDto, int pxh, String hysj, String hyjdkssj,
                                             String hyjdjssj, Long fshysl, String hymxsx, OutptClassesQueueDto outptClassesQueueDto){
        OutptDoctorRegisterDto outptDoctorRegisterDto = new OutptDoctorRegisterDto();
        outptDoctorRegisterDto.setId(SnowflakeUtils.getId());
        outptDoctorRegisterDto.setHospCode(outptDoctorQueueDto.getHospCode());
        outptDoctorRegisterDto.setDqId(outptDoctorQueueDto.getId());
        outptDoctorRegisterDto.setRegisterTime(hysj);
        outptDoctorRegisterDto.setStartTime(hyjdkssj);
        outptDoctorRegisterDto.setEndTime(hyjdjssj);
        outptDoctorRegisterDto.setRegisterNum(fshysl);
        outptDoctorRegisterDto.setProfileId("");
        outptDoctorRegisterDto.setIsUse("0");
        outptDoctorRegisterDto.setIsLock("0");
        outptDoctorRegisterDto.setIsAdd("0");
        outptDoctorRegisterDto.setCrteId(outptClassesQueueDto.getCrteId());
        outptDoctorRegisterDto.setCrteName(outptClassesQueueDto.getCrteName());
        outptDoctorRegisterDto.setCrteTime(new Date());
        return outptDoctorRegisterDto;
    }

    private long getTime(String oldTime,String newTime,int jg) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        long NTime =df.parse(newTime).getTime();
        //从对象中拿到时间
        long OTime = df.parse(oldTime).getTime();
        String sNTime =  String.valueOf(NTime);
        String sOTime = String.valueOf(OTime);
        BigDecimal a1=new BigDecimal(sNTime);
        BigDecimal a2=new BigDecimal(sOTime);
        BigDecimal s = BigDecimalUtils.subtract(a1,a2);
        long diff = Math.round(Double.parseDouble(s.toString())/1000/60/jg);
        return diff;

    }

    private void checkData(OutptDoctorQueueDto outptDoctorQueueDto){
//        String dlrq = UtilFunc.getString(map, "dlrq");
//        String sbkssj = UtilFunc.getString(map, "sbkssj");
//        String sbjssj = UtilFunc.getString(map, "sbjssj");
//        String mzxhsl = UtilFunc.getString(map, "mzxhsl");
//        String yyxhsl = UtilFunc.getString(map, "yyxhsl");
//        String ysxm = UtilFunc.getString(map, "ysmc");
//
//        if (UtilFunc.isEmpty(sbkssj) || UtilFunc.isEmpty(sbjssj) || UtilFunc.isEmpty(dlrq)) {
//            throw new ApusException("医生队列列表中 " + ysxm + " 行的时间没有填写，请检查后重新输入！");
//        }
//
//
//        //转化成Date
//        Date bcksDate = DateUtil.toDatetime(sbkssj, "HH:mm");
//        Date bcjsDate = DateUtil.toDatetime(sbjssj, "HH:mm");
//        Date maxDate = DateUtil.toDatetime("23:59", "HH:mm");
//        //验证时间的格式
//        if (!DateUtil.isDate(sbkssj, "HH:mm") || !DateUtil.isDate(sbjssj, "HH:mm")
//                || !DateUtil.isDate(dlrq, "yyyy-MM-dd")
//                || sbkssj.length() !=5
//                || sbjssj.length() !=5
//                || bcksDate.getTime()>maxDate.getTime()
//                ||bcjsDate.getTime()> maxDate.getTime()
//                || bcksDate.getTime()>bcjsDate.getTime()) {
//            throw new ApusException("医生队列列表中 " + ysxm + " 行的时间格式不对，请检查后重新输入！");
//        }
//
//        if(UtilFunc.isEmpty(mzxhsl)){
//            throw new ApusException("医生队列列表中 "+ysxm+" 行的门诊限号数量为空！");
//        }
//        if(UtilFunc.isEmpty(yyxhsl)){
//            throw new ApusException("医生队列列表中 "+ysxm+" 行的预约限号数量为空！");
//        }
//        //判断是否存在预约限号数量大于门诊限号数量
//        if (!UtilFunc.isEmpty(mzxhsl) && !UtilFunc.isEmpty(yyxhsl)) {
//            if (Integer.valueOf(mzxhsl) < Integer.valueOf(yyxhsl)) {
//                throw new ApusException("医生队列列表中 "+ysxm+" 行的预约限号数量大于门诊限号数量！");
//            }
//        }
    }

    private int dateCompare(String beginTime, String endTime,String strFormat) {
        int compareTo = -2;
        try {
            SimpleDateFormat format = new SimpleDateFormat(strFormat);
            Date date1 = format.parse(beginTime);
            Date date2 = format.parse(endTime);
            compareTo = date1.compareTo(date2);
            return compareTo;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return compareTo;
    }

    private String dateAdd(String inDate,int timeType,int day,String format) {
        try {
            SimpleDateFormat f = new SimpleDateFormat(format);
            Date date = f.parse(inDate);

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(timeType, day);
            return f.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
