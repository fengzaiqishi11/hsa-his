package cn.hsa.module.center.insure.item.bo;

import cn.hsa.base.PageDTO;

import cn.hsa.module.insure.module.dto.InsureItemDTO;

import java.util.Map;

/***
 *  中心端医保项目疾病业务层
 *
 * @author fuhui
 */
public interface CenterInsureItemDiseaseBO {

    /**
     *  分页查询医保项目信息
     * @param insureItemDTO
     * @return
     */
    PageDTO queryInsureItemPage(InsureItemDTO insureItemDTO);

    /**
     *  <p>同步医保平台疾病以及项目数据
     *  <p>根据下载类型的版本号：分页下载数据
     *  <p> num: 下载第几页       size:下载每页数据量的大小
     * @param map 请求参数
     * @return java.util.Map
     */
    Map<String, Object> syncItemDiseaseFromUnifiedPlatform(Map<String, Object> map) ;
}
