layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"

}).define(["form", "inputverify", "setter", "layer", "jquery", "laydate","table"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		a = o("body"),
		table = layui.table,
		laydate = layui.laydate;
		
		window.viewObj = {
		tbData: [{
			schStartId: null,
			projectId: null,
			schStartTime: null,
			schEndTime: null,
			schContent: null
		}],

	};
		var schedules = $("#schedules").val();
		//console.log(JSON.stringify(value))
		var obj = JSON.parse(schedules);
		var userId = $("#userId").val();
		setTimeout(function(){
			$("#projectUserId").val(userId);
			form.render('select');
		},100)
		
		
	var layTableId = "layTable";
	var tableIns = table.render({
		elem: '#dataTable',
		id: layTableId,
		data: obj,
		//width: tbWidth,
		page: true,
        cellMinWidth: 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		loading: true,
		even: false, //不开启隔行背景
		cols: [
			[{
					title: '序号',
					type: 'numbers'
				},
				{
					field: 'schStartTime',
					title: '开始时间',
					edit: 'text'

				},
				{
					field: 'schEndTime',
					title: '结束时间',
					edit: 'text'
				},
				{
					field: 'schContent',
					title: '进度',
					edit: 'text'
				},
				{
					field: 'tempId',
					title: '操作',
					templet: function(d) {
						console.log(d)
						return '<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del" ><i class="layui-icon layui-icon-delete"></i>移除</a>';
					}
				}
			]
		],
		done: function(res, curr, count) {
			console.log(res)
		}
	});
	//定义事件集合
	var active = {
		addRow: function() { //添加一行
			var oldData = table.cache[layTableId];
			console.log(oldData);
			var newRow = {
				schStartId: null,
				schStartTime: null,
				schEndTime: null,
				schContent: null
			};
			oldData.push(newRow);
			tableIns.reload({
				data: oldData
			});
		},
		updateRow: function(obj) {
			var oldData = table.cache[layTableId];
			console.log(oldData);
			for(var i = 0, row; i < oldData.length; i++) {
				row = oldData[i];
				if(row.tempId == obj.tempId) {
					$.extend(oldData[i], obj);
					return;
				}
			}
			tableIns.reload({
				data: oldData
			});
		},
		removeEmptyTableCache: function() {
			var oldData = table.cache[layTableId];
			for(var i = 0, row; i < oldData.length; i++) {
				row = oldData[i];
				if(!row || !row.scheduleId) {
					oldData.splice(i, 1); //删除一项
				}
				continue;
			}
			tableIns.reload({
				data: oldData
			});
		},
		save: function() {
			var oldData = table.cache[layTableId];
			console.log(oldData);

			document.getElementById("jsonResult").innerHTML = JSON.stringify(table.cache[layTableId], null, 2); //使用JSON.stringify() 格式化输出JSON字符串		
		}
	}

	//激活事件
	var activeByType = function(type, arg) {
		if(arguments.length === 2) {
			active[type] ? active[type].call(this, arg) : '';
		} else {
			active[type] ? active[type].call(this) : '';
		}
	}

	//注册按钮事件
	$('.layui-btn[data-type]').on('click', function() {
		var type = $(this).data('type');
		activeByType(type);
	});

	//监听工具条
	table.on('tool(dataTable)', function(obj) {
		var data = obj.data,
			event = obj.event,
			tr = obj.tr; //获得当前行 tr 的DOM对象;
		console.log(data);
		switch(event) {

			case "del":
				layer.confirm('真的删除行么？', function(index) {
					obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
					layer.close(index);
					activeByType('removeEmptyTableCache');
				});
				break;
		}
	});

	laydate.render({
		elem: '#startTime'

	});
	laydate.render({
		elem: '#endTime'

	});

	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	});
	var flag = false;
	form.on('submit(addproject)', function(obj) {
		if(!flag){
			flag = true;

		var oldData = table.cache[layTableId];
		console.log(oldData);
		
		var list =[];
		if(oldData.length == 0) {
			layer.msg("项目进度不能为空", {
				icon: 5
			}); //提
			return;
		}
		for(var i = 0, row; i < oldData.length; i++) {
			row = oldData[i];
			console.log(row);
			var pro = {};
			pro.scheduleId = row.scheduleId;
			pro.schStartTime = row.schStartTime;
			pro.schEndTime = row.schEndTime;
			pro.schContent = row.schContent;
			list.push(pro);
			if(row.schStartTime != null && row.schStartTime != "") {
				if(row.schStartTime.search(/^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/)) {
					layer.msg("时间格式为2019-01-01", {
						icon: 5
					}); //提示
					return;
				}
			} else {
				if(row.LAY_TABLE_INDEX == 0) {
					layer.msg("项目进度必须填写一行数据", {
						icon: 5
					}); //提示
					return;
				} else {
					layer.msg("每行数据开始时间为必填项", {
						icon: 5
					}); //提示
					return;
				}

			}
			if(row.schEndTime != null && row.schEndTime != "") {
				if(row.schEndTime.search(/^(\d{4})[-\/](\d{1}|0\d{1}|1[0-2])([-\/](\d{1}|0\d{1}|[1-2][0-9]|3[0-1]))*$/)) {
					layer.msg("时间格式为2019-01-01", {
						icon: 5
					}); //提示
					return;
				}
                var start = row.schStartTime.replace(/-/g, '/');
                var end = row.schEndTime.replace(/-/g, '/');
                var startdate  = new Date(start);
                var enddate  = new Date(end);
                if(enddate.getTime()<startdate.getTime()){
                    layer.msg("结束时间不能大于开始时间", {
                        icon: 5
                    }); //提示
                    return;
                }
			}

		}
		var project = {};
		project.projectId=$("#projectId").val();
		project.projectName = $("#projectName").val();
		project.projectIntroduction = $("#projectIntroduction").val();
		project.startTime = $("#startTime").val();
		project.endTime = $("#endTime").val();
		project.cooperationCorporate = $("#cooperationCorporate").val();
		project.projectMoney = $("#projectMoney").val();
		project.legalRepresentative = $("#legalRepresentative").val();
		project.status = $("input[name='status']:checked").val();
		project.projectAddress = $("#projectAddress").val();
		project.projectUserId = $("#projectUserId").val();
		project.schedules = list;
		console.log(JSON.stringify(project));
		$.ajax({
			url: layui.setter.project + "/project/edit",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: JSON.stringify(project) ,
			contentType:"application/json;charset=utf-8",
			dataType: "json",
			success: function(data) {
				console.log(data)
				if(data.result == 200) {
					parent.layer.alert(data.message, function() {
						console.log(index);
						parent.layer.close(index);

						parent.location.reload();

					});

				} else {
					parent.layer.alert(data.message);
				}
			}
		});
        }
	});
	
	//用户下拉框加载
	$.get(layui.setter.project + '/user/getroleuser', {}, function(data) {
		var $html = "<option  value=''>直接选择或搜索选择</option>";
		console.log(data)
		if(data != null) {
			$.each(data, function(index, item) {
				$html += "<option value='" + item.userId + "'>" + item.userName + "</option>";
			});
			$("select[name='projectUserId']").append($html);
			//反选
			$("select[name='projectUserId']").val($("#SearchDepartments").val());
			//append后必须从新渲染
			form.render('select');
		}
	});

	e("editproject", {})
})