package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;

import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-08-22
 */
@Api(description="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    public static final String 每页记录数 = "每页记录数";

    //访问地址:http://127.0.1:8001/eduservice/teacher/findAll

    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师表的所有数据
    //rest风格每一种操作用到不同的请求方式或者提交方式
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll") //rest风格的请求  get提交
    public R findAll(){
        //调用service的方法查询所有
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //2.逻辑删除讲师的方法
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}") //delete提交不能使用浏览器测试  id是通过路径进行传递 @PathVariable获取路径上传递的id值
    public R removeTeacher( @ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //3.分页查询讲师方法
    @ApiOperation(value = "分页查询讲师的方法")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current", value="当前页",required = true)
                             @PathVariable long current,
                             @ApiParam(name = "limit",value = 每页记录数,required = true)
                             @PathVariable long limit){

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);


        //手动制造异常  自定义异常需要手动抛出
        try {
            int a=10/0;
        }catch (Exception e){
            //执行自定义异常  有参构造,传入两个值
            throw new GuliException(20001,"执行了自定义异常...");
        }

        //调用分页的方法
        //调用方法时候,底层封装,把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //当前页数据list集合

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }

    //4.条件查询带分页的方法
    @ApiOperation(value = "条件查询带分页的方法")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current", value="当前页",required = true)
                                  @PathVariable long current,
                                  @ApiParam(name = "limit",value = "每页记录数",required = true)
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //@RequestBody(required = false)必须要post请求 参数值必须为空
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        //构建QueryWrapper
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        //mysql的动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件是否为空,如果不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            //第一个是数据库的字段名称,第二个是传入的值
            wrapper.like("name",name);
        }
        //等于
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        //大于等于
        if (!StringUtils.isEmpty(begin)){
            wrapper.gt("gmt_create",begin);
        }
        //小于等于
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }

        //工具修改时间降序排序
        wrapper.orderByDesc("gmt_modified");

        //调用分页的方法
        teacherService.page(pageTeacher,wrapper);
        //调用方法时候,底层封装,把分页所有数据封装到pageTeacher对象里面
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    //5.添加讲师接口的方法
    @ApiOperation(value = "添加讲师接口的方法")
    @PostMapping("addTeacher")
    //从前端获取json数据,进行封装到eduTeacher然后进行添加
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        System.out.println(eduTeacher);
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //6.根据讲师id进行查询  为了修改的时候做数据回显
    @ApiOperation(value = "根据讲师id进行查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)
                        @PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //7.根据id修改讲师功能 回显有一个有一个表单
    @ApiOperation(value = "根据id修改讲师修改功能")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(name = "eduTeacher",value = "讲师对象",required = true)
                           @RequestBody EduTeacher eduTeacher){
        //eduTeacher中的id是前端传入过来的
        boolean flag = teacherService.updateById(eduTeacher);
        return flag? R.ok():R.error();
    }
}

