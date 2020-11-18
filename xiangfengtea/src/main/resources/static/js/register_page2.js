$(document).ready(function () {
    $(".submit").mouseenter(function () {
        setBackgroundColor($(this),"#228b22");
    });
    $(".submit").mouseleave(function () {
        setBackgroundColor($(this),"#249752");
    });
    $("#name").focus(function () {
        displayInline($("#name_tip"));
    });
    $("#name").blur(function () {
        displayNone($("#name_tip"));
        setNameWarnText("* 用户名格式错误");
        if(checkName($("#name").val())){
            displayNone($("#name_warn"));
        }else {
            displayInline($("#name_warn"));
        }
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
    $("#phonenum").blur(function () {
        if(checkPhoneNum($("#phonenum").val())){
            displayNone($("#phonenum_warn"));
        }else {
            displayInline($("#phonenum_warn"));
        }
    });
});
function setNameWarnText(text) {
    document.getElementById("name_warn_text").innerText=text;
}
function mysubmit() {
    if(checkInput()){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/register/checkUserInfoAndSave",
            data: $("#form").serialize(),
            success: function (result) {
                if(result.emailRegisted){
                    alert("注册失败，该邮箱已经注册过了");
                    document.getElementById("form_to_page1").submit();
                    return;
                }
                if(result.nameRegisted){
                    setNameWarnText("* 用户名已存在");
                    displayInline($("#name_warn"));
                    alert("用户名已存在，请更换其他名称");
                    return;
                }
                if(result.success){
                    gotoAction("form","/register/page3");
                }else {
                    alert("注册失败");
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
    if(!checkName($("#name").val())){
        displayInline($("#name_warn"));
        submit=false;
    }
    if(!checkPassword($("#password").val())){
        displayInline($("#password_warn"));
        submit=false;
    }
    if(!checkConfirmPassword($("#password").val(),$("#confirm_password").val())){
        displayInline($("#confirm_password_warn"));
        submit=false;
    }
    if(!checkPhoneNum($("#phonenum").val())){
        displayInline($("#phonenum"));
        submit=false;
    }
    if(!submit){
        alert("请正确填写所需信息");
    }
    return submit;
}