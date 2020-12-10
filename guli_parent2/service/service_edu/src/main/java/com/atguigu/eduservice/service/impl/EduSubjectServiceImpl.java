package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TowSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

//    ServiceImpl因为继承这个类,这个里面帮我们注入了mapper @Autowired
        //@Autowired
        //protected M baseMapper;

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {

        try{
            //获取文件输入流
            InputStream in=file.getInputStream();
            //调用方法读取
            //流,实体类.class,监听器(不能交给spring管理,所有是用的new)
            EasyExcel.read(in,SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //课程分类的列表功能(树形结构)
    @Override
    public List<OneSubject> getAllOneTowSubject() {
        //1.查询所有的一级分类  parentid=0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        //获取了所有一级分类数据
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2.查询所有的二级分类  parentid!=0
        QueryWrapper<EduSubject> wrapperTow = new QueryWrapper<>();
        wrapperTow.ne("parent_id",0);
        //获取了所有一级分类数据
        List<EduSubject> towSubjectList = baseMapper.selectList(wrapperTow);

        //创建一个list集合,用于存储最终封装的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3.封装一级分类
        //查询出来所有的一级分类list集合遍历,得到每一级分类对象,获取每一级分类的值
        //封装到要求的list集合里面,List<OneSubmit> finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //获取所有的一级分类
            EduSubject eduSubject = oneSubjectList.get(i);
            //把eduSubject里面的值获取出来,放到OneSubject对象里面
            //多个OneSubject放到finalSubjectList里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
//            //eduSubject这个李get出来的值复制给oneSubject
            BeanUtils.copyProperties(eduSubject,oneSubject);

            finalSubjectList.add(oneSubject);
            //4.封装二级分类
            //二级分类在一级分类里面,需要嵌套封装
            //创建一个集合,存放二级分类
            List<TowSubject> towFinalSubjectList =new ArrayList<>();
            //遍历二级分类,存放到一级分类里面
            for (int m = 0; m < towSubjectList.size(); m++) {
                //获取每个二级分类
                EduSubject tSubject = towSubjectList.get(m);
                //判断二级属于那个一级分类,一级分类的id和二级分类parentid是否相同
                if (tSubject.getParentId().equals(eduSubject.getId())){
                    //把tSubject值复制到TowSubject里面,放到towFinalSubjectList里面
                    TowSubject towSubject = new TowSubject();
                    BeanUtils.copyProperties(tSubject,towSubject);
                    towFinalSubjectList.add(towSubject);
                }
            }
            //把一级分类下面所有的二级分类放到一级分类里面
            oneSubject.setChildren(towFinalSubjectList);
        }
       //返回最近集合数据
        return finalSubjectList;
    }
}
