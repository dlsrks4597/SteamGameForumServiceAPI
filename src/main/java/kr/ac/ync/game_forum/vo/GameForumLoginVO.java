package kr.ac.ync.game_forum.vo;

public class GameForumLoginVO {
	
	String member_id;
	String name;
	String email;
	String nickname;
	String profile;
	String image;
	String regdate;
	/**
	 * @param member_id 사용자 아이디
	 * @param name      사용자 성명
	 * @param email     사용자 이메일
	 * @param nickname  사용자 닉네임
	 * @param profile   사용자 프로필내용
	 * @param image     사용자 썸네일 이미지
	 * @param regdate   사용자 가입일자
	 */
	public GameForumLoginVO(String member_id, String name, String email, String nickname,
			String profile, String image, String regdate) {
		super();
		this.member_id = member_id;
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.profile = profile;
		this.image = image;
		this.regdate = regdate;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	

}
