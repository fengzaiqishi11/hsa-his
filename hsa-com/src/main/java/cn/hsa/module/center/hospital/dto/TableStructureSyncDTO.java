package cn.hsa.module.center.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TableStructureSyncDTO implements Serializable {

    private  String syncTableName ;

    private  String syncZdName ;

    private  String syncType ;

    private  String zdType ;

    private  String zdLength ;

    private  String comment ;

    private  String isMust ;

    private String sql;

    private List<String> dataSourceIds;
}
