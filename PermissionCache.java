package cn.cust.kyc.dao.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.cust.kyc.vo.Operator;
import cn.cust.kyc.vo.Permission;
import cn.cust.kyc.vo.Role;

public class PermissionCache {
	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	private static List permissions = new ArrayList();
	private static boolean flag = false;
	private static PermissionCache perCache;
	
	private PermissionCache(){
		if(!flag){
			updatePermissionCache();
		}
	}
	
	public static PermissionCache getInstance(){
		if(perCache == null)
			perCache = new PermissionCache();
		return perCache;
	}
	
	// 刷新缓存
	public synchronized void  refresh(){		
	  flag = false;
	  if(!flag){
		  updatePermissionCache();
		}
	}
	
	private synchronized void updatePermissionCache(){
		org.hibernate.Session session = sessionFactory.getCurrentSession();
		permissions = session.createQuery("from Permission").list();
		flag = true;
	}

	// 返回所有权限
	public List getPermissions() {
		return permissions;
	}	
	
	// 返回属于某个操作的权限
	public List getPermissionsByOperator(Operator op){
		List list = new ArrayList();
		Iterator perIt = permissions.iterator();
		while(perIt.hasNext()){
			Permission per = (Permission)perIt.next();
			if(per.getOperator().getId().intValue()== op.getId().intValue())
				list.add(per);
		}
		return list;
	}
	
}
