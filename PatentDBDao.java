package cn.cust.kyc.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import cn.cust.kyc.dao.PatentDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Patent;
import cn.edu.cust.levin.DAOException;

public class PatentDBDao implements PatentDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil
			.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory
			.getLog(PatentDBDao.class);

	@Override
	public List<Patent> getPatents() throws DAOException {
		try {
			return session.createQuery("from Patent").list();
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
			Query q = session.createQuery("select max(createDate) from Patent");
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
			Query q = session.createQuery("select min(patentDate) from Patent");
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
	public void deletePatent(int id) {
		session.delete(session.load(Patent.class, id));
	}

	@Override
	public void savePatent(Patent p) {
		session.save(p);
	}

	@Override
	public void updatePatent(Patent p) {
		session.merge(p);
	}

	@Override
	public Patent getPatent(int id) {
		return (Patent) session.get(Patent.class, id);
	}

	@Override
	public List<Patent> getPatents(Pagination pagination, String year, String s) {
		try {
			String sql = "from Patent";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			}
			if (!("all".equals(year) || year == null)){
				if(sql.contains("where")){
					sql += " and createDate like '%" + year + "%'";//createDate  sql += " and patentDate like '%" + year + "%'";
				}else{
					sql += " where createDate like '%" + year + "%'";//sql += " where patentDate like '%" + year + "%'";
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

	public List<Patent> getApplyPatents(Pagination pagination, String year, String s,String flag) {
		try {
			String sql = "from Patent";
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			}
			if (!("all".equals(year) || year == null)){
				if(sql.contains("where")){
					sql += " and createDate like '%" + year + "%'";
				}else{
					sql += " where createDate like '%" + year + "%'";
				}
			}
				
			if(flag.equals("1"))
			{
				if(sql.contains("where"))
					sql=sql+" and certificate =''";
				else
					sql=sql+"  where certificate =''";
			}
			else{
				if(sql.contains("where"))
					sql=sql+" and certificate != '' ";
				else
					sql=sql+" where certificate != '' ";
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
	public List<Patent> getPatents(String year, String s) {
		try {
			String sql = "from Patent";//+" order by createDate"
			Query q = null;
			if (s != null && !"".equals(s)) {
				sql += " where " + s;
			} else {
				if (!("all".equals(year) || year == null))
					sql += " where createDate like '%" + year + "%'";//+" order by createDate";
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
