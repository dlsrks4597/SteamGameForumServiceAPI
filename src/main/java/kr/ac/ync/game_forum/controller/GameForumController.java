package kr.ac.ync.game_forum.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.ync.game_forum.service.impl.GameForumServiceImpl;
import kr.ac.ync.game_forum.util.ResultData;
import kr.ac.ync.game_forum.util.Utils;
import kr.ac.ync.game_forum.vo.GameForumGetGameListVO;
import kr.ac.ync.game_forum.vo.GameForumGetLatestForumVO;
import kr.ac.ync.game_forum.vo.GameForumGetLatestGameVO;
import kr.ac.ync.game_forum.vo.GameForumGetLikesListVO;
import kr.ac.ync.game_forum.vo.GameForumReplyListVO;
import kr.ac.ync.game_forum.vo.GameForumGetListVO;
import kr.ac.ync.game_forum.vo.GameForumGetProfileVO;
import kr.ac.ync.game_forum.vo.GameForumGetViewVO;
import kr.ac.ync.game_forum.vo.GameForumInsertForumVO;
import kr.ac.ync.game_forum.vo.GameForumLoginVO;
import kr.ac.ync.game_forum.vo.GameForumModifyForumVO;
import kr.ac.ync.game_forum.vo.GameForumModifyGameVO;
import kr.ac.ync.game_forum.vo.GameForumModifyProfileVO;
import kr.ac.ync.game_forum.vo.GameForumRegistVO;
import kr.ac.ync.game_forum.vo.GameForumSearchIDResultVO;
import kr.ac.ync.game_forum.vo.GameForumUploadGameVO;
/**
 * 
 * @author Kim
 *
 */
@RestController
public class GameForumController {
	
	@Autowired
	GameForumServiceImpl gameForumServiceImpl;
	
	@Autowired
	public JavaMailSender javaMailSender;
	
	/**
	 * API_0000 로그인
	 * http://localhost:8080/GameForum/login
	 * @param member_id 사용자 아이디
	 * @param password  비밀번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"member_id":"admin",
			"image":"src/main/webapp/GameForumImg/2020-12-12-22-40-28-9e6ca9b2-4416-499f-8978-85b0f5f2f892-admin_profile.jpg",
			"profile":"안녕하세요",
			"name":"어드민",
			"regdate":"2020-12-13 01:30:17.0",
			"nickname":"어드민ㅇㅅㅇ",
			"email":"admin@naver.com"
		}
	   }
	 */
	@PostMapping(value="/login")
	public String login(
			@RequestParam("member_id") String member_id,
			@RequestParam("password") String password) {
		
		GameForumLoginVO gameForumLoginVO = null;
		ResultData resultData = new ResultData();
		gameForumLoginVO = gameForumServiceImpl.gameForumLogin(member_id, password);
		if (gameForumLoginVO != null) {
			resultData.setHeader("00", "OK");
			resultData.setBody(new JSONObject(gameForumLoginVO));
		} else {
			resultData.setHeader("01", "ERR");
		}
		
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0001  중복체크
	 * http://localhost:8080/GameForum/idCheck
	 * @param member_id 사용자 아이디
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"msg":"사용가능한 아이디"
		}
	   }
	 */
	@PostMapping(value="/idCheck")
	public String idCheck(
			@RequestParam("member_id") String member_id) {
		ResultData resultData = new ResultData();
		int result = 0;
		try {
			result = gameForumServiceImpl.gameForumIdCheck(member_id);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
			if (result == 0) {
				resultData.setBody("msg", "사용가능한 아이디");
			} else {
				resultData.setBody("msg", "중복되는 아이디");
			}
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0002  인증코드 생성 및 이메일 전송
	 * http://localhost:8080/GameForum/createAuthcode
	 * @param email 이메일
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"code":"834147"
		}
	   }
	 */
	@PostMapping(value = "/createAuthcode")
	public String createAuth(
			@RequestParam("email") String email) {
		ResultData resultData = new ResultData();
		String verifyCode = "";
		int result = 0;
		try {
			for (int i = 0; i < 6; i++) {
				verifyCode += (int)(Math.random()*10);
			}
			result = gameForumServiceImpl.searchAuthcode(email);
			System.out.println(email);
			if(result != 0) {
				result = gameForumServiceImpl.resetAuthCode(email);
				System.out.println("기존 인증코드 삭제성공");
			}
			
			result = gameForumServiceImpl.inputAuthCode(email, verifyCode);
			System.out.println("인증코드 추가 성공");
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(email);
			message.setSubject("- Spring Boot 이메일 인증 테스트 - ");
			message.setText(email + "님의 이메일 인증코드는 [" + verifyCode + "] 입니다.\n (본 이메일은 Spring Boot 서비스 API 개발 테스트용입니다.)");
			javaMailSender.send(message);
			
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
			resultData.setBody("code", verifyCode);
		
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0003  인증코드 입력
	 * http://localhost:8080/GameForum/authcodeCheck
	 * @param email    이메일
	 * @param authcode 인증코드
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
			"body":{
				"result":"success"
			}
	   }
	 */
	@PostMapping(value = "/authcodeCheck")
	public String authcodeCheck(
			@RequestParam("email") String email,
			@RequestParam("authcode") String authcode) {
		ResultData resultData = new ResultData();
		int result = 0;
		try {
			result = gameForumServiceImpl.authcodeCheck(email, authcode);
			if(result != 0) {
				System.out.println("인증 성공");
				result = gameForumServiceImpl.resetAuthCode(email);
				resultData.setBody("result", "success");
			} else {
				System.out.println("인증 실패");
				resultData.setBody("result", "fail - 인증 실패");
			}
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0004 회원가입
	 * http://localhost:8080/GameForum/regMember
	 * @param member_id 사용자가 입력한 아이디 명
	 * @param name      사용자의 성명
	 * @param email     사용자가 입력한 이메일 
	 * @param nickname  사용자가 입력한 닉네임
	 * @param password  사용자가 입력한 비밀번호
	 * @param profile   사용자가 입력한 프로필내용
	 * @param image     사용자가 업로드한 사진파일
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
			"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/regMember", consumes= {"multipart/form-data"})
	public String registMember(
			@RequestPart("member_id") String member_id,
			@RequestPart("name") String name,
			@RequestPart("email") String email,
			@RequestPart("nickname") String nickname,
			@RequestPart("password") String password,
			@RequestPart(value = "profile", required = false) String profile,
			@RequestPart(value = "image") MultipartFile image) {
		ResultData resultData = new ResultData();
		String filePath = "src/main/webapp/GameForumImg/";
		String imgFilePath = "";
		File dir = new File(filePath);
		System.out.println("image getOriginalFilename : " + image.getOriginalFilename());
		System.out.println("image : " + image);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		if (image.isEmpty() == false) {
			UUID uuid = UUID.randomUUID();
			Date time = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat ( "yyyy-MM-dd-HH-mm-ss");
			String uploadTime = timeFormat.format(time);
			imgFilePath = filePath + uploadTime + "-" + uuid + "-" + image.getOriginalFilename();
			System.out.println("00 : 이미지 업로드했음");
		} else {
			imgFilePath = null;
			System.out.println("00 : 이미지 업로드하지 않음");
		}
		GameForumRegistVO gameForumRegistVO = new GameForumRegistVO(
				member_id, name, email, nickname, password, profile, imgFilePath);
			int result = 0;
			result = gameForumServiceImpl.gameForumRegist(gameForumRegistVO);
			if (result == 1) {
				resultData.setHeader("00", "OK");
				imgFilePath = Utils.fileSave(imgFilePath, image, "path?");
			} else {
				resultData.setHeader("01", "ERR");
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj); //"{\"result:\":1}"
			return resultData.getResultData().toString();
	}
	
	/**
	 * API_0005 아이디 찾기
	 * http://localhost:8080/GameForum/searchID
	 * @param email 가입 시 등록했던 이메일
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"member_id":"user2",
			"nickname":"유저2",
			"email":"user2@gmail.com"
		}
	   }
	 */
	@PostMapping(value="/searchID")
	public String searchID(
			@RequestParam("email") String email) {
		ResultData resultData = new ResultData();
		GameForumSearchIDResultVO gameForumSearchIDResult = null;
		int result = 0;
		result = gameForumServiceImpl.gameForumSearchID(email);
		if (result != 0) {
			resultData.setHeader("00", "OK");
			gameForumSearchIDResult = gameForumServiceImpl.gameForumSearchIDResult(email);
			resultData.setBody(new JSONObject(gameForumSearchIDResult));
		} else {
			resultData.setHeader("01", "ERR");
		}
		
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0006 비밀번호 변경
	 * http://localhost:8080/GameForum/modifyPW
	 * @param member_id 사용자 아이디
	 * @param password  변경할 새 비밀번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/modifyPW")
	public String modifyPW(
			@RequestParam("member_id") String member_id,
			@RequestParam("password") String password) {
		ResultData resultData = new ResultData();
		int result = 0;
		result = gameForumServiceImpl.gameForumModifyPW(member_id, password);
		if (result == 1) {
			resultData.setHeader("00", "OK");
		} else {
			resultData.setHeader("01", "ERR");
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		resultData.setBody(obj);
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0007 메인화면 프로필
	 * http://localhost:8080/GameForum/getProfile
	 * @param member_id 사용자 아이디
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"member_id":"admin",
			"image":"src/main/webapp/GameForumImg/2020-12-12-22-40-28-9e6ca9b2-4416-499f-8978-85b0f5f2f892-admin_profile.jpg",
			"profile":"안녕하세요",
			"name":"어드민",
			"regdate":"Sun Dec 13 01:30:17 KST 2020",
			"nickname":"어드민ㅇㅅㅇ",
			"email":"admin@naver.com"
		}
	   }
	 */
	@PostMapping(value="/getProfile")
	public String getProfile(
			@RequestParam("member_id") String member_id) {
		GameForumGetProfileVO gameForumGetProfileVO = null;
		ResultData resultData = new ResultData();
		gameForumGetProfileVO = gameForumServiceImpl.gameForumGetProfile(member_id);
		if (gameForumGetProfileVO != null) {
			resultData.setHeader("00", "OK");
			resultData.setBody(new JSONObject(gameForumGetProfileVO));
		} else {
			resultData.setHeader("01", "ERR");
		}
		
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0008 메인화면 최신글 가져오기 (최신순 5개)
	 * http://localhost:8080/GameForum/getLatestForum
	 * @param board_code 게시판 분류 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
					"member_id":"user1",
					"content_idx":31,
					"regdate":"Mon Dec 14 06:10:53 KST 2020",
					"board_code":3,
					"title":"insert test",
					"content":"test",
					"likes":2
				},
				{
					"member_id":"admin",
					"content_idx":21,
					"regdate":"Sun Dec 13 22:52:54 KST 2020",
					"board_code":3,
					"title":"insert test2",
					"image1":"src/main/webapp/GameForumImg/2020-12-13-22-52-54-409cb42a-4fa7-4438-b0ac-d4e4c40a7ae9-1.png",
					"content":"내용",
					"likes":0
				},
				...
			],
			"cnt":5
			}
	   }
	 */
	@PostMapping(value="getLatestForum")
	public String getLatestForum(
			@RequestParam("board_code") int board_code) {
		ResultData resultData = new ResultData();
		List<GameForumGetLatestForumVO> gameForumGetLatestForumVO = null;
		try {
			gameForumGetLatestForumVO = gameForumServiceImpl.gameForumGetLatestForum(board_code);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		JSONArray arry = new JSONArray(gameForumGetLatestForumVO.toArray());
		resultData.setBody(5, 1, arry);
		
		return resultData.getResultData().toString();
		
	}
	
	/**
	 * API_0009 메인화면 최신게임 (관리자가 추가한 게임순서대로)
	 * http://localhost:8080/GameForum/getLatestGame
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
				"member_id":"admin",
				"thumbnail":"src/main/webapp/GameForumImg/2020-12-14-02-36-40-228c9872-7f97-4fae-8422-acc602c3a706-1049590.jpg",
				"appids":1049590,
				"regdate":"2020-12-14 02:36:40.0",
				"gametitle":"영원회귀: 블랙 서바이벌"
				},
				{
				"member_id":"admin",
				"thumbnail":"src/main/webapp/GameForumImg/2020-12-14-02-36-23-51fb0ef9-23b4-4303-9402-1f92715a3d92-960170.jpg",
				"appids":960170,
				"regdate":"2020-12-14 02:36:23.0",
				"gametitle":"DJMAX Respect V"
				},
				...
			],
		"cnt":5
		}
	   }
	 */
	@PostMapping(value="getLatestGame")
	public String getLatestGame() {
		ResultData resultData = new ResultData();
		List<GameForumGetLatestGameVO> gameForumGetLatestGameVO = null;
		try {
			gameForumGetLatestGameVO = gameForumServiceImpl.gameForumGetLatestGame();
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		JSONArray arry = new JSONArray(gameForumGetLatestGameVO.toArray());
		resultData.setBody(5, 1, arry);
		
		return resultData.getResultData().toString();
		
	}
	
	/**
	 * API_0010 프로필 변경
	 * http://localhost:8080/GameForum/modifyProfile
	 * @param member_id 사용자 아이디
	 * @param name      사용자 성명
	 * @param email     사용자 이메일
	 * @param nickname  사용자 닉네임
	 * @param password  사용자 비밀번호
	 * @param profile   사용자 프로필
	 * @param image     사용자 이미지파일 (프로필사진)
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/modifyProfile", consumes= {"multipart/form-data"})
	public String modifyProfile(@RequestPart("member_id") String member_id,
			@RequestPart("name") String name,
			@RequestPart("email") String email,
			@RequestPart("nickname") String nickname,
			@RequestPart("password") String password,
			@RequestPart(value = "profile", required = false) String profile,
			@RequestPart(value = "image") MultipartFile image) {
		ResultData resultData = new ResultData();
		String filePath = "src/main/webapp/GameForumImg/";
		String imgFilePath = "";
		File dir = new File(filePath);
		System.out.println("image getOriginalFilename : " + image.getOriginalFilename());
		System.out.println("image : " + image);
		if (!dir.exists()) {
			dir.mkdir();
		}
		imgFilePath = gameForumServiceImpl.memberImagePathGet(member_id);
		System.out.println("기존 파일 명 : " + imgFilePath);
		if (imgFilePath != null) {
			Utils.deleteFile(imgFilePath);
			System.out.println("파일 삭제 성공");
		} else {
			System.out.println("null이기 때문에 파일 삭제 생략");
		}
		
		if (image.isEmpty() == false) {
			UUID uuid = UUID.randomUUID();
			Date time = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat ( "yyyy-MM-dd-HH-mm-ss");
			String uploadTime = timeFormat.format(time);
			imgFilePath = filePath + uploadTime + "-" + uuid + "-" + image.getOriginalFilename();
			System.out.println("00 : 이미지 업로드했음");
		} else {
			imgFilePath = null;
			System.out.println("00 : 이미지 업로드하지 않음");
		}
		GameForumModifyProfileVO gameForumModifyProfileVO = new GameForumModifyProfileVO(
				member_id, name, email, nickname, password, profile, imgFilePath);
			int result = 0;
			result = gameForumServiceImpl.gameForumModifyProfile(gameForumModifyProfileVO);
			if (result == 1) {
				resultData.setHeader("00", "OK");
				imgFilePath = Utils.fileSave(imgFilePath, image, "path?");
			} else {
				resultData.setHeader("01", "ERR");
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj); //"{\"result:\":1}"
		
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0011  게시글 목록
	 * http://localhost:8080/GameForum/forumList?pageNum=1&board_code=3
	 * @param pageNum    페이징 번호
	 * @param board_code 게시판 분류 코드
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
				"appids":0,
				"content_idx":31,
				"regdate":"2020-12-14 06:10:53.0",
				"nickname":"유저1",
				"board_code":3,
				"title":"insert test",
				"content":"test",
				"likes":2
				},
				{
				"image":"src/main/webapp/GameForumImg/2020-12-13-22-52-54-409cb42a-4fa7-4438-b0ac-d4e4c40a7ae9-1.png",
				"appids":200900,
				"content_idx":21,
				"regdate":"2020-12-13 22:52:54.0",
				"nickname":"어드민ㅇㅅㅇ",
				"board_code":3,
				"title":"insert test2",
				"content":"내용",
				"likes":0
				},
				...
			],
		"cnt":10
		}
	   }
	 */
	@RequestMapping(value="/forumList")
	public String forumList(
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
			@RequestParam(value = "board_code", required = false, defaultValue = "1") int board_code) {
		
		ResultData resultData = new ResultData();
		List<GameForumGetListVO> gameForumGetListVO = null;
		int startNum = (pageNum - 1) * 10;
		
		try {
			gameForumGetListVO = gameForumServiceImpl.gameForumGetList(board_code, startNum);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		JSONArray arry = new JSONArray(gameForumGetListVO.toArray());
		resultData.setBody(10, 1, arry);
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0012 게시글 검색
	 * http://localhost:8080/GameForum/searchForum?board_code=3&title=insert&nickname=어드민&pageNum=1
	 * @param title      검색할 제목명
	 * @param content    검색할 내용
	 * @param nickname   검색할 글쓴이 닉네임
	 * @param board_code 게시판 분류 코드
	 * @param pageNum    페이징 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
				"appids":0,
				"content_idx":27,
				"regdate":"2020-12-14 05:42:35.0",
				"nickname":"어드민ㅇㅅㅇ",
				"board_code":3,
				"title":"insert test",
				"content":"내용",
				"likes":0
				},
				{
				"image":"src/main/webapp/GameForumImg/2020-12-14-11-07-22-83c64092-a4f7-4b67-bb19-0d4f6fde896e-582010.jpg",
				"appids":200900,
				"content_idx":22,
				"regdate":"2020-12-14 02:07:20.0",
				"nickname":"어드민ㅇㅅㅇ",
				"board_code":3,
				"title":"insert test 3",
				"content":"내용-수정3",
				"likes":0
				},
				...
			],
		"cnt":10
		}
	   }
	 */
	@RequestMapping(value="/searchForum")
	public String searchForum(
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "content", required = false) String content,
			@RequestParam(value = "nickname", required = false) String nickname,
			@RequestParam(value = "board_code", required = false, defaultValue = "1") int board_code,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum) {
		ResultData resultData = new ResultData();
		List<GameForumGetListVO> gameForumGetListVO = null;
		int startNum = (pageNum - 1) * 10;
		
		try {
			gameForumGetListVO = gameForumServiceImpl.gameForumGetListWithKeywords(title, content, nickname, board_code, startNum);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		JSONArray arry = new JSONArray(gameForumGetListVO.toArray());
		resultData.setBody(10, 1, arry);
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0013 게시글 상세내용
	 * http://localhost:8080/GameForum/forumView?content_idx=3
	 * @param content_idx 게시글 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"appids":0,
			"content_idx":3,
			"regdate":"2020-12-13 13:28:45.0",
			"nickname":"어드민ㅇㅅㅇ",
			"board_code":3,
			"title":"게시글 1",
			"content":"내용1",
			"likes":3
		}
	   }
	 */
	@RequestMapping("/forumView")
	public String forumView(
			@RequestParam(value = "content_idx", required = false, defaultValue = "1") int content_idx
			) {
		GameForumGetViewVO gameForumGetViewVO = null;
		ResultData resultData = new ResultData();
		gameForumGetViewVO = gameForumServiceImpl.gameForumGetView(content_idx);
		if (gameForumGetViewVO != null) {
			resultData.setHeader("00", "OK");
			resultData.setBody(new JSONObject(gameForumGetViewVO));
		} else {
			resultData.setHeader("01", "ERR");
		}
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0014 좋아요 등록
	 * http://localhost:8080/GameForum/likesForum
	 * @param member_id   좋아요를 등록할 사용자 아이디
	 * @param content_idx 좋아요에 등록하고 싶은 게시글 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1,
			"msg":"31번째 게시글에 좋아요를 누르셨습니다."
		}
	   }
	 */
	@PostMapping("/likesForum")
	public String likesForum(
			@RequestParam(value = "member_id") String member_id,
			@RequestParam(value = "content_idx") int content_idx) {
		ResultData resultData = new ResultData();
		int count = 0;
		int result = 0;
		try {
			count = gameForumServiceImpl.gameForumGetLikes(member_id, content_idx);
			System.out.println("count : " + count);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		if(count == 0) {
			result = gameForumServiceImpl.gameForumInsertLikes(member_id, content_idx);
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj);
			resultData.setBody("msg", content_idx + "번째 게시글에 좋아요를 누르셨습니다.");
		} else {
			resultData.setHeader("02", "ERR");
			resultData.setBody("msg", "이미 좋아요를 누른 게시글입니다.");
		}
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0015 좋아요 취소
	 * http://localhost:8080/GameForum/likesCancelForum
	 * @param member_id   좋아요 취소 요청을 하는 사용자 아이디
	 * @param content_idx 좋아요를 취소하고 싶은 게시글 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1,
			"msg":"18번째 게시글에 좋아요를 취소하셨습니다."
		}
	   }
	 */
	@PostMapping("/likesCancelForum")
	public String likesCancelForum(
			@RequestParam(value = "member_id") String member_id,
			@RequestParam(value = "content_idx") int content_idx) {
		ResultData resultData = new ResultData();
		int count = 0;
		int result = 0;
		try {
			count = gameForumServiceImpl.gameForumGetLikes(member_id, content_idx);
			System.out.println("count : " + count);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		if(count != 0) {
			result = gameForumServiceImpl.gameForumCancelLikes(member_id, content_idx);
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj);
			resultData.setBody("msg", content_idx + "번째 게시글에 좋아요를 취소하셨습니다.");
		} else {
			resultData.setHeader("02", "ERR");
			resultData.setBody("msg", "좋아요를 누르지 않은 게시글입니다.");
		}
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0016 게시글 작성
	 * http://localhost:8080/GameForum/insertForum
	 * @param board_code 게시판 분류 코드
	 * @param title      새 게시글 제목
	 * @param appids     게시글에 태그할 게임코드
	 * @param member_id  게시글 작성자 아이디
	 * @param content    게시글 내용
	 * @param image      게시글 첨부 이미지
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/insertForum", consumes= {"multipart/form-data"})
	public String insertForum(
			@RequestParam("board_code") int board_code,
			@RequestParam("title") String title,
			@RequestParam(value = "appids", required = false, defaultValue = "0") int appids,
			@RequestParam("member_id") String member_id,
			@RequestParam("content") String content,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		ResultData resultData = new ResultData();
		String filePath = "src/main/webapp/GameForumImg/";
		String imgFilePath = "";
		File dir = new File(filePath);
		
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (image.isEmpty() == false) {
			UUID uuid = UUID.randomUUID();
			Date time = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat ( "yyyy-MM-dd-HH-mm-ss");
			String uploadTime = timeFormat.format(time);
			imgFilePath = filePath + uploadTime + "-" + uuid + "-" + image.getOriginalFilename();
			System.out.println("00 : image 업로드");
		} else {
			imgFilePath = null;
			System.out.println("00 : image 업로드하지 않음");
		}

		GameForumInsertForumVO gameForumInsertForumVO = new GameForumInsertForumVO(
				board_code, title, appids, member_id, content, imgFilePath);
			int result = 0;
			result = gameForumServiceImpl.insertForum(gameForumInsertForumVO);
			if (result == 1) {
				resultData.setHeader("00", "OK");
				imgFilePath = Utils.fileSave(imgFilePath, image, "path?");
			} else {
				resultData.setHeader("01", "ERR");
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj);
			return resultData.getResultData().toString();
	}	
	
	/**
	 * API_0017 게시물 수정
	 * http://localhost:8080/GameForum/modifyForum
	 * @param content_idx 수정할 게시글 번호
	 * @param board_code  게시판 분류 코드
	 * @param title       수정할 제목
	 * @param appids      게시글에 태그할 게임코드
	 * @param member_id   게시글 작성자
	 * @param content     수정할 게시글 내용
	 * @param image       새로 업로드할 이미지
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/modifyForum", consumes= {"multipart/form-data"})
	public String modifyForum(
			@RequestParam("content_idx") int content_idx,
			@RequestParam("board_code") int board_code,
			@RequestParam("title") String title,
			@RequestParam(value = "appids", required = false, defaultValue = "0") int appids,
			@RequestParam("member_id") String member_id,
			@RequestParam("content") String content,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		ResultData resultData = new ResultData();
		String filePath = "src/main/webapp/GameForumImg/";
		String imgFilePath = "";
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String existImgFilePath = gameForumServiceImpl.forumImagePathGet(content_idx);
		
		if (image.isEmpty() == false) {
			UUID uuid = UUID.randomUUID();
			Date time = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat ("yyyy-MM-dd-HH-mm-ss");
			String uploadTime = timeFormat.format(time);
			imgFilePath = filePath + uploadTime + "-" + uuid + "-" + image.getOriginalFilename();
			System.out.println("00 : 이미지 업로드했음");
		} else {
			imgFilePath = null;
			System.out.println("00 : 이미지 업로드하지 않음");
		}
		GameForumModifyForumVO gameForumModifyForumVO = new GameForumModifyForumVO(
				content_idx, board_code, title, appids, member_id, content, imgFilePath);
			int result = 0;
			result = gameForumServiceImpl.gameForumModifyForum(gameForumModifyForumVO);
			if (result == 1) {
				resultData.setHeader("00", "OK");
				imgFilePath = Utils.fileSave(imgFilePath, image, "path?");
				System.out.println("기존 파일 명 : " + existImgFilePath);
				if (existImgFilePath != null) {
					Utils.deleteFile(existImgFilePath);
					System.out.println("(image) 파일 삭제 성공");
				} else {
					System.out.println("(image) null이기 때문에 파일 삭제 생략");
				}
			} else {
				resultData.setHeader("01", "ERR");
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj);
		
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0018 게시글 삭제
	 * http://localhost:8080/GameForum/removeForum
	 * @param content_idx 삭제할 게시글 번호
	 * @param member_id   삭제할 게시글 작성자
	 * @return
	 */
	@PostMapping(value="/removeForum")
	public String removeForum(
			@RequestParam("content_idx") int content_idx,
			@RequestParam("member_id") String member_id) {
		ResultData resultData = new ResultData();
		String existImgFilePath = gameForumServiceImpl.forumImagePathGet(content_idx);
			int result = 0;
			result = gameForumServiceImpl.gameForumRemoveForum(content_idx, member_id);
			if (result == 1) {
				resultData.setHeader("00", "OK");
				System.out.println("기존 파일 명 : " + existImgFilePath);
				if (existImgFilePath != null) {
					Utils.deleteFile(existImgFilePath);
					System.out.println("(image) 파일 삭제 성공");
				} else {
					System.out.println("(image) null이기 때문에 파일 삭제 생략");
				}
			} else {
				resultData.setHeader("01", "ERR");
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj);
		
		return resultData.getResultData().toString();
	}
	
	//API_0019 댓글 조회
	/**
	 * API_0019 댓글 조회
	 * http://localhost:8080/GameForum/replyList?content_idx=3&replyPageNum=1
	 * @param content_idx  해당 댓글들이 속해있는 게시글 번호
	 * @param replyPageNum 댓글 페이징 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
				"member_id":"admin",
				"regdate":"Mon Dec 14 03:06:13 KST 2020",
				"replyNum":1,
				"reply_content":"댓글 테스트"
				},
				{
				"member_id":"admin",
				"regdate":"Mon Dec 14 03:06:33 KST 2020",
				"replyNum":2,
				"reply_content":"댓글 테스트2"
				},
				...
			],
		"cnt":20
		}
	   }
	 */
	@RequestMapping(value="/replyList")
	public String replyList(
		@RequestParam(value = "content_idx", required = false, defaultValue = "1") int content_idx,
		@RequestParam(value = "replyPageNum", required = false, defaultValue = "1") int replyPageNum) {
		
		ResultData resultData = new ResultData();
		List<GameForumReplyListVO> gameForumReplyListVO = null;
		int startNum = (replyPageNum - 1) * 20;
			
		try {
			gameForumReplyListVO = gameForumServiceImpl.gameForumReplyList(content_idx, startNum);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		System.out.println("------------------------------");
		System.out.println("replyNum : " + replyPageNum);
		System.out.println("------------------------------");
		
		JSONArray arry = new JSONArray(gameForumReplyListVO.toArray());
		resultData.setBody(20, 1, arry);
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0020 댓글 작성
	 * http://localhost:8080/GameForum/insertReply
	 * @param content_idx   댓글을 등록할 게시글 번호
	 * @param member_id     댓글 작성자 아이디
	 * @param reply_content 댓글 내용
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/insertReply")
	public String insertReply(
			@RequestParam("content_idx") int content_idx,
			@RequestParam("member_id") String member_id,
			@RequestParam("reply_content") String reply_content) {
		ResultData resultData = new ResultData();
		
		int result = 0;
		result = gameForumServiceImpl.gameForumInsertReply(content_idx, member_id, reply_content);
		if (result == 1) {
			resultData.setHeader("00", "OK");
		} else {
			resultData.setHeader("01", "ERR");
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		resultData.setBody(obj); //"{\"result:\":1}"
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0021 댓글 수정
	 * http://localhost:8080/GameForum/modifyReply
	 * @param reply_idx     수정할 댓글 번호
	 * @param member_id     댓글 작성자
	 * @param reply_content 수정할 댓글 내용
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/modifyReply")
	public String modifyReply(
			@RequestParam("reply_idx") int reply_idx,
			@RequestParam("member_id") String member_id,
			@RequestParam("reply_content") String reply_content) {
		ResultData resultData = new ResultData();
		
		int result = 0;
		result = gameForumServiceImpl.gameForumModifyReply(reply_idx, member_id, reply_content);
		if (result == 1) {
			resultData.setHeader("00", "OK");
		} else {
			resultData.setHeader("01", "ERR");
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		resultData.setBody(obj); //"{\"result:\":1}"
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0022 댓글 삭제
	 * http://localhost:8080/GameForum/removeReply
	 * @param reply_idx 삭제할 댓글 번호
	 * @param member_id 댓글 작성자
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/removeReply")
	public String removeReply(
			@RequestParam("reply_idx") int reply_idx,
			@RequestParam("member_id") String member_id) {
		ResultData resultData = new ResultData();
		
		int result = 0;
		result = gameForumServiceImpl.gameForumRemoveReply(reply_idx, member_id);
		if (result == 1) {
			resultData.setHeader("00", "OK");
		} else {
			resultData.setHeader("01", "ERR");
		}
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		resultData.setBody(obj); //"{\"result:\":1}"
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0023 게임 목록
	 * http://localhost:8080/GameForum/gameList?pageNum=1
	 * @param pageNum 페이징 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
				"thumbnail":"src/main/webapp/GameForumImg/2020-12-14-02-36-40-228c9872-7f97-4fae-8422-acc602c3a706-1049590.jpg",
				"appids":1049590,
				"regdate":"2020-12-14 02:36:40.0",
				"nickname":"어드민ㅇㅅㅇ",
				"gametitle":"영원회귀: 블랙 서바이벌"
				},
				{
				"thumbnail":"src/main/webapp/GameForumImg/2020-12-14-02-36-23-51fb0ef9-23b4-4303-9402-1f92715a3d92-960170.jpg",
				"appids":960170,
				"regdate":"2020-12-14 02:36:23.0",
				"nickname":"어드민ㅇㅅㅇ",
				"gametitle":"DJMAX Respect V"
				},
				...
			],
		"cnt":10
		}
	   }
	 */
	@RequestMapping(value="/gameList")
	public String gameList(
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum) {
		ResultData resultData = new ResultData();
		List<GameForumGetGameListVO> gameForumGetGameListVO = null;
		int startNum = (pageNum - 1) * 10;

		try {
			gameForumGetGameListVO = gameForumServiceImpl.gameForumGetGameList(startNum);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		System.out.println("------------------------------");
		System.out.println("pageNum : " + pageNum);
		System.out.println("------------------------------");
		JSONArray arry = new JSONArray(gameForumGetGameListVO.toArray());
		resultData.setBody(10, 1, arry);
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0024 게임 검색
	 * http://localhost:8080/GameForum/searchGame?gametitle=영원&pageNum=1
	 * @param gametitle 검색할 게임명
	 * @param pageNum   페이징 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
				"thumbnail":"src/main/webapp/GameForumImg/2020-12-14-02-36-40-228c9872-7f97-4fae-8422-acc602c3a706-1049590.jpg",
				"appids":1049590,
				"regdate":"2020-12-14 02:36:40.0",
				"nickname":"어드민ㅇㅅㅇ",
				"gametitle":"영원회귀: 블랙 서바이벌"
				}
			],
		"cnt":10
		}
	   }
	 */
	@RequestMapping(value="/searchGame")
	public String searchGame(
			@RequestParam(value = "gametitle", required = false) String gametitle,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum) {
		ResultData resultData = new ResultData();
		List<GameForumGetGameListVO> gameForumGetGameListVO = null;
		int startNum = (pageNum - 1) * 10;
		
		try {
			gameForumGetGameListVO = gameForumServiceImpl.gameForumGetGameListWithKeywords(gametitle, startNum);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		JSONArray arry = new JSONArray(gameForumGetGameListVO.toArray());
		resultData.setBody(10, 1, arry);
		return resultData.getResultData().toString();
	}
	
	//API_0025 Steam API (kr.ac.ync.game_forum.controller.SteamAPIController.java)
	
	/**
	 * API_0026 게임 등록
	 * http://localhost:8080/GameForum/uploadGame
	 * @param appids    게시판에 등록할 게임 고유번호
	 * @param gametitle 게임명
	 * @param member_id 작성자 아이디
	 * @param thumbnail 업로드할 썸네일 이미지
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/uploadGame", consumes= {"multipart/form-data"})
	public String uploadGame(
			@RequestPart("appids") String appids,
			@RequestPart("gametitle") String gametitle,
			@RequestPart("member_id") String member_id,
			@RequestPart(value = "thumbnail") MultipartFile thumbnail) {
		ResultData resultData = new ResultData();
		String filePath = "src/main/webapp/GameForumImg/";
		String imgFilePath = "";
		File dir = new File(filePath);
		
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (thumbnail.isEmpty() == false) {
			UUID uuid = UUID.randomUUID();
			Date time = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat ( "yyyy-MM-dd-HH-mm-ss");
			String uploadTime = timeFormat.format(time);
			imgFilePath = filePath + uploadTime + "-" + uuid + "-" + thumbnail.getOriginalFilename();
			System.out.println("00 : 이미지 업로드했음");
		} else {
			imgFilePath = null;
			System.out.println("00 : 이미지 업로드하지 않음");
		}
		GameForumUploadGameVO gameForumUploadGameVO = new GameForumUploadGameVO(appids, gametitle, member_id, imgFilePath);
			int result = 0;
			result = gameForumServiceImpl.gameForumUploadGame(gameForumUploadGameVO);
			if (result == 1) {
				resultData.setHeader("00", "OK");
				imgFilePath = Utils.fileSave(imgFilePath, thumbnail, "path?");
			} else {
				resultData.setHeader("01", "ERR");
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj); //"{\"result:\":1}"
			return resultData.getResultData().toString();
	}
	
	/**
	 * API_0027 게임 수정
	 * http://localhost:8080/GameForum/modifyGame
	 * @param game_idx  수정할 게임 게시글 번호
	 * @param appids    수정할 게임 고유번호
	 * @param gametitle 수정할 게임명
	 * @param member_id 작성자 아이디
	 * @param thumbnail 새로 업로드할 썸네일 이미지
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/modifyGame", consumes= {"multipart/form-data"})
	public String modifyGame(
			@RequestParam("game_idx") int game_idx,
			@RequestPart("appids") String appids,
			@RequestPart("gametitle") String gametitle,
			@RequestPart("member_id") String member_id,
			@RequestPart(value = "thumbnail") MultipartFile thumbnail) {
		ResultData resultData = new ResultData();
		String filePath = "src/main/webapp/GameForumImg/";
		String imgFilePath = "";
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String existImgFilePath = gameForumServiceImpl.gameThumbnailPathGet(game_idx);
		
		if (thumbnail.isEmpty() == false) {
			UUID uuid = UUID.randomUUID();
			Date time = new Date();
			SimpleDateFormat timeFormat = new SimpleDateFormat ( "yyyy-MM-dd-HH-mm-ss");
			String uploadTime = timeFormat.format(time);
			imgFilePath = filePath + uploadTime + "-" + uuid + "-" + thumbnail.getOriginalFilename();
			System.out.println("00 : 이미지 업로드했음");
		} else {
			imgFilePath = null;
			System.out.println("00 : 이미지 업로드하지 않음");
		}
		GameForumModifyGameVO gameForumModifyGameVO = new GameForumModifyGameVO(
				game_idx, appids, gametitle, member_id, imgFilePath);
			int result = 0;
			result = gameForumServiceImpl.gameForumModifyGame(gameForumModifyGameVO);
			if (result == 1) {
				resultData.setHeader("00", "OK");
				imgFilePath = Utils.fileSave(imgFilePath, thumbnail, "path?");
				System.out.println("기존 파일 명 : " + existImgFilePath);
				if (existImgFilePath != null) {
					Utils.deleteFile(existImgFilePath);
					System.out.println("(image) 파일 삭제 성공");
				} else {
					System.out.println("(image) null이기 때문에 파일 삭제 생략");
				}
			} else {
				resultData.setHeader("01", "ERR");
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj);
		
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0028 게임 삭제
	 * http://localhost:8080/GameForum/removeGame
	 * @param game_idx  삭제할 게임 게시글 번호
	 * @param member_id 작성자 아이디
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"result":1
		}
	   }
	 */
	@PostMapping(value="/removeGame")
	public String removeGame(
			@RequestParam("game_idx") int game_idx,
			@RequestParam("member_id") String member_id) {
		ResultData resultData = new ResultData();
		String existImgFilePath = gameForumServiceImpl.gameThumbnailPathGet(game_idx);
			int result = 0;
			result = gameForumServiceImpl.gameForumRemoveGame(game_idx, member_id);
			if (result == 1) {
				resultData.setHeader("00", "OK");
				System.out.println("기존 파일 명 : " + existImgFilePath);
				if (existImgFilePath != null) {
					Utils.deleteFile(existImgFilePath);
					System.out.println("(image) 파일 삭제 성공");
				} else {
					System.out.println("(image) null이기 때문에 파일 삭제 생략");
				}
			} else {
				resultData.setHeader("01", "ERR");
			}
			JSONObject obj = new JSONObject();
			obj.put("result", result);
			resultData.setBody(obj);
		
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0029 좋아요 목록
	 * http://localhost:8080/GameForum/likesList
	 * @param member_id 좋아요를 한 사용자 아이디
	 * @param pageNum 페이징 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
				"content_idx":3,
				"nickname":"어드민ㅇㅅㅇ",
				"board_code":3,
				"title":"게시글 1",
				"likesRegdate":"Mon Dec 14 07:32:27 KST 2020",
				"contentRegdate":"Sun Dec 13 13:28:45 KST 2020",
				"likes":"3"
				},
				{
				"content_idx":31,
				"nickname":"유저1",
				"board_code":3,
				"title":"insert test",
				"likesRegdate":"Mon Dec 14 07:37:53 KST 2020",
				"contentRegdate":"Mon Dec 14 06:10:53 KST 2020",
				"likes":"2"
				}
			],
		"cnt":10
		}
	   }
	 */
	@PostMapping(value="/likesList")
	public String likesList (
			@RequestParam("member_id") String member_id,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum) {
		ResultData resultData = new ResultData();
		List<GameForumGetLikesListVO> gameForumGetLikesListVO = null;
		int startNum = (pageNum - 1) * 10;

		try {
			gameForumGetLikesListVO = gameForumServiceImpl.gameForumGetLikesList(member_id, startNum);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		JSONArray arry = new JSONArray(gameForumGetLikesListVO.toArray());
		resultData.setBody(10, 1, arry);
		return resultData.getResultData().toString();
	}
	
	/**
	 * API_0030 좋아요 목록 검색
	 * http://localhost:8080/GameForum/likesSearch
	 * @param member_id 좋아요를 한 사용자 아이디
	 * @param title     검색할 제목
	 * @param content   검색할 내용
	 * @param nickname  검색할 게시글 작성자 닉네임
	 * @param pageNum   페이징 번호
	 * @return
	 * {
		"header":{
			"msg":"OK",
			"code":"00"
		},
		"body":{
			"pageNum":1,
			"data":[
				{
				"content_idx":3,
				"nickname":"어드민ㅇㅅㅇ",
				"board_code":3,
				"title":"게시글 1",
				"likesRegdate":"Mon Dec 14 07:32:27 KST 2020",
				"contentRegdate":"Sun Dec 13 13:28:45 KST 2020",
				"likes":"3"
				}
			],
		"cnt":10
		}
	   }
	 */
	@PostMapping(value="/likesSearch")
	public String likesSearch (
			@RequestParam("member_id") String member_id,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("nickname") String nickname,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum) {
		ResultData resultData = new ResultData();
		List<GameForumGetLikesListVO> gameForumGetLikesListVO = null;
		int startNum = (pageNum - 1) * 10;

		try {
			gameForumGetLikesListVO = gameForumServiceImpl.gameForumGetLikesListWithKeywords(member_id, title, content, nickname, startNum);
			resultData.setHeader("00", "OK");
		} catch (Exception e) {
			e.printStackTrace();
			resultData.setHeader("01", "ERR");
		}
		JSONArray arry = new JSONArray(gameForumGetLikesListVO.toArray());
		resultData.setBody(10, 1, arry);
		return resultData.getResultData().toString();
	}
	
	
}
