<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<%
	String keyword = request.getParameter("keyword");
%>
</head>
<body style="font-family: '微软雅黑'">
<div id="tb" style="padding:5px;height:auto">
        <div>
        	<form id="searchFrom" action="">
				<select id="columnSelect"></select>
       	        <input type="text" name="q_item" class="easyui-validatebox" data-options="width:150,prompt: '值'"/>
       	        <input type="text" name="q_keyword" class="easyui-validatebox" data-options="width:150,prompt: '关键字'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
			</form>
        </div>
</div>
<table id="dg"></table> 
<div id="dlg"></div>
<div id="dialog">hello layer!...</div>
<script type="text/javascript">
	var dg=$('#dg').datagrid({
		method: "get",
		url:"${ctx}/system/search/fulltext/json",
		fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
		columns:[[
			{field:'id',title:'id',hidden:false,width:80},
			{field:'title',title:'标题',sortable:false,width:150},
			{field:'author',title:'作者',sortable:false,width:30},
			{field:'price',title:'分类',sortable:false,width:60},
			{field:'createtime',title:'创建时间',sortable:false,width:100},
			{field:'sales',title:'状态',sortable:false,width:30},
			{field:'score',title:'score',sortable:false,width:100},
			{field:'content',title:'详情',sortable:false,width:50,formatter:function(value,rowData,index){
				var jsonData = escape(JSON.stringify(rowData));
				return  "<button type='button' onclick='openLayer(\""+jsonData+"\")'>详情</button>";
			}}
		]],
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: true,
		toolbar:'#tb'
	});
$(function(){
	var keyword = '<%=keyword%>';
	var themeCombo = $("#columnSelect").combobox({
		width: 150, editable: false,
		multiple:true,
		valueField:'value',
		data: [{
			"id":1,
			"text":"title",
			"value":"c_title",
			"selected":true
		},{
			"id":2,
			"text":"author",
			"value":"c_author",
		},{
			"id":3,
			"text":"content",
			"value":"c_content"
		}] ,
	});
	$('#searchFrom').find("[name='q_keyword']").val(keyword);
	cx();
});
//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('load',obj);
}
function openLayer(rowData){
	var jsonData = unescape(rowData);
	var json = JSON.parse(jsonData);
	var html = '<div style="padding:15px;">';
		html +=json.content;
		html +='</div>'
	layer.open({
		type: 1,
		title:json.title + ' author:'+json.author,
		skin:'layui-layer-lan',
		area: ['1150px', '700px'],
		borderWidth:5,
		content:html
	});
}
</script>
</body>
</html>