layui.extend({
	setter: "../../../../static/layui/config"

}).define(["setter", "jquery", 'form', 'laypage', 'table'], function(e) {
    var urls = window.location.href;
    var data = decodeURI(urls);
    var arr = data.split("data=");
    console.log(arr[1]);
	var table = layui.table,
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
	table.render({
		elem: '#test',
		url: layui.setter.project + '/mytask/getMytaskPage',
		//toolbar: '#toolbarDemo',
		title: '通知',
        cellMinWidth: 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		cols: [
			[
				
				{
					field: 'userName',
					title: '发布人名称',
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
							name = "<span style='color:black'>已处理</span>"
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
			title: '回复通知',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
            area: [widthMax,  heightMax],
			shadeClose: false, //开启遮罩关闭
			content:  layui.setter.project+'/sys/editmytask',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#taskbId").val(data.taskBs[0].taskBId);
                body.find("#taskId").val(data.taskId);
				body.find("#taskName").val(data.users.userName);
				body.find("#taskRemark").val(data.taskRemark);
				body.find("#userName").val(data.taskBs[0].users.userName);
				if(data.file!=null){
					body.find("#filename").val(data.file.fileName);
					body.find("#filerename").val(data.file.fileRename);
				}
			},
			end: function() {
                renderTable();
                window.parent.task();
                console.log("调用父页面方法")
			},

		});
		}if(obj.event === 'info') {
			
			layer.open({ //弹框
			//id: 'insert-form',
			type: 2,
			//skin: 'layui-layer-demo', //样式类名
			title: '查看通知',
			maxmin: true,
			btn: [],
			yes: function(index, layero) {

			},
			closeBtn: 1, //不显示关闭按钮
			anim: 2,
            area: [widthMax,  heightMax],
			shadeClose: false, //开启遮罩关闭
			content:  layui.setter.project+'/sys/mytaskinfo',
			success: function(layero, index) {
				var body = layer.getChildFrame('body', index);
				body.find("#taskbId").val(data.taskBs[0].taskBId);
				body.find("#taskName").val(data.users.userName);
				body.find("#taskRemark").val(data.taskRemark);
				body.find("#taskSuggestion").val(data.taskBs[0].taskSuggestion);
				body.find("#userName").val(data.taskBs[0].users.userName);
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