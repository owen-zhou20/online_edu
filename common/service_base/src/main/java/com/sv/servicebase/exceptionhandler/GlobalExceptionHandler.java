package com.sv.servicebase.exceptionhandler;


import com.sv.commonutils.ExceptionUtil;
import com.sv.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  Exception Handler
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // handler for all exceptions
    @ExceptionHandler(Exception.class)
    @ResponseBody //to return R
    public R error(Exception e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().message("execute all exception handler");
    }

    // handler for ArithmeticException error
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //to return R
    public R error(ArithmeticException e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().message("execute ArithmeticException handler");
    }

    // handle for SvException error
    @ExceptionHandler(SvException.class)
    @ResponseBody //to return R
    public R error(SvException e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
