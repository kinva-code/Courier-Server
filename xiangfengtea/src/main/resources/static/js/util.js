function gotoAction(form_id,action) {
    document.getElementById(form_id).action=action;
    document.getElementById(form_id).submit();
}
function displayNone(obj){
    obj.css({"display": "none"});
}
function displayInline(obj){
    obj.css({"display": "inline"});
}
function checkEmail(email) {
    if(email != "") {
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        //调用正则验证test()函数
        isok= reg.test(email);
        if(!isok) {
            return false;
        }else {
            return true;
        }
    }
    return false;
}
function checkCode(code) {
    if(code==""){
        return false;
    }
    return true;
}
function checkName(name) {
    var reg = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“\"'。，、？\\s]");
    if(!reg.test(name) && name.length>=5 && name.length<=20){
        return true;
    }
    return false;
}
function checkPassword(password) {
    var reg = /^[\w]{6,20}$/;
    return reg.test(password);
}
function checkConfirmPassword(password,confirmPassword) {
    var reg = /^[\w]{6,20}$/;
    if(reg.test(confirmPassword) && confirmPassword==password){
        return true;
    }
    return false;
}
function checkPhoneNum(phoneNum){
    var length = phoneNum.length;
    if(length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|)+\d{8})$/.test(phoneNum) ) {
        return true;
    }
    return false;
}
function setBackgroundColor(obj,color){
    obj.css({
        "background-color": color,
    });
}
function setColor(obj,color) {
    obj.css({
        "color": color,
    });
}
function checkAddressInfo(address) {
    if(address==null || address==""){
        return false;
    }
    if(address.search("所在省")==-1 && address.search("所在市")==-1 && address.search("所在区/县")==-1){
        return true;
    }
    return false;
}
function checkReceiveAddress(address) {
    var reg = new RegExp("[`~!@#$^&*()=|{}\\[\\].<>/?~！@#￥……&*（）——|{}【】？\\s]");
    if(!reg.test(address) && address.length>=5 && address.length<=120){
        return true;
    }
    return false;
}