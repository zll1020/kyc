/**
 * 
 */
package cn.cust.kyc.dao.impl;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.OrgDAO;
import cn.cust.kyc.dao.cache.OrgCache;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.vo.Org;
import cn.edu.cust.levin.DAOException;

/**
 * 
 */
public class OrgDBDAO implements OrgDAO{

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();

	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(OrgDBDAO.class);
		
	public void delete(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
		try {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Object obj = session.load(Org.class, id);
			session.delete(obj);
			// 刷新缓存
			OrgCache.getInstance().refresh();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
	public void saveOrUpdate(Org entity) throws cn.edu.cust.levin.DAOException {
		try{
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(entity);
			// 刷新缓存
			OrgCache.getInstance().refresh();
		}catch(Exception ex){
			if(logger.isWarnEnabled()){
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	// 2009-08-20 sh 注释掉的
//	public java.util.List getAll() throws cn.edu.cust.levin.DAOException {
//		try {
//			org.hibernate.Session session = sessionFactory.getCurrentSession();
//			return session.createQuery("from " + model.xgb.vo.Org.class.getName() + " lv_")
//					.list();
//		} catch (Exception ex) {
//			if (logger.isWarnEnabled()) {
//				logger.warn("",ex);
//			}
//			throw new cn.edu.cust.levin.DAOException();
//		}
//		
//	}

	public Org load(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
		try {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return (Org)session.load(Org.class, id);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	// 2009-08-20 sh 注释掉的
//	public java.util.List getChild(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
//		try{
//			org.hibernate.Session session = sessionFactory.getCurrentSession();
//			return session.createCriteria(model.xgb.vo.Org.class).add(org.hibernate.criterion.Expression.eq("parent.id", id)).list();
//		} catch (Exception ex) {
//			if (logger.isWarnEnabled()) {
//				logger.warn("",ex);
//			}
//			throw new cn.edu.cust.levin.DAOException("");
//		}
//	}

	// 2009-08-20 sh 注释掉的
//	public java.util.List getPermission(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
//		try{
//			org.hibernate.Session session = sessionFactory.getCurrentSession();
//			return session.createCriteria(model.xgb.vo.Permission.class).add(org.hibernate.criterion.Expression.eq("org.id", id)).list();
//		} catch (Exception ex) {
//			if (logger.isWarnEnabled()) {
//				logger.warn("",ex);
//			}
//			throw new cn.edu.cust.levin.DAOException("");
//		}
//	}
//
	// 2009-08-20 sh 注释掉的
//	public java.util.List getStudent(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
//		try{
//			org.hibernate.Session session = sessionFactory.getCurrentSession();
//			return session.createCriteria(model.xgb.vo.Student.class).add(org.hibernate.criterion.Expression.eq("org.id", id)).list();
//		} catch (Exception ex) {
//			if (logger.isWarnEnabled()) {
//				logger.warn("",ex);
//			}
//			throw new cn.edu.cust.levin.DAOException("");
//		}
//	}
//
	// 2009-08-20 sh 注释掉的
//	public java.util.List getTeacher(java.io.Serializable id) throws cn.edu.cust.levin.DAOException {
//		try{
//			org.hibernate.Session session = sessionFactory.getCurrentSession();
//			return session.createCriteria(model.xgb.vo.Teacher.class).add(org.hibernate.criterion.Expression.eq("org.id", id)).list();
//		} catch (Exception ex) {
//			if (logger.isWarnEnabled()) {
//				logger.warn("",ex);
//			}
//			throw new cn.edu.cust.levin.DAOException("");
//		}
//	}
	
	/**
	 *  模糊查询组织机构
	 * @return
	 */
	public List queryOrgs(List orgList, Org org)
		throws cn.edu.cust.levin.DAOException {
		try{
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Criteria cr = session.createCriteria(Org.class);
			if(orgList != null && !orgList.isEmpty()){
				cr.add(Restrictions.in("parent", orgList));
			}
			if(org.getName() != null) {
				cr.add(Restrictions.like("name", org.getName(), MatchMode.ANYWHERE));
			}
			return cr.list();
			
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException("");
		}
	}
	
	public List getinsname() throws DAOException {
		try {
            List shtypes=new ArrayList();
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Criteria cri = session.createCriteria(Org.class);
			cri.add(Restrictions.eq("layer",new Integer(2)));
			List oships = cri.list();
			return oships;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("", ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	public Org getorg(int id)throws DAOException{
		try {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return (Org)session.load(Org.class, new Integer(id));
		} catch (Exception ex) {
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
		
	}
	
	public Org getorgbyname(String name)throws DAOException{
		try{
			Session session = sessionFactory.getCurrentSession();
			Criteria cri = session.createCriteria(Org.class);
			cri.add(Expression.eq("name", name));
			return(Org) cri.uniqueResult();
			
		}catch(Exception ex){
			ex.printStackTrace();
		    if(logger.isWarnEnabled()){
		    	logger.warn("", ex);
		    }
		    throw new DAOException();
		}
	}
}
