//package cn.hsa.example.rpc.provider.service.impl;
//
//import cn.hsa.hsaf.core.framework.HsafService;
//import cn.hsa.hsaf.core.framework.web.HsafRestPath;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@HsafRestPath("/service/px/math")
//@Slf4j
//@Service("mathServiceImpl")
//public class MathServiceImpl extends HsafService {
//// implements MathService
////    @HsafRestPath(value = "/add", method = RequestMethod.GET)
////    @Override
////    public WrapperResponse<Integer> add(Integer va, Integer vb) {
////        log.info("调用MathServiceImpl.add()方法，va=" + va + ";vb=" + vb);
////        return WrapperResponse.success(va + vb);
////    }
////
////    //这个用来演示复杂参数
////    @HsafRestPath(value = "/insertuser", method = RequestMethod.POST)
////    @Override
////    public WrapperResponse<String> insertUser(@RequestBody UserDTO user) throws Exception {
////        log.info("调用MathServiceImpl.insertUser()方法，user=" + user);
////        //为了演示方便，只是简单打印信息，并不真正插入数据库
////        if (user.getName().equalsIgnoreCase("testException")) {
////            throw new Exception("username is testException");
////        } else if (user.getName().equalsIgnoreCase("testBusinessException")) {
////            throw new BusinessException(64918, "username is testBusinessException");
////        } else if (user.getName().equalsIgnoreCase("testAppException")) {
////            throw new AppException(64918, "username is testAppException");
////        }
////        return WrapperResponse.success("新建用户成功");
////    }
//}
