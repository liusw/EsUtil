package com.tianyu.jty.common.persistence;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Created by LeslieLiu on 2016/7/27.
 */
public class SearchPage<T> {
    //-- 分页参数 --//
    protected int pageNo = 1;
    protected int pageSize = -1;
    protected List<T> result = Lists.newArrayList();
    protected long hitcount = -1;
    protected long times;
    protected Map<String,Object> queryParams;
    protected double maxScore;

    //-- 构造函数 --//
    public SearchPage() {
    }

    public SearchPage(int pageNo, int pageSize, Map<String,Object> queryParams) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.queryParams = queryParams;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public long getHitcount() {
        return hitcount;
    }

    public void setHitcount(long hitcount) {
        this.hitcount = hitcount;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }
    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }
}
