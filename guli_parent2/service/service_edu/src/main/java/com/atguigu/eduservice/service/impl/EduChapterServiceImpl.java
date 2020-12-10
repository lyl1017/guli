package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-10
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;//注入小结的service

    //课程大纲列表,根据课程id查询章节小结
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2.根据课程Id查询课程里面的所有小结
        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建一个最终集合
        List<ChapterVo> finalLis =new ArrayList<>();

        //3.遍历查询的章节list集合封装
        //遍历章节
        for (int i = 0; i < eduChapterList.size(); i++) {
            //获取每一个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //将eduChapter的值存放到ChapterVo,最后存放到list集合中
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalLis.add(chapterVo);

            //创建一个集合,用于封装章节中的小节
            List<VideoVo> videoVoList = new ArrayList<>();
            //4.遍历查询小节list集合,进行封装
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每一个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断 小节中的chapterid与章节中的id是否一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //存放到小节封装的集合中去
                    videoVoList.add(videoVo);
                }
            }
            //把封装之后的小节list集合,放到章节对象里面
            chapterVo.setChildren(videoVoList);
        }
        return finalLis;
    }

    //删除章节
    @Override
    public Boolean deleteChapter(String chapterId) {
        //根据chapterid章节id,查询小节表,如果查询数据,不能进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if (count>0){//查询出小节,不能进行删除章节
            throw new GuliException(20001,"先删除章节下面的小节");
        }else {//不能查询出,进行删除
            //删除章节 返回只有1或者0
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }
}
