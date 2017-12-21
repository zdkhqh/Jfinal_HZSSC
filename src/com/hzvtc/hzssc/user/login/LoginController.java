package com.hzvtc.hzssc.user.login;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.hzvtc.hzssc.model.Students;
import com.hzvtc.hzssc.tool.EncoderByMd5;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;

public class LoginController extends Controller{
	static LoginService service = new LoginService();
	
	//登录判断
	public void login(){
		String username=getPara("username");
		String password=getPara("password");
		if(username != null && password != null){
			//1.先判断用户名书否存在
			int stuId=Integer.parseInt(username);
			Students stu = service.searchByStuId(stuId);
			if(stu == null)setAttr("returnMsg",0);//如果是0代表账号或密码错误
			else {
				//2.再判断密码是否正确
				try {
					if((EncoderByMd5.EncoderByMd5(password)).equals(stu.getPassword())){//密码匹配成功
						setAttr("returnMsg",1);//如果是1代表登录成功
						setSessionAttr("student", stu);//保存到session中
					}else{
						setAttr("returnMsg",0);//如果是0代表账号或密码错误
					}
				} catch (NoSuchAlgorithmException e) {
					setAttr("returnMsg",-8);//如果是-8代表错误
				} catch (UnsupportedEncodingException e) {
					setAttr("returnMsg",-9);//如果是-9代表错误
				}
			}
		}else{//账号或密码为空
			setAttr("returnMsg",0);//如果是0代表账号或密码错误
		}
		renderJson();
	}
	
	//获取登录信息
	public void getLoginStu(){
		Students stu = getSessionAttr("student");
		if(stu != null){
			setAttr("returnMsg", 1);
			setAttr("student", stu);
		}
		renderJson();
	}
	
	//注销登录
	public void outLogin(){
		// 删除session中的属性  
		removeSessionAttr("student");
		renderJson();
	}
}
