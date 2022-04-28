package cn.hsa.center.sync.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.datasource.dao.CenterDatasourceDAO;
import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.sync.bo.CenterSyncBo;
import cn.hsa.module.center.sync.dao.CenterHospitalSyncDao;
import cn.hsa.module.center.sync.dao.CenterSyncDao;
import cn.hsa.module.center.sync.dto.CenterHospitalSyncDTO;
import cn.hsa.module.center.sync.dto.CenterSyncDTO;
import cn.hsa.module.center.sync.entity.CenterHospitalSyncDO;
import cn.hsa.module.center.sync.entity.CenterSyncDO;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.center.sync.bo.impl
 * @Class_name: CenterSyncBOImpl
 * @Describe(描述): 医院数据同步表Bo 实现层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/05 8:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class CenterSyncBOImpl implements CenterSyncBo {

    @Value("${sql.script.path}")
    private String sqlScriptPath;
    @Resource
    private CenterSyncDao centerSyncDao;
    @Resource
    private CenterDatasourceDAO centerDatasourceDao;
    @Resource
    private CenterHospitalSyncDao centerHospitalSyncDao;
    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    /**
     * @param record 新增参数
     * @Menthod insert
     * @Desrciption 新增医院数据同步
     * @Author Ou·Mr
     * @Date 2020/7/20 19:15
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse insert(CenterSyncDTO record) {
        List<CenterSyncDTO> dto = centerSyncDao.getCenterSyncByTableNameOrCode(record);
        if (!dto.isEmpty()){
            throw new AppException("同步编码或表名重复!");
        }
        record.setId(SnowflakeUtils.getId());
        record.setCrteTime(new Date());
        record.setIsValid(Constants.SF.S);
        centerSyncDao.insert(record);
        return WrapperResponse.success("操作成功。", null);
    }

    /**
     * @param record 新增参数
     * @Menthod insertSelective
     * @Desrciption 新增医院数据同步
     * @Author Ou·Mr
     * @Date 2020/7/20 19:15
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse insertSelective(CenterSyncDO record) {
        //定义查询条件
        CenterSyncDTO centerSyncDTO = new CenterSyncDTO();
        //表名，校验表名是否唯一
        centerSyncDTO.setTableNameField(record.getTableName());
        List<CenterSyncDTO> centerSyncDTOList = centerSyncDao.findByCondition(centerSyncDTO);
        if (centerSyncDTOList.isEmpty()) {
            return WrapperResponse.fail("同步编码【" + record.getTableName() + "】已存在", null);
        }
        record.setId(SnowflakeUtils.getId());
        record.setSyncCode(SnowflakeUtils.getId());
        record.setCrteTime(new Date());
        record.setIsValid(Constants.SF.S);
        centerSyncDao.insertSelective(record);
        return WrapperResponse.success("操作成功。", null);
    }

    /**
     * @param centerSyncDTO 查询条件
     * @Menthod findByCondition
     * @Desrciption 查询医院数据同步表列表数据
     * @Author Ou·Mr
     * @Date 2020/8/4 22:56
     * @Return WrapperResponse 结果集
     */
    @Override
    public WrapperResponse<PageDTO> queryCenterSyncTablePage(CenterSyncDTO centerSyncDTO) {
        PageHelper.startPage(centerSyncDTO.getPageNo(), centerSyncDTO.getPageSize());
        List<CenterSyncDTO> centerSyncDTOList = centerSyncDao.findByCondition(centerSyncDTO);
        return WrapperResponse.success(PageDTO.of(centerSyncDTOList));
    }

    /**
     * @param id 主键id
     * @Menthod deleteById
     * @Desrciption 根据主键删除同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse deleteById(String id) {
        int num = centerSyncDao.deleteById(id);
        return num == 1 ? WrapperResponse.success("删除成功。", null) : WrapperResponse.fail("未查找到删除信息。", null);
    }

    /**
     * @param ids ids
     * @Menthod deleteByIds
     * @Desrciption 根据主键批量删除同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 22:57
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse deleteByIds(String ids) {
        List<String> idslist = Arrays.asList(ids.split(","));
        if (idslist.isEmpty()) {
            throw new AppException("未获取到需要删除的数据!");
        }
        List<CenterSyncDTO> boundHospIdList = centerSyncDao.queryBoundHospSync(ids.split(","), Constants.SF.S);
        if (boundHospIdList != null && !boundHospIdList.isEmpty()){
            String names = String.join(",",boundHospIdList.stream().map(CenterSyncDTO::getTableName).collect(Collectors.toList()));
            throw new AppException(names+"已经绑定了医院,不能删除!");
        }
        centerSyncDao.editCenterSyncIsValidByids(idslist.toArray(new String[idslist.size()]), Constants.SF.F);

        return WrapperResponse.success("删除成功!", null);
    }


    /**
     * @param centerSyncDTO 同步表信息
     * @Menthod updateByPrimaryKeySelective
     * @Desrciption 修改表同步表信息（字段为null不进行修改）
     * @Author Ou·Mr
     * @Date 2020/8/4 22:58
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse updateByPrimaryKeySelective(CenterSyncDTO centerSyncDTO) {
        List<CenterSyncDTO> dto = centerSyncDao.getCenterSyncByTableNameOrCode(centerSyncDTO);
        if (!dto.isEmpty()){
            throw new AppException("同步编码或表名重复!");
        }

        int num = centerSyncDao.updateByPrimaryKeySelective(centerSyncDTO);
        return num > 0 ? WrapperResponse.success("操作成功。", null) : WrapperResponse.fail("未查找到编辑信息。", null);
    }

    /**
     * @param centerSyncDTO 同步表信息
     * @Menthod updateByPrimaryKey
     * @Desrciption 修改同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 23:00
     * @Return WrapperResponse 成功/失败
     */
    @Override
    public WrapperResponse updateByPrimaryKey(CenterSyncDTO centerSyncDTO) {

        List<CenterSyncDTO> dto = centerSyncDao.getCenterSyncByTableNameOrCode(centerSyncDTO);
        if (!dto.isEmpty()){
            throw new AppException("同步编码或表名重复!");
        }

        int num = centerSyncDao.updateByPrimaryKey(centerSyncDTO);
        return num > 0 ? WrapperResponse.success("操作成功。", null) : WrapperResponse.fail("未查找到编辑信息。", null);
    }

    /**
     * @param id 主键id
     * @Menthod selectByPrimaryKey
     * @Desrciption 根据主键查询同步表信息
     * @Author Ou·Mr
     * @Date 2020/8/4 23:00
     * @Return WrapperResponse 结果集
     */
    @Override
    public WrapperResponse selectByPrimaryKey(String id) {
        CenterSyncDO centerSyncDO = centerSyncDao.selectByPrimaryKey(id);
        return WrapperResponse.success(centerSyncDO);
    }

    @Override
    public WrapperResponse saveSyncHospData(CenterSyncDTO centerSyncDTO) {
        String hospCode = centerSyncDTO.getNewHospCode();

        //根据医院编码查询出对应的数据源
        List<CenterDatasourceDTO> datasourceList = centerDatasourceDao.getDataSourceByHosp(centerSyncDTO);
        if (ListUtils.isEmpty(datasourceList)) {
            throw new AppException("该医院没有配置数据源");
        }
        CenterDatasourceDTO centerDatasourceDTO = datasourceList.get(0);
        //创建数据源表
        DBUtils.initScript(centerDatasourceDTO.getDriverName(), centerDatasourceDTO.getUrl(), centerDatasourceDTO.getUsername(),
                centerDatasourceDTO.getPassword(), sqlScriptPath);

        //切换到对应数据源，然后根据医院编码查询出需要同步的表
        List<CenterSyncDTO> syncTableList = centerSyncDTO.getList();

        List<Map<String,Object>> listTableSync = centerSyncDao.getCenterTableContrast();
        if (listTableSync.isEmpty()){
            throw new AppException("请配置同步表的目标表!");
        }


        Map<String,Object> syncTable = new HashMap<String,Object>();
        String key = "";
        String val = "";
        for(Map<String,Object> map:listTableSync){
            key = MapUtils.getVS(map,"table_name","");
            val = MapUtils.getVS(map,"sync_table_name","");
            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(val)){
                syncTable.put(key,val);
            }
        }

        /**
         * 获取医院配置的数据库连接
         */
        Connection connection = null;
        String tableName = null;
        //用户集合
        List<Map<String,Object>> systemList = new ArrayList<>();
        //部门集合
        List<Map<String,Object>> roleList = new ArrayList<>();
        try {
            connection = DBUtils.getConnection(centerDatasourceDTO.getDriverName(), centerDatasourceDTO.getUrl(), centerDatasourceDTO.getUsername(),
                    centerDatasourceDTO.getPassword());
            connection.setAutoCommit(false);
            /**
             * 同步需要同步的数据
             */
            List<Map<String,Object>> dataList = null;
            for (CenterSyncDTO centerSyncDTOTem : syncTableList) {
                tableName = centerSyncDTOTem.getTableName().toLowerCase();
                //查询同步的表数据
                dataList = centerSyncDao.getSyncDataByTableName(centerSyncDTOTem);
                if ("sync_system".equals(tableName)){
                    systemList = dataList;
                }
                if ("sync_role".equals(tableName)){
                    roleList = dataList;
                }
                //设置ID，HOSP_CODE
                dataList.forEach(dto -> {
                    dto.put("id", SnowflakeUtils.getId());
                    dto.put("hosp_code", hospCode);
                });
                //批量插入目标数据库（医院绑定的数据源）
                batchInsertMysqlData(connection, dataList, (String) syncTable.get(tableName));
            }
            /**
             * 手动创建超级管理员账号，并赋予管理员权限
             */
            //用户集合
            List<Map<String,Object>> userList = new ArrayList<>();
            //部门集合
            List<Map<String,Object>> deptList = new ArrayList<>();
            //用户子系统权限集合
            List<Map<String,Object>> userSystemList = new ArrayList<>();
            //用户角色集合
            List<Map<String,Object>> userRoleList = new ArrayList<>();
            String deptCode = Constants.CENTERSYNCKSCODE.DEPT_CODE;
            Map userMap = new HashMap();
            String userCode = "admin";
            userMap.put("id", SnowflakeUtils.getId());
            userMap.put("hosp_code", hospCode);
            userMap.put("dept_code", deptCode);
            userMap.put("code", userCode);
            userMap.put("password", MD5Utils.getMd5AndSha("888888"));
            userMap.put("is_in_job", "1");
            userMap.put("status_code","0");
            userMap.put("name", "管理员");
            userList.add(userMap);

            Map deptMap = new HashMap();
            deptMap.put("id", SnowflakeUtils.getId());
            deptMap.put("hosp_code", hospCode);
            deptMap.put("type_code", "16");
            deptMap.put("code", deptCode);
            deptMap.put("name", Constants.CENTERSYNCKSCODE.DEPT_NAME);
            deptMap.put("is_valid", "1");
            deptList.add(deptMap);

            for (Map<String,Object> map : systemList) {
                Map userSystemMap = new HashMap();
                Map orderMap = new HashMap();
                orderMap.put("hospCode", hospCode);
                orderMap.put("typeCode", "15");
                String us_code = SnowflakeUtils.getId();
                userSystemMap.put("id", SnowflakeUtils.getId());
                userSystemMap.put("hosp_code", hospCode);
                userSystemMap.put("us_code", us_code);
                userSystemMap.put("user_code", userCode);
                userSystemMap.put("system_code", map.get("code"));
                userSystemMap.put("dept_code", deptCode);
                userSystemMap.put("crte_id", centerSyncDTO.getCrteId());
                userSystemMap.put("crte_name", centerSyncDTO.getCrteName());
                userSystemMap.put("crte_time", new Date());
                userSystemList.add(userSystemMap);
            }
            for (Map<String,Object> userSystemMap : userSystemList) {
                for (Map<String,Object> map : roleList) {
                    Map userRoleMap = new HashMap();
                    userRoleMap.put("id", SnowflakeUtils.getId());
                    userRoleMap.put("hosp_code", hospCode);
                    userRoleMap.put("us_code", userSystemMap.get("us_code"));
                    userRoleMap.put("role_code", map.get("code"));
                    userRoleMap.put("crte_id", centerSyncDTO.getCrteId());
                    userRoleMap.put("crte_name", centerSyncDTO.getCrteName());
                    userRoleMap.put("crte_time", new Date());
                    userRoleList.add(userRoleMap);
                }
            }

            batchInsertMysqlData(connection, userList, "sys_user");
            batchInsertMysqlData(connection, deptList, "base_dept");
            batchInsertMysqlData(connection, userSystemList, "sys_user_system");
            batchInsertMysqlData(connection, userRoleList, "sys_user_role");
        } catch (Exception e) {
            throw new AppException("同步表【"+tableName+"】数据失败,原因："+e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return WrapperResponse.success("操作成功!", null);
    }

    /**
     * @param centerSyncDTO 查询条件
     * @Menthod queryCenterSyncTableList
     * @Desrciption 获取关联同步表信息
     * @Author Ou·Mr
     * @Date 2020/9/1 11:40
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryCenterSyncTableList(CenterSyncDTO centerSyncDTO) {
        return WrapperResponse.success(centerSyncDao.queryCenterSyncTableList(centerSyncDTO));
    }

    /**
     * @param centerHospitalSyncDO 请求参数
     * @Menthod queryCenterHospitalSyncList
     * @Desrciption 查询医院所同步的表
     * @Author Ou·Mr
     * @Date 2020/9/1 20:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryCenterHospitalSyncList(CenterHospitalSyncDO centerHospitalSyncDO) {
        return WrapperResponse.success(centerHospitalSyncDao.queryHospitalSyncList(centerHospitalSyncDO));
    }

    /**
     * @param centerHospitalSyncDTO 请求参数
     * @Menthod saveHospitalSync
     * @Desrciption 保存医院同步表信息
     * @Author Ou·Mr
     * @Date 2020/9/2 8:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse saveHospitalSync(CenterHospitalSyncDTO centerHospitalSyncDTO) {

        CenterHospitalSyncDO centerHospitalSyncDOQuery = new CenterHospitalSyncDO();
        centerHospitalSyncDOQuery.setHospCode(centerHospitalSyncDTO.getHospCode());
        //查询医院是否同步过
        List<CenterHospitalSyncDO> centerHospitalSyncList = centerHospitalSyncDao.queryHospitalSyncList(centerHospitalSyncDOQuery);
        if (!ListUtils.isEmpty(centerHospitalSyncList)) {
            throw new AppException("该医院已经下发过数据");
        }

        //先下发数据,成功之后再插入下发记录
        List<CenterSyncDTO> centerSyncList = centerHospitalSyncDTO.getCenterSyncDOList();
        CenterSyncDTO centerSyncDTO = new CenterSyncDTO();
        centerSyncDTO.setNewHospCode(centerHospitalSyncDTO.getHospCode());
        centerSyncDTO.setList(centerSyncList);
        //下发数据
        saveSyncHospData(centerSyncDTO);

        //保存下发记录
        List<CenterHospitalSyncDO> centerHospitalSyncDOList = new ArrayList<CenterHospitalSyncDO>();
        centerSyncList.stream().forEach(centerSync -> {
            CenterHospitalSyncDO centerHospitalSyncDO = new CenterHospitalSyncDO();
            centerHospitalSyncDO.setId(SnowflakeUtils.getId());//id
            centerHospitalSyncDO.setHospCode(centerHospitalSyncDTO.getHospCode());//医院编码
            centerHospitalSyncDO.setSyncCode(centerSync.getSyncCode());//同步编码
            centerHospitalSyncDO.setTypeCode(Constants.SYNCCODE.QL);//同步类型： 默认全量
            centerHospitalSyncDO.setCrteId(centerHospitalSyncDTO.getCrteId());//创建人id
            centerHospitalSyncDO.setCrteName(centerHospitalSyncDTO.getCrteName());//创建人姓名
            centerHospitalSyncDO.setCrteTime(new Date());//创建时间
            centerHospitalSyncDOList.add(centerHospitalSyncDO);
        });
        //删除当前医院绑定的同步表信息
        centerHospitalSyncDao.delHospitalSyncByHospCode(centerHospitalSyncDTO.getHospCode().split(","));
        centerHospitalSyncDao.batchInsert(centerHospitalSyncDOList);

        return WrapperResponse.success("数据下发成功!", null);
    }

    /**
     * @return
     * @备注 查询所有需要同步的表名
     * @创建者 pengbo
     * @创建时间 2021-01-11
     * @修改者 pengbo
     */
    @Override
    public WrapperResponse getAllNeedSyncTableName() {
        return WrapperResponse.success(centerSyncDao.getAllNeedSyncTableName());
    }

    public void batchInsertMysqlData(Connection connection, List<Map<String,Object>> list, String tableName) throws Exception {
        if(ListUtils.isEmpty(list)){
            return;
        }

        connection.setAutoCommit(false);
        StringBuffer table = new StringBuffer();
        List<String> zdValList = new ArrayList<>();
        Map<String, Object> map = list.get(0);
        if ("sys_role_menu_button".equals(tableName)) {
            map.put("button_code", null);
        }
        map.forEach((k, v) -> {
            if (!k.contains("old")) {
                table.append(String.valueOf(k)).append(",");
                zdValList.add(String.valueOf(k));
            }
        });
        String tableTem = table.substring(0, table.length() - 1);
        String insertSql = "insert INTO " + tableName + " ( " + tableTem + " ) " + " VALUES (";
        for (int i = 0; i < zdValList.size(); i++) {
            if (i != zdValList.size() - 1) {
                insertSql += "?,";
            } else {
                insertSql += "?)";
            }
        }
        PreparedStatement cmd = connection.prepareStatement(insertSql);
        for (Map<String, Object> re : list) {
            for (int i = 1; i < zdValList.size() + 1; i++) {
                String zdVal = zdValList.get(i - 1);
                Object obj = re.get(zdVal.toLowerCase());
                cmd.setObject(i, obj);
                //每1000次提交一次
                if (i != 0 && i % 100 == 0) {
                    cmd.executeBatch();
                    connection.commit();
                    cmd.clearBatch();
                }
            }
            cmd.addBatch();
        }
        cmd.executeBatch();
        connection.commit();
        cmd.close();
    }
}
