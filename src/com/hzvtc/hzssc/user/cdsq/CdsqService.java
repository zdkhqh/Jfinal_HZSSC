package com.hzvtc.hzssc.user.cdsq;

import com.hzvtc.hzssc.model.Activity;
import com.jfinal.plugin.activerecord.Page;

public class CdsqService {
	private static final Activity dao = new Activity();
	
	public Page<Activity> paginate(String startTime,
			String endTime, Integer state,Integer space,int pageNumber, int pageSize) {
		//System.out.println("\n"+"select a.*,s.name "+"from activity a left join students s on a.stu_id = s.student_id where a.state = "+state+" and a.space = "+space+" and (a.start_time >= '"+startTime+"' and a.start_time <= '"+endTime+"' ) order by a.start_time asc");
		return dao.paginate(pageNumber, pageSize, "select a.*,s.name ", "from activity a left join students s on a.stu_id = s.student_id where a.state = "+state+" and a.space = "+space+" and (a.start_time >= '"+startTime+"' and a.start_time <= '"+endTime+"' ) order by a.start_time asc");
	}

	
	public int save(Activity activity){
		activity.save();
		return 1;
	}
}
