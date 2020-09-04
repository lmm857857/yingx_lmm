<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script>

    $(function(){

        //初始化表单属性
        $("#feedbackTable").jqGrid({
            url : "${path}/feedback/showAll",  //分页查询   rows  total recoreds  page  rows
            datatype : "json",
            rowNum : 5,  //每页展示条数
            rowList : [5,10, 15,20,25,30,30,1000 ],
            pager : '#feedbackPager',
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            viewrecords:true,  //是否展示数据总条数
            colNames : [ 'ID', '反馈标题', '反馈内容', '反馈时间', '反馈用户' ],
            colModel : [
                {name : 'id',width : 130},
                {name : 'title',width : 130},
                {name : 'content',width : 130},
                {name : 'feedbackTime',width : 130},
                {name : 'userId',width : 130},
            ]
        });
    });

</script>

<%--初始化一个面板--%>
<div class="panel panel-danger">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>反馈管理</h2>
    </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">反馈信息</a></li>
    </div>

    <%--初始化表单--%>
    <table id="feedbackTable" />

    <%--工具栏--%>
    <div id="feedbackPager" />

</div>