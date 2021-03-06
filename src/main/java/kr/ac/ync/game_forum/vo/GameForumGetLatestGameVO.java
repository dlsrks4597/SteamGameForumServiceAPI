package kr.ac.ync.game_forum.vo;

public class GameForumGetLatestGameVO {
	int appids;
	String gametitle;
	String nickname;
	String thumbnail;
	String regdate;
	/**
	 * @param appids    게임 고유번호
	 * @param gametitle 게임명
	 * @param nickname  게임을 등록한 사용자 닉네임
	 * @param thumbnail 등록된 게임 썸네일
	 * @param regdate   게임 등록 일자
	 */
	public GameForumGetLatestGameVO(int appids, String gametitle, String nickname, String thumbnail, String regdate) {
		super();
		this.appids = appids;
		this.gametitle = gametitle;
		this.nickname = nickname;
		this.thumbnail = thumbnail;
		this.regdate = regdate;
	}
	public int getAppids() {
		return appids;
	}
	public void setAppids(int appids) {
		this.appids = appids;
	}
	public String getGametitle() {
		return gametitle;
	}
	public void setGametitle(String gametitle) {
		this.gametitle = gametitle;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
}
