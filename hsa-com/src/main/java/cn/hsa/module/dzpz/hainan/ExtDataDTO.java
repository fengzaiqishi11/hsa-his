package cn.hsa.module.dzpz.hainan;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Description: TODO
 * @Author: 医保开发二部-湛康
 * @Date: 2022-04-20 16:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtDataDTO {

  // 结算信息
  List<SetlInfoDTO>  setlinfo;

  //结算基金分项信息
  List<SetlDetailDTO>  setldetail;

}
