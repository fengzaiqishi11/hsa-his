package cn.hsa.center.datasource.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.datasource.bo.CenterDatasourceBO;
import cn.hsa.module.center.datasource.dao.CenterDatasourceDAO;
import cn.hsa.module.center.datasource.dao.CenterHospitalDatasourceDAO;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.datasource.dto.CenterHospitalDatasourceDTO;
import cn.hsa.module.center.datasource.entity.CenterDatasourceDO;
import cn.hsa.module.center.datasource.entity.CenterHospitalDatasourceDO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.TableStructureSyncDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.center.datasource.bo.impl
 * @Class_name: CenterDatasourceBOImpl
 * @Describe: 医院数据源Bo实现
 * @Author: Ou·Mr
 * @Email: oubo@powersi.com.cn
 * @Date: 2020/7/30 21:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
public class CenterDatasourceBOImpl extends HsafBO implements CenterDatasourceBO {

    @Resource
    private CenterDatasourceDAO centerDatasourceDAO;

    @Resource
    private CenterHospitalDatasourceDAO centerHospitalDatasourceDAO;

    private final Pattern PATTERN = Pattern.compile(".+?(\\{.+?\\})");

    private static final String msgToastSuffix = "天后过期，为避免过期后给您工作带来的不便，请及时续费。客服热线：400 987 5000。";
    private static final Logger log = LoggerFactory.getLogger(CenterDatasourceBOImpl.class);

    /**
     * @Menthod queryCenterHospitalDatasourceAll
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return List<CenterDatasourceDTO>
     */
    @Override
    public List<CenterDatasourceDTO> getCenterHospitalDatasource(String hospCode) {
        return centerDatasourceDAO.getCenterHospitalDatasource(hospCode);
    }

    /**
     * @Menthod queryCenterHospitalDatasourceAll
     * @Desrciption  查询医院数据源集合
     * @param
     * @Author Ou·Mr
     * @Date   2020/7/20 19:15
     * @Return List<CenterDatasourceDTO>
     */
    @Override
    public List<CenterDatasourceDTO> queryCenterHospitalDatasourceAll() {
        return centerDatasourceDAO.queryCenterHospitalDatasourceAll();
    }

    /**
     * @Menthod addCenterDatasource
     * @Desrciption
     * @param centerDatasourceDO 新增数据源
     * @Author Ou·Mr
     * @Date 2020/8/4 13:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse addCenterDatasource(CenterDatasourceDO centerDatasourceDO) {
        centerDatasourceDO.setId(SnowflakeUtils.getId());//id
        centerDatasourceDO.setCrteTime(new Date());//创建时间
        centerDatasourceDAO.insert(centerDatasourceDO);
        return WrapperResponse.success("操作成功。",null);
    }

    /**
     * @Menthod editCenterDatasource
     * @Desrciption 编辑数据源
     * @param centerDatasourceDO 编辑数据源参数
     * @Author Ou·Mr
     * @Date 2020/8/4 9:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse editCenterDatasource(CenterDatasourceDO centerDatasourceDO) {
        centerDatasourceDAO.updateByPrimaryKeySelective(centerDatasourceDO);
        return WrapperResponse.success("操作成功。",null);
    }

    /**
     * @Menthod queryCenterDatasource
     * @Desrciption 分页查询数据源信息
     * @param centerDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 9:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 分页结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryCenterDatasourcePage(CenterDatasourceDTO centerDatasourceDTO) {
        PageHelper.startPage(centerDatasourceDTO.getPageNo(),centerDatasourceDTO.getPageSize());
        List<CenterDatasourceDTO> centerDatasourceDTOList = centerDatasourceDAO.findByCondition(centerDatasourceDTO);
        return WrapperResponse.success(PageDTO.of(centerDatasourceDTOList));
    }

    /**
     * @Menthod queryHospCodePage
     * @Desrciption 分页查询医院机构信息
     * @param centerHospitalDatasourceDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/4 10:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryHospCodePage(CenterHospitalDatasourceDTO centerHospitalDatasourceDTO) {
        PageHelper.startPage(centerHospitalDatasourceDTO.getPageNo(),centerHospitalDatasourceDTO.getPageSize());
        List<CenterHospitalDatasourceDTO> centerHospitalDatasourceDTOList = centerHospitalDatasourceDAO.findByCondition(centerHospitalDatasourceDTO);
        return WrapperResponse.success(PageDTO.of(centerHospitalDatasourceDTOList));
    }

    /**
     * @Menthod delCenterDatasource
     * @Desrciption 删除数据源
     * @param ids 数据源ids
     * @Author Ou·Mr
     * @Date 2020/8/4 10:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse delCenterDatasource(String ids) {
        centerDatasourceDAO.deleteByIds(ids.split(","));
        return WrapperResponse.success("删除成功。",null);
    }

    /**
     * @Menthod delDatasourceHospCode
     * @Desrciption 删除数据源关联的医院信息
     * @param id 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse delHospitalDatasource(String id) {
        centerHospitalDatasourceDAO.deleteById(id);
        return WrapperResponse.success("操作成功。",null);
    }

    /**
     * @Menthod addHospitalDatasource
     * @Desrciption 新增医院数据源关系
     * @param centerDatasourceDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/4 11:52
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 操作结果
     */
    @Override
    public WrapperResponse addHospitalDatasource(CenterDatasourceDTO centerDatasourceDTO) {
        String addDelFlag = centerDatasourceDTO.getAddDelFlag();
        String dsCode = centerDatasourceDTO.getCode();
        List<CenterHospitalDatasourceDO> list = centerDatasourceDTO.getCenterHospitalDatasourceDOS();
        if(null != list && !list.isEmpty()){ //新增
            list = centerDatasourceDTO.getCenterHospitalDatasourceDOS();
            if(ListUtils.isEmpty(list)){
                throw new AppException("请选择医院");
            }
            Map map = new HashMap<>();
            map.put("list", list);
            List<CenterHospitalDatasourceDTO> centerHospitalDatasourceList = centerHospitalDatasourceDAO.queryHaveHospCode(map);
            if(!ListUtils.isEmpty(centerHospitalDatasourceList)){
                for (CenterHospitalDatasourceDTO dto: centerHospitalDatasourceList) {
                    if(!dto.getDsCode().equals(dsCode)){
                        throw new AppException("存在医院已经绑定数据源");
                    }
                }
            }
        }
        centerHospitalDatasourceDAO.delByDsCode(centerDatasourceDTO.getCode());//删除医院和数据源关系信息
        if (!centerDatasourceDTO.getCenterHospitalDatasourceDOS().isEmpty()
                && centerDatasourceDTO.getCenterHospitalDatasourceDOS().size() > 0){
            centerHospitalDatasourceDAO.batchInsert(centerDatasourceDTO.getCenterHospitalDatasourceDOS());//批量新增数据源关系信息
        }
        return WrapperResponse.success("操作成功。",null);
    }

    /**
     * @Menthod queryCenterHospitalPage
     * @Desrciption  分页查询医院信息
     * @param centerHospitalDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/5 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryCenterHospitalPage(CenterHospitalDTO centerHospitalDTO) {
        PageHelper.startPage(centerHospitalDTO.getPageNo(),centerHospitalDTO.getPageSize());
        List<CenterHospitalDTO> centerHospitalDTOList = centerHospitalDatasourceDAO.queryCenterHospitalAll(centerHospitalDTO);
        return WrapperResponse.success(PageDTO.of(centerHospitalDTOList));
    }

    /**
     * @param ids ID集合
     * @Menthod deleteCenterHospital
     * @Desrciption 删除数据源绑定的信息
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse deleteCenterHospital(String ids) {
        if(StringUtils.isEmpty(ids)){
            throw new  AppException("未获取到相关医院配置数据!");
        }
        centerHospitalDatasourceDAO.deleteCenterHospital(ids.split(","));
        return WrapperResponse.success("删除成功!",null);
    }

    /**
     * @param tableStructureSyncDTO
     * @Menthod tableStructureSync
     * @Desrciption 根据所选数据源列表 对数据进行字段同步
     * @Author pengbo
     * @Date 2021 01 06 19:27
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO> 结果集
     */
    @Override
    public WrapperResponse tableStructureSync(TableStructureSyncDTO tableStructureSyncDTO) {
        if (tableStructureSyncDTO == null){
            throw new  AppException("未获取到需要同步的字段信息!");
        }
        if (ListUtils.isEmpty(tableStructureSyncDTO.getDataSourceIds())){
            throw new  AppException("未获取到需要同步表结构的数据库!");
        }
        if (StringUtils.isEmpty(tableStructureSyncDTO.getSql())){
            throw new  AppException("未获取到需要同步表结构的脚本!");
        }
        List<CenterDatasourceDTO> list = new ArrayList<CenterDatasourceDTO>();
        if ("0".equals(tableStructureSyncDTO.getSyncType())){
            list = centerDatasourceDAO.findCenterDataSourceByIds(tableStructureSyncDTO.getDataSourceIds());
        }else {
            list = centerDatasourceDAO.findHospitalCenterDataSourceByIds(tableStructureSyncDTO.getDataSourceIds());
        }


        if (ListUtils.isEmpty(list)){
            throw new  AppException("未获取到需要同步表结构的数据库!");
        }
        List<String> dataSoureIds = new ArrayList<>();
        String message = "";
        for (CenterDatasourceDTO centerDatasourceDTO:list){
            try{
                tableStructureSyncByHospCode(centerDatasourceDTO,tableStructureSyncDTO,dataSoureIds);
            }catch(Exception e){
                    if(message.length()>0){
                        message +=  ","+ e.getMessage();
                    }else{
                        message = e.getMessage();
                    }
            }
        }
        if(message.length()>0){
            return WrapperResponse.error(-1,"["+message+"]同步失败!", null);
        }else{
            return WrapperResponse.success("同步表结构成功!", null);
        }
    }

    /**
     * @Description: 更新版本更新的确认标识
     * @Param: []
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    @Override
    public WrapperResponse updateIsGuide() {
        List<CenterDatasourceDTO> list = new ArrayList<CenterDatasourceDTO>();
        // 查询所有的数据库
        list = centerDatasourceDAO.findHospitalCenterDataSource();
        // 同步所有的数据库的更新公告标识
        TableStructureSyncDTO tableStructureSyncDTO = new TableStructureSyncDTO();
        // 设置更新公告已读标识
        tableStructureSyncDTO.setSql("update sys_user set is_guide = '2'");
        // 设置为更新表数据
        tableStructureSyncDTO.setSyncType("1");
        List<String> dataSoureIds = new ArrayList<>();
        String message = "";
        for (CenterDatasourceDTO centerDatasourceDTO:list){
            try{
                tableStructureSyncByHospCode(centerDatasourceDTO,tableStructureSyncDTO,dataSoureIds);
            }catch(Exception e){
                if(message.length()>0){
                    message +=  ","+ e.getMessage();
                }else{
                    message = e.getMessage();
                }
            }
        }
        return WrapperResponse.success("同步成功!", null);
    }

    @Override
    public void addDataUser(CenterHospitalDTO centerHospitalDTO) {
        centerDatasourceDAO.addDataUser(centerHospitalDTO);
    }

    //根据数据库配置操作表
    private void tableStructureSyncByHospCode(CenterDatasourceDTO centerDatasourceDTO, TableStructureSyncDTO tableStructureSyncDTO,List<String> dataSoureIds) {
        if(centerDatasourceDTO == null ){
            throw new  AppException("同步表结构未获取到数据库");
        }

        /**
         * 获取医院配置的数据库连接
         */
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtils.getConnection(centerDatasourceDTO.getDriverName(), centerDatasourceDTO.getUrl(), centerDatasourceDTO.getUsername(),
                    centerDatasourceDTO.getPassword());
            String sql = tableStructureSyncDTO.getSql() ;
            // 表结构同步时，需要判断 0：表结构同步，1：表数据同步
            if("0".equals(tableStructureSyncDTO.getSyncType())){
                if(dataSoureIds.contains(connection.getCatalog())){
                    return;
                }
            }else{
                Matcher matcher = PATTERN.matcher(sql);
                while(matcher.find()) {
                    String key = matcher.group(1).replaceAll("\\{","").replace("}", "");
                    if("id".equals(key.toLowerCase())){
                        sql = sql.replaceAll("\\{"+key+"}","'" + SnowflakeUtils.getId() + "'");
                    }else{
                        sql = sql.replaceAll("\\{"+key+"}","'" + centerDatasourceDTO.getHospCode() + "'");
                    }
                }
            }
            log.error("this is not an error msg, just for debug, sql parsed is：{}",sql);
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            connection.commit();
            dataSoureIds.add(connection.getCatalog());
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.error("table struct or data synchronize occurred error,the detail is",e);
            throw new  AppException(centerDatasourceDTO.getName());
        }
        finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Description: 导出医院数据（SaaS导出）
     * @Param: []
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     * @Author: liuliyun
     * @Date: 2022-05-05
     */
    @Override
    public WrapperResponse exportHospitalData(String startDate,String endDate) {
        List<CenterDatasourceDTO> list = new ArrayList<CenterDatasourceDTO>();
        // 查询所有的数据库
        list = centerDatasourceDAO.findHospitalCenterDataSource();
        // 同步所有的数据库的更新公告标识
        TableStructureSyncDTO tableStructureSyncDTO = new TableStructureSyncDTO();
        // 设置更新公告已读标识
        tableStructureSyncDTO.setSql("select " +
                "(select name from sys_code_detail where value=c.pro_code and c_code='XZQH'and hosp_code={hospCode}) as 'pro'," +
                "(select name from sys_code_detail where value=c.city_code and c_code='XZQH'and hosp_code={hospCode}) as 'city'," +
                "(select name from sys_code_detail where value=c.area_code and c_code='XZQH' and hosp_code={hospCode}) as 'area'," +
                "'云His' as 'proName'," +
                "c.nation_code as 'nationCode'," +
                "c.name as 'hospitalName'," +
                "c.admin_name as 'contactName'," +
                "c.admin_phone as 'phone'," +
                "c.`code` as 'hospCode'," +
                "(select count(*) from insure_individual_settle a where a.settle_state='1' and a.state ='0') as totalInsureNum,"+
                "(select count(*) from (select a.id from inpt_visit a "+
                "JOIN inpt_settle b on a.id = b.visit_id and a.hosp_code =b.hosp_code "+
                "where b.is_settle='1' and a.patient_code='0' and b.status_code ='0' "+
                "union all "+
                "select a.id from outpt_visit a "+
                "JOIN outpt_settle b on a.id = b.visit_id and a.hosp_code =b.hosp_code "+
                "where b.is_settle='1' and a.patient_code='0' and b.status_code ='0')aa)  as totalInptNum,"+
                "ifnull(sum(ff.insureCount),0) as 'insureCount'," +
                "ifnull(sum(ff.inptCount),0) as 'inptCount'," +
                "c.start_date as 'startDate'," +
                "c.end_date as 'endDate'" +
                " from dhis_center.center_hospital c left JOIN (" +
                "select a.hosp_code as hospCode,COUNT(a.id) insureCount,0 as inptCount,MIN(a.crte_time) as startTime,MAX(a.crte_time) as endTime\n" +
                "from insure_individual_settle a\n" +
                "where a.settle_state='1' and a.state ='0' and a.crte_time >='" +startDate+"'"+ " and a.crte_time <='" +endDate+"' "+
                "union all \n" +
                "select a.hosp_code as hospCode,0 as  insureCount, COUNT(*) as inptCount,MIN(b.crte_time) as startTime,MAX(b.crte_time) as endTime\n" +
                "from inpt_visit a\n" +
                "JOIN inpt_settle b on a.id = b.visit_id and a.hosp_code =b.hosp_code\n" +
                "where b.is_settle='1' and a.patient_code='0' and b.status_code ='0' and b.settle_time  >='" +startDate+"'"+ " and b.settle_time <='" +endDate+"'"+
                "union all \n" +
                "select a.hosp_code as hospCode,0 as insureCount, COUNT(*) as inptCount,MIN(b.crte_time) as startTime,MAX(b.crte_time) as endTime\n" +
                "from outpt_visit a\n" +
                "JOIN outpt_settle b on a.id = b.visit_id and a.hosp_code =b.hosp_code\n" +
                "where b.is_settle='1' and a.patient_code='0' and b.status_code ='0' and b.settle_time  >='" +startDate+"'"+ " and b.settle_time <='" +endDate+"'"+
                ") ff on c.`code`=ff.hospCode" +
                " where c.`code` ={hospCode}");

        List<String> dataSoureIds = new ArrayList<>();
        String message = "";
        List<Map<String,Object>> dataList=new ArrayList<>();
        for (CenterDatasourceDTO centerDatasourceDTO:list){
            try{
                List<Map<String,Object>> data=tableStructureExportDataByHospCode(centerDatasourceDTO,tableStructureSyncDTO,dataSoureIds);
                dataList.addAll(data);
            }catch(Exception e){
                if(message.length()>0){
                    message +=  ","+ e.getMessage();
                }else{
                    message = e.getMessage();
                }
            }
        }
        return WrapperResponse.success("导出成功!", PageDTO.of(dataList));
    }

    @Override
    public WrapperResponse<Map<String, Object>> getHospServiceStatsByCode(CenterHospitalDTO centerHospitalDTO) {
        Map<String,Object> resultMap = new HashMap<>();
        long millsOf15Days = 15 * 24 * 3600 * 1000;

        CenterHospitalDTO centerHospitalDTO1 = centerHospitalDatasourceDAO.getHospServiceStatsByCode(centerHospitalDTO);
        long millsOfEndDate = centerHospitalDTO1.getEndDate().getTime();
        Date now = new Date();
        if(millsOfEndDate - now.getTime() > millsOf15Days){
            return WrapperResponse.success(resultMap);
        }
       int daysLeft =  DateUtils.differentDays(now,centerHospitalDTO1.getEndDate());
       String toastMsg = "系统将于"+daysLeft+msgToastSuffix;
       resultMap.put("toastMsg",toastMsg);
        return WrapperResponse.success(resultMap);
    }

    //根据数据库配置操作表
    private List tableStructureExportDataByHospCode(CenterDatasourceDTO centerDatasourceDTO, TableStructureSyncDTO tableStructureSyncDTO,List<String> dataSoureIds) {
        if(centerDatasourceDTO == null ){
            throw new  AppException("执行导出未获取到数据库");
        }
        List<Map<String,Object>> data=new ArrayList<>();
        /**
         * 获取医院配置的数据库连接
         */
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtils.getConnection(centerDatasourceDTO.getDriverName(), centerDatasourceDTO.getUrl(), centerDatasourceDTO.getUsername(),
                    centerDatasourceDTO.getPassword());
            String sql = tableStructureSyncDTO.getSql() ;
            Matcher matcher = PATTERN.matcher(sql);
            while(matcher.find()) {
                String key = matcher.group(1).replaceAll("\\{","").replace("}", "");
                if("id".equals(key.toLowerCase())){
                    sql = sql.replaceAll("\\{"+key+"}","'" + SnowflakeUtils.getId() + "'");
                }else{
                    sql = sql.replaceAll("\\{"+key+"}","'" + centerDatasourceDTO.getHospCode() + "'");
                }
            }
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            data = convertList(result);
            connection.commit();
            dataSoureIds.add(connection.getCatalog());
        }catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new  AppException(centerDatasourceDTO.getName());
        }
        finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**
     * @Description: 将ResultSet结果集转为list
     * @Param: rs
     * @return: list
     * @Author: liuliyun
     * @Date: 2022-05-05
     */
    private List<Map<String,Object>> convertList(ResultSet rs) throws SQLException {

        List list = new ArrayList();

        ResultSetMetaData md = rs.getMetaData();

        int columnCount = md.getColumnCount();

        while (rs.next()) {

            Map rowData = new LinkedHashMap();

            for (int i = 1; i <= columnCount; i++) {

                rowData.put(md.getColumnName(i), rs.getObject(i));

            }

            list.add(rowData);

        } return list;
    }
}
