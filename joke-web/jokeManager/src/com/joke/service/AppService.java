package com.joke.service;

import java.util.List;

import javax.persistence.Column;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joke.dao.AppDao;
import com.joke.pojo.AppPojo;

@Service
@Transactional
public class AppService {

	/** log */
	private static Log log = LogFactory.getLog(AppService.class);

	@Autowired
	private AppDao appDao;

	public List<AppPojo> list(AppPojo data) {
		return appDao.list(data);
	}

	/**
	 * @return
	 */
	public AppPojo infoById(AppPojo data) {
		return (AppPojo) appDao.getById(AppPojo.class, data.getId());
	}

	/**
	 */
	public void update(AppPojo data) {
		AppPojo result = (AppPojo) appDao.getById(AppPojo.class,
				data.getId());
		String[] strp = {"id","userid","createTime","createUser","status"};
		BeanUtils.copyProperties(data, result, strp);

	}

	/**
	 */
	public void updateStatus(AppPojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			AppPojo result = (AppPojo) appDao.getById(AppPojo.class,
					Integer.valueOf(strArr[i]));
			result.setStatus(data.getStatus());
		}
	}

	/**
	 */
	public void add(AppPojo data) {
		appDao.add(data);
	}

	/**
	 */
	public void delById(AppPojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			AppPojo result = (AppPojo) appDao.getById(AppPojo.class,
					Integer.valueOf(strArr[i]));
			result.setStatus(-1);
		}

	}
	
	/**
	 */
	public void jokeBatch(AppPojo data) {
		appDao.add(data);
	}
}
