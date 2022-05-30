package cn.hsa.sys.code.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrclassifyelement.service.EmrClassifyElementService;
import cn.hsa.module.emr.emrclassifytemplate.dto.EmrClassifyTemplateDTO;
import cn.hsa.module.emr.emrclassifytemplate.service.EmrClassifyTemplateService;
import cn.hsa.module.emr.emrelement.service.EmrElementServcie;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.module.sys.code.bo.SysCodeBO;
import cn.hsa.module.sys.code.dao.SysCodeDao;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
* @Package_name: cn.hsa.sys.code.bo.impl
* @class_name: SysCodeBOImpl
* @Description: 值域代码BO实现层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/15 11:38
* @Company: 创智和宇
**/
@Component
@Slf4j
public class SysCodeBOImpl extends HsafBO implements SysCodeBO {

    /**
     * 值域代码数据库访问接口
     */
    @Resource
    private SysCodeDao sysCodeDao;

    @Resource
    private EmrElementServcie emrElementServcie_consumer;

    @Resource
    private EmrClassifyElementService emrClassifyElementServcie_consumer;

    @Resource
    private EmrClassifyTemplateService emrClassifyTemplateService_consumer;

    @Resource
    RedisUtils redisUtils;

    /**
     * @Method: getCodes
     * @Description: 根据编码获取值域代码值
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     **/
    @Override
    public Map<String, List<SysCodeSelectDTO>> getByCode(String code, String hospCode) {
        List<String> codeList = new ArrayList<>();
        if(!StringUtils.isEmpty(code)){
            //Long数组转List<Long>集合
            codeList = Arrays.asList(code.split(","));
        }
        List<SysCodeDTO> list = sysCodeDao.getByCode(codeList, hospCode);
        if (!ListUtils.isEmpty(list)) {
            return list.stream().collect(HashMap::new,(map,p)->map.put(p.getCCode(),p.getSysCodeSelectDTOList()),Map::putAll);
        }
        return null;
    }

    /**
     * @Method: queryCodePage
     * @Description: 分页获取值域代码列表
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:25
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public PageDTO queryCodePage(SysCodeDTO sysCodeDTO) {
        if (!StringUtils.isEmpty(sysCodeDTO.getKeyword())) {
            sysCodeDTO.setKeyword(sysCodeDTO.getKeyword().toUpperCase());
        }
        // 设置分页信息
        PageHelper.startPage(sysCodeDTO.getPageNo(), sysCodeDTO.getPageSize());

        // 查询所有
        List<SysCodeDTO> list = sysCodeDao.queryCodePage(sysCodeDTO);

        // 返回分页结果
        return PageDTO.of(list);
    }

    /**
     * @Method: queryCodeDetailPage
     * @Description: 分页获取值域代码值列表
     * @Param: [sysCodeDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 15:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public PageDTO queryCodeDetailPage(SysCodeDetailDTO sysCodeDetailDTO) {
        // 设置分页信息
        PageHelper.startPage(sysCodeDetailDTO.getPageNo(), sysCodeDetailDTO.getPageSize());
        if(null == sysCodeDetailDTO.getCode()){
            sysCodeDetailDTO.setCode("null");
        }
        // 查询所有
        List<SysCodeDetailDTO> list = sysCodeDao.queryCodeDetailPage(sysCodeDetailDTO);

        // 返回分页结果
        return PageDTO.of(list);
    }

    /**
    * @Menthod queryCodeDetailAll
    * @Desrciption 获取全部值域代码值列表
    *
    * @Param
    * [sysCodeDetailDTO]
    *
    * @Author jiahong.yang
    * @Date   2020/10/15 14:25
    * @Return java.util.List<cn.hsa.module.sys.code.dto.SysCodeDetailDTO>
    **/
    @Override
    public List<SysCodeDetailDTO> queryCodeDetailAll(SysCodeDetailDTO sysCodeDetailDTO) {
      return sysCodeDao.queryCodeDetailPage(sysCodeDetailDTO);
    }

  /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.SysCodeDTO>
     **/
    @Override
    public SysCodeDTO getCodeById(String id,String hospCode) {
        return sysCodeDao.getCodeById(id,hospCode);
    }

    /**
     * @Method: getCodeById
     * @Description: 根据主键获取值域代码值对象
     * @Param: [id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:01
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.code.dto.SysCodeDTO>
     **/
    @Override
    public SysCodeDetailDTO getCodeDetailById(String id,String hospCode) {
        return sysCodeDao.getCodeDetailById(id,hospCode);
    }

    /**
    * @Menthod getMaxSeqno
    * @Desrciption 获取最大序号
    *
    * @Param
    * [code, hospCode]
    *
    * @Author jiahong.yang
    * @Date   2020/9/1 10:24
    * @Return int
    **/
    @Override
    public int getMaxSeqno(String code,String hospCode) {
      return sysCodeDao.getMaxSeqno(code,hospCode);
    }

  /**
     * @Method: insertCode
     * @Description: 新增值域代码
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean saveCode(SysCodeDTO sysCodeDTO) {

        sysCodeDTO.setCode(sysCodeDTO.getCode().toUpperCase());
        //根据编码查询数据,校验编码不能重复
        if(sysCodeDao.getCodeCount(sysCodeDTO.getCode(),sysCodeDTO.getHospCode(),sysCodeDTO.getId()) > 0){
            throw new AppException("编码已存在");
        }

        if(!StringUtils.isEmpty(sysCodeDTO.getName())){
            //处理拼音码
            sysCodeDTO.setPym(PinYinUtils.toFirstPY(sysCodeDTO.getName()));
            //处理五笔码
            sysCodeDTO.setWbm(WuBiUtils.getWBCode(sysCodeDTO.getName()));
        }

        //新增
        if(StringUtils.isEmpty(sysCodeDTO.getId())){
            sysCodeDTO.setId(SnowflakeUtils.getId());
            sysCodeDTO.setCrteTime(DateUtils.getNow());
            int i = sysCodeDao.insertCode(sysCodeDTO);
            return i > 0;
        } else {
            //修改
            if(StringUtils.isEmpty(sysCodeDTO.getCode()) || StringUtils.isEmpty(sysCodeDTO.getOldCode())){
              throw new AppException("参数不能为空");
            }

            //缓存操作  ---处理detail
            List<SysCodeDetailDTO> newDetail = new ArrayList<>();
            if(!sysCodeDTO.getCode().equals(sysCodeDTO.getOldCode())){
                SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
                sysCodeDetailDTO.setHospCode(sysCodeDTO.getHospCode());
                sysCodeDetailDTO.setCode(sysCodeDTO.getOldCode());
                List<SysCodeDetailDTO> sysCodeDetailDTOS = sysCodeDao.queryCodeDetailPage(sysCodeDetailDTO);
                List<String> ids = sysCodeDetailDTOS.stream().map(SysCodeDetailDTO::getId).collect(Collectors.toList());

                // 更新值域代码明细
                sysCodeDao.updateCodeDetailByCode(sysCodeDTO.getCode(),sysCodeDTO.getOldCode(),sysCodeDTO.getHospCode());

                sysCodeDetailDTO.setIds(ids);
                newDetail = sysCodeDao.queryCodeDetailPage(sysCodeDetailDTO);
            }
            if(!ListUtils.isEmpty(newDetail)){
//                cacheDetailOperate(newDetail,sysCodeDTO.getHospCode(),true);
            }

            int i = sysCodeDao.updateCode(sysCodeDTO);
            return i > 0;
        }
    }

    /**
     * @Method: insertCode
     * @Description: 新增值域代码值
     * @Param: [sysCodeDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:07
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean saveCodeDetail(SysCodeDetailDTO sysCodeDetailDTO) {
        if(StringUtils.isEmpty(sysCodeDetailDTO.getId())){
            sysCodeDetailDTO.setId("");
        }
        //根据编码查询数据,校验值不能重复
        if(sysCodeDao.getCodeDetailCount(sysCodeDetailDTO.getCode(),sysCodeDetailDTO.getHospCode(),sysCodeDetailDTO.getId(),sysCodeDetailDTO.getValue()) > 0){
            throw new RuntimeException("值域值已存在("+sysCodeDetailDTO.getValue()+")");
        }

        if(!StringUtils.isEmpty(sysCodeDetailDTO.getName())){
            //处理拼音码
            sysCodeDetailDTO.setPym(PinYinUtils.toFirstPY(sysCodeDetailDTO.getName()));
            //处理五笔码
            sysCodeDetailDTO.setWbm(WuBiUtils.getWBCode(sysCodeDetailDTO.getName()));
        }

        List<SysCodeDetailDTO>sysCodeDetailDTOS = new ArrayList<>();
        // 新增
        if(StringUtils.isEmpty(sysCodeDetailDTO.getId())){
            sysCodeDetailDTO.setId(SnowflakeUtils.getId());
            sysCodeDetailDTO.setCrteTime(DateUtils.getNow());
            int i = sysCodeDao.insertCodeDetail(sysCodeDetailDTO);

            // 缓存操作
            sysCodeDetailDTOS.clear();
            sysCodeDetailDTOS.add(sysCodeDetailDTO);
//            cacheDetailOperate(sysCodeDetailDTOS,sysCodeDetailDTO.getHospCode(),true);

            return i > 0;
        } else {
            //修改
            int i = sysCodeDao.updateCodeDetail(sysCodeDetailDTO);

            //缓存操作
            sysCodeDetailDTOS.clear();
            sysCodeDetailDTOS.add(sysCodeDetailDTO);
//            cacheDetailOperate(sysCodeDetailDTOS,sysCodeDetailDTO.getHospCode(),true);

            return i > 0;
        }
    }

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean deleteCodes(SysCodeDTO sysCodeDTO) {
        int i = sysCodeDao.deleteCodes(sysCodeDTO);
        return i > 0;
    }

    /**
     * @Method: deleteCodes
     * @Description: 批量删除值域代码值
     * @Param: [idStr]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean deleteCodeDetails(SysCodeDetailDTO sysCodeDetailDTO) {
        //缓存操作
        List<SysCodeDetailDTO> sysCodeDetailDTOS = sysCodeDao.queryCodeDetailPage(sysCodeDetailDTO);
//        cacheDetailOperate(sysCodeDetailDTOS,sysCodeDetailDTO.getHospCode(),false);

        int i = sysCodeDao.deleteCodeDetails(sysCodeDetailDTO);
        return i > 0;
    }

    /**
     * @Method: getCodeTree
     * @Description: 根据编码获取值域代码值树形结构
     * @Param: [code]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/15 11:25
     * @Return: java.util.List
     *
     * @return*/
    @Override
    public List<TreeMenuNode> getCodeTree(String code, String hospCode) {
        return sysCodeDao.getCodeTree(code, hospCode);
    }

    /**
     * @Method queryNationCode()
     * @Desrciption  维护科室信息的国家编码
     * @Param hospCode医院编码 nationCode 国家编码值
     *
     * @Author fuhui
     * @Date   2020/10/30 0:32
     * @Return TreeMenuNode树集合
     **/
    @Override
    public List<TreeMenuNode> queryNationCode(SysCodeDetailDTO sysCodeDetailDTO) {
        return sysCodeDao.queryNationCode(sysCodeDetailDTO);
    }

    /**
     * @Method queryFathersCode
     * @Desrciption 生成自定义码表树（用于级联）
     * @Param
     * [sysCodeDetailDTO]
     * @Author liaojunjie
     * @Date   2020/11/25 10:19
     * @Return java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    @Override
    public List<TreeMenuNode> queryFathersCode(SysCodeDetailDTO sysCodeDetailDTO) {
        return sysCodeDao.queryFathersCode(sysCodeDetailDTO);
    }

    /**
     * @Method queryFathers
     * @Desrciption 根据子代查询祖宗的编码(返回值包括子节点的值)
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/11/25 10:18
     * @Return java.util.List<java.lang.String>
     **/
    @Override
    public List<String> queryFathers(Map map) {
        List<TreeMenuNode> treeMenuNodeList = this.queryFathersCode(MapUtils.get(map, "sysCodeDetailDTO"));
        List fatherList = new ArrayList();
        findParentTreeNode(treeMenuNodeList,  MapUtils.get(map, "value"),fatherList);
        Collections.reverse(fatherList);
        return fatherList;
    }

    /**
     * @Method: getInsureDictByCode
     * @Description: 根据医保值域代码获取值域代码值
     * @Param: [map]
     * @Author: liaojiguang
     * @Email: 847025096@qq.com
     * @Date: 2020/12/17 21:31
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.util.List<InsureDictDTO>>>
     **/
    @Override
    public Map<String, List<InsureDictDTO>> getInsureDictByCode(String code, String hospCode) {
        List<String> codeList = new ArrayList<>();
        if(!StringUtils.isEmpty(code)){
            //Long数组转List<Long>集合
            codeList = Arrays.asList(code.split(","));
        }
        List<InsureDictDTO> insureDictDTOList = sysCodeDao.getInsureDictByCode(codeList,hospCode);
        Map<String, List<InsureDictDTO>> map = new HashMap<>();
        if(!ListUtils.isEmpty(insureDictDTOList)) {
            for(InsureDictDTO dto1:insureDictDTOList) {
                if(StringUtils.isEmpty(dto1.getCode())) {
                    continue;
                }
                List<InsureDictDTO> insureCodeList = new ArrayList<>();
                for(InsureDictDTO dto2:insureDictDTOList) {
                    if (dto1.getCode().equals(dto2.getCode())) {
                        insureCodeList.add(dto2);
                    }
                }
                map.put(dto1.getCode(),insureCodeList);
            }
        }
        return map;
    }

    /**
     * @Menthod: getCodeDetailByValue
     * @Desrciption: 根据值域代码值查询出单个具体的值域代码值对象
     * @Param: sysCodeDetailDTO[c_code, value] c_code值域代码 value代码值
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-05-17 16:10
     * @Return: SysCodeDetailDTO
     **/
    @Override
    public SysCodeDetailDTO getCodeDetailByValue(SysCodeDetailDTO sysCodeDetailDTO) {
        if (StringUtils.isEmpty(sysCodeDetailDTO.getCode())){
            throw new RuntimeException("未获取到值域代码");
        }
        if (StringUtils.isEmpty(sysCodeDetailDTO.getValue())){
            throw new RuntimeException("未获取到值域代码值");
        }
        return sysCodeDao.getCodeDetailByValue(sysCodeDetailDTO);
    }
    /**
     * @Author gory
     * @Description 启用作废的值域明细
     * @Date 2022/5/9 16:32
     * @Param [sysCodeDetailDTO]
     **/
    @Override
    public Boolean updateStatus(SysCodeDetailDTO sysCodeDetailDTO) {
        // 查看启用的值域是否已经存在
        sysCodeDetailDTO.setIsValid(Constants.SF.F);
        List<SysCodeDetailDTO> codeDetailByIds = sysCodeDao.getCodeDetailByIds(sysCodeDetailDTO);
        if (!ListUtils.isEmpty(codeDetailByIds)){
            SysCodeDetailDTO newCodeDetailDTO = new SysCodeDetailDTO();
            List<String> valueList = codeDetailByIds.stream().map(SysCodeDetailDTO::getValue).collect(Collectors.toList());
            newCodeDetailDTO.setValueList(valueList);
            newCodeDetailDTO.setHospCode(sysCodeDetailDTO.getHospCode());
            newCodeDetailDTO.setCode(codeDetailByIds.get(0).getCode());
            newCodeDetailDTO.setIsValid(Constants.SF.S);
            List<SysCodeDetailDTO> codeDetails = sysCodeDao.getCodeDetailByValues(newCodeDetailDTO);
            List<String> erroMessageList = codeDetails.stream().map(SysCodeDetailDTO::getValue).collect(Collectors.toList());
            if (!ListUtils.isEmpty(erroMessageList)){
                throw new AppException("以下值域明细已存在" + String.join(",", erroMessageList));
            }
        }

        int i = sysCodeDao.updateStatus(sysCodeDetailDTO);
        return i >0;
    }

    /**
     * @Method findParentTreeNode
     * @Desrciption 根据子代迭代找祖宗
     * @Param
     * [treeMenuNodeList, child, fatherList]
     * @Author liaojunjie
     * @Date   2020/11/25 10:18
     * @Return java.util.List<java.lang.String>
     **/
    private List<String> findParentTreeNode(List<TreeMenuNode> treeMenuNodeList, String child,List<String> fatherList ) {
        for(TreeMenuNode node:treeMenuNodeList){
            if(child.equals(node.getId())){
                if(StringUtils.isNotEmpty((node.getParentId()))){
                    fatherList.add(child);
                    findParentTreeNode(treeMenuNodeList,node.getParentId(),fatherList);
                }else {
                    fatherList.add(child);
                }
                break;
            }
        }
        return fatherList;
    }

    /**
    * @Menthod changeEmr
    * @Desrciption  增删改码表明细回写电子病历文档
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2020/10/9 10:40
    * @Return void
    **/
    private void changeEmr(SysCodeDetailDTO sysCodeDetailDTO){
      Map map = new HashMap();
      map.put("hospCode",sysCodeDetailDTO.getHospCode());
      map.put("sysCodeRef",sysCodeDetailDTO.getCode());
      // 查询用了该编码的电子病历元素
      WrapperResponse<List<String>> listWrapperResponse = emrElementServcie_consumer.queryElementCodes(map);
      List<String> elementCodes = listWrapperResponse.getData();
      map.put("list",elementCodes);
      //查询用了该元素的所有电子病历文档编码
      if(!ListUtils.isEmpty(elementCodes)) {
        WrapperResponse<List<String>> listWrapperResponse1 = emrClassifyElementServcie_consumer.queryEmrClassifyCodesByElementCodes(map);
        List<String> classInfoCodes = listWrapperResponse1.getData();
        // 根据电子病历编码拿出电子病历文件
        if(!ListUtils.isEmpty(classInfoCodes)){
          EmrClassifyTemplateDTO emrClassifyTemplateDTO = new EmrClassifyTemplateDTO();
          emrClassifyTemplateDTO.setHospCode(sysCodeDetailDTO.getHospCode());
          emrClassifyTemplateDTO.setCodes(classInfoCodes);
          Map map1 = new HashMap();
          map1.put("hospCode",sysCodeDetailDTO.getHospCode());
          map1.put("emrClassifyTemplateDTO",emrClassifyTemplateDTO);
          WrapperResponse<List<EmrClassifyTemplateDTO>> listWrapperResponse2 = emrClassifyTemplateService_consumer.queryTemplateAll(map1);
          readHtml(listWrapperResponse2.getData());
        }
      }
    }

    /**
    * @Menthod readHtml
    * @Desrciption 读取文件并且修改回写
    *
    * @Param
    * []
    *
    * @Author jiahong.yang
    * @Date   2020/10/9 11:57
    * @Return void
    **/
    private void readHtml(List<EmrClassifyTemplateDTO> emrClassifyTemplateDTOS){
      for (int i = 0; i < emrClassifyTemplateDTOS.size(); i++) {
        byte[] templateHtml = emrClassifyTemplateDTOS.get(i).getTemplateHtml();
        String s = new String(templateHtml);
//        System.out.println("开始" + s);
      }
    }

    public void cacheDetailOperate(List<SysCodeDetailDTO> sysCodeDetailDTOS, String hospCode, Boolean operation){
        if (!ListUtils.isEmpty(sysCodeDetailDTOS)) {
            String key = StringUtils.createKey("codeDetail", hospCode, sysCodeDetailDTOS.get(0).getCode());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, sysCodeDetailDTOS);
            }
        }
    }
}
