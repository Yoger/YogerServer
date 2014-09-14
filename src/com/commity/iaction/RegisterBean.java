package com.commity.iaction;

import java.util.HashMap;
import java.util.List;
import net.sf.json.JSONException;
import com.alibaba.fastjson.JSON;
import com.commity.dao.MyDao;
import com.commity.manager.IAction;

public class RegisterBean  implements IAction{

	public String execute(String params) throws	JSONException {
		// TODO Auto-generated method stub
		HashMap<String,String> map = JSON.parseObject(params, HashMap.class);
		String username = map.get("userName");
		String userAccount = map.get("userAccount");
		String password = map.get("userPassword");
		
		MyDao mydao = new MyDao();
		
		List result = mydao.register(username, userAccount,password);
		
		String jsonString = JSON.toJSONString(result);
		return jsonString;
	}
	
}
