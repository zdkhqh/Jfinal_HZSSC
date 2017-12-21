//页面加载完成时加载是否已经登录
var urlStart="http://127.0.0.1/";
$("#welcome")
		.ready(getLogin());

function getLogin(){
	$
	.ajax({
		type : 'POST',
		url : urlStart +  "user_login/getLoginStu",
		success : function(result) {

			if (result.returnMsg == 1) {
				$("#welcome")
						.html(
								"欢迎登录，<span style='color:#337ab7;font-size:20px;'>"
										+ result.student.name
										+ "</span> , <a style='font-size:16px;' href='javascript:outLogin();'>注销</a>");
			} else
				return;
		}
	});
}

$('#myModal').on('shown.bs.modal', function() {
	$('#username').focus();
});

// 前端页面登录功能js
$("#login")
		.click(
				function() {
					var username = $("#username");
					var password = $("#password");
					if (username.val() == "" || password.val() == "") {
						// 输入为空判断并提示
						// alert("学号或密码输入为空！");
					} else {
						$("#close").click();// 点击取消关闭模态框
						$("#welcome").css('display', 'none'); // 隐藏欢迎登录
						$(".loading").css('display', 'block'); // 显示转圈加载
						// ajax与后台进行判断是否登录成功
						$
								.ajax({
									type : 'POST',
									timeout : 7000, // 超时时间设置，单位毫秒
									url : urlStart +  "user_login/login?username="
											+ username.val() + "&password="
											+ password.val(),
									success : function(result) {
										$("#welcome").css('display', 'block'); // 显示欢迎登录
										$(".loading").css('display', 'none'); // 隐藏转圈加载
										if (result.returnMsg == "-1") {
											alert("登录失效!");
										}else if(result.returnMsg < -1){
											alert("程序出错啦！");
										}
										else if (result.returnMsg == "0") {
											alert("账号或密码输入错误!");
										} else if(result.returnMsg == '1'){
											//alert("登录成功！");
											//getLogin();
											window.location.reload();// 刷新当前页面.
											/*$("#welcome")
													.html(
															"欢迎登录，<span style='color:#337ab7;font-size:20px;'>"
																	+ result.student.name
																	+ "</span> , <a style='font-size:16px;' href='javascript:outLogin();'>注销</a>");*/
										}
									},
									complete : function(XMLHttpRequest, status) { // 请求完成后最终执行参数
										if (status == 'timeout') {// 超时,status还有success,error等值的情况
											ajaxTimeoutTest.abort();
											$("#welcome").css('display',
													'block'); // 显示欢迎登录
											$(".loading")
													.css('display', 'none'); // 隐藏转圈加载
											alert("登录超时!");
										}
									}
								});

					}
				});

// 用户注销登录动能
function outLogin() {
	if (confirm("确认注销登录吗？")) {
		$.ajax({
			url : urlStart +  "user_login/outLogin",
			success : function(result) {
				window.location.reload();// 刷新当前页面.
			}
		});
	}
}

//选择图片判断大小并显示到img
function changeFile(target) {
	// document.getElementById("addimg").innerHTML=" <img id='preview' alt=''
	// name='preview' height='180' /><br> ";
    var pic = document.getElementById("checkPic"),
        file = document.getElementById("uploadPic");
 
    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
 
     // gif在IE浏览器暂时无法显示
    if(ext!='png'&&ext!='jpg'&&ext!='jpeg'&&ext!='gif'){
         alert("图片的格式必须为png或者jpg或者jpeg或者gif格式！"); 
         return;
     }
     
     // 限制大小
     var fileSize = 0;         
     if (isIE && !target.files) {     
       var filePath = target.value;     
       var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
       var file = fileSystem.GetFile (filePath);     
       fileSize = file.Size;    
     } else {    
      fileSize = target.files[0].size;
      }   
      var size = fileSize / 1024;    
      if(size>(1024*5)){  
       alert("图片大小不能大于5M");
       target.value="";
       return
      }
     var isIE = navigator.userAgent.match(/MSIE/)!= null,
         isIE6 = navigator.userAgent.match(/MSIE 6.0/)!= null;
 
     if(isIE) {
        file.select();
        var reallocalpath = document.selection.createRange().text;
 
        // IE6浏览器设置img的src为本地路径可以直接显示图片
         if (isIE6) {
            pic.src = reallocalpath;
         }else {
            // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
             pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
             // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
             pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
         }
     }else {
        html5Reader(file,"checkPic");
     }
}
function html5Reader(file,idname){
     var file = file.files[0];
     var reader = new FileReader();
     reader.readAsDataURL(file);
     reader.onload = function(e){
         var pic = document.getElementById(idname);
         pic.src=this.result;
     }
 }
 
//提交场地申请
 function submitApply(){
	if($('#spaceName').val()==''||$('#spaceName').val()==null){
		alert("请输入活动名称！");
		$('#spaceName').focus();
	}else if($('#spaceCompany').val()==''||$('#spaceCompany').val()==null){
		alert("请输入活动方的组织或单位名称！");
		$('#spaceCompany').focus();
	}else if($('input:radio[name="activity.space"]:checked').val()==null){
		alert("请选择活动使用场地!");
	}else if($('#dateTime').val()==null ||	$('#dateTime').val()==''){
		alert("请选择活动日期！");
	}else if($('#startDate').val()==null ||	$('#startDate').val()==''){
		alert("请选择活动开始时间！");
	}else if($('#endDate').val()==null || $('#endDate').val()==''){
		alert("请选择活动结束时间！");
	}else if($('#phone').val()==null ||	$('#phone').val()==''){
		alert("请填写可以联系到的联系方式！");
		$('#phone').focus();
	}else if($('#uploadPic').val()==null ||	$('#uploadPic').val()==''){
		alert("请填选择上传合照图片验证！");
	}else{
		if($('#phone').val().length!=11){
			alert("请输入11位手机号码！");
			return;
		}
		$("#isd").val($('#dateTime').val()+" "+$('#startDate').val());
		$("#ied").val($('#dateTime').val()+" "+$('#endDate').val());
		var st=Date.parse(new Date($("#isd").val())) / 1000;
		var et=Date.parse(new Date($("#ied").val())) / 1000;
		if(et>st){//结束时间大于开始时间
			$('#applyForm').submit();
		}else {
			alert("可能是结束时间大于开始时间，请重新选择时间!");
		}
	}
 }
 
 