package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //1.实现excel写的操作
        //设置写入文件夹的地址和excel文件名称
//        String filename="D:\\Home\\code\\test\\write.xlsx";
        //2.调用easyexcel里面的方法实现操作
        //d路径名称和实体类名称.class
//        EasyExcel.write(filename,DemoData.class).sheet("学生姓名").doWrite(getData());

        //1实现excel读操作
        //设置读取文件的路径
        String filename="D:\\Home\\code\\test\\write.xlsx";
        //读取的路径+实体类.clss+监听器发
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //创建方法返回list集合
    private static List<DemoData> getData(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
