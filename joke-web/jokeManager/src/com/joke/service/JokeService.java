package com.joke.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.joke.dao.BaseDao;
import com.joke.pojo.JarPojo;
import com.joke.pojo.JokePojo;
import com.joke.utils.JokeBatch;

@Service
@Transactional
public class JokeService extends BaseDao {

	/** log */
	private static Log log = LogFactory.getLog(JokeService.class);

	/**
	 * 
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param data
	 * @return List<JokePojo>
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@SuppressWarnings("unchecked")
	public List<JokePojo> list(final JokePojo data) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				String sql = "select j.id,u.nick_name,j.msg,j.img,j.type,"
						+ "j.good,j.bad,j.talk,j.motime,j.mouser,j.status from tb_joke j "
						+ " left join tb_user u on j.userid = u.id  where 1=1  ";
				StringBuffer sqlbuf = new StringBuffer();
				sqlbuf.append(sql);

				sqlbuf.append(" and j.status <> -1 ");

				if (data.getStatus() != null && data.getStatus() != 0) {
					sqlbuf.append(" and j.status = " + data.getStatus());
				}
				sqlbuf.append(" order by j.motime desc ");

				Query query = session.createSQLQuery(sqlbuf.toString());
				setTotalProperty(query.list().size());

				if (data.getPageNo() != 0 && data.getPageSize() != 0) {
					query.setFirstResult((data.getPageNo() - 1)
							* data.getPageSize());
					query.setMaxResults(data.getPageSize());
				}

				List<Map<String, Object>> list = query.setResultTransformer(
						Transformers.ALIAS_TO_ENTITY_MAP).list();

				return list;

			}
		});
	}

	/**
	 * 
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param data
	 * @return JokePojo
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public JokePojo infoById(JokePojo data) {
		return (JokePojo) this.getById(JokePojo.class, data.getId());
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
	public void update(JokePojo data) {
		JokePojo result = (JokePojo) this.getById(JokePojo.class, data.getId());
		result.setModifyTime(data.getModifyTime());
		result.setModifyUser(data.getModifyUser());
		result.setMsg(data.getMsg());
		result.setUrl(data.getUrl());

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
	public void updateStatus(JokePojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			JokePojo result = (JokePojo) getById(JokePojo.class,
					Integer.valueOf(strArr[i]));
			result.setStatus(data.getStatus());
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
	public void delById(JokePojo data) {
		String[] strArr = data.getIds().split(",");
		for (int i = 0; i < strArr.length; i++) {
			JokePojo result = (JokePojo) this.getById(JokePojo.class,
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
	public void addBatch(JokePojo data) {
		JarPojo result = (JarPojo) this.getById(JarPojo.class, data.getType());
		List<JokePojo> jokeList = JokeBatch.getJokeUtil(result,
				result.getLink(), data.getUserId());
		for (JokePojo jokePojo : jokeList) {
			this.getByCid(jokePojo);
			if (totalProperty <= 0) {
				this.add(jokePojo);
			}

		}

	}

	/**
	 * 
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 * 
	 * @param data
	 * @return List<JokePojo>
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@SuppressWarnings("unchecked")
	public List<JokePojo> getByCid(final JokePojo data) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sqlbuf = new StringBuffer();
				String sql = "select id from tb_joke j where 1=1  ";
				sqlbuf.append(sql);
				sqlbuf.append(" and j.status <> -1 ");
				sqlbuf.append(" and j.sid = ").append(data.getSid());
				sqlbuf.append(" and j.source = ").append(data.getSource());
				Query query = session.createSQLQuery(sqlbuf.toString());
				setTotalProperty(query.list().size());
				return null;

			}
		});
	}

}
