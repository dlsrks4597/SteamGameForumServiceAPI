package kr.ac.ync.game_forum.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.ync.game_forum.service.impl.GameForumServiceImpl;
import kr.ac.ync.game_forum.util.ResultSteamAPI;
/**
 * 
 * @author Kim
 *
 */
@RestController
public class SteamAPIController {
	
	@Autowired
	GameForumServiceImpl gameForumServiceImpl;
	
	//API_0025 Steam API
	/**
	 * API_0025 Steam API
	 * http://localhost:8080/GameForum/gameDetails?appids=200900
	 * @param appids 외부 API로 넘길 게임코드
	 * @return
	 * {
		"header":{
		"msg":"OK",
		"code":"00"
		},
		"body":{
			"data":{
				"header_image":"https:\/\/steamcdn-a.akamaihd.net\/steam\/apps\/753420\/header.jpg?t=1589173442",
				"short_description":"Dungreed는 2D 로그라이트 요소가 가미된 2D 횡스크롤 액션 게임입니다. 여러분은 모험가가 되어 마을을 파괴한 끊임없이 변화하는 던전을 탐험해야 합니다. 적들을 때려잡고, 다양한 무기와 마법을 사용하고, 음식을 먹어 던전 안의 악을 물리치세요!",
				"supported_languages":"영어, 일본어, 중국어 간체, 중국어 번체, 한국어",
				"achievements":{
					"total":16,
					"highlighted":[
						{
						"path":"https:\/\/steamcdn-a.akamaihd.net\/steamcommunity\/public\/images\/apps\/753420\/639461f3d8330918a7b9ab5ac1c6b27db23d7c72.jpg",
						"name":"감옥의 수문장"
						},
						{
						"path":"https:\/\/steamcdn-a.akamaihd.net\/steamcommunity\/public\/images\/apps\/753420\/fcd9f2300418487dfd49704e5c7784f6e8ee0038.jpg",
						"name":"얼음 마녀"
						},
						...
	 */
	@RequestMapping("/gameDetails")
	public String gameDetails(
			@RequestParam(value = "appids") String appids){
		ResultSteamAPI resultSteamAPI = new ResultSteamAPI();
		String requestUrl = "https://store.steampowered.com/api/appdetails?appids=";
		requestUrl += appids;
		requestUrl += "&l=korean";
		System.out.println("requestUrl : " + requestUrl);
		URL url;
		try {
			url = new URL(requestUrl);
			BufferedReader bf; 
			String line = ""; 
			String result=""; 
			bf = new BufferedReader(new InputStreamReader(url.openStream()));
			while((line=bf.readLine())!=null){ 
				result=result.concat(line);  
				//System.out.println(result);
			}
			//GetJSONFromURL.getJSON(result);
			JSONParser parser = new JSONParser(); 
			JSONObject obj = (JSONObject) parser.parse(result);
			JSONObject parseResponse = (JSONObject)obj.get(appids);
			System.out.println("JSON : " + parseResponse);
			resultSteamAPI.setBody(parseResponse);
			resultSteamAPI.setHeader("00", "OK");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultSteamAPI.setHeader("01", "ERR - IOException");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultSteamAPI.setHeader("02", "ERR - ParseException");
		}
		
		return resultSteamAPI.getResultData().toString();
	}
	
	
	
}
