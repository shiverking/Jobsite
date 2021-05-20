$(document).ready(function () {
    $.ajax({
        url: "/order/toComment",
        type: "post",
        success: function (res) {
            if (res.status == 200) {
                combineHtml(res);
            } else {
                warning("页面出现错误")
            }
        }
    })
})

function combineHtml(res) {
    var Ids = res.obj.Ids;
    var headUrls = res.obj.headUrls;
    var profiles = res.obj.profiles;
    var str = ""
    for (var i = 0; i < Ids.length; i++) {
        str += show(Ids[i], headUrls[i], profiles[i])
    }
    comment.innerHTML = str;

}

function show(id, headUrl, profile) {
    var str = "";
    str += "<div class='col-sm-6'>" +
        "<div class='featured_box'>" +
        "<div class='fb_image'>" +
        "<img src='" + headUrl + "'>" +
        "</div>" +
        "<div class='fb_content'>" +
        "<h4>" + profile.user_name + "</h4>" +
        "<ul>" +
        "<li>" +
        "<i class='fas fa-money-bill-alt' style='color: #0a58ca'>" + profile.compensation + "</i> " +
        "</li>" +
        "<li>" +
        "<i class='far fa-clock'style='color: #0a58ca'>" + profile.workexperience + "</i>" +
        "</li>" +
        "</ul>" +
        "</div>" +
        "<div class='fb_action'>" +
        "<button id='judgePeople' type='button' class='btn btn-primary' data-toggle='modal' data-target='#exampleModal' onclick='select("+id+")'>去评价</button> " +
        "</div> " +
        "</div>" +
        "</div>"
    return str
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
function select(id) {
    console.log("clicks");
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
    var userid;


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