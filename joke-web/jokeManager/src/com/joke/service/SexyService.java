package com.joke.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joke.dao.SexyDao;
import com.joke.pojo.JarPojo;
import com.joke.pojo.NewsPojo;
import com.joke.pojo.SexyPojo;
import com.joke.utils.JokeBatch;

@Service
@Transactional
public class SexyService {

	/** log */
	private static Log log = LogFactory.getLog(SexyService.class);

	@Autowired
	private SexyDao sexyDao;

	public List<SexyPojo> list(SexyPojo data) {
		return sexyDao.list(data);
	}

	/**
	 * @return
	 */
	public SexyPojo infoById(SexyPojo data) {
		return (SexyPojo) sexyDao.getById(SexyPojo.class, data.getId());
	}

	/**
	 */
	public void update(SexyPojo data) {
		SexyPojo result = (SexyPojo) sexyDao.getById(SexyPojo.class,
				data.getId());
		result.setDescrip(data.getDescrip());
		result.setImg(data.getImg());
		result.setMemo(data.getMemo());
		result.setTitle(data.getTitle());
		result.setModifyTime(data.getModifyTime());
		result.setModifyUser(data.getModifyUser());

	}

	/**
	 */
	public void updateStatus(SexyPojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			SexyPojo result = (SexyPojo) sexyDao.getById(SexyPojo.class,
					Integer.valueOf(strArr[i]));
			result.setStatus(data.getStatus());
		}
	}

	/**
	 */
	public void add(SexyPojo data) {
		data.setTalk(0);
		sexyDao.add(data);
	}

	/**
	 */
	public void delById(SexyPojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			SexyPojo result = (SexyPojo) sexyDao.getById(SexyPojo.class,
					Integer.valueOf(strArr[i]));
			result.setStatus(-1);
		}

	}

	/**
	 * 
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param data
	 *            void
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void addBatch(SexyPojo data) {
		JarPojo result = (JarPojo) sexyDao.getById(JarPojo.class,
				data.getType());
		List<SexyPojo> sexyList = JokeBatch.getSexyUtil(result,
				result.getLink());
		for (SexyPojo sexyPojo : sexyList) {
			sexyDao.getByCid(sexyPojo);
			if (sexyDao.totalProperty <= 0) {
				sexyDao.add(sexyPojo);
			}
		}

	}

	/**
	 * 
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param data
	 *            void
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void addBatchTest(JarPojo data, String url) {
		List<SexyPojo> newsList = JokeBatch.getSexyUtil(data, url);
		for (SexyPojo newsPojo : newsList) {
			sexyDao.getByCid(newsPojo);
			if (sexyDao.totalProperty <= 0) {
				sexyDao.add(newsPojo);
			}

		}

	}
}
