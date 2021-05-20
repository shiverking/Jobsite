$(document).ready(function() {
    $("#5,#3").addClass("current_page").siblings().removeClass("current_page");
})
$(".pagination>li").click(function(){
    $(this).addClass("active").siblings().removeClass("active");
})
$('.fb_action button').click(function () {
    //设置选中的jobid
    $("#jobId").val($(this).attr('id'));
    //向后台发起查询，看是否已经申请过该工作
    $.ajax({
        url: "/job/ifHasSubmitResume",
        type: "post",
        data: JSON.stringify({job_id: $("#jobId").val()}),
        contentType: "application/json",
        success: function (data) {
            if (data.status == 200) {
                //关闭模态框
                $('#confirmModal').modal('hide');
                //弹出警告
                GrowlNotification.notify({
                    title: '申请失败!',
                    description: '您已经申请过该工作',
                    type: 'error',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
            }
            else{
                $('#confirmModal').modal('show');
            }
        }
    })
})
$("#submittedConfirm").click(function () {
    var profile_id;
    var job_id = $("#jobId").val();
    $.ajax({
        url: "/isProfileExist",
        type: "post",
        data: JSON.stringify({}),
        contentType: "application/json",
        success: function (data) {
            if (data.status == 200) {
                profile_id = data.msg;
                obj = {
                    job_id: job_id,
                    profile_id: profile_id,
                }
                $.ajax({
                    url: "/job/submitJobConfirm",
                    type: "post",
                    data: JSON.stringify(obj),
                    contentType: "application/json",
                    success: function (data) {
                        if (data.status == 200) {
                            //弹出提示
                            GrowlNotification.notify({
                                title: '成功!',
                                description: '简历投递成功!请到我投递的职位中查看',
                                type: 'success',
                                position: 'bottom-right',
                                closeTimeout: 3000
                            });
                            //关闭模态框
                            $('#confirmModal').modal('hide')
                        } else {
                            //弹出失败提示
                            GrowlNotification.notify({
                                title: '错误!',
                                description: '简历投递失败',
                                type: 'error',
                                position: 'bottom-right',
                                closeTimeout: 3000
                            });
                        }
                    }
                })
            } else {
                //弹出失败提示
                GrowlNotification.notify({
                    title: '申请出错!',
                    description: '您还未保存简历',
                    type: 'error',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
                //关闭模态框
                $('#confirmModal').modal('hide')
                $("#jobId").val("");
            }
        }
    })
})
