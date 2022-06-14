package cn.hsa.clinical.clinicalpathitem.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.clinical.clinicalpathitem.bo.ClinicalPathItemBO;
import cn.hsa.module.clinical.clinicalpathitem.dao.ClinicalPathItemDAO;
import cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;



import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ClinicalPathItemBOImpl implements ClinicalPathItemBO {
    @Resource
    private ClinicalPathItemDAO clinicalPathItemDAO;
    @Resource
    private BaseOrderRuleService baseOrderRuleService;
    /**
     * 获取码表的服务层接口
     */
    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Description: 查询满足条件的临床路径
     * @Param: [queryDTO]
     * @return: java.util.List<cn.hsa.module.clinical.clinicalpathitem.dto.ClinicalPathItemDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public PageDTO queryAll(ClinicalPathItemDTO queryDTO) {
        Optional.ofNullable(queryDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        PageHelper.startPage(queryDTO.getPageNo(), queryDTO.getPageSize());
        return PageDTO.of(clinicalPathItemDAO.queryAll(queryDTO));
    }

    /**
     * @Meth:updateOrAddPathItem
     * @Description: 更新或者添加临床项目
     * @Param: [clinicalPathItemDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public Boolean updateOrAddPathItem(ClinicalPathItemDTO clinicalPathItemDTO) {
        Optional.ofNullable(clinicalPathItemDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        // 校验项目编码
        int i = clinicalPathItemDAO.queryByCode(clinicalPathItemDTO);
        if (i > 0) {
            throw new AppException("项目编码不能重复");
        }
        if (StringUtils.isEmpty(clinicalPathItemDTO.getId())) { // id为空 说明为添加
            // 生成主键
            String id = SnowflakeUtils.getId();
            clinicalPathItemDTO.setId(id);
            // 生成拼音码
            if (StringUtils.isEmpty(clinicalPathItemDTO.getPym())) {
                clinicalPathItemDTO.setPym(PinYinUtils.toFirstPY(clinicalPathItemDTO.getName()));
            }
            // 生成五笔码
            if (StringUtils.isEmpty(clinicalPathItemDTO.getWbm())) {
                clinicalPathItemDTO.setWbm(WuBiUtils.getWBCode(clinicalPathItemDTO.getName()));
            }
            String code = getCode(clinicalPathItemDTO.getHospCode());
            // 新增赋值编码
            clinicalPathItemDTO.setCode(code);
            // 创建时间
            clinicalPathItemDTO.setCrteTime(DateUtils.getNow());
            // 默认有效
            clinicalPathItemDTO.setIsValid(Constants.SF.S);
            // 插入数据库
            return clinicalPathItemDAO.insert(clinicalPathItemDTO) > 0;
        } else {
            Optional.ofNullable(clinicalPathItemDTO.getId()).orElseThrow(() -> new AppException("无法保存,主键为空"));
            // 编辑
            return clinicalPathItemDAO.updateById(clinicalPathItemDTO) > 0;
        }
    }

    /**
     * @Meth:getCode
     * @Description: 获得系统临床路径项目描述单号规则
     * @Param: [clinicalPathItemDTO]
     * @return: java.lang.String
     * @Author: zhangguorui
     * @Date: 2021/9/15
     */
    private String getCode(String hospCode) {
        HashMap map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("typeCode", Constants.ORDERRULE.LCLJXM);
        WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
        if (StringUtils.isEmpty(orderNo.getData())) {
            throw new AppException("系统没有临床路径项目描述单号规则");
        }
        return orderNo.getData();
    }

    /**
     * @Meth:deletePathItemBatch
     * @Description: 直接删除临床路径项目, 2021/9/15 修改为 修改有效状态
     * @Param: [clinicalPathItemDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public Boolean deletePathItemBatch(ClinicalPathItemDTO clinicalPathItemDTO) {
        Optional.ofNullable(clinicalPathItemDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        if (ListUtils.isEmpty(clinicalPathItemDTO.getIds())) {
            throw new AppException("请选中要删除的数据");
        }
        return clinicalPathItemDAO.deletePathItemBatch(clinicalPathItemDTO) > 0;
    }

    /**
     * @Description: 根据id临床路径项目
     * @Param: [map]
     * @Author: zhangguorui
     * @Date: 2021/9/9
     */
    @Override
    public ClinicalPathItemDTO queryPathItemById(ClinicalPathItemDTO clinicalPathItemDTO) {
        Optional.ofNullable(clinicalPathItemDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        Optional.ofNullable(clinicalPathItemDTO.getId()).orElseThrow(() -> new AppException("项目id不能为空"));
        ClinicalPathItemDTO resultClinicalPathItemDTO = clinicalPathItemDAO.queryPathItemById(clinicalPathItemDTO);
        Optional.ofNullable(resultClinicalPathItemDTO).orElseThrow(() -> new AppException("查询不到数据"));
        return resultClinicalPathItemDTO;
    }

    /**
     * @Meth: insertBatchByExcelUpload
     * @Description: 根据excel文件导入
     * @Param: [clinicalPathItemDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/15
     */
    @Override
    public Boolean insertBatchByExcelUpload(ClinicalPathItemDTO clinicalPathItemDTO) {
        String hospCode = clinicalPathItemDTO.getHospCode();
        String crteId = clinicalPathItemDTO.getCrteId();
        String crteName = clinicalPathItemDTO.getCrteName();
        Optional.ofNullable(hospCode).orElseThrow(() -> new AppException("医院编码不能为空"));
        // 解析Excel文件
        Map<String, Object> resultMap = clinicalPathItemDTO.getResultMap();
        if (MapUtils.isEmpty(resultMap) || !(Boolean) resultMap.get("isSuccess")) {
            throw new AppException("解析错误，文件类型或者格式不对");
        } else {
            List<List<String>> resultList = MapUtils.get(resultMap, "resultList");
            if (ListUtils.isEmpty(resultList)) {
                throw new AppException("解析错误，数据为空");
            }
            // 拿取系统码表列表
            HashMap sysCodeMap = new HashMap();
            sysCodeMap.put("hospCode", hospCode);
            sysCodeMap.put("code", "LCXMFL");
            WrapperResponse<Map<String, List<SysCodeSelectDTO>>> byCode = sysCodeService_consumer.getByCode(sysCodeMap);
            Optional.ofNullable(byCode).orElseThrow(() -> new AppException("调用远程失败：获取不到码表值"));
            Map<String, List<SysCodeSelectDTO>> codeData = byCode.getData();
            // 获得临床路径项目分类
            List<SysCodeSelectDTO> lcxmflList = codeData.get("LCXMFL");
            Optional.ofNullable(lcxmflList).orElseThrow(() -> new AppException("获取不到临床路径项目分类码表值"));
            // 转化成map 的形式
            Map<String, String> lcxmflMap = lcxmflList.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
            List<ClinicalPathItemDTO> insertLists = new ArrayList<>();
            // 拼装插入的数据
            resultList.stream().forEach(item -> {
                // 获得项目名称
                String name = item.get(1);
                if (StringUtils.isEmpty(name)) {
                    throw new AppException("项目名称不能为空，请检查表格之后再重新导入");
                }
                // 获得项目分类名称
                String itemTypeName = item.get(2);
                // 项目分类value 先设置为其他
                String itemType = "9";
                // 通过name 转成value
                if (StringUtils.isNotEmpty(itemTypeName) && StringUtils.isNotEmpty(lcxmflMap.get(itemTypeName))) {
                    itemType = lcxmflMap.get(itemTypeName);
                } else {
                    throw new AppException("项目分类不能为空，请检查表格");
                }
                // 获得拼音码 如果拼音码 是空那么 自动生成pym
                String pym = StringUtils.isNotEmpty(item.get(3)) ? item.get(3) : PinYinUtils.toFirstPY(name);
                // 获得五笔码
                String wbm = StringUtils.isNotEmpty(item.get(4)) ? item.get(4) : WuBiUtils.getWBCode(name);
                // 获得备注
                String remarke = item.get(5);

                // 项目编码自己生成
                String code = getCode(hospCode);
                // 创建时间自己生成
                Date crteDate = DateUtils.getNow();
                // 是否有效 默认有效
                String isValid = Constants.SF.S;
                // 生成id
                String id = SnowflakeUtils.getId();
                // 封装数据 开始
                ClinicalPathItemDTO insertItemDTO = new ClinicalPathItemDTO();
                insertItemDTO.setHospCode(hospCode);
                insertItemDTO.setCrteId(crteId);
                insertItemDTO.setCrteName(crteName);
                insertItemDTO.setName(name);
                insertItemDTO.setItemType(itemType);
                insertItemDTO.setPym(pym);
                insertItemDTO.setWbm(wbm);
                insertItemDTO.setRemarke(remarke);
                insertItemDTO.setCode(code);
                insertItemDTO.setCrteTime(crteDate);
                insertItemDTO.setIsValid(isValid);
                insertItemDTO.setId(id);
                // 封装数据解决 塞到list'中
                insertLists.add(insertItemDTO);
            });
            return clinicalPathItemDAO.insertBatchByExcelUpload(insertLists) > 0;
        }
    }
}
