package com.joke.pojo;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_paylog")
public class PayDetailPojo extends BasePojo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8142828158365582233L;

	@Id
	@Column(name = "id")//主键
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "orders")//订单号
	private String orderNum;
	@Column(name = "deviceid")//手机唯一标示符 
	private String deviceid;
	@Column(name = "deviceid")//通道编码
	private String aisleId;
	@Column(name = "channel")
	private String channel;//渠道
	@Column(name = "app_code")
	private Integer appCode;//应用编码
	@Column(name = "pay_code")//支付编码
	private Integer payCode;
	@Column(name = "phone")//手机号码
	private Integer phone;
	@Column(name = "message")//指令
	private String message;
	@Column(name = "longcode")//长号
	private String longCode;
	@Column(name = "price")
	private Integer price;
	@Column(name = "imsi")
	private String imsi;//imsi
	@Column(name = "pay_time")
	private Timestamp payTime;//支付时间
	@Column(name = "motime")
	private Timestamp moTime;//上行时间
	@Column(name = "mrtime")
	private Timestamp mrTime;//状态报告时间
	@Column(name = "link_id")
	private String linkId;//上行时间
	@Column(name = "status")
	private Integer status;
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

	public PayDetailPojo() {
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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getAisleId() {
		return aisleId;
	}

	public void setAisleId(String aisleId) {
		this.aisleId = aisleId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getAppCode() {
		return appCode;
	}

	public void setAppCode(Integer appCode) {
		this.appCode = appCode;
	}

	public Integer getPayCode() {
		return payCode;
	}

	public void setPayCode(Integer payCode) {
		this.payCode = payCode;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLongCode() {
		return longCode;
	}

	public void setLongCode(String longCode) {
		this.longCode = longCode;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public Timestamp getMoTime() {
		return moTime;
	}

	public void setMoTime(Timestamp moTime) {
		this.moTime = moTime;
	}

	public Timestamp getMrTime() {
		return mrTime;
	}

	public void setMrTime(Timestamp mrTime) {
		this.mrTime = mrTime;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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


}
