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

import cn.cust.kyc.dao.PFileDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.PFile;
import cn.edu.cust.levin.DAOException;

public class PFileDBDao implements PFileDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(PFileDBDao.class);
	
	private boolean isWhere = true;
	
	@Override
	public List<PFile> getPFileByProject(String nbbh) throws DAOException {
		try {
			return session.createQuery("from PFile where project.innerCode='"+nbbh+"' and delFlg=false").list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
	@Override
	public List<PFile> getPFileByProject(Pagination pagination,
			String year, String innerCode, String s) {
		try {
			String sql = "from PFile where project.innerCode='"+innerCode+"' and delFlg=false";
			Query q = null;
			if(!("all".equals(year)||year==null))
				sql+=" and theDate like '%"+year+"%'";
			if(!(s==null||"".endsWith(s)))
				sql+=" and " + s;
			System.out.println(sql);
			q = session.createQuery(sql);
			if(pagination!=null){
				q.setFirstResult((pagination.getPage()-1)*pagination.getSize());
				q.setMaxResults(pagination.getSize());
			}
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
	public List<PFile> getPFileByPageAndProject(Pagination pagination, String year,
			String nbbh) throws DAOException {
		try {
			Query q = null;
			if("all".equals(year)||year==null)
				q = session.createQuery("from PFile where project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("from PFile where project.innerCode='"+nbbh+"' and delFlg=false and theDate like '%"+year+"%'");
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
				q = session.createQuery("select max(theDate) from PFile where project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("select max(theDate) from PFile where delFlg=false");
			Object o = q.uniqueResult();
			if(o!=null)
				return ((Date) o).getYear()+1900;
			else
				return 0;
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
				q = session.createQuery("select min(theDate) from PFile where project.innerCode='"+nbbh+"' and delFlg=false");
			else
				q = session.createQuery("select min(theDate) from PFile where delFlg=false");
			Object o = q.uniqueResult();
			if(o!=null)
				return ((Date) o).getYear()+1900;
			else
				return 0;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void deletePFile(String fileCode) {
		session.delete(session.load(PFile.class, fileCode));
	}

	@Override
	public PFile savePFile(PFile PFile) {
		PFile pfile=(PFile) session.merge(PFile);
		return pfile;
	}
	
	@Override
	public void updatePFile(PFile PFile) {
		session.merge(PFile);
	}

	@Override
	public PFile getPFile(String fileCode) {
		return (PFile) session.get(PFile.class, fileCode);
	}

	@Override
	public List<PFile> getPFiles(String predix, Pagination pagination,
			String year, String s) {
		try {
			String sql = "from PFile where delFlg=false";
			Query q = null;
			if(s!=null&&!"".equals(s)){
				sql+=" and "+s;
			}
			if(!("all".equals(year)||year==null))
				sql+=" and theDate like '%"+year+"%'";
			if(!"".equals(predix)){
				sql+=" and project.innerCode like '"+predix+"%'";
			}
			sql=sql+" order by jsrq desc ";
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
	public List<PFile> getPFiles(String predix, String year, String s) {
		try {
			String sql = "from PFile where delFlg=false";
			Query q = null;
			if(s!=null&&!"".equals(s)){
				sql+=" and "+s;
			}
			if(!("all".equals(year)||year==null)){
				sql+=" and theDate like '%"+year+"%'";
			}
			if(!"".equals(predix)){
				sql+=" and project.innerCode like '"+predix+"%'";
			}
			sql=sql+" order by jsrq desc ";
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
	public List<Integer> getTheDateList(String innerCode) {
		Criteria cri = session.createCriteria(PFile.class).add(Expression.eq("delFlg", false));
		if(innerCode!=null)
			cri.add(Expression.eq("project.innerCode", innerCode));
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("theDate"));
		Iterator<Date> it = cri.setProjection(pl).list().iterator();
		Set<Date> set = new TreeSet();
		while(it.hasNext())
			set.add(it.next());
		List<Integer> list = new ArrayList<Integer>();
		for(Date i : set)
			list.add(i.getYear()+1900);
		return list;
	}

	private String getContactWorld() {
		if(isWhere ){
			isWhere = false;
			return " where ";
		}else
			return " and ";
	}

	@Override
	public String getMaxPFileKey(String predix) throws DAOException {
		try {
			String sql = "select max(pf.fileCode) from PFile pf where pf.fileCode like '"+predix+"%'";
			Query q = session.createQuery(sql);
			Object o = q.uniqueResult();
			System.out.println(sql);
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

}
