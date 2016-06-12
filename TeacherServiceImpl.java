package cn.cust.kyc.bo.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import cn.cust.kyc.bo.TeacherService;
import cn.cust.kyc.dao.DAOFactory;
import cn.cust.kyc.dao.DirDAO;
import cn.cust.kyc.dao.OperatorDAO;
import cn.cust.kyc.dao.PermissionDAO;
import cn.cust.kyc.dao.TeacherDAO;
import cn.cust.kyc.util.SwitchTools;
import cn.cust.kyc.vo.Dir;
import cn.cust.kyc.vo.Operator;
import cn.cust.kyc.vo.Permission;
import cn.cust.kyc.vo.Role;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.DAOException;

public class TeacherServiceImpl implements TeacherService {

	private TeacherDAO teadao;
	private OperatorDAO operdao;
	private DirDAO dirdao;

	public TeacherServiceImpl() {
		teadao = DAOFactory.getTeacherDAO();
		operdao = DAOFactory.getOperDAO();
		dirdao = DAOFactory.getDirDAO();
	}

	public void changePasword(int teacherid, String password) throws DAOException {
		Teacher tch = teadao.load(new Integer(teacherid));
		tch.setPassword(password);
		teadao.saveOrUpdate(tch);
	}

	// id 表示的是 teacher 的 id
	public List getOperator(int id) throws DAOException {
		// TODO Auto-generated method stub
		Teacher tch = teadao.load(new Integer(id));
		// 获取所有权限
		PermissionDAO pd = DAOFactory.getPerDAO();
		List permissionList = pd.getPermissionByRoleId(tch.getRole().getId()
				.intValue());
		// 再用 该老师所属的角色 和 权限的时间段 进行权限过滤
		Role role = tch.getRole();
		Set rolePeSet = new HashSet();
		Iterator it = permissionList.iterator();
		while (it.hasNext()) {
			long currentTime = System.currentTimeMillis();
			Permission pe = (Permission) it.next();
			long startTime = pe.getStart().getTime();
			long endTime = pe.getEndDate().getTime();
			if (pe.getRole().getId().intValue() == role.getId().intValue()
					&& (currentTime > startTime) && (currentTime < endTime))
				rolePeSet.add(pe);
		}

		List perList = SwitchTools.setToList(rolePeSet);
		List opList = new ArrayList();
		List operators = operdao.getAllBySort();
		for (int i = 0; i < operators.size(); i++) {
			Operator op = (Operator) operators.get(i);
			Iterator it2 = perList.iterator();
			while (it2.hasNext()) {
				Permission per = (Permission) it2.next();
				if (per.getOperator().getId().intValue() == op.getId()
						.intValue()) {
					opList.add(op);
				}
			}
		}
		return opList;
	}

	// 得到与老师相关的权限
	public List getPermission(int teacherid) throws DAOException {
		Teacher tch = teadao.load(new Integer(teacherid));
		// 获取所有权限
		PermissionDAO pd = DAOFactory.getPerDAO();
		List permissionList = pd.getPermissionByRoleId(tch.getRole().getId()
				.intValue());
		// 再用 该老师所属的角色 和 权限的时间段 进行权限过滤
		Role role = tch.getRole();
		Set rolePeSet = new HashSet();
		Iterator it = permissionList.iterator();
		while (it.hasNext()) {
			long currentTime = System.currentTimeMillis();
			Permission pe = (Permission) it.next();
			long startTime = pe.getStart().getTime();
			long endTime = pe.getEndDate().getTime();
			if (pe.getRole().getId().intValue() == role.getId().intValue()
					&& (currentTime > startTime) && (currentTime < endTime))
				rolePeSet.add(pe);
		}

		return SwitchTools.setToList(rolePeSet);
	}

	// 用 set 是为了去掉重复目录
	public List getDir(int id) throws DAOException {
		// TODO Auto-generated method stub
		/**
		 * 先得到指定老师的所有操作,然后通过操作关联得到所有目录
		 */
		List opList = getOperator(id);
		List dirSet = new ArrayList();
		List dirs = dirdao.getAllBySort();
		for (int i = 0; i < dirs.size(); i++) {
			Dir dir = (Dir) dirs.get(i);
			Iterator it = opList.iterator();
			while (it.hasNext()) {
				Operator op = (Operator) it.next();
				if (dir.getId().intValue() == op.getDir().getId().intValue()) {
					dirSet.add(dir);
					break;
				}
			}
		}
		return dirSet;
	}

	public Teacher login(int id, String password) throws DAOException {
		// TODO Auto-generated method stub
		Teacher tch = teadao.load(new Integer(id));
		if (tch.getPassword().equals(password)) {
			return tch;
		}
		return null;
	}

	public Teacher loginByName(String tchName, String password) throws DAOException {
		// TODO Auto-generated method stub
		Teacher tch = teadao.getTeacher(tchName);
		if (tch != null && tch.getPassword().equals(password)) {
			return tch;
		}
		return null;
	}


	public Teacher getTeacher(int tid) throws DAOException {
		Teacher teacher = teadao.load(new Integer(tid));
		return teacher;
	}

}
