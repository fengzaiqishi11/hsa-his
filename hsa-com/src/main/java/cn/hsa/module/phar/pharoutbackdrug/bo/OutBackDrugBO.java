package cn.hsa.module.phar.pharoutbackdrug.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;

/**
 * @Package_name: cn.hsa.module.stro.outbackdrug.bo
 * @Class_name: OutBackDrugBO
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/29 17:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutBackDrugBO {

    /**
    * @Menthod queryOutBackDrugPeoplePage
     * @Desrciption  通过姓名和身份证以及手机号查询门诊发药单的人
     * @param pharOutDistributeDTO  门诊发药表数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/29 17:27
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryOutBackDrugPeoplePage(PharOutDistributeDTO pharOutDistributeDTO);

    /**
     * @Menthod queryOutBackDrugDetailPage
     * @Desrciption  通过发药id来查询所有的发药单详情
     * @param pharOutDistributeDTO  门诊发药表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/29 17:19
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryOutBackDrugDetailPage(PharOutDistributeDTO pharOutDistributeDTO);

    /**
     * @Menthod updateBackNumAndInsertDistribute
     * @Desrciption  门诊退药，更新费用表退药数量，更新原发药记录退药数量，新增一条负记录到发药表
     * @param pharOutDistributeDTO 门诊发药详细表数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/30 10:48
     * @Return boolean
     **/
    boolean updateBackNumAndInsertDistribute(PharOutDistributeDTO pharOutDistributeDTO);
}
