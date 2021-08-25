package cn.hsa.module.inpt.nurse.dao;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 *@Package_name: cn.hsa.module.inpt.dao
 *@Class_name: InptAdviceExecDao
 *@Describe: 医嘱执行数据访问层
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptAdviceExecDAO {

    /**
    * @Method queryInptAdviceExecById
    * @Desrciption 单个查询
    * @param InptAdviceExecDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:42
    * @Return cn.hsa.module.inpt.dto.InptAdviceExecDTO
    **/
    InptAdviceExecDTO getInptAdviceExecById(InptAdviceExecDTO InptAdviceExecDTO);

    /**
    * @Method queryInptAdviceExecPage
    * @Desrciption 分页查询
    * @param InptAdviceExecDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:42
    * @Return java.util.List<cn.hsa.module.inpt.dto.InptAdviceExecDTO>
    **/
    List<InptAdviceExecDTO> queryInptAdviceExecPage(InptAdviceExecDTO InptAdviceExecDTO);

    /**
    * @Method queryInptAdvicePage
    * @Desrciption 分页查询医嘱相关信息
    * @param InptAdviceExecDTO
    * @Author liuqi1
    * @Date   2020/9/17 19:21
    * @Return java.util.List<cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO>
    **/
    List<InptAdviceExecDTO> queryInptAdvicePage(InptAdviceExecDTO InptAdviceExecDTO);


    /**
    * @Method insertInptAdviceExec
    * @Desrciption 单个新增
    * @param InptAdviceExecDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:42
    * @Return int
    **/
    int insertInptAdviceExec(InptAdviceExecDTO InptAdviceExecDTO);

    /**
     * @Method insertInptAdviceExec
     * @Desrciption 批量新增
     * @param inptAdviceExecDTOList
     * @Author liuqi1
     * @Date   2020/9/1 20:42
     * @Return int
     **/
    int insertInptAdviceExecBatch(@Param("inptAdviceExecDTOList") List<InptAdviceExecDTO> inptAdviceExecDTOList);

    /**
    * @Method updateInptAdviceExec
    * @Desrciption 单个更新
    * @param InptAdviceExecDTO
    * @Author liuqi1
    * @Date   2020/9/1 20:42
    * @Return int
    **/
    int updateInptAdviceExec(InptAdviceExecDTO InptAdviceExecDTO);

    /**
    * @Method updateInptAdviceExecBatch
    * @Desrciption 医嘱执行批量修改
    * @param InptAdviceExecDTOs
    * @Author liuqi1
    * @Date   2020/9/2 17:11
    * @Return int
    **/
    int updateInptAdviceExecBatch(List<InptAdviceExecDTO> InptAdviceExecDTOs);

    /**
    * @Menthod updateInptAdviceExecBatchCancel
    * @Desrciption 医嘱执行取消还原医嘱执行
    *
    * @Param
    * [InptAdviceExecDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/1/13 14:33
    * @Return int
    **/
    int updateInptAdviceExecBatchCancel(List<InptAdviceExecDTO> InptAdviceExecDTOs);

    /**
    * @Menthod queryAdviceExecLately
    * @Desrciption 查询该医嘱得最近执行时间
    *
    * @Param
    * [InptAdviceExecDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/1/13 15:40
    * @Return java.util.List<cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO>
    **/
    InptAdviceExecDTO queryAdviceExecLately(InptAdviceExecDTO InptAdviceExecDTOs);

    /**
    * @Method intupdateInptAdviceExecBeCancelBatch
    * @Desrciption 取消医嘱执行
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/11/12 20:46
    * @Return
    **/
    int updateInptAdviceExecBeCancelBatch(@Param("list") List<InptCostDTO> inptCostDTOs);

    /**
     * @Method: getNotExec
     * @Description: 根据医嘱ID获取医嘱执行的记录
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 20:32
     * @Return: List<InptAdviceExecDTO>
     **/
    List<InptAdviceExecDTO> getExecs(List<String> adviceIds, String hospCode);

    /**
    * @Method: getNotExec
    * @Description: 获取停嘱时间之后的未执行的记录
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/15 20:32
    * @Return: List<InptAdviceExecDTO>
    **/
    List<InptAdviceExecDTO> getDRNotExec(InptAdviceExecDTO inptAdviceExecDTO);

    /**
     * @Method: getNotExec
     * @Description: 获取停嘱时间当日未执行的记录
     * @Param:
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 20:32
     * @Return: List<InptAdviceExecDTO>
     **/
    List<InptAdviceExecDTO> getDRZHNotExec(InptAdviceExecDTO inptAdviceExecDTO);

    /**
    * @Method: queryExecEqm
    * @Description: 获取未签名的执行记录
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/22 11:12
    * @Return:
    **/
    List<InptAdviceExecDTO> queryExecEqm(String id, String hospCode, String qmzt);

    /**
    * @Method: queryExecNum
    * @Description: 根据医嘱ID获取医嘱执行记录
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/9/22 11:26
    * @Return:
    **/
    List<InptAdviceExecDTO> queryExecByAdviceId(String adviceId, String hospCode,String time);

    void deleteByParams(@Param("kcbzList") List<Map<String, String>> kcbzList);

    List<InptAdviceDTO> queryPsList(@Param("inptAdviceExecDTOs") List<InptAdviceExecDTO> inptAdviceExecDTOs,String hospCode);

    void updateIsPositive(@Param("adviceList") List<InptAdviceDTO> adviceList);
}
