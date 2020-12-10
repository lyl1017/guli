package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-10
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController implements Serializable {

    @Autowired
    private EduCourseService courseService;

    //课程列表 基本的实现
    //TODO 完善条件查询带分页
    @ApiOperation("查询所有课程")
    @GetMapping
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //逻辑删除课程的方法
    @ApiOperation("删除课程")
    @GetMapping("{id}")
    public R deleteCourse(@PathVariable String id){
        //
        boolean flag = courseService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }



    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后的课程id,为了后面添加大纲使用
       String id= courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程的基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R geCourseInfo(@PathVariable String courseId){
         CourseInfoVo courseInfoVo=courseService.getCourseInfo(courseId);
         return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程,确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo=courseService.PublishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程的最终发布,只需要修改课程的状态
    @ApiOperation(value = "根据id修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        List<EduCourse> list = courseService.list(null);
        for (EduCourse eduCourse : list) {
            System.out.println(eduCourse);
        }
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);//设置需要修改的课程id
        eduCourse.setStatus("Normal");//设置课程发布的状态
        courseService.updateById(eduCourse);
        System.out.println("id为:"+eduCourse.getId()+"修改之后状态为:"+eduCourse.getStatus());
        return R.ok();
    }

//    //删除课程
//    @ApiOperation(value = "删除课程")
//    @DeleteMapping("{courseId}")
//    public R deleteCourse(@PathVariable String courseId){
//       courseService.removeCourse(courseId);
//        return R.ok();
//    }

}

