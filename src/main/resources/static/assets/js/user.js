/**
 * 用户名密码与手机验证码登录转换
 */
$(document).ready(function () {
    $("#gotoPassword").click(function () {
        $("#loginByPhone").hide()
        $("#loginByPassword").show()
    })
    $("#gotoPhone").click(function () {
        $("#loginByPhone").show()
        $("#loginByPassword").hide()
    })
})

$(document).ready(function () {
    $("#submit").click(function () {
        var register_code = $("#verifycode").val();
        var register_telephone = $("#telephone").val();
        if (register_code == "") {
            $("#errormsg").val("请输入验证码")
            $("#errormsg").show()
        } else if (register_telephone == "") {
            $("#errormsg").val("请输入手机号")
            $("#errormsg").show()
        } else {
            var obj = {
                "telephone": register_telephone,
                "mobileCode": register_code
            }
            $.ajax({
                url: "/register/verify",
                type: "post",
                data: JSON.stringify(obj),
                contentType: "application/json",
                success: function (data) {
                    if (data.status == 200) {
                        register()
                    } else {
                        $("#errormsg").val(data.msg)
                        $("#errormsg").show()
                    }
                }

            })
        }
    })
})


/**
 * 注册验证提示信息
 */
function register() {
    var register_name = $("#username").val();
    var register_email = $("#email").val();
    var register_password = $("#password").val();
    var register_repassword = $("#repassword").val();
    var register_telephone = $("#telephone").val();
    var identity = $('input:radio[name="identity"]:checked').val()
    var obj = {
        "username": register_name,
        "email": register_email,
        "password": register_password,
        "telephone": register_telephone,
        "identity": identity
    }
    if (register_name == "") {
        $("#errormsg").val("请输入用户名")
        $("#errormsg").show()
    } else if (register_password == "") {
        $("#errormsg").val("请输入密码")
        $("#errormsg").show()
    } else if (register_telephone == "") {
        $("#errormsg").val("请输入手机号")
        $("#errormsg").show()
    } else if (register_repassword == "") {
        $("#errormsg").val("两次密码不一致")
        $("#errormsg").show()
    } else {
        $.ajax({
            url: "/add",
            type: "post",
            data: JSON.stringify(obj),
            contentType: "application/json",
            success: function (data) {
                if (data.status == 200) {
                    window.location.href = "/login"
                } else {
                    $("#errormsg").val(data.msg)
                    $("#errormsg").show()
                }
                console.log(data.errors)
                return data;
            }
        })
    }
}

/**
 * 判断密码与重复密码是否一样
 */
$(document).ready(function () {
    $("#repassword").blur(function () {
        var password = $("#password").val();
        var repassword = $("#repassword").val();
        if (password != repassword) {
            $("#submit").attr("disabled", true);
            $("#errormsg").val("两次密码不一致");
            $("#errormsg").show()
        } else {

            $("#submit").attr("disabled", false);
        }
    })
    $("#password").blur(function () {
        var password = $("#password").val();
        var repassword = $("#repassword").val();
        if (password != repassword && repassword != "") {
            $("#submit").attr("disabled", true);
            $("#errormsg").val("两次密码不一致");
            $("#errormsg").show()
        } else {

            $("#submit").attr("disabled", false);
        }
    })
})
/**
 *获取验证码
 */
$(document).ready(function () {
    $("#mobileCode").click(function () {
        var register_telephone1 = $("#telephone").val();
        if (register_telephone1 == "") {
            $("#errormsg").val("请输入手机号")
            $("#errormsg").show()
        } else {
            var obj1 = {
                "telephone": register_telephone1
            }
            $.ajax({
                url: "/getVerifyCode",
                type: "post",
                data: JSON.stringify(obj1),
                contentType: "application/json",
                success: function (data) {
                    if (data.status == 200) {
                        countdown()
                    } else {
                        $("#errormsg").val(data.msg)
                        $("#errormsg").show()
                        console.log("failed")
                    }
                }
            })
        }
    })
})

var time = 60

function countdown() {
    if (time == 0) {
        $("#mobileCode").attr("disabled", false);
        $("#mobileCode").html("获取验证码");
        time = 60;
        return
    } else {
        $("#mobileCode").attr("disabled", true);
        time = time - 1;
        $("#mobileCode").html("重新发送（" + time + ")");
    }
    setTimeout(function () {
        countdown()
    }, 1000)
}

/**
 * 找回密码
 */
$(document).ready(function () {
    $("#retrievePassword").click(function () {
        var email = $("#email").val();
        if (email == "") {
            $("#errormsg").html("<strong>请填写邮箱信息!</strong>")
            $("#errormsg").show();
            $("#successmsg").hide();
        } else {
            var obj1 = {
                "email": email
            }
            $.ajax({
                url: "/forgetPassword/email",
                type: "post",
                data: JSON.stringify(obj1),
                contentType: "application/json",
                success: function (data) {
                    if (data.status == 200) {
                        $("#successmsg").show();
                        $("#errormsg").hide();
                    } else {
                        $("#errormsg").html("<strong>邮箱不存在!该邮箱未注册或未绑定</strong>")
                        $("#successmsg").hide();
                        $("#errormsg").show();
                    }
                }
            })
        }
    })
})