package cn.cust.kyc.dao.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import cn.cust.kyc.bo.impl.TeacherServiceImpl;
import cn.cust.kyc.dao.OrgDAO;
import cn.cust.kyc.dao.impl.OrgDBDAO;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.SwitchTools;
import cn.cust.kyc.util.TreeList;
import cn.cust.kyc.vo.Org;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;

public class OrgCache {

	/**
	 * @param args
	 */
	private static List list = new ArrayList();
	private static boolean flag = false;
	private static Log logger = LogFactory.getLog(TeacherServiceImpl.class);
	private static OrgCache orgCache;
	
	private OrgCache(){
		if(!flag){
			updateOrgCache();
		}		
	}
	
	public static OrgCache getInstance(){
		if(orgCache == null)
			orgCache = new OrgCache();
		return orgCache;
	}
	
	// 重新从数据库中读取 org 数据
	private synchronized void updateOrgCache()
	{
		OrgDBDAO dao = new OrgDBDAO();
		SessionFactory sessionFactory =cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
		org.hibernate.Session session = sessionFactory.getCurrentSession();
	
		list = session.createQuery("from Org order by id").list();
		flag = true;
	}
	
	/**
	 * 建立组织树
	 */
	public TreeList getOrgTree(int orgid)throws BusinessException 
	{	
		
		try {
			OrgDAO oDao = new OrgDBDAO();
			Org o = oDao.load(new Integer(orgid));
			return getOrgTree(o);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	/**
	 * 建立组织树
	 */
	private TreeList getOrgTree(Org org)
	{
		TreeList treeList = new TreeList();
		
		treeList.setOrg(org);
		
		List childList = getChildOrg(org.getId().intValue());
		
		if(childList != null)
		{
			for(int i = 0 ; i < childList.size(); i++)
			{
				Org o = (Org)childList.get(i);
				TreeList childTree = getOrgTree(o);
				if(childTree != null)
					treeList.addChild(childTree);
			}
		}
		
		return treeList;
	}
	
	/**
	 *  得到指定组织的直接子节点
	 * @param orgid
	 * @return
	 */
	public List getChildOrg(int orgid)
	{
//		if(!flag){
//			updateOrgCache();
//		}	
		
		List childList = new ArrayList();
		for(int i = 0 ; i < list.size(); i++)
		{
			Org org = (Org)list.get(i);
			if(org.getParent()!= null && org.getParent().getId().intValue() == orgid)
				childList.add(org);
		}
		
		return childList;
		
	}
	
	/**
	 *  得到指定组织的所有子节点
	 * @param orgid
	 * @return
	 */
	public List getAllChildOrg(int orgid)
	{
//		if(!flag){
//			updateOrgCache();
//		}	
		
		List childList = new ArrayList();
		
		// 先得到直接子节点
		List reChildList = getChildOrg(orgid);
		childList.addAll(reChildList);
		
		// 然后得到直接子节点的所有子节点
		for(int i = 0 ; i < reChildList.size(); i++)
		{
			childList.addAll(getAllChildOrg(((Org)reChildList.get(i)).getId().intValue()));
		}
		
		return childList;
	}


	
	// 得到指定节点的所有父节点
	public List getParents(int orgid)
	{
//		if(!flag){
//			updateOrgCache();
//		}	
		
			List orgList = new ArrayList();
//			OrgDAO oDao = new OrgDBDAO();
			Org org = getOrg(orgid);//oDao.load(orgid);
			if(org.getParent() != null)
				addParentOrg(org.getParent(), orgList);
			return orgList;
	}
	
	/**
	 *  getParent(int orgid) 的 辅助方法
	 */
	private void addParentOrg(Org org, List orgList)
	{
		
		orgList.add(org);
		if(org.getParent() != null)
			addParentOrg(org.getParent(), orgList);
	}
	
	
	// 刷新缓存
	public synchronized void  refresh(){		
	  flag = false;
	  if(!flag){
			updateOrgCache();
		}
	}

	//获取permission 传递role   获取operator
//	public static void main(String[] args) {
//		org.hibernate.SessionFactory sessionFactory = 
//			cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
//		org.hibernate.Session session = sessionFactory.getCurrentSession();
//	    Transaction tx = session.beginTransaction();
//		List list = OrgCache.getInstance().getAllChildOrg(397);
//		Iterator it = list.iterator();
//		while(it.hasNext()){
//			Org o = (Org)it.next();
//			System.out.println("####### " + o.getName());
//		}
//		tx.commit();
//
//	}
	
	// 返回所有的组织机构
	public List getOrgs(){
//		if(!flag){
//			updateOrgCache();
//		}
		return list;
	}
	
	// 根据orgid 获取 org
	public Org getOrg(int orgId){
		
		Iterator orgIt = list.iterator();
		while(orgIt.hasNext()){
			Org org = (Org)orgIt.next();
			if(org.getId().intValue() == orgId)
				return org;
		}
		return null;
	}
	
	/**
	 * 查询所有指定类型的组织机构
	 * @param orgType
	 * @return
	 */
	public List getOrgsByType(String orgType){
		List tpOrgs = new ArrayList();
		for(int i=0;i<list.size();i++){
			Org o=(Org)list.get(i);
			if(o.getType().equalsIgnoreCase(orgType))
				tpOrgs.add(o);
		}
		return tpOrgs;
	}
	
	/**
	 * 查询指定org 的所有指定类型的子结点
	 * @param orgid
	 * @param orgType
	 * @return
	 */
	public List getOrgsByType(int orgid, String orgType){
		List tpOrgs = new ArrayList();
		
		List allL = getAllChildOrg(orgid);
		for(int i=0;i<allL.size();i++){
			Org o=(Org)allL.get(i);
			if(o.getType().equalsIgnoreCase(orgType))
				tpOrgs.add(o);
		}
		
		return tpOrgs;
	}
	
	
	///===========================
	
	/**
	 * 通过组织机构的名字精确查询组织机构
	 */
	public List getOrgsByName(String orgName){
		List tpOrgs = new ArrayList();
		for(int i=0;i<list.size();i++){
			Org o=(Org)list.get(i);
			if(o.getName().equalsIgnoreCase(orgName))
				tpOrgs.add(o);
		}
		return tpOrgs;
	}
	
	/**
	 * 通过组织机构的名字模糊查询组织机构
	 */
	public List getOrgsByLikeName(String orgName){
		List tpOrgs = new ArrayList();
		for(int i=0;i<list.size();i++){
			Org o=(Org)list.get(i);
			if(o.getName().indexOf(orgName.trim())>=0)
				tpOrgs.add(o);
		}
		return tpOrgs;
	}
	
	/**
	 * 查询指定组织机构的所有父组织和子组织, 不包含指定组织本身
	 * @param orgid
	 * @return
	 */
	public List getAllParentsChildrenOrg(int orgid){
		List tpOrgs = new ArrayList();
		// 加入所有父组织
		tpOrgs.addAll(getParents(orgid));
		// 加入所有子组织
		tpOrgs.addAll(getAllChildOrg(orgid));
		return tpOrgs;
	}
	
	/**
	 * 查询指定组织机构列表中每个组织机构的所有父组织和子组织, 并且包含查询的组织机构列表
	 * 记录不重复
	 * @param orgList
	 * @return
	 */
	public List getAllParentsChildrenOrg(List orgList){
		if(orgList == null || orgList.isEmpty())
			return new ArrayList();
		
		Set tpSet = new HashSet();
		// 先加入查询组织机构自身
		tpSet.addAll(orgList);
		// 再加入查询父组织机构和子组织机构
		for(int i=0;i<orgList.size();i++){		
			Org tp=(Org)orgList.get(i);
			tpSet.addAll(getAllParentsChildrenOrg(tp.getId().intValue()));
		}
		
		return SwitchTools.setToList(tpSet);
		
	}
	
	/**
	 * 获取某个组织机构所属的学院组织机构
	 * 没有返回 null
	 * @param orgid
	 * @return
	 */
	public Org getInsOrg(int orgid){
		Org org = getOrg(orgid);
		if(org == null)
			return null;
		// 先判断自己是否就是学院
		if(org.getType().equalsIgnoreCase(ConstData.OrgType_Institution))
			return org;
		// 再从所有父组织列表中查询学院,有就返回，没有返回null
		List prList = getParents(orgid);
		if(prList == null || prList.isEmpty())
			return null;
		for(int i=0;i<prList.size();i++){
			Org pr=(Org)prList.get(i);
			if(pr.getType().equalsIgnoreCase(ConstData.OrgType_Institution))
				return pr;
		}
		return null;
	}
}
