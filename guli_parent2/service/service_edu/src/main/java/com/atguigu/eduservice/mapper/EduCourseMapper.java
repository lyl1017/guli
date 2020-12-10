package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-09-10
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //传入课程id
    public CoursePublishVo getPublishCourseInfo(String courseId);




}
