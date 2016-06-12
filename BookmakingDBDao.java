package cn.cust.kyc.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import cn.cust.kyc.dao.BookmakingDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Bookmaking;
import cn.edu.cust.levin.DAOException;

public class BookmakingDBDao implements BookmakingDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil
			.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog(BookmakingDBDao.class);

	@Override
	public List<Bookmaking> getBookmakings() throws DAOException {
		try {
			return session.createQuery("from Bookmaking").list();
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
					.createQuery("select max(publishDate) from Bookmaking");
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
					.createQuery("select min(publishDate) from Bookmaking");
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
	public void deleteBookmaking(int id) {
		session.delete(session.load(Bookmaking.class, id));
	}

	@Override
	public void saveBookmaking(Bookmaking bm) {
		session.save(bm);
	}

	@Override
	public void updateBookmaking(Bookmaking bm) {
		session.merge(bm);
	}

	@Override
	public Bookmaking getBookmaking(int id) {
		return (Bookmaking) session.get(Bookmaking.class, id);
	}

	@Override
	public List<Bookmaking> getBookmakings(Pagination pagination, String year,
			String s) {
		try {
			String sql = "from Bookmaking";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			}
			if (!("all".equals(year) || year == null)){
				if(sql.contains("where")){
					sql += " and publishDate like '%" + year + "%'";
				}else{
					sql += " where publishDate like '%" + year + "%'";
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
	public List<Bookmaking> getBookmakings(String year, String s) {
		try {
			String sql = "from Bookmaking";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			} else {
				if (!("all".equals(year) || year == null))
					sql += " where publishDate like '%" + year + "%'";
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
