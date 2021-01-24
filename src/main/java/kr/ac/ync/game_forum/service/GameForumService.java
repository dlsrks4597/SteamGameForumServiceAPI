package kr.ac.ync.game_forum.service;

import java.util.List;

import kr.ac.ync.game_forum.vo.GameForumGetGameListVO;
import kr.ac.ync.game_forum.vo.GameForumGetLatestGameVO;
import kr.ac.ync.game_forum.vo.GameForumGetLikesListVO;
import kr.ac.ync.game_forum.vo.GameForumReplyListVO;
import kr.ac.ync.game_forum.vo.GameForumGetListVO;
import kr.ac.ync.game_forum.vo.GameForumGetViewVO;
import kr.ac.ync.game_forum.vo.GameForumInsertForumVO;
import kr.ac.ync.game_forum.vo.GameForumLoginVO;
import kr.ac.ync.game_forum.vo.GameForumModifyForumVO;
import kr.ac.ync.game_forum.vo.GameForumModifyGameVO;
import kr.ac.ync.game_forum.vo.GameForumModifyProfileVO;
import kr.ac.ync.game_forum.vo.GameForumRegistVO;
import kr.ac.ync.game_forum.vo.GameForumSearchIDResultVO;
import kr.ac.ync.game_forum.vo.GameForumUploadGameVO;

public interface GameForumService {

	String getFilePath(int idx);

	int updateGameForum(int idx, String title, String content, String imgFilePath);
	
	//int uploadGame(int appids, String gametitle, String member_id, String thumbnail);

	int gameForumUploadGame(GameForumUploadGameVO uploadGameVO);

	int gameForumRegist(GameForumRegistVO gameForumRegistVO);
	
	int gameForumIdCheck(String member_id);
	
	int gameForumSearchID(String email);
	
	GameForumSearchIDResultVO gameForumSearchIDResult(String email);

	int gameForumModifyPW(String member_id, String password);
	
	List<GameForumGetLatestGameVO> gameForumGetLatestGame();

	int gameForumModifyProfile(GameForumModifyProfileVO gameForumModifyProfileVO);

	String memberImagePathGet(String member_id);

	public List<GameForumGetListVO> gameForumGetList(int board_code, int startNum);
	
	List<GameForumGetListVO> gameForumGetListWithKeywords(String title, String content, String nickname, int board_code,
			int startNum);

	public List<GameForumGetGameListVO> gameForumGetGameList(int startNum);
	
	List<GameForumGetGameListVO> gameForumGetGameListWithKeywords(String gametitle, int startNum);

	GameForumGetViewVO gameForumGetView(int content_idx);

	int gameForumGetLikes(String member_id, int content_idx);

	int gameForumInsertLikes(String member_id, int content_idx);
	
	int gameForumCancelLikes(String member_id, int content_idx);

	int insertForum(GameForumInsertForumVO gameForumInsertForumVO);

	String forumImagePathGet(int content_idx);

	int gameForumModifyForum(GameForumModifyForumVO gameForumModifyForumVO);

	int gameForumRemoveForum(int content_idx, String member_id);

	int gameForumModifyGame(GameForumModifyGameVO gameForumModifyGameVO);

	String gameThumbnailPathGet(int game_idx);

	int gameForumRemoveGame(int game_idx, String member_id);

	int gameForumInsertReply(int content_idx, String member_id, String reply_content);

	int gameForumModifyReply(int reply_idx, String member_id, String reply_content);

	int gameForumRemoveReply(int reply_idx, String member_id);

	List<GameForumReplyListVO> gameForumReplyList(int content_idx, int startNum);
	
	int searchAuthcode(String email);

	int resetAuthCode(String email);

	int inputAuthCode(String email, String authcode);

	int authcodeCheck(String email, String authcode);

	GameForumLoginVO gameForumLogin(String member_id, String password); //API_0000

	List<GameForumGetLikesListVO> gameForumGetLikesList(String member_id, int startNum);

	List<GameForumGetLikesListVO> gameForumGetLikesListWithKeywords(String member_id, String title, String content,
			String nickname, int startNum);

	

	

	

//	List<gameForumListVO> gameForumList(int startNum);
}
