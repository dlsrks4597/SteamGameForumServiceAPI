package kr.ac.ync.game_forum.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResultData {
	
	JSONObject header = new JSONObject();
	JSONObject body = new JSONObject();
	
	public void setHeader(String code, String msg) {
		header.put("code", code);
		header.put("msg", msg);
	}
	
	public void setBody(int cnt, int pageNum, JSONArray data) {
		body.put("cnt", cnt);
		body.put("pageNum", pageNum);
		body.put("data", data);
	}
	
	public void setBody(JSONObject body) {
		this.body = body;
	}//상세보기
	//오버라이딩
	public void setBody(String key, String value) {
		body.put(key, value);
	}
	
	public JSONObject getResultData() {
		JSONObject obj = new JSONObject();
		obj.put("header", header);
		obj.put("body", body);
		return obj;
	}
}
