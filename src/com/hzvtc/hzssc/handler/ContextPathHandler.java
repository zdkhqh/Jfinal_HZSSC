package com.hzvtc.hzssc.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzvtc.hzssc.tool.ToolWeb;
import com.jfinal.handler.Handler;

public class ContextPathHandler extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		//设置 web 路径;
		String cxt = ToolWeb.getContextPath(request);
		request.setAttribute("cxt", cxt);
		next.handle(target, request, response, isHandled);
	}

}
