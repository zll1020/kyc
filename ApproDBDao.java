package cn.cust.kyc.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import cn.cust.kyc.dao.ApproDao;
import cn.cust.kyc.dao.DAOFactory;
import cn.cust.kyc.dao.InfoDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Appropriation;
import cn.cust.kyc.vo.InfoTable;
import cn.edu.cust.levin.DAOException;

public class ApproDBDao implements ApproDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(ApproDBDao.class);
	
	boolean isWhere = true;
	@Override
	public List<Appropriation> getAppropriationByProject(String nbbh) throws DAOException {
		try {
			return session.createQuery("from Appropriation where project.innerCode='"+nbbh+"' and delFlg=false order by ID desc").list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public List<Appropriation> getAppropriationByPageAndProject(Pagination pagination, String year,
			String nbbh) throws DAOException {
		try {
			Query q = null;
			if("all".equals(year)||year==null){
				InfoDao infoDao=DAOFactory.getInfoDao();
				InfoTable infoTable=infoDao.getInfoBykey("syear");
				String currentYear=infoTable.getKeyindex();
				q = session.createQuery("from Appropriation where project.innerCode='"+nbbh+"' and delFlg=false "+"and approDate <'"+currentYear+"-12-31'  order by ID desc");

			}
			else
				q = session.createQuery("from Appropriation where project.innerCode='"+nbbh+"' and approDate like '%"+year+"%' and delFlg=false order by ID desc");
			q.setFirstResult((pagination.getPage()-1)*pagination.getSize());
			q.setMaxResults(pagination.getSize());
			return q.list();
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Date getMaxStartDate(String predix, String nbbh) throws DAOException {
		try {
			Query q = null;
			if(nbbh!=null)
				q = session.createQuery("select max(ap.approDate) from Appropriation ap where ap.appropriateID like '"+predix+"%' and ap.project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("select max(ap.approDate) from Appropriation ap where ap.appropriateID like '"+predix+"%' and delFlg=false");
			Object o = q.uniqueResult();
			return (Date) o;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public Date getMinStartDate(String predix, String nbbh) throws DAOException {
		try {
			Query q = null;
			if(nbbh!=null)
				q = session.createQuery("select min(ap.approDate) from Appropriation ap where ap.appropriateID like '"+predix+"%' and ap.project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("select min(ap.approDate) from Appropriation ap where ap.appropriateID like '"+predix+"%' and delFlg=false");
			Object o = q.uniqueResult();
			return (Date) o;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void deleteAppropriation(int ID) {
		session.delete(session.load(Appropriation.class, ID));
	}

	@Override
	public Appropriation saveAppropriation(Appropriation ap) {
		System.out.println("获取拨款额：  "+ap.getAppropriation());
		return (Appropriation) session.merge(ap);
	}

	@Override
	public Appropriation getAppropriation(int ID) {
		return (Appropriation) session.get(Appropriation.class, ID);
	}

	@Override
	public String getMaxAppropriationKey(String approPredix) throws DAOException {
		try {
			Query q = session.createQuery("select max(ap.appropriateID) from Appropriation ap where ap.appropriateID like '"+approPredix+"%'");
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

	@Override
	public List<Appropriation> getAppropriations(String predix, Pagination pagination,
			String year, String s) {
		try {
			String sql = "from Appropriation";
			Query q = null;
			if(s!=null&&!"".equals(s))
				sql+= getContactWorld() +s;
			if(!("all".equals(year)||year==null))
				sql+= getContactWorld() + "approDate like '%"+year+"%'";
			if(!"".equals(predix))
				sql+=getContactWorld() + "appropriateID like '"+predix+"%'";
			sql+=getContactWorld() + "delFlg=false order by ID desc";
			System.out.println(sql);
			q = session.createQuery(sql);
			pagination.setResultCount(q.list().size());
			pagination.setPageCount(pagination.getResultCount()/pagination.getSize() + ((pagination.getResultCount()%pagination.getSize()==0)?0:1));
			q.setFirstResult((pagination.getPage()-1)*pagination.getSize());
			q.setMaxResults(pagination.getSize());
			return q.list();
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}
	
	private String getContactWorld() {
		if(isWhere){
			isWhere = false;
			return " where ";
		}else
			return " and ";
	}

	@Override
	public List<Appropriation> getAppropriations(String predix, String year, String s) {
		try {
			String sql = "from Appropriation";
			Query q = null;
			if(s!=null&&!"".equals(s))
				sql+= getContactWorld() +s;
			if(!("all".equals(year)||year==null))
				sql+= getContactWorld() + "approDate like '%"+year+"%'";
			if(!"".equals(predix)){
				sql+= getContactWorld() + "appropriateID like '"+predix+"%'";
			}
			sql+=getContactWorld() + "delFlg=false order by ID desc";
			System.out.println(sql);
			q = session.createQuery(sql);
			return q.list();
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateAppropration(Appropriation ap) {
		session.update(ap);
	}

	@Override
	public List<Appropriation> getAppropriationByIncome(String incomeId) throws DAOException {
		try {
			return session.createQuery("from Appropriation where income.incomeId='"+incomeId+"' and delFlg=false order by ID desc").list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public String getMaxReturnBackKey(String returnbackID) throws DAOException {
		try {
			Query q = session.createQuery("select max(ap.returnbackID) from Appropriation ap where ap.returnbackID like '"+returnbackID+"%'");
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

	@Override
	public List<Appropriation> getAppropriationByAppropriationID(String appropriateID) throws DAOException {
		try {
			return session.createQuery("from Appropriation ap where ap.appropriateID = '"+appropriateID+"' and delFlg=false order by ID desc").list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public List<Appropriation> getAppropriationsByProjectAndYear(String nbbh,
			String year, String s) {
		try {
			String sql = "from Appropriation";
			Query q = null;
			if(s!=null&&!"".equals(s))
				sql+= getContactWorld() +s;
			if(nbbh!=null&&!"".equals(nbbh))
				sql+= getContactWorld() + "nbbh = '"+nbbh+"'";
			
			if(!("all".equals(year)||year==null))
				sql+= getContactWorld() + "approDate like '%"+year+"%'";
			sql+=getContactWorld() + "delFlg=false order by ID desc";
			System.out.println(sql);
			q = session.createQuery(sql);
			return q.list();
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	
	
}
