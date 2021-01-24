package kr.ac.ync.game_forum.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ResultSteamAPI {
	
	JSONObject header = new JSONObject();
	JSONObject body = new JSONObject();
	
	@SuppressWarnings("unchecked")
	public void setHeader(String code, String msg) {
		header.put("code", code);
		header.put("msg", msg);
	}
	
	public void setBody(JSONObject body) {
		this.body = body;
	}//상세보기
	
	@SuppressWarnings("unchecked")
	public JSONObject getResultData() {
		JSONObject obj = new JSONObject();
		obj.put("header", header);
		obj.put("body", body);
		return obj;
	}
}
