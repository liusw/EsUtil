package com.tianyu.jty.system.web;

import com.tianyu.jty.common.persistence.SearchPage;
import com.tianyu.jty.common.web.BaseController;
import com.tianyu.jty.system.entity.Blog;
import com.tianyu.jty.system.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by LeslieLiu on 2016/7/25.
 */
@Controller
@RequestMapping("system/search")
public class SearchController extends BaseController{
    @Autowired
    private SearchService searchService;
    /**
     * 默认页面
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView result(HttpServletRequest request) {
        Map params = request.getParameterMap();
        ModelAndView mv = new ModelAndView("search/searchResult",params);
        return mv;
    }
    @RequestMapping("fulltext/json")
    @ResponseBody
    public Map<String, Object> fullTextSearch(HttpServletRequest request){
        SearchPage<Blog> page = getSearchPage(request);
        searchService.search(page);
        return getEasyUIData(page);
    }
    @RequestMapping("putInxMapping/json")
    @ResponseBody
    public void putInxMapping(){
        searchService.putIndexMapping();
    }
}
