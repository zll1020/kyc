package cn.cust.kyc.action;


import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.bo.impl.ManagerServiceImpl;
import cn.cust.kyc.util.TreeList;
import cn.cust.kyc.vo.Operator;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Permission;
import cn.cust.kyc.vo.Role;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManagePermission extends ActionSupport{
	
	ActionContext context = ActionContext.getContext(); 
	Map<String, Object> params = context.getParameters(); 
	HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
	
	public String execute() throws Exception{
		String method = request.getParameter("method");
		Method m = this.getClass().getMethod(method);
		return (String) m.invoke(this);
	}
	
	public String addPermission() throws Exception {
		// get request parameters
		String orgId = request.getParameter("orgId");
		String operatorId = request.getParameter("operatorId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String info = request.getParameter("info");
		String roleId = request.getParameter("roleId");
		
		Permission permission = new Permission();
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		permission.setInfo(java.net.URLDecoder.decode(info,"UTF-8"));
		DateFormat df = new SimpleDateFormat("yy-MM-dd");
		try {
			Date sd = df.parse(java.net.URLDecoder.decode(startTime,"UTF-8"));
			Date ed = df.parse(java.net.URLDecoder.decode(endTime,"UTF-8"));
			permission.setStart(sd);
			permission.setEndDate(ed);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Org selectedOrg = managerService.getOrg(Integer.parseInt(orgId));
		permission.setOrg(selectedOrg);
		List operatorList = managerService.getOperators();
		for(int i=0;i<operatorList.size();i++) {
			Operator op = (Operator)operatorList.get(i);
			if(op.getId().intValue() == Integer.parseInt(operatorId)) {
				permission.setOperator(op);
				break;
			}
		}
		List aRoleList = managerService.getRoles();
		for(int i=0;i<aRoleList.size();i++) {
			Role role = (Role)aRoleList.get(i);
			if(role.getId().intValue() == Integer.parseInt(roleId)) {
				permission.setRole(role);
				break;
			}
		}
		managerService.addPermission(permission);
		request.setAttribute("selectedOrg", selectedOrg);
		request.getSession().setAttribute("selectedOrgId", selectedOrg.getId());
		
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		int roleid = teacher.getRole().getId().intValue();
		TreeList treeList = managerService.getOrgTree(teacher.getOrg().getId().intValue());
        List permissionList = managerService.getPermissionOrg(teacher.getOrg().getId().intValue());
        List roleList = managerService.getRoles();
        request.setAttribute("permissionList", permissionList);
        request.setAttribute("direchChildOrg", treeList);
        request.setAttribute("roleList",roleList);
        List optGroupList = managerService.getOptGroups();
        request.setAttribute("optGroupList", optGroupList);
		
		return SUCCESS;
	}
	
	public String delPermission() throws Exception {
		int permissionId = Integer.parseInt(request.getParameter("permissionId"));
		String info = java.net.URLDecoder.decode(request.getParameter("info").trim(),"UTF-8");
		
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		Permission permission = new Permission();
		permission.setId(new Integer(permissionId));
		permission.setInfo(info);
		managerService.delPermission(permission);
		
		String orgId = request.getParameter("orgId");
		Integer  selectedOrgId = new Integer(Integer.parseInt(orgId));
		Org selectedOrg = managerService.getOrg(selectedOrgId.intValue());
		request.setAttribute("selectedOrg", selectedOrg);
		request.getSession().setAttribute("selectedOrgId", selectedOrgId);
		
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		int roleid = teacher.getRole().getId().intValue();
		TreeList treeList = managerService.getOrgTree(teacher.getOrg().getId().intValue());
        List permissionList = managerService.getPermissionOrg(teacher.getOrg().getId().intValue());
        List roleList = managerService.getRoles();
        request.setAttribute("permissionList", permissionList);
        request.setAttribute("direchChildOrg", treeList);
        request.setAttribute("roleList",roleList);
        List optGroupList = managerService.getOptGroups();
        request.setAttribute("optGroupList", optGroupList);
		return SUCCESS;	
	}
	
	public String updatePermission() throws Exception {
		// get request parameters
		String permissionId = request.getParameter("permissionId");
		String orgId = request.getParameter("orgId");
		String operatorId = request.getParameter("operatorId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String info = request.getParameter("info");
		String roleId = request.getParameter("roleId");
		
		Permission permission = new Permission();
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);

		permission.setId(new Integer(Integer.parseInt(permissionId)));
		permission.setInfo(java.net.URLDecoder.decode(info,"UTF-8"));
		DateFormat df = new SimpleDateFormat("yy-MM-dd");
		try {
			Date sd = df.parse(java.net.URLDecoder.decode(startTime,"UTF-8"));
			Date ed = df.parse(java.net.URLDecoder.decode(endTime,"UTF-8"));
			permission.setStart(sd);
			permission.setEndDate(ed);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Org selectedOrg = managerService.getOrg(Integer.parseInt(orgId));
		Org org = new Org();
		org.setId(new Integer(Integer.parseInt(orgId)));
		permission.setOrg(org);
		Role role = new Role();
		role.setId(new Integer(roleId));
		permission.setRole(role);
		Operator operator = new Operator();
		operator.setId(new Integer(operatorId));
		permission.setOperator(operator);
		
		managerService.updatePermission(permission);
        return mPermissionEntry();
	}
	
	public String searchPermission() throws Exception {
		// get request parameters
		String orgId = request.getParameter("orgId");
		String operatorId = request.getParameter("operatorId");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String info = request.getParameter("info");
		String roleId = request.getParameter("roleId");
		
		Permission permission = new Permission();
		ManagerService managerService = (ManagerService) BusinessFactory.getBusiness(ManagerServiceImpl.class);
		Org selectedOrg = managerService.getOrg(Integer.parseInt(orgId));
		request.setAttribute("selectedOrg", selectedOrg);
		request.getSession().setAttribute("selectedOrgId", new Integer(selectedOrg.getId().intValue()));
		permission.setOrg(selectedOrg);
		if(!java.net.URLDecoder.decode(info,"UTF-8").equals("0")) {
			permission.setInfo(java.net.URLDecoder.decode(info,"UTF-8"));
		}
		DateFormat df = new SimpleDateFormat("yy-MM-dd");
		try {
			if(!startTime.equals("")) {
				Date sd = df.parse(startTime);
				permission.setStart(sd);
			}
			if(!endTime.equals("")) {
				Date ed = df.parse(endTime);
				permission.setEndDate(ed);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(!roleId.equals("0")) {
			List aRoleList = managerService.getRoles();
			for(int i=0;i<aRoleList.size();i++) {
				Role role = (Role)aRoleList.get(i);
				if(role.getId().intValue() == Integer.parseInt(roleId)) {
					permission.setRole(role);
					break;
				}
			}
		}
		if(!operatorId.equals("0")) {
			List operatorList = managerService.getOperators();
			for(int i=0;i<operatorList.size();i++) {
				Operator op = (Operator)operatorList.get(i);
				if(op.getId().intValue() == Integer.parseInt(operatorId)) {
					permission.setOperator(op);
					break;
				}
			}
		}
		List permissionList = managerService.searchPermission(permission);
		request.setAttribute("permissionList", permissionList);
		request.getSession().setAttribute("selectedOrgId", new Integer(selectedOrg.getId().intValue()));
		
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		int roleid = teacher.getRole().getId().intValue();
		TreeList treeList = managerService.getOrgTree(teacher.getOrg().getId().intValue());
        List roleList = managerService.getRoles();
        request.setAttribute("direchChildOrg", treeList);
        request.setAttribute("roleList",roleList);
        List optGroupList = managerService.getOptGroups();
        request.setAttribute("optGroupList", optGroupList);
        return SUCCESS;
	}
	
	
	public String getPermissionByOrgId() throws Exception {
		if(request.getSession().getAttribute("selectedOrgId") != null) {
			request.getSession().removeAttribute("selectedOrgId");
		}
		
		String selectedOrgId = request.getParameter("selectedOrgId");
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		int orgId = Integer.parseInt(selectedOrgId);
		Org selectedOrg = managerService.getOrg(orgId);
		request.getSession().setAttribute("selectedOrgId", new Integer(Integer.parseInt(selectedOrgId)));
		request.setAttribute("selectedOrg", selectedOrg);
		try{
			 Permission permission = new Permission();
			 permission.setOrg(selectedOrg);
			 List permissionList = managerService.getPermissionOrg(orgId);
			 request.setAttribute("permissionList", permissionList);
			 
			 Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
			 TreeList treeList = managerService.getOrgTree(teacher.getOrg().getId().intValue());
			 request.setAttribute("direchChildOrg", treeList);
	         
	         List roleList = managerService.getRoles();
	         request.setAttribute("roleList",roleList);
	         
	         List optGroupList = managerService.getOptGroups();
	         request.setAttribute("optGroupList", optGroupList);
	         
	         return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info", "获取权限列表出错！");
			return ERROR;
		}
	}
	
	public String mPermissionEntry() throws Exception {
		if(request.getSession().getAttribute("selectedOrgId") != null) {
			request.getSession().removeAttribute("selectedOrgId");
		}
		int orgId = -1;
		String selectedOrgIdS = request.getParameter("selectedOrgId");
		ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		Org selectedOrg = null;
		if(selectedOrgIdS != null && selectedOrgIdS.length() > 0) {
			orgId = Integer.parseInt(selectedOrgIdS);
			selectedOrg = ms.getOrg(orgId);
			request.getSession().setAttribute("selectedOrgId", new Integer(orgId));
		}
		Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		int roleid = teacher.getRole().getId().intValue();
		if(orgId < 0) {
			orgId = teacher.getOrg().getId().intValue();
			selectedOrg = teacher.getOrg();
		}
		request.setAttribute("selectedOrg", selectedOrg);
		try{
			 TreeList treeList = ms.getOrgTree(teacher.getOrg().getId().intValue());
	         List permissionList = ms.getPermissionOrg(orgId);
	         List roleList = ms.getRoles();
	         request.setAttribute("permissionList", permissionList);
	         request.setAttribute("direchChildOrg", treeList);
	         request.setAttribute("roleList",roleList);
	         List optGroupList = ms.getOptGroups();
	         request.setAttribute("optGroupList", optGroupList);
	         return SUCCESS;
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("info", "获取权限列表出错！");
			return ERROR;
		}
	}
}
