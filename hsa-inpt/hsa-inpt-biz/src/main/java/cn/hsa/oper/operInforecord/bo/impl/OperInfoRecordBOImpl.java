package cn.hsa.oper.operInforecord.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.oper.operInforecord.bo.OperInfoRecordBO;
import cn.hsa.module.oper.operInforecord.dao.OperInfoRecordDAO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import cn.hsa.module.oper.operRoom.dao.OperRoomDAO;
import cn.hsa.module.oper.operRoom.dto.OperRoomDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.oper.operInfoRecord.bo.impl
 * @Class_name:: OperInfoRecordBOImpl
 * @Description: 手麻系统
 * @Author: 马荣
 * @Date: ２０２０／０９／１７　　18点18分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Component
@Slf4j
public class OperInfoRecordBOImpl extends HsafBO implements OperInfoRecordBO {

    @Resource
    private OperInfoRecordDAO operInfoRecordDAO;

    @Resource
    private BaseDeptService baseDeptService_consumer;

    @Resource
    private BaseDiseaseService baseDiseaseService_consumer;

    @Resource
    private OperRoomDAO operRoomDAO;

    @Resource
    private SysUserService sysUserService_consumer;

    /**
     * @Method saveSurgery
     * @Param [inptAdviceDTO]
     * @description   保存手术登记信息
     * @author marong
     * @date 2020/9/18 11:05
     * @return java.lang.Boolean
     * @throws
     */
    @Override
    public Boolean saveSurgery(OperInfoRecordDTO operInfoRecordDTO) {
        return operInfoRecordDAO.insertSurgery(operInfoRecordDTO) > 0;
    }

    /** 根据医嘱ID更新手术信息 **/
    @Override
    public Boolean updateSurgeryByAdviceId(Map map) {
        if (map.get("operInfoRecordDTO") == null) {
            throw new AppException("operInfoRecordDTO 必传参数不能为空！");
        }
       OperInfoRecordDTO operInfoRecordDTO = MapUtils.get(map,"operInfoRecordDTO");
       int affectRows = operInfoRecordDAO.updateSurgeryByAdviceId(operInfoRecordDTO);

        return affectRows > 0;
    }

    /**
     * @Method updateSurgeryStatus
     * @Param [operInfoRecordDO]
     * @description 更改手术登记的状态
     * @author marong
     * @date 2020/9/18 17:20
     * @return java.lang.Boolean
     * @throws
     */
    @Override
    public Boolean updateSurgeryStatus(OperInfoRecordDTO operInfoRecordDTO) {
//        boolean checkFlag = checkDoctorId(operInfoRecordDTO);  //检查手术安排中的主刀医生是否已安排手术
//        // 相同的医生和相同的手术安排时间
//        if(checkFlag){
//            throw new AppException("该医生今日已安排手术！");
//        }
        this.checkStatusCode(operInfoRecordDTO);

        return operInfoRecordDAO.updateSurgeryStatus(operInfoRecordDTO) > 0;
    }

    /**
     * @Method updateSurgeryInfo
     * @Param [operInfoRecordDO]
     * @description 手术登记信息更新
     * @author marong
     * @date 2020/9/18 17:20
     * @return java.lang.Boolean
     * @throws
     */
    @Override
    public Boolean updateSurgeryInfo(OperInfoRecordDTO operInfoRecordDTO) {

        this.checkStatusCode(operInfoRecordDTO);

        if(StringUtils.isEmpty(operInfoRecordDTO.getIsOver())){
            SysUserDTO userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getAuditId());
            if (userDTO != null) {
                operInfoRecordDTO.setAuditName(userDTO.getName());
                operInfoRecordDTO.setAuditId(userDTO.getId());
            }
            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getDoctorId());
            if (userDTO != null) {
                operInfoRecordDTO.setDoctorName(userDTO.getName());
                operInfoRecordDTO.setDoctorId(userDTO.getId());
            }
            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getAssistantId1());
            if (userDTO != null) {
                operInfoRecordDTO.setAssistantName1(userDTO.getName());
                operInfoRecordDTO.setAssistantId1(userDTO.getId());
            }
            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getAssistantId2());
            if (userDTO != null) {
                operInfoRecordDTO.setAssistantName2(userDTO.getName());
                operInfoRecordDTO.setAssistantId2(userDTO.getId());
            }
            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getAssistantId3());
            if (userDTO != null) {
                operInfoRecordDTO.setAssistantName3(userDTO.getName());
                operInfoRecordDTO.setAssistantId3(userDTO.getId());
            }

            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getAnaId1());
            if (userDTO != null) {
                operInfoRecordDTO.setAnaName1(userDTO.getName());
                operInfoRecordDTO.setAnaId1(userDTO.getId());
            }
            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getAnaId2());
            if (userDTO != null) {
                operInfoRecordDTO.setAnaName2(userDTO.getName());
                operInfoRecordDTO.setAnaId2(userDTO.getId());
            }
            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getAnaId3());
            if (userDTO != null) {
                operInfoRecordDTO.setAnaName3(userDTO.getName());
                operInfoRecordDTO.setAnaId3(userDTO.getId());
            }
            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getQxNurseId());
            if (userDTO != null) {
                operInfoRecordDTO.setQxNurseName(userDTO.getName());
                operInfoRecordDTO.setQxNurseId(userDTO.getId());
            }
            userDTO = getSysUserByCode(operInfoRecordDTO.getHospCode(), operInfoRecordDTO.getXhNurseId());
            if (userDTO != null) {
                operInfoRecordDTO.setXhNurseName(userDTO.getName());
                operInfoRecordDTO.setXhNurseId(userDTO.getId());
            }
            return operInfoRecordDAO.updateSurgeryInfo(operInfoRecordDTO) > 0;
        }else{
            OperRoomDTO operRoomDTO = new OperRoomDTO();
            operRoomDTO.setName(operInfoRecordDTO.getOperRoomName());
            operRoomDTO.setHospCode(operInfoRecordDTO.getHospCode());
            operRoomDTO.setIsUse("0");
//            operRoomDAO.updateOperRoomStatus(operRoomDTO);
            return operRoomDAO.updateOperRoomStatus(operRoomDTO) > 0;
        }

    }

    private void checkStatusCode (OperInfoRecordDTO operInfoRecordDTO){
        if (StringUtils.isEmpty(operInfoRecordDTO.getId())){
            throw new AppException("修改失败，id为空，请联系管理员");
        }

        OperInfoRecordDTO operInfoById = operInfoRecordDAO.getOperInfoById(operInfoRecordDTO);
        String statusCode = operInfoById.getStatusCode();

        String updateStatusCode = operInfoRecordDTO.getUpdateStatusCode();

        if (!StringUtils.isEmpty(updateStatusCode)&& !StringUtils.isEmpty(statusCode)){
            /* statusCode  已申请	1 ,已安排	2 ,已完成	3 ,已记账	4 ,已开医嘱但未审核 -2 **/
                 //updateStatusCode 0取消手术 1 申请登记 2取消登记 3手术安排 4 取消安排 5完成登记
            switch (updateStatusCode){
                case "0":
                    if (!"0".equals(statusCode)){
                        throw new AppException("此记录不是未申请状态，取消手术失败！");
                    }
                    break;
                case "1":
                    if (!"0".equals(statusCode)){
                        throw new AppException("此记录不是未申请状态，申请登记失败！");
                    }
                    break;
                case "2":
                    if (!"1".equals(statusCode)){
                        throw new AppException("此记录不是已申请状态，取消申请失败！");
                    }
                    operInfoRecordDTO.setAuditTime(null);
                    operInfoRecordDTO.setAuditId(null);
                    operInfoRecordDTO.setAuditName(null);
                    operInfoRecordDTO.setOperApplyStatusChange(Constants.OPER_STATUS_CHANGE.APPLY_CANCEL);
                    break;
                case "3":
                    if (!"1".equals(statusCode)){
                        throw new AppException("此记录不是已申请状态，手术安排失败！");
                    }
                    break;
                case "4":
                    if (!"2".equals(statusCode)){
                        throw new AppException("此记录不是已安排状态，取消安排失败！");
                    }
                    break;
                case "5":
                    if (!"2".equals(statusCode)){
                        throw new AppException("此记录不是已安排状态，完成登记失败！");
                    }
                    break;
            }
        }
    }

    private SysUserDTO getSysUserByCode(String hospCode, String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        Map map1 = new HashMap();
        map1.put("hospCode", hospCode);
        map1.put("userCode", code);
        SysUserDTO userDTO = sysUserService_consumer.getByCode(map1).getData();
        return userDTO;
    }

    /**
     * @Method queryOperInfoRecordList
     * @Param [operInfoRecordDO]
     * @description
     * @author marong
     * @date 2020/9/22 20:14
     * @return java.util.List<cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO>
     * @throws
     */
    @Override
    public PageDTO queryOperInfoRecordList(OperInfoRecordDTO operInfoRecordDTO) {
        List<OperInfoRecordDTO> operInfoRecordDTOS;
        PageHelper.startPage(operInfoRecordDTO.getPageNo(), operInfoRecordDTO.getPageSize());
        if (Constants.VISITTYPE.OUTPT.equals(operInfoRecordDTO.getInptOrOutpt())){ // 门诊病人
            operInfoRecordDTOS = operInfoRecordDAO.queryOperInfoRecordOutpt(operInfoRecordDTO);

        }else if (Constants.VISITTYPE.INPT.equals(operInfoRecordDTO.getInptOrOutpt())){// 住院病人
            operInfoRecordDTOS = operInfoRecordDAO.queryOperInfoRecordInpt(operInfoRecordDTO);

        }else{  // 查询全部
            operInfoRecordDTOS = operInfoRecordDAO.queryOperInfoRecordBasic(operInfoRecordDTO);
        }
        /*增加科室名称*/
        operInfoRecordDTOS = getDeptInfo(operInfoRecordDTOS, operInfoRecordDTO);

        return PageDTO.of(operInfoRecordDTOS);
    }

    /*增加icd9名称*/
    private List<OperInfoRecordDTO> getBaseDiseaseInfo(List<OperInfoRecordDTO> operInfoRecordDTOS, OperInfoRecordDTO operInfoRecordDTO) {
        BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
        baseDiseaseDTO.setHospCode(operInfoRecordDTO.getHospCode());
        List<String> icd9List = new ArrayList<String>();
        operInfoRecordDTOS.forEach(oper ->{
            icd9List.add(oper.getOperDiseaseIcd9());
        });
        baseDiseaseDTO.setCodes(icd9List);
        Map map = new HashMap();
        map.put("hospCode", operInfoRecordDTO.getHospCode());
        map.put("baseDiseaseDTO",baseDiseaseDTO);
        List<BaseDiseaseDTO> baseDiseaseDTOS = baseDiseaseService_consumer.getDiseaseByIds(map);

        Map<String, BaseDiseaseDTO> baseDiseaseMapList = new HashMap<>();
        baseDiseaseDTOS.forEach(disease ->{
            baseDiseaseMapList.put(disease.getCode(),disease);
        });

        for(int i=0; i< operInfoRecordDTOS.size(); i++){
            if(!StringUtils.isEmpty(operInfoRecordDTOS.get(i).getOperDiseaseIcd9())){
                operInfoRecordDTOS.get(i).setIcd9Name(baseDiseaseMapList.get(operInfoRecordDTOS.get(i).getOperDiseaseIcd9()).getName());
            }
        }
        return operInfoRecordDTOS;
    }

    /*增加科室名称*/
    private List<OperInfoRecordDTO> getDeptInfo(List<OperInfoRecordDTO> operInfoRecordDTOS,OperInfoRecordDTO operInfoRecordDTO) {
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setHospCode(operInfoRecordDTO.getHospCode());
        List<String> deptIdList = new ArrayList<String>();
        List<String> deptCodeList = new ArrayList<String>();
        operInfoRecordDTOS.forEach(oper ->{
            deptIdList.add(oper.getDeptId());
            deptIdList.add(oper.getOperDeptId());
            deptIdList.add(oper.getWardId());
            deptCodeList.add(oper.getOperDeptId());
        });
        baseDeptDTO.setIds(deptIdList);
        baseDeptDTO.setCodes(deptCodeList);
        Map map = new HashMap();
        map.put("hospCode", operInfoRecordDTO.getHospCode());
        map.put("baseDeptDTO",baseDeptDTO);
        List<BaseDeptDTO> deptList = baseDeptService_consumer.getDeptByIds(map);

        HashMap<String, BaseDeptDTO> deptMapListById = new HashMap<>();
        deptList.forEach(dept ->{
            if(!StringUtils.isEmpty(dept.getId())){
                deptMapListById.put(dept.getId(),dept);
            }
        });

        HashMap<String, BaseDeptDTO> deptMapListByCode = new HashMap<>();
        deptList = baseDeptService_consumer.getDeptByCodes(map);
        deptList.forEach(dept ->{
            if(!StringUtils.isEmpty(dept.getId())){
                deptMapListByCode.put(dept.getId(),dept);
            }
        });

        for(int i=0; i< operInfoRecordDTOS.size(); i++){
            if(!StringUtils.isEmpty(operInfoRecordDTOS.get(i).getDeptId())){
                operInfoRecordDTOS.get(i).setDeptName(deptMapListById.get(operInfoRecordDTOS.get(i).getDeptId()).getName());
            }
            if(!StringUtils.isEmpty(operInfoRecordDTOS.get(i).getOperDeptId())){
                operInfoRecordDTOS.get(i).setOperDeptName(deptMapListById.get(operInfoRecordDTOS.get(i).getOperDeptId()).getName());
            }
            if(!StringUtils.isEmpty(operInfoRecordDTOS.get(i).getWardId())){
                operInfoRecordDTOS.get(i).setInWardName(deptMapListById.get(operInfoRecordDTOS.get(i).getWardId()).getName());
            }
        }
        return operInfoRecordDTOS;
    }


    /**
     * @Method queryOperInfoRecord
     * @Param [operInfoRecordDO]
     * @description   校验手术是否重复
     * @author marong
     * @date 2020/9/25 17:22
     * @return cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO
     * @throws
     */
    @Override
    public Boolean queryOperInfoRecordIsRepeated(OperInfoRecordDTO operInfoRecordDTO) {
        return operInfoRecordDAO.queryOperInfoRecordIsRepeated(operInfoRecordDTO) > 0;
    }


    private Boolean checkDoctorId(OperInfoRecordDTO operInfoRecordDTO) {
        if(!StringUtils.isEmpty(operInfoRecordDTO.getOperPlanTime())){
            OperInfoRecordDTO newOperInfoRecordDTO = new OperInfoRecordDTO();
            newOperInfoRecordDTO.setHospCode(operInfoRecordDTO.getHospCode());
            newOperInfoRecordDTO.setOperPlanTime(operInfoRecordDTO.getOperPlanTime());
            List<OperInfoRecordDTO> operInfoRecordDoListDB = operInfoRecordDAO.queryOperInfoRecordList(newOperInfoRecordDTO);
            for(OperInfoRecordDO operInfoRecordDo:operInfoRecordDoListDB){
                if(operInfoRecordDTO.getDoctorId().equals(operInfoRecordDo.getDoctorId())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
    * @Menthod getOperInfoById
    * @Desrciption  通过id拿到手术记录
     * @param operInfoRecordDTO
    * @Author xingyu.xie
    * @Date   2021/1/14 20:31
    * @Return cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO
    **/

    @Override
    public OperInfoRecordDTO getOperInfoById(OperInfoRecordDTO operInfoRecordDTO){
        /*增加科室名称*/
        List<OperInfoRecordDTO> operInfoRecordDTOS = new ArrayList<>();
        OperInfoRecordDTO operInfoById = operInfoRecordDAO.getOperInfoById(operInfoRecordDTO);
        operInfoRecordDTOS.add(operInfoById);
        operInfoRecordDTOS = getDeptInfo(operInfoRecordDTOS,operInfoRecordDTO);
        return operInfoRecordDTOS.get(0);
    }

    @Override
    public Boolean updateSurgeryCompleteToCancel(OperInfoRecordDTO operInfoRecordDTO) {
        return operInfoRecordDAO.updateSurgeryCompleteToCancel(operInfoRecordDTO) > 0;
    }

    /**
     * @Menthod: queryOperCostByVisitId
     * @Desrciption: 查询个人手术补记账费用
     * @Param: visit_id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-09 17:37
     * @Return: List<InptCostDTO>
     **/
    @Override
    public List<InptCostDTO> queryOperCostByVisitId(Map<String, Object> paramMap) {
        return operInfoRecordDAO.queryOperCostByVisitId(paramMap);
    }

    /**
     * @Menthod: cancelOper
     * @Desrciption: 取消手术，已核收未申请的状态下取消，statusCode更改未-1
     * @Param: operInfoRecordDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-11 20:19
     * @Return:
     **/
    @Override
    public Boolean updateOperStatusCode(OperInfoRecordDTO operInfoRecordDTO) {
        OperInfoRecordDTO operInfoById = operInfoRecordDAO.getOperInfoById(operInfoRecordDTO);
        if ("1".equals(operInfoById.getStatusCode())) {
            throw new RuntimeException("【" + operInfoById.getContent() +"】已申请，请先取消申请");
        }
        if ("2".equals(operInfoById.getStatusCode())) {
            throw new RuntimeException("【" + operInfoById.getContent() +"】已安排，请先取消安排");
        }
        if ("3".equals(operInfoById.getStatusCode())) {
            throw new RuntimeException("【" + operInfoById.getContent() +"】已完成登记，无法进行取消");
        }
        operInfoRecordDTO.setStatusCode("-1");
        return operInfoRecordDAO.updateSurgeryStatus(operInfoRecordDTO) > 0;
    }
}
