package cn.cust.kyc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.dao.AchieveCristicDao;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.AchieveCristic;
import cn.cust.kyc.vo.Author;
import cn.cust.kyc.vo.Person;
import cn.edu.cust.levin.DAOException;

public class AchieveCristicDBDao implements AchieveCristicDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(AchieveCristic.class);
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	
	@Override
	public void deleteCristic(int ID) throws DAOException {
		try {
			session.delete(session.get(AchieveCristic.class, ID));
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void saveCristic(AchieveCristic ac) throws DAOException {
		try {
			session.save(ac);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public void updateCristic(AchieveCristic ac) throws DAOException {
		try {
			session.saveOrUpdate(ac);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public List<AchieveCristic> getAchieveCristics(int achievementId) throws DAOException {
		try {
			return session.createCriteria(AchieveCristic.class).add(Expression.eq("achievement.ID", achievementId)).list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public AchieveCristic getAchieveCristic(int ID) throws DAOException {
		try {
			return (AchieveCristic) session.get(AchieveCristic.class, ID);
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}

	@Override
	public List<AchieveCristic> getAchieveCristicsByName(String type, String name) throws DAOException {
		try {
			return session.createQuery("from AchieveCristic where name like '%"+name+"%'").list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
			throw new cn.edu.cust.levin.DAOException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<AchieveCristic> getAchieveCristicByAchievementId(String achievementId){
		   List<AchieveCristic> list=null;
		   String sql="from AchieveCristic ac where achievement.ID="+Integer.parseInt(achievementId)+"";
			try {
				System.out.println("mark1");
			    list=session.createQuery(sql).list();
			    System.out.println("mark3");
			} catch (Exception ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("",ex);
				}
			}
			return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<AchieveCristic> getAchieveCristicByPageAndAchievement(Pagination pagination,String achievementId){
		try {
			Query q = null;
			String sql="from AchieveCristic ac where achievement.ID="+Integer.parseInt(achievementId)+"";
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
	
	@Override
	public List<AchieveCristic> getAchieveCristic(String predix, Pagination pagination, String s) {
		try {
			String sql = "from AchieveCristic ac where 1=1 ";
			Query q = null;
			if(s!=null&&!"".equals(s)){
				sql+=" and "+s;
			}
			if(!"".equals(predix)){
				sql+=" and achievement.innerId like '"+predix+"%'";
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
	public List<AchieveCristic> getAchieveCristic(String predix, String s) {
		try {
			String sql = "from AchieveCristic ac where 1=1 ";
			Query q = null;
			if(s!=null&&!"".equals(s)){
				sql+=" and "+s;
			}
			if(!"".equals(predix)){
				sql+=" and achievement.innerId like '"+predix+"%'";
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
	public List<AchieveCristic> getAchieveCristicByYear(String year) {
		// TODO Auto-generated method stub
	//	ProjectService ps=S
		return null;
	}

	@Override
	public List<Integer> getAchieveCristicYears() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> getCristicYears() {
		// TODO Auto-generated method stub
		List<Integer> crisYearList=new ArrayList<Integer>();
		Query q=session.createQuery("select max(djnd) from AchieveCristic");
		Integer max=new Integer((String)q.uniqueResult());
		
		q=session.createQuery("select min(djnd) from AchieveCristic");
		Integer min=new Integer((String)q.uniqueResult());
		
		for(int i=min.intValue();i<=max.intValue();i++){
			crisYearList.add(new Integer(i));
		}
		return crisYearList;
	}

}
