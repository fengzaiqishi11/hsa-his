package cn.hsa.emr.emrElement.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrelement.bo.EmrElementBO;
import cn.hsa.module.emr.emrelement.dao.EmrElementDAO;
import cn.hsa.module.emr.emrelement.dto.EmrElementDTO;
import cn.hsa.module.emr.emrelement.entity.EmrElementMatchDO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.emr.emrElement.bo.impl
 * @Class_name: EmrElementBOImpl
 * @Describe: 电子病历元素管理业务实现曾
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/19 11:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrElementBOImpl extends HsafBO implements EmrElementBO {
  @Resource
  private EmrElementDAO emrElementDAO;

  @Resource
  private SysCodeService sysCodeService_consumer;

  /**
  * @Menthod getById
  * @Desrciption 根据主键id或者编码code查询单个电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:16
  * @Return cn.hsa.module.emr.emrelement.dto.EmrElementDTO
  **/
  @Override
  public EmrElementDTO getByIdorCode(EmrElementDTO emrElementDTO) {
    return emrElementDAO.getByIdorCode(emrElementDTO);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 根据条件查询元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:16
  * @Return java.util.List<cn.hsa.module.emr.emrelement.dto.EmrElementDTO>
  **/
  @Override
  public List<EmrElementDTO> queryAll(EmrElementDTO emrElementDTO) {
    return emrElementDAO.queryPageorAll(emrElementDTO);
  }

  /**
  * @Menthod queryElementCodes
  * @Desrciption 根据条件查询元素编码列表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/10/9 11:07
  * @Return java.util.List<java.lang.String>
  **/
  @Override
  public List<String> queryElementCodes(Map map) {
    return emrElementDAO.queryElementCodes(map);
  }

  /**
  * @Menthod save
  * @Desrciption 保存（修改，增加）电子病历元素入口
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:16
  * @Return boolean
  **/
  @Override
  public boolean save(EmrElementDTO emrElementDTO) {
    emrElementDTO.setPym(PinYinUtils.toFirstPY(emrElementDTO.getName()));
    emrElementDTO.setWbm(WuBiUtils.getWBCode(emrElementDTO.getName()));
    //如果id为空则为新增，不为空则为修改
    if(StringUtils.isEmpty(emrElementDTO.getId())){
      return insert(emrElementDTO);
    } else {
      return update(emrElementDTO);
    }
  }

  /**
  * @Menthod update
  * @Desrciption 修改电子病历元素信息
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:19
  * @Return boolean
  **/
  public boolean update(EmrElementDTO emrElementDTO) {
    //判断修改的元素是不是父级节点
    if("0".equals(emrElementDTO.getIsEnd()) && "0".equals(emrElementDTO.getIsValid())){
      //查询数据库的有效状态
      EmrElementDTO byIdorCode = emrElementDAO.getByIdorCode(emrElementDTO);
      //如果状态不一样,则修改该父节点下所有子节点的有效状态
      if(!byIdorCode.getIsValid().equals(emrElementDTO.getIsValid())){

        //查出该父节点下所有子节点的值
        List<TreeMenuNode> emrElementTree = getEmrElementTree(emrElementDTO);
        String chidldrenCodes = TreeUtils.getChidldrenIds(emrElementTree, emrElementDTO.getCode());
        if(!StringUtils.isEmpty(chidldrenCodes)){
          //给所有子节点修改状态
          String[] split = chidldrenCodes.split(",");
          List<String> list = new ArrayList<>(Arrays.asList(split));
          EmrElementDTO emrElementDTO1 = new EmrElementDTO();
          emrElementDTO1.setHospCode(emrElementDTO.getHospCode());
          emrElementDTO1.setCodes(list);
          emrElementDTO1.setIsValid(emrElementDTO.getIsValid());
          emrElementDAO.updateList(emrElementDTO1);
        }
      }
    }
    return emrElementDAO.update(emrElementDTO) > 0;
  }

  /**
  * @Menthod insert
  * @Desrciption 新增电子病历元素
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:19
  * @Return boolean
  **/
  public boolean insert(EmrElementDTO emrElementDTO) {
    //判断名称是否重复
    EmrElementDTO emrElementDTO1 = new EmrElementDTO();
    emrElementDTO1.setName(emrElementDTO.getName());
    emrElementDTO1.setHospCode(emrElementDTO.getHospCode());
    List<EmrElementDTO> emrElementDTOS = emrElementDAO.queryPageorAll(emrElementDTO1);
    if(!ListUtils.isEmpty(emrElementDTOS)){
      throw new AppException("元素名称重复");
    }
    // 生成编码
    Map map = new HashMap();
    map.put("hospCode",emrElementDTO.getHospCode());
    map.put("upCode",emrElementDTO.getUpCode());
    // 查询同一父级下编码最大的元素
    EmrElementDTO maxCode = emrElementDAO.getMaxCode(map);

    if(maxCode == null && !("-1".equals(emrElementDTO.getUpCode()))){
      emrElementDTO.setCode(emrElementDTO.getUpCode() + "01");
    } else if (maxCode == null && ("-1".equals(emrElementDTO.getUpCode()))) {
      emrElementDTO.setCode("emr01");
    } else {
      //判断该节点下子节点数量是否大于99
      String[] s = maxCode.getCode().split("emr");
      int result = Integer.parseInt(s[1]);
      if((result % 100) > 99 ){
        throw new AppException("子元素不能大于99个");
      }
      //化为整数时会去掉前面的0，在节点下加1生成新编码
		if (Integer.parseInt(s[1].substring(0,2)) >= 9) {
			emrElementDTO.setCode("emr" + String.valueOf(result + 1));
		} else {
			emrElementDTO.setCode("emr0" + String.valueOf(result + 1));
		}
      //emrElementDTO.setCode("emr0" + String.valueOf(result + 1));
    }
    // 校验当前病历结构化存储表的列数是否大于1000，大于1000则需要换表存储
//    String[] recordName = {"emr_patient_record", "emr_patient_record_1", "emr_patient_record_2", "emr_patient_record_3"};
//    String recordTableName = null;
//    // 遍历查询病历结构存储表列数是否超过1000
//    for (int i = 0; i < recordName.length; i++) {
//      int temp = emrElementDAO.getEmrPatientRecordColumns(recordName[i]);
//      if (temp < 197) {
//        recordTableName = recordName[i];
//        break;
//      }
//    }
//    if (recordTableName == null) {
//      throw new AppException("当前元素创建病历结构化存储表字段失败，请联系管理员排查");
//    }
    //将新增的元素作为字段扩充到 指定的病历结构化 表中
	  // TODO 2020年11月18日08:45:53
    //addColumn(emrElementDTO, recordTableName);
    //emrElementDTO.setRecordName("recordTableName");
    emrElementDTO.setId(SnowflakeUtils.getId());
    emrElementDTO.setCrteTime(DateUtils.getNow());
    return emrElementDAO.insert(emrElementDTO) > 0;
  }

  /**
  * @Menthod addColumn
  * @Desrciption 将新增的元素作为字段扩充到emr_patient表中
  *
  * @Param
  * []
  *
  * @Author jiahong.yang
  * @Date   2020/9/24 9:23
  * @Return void
  **/
  public void addColumn(EmrElementDTO emrElementDTO, String recordTableName) {
    Map map = new HashMap();
    map.put("columnName", emrElementDTO.getCode());
    map.put("recordTableName", recordTableName);
    if("5".equals("emrElementDTO.getTypeCode()")){
      map.put("type","timestamp");
    } else {
      map.put("type","text");
    }

    emrElementDAO.addColumn(map);
  }
  /**
  * @Menthod getEmrElementTree
  * @Desrciption 获取元素树
  *
  * @Param
  * [emrElementDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/19 11:17
  * @Return java.util.List<cn.hsa.base.TreeMenuNode>
  **/
  @Override
  public List<TreeMenuNode> getEmrElementTree(EmrElementDTO emrElementDTO) {
    return emrElementDAO.getEmrElementTree(emrElementDTO);
  }

  /**
   * @Menthod getEmrElementTree
   * @Desrciption 获取电子病历元素树(医保使用)
   *
   * @Param
   * [emrElementDTO]
   *
   * @Author jiguang.liao
   * @Date   2022/1/04 10:18
   * @Return java.util.List<cn.hsa.base.TreeMenuNode>
   **/
  @Override
  public List<TreeMenuNode> getInsureEmrElementTree(EmrElementDTO emrElementDTO) {
    return emrElementDAO.getInsureEmrElementTree(emrElementDTO);
  }

  /**
   * @param emrElementDTO
   * @Menthod getInsureDictEmrElementTree
   * @Desrciption 获取系统码表中电子病历元素树(医保使用)
   * @Param [emrElementDTO]
   * @Author jiguang.liao
   * @Date 2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.base.TreeMenuNode>>
   */
  @Override
  public List<TreeMenuNode> getInsureDictEmrElementTree(EmrElementDTO emrElementDTO) {
    return emrElementDAO.getInsureDictEmrElementTree(emrElementDTO);
  }

  /**
   * @param emrElementMatchDO
   * @Menthod queryInsureEmrElementMatchInfo
   * @Desrciption 获取元素匹配关系(医保使用)
   * @Param [emrElementMatchDO]
   * @Author jiguang.liao
   * @Date 2022/1/04 10:18
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.base.EmrElementMatchDO>>
   */
  @Override
  public PageDTO queryInsureEmrElementMatchInfo(EmrElementMatchDO emrElementMatchDO) {
    PageHelper.startPage(emrElementMatchDO.getPageNo(), emrElementMatchDO.getPageSize());
    return PageDTO.of(emrElementDAO.queryInsureEmrElementMatchInfoPage(emrElementMatchDO));
  }

  /**
   * @param emrElementMatchDO
   * @Menthod saveInsureMatch
   * @Desrciption 保存病历元素匹配信息
   * @Param [emrElementMatchDO]
   * @Author jiguang.liao
   * @Date 2022/01/05 10:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @Override
  public WrapperResponse<Boolean> saveInsureMatch(EmrElementMatchDO emrElementMatchDO) {

    // 校验是否已经匹配
    int count = emrElementDAO.selectInsureEmrCode(emrElementMatchDO);
    if (count > 0) {
       throw new AppException(emrElementMatchDO.getInsureEmrName() + "【" + emrElementMatchDO.getInsureEmrCode() + "】 已完成匹配！");
    }

    // 校验医院病历元素是否为末级元素
    int hospCount = emrElementDAO.selectHospEmrUpCode(emrElementMatchDO);
    if (hospCount > 0) {
      throw new AppException("医院病历元素" + emrElementMatchDO.getEmrElementName() + "【" + emrElementMatchDO.getEmrElementCode() + "】 非末级元素！");
    }

    // 校验医院病历元素是否为末级元素
    int insureCount = emrElementDAO.selectInsureEmrUpCode(emrElementMatchDO);
    if (insureCount > 0) {
      throw new AppException("医保病历元素" + emrElementMatchDO.getInsureEmrName() + "【" + emrElementMatchDO.getInsureEmrCode() + "】 非末级元素！");
    }

    // 新增匹配关系
    emrElementMatchDO.setId(SnowflakeUtils.getId());
    emrElementMatchDO.setCrteTime(DateUtils.getNow());
    return WrapperResponse.success(emrElementDAO.saveInsureMatch(emrElementMatchDO) > 0);
  }

  /**
   * @param map
   * @Menthod deleteInsureMatch
   * @Desrciption 保存病历元素匹配信息
   * @Param [map]
   * @Author jiguang.liao
   * @Date 2022/01/05 10:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @Override
  public WrapperResponse<Boolean> deleteInsureMatch(Map map) {
    return WrapperResponse.success(emrElementDAO.deleteInsureMatch(map) > 0);
  }
}
