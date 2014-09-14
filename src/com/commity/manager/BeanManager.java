package com.commity.manager;

import java.io.IOException;
import java.util.Properties;

public class BeanManager {

	private Properties p = null;

	public BeanManager() {
		p = new Properties();
		try {
			p.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("bean.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getClass(String key) {
		return p.getProperty(key);
	}
	
	public IAction createAction(String className) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			Class cls = loader.loadClass(className);
			return (IAction)cls.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
		
	}
}
