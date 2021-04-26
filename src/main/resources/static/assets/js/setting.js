$(window).on('load', function(){
  $(".job_main_right>div").eq(0).show().siblings().hide();
    //默认禁用按钮
    $("#confirmChangePassword").attr("disabled", true);
    //如果没有绑定邮箱，则设置为提示文字
    var boundemail = $("#boundemail").val();
    if(boundemail==""||boundemail==null){
        $("#boundemail").val("您还未绑定邮箱哦!");
    }
});

$(document).ready(function () {
    //切换标签页
    $(".user_navigation>li").click(function () {
        $(this).addClass("is-active").siblings().removeClass("is-active");
        var index = $(this).index();
        // console.log(index);
        $(".job_main_right>div").eq(index).show().siblings().hide();
    })

})

//确认密码是否存在与数据库中
$("#password").blur(function () {
    var password = $("#password").val();
    var obj = {
        "password": password
    }
    $.ajax({
        url: "/setting/isPasswordExist",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            if (data.status == 200) {
                //启用按钮
                $("#confirmChangePassword").attr("disabled", false);
            } else {
                //禁用按钮
                $("#confirmChangePassword").attr("disabled", true);
                //弹出提示
                GrowlNotification.notify({
                    title: 'ERROR!',
                    description: '密码输入错误',
                    type: 'error',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
            }
        }
    })
})
//修改密码系列操作
$("#confirmChangePassword").click(function(){
        var newPassword = $("#newPassword").val();
        var newPassword2 = $("#newPassword2").val();
        //密码不为空但是不相等
        if (newPassword != newPassword2&&newPassword!=""&&newPassword2!="") {
            //弹出提示
            GrowlNotification.notify({
                title: '错误!',
                description: '两次密码输入不一致',
                type: 'error',
                position: 'bottom-right',
                closeTimeout: 3000
            });
        }
        //两个密码输入有一个为空
        else if(newPassword==""||newPassword2==""){
            GrowlNotification.notify({
                title: '提醒!',
                description: '新密码输入不允许为空',
                type: 'warning',
                position: 'bottom-right',
                closeTimeout: 3000
            });
        }
        //两个密码不为空且相等，发送修改密码请求
        if(newPassword == newPassword2&&newPassword!=""&&newPassword2!="") {
            var obj = {
                "newPassword": newPassword
            }
            //发送重置密码请求
            $.ajax({
                url: "/setting/restPassword",
                type: "post",
                data: JSON.stringify(obj),
                contentType: "application/json",
                success: function (data) {
                    if (data.status == 200) {
                        //弹出提示
                        GrowlNotification.notify({
                            title: '成功!',
                            description: '密码修改成功',
                            type: 'success',
                            position: 'bottom-right',
                            closeTimeout: 3000
                        });
                        //将三个输入框的值清空
                        $("#password").val("");
                        $("#newPassword").val("");
                        $("#newPassword2").val("");
                        //重新禁用按钮
                        $("#confirmChangePassword").attr("disabled", true);
                    } else {
                        //弹出失败提示
                        GrowlNotification.notify({
                            title: '错误!',
                            description: '密码修改失败',
                            type: 'error',
                            position: 'bottom-right',
                            closeTimeout: 3000
                        });

                    }
                    console.log(data);
                }
            })
        }
});
// 需在引入 <script src="jquery.cxselect.js"></script> 之后，调用之前设置
$.cxSelect.defaults.url = '/assets/js/cityData.min.js';
$.cxSelect.defaults.emptyStyle = 'none';
$('#element_id').cxSelect({
    url: '/assets/js/cityData.min.js',               // 提示：如果服务器不支持 .json 类型文件，请将文件改为 .js 文件
    selects: ['province', 'city', 'area'],  // selects 为数组形式，请注意顺序
    emptyStyle: 'none'
});

//绑定邮箱按钮
$("#bindEmailConfirm").click(function(){
    var email = $("#email").val();
    var obj = {
        "email": email
    }
    //验证邮箱
    var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    //如果输入的邮箱为空
    if(email==""||email==null){
        GrowlNotification.notify({
            title: '提醒!',
            description: '请输入邮箱',
            type: 'warning',
            position: 'bottom-right',
            closeTimeout: 3000
        });
    }
    //邮箱格式不正确
    else if(!reg.test(email)){
        GrowlNotification.notify({
            title: '提醒!',
            description: '邮箱输入格式不正确',
            type: 'warning',
            position: 'bottom-right',
            closeTimeout: 3000
        });
    }
    //格式也正确，也不为空
    else{
        $.ajax({
            url: "/setting/setEmail",
            type: "post",
            data: JSON.stringify(obj),
            contentType: "application/json",
            success: function (data) {
                if (data.status == 200) {
                    //弹出提示
                    GrowlNotification.notify({
                        title: '成功!',
                        description: '邮箱设置成功',
                        type: 'success',
                        position: 'bottom-right',
                        closeTimeout: 3000
                    });
                    //清空值
                    $("#email").val("");
                    //设置显示内容
                    $("#boundemail").val(email);
                } else {
                    //弹出错误提示
                    GrowlNotification.notify({
                        title: '错误!',
                        description: '邮箱设置失败',
                        type: 'error',
                        position: 'bottom-right',
                        closeTimeout: 3000
                    });
                }
            }
        })
    }
})





































