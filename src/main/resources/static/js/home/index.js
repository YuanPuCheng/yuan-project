layui.extend({
    setter: "../../../static/layui/config"

}).define(["setter", "jquery",'layer','carousel'], function(e) {
    var $ = layui.jquery;
    var layer=layui.layer;
    var carousel = layui.carousel;

    carousel.render({
        elem: '#test10'
        ,width: '100%'
        ,height: '100%'
        ,interval: 5000
    });
    var myChart = echarts.init(document.getElementById('main'));
    var widthMax = "70%",
        heightMax = "80%";
    if($(window).width() < 768) {
        widthMax = "99%";
        heightMax = "85%";
    }
    //根据窗口的大小变动图表 --- 重点
    window.onresize = function(){
        myChart.resize();
        //myChart1.resize();    //若有多个图表变动，可多写

    };
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
    //公告页面渲染开始
    $.ajax({
        url: '/notice/queryNotice',
        type: "get",
        data: {
            "size":10,
            "page":1,
            "limit":10
        },
        dataType: "json",
        success: function(result) { //请求成功的回调
            var noticeList=result.data;
            var noticeListLen=noticeList.length;
            var noticeHtml="";
            for (var i=1;i<=noticeListLen;i++){
                var noticeTime=new Date(noticeList[i-1].create_time).format("yyyy-MM-dd");
                noticeHtml+='<div class="noticeStyle" noticeIndex="'+i+'"><span style="display:inline-block;width: 20px">'+i+'.</span>'+
                            '<span style="color:#01AAED;">'+
                            noticeList[i-1].notice_name+'</span>' +
                            '<span style="position: absolute;right: 25px">'+noticeTime+'</span></div>';
            }
            $('#notice').html(noticeHtml);
            $('#notice div').click(function () {
                var noticeIndex=$(this).attr("noticeIndex");
                if (typeof noticeIndex==='string'){
                    layer.open({
                        type: 1,
                        shadeClose: true,
                        title:noticeList[noticeIndex-1].notice_name,
                        area: [widthMax, heightMax], //宽高
                        content: noticeList[noticeIndex-1].notice_text
                    });
                }
            });
        },
        error: function() { //请求失败的回调
            layer.msg('发生了未知错误！', {
                icon: 2
            });
        }
    });//公告页面渲染结束







	 e("index", {})
});

