$(document).ready(function () {
    $(".submit").mouseenter(function () {
        setBackgroundColor($(this),"#228b22");
    });
    $(".submit").mouseleave(function () {
        setBackgroundColor($(this),"#249752");
    });
    $("#password").focus(function () {
        displayInline($("#password_tip"));
    });
    $("#password").blur(function () {
        displayNone($("#password_tip"));
        if(checkPassword($("#password").val())){
            displayNone($("#password_warn"));
        }else {
            displayInline($("#password_warn"));
        }
        if(checkConfirmPassword($("#password").val(),$("#confirm_password").val())){
            displayNone($("#confirm_password_warn"));
        }else {
            displayInline($("#confirm_password_warn"));
        }
    });
    $("#confirm_password").blur(function () {
        if(checkConfirmPassword($("#password").val(),$("#confirm_password").val())){
            displayNone($("#confirm_password_warn"));
        }else {
            displayInline($("#confirm_password_warn"));
        }
    });
});
function mysubmit() {
    if(checkInput()){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/update_password/checkUserInfoAndUpdate",
            data: $("#form").serialize(),
            success: function (result) {
                if(result.success){
                    gotoAction("form","/update_password/page3")
                }else {
                    alert(result.message);
                }
            },
            error: function () {
                alert("异常！");
            }
        });
    }
    return false;
}
function checkInput() {
    var submit=true;
    if(!checkPassword($("#password").val())){
        displayInline($("#password_warn"));
        submit=false;
    }
    if(!checkConfirmPassword($("#password").val(),$("#confirm_password").val())){
        displayInline($("#confirm_password_warn"));
        submit=false;
    }
    if(!submit){
        alert("请正确填写所需信息");
    }
    return submit;
}