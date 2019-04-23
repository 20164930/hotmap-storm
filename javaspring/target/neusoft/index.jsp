<%--
  Created by IntelliJ IDEA.
  User: ttc
  Date: 2018/7/6
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>高德地图</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
</head>
<body>
<script src="https://cdn.bootcss.com/echarts/4.1.0.rc2/echarts.min.js"></script>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script src="http://webapi.amap.com/maps?v=1.4.9&amp;key=d16808eab90b7545923a1c2f4bb659ef"></script>
<div id="container"></div>

<script>
    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [123.453169, 41.742567],
        // center: [116.39, 39.92],
        zoom: 17

    });

    var heatmap;
    map.plugin(["AMap.Heatmap"],function() {      //加载热力图插件
        heatmap = new AMap.Heatmap(map,{
            raduis:50,
            opacity:[0,0.7]
        });    //在地图对象叠加热力图
        //具体参数见接口文档
    });
    setInterval(function (args) {
        var points =(function a(){  //<![CDATA[
            var city=[];
            $.ajax({
                type:"POST",
                url:"/mavensp_war_exploded/get_map",
                dataType:'json',
                async:false,        //
                success:function(result){
                    for(var i=0;i<result.length;i++){
                        //alert("调用了");
                        city.push({"lng":result[i].latitude,"lat":result[i].longitude,"count":result[i].count});
                    }

                }
            })
            return city;
        })();//]]>
        heatmap.setDataSet({data:points,max:100}); //设置热力图数据集
    },1000)


    // var map = new AMap.Map('container', {
    //    pitch:75, // 地图俯仰角度，有效范围 0 度- 83 度
    //    viewMode:'3D' // 地图模式
    //});
</script>

</body>
</html>