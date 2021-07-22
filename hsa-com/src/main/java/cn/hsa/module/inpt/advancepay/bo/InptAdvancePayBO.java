package cn.hsa.module.inpt.advancepay.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.advancepay.dto.InptAdvancePayDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.base.bfc.bo
 * @Class_name: InptAdvancePayBO
 * @Describe: 预交金信息业务逻辑实现层接口
 * @Author: xingyu.xie
 * @Date: 2020/7/7 16:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptAdvancePayBO {

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
     * @Method queryPatientInfoPage
     * @Desrciption 分页查询患者信息
     * @param inptVisitDTO
     * @Author xingyu.xie
     * @Date   2020/9/4 10:33
     * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
     **/
    PageDTO queryPatientInfoPage(InptVisitDTO inptVisitDTO);

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
     * @Menthod queryPage
     * @Desrciption 查询全部预交金信息分页
     * @param inptAdvancePayDTO  医院编码等实体筛选条件
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return java.util.List<cn.hsa.module.base.bad.dto.inptAdvancePayDTO>
     **/
    PageDTO queryPage(InptAdvancePayDTO inptAdvancePayDTO);

    /**
     * @Menthod insert
     * @Desrciption 新增单条预交金信息
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return int
     **/
    boolean insert(InptAdvancePayDTO inptAdvancePayDTO);


    /**
     * @Menthod flushingRed
     * @Desrciption 多条预交金冲红
     * @param inptAdvancePayDTOList  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/7/14 16:18
     * @Return
     **/
    boolean updateFlushingRed(List<InptAdvancePayDTO> inptAdvancePayDTOList);

    /**
     * @Menthod update
     * @Desrciption 修改单条预交金信息（有非空判断）
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return int
     **/
    boolean updateInptAdvancePay(InptAdvancePayDTO inptAdvancePayDTO);

    /**
     * @Menthod update
     * @Desrciption 修改单条预交金信息（无非空判断）
     * @param inptAdvancePayDTO  预交金分类数据对象
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return int
     **/
    boolean updateInptAdvancePayS(InptAdvancePayDTO inptAdvancePayDTO);


    /**
     * @Menthod delete
     * @Desrciption   根据主键ID等参数删除预交金信息
     * @param inptAdvancePayDTO  预交金信息表主键id列表等参数
     * @Author xingyu.xie
     * @Date   2020/9/8 16:05
     * @Return int
     **/
    boolean deleteById(InptAdvancePayDTO inptAdvancePayDTO);

    /**
     * @Menthod queryAdvancePay
     * @Desrciption 预交金查询接口
     * @param inptVisitDTO 住院病人dto
     * @Author luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date 2020/9/9 11:29
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryAdvancePay(InptVisitDTO inptVisitDTO);
}
