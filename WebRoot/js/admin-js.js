$('.sidebar>ul>li>span')
		.click(
				function() {
					// $('.sidebar>ul>li>ul').hide();
					// 隐藏显示菜单子列表
					$(this).next('ul').slideToggle(180);
					// 改变箭头方向
					$(this)
							.find('span')
							.attr(
									'class',
									$(this).find('span').attr('class') == "arrowright" ? "arrowdown"
											: "arrowright");

				});

// 管理员注销登录动能
function outLogin() {
	if (confirm("确认注销登录吗？")) {
		window.location.href = "manager_outLogin";// admin注销action
	}
}

// studentsList按条件查询
$("#submitQueryForm").click(function() {
	page_nav("queryForm", 1);
})

// 翻页功能js
function page_nav(frm, num) {
	if (typeof (frm) != 'object') {
		frm = document.querySelector("#" + frm);
	}
	with (frm) {
		pageNo.value=num;
		var strhref = "?"+$(frm).serialize();
		window.location.href = strhref;
	}
}
//翻页B功能js
function page_navB(frm, num) {
	if (typeof (frm) != 'object') {
		frm = document.querySelector("#" + frm);
	}
	with (frm) {
		day.value=num;
		var strhref = "?"+$(frm).serialize();
		window.location.href = strhref;
	}
}
//修改学生或管理员的账号信息
function updateInfo(formId){
	if(confirm("确认保存信息吗?")){
		var strUrl='';
		if(formId=='updateAdmin')strUrl='admin_updateAdmin';
		else if(formId=='updateStu')strUrl='stu_updateStudent';
		else{
			alert("更新失败！");
			return; 
		}
		$.ajax({
	        url: strUrl,
	        type: "POST",
	        data: $("#"+formId).serialize(),
	        beforeSend: function(){
	        	 $("#"+formId).modal('hide');
	         },
	        success: function(data){
	          if(data.returnMsg == 1){
	        	  alert("更新数据成功！");
	        	  location.reload();
	          }else{
	        	  alert("更新失败！"+data.returnMsg);
	          }
	        },
	        error: function(err){
	          alert("更新失败！");
	        }
	      });
	}
}

//学生或管理员修改自己的密码
function changeMyPassword(who){
	var p1=$('#inputPassword1').val();
	var p2=$('#inputPassword2').val();
	var p3=$('#inputPassword3').val();
	if(p1 =='' || p2=='' || p3==''){
		alert("输入不得为空！");
		return;
	}
	if(p2 != p3){
		alert("两次新密码输入不相同！");
		return;
	}else{
		if(p1 == p2){
			alert("新密码不能与原始密码输入相同！");
			return;
		}
	}
	if(confirm("确认修改吗?")){
		var strUrl='';
		if(who == 'managerPassword')strUrl='manager_updatePassword';
		else if(who == 'studentPassword')strUrl='user_updatePassword';
		else{
			alert("修改失败！");
			return; 
		}
		$.ajax({
	        url: strUrl,
	        type: "POST",
	        data: $("#"+who).serialize(),
	        beforeSend: function(){
	        	 $("#"+who).modal('hide');
	         },
	        success: function(data){
	          if(data.returnMsg == 1){
	        	  alert("修改密码成功！请重新登录！");
	        	  window.location.reload();
	          }else{
	        	  
	        	  alert("修改失败！可能是原密码输入错误！"+data.returnMsg);
	          }
	        },
	        error: function(err){
	          alert("修改失败！");
	        }
	      });
	}
}

//选择图片判断大小并显示到img
function changeFile(target,img,f_upload) {
	// document.getElementById("addimg").innerHTML=" <img id='preview' alt=''
	// name='preview' height='180' /><br> ";
    var pic = document.getElementById(img),
        file = document.getElementById(f_upload);
 
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
        html5Reader(file,img);
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