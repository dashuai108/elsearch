package com.example.myelastisearch.qo;


import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.metrics.InternalGeoBounds;

import java.util.*;

/**
 * 封装分页数据
 * @param <T>
 * */
@Setter
@Getter
public class PageResult<T> extends QueryObject{
    //查询出的数据
    public List<T> data;
    //总数据条数
    public Integer totalCount;
    //总页数
    public Integer totalPage;

    //上一页
    public Integer prevPage;
    //下一页
    public Integer nextPage;

    //无参构造
    public PageResult(){};

    public PageResult(List<T> data, Integer totalCount, Integer currentPage,Integer pageSize){
        this.data=data;
        this.totalCount=totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPage = this.totalCount % this.pageSize == 0 ? this.totalCount / this.pageSize:this.totalCount/this.pageSize+1;
    }

    //返回空集合
    public static PageResult empty(){
        return new PageResult(new ArrayList(),0,0,0);
    }

    @Override
    public void setHighlightFields(Object obj, SearchHit hit) {

        Map<String, Object> source = hit.getSourceAsMap();
        float score = hit.getScore();
        keyWord =score+"";
        Set<String> keySet = source.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            Object objects = source.get(next);
            System.out.println("key2:"+next+", value2:"+objects);
        }
    }
}
