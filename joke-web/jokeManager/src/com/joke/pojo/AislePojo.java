package com.joke.pojo;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

@Entity
@Table(name = "tb_aisleinfo")
public class AislePojo extends BasePojo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8142828158365582233L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/** cp **/
	@Column(name = "cp")
	private String cp;
	/** 金额 **/
	@Column(name = "price")
	private Double price;
	/** 计费类型 **/
	@Column(name = "type")
	private Integer type;
	/** 详细 **/
	@Column(name = "memo")
	private String memo;
	/** 分成比例 **/
	@Column(name = "saparate")
	private Double saparate;
	/** 省份 **/
	@Column(name = "province")
	private String province;
	/** 运营商 1 移动 2 联通 3 电信 **/
	@Column(name = "operators")
	private Integer simCard;
	/** 屏蔽地区 **/
	@Column(name = "mask_area")
	private String maskArea;
	/** 黑名单 **/
	@Column(name = "blacklist")
	private String blacklist;
	/** 计费类型 **/
	@Column(name = "charge")
	private Integer charge;
	/** 通道详细 **/
	@Column(name = "fcharge")
	private BigInteger fcharge;
	/** 创建时间 **/
	@Column(name = "create_time")
	private Timestamp createTime;
	/** 创建人 **/
	@Column(name = "create_user")
	private String createUser;
	/** 更新时间 **/
	@Column(name = "modify_time")
	private Timestamp modifyTime;
	/** 更新人 **/
	@Column(name = "modify_user")
	private String modifyUser;
	/** 状态 **/
	@Column(name = "status")
	private Integer status;

	// @OneToOne(mappedBy = "id", cascade = CascadeType.ALL, optional = false)
	@Transient
	private MessagePojo messagePojo;

	/** 定制指令 **/
	@Transient
	private String message;
	/** 长号 **/
	@Transient
	private String longCode;
	/** 日限制 **/
	@Transient
	private Integer dayLimit;
	/** 月限制 **/
	@Transient
	private Integer monLimit;
	/** 日流量限制 **/
	@Transient
	private Integer dflowLimit;
	/** 月流量限制 **/
	@Transient
	private Integer mflowLimit;
	/** 屏蔽关键字 **/
	@Transient
	private String keyword;
	/** 二次确认状态 0 未知 1 不需要二次确认 2 首条二次确认 3 每条都二次确认 **/
	@Transient
	private Integer secType;
	/** 二次回复信息 **/
	@Transient
	private String secMsg;
	/** 二次确认回复类型 0 未知 1 不用回复 2 任何字符 3 特定字符 4密码 5 问题 **/
	@Transient
	private String replyMsg;
	@Transient
	private String replyType;
	@Transient
	private Integer dflowSta;
	@Transient
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

	public AislePojo() {
		super();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp
	 *            the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the charge
	 */
	public Integer getCharge() {
		return charge;
	}

	/**
	 * @param charge
	 *            the charge to set
	 */
	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	/**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo
	 *            the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the saparate
	 */
	public Double getSaparate() {
		return saparate;
	}

	/**
	 * @param saparate
	 *            the saparate to set
	 */
	public void setSaparate(Double saparate) {
		this.saparate = saparate;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the simCard
	 */
	public Integer getSimCard() {
		return simCard;
	}

	/**
	 * @param simCard
	 *            the simCard to set
	 */
	public void setSimCard(Integer simCard) {
		this.simCard = simCard;
	}

	/**
	 * @return the maskArea
	 */
	public String getMaskArea() {
		return maskArea;
	}

	/**
	 * @param maskArea
	 *            the maskArea to set
	 */
	public void setMaskArea(String maskArea) {
		this.maskArea = maskArea;
	}

	/**
	 * @return the blacklist
	 */
	public String getBlacklist() {
		return blacklist;
	}

	/**
	 * @param blacklist
	 *            the blacklist to set
	 */
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser
	 *            the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the modifyTime
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the modifyUser
	 */
	public String getModifyUser() {
		return modifyUser;
	}

	/**
	 * @param modifyUser
	 *            the modifyUser to set
	 */
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * @return the fcharge
	 */
	public BigInteger getFcharge() {
		return fcharge;
	}

	/**
	 * @param fcharge
	 *            the fcharge to set
	 */
	public void setFcharge(BigInteger fcharge) {
		this.fcharge = fcharge;
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
	 * @return the secMsg
	 */
	public String getSecMsg() {
		return secMsg;
	}

	/**
	 * @param secMsg
	 *            the secMsg to set
	 */
	public void setSecMsg(String secMsg) {
		this.secMsg = secMsg;
	}

	/**
	 * @return the replyMsg
	 */
	public String getReplyMsg() {
		return replyMsg;
	}

	/**
	 * @param replyMsg
	 *            the replyMsg to set
	 */
	public void setReplyMsg(String replyMsg) {
		this.replyMsg = replyMsg;
	}

	/**
	 * @return the messagePojo
	 */
	public MessagePojo getMessagePojo() {
		return messagePojo;
	}

	/**
	 * @param messagePojo
	 *            the messagePojo to set
	 */
	public void setMessagePojo(MessagePojo messagePojo) {
		this.messagePojo = messagePojo;
	}

	/**
	 * @return the replyType
	 */
	public String getReplyType() {
		return replyType;
	}

	/**
	 * @param replyType
	 *            the replyType to set
	 */
	public void setReplyType(String replyType) {
		this.replyType = replyType;
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
		return "AislePojo [id=" + id + ", cp=" + cp + ", price=" + price
				+ ", type=" + type + ", memo=" + memo + ", saparate="
				+ saparate + ", province=" + province + ", simCard=" + simCard
				+ ", maskArea=" + maskArea + ", blacklist=" + blacklist
				+ ", charge=" + charge + ", fcharge=" + fcharge
				+ ", createTime=" + createTime + ", createUser=" + createUser
				+ ", modifyTime=" + modifyTime + ", modifyUser=" + modifyUser
				+ ", status=" + status + ", messagePojo=" + messagePojo
				+ ", message=" + message + ", longCode=" + longCode
				+ ", dayLimit=" + dayLimit + ", monLimit=" + monLimit
				+ ", dflowLimit=" + dflowLimit + ", mflowLimit=" + mflowLimit
				+ ", keyword=" + keyword + ", secType=" + secType + ", secMsg="
				+ secMsg + ", replyMsg=" + replyMsg + ", replyType="
				+ replyType + ", dflowSta=" + dflowSta + ", mflowSta="
				+ mflowSta + ", validate=" + validate + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", totalProperty=" + totalProperty
				+ ", sortModel=" + sortModel + ", sortName=" + sortName
				+ ", ids=" + ids + ", deduct=" + deduct + ", parentId="
				+ parentId + "]";
	}

}
