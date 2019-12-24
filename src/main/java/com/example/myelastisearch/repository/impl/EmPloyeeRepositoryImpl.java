package com.example.myelastisearch.repository.impl;

import com.example.myelastisearch.repository.IEmPloyeeRepository;
import com.example.myelastisearch.utils.RepositoryName;
import com.example.myelastisearch.vo.EmployeeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Slf4j
public class EmPloyeeRepositoryImpl extends BaseRepositoryImpl<EmployeeVo>{

    //索引
    @RepositoryName("store")
    protected String index;

    //类型
    @RepositoryName("employee")
    protected String type;

    public EmPloyeeRepositoryImpl(){};

//    /**
//     * 写一些特殊的方法
//     */
//    public List<EmployeeVo> getEmployeeVo(EmployeeVo t)throws Exception{
//        Map map = BeanUtil.beanTtoMap(t);
//        IndexRequest indexRequest = new IndexRequest(index);
//        indexRequest.id(map.get("id")+"");
//        indexRequest.source(map);
//
//        //设置路由
//        indexRequest.routing("");
//
//        //主要分片超时时间
//        indexRequest.timeout(TimeValue.timeValueSeconds(1));
//        indexRequest.timeout("1s");
//
//        //刷新策略
//        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        indexRequest.setRefreshPolicy("wait_for");
//
//        //版本号
//        indexRequest.version(2);
//
//        //版本类型
//        indexRequest.versionType(VersionType.EXTERNAL);
//
//        //操作类型
//        indexRequest.opType(DocWriteRequest.OpType.CREATE);
//        indexRequest.opType("create");
//
//
//        ActionListener listener = new ActionListener<IndexResponse>() {
//            @Override
//            public void onResponse(IndexResponse indexResponse) {
//                RestStatus status = indexResponse.status();
//                if("200".equalsIgnoreCase(status.getStatus()+"")){
//                }
//            }
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
//
//        //异步执行
//        client.indexAsync(indexRequest,RequestOptions.DEFAULT,listener);
//
//
//        //同步执行
//        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
//
//        String id = index.getId();
//        RestStatus status = index.status();
//        System.out.println("status : "+status);
//        System.out.println("id : "+id);
//        return null;
//    }
//
//
//    public void getApi()throws Exception{
//        GetRequest getRequest = new GetRequest(index);
//        getRequest.id("");
//
//        //参数
//        //禁用源检索  默认开启
//        getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
//
//        String[] includes = new String[]{"message", "*Date"};
//        String[] excludes = Strings.EMPTY_ARRAY;
//        FetchSourceContext fetchSourceContext =
//                new FetchSourceContext(true, includes, excludes);
//        //为特定字段配置源包含
//        getRequest.fetchSourceContext(fetchSourceContext);
//
//        //为特定字段排除源包含
//        getRequest.storedFields("message");
//        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
//        String message = getResponse.getField("message").getValue();
//    }
}
