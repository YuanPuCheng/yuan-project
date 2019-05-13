layui.extend({
	setter: "../../../../static/layui/config"

}).define(["setter", "jquery", 'form', 'laypage', 'table','laytpl'], function(e) {
    var urls = window.location.href;
    var data = decodeURI(urls);
    var arr = data.split("data=");
    console.log(arr[1]);
	var table = layui.table,
		laytpl = layui.laytpl,
        laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		$ = layui.jquery;
    var widthMax = "70%",
        heightMax = "80%";
    if($(window).width() < 768) {
        widthMax = "100%";
        heightMax = "80%";
    }
    var data={
        add : '',
        deletes:''
    }

    var result = {
        del:'',
        edit:''
    }
    if(arr[1]!=null){
        var array = arr[1].split(',');
        for(var i =0;i<array.length;i++){
            switch (array[i]){
                case ("添加"): data.add="添加";break;
                case ("删除"): result.del="删除";break;
                case ("编辑"): result.edit="编辑";break;
                case ("批量删除"): data.deletes="批量删除";break;
            }
        }

    }



    laytpl($('#toolbarDemo').html()).render(data, function(html){
        $('#toolbarDemo').html(html);
    });
    laytpl($('#barDemo').html()).render(result, function(html){
        $('#barDemo').html(html);
    });
	table.render({
		elem: '#test',
		url: layui.setter.project + '/department/getdepartmentPage',
		toolbar: '#toolbarDemo',
        cellMinWidth: 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		title: '部门',
		cols: [
			[{
					type: 'checkbox'
				},
				{
					field: 'departmentName',
					title: '部门名称',
					sort: true
				},
				{
					field: 'departmentCode',
					title: '部门编码'
				},
				{
					field: 'ts',
					title: '时间'
				},

				{
					field: 'status',
					title: '部门',
					templet: function(d) {
						var name = ""
						if(d.status == 0) {
							name = "正常"
						} else {
							name = "禁用"
						}
						return name;
					}
				}, {
					//fixed: 'right',
					title: '操作',
					toolbar: '#barDemo',
					width: 120
				}
			]
		],
		page: true
	});

	$('#Search').on('click', function() {
		renderTable();
	});

	function renderTable() {
		var derNAme = $('#derNAme').val();
		//				console.log(name1+sex1);
		table.reload('test', {
			url: layui.setter.project + '//department/getdepartmentPage',
			where: {
				departmentName: $('#derNAme').val()

			}
		});
	}

	//头工具栏事件
	table.on('toolbar(test)', function(obj) {
		var checkStatus = table.checkStatus(obj.config.id);
		switch(obj.event) {
			case 'add':
				layer.open({ //弹框
					id: 'insert-form',
					type: 2,
					//skin: 'layui-layer-demo', //样式类名
					title: '新增菜单',
					maxmin: true,
					btn: [],
					yes: function(index, layero) {

					},
					closeBtn: 1, //不显示关闭按钮
					anim: 2,
                    area: [widthMax,  heightMax],
					shadeClose: false, //开启遮罩关闭
					content: layui.setter.project+'/sys/adddepartment',
					success: function(layero, index) {

					},
					end: function() {

					},

				});

				break;
			case 'deletes':
				var data = checkStatus.data;
				if(data.length == 0) {
					layer.alert("未选中数据");
					return false;
				}
				var name = "";
				var id = "";
				$.each(data, function(index, value) {
					name = name + value.departmentName + "、";
					id = id + value.departmentId + ","
				});
				if(name != "" || name != null) {
					name = name.substring(0, name.length - 1);
					id = id.substring(0, id.length - 1);
				}
				layer.confirm('已选择' + name + "，是否删除？", function(index) {
					$.ajax({
						url: layui.setter.project + "/department/deletes",
						type: "post",
						xhrFields: {
							withCredentials: true
						},
						data: {
							"departmentIds": id
						},
						//contentType:"application/json",
						dataType: "json",
						success: function(data) {
							if(data.result == 200) {
								renderTable();
								layer.close(index);
							}
						}
					});

				})
				break;

		};
	});

	//监听行工具事件
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		console.log(data);
		if(obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				$.ajax({
					url: layui.setter.project + "/department/del",
					type: "post",
					xhrFields: {
						withCredentials: true
					},
					data: {
						"departmentId": data.departmentId
					},
					//contentType:"application/json",
					dataType: "json",
					success: function(data) {
						if(data.result == 200) {
							renderTable();
							layer.close(index);
						}
					}
				});

			});
		} else if(obj.event === 'edit') {
			console.log(data)
			layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '修改部门',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
                area: [widthMax,  heightMax],
			shadeClose: false, //开启遮罩关闭
			content: layui.setter.project+'/sys/editdepartment',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#departmentName").val(data.departmentName);
				body.find("#departmentCode").val(data.departmentCode);
				body.find("input[name=status][value="+data.status+"]").attr("checked","checked");
				console.log(data.status)
				body.find("#departmentId").val(data.departmentId);
				

			},
			end: function() {

			},

		});
		}
	});
	e("department", {})
});