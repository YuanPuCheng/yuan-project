layui.extend({
	setter: "../../../static/layui/config"

}).define(["setter", "jquery", 'form', 'laydate'], function(e) {

	var form = layui.form,
		laydate = layui.laydate,
		$ = layui.jquery;

	var data = {};
	var dataArr = [];
    var sessionData = sessionStorage.getItem('user');//取出数据
    var user = JSON.parse(sessionData);
    console.log(user.userId);
	var userID = user.userId;
	render(userID);

	function render(userId) {
		$.ajax({
			type: "get",
			url: layui.setter.project + "/trip/trip",
			async: true,
			data: {
				userId: userId
			},
			success: function(res) {
				//data = JSON.parse(d);
				var new_date = new Date();
				loding_date(new_date, res);
			}
		});

	}

	//定义json	

	function formatDate(date) {
		var d = new Date(date),
			month = '' + (d.getMonth() + 1),
			day = '' + d.getDate(),
			year = d.getFullYear();

		if(month.length < 2) month = '0' + month;
		if(day.length < 2) day = '0' + day;

		return [year, month, day].join('-');
	}
	//console.log(JSON.stringify(data));

	$("#add").click(function() {
		console.log(JSON.stringify(dataArr))
	})
	//日历插件调用方法  
	function loding_date(date_value, data) {
		//var value = formatDate(date_value);
		//console.log(date_value)
		var mintime = formatDate(new Date());
		laydate.render({
			elem: '#test-n2',
			type: 'date',
			theme: 'grid',
			min: mintime,
			max: '2099-06-16 23:59:59',
			position: 'static',
			mark: data, //重要json！	
			range: false,
			value: date_value,
			isInitValue: false,
			calendar: true,
			btns: false,
			ready: function(value) {
				//console.log(value);
				hide_mr(value);
			},
			done: function(value, date, endDate) {
				//console.log(value); //得到日期生成的值，如：2017-08-18
				// console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
				//console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
				//layer.msg(value)
				//调用弹出层方法
				date_chose(value, data);
			},
			change: function(value, date) {
				hide_mr(date);
			}

		});
	}

	function hide_mr(value) {
		var mm = value.year + '-' + value.month + '-' + value.date;
		
		//$('.laydate-theme-grid table tbody').find('[lay-ymd="' + mm + '"]').
		$('.laydate-theme-grid table tbody').find('[lay-ymd="' + mm + '"]').removeClass('layui-this');
		//console.log(value)
	}

	//获取隐藏的弹出层内容
	var date_choebox = $('.date_box').html();

	//定义弹出层方法
	function date_chose(obj_date, data) {
		var index = layer.open({
			type: 1,
			skin: 'layui-layer-rim', //加上边框
			title: '添加记录',
			area: ['400px', 'auto'], //宽高
			btn: ['确定', '删除', '取消'],
			content: '<div class="text_box">' +
				'<form class="layui-form" action="">' +
				'<div class="layui-form-item layui-form-text">' +
				' <textarea id="text_book" placeholder="请输入内容"  class="layui-textarea"></textarea>' +
				'</div>' +
				'</form>' +
				'</div>',
			success: function() {
				$('#text_book').val(data[obj_date])
			},
			yes: function() {
				//调用添加/编辑标注方法
				if($('#text_book').val() != '') {
					chose_moban(obj_date, data);
					layer.close(index);
				} else {
					layer.msg('不能为空', {
						icon: 2
					});
				}

			},
			btn2: function() {

				chexiao(obj_date, data);
			}
		});
	}

	function del(data, userId) {
		$.ajax({
			type: "post",
			url: layui.setter.project + "/trip/del",
			async: true,
			data: {
				"tripTime": data,
				"tripUserId": userId
			},
			success: function(res) {
				if(res.result == 200) {
					layer.msg(res.message);
				}

			}
		});
	}

	function addoredit(data, content, userId) {
		$.ajax({
			type: "post",
			url: layui.setter.project + "/trip/addoredit",
			async: true,
			data: {
				"tripTime": data,
				"tripContent": content,
				"tripUserId": userId
			},
			success: function(res) {
				if(res.result == 200) {
					layer.msg(res.message)
				}
			}
		});

	}

	//定义添加/编辑标注方法
	function chose_moban(obj_date, markJson) {
		//console.log(obj_date)
		//console.log(markJson)
		//获取弹出层val
		var chose_moban_val = $('#text_book').val();
        //var content =  "<span style='color:red;font-size:14px'>"+chose_moban_val+"</span>";
		$('#test-n2').html(''); //重要！由于插件是嵌套指定容器，再次调用前需要清空原日历控件
		//添加属性 
		markJson[obj_date] = chose_moban_val;
		addoredit(obj_date, chose_moban_val, userID);
		data = {
			time: obj_date,
			value: chose_moban_val
		}

		//添加修改数值
		for(var i in dataArr) {
			if(dataArr[i].time == obj_date) {
				dataArr[i].value = chose_moban_val;
				dataArr.splice(i, 1);
			}
		}
		dataArr.push(data);

		//按时间正序排序

		dataArr.sort(function(a, b) {
			return Date.parse(a.time) - Date.parse(b.time); //时间正序
		});
		console.log(dataArr)
		//按时间倒序排列
		//				dataArr.sort(function(a,b){
		//					return Date.parse(b.time) - Date.parse(a.time);//时间正序
		//				});

		//console.log(JSON.stringify(data))
		// console.log(JSON.stringify(markJson));
		//console.log(JSON.stringify(dataArr))

		//再次调用日历控件，
		loding_date(obj_date, markJson); //重要！，再标注一个日期后会刷新当前日期变为初始值，所以必须调用当前选定日期。

	}

	//撤销选择
	function chexiao(obj_date, markJson) {
		if(markJson[obj_date] != null) {
			//判断是否存在数据，存在执行删除，不存在不执行
			del(obj_date, userID);
		}
		//删除指定日期标注
		delete markJson[obj_date];
		//console.log(JSON.stringify(markJson));

		for(var i in dataArr) {
			if(dataArr[i].time == obj_date) {

				dataArr.splice(i, 1);
			}
		}

		console.log(JSON.stringify(dataArr))
		//原理同添加一致
		$('#test-n2').html('');
		loding_date(obj_date, markJson);

	}
	e("trip", {})
});