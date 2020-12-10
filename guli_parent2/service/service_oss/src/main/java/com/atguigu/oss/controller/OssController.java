package com.atguigu.oss.controller;

import com.atguigu.oss.service.OssService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController//交给spring管理,返回json数据
@RequestMapping("/eduoss/fileoss")
@CrossOrigin//解决跨域
@Api(description="文件上传")
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法  MultipartFile得到上传文件
    @ApiOperation(value = "文件上传的方法")
    @PostMapping
    public R uploadOssFile(@ApiParam(name = "file",value="文件",required = true) MultipartFile file){
        //获取上传的文件
        //返回上传到oss的路径(因为数据库存储的就是一个路径)
         String url=ossService.uploadAvatar(file);
        return R.ok().data("url",url);
    }

}
