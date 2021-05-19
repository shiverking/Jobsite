$(document).ready(function (){
    var id = $("#orderId").val();
    console.log("234")
    $.ajax({
        url:"/order/getDetails",
        success: function (res){
            if(res.status == 200){
                console.log(res);
            }
        }
    })
})