package cn.hsa.module.emr.message.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.message.dto.MessageInfoDTO;
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
     * @Menthod insertMessageInfoBatch
     * @Desrciption 批量新增消息
     * @param map
     * @Author liuliyun
     * @Date   2021/11/26 8:50
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/msg/message/insertMessageInfoBatch")
    WrapperResponse<Boolean> insertMessageInfoBatch(Map map);

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
     * @Menthod updateMessageInfoBatch
     * @Desrciption 批量更新消息
     * @param map
     * @Author liuliyun
     * @Date   2021/12/03 14:21
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/service/msg/message/updateMssageInfoBatchById")
    WrapperResponse<Boolean> updateMssageInfoBatchById(Map map);


    /**@Menthod queryMessageInfoPage
     * @Desrciption 查询消息列表(分页)
     * @param map
     * @Author liuliyun
     * @Date   2021/12/03 14:32
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/msg/message/queryMessageInfoPage")
    WrapperResponse<PageDTO> queryMessageInfoPage(Map map);

    /**@Menthod queryMessageInfoList
     * @Desrciption 查询消息列表
     * @param map
     * @Author liuliyun
     * @Date   2021/12/03 15:04
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/service/msg/message/queryMessageInfoList")
    WrapperResponse<List<MessageInfoDTO>> queryMessageInfoList(Map map);

}
