package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-08
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin//解决跨域问题
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件,把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
       //获取上传过来的excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类的列表功能(树形结构)
        @GetMapping("getAllSubject")
    public R getAllSubject(){
        //一级分类里面有多个二级分类,所有泛型写一级分类的
         List<OneSubject> list=subjectService.getAllOneTowSubject();
        return R.ok().data("list",list);
    }
}

