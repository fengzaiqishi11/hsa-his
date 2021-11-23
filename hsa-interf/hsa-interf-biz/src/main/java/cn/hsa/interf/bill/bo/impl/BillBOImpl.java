package cn.hsa.interf.bill.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.interf.bill.bo.BillBO;
import cn.hsa.module.interf.bill.dao.BillDAO;
import cn.hsa.module.interf.bill.dto.*;
import cn.hsa.util.MapUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *
 * 电子票据BO实现类
 * @author liudawen
 * @date 2021/11/9
 */
@Component
@Slf4j
public class BillBOImpl implements BillBO{

    @Resource
    private BillDAO billDAO;

    @Override
    public List<MzpjZtDTO> queryMzZtView(Map<String, Object> map) {
        return billDAO.listMzpjZtDTOsInView(map);
    }

    @Override
    public List<MzpjFyDTO> queryMzFyView(Map<String, Object> map) {
        List<String> pjhmList = (List<String>)MapUtils.get(map, "pjhmList");
        return billDAO.listMzpjFyByPjhm(pjhmList);
    }

    @Override
    public List<MzylMxDTO> queryMzYlView(Map<String, Object> map) {
        List<String> pjhmList = (List<String>)MapUtils.get(map, "pjhmList");
        return billDAO.listMzpjMxByPjhm(pjhmList);
    }

    @Override
    public List<ZypjZtDTO> queryZyZtView(Map<String, Object> map) {
        return billDAO.listZypjZtDTOsInView(map);
    }

    @Override
    public List<ZypjFyDTO> queryZyFyView(Map<String, Object> map) {
        List<String> pjhmList = (List<String>)MapUtils.get(map, "pjhmList");
        return billDAO.listZypjFyByPjhm(pjhmList);
    }

    @Override
    public List<ZyylMxDTO> queryZyYlView(Map<String, Object> map) {
        List<String> pjhmList = (List<String>)MapUtils.get(map, "pjhmList");
        return billDAO.listZypjMxByPjhm(pjhmList);
    }
}
