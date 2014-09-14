package com.commity.iaction;

import java.util.List;

import net.sf.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.commity.dao.MyDao;
import com.commity.manager.IAction;

public class RecommendBean implements IAction{

	public String execute(String params) throws JSONException {
		MyDao mydao = new MyDao();
		List result = mydao.recommend();
		
		String jsonString = JSON.toJSONString(result);
		return jsonString;
	}
	
}
