package cn.hsa.sys.log.bo.impl;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.module.sys.log.bo.HisLogInfoCzBO;
import cn.hsa.module.sys.log.bo.HisLogInfoYcBO;
import cn.hsa.module.sys.log.dao.HisLogInfoCzDao;
import cn.hsa.module.sys.log.dao.HisLogInfoYcDao;
import cn.hsa.module.sys.log.dto.HisLogInfoYcDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Package_name:cn.hsa.module.sys.log.bo
 * @Class_name:hisLogInfoCz
 * @Project_name:hsa-his
 * @Describe: 操作日志BO
 * @Author: zibo.yuan
 * @Date: 2020/12/1 11:10
 * @Email: zibo.yuan@powersi.com.cn
 */
@Component
@Slf4j
public class HisLogInfoYcBOImpl implements HisLogInfoYcBO {

    @Resource
    HisLogInfoYcDao hisLogInfoYcDao;

    @Resource
    HisLogInfoCzBO hisLogInfoCzBO;

    /***
     * @Description 新增异常日志
     * @param hisLogInfoYcDTO
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: java.lang.Boolean
     **/
    @Override
    public Boolean saveLogYc(HisLogInfoYcDTO hisLogInfoYcDTO) {
        if (hisLogInfoYcDTO != null) {
            return hisLogInfoYcDao.saveLogYc(hisLogInfoYcDTO) > 0;
        }
        return false;
    }

    /**
    * @Method queryLogInfo
    * @Desrciption 查询日志信息
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/9 14:38
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryLogInfo(HisLogInfoYcDTO hisLogInfoYcDTO) {
        PageHelper.startPage(hisLogInfoYcDTO.getPageNo(),hisLogInfoYcDTO.getPageSize());
        List<HisLogInfoYcDTO> list = new ArrayList<>();

        //默认查询当天日志
        if(hisLogInfoYcDTO.getQueryDate() == null){
            hisLogInfoYcDTO.setQueryDate(DateUtils.getNow());
        }

        if("cz".equals(hisLogInfoYcDTO.getQueryType())){
            //操作日志日志查询
            list = hisLogInfoYcDao.queryLogInfoWithCz(hisLogInfoYcDTO);
        }else{
            //异常日志日志查询
            list = hisLogInfoYcDao.queryLogInfoWithYc(hisLogInfoYcDTO);
        }

        return PageDTO.of(list);
    }

    /**
    * @Method queryLogFileInfo
    * @Desrciption 查询日志文件信息
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/11 14:59
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryLogFileInfo(HisLogInfoYcDTO hisLogInfoYcDTO) {
        String keyword = hisLogInfoYcDTO.getKeyword();
        // 从日志文件中获得日志信息
        List<HisLogInfoYcDTO> hisLogInfoYcDTOS = hisLogInfoCzBO.queryLogInfoWithFile(hisLogInfoYcDTO);

        //筛选后的集合
        List<HisLogInfoYcDTO> filterDtos = hisLogInfoYcDTOS;
        if(StringUtils.isNotEmpty(keyword)){
            //根据日志级别和唯一编码筛选
            filterDtos = hisLogInfoYcDTOS.stream()
                    .filter(dto -> keyword.equals(dto.getLogLevel())
                            || keyword.equals(dto.getTraceId()))
                    .collect(Collectors.toList());

        }

        //反转
        Collections.reverse(filterDtos);
        //分页
        List list = ListUtils.startPage(filterDtos, hisLogInfoYcDTO.getPageNo(), hisLogInfoYcDTO.getPageSize());

        PageDTO dto = new PageDTO();
        dto.setTotal((long)filterDtos.size());
        dto.setResult(list);

        return dto;
    }

}
