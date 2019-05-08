layui.extend({
	setter: "../../../../static/layui/config"

}).define(["form", "setter", "layer", "jquery", "upload","table"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		table = layui.table,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		upload = layui.upload,
		a = o("body");
			
		var taskb = $("#taskb").val();
		var fileName = $("#filename").val();
		var taskRemark = $("#taskRemark").val();
		var userName = $("#userName").val();
		var fileRename = $("#filerename").val();
		console.log(fileName);
		if(fileRename!=''&&fileRename!=null){
			$("#down").css("display","inline-block");
		}
		$("#attachment").html(fileRename);
		$("#down").attr("href",layui.setter.project+'/file/down?filename='+fileName);
		var obj = JSON.parse(taskb);
		var userId = $("#userId",parent.document).attr("name");
		
		form.val("myform", {
  			"taskName":userName  // "name": "value"
  			,"taskRemark": taskRemark
		})
	table.render({
		elem: '#test',
		data:obj,
        cellMinWidth: 120, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		//url: layui.setter.project + '/task/gettaskPage',
		//toolbar: '#toolbarDemo',
		title: '任务',
		cols: [
			[
				{
					field: 'userName',
					title: '待办人',
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
				}
				,
				{
					field: 'taskLookTime',
					title: '查看时间'
				}
				,
				
				{
					field: 'taskSuggestion',
					title: '备注'
				},
				{
					field: 'taskEndTime',
					title: '处理时间'
				}
				,
				{
					field: 'taskAttachment',
					title: '附件下载',
                    templet: function(d) {

                        var name = "";
                        var task = d.file;
                        console.log(task);
                        if(task!=null){
                            name = "<a style='color: #01AAED;' href='"+layui.setter.project +"/file/down?filename="+task.fileName+"'>"+task.fileRename+"</a>"
                        }
                        return name;
                    }
				},
				{
					field: 'taskStatus',
					title: '状态',
					templet: function(d) {
						
						var name = "";
						if(d.taskStatus==1){
							name = "<span></span>"
						}else if(d.taskStatus==2){
							name = "<span style='color:green'>已通知</span>"
						}else if(d.taskStatus==3){
							name = "<span style='color:red'>已查看</span>"
						}else if(d.taskStatus==4){
							name = "<span style='color:black'>已处理</span>"
						}
						return name;
					}
				}
				
			]
		],
		done: function(res, curr, count){
  	}
	});


	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);
		//parent.location.reload();

	})
	
	
	
	function tijiao(){
		var taskRemark = $("#taskRemark").val();
		var users = formSelects.value('select1', 'valStr');
		$.ajax({
			url: layui.setter.project + "/task/addtask",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"taskUserId":46,
				"attachment":attachment,
				"taskRemark": taskRemark,
				"tempVar1": users
			},
			//contentType:"application/json;charset=utf-8",
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

	form.on('submit(add)', function(obj) {
		up.upload();
		if(choose_file_flag==false){
			tijiao();
		}
		

	});

	e("edittask", {})
})