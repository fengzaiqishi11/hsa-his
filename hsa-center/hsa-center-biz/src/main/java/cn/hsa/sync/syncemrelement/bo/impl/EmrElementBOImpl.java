package cn.hsa.sync.syncemrelement.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sync.syncemrelement.bo.SyncEmrElementBO;
import cn.hsa.module.sync.syncemrelement.dao.SyncEmrElementDAO;
import cn.hsa.module.sync.syncemrelement.dto.SyncEmrElementDTO;
import cn.hsa.util.*;
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
public class EmrElementBOImpl extends HsafBO implements SyncEmrElementBO {
  @Resource
  private SyncEmrElementDAO emrElementDAO;

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
  public SyncEmrElementDTO getByIdorCode(SyncEmrElementDTO emrElementDTO) {
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
  * @Return java.util.List<cn.hsa.module.emr.emrelement.dto.SyncEmrElementDTO>
  **/
  @Override
  public List<SyncEmrElementDTO> queryAll(SyncEmrElementDTO emrElementDTO) {
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
  public boolean save(SyncEmrElementDTO emrElementDTO) {
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
  public boolean update(SyncEmrElementDTO emrElementDTO) {
    //判断修改的元素是不是父级节点
    if("0".equals(emrElementDTO.getIsEnd()) && "0".equals(emrElementDTO.getIsValid())){
      //查询数据库的有效状态
      SyncEmrElementDTO byIdorCode = emrElementDAO.getByIdorCode(emrElementDTO);
      //如果状态不一样,则修改该父节点下所有子节点的有效状态
      if(!byIdorCode.getIsValid().equals(emrElementDTO.getIsValid())){

        //查出该父节点下所有子节点的值
        List<TreeMenuNode> emrElementTree = getEmrElementTree(emrElementDTO);
        String chidldrenCodes = TreeUtils.getChidldrenIds(emrElementTree, emrElementDTO.getCode());
        if(!StringUtils.isEmpty(chidldrenCodes)){
          //给所有子节点修改状态
          String[] split = chidldrenCodes.split(",");
          List<String> list = new ArrayList<>(Arrays.asList(split));
          SyncEmrElementDTO emrElementDTO1 = new SyncEmrElementDTO();
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
  public boolean insert(SyncEmrElementDTO emrElementDTO) {
    //判断名称是否重复
    SyncEmrElementDTO emrElementDTO1 = new SyncEmrElementDTO();
    emrElementDTO1.setName(emrElementDTO.getName());
    List<SyncEmrElementDTO> emrElementDTOS = emrElementDAO.queryPageorAll(emrElementDTO1);
    if(!ListUtils.isEmpty(emrElementDTOS)){
      throw new AppException("元素名称重复");
    }
    // 生成编码
    Map map = new HashMap();
    map.put("upCode",emrElementDTO.getUpCode());
    // 查询同一父级下编码最大的元素
    SyncEmrElementDTO maxCode = emrElementDAO.getMaxCode(map);

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
  public void addColumn(SyncEmrElementDTO emrElementDTO, String recordTableName) {
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
  public List<TreeMenuNode> getEmrElementTree(SyncEmrElementDTO emrElementDTO) {
    return emrElementDAO.getEmrElementTree(emrElementDTO);
  }
}
