package com.example.myelastisearch.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {


//    public static ApplicationContext applicationContext;
//
//    @Override
//    public  void setApplicationContext(ApplicationContext applicationContext) {
//        try {
//            this.applicationContext = applicationContext;
//        } catch (BeansException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static  <T>T getBean(String beanName){
////        System.out.println("applicationContext 测试======"+applicationContext.toString());
//        return (T)applicationContext.getBean(beanName);
//
//    }



    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("applicationContext正在初始化,application:"+applicationContext);
        this.applicationContext=applicationContext;
    }

    public static <T> T getBean(String name){
        if(applicationContext==null){
            System.out.println("applicationContext是空的");
        }else{
            System.out.println("applicationContext不是空的");
        }
        return (T)applicationContext.getBean(name);
    }

    public static <T>T getApplicationContext(String name){
        return (T) applicationContext.getBean(name);
    }
}
