package cn.cust.kyc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.AuthorDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.*;
import cn.edu.cust.levin.DAOException;

public class AuthorDBDao implements AuthorDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(Author.class);
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	
	@Override
	public void deleteAuthor(int ID) throws DAOException {
		try {
			session.delete(session.get(Author.class, ID));
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void saveAuthor(Author a) throws DAOException {
		try {
			session.save(a);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void updateAuthor(Author a) throws DAOException {
		try {
			session.saveOrUpdate(a);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public List<Author> getAuthors(int type, int id) throws DAOException {
		try {
			return session.createCriteria(Author.class).add(Expression.eq("type", type)).add(Expression.eq("outId", id)).list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public Author getAuthor(int ID) throws DAOException {
		try {
			return (Author) session.get(Author.class, ID);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public List<?> getAwardByAuthor(String voName, int i, String name) throws DAOException {
		try {
			Criteria cri = session.createCriteria(Author.class);
			cri.add(Expression.eq("type", i));
			cri.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
			cri.setProjection(Projections.distinct(Projections.property("outId")));
			List<Integer> outIds = cri.list();
			//不知为啥，outIds为空时，cri.add(Expression.in("ID", outIds))就会报错--::ERROR::第 1 行: ')' 附近有语法错误。
			if(outIds.size()==0)
				return new ArrayList();
			cri = session.createCriteria(Class.forName("cn.cust.kyc.vo."+voName));
			cri.add(Expression.in("ID", outIds));
			return cri.list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Author> getAuthorByOutId(String outId, String type){
		   List<Author> list=null;
		   String sql="from Author au where au.outId="+Integer.parseInt(outId)+" and au.type = " + Integer.parseInt(type);
			try {
			    list=session.createQuery(sql).list();
			} catch (Exception ex) {
				if (logger.isWarnEnabled()) { 
					logger.warn("",ex);
				}
			}
			return list;
	}
	
	public List<Author> getAuthorByPageAndProject(Pagination pagination,String outId, String type){
		try {
			Query q = null;
			String sql="from Author au where au.outId="+Integer.parseInt(outId)+" and au.type = " + Integer.parseInt(type);
			q=session.createQuery(sql);
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
}
