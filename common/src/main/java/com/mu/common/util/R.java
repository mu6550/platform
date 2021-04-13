package com.mu.common.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private R() {
    }

    /**
     * 通用返回成功
     *
     * @return R
     */
    public static R ok() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 通用返回未知错误
     *
     * @return R
     */
    public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.UNEXPECTED_EXCEPTION.getSuccess());
        r.setCode(ResultCodeEnum.UNEXPECTED_EXCEPTION.getCode());
        r.setMessage(ResultCodeEnum.UNEXPECTED_EXCEPTION.getMessage());
        return r;
    }

    /**
     * 设置结果，形式参数为枚举
     *
     * @param resultCodeEnum resultCodeEnum
     * @return R
     */
    public static R setResult(ResultCodeEnum resultCodeEnum) {
        R r = new R();
        r.setSuccess(resultCodeEnum.getSuccess());
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    /**
     * 自定义返回数据
     *
     * @param data data
     * @return R
     */
    public R data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }

    /**
     * 通用设置data
     *
     * @param key   key
     * @param value value
     * @return R
     */
    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 自定义状态信息
     *
     * @param message message
     * @return R
     */
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 自定义状态码
     *
     * @param code code
     * @return R
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义返回数据结果
     *
     * @param success success
     * @return R
     */
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
