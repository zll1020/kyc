package cn.cust.kyc.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import cn.cust.kyc.dao.AwardDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Award;
import cn.edu.cust.levin.DAOException;

public class AwardDBDao implements AwardDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil
			.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog(AwardDBDao.class);

	@Override
	public List<Award> getAwards() throws DAOException {
		try {
			return session.createQuery("from Award").list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public Date getMaxStartDate() throws DAOException {
		try {
			Query q = session
					.createQuery("select max(a.awardDate) from Award a");
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
	public Date getMinStartDate() throws DAOException {
		try {
			Query q = session
					.createQuery("select min(a.awardDate) from Award a");
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
	public void deleteAward(int id) {
		session.delete(session.load(Award.class, id));
	}

	@Override
	public void saveAward(Award income) {
		session.save(income);
	}

	@Override
	public void updateAward(Award income) {
		session.merge(income);
	}

	@Override
	public Award getAward(int id) {
		return (Award) session.get(Award.class, id);
	}

	@Override
	public List<Award> getAwards(Pagination pagination, String year, String s) {
		try {
			String sql = "from Award";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			}
			if (!("all".equals(year) || year == null)){
				if(sql.contains("where")){
					sql += " and awardDate like '%" + year + "%'";
				}else{
					sql += " where awardDate like '%" + year + "%'";
				}
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
	public List<Award> getAwards(String year, String s) {
		try {
			String sql = "from Award";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			} else {
				if (!("all".equals(year) || year == null))
					sql += " where awardDate like '%" + year + "%'";
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
}
