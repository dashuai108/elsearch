package com.example.myelastisearch.repository.impl;

import com.example.myelastisearch.repository.IArticleRepository;
import com.example.myelastisearch.utils.RepositoryName;
import com.example.myelastisearch.vo.ArticleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
public class ArticleRepositoryImpl extends BaseRepositoryImpl<ArticleVo> implements IArticleRepository {
    //索引
    @RepositoryName("articles")
    protected String index;

    //类型
    @RepositoryName("history")
    protected String type;


    /**
     * 写一些特殊的方法
     */

}
