package com.tianyu.jty.system.entity;

import com.alibaba.fastjson.JSONObject;
import com.tianyu.jty.common.annotation.Index;
import com.tianyu.jty.common.annotation.Indexable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LeslieLiu on 2016/7/27.
 */
@Index(name = "blogs",type = "blog")
public class Blog implements Indexable {
    private String id;
    private String title;
    private String author;
    private String content;
    private String email;
    private String updateTime;
    private String createTime;
    private String ip;
    private String status;
    private String tags;
    private String meta;
    private double score;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String getMapping() {
        JSONObject mapping = new JSONObject();
        JSONObject type = new JSONObject();
        type.put("properties",null);
        mapping.put("blog",type);
        return mapping.toJSONString();
    }
}
