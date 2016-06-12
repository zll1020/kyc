/**
 * 
 */
package cn.cust.kyc.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.DirDAO;
import cn.cust.kyc.vo.Dir;
import cn.cust.kyc.vo.Operator;
import cn.edu.cust.levin.DAOException;

/**
 * 
 */
public class DirDBDao implements DirDAO{

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(DirDBDao.class);
	
	public void delete(java.io.Serializable id) throws DAOException {
		try{
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Object obj = session.load(Dir.class, id);
			session.delete(obj);
		}catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
	public void saveOrUpdate(Dir entity) throws DAOException {
			try {
				org.hibernate.Session session = sessionFactory.getCurrentSession();
				session.saveOrUpdate(entity);
			}catch (Exception ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("",ex);
				}
				throw new cn.edu.cust.levin.DAOException();
			}
	}

	public java.util.List getAll() throws DAOException {
		try {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return session.createQuery("from " + Dir.class.getName() + " lv_").list();
		}catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
		
	}

	public Dir load(java.io.Serializable id) throws DAOException{
			try {
				org.hibernate.Session session = sessionFactory.getCurrentSession();
				return (Dir)session.load(Dir.class, id);
			}catch (Exception ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("",ex);
				}
				throw new cn.edu.cust.levin.DAOException();
			}
	}

	public java.util.List getAction(java.io.Serializable id) throws DAOException{
			try {
				org.hibernate.Session session = sessionFactory.getCurrentSession();
				return session.createCriteria(Operator.class).add(org.hibernate.criterion.Expression.eq("dir.id", id)).list();
			}catch (Exception ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("",ex);
				}
				throw new cn.edu.cust.levin.DAOException();
			}
	}
	
	/**
	 *  模糊查询目录
	 *  为 "" 则不进行判断
	 * @param name
	 * @return
	 * @throws DAOException 
	 */
	public List queryDirs(Dir dir) throws DAOException{
			try {
				org.hibernate.Session session = sessionFactory.getCurrentSession();
				Criteria cr = session.createCriteria(Dir.class);
				if(dir.getName() != null) {
					cr.add(Restrictions.like("name", dir.getName(), MatchMode.ANYWHERE));
				}
				return cr.list();
			}catch (Exception ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("",ex);
				}
				throw new cn.edu.cust.levin.DAOException();
			}
	}
	
	public List getAllBySort() throws DAOException{
		 try {
			List dirs = null;
				org.hibernate.Session session = sessionFactory.getCurrentSession();
				Criteria cri = session.createCriteria(Dir.class);
				cri.addOrder(Order.asc("sortid"));
				dirs = cri.list();
			return dirs;
		}catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	public int getLastDir() throws DAOException {
		try {
			int size=0;
				org.hibernate.Session session = sessionFactory.getCurrentSession();
				Criteria cr = session.createCriteria(Dir.class);
				cr.addOrder(Order.desc("id"));
				List dirlist=cr.list();
				if(dirlist.size()>0){
					for(int i=0;i<dirlist.size();i++){
						Dir dir=(Dir)dirlist.get(i);
						size=dir.getId().intValue();
						break;
					}
			  }
			return size;
		}catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
}
