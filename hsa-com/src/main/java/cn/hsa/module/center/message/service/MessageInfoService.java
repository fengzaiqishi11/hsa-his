package cn.hsa.module.center.message.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.message.service
 * @Class_name: SysParameterservice
 * @Describe:
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/11/25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-msg")
public interface MessageInfoService {
    /**
     * @Menthod insertMessageInfo
     * @Desrciption 新增消息
     * @param map
     * @Author liuliyun
     * @Date   2021/11/26 8:50
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/msg/message/insertMessageInfo")
    WrapperResponse<Boolean> insertMessageInfo(Map map);



    /**
     * @Menthod updateMessageInfo
     * @Desrciption 更新消息
     * @param map
     * @Author liuliyun
     * @Date   2021/11/26 8:50
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/msg/message/updateMessageInfo")
    WrapperResponse<Boolean> updateMessageInfo(Map map);


    /**
     * @Menthod deleteMessageInfo
     * @Desrciption 单个删除消息
     * @param map
     * @Author liuliyun
     * @Date   2021/11/26 9:01
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/msg/message/deleteMessageInfo")
    WrapperResponse<Boolean> deleteMessageInfo(Map map);

    /**
     * @Menthod deleteMessageInfoBatch
     * @Desrciption 批量删除消息
     * @param map
     * @Author liuliyun
     * @Date   2021/11/26 9:01
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/msg/message/deleteMessageInfoBatch")
    WrapperResponse<Boolean> deleteMessageInfoBatch(Map map);


    /**
     * @Menthod updateMssageInfoStatusById
     * @Desrciption 根据消息id修改发布状态
     * @param map
     * @Author liuliyun
     * @Date   2021/12/03 14:21
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/msg/message/updateMssageInfoStatusById")
    WrapperResponse<Boolean> updateMssageInfoStatusById(Map map);


    /**@Menthod queryMessageInfoPage
     * @Desrciption 查询消息列表(分页)
     * @param map
     * @Author liuliyun
     * @Date   2021/12/03 14:32
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/msg/message/queryMessageInfoByType")
    WrapperResponse<PageDTO> queryMessageInfoByType(Map map);
    /**
     * @Author gory
     * @Description 查询消息推送的信息
     * @Date 2022/8/4 15:28
     * @Param [map]
     **/
    @PostMapping("/service/msg/message/queryMessageInfoByType")
    WrapperResponse<PageDTO> queryMessageInfos(Map map);
}
