$(document).ready(function () {
    $("#2,#6").addClass("current_page").siblings().removeClass("current_page");
    $.ajax({
        url: "/findStaff/getAllProfile",
        type: "post",
        success:function (data) {
            console.log(data);
            for(var i=0,l=data.length;i<l;i++) {
                addEmployee(data[i][0],data[i][1],data[i][2],data[i][3],data[i][4]);
            }
        }
    })
})
function addEmployee(name,headurl,expertise_level,compensation,expertise_realm){
    var div_str =' <div class="col-sm-3">\n' +
        '                            <div class="staffBox">\n' +
        '                                <div class="staff_img">\n' +
        '                                    <img alt="" src="'+headurl+'">\n' +
        '                                </div>\n' +
        '                                <div class="staff_detail">\n' +
        '                                    <h3>'+name+'</h3>\n' +
        '                                    <p>'+expertise_realm+'</p>\n' +
        '                                    <ul>\n' +
        '                                        <li>\n' +
        '                                            <h6>专业等级</h6>\n' +
        '                                            <i class="fas fa-calendar-check"></i>\n' +
        '                                            <span>'+expertise_level+'</span>\n' +
        '                                        </li>\n' +
        '                                        <li>\n' +
        '                                            <h6>薪酬</h6>\n' +
        '                                            <i class="fas fa-user"></i>\n' +
        '                                            <span>'+compensation+'</span>\n' +
        '                                        </li>\n' +
        '                                    </ul>\n' +
        '                                    <div class="staffBox_action">\n' +
        '                                        <a class="btn btn-third" href="staff-profile-single.html">查看简历</a>\n' +
        '                                    </div>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </div>'
    $('#people').append(div_str);
}