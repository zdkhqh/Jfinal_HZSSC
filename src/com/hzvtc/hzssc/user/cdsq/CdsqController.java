package com.hzvtc.hzssc.user.cdsq;

import java.util.Date;

import com.hzvtc.hzssc.model.Activity;
import com.hzvtc.hzssc.model.Students;
import com.hzvtc.hzssc.tool.DateUtil;
import com.jfinal.core.Controller;

public class CdsqController extends Controller {
	
	static CdsqService service = new CdsqService();
	
	
	//前端场地申请表单提交保存
	public void applySpace(){
		Activity activity = getModel(Activity.class);
		String startDate = getPara("startDate");
		String endDate = getPara("endDate");
		Integer returnMsg;
		try {
			activity.setStartTime(DateUtil.strToDate(startDate,"yyyy-MM-dd hh:mm:ss"));
			activity.setEndTime(DateUtil.strToDate(endDate, "yyyy-MM-dd hh:mm:ss"));// 设置活动结束时间
			activity.setCreateTime(new Date());// 申请创建时间
			activity.setState(1);// 0表示审核未通过，1表示待审核，2表示审核通过
			Students stu = getSessionAttr("student");
			if(stu == null){
				returnMsg = -1;//未登录
			}else{
				activity.setStuId(stu.getStudentId());
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}// 设置活动开始时间
		
		returnMsg = service.save(activity);
		setAttr("returnMsg", returnMsg);//表单提交保存成功
		render("cdsq.html");
	}
	
	//前端场地查看页
	public void userShow(){
		Students stu = getSessionAttr("student");
		if(stu!=null){
			setAttr("stuName", stu.getName());
			setAttr("stuId", stu.getStudentId());
		}
		render("cdsq.html");
	}
	
	//前端场地列表查询
	public void cdsqList(){
		String nextDay = getPara("nextDay");
		String spaceStr = getPara("space");
		Integer space =  Integer.valueOf(spaceStr);
		String dayStr = getPara("day");
		Integer day =  0;
		if(dayStr != null)day=Integer.valueOf(dayStr);
		Date nowDate = new Date();
		String startTime = "";
		String endTime = "";
		if (nextDay != null) {
			if(nextDay.equals("")){
				nextDay = DateUtil.getDateInstance(nowDate);
			}else{
				try {
					Date d=DateUtil.strToDate(nextDay,"yyyy-MM-dd");
				} catch (Exception e) {
					nextDay = DateUtil.getDateInstance(nowDate);//出错了
					day=0;
				}
				//指定日期查询
					String n=DateUtil.getDateInstance(nowDate);
					for (int i = 0; i < 15; i++) {
						n=DateUtil.getAfterDay(n);
						if(n.equals(nextDay)){
							day=i+1;
							break;
						}
					}
			}
		}else{
			nextDay = DateUtil.getDateInstance(nowDate);// 当天日期转成字符串类型
			if (day > 15)
				day = 15;
			else if (day < 0)
				day = 0;
			if (day > 0) {
				for (int i = 0; i < day; i++) {
					nextDay = DateUtil.getAfterDay(nextDay);
				}
			}
		}
		startTime = nextDay + " 00:00:00";
		endTime = nextDay + " 23:59:59";
		
		setAttr("nextDay", nextDay);
		setAttr("day", day);
		setAttr("space", space);
		setAttr("items", service.paginate(startTime, endTime, 2,space,getParaToInt(0, 1), 100));
		
		renderJson();
	}
}
