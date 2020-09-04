<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script>

    $(function(){

        //初始化表单属性
    $("#logTable").jqGrid({

        url : "${path}/log/showAll",  //分页查询   rows  total recoreds  page  rows
        datatype : "json",
        rowNum : 10,  //每页展示条数
        styleUI:"Bootstrap",
        height:"auto",
        autowidth:true,//是否展示数据总条数
        rowList : [5,10, 20,30,40,50,60,1000 ],
        pager : '#logPage',
        viewrecords : true,

        colNames : [ 'ID', '管理员姓名', '操作时间', '操作', '状态(是否成功)' ],
        colModel : [
            {name : 'id',width : 130},
            {name : 'adminName',width : 130},
            {name : 'data',width : 130},
            {name : 'operation',width : 130},
            {name : 'status',width : 130},
        ]
    });
        //表格工具栏
        $("#logTable").jqGrid('navGrid', '#logPage',
            {edit : false,add : false,del : false});
    });

</script>

<%--初始化一个面板--%>
<div class="panel panel-primary">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>日志管理</h2>
    </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">日志信息</a></li>
    </div>

    <%--初始化表单--%>
    <table id="logTable" />

    <%--分页工具栏--%>
     <div id="logPage" />

</div>