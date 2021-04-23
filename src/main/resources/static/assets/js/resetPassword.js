$(document).ready(function(){
    (function ($) {
        $.getUrlParam = function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
    })(jQuery);

    var key = $.getUrlParam('key');
    var obj1 = {
        "key": key
    }
    $.ajax({
        url:'/resetPassword/isLegal',
        type:'post',
        data:JSON.stringify(obj1),
        contentType:"application/json",
        success:function(data){
            //开始修改密码
            if (data.status == 200){
                $('#inputform').show();
                $('#errormsg1').hide();
                $('#errormsg2').hide();
            }else if(data.status == 1){//链接失效
                $('#errormsg1').show();
                $('#inputform').hide();
                $('#errormsg2').hide();
            }else if(data.status == 2){//无效链接
                $('#errormsg2').show();
                $('#inputform').hide();
                $('#errormsg1').hide();
            }
            console.log(data);
        }
    })
    $("#confirmReset").click(function () {
        var newPassword = $("#newPassword").val();
        var newPasswordConfirm = $("#newPasswordConfirm").val();
        if (newPassword != newPasswordConfirm) {
            $("#errormsg3").show();
        } else {
            $("#errormsg3").hide();
            var key = $.getUrlParam('key');
            var obj1 = {
                "pwd": newPassword,
                "key": key
            }
            $.ajax({
                url: "/resetPassword/confirm",
                type: "post",
                data: JSON.stringify(obj1),
                contentType: "application/json",
                success: function (data) {
                    if (data.status == 200) {
                        alert("修改成功，正在为您重定向...");
                        setTimeout(function() {
                            window.location.href = "/login";
                        },3000);
                    } else {
                        console.log("failed")
                    }
                }
            })
        }
    });


});