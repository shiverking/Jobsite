$(document).ready(function (){
    $("#gotoPassword").click(function (){
        $("#loginByPhone").hide()
        $("#loginByPassword").show()
    })
    $("#gotoPhone").click(function (){
        $("#loginByPhone").show()
        $("#loginByPassword").hide()
    })
})