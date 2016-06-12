package cn.cust.kyc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.DAOFactory;
import cn.cust.kyc.dao.InfoDao;
import cn.cust.kyc.dao.PersonDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.InfoTable;
import cn.cust.kyc.vo.Person;
import cn.edu.cust.levin.DAOException;

public class PersonDBDao implements PersonDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(PersonDBDao.class);
	
	private boolean isWhere = true;
	
	@Override
	public List<Person> getPersonByProject(String nbbh) throws DAOException {
		try {
			return session.createQuery("from Person where project.innerCode='"+nbbh+"' and delFlg=false").list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
	@Override
	public List<Person> getPersonByPageAndProject(String year,
			String nbbh, String s) {
		try {
			Query q = null;
			if("all".equals(year)||year==null)
				q = session.createQuery("from Person where project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("from Person where project.innerCode='"+nbbh+"' and delFlg=false and year="+year);
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
	public List<Person> getPersonByPageAndProject(Pagination pagination, String year,
			String nbbh) throws DAOException {
		try {
			Query q = null;
			if("all".equals(year)||year==null)
				{
				InfoDao infoDao=DAOFactory.getInfoDao();
				InfoTable infoTable=infoDao.getInfoBykey("syear");
				String currentYear=infoTable.getKeyindex();
				q = session.createQuery("from Person where project.innerCode='"+nbbh+"' and delFlg=false"+" and year <="+currentYear+" order by ID desc");
				}
			else
				q = session.createQuery("from Person where project.innerCode='"+nbbh+"' and delFlg=false and year="+year);
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
	public int getMaxYear(String nbbh) throws DAOException {
		try {
			Query q = null;
			if(nbbh!=null)
				q = session.createQuery("select max(year) from Person where project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("select max(year) from Person where delFlg=false");
			Object o = q.uniqueResult();
			return (Integer) o;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public int getMinYear(String nbbh) throws DAOException {
		try {
			Query q = null;
			if(nbbh!=null)
				q = session.createQuery("select min(year) from Person where project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("select min(year) from Person where delFlg=false");
			Object o = q.uniqueResult();
			return (Integer) o;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void deletePerson(int personId) {
		session.delete(session.load(Person.class, personId));
	}

	@Override
	public Person savePerson(Person person) {
		return (Person) session.merge(person);
	}
	
	@Override
	public void updatePerson(Person person) {
		session.merge(person);
	}

	@Override
	public Person getPerson(int personId) {
		return (Person) session.get(Person.class, personId);
	}

	@Override
	public List<Person> getPersons(String predix, Pagination pagination,
			String year, String s) {
		try {
			String sql = "from Person where delFlg=false";
			Query q = null;
			if(s!=null&&!"".equals(s)){
				sql+=" and "+s;
			}
			if(!("all".equals(year)||year==null))
				sql+=" and year="+year;
			if(!"".equals(predix)){
				sql+=" and project.innerCode like '"+predix+"%'";
			}
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
	public List<Person> getPersons(String predix, String year, String s) {
		try {
			String sql = "from Person where delFlg=false";
			Query q = null;
			if(s!=null&&!"".equals(s)){
				sql+=" and "+s;
			}
			if(!("all".equals(year)||year==null)){
				sql+=" and year="+year;
			}
			if(!"".equals(predix)){
				sql+=" and project.innerCode like '"+predix+"%'";
			}
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
	public List<Integer> getYearList(String innerCode) {
		Criteria cri = session.createCriteria(Person.class).add(Expression.eq("delFlg", false));
		if(innerCode!=null)
			cri.add(Expression.eq("project.innerCode", innerCode));
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("year"));
		Iterator<Integer> it = cri.setProjection(pl).list().iterator();
		Set<Integer> set = new TreeSet();
		while(it.hasNext())
			set.add(it.next());
		List<Integer> list = new ArrayList<Integer>();
		for(int i : set)
			list.add(i);
		return list;
	} 

	@Override
	public int doPersonControl() {
		//成员过度时，将项目的当年累计到款和当年累计拨款清空
		try {
			Query query = session.createQuery("update Project p set p.yearArriveMoney = 0, p.yearGrant = 0, p.yearCapitalAccount = 0"); 
			query.executeUpdate(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		//成员过度
		try {
			int year = new Date().getYear() + 1900 - 1;
			Criteria cri = session.createCriteria(Person.class);
			cri.add(Expression.eq("delFlg", false));
			cri.add(Expression.eq("year", year));
			List<Person> list = cri.list();
			int count = 0;
			for(Person p : list){
				if("在研".equals(p.getProject().getProStatus())){
					if(session.createQuery("from Person where project.innerCode='"+p.getProject().getInnerCode()+"' and serialNumber="+p.getSerialNumber()+" and year="+(year+1)).list().size()==0){
						Person ps = new Person(p);
			//			ps.setYear(year);
			//			ps.setCreateDate(new Date());
						session.save(ps);
						count++;
					}else{
						
					}
				}
			}
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	private String getContactWorld() {
		if(isWhere ){
			isWhere = false;
			return " where ";
		}else
			return " and ";
	}

	@Override
	public double getSumWorkloadByInnerCodeAndYear(String innerCode, String year) {
		try {
			String sql = "select sum(p.workload) from Person p where p.project.innerCode='"
					+ innerCode
					+ "' and p.delFlg=false and p.year = "
					+ Integer.parseInt(year);
			Query q = session.createQuery(sql);
			System.out.println(sql);
			double r = ((Double) q.uniqueResult()).doubleValue();
			return r;
		}catch(NullPointerException e){
			return 0.0;
		}catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}

}
