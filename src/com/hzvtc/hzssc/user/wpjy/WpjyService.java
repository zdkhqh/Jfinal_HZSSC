package com.hzvtc.hzssc.user.wpjy;

import java.util.List;

import com.hzvtc.hzssc.model.Goods;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class WpjyService {
	private static final Goods dao = new Goods();
	
	public List<Record> getAll(){
		String sql="select * from goods";
		List<Record> list = Db.find(sql);
		return list;
	}


}
