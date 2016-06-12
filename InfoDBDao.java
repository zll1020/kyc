package cn.cust.kyc.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import cn.cust.kyc.dao.InfoDao;
import cn.cust.kyc.vo.InfoTable;


public class InfoDBDao implements InfoDao {
	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
private org.hibernate.Session session = sessionFactory.getCurrentSession();
//		private org.hibernate.Session session = sessionFactory.openSession();

	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(InfoDBDao.class);

	
	@Override
	public InfoTable getInfoBykey(String key) {
		try {
			String sql = "from InfoTable it where it.keyword = '"+key+"'";// 
			 Query q = this.session.createQuery(sql);
			List<InfoTable> ilist=q.list();
/*			for(int i=0;i<ilist.size();i++)
				System.out.println("info:"+ilist.get(i).getId()+"   "+ilist.get(i).getKeyword()+"  "+ilist.get(i).getKeyindex());*/
			return (InfoTable)ilist.get(0);
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveInfo(InfoTable info) {
		// TODO Auto-generated method stub
		session.merge(info);
	}

	@Override
	public void update(InfoTable info) {
		// TODO Auto-generated method stub
		session.merge(info);
		/*Transaction tx=session.beginTransaction();
		session.update(info);
		session.flush();//
		tx.commit();*/
	}
}
