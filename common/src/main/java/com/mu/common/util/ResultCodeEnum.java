package com.mu.common.util;

import lombok.Getter;

/**
 * 自定义枚举异常信息
 */
@Getter
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(true, 2000, "成功"),
    /**
     * 参数异常
     */
    PARMETER_EXCEPTION(false, 102, "参数异常!"),
    /**
     * 等待超时
     */
    SERVICE_TIME_OUT(false, 103, "服务调用超时！"),
    /**
     * 参数过大
     */
    PARMETER_BIG_EXCEPTION(false, 102, "输入的图片数量不能超过50张!"),
    /**
     * 500 : 一劳永逸的提示也可以在这定义
     */
    UNEXPECTED_EXCEPTION(false, 500, "系统发生异常，请联系管理员！");


    private Boolean success;
    private Integer code;
    private String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
