package cn.cust.kyc.bo;

import java.io.Serializable;
import java.util.List;

import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.TreeList;
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

public interface ManagerService {

	/**
	 * 获取目录
	 * 
	 * @param id
	 * @return 获取目录
	 */
	public Dir getDir(int id, boolean def) throws BusinessException;

	/**
	 * 获取目录
	 * 
	 * @param id
	 * @return 获取目录
	 */
	public List getDir(int id) throws BusinessException;

	/**
	 * 获取可以操做的菜单项
	 * 
	 * @param id
	 *            操作实体id
	 * @return 获取可以操做的菜单项
	 */
	public List getOperators(int id) throws BusinessException;

	// ------------------
	// 管理组织机构
	// ------------------
	/**
	 * 获取组织机构
	 * 
	 * @param orgid
	 *            组织机构id
	 * @return 获取组织机构
	 */
	public Org getOrg(int orgid) throws BusinessException;

	public List getOrg() throws BusinessException;

	/**
	 * 获取组织机构
	 * 
	 * @param org
	 *            组织机构
	 * @return 获取组织机构
	 */
	public List getOrg(int orgid, Org org) throws BusinessException;

	/**
	 * 获取所属的组织机构祖先
	 * 
	 * @param teacherid
	 *            教师实体id
	 * @return 获取所属的组织机构祖先
	 */
	public List getParentOrg(int teacherid) throws BusinessException;

	/**
	 * 获取下级组织机构
	 * 
	 * @param teacherid
	 *            教师id
	 * @return 获取下级组织机构
	 */
	public List getChildOrg(int teacherid) throws BusinessException;

	/**
	 * 获取直接下级组织机构
	 * 
	 * @param orgid
	 *            组织机构id
	 * @return 获取直接下级组织机构
	 */
	public List getDirectChildOrg(int orgid) throws BusinessException;

	/**
	 * 获取该Org及其直接下级组织机构
	 * 
	 * @param orgid
	 *            组织机构id
	 * @return 获取该Org及其直接下级组织机构列表
	 */
	public List getOrgAndDirectChildOrg(int orgid) throws BusinessException;

	/**
	 * 添加一个组织机构
	 * 
	 * @param org
	 *            组织机构对象
	 */
	public void addOrg(Org org) throws BusinessException;

	/**
	 * 删除一个组织机构
	 * 
	 * @param orgid
	 *            组织机构id
	 */
	public void delOrg(int orgid) throws BusinessException;

	/**
	 * 修改一个组织机构
	 * 
	 * @param org
	 *            组织机构对象
	 */
	public void updateOrg(Org org) throws BusinessException;

	// ------------------
	// 管理程序目录
	// ------------------
	/**
	 * 添加一个目录
	 * 
	 * @param org
	 *            目录对象
	 */
	public void addDir(Dir dir) throws BusinessException;

	/**
	 * 删除目录
	 * 
	 * @param dirid
	 *            目录id
	 */
	public void delDir(int dirid) throws BusinessException;

	/**
	 * 更新目录
	 * 
	 * @param dir
	 *            目录对象
	 */
	public void update(Dir dir) throws BusinessException;

	/**
	 * 获取系统目录列表
	 * 
	 * @return 获取系统目录列表
	 */
	public List getDirs() throws BusinessException;

	public List getDir(Dir dir) throws BusinessException;

	/**
	 * 根据组织机构和角色获取当前可用的目录
	 * 
	 * @param orgid
	 *            组织机构id
	 * @param roleid
	 *            角色id
	 * @return 根据组织机构和角色获取当前可用的目录
	 */
	public List getDirsByOrg(int orgid, int roleid) throws BusinessException;

	// 测试后再决定是否使用 public Dir loadDir(int dirid);

	// ------------------
	// 管理Operator
	// ------------------

	/**
	 * 添加一个操做
	 * 
	 * @param op
	 *            操作对象
	 * @throws DAOException 
	 */
	public void addOperator(Operator op) throws BusinessException, DAOException;

	/**
	 * 删除操作
	 * 
	 * @param opid
	 *            参照id
	 * @throws DAOException 
	 */
	public void delOperator(Integer opid) throws BusinessException, DAOException;

	public Operator getOperator(Serializable opid) throws BusinessException;

	/**
	 * 查询操作
	 * 
	 * @param op
	 * @throws DAOException 
	 */
	public List getOperators(Operator op) throws BusinessException, DAOException;

	/**
	 * 获取操作
	 * @throws DAOException 
	 * 
	 * @return获取操作
	 */
	public List getOperators() throws BusinessException, DAOException;

	/**
	 * 获取某个目录下的所有操作
	 * 
	 * @param dirid
	 *            目录id
	 * @return 获取某个目录下的所有操作
	 */
	public List getOperatorsInDir(int dirid) throws BusinessException;

	/**
	 * 更新操作
	 * 
	 * @param op
	 *            操作对象
	 */
	public void updateOperator(Operator op) throws BusinessException;

	/**
	 * 通过组织机构获取操作
	 * 
	 * @param orgid
	 *            组织机构id
	 * @return 通过组织机构获取操作
	 */
	public List getOperatorsByOrg(int orgid) throws BusinessException;

	/**
	 * 获取某个组织结构下的某个角色具有哪些操作
	 * 
	 * @param orgid
	 *            组织机构id
	 * @param roleid
	 *            角色id
	 * @return 获取某个组织结构下的某个角色具有哪些操作
	 */
	public List getOperatorsByOrg(int orgid, int roleid)
			throws BusinessException;

	// ------------------
	// 管理Permission
	// ------------------
	/**
	 *增加权限
	 * 
	 * @param permission
	 *            组织机构id
	 */
	public void addPermission(Permission permission) throws BusinessException;

	/**
	 * 添加多个permission
	 * 
	 * @param permission
	 *            权限对象
	 * @param orgid
	 *            组织机构id
	 */
	public void addPermissions(Permission permission, int[] orgid)
			throws BusinessException;

	/**
	 * 删除权限
	 * 
	 * @param id
	 */
	public void delPermission(Serializable id) throws BusinessException;

	/**
	 * 更新权限对象
	 * 
	 * @param permission
	 *            权限对象
	 */
	public void updatePermission(Permission permission)
			throws BusinessException;

	/**
	 * 获取某个角色的所有权限
	 * 
	 * @param orgid
	 *            组织机构id
	 * @param roleid
	 *            角色id
	 * @return 获取某个角色的所有权限
	 */
	public List getPermissionByRootOrg(int orgid, int roleid)
			throws BusinessException;

	/**
	 * 通过组织机构获取权限
	 * 
	 * @param orgid
	 *            组织机构id
	 * @param term
	 *            学期
	 * @return 通过组织机构获取权限
	 */
	public List getPermissionByOrg(int orgid, String term)
			throws BusinessException;

	/**
	 * 根据角色和组织机构、学期获取组织机构权限
	 * 
	 * @param orgid
	 *            组织机构id
	 * @return 根据角色和组织机构、学期获取组织机构权限
	 * @throws BusinessException
	 */
	public List getPermissionOrg(int orgid)
			throws BusinessException;

	/**
	 * 通过操作获取权限
	 * 
	 * @param operatorId
	 *            操作id
	 * @param term
	 *            学期
	 * @return 通过操作获取权限
	 * @throws DAOException 
	 */
	public List getPermissionByOperator(int operatorId, String term)
			throws BusinessException, DAOException;

	// ------------------
	// 管理role
	// ------------------
	/**
	 * 增加角色
	 * 
	 * @param role
	 *            角色对象
	 */
	public void addRole(Role role) throws BusinessException;

	/**
	 * 删除角色
	 * 
	 * @param roleid
	 *            角色id
	 */
	public void delRole(int roleid) throws BusinessException;

	/**
	 * 获取角色列表
	 * 
	 * @return 获取角色列表
	 */
	public List getRoles() throws BusinessException;

	public List getRole(Role role) throws BusinessException;

	/**
	 * 更新角色
	 * 
	 * @param role
	 *            角色对象
	 */
	public void updateRole(Role role) throws BusinessException;

	// ------------------
	// 管理teacher
	// ------------------
	/**
	 * 增加角色
	 * 
	 * @param teacher
	 *            教师对象
	 * @param roleid
	 *            角色id
	 * @param orgid
	 *            组织机构id
	 */
	public void addTeacher(Teacher teacher, int roleid, int orgid)
			throws BusinessException;

	/**
	 * 更新教师对象
	 * 
	 * @param teacher
	 *            教师对象
	 */
	public void updateTeacher(int tid, String tname, int orgid, int roleid)
			throws BusinessException;
	
	/**
	 * 更新教师密码
	 * @param tid
	 * @param password
	 * @throws BusinessException
	 */
	public void updateTeacher(int tid, String password)throws BusinessException;

	/**
	 * 删除教师
	 * 
	 * @param teacherid
	 *            教师id
	 * @throws DAOException 
	 */
	public void delTeacher(int teacherid) throws BusinessException, DAOException;

	/**
	 * 根据组织机构获取教师列表
	 * 
	 * @param orgid
	 *            组织机构id
	 * @return 根据组织机构获取教师列表
	 */
	public List getTeachers(int orgid) throws BusinessException;

	/**
	 * 根据组织机构和角色获取教师列表
	 * 
	 * @param orgid
	 *            组织机构id
	 * @param roleid
	 *            角色id
	 * @return 根据组织机构和角色获取教师列表
	 */
	public List getTeachers(int orgid, int roleid) throws BusinessException;

	/**
	 * 重置教师密码为11111
	 * 
	 * @param teacher
	 *            教师对象
	 */
	public void resetPassword(Teacher teacher) throws BusinessException;

	/**
	 * 修改密码
	 * 
	 * @param teacherid
	 *            教师id
	 * @param password
	 *            密码
	 */
	public void changePasword(int teacherid, String password)
			throws BusinessException;

	/**
	 * 管理员层获取组织机构树
	 * 
	 * @param orgid
	 *            管理员所在组织机构id
	 * @return 返回管理员层获取组织机构树
	 * @throws BusinessException
	 */
	public TreeList getOrgTree(int orgid) throws BusinessException;

	/**
	 * 获取包括自己和所有孩子组织机构
	 * 
	 * @param orgid
	 * @return
	 * @throws BusinessException
	 */
	public List getAllChildOrgs(int orgid) throws BusinessException;

	public List getOptGroups() throws BusinessException, DAOException;

	public int generatePermissionId() throws BusinessException;

	public List searchPermission(Permission permission)
			throws BusinessException;

	/**
	 * 对操作进行向上排序
	 * 
	 * @throws BusinessException
	 */
	public void upSortOperator(int opId) throws BusinessException;

	/**
	 * 对操作进行降序处理
	 * 
	 * @throws BusinessException
	 */
	public void downSortOperator(int opId) throws BusinessException;

	/**
	 * 
	 * @return 获取排序后的所有操作
	 * @throws BusinessException
	 * @throws DAOException 
	 */
	public List getOperatorsBySort() throws BusinessException, DAOException;

	public void upSortDir(int dirid) throws BusinessException;

	public void downSortDir(int dirid) throws BusinessException;

	public List getDirsBySort() throws BusinessException;

	public int generateOperatorId() throws BusinessException;

	/**
	 * 获取某个组织机构下的老师
	 */
	public List getClassTeacher(int orgid, int size, int page,
			Pagination pagination) throws BusinessException;
	
	/**
	 * 获取某个组织机构下的老师
	 */
	public List getClassTeacher(int orgid,int tchid, String tchName, int size, int page,
			Pagination pagination) throws BusinessException;

	/*
	 * 获取操作里的最大id值
	 */
	public int getOpLastSize()throws BusinessException;
	
	/*
	 * 获取目录里的最大id值
	 */
	public int getDirLastSize()throws BusinessException;
	/**
	 * 登录
	 * @param id
	 * @param password
	 * @return
	 * @throws DAOException 
	 */
	public Teacher loginByName(String id, String password) throws DAOException;

	

	public void changeVerity(String id, String verity)throws BusinessException;

	
	

	

	
	
}
