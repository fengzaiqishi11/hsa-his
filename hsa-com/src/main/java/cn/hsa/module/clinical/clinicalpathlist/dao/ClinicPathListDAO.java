package cn.hsa.module.clinical.clinicalpathlist.dao;

import cn.hsa.module.clinical.clinicalpathlist.dto.ClinicPathListDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 表名含义：临床路径目录表(ClinicPathListDTO)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-08 19:26:54
 */
public interface ClinicPathListDAO {

    ClinicPathListDTO getClinicPathById(String id);

    List<ClinicPathListDTO> queryClinicPathAll(ClinicPathListDTO clinicPathList);

    int insertClinicPath(ClinicPathListDTO clinicPathList);

    int updateClinicPath(ClinicPathListDTO clinicPathList);

    int deleteClinicPathById(ClinicPathListDTO clinicPathList);

}
