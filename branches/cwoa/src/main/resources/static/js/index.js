





	$("#info").attr("lay-href",project+"/sys/userinfo");
	$("#pass").attr("lay-href",project+"/sys/updatepass");
	$("#logout").attr("href",project+"/sys/logoutuser");
	$("#task").attr("lay-href",project+"/sys/mytask");
    $("#iii").attr("lay-href",project+"/sys/syindex");
    $("#index1").attr("src",project+"/sys/syindex");
    //供子窗口调用
    function openbq(id) {
        //alert(id);
        document.getElementById(id).click();
    }
	//目录ajax  post请求
	$.ajax({
		type:"post",
		url:project+"/system/index",
		async:true,
		timeout:5000,
		dataType:"json",
		success:function(data){
			console.log(data);

			var list='';
			for(var i=0;i<data.length;i++){
				var lis=$('<li>');
				lis.attr('class','layui-nav-item');
				var als='<a href="javascript:;"><i class="layui-icon '+data[i].menuIcon+'"></i><cite>'+data[i].menuName+'</cite><span class="layui-nav-more"></span></a>';
				var dds="";
				if(data[i].menus!=null){
                    for(var j=0;j<data[i].menus.length;j++){
                        if(data[i].menus[j].menus.length!=0){
                            var menusName = "";
                            for(var k=0;k<data[i].menus[j].menus.length;k++){
                                menusName+=data[i].menus[j].menus[k].menuName+",";
                            }
                            console.log(menusName);
                            dds+='<dd><a id="'+data[i].menus[j].menuCode+'" lay-href="'+project+data[i].menus[j].menuUrl+'?data='+menusName.substring(0,menusName.length-1)+'\">'+data[i].menus[j].menuName+'</a></dd>';
                        }else{
                            dds+='<dd><a id="'+data[i].menus[j].menuCode+'" lay-href="'+project+data[i].menus[j].menuUrl+'?data=\">'+data[i].menus[j].menuName+'</a></dd>';
                        }
                        console.log(data[i].menus[j].menuUrl);
                    }

                }
                var dls="<dl class='layui-nav-child'>"+dds+"</dl>";
                lis.html(als+dls);
                $('#lay_animate3').append(lis);

			}
            function size_changed_zero(){
                $('.layui-side-menu').width(0);
                $('.layui-header .layui-layout-left,#LAY_app_tabs,.layui-body,.layadmin-body-shade').css({'left':'0'})
                $('.layui-side-menu .layui-side-scroll').hide();
            }
            function size_changed_before(){
                $('.layui-side-menu,.layui-logo').width("175px");
                $('.layui-header .layui-layout-left,#LAY_app_tabs,.layui-body,.layadmin-body-shade').css({'left':'175px'})
                $('.layui-side-menu .layui-side-scroll').show();
            }
            $(window).resize(function(){
                if($(window).width()<=992){
                    size_changed_zero();
                }else{
                    size_changed_before();
                    $('.layadmin-body-shade').addClass('layui_active');
                    $('#LAY_app_tabs,#lay_animate1,.layui-layout-right').css('right','0');
                }
            })
            $('#refresh').on('click',function(){
                console.log($('#LAY_app_body .layui-show iframe').attr("src"));
            	$('#LAY_app_body .layui-show iframe').attr("src",$('#LAY_app_body .layui-show iframe').attr("src"));
            })
            //荧幕开关
            function shade_on(){
                if($('.layadmin-body-shade').hasClass('layui_active')){
                    $('.layadmin-body-shade').removeClass('layui_active');
                }else{
                    $('.layadmin-body-shade').addClass('layui_active');
                }
            }
            $('.layadmin-body-shade').on("click",function(){
                if($('.layui-side-menu').width()){
                    size_changed_zero();
                    $('#LAY_app_tabs,#lay_animate1,.layui-layout-right').css('right','0');
                }else{
                    size_changed_before();
                    $('#LAY_app_tabs,#lay_animate1,.layui-layout-right').css('right','-175px');
                }
                shade_on();
            })
            $('.layadmin-flexible').on("click",function(){
                if($(window).width()<=992){
                    if($('.layui-side-menu').width()){
                        size_changed_zero();
                        $('#LAY_app_tabs,#lay_animate1,.layui-layout-right').css('right','0');
                    }else{
                        size_changed_before();
                        $('#LAY_app_tabs,#lay_animate1,.layui-layout-right').css('right','-175px');
                    }
                    shade_on();
                }else{
                    if($('.layui-side-menu').width()){
                        size_changed_zero();
                    }else{
                        size_changed_before();
                    }
                }
            })
            $('#lay_animate3').on("click",".layui-nav-child dd",function(){
                setTimeout(function(){
                    if($(window).width()<=992){
                        size_changed_zero();
                        shade_on();
                        $('#LAY_app_tabs,#lay_animate1,.layui-layout-right').css('right','0');
                    }else{
                        size_changed_before();
                    }
                },200);
            })
            //菜单点击事项
            $('#_user').click(function(){
                if($('#layui-na').hasClass('layui-show')){
                    $('#layui-na').removeClass('layui-show')
                }else{
                    $('#layui-na').addClass('layui-show')
                }
                if($('#_user-icon').hasClass('layui-nav-mored')){
                    $('#_user-icon').removeClass('layui-nav-mored')
                }else{
                    $('#_user-icon').addClass('layui-nav-mored')
                }
            })
            //顶部移动条
            $('#lay_animate1').on("mouseover",".layui-nav-item",function(){
                var i=$('#lay_animate1 .layui-nav-item').index(this);
                $('#lay_animate1 .layui-nav-bar').css({
                    'width':$('#lay_animate1 .layui-nav-item').eq(i).width(),
                    'opacity':1,
                    'top':0,
                    'left':$('#lay_animate1 .layui-nav-item').eq(i).width()*i+20,
                    'height':'2px',
                    'background-color':'#777'
                })
            });
            $('#lay_animate1').on("mouseout",".layui-nav-item",function(){
                $('#lay_animate1 .layui-nav-bar').css({
                    'width':0,
                    'opacity':0,
                })
            });
            //右侧顶部导航
            $('#lay_animate2').on("mouseover",".layui-nav-item",function(){
                var i=$('#lay_animate2 .layui-nav-item').index(this);
                $('#lay_animate2 .layui-nav-bar').css({
                    'width':$('#lay_animate2 .layui-nav-item').eq(i).width(),
                    'opacity':1,
                    'top':0,
                    'left':$('#lay_animate2 .layui-nav-item').eq(i).offset().left-$('#lay_animate2').offset().left+20,
                    'height':'2px',
                    'background-color':'#777'
                })
            });
            $('#lay_animate2').on("mouseout",".layui-nav-item",function(){
                $('#lay_animate2 .layui-nav-bar').css({
                    'width':0,
                    'opacity':0,
                })
            });
            $('#lay_item').click(function(){
                $('#lay_menu').addClass('layui-show')
            })
            //侧边绿条位置
            $('#lay_animate3').on('mouseover','.layui-nav-item',function(){
                var i=$('#lay_animate3 .layui-nav-item').index(this);
                $('#lay_animate3 .layui-nav-bar').css({
                    'height':'56px',
                    'opacity':1,
                    'top':$('#lay_animate3 .layui-nav-item').eq(i).position().top+$("#lay_animate3").scrollTop(),
                    'left':0,
                    'width':'4px',
                })
            });
            $('#lay_animate3').on("mouseout",".layui-nav-item",function(){
                var i=$('#lay_animate3 .layui-nav-item').index(this);
                $('#lay_animate3 .layui-nav-bar').css({
                    'height':0,
                    'opacity':0,
                })
            });
            $('#lay_animate3').on("click",'.layui-nav-item a',function(){
                var i=$('.layui-nav-item a').index(this);
                if($('.layui-nav-item a').eq(i).parent().hasClass('layui-nav-itemed')){
                    $('.layui-nav-item a').eq(i).parent().removeClass('layui-nav-itemed');
                }else{
                    $('.layui-nav-item a').eq(i).parent().addClass('layui-nav-itemed');
                }
                $('.layui-nav-item a').eq(i).parent().siblings().removeClass('layui-nav-itemed');
            })
            //二级导航栏绿条
            $('#lay_animate3').on("click",".layui-nav-child dd",function (){
                var i=$('.layui-nav-child dd').index(this);
                $('.layui-nav-child dd').eq(i).addClass('layui-this').siblings().removeClass('layui-this');
            })
            //创建页面
            var arr=new Array();
            var maxWidth=$('.lay_app_li').eq(0).innerWidth();//用来保存li的总宽度
            $('a[lay-href]').bind('click',function(){
                var i=$('a[lay-href]').index(this);
                //把所有点击的路径放在一个空数组中
                if(arr.length==0){
                    arr.push($('.layadmin-tabsbody-item').eq(0).find('iframe').attr("src"));
                }else{
                    for(var j=0;j<arr.length;j++){
                        if(!(arr.indexOf($('.layadmin-tabsbody-item').eq(j).find('iframe').attr("src"))+1)){
                            arr.push($('.layadmin-tabsbody-item').eq(j).find('iframe').attr("src"));
                        }
                    }
                }
                //遍历所有的子窗口有没有这个路径窗口，没有就创建
                for(var j=0;j<arr.length;j++){
                    if(!((arr.indexOf($('a[lay-href]').eq(i).attr('lay-href')))+1)){
                        $('#LAY_app_body').append('<div class="layadmin-tabsbody-item layui-show"><iframe src="'+$('a[lay-href]').eq(i).attr('lay-href')+'" frameborder="0" class="layadmin-iframe"></iframe></div>')
                        $('#LAY_app_tabsheader').append('<li class="lay_app_li" lay-id="'+$('a[lay-href]').eq(i).attr('lay-href')+'" lay-attr="'+$('a[lay-href]').eq(i).attr('lay-href')+'" class="layui-this"><span>'+$('a[lay-href]').eq(i).html()+'</span><i class="layui-icon layui-unselect layui-tab-close">ဆ</i></li>')
                        arr.push($('a[lay-href]').eq(i).attr('lay-href'));
                        maxWidth=maxWidth+$('.lay_app_li').last().innerWidth();
                    }
                }
                //遍历所有的窗口，添加选中active样式
                for(var k=0;k<arr.length;k++){
                    if($('.layadmin-tabsbody-item').eq(k).find('iframe').attr("src")==$('a[lay-href]').eq(i).attr('lay-href')){
                        $('.layadmin-tabsbody-item').eq(k).addClass('layui-show').siblings().removeClass('layui-show');
                    }
                    if($('.layui-tab-title li').eq(k).attr('lay-id')==$('a[lay-href]').eq(i).attr('lay-href')){
                        $('.layui-tab-title li').eq(k).addClass('layui-this').siblings().removeClass('layui-this');
                    }
                }
                //窗口可视化移动函数
                view_scroll();
            });
            $('#LAY_app_tabsheader').on("click",".lay_app_li",function(){
                var i=$('.lay_app_li').index(this);
                $('.lay_app_li').eq(i).addClass('layui-this').siblings().removeClass('layui-this');
                for(var k=0;k<$('.lay_app_li').length;k++){
                    if($('.layadmin-tabsbody-item').eq(k).find('iframe').attr("src")==$('.lay_app_li').eq(i).attr('lay-id')){
                        $('.layadmin-tabsbody-item').eq(k).addClass('layui-show').siblings().removeClass('layui-show');
                    }
                    if($('.lay_app_li').eq(i).attr('lay-id')==$('#lay_animate3 a[lay-href]').eq(k).attr('lay-href')){
                        $('#lay_animate3 a[lay-href]').eq(k).parent().addClass('layui-this').siblings().removeClass('layui-this');
                    }
                }
                view_scroll();
            });
            //获取显示窗口的下标
            var tab_index;
            function closed_tab(){
                for(var i=0;i<$('.lay_app_li').length;i++){
                    if($('.lay_app_li').eq(i).hasClass('layui-this')){
                        tab_index=i;
                    }
                }
            }
            //页面关闭
            $('#LAY_app_tabsheader').on("click",'.lay_app_li .layui-tab-close',function(){
                var i=$('#LAY_app_tabsheader .layui-tab-close').index(this);
                if(i==tab_index){
                    $('#LAY_app_tabsheader .lay_app_li').eq(tab_index-1).addClass('layui-this');
                    $('.layadmin-tabsbody-item').addClass('layui-show')
                }
                arr.splice(i,1);
                $('.lay_app_li').eq(i).remove();
                $('#LAY_app_body .layadmin-tabsbody-item').eq(i).remove();

                closed_tab();
                view_scroll();
                if($('#LAY_app_tabsheader').position().left>0){
                    $('#LAY_app_tabsheader').css('left',0);
                }
                //累计偏移量清零
                left_ul=0;
            })

            //右边悬浮窗口关闭
            $('#closed').click(function(){
                if($('#closed dl').hasClass('layui-show')){
                    $('#closed dl').removeClass('layui-show');
                }else{
                    $('#closed dl').addClass('layui-show');
                }
                closed_tab();
            })
            //关闭功能

            //关闭当前
            $('#closed_index').on("click",function(){
                closed_tab();
                if(tab_index){//保证首页不会被删除
                    arr.splice(tab_index,1);
                    if(tab_index+1>=$('.lay_app_li').length){
                        $('.lay_app_li').eq(tab_index-1).addClass('layui-this').siblings().removeClass('layui-this');
                        $('#LAY_app_body .layadmin-tabsbody-item').eq(tab_index-1).addClass('layui-show').siblings().removeClass('layui-show');
                    }else{
                        $('.lay_app_li').eq(tab_index+1).addClass('layui-this').siblings().removeClass('layui-this');
                        $('#LAY_app_body .layadmin-tabsbody-item').eq(tab_index+1).addClass('layui-show').siblings().removeClass('layui-show');
                    }
                    $('.lay_app_li').eq(tab_index).remove();
                    $('#LAY_app_body .layadmin-tabsbody-item').eq(tab_index).remove();
                }
                view_scroll();
            })
            //关闭其他
            $('#closed_others').on("click",function(){
                closed_tab();
                var maxlength=$('.lay_app_li').length;
                for(var i=1;i<$('.lay_app_li').length;i++){
                    if($('.lay_app_li').eq(i).hasClass('layui-this')){
                        continue;
                    }else{
                        arr.splice(i,1);
                        $('.lay_app_li').eq(i).remove();
                        $('#LAY_app_body .layadmin-tabsbody-item').eq(i).remove();
                        i--;
                    }
                }
                view_scroll();
            })
            //关闭全部
            $('#closed_all').on("click",function(){
                arr.splice(1,$('.lay_app_li').length-1);
                $('.lay_app_li').eq(0).siblings().remove();
                $('#LAY_app_body .layadmin-tabsbody-item').eq(0).siblings().remove();
                $('.lay_app_li').eq(0).addClass('layui-this');
                $('#LAY_app_body .layadmin-tabsbody-item').eq(0).addClass("layui-show");
                view_scroll();
            })
            //窗口可视区域滑动
            //判断窗口li的长度是否超过ul可视窗口，超过就移动
            //父级ul需要偏移的left值
            var left_ul=0;
            //当前ul的偏移量
            var left_active=$('#LAY_app_tabsheader').position().left;
            //ul宽度
            var width_ul=$('#LAY_app_tabsheader').innerWidth();
            function view_scroll(){
                closed_tab();
                //li的总宽度
                var width_lis=0;
                for(var i=0;i<$('#LAY_app_tabsheader .lay_app_li').length;i++){
                    width_lis=width_lis+$('#LAY_app_tabsheader .lay_app_li').eq(i).innerWidth();
                }
                //当前显示的li相对可视窗口的左偏移量
                var left_active_li=$('#LAY_app_tabsheader .lay_app_li').eq(tab_index).offset().left-$('.layui-tab').offset().left;
                //当前显示的li相对可视窗口的右偏移量
                var right_active_li=($('.layui-tab').offset().left+$('.layui-tab').innerWidth())-($('#LAY_app_tabsheader .lay_app_li').eq(tab_index).offset().left+$('#LAY_app_tabsheader .lay_app_li').eq(tab_index).innerWidth())
//		console.log(right_active_li)
                if($('#LAY_app_tabsheader').position().left>30){
                    $('#LAY_app_tabsheader').css('left',0);
                    console.log("$('#LAY_app_tabsheader').position().left="+$('#LAY_app_tabsheader').position().left);
                }else{
                    if(left_active_li<0){
                        left_ul=left_ul-left_active_li;
                        $('#LAY_app_tabsheader').css('left',left_ul);
                    }
                    if(right_active_li<0){
                        left_ul=left_ul+right_active_li;
                        $('#LAY_app_tabsheader').css('left',left_ul);
                    }
                }


            }

            //点击偏移函数
            $('#leftPage').on("click",function(){
                //左点击偏移量
                left_active=left_active+width_ul;
                if(left_active>0){
                    $('#LAY_app_tabsheader').css('left',0);
                }else{
                    $('#LAY_app_tabsheader').css('left',left_active);
                }
                //累计偏移量清零
                left_ul=0;
            })

            $('#rightPage').on("click",function (){
                //li的总宽度
                var width_lis=0;
                for(var i=0;i<$('#LAY_app_tabsheader .lay_app_li').length;i++){
                    width_lis=width_lis+$('#LAY_app_tabsheader .lay_app_li').eq(i).innerWidth();
                }
                var left_active=$('#LAY_app_tabsheader').position().left;
                //右点击偏移量
                left_active=left_active-width_ul;
                if(width_ul<=width_lis){
                    if(-left_active+width_ul>=width_lis){
                        $('#LAY_app_tabsheader').css('left',width_ul-width_lis);
                    }else{
                        $('#LAY_app_tabsheader').css('left',left_active);
                    }
                }
                //累计偏移量清零
                left_ul=0;
            });

            
            $.ajax({
        	  	type:"post",
        	  	url:project+"/user/getuserinfo",
        	  	async:true,
        	  	dataType:"json",
        	  	success:function(data){
                    sessionStorage.setItem('user', JSON.stringify(data));//设置数据
                    console.log(data)
        	  		$('#UserName').html(data.userName);
        	  		var tst="";
        	  		var departmentsName="";
        	  		var departmentsId="";
        	  		var project ="";
        	  		var roleName ="";
                    var roleId ="";
        	  		if(data.department!=null){
                        departmentsName =data.department.departmentName;
                    }
        	  		if(data.project!=null){
                        project = data.project.projectName;
                    }
        	  		for(var i=0;i<data.roles.length;i++){
                        roleName=data.roles[i].roleName+"、";
                        roleId=data.roles[i].roleId+"、";
                        roleName=roleName.substring(0,roleName.length-1);
                        roleId=roleId.substring(0,roleId.length-1);
        	  		}

        	  		tst='<span id="userCode" name="'+  data.userCode  +'" title="工号"></span>'+
        	  		'<span id="userId" name="'+  data.userId  +'" title="ID"></span>'+
						'<span id="userName" name="'+  data.userName  +'" title="姓名"></span>'+
						'<span id="email" name="'+  data.email  +'" title="邮箱"></span>'+
						'<span id="sex" name="'+  data.sex  +'" title="性别"></span>'+
						'<span id="userDepartmentName" name="'+  departmentsName  +'" title="部门名称"></span>'+
						'<span id="userProject" name="'+ project   +'" title="项目"></span>'+
						'<span id="userrole" name="'+  roleName  +'" title="角色名称"></span>';
        				$('.news').append(tst);
        			
        	  	},
        	  	error:function(jqx){
        	  		console.log("获取用户信息失败");
        	  	}
        	  });


		},
		error:function(){
			alert('登录超时!，请重新登录！');
		}
	});

