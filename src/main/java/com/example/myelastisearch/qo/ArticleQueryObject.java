package com.example.myelastisearch.qo;


import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.common.xcontent.ToXContent;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * 查询文章
 * */
@Setter@Getter
public class ArticleQueryObject extends QueryObject {

    @Override
    public SearchSourceBuilder createSearchSourceBuilder() {
        SearchSourceBuilder searchSourceBuilder = super.createSearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("title",keyWord));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content",keyWord));
        return searchSourceBuilder.query(boolQueryBuilder);
    }
}
