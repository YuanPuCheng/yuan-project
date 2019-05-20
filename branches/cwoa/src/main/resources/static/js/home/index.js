layui.extend({
    setter: "../../../static/layui/config"

}).define(["setter", "jquery"], function(e) {
    var $ = layui.jquery;
    var myChart = echarts.init(document.getElementById('main'));
//根据窗口的大小变动图表 --- 重点
    window.onresize = function(){
        myChart.resize();
        //myChart1.resize();    //若有多个图表变动，可多写

    }
    $.ajax({
        type: "get",
        url: layui.setter.project+"/project/projectEchars",
        async: true,
        success: function(data) {

            myChart.setOption({

                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: data.name
                },
                series: [{
                    name: '数量',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '45%'],
                    data: data.namevalue,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            });
            console.log(data)

        }
    });





	 e("index", {})
});