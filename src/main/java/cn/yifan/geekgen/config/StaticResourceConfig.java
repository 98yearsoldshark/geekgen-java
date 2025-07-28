package cn.yifan.geekgen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @FileName StaticResourceConfig
 * @Description
 * @Author yifan
 * @date 2025-03-01 20:08
 **/

@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 自定义静态资源访问路径和存放位置
        registry.addResourceHandler("/static/banners/**")
                .addResourceLocations("classpath:/static/banners/");
    }

}
