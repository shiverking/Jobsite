$(window).on('load', function() {

    //显示欢迎界面
    $("#welcome").show().siblings().hide();
    //$("#welcome").hide().siblings().show();
})

//点击确认发送按钮
$("#confirmSend").click(function () {
    var toUserName = $("#currentUserName").val();
    var content = $("#chatContent").val();
    //向聊天界面添加一条自己的信息
    var now = new Date().Format("yyyy-MM-dd");;
    addOwnRecord(content,now);
    //滑动到最低端
    $('#chat-content').scrollTop($('#chat-content')[0].scrollHeight);
    //向后台发送消息，保存聊天记录
    var obj = {
        "toUserName":toUserName,
        "content": content
    }
    $.ajax({
        url: "/message/saveMessage",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            if (data.status == 200) {
                //发送信息
                sendSpittle(toUserName,content);
                //输入栏置空
                $("#chatContent").val('');
            } else {
                console.log(data);
            }
        }
    })

})

//建立socket服务器
var sock = new SockJS("/endpointChat");
var stomp = Stomp.over(sock);
stomp.connect('guest','guest',function (frame) {
    stomp.subscribe("/user/queue/chat", handleNotification);
});
function handleNotification(message) {
    //对方的用户名
    var username= $("#currentUserName").val();
    var now = new Date().Format("yyyy-MM-dd");
    addOtherRecord(message.body,username,now);
}
function sendSpittle(toUserName,text) {
    //用ascii码为0x01的字符分割，该字符键盘无法输入，十分安全
    var newText =  toUserName+String.fromCharCode(0x01)+text;
    stomp.send("/chat", {}, newText);
}
$("#stop").click(function () {
    sock.close();
});

//添加对方的聊天数据到ul
function addOtherRecord(message,username,time){
    var li_str='<li class="d-flex message">\n' +
        '                            <div class="mr-lg-3 me-2">\n' +
        '                                <img class="avatar sm rounded-circle" src="/assets_Message/images/xs/avatar5.jpg"\n' +
        '                                     alt="avatar">\n' +
        '                            </div>\n' +
        '                            <div class="message-body">\n' +
        '                                <span class="date-time text-muted">'+username+','+time.substr(0,10)+'</span>\n' +
        '                                <div class="message-row d-flex align-items-center">\n' +
        '                                    <div class="message-content p-3">'+message+
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </li>'
    $('#content-list').append(li_str);
}
//添加自己的聊天数据到ul
function addOwnRecord(message,time){
    var li_str='<li class="d-flex message right">\n' +
        '                            <div class="message-body">\n' +
        '                                <span class="date-time text-muted">'+time.substr(0,10)+'<i\n' +
        '                                        class="zmdi zmdi-check-all text-primary"></i></span>\n' +
        '                                <div class="message-row d-flex align-items-center justify-content-end">\n' +
        '                                    <div class="message-content border p-3">'+message+
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </li>'
    $('#content-list').append(li_str);
}
//当点击了某个用户标签进行会话时，切换到对应用户的聊天界面
$("li[id^=user]").click(function () {
    //显示聊天界面，关闭欢迎界面
    $("#communication").show();
    $("#chat-content").show();
    $("#input-box").show();
    //移除欢迎界面
    $("#welcome").remove();
    //添加选中样式
    $(this).addClass("active").siblings().removeClass("active");
    var toUserName = $(this).find('h6').html();
    var toUserId = $(this).attr("id").replace('user','');
    //清空右侧所有聊天信息
    $("#content-list").find("li").remove();
    //修改当前聊天显示的用户名
    $("#currentChatUserName").html(toUserName);
    //修改当前聊天显示的ID
    $("#currentUserId").val(toUserId);
    //记录当前的聊天用户名
    $("#currentUserName").val(toUserName);
    //向后台发送消息，查找聊天记录
    var obj = {
        "toUserName":toUserName,
    }
    $.ajax({
        url: "/message/queryMessageRecord",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            for(var i=0,l=data.length;i<l;i++) {
                   if(data[i]["receiver_id"]==toUserId){
                       addOwnRecord(data[i]["content"],data[i]["send_time"]);
                   }
                   else{
                       addOtherRecord(data[i]["content"],toUserName,data[i]["send_time"]);
                   }
            }
            //滑动到最低端
            $('#chat-content').scrollTop($('#chat-content')[0].scrollHeight);
        }
    })

})
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