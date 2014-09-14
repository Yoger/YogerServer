package com.commity.iaction;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import com.commity.DataBaseUtil.DataBaseConnection;
import com.commity.bean.Classify;
import com.commity.dao.DaoOperator;
import com.commity.manager.IAction;

public class ClassifyBean implements IAction{

	public String execute(String params) throws JSONException {
		String response=null;
		if (params == null){
			response="{\"error\":\"no json params\"}";
		}		
		Classify classify = null;
		try {
			JSONObject obj = JSONObject.fromObject(params);
			classify = (Classify) JSONObject.toBean(obj, Classify.class);
		} catch (Exception ex) {
			throw new JSONException("无效json");
		}
		
		try {
			Connection con = DataBaseConnection.getConn();
			DaoOperator dao = new DaoOperator(con);
			List<Map<String,Object>> list=null;
			if(classify.getGoodsType2().equals("all"))
				list=dao.getAllClassifyResult(classify.getGoodsType());
			else
				list=dao.getClassifyResult(classify.getGoodsType(),classify.getGoodsType2());
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
