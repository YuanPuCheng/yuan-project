layui.extend({
	setter: "../../../../static/layui/config",
	formSelects: "../../../../static/layui/formSelects-v4"

}).define(["form", "setter", "layer", "jquery", "upload","formSelects"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		formSelects = layui.formSelects,
		index = parent.layer.getFrameIndex(window.name), //获取窗口索引
		$ = layui.jquery,
		upload = layui.upload,
		a = o("body");
		var taskBId = $("#taskbId").val();
    	var taskId = $("#taskId").val();
		var attachment = "";
		var fileName = $("#filename").val();
		var fileRename = $("#filerename").val();
		if(fileRename!=''&&fileRename!=null){
			$("#down").css("display","inline-block");
		}
		$("#attachment").html(fileRename);
		$("#down").attr("href",layui.setter.project+'/file/down?filename='+fileName);
		
		$.ajax({
			type:"get",
			url:layui.setter.project+"/mytask/mytaskstatus",
			async:true,
			data: {
				"taskBId":taskBId
			},
			dataType: "json",
			success:function(o){
				
			}
		});

	//选完文件后不自动上传
	var choose_file_flag = false;
	var up = upload.render({
		elem: '#test8',
		url: layui.setter.project +'/file/upload',
		data: {
			filetype: "TA"
		},
		accept: 'file',
		auto: false,
		bindAction: '',
		before: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
		  	choose_file_flag = true;
  		},
		
		done: function(res) {
			attachment = res.map.fileID;
			choose_file_flag = true;
			tijiao();
		}
	});

	a.on('click', "#addqx", function(o) {

		parent.layer.close(index);

		parent.location.reload();

	})
	
	
	function tijiao(){
		
		var taskSuggestion = $("#taskSuggestion").val();
		$.ajax({
			url: layui.setter.project + "/mytask/addmytask",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"taskBId":taskBId,
                "taskId":taskId,
				"taskAttachment":attachment,
				"taskSuggestion": taskSuggestion
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
                    window.parent.parent.task();
				} else {
					parent.layer.alert(data.message);
				}
			}
		});
	};
	var flag = false;
	form.on('submit(add)', function(obj) {
		if(!flag){
			flag = true;

		up.upload();
		if(choose_file_flag==false){
			tijiao();
		}
        }

	});

	e("editmytask", {})
})