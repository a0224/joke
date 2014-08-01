package com.joke.action.user;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.joke.action.BaseAction;
import com.joke.pojo.MenuPojo;
import com.joke.pojo.RoleMenuPojo;
import com.joke.service.UserManageService;
import com.joke.utils.Constant;
import com.joke.utils.DateUtil;

public class MenuAction extends BaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 5386469775398514743L;
	/** log */
	private static Log log = LogFactory.getLog(MenuAction.class);

	private UserManageService userManageService;

	private List<MenuPojo> root;
	private MenuPojo data;

	@Override
	protected String executeProcess() throws Exception {
		log.info("menuInfoById start");
		try {

			root = userManageService.menuInfoList(data);
			setTotalProperty(Constant.totalProperty);
			if (null == root) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("menuInfoById failed");
				return "failed";
			} else {
				setResult("success");
				setMessage("获取数据成功");
				log.info("menuInfoById success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("menuInfoById failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String parentMenuList() throws Exception {
		log.info("parentMenuList start");
		try {

			data.setParent(0);
			root = userManageService.parentMenuList(data);
			if (null == root) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("parentMenuList failed");
				return "failed";
			} else {
				setTotalProperty(root.size());

				setResult("success");
				setMessage("获取数据成功");
				log.info("parentMenuList success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("parentMenuList failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String menuListRole() throws Exception {
		log.info("menuListRole start");
		try {

			root = userManageService.menuListRole(new RoleMenuPojo());

			if (null == root) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("menuListRole failed");
				return "failed";
			} else {
				setTotalProperty(root.size());

				setResult("success");
				setMessage("获取数据成功");
				log.info("menuListRole success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("menuListRole failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String menuInfoById() throws Exception {
		log.info("menuInfoById start");
		try {

			 data = userManageService.menuInfoById(data);

			if (null == data) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("menuInfoById failed");
				return "failed";
			} else {
				setResult("success");
				setMessage("获取数据成功");
				log.info("menuInfoById success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("menuInfoById failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String addMenu() throws Exception {
		log.info("addMenu start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				return "failed";
			}

			data.setCreateUser(getUser().getLoginName());
			data.setCreateTime(DateUtil.GetNowDateTime().toString());
			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime().toString());
			userManageService.addMenu(data);

			setResult("success");
			setMessage("更新数据成功");
			log.info("addMenu success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("addMenu failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String delMemu() throws Exception {
		log.info("delMemu start");
		try {

			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime().toString());

			userManageService.delMemu(data);

			setResult("success");
			setMessage("删除数据成功");
			log.info("delMemu success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("删除数据失败");
			log.error("delMemu failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String updateMenu() throws Exception {
		log.info("updateMenu start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				log.info("userPwdEdit failed");
				return "failed";
			}

			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime().toString());
			 userManageService.updateMenu(data);
			setResult("success");
			setMessage("更新数据成功");
			setResult("success");
			log.info("updateMenu success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("updateMenu failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
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
	public List<MenuPojo> getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(List<MenuPojo> root) {
		this.root = root;
	}

	/**
	 * @return the data
	 */
	public MenuPojo getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(MenuPojo data) {
		this.data = data;
	}

}
