package com.commity.iaction;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.commity.DataBaseUtil.DataBaseConnection;
import com.commity.dao.DaoOperator;
import com.commity.manager.IAction;

public class CollectionBean implements IAction {

	public String execute(String params) throws JSONException {
		String response=null;
		int id;
		if (params == null){
			response="{\"error\":\"no json params\"}";
		}
			
		try {
			JSONObject obj = JSONObject.fromObject(params);
			id=obj.getInt("userID");
			
		} catch (Exception ex) {
			throw new JSONException("无效json");
		}	
		try {
			Connection con = DataBaseConnection.getConn();
			DaoOperator dao = new DaoOperator(con);
			List<Map<String,Object>> list=dao.getCollection(id);
		    JSONArray ja = JSONArray.fromObject(list);
            response=ja.toString();
			DataBaseConnection.releaseConn(con);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}

}
