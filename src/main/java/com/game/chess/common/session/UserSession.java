package com.game.chess.common.session;

/**
 * 
 * @Description Session对象
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
public class UserSession extends SessionObject {
	private static final long serialVersionUID = 1L;

	private String userName;// 用户名称
	private String name;// 真实姓名
	private String mobile;// 手机号码
	private String email;// 邮箱

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
