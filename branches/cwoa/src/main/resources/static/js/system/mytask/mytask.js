layui.extend({
	setter: "../../../../static/layui/config"

}).define(["setter", "jquery", 'form', 'laypage', 'table'], function(e) {

	var table = layui.table,
		laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		$ = layui.jquery;
	var widthMax = 1050,
		heightMax = 570;
	if($(window).width() < 768) {
		widthMax = 280;
		heightMax = 350
	}
	table.render({
		elem: '#test',
		url: layui.setter.project + '/mytask/getMytaskPage',
		//toolbar: '#toolbarDemo',
		title: '任务',
		cols: [
			[
				
				{
					field: 'userName',
					title: '指派人',
					templet: function(d) {
						var u = d.users;
						console.log(d);
						var name = "";
						if(u!=null){
							name = u.userName;
						}
						return name;
					},					
					sort: true
				},
				{
					field: 'ts',
					title: '发布时间'
				},
				{
					field: 'taskRemark',
					title: '描述'
				}
				,
				{
					field: 'taskStatus',
					title: '状态',
					templet: function(d) {
						console.log(d)
						var name = "";
						if(d.taskBs[0].taskStatus==2){
							name = "<span style='color:green'>已接收</span>"
						}else if(d.taskBs[0].taskStatus==3){
							name = "<span>已查看</span>"
						}else if(d.taskBs[0].taskStatus==4){
							name = "<span style='color:black'>已完成</span>"
						}
						return name;
					}
				}
				,{
					//fixed: 'right',
					title: '操作',
					toolbar: '#barDemo',
					width:210,
					
				}
			]
		],
		done: function(res, curr, count){
    		console.log(res);
    		console.log(curr); 
    		console.log(count);
  	},
		page: true
	});

	$('#Search').on('click', function() {
		renderTable();
	});

	function renderTable() {
		table.reload('test', {
			url: layui.setter.project + '/mytask/getMytaskPage'
		});
	}

	
	//监听行工具事件
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		console.log(data);
		if(obj.event === 'edit') {
			layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '查看任务',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
			area: [widthMax + "px", heightMax + "px"],
			shadeClose: false, //开启遮罩关闭
			content:  'editmytask.html',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#taskbId").val(data.taskBs[0].taskBId);
				body.find("#taskName").val(data.taskBs[0].users.userName);
				body.find("#taskRemark").val(data.taskRemark);
				body.find("#userName").val(data.users.userName);
				if(data.file!=null){
					body.find("#filename").val(data.file.fileName);
					body.find("#filerename").val(data.file.fileRename);
				}
			},
			end: function() {

			},

		});
		}if(obj.event === 'info') {
			
			layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '查看任务',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
			area: [widthMax + "px", heightMax + "px"],
			shadeClose: false, //开启遮罩关闭
			content:  'mytaskinfo.html',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#taskbId").val(data.taskBs[0].taskBId);
				body.find("#taskName").val(data.taskBs[0].users.userName);
				body.find("#taskRemark").val(data.taskRemark);
				body.find("#taskSuggestion").val(data.taskBs[0].taskSuggestion);
				body.find("#userName").val(data.users.userName);
				if(data.taskBs[0].file!=null){
					body.find("#taskb_filename").val(data.taskBs[0].file.fileName);
					body.find("#taskb_filerename").val(data.taskBs[0].file.fileRename);
				}
				if(data.file!=null){
					body.find("#filename").val(data.file.fileName);
					body.find("#filerename").val(data.file.fileRename);
				}
			},
			end: function() {

			},

		});
		}
	});
	e("mytask", {})
});