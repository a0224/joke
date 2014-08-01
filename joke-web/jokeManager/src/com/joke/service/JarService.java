package com.joke.service;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joke.dao.JarDao;
import com.joke.pojo.JarPojo;

@Service
@Transactional
public class JarService {

	/** log */
	private static Log log = LogFactory.getLog(JarService.class);

	@Autowired
	private JarDao jarDao;

	public List<JarPojo> list(JarPojo data) {
		return jarDao.list(data);
	}

	/**
	 * @return
	 */
	public JarPojo infoById(JarPojo data) {
		return (JarPojo) jarDao.getById(JarPojo.class, data.getId());
	}

	/**
	 */
	public void update(JarPojo data) {
		JarPojo result = (JarPojo) jarDao.getById(JarPojo.class,
				data.getId());
		String[] strp = {"id","userid","createTime","createUser","status"};
		BeanUtils.copyProperties(data, result, strp);

	}

	/**
	 */
	public void updateStatus(JarPojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			JarPojo result = (JarPojo) jarDao.getById(JarPojo.class,
					Integer.valueOf(strArr[i]));
			result.setStatus(data.getStatus());
		}
	}

	/**
	 */
	public void add(JarPojo data) {
		jarDao.add(data);
	}

	/**
	 */
	public void delById(JarPojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			JarPojo result = (JarPojo) jarDao.getById(JarPojo.class,
					Integer.valueOf(strArr[i]));
			result.setStatus(-1);
		}

	}
	
	/**
	 */
	public void jokeBatch(JarPojo data) {
		jarDao.add(data);
	}
}
