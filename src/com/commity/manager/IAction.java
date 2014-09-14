package com.commity.manager;

import net.sf.json.JSONException;


public interface IAction {
	
	public String execute(String params) throws JSONException;
}
