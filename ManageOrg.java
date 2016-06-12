package cn.cust.kyc.action;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.bo.impl.ManagerServiceImpl;
import cn.cust.kyc.dao.cache.OrgCache;
import cn.cust.kyc.util.TreeList;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageOrg extends ActionSupport {

	ActionContext context = ActionContext.getContext(); 
	Map<String, Object> params = context.getParameters(); 
	HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
	HttpServletResponse response = (HttpServletResponse) context.get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
	
	public String execute() throws Exception{
		String method = request.getParameter("method");
		Method m = this.getClass().getMethod(method);
		return (String) m.invoke(this);
	}
	
	public String getParentOrg() throws Exception {
		int teacherId = (new Integer(request.getParameter("teacherId"))).intValue();
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List parentOrgs = managerService.getParentOrg(teacherId);
		request.setAttribute("parentOrgList", parentOrgs);
		return null;
	}
	
	public String getChildOrg() throws Exception {
		int intSelectedOrgId = -1;
		String selectedOrgId = request.getParameter("selectedOrgId");
		if(selectedOrgId != null && selectedOrgId.length() > 0){
			intSelectedOrgId = Integer.parseInt(selectedOrgId);
			request.getSession().setAttribute("selectedOrgId", new Integer(intSelectedOrgId));
		}
		Teacher manager = null;
		manager = (Teacher)request.getSession().getAttribute("teacher");

		if(intSelectedOrgId < 0) 
			intSelectedOrgId = manager.getOrg().getId().intValue();
		
	    List directChildOrgList = new ArrayList();
		try{
			int porgid = Integer.parseInt(request.getParameter("porgid"));
			String orgname = request.getParameter("orgname");

			ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			List childOrgs = managerService.getChildOrg(porgid);

			if(orgname!=null && !orgname.equalsIgnoreCase("")){
				for(int i=0;i<childOrgs.size();i++){
					Org org = (Org)childOrgs.get(i);
					if(org.getName().equalsIgnoreCase(orgname)){
						directChildOrgList.add(org);
					}
				}
				request.setAttribute("directChildOrgList", directChildOrgList);
			}else{
				request.setAttribute("directChildOrgList", childOrgs);
			}
			Org org = null;
			org = managerService.getOrg(intSelectedOrgId);
			request.setAttribute("selectedOrg", org);
			TreeList treeList = managerService.getOrgTree(manager.getOrg().getId().intValue());
			request.setAttribute("orgTree", treeList);
			return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info", "查询子组织机构出错！");
			return ERROR;
		}
	}
	
	public String addOrg() throws Exception {
		String orgName = java.net.URLDecoder.decode((request.getParameter("orgName")),"UTF-8");
		String orgType = java.net.URLDecoder.decode((request.getParameter("orgType")),"UTF-8");
		String orgId = request.getParameter("orgId");
		Org selectedOrg = null;
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		// 选择的Org
		int intOrgId = Integer.parseInt(orgId);
		selectedOrg = managerService.getOrg(intOrgId);
		// 设置添加的Org的层
		int layer = selectedOrg.getLayer().intValue();
		// set this Org which we want to add
		Org org = new Org();
		org.setName(orgName);
		org.setType(orgType);
		org.setLayer(new Integer(layer + 1));
		org.setParent(selectedOrg);
		managerService.addOrg(org);	
		// 查出上次数据，并返回
		List directChildOrgList = managerService.getDirectChildOrg(selectedOrg.getId().intValue());
		// 查出选定Org的下级组织机构列表
		request.setAttribute("directChildOrgList", directChildOrgList);
		// 查出选定的Org
		request.setAttribute("selectedOrg", selectedOrg);
		Teacher manager =null;
		manager = (Teacher)request.getSession().getAttribute("teacher");

		// 查出组织机构树
		TreeList treeList = managerService.getOrgTree(manager.getOrg().getId().intValue());
		request.setAttribute("orgTree", treeList);		
		return SUCCESS;
	}
	
	public String delOrg() throws Exception {
		response.setHeader("Cache-Control", "no-cache");
		int intOrgId = (new Integer(request.getParameter("orgId").trim())).intValue();
		int selectedOrgId = Integer.parseInt(request.getParameter("selectedOrgId").trim());
		request.getSession().setAttribute("selectedOrgId", new Integer(selectedOrgId));
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		managerService.delOrg(intOrgId);
		Org selectedOrg = null;
		selectedOrg = managerService.getOrg(selectedOrgId);
		// 查出上次数据，并返回
		List directChildOrgList = managerService.getDirectChildOrg(selectedOrgId);
		// 查出选定Org的下级组织机构列表
		request.setAttribute("directChildOrgList", directChildOrgList);
		// 查出选定的Org
		request.setAttribute("selectedOrg", selectedOrg);
		Teacher manager = null;
		manager = (Teacher)request.getSession().getAttribute("teacher");

		// 查出组织机构树
		TreeList treeList = managerService.getOrgTree(manager.getOrg().getId().intValue());
		request.setAttribute("orgTree", treeList);		
		return SUCCESS;
	}
	
	public String updateOrg() throws Exception {
		Org org = new Org();
		org.setId(new Integer(request.getParameter("orgId")));
		org.setName(java.net.URLDecoder.decode(request.getParameter("orgName"),"UTF-8"));
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		managerService.updateOrg(org);
		return null;
	}
	
	
	public String searchOrg() throws Exception {
		int intOrgId = (new Integer(request.getParameter("orgId").trim())).intValue();
		request.getSession().setAttribute("selectedOrgId", new Integer(intOrgId));
		String orgName = java.net.URLDecoder.decode((request.getParameter("orgName")),"UTF-8");
		request.setAttribute("r_orgname", orgName);
		Teacher manager = null;
		manager = (Teacher)request.getSession().getAttribute("teacher");
		Org selectedOrg = null;
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		Org org = null;
		org = new Org();
		org.setName(orgName);
		List directChildOrgList = managerService.getOrg(intOrgId,org);
		if(directChildOrgList.isEmpty()) {
			selectedOrg = managerService.getOrg(intOrgId);
		}
		else {
			selectedOrg = (Org)directChildOrgList.get(0);	
		}
		// 查出选定Org的下级组织机构列表
		request.setAttribute("directChildOrgList", directChildOrgList);
		// 查出选定的Org
		request.setAttribute("selectedOrg", selectedOrg);

		TreeList treeList = managerService.getOrgTree(manager.getOrg().getId().intValue());
		request.setAttribute("orgTree", treeList);		
		return SUCCESS;
	}
	
	public String mOrgEntry() throws Exception {
		if(request.getSession().getAttribute("selectedOrgId") != null) {
			request.getSession().removeAttribute("selectedOrgId");
		}
		Teacher manager = (Teacher)request.getSession().getAttribute("teacher");
		request.getSession().setAttribute("selectedOrgId", manager.getOrg().getId());
		request.setAttribute("selectedOrg", manager.getOrg());
		int orgId = manager.getOrg().getId().intValue();
		// 查出下级组织机构列表
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List directChildOrgList = managerService.getDirectChildOrg(orgId);
		request.setAttribute("directChildOrgList", directChildOrgList);
		// 查出组织机构树
		TreeList orgTree = managerService.getOrgTree(orgId);
		request.setAttribute("orgTree", orgTree);
		return SUCCESS;
	}
	
	public String getDirectChildOrgs() throws Exception {
		int intSelectedOrgId = -1;
		String selectedOrgId = request.getParameter("selectedOrgId");
		if(selectedOrgId != null && selectedOrgId.length() > 0){
			intSelectedOrgId = Integer.parseInt(selectedOrgId);
			request.getSession().setAttribute("selectedOrgId", new Integer(intSelectedOrgId));
		}
		Teacher manager = null;
		manager=(Teacher)request.getSession().getAttribute("teacher");
		if(intSelectedOrgId < 0) 
			intSelectedOrgId = manager.getOrg().getId().intValue();
		
		try{
			ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			List directChildOrgList = managerService.getDirectChildOrg(intSelectedOrgId);
			request.setAttribute("directChildOrgList", directChildOrgList);
			Org org = null;
			org = managerService.getOrg(intSelectedOrgId);
			request.setAttribute("selectedOrg", org);
			TreeList treeList = managerService.getOrgTree(manager.getOrg().getId().intValue());
			request.setAttribute("orgTree", treeList);
			return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info", "请求组织机构的下级组织机构列表出错！");
			return ERROR;
		}
	}
}
