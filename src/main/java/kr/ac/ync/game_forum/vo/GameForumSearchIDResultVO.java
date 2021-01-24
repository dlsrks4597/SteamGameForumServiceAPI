package kr.ac.ync.game_forum.vo;

public class GameForumSearchIDResultVO {
	String member_id;
	String nickname;
	String email;
	/**
	 * @param member_id 검색결과로 출력될 아이디
	 * @param nickname  검색결과로 출력될 닉네임
	 * @param email     검색결과로 출력될 이메일
	 */
	public GameForumSearchIDResultVO(String member_id, String nickname, String email) {
		super();
		this.member_id = member_id;
		this.nickname = nickname;
		this.email = email;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
