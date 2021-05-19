//页面加载时
$(document).ready(function () {
    //默认优先显示我收到的评价
    $("#comments_to_me").show();
    $("#comments_from_me").hide();
})
//切换成不同的列表显示
$(".navbar-nav>li").click(function(){
    $(this).addClass("active").siblings().removeClass("active");
    if($(this).innerHTML=="我收到的评价"){
        $("#comments_to_me").show();
        $("#comments_from_me").hide();
    }
    else{
        $("#comments_to_me").hide();
        $("#comments_from_me").show();
    }
})