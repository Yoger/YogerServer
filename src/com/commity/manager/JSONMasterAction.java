package com.commity.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONException;

public class JSONMasterAction extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		
		System.out.println("来了");
		String uri = req.getRequestURI();
		String key  = uri.substring(1,uri.lastIndexOf("."));		
		String json = "";
	
		BufferedReader is = new BufferedReader(new InputStreamReader(req.getInputStream(),"UTF-8"));
		String line = "";
     
		while((line = is.readLine()) != null) {
			json += line;
		}
	//	json=req.getParameter("params");      ///////////////////////////////////////
		System.out.println(""+json);
		BeanManager manager = new BeanManager();
		String className = manager.getClass(key);
		IAction action = manager.createAction(className);
		try {

			String response = action.execute(json);	
			PrintWriter pw = resp.getWriter();
			pw.println(response);
			pw.flush();
			pw.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}