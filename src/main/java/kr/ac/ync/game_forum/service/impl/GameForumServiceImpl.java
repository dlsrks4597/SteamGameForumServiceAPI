package kr.ac.ync.game_forum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.ync.game_forum.dao.GameForumDAO;
import kr.ac.ync.game_forum.service.GameForumService;
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

@Service
public class GameForumServiceImpl implements GameForumService{

	@Autowired
	GameForumDAO gameForumDao;



	@Override
	public String getFilePath(int idx) {
		// TODO Auto-generated method stub
		String filePath = "";
		try {
			return gameForumDao.getFilePath(idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int deleteGameForum(int idx) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumDelete(idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
//	@Override
//	public int updateGameForum(GameForumUpdateVO updateVO, int idx) {
//		try {
//			return gameForumDao.updateGameForum(updateVO, idx);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}


	@Override
	public int updateGameForum(int idx, String title, String content, String img_file) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.updateGameForum(idx, title, content, img_file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int gameForumUploadGame(GameForumUploadGameVO gameForumuploadGameVO) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumUploadGame(gameForumuploadGameVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int gameForumRegist(GameForumRegistVO gameForumRegistVO) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumRegist(gameForumRegistVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public GameForumLoginVO gameForumLogin(String member_id, String password) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumLogin(member_id, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public int gameForumIdCheck(String member_id) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumIdCheck(member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

//	@Override
//	public int updateGameForum(GameForumUpdateVO updateVO, int idx) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public int gameForumSearchID(String email) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumSearchID(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public GameForumSearchIDResultVO gameForumSearchIDResult(String email) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumSearchIDResult(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int gameForumModifyPW(String member_id, String password) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumModifyPW(member_id, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public GameForumGetProfileVO gameForumGetProfile(String member_id) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetProfile(member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<GameForumGetLatestForumVO> gameForumGetLatestForum(int board_code) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetLatestForum(board_code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GameForumGetLatestGameVO> gameForumGetLatestGame() {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetLatestGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int gameForumModifyProfile(GameForumModifyProfileVO gameForumModifyProfileVO) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumModifyProfile(gameForumModifyProfileVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String memberImagePathGet(String member_id) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.memberImagePathGet(member_id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GameForumGetListVO> gameForumGetList(int board_code, int startNum) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetList(board_code, startNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<GameForumGetListVO> gameForumGetListWithKeywords(String title, String content, String nickname, int board_code,
			int startNum) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetListWithKeywords(title, content, nickname, board_code, startNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GameForumGetGameListVO> gameForumGetGameList(int startNum) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetGameList(startNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<GameForumGetGameListVO> gameForumGetGameListWithKeywords(String gametitle, int startNum) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetGameListWithKeywords(gametitle, startNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public GameForumGetViewVO gameForumGetView(int content_idx) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetView(content_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int gameForumGetLikes(String member_id, int content_idx) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetLikes(member_id, content_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int gameForumCancelLikes(String member_id, int content_idx) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumCancelLikes(member_id, content_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int gameForumInsertLikes(String member_id, int content_idx) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumInsertLikes(member_id, content_idx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insertForum(GameForumInsertForumVO gameForumInsertForumVO) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumInsertForum(gameForumInsertForumVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String forumImagePathGet(int content_idx) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.forumImagePathGet(content_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int gameForumModifyForum(GameForumModifyForumVO gameForumModifyForumVO) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumModifyForum(gameForumModifyForumVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int gameForumRemoveForum(int content_idx, String member_id) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumRemoveForum(content_idx, member_id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int gameForumModifyGame(GameForumModifyGameVO gameForumModifyGameVO) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumModifyGame(gameForumModifyGameVO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String gameThumbnailPathGet(int game_idx) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameThumbnailPathGet(game_idx);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int gameForumRemoveGame(int game_idx, String member_id) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumRemoveGame(game_idx, member_id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int gameForumInsertReply(int content_idx, String member_id, String reply_content) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumInsertReply(content_idx, member_id, reply_content);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int gameForumModifyReply(int reply_idx, String member_id, String reply_content) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumModifyReply(reply_idx, member_id, reply_content);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int gameForumRemoveReply(int reply_idx, String member_id) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumRemoveReply(reply_idx, member_id);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<GameForumReplyListVO> gameForumReplyList(int content_idx, int startNum) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumReplyList(content_idx, startNum);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int searchAuthcode(String email) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.searchAuthcode(email);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int resetAuthCode(String email) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.resetAuthCode(email);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int inputAuthCode(String email, String authcode) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.inputAuthCode(email, authcode);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int authcodeCheck(String email, String authcode) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.authcodeCheck(email, authcode);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public List<GameForumGetLikesListVO> gameForumGetLikesList(String member_id, int startNum) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetLikesList(member_id, startNum);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<GameForumGetLikesListVO> gameForumGetLikesListWithKeywords(String member_id, String title, String content,
			String nickname, int startNum) {
		// TODO Auto-generated method stub
		try {
			return gameForumDao.gameForumGetLikesListWithKeywords(member_id, title, content, nickname, startNum);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
	

	



	



	

//	@Override
//	public List<gameForumListVO> gameForumList() throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
