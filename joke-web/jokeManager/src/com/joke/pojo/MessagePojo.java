package com.joke.pojo;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_message")
public class MessagePojo extends BasePojo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8142828158365582233L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;
	/** 定制指令 **/
	@Column(name = "message")
	private String message;
	/** 长号 **/
	@Column(name = "long_code")
	private String longCode;
	/** 日限制 **/
	@Column(name = "day_limit")
	private Integer dayLimit;
	/** 月限制 **/
	@Column(name = "mon_limit")
	private Integer monLimit;
	/** 日流量限制 **/
	@Column(name = "dflow_limit")
	private Integer dflowLimit;
	/** 月流量限制 **/
	@Column(name = "mflow_limit")
	private Integer mflowLimit;
	/** 屏蔽关键字 **/
	@Column(name = "keyword")
	private String keyword;
	/** 二次确认状态 0 未知 1 不需要二次确认 2 首条二次确认 3 每条都二次确认 **/
	@Column(name = "sec_type")
	private Integer secType;
	/** 二次确认回复类型 0 未知 1 不用回复 2 任何字符 3 特定字符 4密码 5 问题 **/
	@Column(name = "reply_type")
	private String replyType;
	@Column(name = "dflow")
	private Integer dflowSta;
	@Column(name = "mflow")
	private Integer mflowSta;

	@Transient
	private String validate;
	@Transient
	private int pageNo;
	@Transient
	private int pageSize;
	@Transient
	private int totalProperty;
	@Transient
	private String sortModel;
	@Transient
	private String sortName;
	@Transient
	private String ids;
	@Transient
	private String deduct;
	@Transient
	private String parentId;

	public MessagePojo() {
		super();
	}

	/**
	 * @return the id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the longCode
	 */
	public String getLongCode() {
		return longCode;
	}

	/**
	 * @param longCode
	 *            the longCode to set
	 */
	public void setLongCode(String longCode) {
		this.longCode = longCode;
	}

	/**
	 * @return the dayLimit
	 */
	public Integer getDayLimit() {
		return dayLimit;
	}

	/**
	 * @param dayLimit
	 *            the dayLimit to set
	 */
	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	/**
	 * @return the monLimit
	 */
	public Integer getMonLimit() {
		return monLimit;
	}

	/**
	 * @param monLimit
	 *            the monLimit to set
	 */
	public void setMonLimit(Integer monLimit) {
		this.monLimit = monLimit;
	}

	/**
	 * @return the dflowLimit
	 */
	public Integer getDflowLimit() {
		return dflowLimit;
	}

	/**
	 * @param dflowLimit
	 *            the dflowLimit to set
	 */
	public void setDflowLimit(Integer dflowLimit) {
		this.dflowLimit = dflowLimit;
	}

	/**
	 * @return the mflowLimit
	 */
	public Integer getMflowLimit() {
		return mflowLimit;
	}

	/**
	 * @param mflowLimit
	 *            the mflowLimit to set
	 */
	public void setMflowLimit(Integer mflowLimit) {
		this.mflowLimit = mflowLimit;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the secType
	 */
	public Integer getSecType() {
		return secType;
	}

	/**
	 * @param secType
	 *            the secType to set
	 */
	public void setSecType(Integer secType) {
		this.secType = secType;
	}

	/**
	 * @return the replyMsg
	 */
	public String getReplyType() {
		return replyType;
	}

	/**
	 * @param replyMsg
	 *            the replyMsg to set
	 */
	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}

	/**
	 * @return the validate
	 */
	public String getValidate() {
		return validate;
	}

	/**
	 * @param validate
	 *            the validate to set
	 */
	public void setValidate(String validate) {
		this.validate = validate;
	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the totalProperty
	 */
	public int getTotalProperty() {
		return totalProperty;
	}

	/**
	 * @param totalProperty
	 *            the totalProperty to set
	 */
	public void setTotalProperty(int totalProperty) {
		this.totalProperty = totalProperty;
	}

	/**
	 * @return the sortModel
	 */
	public String getSortModel() {
		return sortModel;
	}

	/**
	 * @param sortModel
	 *            the sortModel to set
	 */
	public void setSortModel(String sortModel) {
		this.sortModel = sortModel;
	}

	/**
	 * @return the sortName
	 */
	public String getSortName() {
		return sortName;
	}

	/**
	 * @param sortName
	 *            the sortName to set
	 */
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids
	 *            the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @return the deduct
	 */
	public String getDeduct() {
		return deduct;
	}

	/**
	 * @param deduct
	 *            the deduct to set
	 */
	public void setDeduct(String deduct) {
		this.deduct = deduct;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId
	 *            the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the dflowSta
	 */
	public Integer getDflowSta() {
		return dflowSta;
	}

	/**
	 * @param dflowSta
	 *            the dflowSta to set
	 */
	public void setDflowSta(Integer dflowSta) {
		this.dflowSta = dflowSta;
	}

	/**
	 * @return the mflowSta
	 */
	public Integer getMflowSta() {
		return mflowSta;
	}

	/**
	 * @param mflowSta
	 *            the mflowSta to set
	 */
	public void setMflowSta(Integer mflowSta) {
		this.mflowSta = mflowSta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessagePojo [id=" + id + ", message=" + message + ", longCode="
				+ longCode + ", dayLimit=" + dayLimit + ", monLimit="
				+ monLimit + ", dflowLimit=" + dflowLimit + ", mflowLimit="
				+ mflowLimit + ", keyword=" + keyword + ", secType=" + secType
				+ ", replyType=" + replyType + ", dflowSta=" + dflowSta
				+ ", mflowSta=" + mflowSta + ", validate=" + validate
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", totalProperty=" + totalProperty + ", sortModel="
				+ sortModel + ", sortName=" + sortName + ", ids=" + ids
				+ ", deduct=" + deduct + ", parentId=" + parentId + "]";
	}

}
