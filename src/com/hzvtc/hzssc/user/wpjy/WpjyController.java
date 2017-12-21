package com.hzvtc.hzssc.user.wpjy;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class WpjyController extends Controller {
	
	static WpjyService service = new WpjyService();
	
	public void index(){
		List<Record> list = service.getAll();
		setAttr("list", list);
		render("wpjy.html");
	}

}
