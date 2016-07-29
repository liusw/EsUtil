package com.tianyu.jty.common.web;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tianyu.jty.common.persistence.SearchPage;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.tianyu.jty.common.persistence.Page;
import com.tianyu.jty.common.utils.DateUtils;
import com.tianyu.jty.common.utils.StringUtils;
import com.tianyu.jty.system.entity.User;


/**
 * 基础控制器 
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * @description 
 * @author ty
 * @date 2014年3月19日
 */
public class BaseController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
		
		// Timestamp 类型转换
		binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				Date date = DateUtils.parseDate(text);
				setValue(date==null?null:new Timestamp(date.getTime()));
			}
		});
	}
	
	/**
	 * 获取page对象
	 * @param request
	 * @return page对象
	 */
	public <T> Page<T> getPage(HttpServletRequest request){
		int pageNo=1;	//当前页码
		int pageSize=15;	//每页行数
		String orderBy="id";	//排序字段
		String order="asc";	//排序顺序
		if(StringUtils.isNotEmpty(request.getParameter("page")))
			pageNo=Integer.valueOf(request.getParameter("page"));
		if(StringUtils.isNotEmpty(request.getParameter("rows")))
			pageSize=Integer.valueOf(request.getParameter("rows"));
		if(StringUtils.isNotEmpty(request.getParameter("sort")))
			orderBy=request.getParameter("sort").toString();
		if(StringUtils.isNotEmpty(request.getParameter("order")))
			order=request.getParameter("order").toString();
		return new Page<T>(pageNo, pageSize, orderBy, order);
	}
	public <T>SearchPage<T> getSearchPage(HttpServletRequest request){
		int pageNo = 1;
		int pageSize = 10;
		Map<String,Object> queryParams = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(request.getParameter("page"))){
			pageNo = Integer.valueOf(request.getParameter("page"));
		}
		if(StringUtils.isNotEmpty(request.getParameter("rows"))){
			pageSize = Integer.valueOf(request.getParameter("rows"));
		}
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String param = params.nextElement();
			if(org.apache.commons.lang3.StringUtils.startsWith(param,"c_")){
				queryParams.put("qColumn",request.getParameter(param));
			}
		}
		return new SearchPage<T>(pageNo,pageSize,queryParams);
	}
	
	/**
	 * 获取easyui分页数据
	 * @param page
	 * @return map对象
	 */
	public <T> Map<String, Object> getEasyUIData(Page<T> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getResult());
		map.put("total", page.getTotalCount());
		return map;
	}
	public <T> Map<String, Object> getEasyUIData(SearchPage<T> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getResult());
		map.put("total", page.getHitcount());
		return map;
	}
	
	/**
	 * 用来获取当前登录用户
	 * @return 当前登录用户
	 */
	public User getCurUser() {
		
		//Object = null;
		User curUser = (User) SecurityUtils.getSubject().getPrincipal();
		return curUser;
	}
	
}
