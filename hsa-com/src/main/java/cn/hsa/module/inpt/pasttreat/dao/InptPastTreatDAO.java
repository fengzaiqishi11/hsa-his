package cn.hsa.module.inpt.pasttreat.dao;

import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.pasttreat.dto.*;
import cn.hsa.module.inpt.pasttreat.entity.InptPastTreatDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (InptPastTreat)表数据库访问层
 *
 * @author makejava
 * @since 2020-09-17 14:13:05
 */
public interface InptPastTreatDAO {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InptPastTreatDO queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<InptPastTreatDO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param inptPastTreatDO 实例对象
     * @return 对象列表
     */
    List<InptPastTreatDO> queryAll(InptPastTreatDO inptPastTreatDO);

    /**
     * 新增数据
     *
     * @param inptPastTreatDO 实例对象
     * @return 影响行数
     */
    int insert(InptPastTreatDO inptPastTreatDO);

    /**
     * 修改数据
     *
     * @param inptPastTreatDO 实例对象
     * @return 影响行数
     */
    int update(InptPastTreatDO inptPastTreatDO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);


    /**
     * @Method queryInptPastOperationDTOPage()
     * @Desrciption 既往手术史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    List<InptPastOperationDTO> queryInptPastOperationDTOPage(InptPastOperationDTO inptPastOperationDTO);

    /**
     * @Method queryInptPastDrugDTOPage()
     * @Desrciption 既往用药史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    List<InptPastDrugDTO> queryInptPastDrugDTOPage(InptPastDrugDTO inptPastDrugDTO);

    /**
     * @Method queryInptPastAllergyDTOPage()
     * @Desrciption 既往过敏史分页查询
     * @Param 过敏史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    List<InptPastAllergyDTO> queryInptPastAllergyDTOPage(InptPastAllergyDTO inptPastAllergyDTO);

    /**
     * @Method queryPastTreatPage()
     * @Desrciption 既往诊疗史分页查询
     * @Param 既往诊疗史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    List<InptPastTreatDTO> queryPastTreatPage(InptPastTreatDTO inptPastTreatDTO);

    /**
     * @Method queryInptPastAdrsPage()
     * @Desrciption 不良反应史分页查询
     * @Param 不良反应史数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 15:58
     * @Return pageDTO
     **/
    List<InptPastAdrsDTO> queryInptPastAdrsPage(InptPastAdrsDTO inptPastTreatDTO);

    /**
     * @Method insertInptPastAdrs
     * @Desrciption 新增不良反应史
     * @Param InptPastAllergyDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    int insertInptPastAdrs(InptPastAdrsDTO inptPastTreatDTO);

    /**
     * @Method insertPastTreat
     * @Desrciption 新增既往诊疗史记录
     * @Param inptPastTreatDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    int insertPastTreat(InptPastTreatDTO inptPastTreatDTO);

    /**
     * @Method insertInptPastOperation
     * @Desrciption 新增既往手术史记录
     * @Param iinptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    int insertInptPastOperation(InptPastOperationDTO inptPastOperationDTO);


    /**
     * @Method insertInptPastDrug
     * @Desrciption 新增既往用药史史记录
     * @ParamInptPastDrugDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    int insertInptPastDrug(InptPastDrugDTO inptPastDrugDTO);

    /**
     * @Method insertInptPastAllergy
     * @Desrciption 新增既往过敏史记录
     * @Param InptPastAllergyDTO数据传输对象
     * @Author fuhui
     * @Date 2020/9/17 21:28
     * @Return boolean
     **/
    int insertInptPastAllergy(InptPastAllergyDTO inptPastAllergyDTO);

    /**
     * @Method queryDrugPage()
     * @Desrciption 分页查询药品的名称 和对应的频率
     * @Param
     * @Author fuhui
     * @Date 2020/9/22 13:58
     * @Return BaseDrugDTO 药品数据传输对象
     **/
    List<BaseDrugDTO> queryDrugPage(BaseDrugDTO baseDrugDTO);

    /**
     * @Method deletePastTreat()
     * @Desrciption 删除既往诊疗史的记录数据
     * @Param id:既往诊疗史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean deletePastTreat(InptPastTreatDTO inptPastTreatDTO);

    /**
     * @Method updatePastTreat()
     * @Desrciption 修改既往诊疗史的记录数据
     * @Param inptPastTreatDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean updatePastTreat(InptPastTreatDTO inptPastTreatDTO);

    /**
     * @Method deleteInptPastAdrs()
     * @Desrciption 删除不良反应史的记录数据
     * @Param id:不良反应史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean deleteInptPastAdrs(InptPastAdrsDTO inptPastAdrsDTO);

    /**
     * @Method updateInptPastAdrs()
     * @Desrciption 修改不良反应史的记录数据
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean updateInptPastAdrs(InptPastAdrsDTO inptPastAdrsDTO);

    /**
     * @Method deleteInptPastAdrs()
     * @Desrciption 删除不良反应史的记录数据
     * @Param id:不良反应史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean deleteInptPastAllergy(InptPastAllergyDTO inptPastAllergyDTO);

    /**
     * @Method updateInptPastAllergy()
     * @Desrciption 修改不良反应史的记录数据
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean updateInptPastAllergy(InptPastAllergyDTO inptPastAllergyDTO);

    /**
     * @Method deleteInptPastOperation()
     * @Desrciption 删除既往手术史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean deleteInptPastOperation(InptPastOperationDTO inptPastOperationDTO);

    /**
     * @Method updateInptPastOperation()
     * @Desrciption 修改手术史记录
     * @Param InptPastOperationDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean updateInptPastOperation(InptPastOperationDTO inptPastOperationDTO);


    /**
     * @Method updateInptPastDrug()
     * @Desrciption 修改既往用药史史记录
     * @Param InptPastAdrsDTO数据传输对象
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean updateInptPastDrug(InptPastDrugDTO inptPastDrugDTO);

    /**
     * @Method deleteInptPastDrug()
     * @Desrciption 删除既往用药史记录
     * @Param id:既往用药史的id
     * @Author fuhui
     * @Date 2020/11/3 9:45
     * @Return boolean
     **/
    boolean deleteInptPastDrug(InptPastDrugDTO inptPastDrugDTO);

    /**
     * @Method getPastTreatById()
     * @Desrciption  根据id查询既往诊疗史的数据
     * @Param id：数据ID
     *
     * @Author fuhui
     * @Date   2020/11/3 14:29
     * @Return InptPastTreatDTO数据传输对象
     **/
    InptPastTreatDTO getPastTreatById(InptPastTreatDTO inptPastTreatDTO);

    /**
     * @Method getInptPastAdrsById()
     * @Desrciption  根据id查找不良不反应史记录
     * @Param id:不良不反应史数据id
     *
     * @Author fuhui
     * @Date   2020/11/3 14:32
     * @Return 不良不反应史数据传输对象
     **/
    InptPastAdrsDTO getInptPastAdrsById(InptPastAdrsDTO inptPastAdrsDTO);

    /**
     * @Method getInptPastAllergyById()
     * @Desrciption  根据id查找既往过敏史数据
     * @Param id:既往过敏史数据Id
     *
     * @Author fuhui
     * @Date   2020/11/3 14:35
     * @Return 既往过敏史数据传输对象
     **/
    InptPastAllergyDTO getInptPastAllergyById(InptPastAllergyDTO inptPastAllergyDTO);

    /**
     * @Method getInptPastDrugById()
     * @Desrciption  根据id查找既往用药史数据
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/3 14:38
     * @Return 既往用药史数据传输对象
     **/
    InptPastDrugDTO getInptPastDrugById(InptPastDrugDTO inptPastDrugDTO);

    /**
     * @Method getInptPastOperationById()
     * @Desrciption  根据id查找既往手术史记录
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/3 14:40
     * @Return 既往手术史数据传输对象
     **/
    InptPastOperationDTO getInptPastOperationById(InptPastOperationDTO inptPastOperationDTO);
}