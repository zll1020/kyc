/**
 * 
 */
package cn.cust.kyc.dao.impl;

import java.io.Serializable;
import java.util.List;

import cn.cust.kyc.dao.OperatorDAO;
import cn.cust.kyc.vo.Operator;
import cn.cust.kyc.vo.Permission;
import cn.edu.cust.levin.DAOException;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * 
 */
public class OperatorDBDao implements OperatorDAO{

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(OperatorDBDao.class);
	
	public void delete(java.io.Serializable id) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Object obj = session.load(Operator.class, id);
			session.delete(obj);
	}
	
	public void saveOrUpdate(Operator entity) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(entity);
	}

	public List getAll() {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return session.createQuery("from Operator").list();
	}

	public Operator load(Integer id) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return (Operator)session.get(Operator.class, id);
	}

	public List getPermission(java.io.Serializable id) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			return session.createCriteria(Permission.class).add(org.hibernate.criterion.Expression.eq("action", id)).list();
	}
	public List getAllBySort(){
		 List operators = null;
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Criteria cri = session.createCriteria(Operator.class);
			cri.addOrder(Order.asc("sortid"));
			operators = cri.list();
		return operators;
	}
	
	/**
	 *  模糊查询操作
	 *  为 "" 则不进行判断
	 * @param name
	 * @return
	 */
	public List queryOperators(Operator op) {
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Criteria cr = session.createCriteria(Operator.class);
			if(op.getId() != null) {
				cr.add(Restrictions.eq("id", op.getId()));
			}
			if(op.getName() != null) {
				cr.add(Restrictions.like("name", op.getName(), MatchMode.ANYWHERE));
			}
			if(op.getEnterUrl() != null) {
				cr.add(Restrictions.like("enterUrl", op.getEnterUrl(), MatchMode.ANYWHERE));
			}
			if(op.getType() != null) {
				cr.add(Restrictions.like("type", op.getType(), MatchMode.ANYWHERE));
			}
			if(op.getClassname() != null) {
				cr.add(Restrictions.like("classname", op.getClassname(), MatchMode.ANYWHERE));
			}
			if(op.getDir() != null) {
				cr.add(Restrictions.eq("dir", op.getDir()));
			}
			return cr.list();
	}
	
	public int getLastOp() {
		 int size=0;
			org.hibernate.Session session = sessionFactory.getCurrentSession();
			Criteria cr = session.createCriteria(Operator.class);
			cr.addOrder(Order.desc("id"));
			List oplist=cr.list();
			if(oplist.size()>0){
				for(int i=0;i<oplist.size();i++){
					Operator op=(Operator)oplist.get(i);
					size=op.getId().intValue();
					break;
				}
		  }
		return size;
	}

	@Override
	public Operator load(Serializable id) throws DAOException {
		// TODO Auto-generated method stub
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		return (Operator)session.get(Operator.class, id);
	}
}
