package kr.ac.ync.game_forum.vo;

public class GameForumModifyProfileVO {
	String member_id;
	String name;
	String email;
	String nickname;
	String password;
	String profile;
	String image;
	/**
	 * @param member_id 사용자 아이디
	 * @param name      수정할 사용자 성명
	 * @param email     수정할 사용자 이메일
	 * @param nickname  수정할 사용자 닉네임
	 * @param password  수정할 사용자 비밀번호
	 * @param profile   수정할 프로필내용
	 * @param image     수정할 이미지
	 */
	public GameForumModifyProfileVO(String member_id, String name, String email, String nickname, String password,
			String profile, String image) {
		super();
		this.member_id = member_id;
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.profile = profile;
		this.image = image;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
}
