package com.example.myelastisearch.repository.impl;

import com.example.myelastisearch.config.ApplicationContextHolder;
import com.example.myelastisearch.qo.PageResult;
import com.example.myelastisearch.qo.QueryObject;
import com.example.myelastisearch.repository.IBaseRepository;
import com.example.myelastisearch.utils.BeanUtil;
import com.example.myelastisearch.utils.RepositoryName;
import com.sun.org.apache.bcel.internal.generic.LineNumberGen;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.naming.directory.SearchResult;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Slf4j
@Component
public class BaseRepositoryImpl<T> implements IBaseRepository<T> {
    //索引
    private String baseIndex;
    ///类型
    private String baseType;




    //es的REST客户端
    protected RestHighLevelClient client = ApplicationContextHolder.getBean("client");
//    public RestHighLevelClient client = ApplicationContextHolder.getBean("client");


    //传递过来的泛型类型
    private Class<T> clazz;



    /**
     * 父类的无参构造器会被子类调用
     */
    public BaseRepositoryImpl(){
        try{
            //获取子类类型（this代表子类,因为是子类调用父类的这个无参构造方法）
            Class<? extends BaseRepositoryImpl> childClass = this.getClass();

            //获取子类的index和type字段,并赋值给自己
            Field[] fields = childClass.getDeclaredFields();
            for(Field field : fields){
                if(Objects.equals(field.getName(),"index"))
                    baseIndex = field.getAnnotation(RepositoryName.class).value();
                else if(Objects.equals(field.getName(),"type"))
                    baseType = field.getAnnotation(RepositoryName.class).value();
            }
            //获取定义在父类上的泛型参数对象
            ParameterizedType parameterizedType = (ParameterizedType) childClass.getGenericSuperclass();
            // 返回实际参数类型(泛型参数可以写多个)
            Type[] types = parameterizedType.getActualTypeArguments();
            // 获取第一个参数(泛型的具体类)
            this.clazz = (Class) types[0];
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    
    /**
     * 关闭连接
     * @throws Exception
     * */
    @Override
    public void close() throws Exception {
        if (client!=null){
            client.close();
        }
    }

    @Override
    public RestClient getLowLevelClient() {
        return client.getLowLevelClient();
    }

    @Override
    public void insertOrUpdate(T t) throws Exception {
        Map map = BeanUtil.beanTtoMap(t);
        IndexRequest indexRequest = new IndexRequest(baseIndex);
        indexRequest.id(map.get("id")+"");
        indexRequest.source(map);


        //同步执行
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);

        String id = index.getId();
        RestStatus status = index.status();
        System.out.println("status : "+status);
        System.out.println("id : "+id);

    }

    @Override
    public void delete(String  id) throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest(baseIndex);
        DeleteRequest request = deleteRequest.id(id);
        client.delete(deleteRequest,RequestOptions.DEFAULT);
    }

    @Override
    public T get(String id) throws Exception {
        GetRequest getRequest = new GetRequest(baseIndex);
        getRequest.id(id);
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> source = documentFields.getSource();
        T t = BeanUtil.map2Bean(source, clazz);
        return t;
    }

    @Override
    public List<T> getAll() throws Exception {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(baseIndex);
//        searchRequest.searchType(baseType);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        searchRequest.source(searchSourceBuilder);

        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = search.getHits();
        SearchHit[] hitsArray = hits.getHits();
        List<T> data = new ArrayList<>();
        for(SearchHit hit : hitsArray){
            Map<String, Object> source = hit.getSourceAsMap();
            T t = BeanUtil.map2Bean(source, clazz);
            data.add(t);
        }
        return data;


    }

    @Override
    public PageResult<T> search(QueryObject qo) throws Exception {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(baseIndex);
//        searchRequest.searchType(baseType);

        SearchSourceBuilder searchSourceBuilder = qo.createSearchSourceBuilder();

        //设置高亮显示
        HighlightBuilder highlightBuilder = qo.createHighlightBuilder();
        searchSourceBuilder.highlighter(highlightBuilder);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder name = QueryBuilders.matchQuery("name", "张三");
        MatchQueryBuilder age = QueryBuilders.matchQuery("age", 44);
        boolQueryBuilder.should(name);
        boolQueryBuilder.should(age);
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//        searchSourceBuilder.query(matchAllQueryBuilder);

        searchSourceBuilder.query(boolQueryBuilder);
        //设置查询的参数和范围
        searchRequest.source(searchSourceBuilder);

        SearchResponse search = this.client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();

        Long totalHits = hits.getTotalHits().value;

        SearchHit[] hitsArray = hits.getHits();

        List<T> data = new ArrayList<>();
        for(SearchHit hit : hitsArray){
            Map<String, Object> source = hit.getSourceAsMap();
            T t = BeanUtil.map2Bean(source, clazz);
            qo.setHighlightFields(t,hit);
            qo.setKeyWord(hit.getScore()+"");
            data.add(t);

        }
        PageResult pageResult = new PageResult(data, Integer.parseInt(totalHits + ""), qo.getCurrentPage(), qo.getPageSize());
        pageResult.setKeyWord(qo.getKeyWord());

        return pageResult;
    }
}
