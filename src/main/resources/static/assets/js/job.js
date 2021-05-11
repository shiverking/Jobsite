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
        var post_budget = $("#budgetByHour").val().trim();
        var post_require_level = $("#require_skill option:selected").attr("id");
        var post_work_time = $("#work_time").val().trim();
        var post_skill =$("#skill option:selected").val().trim();
        var post_require_people =$("#require_people").val().trim();
        var post_position = $("#position option:selected").val().trim();
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
        }else if(post_skill == "" || post_skill == "------"){
            warning("需求不能人才类型为空")
        }else if(post_position == ""){
            warning("需求岗位不能为空")
        }
        else {
            var job = {
                "title": post_title,
                "description": post_description,
                "budget": post_budget,
                "expertize_level": post_require_level,
                "work_time": post_work_time,
                "skill": post_skill,
                "required": post_require_people,
                "position": post_position,
            }
            post(job)
        }
    })


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

    $("#toJobLists").click(function (){
        var url = "/job/MyJobLists"
        $.ajax({
            url:url,
            type:'get',
            success(data){
                window.location.href = url
            }
        })
    })


    //后续计划将标签位置存入数据库中，以便后台管理修改
    var skill_tech =['前端开发','后端开发','移动开发','全栈开发','人工智能','硬件开发','测试','运维','项目管理','其他']
    var skill_write =['新媒体编辑','文案编辑','内容审核','文案翻译','其他']
    var skill_operate =['社区运营','电商运营','产品运营','用户运营','内容运营','活动策划','客服','其他']
    var skill_market = ['市场营销','媒介/公关','品牌增长','广告投放','其他']
    var skill_design =['UI设计','交互设计','LOGO设计','平面设计','产品设计','工业设计','游戏设计','其他']
    var skill_video =['短视频相关','视频制作','配音','视频翻译','字幕校对','其他']
    var skill_more =['策划','教育','心理辅导','健身教练','游戏陪玩','其他']

    $("#skill").change(function (){
        var post_skill =$("#skill option:selected").val().trim();
        if (post_skill == "------"){
            $("#positions").hide()
            $("#position").empty()
        }else if(post_skill=="技术"){
            appendSelect(skill_tech);
        }else if(post_skill=="文案写作"){
            appendSelect(skill_write)
        }else if(post_skill=="运营"){
            appendSelect(skill_operate)
        }else if(post_skill=="市场营销"){
            appendSelect(skill_market)
        }else if(post_skill=="设计"){
            appendSelect(skill_design)
        }else if(post_skill=="音视频"){
            appendSelect(skill_video)
        }else {
            appendSelect(skill_more)
        }
    })
    function appendSelect(obj){
        $("#positions").show()
        $("#position").empty()
        for(var i=0;i<obj.length;i++) {
            $("#position").append($("<option>").html(obj[i]))
        }
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