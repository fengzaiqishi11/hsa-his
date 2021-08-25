package cn.hsa.module.inpt.doctor.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailDTO;

import java.util.List;


/**
 *@Package_name: cn.hsa.module.inpt.dao
 *@Class_name: InptAdviceDetailDao
 *@Describe: 医嘱明细数据访问层
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptAdviceDetailDAO {

    /**
    * @Method queryInptAdviceDetailById
    * @Desrciption 单个查询
    * @param inptAdviceDetailDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:47
    * @Return cn.hsa.module.inpt.dto.InptAdviceDetailDTO
    **/
    InptAdviceDetailDTO getInptAdviceDetailById(InptAdviceDetailDTO inptAdviceDetailDTO);

    /**
    * @Method queryInptAdviceDetailPage
    * @Desrciption 分页查询
    * @param inptAdviceDetailDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:47
    * @Return java.util.List<cn.hsa.module.inpt.dto.InptAdviceDetailDTO>
    **/
    List<InptAdviceDetailDTO> queryInptAdviceDetailPage(InptAdviceDetailDTO inptAdviceDetailDTO);

    /**
    * @Method insertInptAdviceDetail
    * @Desrciption 单个新增
    * @param inptAdviceDetailDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:47
    * @Return int
    **/
    int insertInptAdviceDetail(InptAdviceDetailDTO inptAdviceDetailDTO);

    /**
    * @Method updateInptAdviceDetail
    * @Desrciption 单个更新
    * @param inptAdviceDetailDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:47
    * @Return int
    **/
    int updateInptAdviceDetail(InptAdviceDetailDTO inptAdviceDetailDTO);

    /**
    * @Method: getAdviceDetails
    * @Description: 根据医嘱ID获取医嘱明细表
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/17 14:01
    * @Return: List<InptAdviceDetailDTO>
    **/
    List<InptAdviceDetailDTO> getAdviceDetails(String hospCode, List<String> adviceIds);

    /**
     * @Method insertInptAdviceDetail
     * @Desrciption 批量新增
     * @param list
     * @Author liuqi1
     * @Date   2020/9/1 20:47
     * @Return int
     **/
    int insertInptAdviceDetails(List<InptAdviceDetailDTO> list);

    /**
    * @Method: queryAdviceDetailsToLongCost
    * @Description: 获取需要滚动长期费用的医嘱明细集合(长期医嘱、已核收、未停止、在院病人、拒收标志正常)
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/10/31 15:32
    * @Return:
    **/
    List<InptAdviceDetailDTO> queryAdviceDetailsToLongCost(String hospCode);

    /**
    * @Method: getAdviceDetailByAdviceId
    * @Description: 根据医嘱ID获取医嘱明细记录
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/12/11 11:27
    * @Return:
    **/
    List<InptAdviceDetailDTO> getAdviceDetailByAdviceId(String hospCode, String adviceId);
}