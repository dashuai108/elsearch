package com.example.myelastisearch.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

<<<<<<< HEAD
    private static ApplicationContext applicationContext;
=======
    @Autowired
    private ApplicationContext applicationContext;
>>>>>>> 5ebb65f003a042a2fee0328b02f70805ff76279d

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        try {
            this.applicationContext = applicationContext;
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    public static  <T>T getBean(String beanName){

        System.out.println("applicationContext 测试======"+applicationContext.toString());
        return (T)applicationContext.getBean(beanName);
    }
}
