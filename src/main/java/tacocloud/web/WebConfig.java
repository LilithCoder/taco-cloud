package tacocloud.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 视图控制器: 只将请求转发给视图的控制器
 * addViewControllers() 方法提供了一个 ViewControllerRegistry，可以使用它来注册一个或多个视图控制器
 * */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 在注册表上调用 addViewController()，传入 “/”，这是视图控制器处理 GET 请求的路径
     * 该方法返回一个 ViewControllerRegistration 对象，
     * 在该对象上立即调用 setViewName() 来指定 home 作为应该转发 “/” 请求的视图
     * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
}
