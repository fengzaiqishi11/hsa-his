package cn.hsa.module.sys.parameter.dao;

import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterUpdateDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.data.dao
 * @Class_name: SysParameterDAO
 * @Describe: 参数数据访问层接口
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 15:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SysParameterDAO {

        SysParameterDTO getById(SysParameterDTO sysParameterDTO);

        /**
         * @Menthod queryPage
         * @Desrciption 根据条件查询参数信息
         * @Param
         * 1. SysParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/7/28 17:02
         * @Return cn.hsa.base.PageDTO
         **/
        List<SysParameterDTO> queryPage(SysParameterDTO sysParameterDTO);

        /**
         * @Menthod queryAll()
         * @Desrciption  查询所有参数信息
         * @Param
         * [1. SysParameterDTO]
         * @Author zhangxuan
         * @Date   2020/7/28 14:47
         * @Return java.util.List<cn.hsa.module.base.bspl.dto.SysParameterDTO>
         **/
        List<SysParameterDTO> queryAll(SysParameterDTO sysParameterDTO);

        /**
         * @Menthod insert()
         * @Desrciption 新增参数
         * @Param
         *1. SysParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/7/28 15:53
         * @Return int
         **/
        int insert(SysParameterDTO sysParameterDTO);

        /**
         * @Menthod deleteSuppelier()
         * @Desrciption 删除参数
         * @Param
         *  1. map
         * @Author zhangxuan
         * @Date   2020/7/28 15:57
         * @Return int
         **/
        int delete(SysParameterDTO sysParameterDTO);

        /**
         * @Menthod update()
         * @Desrciption 修改参数信息
         * @Param
         * 1. SysParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/7/28 15:58
         * @Return int
         **/
        int update(SysParameterDTO sysParameterDTO);

        /**
         * @Menthod queryCodeIsExist()
         * @Desrciption 判断编码是否存在
         * @Param
         * 1. SysParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/7/28 15:58
         * @Return int
         **/
        int queryCodeIsExist(SysParameterDTO sysParameterDTO);

        /**
         * @Menthod queryNameIsExist()
         * @Desrciption 判断编码是否存在
         * @Param
         * 1. SysParameterDTO  参数数据对象
         * @Author zhangxuan
         * @Date   2020/9/28 15:58
         * @Return int
         **/
        int queryNameIsExist(SysParameterDTO sysParameterDTO);

        /**
         * @Method: getParameterByCode
         * @Description: 根据编码获取参数信息
         * @Param: [code]
         * @Author: youxianlin
         * @Email: 254580179@qq.com
         * @Date: 2020/9/15 16:15
         * @Return: cn.hsa.module.sys.parameter.dto.SysParameterDTO
         **/
        SysParameterDTO getParameterByCode(String hospCode, String code);

        @MapKey("code")
        Map<String, SysParameterDTO> getParameterByCodeList(@Param("hospCode") String hospCode, @Param("codeList") String[] codeList);

        /**
        * @Menthod insertParameterUpdate
        * @Desrciption 新增保存记录
        *
        * @Param
        * []
        *
        * @Author jiahong.yang
        * @Date   2021/12/20 15:14
        * @Return int
        **/
        int insertParameterUpdate(@Param("list") List<SysParameterUpdateDTO> sysParameterUpdateDTOS);

        /**
        * @Menthod querySysParameterByIds
        * @Desrciption 删除系统参数前查询数据
        *
        * @Param
        * [sysParameterDTO]
        *
        * @Author jiahong.yang
        * @Date   2021/12/20 15:51
        * @Return java.util.List<cn.hsa.module.sys.parameter.dto.SysParameterUpdateDTO>
        **/
        List<SysParameterUpdateDTO> querySysParameterByIds(SysParameterDTO sysParameterDTO);
}

