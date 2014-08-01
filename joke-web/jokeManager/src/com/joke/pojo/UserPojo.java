package com.joke.pojo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_user")
public class UserPojo extends BasePojo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8142828158365582233L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "channel")
	private String channel;
	@Column(name = "nick_name")
	private String nickName;
	@Column(name = "login_name")
	private String loginName;
	@Column(name = "pass_word")
	private String userPaWord;
	@Column(name = "role")
	private Integer userRole;
	@Column(name = "address")
	private String address;
	@Column(name = "company")
	private String company;
	@Column(name = "department")
	private String department;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;
	@Column(name = "qq_num")
	private String qqnum;
	@Column(name = "province")
	private String province;
	@Column(name = "city")
	private String city;
	@Column(name = "account")
	private String account;
	@Column(name = "which_bank")
	private String whichBank;
	@Column(name = "contact")
	private String contact;
	@Column(name = "web_name")
	private String joinNetName;
	@Column(name = "web_url")
	private String joinNetUrl;
	@Column(name = "bus_scope")
	private String busScope;
	@Column(name = "lastlogin_time", columnDefinition = "timestamp")
	private Timestamp lastLoginTime;
	@Column(name = "create_time", columnDefinition = "timestamp")
	private Timestamp createTime;
	@Column(name = "modify_time", columnDefinition = "timestamp")
	private Timestamp updateTime;
	@Column(name = "create_user")
	private String createUser;
	@Column(name = "modify_user")
	private String modifyUser;
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
	@Transient
	private Integer charge;
	@Transient
	private String startTime;
	@Transient
	private String endTime;
	@Transient
	private String repassword;
	@Transient
	private String oldpassword;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}

	public UserPojo() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserPaWord() {
		return userPaWord;
	}

	public void setUserPaWord(String userPaWord) {
		this.userPaWord = userPaWord;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDeduct() {
		return deduct;
	}

	public void setDeduct(String deduct) {
		this.deduct = deduct;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the qqnum
	 */
	public String getQqnum() {
		return qqnum;
	}

	/**
	 * @param qqnum
	 *            the qqnum to set
	 */
	public void setQqnum(String qqnum) {
		this.qqnum = qqnum;
	}

	/**
	 * @return the joinNetName
	 */
	public String getJoinNetName() {
		return joinNetName;
	}

	/**
	 * @param joinNetName
	 *            the joinNetName to set
	 */
	public void setJoinNetName(String joinNetName) {
		this.joinNetName = joinNetName;
	}

	/**
	 * @return the busScope
	 */
	public String getBusScope() {
		return busScope;
	}

	/**
	 * @param busScope
	 *            the busScope to set
	 */
	public void setBusScope(String busScope) {
		this.busScope = busScope;
	}

	/**
	 * @return the whichBank
	 */
	public String getWhichBank() {
		return whichBank;
	}

	/**
	 * @param whichBank
	 *            the whichBank to set
	 */
	public void setWhichBank(String whichBank) {
		this.whichBank = whichBank;
	}

	/**
	 * @return the joinNetUrl
	 */
	public String getJoinNetUrl() {
		return joinNetUrl;
	}

	/**
	 * @param joinNetUrl
	 *            the joinNetUrl to set
	 */
	public void setJoinNetUrl(String joinNetUrl) {
		this.joinNetUrl = joinNetUrl;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the repassword
	 */
	public String getRepassword() {
		return repassword;
	}

	/**
	 * @param repassword
	 *            the repassword to set
	 */
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	/**
	 * @return the oldpassword
	 */
	public String getOldpassword() {
		return oldpassword;
	}

	/**
	 * @param oldpassword
	 *            the oldpassword to set
	 */
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

}
