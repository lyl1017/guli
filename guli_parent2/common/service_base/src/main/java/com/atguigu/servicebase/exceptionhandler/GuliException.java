package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义异常类
@Data
@AllArgsConstructor //生成有参数的构造方法
@NoArgsConstructor  //生成无参数的构造方法
public class GuliException extends RuntimeException{
    //编写异常中的属性
    private Integer code;//异常的状态码
    private String msg;//异常的信息
}
