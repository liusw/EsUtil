package com.tianyu.jty.system.dao;

import com.tianyu.jty.common.persistence.es.SimpleEsDao;
import com.tianyu.jty.system.entity.Blog;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by LeslieLiu on 2016/7/27.
 */
@Repository
public class BlogSearchDao extends SimpleEsDao<Blog>{

    public BlogSearchDao(){
        super();
    }
    @Override
    protected QueryBuilder buildQuery(Map<String, Object> queryParams) {
        if(queryParams == null)
            return QueryBuilders.queryStringQuery("");
        String keyWords = (String) queryParams.get("keyword");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(keyWords != null){
            QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery(keyWords);
            String json = searchSourceBuilder.query(queryBuilder).toString();
            logger.info("[search]:{}", json);
            return queryBuilder;
        }else{
            //TermsQueryBuilder queryBuilder = queryByIds();
            FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("content", "大话数据结构——树");
            //MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "大话数据结构——树");
            //WildcardQueryBuilder queryBuilder = QueryBuilders.wildcardQuery("title", "*大话数据结构*");
            String json = searchSourceBuilder.query(queryBuilder).toString();
            logger.info("[search]:{}",json);
            return queryBuilder;
        }
    }
}
