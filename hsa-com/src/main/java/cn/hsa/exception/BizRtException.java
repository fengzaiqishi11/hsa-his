package cn.hsa.exception;

import cn.hsa.hsaf.core.framework.web.exception.AppException;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 应用运行时业务异常类，采用异常代码和上下文数据描述方式。
 *
 * @author
 */
public class BizRtException extends AppException {

    private static final long serialVersionUID = -4729161738021046262L;

    /**
     * 异常代码
     */
    private int code;

    /**
     * 本地化异常信息
     */
    private String localizedMessage;

    /**
     * 异常信息是否不可修改
     */
    private boolean immutable = false;

    /**
     * 是否已输出异常信息
     */
    private boolean logged = false;

    /**
     * 异常数据上下文对象数组
     */
    private Map<String, String> context = new LinkedHashMap<String, String>();

    public BizRtException(int code) {
        this.code = code;
    }

    public BizRtException(int code, String message) {
        super(message);
        this.code = code;
        this.localizedMessage = message;
    }

    public BizRtException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.localizedMessage = message;
    }

    public BizRtException(int code, Throwable t) {
        super(t);
        this.code = code;
    }

    public BizRtException(int code, String message, Map<String, String> params) {
        super(message);
        this.code = code;
        this.localizedMessage = message;
        context.putAll(params);
    }

    public BizRtException(int code, String message, Map<String, String> params, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.localizedMessage = message;
        context.putAll(params);
    }

    public BizRtException(BizExcCodes bizExcCodes) {
        super(bizExcCodes.getMessage());
        this.code = bizExcCodes.getCode();
        this.localizedMessage = bizExcCodes.getMessage();
    }

    public BizRtException(BizExcCodes bizExcCodes, Throwable cause) {
        super(bizExcCodes.getMessage(), cause);
        this.code = bizExcCodes.getCode();
        this.localizedMessage = bizExcCodes.getMessage();
    }

    public BizRtException(BizExcCodes bizExcCodes, String concatMessage) {
        super(concatMessage + "," + bizExcCodes.getMessage());
        this.code = bizExcCodes.getCode();
        this.localizedMessage = getMessage();
    }

    public BizRtException(BizExcCodes bizExcCodes, String concatMessage, Throwable cause) {
        super(concatMessage + "," + bizExcCodes.getMessage(), cause);
        this.code = bizExcCodes.getCode();
        this.localizedMessage = getMessage();
    }

    public BizRtException(BizExcCodes bizExcCodes, Object... replacements) {
        super(MessageFormat.format(bizExcCodes.getMessage(), replacements));
        this.code = bizExcCodes.getCode();
        this.localizedMessage = getMessage();
    }

    public BizRtException(BizExcCodes bizExcCodes, Throwable cause, Object... replacements) {
        super(MessageFormat.format(bizExcCodes.getMessage(), replacements), cause);
        this.code = bizExcCodes.getCode();
        this.localizedMessage = getMessage();
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> args) {
        this.context = args;
    }

    public boolean isImmutable() {
        return immutable;
    }

    public void setImmutable(boolean immutable) {
        this.immutable = immutable;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public String toString() {
        String s = getClass().getName() + ", code=" + code;
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

}
