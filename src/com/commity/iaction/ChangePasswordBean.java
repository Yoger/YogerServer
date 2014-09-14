package com.commity.iaction;

import java.sql.Connection;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.commity.DataBaseUtil.DataBaseConnection;
import com.commity.bean.Password;
import com.commity.dao.DaoOperator;
import com.commity.manager.IAction;

public class ChangePasswordBean implements IAction{
	
	public String execute(String params) throws JSONException {
		// TODO Auto-generated method stub
		String response=null;
		if (params == null){
			response="[{\"error\":\"no json params\"}]";
		}	
		Password psw = null;
		try {
			JSONObject obj = JSONObject.fromObject(params);
			psw = (Password) JSONObject.toBean(obj, Password.class);
		} catch (Exception ex) {
			throw new JSONException("无效jsonʽ");
		}
		
		try {
			Connection con = DataBaseConnection.getConn();
			DaoOperator dao = new DaoOperator(con);
			response=dao.getpsw(psw.getUserID());
			if(psw.getOldpwd().equals(response))
				response=dao.updatepsw(psw.getUserID(), psw.getNewpwd());
			else
				response="[{\"error\":\"oldpsw error\"}]";
			DataBaseConnection.releaseConn(con);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}

}

