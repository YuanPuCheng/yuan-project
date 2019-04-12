/** layuiAdmin.std-v1.2.1 LPPL License By http://www.layui.com/admin/ */ ;
layui.extend({
	setter: "../../layui/config",
	inputverify: "../common/inputverify"

}).define(["setter", "form", "inputverify","jquery"], function(e) {
		var s = layui.$,
		form = layui.form,
		$ = layui.jquery,
		a = s("body");

	//点击图片更换验证码
	a.on("click", "#LAY-user-get-vercode", function() {
		s(this);
		this.src = "http://127.0.0.1:8080/yzm/yzm";
	}),
	//提交
	form.on('submit(LAY-user-login-submit)', function(obj) {

			var usercode = $("#LAY-user-login-username").val();
			var password = $("#LAY-user-login-password").val();
			var vrifyCode = $("#LAY-user-login-vercode").val();
			var remember = $("#remember").prop('checked');
			console.log(remember)
			$.ajax({
				url: layui.setter.project+"/system/toLogin",
				type: "post",
				xhrFields: {
					withCredentials: true
				},
				data: {
					"usercode": usercode,
					"password": password,
					"vrifyCode": vrifyCode,
					"remeber": remember
				},
				//contentType:"application/json",
				dataType: "json",
				success: function(data) {
					console.log(data)
					if(data.result==400){
						layer.msg(data.message);
						//var usercode = $("#LAY-user-login-username").val("");
						var password = $("#LAY-user-login-password").val("");
						var vrifyCode = $("#LAY-user-login-vercode").val("");
					}else{
						window.location.href = "index.html";
					}
					
				}
			})
			return false;
		});
		
		
		
	$(function(){
		//$("#remember").attr("checked","checked");
		$.ajax({
				url: layui.setter.project+"/system/cookie",
				type: "post",
				xhrFields: {
					withCredentials: true
				},
				async: false,
				//contentType:"application/json",
				dataType: "json",
				success: function(data) {
					console.log(data)
					if(data.result==200){
						$("#LAY-user-login-username").val(data.map.usercode);
						$("#LAY-user-login-password").val(data.map.password);
						$("#remember").attr("checked","checked");
						$(".layui-unselect").addClass("layui-form-checked");
						console.log("记住我"+$("#remember").prop("checked"))
					}else{
						$("#remember").attr('checked',false);
						$(".layui-unselect").removeClass("layui-form-checked");
						console.log("记住我"+$("#remember").prop("checked"))
					}
					
				}
			})
		
	});

		 e("login", {})
});