package com.commity.iaction;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.commity.dao.MyDao;
import com.commity.manager.IAction;

public class LoginBean implements IAction {

	public String execute(String params) throws JSONException {
		HashMap<String,Object> map = JSON.parseObject(params, HashMap.class);
		String userAccount = (String) map.get("userAccount");
		String userPassword = (String) map.get("userPassword");
		MyDao mydao = new MyDao();
		List result = mydao.login(userAccount, userPassword);
		
		System.out.print(result);
		
		String jsonString = JSON.toJSONString(result);
		System.out.print(jsonString);
		return jsonString;
	}
}
