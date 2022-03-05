var basePath = window.location.host;
if(basePath.indexOf("http://") < 0){
	basePath = "http://" + basePath + "/";
}

/*var domain = "92yichu";
var youpai_url = "https://yssj668.b0.upaiyun.com/";
var globalUploadPath = "yssj668";
var globalFormApiSecret = "M3s2N+dRDbjzzRATbBP5IbYIThc=";*/

var domain = "92yichu";
var youpai_url = "https://yssj-real-test.b0.upaiyun.com/"
var globalUploadPath = "yssj-real-test";
var globalFormApiSecret = "T8CDRaESN017je6QcRjYqmjxXkw=";

//融云测试环境
var RYkey = "8luwapkvue73l";

//融云正式环境
//var RYkey = "lmxuhwagxvoqd"
	
var App={};
//封装ajax(提示错误)
App.ajax = function(param){
	var loadingFlag = !!param.loading?param.loading:true;
	$.ajax({
		url: param.url,
		data: param.data,
		type: "POST",
		dataType: "json",
		cache: false,
		async: (typeof param.async)!='undefined'?param.async:true,
		beforeSend: function(){
			if(loadingFlag){
				App.loading("show");
			}
        },
		success: function(data){
			if(data.status == 1){
				param.success(data);
			}else if(data.status == 10030){
				var isAndroid = $.cookie("isAndroid");
				var isIOS = $.cookie("isIOS");
				if(isAndroid == "true"){
					android.toLogin()
				}else if(isIOS == "true"){
					OCModel.callObjectiveCWithGoLogin();
				}else{
					View.login();
				}
					
			}else{
				if(!!data.message){
					App.warning(data.message);
				}
				
			}
			
		},
		error: function(data){
			if(loadingFlag){
				App.loading("hide");
			}
			if(data.readyState == 0){
				window.location.reload();
			}else{
				App.warning("系统繁忙,请稍后！");
				if(!!param.error){
					param.error(data);
				}
			}
		},
		complete: function() {
			if(loadingFlag){
				App.loading("hide");
			}
			
        }
	});
}

//获取店铺名称
App.storeName = function(){
	var name = "衣蝠，无关潮流，只做自己！";
	$.ajax({
		url: basePath + "store/getStoreName?realm=" + $.cookie("realm"),
		type: "get",
		dataType: "json",
		async: false,
		success: function(data){
			if(!!data.store_name){
				name = data.store_name;
			}
		}
	});
	return name;
}

//获取url参数方法
App.getUrlParam = function(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var _r = window.location.search.substr(1).match(reg);
	if (_r!=null){ 
		return decodeURI(_r[2]); 
	}else{
		var thisurl=window.location.href;
		var domainUrl=thisurl.substring(0,thisurl.indexOf(domain));
		if(domainUrl!=null)
			return domainUrl.split(".")[1];
		else
			return "";
	}
}

//请求模板
App.template = function(url){
	var template = null;
	$.ajax({
		url: url,
		async: false,
		success: function(data){
			template = data;
		},
		error: function(data){
			alert("系统繁忙,请稍后！");
		}
	});
	return template;
}

//ajax加载动画
App.loading = function(type){
	var loading = '<div id="loadingToast" class="weui_loading_toast"><div class="weui_mask_transparent"></div><div class="weui_toast"><div class="weui_loading"><div class="weui_loading_leaf weui_loading_leaf_0"></div><div class="weui_loading_leaf weui_loading_leaf_1"></div><div class="weui_loading_leaf weui_loading_leaf_2"></div><div class="weui_loading_leaf weui_loading_leaf_3"></div><div class="weui_loading_leaf weui_loading_leaf_4"></div><div class="weui_loading_leaf weui_loading_leaf_5"></div><div class="weui_loading_leaf weui_loading_leaf_6"></div><div class="weui_loading_leaf weui_loading_leaf_7"></div><div class="weui_loading_leaf weui_loading_leaf_8"></div><div class="weui_loading_leaf weui_loading_leaf_9"></div><div class="weui_loading_leaf weui_loading_leaf_10"></div><div class="weui_loading_leaf weui_loading_leaf_11"></div></div><p class="weui_toast_content">数据加载中</p></div></div>';
	var $loadingToast = $("#loadingToast");
	if(type == "show"){
		$loadingToast.remove();
		$("body").append(loading);
	}else{
		$loadingToast.remove();
	}
}

//操作成功提示
App.remindOk = function(msg){
	var m = !!msg?msg:"操作成功";
	var ok = '<div id="toastOk"><div class="weui_mask_transparent"></div><div class="weui_toast"><i class="weui_icon_toast"></i><p class="weui_toast_content">'+m+'</p></div></div>';
	if($("#toastOk").length > 0){
		$("#toastOk").remove();
	}
	$("body").append(ok);
	$("#toastOk").fadeIn(400).delay(1000).fadeOut(500);
}

//错误提示
App.warning = function(msg){
	var warning = '<div id="warningDialog" class="weui_dialog_alert"><div class="weui_mask"></div><div class="weui_dialog"><div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div><div class="weui_dialog_bd">'+msg+'</div><div class="weui_dialog_ft"><a href="javascript:;" class="weui_btn_dialog primary" onclick="closeWarningDialog()">关闭</a></div></div></div>';
	var $warning = $("#warningDialog");
	if($warning.length > 0){
		$warning.remove();
	}
	$("body").append(warning);
	
}

App.tip = function(param){
	var tipbox = $("body>.tipbox");
	if(tipbox.length > 0){
		tipbox.remove();
	}
	var html = '<div class="tipbox"><div><span>' + param.message + '</span></div></div>';
	$("body").append(html);
	setTimeout(function(){
		$("body>.tipbox").remove();
	},1500);
}

App.remind = function(message){
	$("#messager").remove();
	var html = '<div id="messager"><div><div>' + message + '</div></div>';
	$("body").append(html);
	$("#messager").fadeIn(400).delay(1000).fadeOut(400);
}

//是否在微信客户端打开
App.isWX = function(){ 
    var ua = navigator.userAgent.toLowerCase(); 
    if(ua.match(/MicroMessenger/i) == "micromessenger") { 
        return true; 
     } else { 
        return false; 
    } 
} 

//判断是否登录
App.islogin = function(){
	var token = $.cookie("token");
	if(!!token){
		return true;
	}else{
		return false;
	}
}

//计算字节
App.countByte = function(s){
	var len = 0;  
    for (var i=0; i<s.length; i++){   
    	var c = s.charCodeAt(i);   
    	//单字节加1   
     	if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)){   
       		len++;   
     	}   
     	else {   
      		len += 2;   
     	}   
    } 
    return len;
}

App.loadScript = function(url, callback){  
    var script = document.createElement("script")  
    script.type = "text/javascript";  
    if (script.readyState){  //IE  
        script.onreadystatechange = function(){  
            if (script.readyState == "loaded" || script.readyState == "complete"){  
                script.onreadystatechange = null;  
                callback();  
            }  
        };  
    } else {  //Others  
        script.onload = function(){  
            callback();  
        };  
    }  
    script.src = url;  
    document.getElementsByTagName("head")[0].appendChild(script);  
}  

//过滤表情
App.filteremoji=function(content){  
    var ranges = [  
        '\ud83c[\udf00-\udfff]',  
        '\ud83d[\udc00-\ude4f]',  
        '\ud83d[\ude80-\udeff]'  
    ];  
    var emojireg = content .replace(new RegExp(ranges.join('|'), 'g'), '');  
    return emojireg;  
}  

function imgUrl(shop_code){
	return (shop_code).substring(1,4) + "/" + shop_code + "/";
}

function shopName(name){
	if(!name){
		name = "";
	}else if(name.length > 10){
		name = "..." + name.substring(name.length-10, name.length);
	}
	return name;
}

//跳转商品详情
function toGoodsDetail(self){
	var shop_code = $(self).attr("data-shop_code");
	var isAndroid = $.cookie("isAndroid");
	var isIOS = $.cookie("isIOS");
	var realm = !!App.getUrlParam("realm")?App.getUrlParam("realm"):$.cookie('realm');
	if(isAndroid == "true"){
		android.shopDetail(shop_code.toString());
	}else if(isIOS == "true"){
		OCModel.callObjectiveCWithDict({"shop_code": shop_code});
	}else{
		window.location.href=basePath + "view/store/d.html?r=" + realm + "&s=" + shop_code;
	}
}

function closeWarningDialog(){
	$("#warningDialog").remove();
}

//日期格式化
/*
 * 	示例
	alert(new Date().format("yyyy年MM月dd日"));
	alert(new Date().format("MM/dd/yyyy"));
	alert(new Date().format("yyyyMMdd"));
	alert(new Date().format("yyyy-MM-dd hh:mm:ss"));
*/
Date.prototype.format = function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}

	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	}

	for(var k in o) {
		if(new RegExp("("+ k +")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		}
	}
	return format;
} 

/**
 ** 加法函数，用来得到精确的加法结果
 ** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
 ** 调用：accAdd(arg1,arg2)
 ** 返回值：arg1加上arg2的精确结果
 **/
function plusNumber(arg1, arg2) {
    var r1, r2, m, c;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}

//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function (arg) {
    return accAdd(arg, this);
};

/**
 ** 减法函数，用来得到精确的减法结果
 ** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
 ** 调用：accSub(arg1,arg2)
 ** 返回值：arg1加上arg2的精确结果
 **/
function minusNumber(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.sub = function (arg) {
    return accMul(arg, this);
};

/**
 ** 乘法函数，用来得到精确的乘法结果
 ** 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
 ** 调用：accMul(arg1,arg2)
 ** 返回值：arg1乘以 arg2的精确结果
 **/
function mulNumber(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    }
    catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    }
    catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function (arg) {
    return accMul(arg, this);
};

/** 
 ** 除法函数，用来得到精确的除法结果
 ** 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
 ** 调用：accDiv(arg1,arg2)
 ** 返回值：arg1除以arg2的精确结果
 **/
function divNumber(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
    }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function (arg) {
    return accDiv(this, arg);
};

App.getXY=function(obj){
    var x = 0,y = 0;
    if (obj.getBoundingClientRect) {
        var box = obj.getBoundingClientRect();
        var D = document.documentElement;
        x = box.left + Math.max(D.scrollLeft, document.body.scrollLeft) - D.clientLeft;
        y = box.top + Math.max(D.scrollTop, document.body.scrollTop) - D.clientTop
    }
    else{
        for (; obj != document.body; x += obj.offsetLeft, y += obj.offsetTop, obj = obj.offsetParent) {}
        }
        return {
        x: x,
        y: y
    }
}

//uuid 随机数
$.extend({
	uuid:function(){
		var s = [];
	    var hexDigits = "0123456789abcdef";
	    for (var i = 0; i < 36; i++) {
	        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	    }
	    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	    s[8] = s[13] = s[18] = s[23] = "-";
	 
	    var uuid = s.join("");
	    return uuid;
	}
})

var View={};
//5元现金弹窗
View.fiveFrame=function(){
	
	if(!$.cookie("fiveFrame")&&!$.cookie("fiveFrameTime")){
		if((new Date()).getTime()-parseInt($.cookie("fiveFrameTime"))<24*3600*1000){
			return;
		}
		var grayback=$(".grayback");
		var body=$("body");
		var graybackHtml='<div class="grayback" style="position:fixed;top:0;left:0;right:0;bottom:0;z-index:9999990;background:#000;opacity:0.3"></div>';
		var fiveHtml='<div id="fiveFrame" class="absolute" style="width:280px;height:400px;top:50%;margin-top:-200px;left:50%;margin-left:-140px;z-index:9999991;"><a class="f-right closeFrame"><img class="f-right" src="https://yssj-real-test.b0.upaiyun.com/H5/public/img/store/frame/close.png" width="30"></a><img src="https://yssj-real-test.b0.upaiyun.com/H5/public/img/store/frame/five.png" width="100%"><div class="t-center"><img src="https://yssj-real-test.b0.upaiyun.com/H5/public/img/store/frame/btn.png" width="140"></div><div>';
		body.addClass("overflow-hidden");
		if(grayback.length==0){
			body.append(graybackHtml);
		}
		body.append(fiveHtml);
		$.cookie("fiveFrame","true");
		$.cookie("fiveFrameTime",(new Date()).getTime());
		$("#fiveFrame .closeFrame").on("click",function(){
			body.removeClass("overflow-hidden");
			$(".grayback").remove();
			$("#fiveFrame").remove();
		});
		$("#fiveFrame .t-center img").on("click",function(){
			var realm=!!App.getUrlParam("realm")?App.getUrlParam("realm"):$.cookie("realm");
			if(App.islogin()){
				$.ajax({
					url: basePath + "/record/add?key=v1&type=603&tab_type=8",
					dataType: "json",
					success: function(data){
						window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.yssj.activity&ckey=CK1321531368906";
					},
					error: function(data){
						window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.yssj.activity&ckey=CK1321531368906";
					}
				});
			}else{
				App.ajax({
					url: basePath + "user/getSwitch",
					success: function(data){
						if(data.data == 0){
							if(App.getUrlParam("s")){
								window.location.href=basePath+"view/download/4.html?realm="+realm+"&shop_code="+App.getUrlParam("s");
							}else{
								window.location.href=basePath+"view/download/4.html?realm="+realm;
							}
						}else{
							if(App.isWX()){
								$.ajax({
									url: basePath + 'wxpay/getData',
									async: false,
									success: function(data){
										window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + data.appID + "&redirect_uri=" + encodeURIComponent(basePath+"view/download/weixin_back5.html") + "&response_type=code&scope=snsapi_userinfo&state=" + realm + "#wechat_redirect";
									}
								});
							}else{
								App.warning("请在微信客户端打开！");
							}
						}
					}
				});
				
			}
			
		});
	}
	
}

View.login=function(){
	var html='<div class="weui_dialog_confirm" id="loginConfirm"><div class="weui_mask"></div>'+
	    '<div class="weui_dialog">'+
	        '<div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>'+
	        '<div class="weui_dialog_bd">您还未登录哦，是否立即前往登录页？</div>'+
	        '<div class="weui_dialog_ft"><a class="weui_btn_dialog default" onclick="App.hideConfirm(this)">取消</a><a class="weui_btn_dialog primary" onclick="App.toLogin()">我要登录</a></div>'+
	    '</div>'+
	  '</div>'
	if($("#loginConfirm").length>0){
		$("#loginConfirm").remove();
	}
	$("body").append(html);
}

App.hideConfirm=function(self){
	$(self).parents(".weui_dialog_confirm").hide();
}

App.toLogin=function(){
	var uri=encodeURIComponent(window.location.pathname+window.location.search);
	var realm=!!App.getUrlParam("realm")?App.getUrlParam("realm"):$.cookie("realm");
	window.location.href=basePath+"/user/logi?realm="+realm+"&uri="+uri;
}