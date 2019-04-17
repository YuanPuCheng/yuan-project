layui.extend({
	setter: "../../../../static/layui/config"

}).define(["setter", "jquery"], function(e) {
	var $ = layui.jquery;

layui.use(['form','laypage','layer','table'], function(){
		  var table = layui.table,
		  	  laypage = layui.laypage,
		  	  layer = layui.layer,
		  	  form = layui.form,
		  	  $ = layui.jquery;
		  		
		  var tabins = table.render({
		    elem: '#test',
		    url:layui.setter.project+'/user/getuser',
		    toolbar:"#barDemo",
		    method: 'post',
		    id: 'testReload',
		    cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
		    cols: [[
		      {type:'checkbox'},
		      {field:'userCode',  title: '用户工号', sort: true},
		      {field:'userName',  title: '用户名称'},
		      {field:'sex',  title: '性别'},
		      {field:'email',  title: '邮箱'},
		      {field:'images',  title: '头像'},
		      {field:'phone',  title: '电话'},
		      {field:'ts',  title: '登陆时间', sort: true},
		      {field:'ip',  title: 'IP'},
		      {field:'name',  title: '部门',templet:function(d){
		      	var a = eval(d.departments);
		      	var name="";
		      	if(a!=null){
		      		$.each(a,function(index,value){
	     				name = value.departmentName+"、"+name;
					});
		      	}else{
		      		name=""
		      	}
		      	
		      	//var ddd=a.departments[0].departmentCode
		      	name = name.substring(0,name.length-1);
		      	return name ;
		      }},
		      {field:'projects',  title: '项目名称',templet:function(d){
			      	var a = d.project;
			      	var name1="";
			      	if(a!=null){
			      		name1 = d.project.projectName;
			      	}
			      	
			      	return name1 ;
			      }}
		      
		    ]],
		  });
		  //搜索(方法重载)
 			
		  $('#Search').on('click', function(){
		  	var name2 =$('#SearchDepartments').val();
		  	var name1= names(name2);
		  	var sex1 = names( $('#SearchSex').val());
		  	var project1 = names( $('#SearchProjects').val());
 			function names(obj){
 				var n = "";
 				if(obj=="不限"){
 					return n;
 				}
 				return obj;
 			}
//				console.log(name1+sex1);
		      table.reload('testReload', {
		      	url:layui.setter.project+'/user/getuser',
		        where: {
		           	userCode: $('#SearchUserCode').val(),
		            userName: $('#SearchUserName').val(),
		            tempVar3: name1,
		            sex: sex1,
		            tempInt1: project1
		        }
		      });
		  });
		  
		  
		  //分页
		  var counts=0;
		  
		      $.ajax({
		      	type:"post",
		      	url:layui.setter.project+"/user/getuser",
		      	dataType:'json',
		      	success:function (data){
		      		counts=data.data.length;//数据总条数
		      		laypage.render({
					    elem: 'demo0',
					    count: counts,//数据总数
					    layout: [ 'count','prev', 'page', 'next', 'refresh', 'skip'],
					    jump: function(obj){
					    	
					    }
			    	});
		      	},
		      	error: function (){
		      		alert('数据加载失败！');
		      	}
		      });
		  
		  //头工具栏事件
		  table.on('toolbar(test)', function(obj){
		    var checkStatus = table.checkStatus(obj.config.id);
		    switch(obj.event){
		      case 'edit':
		      	//用户编辑
		    	  
		    	var widthMax=450,
	      			heightMax=400;
					if($(window).width()<768){
						widthMax=280;
						heightMax=260
					}
		        var data = checkStatus.data;
		        if(data.length==1){
		        	layer.open({
		                type: 2,
		                //skin: 'layui-layer-demo', //样式类名
		                title: '用户编辑',
		                maxmin: true,
		                btn: ['确定', '取消'],
		                yes: function(index, layero){
		                    var iframeWindow = window['layui-layer-iframe'+ index]
		                    ,submit = layero.find('iframe').contents().find("#LAY-user-role-submit");
		                    //监听提交
		                    iframeWindow.layui.form.on('submit(LAY-user-role-submit)', function(data){
		                      var field = data.field; //获取提交的字段
		                      alert(field);
		                      //提交 Ajax 成功后，静态更新表格中的数据
		                      //$.ajax({});              
		                      table.reload('LAY-user-back-role');
		                      layer.close(index); //关闭弹层
		                    });  
		                    
		                    submit.trigger('click');
		                },
		                success: function (layero, index){
		                	var iframe = window['layui-layer-iframe' + index];
		                    // 向子页面的全局函数child传参
		                	console.log(data);
		                    iframe.child("adasd");
		                },
		                closeBtn: 1, //不显示关闭按钮
		                anim: 2,
		                area: [widthMax+"px", heightMax+"px"],
		                shadeClose: true, //开启遮罩关闭
		                content: 'reviseManagement.html?data='+data,
		            });
		        	
		        }else{
		        	layer.alert("请一次选中一个！");
		        }
		      break;
		      case 'add':
		      	//用户添加
				var widthMax=450,
        			heightMax=400;
				if($(window).width()<768){
					widthMax=280;
					heightMax=260
				}
	        	
		    	  layer.open({
		                type: 2,
		                //skin: 'layui-layer-demo', //样式类名
		                title: '用户编辑',
		                closeBtn: 1, //不显示关闭按钮
		                anim: 2,
		                fixed: false,
		                maxmin: true,
		                yes: function (index,layero){
		                	var iframeWindow = window['layui-layer-iframe'+ index];
		                    
		                      //提交 Ajax 成功后，静态更新表格中的数据
		                   /*   $.ajax({
		                    	  url:'',
		                    	  type:'post',
		                    	  data:{
		                    		  
		                    	  },
		                    	  dataType:"json",
		                    	  xhrFields: {
		          					withCredentials: true
		          				  },
		                    	  success: function(data){
		                    		  if(data.result == 200) {
		          						layer.msg(data.message);
		          						
			          						form.render();
			          					} else {
			          						layer.msg(data.message);
			          						form.render();
			          					}
		                    	  }
		                      });              
		                      table.reload('LAY-user-back-role');
		                      layer.close(index); //关闭弹层
		                    });  
		                    */
		                    submit.trigger('click');

		                },
		                btn: ['确定', '取消'],
		                area: [widthMax+'px', heightMax+'px'],
		                shadeClose: true, //开启遮罩关闭
		                content: 'addMangement.html?data='+data
		            });
		      break;
		      case 'del':
		      	//用户删除
//		        var checkStatus = table.checkStatus('test');
		        console.log(checkStatus.data.length);
				if(checkStatus.data.length==0){
					parent.layer.msg('请先选择要删除的数据行！', {icon: 2});
					return ;
				}
				var ids = "";
				for(var i=0;i<checkStatus.data.length;i++){
					ids += checkStatus.data[i].userCode+",";
				}
				ids=ids.substring(0,ids.length-1);
				var usercodes=ids;
				parent.layer.msg('删除中...', {icon: 16,shade: 0.3,time:5000});
				 $.ajax({
				 	
        	  	type:"post",
        	  	url:layui.setter.project+"/user/delete",
        	  	xhrFields: {
					withCredentials: true
				},
				data: {
					"usercodes": ids
				},
        	  	dataType:"json",
        	  	success:function(data){
    	  		 if(data.result==200){
			      layer.msg(data.message);
    	  		 	table.reload('testReload', {
			      	url:layui.setter.project+'/user/getuser'
			      });
    	  		 }else{
    	  		 	layer.alert(result.message);
    	  		 }
        	  	},
        	  	error:function(jqx){
        	  		alert("发生错误："+jqx);
        	  	}
        	  });
			 
		      break;
		    };
		  });
		  
		 //部门下拉框加载
		 $.get(layui.setter.project+'/department/getdepartment', {}, function (data) {
                var $html = "<option>不限</option>";
                if(data.data != null){
                    $.each(data.data, function (index, item) {
                        if (item.proType){
                            $html += "<option class='generate'>不限</option>";
                        }else{
                            $html += "<option value='" + item.departmentId + "'>" + item.departmentName + "</option>";
                        }
                    });
                 $("select[name='departments']").append($html);
                 //反选
                 $("select[name='departments']").val($("#SearchDepartments").val());
                 //append后必须从新渲染
                 form.render('select');
             }
		});
		 
		//项目下拉框加载
		 $.get(layui.setter.project+'/project/getproject', {}, function (data) {
                var $html = "<option>不限</option>";
                if(data.data != null){
                    $.each(data.data, function (index, item) {
                        if (item.proType){
                            $html += "<option class='generate'>不限</option>";
                        }else{
                            $html += "<option value='" + item.projectId + "'>" + item.projectName + "</option>";
                        }
                    });
                 $("select[name='projects']").append($html);
                 //反选
                 $("select[name='projects']").val($("#SearchProjects").val());
                 //append后必须从新渲染
                 form.render('select');
             }
		});
})

e("userMangement", {})
});