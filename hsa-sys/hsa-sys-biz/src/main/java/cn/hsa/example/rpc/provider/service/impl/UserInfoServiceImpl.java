//package cn.hsa.example.rpc.provider.service.impl;
//
//import cn.hsa.hsaf.core.framework.HsafService;
//import cn.hsa.hsaf.core.framework.web.HsafRestPath;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
////import cn.hsa.base.module1.bo.UserBO;
//
///**
// * 示例：
// * 提供RPC&REST接口给其他应用访问（需在rpc下的provoder.xml声明）
// */
//@Service("userInfoService")
//@Slf4j
//@HsafRestPath("/service/module1/user")
//public class UserInfoServiceImpl extends HsafService  {
//// implements UserInfoService
//////    @Autowired
//////    UserBO userBO;
////
////    @HsafRestPath(value = "/get", method = RequestMethod.GET)
////    @Override
////    public WrapperResponse<UserDTO> getUser(int userId) {
////
////        UserDTO userDTO = null;
////
////        log.info("userId:{}", userId);
////
////        try {
//////            cn.hsa.base.module1.dto.UserDTO dto = userBO.get(userId);
//////            if (dto == null) {
//////                return WrapperResponse.success(null);
//////            }
//////
//////            log.debug("getUser,userId:{}", dto.getId());
//////
//////            userDTO = new UserDTO();
//////            userDTO.setName(dto.getName());
//////            userDTO.setId(dto.getId());
//////            userDTO.setAddr(dto.getAddr());
//////            userDTO.setSex(dto.getSex());
////
////        } catch (Exception e) {
////            log.error("error", e);
////            return WrapperResponse.error(123123, "错误", null);
////        }
////
////        return WrapperResponse.success(userDTO);
////    }
////
////    @HsafRestPath(value = "/save", method = RequestMethod.POST)
////    @Override
////    public WrapperResponse<Integer> saveUser(@RequestBody UserDTO user) {
//////        log.info("received caller");
//////        cn.hsa.base.module1.dto.UserDTO userDTO = new cn.hsa.base.module1.dto.UserDTO();
//////        BeanUtils.copyProperties(user, userDTO);
//////        cn.hsa.base.module1.dto.UserDTO result = userBO.add(userDTO);
//////        return WrapperResponse.success(result.getId());
////        return null;
////    }
//}
