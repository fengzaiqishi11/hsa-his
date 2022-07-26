package cn.hsa.module.sys.parameter.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.parameter.bo
 * @Class_name: SysParameterBO
 * @Describe:  参数管理业务逻辑实现层
 * @Author: zhangxuan
 * @Email: zhangxuan@powersi.com
 * @Date: 2020/7/28 16:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SysParameterBO {

  /**
   * @Menthod queryPage
   * @Desrciption 根据条件查询参数信息
   * @Param
   * 1. SysParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/7/28 17:02
   * @Return cn.hsa.base.PageDTO
   **/
  PageDTO queryPage(SysParameterDTO sysParameterDTO);

  /**
   * @Menthod queryAll()
   * @Desrciption  查询所有参数信息
   * @Param
   * [1. SysParameterDTO]
   * @Author zhangxuan
   * @Date   2020/7/28 14:47
   * @Return java.util.List<cn.hsa.module.sys.parameter.dto.SysParameterDTO>
   **/
  List<SysParameterDTO> queryAll(SysParameterDTO sysParameterDTO);

  /**
   * @Menthod insert()
   * @Desrciption 新增参数
   * @Param
   * 1. SysParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/7/28 15:53
   * @Return int
   **/
  boolean insert(SysParameterDTO sysParameterDTO);

  /**
   * @Menthod deleteSuppelier()
   * @Desrciption 删除参数
   * @Param
   *  1. map
   * @Author zhangxuan
   * @Date   2020/7/28 15:57
   * @Return int
   **/
  boolean delete(SysParameterDTO sysParameterDTO);

  /**
   * @Menthod update()
   * @Desrciption 修改参数信息
   * @Param
   * 1. SysParameterDTO  参数数据对象
   * @Author zhangxuan
   * @Date   2020/7/28 15:58
   * @Return int
   **/
  boolean update(SysParameterDTO sysParameterDTO);

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

  Map<String, SysParameterDTO> getParameterByCodeList(String hospCode, String[] codeList);

  /**
  * @Menthod getIsReallyPwd
  * @Desrciption 校验密码是否正确
  *
  * @Param
  * [sysParameterDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/12/20 14:07
  * @Return java.util.Map
  **/
  Map getIsReallyPwd(Map map);

  /**
   * @Menthod getIsReallyPwd
   * @Desrciption 请求登录人员与机构信息信息
   *
   * @Param
   * [sysParameterDTO, req, res]
   *
   * @Author yuelong.chen
   * @Date   2022/5/10 10:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
    Map getLoginInfo(Map map);
  /**
   * @Author gory
   * @Description 过期提醒(后续天数要做出参数配置)
   * @Date 2022/7/26 9:50
   * @Param [centerHospitalDTO]
   **/
  Map<String, Object> getHospServiceStatsByCode(CenterHospitalDTO centerHospitalDTO);
}
