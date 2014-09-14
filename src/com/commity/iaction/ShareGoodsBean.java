package com.commity.iaction;

import java.util.HashMap;
import java.util.List;
import net.sf.json.JSONException;
import com.alibaba.fastjson.JSON;
import com.commity.dao.MyDao;
import com.commity.manager.IAction;


public class ShareGoodsBean implements IAction{;
	public String execute(String params) throws JSONException {
		HashMap<String,Object> map = JSON.parseObject(params, HashMap.class);
		Integer userID = (Integer)map.get("searchType");
		Integer goodsID = (Integer)map.get("goodsID");

		MyDao mydao = new MyDao();
		List result = mydao.sharegoods(userID, goodsID);
		
		String jsonString = JSON.toJSONString(result);
		return jsonString;
	}	
}
