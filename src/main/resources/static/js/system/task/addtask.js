layui.extend({
	setter: "../../../../static/layui/config",
	formSelects: "../../../../static/layui/formSelects-v4"

}).define(["form", "setter", "layer", "jquery", "upload","formSelects"], function(e) {
	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		o = layui.$,
		layer = layui.layer,
		formSelects = layui.formSelects,
		index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$ = layui.jquery,
		upload = layui.upload,
		a = o("body");
		var attachment="";
    	var sessionData = sessionStorage.getItem('user');//取出数据
	    var user = JSON.parse(sessionData);
    	console.log(user.userId);
    	form.val("myform", {
        	"taskName":user.userName  // "name": "value"
    	})
		
	layui.formSelects.config('select1', {
		direction: 'down',
		beforeSuccess: function(id, url, searchVal, result) {
			result = result.data;
			return result;
		},
		success: function(id, url, searchVal, result) {
			form.render('select'); //使用远程方式的success回调
			console.log(id); //组件ID xm-select
			console.log(url); //URL
			console.log(searchVal); //搜索的value
			console.log(result); //返回的结果
		}
	}).data('select1', 'server', {
		url: layui.setter.project + '/role/getroleUser'
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
				"taskUserId":user.userId,
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
    var flag =false;
	form.on('submit(add)', function(obj) {

		if(!flag){
            flag = true;
		up.upload();
		if(choose_file_flag==false){
			tijiao();
		}
        }

	});

	e("addtask", {})
})