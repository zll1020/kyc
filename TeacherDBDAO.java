/**
 * 
 */
package cn.cust.kyc.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.TeacherDAO;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.SwitchTools;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.DAOException;

/**
 * 
 */
public class TeacherDBDAO implements TeacherDAO{

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	

	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(TeacherDBDAO.class);
	
	public void delete(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
		try {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Object obj = session.load(Teacher.class, id);
			session.delete(obj);
		} catch (Exception ex) {
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
	public void saveOrUpdate(Teacher entity) throws cn.edu.cust.levin.DAOException {
		try{
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(entity);
		}catch(Exception ex){
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if(logger.isWarnEnabled()){
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
  public void save(Teacher entity)throws cn.edu.cust.levin.DAOException{
	  try{
		  org.hibernate.Session session = sessionFactory.getCurrentSession();
		  session.save(entity);
	  }catch(Exception ex){
		  if(ConstData.isPrintEx)
				ex.printStackTrace();
		  if(logger.isWarnEnabled()){
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
	  }
  }
  
	public java.util.List getAll() throws cn.edu.cust.levin.DAOException {
		try {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return session.createQuery("from " + Teacher.class.getName() + " lv_")
					.list();
		} catch (Exception ex) {
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
		
	}

	public Teacher load(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
		try {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return (Teacher)session.load(Teacher.class, id);
		} catch (Exception ex) {
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
	public List queryTeacher(String tname, String tno) throws cn.edu.cust.levin.DAOException
	{
		try{
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Criteria cr = session.createCriteria(Teacher.class);
			if(tname.length() > 0)
				cr.add(Restrictions.like("name", tname, MatchMode.ANYWHERE));
			if(tno.length() >0)
				cr.add(Restrictions.eq("id",new Integer(tno)));
			return cr.list();
			
		} catch (Exception ex) {
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException("");
		}
	}
	
	public java.util.List getTeacher(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
	try{
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Teacher.class).add(org.hibernate.criterion.Expression.eq("org.id", id)).list();
	} catch (Exception ex) {
		if(ConstData.isPrintEx)
			ex.printStackTrace();
		if (logger.isWarnEnabled()) {
			logger.warn("",ex);
		}
		throw new cn.edu.cust.levin.DAOException("");
	}
}
	
    //以下这个方法有待完善====================================================================================================

	
	public int getTeachersCount(List classlist) throws cn.edu.cust.levin.DAOException
	{
		return getTeachersCount(classlist, -1, null);
	}
	
	public int getTeachersCount(List classlist, int tchid, String tchName) throws cn.edu.cust.levin.DAOException
	{
		try{
			if(classlist == null || classlist.size() <= 0)
				return 0;
			
			Session ss = sessionFactory.getCurrentSession();
			String hql = "select count(*) from Teacher s where s.org in (:orgList)";
			if(tchid > 0)
				hql += " and s.id = :tchid";
			if(tchName != null && tchName.trim().length()>0)
				hql += " and s.name like :tchName";
			Query q = ss.createQuery(hql);
			q.setParameterList("orgList", classlist);
			if(tchid > 0)
				q.setParameter("tchid", new Integer(tchid));
			if(tchName != null && tchName.trim().length()>0)
				q.setParameter("tchName", "%"+tchName+"%");
			long c = ((Long)q.uniqueResult()).longValue();
			return (int)c;
			
		} catch (Exception ex) {
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException("");
		}
	}
	
	//这个方法也是有待完善====================================================================================================
	
	public List getTeachers(List classList, int size, int page) throws cn.edu.cust.levin.DAOException 
	{		
		return getTeachers(classList, -1, null,size,page);
	}

	public List getTeachers(List classList, int tchid, String tchName, int size, int page) throws cn.edu.cust.levin.DAOException 
	{		
		try{
			if(classList == null || classList.size() <= 0)
				return new ArrayList();
			
			Session ss = sessionFactory.getCurrentSession();
			Criteria cr = ss.createCriteria(Teacher.class);	
			cr.add(Restrictions.in("org", classList));
			if(tchid > 0)
				cr.add(Restrictions.eq("id", new Integer(tchid)));
			if(tchName != null && tchName.trim().length()>0)
				cr.add(Restrictions.like("name", tchName, MatchMode.ANYWHERE));
			cr.addOrder(Order.asc("id"));
			
			if(size > 0 && page > 0)
			{
				cr.setFirstResult((page-1)*size);
				cr.setMaxResults(size);
			}
			return cr.list();
			
		} catch (Exception ex) {
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException("");
		}
	}

	public Teacher getTeacher(String tchName) throws DAOException {
		// TODO Auto-generated method stub
		try{
			
			if(!SwitchTools.havaValue(tchName))
				return null;
			
			Session ss = sessionFactory.getCurrentSession();
			Criteria cr = ss.createCriteria(Teacher.class);	
			cr.add(Restrictions.eq("name", tchName));
			List list = cr.list();
			if(list!=null && !list.isEmpty())
				return (Teacher)list.get(0);
			else return null;
			
		} catch (Exception ex) {
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException("");
		}
	
	}
	
	
}
