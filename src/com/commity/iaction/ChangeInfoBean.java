package com.commity.iaction;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;

import sun.misc.BASE64Decoder;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.commity.DataBaseUtil.DataBaseConnection;
import com.commity.bean.Info;
import com.commity.dao.DaoOperator;
import com.commity.manager.IAction;

public class ChangeInfoBean implements IAction {
	private String picUrl="E:/学习/MyEclipse10/CommityServer/WebRoot/pic/";
	public String execute(String params) throws JSONException {
		String response=null;
		if (params == null){
			response="{\"error\":\"no json params\"}";
			return response;
		}		
		Info info = null;
		try {
			JSONObject obj = JSONObject.fromObject(params);
			info = (Info) JSONObject.toBean(obj, Info.class);
		} catch (Exception ex) {
			throw new JSONException("无效json");
		}
		
		try {
			Connection con = DataBaseConnection.getConn();
			DaoOperator dao = new DaoOperator(con);
			picUrl+=info.getUserID()+".jpg";
			generateImage(info.getAvater(),picUrl);
			response=dao.changeinfo(info.getUserID(),picUrl,info.getPhoneNum(),info.getEmail());
			DataBaseConnection.releaseConn(con);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return response;
	}
	public static boolean generateImage(String imgStr,String imgFile)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		 if (imgStr == null) // 图像数据为空
		 return false;
		 BASE64Decoder decoder = new BASE64Decoder();
		 try {
		 // Base64解码
		 byte[] b = decoder.decodeBuffer(imgStr);
		 for (int i = 0; i < b.length; ++i) {
		 if (b[i] < 0) {// 调整异常数据
		 b[i] += 256;
		 }
		 }
		 // 生成jpeg图片
		 String imgFilePath = imgFile;// 新生成的图片
		 OutputStream out = new FileOutputStream(imgFilePath);
		 out.write(b);
		 out.flush();
		 out.close();
		 return true;
		 } catch (Exception e) {
		 throw e;
		 }
   }
}
