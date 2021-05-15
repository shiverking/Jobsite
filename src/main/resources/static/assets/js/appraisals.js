$(window).on('load', function() {
    //向后台发送请求，查询自己收到的评论
    $.ajax({
        url: "/judge/tome",
        type: "post",
        success: function (data) {
            console.log(data);
            $("#comments_to_me").show();
            $("#comments_from_me").hide();
            $("#comments_to_me").find("a").remove();
            for(var i=0,l=data.length;i<l;i++) {
                addCommentsToMe(data[i]["id"],data[i]["name"],data[i]["score"],data[i]["create_time"],data[i]["content"]);
            }
        }
    })
})
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
function MaskIt(obj){
    var hoverdiv = '<div class="divMask" style="position: absolute; width: 100%; height: 100%; left: 0px; top: 0px; background: #fff; opacity: 0; filter: alpha(opacity=0);z-index:5;"></div>';
    $(obj).wrap('<div class="position:relative;"></div>');
    $(obj).before(hoverdiv);
    $(obj).data("mask",true);
}
//页面加载时
$(document).ready(function () {
    //默认优先显示我收到的评价
    $("#comments_to_me").show();
    $("#comments_from_me").hide();
})
//切换成不同的列表显示
$(".navbar-nav>li").click(function(){
    $(this).addClass("active").siblings().removeClass("active");
})
//绑定查询动作
$('#judgePeople').click(function () {
    $.ajax({
        url: "/judge?id=2",
        type: "post",
        success: function (data) {
            console.log(data);
            //设置头像链接
            $('.modal-body').find('img').attr('src',data[1]);
            $('.modal-body').find('h6').eq(0).html(data[0]);
            $('#commentedId').val(data[2]);
            //禁用按钮
            $("#confirmJudgement").attr("disabled", true);
            //已经评价过
            if(data[3]=="true"){
                //隐藏按钮
                $("#confirmJudgement").hide();
                $("#recipient-content").val(data[4]);
                //设为只读
                $("#recipient-content").attr("readonly","readonly");
                $('#ratingOnEnter').html(data[5]);
                //显示已评价
                $("#evaluation").show();
                //让对应的星星显示
                for(i=0;i<parseInt(data[5]);i++){
                    $('.rating-stars-container').find('div').eq(i).addClass('is--active');
                }
                //创建遮罩层，禁用所有鼠标动作，不让分数变动
                MaskIt($("#rating"));
            }
        }
    })
})

//必须输入全部的数据才可以点击
$("#exampleModal").mouseover(function () {
    var score = $('#ratingOnEnter').html();
    var content = $('#recipient-content').val();
    if(score!=""&&content!=""){
        //启用按钮
        $("#confirmJudgement").attr("disabled", false);
    }
    else{
        //继续禁用按钮
        $("#confirmJudgement").attr("disabled", true);
    }
})


//绑定保存动作
$('#confirmJudgement').click(function(){
    var content = $('#recipient-content').val();
    var id = $('#commentedId').val();
    var score = $('#ratingOnEnter').html();

    obj={
        id :id,
        score : score,
        content : content,
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

//绑定查找我发出的评论动作
$("#FromMe").click(function(){
    //向后台发送要保存的数据
    $.ajax({
        url: "/judge/fromme",
        type: "post",
        success: function (data) {
            console.log(data);
            $("#comments_to_me").hide();
            $("#comments_from_me").show();
            $("#comments_from_me").find("a").remove();
            for(var i=0,l=data.length;i<l;i++) {
                addCommentsFromMe(data[i]["id"],data[i]["name"],data[i]["score"],data[i]["create_time"],data[i]["content"]);
            }
        }
    })
})
//查询我收到的评论
$("#ToMe").click(function(){
    //向后台发送请求，查询自己收到的评论
    $.ajax({
        url: "/judge/tome",
        type: "post",
        success: function (data) {
            console.log(data);
            $("#comments_to_me").show();
            $("#comments_from_me").hide();
            $("#comments_to_me").find("a").remove();
            for(var i=0,l=data.length;i<l;i++) {
                addCommentsToMe(data[i]["id"],data[i]["name"],data[i]["score"],data[i]["create_time"],data[i]["content"]);
            }
        }
    })
})

$("#comments_to_me").on("click",'a',function(){
    var id=$(this).attr('id');
    obj={
        id :id,
        //1为查关于自己的评论，2为查自己对别人的评论
        type:"1",
    }
    //向后台发送请求查询数据
    $.ajax({
        url: "/judge/findJudgement",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            //设置头像链接
            $('.modal-body').find('img').attr('src',data[1]);
            $('.modal-body').find('h6').eq(0).html(data[0]);
            $('#commentedId').val(data[2]);
            $("#recipient-content").val(data[3]);
            //设为只读
            $("#recipient-content").attr("readonly","readonly");
            $('#ratingOnEnter').html(data[4]);
            //显示已评价
            $("#evaluation").show();
            //让对应的星星显示
            for(i=0;i<parseInt(data[4]);i++){
                $('.rating-stars-container').find('div').eq(i).addClass('is--active');
            }
            //创建遮罩层，禁用所有鼠标动作，不让分数变动
            MaskIt($("#rating"));
        }
    })
})

$("#comments_from_me").on("click",'a',function(){
    var id=$(this).attr('id');
    obj={
        id :id,
        type:"2",
    }
    //向后台发送请求查询数据
    $.ajax({
        url: "/judge/findJudgement",
        type: "post",
        data: JSON.stringify(obj),
        contentType: "application/json",
        success: function (data) {
            console.log(data);
            //设置头像链接
            $('.modal-body').find('img').attr('src',data[1]);
            $('.modal-body').find('h6').eq(0).html(data[0]);
            $('#commentedId').val(data[2]);
            $("#recipient-content").val(data[3]);
            //设为只读
            $("#recipient-content").attr("readonly","readonly");
            $('#ratingOnEnter').html(data[4]);
            //显示已评价
            $("#evaluation").show();
            //让对应的星星显示
            for(i=0;i<parseInt(data[4]);i++){
                $('.rating-stars-container').find('div').eq(i).addClass('is--active');
            }
            //创建遮罩层，禁用所有鼠标动作，不让分数变动
            MaskIt($("#rating"));
        }
    })
})







//添加自己对他人的评价
function addCommentsFromMe(id,name,score,time,content){
    var a_str='<a href="#" class="list-group-item list-group-item-action" data-toggle="modal" data-target="#commentModal" id="'+id+'">\n' +
        '                                    <div class="d-flex w-100 justify-content-between">\n' +
        '                                        <h5 class="mb-1">对'+name+'的评价&nbsp;&nbsp;&nbsp;'+score+'分</h5>\n' +
        '                                        <small>'+time.substr(0,10)+'</small>\n' +
        '                                    </div>\n' +
        '                                    <p class="mb-1" style="width: 150px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">'+content+'</p>\n' +
        '                                </a>'
    $('#comments_from_me').append(a_str);
}
//添加他人对自己的评价
function addCommentsToMe(id,name,score,time,content){
    var a_str='<a href="#" class="list-group-item list-group-item-action" data-toggle="modal" data-target="#commentModal" id="'+id+'">\n' +
        '                                    <div class="d-flex w-100 justify-content-between">\n' +
        '                                        <h5 class="mb-1">来自'+name+'的评价&nbsp;&nbsp;&nbsp;'+score+'分</h5>\n' +
        '                                        <small>'+time.substr(0,10)+'</small>\n' +
        '                                    </div>\n' +
        '                                    <p class="mb-1" style="width: 150px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">'+content+'</p>\n' +
        '                                </a>'
    $('#comments_to_me').append(a_str);
}