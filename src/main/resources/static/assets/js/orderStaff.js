$(document).ready(function () {
    $.ajax({
        url: "/order/getDetails",
        success: function (res) {
            if (res.status == 200) {
                showHtml(res);
            } else {
                warning("页面出现错误")
            }
        }
    })
})


function showHtml(res) {
    var job = res.obj.job;
    $("#JobTitle").html(job.title)
    $("#budget").html(job.budget)
    $("#skill").html(job.skill)
    $("#position").html(job.position)
    var Ids = res.obj.Ids;
    var headUrls = res.obj.headUrls;
    var userName = res.obj.userNames;
    var telephone = res.obj.telephones;
    if (Ids.length == 0 || headUrls.length == 0 || userName.length == 0 || telephone.length == 0) {
        warning("页面发生错误，请重新操作")
    } else {
        var str = ""
        for(var i =0;i<Ids.length;i++){
            str+= getHtml(Ids[i],headUrls[i],userName[i],telephone[i])
        }
        staffs.innerHTML=str;
    }
}

function getHtml(Ids, headUrl, userName, telephone) {
    var str = "";
    str += "<div class='col-sm-6'>" +
        "<div class='staffBox'>" +
        "<div class='staff_img'>" +
        "<img alt src='" + headUrl + "'>" +
        "</div>" +
        "<div class='staff_detail'>" +
        "<h3>" + userName + "</h3>" +
        "<ul>" +
        "<li>" +
        "<h6>联系电话</h6>" +
        "<span>" + telephone + "</span>" +
        "</li>" +
        "</ul>" +
        "<div class='staffBox_action'>" +
        "<a class='btn btn-third' href='/viewProfile/user_id="+Ids+"'>查看他的简历</a> "+
        "<a class='btn btn-third' href='/message?id="+Ids+"'>联系他</a> "+
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>"
return str;

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