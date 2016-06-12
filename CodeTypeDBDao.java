package cn.cust.kyc.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;

import cn.cust.kyc.dao.CodeTypeDao;
import cn.cust.kyc.dao.cache.OrgCache;
import cn.cust.kyc.vo.Code;
import cn.cust.kyc.vo.CodeType;
import cn.cust.kyc.vo.Org;
import cn.edu.cust.levin.DAOException;

public class CodeTypeDBDao implements CodeTypeDao {
	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(CodeTypeDBDao.class);
	
	@Override
	public List<CodeType> getCodeTypes() throws DAOException {
		try {
			Criteria cri = session.createCriteria(CodeType.class);
			List<CodeType> list = cri.list();
			for(CodeType codeType : list){
				codeType.getCodes().size();
			}
			return list;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public boolean addCodeType(String codeType) {
		try {
			CodeType ct = null;
			try {
				ct = (CodeType) session.load(CodeType.class, codeType);
				return false;
			} catch (ObjectNotFoundException e) {
				//正常情况下，抛出该异常
			}
			ct = new CodeType();
			ct.setType(codeType);
			session.save(ct);
			return true;
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteCodeType(String codeType) {
		try {
			CodeType ct;
			try {
				ct = (CodeType) session.load(CodeType.class, codeType);
			} catch (ObjectNotFoundException e) {
				return false;
			}
			session.delete(ct);
			return true;
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean renameCodeType(String codeType, String _codeType) {
		try {
			CodeType ct = null;
			try {
				ct = (CodeType) session.load(CodeType.class, codeType);
				return false;
			} catch (ObjectNotFoundException e) {
				//正常情况下，抛出该异常
			}
			try {
				ct = (CodeType) session.load(CodeType.class, _codeType);
			} catch (ObjectNotFoundException e) {
				return false;
			}
			Set<Code> set = ct.getCodes();
			session.delete(ct);
			ct = new CodeType();
			ct.setType(codeType);
			session.save(ct);
			for(Code obj:set){
				obj.setType(codeType);
				session.save(obj);
			}
			return true;
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public CodeType getCodeType(String codeType) {
		try {
			return (CodeType) session.load(CodeType.class, codeType);
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Code> getCodesByType(String codeType) {
		try {
			return session.createQuery("from Code where type='"+codeType+"'").list();
		} catch (HibernateException ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}
}
