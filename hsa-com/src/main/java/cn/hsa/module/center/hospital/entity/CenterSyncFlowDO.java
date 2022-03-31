package cn.hsa.module.center.hospital.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterSyncFlowDO  extends PageDO implements Serializable {


    private String id	; //主键ID
    private String  hospCode	; //	医院编码
    private String type ; //			操作类型（0创建医院,1创建数据库，2匹配数据源，3同步基础数据，4全部完成）
    private String statusCode	; //		操作状态（0进行中，1成功，2失败）
    private String message	; //		操作提示
    private Date creatTime	; //		创建时间
}
