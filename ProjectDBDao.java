package cn.cust.kyc.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.dao.DAOFactory;
import cn.cust.kyc.dao.InfoDao;
import cn.cust.kyc.dao.ProjectDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Income;
import cn.cust.kyc.vo.InfoTable;
import cn.cust.kyc.vo.Project;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ProjectDBDao implements ProjectDao {
	
	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(ProjectDBDao.class);
	
	boolean isWhere;
	@Override
	public List<Project> getProjects() {
		List<Project> list = session.createCriteria(Project.class).add(Expression.eq("delFlg", false)).list();
		return list;
	}

	@Override
	public boolean saveProject(Project p) {
		try {
			session.merge(p);
			return true;
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Project> getProjectsByPage(Pagination pagination) {
		try {
			Criteria cri= session.createCriteria(Project.class);
			cri.add(Expression.eq("delFlg", false));
			cri.setFirstResult((pagination.getPage()-1)*pagination.getSize());
			cri.setMaxResults(pagination.getSize());
			return cri.list();
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public String getMaxProjectKey(String innerPredix) throws DAOException {
		try {
			Query q = session.createQuery("select max(p.innerCode) from Project p where p.innerCode like '"+innerPredix+"%'");
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
	public boolean deleteProject(String innerCode) {
		try {
			session.delete(session.load(Project.class, innerCode));
			return true;
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Project getProject(String innerCode) {
		try {
			if(innerCode==null)
				return null;
			return (Project) session.get(Project.class, innerCode);
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean update(Project p) {
		try {
			session.merge(p);
			return true;
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			return false;
		}
	}

	@Override
	public List<Project> getProjectsByPage(String name, Pagination pagination, String year, String s) {
		try {
			isWhere = true;
			String sql = "from Project";
			Query q = null;
			if(s!=null&&!"".equals(s)) {
				sql+= getContactWorld() + s;
				System.out.println(s);
			}
			if(!("all".equals(year)||year==null)){
				
				if(s!=null&&!"".equals(s)) {
					System.out.println("-----------" + s);
					System.out.println("----------" + sql);
					if(s.indexOf("在研") != -1) {
						
						// 将在研条件删除
						int startIndex = sql.indexOf("proStatus");
						int endIndex = sql.indexOf("在研", startIndex);
						if((endIndex + 7 < sql.length() ) && ("and".equals(sql.substring(endIndex + 5, endIndex + 8)))) {
							endIndex = endIndex + 5;
						}
						sql = sql.substring(0, startIndex) + sql.substring(endIndex + 3);
						
						if("from Project where".equals(sql.trim())) {
							sql = "from Project ";
							isWhere = true;
						}
						
						sql += getContactWorld() + "(( proStatus = '在研' and startDate <= '" + year + "-12-31') or ( proStatus = '完成' and startDate <= '" + year + "-12-31' and overFormatDate > '" + year + "-12-31'))";
					} else if (s.indexOf("完成") != -1) {
						sql += getContactWorld() + "proStatus = '完成' and overFormatDate like '%" + year + "%'";
					} else  {
						sql += getContactWorld() + "((( proStatus = '完成' and startDate <= '" + year + "-12-31' and overFormatDate > '" + year + "-12-31') or ( proStatus = '在研' and startDate <= '" + year + "-12-31')) or (proStatus = '完成' and overFormatDate like '%" + year + "%'))";
					}
				} else {
					sql += getContactWorld() + "((( proStatus = '完成' and startDate <= '" + year + "-12-31' and overFormatDate > '" + year + "-12-31') or ( proStatus = '在研' and startDate <= '" + year + "-12-31')) or (proStatus = '完成' and overFormatDate like '%" + year + "%'))";
				}
				
			}
			if(!"科技处".equals(name)){
				sql+=getContactWorld() + "department = '"+name+"'";
			}
			sql+=getContactWorld() + "delFlg = false";
			if("all".equals(year)){
				InfoDao infoDao=DAOFactory.getInfoDao();
				InfoTable infoTable=infoDao.getInfoBykey("syear");
				String currentYear=infoTable.getKeyindex();
				sql+=getContactWorld()+"startDate <='"+currentYear+"-12-31'";
			}
				
			System.out.println(sql);
			q = session.createQuery(sql+" order by innerCode desc");
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
	public List<Project> getProjectsByPage(String name, Pagination pagination, String year, String s, boolean c) {
		try {
			isWhere = true;
			String sql = "from Project";
			Query q = null;
			if(s!=null&&!"".equals(s))
				sql+= getContactWorld() + s;
			if(!("all".equals(year)||year==null))
				sql+=getContactWorld() + "startDate like '%"+year+"%'";
			if(!"科技处".equals(name)){
				sql+=getContactWorld() + "department = '"+name+"'";
			}
			sql+=getContactWorld() + "delFlg = false";
			System.out.println(sql);
			q = session.createQuery(sql);
			List<Project> list = q.list();
			Project pCalc = new Project();
			if(c){
				for(Project p : list){
					pCalc.setContractFounds(pCalc.getContractFounds()+p.getContractFounds());
					pCalc.setRiskFee(pCalc.getRiskFee()+p.getRiskFee());
					pCalc.setRiskFeeBack(pCalc.getRiskFeeBack()+p.getRiskFeeBack());
					pCalc.setTotalArriveMoney(pCalc.getTotalArriveMoney()+p.getTotalArriveMoney());
					pCalc.setTotalGrant(pCalc.getTotalGrant()+p.getTotalGrant());
					pCalc.setYearArriveMoney(pCalc.getYearArriveMoney()+p.getYearArriveMoney());
					pCalc.setYearGrant(pCalc.getYearGrant()+p.getYearGrant());
				}
			}
			pagination.setResultCount(list.size());
			pagination.setPageCount(pagination.getResultCount()/pagination.getSize() + ((pagination.getResultCount()%pagination.getSize()==0)?0:1));
			q.setFirstResult((pagination.getPage()-1)*pagination.getSize());
			q.setMaxResults(pagination.getSize());
			list = q.list();
			list.add(pCalc);
			return list;
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
	public Date getMaxStartDate() throws DAOException {
		try {
			Query q = session.createQuery("select max(startDate) from Project where delFlg = false");
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
	public Date getMinStartDate() throws DAOException {
		try {
			Query q = session.createQuery("select min(startDate) from Project where delFlg = false");
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
	public boolean isExistInStartDate(int y) {
		try {
			Query q= session.createQuery("from Project where startDate like '%"+y+"%' and delFlg = false");
			if(q.list().size()>0)
				return true;
			else
				return false;
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<Project> getProjects(String s, String name) {
		String sql = "from Project where delFlg = false";
		if(!"科技处".equals(name)){
			sql+=" and department = '"+name+"'";
		}
		if(s!=null&&!"".equals(s)){
			sql+=" and " + s;
		}
		System.out.println(sql);
		return session.createQuery(sql).list();
	}

	@Override
	public List<Project> getProjects(String s, String name, String year) {
		/*String sql = "from Project where delFlg = false";
		if(!"科技处".equals(name)){
			sql+=" and department = '"+name+"'";
		}
		if(s!=null&&!"".equals(s)){
			sql+=" and " + s;
		}
		if(!(year==null||"".equals(year)||"all".equals(year))){
			sql+=" and startDate like '%"+year+"%'";
		}
		System.out.println(sql);
		return session.createQuery(sql).list();*/
		
		try {
			isWhere = true;
			String sql = "from Project";
			Query q = null;
			if(s!=null&&!"".equals(s)) {
				sql+= getContactWorld() + s;
				System.out.println(s);
			}
			if(!("all".equals(year)||year==null)){
				
				if(s!=null&&!"".equals(s)) {
					System.out.println("-----------" + s);
					System.out.println("----------" + sql);
					if(s.indexOf("在研") != -1) {
						
						// 将在研条件删除
						int startIndex = sql.indexOf("proStatus");
						int endIndex = sql.indexOf("在研", startIndex);
						if((endIndex + 7 < sql.length() ) && ("and".equals(sql.substring(endIndex + 5, endIndex + 8)))) {
							endIndex = endIndex + 5;
						}
						sql = sql.substring(0, startIndex) + sql.substring(endIndex + 3);
						
						if("from Project where".equals(sql.trim())) {
							sql = "from Project ";
							isWhere = true;
						}
						
						sql += getContactWorld() + "(( proStatus = '在研' and startDate <= '" + year + "-12-31') or ( proStatus = '完成' and startDate <= '" + year + "-12-31' and overFormatDate > '" + year + "-12-31'))";
					} else if (s.indexOf("完成") != -1) {
						sql += getContactWorld() + "proStatus = '完成' and overFormatDate like '%" + year + "%'";
					} else  {
						sql += getContactWorld() + "((( proStatus = '完成' and startDate <= '" + year + "-12-31' and overFormatDate > '" + year + "-12-31') or ( proStatus = '在研' and startDate <= '" + year + "-12-31')) or (proStatus = '完成' and overFormatDate like '%" + year + "%'))";
					}
				} else {
					sql += getContactWorld() + "((( proStatus = '完成' and startDate <= '" + year + "-12-31' and overFormatDate > '" + year + "-12-31') or ( proStatus = '在研' and startDate <= '" + year + "-12-31')) or (proStatus = '完成' and overFormatDate like '%" + year + "%'))";
				}
				
			}
			if(!"科技处".equals(name)){
				sql+=getContactWorld() + "department = '"+name+"'";
			}
			sql+=getContactWorld() + "delFlg = false";
			if("all".equals(year)){
				InfoDao infoDao=DAOFactory.getInfoDao();
				InfoTable infoTable=infoDao.getInfoBykey("syear");
				String currentYear=infoTable.getKeyindex();
				sql+=getContactWorld()+"startDate <='"+currentYear+"-12-31'";
			}
				
			System.out.println(sql);
			q = session.createQuery(sql+" order by innerCode desc");
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
	public void lockUnlockProject(String innerCode, boolean lock) {
		String status = "false";
		if(lock)
			status = "true";
		String sql = "update Project set isLock = '" + status + "' where innerCode = '" + innerCode + "'";
		System.out.println(sql);
		session.createQuery(sql).executeUpdate();
		
	}

	@Override
	public boolean isProjectLock(String innerCode) {
		String lock = getProject(innerCode).getIsLock();
		return Boolean.parseBoolean((lock==null||lock.length() == 0)?"false":getProject(innerCode).getIsLock());
	}

	@Override
	public List<Project> getProjects(String year) {
		// TODO Auto-generated method stub
		String sql = "from Project where delFlg = false";
/*
		if(!(year==null||"".equals(year)||"all".equals(year))){
			sql+=" and startDate like '%"+year+"%'";
		}*/
		return null;
	}

	@Override
	public List<Project> getSyncMoneyProjects(String year) {
		// TODO Auto-generated method stub
		try {
			isWhere = true;
			String sql = "from Project where delFlg=false and startDate >='"+year+"-01-01'";
			Query q = null;
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
