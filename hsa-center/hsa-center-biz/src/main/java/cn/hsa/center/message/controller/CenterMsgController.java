package cn.hsa.center.message.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.message.dto.MessageInfoDTO;
import cn.hsa.module.center.message.service.MessageInfoService;
import cn.hsa.util.SnowflakeUtils;
import cn.hutool.core.lang.Snowflake;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.code.controller
 * @class_name: CenterDatasourceController
 * @Description: 值域代码控制层
 * @Author: youxianlin
 * @Email: 254580179@qq.com
 * @Date: 2020/8/31 11:18
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("center/center/msg")
public class CenterMsgController extends CenterBaseController {
    @Resource
    private MessageInfoService messageInfoService_consumer;

    /**@Menthod insertMessageInfo
     * @Desrciption 更新消息
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2022/1/5 14:33
     * @Return WrapperResponse<PageDTO>
     **/
    @PostMapping("/insertMessageInfo")
    public WrapperResponse<Boolean> insertMessageInfo(@RequestBody MessageInfoDTO messageInfoDTO) {
        Map map = new HashMap();
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.insertMessageInfo(map);
    }

    /**@Menthod updateMessageInfo
     * @Desrciption 更新消息
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2022/1/5 14:33
     * @Return WrapperResponse<PageDTO>
     **/
    @PostMapping("/updateMessageInfo")
    public WrapperResponse<Boolean> updateMessageInfo(@RequestBody MessageInfoDTO messageInfoDTO) {
        Map map = new HashMap();
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.updateMessageInfo(map);
    }

    /**@Menthod updateMssageInfoStatusById
     * @Desrciption 更新消息发布
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2022/1/5 14:33
     * @Return WrapperResponse<PageDTO>
     **/
    @PostMapping("/updateMssageInfoStatusById")
    public WrapperResponse<Boolean> updateMssageInfoStatusById(@RequestBody MessageInfoDTO messageInfoDTO) {
        Map map = new HashMap();
        messageInfoDTO.setCrteId(userId);
        messageInfoDTO.setCrteName(userName);
        messageInfoDTO.setIsPublish("1");
        messageInfoDTO.setHospCode("");
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.updateMssageInfoStatusById(map);
    }

    /**@Menthod queryMessageInfoByType
     * @Desrciption 查询消息列表（分页）
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2022/1/5 14:34
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryMessageInfoByType")
    public WrapperResponse<PageDTO> queryMessageInfoByType(MessageInfoDTO messageInfoDTO) {
        Map map = new HashMap();
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.queryMessageInfoByType(map);
    }

    /**@Menthod deleteMessageInfoBatch
     * @Desrciption 批量删除消息
     * @param messageInfoDTO
     * @Author liuliyun
     * @Date   2022/1/5 14:33
     * @Return WrapperResponse<PageDTO>
     **/
    @PostMapping("/deleteMessageInfoBatch")
    public WrapperResponse<Boolean> deleteMessageInfoBatch(@RequestBody MessageInfoDTO messageInfoDTO) {
        Map map = new HashMap();
        map.put("messageInfoDTO",messageInfoDTO);
        return messageInfoService_consumer.deleteMessageInfoBatch(map);
    }
    /**
     * @Author gory
     * @Description 查询消息推送
     * @Date 2022/8/4 15:24
     * @Param [messageInfoDTO]
     **/
    @PostMapping("/queryMessageInfos")
    public WrapperResponse<PageDTO> queryMessageInfos(@RequestBody MessageInfoDTO messageInfoDTO){
        Map map = new HashMap();
        map.put("messageInfoDTO",messageInfoDTO);

        return messageInfoService_consumer.queryMessageInfos(map);
    }
}
