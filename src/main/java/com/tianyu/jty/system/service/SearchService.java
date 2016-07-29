package com.tianyu.jty.system.service;

import com.tianyu.jty.common.persistence.SearchPage;
import com.tianyu.jty.system.dao.BlogSearchDao;
import com.tianyu.jty.system.entity.Blog;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LeslieLiu on 2016/7/25.
 */
@Service
public class SearchService {
    private static final Logger log = Logger.getLogger(SearchService.class);

    @Autowired
    private BlogSearchDao blogSearchDao;

    public void search(SearchPage<Blog> page) {
        blogSearchDao.searchPage(page);
    }
    public void putIndexMapping(){
        blogSearchDao.putMapping();
    }
}
