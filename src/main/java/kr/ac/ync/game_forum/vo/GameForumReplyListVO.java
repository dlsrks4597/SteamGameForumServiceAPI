package kr.ac.ync.game_forum.vo;

import java.util.Date;

public class GameForumReplyListVO {
	int replyNum;
	String nickname;
	String reply_content;
	Date regdate;
	/**
	 * @param replyNum      답글번호
	 * @param nickname      답글 작성자 닉네임
	 * @param reply_content 답글 내용
	 * @param regdate       답글 등록일자
	 */
	public GameForumReplyListVO(int replyNum, String nickname, String reply_content, Date regdate) {
		super();
		this.replyNum = replyNum;
		this.nickname = nickname;
		this.reply_content = reply_content;
		this.regdate = regdate;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
	
}
