package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Author:   李玉磊
 * Date:     2020/11/21 10:38
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo {

    private static final long serialVersionUID = 1L;

    private String id;//课程id
    private String title;//课程名称
    private String cover;//课程封面
    private Integer lessonNum;//课时数
    private String subjectLevelOne;//一级分类
    private String subjectLevelTwo;//二级分类
    private String teacherName;//讲师名称
    private String price;//只用于显示
}
