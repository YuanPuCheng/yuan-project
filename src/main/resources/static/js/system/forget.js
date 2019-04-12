layui.extend({
	setter: "../../layui/config",
	inputverify: "../common/inputverify"

}).define(["setter", "form", "inputverify","jquery"], function(e) {
		var s = layui.$,
		form = layui.form,
		$ = layui.jquery,
		a = s("body");
	
	
		form.on('submit(forgetpasswrod)', function(obj) {
			var usercode = $("#forusercode").val();
			var password = $("#forpassword").val();
			var emailcode = $("#foremailcode").val();
			var email = $("#foremail").val();
			console.log(email)
			$.ajax({
				url: layui.setter.project+"/system/forget",
				type: "post",
				xhrFields: {
					withCredentials: true
				},
				data: {
					"usercode": usercode,
					"password": password,
					"email": email,
					"emailcode": emailcode
				},
				//contentType:"application/json",
				dataType: "json",
				success: function(data) {
					console.log(data)
					if(data.result==400){
						layer.msg(data.message);
						
						
					}else{
						layer.msg(data.message);
						setTimeout(function () { 
        				location.href=layui.setter.project+"/sys/login";
    				}, 2000);
					}
					
				}
			})
			return false;
			
			
		});
		a.on('click', "#forgetemail", function(o) {
		var email = $("#foremail").val();
		
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
					getRandomCode($("#forgetemail"));
					layer.msg(data.message)
				}
		}
		});

	});
    $(function(){
        $("#loginto").attr("href",layui.setter.project+"/sys/login");
        $("#regto").attr("href",layui.setter.project+"/sys/redirect");
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


	 e("forget", {});
})