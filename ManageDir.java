package cn.cust.kyc.action;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.bo.impl.ManagerServiceImpl;
import cn.cust.kyc.vo.Dir;
import cn.edu.cust.levin.business.BusinessFactory;


public class ManageDir extends ActionSupport{
	ActionContext context = ActionContext.getContext(); 
	Map params = context.getParameters(); 
	HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
	
	public String execute() throws Exception{
		String method = request.getParameter("method");
		Method m = this.getClass().getMethod(method);
		return (String) m.invoke(this);
	}
	
	public String mDirEntry()throws Exception {
		ManagerService managerService = (ManagerService) BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List dirs = managerService.getDirsBySort();
		request.setAttribute("dirList", dirs);
		return SUCCESS;
	}
	
	public String addDir() throws Exception {
		ActionContext context = ActionContext.getContext(); 
		Map params = context.getParameters(); 
		HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST); 
		
		Dir dir = new Dir();
		dir.setName(java.net.URLDecoder.decode((request.getParameter("dirName")),"UTF-8"));
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		int sortid = managerService.getDirLastSize()+1;
		dir.setSortid(sortid);
		
		managerService.addDir(dir);
		List dirs = managerService.getDirs();
		request.setAttribute("dirList", dirs);
		return SUCCESS;
	}
	
	public String delDir() throws Exception {
		Integer dirId = new Integer(request.getParameter("dirId"));
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		managerService.delDir(dirId.intValue());
		List dirs = managerService.getDirsBySort();
		request.setAttribute("dirList", dirs);
		return SUCCESS;
	}
	
	public String searchDir() throws Exception {
		Dir dir = new Dir();
		dir.setName(java.net.URLDecoder.decode((request.getParameter("dirName")),"UTF-8"));
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List dirs = managerService.getDir(dir);
		request.setAttribute("dirList", dirs);
		return SUCCESS;
	}
	
	public String upSort() throws Exception {
		  int dirid=Integer.parseInt(request.getParameter("dirid"));
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			ms.upSortDir(dirid);
			List dirs = ms.getDirsBySort();
			request.setAttribute("dirList", dirs);
			return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info","升序失败！");
			return ERROR;
		}
	}
	
	public String downSort() throws Exception {
		 int dirid=Integer.parseInt(request.getParameter("dirid"));
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			ms.downSortDir(dirid);
			List dirs = ms.getDirsBySort();
			request.setAttribute("dirList", dirs);
			return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info","降序失败！");
			return ERROR;
		}
	}
}
