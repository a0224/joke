package com.joke.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joke.dao.NewsDao;
import com.joke.pojo.JarPojo;
import com.joke.pojo.JokePojo;
import com.joke.pojo.NewsPojo;
import com.joke.utils.JokeBatch;

@Service
@Transactional
public class NewsService {

	/** log */
	private static Log log = LogFactory.getLog(NewsService.class);

	@Autowired
	private NewsDao newsDao;

	public List<NewsPojo> list(NewsPojo data) {
		return newsDao.list(data);
	}

	/**
	 * @return
	 */
	public NewsPojo infoById(NewsPojo data) {
		return (NewsPojo) newsDao.getById(NewsPojo.class, data.getId());
	}

	/**
	 */
	public void update(NewsPojo data) {
		NewsPojo result = (NewsPojo) newsDao.getById(NewsPojo.class,
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
	public void updateStatus(NewsPojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			NewsPojo result = (NewsPojo) newsDao.getById(NewsPojo.class,
					Integer.valueOf(strArr[i]));
			result.setStatus(data.getStatus());
		}
	}

	/**
	 */
	public void add(NewsPojo data) {
		data.setTalk(0);
		newsDao.add(data);
	}

	/**
	 */
	public void delById(NewsPojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			NewsPojo result = (NewsPojo) newsDao.getById(NewsPojo.class,
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
	public void addBatch(NewsPojo data) {
		JarPojo result = (JarPojo) newsDao.getById(JarPojo.class,
				data.getType());
		List<NewsPojo> newsList = JokeBatch.getNewsUtil(result, result.getLink());
		for (NewsPojo newsPojo : newsList) {
			newsDao.getByCid(newsPojo);
			if (newsDao.totalProperty <= 0) {
				newsDao.add(newsPojo);
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
	public void addBatchTest(JarPojo data,String url) {
		List<NewsPojo> newsList = JokeBatch.getNewsUtil(data,url);
		for (NewsPojo newsPojo : newsList) {
			newsDao.getByCid(newsPojo);
			if (newsDao.totalProperty <= 0) {
				newsDao.add(newsPojo);
			}
		}

	}
}
