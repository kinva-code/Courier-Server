$(document).ready(function () {
    $(".submit").mouseenter(function () {
        setBackgroundColor($(this),"#228b22");
    });
    $(".submit").mouseleave(function () {
        setBackgroundColor($(this),"#249752");
    });
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
});
function showImg(obj) {
    var reader=new FileReader();
    reader.readAsDataURL(obj.files[0]);
    reader.onload=function () {
        document.getElementById("mine_img").src=reader.result;
        if(obj.files[0].size > 1*1024*1024){
            $(".imgWarnTip").slideDown();
        }else{
            $(".imgWarnTip").slideUp();
        }
    }
}
function checkImg() {
    var obj=document.getElementById("imgfile");
    if(obj.files[0].size > 1*1024*1024){
        return false;
    }else{
        return true;
    }
}