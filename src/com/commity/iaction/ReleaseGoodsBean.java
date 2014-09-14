package com.commity.iaction;

import java.sql.Connection;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.commity.DataBaseUtil.DataBaseConnection;
import com.commity.bean.Goods;
import com.commity.dao.DaoOperator;
import com.commity.manager.IAction;

public class ReleaseGoodsBean implements IAction {

	public String execute(String params) throws JSONException {
		String response=null;
		if (params == null){
			response="{\"error\":\"no json params\"}";
			return response;
		}		
		Goods goods = null;
		try {
			JSONObject obj = JSONObject.fromObject(params);
			goods = (Goods) JSONObject.toBean(obj, Goods.class);
		} catch (Exception ex) {
			throw new JSONException("无效json");
		}
		
		try {
			Connection con = DataBaseConnection.getConn();
			DaoOperator dao = new DaoOperator(con);
			response=dao.releaseGoods(goods);
			DataBaseConnection.releaseConn(con);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}

}
