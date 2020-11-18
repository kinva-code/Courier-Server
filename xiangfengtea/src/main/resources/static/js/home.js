function mysubmit() {
    $.ajax({
        type: "POST",
        dataType: "json",
        url: "/logout",
        data: $("#form").serialize(),
        success: function () {
            console.log("???");
            gotoAction("form","/home");
        },
        error: function () {
            alert("异常！");
        }
    });
    return false;
}
function gotoAction(form_id,action) {
    document.getElementById(form_id).action=action;
    document.getElementById(form_id).submit();
}