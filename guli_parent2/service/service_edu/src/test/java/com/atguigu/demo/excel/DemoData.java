package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data//生成get和set方法
public class DemoData {

    //设置excel的名称,index=0表示第一列对应的关系
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生名称",index = 1)
    private String sname;
}
