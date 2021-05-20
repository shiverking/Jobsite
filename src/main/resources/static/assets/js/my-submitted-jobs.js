$(document).ready(function() {
    $("#8").addClass("current_page").siblings().removeClass("current_page");
    $.ajax({
        url: "/job/getAllSubmittedJobs",
        type: "post",
        success: function (data) {
            console.log(data);
            for(var i=0,l=data.length;i<l;i++) {
                if(data[i][3]=="true"){
                    addSubmittedJobList(data[i][0],data[i][2],data[i][1],"已开启");
                }
                if(data[i][3]=="false"){
                    addSubmittedJobList(data[i][0],data[i][2],data[i][1],"已关闭");
                }
            }
        }
    })
})
function addSubmittedJobList(title,position,time,status) {
    var tr_str = '<tr>\n' +
        '                                        <td>'+title+'</td>\n' +
        '                                        <td>'+position+'</td>\n' +
        '                                        <td>'+time+'</td>\n' +
        '                                        <td>'+status+'</td>\n' +
        '                                        <td></td>\n' +
        '                                        <td></td>\n' +
        '                                    </tr>'
    $("#openlist").append(tr_str)
}
