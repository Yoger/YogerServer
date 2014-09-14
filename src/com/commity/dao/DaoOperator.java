package com.commity.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.commity.bean.Goods;
import com.commity.bean.Seek;

public class DaoOperator {

	private Connection con;

	private Statement stat;
	private ResultSet rs = null;
    Map<String,Object> map=null;
    List<Map<String,Object>> list=null;
	private String response=null;
	int result;


	public DaoOperator(Connection con) {
		this.con = con;
	}

	public List<Map<String,Object>> getSoldingGoods(int id) {    //姝ｅ湪鍑哄敭
		// TODO Auto-generated method stub
		list=new ArrayList<Map<String,Object>>();
		try {			
			String sql = "select goods.goodsID,goods.goodsImage,goods.goodsName,goods.goodsPrice,soldinggoods.soldingTime" +
					" from soldinggoods left join goods on goods.goodsID=soldinggoods.goodsID where soldinggoods.userID="+id+"";
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
            while(rs.next()){ 	
            	map=new HashMap<String,Object>();
            	map.put("goodsID", rs.getInt(1));
            	map.put("goodsImage",rs.getString(2));
           	    map.put("goodsName",rs.getString(3));
           	    map.put("goodsPrice",rs.getDouble(4));
           	    map.put("soldingTime",rs.getString(5));
                list.add(map);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty()){
			map=new HashMap<String,Object>();
        	map.put("error","未找到有效内容");
        	list.add(map);
		}
		return list;
	}
	public List<Map<String,Object>> getAuditedGoods(int id) {          //瀹℃牳涓晢鍝�
		// TODO Auto-generated method stub
		list=new ArrayList<Map<String,Object>>();
		try {			
			String sql = "select goods.goodsID,goods.goodsImage,goods.goodsName,goods.goodsPrice,auditinggoods.time" +
					" from auditinggoods left join goods on goods.goodsID=auditinggoods.goodsID where auditinggoods.userID="+id+"";
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
            while(rs.next()){ 	
            	map=new HashMap<String,Object>();
            	map.put("goodsID", rs.getInt(1));
            	map.put("goodsImage",rs.getString(2));
           	    map.put("goodsName",rs.getString(3));
           	    map.put("goodsPrice",rs.getDouble(4));
           	    map.put("time",rs.getString(5));
                list.add(map);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty()){
			map=new HashMap<String,Object>();
        	map.put("error","未找到有效内容");
        	list.add(map);
		}
		return list;
	}
	public List<Map<String,Object>> getSoldgoods(int id) { 
		// TODO Auto-generated method stub
		list=new ArrayList<Map<String,Object>>();
		try {			
			String sql = "select goods.goodsID,goods.goodsImage,goods.goodsName,goods.goodsPrice,soldgoods.soldTime" +
					" from soldgoods left join goods on goods.goodsID=soldgoods.goodsID where soldgoods.userID="+id+"";
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
            while(rs.next()){ 	
            	map=new HashMap<String,Object>();
            	map.put("goodsID", rs.getInt(1));
            	map.put("goodsImage",rs.getString(2));
           	    map.put("goodsName",rs.getString(3));
           	    map.put("goodsPrice",rs.getDouble(4));
           	    map.put("soldTime",rs.getString(5));
                list.add(map);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty()){
			map=new HashMap<String,Object>();
        	map.put("error","未找到有效内容");
        	list.add(map);
		}
		return list;
	}
	public String  changeinfo(int userID,String avatar,String phone,String email) {
		// TODO Auto-generated method stub
		String response=null;
		try {			
			String sql = "update user set avater=\""+avatar+"\",phoneNum=\""+phone+"\",email=\""+email+"\" where userID="+userID;
			stat = (Statement) con.createStatement();
			result=stat.executeUpdate(sql);
			if(result==1)
		 	    response="{\"ok\":\"changeinfo success\"}";
			else
				response="{\"error\":\"changeinfo error\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	public String getpsw(int userID){
		try {			
			 String sql ="select userPassword from user where userID= "+userID;
		     stat = (Statement) con.createStatement();
		     rs=stat.executeQuery(sql);
		     rs.next();
		     response=rs.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	public String updatepsw(int userID,String psw){
		try {			
			String sql = "update user set userPassword=\""+psw+"\" where userID="+userID;
			stat = (Statement) con.createStatement();
			result=stat.executeUpdate(sql);
			if(result==1)
				response="[{\"ok\":\"changepsw success\"}]";    

			else
				response="[{\"error\":\"changepsw error\"}]"; 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	public List<Map<String,Object>> getAllClassifyResult(String bigclassify){
		list=new ArrayList<Map<String,Object>>();
		try {	
			String sql = "select goodsID,goodsImage,goodsName,goodsPrice,goodsSupport from goods" +
					" where goodsType='"+bigclassify+"' and goodsState = 1";
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
            while(rs.next()){ 	
            	map=new HashMap<String,Object>();
            	map.put("goodsID", rs.getInt(1));
            	map.put("goodsImage",rs.getString(2));
           	    map.put("goodsName",rs.getString(3));
           	    map.put("goodsPrice",rs.getDouble(4));
           	    map.put("goodsSupport",rs.getInt(5));
                list.add(map);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty()){
			map=new HashMap<String,Object>();
        	map.put("error","未找到有效内容");
        	list.add(map);
		}
		return list;
	}
	public  List<Map<String,Object>>  getClassifyResult(String bigclassify,String smallclassify){
		list=new ArrayList<Map<String,Object>>();
		try {	
			String sql = "select goodsID,goodsImage,goodsName,goodsPrice,goodsSupport from  goods " +
					" where goodsType='"+bigclassify+"'"
					+"and goodsType2='"+smallclassify+"' and goodsState=1 ";
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
            while(rs.next()){ 
            	map=new HashMap<String,Object>();
            	map.put("goodsID", rs.getInt(1));
            	map.put("goodsImage",rs.getString(2));
           	    map.put("goodsName",rs.getString(3));
           	    map.put("goodsPrice",rs.getDouble(4));
           	    map.put("goodsSupport",rs.getInt(5));
                list.add(map);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty()){
			map=new HashMap<String,Object>();
        	map.put("error","未找到有效内容");
        	list.add(map);
		}
		return list;
	}
	public List<Map<String,Object>> getCollection(int id){
		list=new ArrayList<Map<String,Object>>();
		try {	
			String sql = "select goods.goodsID,goods.goodsImage,goods.goodsName,goods.goodsPrice" +
					" from collectedgoods left join goods on goods.goodsID=collectedgoods.goodsID where collectedgoods.userID="+id;
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
            while(rs.next()){ 	
            	map=new HashMap<String,Object>();
            	map.put("goodsID", rs.getInt(1));
            	map.put("goodsImage",rs.getString(2));
           	    map.put("goodsName",rs.getString(3));
           	    map.put("goodsPrice",rs.getDouble(4));
                list.add(map);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty()){
			map=new HashMap<String,Object>();
        	map.put("error","未找到有效内容");
        	list.add(map);
		}
		return list;
	}
	public List<Map<String,Object>> getFocus(int id){
		list=new ArrayList<Map<String,Object>>();
		try {	
			String sql = "select user.userID,user.avater,user.userName" +
					" from focuseduser left join user on user.userID=focuseduser.focusUserID where focuseduser.userID="+id+"";
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);   
            while(rs.next()){ 	
              	map=new HashMap<String,Object>();
            	map.put("userID", rs.getInt(1));
            	map.put("avater",rs.getString(2));
           	    map.put("userName",rs.getString(3));
                list.add(map);            
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.isEmpty()){
			map=new HashMap<String,Object>();
			map.put("error", "未找到有效内容");	
			list.add(map);
		}
		return list;
	}
	public List<Map<String,Object>> getgoodsDetail(int gID){
		list=new ArrayList<Map<String,Object>>();
		try {	
			String sql = "select goodsID,goodsImage,goodsName,goodsPrice," +
					"goodsType,goodsType2,goodsSupport,goodsdetail,goodsDetailImage1," +
					"goodsDetailImage2,goodsDetailImage3 from  goods where goodsID="+gID;
			stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			rs.next();
	    	map=new HashMap<String,Object>();
	    	map.put("goodsID", rs.getInt(1));
	    	map.put("goodsImage",rs.getString(2));
       	    map.put("goodsName",rs.getString(3));
            map.put("goodsPrice",rs.getDouble(4));
    	    map.put("goodsType",rs.getString(5));
       	    map.put("goodsType2",rs.getString(6));
        	map.put("goodsSupport",rs.getInt(7));
    	    map.put("goodsdetail",rs.getString(8));
       	    map.put("goodsDetailImage1",rs.getString(9));
       	    map.put("goodsDetailImage2",rs.getString(10));
    	    map.put("goodsDetailImage3",rs.getString(11));  
     	    sql = "select user.userName,user.phoneNum from soldinggoods left join user on soldinggoods.userID=user.userID " +
    	    		"where soldinggoods.goodsID="+gID;
     	    stat = (Statement) con.createStatement();
			rs = stat.executeQuery(sql);
			rs.next();
			map.put("userName", rs.getString(1));
			map.put("phoneNum", rs.getString(2));
    	    list.add(map); 
          
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public String releaseGoods(Goods g){
		int userid=g.getUserID();
		String gName=g.getGoodsName();
		Double gPrice=g.getGoodsPrice();
		String gType=g.getGoodsType();
		String gType2=g.getGoodsType2();
		int gQuantity=g.getGoodsQuantity();
		String gDetail=g.getGoodsDetail();
		String gImage=g.getGoodsImage();
		String gDetailimg1=g.getGoodsDetailImage1();
		String gDetailimg2=g.getGoodsDetailImage2();
		String gDetailimg3=g.getGoodsDetailImage3();
		String gKeyword=g.getGoodsKeyword();
		try {	
		    ///table goods
			String sql = "insert into goods (goodsName,goodsPrice,goodsType,goodsType2,goodsQuantity,goodsDetail,goodsState,goodsImage,goodsDetailImage1," +
					"goodsDetailImage2,goodsDetailImage3,keyword) values ('"+gName+"',"+gPrice+",'"+gType+"','"+gType2+"',"+gQuantity+",'"+gDetail+"'," +
					"2,'"+gImage+"','"+gDetailimg1+"','"+gDetailimg2+"','"+gDetailimg3+"','"+gKeyword+"') ";
			stat = (Statement) con.createStatement();
			result=stat.executeUpdate(sql);
			if(result==1)
		 	    response="[{\"ok\":\"insert goods success\"}]";
			else{
				response="[{\"error\":\"insert goods error\"}]";
				return response;
			}
			
			sql="SELECT LAST_INSERT_ID()";    //找到goodsID
			stat = (Statement) con.createStatement();
			rs=stat.executeQuery(sql);
			rs.next();
			int goodsid = rs.getInt(1);    
			
		    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
		    String date=matter1.format(new Date());
		    
		    ////table auditinggoods
			sql="insert into auditinggoods (userID,goodsID,time) values ("+userid+","+goodsid+",'"+date+"')";
			stat = (Statement) con.createStatement();
			result=stat.executeUpdate(sql);
			if(result==1)
		 	    response="[{\"ok\":\"insert auditinggoods success\"}]";
			else{
				response="[{\"error\":\"insert auditinggoods error\"}]";
			}
			
			sql="select userName from user where userID= "+userid;
			stat = (Statement) con.createStatement();
			rs=stat.executeQuery(sql);
			rs.next();
			String username = rs.getString(1);   
			String move=username+"发布了一件商品，商品名称："+gName+",商品价格："+gPrice;
			System.out.println(move);
			
			/////table movement 
			sql="insert into movement (userID,tagetGoodsID,time,movementName) values ("+userid+","+goodsid+",'"+date+"','"+move+"')";
			stat = (Statement) con.createStatement();
			result=stat.executeUpdate(sql);
			if(result==1)
		 	    response="[{\"ok\":\"releasegoods success\"}]";
			else{
				response="[{\"error\":\"releasegoods error\"}]";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	public String releaseSeek(Seek s){
		int result;
		int userid=s.getUserID();
		String gName=s.getGoodsName();
		String gType=s.getGoodsType();
		String gType2=s.getGoodsType2();
		int gQuantity=s.getGoodsQuantity();
		String gDetail=s.getGoodsDetail();
		try {	
			String sql = "insert into goods (goodsName,goodsType,goodsType2,goodsQuantity,goodsDetail,goodsState) values" +
					"('"+gName+"','"+gType+"','"+gType2+"',"+gQuantity+",'"+gDetail+"',6) ";
			stat = (Statement) con.createStatement();
			result=stat.executeUpdate(sql);
			if(result==1)
		 	    response="[{\"ok\":\"insert seek success\"}]";
			else{
				response="[{\"error\":\"insert seek error\"}]";
				return response;
			}
			
			sql="SELECT LAST_INSERT_ID()";    //找到goodsID
			stat = (Statement) con.createStatement();
			rs=stat.executeQuery(sql);
			rs.next();
			int goodsid = rs.getInt(1);    
			
		    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
		    String date=matter1.format(new Date());

			sql="insert into auditinggoods (userID,goodsID,time) values ("+userid+","+goodsid+",'"+date+"')";
			stat = (Statement) con.createStatement();
			result=stat.executeUpdate(sql);
			if(result==1)
		 	    response="[{\"ok\":\"insert auditinggoods success\"}]";
			else{
				response="[{\"error\":\"insert auditinggoods error\"}]";
			}
			
			sql="select userName from user where userID= "+userid;
			stat = (Statement) con.createStatement();
			rs=stat.executeQuery(sql);
			rs.next();
			String username = rs.getString(1);   
			String move=username+"发布了一个求购："+gName;
			System.out.println(move);
				
			sql="insert into movement (userID,tagetGoodsID,time,movementName) values ("+userid+","+goodsid+",'"+date+"','"+move+"')";
			stat = (Statement) con.createStatement();
			result=stat.executeUpdate(sql);
			if(result==1)
		 	    response="[{\"ok\":\"releaseseek success\"}]";
			else{
				response="[{\"error\":\"releaseseek error\"}]";
			}
     
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return response;
	}

}
