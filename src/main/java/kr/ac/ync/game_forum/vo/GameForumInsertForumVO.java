package kr.ac.ync.game_forum.vo;

public class GameForumInsertForumVO {
	int board_code;
	String title;
	int appids;
	String member_id;
	String content;
	String image;
	/**
	 * @param board_code 게시판 분류코드
	 * @param title      게시글 제목
	 * @param appids     게시글에 등록할 게임번호
	 * @param member_id  게시글을 등록할 사용자 아이디
	 * @param content    게시글 내용
	 * @param image      게시글에 등록할 이미지
	 */
	public GameForumInsertForumVO(int board_code, String title, int appids, String member_id, String content,
			String image) {
		super();
		this.board_code = board_code;
		this.title = title;
		this.appids = appids;
		this.member_id = member_id;
		this.content = content;
		this.image = image;
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
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
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
	
}
