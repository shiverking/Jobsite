function hire(user_id, job_id) {
    var res = confirm("确定要雇佣此用户吗");
    if (res) {
        var data ={
            "job_id": job_id,
            "user_id": user_id
        }

        console.log(data);
        $.ajax({
            url: "/job/hireUser",
            data: JSON.stringify(data),
            type:"post",
            contentType: 'application/json',
            success:function (data){
                if(data.status == 200){
                    success(data.msg,2000)
                }else {
                    warning(data.msg)
                }
            }

        })
    }
}

function warning(str) {
    GrowlNotification.notify({
        title: '提醒!',
        description: str,
        type: 'warning',
        position: 'bottom-right',
        closeTimeout: 3000
    })
}

function success(str, time) {
    GrowlNotification.notify({
        title: '成功!',
        description: str,
        type: 'success',
        position: 'bottom-right',
        closeTimeout: time
    });
}