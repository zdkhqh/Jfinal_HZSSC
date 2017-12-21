package com.hzvtc.hzssc.config;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;


public class SessionInViewInterceptor  implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		HttpSession session = controller.getSession();
		if(session!=null)controller.setAttr("session", session);
		inv.invoke();
	}
	
}
