package cn.cust.kyc.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import cn.cust.kyc.dao.ReportDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Report;
import cn.edu.cust.levin.DAOException;

public class ReportDBDao implements ReportDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil
			.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog(ReportDBDao.class);

	@Override
	public List<Report> getReports() throws DAOException {
		try {
			return session.createQuery("from Report").list();
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
			Query q = session.createQuery("select max(reportDate) from Report");
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
			Query q = session.createQuery("select min(reportDate) from Report");
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
	public void deleteReport(int id) {
		session.delete(session.load(Report.class, id));
	}

	@Override
	public void saveReport(Report report) {
		session.save(report);
	}

	@Override
	public void updateReport(Report report) {
		session.merge(report);
	}

	@Override
	public Report getReport(int id) {
		return (Report) session.get(Report.class, id);
	}

	@Override
	public List<Report> getReports(Pagination pagination, String year, String s) {
		try {
			String sql = "from Report";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			}
			if (!("all".equals(year) || year == null)){
				if(sql.contains("where")){
					sql += " and reportDate like '%" + year + "%'";
				}else{
					sql += " where reportDate like '%" + year + "%'";
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
	public List<Report> getReports(String year, String s) {
		try {
			String sql = "from Report";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			} else {
				if (!("all".equals(year) || year == null))
					sql += " where reportDate like '%" + year + "%'";
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
