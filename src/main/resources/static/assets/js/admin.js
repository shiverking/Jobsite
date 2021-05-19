//审核Job操作
$("#checkJob").click(function () {
    var id = $("#jobId").val();
    alert(id);
    //id不为空，发送审核请求
    if(id!="") {
        var obj = {
            "id":id,
        }
        //发送添加用户请求
        $.ajax({
            url: "/admin/checkJob",
            type: "post",
            data: JSON.stringify(obj),
            contentType: "application/json",
            success: function (data) {
                if (data.status == 200) {
                    //弹出提示
                    GrowlNotification.notify({
                        title: '成功!',
                        description: '职位审核成功',
                        type: 'success',
                        position: 'bottom-right',
                        closeTimeout: 3000
                    });
                } else {
                    //弹出失败提示
                    GrowlNotification.notify({
                        title: '错误!',
                        description: '职位审核失败',
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

//删除Job操作
$("#deleteJob").click(function () {
    var id = $("#jobId").val();
    alert(id);
    //id不为空，发送审核请求
    if(id!="") {
        var obj = {
            "id":id,
        }
        //发送添加用户请求
        $.ajax({
            url: "/admin/deleteJob",
            type: "post",
            data: JSON.stringify(obj),
            contentType: "application/json",
            success: function (data) {
                if (data.status == 200) {
                    //弹出提示
                    GrowlNotification.notify({
                        title: '成功!',
                        description: '职位删除成功',
                        type: 'success',
                        position: 'bottom-right',
                        closeTimeout: 3000
                    });
                } else {
                    //弹出失败提示
                    GrowlNotification.notify({
                        title: '错误!',
                        description: '职位删除失败',
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

function selectUser() {
    //获取被选中的option标签
    var opt = $('select  option:selected').val();
    if (opt!=""){
        if(opt=="employee"){
            $.ajax({
                url:"/admin/employee",
                type:"get"
                }
            );
        }
        else if(opt=="employer"){
            $.ajax({
                    url:"/admin/employer",
                    type:"get"
                }
            );
        }
        else if(opt=="user"){
            $.ajax({
                    url:"/admin",
                    type:"get"
                }
            );
        }
    }
}

function checkBoxOnClick(checkbox) {
    if(checkbox.checked == true){
        $.ajax({
            url: "/admin/jobOrderByTime",
            type: "post",
            success:function(data){
                console.log(data);
            }
        })
    }
}