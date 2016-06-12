package cn.cust.kyc.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import cn.cust.kyc.dao.CodeDao;
import cn.cust.kyc.vo.Code;
import cn.edu.cust.levin.DAOException;

public class CodeDBDao implements CodeDao {
	
	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(CodeDBDao.class);
	
	@Override
	public void delete(int id) throws DAOException {
		try {
			session.delete(session.load(Code.class, id));
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public List<Code> getCodesByNumber(int number, String type) throws DAOException {
		try {
			Criteria cri = session.createCriteria(Code.class);
			cri.add(Expression.eq("number", number));
			cri.add(Expression.eq("type", type));
			return cri.list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public int getMaxNumber(String type) throws DAOException {
		try {
			Query q = session.createQuery("select max(c.number) from Code c where c.type='"+type+"'");
			Object o = q.uniqueResult();
			if(o==null)
				return 0;
			else
				return ((Integer) o).intValue();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void saveCode(Code c) throws DAOException {
		try {
			session.save(c);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void update(int id, String name, String info) throws DAOException {
		try {
			Code c = (Code) session.load(Code.class, id);
			c.setName(name);
			c.setInfo(info);
			session.update(c);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
		
	}

}
