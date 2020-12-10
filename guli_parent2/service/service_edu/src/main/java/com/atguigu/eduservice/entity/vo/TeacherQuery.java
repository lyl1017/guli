package com.atguigu.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import lombok.Data;

//多条件查询的实体类
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称 模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2019-01-01 10:10:10")
    private String begin;  //注意,这里使用的是string类型,前端传来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间",example = "2019-12-01 10:10:10")
    private String end;

}
