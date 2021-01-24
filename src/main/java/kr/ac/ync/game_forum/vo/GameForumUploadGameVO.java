package kr.ac.ync.game_forum.vo;

public class GameForumUploadGameVO {
	String appids;
	String gametitle;
	String member_id;
	String thumbnail;
	/**
	 * @param appids     등록할 게임번호
	 * @param gametitle  등록할 게임명
	 * @param member_id  등록자
	 * @param thumbnail  등록할 썸네일
	 */
	public GameForumUploadGameVO(String appids, String gametitle, String member_id, String thumbnail) {
		super();
		this.appids = appids;
		this.gametitle = gametitle;
		this.member_id = member_id;
		this.thumbnail = thumbnail;
	}
	public String getAppids() {
		return appids;
	}
	public void setAppids(String appids) {
		this.appids = appids;
	}
	public String getGametitle() {
		return gametitle;
	}
	public void setGametitle(String gametitle) {
		this.gametitle = gametitle;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
}
