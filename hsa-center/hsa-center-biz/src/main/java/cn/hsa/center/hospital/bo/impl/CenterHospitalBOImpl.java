package cn.hsa.center.hospital.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.datasource.bo.CenterDatasourceBO;
import cn.hsa.module.center.datasource.dao.CenterHospitalDatasourceDAO;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.datasource.dto.CenterHospitalDatasourceDTO;
import cn.hsa.module.center.datasource.entity.CenterDatasourceDO;
import cn.hsa.module.center.datasource.entity.CenterHospitalDatasourceDO;
import cn.hsa.module.center.hospital.bo.CenterHospitalBO;
import cn.hsa.module.center.hospital.dao.CenterHospitalDAO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterHospitalDO;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;
import cn.hsa.module.center.sync.bo.CenterSyncBo;
import cn.hsa.module.center.sync.dto.CenterSyncDTO;
import cn.hsa.module.center.syncorderrule.bo.SyncOrderRuleBO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @Package_name: cn.hsa.sys.hospital.bo.impl
 * @Class_name:: CenterHospitalBOImpl
 * @Description: 医院信息管理业务逻辑实现层
 * @Author: zhangxuan
 * @Date: 2020-07-30 13:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class CenterHospitalBOImpl extends HsafBO implements CenterHospitalBO {
    /**
     * 医院数据库访问接口
     */
    @Resource
    private CenterHospitalDAO centerHospitalDao;
    @Resource
    private CenterDatasourceBO centerDatasourceBO;
    @Resource
    private CenterSyncBo centerSyncBo;
    @Resource
    private SyncOrderRuleBO syncOrderRuleBO;


    @Resource
    private CenterHospitalDatasourceDAO centerHospitalDatasourceDAO;

    /**
     * @Menthod getByHospCode()
     * @Desrciption  通过code查询医院信息
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/7/30 15:45
     * @Return cn.hsa.module.sys.hospital.dao.CenterHospitalDTO
     **/
    @Override
    public CenterHospitalDTO getByHospCode(String hospCode) {
        return this.centerHospitalDao.getByHospCode(hospCode);
    }
    /**
     * @Menthod getById()
     * @Desrciption  通过id查询医院信息
     * @Param
     * 1. id
     * @Author zhangxuan
     * @Date   2020/7/30 15:45
     * @Return cn.hsa.module.sys.hospital.dao.CenterHospitalDTO
     **/
    @Override
    public CenterHospitalDTO getById(CenterHospitalDTO centerHospitalDTO) {
        return this.centerHospitalDao.getById(centerHospitalDTO);
    }
    /**
     * @Menthod queryPage()
     * @Desrciption 根据条件查询医院信息
     * @Param
     * 1. CenterHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 17:02
     * @Return cn.hsa.center.PageDTO
     **/
    @Override
    public PageDTO queryPage(CenterHospitalDTO centerHospitalDTO) {
        // 设置分页信息
        PageHelper.startPage(centerHospitalDTO.getPageNo(), centerHospitalDTO.getPageSize());
        // 根据条件查询所
        List<CenterHospitalDTO> centerHospitalDTOS = centerHospitalDao.queryPage(centerHospitalDTO);
        return PageDTO.of(centerHospitalDTOS);
    }

    /**
     *  每天0:10 定时更新医院服务状态信息
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void updateServericeStatusByPeriod(){
        Date nowDate = new Date();
        log.info("updateServiceStatus:{},开始更新医院服务有效期状态",DateUtils.format(nowDate,DateUtils.Y_M_DH_M_S));
        CenterHospitalDTO hospitalDTO = new CenterHospitalDTO();
        hospitalDTO.setIsValid(Constants.SF.S);
        List<CenterHospitalDTO> hospitalDTOList = centerHospitalDao.queryAll(hospitalDTO);
        List<String> hospIdListToExpired = new ArrayList<>();
        List<String> hospIdListExpired = new ArrayList<>();
        List<String> hospIdListNormally= new ArrayList<>();
        for(CenterHospitalDTO  centerHospitalDTO : hospitalDTOList){

            long  timeMillsAfter15Days =  org.apache.commons.lang3.time.DateUtils.addDays(nowDate,15).getTime();
            long  timeMillsOfNow = nowDate.getTime();
            long  timeMillsOfEndDate = centerHospitalDTO.getEndDate().getTime();
            if(timeMillsOfNow - timeMillsOfEndDate >= 0 && !centerHospitalDTO.getServiceStatus().equals(CenterHospitalDO.FWZT.YGQ)){
                // 已经过期 记录已经过期的医院id，只有状态未被更新的才会记录
                hospIdListExpired.add(centerHospitalDTO.getId());
                continue;
            }
            if(timeMillsAfter15Days >= timeMillsOfEndDate && !centerHospitalDTO.getServiceStatus().equals(CenterHospitalDO.FWZT.JJDQ)){
                // 还有15天过期的 记录即将过期的医院id,只有状态未被更新的才会记录
                hospIdListToExpired.add(centerHospitalDTO.getId());
                continue;
            }
            if(!centerHospitalDTO.getServiceStatus().equals(CenterHospitalDO.FWZT.ZC)) {
                hospIdListNormally.add(centerHospitalDTO.getId());
            }
        }
        if(hospIdListToExpired.size() > 0){
            centerHospitalDao.updateServiceStatus(CenterHospitalDO.FWZT.JJDQ,hospIdListToExpired);
            log.info("updateServiceStatus:{},以上医院的有效状态被更新为即将过期", JSONArray.toJSONString(hospIdListToExpired));
        }
        if(hospIdListExpired.size() > 0){
            centerHospitalDao.updateServiceStatus(CenterHospitalDO.FWZT.YGQ,hospIdListExpired);
            log.info("updateServiceStatus:{},以上医院的有效状态被更新为已过期", JSONArray.toJSONString(hospIdListExpired));
        }
        if(hospIdListNormally.size() > 0){
            centerHospitalDao.updateServiceStatus(CenterHospitalDO.FWZT.ZC,hospIdListNormally);
            log.info("updateServiceStatus:{},以上医院的有效状态被更新为正常", JSONArray.toJSONString(hospIdListNormally));
        }
    }

    /**
     * @Menthod queryAll()
     * @Desrciption  查询所有医院信息
     *
     * @Param
     * [CenterHospitalDTO]
     * @Author zhangxuan
     * @Date   2020/8/03 15:31
     * @Return java.util.List<cn.hsa.module.sys.Hospital.dto.CenterHospitalDTO>
     **/
    @Override
    public List<CenterHospitalDTO> queryAll(CenterHospitalDTO centerHospitalDTO) {
        List<CenterHospitalDTO> centerHospitalDTOS = centerHospitalDao.queryAll(centerHospitalDTO);
        return centerHospitalDTOS;
    }


    /**
     * @Menthod insert()
     * @Desrciption 新增医院
     * @Param
     * 1. CenterHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/8/03 15:53
     * @Return int
     **/
    @Override
    public boolean insert(CenterHospitalDTO centerHospitalDTO) {
            return save(centerHospitalDTO);
    }

    /**
     * @Menthod delete()
     * @Desrciption 删除医院
     * @Param
     * 1. map
     * @Author zhangxuan
     * @Date   2020/8/03 15:57
     * @Return int
     **/
    @Override
    public boolean delete(CenterHospitalDTO CenterHospitalDTO) {
            CenterHospitalDTO.setId(CenterHospitalDTO.getId());
            CenterHospitalDTO.setCode(CenterHospitalDTO.getCode());
            return centerHospitalDao.delete(CenterHospitalDTO) > 0;
    }
    /**
     * @Menthod update()
     * @Desrciption 修改医院信息
     * @Param
     * 1. CenterHospitalDTO  医院数据传输对象
     * @Author zhangxuan
     * @Date   2020/7/30 15:58
     * @Return int
     **/
    @Override
    public boolean update(CenterHospitalDTO centerHospitalDTO) {
        return save(centerHospitalDTO);
    }
    /**
     * @Method: save()
     * @Description: 该方法主要用来保存医院信息维护修改和新增的信息
     * @Param: centerHospitalDTO数据传输对象
     * @Author: zhangxuan
     * @Date: 2020/8/4 14:17
     * @Return: centerHospitalDTO数据传输对象集合
     */
    public boolean save(CenterHospitalDTO centerHospitalDTO) {
        //生成拼音码
        if (StringUtils.isEmpty(centerHospitalDTO.getPym())) {
            centerHospitalDTO.setPym(PinYinUtils.toFirstPY(centerHospitalDTO.getName()));
        }
        //生成五笔码
        if (StringUtils.isEmpty(centerHospitalDTO.getWbm())) {
            centerHospitalDTO.setPym(PinYinUtils.toFirstPY(centerHospitalDTO.getName()));
        }
        int count = centerHospitalDao.queryCodeIsExist(centerHospitalDTO);
        //说明编码已经存在
        if (count > 0){
            throw new AppException("该编码已经存在");
        }
        //判断主键Id是否存在 如果存在 则是修改操作 否则就是新增操作
        if(StringUtils.isEmpty(centerHospitalDTO.getId())){
            centerHospitalDTO.setId(SnowflakeUtils.getId());   // 设置主键id
            centerHospitalDTO.setCode(syncOrderRuleBO.updateOrderNo("YYBM"));
            centerHospitalDTO.setAuditFlag("0");
            centerHospitalDTO.setCrteTime(DateUtils.getNow()); //设置操作时间
            centerHospitalDTO.setCrteId(centerHospitalDTO.getCrteId());     // 设置操作人id
            centerHospitalDTO.setCrteName(centerHospitalDTO.getCrteName()); // 设置操作人姓名

            List<CenterSyncFlowDto> centerSyncFlowDtos = new ArrayList<CenterSyncFlowDto>();
            CenterSyncFlowDto centerSyncFlowDto = null ;
            int i =0 ;
            while(i<6){
                centerSyncFlowDto = new CenterSyncFlowDto ();
                centerSyncFlowDto.setId(SnowflakeUtils.getId());
                centerSyncFlowDto.setHospCode(centerHospitalDTO.getCode());
                centerSyncFlowDto.setType(String.valueOf(i));
                centerSyncFlowDto.setStatusCode((i == 0?"1":"0"));
                centerSyncFlowDto.setMessage((i == 0?"操作成功":"等待"));
                //创建时间每次往后移，是为了方便前端展示自动创建数据库的一个时间线，统一个时间不好去排序展示
                centerSyncFlowDto.setCreatTime(DateUtils.dateAddMinute(new Date(),i));
                centerSyncFlowDtos.add(centerSyncFlowDto);
                i++;
            }

            centerHospitalDao.insert(centerHospitalDTO);

            if(!centerSyncFlowDtos.isEmpty()){
                centerHospitalDao.insertBatchCenterSyncFlow(centerSyncFlowDtos);
            }


            return  true;
        }else{
            return centerHospitalDao.update(centerHospitalDTO) > 0;
        }
    }


    /**
     * @Menthod queryCenterSyncFlow()
     * @Desrciption  查询医院同步数据
     *
     * @Param
     * [CenterHospitalDTO]
     * @Author [pengbo]
     * @Date   2022/3/23 15:31
     * @Return java.util.List<cn.hsa.module.sys.Hospital.dto.CenterHospitalDTO>
     **/
    @Override
    public List<CenterSyncFlowDto> queryCenterSyncFlows(CenterSyncFlowDto centerSyncFlowDto) {
        List<CenterSyncFlowDto> centerSyncFlowDtos = centerHospitalDao.queryCenterSyncFlows(centerSyncFlowDto);
        return centerSyncFlowDtos;
    }

    /**
     * @param centerHospitalDTO
     * @Menthod auditHosp()
     * @Desrciption 审核医院
     * @Param 1.[CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date 2022/3/21 16:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.Hospital.dto.CenterHospitalDTO>
     */
    @Override
    public boolean updateAudit(CenterHospitalDTO centerHospitalDTO) {
        //修改医院审核状态
        centerHospitalDao.updateAudit(centerHospitalDTO);

        //审核时间线集合
        List<CenterSyncFlowDto> centerSyncFlowDtos = new ArrayList<CenterSyncFlowDto>();
        //时间线流程对象
        CenterSyncFlowDto  centerSyncFlowDto= null ;
        //医院对应数据库对象
        CenterHospitalDatasourceDO centerHospitalDatasourceDO = null ;

        //审核通过的情况下 进行建库，匹配，下发等操作
        if("1".equals(centerHospitalDTO.getAuditFlag())){


            //获取数据库ROOT账号信息
            CenterRootDatabaseBO centerRootDatabaseBO = centerHospitalDao.findRootDataBase();
            try {
                //新建医院数据库用户
                try {
                    if(centerRootDatabaseBO == null){
                        throw new AppException("中心端ROOT权限用户未配置！");
                    }
                    centerSyncFlowDto = new CenterSyncFlowDto ();
                    centerSyncFlowDto.setHospCode(centerHospitalDTO.getCode());
                    centerSyncFlowDto.setType("1");
                    centerSyncFlowDto.setCreatTime(new Date());
                    centerSyncFlowDto.setStatusCode("1");
                    centerSyncFlowDto.setMessage("操作成功");
                    createDataUser(centerHospitalDTO,centerRootDatabaseBO);
                }catch (Exception e){
                    e.printStackTrace();
                    centerSyncFlowDto.setStatusCode("2");
                    centerSyncFlowDto.setMessage("新建医院数据库用户，原因："+e.getMessage());
                    throw new RuntimeException("新建医院数据库用户失败，原因："+e.getMessage());
                }finally {

                    centerSyncFlowDtos.add(centerSyncFlowDto);
                }

                //创建医院数据库信息
                try {
                    centerSyncFlowDto = new CenterSyncFlowDto ();
                    centerSyncFlowDto.setHospCode(centerHospitalDTO.getCode());
                    centerSyncFlowDto.setType("2");
                    centerSyncFlowDto.setCreatTime(new Date());
                    centerSyncFlowDto.setStatusCode("1");
                    centerSyncFlowDto.setMessage("操作成功");
                    centerHospitalDatasourceDO =  createDataSource (centerHospitalDTO,centerRootDatabaseBO);
                }catch (Exception e){
                    centerSyncFlowDto.setStatusCode("2");
                    centerSyncFlowDto.setMessage("创建医院数据库信息操作失败,原因："+e.getMessage());
                    throw new RuntimeException("创建医院数据库信息失败!");
                }finally {

                    centerSyncFlowDtos.add(centerSyncFlowDto);
                }
                //匹配医院数据源信息
                try {
                    centerSyncFlowDto = new CenterSyncFlowDto ();
                    centerSyncFlowDto.setHospCode(centerHospitalDTO.getCode());
                    centerSyncFlowDto.setType("3");
                    centerSyncFlowDto.setCreatTime(new Date());
                    centerSyncFlowDto.setStatusCode("1");
                    centerSyncFlowDto.setMessage("操作成功");
                    centerSyncFlowDtos.add(centerSyncFlowDto);
                    linkDataSource(centerHospitalDatasourceDO);
                }catch (Exception e){
                    centerSyncFlowDto.setStatusCode("2");
                    centerSyncFlowDto.setMessage("匹配医院数据源信息操作失败,原因："+e.getMessage());
                    throw new RuntimeException("匹配医院数据源失败!");
                }finally {

                    centerSyncFlowDtos.add(centerSyncFlowDto);
                }
                //下发医院基础数据
                try {
                    centerSyncFlowDto = new CenterSyncFlowDto ();
                    centerSyncFlowDto.setHospCode(centerHospitalDTO.getCode());
                    centerSyncFlowDto.setType("4");
                    centerSyncFlowDto.setCreatTime(new Date());
                    centerSyncFlowDto.setStatusCode("1");
                    centerSyncFlowDto.setMessage("操作成功");
                    centerSyncFlowDtos.add(centerSyncFlowDto);
                    downBaseData (centerHospitalDatasourceDO);
                }catch (Exception e){
                    e.printStackTrace();
                    centerSyncFlowDto.setStatusCode("2");
                    centerSyncFlowDto.setMessage("下发医院基础数据操作失败,原因："+e.getMessage());
                    throw new RuntimeException("下发匹配同步表失败!");
                }finally {

                    centerSyncFlowDtos.add(centerSyncFlowDto);
                }

                //全部完成
                try {
                    centerSyncFlowDto = new CenterSyncFlowDto ();
                    centerSyncFlowDto.setHospCode(centerHospitalDTO.getCode());
                    centerSyncFlowDto.setType("5");
                    centerSyncFlowDto.setCreatTime(new Date());
                    centerSyncFlowDto.setStatusCode("1");
                    centerSyncFlowDto.setMessage("操作成功");
                    centerSyncFlowDtos.add(centerSyncFlowDto);
                }catch (Exception e){
                    centerSyncFlowDto.setStatusCode("2");
                    centerSyncFlowDto.setMessage("操作失败,原因："+e.getMessage());
                    throw new RuntimeException("全部完成失败!");
                }finally {
                    centerSyncFlowDtos.add(centerSyncFlowDto);
                }


            }catch (Exception e){
                    e.printStackTrace();
            }



            if(!ListUtils.isEmpty(centerSyncFlowDtos)){
                centerHospitalDao.updateBatchCenterSyncFlow(centerSyncFlowDtos);
            }
        }

        return false;
    }

    @Override
    public boolean updateRootBase(CenterRootDatabaseBO centerRootDatabaseBO) {
        if(StringUtils.isEmpty(centerRootDatabaseBO.getId())){
            centerRootDatabaseBO.setId(SnowflakeUtils.getId());
            return centerHospitalDao.insertRootBase(centerRootDatabaseBO);
        }

        return centerHospitalDao.updateRootBase(centerRootDatabaseBO);
    }

    @Override
    public CenterRootDatabaseBO findRootBase(CenterRootDatabaseBO centerRootDatabaseBO) {
        return centerHospitalDao.findRootDataBase();
    }


    public CenterHospitalDatasourceDO createDataSource (CenterHospitalDTO centerHospitalDTO,CenterRootDatabaseBO centerRootDatabaseBO ){
        String dsCode = "DS"+centerHospitalDTO.getCode();
        CenterDatasourceDTO centerDatasourceDTO =  new CenterDatasourceDTO();
        centerDatasourceDTO.setCode(dsCode);
        List<CenterDatasourceDTO> centerDatasourceDTOList = (List<CenterDatasourceDTO>) centerDatasourceBO.queryCenterDatasourcePage(centerDatasourceDTO).getData().getResult();


        CenterDatasourceDO centerDatasourceDO = new CenterDatasourceDO();
        if(ListUtils.isEmpty(centerDatasourceDTOList)){
            //主键ID
            centerDatasourceDO.setId(SnowflakeUtils.getId());//id
            //数据据编码
            centerDatasourceDO.setCode(dsCode);
            //数据库名称
            centerDatasourceDO.setName(centerHospitalDTO.getName()+"数据库");
            //数据库类型
            centerDatasourceDO.setTypeCode(centerRootDatabaseBO.getType());
            //数据源驱动
            centerDatasourceDO.setDriverName(centerRootDatabaseBO.getJdbcDriver());;
            //数据源URL
            String url = centerRootDatabaseBO.getHospUrl().replaceAll("\\{dataBaseName}","" + centerHospitalDTO.getDataName() + "");
            centerDatasourceDO.setUrl(url);
            //数据源账号
            centerDatasourceDO.setUsername(centerRootDatabaseBO.getRootUser());
            //数据源密码
            centerDatasourceDO.setPassword(centerRootDatabaseBO.getRootPassword());
            //创建人ID
            centerDatasourceDO.setCrteId(centerHospitalDTO.getCrteId());
            //创建人姓名
            centerDatasourceDO.setCrteName(centerHospitalDTO.getCrteName());
            centerDatasourceDO.setCrteTime(new Date());//创建时间
            centerDatasourceBO.addCenterDatasource(centerDatasourceDO);
        }else {
            centerDatasourceDO = centerDatasourceDTOList.get(0);
        }



        CenterHospitalDatasourceDO centerHospitalDatasourceDO = new CenterHospitalDatasourceDO () ;
        //主键
        centerHospitalDatasourceDO.setId(SnowflakeUtils.getId());
        //数据源编码
        centerHospitalDatasourceDO.setDsCode(centerDatasourceDO.getCode());
        //医院编码
        centerHospitalDatasourceDO.setHospCode(centerHospitalDTO.getCode());

        //创建人ID
        centerHospitalDatasourceDO.setCrteId(centerHospitalDTO.getCrteId());
        centerHospitalDatasourceDO.setCrteName(centerHospitalDTO.getCrteName());
        centerHospitalDatasourceDO.setCrteTime(new Date());//创建时间

        return centerHospitalDatasourceDO;
    }

    public void linkDataSource (CenterHospitalDatasourceDO centerHospitalDatasourceDO){

        List<CenterHospitalDatasourceDO> centerHospitalDatasourceDOS = new ArrayList<CenterHospitalDatasourceDO>();
        centerHospitalDatasourceDOS.add(centerHospitalDatasourceDO);


        Map map = new HashMap<>();
        map.put("list", centerHospitalDatasourceDOS);
        List<CenterHospitalDatasourceDTO> centerHospitalDatasourceList = centerHospitalDatasourceDAO.queryHaveHospCode(map);
        if(!ListUtils.isEmpty(centerHospitalDatasourceList)){
            for (CenterHospitalDatasourceDTO dto: centerHospitalDatasourceList) {
                if(!dto.getDsCode().equals(centerHospitalDatasourceDO.getDsCode())){
                    throw new AppException("存在医院已经绑定数据源");
                }
            }
        }
        //删除医院和数据源关系信息
        centerHospitalDatasourceDAO.delByDsCode(centerHospitalDatasourceDO.getDsCode());
        //批量新增数据源关系信息
        centerHospitalDatasourceDAO.batchInsert(centerHospitalDatasourceDOS);
    }


    public void downBaseData (CenterHospitalDatasourceDO centerHospitalDatasourceDO){
        CenterSyncDTO centerSyncDTO = new CenterSyncDTO();
        centerSyncDTO.setNewHospCode(centerHospitalDatasourceDO.getHospCode());
        centerSyncDTO.setIsValid("1");
        centerSyncDTO.setCrteId(centerHospitalDatasourceDO.getCrteId());
        centerSyncDTO.setCrteTime(new Date());
        List<CenterSyncDTO> list = (List<CenterSyncDTO>) centerSyncBo.queryCenterSyncTableList(centerSyncDTO).getData();

        if(ListUtils.isEmpty(list)){
            throw new RuntimeException("未配置需要同步的表!");
        }

        centerSyncDTO.setList(list);
        centerSyncBo.saveSyncHospData(centerSyncDTO);
    }



    public void createDataUser(CenterHospitalDTO centerHospitalDTO,CenterRootDatabaseBO centerRootDatabaseBO ) {
        String dataName = "his_"+centerHospitalDTO.getCode();
        centerHospitalDTO.setDataName(dataName);
        /**
         * 获取医院配置的数据库连接
         */
        Connection connection = null ;
        PreparedStatement cmd = null ;
        ResultSet resultSet = null ;
        try {
            connection = DBUtils.getConnection(centerRootDatabaseBO.getJdbcDriver(), centerRootDatabaseBO.getRootUrl(), centerRootDatabaseBO.getRootUser(),
                    centerRootDatabaseBO.getRootPassword());
            connection.setAutoCommit(false);

            //判断数据库实例是否存在
            cmd =  connection.prepareStatement("SELECT * FROM information_schema.SCHEMATA where SCHEMA_NAME='"+dataName+"'");
            resultSet =  cmd.executeQuery();
            if(resultSet.next()){
                throw new AppException(dataName+"数据库实例已经存在!");
            };
            //创建数据库实例
            StringBuffer sql = new StringBuffer();
            sql.append("CREATE DATABASE "+dataName+" DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci");
            cmd =  connection.prepareStatement(sql.toString());
            cmd.execute();
            connection.commit();
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (cmd != null) {
                try {
                    cmd.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
