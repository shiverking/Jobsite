$(window).on('load', function(){
    $(".job_main_right>div").eq(0).show().siblings().hide();
    //默认禁用按钮
    $("#confirmInsertUser").attr("disabled", true);
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

//确认用户名是否存在于数据库中
$("#username1").blur(function () {
    var username = $("#username1").val();
    var obj = {
        "username": username
    }
    $.ajax({
        url: "/superadmin/isUsernameExist",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            if (data.status == 200) {
                //禁用按钮
                $("#confirmInsertUser").attr("disabled", true);
                //弹出提示
                GrowlNotification.notify({
                    title: 'ERROR!',
                    description: '用户名已被占用',
                    type: 'error',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
            } else {
                //启用按钮
                $("#confirmInsertUser").attr("disabled", false);
            }
        }
    })
})

//确认手机号是否存在于数据库中
$("#telephone").blur(function () {
    var telephone = $("#telephone").val();
    var obj = {
        "telephone": telephone
    }
    $.ajax({
        url: "/superadmin/isTelephoneExist",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            if (data.status == 200) {
                //禁用按钮
                $("#confirmInsertUser").attr("disabled", true);
                //弹出提示
                GrowlNotification.notify({
                    title: 'ERROR!',
                    description: '手机号已被占用',
                    type: 'error',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
            } else {
                //启用按钮
                $("#confirmInsertUser").attr("disabled", false);
            }
        }
    })
})

//添加管理员用户操作
$("#confirmInsertUser").click(function () {
    var username1 = $("#username1").val();
    var telephone = $("#telephone").val();
    var password = $("#password").val();
    var repeatPassword =$("#repeatPassword").val();
    //密码不为空但是不相等
    if (password != repeatPassword&&password!=""&&repeatPassword!="") {
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
    else if(password==""||repeatPassword==""){
        GrowlNotification.notify({
            title: '提醒!',
            description: '密码输入不允许为空',
            type: 'warning',
            position: 'bottom-right',
            closeTimeout: 3000
        });
    }
    //两个密码不为空且相等，发送添加管理员用户请求
    if(password == repeatPassword&&password!=""&&repeatPassword!="") {
        var obj = {
            "username":username1,
            "telephone":telephone,
            "password": password
        }
        //发送添加用户请求
        $.ajax({
            url: "/superadmin/insertUser",
            type: "post",
            data: JSON.stringify(obj),
            contentType: "application/json",
            success: function (data) {
                if (data.status == 200) {
                    //弹出提示
                    GrowlNotification.notify({
                        title: '成功!',
                        description: '管理员添加成功',
                        type: 'success',
                        position: 'bottom-right',
                        closeTimeout: 3000
                    });
                    //将四个输入框的值清空
                    $("#username1").val("");
                    $("#telephone").val("");
                    $("#password").val("");
                    $("#repeatPassword").val("");
                    //重新禁用按钮
                    $("#confirmInsertUser").attr("disabled", true);
                } else {
                    //弹出失败提示
                    GrowlNotification.notify({
                        title: '错误!',
                        description: '管理员添加失败',
                        type: 'error',
                        position: 'bottom-right',
                        closeTimeout: 3000
                    });

                }
                console.log(data);
            }
        })
    }
})


//确认用户名是否存在于数据库中
$("#username2").blur(function () {
    var username = $("#username2").val();
    var obj = {
        "username": username
    }
    $.ajax({
        url: "/superadmin/isUsernameExist",
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
                    description: '用户名不存在',
                    type: 'error',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
            }
        }
    })
})

//修改密码操作
$("#confirmChangePassword").click(function(){
    var newPassword = $("#newPassword").val();
    var newPassword2 = $("#newPassword2").val();
    var username2 = $("#username2").val();
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
            "username":username2,
            "newPassword": newPassword
        }
        //发送重置密码请求
        $.ajax({
            url: "/superadmin/resetPassword",
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
                    $("#username2").val("");
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


//确认用户名是否存在于数据库中
$("#username3").blur(function () {
    var username = $("#username3").val();
    var obj = {
        "username": username
    }
    $.ajax({
        url: "/superadmin/isUsernameExist",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            if (data.status == 200) {
                //启用按钮
                $("#confirmDeleteUser").attr("disabled", false);
            } else {
                //禁用按钮
                $("#confirmDeleteUser").attr("disabled", true);
                //弹出提示
                GrowlNotification.notify({
                    title: 'ERROR!',
                    description: '用户名不存在',
                    type: 'error',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
            }
        }
    })
})

//删除管理员操作
$("#confirmDeleteUser").click(function(){
    var username3 = $("#username3").val();
    //用户名为空
    if(username3==""){
        GrowlNotification.notify({
            title: '提醒!',
            description: '用户名输入不允许为空',
            type: 'warning',
            position: 'bottom-right',
            closeTimeout: 3000
        });
    }
    //用户名不为空
    if(username3!="") {
        var obj = {
            "username":username3
        }
        //发送重置密码请求
        $.ajax({
            url: "/superadmin/deleteUser",
            type: "post",
            data: JSON.stringify(obj),
            contentType: "application/json",
            success: function (data) {
                if (data.status == 200) {
                    //弹出提示
                    GrowlNotification.notify({
                        title: '成功!',
                        description: '管理员删除成功',
                        type: 'success',
                        position: 'bottom-right',
                        closeTimeout: 3000
                    });
                    //将三个输入框的值清空
                    $("#username3").val("");
                    //重新禁用按钮
                    $("#confirmDeleteUser").attr("disabled", true);
                } else {
                    //弹出失败提示
                    GrowlNotification.notify({
                        title: '错误!',
                        description: '用户删除失败',
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