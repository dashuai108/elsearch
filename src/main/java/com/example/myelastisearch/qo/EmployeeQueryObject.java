package com.example.myelastisearch.qo;

import com.example.myelastisearch.vo.EmployeeVo;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;

import java.util.Arrays;

import static org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.*;


@Setter@Getter
public class EmployeeQueryObject extends QueryObject {



    /**设置搜索员工资源的具体添加
     * @return
     * */
    @Override
    public SearchSourceBuilder createSearchSourceBuilder() {
        SearchSourceBuilder searchSourceBuilder = super.createSearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("about",keyWord));
        return searchSourceBuilder;
    }

    /**
     * 设置哪些字段需要高亮显示
     * */
    @Override
    public HighlightBuilder createHighlightBuilder() {
        HighlightBuilder highlightBuilder = super.createHighlightBuilder();
        HighlightBuilder.Field highlightAbout = new HighlightBuilder.Field("about");
        highlightBuilder.field(highlightAbout);
        return highlightBuilder;
    }

    /**
     * 给搜索出来的员工资源对象重新设置高亮后的字段值
     * @param obj
     * @param hit
     * @return
     * */
    @Override
    public void setHighlightFields(Object obj, SearchHit hit) {
        super.setHighlightFields(obj, hit);
        HighlightField highlightField = hit.getHighlightFields().get("about");
        EmployeeVo employeeVo = (EmployeeVo) obj;
        employeeVo.setAbout(Arrays.toString(highlightField.getFragments()));
    }
}
