package com.joke.pojo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_chargeinfo")
public class ChargPojo extends BasePojo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8142828158365582233L;

	@Id
	@Column(name = "id")
	// 主键
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "app_id")
	// 应用关联
	private Integer appCode;
	@Column(name = "pay_code")
	// 计费编号
	private String payCode;
	@Column(name = "name")
	// 支付名称
	private String chargeName;
	@Column(name = "price")
	// 价格
	private Double price;
	@Column(name = "type")
	// 支付类型 1 点播 2 定制',
	private Integer type;
	@Column(name = "charge")
	// 支付方式 1 短信 2 net 3支付宝 4充值卡 5点卡 6 余额支付'
	private Integer payType;
	@Column(name = "cha_msg")
	// 计费提示
	private String chaMsg;
	@Column(name = "cha_memo")
	// 计费描述
	private String chaMemo;
	@Column(name = "createTime")
	// 创建时间
	private String createTime;
	@Column(name = "create_user")
	// 创建人
	private String createUser;
	@Column(name = "modify_time")
	// 更新时间
	private String modifyTime;
	@Column(name = "modify_user")
	// 更新人
	private String modifyUser;
	@Column(name = "status")
	// 状态
	private Integer status;

	
	@Transient
	private List<ChargPojo> child;

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
	private String channel;
	@Transient
	private String deduct;
	@Transient
	private String parentId;

	public ChargPojo() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAppCode() {
		return appCode;
	}

	public void setAppCode(Integer appCode) {
		this.appCode = appCode;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getChaMsg() {
		return chaMsg;
	}

	public void setChaMsg(String chaMsg) {
		this.chaMsg = chaMsg;
	}

	public String getChaMemo() {
		return chaMemo;
	}

	public void setChaMemo(String chaMemo) {
		this.chaMemo = chaMemo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<ChargPojo> getChild() {
		return child;
	}

	public void setChild(List<ChargPojo> child) {
		this.child = child;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(int totalProperty) {
		this.totalProperty = totalProperty;
	}

	public String getSortModel() {
		return sortModel;
	}

	public void setSortModel(String sortModel) {
		this.sortModel = sortModel;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDeduct() {
		return deduct;
	}

	public void setDeduct(String deduct) {
		this.deduct = deduct;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

}
