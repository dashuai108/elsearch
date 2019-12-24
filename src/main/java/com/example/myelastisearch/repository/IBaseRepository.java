package com.example.myelastisearch.repository;

import com.example.myelastisearch.qo.PageResult;
import com.example.myelastisearch.qo.QueryObject;
import org.elasticsearch.client.RestClient;

import java.util.List;

/**
 * 通用的ES方法
 * */
public interface IBaseRepository<T> {

    //关闭连接
    void close()throws Exception;

    //获取低水平客户端
    RestClient getLowLevelClient();

//    RestClient getHighLevelClient();

    //新增或修改数据
    void insertOrUpdate(T t)throws Exception;

    //删除数据
    void delete(String id)throws Exception;

    //通过id获取数据
    T get(String  id) throws Exception;

    //获取所有文档
    List<T> getAll()throws Exception;

    //搜索
    PageResult<T> search(QueryObject qo)throws Exception;
}
