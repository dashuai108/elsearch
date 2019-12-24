package com.example.myelastisearch.qo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

import java.io.Serializable;


/**
 * 查询条件相关属性文档
 */
@Setter@Getter
@Slf4j
public class QueryObject implements Serializable {

    private static final long serialVersionUID = 4666457670566951226L;

    //当前页
    protected Integer currentPage = 1;

    //每页显示数据
    protected Integer pageSize = 10;

    //搜索关键字
    protected String keyWord;

    public Integer getStart() {
        log.info(" currentPage :"+currentPage+", paegSize:"+pageSize);
        if(currentPage<1){
            currentPage=1;
        }
        return (currentPage - 1) * pageSize;
    }

    /**
     * 创建SearchSourceBuilder 并设置通用属性
     *
     * @return SearchSourceBuilder
     */
    public SearchSourceBuilder createSearchSourceBuilder() {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(getStart());
        searchSourceBuilder.size(pageSize);
        return searchSourceBuilder;
    }

    /**
     * 创建HighlightBuilder 并设置通用属性
     *
     * @return HighlightBuilder
     */
    public HighlightBuilder createHighlightBuilder() {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<b>");
        highlightBuilder.postTags("</b>");
        return highlightBuilder;
    }

    /**
     * 给搜索出来的结果设置高亮显示 父类无需做任何操作 由子类操作
     *
     * @param obj
     * @param hit
     */
    public void setHighlightFields(Object obj, SearchHit hit) {

    }
}
//</editor-fold>
