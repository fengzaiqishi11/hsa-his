//package cn.hsa.example.rpc.consumer.controller;
//
//import cn.hsa.hsaf.core.framework.HsafController;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 示例介绍如何通过java类方式，一致性的访问RPC和Rest服务UserInfoService
// */
//@RestController
//@RequestMapping("/web/rpc")
//@Slf4j
//public class RpcConsumerController extends HsafController {
//
////    /**
////     * 远程接口（见rpc下的consumer.xml的声明）
////     */
////    @Autowired
////    private UserInfoService userInfoService;
////
////    @GetMapping("/queryUser")
////    public WrapperResponse<UserDTO> queryUser(Integer userId) {
////
////        log.debug("userId:{}", userId);
////
////        /**
////         * 在这里RPC和REST调用已统一
////         */
////        WrapperResponse<UserDTO> userDTO = userInfoService.getUser(userId);
////        return userDTO;
////    }
////
////    @PostMapping("/saveUser")
////    public WrapperResponse<Integer> saveUser(@RequestBody cn.hsa.base.module1.dto.UserDTO userDTO) {
////        cn.hsa.base.service.dto.UserDTO user = new cn.hsa.base.service.dto.UserDTO();
////        BeanUtils.copyProperties(userDTO, user);
////        WrapperResponse<Integer> response = userInfoService.saveUser(user);
////        return response;
////    }
//}