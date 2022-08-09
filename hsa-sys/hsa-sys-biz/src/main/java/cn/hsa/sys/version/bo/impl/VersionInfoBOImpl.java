package cn.hsa.sys.version.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.filter.BloomFilterHelper;
import cn.hsa.filter.RedisBloomFilter;
import cn.hsa.module.sys.message.dao.UserReadMessageDAO;
import cn.hsa.module.sys.message.dto.UserReadMessageDTO;
import cn.hsa.module.sys.version.dao.VersionInfoDAO;
import cn.hsa.module.sys.version.bo.VersionInfoBO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hsa.module.sys.version.dto.VersionInfoDTO;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @ClassName VersionInfoBOImpl
* @Deacription 服务层
* @Author liuhuiming
* @Date 2022-02-10
* @Version 1.0
**/
@Component
public class VersionInfoBOImpl extends HsafBO implements VersionInfoBO {

     @Autowired
     private VersionInfoDAO versionInfoDAO;
     /**
      *  默认消息表中存取100w条数据
      */
     private long expectedVersionMessagesNums = 1000000L;
     /**
      *  用户信息已读服务
      */
     @Resource
     UserReadMessageDAO userReadMessageDAO;

     /**
      *  布隆过滤器实现数据去重,默认100w长度
      */
     @Autowired
     RedisBloomFilter redisBloomFilter;

     @Autowired
     private BloomFilterHelper<String> bloomFilterHelper;

     /**
      *  默认布隆过滤器名称
      */
     private static final String DEFAULT_BLOOMFILTER_NAME = "bloom";
     @Override
     public PageDTO queryVersionInfoListByPage(VersionInfoDTO versionInfoDTO) {
          // 设置分页信息
          PageHelper.startPage(versionInfoDTO.getPageNo(), versionInfoDTO.getPageSize());
          List<VersionInfoDTO> versionInfoList = versionInfoDAO.queryPage(versionInfoDTO);
          return PageDTO.of(versionInfoList);
     }

     @Override
     public boolean saveVersionInfo(VersionInfoDTO versionInfoDTO) {
          if(StringUtils.isEmpty(versionInfoDTO.getId())){
               versionInfoDTO.setId(SnowflakeUtils.getId());
          }
          int insert = versionInfoDAO.insert(versionInfoDTO);
          return insert > 0;
     }

     @Override
     public VersionInfoDTO queryVersionInfo() {
          VersionInfoDTO versionInfo = versionInfoDAO.queryVersionInfo();
          return versionInfo;
     }

     /**
      * 查询历史升级公告消息 默认分页5条
      *   按照消息已读状态与创建时间排序，优先消息读取状态
      * @param versionInfoDTO 分页参数信息
      * @return
      */
     @Override
     @SuppressWarnings("unchecked")
     public PageDTO queryHistoryVersionInfo(VersionInfoDTO versionInfoDTO) {

          // 历史消息列表
          // hospCode+':'+userId+':'+messageId;

          List<VersionInfoDTO> allVersionInfoDTOList = versionInfoDAO.queryHistoryVersionInfo(versionInfoDTO);
          AtomicInteger count = new AtomicInteger(); // 未读消息总数量
          allVersionInfoDTOList.forEach(versionInfoDTO1 -> {
               String bloomKey = versionInfoDTO.getHospCode()+':'+versionInfoDTO.getUserId()+':'+versionInfoDTO1.getId();
               if(redisBloomFilter.includeByBloomFilter(bloomFilterHelper,DEFAULT_BLOOMFILTER_NAME,bloomKey)){
                    versionInfoDTO1.setMsgStatus(Integer.valueOf(Constants.SF.S));
               }else{
                    count.incrementAndGet();
                    versionInfoDTO1.setMsgStatus(Integer.valueOf(Constants.SF.F));
               }
          });
          // 按照消息已读状态与创建时间排序，优先消息读取状态
          allVersionInfoDTOList = allVersionInfoDTOList.stream().sorted(
                  Comparator.comparing(VersionInfoDTO::getMsgStatus).
                          thenComparing(VersionInfoDTO::getCreateTime,Comparator.reverseOrder())
          ).collect(Collectors.toList());

          return PageDTO.ofByManual(allVersionInfoDTOList,versionInfoDTO.getPageNo(),5, count.get());
     }

     /**
      * 批量更新 系统公告信息状态为已读
      *
      * @param map 参数
      * @return
      */
     @Override
     public Boolean updateStatus2Read(Map<String, Object> map) {
          String messageIdsStr = MapUtils.get(map,"messageIds");
          List<String> messageIdList = Arrays.asList(messageIdsStr.split(","));
          String hospCode = MapUtils.get(map,"hospCode");
          String userId = MapUtils.get(map,"userId");
          UserReadMessageDTO userReadMessageDTO = null;
          int affectRows = 0;
          // 1.插入userRead表
          for(String messageId : messageIdList){
               userReadMessageDTO = new UserReadMessageDTO();
               userReadMessageDTO.setUserId(userId);
               userReadMessageDTO.setHospCode(hospCode);
               userReadMessageDTO.setMessageType(Constants.MESSAGETYPE.ANNOUNCEMENT);
               userReadMessageDTO.setMessageId(messageId);
               userReadMessageDTO.setMessageStatus(Constants.SF.S);
               affectRows += userReadMessageDAO.insertUserReadMessage(userReadMessageDTO);
               String bloomKey = hospCode+':'+userId+':'+messageId;
               // 2.更新布隆过滤器
               redisBloomFilter.addByBloomFilter(bloomFilterHelper,DEFAULT_BLOOMFILTER_NAME,bloomKey);
          }

          return affectRows > 0 ;
     }
}
