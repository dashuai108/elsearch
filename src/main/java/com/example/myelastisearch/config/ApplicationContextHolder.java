package com.example.myelastisearch.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        try {
            this.applicationContext = applicationContext;
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    public  <T>T getBean(String beanName){
        return (T)this.applicationContext.getBean(beanName);
    }
}
