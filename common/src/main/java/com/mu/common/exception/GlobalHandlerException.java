package com.mu.common.exception;


import com.mu.common.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author mujiangkui
 * @description 捕获全局异常信息类
 * @Date 2021/01/24 22:44:44
 */
@ControllerAdvice
@ResponseBody
public class GlobalHandlerException extends RuntimeException {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalHandlerException.class);


    /**
     * 处理缺少参数的全局异常
     *
     * @param exception exception
     * @return R
     */
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R handleHttpMessageNotReadableException(MissingPathVariableException exception) {
        LOGGER.error("缺少请求参数，{}", exception.getMessage());
        return R.error().code(400).message("缺少参数" + exception.getMessage());
    }

    /**
     * 空指针异常
     *
     * @param e e
     * @return R
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleTypeMismatchException(NullPointerException e) {
        LOGGER.error("空指针参数异常，{}", e.getMessage());
        return R.error().code(500).message("空指针异常");
    }

    /**
     * 系统异常 预期以外异常
     *
     * @param ex ex
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleUnexpectedServer(Exception ex) {
        LOGGER.error("系统异常,{}", ex.getMessage());
        return R.error().code(500).message("系统异常，请联系管理员！");
    }

    /**
     * 自定义异常
     *
     * @param ex ex
     * @return R
     */
    @ExceptionHandler(HandleBusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleBusinessError(HandleBusinessException ex) {
        Integer code = ex.getCode();
        String message = ex.getMessage();
        LOGGER.error("{}", message);
        return R.error().code(code).message(message);
    }
}
