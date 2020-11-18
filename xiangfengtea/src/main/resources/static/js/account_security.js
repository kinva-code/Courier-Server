$(document).ready(function () {
    $("#navigationDiv ul li").mouseenter(function () {
        setBackgroundColor($(this),"#228b22");
    });
    $("#navigationDiv ul li").mouseleave(function () {
        setBackgroundColor($(this),"#249752");
    });
    $("#mine_func_ul li").mouseenter(function () {
        setColor($(this),"#249752");
    });
    $("#mine_func_ul li").mouseleave(function () {
        setColor($(this),"#000000");
    });
    $(".updateDiv").mouseenter(function () {
        setColor($(this),"#249752");
    });
    $(".updateDiv").mouseleave(function () {
        setColor($(this),"#551A8B");
    });
    $("#newPhoneNum").blur(function () {
        if(checkPhoneNum($(this).val())){
            $("#phoneNumWarn").slideUp();
        }else {
            $("#phoneNumWarn").slideDown();
        }
    });
    hiddenPopupWindow();
});
function updatePassword() {
    document.getElementById("form").submit();
}
function hiddenPopupWindow() {
    $("#updatePhoneNumFormDiv").PopupWindow({
        autoOpen: false,
        title: "修改手机号码"
    });
    $("#updatePhoneNumDiv").on("click", function(event){
        $("#updatePhoneNumFormDiv").PopupWindow("open");
    });
}
function myconfirm() {
    if(checkPhoneNum($("#newPhoneNum").val())){
        $("#phoneNumWarn").slideUp();
        document.getElementById("updatePhoneNumForm").submit();
        closeDialog();
    }else {
        $("#phoneNumWarn").slideDown();
    }
}
function closeDialog() {
    $("#updatePhoneNumFormDiv").PopupWindow("close");
}