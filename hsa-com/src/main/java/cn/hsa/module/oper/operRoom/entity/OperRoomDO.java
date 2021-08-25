package cn.hsa.module.oper.operRoom.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 *@Package_name: cn.hsa.module.inpt.operRoom.entity
 *@Class_name: OperRoom
 *@Describe: 手术室表
 *@Author: marong
 *@Date: 2020-09-17 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperRoomDO extends PageDO implements Serializable {

     private static final long serialVersionUID = 2082026098104216712L;

     private String id;
    /**
    医院编码
     */
    private String hospCode;
    /**
    手术室编码
     */
    private String code;
     /**
      * 手术室名称
      */
    private String name;
     /**
      * 手术室地址
      */
    private String address;
     /**
      * 是否在用
      */
    private String isUse;
     /**
      * 是否有效
      */
    private String isValid;
     /**
      * 创建人ID
      */
    private String crteId;
     /**
      * 创建人姓名
      */
    private String crteName;
     /**
      * 创建时间
      */
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

}
