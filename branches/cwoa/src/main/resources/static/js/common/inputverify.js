/**
 * 公用自定义校验类
 * 
 */
layui.define(["form","jquery"],function(e){
	var $ = layui.jquery;
	var form = layui.form;
	
	//正则校验方法，可添加
	form.verify({
		nickname: function(e, s) {
			return new RegExp("^[a-zA-Z0-9_一-龥\\s·]+$").test(e) ? /(^\_)|(\__)|(\_+$)/.test(e) ? "用户名首尾不能出现下划线'_'" : /^\d+\d+\d$/.test(e) ? "用户名不能全为数字" : void 0 : "用户名不能有特殊字符"
		},
		pass: [/^[\S]{6,12}$/, "密码必须6到12位，且不能出现空格"],
		repass: function(value){
			var aa = $("#password").val();
			console.log(aa);
			if(aa!=value){
				return "二次密码不一致";
			}
		},
		usercode:[/^[0-9a-zA-Z]+$/,'只能输入英文和数字'],
		user: function(value, item){//校验后台是否有相同用户
			var checkResult;
			$.ajax({
				url: layui.setter.project+"/system/getuser",
				type: "post",
				data: {"usercode":value},
				dataType: "json",
				async: false,//同步请求
				success: function(result) {
					if(result.result==400){
						checkResult = result.message;
			    		return result.message;
			    	}
				}
			
			});
				return checkResult;
		},
		emailuser: function(value, item){//校验后台是否有相同邮箱
			var checkResult;
			console.log(value)
			$.ajax({
				url: layui.setter.project+"/system/getemail",
				type: "post",
				data: {"email":value},
				dataType: "json",
				async: false,//同步请求
				success: function(result) {
					if(result.result==400){
						checkResult = result.message;
			    		return result.message;
			    	}
				}
			
			});
				return checkResult;
		}
	});
	e("inputverify", {})
})


