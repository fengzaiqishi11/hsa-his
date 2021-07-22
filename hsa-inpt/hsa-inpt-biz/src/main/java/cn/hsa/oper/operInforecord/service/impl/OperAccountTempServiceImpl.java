package cn.hsa.oper.operInforecord.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operInforecord.bo.OperAccountTempBO;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDTO;
import cn.hsa.module.oper.operInforecord.dto.OperAccountTempDetailDTO;
import cn.hsa.module.oper.operInforecord.service.OperAccountTempService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.oper.operInforecord.service.impl
 *@Class_name: OperAccountTempServiceImpl
 *@Describe: 手术补记账模板service实现类
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/12/5 10:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/oper/operAccountTemp")
@Service("operAccountTempService_provider")
public class OperAccountTempServiceImpl extends HsafService implements OperAccountTempService {

    @Resource
    OperAccountTempBO operAccountTempBo;

    /**
    * @Method queryOperAccountTempDTOPage
    * @Desrciption 手术模板分页查询
    * @param map
    * @Author liuqi1
    * @Date   2020/12/5 11:00
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryOperAccountTempDTOPage(Map<String,Object> map) {
        OperAccountTempDTO operAccountTempDTO = MapUtils.get(map, "operAccountTempDTO");
        PageDTO pageDTO = operAccountTempBo.queryOperAccountTempDTOPage(operAccountTempDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
    * @Method queryOperAccountTempDetailDTOPage
    * @Desrciption 手术模板明细分页查询
    * @param map
    * @Author liuqi1
    * @Date   2020/12/5 11:02
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryOperAccountTempDetailDTOPage(Map<String,Object> map) {
        OperAccountTempDetailDTO operAccountTempDetailDTO = MapUtils.get(map, "operAccountTempDetailDTO");
        PageDTO pageDTO = operAccountTempBo.queryOperAccountTempDetailDTOPage(operAccountTempDetailDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method queryOperAccountTempDetailDTOPage
     * @Desrciption 手术模板明细分页查询
     * @param map
     * @Author liuqi1
     * @Date   2020/12/5 11:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<List<OperAccountTempDetailDTO>> queryOperAccountTempDetailDTO(Map<String,Object> map) {
        OperAccountTempDetailDTO operAccountTempDetailDTO = MapUtils.get(map, "operAccountTempDetailDTO");
        List<OperAccountTempDetailDTO> list = operAccountTempBo.queryOperAccountTempDetailDTO(operAccountTempDetailDTO);
        return WrapperResponse.success(list);
    }

    /**
    * @Method insertOperAccountTempDTO
    * @Desrciption 手术模板、明细新增
    * @param map
    * @Author liuqi1
    * @Date   2020/12/5 11:03
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<Boolean> insertOperAccountTempDTO(Map<String,Object> map) {
        OperAccountTempDTO operAccountTempDTO = MapUtils.get(map, "operAccountTempDTO");
        Boolean aBoolean = operAccountTempBo.insertOperAccountTempDTO(operAccountTempDTO);
        return WrapperResponse.success(aBoolean);
    }

    /**
     * @Menthod deleteOperAccountTempDetailByTempId
     * @Desrciption  根据模版id删除模版和明细
     * @param map
     * @Author xingyu.xie
     * @Date   2021/1/22 11:17
     * @Return java.lang.Boolean
     **/
    @Override
    public WrapperResponse<Boolean> deleteOperAccountTempDTOById(Map<String,Object> map){
        OperAccountTempDTO operAccountTempDTO = MapUtils.get(map, "operAccountTempDTO");
        Boolean aBoolean = operAccountTempBo.deleteOperAccountTempDTOById(operAccountTempDTO);
        return WrapperResponse.success(aBoolean);
    }


    /**
     * @Menthod deleteOperAccountTempDetailById
     * @Desrciption  保存模版内容和模版明细内容
     * @param map
     * @Author xingyu.xie
     * @Date   2021/1/22 11:18
     * @Return java.lang.Boolean
     **/
    @Override
    public WrapperResponse<Boolean> saveOperAccountTemp(Map<String,Object> map ){
        OperAccountTempDTO operAccountTempDTO = MapUtils.get(map, "operAccountTempDTO");
        Boolean aBoolean = operAccountTempBo.saveOperAccountTemp(operAccountTempDTO);
        return WrapperResponse.success(aBoolean);
    }

}
