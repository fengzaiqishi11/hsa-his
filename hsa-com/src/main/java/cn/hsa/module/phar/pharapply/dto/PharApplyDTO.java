package cn.hsa.module.phar.pharapply.dto;

import cn.hsa.module.phar.pharapply.entity.PharApplyDO;
import cn.hsa.module.phar.pharapply.entity.StroOut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.phar.pharapply
 * @class_name: PharApplyDTO
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/28 16:31
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PharApplyDTO extends PharApplyDO implements Serializable {
    private static final long serialVersionUID = 971521238073517862L;
    private String keyword;
    //搜索关键字
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    //开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;         //结束日期
    private String flag;              //判断是审核还是作废标识  0未审核 1。审核 2。作废
    private List<PharApplyDetailDTO> pharApplyDetailDTOS;    //出库明细信息列表
    private List<PharApplyDetailDTO> detailList;   // 保存明细数据
    private List<PharApplyDetailDTO> addDetailList ; // 保存要新增的明细数据
    private List<String> ids;        //批量审核和作废
    private List<StroOut> stroOutinList;
    private String outCode ;

    private String applyDeptName;  //领药科室名称

    private String limitFlag;
}
