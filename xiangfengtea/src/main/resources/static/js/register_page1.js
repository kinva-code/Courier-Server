var timeout=60;
$(document).ready(function () {
    $(".input").bind("input propertychange",function () {
        $("#emailinput").val($(this).val());
    });
    $(".input2").bind("input propertychange",function () {
        $("#codeinput").val($(this).val());
    });
    $(".input").blur(function () {
        setEmailWarnText("* 邮箱格式错误");
        if(checkEmail($(".input").val())){
            displayNone($("#email_warn"));
        }else {
            displayInline($("#email_warn"));
        }
    });
    $(".input2").blur(function () {
        if(checkCode($(".input2").val())){
            displayNone($("#verifycode_warn"));
        }else {
            displayInline($("#verifycode_warn"));
        }
    });
    $(".submit").mouseenter(function () {
        setBackgroundColor($(this),"#228b22");
    });
    $(".submit").mouseleave(function () {
        setBackgroundColor($(this),"#249752");
    });
});
function setEmailWarnText(text) {
    document.getElementById("email_warn_text").innerText=text;
}
function getCode() {
    $("#tips").slideUp();
    if(!checkEmail($(".input").val())){
        setEmailWarnText("* 邮箱格式错误");
        displayInline($("#email_warn"));
        return false;
    }else {
        displayNone($("#email_warn"));
    }
    setSubmitInput(true,"获取验证码中");
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/register/getVerifyCode",
        data: $("#form").serialize(),
        success: function (result) {
            if(result.emailRegisted){
                setSubmitInput(false,"获取验证码");
                setEmailWarnText("* 邮箱已注册");
                displayInline($("#email_warn"));
                alert("该邮箱已注册，请用其他邮箱注册账号");
                return;
            }
            if(result.success){
                getCodeAfterTime();
                $("#tips").slideDown();
            }else {
                setSubmitInput(false,"获取验证码");
                alert("验证码发送失败，请重新尝试");
            }
        },
        error: function () {
            alert("异常！");
        }
    });
    return false;
}
function mysubmit() {
    if(checkInput()){
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/register/checkCode",
            data: $("#form2").serialize(),
            success: function (result) {
                if(result.success){
                    gotoAction("form2","/register/page2");
                }else {
                    displayInline($("#verifycode_warn"));
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
    if(!checkEmail($(".input").val())){
        displayInline($("#email_warn"));
        submit=false;
    }else {
        displayNone($("#email_warn"));
    }
    if(!checkCode($(".input2").val())){
        displayInline($("#verifycode_warn"));
        submit=false;
    }else {
        displayNone($("#verifycode_warn"));
    }
    if(!submit){
        alert("请正确填写所需信息");
    }
    return submit;
}
function setSubmitInput(disabled,context) {
    var submit=document.getElementById("submit");
    submit.disabled=disabled;
    submit.value=context;
}
function getCodeAfterTime() {
    if(timeout==0){
        setSubmitInput(false,"获取验证码");
        timeout=60;
    }else {
        setSubmitInput(true,"重新发送("+timeout+"s)");
        timeout--;
        setTimeout("getCodeAfterTime()",1000);
    }
}