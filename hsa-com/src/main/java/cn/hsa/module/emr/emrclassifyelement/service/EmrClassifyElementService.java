package cn.hsa.module.emr.emrclassifyelement.service;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrelement.service
 * @Class_name: EmrElementServcie
 * @Describe: 电子病历元素管理业务传输接口
 * @Author: xiexingyu
 * @Email: xingyu.xie@powersi.com
 * @Date: 2020/9/17 16:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-emr")
public interface EmrClassifyElementService {


   /**
   * @Menthod queryAll
   * @Desrciption  根据条件筛选电子病历文档元素管理表中的数据
    * @param map
   * @Author xingyu.xie
   * @Date   2020/9/28 10:13
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.emr.emrclassifyelement.dto.EmrClassifyElementDTO>>
   **/
   @GetMapping("/queryAll")
   WrapperResponse<List<EmrClassifyElementDTO>> queryAll(Map map);

   /**
   * @Menthod save
   * @Desrciption 修改文档分类已勾选的元素节点
    * @param map
   * @Author xingyu.xie
   * @Date   2020/9/28 10:13
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   **/
   @PostMapping("/save")
   WrapperResponse<Boolean> save(Map map);

   /**
   * @Menthod queryTreeByEmrClassify
   * @Desrciption  根据文档分类已选择的元素分类节点生成树
    * @param map
   * @Author xingyu.xie
   * @Date   2020/9/28 10:12
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
   @GetMapping("/queryTreeByEmrClassify")
   WrapperResponse<List<TreeMenuNode>> queryTreeByEmrClassify(Map map);

   /**
   * @Menthod queryTreeIsAble
   * @Desrciption  根据文档分类code查询元素分类树和已勾选的节点
    * @param map
   * @Author xingyu.xie
   * @Date   2020/9/28 10:12
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
   **/
   @GetMapping("/queryTreeIsAble")
   WrapperResponse<List<TreeMenuNode>> queryTreeIsAble(Map map);

   /**
    * @Menthod queryEmrClassifyCodesByElementCodes
    * @Desrciption  根据元素编码查询出所有的文档分类编码
    * @param map 医院编码，元素编码list
    * @Author xingyu.xie
    * @Date   2020/10/9 11:27
    * @Return java.util.List<java.lang.String>
    **/
   @GetMapping("/queryEmrClassifyCodesByElementCodes")
   WrapperResponse<List<String>> queryEmrClassifyCodesByElementCodes(Map map);


}
