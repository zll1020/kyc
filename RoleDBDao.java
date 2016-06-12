/**
 * 
 */
package cn.cust.kyc.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.RoleDAO;
import cn.cust.kyc.vo.Permission;
import cn.cust.kyc.vo.Role;
import cn.cust.kyc.vo.Teacher;


public class RoleDBDao implements RoleDAO{

	private static final Serializable Integer = null;
	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	private List list;
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(RoleDBDao.class);
	
	public void delete(java.io.Serializable id) {
		org.hibernate.Session session = sessionFactory.getCurrentSession();
			Object obj = session.load(Role.class, id);
			session.delete(obj);
	}
	
	public void saveOrUpdate(Role entity) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(entity);

	}

	public java.util.List getAll() {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return session.createQuery("from Role").list();
		
	}

	public Role load(java.io.Serializable id) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return (Role)session.load(Role.class, id);
	}

	public java.util.List getPermission(java.io.Serializable id) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return session.createCriteria(Permission.class).add(org.hibernate.criterion.Expression.eq("roles.id", id)).list();
	}

	public java.util.List getTeacher(java.io.Serializable id) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return session.createCriteria(Teacher.class).add(org.hibernate.criterion.Expression.eq("role.id", id)).list();

	}
	
	/**
	 *  模糊查询角色
	 *  为 "" 则不进行判断
	 * @param name
	 * @return
	 */
	public List queryRoles(Role role){
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Criteria cr = session.createCriteria(Role.class);
			if(role.getName() != null) {
				cr.add(Restrictions.like("name", role.getName(), MatchMode.ANYWHERE));
			}
			return cr.list();
	}

	public Role getRole(int roleid) {
		// TODO Auto-generated method stub
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		return (Role) session.get(Role.class, new Integer(roleid));
	}
	
	/*public int getstatus(int st){
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		//String hql = "SELECT role.status FROM Role role";
		String hql="from Role";
		Query query = session.createQuery(hql);
		List list = query.list();
		Iterator itor=list.iterator();
		while(itor.hasNext()){
			Role role=(Role)itor.next();
			while(role.getId().equals(3)){
				st= role.getStatus();
			}
			Role rol=(Role)session.get(Role.class,new Integer(3));
			int sta=rol.getStatus();
		return sta;

		//return  (Integer) session.get(Role.class,new Integer (sta));
	}*/
	/*public int setstatus(){
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		Role rol=(Role)session.get(Role.class,new Integer(3));
		return 0;*/
		
	//}


	
	
	
}
