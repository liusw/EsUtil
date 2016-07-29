package com.tianyu.jty.common.persistence.es;

import com.alibaba.fastjson.JSONObject;
import com.tianyu.jty.common.annotation.Index;
import com.tianyu.jty.common.annotation.Indexable;
import com.tianyu.jty.common.persistence.SearchPage;
import com.tianyu.jty.common.utils.Reflections;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LeslieLiu on 2016/7/25.
 */
public abstract class SimpleEsDao<T extends Indexable> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static TransportClient client = EsClient.getTransportClient();

    protected Class<T> entityClass;

    public SimpleEsDao() {
        this.entityClass = Reflections.getClassGenricType(getClass());
    }
    public void putMapping(){
        Index indexAnno = entityClass.getAnnotation(Index.class);
        String indexName = indexAnno.name();
        String indexType = indexAnno.type();
        try {
            T entity = entityClass.newInstance();
            String mapping = entity.getMapping();
            client.admin().indices().preparePutMapping(indexName).setType(indexType)
                    .setSource(mapping).execute().actionGet();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    public void searchPage(SearchPage<T> page) {
        try {
            int pageNo = page.getPageNo()<0?1:page.getPageNo();
            int pageSize = page.getPageSize();
            int start = (pageNo - 1) * pageSize;
            Map<String, Object> queryParams = page.getQueryParams();
            QueryBuilder qb = buildQuery(queryParams);
            Index indexAnno = entityClass.getAnnotation(Index.class);
            String indexName = indexAnno.name();
            String type = indexAnno.type();
            SearchRequestBuilder requestBuilder = client.prepareSearch(indexName).setTypes(type).
                    setQuery(qb).setFrom(start).setSize(pageSize);
            SearchResponse response = requestBuilder.execute().actionGet();
            if (response != null) {
                SearchHits hits = response.getHits();
                float maxScore = hits.getMaxScore();
                long hitcount = hits.getTotalHits();
                SearchHit[] cells = hits.getHits();
                List<T> result = new ArrayList<T>();
                for (int i = 0; i < cells.length; i++) {
                    SearchHit cell = cells[i];
                    double score = cell.getScore();
                    T entity = JSONObject.parseObject(cell.getSourceAsString(), entityClass);
                    entity.setScore(score);
                    result.add(entity);
                }
                page.setMaxScore(maxScore);
                page.setHitcount(hitcount);
                page.setResult(result);
            }
        } catch (Exception e) {
            logger.error("errpr:{}", e);
        }
    }
    protected abstract QueryBuilder buildQuery(Map<String,Object> queryParams);
}
