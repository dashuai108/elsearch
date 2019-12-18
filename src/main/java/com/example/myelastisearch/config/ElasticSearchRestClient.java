package com.example.myelastisearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
连接客户端
* */
@Configuration
public class ElasticSearchRestClient {
    //获取配置文件的值
    @Value("${elasticsearch.ip}")
    private String[] ips;

    //获取elasticsearch 客户端
    @Bean
    public RestHighLevelClient client(){

        Stream<HttpHost> httpHostStream = Stream.of(ips).map(this::createHttpHost);
        List<HttpHost> collect = httpHostStream.collect(Collectors.toList());
        HttpHost[] httpHosts = new HttpHost[collect.size()];
        for(int i=0;i<collect.size();i++){
            httpHosts[i] = collect.get(i);
        }

//        HttpHost[] httpHosts = (HttpHost[]) Stream.of(ips).map(this::createHttpHost).collect(Collectors.toList()).toArray();
        RestClientBuilder builder = RestClient.builder(httpHosts);
        return new RestHighLevelClient(builder);
    }

    //根据ip创建连接
    private HttpHost createHttpHost(String ip) {
        return HttpHost.create(ip);
    }
}
