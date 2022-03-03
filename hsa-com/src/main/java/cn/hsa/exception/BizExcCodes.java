package cn.hsa.exception;

/**
 * @author powersi
 */
public interface BizExcCodes {

    String OTHER_SYS_ERR_MSG = "{0}系统错误，详细错误信息如下：";
    String SYS_MSG_OUT = "系统错误，详细错误信息如下：{0}服务无返回，}";
    String SYS_MSG_INNER = "系统错误，详细错误信息如下：{0}服务异常，";
    public int getCode();

    public String getMessage();

}
