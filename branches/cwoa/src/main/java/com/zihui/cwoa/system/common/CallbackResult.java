package com.zihui.cwoa.system.common;

import java.util.HashMap;
import java.util.List;


public class CallbackResult {
	public int result;
    public String message;
    public HashMap<String,Object> map;
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
	public HashMap<String, Object> getMap() {
		return map;
	}
	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

	
}
