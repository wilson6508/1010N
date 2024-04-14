//package com.itheima.config;
//
//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;
//
//public class ServletContainersInitConfig01 extends AbstractDispatcherServletInitializer {
//
//    // springmvc對應的容器對象
//    protected WebApplicationContext createServletApplicationContext() {
//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(SpringMvcConfig.class);
//        return ctx;
//    }
//
//    // 攔截進入tomcat的請求
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//
//    // spring對應的容器對象
//    protected WebApplicationContext createRootApplicationContext() {
//        return null;
//    }
//
//}
