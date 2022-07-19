package cn.hsa.module.phar.pharinbackdrug.dao;

import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 *@Package_name: cn.hsa.module.phar.pharinbackdrug.dao
 *@Class_name: PharInWaitReceiveDao
 *@Describe: 住院待领
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/21 15:32
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PharInWaitReceiveDAO {

    /**
    * @Method getPharInWaitReceiveById
    * @Desrciption 单个查询
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/21 15:41
    * @Return cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO
    **/
    PharInWaitReceiveDTO getPharInWaitReceiveById(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method queryPharInWaitReceives
    * @Desrciption 批量查询
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/27 13:50
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryPharInWaitReceivess(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method queryPharInWaitReceive
    * @Desrciption 分页查询
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/21 15:42
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryPharInWaitReceivePage(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method queryPharInWaitReceive
    * @Desrciption 根据条件查询出待领信息集合
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/9/7 10:43
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryPharInWaitReceive(PharInWaitReceiveDTO pharInWaitReceiveDTO);
    List<PharInWaitReceiveDTO> queryPharInWaitReceives(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    List<PharInWaitReceiveDTO> queryPharInWaitReceiveToIsBack(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method insertPharInWaitReceive
    * @Desrciption 单个新增
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/21 15:42
    * @Return int
    **/
    int insertPharInWaitReceive(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method insertPharInWaitReceive
    * @Desrciption 批量新增
    * @param pharInWaitReceiveDTOs
    * @Author liuqi1
    * @Date   2020/8/25 15:01
    * @Return int
    **/
    int insertPharInWaitReceiveBatch(List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs);

    /**
    * @Method updatePharInWaitReceive
    * @Desrciption 单个更新
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/21 15:42
    * @Return int
    **/
    int updatePharInWaitReceive(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method deletePharInWaitReceive
    * @Desrciption 通过主键删除
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/21 15:43
    * @Return int
    **/
    int deletePharInWaitReceive(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method deletePharInWaitReceiveBatch
    * @Desrciption 通过主键批量删除
    * @param pharInWaitReceiveDTOs
    * @Author liuqi1
    * @Date   2020/8/28 17:07
    * @Return int
    **/
    int deletePharInWaitReceiveBatch(@Param("list") List<PharInWaitReceiveDTO> pharInWaitReceiveDTOs);


    /**
    * @Method queryWaitReceiveGroupByDeptId
    * @Desrciption 按申请科室分组查询出待退药的信息
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 13:59
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryWaitReceiveGroupByDeptId(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method queryWaitReceiveByDeptId
    * @Desrciption 按项目id分组查询出科室待退药的信息
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 15:00
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryWaitReceiveGroupByItemIdPage(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method queryWaitReceiveByDeptId
    * @Desrciption 查询出申请科室的退药明细
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 15:08
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryWaitReceiveByDeptIdPage(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method queryWaitReceiveByIds
    * @Desrciption 根据待领id集合批量出查询出退药信息
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 16:25
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryWaitReceiveByIds(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method queryWaitReceiveByItemIds
    * @Desrciption 根据项目id集合批量出查询出退药信息
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 16:27
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryWaitReceiveByItemIds(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method updatePharInWaitReceiveBatch
    * @Desrciption 批量修改发药状态
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2020/8/28 17:04
    * @Return int
    **/
    int updatePharInWaitReceiveBatch(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    int updateInWaitStatusByWrIds(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Menthod updateUrgentMedicine
    * @Desrciption 批量修改为紧急领药
    *
    * @Param
    * [pharInWaitReceiveDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/1/22 11:07
    * @Return int
    **/
    int updateUrgentMedicine(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method: getWaitReceiveByCost
     * @Description: 根据相关参数查询领药申请记录
     * @Param: [pharInWaitReceiveDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 14:03
     * @Return: cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO
     **/
    PharInWaitReceiveDTO getWaitReceiveByCost(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
     * @Method queryPharInWaitReceiveApply
     * @Desrciption 科室领药待领药品
       @params [pharInWaitReceiveDTO]
     * @Author chenjun
     * @Date   2020/9/24 19:11
     * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryPharInWaitReceiveApply(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    /**
    * @Method updateCostIdBatch
    * @Desrciption 批量更新待领表的费用明细id
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/11/9 20:25
    * @Return int
    **/
    int updateCostIdBatch(@Param("list")List<InptCostDTO> inptCostDTOs);

    /**未退药查询
    * @Method queryBackDrugPage
    * @Desrciption
    * @param pharInWaitReceiveDTO
    * @Author liuqi1
    * @Date   2021/4/23 11:03
    * @Return java.util.List<cn.hsa.module.phar.pharinbackdrug.dto.PharInWaitReceiveDTO>
    **/
    List<PharInWaitReceiveDTO> queryBackDrugPage(PharInWaitReceiveDTO pharInWaitReceiveDTO);

    List<Map<String,Object>> queryAllVisit(PharInWaitReceiveDTO pharInWaitReceiveDTO);
}
