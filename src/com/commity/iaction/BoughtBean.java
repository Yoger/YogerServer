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

public class BoughtBean implements IAction {

	public String execute(String params) throws JSONException {
		String response=null;
		List<Map<String,Object>> list=null;
		int type;
		int userID;
		if (params == null){
			response="[{\"error\":\"no json params\"}]";
		    return response;
		}
		try {
			JSONObject obj = JSONObject.fromObject(params);
			userID=obj.getInt("userID");
			type=obj.getInt("type");		
		} catch (Exception ex) {
			throw new JSONException("无效json");
		}
		
		try {
			Connection con = DataBaseConnection.getConn();
			DaoOperator dao = new DaoOperator(con);
            switch(type){
            case 1:
            	list=dao.getSoldingGoods(userID);
            	break;
            case 2:
            	list=dao.getAuditedGoods(userID);
            	break;
            case 3:
            	list=dao.getSoldgoods(userID);
            	System.out.println("3");
            	break;	
            default:
            	response="[{\"error\":\"type类型错误\"}]";
            }
			
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
