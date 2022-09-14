package cn.hsa.platform.dao;

import cn.hsa.platform.domain.MessageInfoModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.message.dao
 * @Class_name: MessageInfoDAO
 * @Describe:
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2021/12/14 19:10
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Mapper
public interface MessageInfoDao extends BaseMapper<MessageInfoModel> {

   /**
    * 新增消息
    */
   int insertMessageInfo(MessageInfoModel messageInfoDTO);

   /**
    * 批量新增消息
    */
   int insertMessageInfoBatch(List<MessageInfoModel> messageInfoDTO);
   /**
    * 更新消息
    */
   int updateMssageInfo(MessageInfoModel messageInfoDTO);

   /**
    * 删除消息
    */
   int deleteMessageInfo(MessageInfoModel messageInfoDTO);
   /**
    * 批量删除消息
    */
   int deleteMessageInfoBatch(MessageInfoModel messageInfoDTO);

   /**
    * 查询消息列表
    */
   List<MessageInfoModel> queryMessageInfoByType(MessageInfoModel messageInfoDTO);

   /**
    * 查询自己未读消息列表
    */
   List<MessageInfoModel> queryUnReadMessageInfoList(MessageInfoModel messageInfoDTO);

   /**
    * 批量更新消息
    */
   int updateMssageInfoBatch(MessageInfoModel messageInfoDTO);

   /**
    * 根据消息id批量更新消息
    */
   int updateMssageInfoBatchById(MessageInfoModel messageInfoDTO);

   int updateMssageInfoBatchByMsgId(List<MessageInfoModel> messageInfoModels);

   int updateMssageInfoById(MessageInfoModel messageInfoDTO);


   /**
    * 查询历史消息列表
    */
   List<MessageInfoModel> queryHistoryMessageInfoList(MessageInfoModel messageInfoDTO);
   /**
    * 查询系统消息列表
    */
   List<MessageInfoModel> querySysMessageInfoList(MessageInfoModel messageInfoDTO);

   /**
    * 查询推送个人消息列表
    */
   List<MessageInfoModel> queryPersonalMessageInfoByType(MessageInfoModel messageInfoDTO);

   /**
    *  根据医院编码查询该医院待推送的所有类型的数据
    * @param messageInfoDTO
    * @return
    */
   List<MessageInfoModel> queryAllMessageInfoByHospCode(MessageInfoModel messageInfoDTO);



}
