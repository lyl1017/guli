package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;
//继承AnalysisEventListener类从该类中复制出invokeHeadMap获取表头的方法
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //通过监听从第二行开始(第一行是表头)一行一行的读取excel中的内容,data为每行内容
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        System.out.println("****"+data);
    }

    //读取表头中的内容,headMap表头的内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头:"+headMap);

    }
    //在读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
