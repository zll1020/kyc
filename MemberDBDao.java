package cn.cust.kyc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import cn.cust.kyc.dao.MemberDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Author;
import cn.cust.kyc.vo.Member;
import cn.edu.cust.levin.DAOException;

public class MemberDBDao implements MemberDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(MemberDBDao.class);
	
	@Override
	public void saveMember(Member member) {
		session.save(member);
	}
	
	@Override
	public void updateMember(Member member) {
		session.update(member);
	}

	@Override
	public Member getMember(int memberId) {
		return (Member) session.get(Member.class, memberId);
	}

	@Override
	public List<Member> getMembers(Pagination pagination, String s) {
		try {
			String sql = "from Member";
			Query q = null;
			if(s!=null&&!"".equals(s)){
					sql+=" where "+s;
			}
			System.out.println(sql);
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
	public List<Member> getMembers(String s) {
		try {
			String sql = "from Member";
			Query q = null;
			if(s!=null&&!"".equals(s)){
					sql+=" where "+s;
			}
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
	public List<String> getOrganizations() throws DAOException {
		try {
			Criteria cri = session.createCriteria(Member.class);
			cri.setProjection(Projections.distinct(Projections.property("organization")));
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
	public void deleteMember(int id) throws DAOException {
		try {
			session.delete(session.get(Member.class, id));
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn(ex.getMessage() ,ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public List<Member> getMemberInfoListByPredix(String strpredix) {
		try {
			List<Member> al = new ArrayList<Member>();
			if(strpredix == null)
				return al;
			strpredix = strpredix.trim();
			if(strpredix.length() == 0)
				return al;
			//自动截断字符，大于20字节
			if(strpredix.length() > 20)
				strpredix = strpredix.substring(0, 20);
			if(!strpredix.endsWith("%"))
				strpredix += "%";
			
			String sql = "from Member";
			Query q = null;
			sql+=" where xm like '"+strpredix+"'";
			System.out.println(sql);
			q = session.createQuery(sql);
			//查询结果长度
			q.setMaxResults(15);
			
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
