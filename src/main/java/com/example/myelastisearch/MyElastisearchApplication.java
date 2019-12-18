package com.example.myelastisearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EntityScan("com.example.myelastisearch.*")
public class MyElastisearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyElastisearchApplication.class, args);
    }

//    @RequestMapping(value = "/")
//    public String index() {
//        return "redirect:swagger-ui.html";
//    }
}
