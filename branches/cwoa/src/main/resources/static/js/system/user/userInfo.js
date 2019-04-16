layui.extend({
	inputverify: "../../common/inputverify",
	setter: "../../../../static/layui/config"
}).define(['upload', "form", "inputverify", "setter"], function(e) {

	var form = layui.form, //只有执行了这一步，部分表单元素才会自动修饰成功
		$ = layui.jquery,
		s = layui.$,
		a = s("body"),
		upload = layui.upload;
	console.log(layui.setter.project);
	//页面初始化方法
	$(function() {
		$("#tijiao").hide();
		$("#quxiao").hide();
		$("#test1").hide();
		$(".dis").attr("disabled", "disabled");
		$(".dis").css("border", "none");
		$("input[name='sex']").attr("disabled", "disabled");
		$.ajax({
			url: layui.setter.project + "/user/getuserinfo",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			dataType: "json",
			success: function(data) {
				console.log(data);
				var de = eval(data.departments);
				var name="";
				if(de!=null){
					$.each(de,function(index,value){
						name=name+value.departmentName+"、"
					})
					name = name.substring(0,name.length-1);
				}
				
				console.log(name);
				filename = data.images;
				$("#demo1").attr("src",layui.setter.project + "/file/show?filename="+data.images);
				form.val("myform", {
					"userId":data.userId,
					"userCode":data.userCode,
					"userName": data.userName, // "name": "value,
					"phone": data.phone,
					"email": data.email,
					"idNum": data.idNum,
					"sex": data.sex,
					"bankCardNum": data.bankCardNum,
					"departmentName":name,
					"projectName":data.project.projectName
				})
			}
		});

		
	})

	var filename = "";
	//普通图片上传
	var uploadInst = upload.render({
		elem: '#test1',
		url: layui.setter.project + '/file/upload',
		data: {
			filetype: "TX"
		},
		auto: false, //选择文件后不自动上传
		accept: 'images', //校验的文件类型
		acceptMime: 'image/*', //规定打开文件选择框时，筛选出的文件类型
		size: '5000', //5m 单位 KB
		bindAction: '#tijiao', //指向一个按钮触发上传
		choose: function(obj) { //选择文件后的回调函数
			//预读本地文件示例，不支持ie8
			obj.preview(function(index, file, result) {
				$('#demo1').attr('src', result); //图片链接（base64）
			});
		},
		before: function(obj) { //文件上传前的回调函数

		},
		done: function(res) {
			console.log(res)

			if(res.result == 200) {
				filename = res.map.filename;
				console.log(filename);
			}
			//上传成功
		},
		error: function() {
			//演示失败状态，并实现重传
			var demoText = $('#demoText');
			demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
			demoText.find('.demo-reload').on('click', function() {
				uploadInst.upload();
			});
		}
	});

	a.on("click", "#edit", function() {
		form.render();
			$("#edit").hide();
			$("#tijiao").show();
			$("#quxiao").show();
			$("input[name='sex']").removeAttr("disabled");
			$(".dis").removeAttr("disabled");
			$(".dis").css("border", "1px solid #e6e6e6");
			$("#test1").show();
			form.render();
		}),
		a.on("click", "#quxiao", function() {
			
			$(".dis").attr("disabled", "disabled");
			$("#quxiao").hide();
			$("#test1").hide();
			$("#edit").show();
			$("#tijiao").hide();
			$("input[name='sex']").attr("disabled", "disabled");
			$(".dis").css("border", "none");
			form.render();
		})

	//监听提交按钮
	form.on('submit(setmyinfo)', function(data) {

		setTimeout(function() {
			var userId = $("#userId").val();
			var usercode = $("#LAY-user-login-code").val();
			var username = $("#username").val();
			var email = $("#email").val();
			var images = filename;
			var cellphone = $("#cellphone").val();
			var bankCardNum = $("#bankCardNum").val();//银行卡
			var idNum = $("#idNum").val();//身份证
			var item = $("input[name='sex']:checked").val();
			
			console.log("进入ajax" + userId+usercode+username+images+cellphone+bankCardNum+idNum+item+email);
			$.ajax({
				url: layui.setter.project + "/user/userinfo",
				type: "post",
				xhrFields: {
					withCredentials: true
				},
				data: {
					"userId":userId,
					"userCode": usercode,
					"userName": username,
					"images": images,
					"phone": cellphone,
					"email":email,
					"sex": item,
					"bankCardNum":bankCardNum,
					"idNum":idNum
				},
				//contentType:"application/json",
				dataType: "json",
				success: function(data) {
					if(data.result == 200) {
						layer.msg(data.message);
						$(".layui-input").attr("disabled", "disabled");
						$("#quxiao").hide();
						$("#test1").hide();
						$("#edit").show();
						$("#tijiao").hide();
						$("input[name='sex']").attr("disabled", "disabled");
						$(".layui-input").css("border", "none");
						form.render();
					} else {
						layer.msg(data.message);
						form.render();
					}
				}
			});
		}, 1000);

	});

	e("userInfo", {})
})