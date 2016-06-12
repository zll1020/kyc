package cn.cust.kyc.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.AchievementDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Achievement;
import cn.edu.cust.levin.DAOException;

public class AchievementDBDao implements AchievementDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil
			.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog(AchievementDBDao.class);

	@Override
	public void saveAchievement(Achievement acm) throws DAOException {
		try {
			session.merge(acm);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void updateAchievement(Achievement acm) throws DAOException {
		try {
			session.merge(acm);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public Achievement getAchievement(String s, String innerCode)
			throws DAOException {
		try {
			System.out.println("dao数据");
			Criteria cri = session.createCriteria(Achievement.class);
			cri.add(Restrictions.eq("innerId", innerCode));
			cri.add(Restrictions.eq("type", s));
			//cri.add(Expression.eq("project.innerCode", innerCode));
			//cri.add(Expression.eq("type", s));
			System.out.println("dao数据   haha");
			List<Achievement> list = cri.list();
			System.out.println("dao数据   gaga:"+list.size());

			if (list.size() == 0)
				return new Achievement();
			else
				return list.get(0);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void deleteAchievement(int id) {
		session.delete(session.get(Achievement.class, id));
	}

	@Override
	public Achievement getAchievementById(int id) {
		return (Achievement) session.get(Achievement.class, id);
	}

	@Override
	public List<Achievement> getAchievements(String type, String predix,
			Pagination pagination, String year, String s) {
		try {
			String sql = "from Achievement where type='" + type + "'";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " and " + s;
			}
			if (!("all".equals(year) || year == null))
				sql += " and concludeDate like '%" + year + "%'";

			if (!"".equals(predix)) {
				//clg修改，2012.1.20
				//sql += " and project.innerCode like '" + predix + "%'";
				sql += " and innerId like '" + predix + "%'";
			}
			System.out.println(sql);
			q = session.createQuery(sql);
			pagination.setResultCount(q.list().size());
			pagination
					.setPageCount(pagination.getResultCount()
							/ pagination.getSize()
							+ ((pagination.getResultCount()
									% pagination.getSize() == 0) ? 0 : 1));
			q.setFirstResult((pagination.getPage() - 1) * pagination.getSize());
			q.setMaxResults(pagination.getSize());
			return q.list();
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Achievement> getAchievements(String type, String predix,
			String year, String s) {
		System.out.println("查询数据库：getAchievements");//+type+" "+predix+" "+year+" "+s
		try {
			String sql = "from Achievement where type='" + type + "'";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " and " + s;
			} else {
				if (!("all".equals(year) || year == null))
					sql += " and concludeDate like '%" + year + "%'";
			}
			if (!"".equals(predix)) {
				/*clg修改，2012.1.20
				 * sql += " and project.innerCode like '" + predix + "%'";
				 * */
				sql += " and innerId like '" + predix + "%'";
			}
			System.out.println(sql);
			q = session.createQuery(sql);
			return q.list();
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Date getMaxStartDate(String predix, String type) throws DAOException {
		try {
			/*clg修改，2012.1.20
			 * Query q = session
					.createQuery("select max(acm.concludeDate) from Achievement acm where acm.project.innerCode like '"
							+ predix + "%' and acm.type='" + type + "'"); */
			Query q = session
			.createQuery("select max(acm.concludeDate) from Achievement acm where acm.innerId like '"
					+ predix + "%' and acm.type='" + type + "'"); 
			Object o = q.uniqueResult();
			return (Date) o;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public Date getMinStartDate(String predix, String type) throws DAOException {
		try {
			/*clg修改，2012.1.20
			 * Query q = session
					.createQuery("select min(acm.concludeDate) from Achievement acm where acm.project.innerCode like '"
							+ predix + "%' and acm.type='" + type + "'");*/
			Query q = session
			.createQuery("select min(acm.concludeDate) from Achievement acm where acm.innerId like '"
					+ predix + "%' and acm.type='" + type + "'");
			
			Object o = q.uniqueResult();
			return (Date) o;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	/**
	 * 从立刚添加2012.1.29
	 * @param innerPredix
	 * @return
	 * @throws DAOException
	 */
	public String getMaxAchievementKey(String innerPredix) throws DAOException {
		try {
			Query q = session.createQuery("select max(a.innerId) from Achievement a where a.innerId like '"+innerPredix+"%'");
			Object o = q.uniqueResult();
			if(o==null)
				return null;
			else
				return (String) o;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

}
