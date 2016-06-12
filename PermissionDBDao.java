/**
 * 
 */
package cn.cust.kyc.dao.impl;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.PermissionDAO;
import cn.cust.kyc.dao.cache.PermissionCache;
import cn.cust.kyc.vo.Permission;

/**
 * 
 */
public class PermissionDBDao implements PermissionDAO{

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(PermissionDBDao.class);
	
	public void delete(java.io.Serializable id) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			session.delete(id);
			// 刷新缓存
			PermissionCache.getInstance().refresh();
	}
	
	public void saveOrUpdate(Permission entity) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(entity);
			// 刷新缓存
			//PermissionCache.getInstance().refresh();
	}

	public java.util.List getAll() {
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Permission").list();
	}

	public Permission load(java.io.Serializable id) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return (Permission)session.load(Permission.class, id);
	}
	
	/**
	 *  模糊查询权限
	 *  为 "" 则不进行判断
	 * @param name
	 * @return
	 */
	public List queryPermissions(Permission permission) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Criteria cr = session.createCriteria(Permission.class);
			if(permission.getInfo() != null && permission.getInfo().trim().length()>0) {
				cr.add(Restrictions.eq("info", permission.getInfo()));
			}
			if(permission.getRole() != null) {
				cr.add(Restrictions.eq("roles", permission.getRole()));
			}
			if(permission.getOperator() != null) {
				cr.add(Restrictions.eq("operator", permission.getOperator()));
			}
			if(permission.getStart() != null) {
				cr.add(Restrictions.ge("start", permission.getStart()));
			}
			if(permission.getEndDate() != null) {
				cr.add(Restrictions.le("endDate", permission.getEndDate()));
			}
			return cr.list();
	}

	public List getPermissionByRoleId(int id) {
		// TODO Auto-generated method stub
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		Criteria cri = session.createCriteria(Permission.class);
		cri.add(Expression.eq("role.id", new Integer(id)));
		return cri.list();
	}

	public List<Permission> getPermissionByOrgId(int orgid) {
		// TODO Auto-generated method stub
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		Criteria cri = session.createCriteria(Permission.class);
		cri.add(Expression.eq("org.id", orgid));
		return cri.list();
	}
}
