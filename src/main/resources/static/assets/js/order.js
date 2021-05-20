$(document).ready(function () {
    //进入页面默认展示全部order
    allorders();

})

function unpaid() {
    $("#unpaid").addClass("is-active").siblings().removeClass("is-active")
    $("#allorder").hide();
    $("#peningorder").hide();
    $("#finishedorder").hide();
    $("#workingorder").hide();
    var data = {
        "state": "0"
    }
    unpaidorder.innerHTML = loadData(data);
    $("#unpaidorder").show();
}

function working() {
    $("#working").addClass("is-active").siblings().removeClass("is-active")
    $("#allorder").hide();
    $("#peningorder").hide();
    $("#finishedorder").hide();
    $("#unpaidorder").hide();
    var data = {
        "state": "1"
    }
    workingorder.innerHTML = loadData(data);
    $("#workingorder").show();

}

function pending() {
    $("#pending").addClass("is-active").siblings().removeClass("is-active")
    $("#allorder").hide();
    $("#workingorder").hide();
    $("#finishedorder").hide();
    $("#unpaidorder").hide();
    var data = {
        "state": "2"
    }
    peningorder.innerHTML = loadData(data);
    $("#peningorder").show();


}


function finished() {
    $("#finished").addClass("is-active").siblings().removeClass("is-active")
    $("#allorder").hide();
    $("#workingorder").hide();
    $("#peningorder").hide();
    $("#unpaidorder").hide();
    var data = {
        "state": "3"
    }
    finishedorder.innerHTML = loadData(data);
    $("#finishedorder").show();

}

//后台请求数据并将返回的数据拼接html语句
function loadData(data) {
    var s = ""
    $.ajax({
        url: "/order/getOrders",
        type: "post",
        data: JSON.stringify(data),
        //设置为同步执行
        async: false,
        contentType: 'application/json; charset=UTF-8',
        success: function (res) {
            if (res.status == 200) {
                console.log(res);

                var a = res.obj.authority;
                if (a == "[ROLE_employer]") {
                    for (var i = 0; i < res.obj.order.length; i++) {
                        var id = res.obj.order[i].id;
                        var title = res.obj.job_title[i];
                        var create_time = new Date(res.obj.order[i].created_time).Format("yyyy-MM-dd");
                        var state = res.obj.order[i].state;
                        if (res.obj.order[i].end_time != null) {
                            var end_time = new Date(res.obj.order[i].end_time).Format("yyyy-MM-dd");
                            s += shows(id, title, state, create_time, end_time);
                        } else {
                            s += shows(id, title, state, create_time, "");
                        }
                    }
                } else if(a == "[ROLE_employee]"){
                    for (var i = 0; i < res.obj.order.length; i++) {
                        var id = res.obj.order[i].id;
                        var title = res.obj.job_title[i];
                        var create_time = new Date(res.obj.order[i].created_time).Format("yyyy-MM-dd");
                        var state = res.obj.order[i].state;
                        if (res.obj.order[i].end_time != null) {
                            var end_time = new Date(res.obj.order[i].end_time).Format("yyyy-MM-dd");
                            s += showsEE(id, title, state, create_time, end_time);
                        } else {
                            s += showsEE(id, title, state, create_time, "");
                        }
                    }

                }else {
                    warning("获取数据失败，请重新尝试")
                    return "";
                }
            }
        }
    })
    return s;
}


function allorders() {
    $("#allOrders").addClass("is-active").siblings().removeClass("is-active")
    $("#finishedorder").hide();
    $("#workingorder").hide();
    $("#peningorder").hide();
    $("#unpaidorder").hide();
    var data = {
        "state": "5"
    }
    allorder.innerHTML = loadData(data);
    $("#allorder").show();
}


//返回html展示order语句
function shows(id, title, state, create_time, end_time) {
    var button = "";
    if (state == "0") {
        state = "未预付"
        var button = "<button type='button' class='btn btn-info' onclick='toworking(" + id + ")'>已预付</button>"
    } else if (state == "1") {
        state = "工作中"
        var button = "<button type='button' class='btn btn-info' onclick='topengding(" + id + ")'>已完成</button>"
    } else if (state == "2") {
        state = "未支付"
        var button = "<button type='button' class='btn btn-info' onclick='tofinished(" + id + ")'>已支付</button>"
    } else {
        state = "已结束"
        var button = "<button type='button' class='btn btn-info' onclick='toComment(" + id + ")'>去评价</button>"
    }
    var str = "";
    str += "<div class='col-sm-12'>" +
        "<div class='featured_box'>" +
        "<div class='fb_content'>" +
        "<h4><a href='#' onclick='todetaile(" + id + ")'>" + title + "</a></h4>" +
        "<ul>" +
        "<li> 创建时间:" +
        create_time +
        "</li>"
    if (state == "已结束" && end_time != "") {
        str += "<li>结束时间:" +
            end_time +
            "</li>"
    }
    str += "<li>状态:" +
        state +
        "</li>" +
        "</ul>" +
        "</div>" +
        button +
        "</div></div>";
    return str;
}

function showsEE(id, title, state, create_time, end_time){
    var button = "";
    if (state == "0") {
        state = "未预付"
    } else if (state == "1") {
        state = "工作中"
    } else if (state == "2") {
        state = "未支付"
    } else {
        state = "已结束"
        var button = "<button id='judgePeople' type='button' class='btn btn-primary' data-toggle='modal' data-target='#exampleModal' onclick='employeeCom("+id+")'>去给雇主一个评价吧</button> "
    }
    var str = "";
    str += "<div class='col-sm-12'>" +
        "<div class='featured_box'>" +
        "<div class='fb_content'>" +
        "<h4>" + title + "</h4>" +
        "<ul>" +
        "<li> 创建时间:" +
        create_time +
        "</li>"
    if (state == "已结束" && end_time != "") {
        str += "<li>结束时间:" +
            end_time +
            "</li>"
    }
    str += "<li>状态:" +
        state +
        "</li>" +
        "</ul>" +
        "</div>" +
        button +
        "</div></div>";
    return str;
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
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


//订单已经完成预付，将进入下个阶段,下个阶段为工作中
function toworking(id) {
    var bool;
    bool = updateState(id, "1");
    if (bool) {
        success("更新该订单状态成功", 3000);
        //重新请求数据，确保无误
        working();
        sendMessages(id, "1");
    } else {
        warning("更新订单状态失败，请重新尝试")
    }
}


function topengding(id) {
    var bool;
    bool = updateState(id, "2");
    if (bool) {
        success("更新该订单状态成功", 3000);
        //重新请求数据，确保无误
        pending();
        sendMessages(id, "2")
    } else {
        warning("更新订单状态失败，请重新尝试")
    }
}


function tofinished(id) {

    var bool;
    bool = updateState(id, "3");
    if (bool) {
        success("更新该订单状态成功", 3000);
        //重新请求数据，确保无误
        finished();
        sendMessages(id, "3");
    } else {
        warning("更新订单状态失败，请重新尝试")
    }
}


//用于每次更新完订单状态后发送消息
function sendMessages(id, state) {
    var data = {
        "id": id,
        "state": state
    }
    $.ajax({
        url: "/order/sendMessage",
        type: "post",
        data: JSON.stringify(data),
        contentType: 'application/json; charset=UTF-8',
        success: function (res) {
            if (res.status != 200) {
                warning("消息通知失败，请手动通知参与该工作的人员订单目前已更新状态")
            }
        }
    })
}

//更新订单状态，id为订单的id，state为欲更新的状态
function updateState(id, state) {
    var bool = false;
    var data = {
        "id": id,
        "state": state
    }
    $.ajax({
        url: "/order/updateState",
        type: "post",
        data: JSON.stringify(data),
        //设置为同步执行
        async: false,
        contentType: 'application/json; charset=UTF-8',
        success: function (res) {
            if (res.status == 200) {
                bool = true;
            } else {
                bool = false;
            }
        }
    })
    return bool;
}

//跳转页面，查看订单详细情况
function todetaile(id) {
    $.ajax({
        url: "/order/saveOrderSession?orderId=" + id,
        type: "post",
        success: function (res) {
            if (res.status == 200) {
                window.location.href = "/order/showDetails"
            } else {
                warning("查看订单失败")
            }
        }
    })
}

function toComment(id) {
    $.ajax({
        url: "/order/saveOrderSession?orderId=" + id,
        type: "post",
        success: function (res) {
            if (res.status == 200) {
                window.location.href = "/order/comment"
            } else {
                warning("进入评论页面失败")
            }
        }
    })
}

function toCommentER(id){

}
var ratingOptions = {
    selectors: {
        starsSelector: '.rating-stars',
        starSelector: '.rating-star',
        starActiveClass: 'is--active',
        starHoverClass: 'is--hover',
        starNoHoverClass: 'is--no-hover',
        targetFormElementSelector: '.rating-value'
    }
};
$(".rating-stars").ratingStars(ratingOptions);
$(".rating-stars").on("ratingOnEnter", function (ev, data) {
    $("#ratingOnEnter").html(data.ratingValue);
});

//创建遮罩层禁用鼠标动作
function MaskIt(obj) {
    var hoverdiv = '<div class="divMask" style="position: absolute; width: 100%; height: 100%; left: 0px; top: 0px; background: #fff; opacity: 0; filter: alpha(opacity=0);z-index:5;"></div>';
    $(obj).wrap('<div class="position:relative;"></div>');
    $(obj).before(hoverdiv);
    $(obj).data("mask", true);
}

//必须输入全部的数据才可以点击
$("#exampleModal").mouseover(function () {
    var score = $('#ratingOnEnter').html();
    var content = $('#recipient-content').val();
    if (score != "" && content != "") {
        //启用按钮
        $("#confirmJudgement").attr("disabled", false);
    } else {
        //继续禁用按钮
        $("#confirmJudgement").attr("disabled", true);
    }
})





//绑定查询动作
function judge(id) {
    $.ajax({
        url: "/judge?id="+id,
        type: "post",
        success: function (data) {
            console.log(data);
            //设置头像链接
            $('.modal-body').find('img').attr('src', data[1]);
            $('.modal-body').find('h6').eq(0).html(data[0]);
            $('#commentedId').val(data[2]);
            //禁用按钮
            $("#confirmJudgement").attr("disabled", true);
            //已经评价过
            if (data[3] == "true") {
                //隐藏按钮
                $("#confirmJudgement").hide();
                $("#recipient-content").val(data[4]);
                //设为只读
                $("#recipient-content").attr("readonly", "readonly");
                $('#ratingOnEnter').html(data[5]);
                //显示已评价
                $("#evaluation").show();
                //让对应的星星显示
                for (i = 0; i < parseInt(data[5]); i++) {
                    $('.rating-stars-container').find('div').eq(i).addClass('is--active');
                }
                //创建遮罩层，禁用所有鼠标动作，不让分数变动
                MaskIt($("#rating"));
            }
        }
    })
}

function employeeCom(id){
    var data = {
        "orderId":id
    }
    $.ajax({
        url:"/order/getEmployerId",
        data:JSON.stringify(data),
        type:"post",
        contentType:"application/json",
        success: function (res){
            if(res.status == 200){
                console.log(res.obj)
                judge(res.obj)

            }else {
                warning("评价失败，请重新尝试")
            }
        }

    })
}


//绑定保存动作
$('#confirmJudgement').click(function () {
    var content = $('#recipient-content').val();
    var id = $('#commentedId').val();
    var score = $('#ratingOnEnter').html();

    obj = {
        id: id,
        score: score,
        content: content,
    }
    console.log(obj);
    //向后台发送要保存的数据
    $.ajax({
        url: "/judge/confirm",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            if (data.status == 200) {
                //弹出提示
                GrowlNotification.notify({
                    title: '成功!',
                    description: '评价成功，请到我的评价页面中查看',
                    type: 'success',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
                //关闭模态框
                $('#exampleModal').modal('hide')
            } else {
                //弹出失败提示
                GrowlNotification.notify({
                    title: '错误!',
                    description: '评价失败',
                    type: 'error',
                    position: 'bottom-right',
                    closeTimeout: 3000
                });
            }
        }
    })
})