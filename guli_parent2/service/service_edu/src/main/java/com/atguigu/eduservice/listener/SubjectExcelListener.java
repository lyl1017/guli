package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;
//继承AnalysisEventListener类从该类中复制出invokeHeadMap获取表头的方法
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能交给spring进行管理,需要自己new,所有不能注入其他对象
    //所有不能实现数据库操作

    //解决方法,在监听器中传入应该subjectService,为了可以操作数据库
    public EduSubjectService subjectService;
    //有参构造
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    //无参构造
    public SubjectExcelListener() {
    }

    //通过监听从第二行开始(第一行是表头)一行一行的读取excel中的内容,subjectData为每行内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){//判断Excel是否为空
            throw new GuliException(2001,"文件数据为空");
        }

        //一行一行的读取,每次读取有两个值,第一个值为一级分类,第二个值为二级分类
        //判断一级分类是否重复
        EduSubject existOneSubject=this.existOneSubject(subjectService,subjectData.getOneSubjectName());
        if (existOneSubject==null){//没有相同一级分类,进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }

        //一级级分类的id是二级分类的parent_id
        String pid=existOneSubject.getId();
        //添加二级分类
        EduSubject existTowSubject = this.existTowSubject(subjectService, subjectData.getTowSubjectName(), pid);
        if (existTowSubject==null){
            existTowSubject = new EduSubject();
            existTowSubject.setParentId(pid);
            existTowSubject.setTitle(subjectData.getTowSubjectName());
            subjectService.save(existTowSubject);
        }
    }

    //判断一级分类不能重复添加
    public EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    public EduSubject existTowSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject towSubject = subjectService.getOne(wrapper);
        return towSubject;
    }

    //读取表头中的内容,headMap表头的内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    }

    //在读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
