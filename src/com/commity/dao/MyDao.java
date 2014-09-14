package com.commity.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.commity.DataBaseUtil.DataBaseConnection;

public class MyDao {
	private static Connection con;
	private Statement stat;
	private ResultSet rs = null;

	
	public MyDao() {
		super();
		try {
			this.con = DataBaseConnection.getConn();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/*
	 * 登录
	 */	
	public List<Map<String,Object>> login(String userAccount ,String userPassword){
		List result = new ArrayList<Map<String,String>>();
		Map<String,Object> temp = new HashMap<String,Object>();
		try {			
			String sql = "select userID "
					+ "from user where userAccount= '" + userAccount +"'and userPassword = '" 
					+ userPassword  + "'";
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);	
			Integer userID;
			if(rs.next()){
				temp = new HashMap<String,Object>();
				userID = rs.getInt("userID");
				temp.put("ok", "登录成功");
				temp.put("userID", userID);
				result.add(temp);
			}else{
				temp = new HashMap<String,Object>();
				temp.put("error", "登录失败");
				result.add(temp);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}				
		return result;
	}
	
	/*
	 * 注册
	 */
	public List register(String userName , String userAccount,String userPassword){
		List result = new ArrayList<Map<String,Object>>();
		Map<String,Object> temp = new HashMap<String,Object>() ;
		try {			
			String sql = "select userID from user where userAccount = '"+ userAccount+ "'" ;
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);		
			if(rs.next()){
				temp.put("error", "账号已存在");
				result.add(temp);
				return result;
			}else{
				java.util.Date ud = new java.util.Date(); 
				java.sql.Date registerDate = new java.sql.Date(ud.getTime());
				sql = "INSERT INTO user (userName,userAccount,userPassword,registerDate) VALUES ( '" 
						+ userName +"','" + userAccount +"','" + userPassword +  "','" + registerDate+ "')";
				stat.executeUpdate(sql);
				temp.put("ok", "创建成功");
				result.add(temp);				
			}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return result;	
		}
	
	
	/*
	 * 取得推荐商品信息
	 */
	public List<Map<String,Object>> recommend(){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {			
			String sql = "select goodsName,goodsID,goodsImage from goods "
					+ "where goodsState=1 order by goodsSupport limit 10" ;
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			String goodsName;
			Integer goodsID;
			String goodsImage;
			while(rs.next()){
				temp = new HashMap<String,Object>();
				goodsID = rs.getInt("goodsID");
				goodsImage = rs.getString("goodsImage");
				goodsName = rs.getString("goodsName");
				temp.put("goodsID", goodsID);
				temp.put("goodsImage", goodsImage);
				temp.put("goodsName", goodsName);
				result.add(temp);				
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			}
		
		return result;	
	}
	
	/*
	 * 取得活动信息
	 */
	public List<Map<String,Object>> movement(Integer userid){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {			
			String sql = "select user.avater, movement.userID,movement.movementName,"
					+ "movement.tagetGoodsID, movement.time from movement "
					+ "left join user ON movement.userID = user.userID "
					+ "left join focuseduser on focuseduser.focusUserID=movement.userID "
					+ "where focuseduser.userID = " +userid;
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			String avater;
			Integer userID;
			String movementName;
			Integer tagetGoodsID;
			Date time ;
			while(rs.next()){
				temp = new HashMap<String,Object>();
				avater = rs.getString("avater");
				movementName = rs.getString("movementName");
				userID = rs.getInt("userID");
				time = rs.getDate("time");
				tagetGoodsID = rs.getInt("tagetGoodsID");
			
				temp.put("avater", avater);
				temp.put("movementName", movementName);
				temp.put("userID", userID);
				temp.put("time", time);
				temp.put("tagetGoodsID", tagetGoodsID);
				
				result.add(temp);
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			}
		
		if(result.isEmpty()){
			temp.put("error", "暂时没有动态");
		}
		return result;	
	}
	
	/*
	 * 赚钱
	 */
	public List<Map<String,Object>> makemoney(){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {			
			String sql = "select user.userID,user.userName,user.avater,seekinggoods.seekingTime,goods.goodsName,goods.goodsID from seekinggoods "
					+ "LEFT JOIN user on seekinggoods.userID=user.userID "
					+ "LEFT JOIN goods on seekinggoods.goodsID = goods.goodsID "
					+ "ORDER BY seekingTime LIMIT 0,20";

			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);

			Integer goodsID;
			int userID;
			String userName;
			String avater;
			String time1;
			String goodsName;
			while(rs.next()){
				temp = new HashMap<String,Object>();
				userID=rs.getInt("userID");
				userName=rs.getString("userName");
				goodsID = rs.getInt("goodsID");
				avater = rs.getString("avater");
				time1 = rs.getString("seekingTime");
				goodsName = rs.getString("goodsName");
				temp.put("userID", userID);
				temp.put("userName", userName);
				temp.put("avater", avater);
				temp.put("time", time1);
				temp.put("goodsName", goodsName);
				result.add(temp);				
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			}
		
		if(result.isEmpty()){
			temp.put("error", "暂时没有求购");
		}
		System.out.println(result);
		return result;	
	}
	
	/*
	 * 搜索商品
	 */	
	public List<Map<String,Object>> searchGoods(String searchName , Integer order){
		List result = new ArrayList<Map<String,Object>>();
		Map<String,Object> temp = new HashMap<String,Object>();
		String listOrder = "goodsSupport";		
//		switch (order) {
//		case SearchBean.GOOGDS_ORDER_BY_SUPPORT:
//			listOrder = "goodsSupport";
//			break;
//		case SearchBean.GOODS_BY_PRICE:
//			listOrder = "goodsPrice";
//			break;
//		case SearchBean.GOODS_ORDER_BY_SODINGDATE:
//			listOrder = "goodsStartSoldingDate";
//			break;
//		default:
//			break;
//		}
			
		if(listOrder != null){
			try {			
				String sql = "select goodsID, goodsName,goodsImage,goodsPrice,goodsSupport "
						+ "from goods where goodsState=1 and (goodsName like '%"
						+ searchName+ "%' or keyword like '%" + searchName + "%') order by " + listOrder
						+ " limit 0,100";
				stat = (Statement) con.createStatement();
				rs = stat.executeQuery(sql);	
				Integer goodsID;
				String goodsname;
				String goodsImage;
				Integer goodsSupport;
				Double goodsPrice;
				while(rs.next()){
					temp = new HashMap<String,Object>();;
					goodsID = rs.getInt("goodsID");
					goodsname = rs.getString("goodsName");
					goodsImage = rs.getString("goodsImage");
					goodsSupport = rs.getInt("goodsSupport");
					goodsPrice = rs.getDouble("goodsPrice");
					temp.put("goodsID", goodsID);
					temp.put("goodsname", goodsname);
					temp.put("goodsImage", goodsImage);
					temp.put("goodsSupport", goodsSupport);
					temp.put("goodsPrice", goodsPrice);
					result.add(temp);

				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}	
		if(result.isEmpty()){
			temp.put("errormes","没有找到您查找的商品");
			result.add(temp);
		}
		return result;
	}
	
	/*
	 * 搜索求购
	 */
	public List<Map<String,Object>> searchQiugou(String searchName , Integer order){
		List result = new ArrayList<Map<String,Object>>();
		Map<String,Object> temp = new HashMap<String,Object>();
		String listOrder = "seekingTime";				
		try {			
			String sql = "select goods.goodsID, goods.goodsName, goods.goodsImage, seekinggoods.seekingTime "
					+ "from goods LEFT JOIN seekinggoods on seekinggoods.goodsID=goods.goodsID  "
					+ "where goodsName like '%" + searchName+ "%'  order by " + listOrder
					+ " limit 0,100";
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			Integer goodsID;
			String goodsname;
			String goodsImage;
				
			while(rs.next()){
				temp = new HashMap<String,Object>() ;
				goodsID = rs.getInt("goodsID");
				goodsname = rs.getString("goodsName");
				goodsImage = rs.getString("goodsImage");
				temp.put("goodsID", goodsID);
				temp.put("goodsname", goodsname);
				temp.put("goodsImage", goodsImage);
				result.add(temp);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
		if(result.isEmpty()){
			temp.put("errormes","没有找到您查找的商品");
			result.add(temp);
		}
		return result;
	}
	
	/*
	 * 搜索用户
	 */
	public List<Map<String,Object>> searchUser(String searchName , Integer order){
		List result = new ArrayList<Map<String,Object>>();	
		Map<String,Object> temp = new HashMap<String,Object>() ;
		String listOrder = "userName";
		
//		switch (order) {
//		case SearchBean.USER_ORDER_BY_USERNAME:
//			listOrder = "userName";
//			break;
//		case SearchBean.USER_ORDER_BY_REGISTERDATE:
//			listOrder = "registerDate";
//			break;
//		default:
//			System.out.println("error!");
//			break;
//		}
		
		if(listOrder != null){
			try {			
				String sql = "select userID,username from user where username like '%"
						+ searchName+ "%' order by " + listOrder;
				stat = (Statement) con.createStatement();
				rs = stat.executeQuery(sql);
				Integer userID;
				String username;
				while(rs.next()){
					temp = new HashMap<String,Object>(); ;
					userID = rs.getInt("userID");
					username = rs.getString("username");
					temp.put("userID", userID);
					temp.put("username", username);
					result.add(temp);
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
		if(result.isEmpty()){
			temp.put("errormes","没有找到您查找的用户");
			result.add(temp);
		}
		return result;		
	}
	
	/*
	 * 求购详情
	 */
	public List<Map<String,Object>> seekdetail(Integer goodsIDINS){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {			
			String sql = "SELECT goods.goodsID, user.avater,seekinggoods.seekingTime,user.userName,"
					+ "user.phoneNum FROM seekinggoods LEFT JOIN user on seekinggoods.userID = user.userID "
					+ "LEFT JOIN goods on seekinggoods.goodsID = goods.goodsID "
					+ "where seekinggoods.goodsID = " + goodsIDINS;

			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			Integer goodsID;
			String avater;
			java.sql.Date time1;
			String userName;
			String phoneNum;	
			while(rs.next()){
				temp = new HashMap<String,Object>();
				goodsID = rs.getInt("goodsID");
				time1 = rs.getDate("seekingTime");
				userName = rs.getString("userName");
				phoneNum = rs.getString("phoneNum");
				temp.put("goodsID", goodsID);
				temp.put("seekingTime", time1);
				temp.put("userName", userName);
				temp.put("phoneNum", phoneNum);
				result.add(temp);				
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			
		}
		if(result.isEmpty()){
			temp.put("error","没有该求购");
			result.add(temp);
		}
		return result;	
	}
	
	/*
	 * 收藏商品
	 * 不产生movement
	 */
	public List<Map<String,Object>> collectgoods(Integer userIDINS,Integer goodsIDINS){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {	
			stat = (Statement) con.createStatement();
			
			String sql = "select collectedGoodsID from collectedgoods where userID="
					+ userIDINS + " and goodsID = " + goodsIDINS;
			rs =stat.executeQuery(sql);
			if(rs.next()){
				temp.put("error", "请勿重复收藏");
				result.add(temp);
				return result;
			}
			sql = "INSERT INTO collectedgoods (userID,goodsID) VALUES("
					+ userIDINS + "," + goodsIDINS + ")";

			stat.executeUpdate(sql);	
		}catch (Exception e) {
			// TODO: handle exception
			temp.put("error", "发生未知错误");	
			result.add(temp);
			return result;	
		}
		temp.put("ok", "ok");
		result.add(temp);
		return result;	
	}
	
	/*
	 * 分享商品
	 * 产生movement
	 */
	public List<Map<String,Object>> sharegoods(Integer userID , Integer goodsID){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {			
			String sql = "select movementID  from movement where userID ="+ userID  
					+" and tagetGoodsID =" + goodsID ;

			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			
			if(rs.next()){
				temp.put("error", "请勿重复分享");
				result.add(temp);
				return result;
			}
			java.util.Date ud = new java.util.Date(); 
			java.sql.Date time = new java.sql.Date(ud.getTime());
			sql = "INSERT INTO movement(userID,tagetGoodsID,time,movementName) VALUES( "
					+ userID +"," + goodsID +"," + time + "," + "'分享了'" + ")";
			stat.executeUpdate(sql);
			temp.put("ok", "已成功分享");
			result.add(temp);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			
		}
		return result;	
	}

	/*
	 * 关注用户
	 */
	public List<Map<String,Object>> focususer(Integer userID , Integer focusUserID){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {			
			String sql = "select focusedUserID "
					+ " from focuseduser where userID = "+ userID  + " and focusUserID = " 
					+ focusUserID ;

			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			
			if(rs.next()){
				Integer id = rs.getInt("focusedUserID");
				sql = "delete from focusedUser where focusedUserID =" + id ;
				stat.executeUpdate(sql);
				temp.put("ok", "已经取消关注");
				result.add(temp);
				return result;
			}
			
			sql = "INSERT INTO focusedUser (userID,FocusUserID) VALUES ( " 
			+ userID + "," + focusUserID +")";
			stat.executeUpdate(sql);
			temp.put("ok", "已成功关注");
			result.add(temp);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			
		}
		return result;	
	}
	
	
	/*
	 * 我的信息
	 */
	public List<Map<String,Object>> myinfo(Integer userID){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {			
			String sql = "select userName, fans , avater ,sex"
					+ " from user where userID = "+ userID;

			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			String userName;
			Integer fans;
			String avater;
			Integer sex;	
			while(rs.next()){
				temp = new HashMap<String,Object>();				
				userName = rs.getString("userName");
				fans = rs.getInt("fans");
				avater = rs.getString("avater");
				sex = rs.getInt("sex");
				temp.put("userName", userName);
				temp.put("fans", fans);
				temp.put("avater", avater);
				temp.put("sex", sex);
				result.add(temp);				
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			
		}
		if(result.isEmpty()){
			temp.put("error","没有找到该用户");
			result.add(temp);
		}
		return result;	
	}

	/*
	 * 获取商品留言
	 */
	public List<Map<String,Object>> getNews(Integer goodsID){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {			
			String sql = "select news.newsDetail,news.newsDate, user.userName ,user.userID from news "
					+ "LEFT JOIN user ON news.newsSenderID = user.userID "
					+ "where news.goodsID = " + goodsID;

			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			String newsDetail;
			String userName;
			java.sql.Date newsDate ;
			Integer userID;
			while(rs.next()){
				temp = new HashMap<String,Object>();				
				newsDetail = rs.getString("newsDetail");
				userName = rs.getString("userName");
				newsDate = rs.getDate("newsDate");
				userID = rs.getInt("userID");
				temp.put("newsDetail", newsDetail);
				temp.put("newsDate", newsDate);
				temp.put("userName", userName);
				temp.put("userID", userID);
				result.add(temp);				
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			
		}
		if(result.isEmpty()){
			temp.put("error","没有找到该用户");
			result.add(temp);
		}
		return result;	
	}
	
	/*
	 * 发布留言
	 */
	public List<Map<String,Object>> releaseNews(Integer userID,Integer goodsID ,String newsDetail){
		List result = new ArrayList<Map<String,Object>>();
		Map temp = new HashMap<String, Object>();
		try {		
			
			java.util.Date ud = new java.util.Date(); 
			java.sql.Date newsDate = new java.sql.Date(ud.getTime());
			String sql = "INSERT INTO news(newsSenderID,goodsID,newsDetail,newsDate)"
					+ " VALUES( " + userID +","  +goodsID +  ",'" + newsDetail + "'," + newsDate + ")";

			stat = (Statement) con.createStatement();
			stat.executeUpdate(sql);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			temp.put("error","发生未知错误");
			result.add(temp);
			return result;
		}
		temp.put("ok", "成功");
		result.add(temp);
		return result;	
	}

}
