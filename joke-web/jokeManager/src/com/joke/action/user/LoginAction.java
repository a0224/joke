package com.joke.action.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.joke.action.BaseAction;
import com.joke.pojo.MenuPojo;
import com.joke.pojo.RoleMenuPojo;
import com.joke.pojo.UserPojo;
import com.joke.service.UserManageService;
import com.joke.utils.Constant;
import com.joke.utils.DateUtil;

public class LoginAction extends BaseAction {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5386469775398514698L;
	/** log */
	private static Log log = LogFactory.getLog(LoginAction.class);

	private UserManageService userManageService;

	private UserPojo data;
	private List<MenuPojo> menulist;
	private Integer Count = 1;

	@Override
	protected String executeProcess() throws Exception {
		Constant.PROJECT_PATH = getRequest().getSession().getServletContext()
				.getRealPath("");
		log.info("LoginAction start");
		try {
			// String data=(String) getRequest().getParameter("data");
			UserPojo userResult;

			if (data == null) {
				setResult("failed");
				setMessage("请登录!");
				return "nosuccess";
			}
			log.debug(data.getLoginName() + data.getUserPaWord()
					+ data.getUserPaWord());

			// 获取session
			String validate = (String) getSession().get(
					Constant.VALIDATE_STRING);

			// 判断用户信息
			if (data.getValidate().toLowerCase().equals(validate)) {
				List<UserPojo> userList = userManageService.getUserByName(data);
				if (null == userList || "".equals(userList)
						|| 0 == userList.size()) {
					setResult("failed");
					setMessage("没有此用户");
					log.info("没有此用户");
					return "failed";
				}

				userResult = userList.get(0);

				if (userResult != null
						&& data.getUserPaWord().equals(
								userResult.getUserPaWord())) {
					if (userResult.getStatus() == 3) {
						setResult("failed");
						setMessage("此账号已冻结,不能登录!");
						return "failed";
					}
					userResult.setLastLoginTime(DateUtil.GetNowDateTime());
					userManageService.updateUser(userResult);
					Map<String, Object> session = getSession();
					session.put(Constant.USER_INF_SESSION_NAME, userResult);
					session.put("down", Constant.getParam().getProperty("down"));
					session.put("manager",
							Constant.getParam().getProperty("manager"));
					session.put("client",
							Constant.getParam().getProperty("client"));
					session.put("push", Constant.getParam().getProperty("push"));
					RoleMenuPojo roleMenuPojo = new RoleMenuPojo();
					roleMenuPojo.setRole(userResult.getUserRole());
					menulist = userManageService.menuListRole(roleMenuPojo);
					session.put("menulist", menulist);
					setResult("success");
					setMessage("登录成功");

					log.info("登录成功");
					return "success";

				} else {
					setResult("failed");
					setMessage("密码错误");
					return "failed";
				}

			} else {
				log.info("验证码错误");
				setResult("failed");
				setMessage("验证码错误");
				return "failed";
			}
		} catch (Exception e) {
			setResult("failed");
			setMessage("系统错误");
			e.printStackTrace();
			log.error("LoginAction error");
			log.error(e);
			return "systemerror";
		}

	}

	public String loginOut() {
		log.info("LoginAction end");
		try {
			getSession().put(Constant.USER_INF_SESSION_NAME, null);
			setResult("success");
			setMessage("退出成功");
			log.info("退出成功");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("系统错误");
			e.printStackTrace();
			log.error("LoginAction error");
			log.error(e);
			return "systemerror";
		}
	}

	public String isLogin() {
		log.info("isLogin start");
		try {
			UserPojo userPojo = getUser();
			if (userPojo == null) {
				setResult("failed");
				setMessage("先登陆");
				log.info("先登陆");
				return "failed";
			} else {
				RoleMenuPojo roleMenuPojo = new RoleMenuPojo();
				roleMenuPojo.setRole(getUser().getUserRole());
				menulist = userManageService.menuListRole(roleMenuPojo);
				setResult("success");
				setMessage("登陆成功");
				log.info("登陆成功");
				return "success";
			}
		} catch (Exception e) {
			setResult("failed");
			setMessage("系统错误");
			e.printStackTrace();
			log.error("LoginAction error");
			log.error(e);
			return "systemerror";
		}

	}

	public String systemerror() {
		log.info("systemerror start");
		try {
			HttpServletResponse response = getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();
			// JSONObject result = new JSONObject();
			// result.put("timeout", true);
			// result.put("redirectUrl", request.getContextPath() + LOGIN_URL);
			// out.print(result);
			out.print("{\"error\":true}");
			out.flush();
			return null;
		} catch (IOException e) {
			setResult("failed");
			setMessage("系统错误");
			e.printStackTrace();
			log.error("LoginAction error");
			log.error(e);
			return "systemerror";
		}

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

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	public List<MenuPojo> getMenulist() {
		return menulist;
	}

	public void setMenulist(List<MenuPojo> menulist) {
		this.menulist = menulist;
	}

}
