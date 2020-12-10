package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//当项目已启动,spring接口,spring加载,执行一个接口
@Component //交给spring容器来管理
public class ConstantPropertiesUtils implements InitializingBean {
    //加上@Component注解,一启动项目,这个类会交给spring来管理,在管理过程中,会用注解的方式将把配置文件中的值读取出来复制给定义的属性,
    //当这个过程完成之后,接口中的afterPropertiesSet()方法就会执行,在方法里面定义几个static的常量,对外就可以使用,类名.常量名称即可使用
    //读取配置文件中的内容
    @Value("${aliyun.oss.file.endpoint}")//spring中value注解读取配置文件key的名称,然后获取key的值赋给给属性
    private String endPoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //定义公开静态的常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endPoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
