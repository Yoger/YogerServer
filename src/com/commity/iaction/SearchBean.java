package com.commity.iaction;


import java.util.HashMap;
import java.util.List;
import net.sf.json.JSONException;
import com.alibaba.fastjson.JSON;
import com.commity.dao.MyDao;
import com.commity.manager.IAction;

public class SearchBean implements IAction{;
	public static final int GOOGDS_ORDER_BY_SUPPORT = 0x0001;
	public static final int GOODS_BY_PRICE = 0x0002;
	public static final int GOODS_ORDER_BY_SODINGDATE = 0x0003;
	public static final int USER_ORDER_BY_USERNAME = 0x0004;
	public static final int USER_ORDER_BY_REGISTERDATE = 0x0005;
	public static final int SEARCH_GOODS = 0x0006;
	public static final int SEARCH_USER = 0x0007;
	public static final int SEARCH_QIUGOU = 0x0008;
		
	public String execute(String params) throws JSONException {
		HashMap<String,Object> map = JSON.parseObject(params, HashMap.class);
		int searchType = Integer.parseInt((String) map.get("searchType"));
		String searchWord = (String) map.get("searchWord");
		int order = Integer.parseInt((String) map.get("order"));
		
		MyDao searcher = new MyDao();
		List result;
		switch (searchType) {
		case SEARCH_GOODS:
			result = searcher.searchGoods(searchWord,order);
			break;
		case SEARCH_USER:
			result = searcher.searchUser(searchWord,order);
			break;
		case SEARCH_QIUGOU:
			result = searcher.searchQiugou(searchWord, order);
		default:
			result = null;
			break;
		}
		
		String jsonString = JSON.toJSONString(result);
		return jsonString;
	}

	
}
