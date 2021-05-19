$(document).ready(function (){
    $("#setting").click(function (){
        var s = $("#setting").attr("value")
        console.log("123")
        warning(s)
    })
})

/*
页面加载表格
 */
$(document).ready(function (){
    $.ajax({
        url: "/job/showMyJobLists",
        type: "post",
        success(data){
            if(data.status == 200){
                var jobs = data.obj.jobLists;
                var sends = data.obj.sends;
                var str1 = "";
                var str2 = "";
                for(var i=0;i<jobs.length;i++) {
                    if (jobs[i].job_status == true) {
                        str1 += "<tr>" +
                            "<td>" + "<a href='/job/"+jobs[i].id+"'"+"style='color: #111111'>"+jobs[i].title + "</a></td>" +
                            "<td>" + jobs[i].required + "</td>" +
                            "<td>" + jobs[i].position + "</td>" +
                            "<td>" + jobs[i].create_time + "</td>" +
                            "<td>" +"<a href='/job/viewSeeker/job_id="+jobs[i].id+"'>"+ sends[i] + "</a></td>"+
                            "<td>开启</td>" +
                            "<td><input  type=\"button\" onclick=\"colRow(this," + jobs[i].id + ");\" value=\"关闭\"/></td>"+
                            "</tr>"
                        }
                    else {
                        str2 +="<tr>"+
                            "<td>" + "<a href='/job/"+jobs[i].id+"'"+"style='color: #111111'>"+jobs[i].title + "</a></td>" +
                            "<td>" + jobs[i].required + "</td>" +
                            "<td>" + jobs[i].position + "</td>" +
                            "<td>" + jobs[i].create_time + "</td>" +
                            "<td>" +"<a href='/job/viewSeeker/job_id="+jobs[i].id+"'>"+ sends[i] + "</a></td>"+
                            "<td style=\"color:red\">关闭</td>" +
                            "<td><input  type=\"button\" onclick=\"openRow(this," + jobs[i].id + ");\" value=\"开启\"/></td>"+
                            "</tr>"
                    }
                }
                openlist.innerHTML = str1;
                closelist.innerHTML = str2;
            }else if(data.msg == "列表为空"){
                $("#nojob").show();
                $("#hasjob").hide();

            }else{
                console.log(data)
                warning("获取列表失败，请重新尝试")
            }
        },
    })
})


/**
 * 关闭谋工作，第一个参数未元素对象，第二个参数未关闭的工作的id
 * @param obj
 * @param id
 */
function colRow(obj,id){
    var res=confirm("确定要关闭该工作招聘吗？");
    if(res){
        var pa =$(obj).parents("tr").children();
        var url = "/job/closeJob/"+id
        $.ajax({
            url: url,
            type: "post",
            success(data){
                if(data.status == 200){
                    //修改表格内容
                    pa.eq(5).html("关闭")
                    pa.eq(5).css("color","red")
                    pa.eq(6).html("<input  type=\"button\" onclick=\"openRow(this," + id + ");\" value=\"开启\"/>")
                }else {

                    warning(data.msg)
                }

            }
        })
    }
}

function openRow(obj,id){
    var res= confirm("确定要开启该工作招聘吗?");
    if(res){
        var pa =$(obj).parents("tr").children();
        var url = "/job/openJob/"+id
        $.ajax({
            url: url,
            type: "post",
            success(data){
                if(data.status == 200){
                    pa.eq(5).html("开启")
                    pa.eq(5).css("color","black")
                    pa.eq(6).html("<input  type=\"button\" onclick=\"colRow(this," + id + ");\" value=\"关闭\"/>")
                }else {
                    warning(data.msg);
                }

            }
        })

    }
}



function warning(str){
    GrowlNotification.notify({
        title: '提醒!',
        description: str,
        type: 'warning',
        position: 'bottom-right',
        closeTimeout: 3000
    })
}