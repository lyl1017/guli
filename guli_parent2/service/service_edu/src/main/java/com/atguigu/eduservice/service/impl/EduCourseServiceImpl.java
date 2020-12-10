package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-10
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //注入EduCourseDescriptionService成是为了将应该实体类存到另一种表上
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //注入小节和章节的service
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;


    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息
        //将CourseInfoVo转换为eduCourse  先将courseInfoVo的值get出来,在set到eduCourse中,保证属性名称一致,
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        //通过判断insert的影响行数判断是否添加成功
        if (insert<=0){
            throw new GuliException(20001,"添加课程信息失败");
        }

        //为了保证一对一,课程id和描述id的值一样
       String cid= eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    //根据课程id查询课程的基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查看课程的基本信息,包含两张表,一个是课程表,一个描述表,查询完成之后封装到CourseInfoVo中
        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2.查询课程描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);//返回的是影响行数
        if (update==0){
            //修改失败
            throw new GuliException(20001,"修改信息失败");
        }
        //2.修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }


    //根据课程id查询课程,确认信息
    @Override
    public CoursePublishVo PublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    //删除课程的方法
    @Override
    public void removeCourse(String courseId) {
        //1.根据课程id删除课程中的小节,这个里面只做调用
        videoService.removeVideoByCourseId(courseId);

        //2.根据课程id删除章节


        //3.根据课程id删除描述

        //4.根据课程id删除课程本身
    }


}
