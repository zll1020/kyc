package cn.cust.kyc.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import cn.cust.kyc.dao.DAOFactory;
import cn.cust.kyc.dao.IncomeDao;
import cn.cust.kyc.dao.InfoDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Income;
import cn.cust.kyc.vo.InfoTable;
import cn.edu.cust.levin.DAOException;

public class IncomeDBDao implements IncomeDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	
	private boolean isWhere = true;
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(IncomeDBDao.class);
	
	@Override
	public List<Income> getIncomeByProject(String nbbh) throws DAOException {
		try {
			System.out.println("from Income where project.innerCode='"+nbbh+"' and delFlg=false");
			return session.createQuery("from Income where project.innerCode='"+nbbh+"' and delFlg=false").list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
	@Override
	public List<Income> getIncomeByProject(String nbbh, String year, String s) throws DAOException {
		try {
			String sql = "from Income where project.innerCode='"+nbbh+"' and delFlg=false";
			if(s!=null&&!"".equals(s))
				sql+= " and " + s;
			if(!("all".equals(year)||year==null)){
				sql+=" and incomeDate like '%"+year+"%'";
			}
			System.out.println(sql);
			return session.createQuery(sql).list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	

	@Override
	public List<Income> getIncomeByPageAndProject(Pagination pagination, String year,
			String nbbh) throws DAOException {
		try {
			Query q = null;
			if("all".equals(year)||year==null){
				InfoDao infoDao=DAOFactory.getInfoDao();
				InfoTable infoTable=infoDao.getInfoBykey("syear");
				String currentYear=infoTable.getKeyindex();
				q = session.createQuery("from Income where project.innerCode='"+nbbh+"' and delFlg=false "+"and incomeDate <'"+currentYear+"-12-31'  order by incomeId desc");// <'"+currentYear+"-12-31'"
			}
			else
				q = session.createQuery("from Income where project.innerCode='"+nbbh+"' and incomeDate like '%"+year+"%' and delFlg=false  order by incomeId desc");
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
				q = session.createQuery("select max(ic.incomeDate) from Income ic where ic.incomeId like '"+predix+"%' and ic.project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("select max(ic.incomeDate) from Income ic where ic.incomeId like '"+predix+"%' and delFlg=false");
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
				q = session.createQuery("select min(ic.incomeDate) from Income ic where ic.incomeId like '"+predix+"%' and ic.project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("select min(ic.incomeDate) from Income ic where ic.incomeId like '"+predix+"%' and delFlg=false");
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

//	@Override
//	public boolean isExistInStartDate(int year, String predix, String nbbh) {
//		try {
//			Query q = null;
//			if(nbbh!=null)
//				q = session.createQuery("from Income ic where ic.project.innerCode='"+nbbh+"' and ic.incomeId like '"+predix+"%' and ic.incomeDate like '%"+year+"%'");
//			else
//				q = session.createQuery("from Income ic where ic.incomeId like '"+predix+"%' and ic.incomeDate like '%"+year+"%'")
//			if(q.list().size()>0)
//				return true;
//			else
//				return false;
//		} catch (HibernateException ex) {
//			if (logger.isWarnEnabled()) {
//				logger.warn("",ex);
//			}
//			ex.printStackTrace();
//		}
//		return false;
//	}

	@Override
	public void deleteIncome(String incomeId) {
		session.delete(session.load(Income.class, incomeId));
	}

	@Override
	public void saveIncome(Income income) {
		session.merge(income);
	}
	
	@Override
	public void updateIncome(Income income) {
		session.merge(income);
	}

	@Override
	public Income getIncome(String incomeId) {
		return (Income) session.get(Income.class, incomeId);
	}

	@Override
	public String getMaxIncomeKey(String incomePredix) throws DAOException {
		try {
			Query q = session.createQuery("select max(inc.incomeId) from Income inc where inc.incomeId like '"+incomePredix+"%'");
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
	public List<Income> getIncomes(String predix, Pagination pagination,
			String year, String s) {
		try {
			String sql = "from Income";
			Query q = null;
			if(s!=null&&!"".equals(s))
				sql+= getContactWorld() + s;
			if(!("all".equals(year)||year==null))
				sql+=getContactWorld() + "incomeDate like '%"+year+"%'";
			if(!"".equals(predix))
					sql+= getContactWorld() + "incomeId like '"+predix+"%'";
			sql+= getContactWorld() + "delFlg=false  order by incomeId desc";
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

	@Override
	public List<Income> getIncomes(String predix, String year, String s) {
		try {
			String sql = "from Income";
			Query q = null;
			if(s!=null&&!"".equals(s))
				sql+= getContactWorld() + s;
			if(!("all".equals(year)||year==null))
				sql+= getContactWorld() + "incomeDate like '%"+year+"%'";
			if(!"".equals(predix)){
				sql+= getContactWorld() + "incomeId like '"+predix+"%'";
			}
			sql+= getContactWorld() + "delFlg=false";
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

	private String getContactWorld() {
		if(isWhere ){
			isWhere = false;
			return " where ";
		}else
			return " and ";
	}

	@Override
	public double getSumIncomeByInnerCodeAndYear(String innerCode, String year) {
		try {
			String sql = "select sum(i.income) from Income i where i.project.innerCode='"
					+ innerCode
					+ "' and i.delFlg=false and i.incomeDate like '%"
					+ year
					+ "%'";
			Query q = session.createQuery(sql);
			System.out.println(sql);
			return Double.parseDouble((q.uniqueResult()).toString());
		}catch(NullPointerException e){
			return 0.0;
		}catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}
