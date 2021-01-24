package kr.ac.ync.game_forum.vo;

public class GameForumModifyGameVO {
	int game_idx;
	String appids;
	String gametitle;
	String member_id;
	String thumbnail;
	/**
	 * @param game_idx  게임 등록 번호 
	 * @param appids    게임 코드
	 * @param gametitle 게임명
	 * @param member_id 게임을 등록한 사용자 아이디
	 * @param thumbnail 썸네일 이미지
	 */
	public GameForumModifyGameVO(int game_idx, String appids, String gametitle, String member_id, String thumbnail) {
		super();
		this.game_idx = game_idx;
		this.appids = appids;
		this.gametitle = gametitle;
		this.member_id = member_id;
		this.thumbnail = thumbnail;
	}
	public int getGame_idx() {
		return game_idx;
	}
	public void setGame_idx(int game_idx) {
		this.game_idx = game_idx;
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
