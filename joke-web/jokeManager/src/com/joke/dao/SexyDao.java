package com.joke.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;

import com.joke.pojo.JokePojo;
import com.joke.pojo.SexyPojo;
import com.joke.pojo.MenuPojo;
import com.joke.pojo.RoleMenuPojo;
import com.joke.pojo.UserPojo;
import com.joke.utils.Constant;

@Component
public class SexyDao extends BaseDao {

	private static Log log = LogFactory.getLog(SexyDao.class);

	@SuppressWarnings("unchecked")
	public List<SexyPojo> list(final SexyPojo data) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				String sql = "select j.id,u.nick_name,j.title,j.descrip,j.img,j.memo,"
						+ "j.talk,j.motime,j.mouser,j.status from tb_sexy j "
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
	 * @return List<JokePojo>
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@SuppressWarnings("unchecked")
	public List<JokePojo> getByCid(final SexyPojo data) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuffer sqlbuf = new StringBuffer();
				String sql = "select id from tb_sexy j where 1=1  ";
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
