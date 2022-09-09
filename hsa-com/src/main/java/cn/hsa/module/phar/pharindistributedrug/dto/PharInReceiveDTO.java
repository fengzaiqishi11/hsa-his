package cn.hsa.module.phar.pharindistributedrug.dto;

import cn.hsa.module.phar.pharinbackdrug.entity.PharInReceiveDO;
import cn.hsa.module.stro.stock.dto.StroStockDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
* @Package_name: cn.hsa.module.phar.pharoutdistributedrug.entity
* @class_name: PharOutReceiveDO
* @Description: 门诊领药申请表DTO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/9/2 15:58
* @Company: 创智和宇
**/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PharInReceiveDTO extends PharInReceiveDO implements Serializable {

    private static final long serialVersionUID = 402755114841777914L;

    // 用于搜索身份证，姓名，手机号的关键字
    private String keyword;
    // 申请科室名称
    private String deptName;
    // 发药窗口名称
    private String windowName;
    //创建时间
    private String createDate;
    //配药时间
    private String assignDate;
    //类型
    private String status;
    //搜索日期
    private String queryDate;
    //单据类型
    private String orderReceiveName;
    //是否查所有的单据类型
    private String queryFlag;
    // 就诊id
    private String visitId;

    private String canclePremedication;
    // 配药明细
    private List<PharInReceiveDetailDTO> pharInReceiveDetailDTOList;
    // 返回数据
    List<StroStockDetailDTO> resultList;
    // 返回标识：警告0,或者错误1
    String flag;
}
