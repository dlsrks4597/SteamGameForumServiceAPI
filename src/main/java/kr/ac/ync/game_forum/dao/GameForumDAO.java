package kr.ac.ync.game_forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

@Repository
@Mapper
public interface GameForumDAO {

	public String getFilePath(int idx);

	public int gameForumDelete(int idx);

	public int updateGameForum(int idx, String title, String content, String img_file);
	
	public int gameForumUploadGame(GameForumUploadGameVO gameForumuploadGameVO);

	public int gameForumRegist(GameForumRegistVO gameForumRegistVO);

	public GameForumLoginVO gameForumLogin(String member_id, String password); //API_0000

	public int gameForumIdCheck(String member_id);

	public int gameForumSearchID(String email);
	
	public GameForumSearchIDResultVO gameForumSearchIDResult(String email);

	public int gameForumModifyPW(String member_id, String password);

	public GameForumGetProfileVO gameForumGetProfile(String member_id);

	public List<GameForumGetLatestForumVO> gameForumGetLatestForum(int board_code) throws Exception;

	public List<GameForumGetLatestGameVO> gameForumGetLatestGame() throws Exception;

	public int gameForumModifyProfile(GameForumModifyProfileVO gameForumModifyProfileVO);

	public String memberImagePathGet(String member_id);

	public List<GameForumGetListVO> gameForumGetList(int board_code, int startNum) throws Exception;

	public List<GameForumGetListVO> gameForumGetListWithKeywords(String title, String content, String nickname,
			int board_code, int startNum);
	
	public List<GameForumGetGameListVO> gameForumGetGameList(int startNum);
	
	public List<GameForumGetGameListVO> gameForumGetGameListWithKeywords(String gametitle, int startNum);

	public GameForumGetViewVO gameForumGetView(int content_idx);

	public int gameForumGetLikes(String member_id, int content_idx);

	public int gameForumInsertLikes(String member_id, int content_idx);
	
	public int gameForumCancelLikes(String member_id, int content_idx);

	public int gameForumInsertForum(GameForumInsertForumVO gameForumInsertForumVO);

	public String forumImagePathGet(int content_idx);

	public int gameForumModifyForum(GameForumModifyForumVO gameForumModifyForumVO);
	
	public int gameForumRemoveForum(int content_idx, String member_id);

	public int gameForumModifyGame(GameForumModifyGameVO gameForumModifyGameVO);

	public String gameThumbnailPathGet(int game_idx);

	public int gameForumRemoveGame(int game_idx, String member_id);

	public int gameForumInsertReply(int content_idx, String member_id, String reply_content);

	public int gameForumModifyReply(int reply_idx, String member_id, String reply_content);

	public int gameForumRemoveReply(int reply_idx, String member_id);

	public List<GameForumReplyListVO> gameForumReplyList(int content_idx, int startNum);

	public int searchAuthcode(String email);
	
	public int resetAuthCode(String member_id);

	public int inputAuthCode(String email, String authcode);

	public int authcodeCheck(String email, String authcode);

	public List<GameForumGetLikesListVO> gameForumGetLikesList(String member_id, int startNum);

	public List<GameForumGetLikesListVO> gameForumGetLikesListWithKeywords(String member_id, String title,
			String content, String nickname, int startNum);




}
