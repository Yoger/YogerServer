package com.commity.iaction;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.commity.dao.MyDao;
import com.commity.manager.IAction;

public class MovementBean implements IAction{

	public String execute(String params) throws JSONException {
		HashMap<String,Object> map = JSON.parseObject(params, HashMap.class);
		Integer userid = (Integer) map.get("userAccount");
		MyDao mydao = new MyDao();
		List result = mydao.movement(userid);
		
		String jsonString = JSON.toJSONString(result);
		return jsonString;
	}
	
}