package com.joke.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joke.dao.UserManageDao;
import com.joke.pojo.AppPojo;
import com.joke.pojo.MenuPojo;
import com.joke.pojo.RoleMenuPojo;
import com.joke.pojo.RolePojo;
import com.joke.pojo.UserPojo;
import com.joke.utils.DateUtil;

@Service
@Transactional
public class UserManageService {

	/** log */
	private static Log log = LogFactory.getLog(UserManageService.class);

	@Autowired
	private UserManageDao userManageDao;

	public List<UserPojo> getUserList(UserPojo user) {
		return userManageDao.UserListQuery(user);
	}

	/**
	 * 获取所有渠道商的列表
	 * 
	 * @param channel
	 * @return
	 */
	public List<UserPojo> getChannelList(UserPojo channel, int userId) {
		return userManageDao.getChannelList(channel, userId);
	}

	/**
	 * 根据渠道号和名称模糊查询
	 * 
	 * @param userPojo
	 * @return
	 */
	public List<UserPojo> getChannelByPara(UserPojo userPojo, int userId) {
		return userManageDao.getChannelByPara(userPojo, userId);
	}

	/**
	 * 更改渠道信息
	 * 
	 * @param user
	 */
	public void updateChannel(UserPojo user) {
		UserPojo userResult = userManageDao.getById(user);

		userResult.setLoginName(user.getLoginName());
		userResult.setUserPaWord(user.getUserPaWord());
		userResult.setChannel(user.getChannel());
		userResult.setStatus(user.getStatus());
		userResult.setBusScope(user.getBusScope());
		userResult.setAccount(user.getAccount());
		userResult.setModifyUser(user.getModifyUser());
		userResult.setUpdateTime(user.getUpdateTime());
		userResult.setCompany(user.getCompany());
		userResult.setLastLoginTime(user.getLastLoginTime());
		userResult.setPhone(user.getPhone());
		userResult.setProvince(user.getProvince());
		userResult.setDepartment(user.getDepartment());
		userResult.setAddress(user.getAddress());
		userResult.setEmail(user.getEmail());
		userResult.setJoinNetName(user.getJoinNetName());
		userResult.setJoinNetUrl(user.getJoinNetUrl());
		userResult.setWhichBank(user.getWhichBank());
		userManageDao.save(userResult);
	}

	public int contactsCount() {
		return userManageDao.contactsCount("select count(*) from UserPojo");

	}

	public List<UserPojo> getUserByName(UserPojo user) {
		return userManageDao.getUserByUserName(user);
	}

	public void updateUserStatu(UserPojo user) {
		UserPojo userResult = userManageDao.getById(user);
		userResult.setStatus(user.getStatus());
		userResult.setModifyUser(user.getModifyUser());
		userResult.setUpdateTime(DateUtil.GetNowDateTime());

	}

	/**
	 * 删除渠道
	 * 
	 * @param channelId
	 * @param userId
	 */
	public void updateChannelStatus(UserPojo channel, int userId) {
		UserPojo temp = userManageDao.getById(channel);
		temp.setStatus(-1);
		temp.setModifyUser(channel.getModifyUser());
		temp.setUpdateTime(channel.getUpdateTime());
		userManageDao.updateChannelStatus(channel.getId(), userId);
	}

	public UserPojo getUserById(UserPojo user) {
		return userManageDao.getById(user);

	}

	public String userPwdEdit(UserPojo user) {
		UserPojo userResult = userManageDao.getById(user);
		if (userResult.getStatus() == 2) {
			return "limit";
		} else if (!userResult.getUserPaWord().equals(user.getOldpassword())) {
			return "erpw";
		}
		userResult.setUserPaWord(user.getUserPaWord());
		userResult.setUpdateTime(user.getUpdateTime());
		userResult.setModifyUser(user.getModifyUser());
		userManageDao.save(userResult);
		// userResult.setModifyTime(DateUtil.GetNowDateTime());
		return "true";
	}

	public void insertUser(UserPojo user) {
		userManageDao.save(user);
	}

	/**
	 * 添加渠道
	 * 
	 * @param user
	 * @param userId
	 */
	public void insertChannel(UserPojo user, int userId) {
		userManageDao.insertUser(user, userId);
	}

	public void updateUser(UserPojo user) {
		UserPojo userResult = userManageDao.getById(user);
		userResult.setLoginName(user.getLoginName());
		userResult.setNickName(user.getNickName());
		userResult.setUserRole(user.getUserRole());
		userResult.setUpdateTime(user.getUpdateTime());
		userResult.setModifyUser(user.getModifyUser());
		userResult.setCompany(user.getCompany());
		userResult.setLastLoginTime(user.getLastLoginTime());
		userResult.setPhone(user.getPhone());
		userResult.setProvince(user.getProvince());
		userResult.setDepartment(user.getDepartment());
		userResult.setAccount(user.getAccount());
		userResult.setAddress(user.getAddress());
		userResult.setStatus(user.getStatus());
	}

	/**
	 * @return the userManageDao
	 */
	public UserManageDao getUserManageDao() {
		return userManageDao;
	}

	/**
	 * @param userManageDao
	 *            the userManageDao to set
	 */
	public void setUserManageDao(UserManageDao userManageDao) {
		this.userManageDao = userManageDao;
	}

	public String getDeductInfo(String channel) {
		return userManageDao.getDeductInfo(channel);
	}

	public void updateDeduct(UserPojo data) {
		userManageDao.updateDeduct(data.getParentId(), data.getChannel(),
				data.getDeduct());
	}

	public void addMenu(MenuPojo data) {
		userManageDao.save(data);
	}

	public List<MenuPojo> menuInfoList(MenuPojo data) {
		return userManageDao.menuInfoList(data);
	}

	public MenuPojo menuInfoById(MenuPojo data) {
		return (MenuPojo) userManageDao.getById(MenuPojo.class, data.getId());
	}

	public void delMemu(MenuPojo data) {
		String ids = data.getIds();
		String[] idsArr = ids.split(",");
		for (int i = 0; i < idsArr.length; i++) {
			MenuPojo result = (MenuPojo) userManageDao.getById(MenuPojo.class,
					Integer.valueOf(idsArr[i]));
			data.setId(Integer.valueOf(idsArr[i]));
			userManageDao.delete(result);
		}
	}

	public List<MenuPojo> menuListRole(RoleMenuPojo data) {
		List<MenuPojo> child = null;
		List<MenuPojo> parent = new ArrayList<MenuPojo>();
		List<MenuPojo> allMenu = (List<MenuPojo>) userManageDao
				.leftMenuList(data);
		for (MenuPojo menuPojo : allMenu) {
			if (menuPojo != null && menuPojo.getParent() == 0) {
				parent.add(menuPojo);
			}
		}

		for (MenuPojo pmenuPojo : parent) {
			child = new ArrayList<MenuPojo>();
			for (MenuPojo aMenuPojo : allMenu) {
				if (aMenuPojo != null
						&& pmenuPojo != null
						&& aMenuPojo.getParent().intValue() == pmenuPojo
								.getId().intValue()) {
					child.add(aMenuPojo);
				}
			}
			pmenuPojo.setChild(child);
		}

		return parent;
	}

	public List<MenuPojo> parentMenuList(MenuPojo data) {
		return userManageDao.parentMenuList(data);
	}

	public void updateMenu(MenuPojo data) {
		MenuPojo result = (MenuPojo) userManageDao.getById(MenuPojo.class,
				data.getId());
		result.setTitle(data.getTitle());
		result.setMemo(data.getMemo());
		result.setParent(data.getParent() == -1 ? 0 : data.getParent());
		result.setStatus(data.getStatus());
		result.setUrl(data.getUrl());
		result.setOrder(data.getOrder());

	}

	@SuppressWarnings("unchecked")
	public List<RolePojo> roleInfoList(RolePojo data) {
		return (List<RolePojo>) userManageDao.getAll(RolePojo.class);
	}

	public List<MenuPojo> roleMenuInfoList(RoleMenuPojo data) {
		return userManageDao.roleMenuInfoList(data);
	}

	public void addRoleMenu(RoleMenuPojo data) {
		userManageDao.save(data);
	}

	public void addRoleMemuMore(RoleMenuPojo data) {
		RoleMenuPojo roleMenuPojo;
		String[] idArr = data.getIds().split(",");
		for (int i = 0; i < idArr.length; i++) {
			roleMenuPojo = new RoleMenuPojo();
			roleMenuPojo.setMenuId(Integer.valueOf(idArr[i]));
			roleMenuPojo.setRole(data.getRole());
			userManageDao.save(roleMenuPojo);
		}

	}

	public void delRoleMenu(RoleMenuPojo data) {
		String ids = data.getIds();
		String[] idsArr = ids.split(",");
		for (int i = 0; i < idsArr.length; i++) {
			RoleMenuPojo result = (RoleMenuPojo) userManageDao.getById(
					RoleMenuPojo.class, Integer.valueOf(idsArr[i]));
			data.setId(Integer.valueOf(idsArr[i]));
			userManageDao.delete(result);
		}
	}

	public RolePojo roleInfoById(RolePojo data) {
		return (RolePojo) userManageDao.getById(RolePojo.class, data.getId());
	}

	public void addRole(RolePojo data) {
		userManageDao.save(data);
	}

	public void delRole(RolePojo data) {
		String ids = data.getIds();
		String[] idsArr = ids.split(",");
		for (int i = 0; i < idsArr.length; i++) {
			RolePojo result = (RolePojo) userManageDao.getById(RolePojo.class,
					Integer.valueOf(idsArr[i]));
			data.setId(Integer.valueOf(idsArr[i]));
			userManageDao.delete(result);
		}
	}

	public void updateRole(RolePojo data) {
		RolePojo result = (RolePojo) userManageDao.getById(RolePojo.class,
				data.getId());
		result.setCode(data.getCode());
		result.setMemo(data.getMemo());
		result.setStatus(data.getStatus());
		result.setName(data.getName());
	}

}
