package cn.cust.kyc.action;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ManagerServiceImpl;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.vo.Role;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.business.BusinessFactory;



public class ManageRole extends ActionSupport{
	
	private Role id;
	
	private int status;
	
	ActionContext context = ActionContext.getContext(); 
	Map params = context.getParameters(); 
	HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
	
	public String execute() throws Exception{
		String method = request.getParameter("method");
		Method m = this.getClass().getMethod(method);
		return (String) m.invoke(this);
	}
	
	public String addRole()
			throws Exception {
		Role role = new Role();
		role.setName(java.net.URLDecoder.decode((request.getParameter("roleName")),"UTF-8"));
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		managerService.addRole(role);
		List roles = managerService.getRoles();
		request.setAttribute("roleList", roles);
		return SUCCESS;
	}
	
	public String delRole() throws Exception {
		int roleId = (new Integer(request.getParameter("roleId"))).intValue();
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		managerService.delRole(roleId);
		List roles = managerService.getRoles();
		request.setAttribute("roleList", roles);
		return SUCCESS;
	}
	
	public String mRoleEntry() throws Exception {
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List roles = managerService.getRoles();
		request.setAttribute("roleList", roles);
		return SUCCESS;
	}
	
	public String updateRole() throws Exception {
		Role role = new Role();
		role.setId(new Integer(Integer.parseInt(request.getParameter("roleId"))));
		role.setName(java.net.URLDecoder.decode(request.getParameter("roleName"),"UTF-8"));
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		managerService.updateRole(role);
		return null;
	}
	
	public String searchRole() throws Exception {
		Role role = new Role();
		role.setName(java.net.URLDecoder.decode((request.getParameter("roleName")),"UTF-8"));
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List roles = managerService.getRole(role);
		request.setAttribute("roleList", roles);
		return SUCCESS;
	}
	/*public String OpenVerity() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		//List<Role> r = managerService.getRole(id);
		int sta = 0;//=((Role) r).setStatus(status);
		
		if(managerService.OpenVerity(sta)){
		
			request.setAttribute("info", "成功打开审核操作！");
			return SUCCESS;
		}
		else{
			request.setAttribute("info", "打开审核操作失败！");
			return ERROR;
		}
		
		
		//return SUCCESS;
		}*/
	/*public String CloseVerity() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List<Role> r = managerService.getRole(id);
		int sta=((Role) r).setStatus(status);
		if(managerService.CloseVerity(sta)){
			request.setAttribute("info", "成功关闭操作申请！");
			return SUCCESS;
		}else{
			request.setAttribute("info", "执行申请出错！");
			return ERROR;
		}
	}*/
	
}
