package com.example.myelastisearch.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    @Autowired
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){
        applicationContext = applicationContext;
    }
    public  static <T>T getBean(String beanName){
        System.out.println("applicationContext-----:"+applicationContext.toString());
        return (T)applicationContext.getBean(beanName);
    }
}
