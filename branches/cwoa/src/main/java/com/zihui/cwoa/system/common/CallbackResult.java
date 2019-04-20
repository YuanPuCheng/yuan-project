package com.zihui.cwoa.system.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CallbackResult {
	public int result;
    public String message;
    public Map map;
    public List<?> list ;

	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}

	
}
