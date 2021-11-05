package cn.hsa.outpt.skin.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.skin.bo.OutptSkinBO;
import cn.hsa.module.outpt.skin.dao.OutptSkinDAO;
import cn.hsa.module.outpt.skin.dto.OutptSkinDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.skin.bo.impl
 * @Class_name:: OutptSkinBOImpl
 * @Description: 皮试处理BO层实现类
 * @Author: zhangxuan
 * @Date: 2020-08-14 10:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class OutptSkinBOImpl extends HsafBO implements OutptSkinBO {
    /**
     * 皮试处理数据库访问接口
     */
    @Resource
    private OutptSkinDAO outptSkinDAO;

    /**
     * 码表服务
     */
    @Resource
    private SysCodeService sysCodeService_consumer;

    /**
     * @Method
     * @Desrciption  根据条件分页查询皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 15:20
     * @Return outptSkinDTOS
    **/
    @Override
    public PageDTO queryPage(OutptSkinDTO outptSkinDTO) {
        //设置分页信息
        PageHelper.startPage(outptSkinDTO.getPageNo(),outptSkinDTO.getPageSize());
        //根据条件查询
        List<OutptSkinDTO> outptSkinDTOS = outptSkinDAO.queryPage(outptSkinDTO);
        System.out.println(outptSkinDTOS);

        return PageDTO.of(outptSkinDTOS);
    }
    /**
     * @Method 根据条件查询皮试处理结果
     * @Desrciption
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 15:21
     * @Return outptSkinDTOS
    **/
    @Override
    public List<OutptSkinDTO> queryAll(OutptSkinDTO outptSkinDTO) {
        List<OutptSkinDTO> outptSkinDTOS = outptSkinDAO.queryAll(outptSkinDTO);
        return outptSkinDTOS;
    }
    /**
     * @Method getById
     * @Desrciption  根据主键查询皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:01
     * @Return outptSkinDTOS
    **/
    @Override
    public OutptSkinDTO getById(OutptSkinDTO outptSkinDTO) {
        OutptSkinDTO outptSkinDTOS = outptSkinDAO.getById(outptSkinDTO);
        return outptSkinDTOS;
    }
    /**
     * @Method insert
     * @Desrciption  新增皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:01
     * @Return outptSkinDTO
     **/
    @Override
    public Boolean insert(OutptSkinDTO outptSkinDTO) {
        return save(outptSkinDTO);
    }
    /**
     * @Method update
     * @Desrciption  编辑皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:01
     * @Return outptSkinDTO
     **/
    @Override
    public Boolean update(OutptSkinDTO outptSkinDTO) {
        return save(outptSkinDTO);
    }
    /**
     * @Method delete
     * @Desrciption  删除皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:01
     * @Return outptSkinDTO
     **/
    @Override
    public Boolean delete(OutptSkinDTO outptSkinDTO) {
        if(StringUtils.isEmpty(outptSkinDTO.getHospCode())){
            return false;
        }
        return outptSkinDAO.delete(outptSkinDTO) > 0;
    }
    /**
     * @Method save
     * @Desrciption  新增或者编辑皮试处理结果
     * @Param
     * outptSkinDTO
     * @Author zhangxuan
     * @Date   2020-08-14 17:01
     * @Return outptSkinDTO
     **/
    private Boolean save(OutptSkinDTO outptSkinDTO) {
        //更改处方明细表中的是否阳性字段
        outptSkinDAO.updateS(outptSkinDTO);
        //根据处方明细id查询单个对象
        OutptPrescribeDetailsDTO prescribeDetailsDTO = outptSkinDAO.getPreDetailById(outptSkinDTO);
        //将皮试结果修改处方明细表处方内容中
        SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
        sysCodeDetailDTO.setHospCode(outptSkinDTO.getHospCode());
        sysCodeDetailDTO.setCode("PSJG");
        sysCodeDetailDTO.setValue(outptSkinDTO.getSkinResultCode());
        Map map = new HashMap<>();
        map.put("hospCode", outptSkinDTO.getHospCode());
        map.put("sysCodeDetailDTO", sysCodeDetailDTO);
        List<SysCodeDetailDTO> data = sysCodeService_consumer.queryCodeDetailAll(map).getData();
        String name = null;
        String content = null;
        if (!ListUtils.isEmpty(data)){
            name = "("+ data.get(0).getName() +")";
        }
        if (prescribeDetailsDTO != null && StringUtils.isNotEmpty(prescribeDetailsDTO.getContent())){
            content = prescribeDetailsDTO.getContent();
        }
        if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(content)){
            List<String> list = Arrays.asList(content.split(" "));
            if (list.size() > 2){
                content = list.get(0) + " " + list.get(1) + " " + name;
            } else if (list.size() == 2){
                content = content + " " + name;
            }
            prescribeDetailsDTO.setContent(content);
        }
        //将添加皮试结果后的处方内容回写到处方明细表中
        outptSkinDAO.updatePreDetail(prescribeDetailsDTO);

        if(StringUtils.isEmpty(outptSkinDTO.getId())){
            outptSkinDTO.setId(SnowflakeUtils.getId());
            if(StringUtils.isEmpty(String.valueOf(outptSkinDTO.getExecDate()))) {
              outptSkinDTO.setExecDate(DateUtil.getDate());
            }
            return outptSkinDAO.insert(outptSkinDTO) > 0;
        }else{
            return outptSkinDAO.update(outptSkinDTO) > 0;
        }
    }
}
