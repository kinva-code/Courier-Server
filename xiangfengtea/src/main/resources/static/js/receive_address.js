$(document).ready(function () {
    $(".receiveInfoOperate").mouseenter(function () {
        setColor($(this),"#249752");
    });
    $(".receiveInfoOperate").mouseleave(function () {
        setColor($(this),"#000000");
    });
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
    $("#receiveAddress").focus(function () {
        displayInline($("#receiveAddress_tip"));
    });
    $("#receiveAddress").blur(function () {
        displayNone($("#receiveAddress_tip"));
        if(checkReceiveAddress($("#receiveAddress").val())){
            displayNone($("#receiveAddressWarn"));
        }else {
            displayInline($("#receiveAddressWarn"));
        }
    });
    $("#receiveName").focus(function () {
        displayInline($("#receiveName_tip"));
    });
    $("#receiveName").blur(function () {
        displayNone($("#receiveName_tip"));
        if(checkName($("#receiveName").val())){
            displayNone($("#receiveNameWarn"));
        }else {
            displayInline($("#receiveNameWarn"));
        }
    });
    $("#phoneNum").blur(function () {
        if(checkPhoneNum($("#phoneNum").val())){
            displayNone($("#phoneNumWarn"));
        }else{
            displayInline($("#phoneNumWarn"));
        }
    });
});
function setDefaultAddress() {
    var defaultAddress=document.getElementById("isDefaultAddress");
    if(defaultAddress.checked){
        defaultAddress.checked=false;
    }else {
        defaultAddress.checked=true;
    }
}
function choose() {
    var s1 = document.getElementById('s1').value;
    var s2 = document.getElementById('s2').value;
    var s3 = document.getElementById('s3').value;
    var address=s1+s2+s3;
    $("#addressInfoShow").val(address);
    $("#addressInfo").val(address);
    if(checkAddressInfo($("#addressInfo").val())){
        displayNone($("#addressInfoWarn"));
    }else {
        displayInline($("#addressInfoWarn"));
    }
}
function checkInput() {
    var submit=true;
    if(!checkAddressInfo($("#addressInfo").val())){
        submit=false;
        displayInline($("#addressInfoWarn"));
    }
    if(!checkReceiveAddress($("#receiveAddress").val())){
        submit=false;
        displayInline($("#receiveAddressWarn"));
    }
    if(!checkName($("#receiveName").val())){
        submit=false;
        displayInline($("#receiveNameWarn"));
    }
    if(!checkPhoneNum($("#phoneNum").val())){
        submit=false;
        displayInline($("#phoneNumWarn"));
    }
    return submit;
}
function updateReceiveInfo(receiveInfoId) {
    $("#receiveInfoIdInput").val(receiveInfoId);
    var form=document.getElementById("receiveInfoOperateForm");
    form.action="/account_manage/receive_address";
    form.submit();
}
function deleteReceiveInfo(receiveInfoId) {
    if(confirm("确认删除该条地址么？")){
        $("#receiveInfoIdInput").val(receiveInfoId);
        var form=document.getElementById("receiveInfoOperateForm");
        form.action="/account_manage/deleteReceiveInfo";
        form.submit();
    }
}
function setDefaultReceiveInfo(receiveInfoId) {
    $("#receiveInfoIdInput").val(receiveInfoId);
    var form=document.getElementById("receiveInfoOperateForm");
    form.action="/account_manage/setDefaultReceiveInfo";
    form.submit();
}