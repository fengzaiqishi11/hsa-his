package cn.hsa.module.center.hospital.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CenterRootDatabaseBO extends PageDO implements Serializable {

    private String id;
    private String type;

    private String jdbcDriver;

    private String rootUser;

    private String rootPassword;

    private String rootUrl;
    private String hospUrl;

}
