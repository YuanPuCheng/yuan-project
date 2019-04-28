layui.extend({
	setter: "../../../../static/layui/config",
	inputverify: "../../common/inputverify",
	formSelects: "../../../../static/layui/formSelects-v4"

}).define(["setter", "inputverify", "jquery", "formSelects", "form"], function(e) {

	var $ = layui.jquery,
		formSelects = layui.formSelects,
		form = layui.form,
		o = layui.$,
		index = parent.layer.getFrameIndex(window.name),
		a = o("body");;
	a.on('click', "#addqx", function(o) {
		parent.layer.close(index);

	})

	//多选

	layui.formSelects.config('select1', {
		keyName: 'departmentName', //自定义返回数据中name的key, 默认 name
		keyVal: 'departmentId',
		beforeSuccess: function(id, url, searchVal, result) {
			//我要把数据外层的code, msg, data去掉
			result = result.data;

			//然后返回数据
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
		url: layui.setter.project + '/department/getdepartment'
	});

	//项目下拉框加载
	$.get(layui.setter.project + '/project/getprojecttoselect', {}, function(data) {
		var $html = "<option  value='0'  class='generate'>请选择</option>";
		if(data != null) {
			$.each(data, function(index, item) {
				if(item.proType) {
					$html += "<option value='0'  class='generate'>请选择</option>";
				} else {
					$html += "<option value='" + item.projectId + "'>" + item.projectName + "</option>";
				}
			});
			$("select[name='projects']").append($html);
			//反选
			$("select[name='projects']").val($("#SearchProjects").val());
			//append后必须从新渲染
			form.render('select');
		}
	})

	//提交
	form.on('submit(adduser)', function(obj) {
		var usercode = $("#usercode").val();
		var username = $("#username").val();
		var email = $("#email").val();
		var phone = $("#phone").val();
		var departments = formSelects.value('select1', 'valStr');
		var projects = $("#project").val();
		var idNum = $('#idNum').val();
		var bankCardNum = $('#bankCardNum').val();
		var state = $('input[name="state"]:checked').val();

		$.ajax({
			url: layui.setter.project + "/user/add",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"userCode": usercode,
				"userName": username,
				"userPassword": "A!123456",
				"email": email,
				"phone": phone,
				"tempVar2": departments,
				"tempInt1": projects,
				"idNum": idNum,
				"bankCardNum": bankCardNum,
				"status": state,

			},
			dataType: "json",
			success: function(data) {
				if(data.result == 200) {
					parent.layer.alert(data.message, function() {
						parent.layer.close(index);
						//重新加载父页面
						parent.location.reload();
					})
				} else {
					layer.msg(data.message);
				}

			}
		})
		form.render();
		return false;
	});
	e("addMangement", {})

});