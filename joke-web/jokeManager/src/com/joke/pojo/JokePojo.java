package com.joke.pojo;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

@Entity
@Table(name = "tb_joke")
public class JokePojo extends JokeBase {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8142828158365582233L;
	
	@Column(name = "userid")
	private int userId;
	@Column(name = "msg")
	private String msg;
	@Column(name = "type")
	private int type;
	@Column(name = "img")
	private String url;
	@Column(name = "good")
	private int good;
	@Column(name = "bad")
	private int bad;
	@Column(name = "talk")
	private int talk;

	public JokePojo() {
		super();
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the good
	 */
	public int getGood() {
		return good;
	}

	/**
	 * @param good
	 *            the good to set
	 */
	public void setGood(int good) {
		this.good = good;
	}

	/**
	 * @return the bad
	 */
	public int getBad() {
		return bad;
	}

	/**
	 * @param bad
	 *            the bad to set
	 */
	public void setBad(int bad) {
		this.bad = bad;
	}

	/**
	 * @return the talk
	 */
	public int getTalk() {
		return talk;
	}

	/**
	 * @param talk
	 *            the talk to set
	 */
	public void setTalk(int talk) {
		this.talk = talk;
	}

}
