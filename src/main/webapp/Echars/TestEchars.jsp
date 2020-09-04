<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!-- 引入Echars js -->
<script src="${path}/Echars-js/echarts.js"></script>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript">

    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        $.get("${path}/Echars-js/userJson.json",function (data) {
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '用户月注册统计'//标题
                },
                tooltip: {}, //鼠标提示
                legend: {
                    data:['男生','女生'] //选项卡
                },
                xAxis: {//横轴
                    data: data.month
                },
                yAxis: {},//纵轴 自适应
                series: [{
                    name: '男生',
                    type: 'bar',//类型 bar柱状图
                    data: data.boys
                },{
                    name: '女生',
                    type: 'bar',//类型 bar柱状图
                    data: data.girls
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },"json");

    });
</script>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div align="center">
        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div id="main" style="width: 600px;height:400px;"></div>


    </div>
</body>
</html>


