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

public class RoleMenuAction extends BaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 5386469775398514743L;
	/** log */
	private static Log log = LogFactory.getLog(RoleMenuAction.class);

	private UserManageService userManageService;

	private List<RoleMenuPojo> root;
	private List<MenuPojo> menuList;
	private RoleMenuPojo data;

	@Override
	protected String executeProcess() throws Exception {
		log.info("roleMenuInfoList start");
		try {

			menuList = userManageService.roleMenuInfoList(data);
			setTotalProperty(Constant.totalProperty);
			if (null == menuList) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("roleMenuInfoList failed");
				return "failed";
			} else {
				setResult("success");
				setMessage("获取数据成功");
				log.info("roleMenuInfoList success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("roleMenuInfoList failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String roleMenuInfoById() throws Exception {
		log.info("roleMenuInfoById start");
		try {

			// data = userManageService.aisleDownInfoById(data);

			if (null == data) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("roleMenuInfoById failed");
				return "failed";
			} else {
				setResult("success");
				setMessage("获取数据成功");
				log.info("roleMenuInfoById success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("roleMenuInfoById failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String addRoleMenu() throws Exception {
		log.info("addRoleMenu start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				return "failed";
			}
			data.setStatus(Constant.NORMAL);
			userManageService.addRoleMenu(data);

			setResult("success");
			setMessage("更新数据成功");
			log.info("addRoleMenu success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("addRoleMenu failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String addRoleMemuMore() throws Exception {
		log.info("addRoleMemuMore start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				return "failed";
			}

			userManageService.addRoleMemuMore(data);

			setResult("success");
			setMessage("更新数据成功");
			log.info("addRoleMemuMore success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("addRoleMemuMore failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String delRoleMenu() throws Exception {
		log.info("delRoleMenu start");
		try {
			
			if (null == data) {
				setMessage("删除数据失败");
				setResult("failed");
				return "failed";
			}

			userManageService.delRoleMenu(data);

			setResult("success");
			setMessage("删除数据成功");
			log.info("delRoleMenu success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("删除数据失败");
			log.error("delRoleMenu failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String updateAisleDown() throws Exception {
		log.info("updateAisleDown start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				log.info("updateAisleDown failed");
				return "failed";
			}

			// userManageService.updateAisleDown(data);

			setResult("success");
			setMessage("更新数据成功");
			setResult("success");
			log.info("updateAisleDown success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("updateAisleDown failed");
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
	public List<RoleMenuPojo> getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(List<RoleMenuPojo> root) {
		this.root = root;
	}

	/**
	 * @return the data
	 */
	public RoleMenuPojo getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(RoleMenuPojo data) {
		this.data = data;
	}

	/**
	 * @return the menuList
	 */
	public List<MenuPojo> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList
	 *            the menuList to set
	 */
	public void setMenuList(List<MenuPojo> menuList) {
		this.menuList = menuList;
	}

}
