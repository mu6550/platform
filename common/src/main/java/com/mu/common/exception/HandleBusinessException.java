package com.mu.common.exception;

import com.mu.common.util.ResultCodeEnum;
import lombok.Data;

/**
 * @author mujiangkui
 * @description 自定义异常类
 * @Date 2021/01/24 22:50:22
 */
@Data
public class HandleBusinessException extends RuntimeException {

    /**
     * 异常状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 操作成功
     */
    private Boolean success;

    /**
     * 构造函数
     *
     * @param resultCodeEnum 参数为枚举类型
     */
    public HandleBusinessException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.success = resultCodeEnum.getSuccess();
    }
}
