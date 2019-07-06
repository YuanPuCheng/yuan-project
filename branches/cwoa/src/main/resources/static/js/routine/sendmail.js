layui.extend({
	setter: "../../../static/layui/config",
	formSelects: "../../../static/layui/formSelects-v4"

}).define(["setter", "jquery", 'form', 'laypage', 'table','formSelects'], function(e) {
	var $ = layui.jquery;
	var layer = layui.layer;
	var form = layui.form;
	var E = window.wangEditor;
	var formSelects = layui.formSelects;
	var editor = new E('#div1');
	var userId = "46";
	$("#addcs").click(function(){
		$("#addcs").hide();
		$("#decs").show();
		$("#cspeo").show();
	});
	$("#addms").click(function(){
		$("#addms").hide();
		$("#dems").show();
		$("#mspeo").show();
	});
	$("#decs").click(function(){
		$("#decs").hide();
		$("#addcs").show();
		$("#cspeo").hide();
		formSelects.value('select2', []); 
	});
	$("#dems").click(function(){
		$("#dems").hide();
		$("#mspeo").hide();
		$("#addms").show();
		formSelects.value('select3', []); 
	});
	
		//多选
layui.formSelects.config('select1', {
		keyName: 'userName', //自定义返回数据中name的key, 默认 name
		keyVal: 'userId',
		direction: 'down',
		beforeSuccess: function(id, url, searchVal, result) {
			console.log(result)
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
		url: layui.setter.project + '/user/getuserselect'
	});
layui.formSelects.config('select2', {
		keyName: 'userName', //自定义返回数据中name的key, 默认 name
		keyVal: 'userId',
		direction: 'down',
		beforeSuccess: function(id, url, searchVal, result) {
			console.log(result)
			return result;
		},
		success: function(id, url, searchVal, result) {
			form.render('select'); //使用远程方式的success回调
			console.log(id); //组件ID xm-select
			console.log(url); //URL
			console.log(searchVal); //搜索的value
			console.log(result); //返回的结果
		}
	}).data('select2', 'server', {
		url: layui.setter.project + '/user/getuserselect'
	});
	layui.formSelects.config('select3', {
		keyName: 'userName', //自定义返回数据中name的key, 默认 name
		keyVal: 'userId',
		direction: 'down',
		beforeSuccess: function(id, url, searchVal, result) {
			console.log(result)
			return result;
		},
		success: function(id, url, searchVal, result) {
			form.render('select'); //使用远程方式的success回调
			console.log(id); //组件ID xm-select
			console.log(url); //URL
			console.log(searchVal); //搜索的value
			console.log(result); //返回的结果
		}
	}).data('select3', 'server', {
		url: layui.setter.project + '/user/getuserselect'
	});
	editor.customConfig.menus = [
		'head', // 标题
		'bold', // 粗体
		'fontSize', // 字号
		'fontName', // 字体
		'italic', // 斜体
		'underline', // 下划线
		'strikeThrough', // 删除线
		'foreColor', // 文字颜色
		'backColor', // 背景颜色
		'link', // 插入链接
		'list', // 列表
		'justify', // 对齐方式
		'quote', // 引用
		'emoticon', // 表情
		'image', // 插入图片
		'table', // 表格
		'code', // 插入代码
		'undo', // 撤销
		'redo' // 重复
	];
	//http://192.168.10.106:8080/file/show?filename=3c2a22a5-343b-437d-aed7-669e99ef792c.jpg
 //关闭粘贴样式的过滤
      editor.customConfig.pasteFilterStyle = false;
      //忽略粘贴内容中的图片
      editor.customConfig.pasteIgnoreImg = true;
     // 使用 base64 保存图片
      editor.customConfig.uploadImgShowBase64 = true;
      // 隐藏“网络图片”tab
      editor.customConfig.showLinkImg = false;
      //改变z-index
      editor.customConfig.zIndex = 10;
     // 最大300M
     	 editor.customConfig.uploadImgMaxSize = 300 * 1024 * 1024;
	
	editor.create();
	editor.txt.html('<p><img src="data:image/png;base64,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" style="max-width:100%;"><br></p>')
	//发送邮件
	var flag2 = false;
	form.on('submit(add)', function(obj) {
		if(!flag2){
		
		
		var suser = formSelects.value('select1', 'valStr');
		var cuser = formSelects.value('select2', 'valStr');
		var muser = formSelects.value('select3', 'valStr');
		var zhuti = $("#zhuti").val();
		var publicNotice = editor.txt.html();
		var state = "2";
		console.log(userId+"|"+suser+"|"+cuser+"|"+muser+"|"+zhuti+"|"+publicNotice+"|");
		if(suser==null||suser==""){
			layer.msg("收件人不能为空！");
			return;
		}
		if(zhuti==""||zhuti==null){
			layer.msg("主题不能为空！");
			return;
		}
		
		$.ajax({
			url: layui.setter.project + "/mail/sendmail",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"senderUser":userId,
				"mailTheme":zhuti,
				"mailContent": publicNotice,
				"suser": suser,
				"cuser": cuser,
				"muser": muser,
				"state":state
				
			},
			//contentType:"application/json;charset=utf-8",
			dataType: "json",
			success: function(data) {
				console.log(data);
				if(data.result==200){
					layer.alert(data.message, function() {
						layer.close();
						location.reload();
					});
				}else{
					layer.alert(data.message, function() {
						layer.close();
					});
				}
			}
		})
		}
	});
	
	//存草稿
	
	function tijiao(){
		
		var suser = formSelects.value('select1', 'valStr');
		var cuser = formSelects.value('select2', 'valStr');
		var muser = formSelects.value('select3', 'valStr');
		var zhuti = $("#zhuti").val();
		var publicNotice = editor.txt.html();
		var state = "1";
		$.ajax({
			url: layui.setter.project + "/mail/sendmail",
			type: "post",
			xhrFields: {
				withCredentials: true
			},
			data: {
				"senderUser":userId,
				"mailTheme":zhuti,
				"mailContent": publicNotice,
				"suser": suser,
				"cuser": cuser,
				"muser": muser,
				"state":state
				
			},
			//contentType:"application/json;charset=utf-8",
			dataType: "json",
			success: function(data) {
				console.log(data);
				if(data.result==200){
					layer.alert(data.message, function() {
						layer.close();
						location.reload();
					});
				}else{
					layer.alert(data.message, function() {
						layer.close();
					});
				}
			}
		})
	}
	var flag = false;
	form.on('submit(cuncg)', function(obj) {
		if(!flag){
			flag = true;
			tijiao();
		}
	})
	

	e("sendmail", {})
})