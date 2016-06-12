package cn.cust.kyc.bo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import cn.cust.kyc.bean.OptGroup;
import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.dao.CodeDao;
import cn.cust.kyc.dao.CodeTypeDao;
import cn.cust.kyc.dao.DAOFactory;
import cn.cust.kyc.dao.DirDAO;
import cn.cust.kyc.dao.OperatorDAO;
import cn.cust.kyc.dao.OrgDAO;
import cn.cust.kyc.dao.PermissionDAO;
import cn.cust.kyc.dao.ProjectDao;
import cn.cust.kyc.dao.RoleDAO;
import cn.cust.kyc.dao.TeacherDAO;
import cn.cust.kyc.dao.cache.OrgCache;
import cn.cust.kyc.dao.cache.PermissionCache;
import cn.cust.kyc.dao.impl.OrgDBDAO;
import cn.cust.kyc.dao.impl.TeacherDBDAO;
import cn.cust.kyc.util.Md5Util;
import cn.cust.kyc.util.MyList;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.SwitchTools;
import cn.cust.kyc.util.TreeList;
import cn.cust.kyc.vo.Code;
import cn.cust.kyc.vo.CodeType;
import cn.cust.kyc.vo.Dir;
import cn.cust.kyc.vo.Operator;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Permission;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Role;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;

public class ManagerServiceImpl implements ManagerService {

private static Log logger = LogFactory.getLog(ManagerServiceImpl.class);
private org.hibernate.SessionFactory sessionFactory = cn.edu.cust.levin.persistence.HibernateUtil.getSessionFactory();
    private DirDAO dirdao;
    private OperatorDAO operdao;
    private OrgDAO orgdao;
    private PermissionDAO perdao;
    private RoleDAO roledao;
    private TeacherDAO teadao;
   
    public ManagerServiceImpl(){
    	dirdao = DAOFactory.getDirDAO();
    	operdao = DAOFactory.getOperDAO();
    	orgdao = DAOFactory.getOrgDAO();
    	perdao = DAOFactory.getPerDAO();
    	roledao = DAOFactory.getRoleDAO();
    	teadao = DAOFactory.getTeacherDAO();
    }
    
    public Org getOrg(int orgid) throws BusinessException {
		Org org = null;
		try {
			org = (Org)orgdao.load(new Integer(orgid));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BusinessException();
		}
		return org;
	}
    
    public List getOrg()throws BusinessException{
    	OrgCache oc = OrgCache.getInstance();
    	List orgs = null;
    	orgs = oc.getOrgs();
    	return orgs;
    }
 

    public List getOrg(int orgid,Org org) throws BusinessException {
    	String orgName = org.getName().trim();
    	try {    		
    		List thisList = new ArrayList();
    		Org thisOrg =OrgCache.getInstance().getOrg(orgid);
	    	thisList.add(thisOrg);
	    	thisList.addAll(OrgCache.getInstance().getAllChildOrg(orgid));
    		if(orgName.equals("")) {
	    		List list = new ArrayList();
	    		list.addAll(thisList);
	    		return list;
	    	}
	    	else {
				List orgList = orgdao.queryOrgs(thisList,org);
				return orgList;
	    	}
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
    
	public void addDir(Dir dir)throws BusinessException {
		try {
			dirdao.saveOrUpdate(dir);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	public List getDir(Dir dir)throws BusinessException {
		try {
			return dirdao.queryDirs(dir);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	public List getRole(Role role)throws BusinessException {
		return roledao.queryRoles(role);
	}

	
	public void addOperator(Operator op)throws BusinessException, DAOException {
		operdao.saveOrUpdate(op);
	}


	public void addOrg(Org org)throws BusinessException {
		try {
			orgdao.saveOrUpdate(org);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}


	public void addPermission(Permission permission)throws BusinessException {
		perdao.saveOrUpdate(permission);

	}

	public void addPermissions(Permission permission, int[] orgid)throws BusinessException {
		Set permissions = new HashSet();
		permissions.add(permission);
		for(int i=0 ; i<orgid.length ; i++) {
			Org org;
			try {
				org = orgdao.load(new Integer(orgid[i]));
				org.setPermission(permissions);
				orgdao.saveOrUpdate(org);
			} catch (DAOException e) {
				if(logger.isWarnEnabled()){
					logger.warn("", e);
				}
				throw new BusinessException();
			}
		}
	}


	public void addRole(Role role)throws BusinessException{
		roledao.saveOrUpdate(role);

	}


	public void addTeacher(Teacher teacher, int roleid, int orgid)throws BusinessException {
		try {
			teacher.setRole(roledao.load(new Integer(roleid)));
			teacher.setOrg(orgdao.load(new Integer(orgid)));
			teadao.save(teacher);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public void changePasword(int teacherid, String password)throws BusinessException {
		try {
			Teacher teacher=teadao.load(new Integer(teacherid));
			teacher.setPassword(password);
			teadao.saveOrUpdate(teacher);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
		throw new BusinessException();
	}
	}

	public void delDir(int dirid)throws BusinessException {
		try {
			dirdao.delete(new Integer(dirid));
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}

	}

	public void delOperator(Integer opid)throws BusinessException, DAOException {
		operdao.delete(opid);

	}

	public void delOrg(int orgid)throws BusinessException {
		try {
			orgdao.delete(new Integer(orgid));
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}

	}

	public void delPermission(Serializable id) throws BusinessException{
		perdao.delete(id);

	}

	public void delRole(int roleid)throws BusinessException {
		roledao.delete(new Integer(roleid));

	}

	public void delTeacher(int teacherid)throws BusinessException, DAOException {
		teadao.delete( new Integer(teacherid));

	}

	// get all operators in one Dir
	public List getOperatorsInDir(int id) throws BusinessException{
		try {
			return SwitchTools.setToList(dirdao.load(new Integer(id)).getOperators());
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public List getChildOrg(int teacherid)throws BusinessException {
		try {
			return OrgCache.getInstance().getAllChildOrg(teadao.load(new Integer(teacherid)).getOrg().getId().intValue());
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	public List getAllChildOrgs(int orgid)throws BusinessException{
		try{
			Set childOrgs = new HashSet();
			childOrgs.add(orgdao.load(new Integer(orgid)));
			OrgCache oc = OrgCache.getInstance();
			childOrgs.addAll(oc.getAllChildOrg(orgid));
			return SwitchTools.setToList(childOrgs);
		}catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	public List getDirectChildOrg(int orgid)throws BusinessException {
		List directChildOrgs = new ArrayList();
		return OrgCache.getInstance().getChildOrg(orgid);
	}
	
	public List getOrgAndDirectChildOrg(int orgid)throws BusinessException {
		List orgs = new ArrayList();
		try {
			
			Org org = orgdao.load(new Integer(orgid));
			orgs.add(org);
			orgs.addAll(getDirectChildOrg(orgid));
			return orgs;
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	public List getDir(int id)throws BusinessException {
		List opList = getOperators(id);
		List dirSet = new ArrayList();
		try{
			List dirs = dirdao.getAllBySort();
			for(int i=0;i<dirs.size();i++){
				Dir dir = (Dir)dirs.get(i);
				Iterator it = opList.iterator();
				while(it.hasNext()){
					Operator op = (Operator)it.next();
					if(dir.getId().intValue()==op.getDir().getId().intValue()){
						dirSet.add(dir);
						break;
					}
				}
			}
		}catch(Exception ex){
			if(logger.isWarnEnabled()){
				logger.warn("", ex);
			}
			throw new BusinessException();
		}
		return dirSet;
	}
	
	public Dir getDir(int id,boolean def)throws BusinessException {
		try {
			return dirdao.load(new Integer(id));
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public List getDirs() throws BusinessException{
		try {
			return dirdao.getAll();
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public List getDirsByOrg(int orgid, int roleid)throws BusinessException {
		
		PermissionCache pCache = PermissionCache.getInstance();
		Set dirSet = new HashSet();
		Iterator it;
		it = perdao.getPermissionByOrgId(orgid).iterator();
		while(it.hasNext()) {
			Permission permission = (Permission)it.next();
			if(permission.getRole().getId().intValue() == roleid) {
				dirSet.add(permission.getOperator().getDir());
			}
		}
		return SwitchTools.setToList(dirSet);
	}

	public List getOperators() throws BusinessException, DAOException{
		return operdao.getAll();
	}
	
	public List getOperatorsBySort()throws BusinessException, DAOException{
		List opers = null;
		opers=operdao.getAllBySort();
		return opers;
	}
	public List getDirsBySort()throws BusinessException{
		List dirs = null;
		try{
			dirs=dirdao.getAllBySort();
		}catch(DAOException ex){
			if(logger.isWarnEnabled()){
				logger.warn("", ex);
			}
			throw new BusinessException();
		}
		return dirs;
	}
	public List getOperators(Operator op) throws BusinessException, DAOException{
		return operdao.queryOperators(op);
	}

	public List getOperators(int id) throws BusinessException {
		try {
			List opList = new ArrayList();
			Teacher teacher = teadao.load(new Integer(id));
			Role role = teacher.getRole();
			OrgCache oc = OrgCache.getInstance();
			
			/**
			 * 替代下面注释处
			 */
			List tpOrgList = oc.getAllParentsChildrenOrg(teacher.getOrg().getId().intValue());
			tpOrgList.add(teacher.getOrg());
			// 获取所有权限
			List permissionList = new ArrayList();
			for(int i = 0; i < tpOrgList.size(); i++)
			{
				Org o = (Org)tpOrgList.get(i);
				List tempList = perdao.getPermissionByOrgId(o.getId());
				if(tempList != null && !tempList.isEmpty())
				{
					permissionList.addAll(tempList);
				}
			}
			
			// 再用 该教师所属的角色 和 权限的时间段 进行权限过滤
			Set rolePeSet = new HashSet();
			Iterator it = permissionList.iterator();
			while(it.hasNext())
			{
				long currentTime = System.currentTimeMillis();				
				Permission pe = (Permission)it.next();
				long startTime = pe.getStart().getTime();
				long endTime = pe.getEndDate().getTime();
				if(pe.getRole().getId().intValue() == role.getId().intValue()
					&& (currentTime > startTime) && (currentTime < endTime))
					rolePeSet.add(pe);
			}
			List perList = SwitchTools.setToList(rolePeSet);
			List operators = operdao.getAllBySort();
			for(int i=0;i<operators.size();i++){
				Operator op = (Operator)operators.get(i);
				Iterator it2 = perList.iterator();
				while(it2.hasNext()){
					Permission per = (Permission)it2.next();
					if(per.getOperator().getId().intValue()==op.getId().intValue()){
						opList.add(op);
					}
				}
			}
			/*for(int i = 0 ; i < perList.size(); i++)
			{
				opList.add(((Permission)perList.get(i)).getOperator());
			}*/
			return opList;
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("", e);
			}
			e.printStackTrace();
			throw new BusinessException();
		}
	}

	public List getOperatorsByOrg(int orgid) throws BusinessException{
		List operators = new ArrayList();
		Iterator it;
		it = perdao.getPermissionByOrgId(orgid).iterator();
		while(it.hasNext()) {
			Permission permission = (Permission)it.next();
			operators.add(permission.getOperator());
		}
		return operators;
	}

	public List getOperatorsByOrg(int orgid, int roleid)throws BusinessException {
		List operators = new ArrayList();
		Iterator it = perdao.getPermissionByOrgId(orgid).iterator();
		while(it.hasNext()) {
			Permission permission = (Permission)it.next();
			Role role = permission.getRole();
			if(role.getId().intValue() == roleid) {
				operators.add(permission.getOperator());
			}
		}
		return operators;
	}

	public List getParentOrg(int teacherid)throws BusinessException {
		List parentOrgs = new ArrayList();
		Org org;
		try {
			org = teadao.load(new Integer(teacherid)).getOrg();
			while(org.getParent() != null) {
				org = org.getParent();
				parentOrgs.add(org);
			}
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
		return parentOrgs;
	}

	public List getPermissionByOperator(int operatorId, String term) throws BusinessException, DAOException{
		Date currentDate = new Date();
		List permissions = new ArrayList();
		Iterator it;
		Operator op = new Operator();
		op.setId(new Integer(operatorId));
		op.setType("type1");
		op.setName("login");
		it = PermissionCache.getInstance().getPermissionsByOperator(operdao.load(op)).iterator();

		while(it.hasNext()) {
			Permission permission = (Permission)it.next();
			if((permission.getInfo().equals(term.trim())) 
				&& (permission.getStart().before(currentDate))
				&& (permission.getEndDate().after(currentDate))) {
					permissions.add(permission);
			}
		}
		return permissions;
	}

	public List getPermissionByOrg(int orgid, String term) throws BusinessException{
		Date currentDate = new Date();
		List permissions = new ArrayList();
		Iterator it;
		it = perdao.getPermissionByOrgId(orgid).iterator();
		while(it.hasNext()) {
			Permission permission = (Permission)it.next();
			if((permission.getInfo().equals(term.trim())) 
					&& (permission.getStart().before(currentDate))
					&& (permission.getEndDate().after(currentDate))) {
				permissions.add(permission);
			}
		}
		return permissions;
	}
	
	public List getPermissionOrg(int orgid) throws BusinessException{
		try {
			List permissions=new ArrayList();
			long currentTime = System.currentTimeMillis();	
			OrgCache oc = OrgCache.getInstance();
			List parentList = oc.getParents(orgid);
			List childList = oc.getAllChildOrg(orgid);
			Org org=orgdao.load(new Integer(orgid));

			List permissionList = new ArrayList();
			if(org!=null){
				permissionList = perdao.getPermissionByOrgId(orgid);
			}
			
			for(int i = 0; i < childList.size(); i++)
			{
				Org o = (Org)childList.get(i);
				List tempList = perdao.getPermissionByOrgId(o.getId());
				if(tempList != null && !tempList.isEmpty())
				{
					permissionList.addAll(tempList);
				}
			}
			return permissionList;
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	public List getPermissionByRootOrg(int orgid, int roleid) throws BusinessException{
		Date currentDate = new Date();
		List permissions = new ArrayList();
		Iterator it = PermissionCache.getInstance().getPermissions().iterator();
		//Iterator it = perdao.getAll().iterator();
		while(it.hasNext()) {
			Permission permission = (Permission)it.next();
			if((permission.getOrg().getId().intValue() == orgid)
					&& (permission.getRole().getId().intValue() == roleid)
					&& (permission.getStart().before(currentDate))
					&& (permission.getEndDate().after(currentDate))){
				permissions.add(permission);
			}
		}
		return permissions;
	}

	public List getRoles()throws BusinessException {
		return roledao.getAll();
	}

	public List getTeachers(int orgid) throws BusinessException{
		try {
			Set<Teacher> teachers = new HashSet();
			OrgCache oc = OrgCache.getInstance();
			List orgs =oc.getAllChildOrg(orgid);
			teachers.addAll(teadao.getTeacher(new Integer(orgid)));
			Iterator it = orgs.iterator();
			while(it.hasNext()){
				Org org = (Org)it.next();
				teachers.addAll(teadao.getTeacher(org.getId()));
			}
			return SwitchTools.setToList(teachers);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public List getTeachers(int orgid, int roleid) throws BusinessException{
		List teachers = new ArrayList();
		Iterator it;
		try {
			it = orgdao.load(new Integer(orgid)).getTeacher().iterator();
			while(it.hasNext()) {
				Teacher teacher = (Teacher)it.next();
				if(teacher.getRole() != null) {
					if(teacher.getRole().getId().intValue() == roleid) {
						teachers.add(teacher);
					}
				}
			}
			return teachers;
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public void resetPassword(Teacher teacher)throws BusinessException {
		teacher.setPassword("111111");
		try {
			teadao.saveOrUpdate(teacher);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public void update(Dir dir)throws BusinessException {
		try {
			Dir di = dirdao.load(dir.getId());
			di.setName(dir.getName());
			dirdao.saveOrUpdate(di);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public void updateOperator(Operator op) throws BusinessException{
		try {		
			Operator oper = operdao.load(op.getId());
			Dir dir = dirdao.load(op.getDir().getId());
			oper.setName(op.getName());
			oper.setDir(dir);
			oper.setEnterUrl(op.getEnterUrl());
			oper.setClassname(op.getClassname());
			operdao.saveOrUpdate(oper);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}

	}
	public void updateOrg(Org org) throws BusinessException{
		try {
			Org dbOrg = orgdao.load(org.getId());
			if(org.getName() != null) {
				dbOrg.setName(org.getName());
			}
			orgdao.saveOrUpdate(dbOrg);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public void updatePermission(Permission permission)throws BusinessException {
		try {
			Permission p = perdao.load(permission.getId());
			p.setInfo(permission.getInfo());
			Role role = roledao.load(permission.getRole().getId());
			p.setRole(role);
			
			List operatorList =getOperators();
			int operatorId = permission.getOperator().getId().intValue();
			for(int i=0;i<operatorList.size();i++) {
				Operator op = (Operator)operatorList.get(i);
				if(op.getId().intValue() == operatorId) {
					p.setOperator(op);
					break;
				}
			}	
			Org org = getOrg(permission.getOrg().getId().intValue());
			p.setOrg(org);
			p.setStart(permission.getStart());
			p.setEndDate(permission.getEndDate());		
			perdao.saveOrUpdate(p);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public void updateRole(Role role) throws BusinessException{
		roledao.saveOrUpdate(role);
	}

	public void updateTeacher(int tid,String tname,int orgid,int roleid)throws BusinessException {
		try {
			Teacher teacher = teadao.load(new Integer(tid));
			Org org = orgdao.load(new Integer(orgid));//OrgCache.getInstance().getOrg(orgid);//
			Role role = roledao.load(new Integer(roleid));
			teacher.setName(tname);
			teacher.setOrg(org);
			teacher.setRole(role);
			teadao.saveOrUpdate(teacher);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}

	}
	
	public void updateTeacher(int tid, String password)throws BusinessException {
		try {
			Teacher teacher = teadao.load(new Integer(tid));
			teacher.setPassword(password);
			teadao.saveOrUpdate(teacher);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}

	}
	
	public TreeList getOrgTree(int orgid)throws BusinessException 
	{
		OrgCache oc = OrgCache.getInstance();
		return oc.getOrgTree(orgid);
	}
	
	public List getOptGroups()throws BusinessException, DAOException{
		List operatorList = getOperators();
		List optGroupList = new ArrayList();
		List tempList = new ArrayList();
		for(int i=0;i<operatorList.size();i++) {
			Operator op = (Operator)operatorList.get(i);
			tempList.add((String)op.getType());
		}	
		
		List typeList = MyList.removeDuplicateString(tempList);
		for(int i=0;i<typeList.size();i++) {
			String a = (String)typeList.get(i);
		}
		Iterator it = null;
		it = typeList.iterator();
		while(it.hasNext()) {
			String name = (String)it.next();
			OptGroup og = new OptGroup();
			og.setName(name);
			og.setOptions(new ArrayList());	
			optGroupList.add(og);
		}
		List optGroups = new ArrayList();
		for(int i=0;i<optGroupList.size();i++) {
			OptGroup og = (OptGroup)optGroupList.get(i);
			String name = og.getName();
			Iterator oit = operatorList.iterator();
			while(oit.hasNext()) {
				Operator op = (Operator)oit.next();
				if(name.equals(op.getType())) {
					og.getOptions().add(op);
				}
			}
			optGroups.add(og);
		}
		return optGroups;
	}
	
	public Operator getOperator(Serializable opid)throws BusinessException{
		try {
			return operdao.load((Operator)opid);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	public int generatePermissionId()throws BusinessException{
		List permissionList = PermissionCache.getInstance().getPermissions();
		//List permissionList = perdao.getAll();
		Iterator it = permissionList.iterator();
		int id = 0;
		while(it.hasNext()) {
			Permission p = (Permission)it.next();
			if(p.getId().intValue()>id) {
				id =p.getId().intValue();
			}
		}
		return (id + 1);
	}
	
	public int generateOperatorId()throws BusinessException{
		try {
			List operatorList = operdao.getAll();
			Iterator it = operatorList.iterator();
			int id = 0;
			while(it.hasNext()) {
				Operator op = (Operator)it.next();
				if(op.getId().intValue()>id) {
					id =op.getId().intValue();
				}
			}
			return (id + 1);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}
	
	public List searchPermission(Permission permission)throws BusinessException{
		return perdao.queryPermissions(permission);
	}
	
	public void upSortOperator(int ipid)throws BusinessException{
	try{
		Session session = sessionFactory.getCurrentSession();
		Criteria cri2 = null;
		Operator op = operdao.load(new Integer(ipid));
		int sortid = op.getSortid();
		cri2=session.createCriteria(Operator.class);
		cri2.add(Expression.lt("sortid", new Integer(sortid)));
		cri2.addOrder(Order.desc("sortid"));
		List opers = cri2.list();
		if(opers.size()>0){
			Operator oper = (Operator)opers.get(0);
			int newsortid = oper.getSortid();
			oper.setSortid(sortid);
			op.setSortid(newsortid);
			operdao.saveOrUpdate(oper);
			operdao.saveOrUpdate(op);
		}
		}catch(Exception ex){
			new BusinessException();
		}
	}
    public void downSortOperator(int opid)throws BusinessException{
    	try{
    		Session session = sessionFactory.getCurrentSession();
    		Criteria cri2 = null;
    		Operator op = operdao.load(new Integer(opid));
    		int sortid = op.getSortid();
    		cri2=session.createCriteria(Operator.class);
    		cri2.add(Expression.gt("sortid", new Integer(sortid)));
    		cri2.addOrder(Order.asc("sortid"));
    		List opers = cri2.list();
    		if(opers.size()>0){
    			Operator oper = (Operator)opers.get(0);
    			int newsortid = oper.getSortid();
    			oper.setSortid(sortid);
    			op.setSortid(newsortid);
    			operdao.saveOrUpdate(oper);
    			operdao.saveOrUpdate(op);
    		}
    		}catch(Exception ex){
    			new BusinessException();
    		}
	}
	

    public void upSortDir(int dirid)throws BusinessException{
    	try{
    		Session session = sessionFactory.getCurrentSession();
    		Criteria cri = null;
    		Dir  dir = dirdao.load(new Integer(dirid));
    		int sortid = dir.getSortid();
    		cri=session.createCriteria(Dir.class);
    		cri.add(Expression.lt("sortid", new Integer(sortid)));
    		cri.addOrder(Order.desc("sortid"));
    		List dirs = cri.list();
    		if(dirs.size()>0){
    			Dir di = (Dir)dirs.get(0);
    			int newsortid = di.getSortid();
    			di.setSortid(sortid);
    			dir.setSortid(newsortid);
    			dirdao.saveOrUpdate(di);
    			dirdao.saveOrUpdate(dir);
    		}
    		}catch(Exception ex){
    			new BusinessException();
    		}
    	}
        public void downSortDir(int dirid)throws BusinessException{
        	try{
        		Session session = sessionFactory.getCurrentSession();
        		Criteria cri = null;
        		Dir  dir = dirdao.load(new Integer(dirid));
        		int sortid = dir.getSortid();
        		cri=session.createCriteria(Dir.class);
        		cri.add(Expression.gt("sortid", new Integer(sortid)));
        		cri.addOrder(Order.asc("sortid"));
        		List dirs = cri.list();
        		if(dirs.size()>0){
        			Dir di = (Dir)dirs.get(0);
        			int newsortid = di.getSortid();
        			di.setSortid(sortid);
        			dir.setSortid(newsortid);
        			dirdao.saveOrUpdate(di);
        			dirdao.saveOrUpdate(dir);
        		}
        		}catch(Exception ex){
        			if(logger.isWarnEnabled()){
        				logger.warn("", ex);
        			}
        			throw new BusinessException();
        		}
    	}
      
	
	
	
	public List getClassTeacher(int orgid, int size, int page, Pagination pagination) throws BusinessException{
	// TODO Auto-generated method stub
	
		return getClassTeacher(orgid, -1, null, size, page, pagination);
	}

	public List getClassTeacher(int orgid, int tchid, String tchName, int size,
			int page, Pagination pagination) throws BusinessException {
		// TODO Auto-generated method stub
		// 先得到该组织下的所有班级,然后通过班级查询出学生
		
		try {
			OrgDAO oDao = new OrgDBDAO();
			OrgCache oc = OrgCache.getInstance();
			// 得到所有 child Org
			List classList = oc.getAllChildOrg(orgid);//getSClass(oDao.load(orgid));
			classList.add(oc.getOrg(orgid));
						
			TeacherDAO teaDao = new TeacherDBDAO();
			
			if(pagination != null)
			{
				pagination.setSize(size);
				pagination.setPage(page);
				int resultCount = teaDao.getTeachersCount(classList, tchid, tchName );
				pagination.setResultCount(resultCount);
				pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
			}
			
			// 得到班级所有学生
			List TeacherList = teaDao.getTeachers(classList, tchid, tchName, size, page);
			if(TeacherList != null)
				return TeacherList;
			else return new ArrayList();
			
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
			throw new BusinessException();
		}
	}

	public int getDirLastSize() throws BusinessException {
		int size=0;
		try{
			size=dirdao.getLastDir();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	   return size;
	}

	public int getOpLastSize() throws BusinessException {
		int size=0;
		try{
			size=operdao.getLastOp();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return size;
	}
	
	public Teacher loginByName(String tchName, String password) throws DAOException{
		Teacher tch = teadao.getTeacher(tchName);
		if(tch !=null && tch.getPassword().equals(Md5Util.getMD5Str(password)))
		{
			return tch;
		}
		return null;
	}
	
	@Override
	public void changeVerity(String name, String verity)throws BusinessException {
		// TODO Auto-generated method stub
		try {
			List teacherList = teadao.getAll();
			Iterator it = teacherList.iterator();
			int id = 0;
			while(it.hasNext()) {
				Teacher t = (Teacher)it.next();
				if(t.getId().intValue()>id) {
					t.setVerity(verity);
					teadao.saveOrUpdate(t);
				}
			}
			
		//	Teacher v=teadao.getTeacher(name);
		//	v.setVerity(verity);
		//	teadao.saveOrUpdate(v);
		} catch (DAOException e) {
			if(logger.isWarnEnabled()){
				logger.warn("", e);
			}
		throw new BusinessException();
	}
	
		
			
			
	}

	

	
	

	

	
		
		
		
		
}

	
	


	
	
	
