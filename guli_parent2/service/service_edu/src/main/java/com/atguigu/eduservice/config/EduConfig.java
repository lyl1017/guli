package com.atguigu.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//配置类
//组件扫描的规则 扫描公共的swagger配置类使用没有放在配置类中
// 默认是扫描service_edu下面的包,现在可以扫描到service_base下面的comatguidu
@MapperScan("com.atguigu.eduservice.mapper")
public class EduConfig {

    /**
     * 逻辑删除插件
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

/**
 * 分页插件
 */
    @Bean    //分页拦截器
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
