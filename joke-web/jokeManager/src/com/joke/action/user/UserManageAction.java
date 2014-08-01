package com.joke.action.user;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.joke.action.BaseAction;
import com.joke.pojo.UserPojo;
import com.joke.service.UserManageService;
import com.joke.utils.Constant;
import com.joke.utils.DateUtil;

public class UserManageAction extends BaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 5386469775398514743L;
	/** log */
	private static Log log = LogFactory.getLog(UserManageAction.class);

	private UserManageService userManageService;

	private List<UserPojo> root;
	private UserPojo data;
	private String channel;
	private String deduct;

	@Override
	protected String executeProcess() throws Exception {
		log.info("getUserList start");
		try {
			if (null == data) {
				data = getUser();
				data.setPageSize(10);
				data.setPageNo(1);
			}

			data.setUserRole(getUser().getUserRole());
			root = userManageService.getUserList(data);
			totalProperty = Constant.totalProperty;
			if (root == null) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("getUserList failed");
				return "failed";
			} else {
				for (UserPojo userPojo : root) {
					userPojo.setUserPaWord("********");
				}
				setMessage("获取数据成功");
				setResult("success");
				log.info("getUserList success");
				return "success";
			}
		} catch (Exception e) {
			setResult("failed");
			setMessage("添加数据失败");
			log.error("getUserList failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String getChannelList() {
		log.info("getChannelList start");
		try {
			if (data == null) {
				data = getUser();
				data.setPageSize(10);
				data.setPageNo(1);
			}
			UserPojo user = getUser();
			int userId = user.getId();
			String loginName = user.getLoginName();
			data.setCreateUser(loginName);
			root = userManageService.getChannelList(data, userId);
			totalProperty = Constant.totalProperty;
			if (root == null) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("getChannelList failed");
				return "failed";
			} else {
				setResult("success");
				setMessage("获取数据成功");
				log.info("getChannelList success");
				return "success";
			}
		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("getChannelList failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String getChannelByPara() {
		log.info("getChannelByPara start");
		try {
			if (data == null) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("getChannelByPara failed");
				return "failed";
			}
			int userId = getUser().getId();
			root = userManageService.getChannelByPara(data, userId);
			totalProperty = Constant.totalProperty;
			setResult("success");
			setMessage("获取数据成功");
			log.info("getChannelByPara success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error(e);
			log.error("getChannelByPara failed");
			e.printStackTrace();
			return "failed";
		}
	}

	public String updateUserStatu() throws Exception {
		log.info("updateUserStatu start");
		try {

			if (null == data) {
				setMessage("更新失败");
				setResult("failed");
				return "failed";
			}

			data.setModifyUser(getUser().getLoginName());
			data.setUpdateTime(DateUtil.GetNowDateTime());

			String ids = data.getIds();
			String[] idsArr = ids.split(",");
			for (int i = 0; i < idsArr.length; i++) {
				data.setId(Integer.valueOf(idsArr[i]));
				userManageService.updateUserStatu(data);
			}

			setResult("success");
			setMessage("更新成功");
			log.info("updateUserStatu success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("添加数据失败");
			log.error("updateUserStatu failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String updateUChannelStatus() throws Exception {
		log.info("updateUChannelStatus start");
		try {

			if (null == data) {
				setMessage("更新失败");
				setResult("failed");
				return "failed";
			}

			data.setModifyUser(getUser().getLoginName());

			String ids = data.getIds();
			String[] idsArr = ids.split(",");
			int userId = getUser().getId();
			for (int i = 0; i < idsArr.length; i++) {
				data.setUpdateTime(DateUtil.GetNowDateTime());
				data.setId(Integer.valueOf(idsArr[i]));
				userManageService.updateChannelStatus(data, userId);
			}
			setResult("success");
			setMessage("更新成功");
			log.info("updateUChannelStatus success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新失败");
			log.error("updateUChannelStatus failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String insertUser() throws Exception {
		log.info("insertUser start");
		try {

			if (null == data) {
				setMessage("更新失败");
				setResult("failed");
				return "failed";
			}

			int role = getUser().getUserRole();
			if (role != 1 && data.getUserRole() == 1) {
				setMessage("更新失败");
				setResult("failed");
				return "failed";
			} else if (role > data.getUserRole()) {
				setMessage("更新失败");
				setResult("failed");
				return "failed";
			}

			List<UserPojo> userList = userManageService.getUserByName(data);
			if (null == userList || "".equals(userList) || 0 == userList.size()) {
			} else {
				setResult("failed");
				setMessage("此用户已经存在");
				log.info("此用户已经存在");
				return "failed";
			}

			data.setChannel(DateUtil.fileName());
			data.setModifyUser(getUser().getLoginName());
			data.setUpdateTime(DateUtil.GetNowDateTime());
			data.setCreateUser(getUser().getLoginName());
			data.setCreateTime(DateUtil.GetNowDateTime());
			data.setUserPaWord("aaa111");
			// data.setStatus(Constant.NORMAL);
			userManageService.insertUser(data);
			setResult("success");
			setMessage("更新成功");
			log.info("insertUser success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("添加数据失败");
			log.error("insertUser failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String insertChannel() throws Exception {
		log.info("insertUser start");
		try {

			if (null == data) {
				setMessage("更新失败");
				setResult("failed");
				return "failed";
			}

			List<UserPojo> userList = userManageService.getUserByName(data);
			if (null == userList || "".equals(userList) || 0 == userList.size()) {
			} else {
				setResult("failed");
				setMessage("此用户已经存在");
				log.info("此用户已经存在");
				return "failed";
			}
			int role = getUser().getUserRole();
			// 如果当前角色为渠道商，创建的默认为子渠道
			if (role == 5) {
				data.setUserRole(8);
			} else {
				data.setUserRole(5);
			}

			// data.setChannel(MD5Utils.GetMD5Code(DateUtil.DateUtilTime()));
			data.setModifyUser(getUser().getLoginName());
			data.setCreateTime(DateUtil.GetNowDateTime());
			data.setCreateUser(getUser().getLoginName());
			data.setUpdateTime(DateUtil.GetNowDateTime());
			int userId = getUser().getId();
			// data.setStatus(Constant.NORMAL);
			userManageService.insertChannel(data, userId);
			setResult("success");
			setMessage("更新成功");
			log.info("insertUser success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("添加数据失败");
			log.error("insertUser failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String updateChannel() {
		log.info("updateChannel start");
		try {
			if (data == null) {
				setResult("failed");
				setMessage("修改数据失败");
				log.info("updateChannel failed");
				return "failed";
			}
			data.setModifyUser(getUser().getLoginName());
			data.setUpdateTime(DateUtil.GetNowDateTime());
			userManageService.updateChannel(data);
			setResult("success");
			setMessage("修改数据成功");
			log.info("updateChannel success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("修改数据失败");
			log.error(e);
			log.error("updateChannel failed");
			e.printStackTrace();
			return "failed";
		}
	}

	public String userInfo() throws Exception {
		log.info("userInfo start");
		try {

			data = getUser();
			if (null == data) {
				setResult("failed");
				setMessage("获取用户失败");
				log.info("userInfo failed");
				return "failed";
			} else {
				data.setUserPaWord("********");
				setResult("success");
				setMessage("用户信息获取成功");
				log.info("userInfo success");
				return "success";
			}
		} catch (Exception e) {
			setResult("failed");
			setMessage("添加数据失败");
			log.error("userInfo failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String userPwdEdit() throws Exception {
		log.info("userPwdEdit start");
		try {

			if (null == data) {
				setMessage("更新失败");
				setResult("failed");
				log.info("userPwdEdit failed");
				return "failed";
			}
			UserPojo user = getUser();
			Integer userId = user.getId();
			String userName = user.getLoginName();
			Integer dataId = data.getId();
			String createUser = data.getCreateUser();
			if (getUser() == null) {
				setResult("timeout");
				setMessage("您登陆后长时间没有使用，请重新登录吧！");
				log.info("您登陆后长时间没有使用，请重新登录吧！");
				return "failed";
			} else if ((!userId.equals(dataId)) && !userName.equals(createUser)) {
				setResult("failed");
				setMessage("修改密码异常!");
				log.info("修改密码异常!");
				return "failed";
			}

			data.setModifyUser(userName);
			data.setUpdateTime(DateUtil.GetNowDateTime());
			data.setId(dataId);
			String result = userManageService.userPwdEdit(data);
			if ("limit".equals(result)) {
				setResult("failed");
				setMessage("此账号是测试用户,不能更改密码!");
				log.info("此账号是测试用户,不能更改密码!");
				return "failed";
			} else if ("erpw".equals(result)) {
				setResult("failed");
				setMessage("旧密码不正确!");
				log.info("旧密码不正确!");
				return "failed";
			}
			setResult("success");
			setMessage("更新成功");
			setResult("success");
			log.info("userPwdEdit success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("修改密码异常!");
			log.error("userPwdEdit failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String userInfoById() throws Exception {
		log.info("userInfoById start");
		try {
			data = userManageService.getUserById(data);
			if (null == data) {
				setResult("failed");
				setMessage("获取用户失败");
				log.info("userInfoById failed");
				return "failed";
			} else {
				data.setUserPaWord("********");
				setResult("success");
				setMessage("用户信息获取成功");
				log.info("userInfoById success");
				return "success";
			}
		} catch (Exception e) {
			setResult("failed");
			setMessage("添加数据失败");
			log.error("userInfoById failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String updateUser() throws Exception {
		log.info("userInfoById start");
		try {
			if (null == data) {
				setMessage("更新失败");
				setResult("failed");
				log.info("userInfoById failed");
				return "failed";
			}

			data.setModifyUser(getUser().getLoginName());
			data.setUpdateTime(DateUtil.GetNowDateTime());
			userManageService.updateUser(data);
			setResult("success");
			setMessage("更新成功");
			setResult("success");
			log.info("userInfoById success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("添加数据失败");
			log.error("userInfoById failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String updateDeduct() {
		log.info("updateDeduct start");
		try {
			userManageService.updateDeduct(data);
			setResult("success");
			setMessage("更新成功");
			log.info("updateUserStatu success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("updateDeduct failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	/***
	 * 验证码 校验
	 * 
	 * @return
	 */
	public String vlidateImg() {
		// 获取session
		String validate = (String) getSession().get(Constant.VALIDATE_STRING);
		String dataValidate = data.getValidate();
		// 判断用户信息
		if (!dataValidate.toLowerCase().equals(validate)) {
			setResult("false");
			setMessage("验证码错误");
			return "failed";
		}
		return "success";
	}

	/**
	 * @return the userManageService
	 */
	public UserManageService getUserManageService() {
		return userManageService;
	}

	/**
	 * @param userManageService
	 *            the userManageService to set
	 */
	@Resource
	public void setUserManageService(UserManageService userManageService) {
		this.userManageService = userManageService;
	}

	/**
	 * @return the root
	 */
	public List<UserPojo> getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(List<UserPojo> root) {
		this.root = root;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the data
	 */
	public UserPojo getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(UserPojo data) {
		this.data = data;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

}
