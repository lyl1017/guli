package com.atguigu.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
//启动类,exclude = DataSourceAutoConfiguration.class启动默认不加载数据库信息
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//组件扫描的规则 扫描配置类 扫描公共的swagger配置类使用没有放在配置类中
// 默认是扫描service_edu下面的包,现在可以扫描到service_base下面的com.atguidu
//启动的时候扫描带com.atguigu下面代码@Configuration注解的配置类
@ComponentScan(basePackages = {"com.atguigu"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
