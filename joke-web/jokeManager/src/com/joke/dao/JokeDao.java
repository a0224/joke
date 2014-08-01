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
import com.joke.pojo.MenuPojo;
import com.joke.pojo.RoleMenuPojo;
import com.joke.pojo.UserPojo;
import com.joke.utils.Constant;

@Component
public class JokeDao extends BaseDao {

	private static Log log = LogFactory.getLog(JokeDao.class);

	@SuppressWarnings("unchecked")
	public List<UserPojo> UserListQuery(final UserPojo user) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// eq是等于，gt是大于，lt是小于,or是或
				Criteria criteria = session.createCriteria(UserPojo.class);

				if (Constant.CPIS == user.getUserRole()) {
					criteria.add(Restrictions
							.sqlRestriction("role not in ('1,2')"));
				}

				criteria.add(Restrictions.sqlRestriction("status <> "
						+ Constant.DELETE));

				setTotalProperty(((Number) criteria.setProjection(
						Projections.rowCount()).uniqueResult()).intValue());
				criteria.setProjection(null);

				if ("desc".equals(user.getSortModel())
						&& null != user.getSortName()
						&& !"".equals(user.getSortName())) {
					criteria.addOrder(Order.desc(user.getSortName()));
				} else if ("asc".equals(user.getSortModel())
						&& null != user.getSortName()
						&& !"".equals(user.getSortName())) {
					criteria.addOrder(Order.asc(user.getSortName()));
				}

				// 决定返回的结果
				// criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

				if (user.getPageNo() != 0 && user.getPageSize() != 0) {
					criteria.setFirstResult((user.getPageNo() - 1)
							* user.getPageSize());
					criteria.setMaxResults(user.getPageSize());
				}
				// criteria.createAlias("cloudAdvImagePojo", "ima",
				// CriteriaSpecification.LEFT_JOIN);
				// criteria.createAlias("cloudCountInfoPojo", "count",
				// CriteriaSpecification.LEFT_JOIN);
				// ProjectionList proList = Projections.projectionList();
				// Projections.
				// proList.add(Projections.distinct(Projections.property("id")));
				// criteria.setProjection(proList);
				// criteria.setFetchMode("cloudAdvImagePojo", FetchMode.SELECT);
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				// criteria.add(Restrictions
				// .sqlRestriction("id in (select   from cloud_info i group by i.id)"));
				// criteria.setProjection(Projections.distinct(Property.forName("id")));

				return criteria.list();
			}
		});
		// List<UserPojo> userlist = (List<UserPojo>) pageQuery("from UserPojo",
		// user.getPageNo(), user.getPageSize());
		// Constant.totalProperty = getTotalProperty();
		// return userlist;
	}

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

				// List resultlist = query.list();
				//
				// for (int i = 0; i < resultlist.size(); i++) {
				// Object[] objects = (Object[]) resultlist.get(i);
				//
				// System.out.println(objects[0]);
				// System.out.println(objects[1]);
				// System.out.println(objects[2]);
				// }
				//
				// return resultlist;

				// List list = query
				// .setResultTransformer(Transformers.TO_LIST).list();

				// List list = query.setResultTransformer(
				// Transformers.aliasToBean(ReportPojo.class)).list();
				//

				// Query query =
				// session.createSQLQuery(buf.toString()).addEntity(
				// ReportPojo.class);
				//
				// if (data.getPageNo() != 0 && data.getPageSize() != 0) {
				// query.setFirstResult((data.getPageNo() - 1)
				// * data.getPageSize());
				// query.setMaxResults(data.getPageSize());
				// }
				//
				// List<ReportPojo> stics = new ArrayList<ReportPojo>();
				// stics = query.list();
				// return stics;

			}
		});
	}

	@SuppressWarnings("unchecked")
	public String getDeductInfo(String channel) {
		List<Object> list = getHibernateTemplate().find(
				"select deduct from UserChannel where channel = ?", channel);
		if (list.size() > 0 && list != null) {
			return list.get(0).toString();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public void updateDeduct(final String parentId, final String channel,
			final String deduct) {
		getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				StringBuilder sql = new StringBuilder();
				sql.append("update UserChannel set deduct = '" + deduct
						+ "' where 1 = 1");
				if (channel == null || "".equals(channel)) {
					if (!"0".equals(parentId)) {
						sql.append(" and user_id = '" + parentId + "'");
					}
				} else {
					sql.append(" and channel = '" + channel + "'");
				}
				sql.append(" and status <> -1");
				session.createQuery(sql.toString()).executeUpdate();
				return null;
			}

		});
	}

	@SuppressWarnings("unchecked")
	public List<MenuPojo> parentMenuList(final MenuPojo menu) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(MenuPojo.class);

				if (menu.getParent() != null) {
					criteria.add(Restrictions.eq("parent", 0));
				}
				criteria.addOrder(Order.desc("order"));
				criteria.add(Restrictions.sqlRestriction("status <> "
						+ Constant.DELETE));

				setTotalProperty(((Number) criteria.setProjection(
						Projections.rowCount()).uniqueResult()).intValue());
				criteria.setProjection(null);

				if (menu.getPageNo() != 0 && menu.getPageSize() != 0) {
					criteria.setFirstResult(
							(menu.getPageNo() - 1) * menu.getPageSize())
							.setMaxResults(menu.getPageSize());
				}

				return criteria.list();

			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<MenuPojo> menuInfoList(final MenuPojo menu) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(MenuPojo.class);

				if (menu.getParent() != null) {
					criteria.add(Restrictions.eq("parent", 0));
				}
				criteria.addOrder(Order.desc("order"));
				criteria.add(Restrictions.sqlRestriction("status <> "
						+ Constant.DELETE));

				setTotalProperty(((Number) criteria.setProjection(
						Projections.rowCount()).uniqueResult()).intValue());
				criteria.setProjection(null);

				if (menu.getPageNo() != 0 && menu.getPageSize() != 0) {
					criteria.setFirstResult(
							(menu.getPageNo() - 1) * menu.getPageSize())
							.setMaxResults(menu.getPageSize());
				}

				return criteria.list();

			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<MenuPojo> roleMenuInfoList(final RoleMenuPojo role) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				String sql = "select r.id,m.title,m.url,m.parent,m.memo,m.order_by,"
						+ "m.create_time,m.create_user,m.modify_user,m.modify_time,m.status from tb_rolemenu r "
						+ "left join tb_menu m on r.menu_id = m.id where 1=1 ";
				StringBuffer sqlbuf = new StringBuffer();
				sqlbuf.append(sql);

				if (role.getRole() != null) {
					sqlbuf.append(" and r.role = " + role.getRole() + " ");
				}
				sqlbuf.append(" order by m.order_by ");
				Query query = session.createSQLQuery(sqlbuf.toString())
						.addEntity(MenuPojo.class);
				setTotalProperty(query.list().size());

				if (role.getPageNo() != 0 && role.getPageSize() != 0) {
					query.setFirstResult(
							(role.getPageNo() - 1) * role.getPageSize())
							.setMaxResults(role.getPageSize());
				}

				return query.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<MenuPojo> leftMenuList(final RoleMenuPojo role) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				String sql = "select m.id,m.title,m.url,m.parent,m.memo,m.order_by,"
						+ "m.create_time,m.create_user,m.modify_user,m.modify_time,m.status from tb_rolemenu r "
						+ "left join tb_menu m on r.menu_id = m.id where 1=1 ";
				StringBuffer sqlbuf = new StringBuffer();
				sqlbuf.append(sql);

				if (role.getRole() != null) {
					sqlbuf.append(" and r.role = " + role.getRole() + " ");
				}
				sqlbuf.append(" order by m.order_by ");
				Query query = session.createSQLQuery(sqlbuf.toString())
						.addEntity(MenuPojo.class);
				setTotalProperty(query.list().size());

				if (role.getPageNo() != 0 && role.getPageSize() != 0) {
					query.setFirstResult(
							(role.getPageNo() - 1) * role.getPageSize())
							.setMaxResults(role.getPageSize());
				}

				return query.list();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserPojo> getChannelList(final UserPojo user, final int userId) {
		return getHibernateTemplate().executeFind(
				new HibernateCallback<List<Map<String, Object>>>() {
					public List<Map<String, Object>> doInHibernate(
							Session session) throws HibernateException,
							SQLException {
						StringBuffer sql = new StringBuffer(
								"select u.* , sum(p.price) as  `money`,sum(at.price) as `price` from tb_userinfo u left  join tb_userrelevancy ul   on u.id = ul.relevancy   left join tb_paylog p on p.channel = u.name left join tb_aisleinfo ai on p.aisle_id = ai.id "
										+ " left  join tb_accountinfo at on at.mobile = p.phone"
										+ " where 1=1");

						if (null != user.getCreateUser()
								&& !"".equals(user.getCreateUser())) {
							sql.append(" and u.create_user= '"
									+ user.getCreateUser() + "'");
						}
						if (null != user.getLoginName()
								&& !"".equals(user.getLoginName())) {
							sql.append(" and u.name= '" + user.getLoginName()
									+ "'");
						}
						;
						if (null != user.getChannel()
								&& !"".equals(user.getChannel())) {
							sql.append(" and u.channel='" + user.getChannel()
									+ "'");
						}
						;
						sql.append(" and u.status <> -1");
						sql.append(" and ul.user_id =" + userId);
						if (null != user.getStartTime()
								&& null != user.getEndTime()) {
							sql.append(" and p.pay_time  between date_format('"
									+ user.getStartTime()
									+ "','%Y-%m-%d %H-%i-%s') and date_format('"
									+ user.getEndTime()
									+ "','%Y-%m-%d %H-%i-%s')");
						}
						if (null != user.getCharge()) {
							sql.append(" and ai.charge= " + user.getCharge());
						}
						sql.append(" group by u.name");
						Query query = session.createSQLQuery(sql.toString());
						if (user.getPageNo() != 0 && user.getPageSize() != 0) {
							query.setFirstResult(
									(user.getPageNo() - 1) * user.getPageSize())
									.setMaxResults(user.getPageSize());
						}
						List<Map<String, Object>> list = query
								.setResultTransformer(
										Transformers.ALIAS_TO_ENTITY_MAP)
								.list();
						setTotalProperty(list.size());
						return list;
					}
				});

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserPojo> getChannelByPara(final UserPojo user, final int userId) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "select relevancy from tb_userrelevancy where user_id = '"
						+ 1 + "'";
				Query query = session.createSQLQuery(sql);
				List<Integer> list = query.list();
				Criteria criteria = session.createCriteria(UserPojo.class);
				criteria.add(Restrictions.eq("userRole", 5));

				criteria.add(Restrictions.sqlRestriction("status <> "
						+ Constant.DELETE));

				if (user.getChannel() != null && !"".equals(user.getChannel())) {
					criteria.add(Restrictions.like("channel",
							"%" + user.getChannel() + "%"));
				}
				if (user.getLoginName() != null
						&& !"".equals(user.getLoginName())) {
					criteria.add(Restrictions.like("loginName",
							"%" + user.getLoginName() + "%"));
				}
				if (list != null && list.size() > 0) {
					criteria.add(Restrictions.in("id", list.toArray()));
				} else {
					criteria.add(Restrictions.in("id", new Object[] { "" }));
				}
				setTotalProperty(((Number) criteria.setProjection(
						Projections.rowCount()).uniqueResult()).intValue());
				criteria.setProjection(null);

				if ("desc".equals(user.getSortModel())
						&& null != user.getSortName()
						&& !"".equals(user.getSortName())) {
					criteria.addOrder(Order.desc(user.getSortName()));
				} else if ("asc".equals(user.getSortModel())
						&& null != user.getSortName()
						&& !"".equals(user.getSortName())) {
					criteria.addOrder(Order.asc(user.getSortName()));
				}

				if (user.getPageNo() != 0 && user.getPageSize() != 0) {
					criteria.setFirstResult((user.getPageNo() - 1)
							* user.getPageSize());
					criteria.setMaxResults(user.getPageSize());
				}
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

				return criteria.list();
			}

		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insertUser(final UserPojo user, final int userId) {
		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.save(user);
				String sql = "select last_insert_id()";
				Query query = session.createSQLQuery(sql);
				BigInteger bi = (BigInteger) query.uniqueResult();
				sql = "insert into tb_userrelevancy(user_id,relevancy,sattus) values('"
						+ userId + "','" + bi.intValue() + "','1')";
				query = session.createSQLQuery(sql);
				query.executeUpdate();
				return null;
			}

		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateChannelStatus(final int channelId, final int userId) {
		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "update tb_userrelevancy set sattus = '-1' where user_id = '"
						+ userId + "' and relevancy = '" + channelId + "'";
				Query query = session.createSQLQuery(sql);
				query.executeUpdate();
				sql = "update tb_appchannel set status = '-1' where channel = (select channel from tb_userinfo where id = '"
						+ channelId + "')";
				query = session.createSQLQuery(sql);
				query.executeUpdate();
				return null;
			}

		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addChannelApp(final String appCode, final String channel) {
		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "insert into tb_appchannel(channel,app_id,status) values('"
						+ channel + "','" + appCode + "','1')";
				Query query = session.createSQLQuery(sql);
				query.executeUpdate();
				return null;
			}

		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void delChannelApp(final String appCode, final String channel) {
		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				String sql = "update tb_appchannel set status = '-1' where app_id = '"
						+ appCode + "' and channel = '" + channel + "'";
				Query query = session.createSQLQuery(sql);
				query.executeUpdate();
				return null;
			}

		});
	}

}
