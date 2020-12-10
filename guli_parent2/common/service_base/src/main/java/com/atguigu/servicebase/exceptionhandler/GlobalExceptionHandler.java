package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import com.atguigu.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常处理类
@ControllerAdvice
@Slf4j   //要用到logback的日志
public class GlobalExceptionHandler {


    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了能够返回数据
    public R error(Exception e){
        //打印异常
        e.printStackTrace();
        return R.error().message("执行全局异常处理");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行特定异常处理");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        //logback异常信息
        log.error(e.getMessage());
        //异常工具类  相对logback异常信息可以打印多行
        log.error(ExceptionUtil.getMessage(e));
        //打印异常
        e.printStackTrace();
        //提供get方法获取传入的参数
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
