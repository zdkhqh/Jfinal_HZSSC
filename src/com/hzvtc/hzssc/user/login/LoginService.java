package com.hzvtc.hzssc.user.login;

import com.hzvtc.hzssc.model.Students;

public class LoginService {
	private static final Students dao = new Students().dao();
	
	public Students searchByStuId(int stuId) {
		return dao.findById(stuId);
	}

}
