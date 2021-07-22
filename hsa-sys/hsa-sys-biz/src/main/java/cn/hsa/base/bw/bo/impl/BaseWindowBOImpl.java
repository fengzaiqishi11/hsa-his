package cn.hsa.base.bw.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bw.bo.BaseWindowBO;
import cn.hsa.module.base.bw.dao.BaseWindowDAO;
import cn.hsa.module.base.bw.dto.BaseWindowDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * @Package_name: cn.hsa.base.bmm.bo.impl
 * @Class_name: BaseWindowManagementBOImpl
 * @Describe: 发药窗口业务逻辑实现层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 16:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseWindowBOImpl extends HsafBO implements BaseWindowBO {

    /**
    * @Menthod
    * @Desrciption 注入Dao层对象
     * @param null
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return
    **/
    @Resource
    BaseWindowDAO baseWindowDAO;

    /**
     * 单据消费者
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
    * @Menthod getById
     * @Desrciption 根据主键Id，医院编码等参数查询发药窗口
     * @param BaseWindowDTO  主键ID 医院编码等参数
    * @Author xingyu.xie
    * @Date   2020/7/17 15:41
    * @Return cn.hsa.module.base.bmm.dto.BaseWindowDTO
    **/
    @Override
    public BaseWindowDTO getById(BaseWindowDTO BaseWindowDTO) {
        return this.baseWindowDAO.getById(BaseWindowDTO);
    }

    /**
    * @Menthod queryAll
    * @Desrciption  查询全部发药窗口
     * @param baseWindowDTO 医院编码
    * @Author xingyu.xie
    * @Date   2020/7/14 21:02
    * @Return java.util.List<cn.hsa.module.base.bmm.dto.BaseWindowDTO>
    **/
    @Override
    public List<BaseWindowDTO> queryAll(BaseWindowDTO baseWindowDTO) {
        return this.baseWindowDAO.queryPage(baseWindowDTO);
    }

    /**
    * @Menthod queryPage
    * @Desrciption   根据数据对象分页查询发药窗口
     * @param BaseWindowDTO 发药窗口数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:42
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPage(BaseWindowDTO BaseWindowDTO) {
        // 设置分页信息
        PageHelper.startPage(BaseWindowDTO.getPageNo(),BaseWindowDTO.getPageSize());
        List<BaseWindowDTO> baseWindowDTOList = baseWindowDAO.queryPage(BaseWindowDTO);
        return  PageDTO.of(baseWindowDTOList);
    }

    /**
    * @Menthod insert
    * @Desrciption   新增单条发药窗口
     * @param baseWindowDTO 发药窗口数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:42
    * @Return boolean
    **/
    @Override
    public boolean insert(BaseWindowDTO baseWindowDTO) {
        if (this.baseWindowDAO.isNameExist(baseWindowDTO)!=0){
            throw new AppException("发药窗口名重复,请重新输入！");
        }
        HashMap map = new HashMap();
        map.put("hospCode",baseWindowDTO.getHospCode());
        map.put("typeCode", Constants.ORDERRULE.FYCK);
        WrapperResponse<String> orderNo = baseOrderRuleService_consumer.getOrderNo(map);
        baseWindowDTO.setCode(orderNo.getData());
        baseWindowDTO.setId(SnowflakeUtils.getId());
        baseWindowDTO.setCrteTime(DateUtils.getNow());

        return this.baseWindowDAO.insert(baseWindowDTO)>0;
    }

    /**
    * @Menthod update
    * @Desrciption   修改单条发药窗口
     * @param baseWindowDTO 发药窗口数据对象
    * @Author xingyu.xie
    * @Date   2020/7/17 15:43
    * @Return boolean
    **/
    @Override
    public boolean update(BaseWindowDTO baseWindowDTO) {
        if (this.baseWindowDAO.isNameExist(baseWindowDTO)!=0){
            throw new AppException("发药窗口名重复,请重新输入！");
        }
        return this.baseWindowDAO.update(baseWindowDTO)>0;
    }

    /**
    * @Menthod delete
    * @Desrciption   根据主键ID，和医院编码等参数，删除一个或多个发药窗口
     * @param baseWindowDTO
    * @Author xingyu.xie
    * @Date   2020/7/17 15:43
    * @Return boolean
    **/
    @Override
    public boolean delete(BaseWindowDTO baseWindowDTO) {
        return this.baseWindowDAO.delete(baseWindowDTO)>0;
    }

    /**
     * @Menthod queryByDeptId
     * @Desrciption  查询当前登录科室的发药窗口
     * @param baseWindowDTO 发药窗口表的数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/31 10:01
     * @Return java.util.List<cn.hsa.module.base.bw.dto.BaseWindowDTO>
     **/
    @Override
    public List<BaseWindowDTO> queryByDeptId(BaseWindowDTO baseWindowDTO) {
        return this.baseWindowDAO.queryByDeptId(baseWindowDTO);
    }
}
