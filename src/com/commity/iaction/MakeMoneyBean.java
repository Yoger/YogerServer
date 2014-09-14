package com.commity.iaction;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import com.alibaba.fastjson.JSON;
import com.commity.dao.MyDao;
import com.commity.manager.IAction;

public class MakeMoneyBean implements IAction{

	public String execute(String params) throws JSONException {
		// TODO Auto-generated method stub
		MyDao mydao = new MyDao();
		List result = mydao.makemoney();
		
		String jsonString = JSON.toJSONString(result);
		
        //JSONArray ja = JSONArray.fromObject(result);
        //String response=ja.toString();
     //   System.out.println("response:"+response);
        
    	System.out.println(jsonString);
		return jsonString;
	}

}
