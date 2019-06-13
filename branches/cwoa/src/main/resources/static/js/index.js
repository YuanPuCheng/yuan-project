

//供子窗口调用
function openbq(id) {
    //alert(id);
    document.getElementById(id).click();
}




$.ajax({
    type:"post",
    url:project+"/user/getuserinfo",
    async:true,
    dataType:"json",
    success:function(data){
       // $("#im").attr("src",project+"/file/show?filename="+data.images);

        sessionStorage.setItem('user', JSON.stringify(data));//设置数据
        console.log(data)
        $('#uname').html(data.userName);
        var tst="";
        var departmentsName="";
        var departmentsId="";
        var project1 ="";
        var roleName ="";
        var roleId ="";
        if(data.department!=null){
            departmentsName =data.department.departmentName;
        }
        if(data.project!=null){
            project1 = data.project.projectName;
        }
        if(data.roles!=null){
            for(var i=0;i<data.roles.length;i++){

                roleName+=data.roles[i].roleName+"、";
                roleId+=data.roles[i].roleId+"、";
            }
            roleName=roleName.substring(0,roleName.length-1);
            roleId=roleId.substring(0,roleId.length-1);
        }

        //$("#roleshow").html(roleName)
        tst='<span id="userCode" name="'+  data.userCode  +'" title="工号"></span>'+
            '<span id="userId" name="'+  data.userId  +'" title="ID"></span>'+
            '<span id="userName" name="'+  data.userName  +'" title="姓名"></span>'+
            '<span id="email" name="'+  data.email  +'" title="邮箱"></span>'+
            '<span id="sex" name="'+  data.sex  +'" title="性别"></span>'+
            '<span id="userDepartmentName" name="'+  departmentsName  +'" title="部门名称"></span>'+
            '<span id="userProject" name="'+ project1   +'" title="项目"></span>'+
            '<span id="userrole" name="'+  roleName  +'" title="角色名称"></span>';
        $('.news').append(tst);

    },
    error:function(jqx){
        console.log("获取用户信息失败");
    }
});

