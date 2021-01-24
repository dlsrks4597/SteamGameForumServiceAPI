package kr.ac.ync.game_forum.vo;

import java.util.Date;

public class GameForumGetLikesListVO {
	int content_idx;
	int board_code;
	String title;
	String nickname;
	String image;
	String likes;
	Date contentRegdate;
	Date likesRegdate;
	/**
	 * @param content_idx    좋아요 로 지정된 게시글 번호
	 * @param board_code     게시판 분류 코드
	 * @param title          게시글 제목
	 * @param nickname       게시글 작성자 닉네임
	 * @param image          게시글에 등록된 이미지
	 * @param likes          게시글이 좋아요 를 받은 수
	 * @param contentRegdate 게시글 등록일자
	 * @param likesRegdate   사용자가 좋아요 를 남긴 일자
	 */
	public GameForumGetLikesListVO(int content_idx, int board_code, String title, String nickname, String image,
			String likes, Date contentRegdate, Date likesRegdate) {
		super();
		this.content_idx = content_idx;
		this.board_code = board_code;
		this.title = title;
		this.nickname = nickname;
		this.image = image;
		this.likes = likes;
		this.contentRegdate = contentRegdate;
		this.likesRegdate = likesRegdate;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public Date getContentRegdate() {
		return contentRegdate;
	}
	public void setContentRegdate(Date contentRegdate) {
		this.contentRegdate = contentRegdate;
	}
	public Date getLikesRegdate() {
		return likesRegdate;
	}
	public void setLikesRegdate(Date likesRegdate) {
		this.likesRegdate = likesRegdate;
	}
	
	
}
