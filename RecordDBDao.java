package cn.cust.kyc.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;

import cn.cust.kyc.dao.RecordDao;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Record;
import cn.cust.kyc.vo.Teacher;

public class RecordDBDao implements RecordDao {

	private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
	org.hibernate.Session session = sessionFactory.getCurrentSession();
	
	private static org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(RecordDBDao.class);
	
	boolean isWhere;
	
	private String getContactWorld() {
		if(isWhere){
			isWhere = false;
			return " where ";
		}else
			return " and ";
	}
	
	@Override
	public void delete(Record r) {
		session.delete(r);
	}

	@Override
	public Object getNewTable(Record r) {
		try {
			if(r.getIDType())
				return session.get(Class.forName("cn.cust.kyc.vo." + r.getTableName()), Integer.parseInt(r.getNewId()));
			else
				return session.get(Class.forName("cn.cust.kyc.vo." + r.getTableName()), r.getNewId());
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getOldTable(Record r) {
		try {
			if(r.getIDType())
				return session.get(Class.forName("cn.cust.kyc.vo." + r.getTableName()), Integer.parseInt(r.getOldId()));
			else
				return session.get(Class.forName("cn.cust.kyc.vo." + r.getTableName()), r.getOldId());
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Record getRecord(int id) {
		return (Record) session.get(Record.class, id);
	}

	@Override
	public List<Record> getRecords(Pagination pagination, String s, Teacher teacher, boolean b) {
		try {
			isWhere = true;
			String sql = "from Record";
			Query q = null;
			if(s!=null&&!"".equals(s))
				sql+= getContactWorld() + s;
			if(teacher!=null)
				sql+= getContactWorld() + "applyer.id = "+teacher.getId();
			if(b)
				sql+= getContactWorld() + "flg = "+ConstData.RecordApplied;
			else
				sql+= getContactWorld() + "flg <> "+ConstData.RecordApplied;
			sql+=" order by id desc";
			System.out.println(sql);
			q = session.createQuery(sql);
			if(pagination==null)
				return q.list();
			pagination.setResultCount(q.list().size());
			pagination.setPageCount(pagination.getResultCount()/pagination.getSize() + ((pagination.getResultCount()%pagination.getSize()==0)?0:1));
			q.setFirstResult((pagination.getPage()-1)*pagination.getSize());
			q.setMaxResults(pagination.getSize());
			return q.list();
		} catch (Exception ex) {
			if (logger.isWarnEnabled()) {
				logger.warn("",ex);
			}
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void saveRecord(Record r) {
		session.save(r);
	}

	@Override
	public void updateObject(Object oo) {
		session.merge(oo);
	}

	@Override
	public void updateRecord(Record r) {
		session.merge(r);	
	}

	@Override
	public void deleteObject(Object no) {
		session.delete(no);
	}

}
