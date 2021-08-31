package cn.hsa.module.oper.operInforecord.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.operInforecord.service
 * @Class_name:: OperInfoRecordService
 * @Description: 手麻系统接口
 * @Author: ｍａｒｏｎｇ
 * @Date: ２０２０／０９／１７　　18点12分
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-oper")
public interface OperInfoRecordService {




    /**
    * @Method saveSurgery
    * @Param [map] 
    * @description   
    * @author marong 
    * @date 2020/9/18 14:47 
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>  
    * @throws   
    */
    @PostMapping("/service/oper/operInfoRecord/saveSurgery")
    WrapperResponse<Boolean> saveSurgery(Map map);
    
    /**
       *  批量保存手术信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/7/6 20:08
       * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd 
    **/
    WrapperResponse<Boolean> updateSurgeryByAdviceId(Map map);

    /**
    * @Method updateSurgeryStatus
    * @Param [map]
    * @description   更改手术登记的状态
    * @author marong
    * @date 2020/9/18 17:09
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @PostMapping("/service/oper/operInfoRecord/updateSurgeryStatus")
    WrapperResponse<Boolean> updateSurgeryStatus(Map map);

    /**
    * @Method updateSurgeryInfo
    * @Param [map]
    * @description   手术登记信息更新
    * @author marong
    * @date 2020/9/18 17:19
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */
    @PostMapping("/service/oper/operInfoRecord/updateSurgeryInfo")
    WrapperResponse<Boolean> updateSurgeryInfo(Map map);
    /** 取消手术完成登记 **/
    WrapperResponse<Boolean> updateSurgeryCompleteToCancel(Map map);

   /**
   * @Method queryOperInfoRecordList
   * @Param [map]
   * @description   获取手术信息
   * @author marong
   * @date 2020/9/27 11:11
   * @return java.util.List<cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO>
   * @throws
   */
   WrapperResponse<PageDTO> queryOperInfoRecordList(Map map);

    /**
    * @Method checkOperRepeat
    * @Param [operInfoRecordDO]
    * @description   校验是否重新申请
    * @author marong
    * @date 2020/9/25 17:19
    * @return boolean
    * @throws
    */
    boolean checkOperRepeat(Map map);


    /**
    * @Menthod getOperInfoById
    * @Desrciption  通过id拿到手术记录
     * @param map
    * @Author xingyu.xie
    * @Date   2021/1/14 20:33
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO>
    **/
    WrapperResponse<OperInfoRecordDTO>  getOperInfoById(Map map);
}
