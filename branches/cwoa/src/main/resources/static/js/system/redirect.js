layui.extend({
setter: "../../layui/config",
inputverify: "../common/inputverify"
}).define(["setter", "form", "inputverify", "jquery"], function(e) {
	var $ = layui.jquery,
		s = layui.$,
		a = s("body"),
		form = layui.form;

	form.on('submit(LAY-user-reg-submit)', function(obj) {
		var usercode = $("#LAY-user-login-code").val();
		var email = $("#email").val();
		var vrifyCode = $("#LAY-user-login-vercode").val();
		var password = $("#password").val();
		var erpassword = $("#repassword").val();
		console.log(vrifyCode)
		$.ajax({
			url: layui.setter.project + "/system/redirect",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"usercode": usercode,
				"password": password,
				"vrifyCode": vrifyCode,
				"email": email
			},
			//contentType:"application/json",
			dataType: "json",
			success: function(data) {
				if(data.result==200){
					layer.msg(data.message);
					setTimeout(function () { 
        				location.href=layui.setter.project+"/sys/login";
    				}, 2000);
					
				}else{
					layer.msg(data.message);
					$("#LAY-user-login-vercode").val("");
					form.render();
				}
			}
		})
	});

	a.on('click', "#emailcode", function(o) {
		var email = $("#email").val();
		
		if(email==null||email==""){
			layer.msg("请输入邮箱");
			return;
		}
		$.ajax({
		type:"post",
		url:layui.setter.project+"/yzm/emailyzm",
		async:true,
		xhrFields: {
			withCredentials: true
				},
		data: {
				"email":email
			},
		dataType: "json",
		success: function(data) {
				if(data.result==400){
					layer.msg(data.message)
				}else{
					getRandomCode($("#emailcode"));
					layer.msg(data.message)
				}
		}
		});

	});

var wait = 60;
//倒计时
var getRandomCode =function (o) {
  				if (wait == 0) {
                   $(o).attr("disabled", false);
                   $(o).text("获取验证码");
                   wait = 60;
               	} else {
                   $(o).attr("disabled", true);
                   o.text(wait + "秒后重新发送");
                   wait--;
                   setTimeout(function () {
                   	getRandomCode(o);
                   },1000);
               }
};

    $(function(){
        $("#login").attr("href",layui.setter.project+"/sys/login");
    })
e("redirect", {})
})