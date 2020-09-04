<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <!-- 引入Echars js -->
    <script src="${path}/Echars-js/echarts.js"></script>
    <!-- 引入Echars 地图的js -->
    <script src="${path}/Echars-js/china.js"></script>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(function(){

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            //发请求获取数据
            $.get("${path}/user/userDistribution",function(datas){
                var serie=[];
                for(var i=0;i<datas.length;i++){
                    var data= datas[i];
                    serie.push(
                        {
                            name: data.titles,
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data:data.cities
                        }
                    )
                }
                // 指定图表的配置项和数据
                var option = {
                    title : {
                        text: '应学App用户分布图',
                        subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:['男性','女性']
                    },
                    visualMap: {
                        min: 0,
                        max: 200,
                        left: 'left',
                        top: 'bottom',
                        text:['高','低'],           // 文本，默认为数值文本
                        calculable : true
                    },
                    toolbox: {
                        show: true,
                        orient : 'vertical',
                        left: 'right',
                        top: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    series : serie
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            });
        });
    </script>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 1500px;height:800px;"></div>

</body>
</html>