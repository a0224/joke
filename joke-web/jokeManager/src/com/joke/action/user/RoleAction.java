package com.joke.action.user;

import java.util.List;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.joke.action.BaseAction;
import com.joke.pojo.RolePojo;
import com.joke.service.UserManageService;
import com.joke.utils.DateUtil;

public class RoleAction extends BaseAction {

	/** serialVersionUID */
	private static final long serialVersionUID = 5386469775398514743L;
	/** log */
	private static Log log = LogFactory.getLog(RoleAction.class);

	private UserManageService userManageService;

	private List<RolePojo> root;
	private RolePojo data;

	@Override
	protected String executeProcess() throws Exception {
		log.info("roleInfoList start");
		try {

			root = userManageService.roleInfoList(data);

			if (null == root) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("roleInfoList failed");
				return "failed";
			} else {
				setTotalProperty(root.size());
				setResult("success");
				setMessage("获取数据成功");
				log.info("roleInfoList success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("roleInfoList failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String roleInfoById() throws Exception {
		log.info("roleInfoById start");
		try {

			if (null == data) {
				setMessage("获取数据失败");
				setResult("failed");
				return "failed";
			}

			data = userManageService.roleInfoById(data);

			if (null == data) {
				setResult("failed");
				setMessage("获取数据失败");
				log.info("roleInfoById failed");
				return "failed";
			} else {
				setResult("success");
				setMessage("获取数据成功");
				log.info("roleInfoById success");
				return "success";
			}

		} catch (Exception e) {
			setResult("failed");
			setMessage("获取数据失败");
			log.error("roleInfoById failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}
	}

	public String addRole() throws Exception {
		log.info("addRole start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				return "failed";
			}

			data.setCreateUser(getUser().getLoginName());
			data.setCreateTime(DateUtil.GetNowDateTime());
			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime());

			userManageService.addRole(data);

			setResult("success");
			setMessage("更新数据成功");
			log.info("addRole success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("addRole failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String delRole() throws Exception {
		log.info("delRole start");
		try {

			if (null == data) {
				setMessage("删除数据失败");
				setResult("failed");
				return "failed";
			}

			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime());

			userManageService.delRole(data);

			setResult("success");
			setMessage("删除数据成功");
			log.info("delRole success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("删除数据失败");
			log.error("delRole failed");
			log.error(e);
			e.printStackTrace();
			return "failed";
		}

	}

	public String updateRole() throws Exception {
		log.info("updateRole start");
		try {

			if (null == data) {
				setMessage("更新数据失败");
				setResult("failed");
				log.info("updateRole failed");
				return "failed";
			}

			data.setModifyUser(getUser().getLoginName());
			data.setModifyTime(DateUtil.GetNowDateTime());

			userManageService.updateRole(data);

			setResult("success");
			setMessage("更新数据成功");
			setResult("success");
			log.info("updateRole success");
			return "success";
		} catch (Exception e) {
			setResult("failed");
			setMessage("更新数据失败");
			log.error("updateRole failed");
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
	public List<RolePojo> getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(List<RolePojo> root) {
		this.root = root;
	}

	/**
	 * @return the data
	 */
	public RolePojo getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(RolePojo data) {
		this.data = data;
	}

}
