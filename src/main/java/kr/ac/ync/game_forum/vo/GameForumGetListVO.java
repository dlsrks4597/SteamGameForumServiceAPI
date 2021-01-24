package kr.ac.ync.game_forum.vo;

public class GameForumGetListVO {
	int content_idx;
	int board_code;
	String title;
	int appids;
	String nickname;
	String content;
	String image;
	int likes;
	String regdate;
	/**
	 * @param content_idx 게시글 번호
	 * @param board_code  게시판 분류 코드
	 * @param title       게시글 제목
	 * @param appids      게시글에 등록된 게임번호
	 * @param nickname    게시글 작성자 닉네임
	 * @param content     게시글 내용
	 * @param image       게시글에 등록된 이미지
	 * @param likes       게시글이 좋아요 를 받은 수
	 * @param regdate     게시글 등록일자
	 */
	public GameForumGetListVO(int content_idx, int board_code, String title, int appids, String nickname,
			String content, String image, int likes, String regdate) {
		super();
		this.content_idx = content_idx;
		this.board_code = board_code;
		this.title = title;
		this.appids = appids;
		this.nickname = nickname;
		this.content = content;
		this.image = image;
		this.likes = likes;
		this.regdate = regdate;
	}
	public int getContent_idx() {
		return content_idx;
	}
	public void setContent_idx(int content_idx) {
		this.content_idx = content_idx;
	}
	public int getBoard_code() {
		return board_code;
	}
	public void setBoard_code(int board_code) {
		this.board_code = board_code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAppids() {
		return appids;
	}
	public void setAppids(int appids) {
		this.appids = appids;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
}
