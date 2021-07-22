package cn.hsa.module.inpt.advancepay.dao;

import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dao
 * @Class_name: BaseAdviceDAO
 * @Describe: 预交金信息数据访问层接口
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/9/8 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptAdvancePayDAO {

    /**
     * @Menthod getById
     * @Desrciption 根据主键Id查询预交金信息
     * @param inptAdvancePayDTO  主键ID等参数
     * @Author xingyu.xie
     * @Date   2020/9/8 15:31
     * @Return inptAdvancePayDTO
     **/
    InptAdvancePayDTO getById(InptAdvancePayDTO inptAdvancePayDTO);

    /**
     * @Menthod queryAll
     * @Desrciption 查询全部预交金信息
     * @param inptAdvancePayDTO  医院编码等实体筛选条件
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return java.util.List<cn.hsa.module.base.bad.dto.inptAdvancePayDTO>
     **/
    List<InptAdvancePayDTO> queryAll(InptAdvancePayDTO inptAdvancePayDTO);

    /**
     * @Menthod insert
     * @Desrciption 新增单条预交金信息
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return int
     **/
    int insert(InptAdvancePayDTO inptAdvancePayDTO);


    /**
     * @Menthod update
     * @Desrciption 修改单条预交金信息（有非空判断）
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return int
     **/
    int updateInptAdvancePay(InptAdvancePayDTO inptAdvancePayDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条预交金信息（无非空判断）
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return int
     **/
    int updateInptAdvancePayS(InptAdvancePayDTO inptAdvancePayDTO);


    /**
     * @Menthod delete
     * @Desrciption   根据主键ID等参数删除预交金信息
     * @param inptAdvancePayDTO  预交金信息表主键id列表等参数
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return int
     **/
    int deleteById(InptAdvancePayDTO inptAdvancePayDTO);

    /**
     * @Menthod queryAdvancePay
     * @Desrciption 预交金查询接口
     * @param inptVisitDTO 住院病人dto
     * @Author luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2020/9/9 11:29
     * @Return java.util.list<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    List<InptVisitDTO> queryAdvancePay(InptVisitDTO inptVisitDTO);
    
    /**
     * @Menthod editAdvancePayByIds
     * @Desrciption 根据ids修改预交金信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/30 16:41 
     * @Return int 受影响行数
     */
    int editAdvancePayByIds(Map param);
}
