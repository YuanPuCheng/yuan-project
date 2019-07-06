layui.extend({
	setter: "../../static/layui/config",
	formSelects: "../../static/layui/formSelects-v4"

}).define(["setter", "jquery", 'form', 'laypage', 'table','formSelects'], function(e) {
	var $ = layui.jquery;
	var layer = layui.layer;
	var form = layui.form;
	var E = window.wangEditor;
	var editor = new E('#div1');
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
	
	var formSelects = layui.formSelects;
    var sessionData = sessionStorage.getItem('user');//取出数据
    var user = JSON.parse(sessionData);
    var userId = user.userId;
	var mailId = $("#mailId").val();
	$("#name").val(user.userName);//发件人
	let arr;//收件人
	let arr1;//抄送人
	let arr2//密送人
	if($("#state").val()==2){//
		console.log($("#cuserIds").val()+"............")
		if($("#cuserIds").val()!=""){
			$("#cspeo").show();
			arr1 = JSON.parse('[' + $("#cuserIds").val() +']');
		}
		if($("#muserIds").val()!=""){
			$("#mspeo").show();
			arr2 = JSON.parse('[' + $("#muserIds").val() +']');
		}
		arr = JSON.parse('[' + $("#suserIds").val() +']');
		
		
		var contents = $("#contents").val();
		editor.txt.html(contents);
	}
	
	
	
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
			return result;
		},
		success: function(id, url, searchVal, result) {
			formSelects.value('select1',arr, true);
                form.render('select');//使用远程方式的success回调
			form.render('select'); //使用远程方式的success回调
		}
	}).data('select1', 'server', {
		url: layui.setter.project + '/user/getuserselect'
	});
layui.formSelects.config('select2', {
		keyName: 'userName', //自定义返回数据中name的key, 默认 name
		keyVal: 'userId',
		direction: 'down',
		beforeSuccess: function(id, url, searchVal, result) {
			return result;
		},
		success: function(id, url, searchVal, result) {
			formSelects.value('select2',arr1, true);
			form.render('select'); //使用远程方式的success回调
		}
	}).data('select2', 'server', {
		url: layui.setter.project + '/user/getuserselect'
	});
	layui.formSelects.config('select3', {
		keyName: 'userName', //自定义返回数据中name的key, 默认 name
		keyVal: 'userId',
		direction: 'down',
		beforeSuccess: function(id, url, searchVal, result) {
			return result;
		},
		success: function(id, url, searchVal, result) {
			formSelects.value('select3',arr2, true);
			form.render('select'); //使用远程方式的success回调
		}
	}).data('select3', 'server', {
		url: layui.setter.project + '/user/getuserselect'
	});
	
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
		console.log(mailId+"|"+userId+"|"+suser+"|"+cuser+"|"+muser+"|"+zhuti+"|"+publicNotice+"|");
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
				"mailId":mailId,
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
                        parent.location.reload();
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
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
				"mailId":mailId,
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
                        parent.location.reload();
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
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
	

	e("deaftsmail", {})
})