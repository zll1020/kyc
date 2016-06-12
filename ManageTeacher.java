package cn.cust.kyc.action;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.bo.impl.ManagerServiceImpl;
import cn.cust.kyc.dao.TeacherDAO;
import cn.cust.kyc.dao.impl.TeacherDBDAO;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.Md5Util;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.SwitchTools;
import cn.cust.kyc.util.TreeList;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageTeacher extends ActionSupport {
	
	ActionContext context = ActionContext.getContext(); 
	Map<String,Object> params = context.getParameters(); 
	HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST); 
	
	public String execute() throws Exception{
		String method = request.getParameter("method");
		Method m = this.getClass().getMethod(method);
		return (String) m.invoke(this);
	}
	
	public String addTeacher() throws Exception {
		String tname = request.getParameter("tname");
		String orgid = request.getParameter("orgid");
		String roleid = request.getParameter("roleid");
		int orgId = -1;
		String selectedOrgIdS =request.getParameter("selectedOrgId");
		if(selectedOrgIdS != null && selectedOrgIdS.length() > 0)
		{
			orgId = Integer.parseInt(selectedOrgIdS);
			request.getSession().setAttribute("selectedOrgId", new Integer(orgId));
		}
		Teacher tch = (Teacher)request.getSession().getAttribute("teacher");
		if(orgId < 0)
			orgId = tch.getOrg().getId().intValue();	
		
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			if((tname==null||tname.equalsIgnoreCase(""))||(orgid==null||orgid.equalsIgnoreCase("")) ||(roleid==null||roleid.equalsIgnoreCase(""))){	
				request.setAttribute("info","请完整填写表格！");
			}else{
				// 先判断是否该名字是否已经被占用
				TeacherDAO tchdao = (TeacherDAO)BusinessFactory.getBusiness(TeacherDBDAO.class);
				Teacher oldTch = tchdao.getTeacher(tname);
				if(oldTch==null){
					Teacher teacher = new Teacher();
					teacher.setName(tname);
					teacher.setPassword(Md5Util.getMD5Str("111111"));
					ms.addTeacher(teacher,Integer.parseInt(roleid) , Integer.parseInt(orgid));
					request.setAttribute("info", "添加教师成功！");
					List teachers =ms.getTeachers(orgId);
				}else{
					request.setAttribute("info", "已经存在的名字，请重新选择！");
				}
			}
			return mTeacherEntry();
			
		}catch(Exception ex){
			if(ConstData.isPrintEx)
				ex.printStackTrace();
			request.setAttribute("info", "添加教师失败！");
			return ERROR;
		}
	}
	
	public String updateTeacher() throws Exception {
		int tid = Integer.parseInt(request.getParameter("tId"));
		String tName = SwitchTools.decode(request.getParameter("tName"));
		int orgid=Integer.parseInt(request.getParameter("orgid"));
		int roleid=Integer.parseInt(request.getParameter("roleid"));
		
		int orgId = -1;
		String selectedOrgIdS =request.getParameter("selectedOrgId");
		if(selectedOrgIdS != null && selectedOrgIdS.length() > 0)
		{
			orgId = Integer.parseInt(selectedOrgIdS);
			request.getSession().setAttribute("selectedOrgId", new Integer(orgId));
		}
		
		Teacher tch = null;
		tch=(Teacher)request.getSession().getAttribute("teacher");
		if(tch==null){
			tch=(Teacher)request.getSession().getAttribute("teacher");
		}
		if(orgId < 0)
			orgId = tch.getOrg().getId().intValue();	
		
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			String tname = URLDecoder.decode(tName,"UTF-8");
			ms.updateTeacher(tid,tname,orgid,roleid);
			return mTeacherEntry();
		}catch(Exception ex){
			request.setAttribute("info", "修改教师出错！");
			return ERROR;
		}
	}
	
	public String delTeacher() throws Exception {
		int teaid = Integer.parseInt(request.getParameter("teacherid"));
		int orgId = -1;
		String selectedOrgIdS =request.getParameter("selectedOrgId");
		if(selectedOrgIdS != null && selectedOrgIdS.length() > 0)
		{
			orgId = Integer.parseInt(selectedOrgIdS);
			request.getSession().setAttribute("selectedOrgId", new Integer(orgId));
		}
		
		Teacher tch = (Teacher)request.getSession().getAttribute("teacher");
		if(orgId < 0)
			orgId = tch.getOrg().getId().intValue();	
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			ms.delTeacher(teaid);
			return mTeacherEntry();
		}catch(Exception ex){
			request.setAttribute("info", "删除教师失败！");
			return ERROR;
		}
	}
	
	public String seachTeacher() throws Exception {
		int size = ConstData.size;
		int page = (request.getParameter("page")==null||request.getParameter("page").trim().length()<=0)?1:Integer.parseInt(request.getParameter("page"));
		Pagination pagination = new Pagination();
		
		String tno = request.getParameter("tno");
		String tname = request.getParameter("tname");
		int orgId = -1;
		String selectedOrgIdS = request.getParameter("selectedOrgId");
		if(selectedOrgIdS != null && selectedOrgIdS.length() > 0)
		{
			orgId = Integer.parseInt(selectedOrgIdS);
			request.getSession().setAttribute("selectedOrgId", new Integer(orgId));
		}
		Teacher tch =null;
		tch = (Teacher)request.getSession().getAttribute("teacher");
		if(tch==null){
			tch=(Teacher)request.getSession().getAttribute("teacher");
		}
		if(orgId < 0)
			orgId = tch.getOrg().getId().intValue();	
		
		try{
			if (tname == null){
				tname = "";
			}
			tname = URLDecoder.decode(tname, "UTF-8");
			int tchid = -1;
			if (tno == null ){
				tno = "";
			}
			else if(tno.trim().length()>0)
				tchid = Integer.parseInt(tno);
			
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		    TreeList tl = ms.getOrgTree(tch.getOrg().getId().intValue());
		    request.setAttribute("orgTree", tl);
		    List orgs = ms.getAllChildOrgs(orgId);
		    request.setAttribute("orgs",orgs);
		    List roles = ms.getRoles();
		    request.setAttribute("roles",roles);
		    List teachers =ms.getClassTeacher(orgId, tchid, tname, size, page, pagination);//queryTeacher(tname, tno);
		    request.setAttribute("pagination", pagination);
		    String pagiUrl = "mTeacher.do?method=seachTeacher";
		    if(tno != null && tno.trim().length()>0)
		    	pagiUrl += "&tno="+tno;
		    if(tname != null && tname.trim().length()>0)
		    	pagiUrl += "&tname="+tname;
		    pagiUrl += "&selectedOrgId=";
		    request.setAttribute("pagiUrl", pagiUrl);
		    request.setAttribute("teachers", teachers);
		    
		    request.setAttribute("r_id", tno);
		    request.setAttribute("r_name", tname);
			return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info", "查询教师失败！");
			return ERROR;
		}
	}
	
	public String getTeachersByOrgRole() throws Exception {
		 int orgid = Integer.parseInt(request.getParameter("orgid"));
		 int roleid = Integer.parseInt(request.getParameter("roleid"));
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			List orgs = ms.getTeachers(orgid, roleid);
			request.setAttribute("orgs", orgs);
		    return null;
		}catch(Exception ex){
			return ERROR;
		}
	}
	
	public String resetPassword() throws Exception {
		int tid = Integer.parseInt(request.getParameter("teacherid"));
		int orgId = -1;
		String selectedOrgIdS =request.getParameter("selectedOrgId");
		if(selectedOrgIdS != null && selectedOrgIdS.length() > 0)
		{
			orgId = Integer.parseInt(selectedOrgIdS);
			request.getSession().setAttribute("selectedOrgId", new Integer(orgId));
		}
		
		Teacher tch = (Teacher)request.getSession().getAttribute("teacher");
		if(orgId < 0)
			orgId = tch.getOrg().getId().intValue();	
		
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			/*TeacherService ts = (TeacherService)BusinessFactory.getBusiness(TeacherServiceImpl.class);
			Teacher teacher = ts.getTeacher(tid);
			ms.resetPassword(teacher);*/
			ms.updateTeacher(tid, Md5Util.getMD5Str("111111")); 
			request.setAttribute("info","重置密码成功！");
			return mTeacherEntry();
			
			
		}catch(Exception ex){
			request.setAttribute("info","重置密码失败！");
			return ERROR;
		}
	}
	
	public String changePasword() throws Exception {
		  String newpassword = request.getParameter("newPassword");
		  String againnewpwd = request.getParameter("againPassword");
		  String oldpassword = request.getParameter("oldPassword");
		  Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
		  int tid =teacher.getId().intValue();
		  String msg = null;
		try{
			newpassword = newpassword.trim();
			againnewpwd = againnewpwd.trim();
			oldpassword = oldpassword.trim(); 
			
			if(newpassword == "" || newpassword.length() == 0) {
				msg="要修改的密码 不能为空!";
				request.setAttribute("info", msg);
				 return ERROR;
			}
			
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			if(teacher.getPassword().equals(Md5Util.getMD5Str(oldpassword))){
				if(newpassword.equals(againnewpwd)&&!(newpassword.equals(oldpassword))){
					ms.changePasword(tid, Md5Util.getMD5Str(newpassword));
					msg="修改密码成功！";
					request.setAttribute("pagePath", "page/manage/changePassword.jsp");
					request.setAttribute("info", msg);
					return "info";
				}else if(newpassword.equals(againnewpwd)&& newpassword.equals(oldpassword)){
					msg="你想修改的密码跟原密码相同！";
					request.setAttribute("info", msg);
					return ERROR;
				}else{
					msg="请确认两次输入的密码是否一致！";
					request.setAttribute("info", msg);
					return ERROR;
				}
			}else{
				msg="你输入的原密码不正确！";
				request.setAttribute("info", msg);
				 return ERROR;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			msg="修改密码失败！";
			request.setAttribute("info", msg);
			return ERROR;
		}
	}
	
	public String mTeacherEntry() throws Exception {
		int size = ConstData.size;
		int page = (request.getParameter("page")==null||request.getParameter("page").trim().length()<=0)?1:Integer.parseInt(request.getParameter("page"));
		Pagination pagination = new Pagination();

		int orgId = -1;
		String selectedOrgIdS =request.getParameter("selectedOrgId");
		if(selectedOrgIdS != null && selectedOrgIdS.length() > 0)
		{
			orgId = Integer.parseInt(selectedOrgIdS);
			request.getSession().setAttribute("selectedOrgId", new Integer(orgId));
		}
		Teacher tch=(Teacher)request.getSession().getAttribute("teacher");
		if(orgId < 0)
			orgId = tch.getOrg().getId().intValue();		
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			List teachers = ms.getClassTeacher(orgId, size, page, pagination);
			request.setAttribute("teachers", teachers);
			TreeList tl = ms.getOrgTree(tch.getOrg().getId().intValue());
			request.setAttribute("orgTree", tl);
			List treechilds = ((TreeList) tl).getChildList();
	//*******************获取管理教师中的组织机构下拉列表  BEGAIN******************//
			TreeList tree = new TreeList();
			 TreeList newtree=ms.getOrgTree(orgId);
			List orgs= tree.getOrgsByTree(newtree);
	//******************获取管理教师中的组织机构下拉列表  END*******************//
			request.setAttribute("orgs",orgs);
			List roles = ms.getRoles();
			
			request.setAttribute("pagination", pagination);
			request.setAttribute("pagiUrl", "mTeacher.action?method=mTeacherEntry&selectedOrgId=");
			request.setAttribute("roles",roles);
			return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info", "管理员获取教师列表出错！");
			return ERROR;
		}
	}
	public String ManageVerity() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		
		
		String status = request.getParameter("status");
		
		 String msg = null;
			try{
				status = status.trim();
				if(status == "" || status.length() == 0) {
					msg="选择的操作不能为空!";
					request.setAttribute("info", msg);
					 return ERROR;
				}
		ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		String id = teacher.getName();
		
		 if(status.equals("打开")||status.equals("关闭")){
			// ms.changeVerity(id, status);
			// request.setAttribute("teachers", teacher);
			 
			/* msg="成功修改状态！";
			 	request.setAttribute("pagePath", "page/Verity/manageVerity.jsp");
			 	request.setAttribute("info", msg);
				return "info";*/
			if(teacher.getVerity().equals(status)){
					ms.changeVerity(id, status);
					msg="要修改的状态为当前状态！";
					request.setAttribute("pagePath", "page/Verity/manageVerity.jsp");
					request.setAttribute("info", msg);
					return "info";
					
				}else if(teacher.getVerity().equals("打开")&& status.equals("关闭")){
					ms.changeVerity(id, status);
					msg="成功关闭审核！";
					request.setAttribute("pagePath", "page/Verity/manageVerity.jsp");
					request.setAttribute("info", msg);
					return "info";
				}else if(teacher.getVerity().equals("关闭")&& status.equals("打开")){
					ms.changeVerity(id, status);
					msg="成功打开审核！";
					request.setAttribute("pagePath", "page/Verity/manageVerity.jsp");
					request.setAttribute("info", msg);
					return "info";
				}else{
					msg="你输入的操作类型不正确！";
					request.setAttribute("info", msg);
					return ERROR;
				}
		}else{
			msg="你输入的操作类型不正确！";
			request.setAttribute("info", msg);
			return ERROR;
			 
		}
	}catch(Exception ex){
		ex.printStackTrace();
		msg="修改审核失败！";
		request.setAttribute("info", msg);
		return ERROR;
	}
			
}
}
