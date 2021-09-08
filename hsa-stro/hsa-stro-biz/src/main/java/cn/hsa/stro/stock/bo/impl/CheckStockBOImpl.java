package cn.hsa.stro.stock.bo.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.stro.stock.bo.CheckStockBO;
import cn.hsa.module.stro.stock.dao.CheckStockDAO;
import cn.hsa.module.stro.stock.dto.CheckStockDTO;
import cn.hsa.module.stro.stock.dto.CheckStockRespDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Package_name: cn.hsa.stro.stock.bo.impl
 * @Class_name: CheckStockBOImpl
 * @Describe:
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/9 20:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class CheckStockBOImpl implements CheckStockBO {

    @Resource
    private CheckStockDAO checkStockDAO;

    /**
     * @Method queryOutDrugOrMeterialStock
     * @Desrciption 库存 - 占用 - 未核收(参数控制)  - 已核收 数量
     * @Param [outptPrescribeDetailsDTO]
     * @Author zhangguorui
     * @Date 2021/8/9 20:10
     * @Return java.lang.Integer
     */
    @Override
    public CheckStockRespDTO queryOutDrugOrMeterialStock(CheckStockDTO checkStockDTO) {
        // 参数校验
        checkOutParam(checkStockDTO);
        // 获得库存、占存数量、库存单位、库存拆零单位
        Map<String, BigDecimal> strockMap = checkStockDAO.getStrockNumber(checkStockDTO);
        // 查不出数据说明该配药药房下面没有这个材料
        if (MapUtils.isEmpty(strockMap)) {
            // 查询配药科室
            Map<String, String> deptMap = checkStockDAO.getDept(checkStockDTO);
            String deptName = "";
            if (!MapUtils.isEmpty(deptMap)){
                deptName = deptMap.get("deptName");
                throw new AppException( deptName +"下面" + checkStockDTO.getItemName() + "的库存不足");
            }
            throw new AppException("发药药房下面" + checkStockDTO.getItemName() + "的库存不足");
        }
        // 拆分比
        BigDecimal splitRatio = MapUtils.get(strockMap, "splitRatio");
        // 库存数量
        BigDecimal strockNum = MapUtils.get(strockMap, "strockNum");
        // 库存拆零数量
        BigDecimal strockSplitNum = BigDecimalUtils.multiply(strockNum, splitRatio);
        // 获得占用库存
        BigDecimal stockOccupy = MapUtils.get(strockMap, "stockOccupy");
        // 获得系统参数 是否计算未结算/未核收数据（MZYS_SFJSWJSSJ），时间间隔（MZYS_CF_WJSFYKC）
        Map<String, String> mapParameter = this.getParameterValue(checkStockDTO.getHospCode(),
                new String[]{"MZYS_SFJSWJSSJ", "MZYS_CF_WJSFYKC"});
        // 获得时间间隔 如果为空那么默认24小时
        String wjsykc = MapUtils.getVS(mapParameter, "MZYS_CF_WJSFYKC", "24");
        checkStockDTO.setWjsykc(wjsykc);
        // 获得是否计算未结算数据参数(默认算进去)
        String isCaculate = MapUtils.getVS(mapParameter, "MZYS_SFJSWJSSJ", "1");
        // 获得已开处方，但未结算/未核收（系统的参数进行控制）药品数量
        BigDecimal totalNumberNoCaculate = new BigDecimal(0);
        BigDecimal totalNumberNoCheck = new BigDecimal(0);
        if ("1".equals(isCaculate)) {
            // 门诊未结算数量 保留4位小数
            totalNumberNoCaculate = checkStockDAO.getOuptCostTotalNumber(checkStockDTO)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            // 住院未核收数量
            totalNumberNoCheck = checkStockDAO.getInptCostTotalNumber(checkStockDTO)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        // 获得已结算未配药/已核收 未配药
        // 已结算未配药
        BigDecimal prescribeOuptNumber = checkStockDAO.getOuptCostNoPrescribeNumber(checkStockDTO)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        // 已核收未配药
        BigDecimal prescribeInptNumber = checkStockDAO.getInptCostNoPrescribeNumber(checkStockDTO)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal result = BigDecimalUtils.subtractMany(strockSplitNum, stockOccupy,
                totalNumberNoCaculate, totalNumberNoCheck, prescribeOuptNumber, prescribeInptNumber);
        BigDecimal dailyTimes = new BigDecimal(0);
        BigDecimal execInterval = new BigDecimal(0);
        if (StringUtils.isNotEmpty(checkStockDTO.getRateId())) {
            // 通过频率Id 获得每日次数、执行周期
            Map<String, BigDecimal> rateMap = checkStockDAO.getRateMessage(checkStockDTO);
            if (!MapUtils.isEmpty(rateMap)) { // 防止空指针
                // 每日次数
                dailyTimes = MapUtils.get(rateMap, "dailyTimes");
                // 执行周期
                execInterval = MapUtils.get(rateMap, "execInterval");
            }
        }
        // 频率 = 每日次数/执行周期
        BigDecimal rate = new BigDecimal(1);
        if (!BigDecimalUtils.isZero(dailyTimes) && !BigDecimalUtils.isZero(execInterval)) {
            rate = BigDecimalUtils.divide(dailyTimes, execInterval);
        }
        // 执行天数
        BigDecimal userDay = new BigDecimal(1);
        if (checkStockDTO.getUseDays() != 0 && null != checkStockDTO.getUseDays()) {
            userDay = new BigDecimal(checkStockDTO.getUseDays());
        }
        // 计算所需总数量，需要使用频率*总量*天数 获得总数量
        BigDecimal totalNum = BigDecimalUtils.multiplyMany(checkStockDTO.getNum(), rate, userDay)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        // 封装返回参数
        CheckStockRespDTO checkStockRespDTO = new CheckStockRespDTO();
        checkStockRespDTO.setPrescribeInptNumber(prescribeInptNumber);
        checkStockRespDTO.setPrescribeOuptNumber(prescribeOuptNumber);
        checkStockRespDTO.setStockOccupy(stockOccupy.setScale(2, BigDecimal.ROUND_HALF_UP));
        checkStockRespDTO.setStrockSplitNum(strockSplitNum.setScale(2, BigDecimal.ROUND_HALF_UP));
        checkStockRespDTO.setTotalNumberNoCaculate(totalNumberNoCaculate);
        checkStockRespDTO.setTotalNumberNoCheck(totalNumberNoCheck);
        // 所需数量 - 剩余数量
        checkStockRespDTO.setResult(result.subtract(totalNum));
        return checkStockRespDTO;
    }

    /**
     * @Method queryInptDrugOrMeterialStock
     * @Desrciption
     * @Param [inptAdviceDTO]
     * @Author zhangguorui
     * @Date 2021/8/10 20:25
     * @Return java.math.BigDecimal
     */
    @Override
    public BigDecimal queryInptDrugOrMeterialStock(InptAdviceDTO inptAdviceDTO) {
        return null;
    }

    /**
     * @Method checkOutParam
     * @Desrciption 判断门诊传入参数
     * @Param [outptPrescribeDetailsDTO]
     * @Author zhangguorui
     * @Date 2021/8/10 9:50
     * @Return void
     */
    private void checkOutParam(CheckStockDTO checkStockDTO) {
        Optional.ofNullable(checkStockDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        Optional.ofNullable(checkStockDTO.getPharId()).orElseThrow(() -> new AppException("发药药房id不能为空"));
        Optional.ofNullable(checkStockDTO.getItemId()).orElseThrow(() -> new AppException("项目id不能为空"));
    }

    /**
     * 获取系统参数
     *
     * @param hospCode
     * @param code
     * @return
     */
    public Map<String, String> getParameterValue(String hospCode, String[] code) {
        List<SysParameterDTO> list = checkStockDAO.getParameterValue(hospCode, code);
        Map<String, String> retMap = new HashMap<>();
        if (!MapUtils.isEmpty(list)) {
            for (SysParameterDTO hit : list) {
                retMap.put(hit.getCode(), hit.getValue());
            }
        }
        return retMap;
    }

}
