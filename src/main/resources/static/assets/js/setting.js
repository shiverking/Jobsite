// $(window).on('load', function(){
//   $(".job_main_right>div").eq(0).show().siblings().hide();
// });

$(document).ready(function () {
    //切换标签页
    $(".user_navigation>li").click(function () {
        $(this).addClass("is-active").siblings().removeClass("is-active");
        var index = $(this).index();
        // console.log(index);
        $(".job_main_right>div").eq(index).show().siblings().hide();
    })
})
//修改密码成功提示
$('#confirmChangePassword').click(function(){
    GrowlNotification.notify({
        title: '修改成功',
        description: '修改密码成功!',
        type: 'success',
        position: 'bottom-right',
        closeTimeout: 3000
    });
})
//确认密码是否存在与数据库中
$("#password").blur(function () {
    var password = $("#password").val();
    $.ajax({
        url: "/setting/isPasswordExist",
        type: "post",
        data: JSON.stringify(obj1),
        contentType: "application/json",
        success: function (data) {
            if (data.status != 200) {

            } else {
                $("#errormsg").val(data.msg)
                $("#errormsg").show()
                console.log("failed")
            }
        }
    })

})