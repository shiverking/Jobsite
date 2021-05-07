$(document).ready(function(){








    /**
     * 检验表单各个内容是否合法
     */
    $("#postJob").click(function (){
        /*
        标题 title
        描述 description
        时薪 budgetByHour
        专业等级 required_skill
        需求人数 require_people
        工作时间 work_time
        岗位 position
         */
        var reg = /^\+?[1-9][0-9]*$/
        var post_title = $("#title").val().trim();
        var post_description = $("#description").val().trim();
        var post_budget = $("#budgetByHour").val();
        var post_require_skill = $("#require_skill option:selected").attr("id");
        var post_work_time = $("#work_time").val();
        var post_position =$("#position").val();
        var post_require_people =$("#require_people").val();
        if(post_title == ""){
            warning("标题不能为空")
        }else if(post_budget == ""){
            warning("时薪不能为空")
        }else if(!reg.test(post_budget)) {
            warning("时薪必须为正整数")
        }else if(post_work_time == ""){
            warning("工作时间不能为空")
        }else if(!reg.test(post_work_time) || parseInt(post_work_time)>=24){
            warning("工作时间必须为正整数且小于24")
        }else if(post_require_people == ""){
            warning("需求人数不能为空")
        }else if(!reg.test(post_require_people)){
            warning("需求人数必须为正整数")
        }else if(post_description == ""){
            warning("工作描述不能为空")
        }else if(post_position == ""){
            warning("需求岗位不能为空")
        }else {
            var job = {
                "title": post_title,
                "description": post_description,
                "budget": post_budget,
                "expertize_level": post_require_skill,
                "work_time": post_work_time,
                "position": post_position,
                "required": post_require_people,
                "create_time": new Date()
            }
            post(job)
        }
    })

    function warning(str){
        GrowlNotification.notify({
            title: '提醒!',
            description: str,
            type: 'warning',
            position: 'bottom-right',
            closeTimeout: 3000
        })
    }

    function success(str, time){
        GrowlNotification.notify({
            title: '成功!',
            description: str,
            type: 'success',
            position: 'bottom-right',
            closeTimeout: time
        });
    }

    function post(job) {
        $.ajax({
            url: '/job/postAJob',
            type: 'post',
            data: JSON.stringify(job),
            contentType: 'application/json; charset=UTF-8',
            success: function (data) {
                if(data.status == 200) {
                    var id = data.obj.JobId;
                    success("发布工作成功", 1500)
                    setTimeout(function (){
                        window.location.href = "/job/"+id
                    },1500)
                }else {
                    warning("发布工作失败，请重新尝试")
                }
            }
        })
    }

});