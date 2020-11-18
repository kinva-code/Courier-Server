$(document).ready(function () {
    $(".maindiv").css({
        position:"absolute",
        top: ($(window).height()-$(".maindiv").outerHeight())/2
    });
    $(".input").css({
        width: $(".divin").width()-$(".img").outerWidth(true)-20
    });
    $(".submit").mouseenter(function () {
        $(this).css({
            "background-color":"#228b22",
        });
    });
    $(".submit").mouseleave(function () {
        $(this).css({
            "background-color":"#249752",
        });
    });
});
function mysubmit() {
    $("#warndiv").slideUp();
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/checkNameAndPassword",
        data: $("#form").serialize(),
        success: function (result) {
            if(result.accountExist){
                if(result.login){
                    gotoAction("form","/home")
                }else {
                    loginError("密码错误");
                }
            }else {
                loginError("登录名错误，账号不存在");
            }
        },
        error: function (error) {
            console.log(error.toString());
            alert("异常！");
        }
    });
    return false;
}
function gotoAction(form_id,action) {
    document.getElementById(form_id).action=action;
    document.getElementById(form_id).submit();
}
function loginError(text) {
    setWarnText(text);
    $("#warndiv").slideDown();
}
function setWarnText(text) {
    document.getElementById("warn_text").innerText=text;
}