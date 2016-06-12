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
import cn.cust.kyc.vo.Operator;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageOperator extends ActionSupport {
	ActionContext context = ActionContext.getContext(); 
	Map params = context.getParameters(); 
	HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
	
	public String execute() throws Exception{
		String method = request.getParameter("method");
		Method m = this.getClass().getMethod(method);
		return (String) m.invoke(this);
	}
	
	public String addOperator() throws Exception {
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		Operator operator = new Operator();
		operator.setName(java.net.URLDecoder.decode(request.getParameter("operatorName"),"UTF-8"));
		operator.setType(java.net.URLDecoder.decode(request.getParameter("operatorType"),"UTF-8"));
		operator.setClassname(java.net.URLDecoder.decode(request.getParameter("operatorClsName"),"UTF-8"));
		operator.setEnterUrl(java.net.URLDecoder.decode(request.getParameter("operatorURL"),"UTF-8"));
		int dirId = Integer.parseInt(request.getParameter("dirId"));
		
		Dir dir = managerService.getDir(dirId,true);
		operator.setDir(dir);
		int size=managerService.getOpLastSize()+1;
		operator.setSortid(size);
		dir = null;
		managerService.addOperator(operator);
		//List operators = managerService.getOperators();
		List operators = managerService.getOperatorsBySort();
		request.setAttribute("operatorList", operators);
		List dirList = managerService.getDirs();
		request.setAttribute("dirList", dirList);
		return SUCCESS;
	}
	
	public String searchOperator() throws Exception {
		//String operatorId = request.getParameter("operatorId").trim();
		String operatorName = java.net.URLDecoder.decode(request.getParameter("operatorName"),"UTF-8");
		String operatorType = java.net.URLDecoder.decode(request.getParameter("operatorType"),"UTF-8");
		String operatorClsName = java.net.URLDecoder.decode(request.getParameter("operatorClsName"),"UTF-8");
		String operatorEnterUrl = java.net.URLDecoder.decode(request.getParameter("operatorURL"),"UTF-8");
		String dirId = request.getParameter("dirId");
		
		Operator operator = new Operator();
		if(!operatorName.equals("")) {
			operator.setName(operatorName);
		}
		if(!operatorType.equals("")) {
			operator.setType(operatorType);
		}
		if(!operatorClsName.equals("")) {
			operator.setClassname(operatorClsName);
		}
		if(!operatorEnterUrl.equals("")) {
			operator.setEnterUrl(operatorEnterUrl);
		}
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		if(!dirId.equals("0")) {
			int intDirId = Integer.parseInt(dirId);
			operator.setDir(managerService.getDir(intDirId,true));
		}
		List operators = managerService.getOperators(operator);
		//List operators = managerService.getOperatorsBySort();
		request.setAttribute("operatorList", operators);
		List dirList = managerService.getDirs();
		request.setAttribute("dirList", dirList);
		return SUCCESS;
	}
	
	public String delOperator() throws Exception {
		Operator operator = new Operator();
		int opId =Integer.parseInt(java.net.URLDecoder.decode(request.getParameter("operatorId"),"UTF-8"));
		String opName = java.net.URLDecoder.decode(request.getParameter("operatorName"),"UTF-8");
		String opType= java.net.URLDecoder.decode(request.getParameter("operatorType"),"UTF-8");
		operator.setId(new Integer(opId));
		operator.setName(opName);
		operator.setType(opType); 
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		managerService.delOperator(new Integer(opId));
		List dirList = managerService.getDirs();
		request.setAttribute("dirList", dirList);
		List operators = managerService.getOperatorsBySort();
		request.setAttribute("operatorList", operators);
		return SUCCESS;
	}
	
	public String mOperatorEntry() throws Exception {
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List operators = managerService.getOperatorsBySort();
		request.setAttribute("operatorList", operators);
		List dirList = managerService.getDirs();
		request.setAttribute("dirList", dirList);
		return SUCCESS;
	}
	
	public String getOperatorsInDir() throws Exception {
		int dirId = new Integer(request.getParameter("dirId")).intValue();
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		List operatorsInDir = managerService.getOperatorsInDir(dirId);
		request.setAttribute("operatorsInDir", operatorsInDir);
		return SUCCESS;
	}
	
	public String updateOperator() throws Exception {
		
		String opId = request.getParameter("operatorId");
		String opName = java.net.URLDecoder.decode(request.getParameter("operatorName"),"UTF-8");
		String opType= java.net.URLDecoder.decode(request.getParameter("operatorType"),"UTF-8");
		String opClsName = java.net.URLDecoder.decode(request.getParameter("operatorClsName"),"UTF-8");
		String opEnterUrl = java.net.URLDecoder.decode(request.getParameter("operatorURL"),"UTF-8");
		String opDirId = request.getParameter("operatorDirId");

		Operator operator = new Operator();
		operator.setId(new Integer(opId));
		operator.setName(opName);
		operator.setType(opType);
		operator.setClassname(opClsName);
		operator.setEnterUrl(opEnterUrl);
		ManagerService managerService = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
		Dir dir = managerService.getDir(Integer.parseInt(opDirId),true);
		operator.setDir(dir);
		managerService.updateOperator(operator);
		return null;
	}	
	
	public String upSort() throws Exception {
		  int operatorid=Integer.parseInt(request.getParameter("operatorid"));
		  String opname= new String(request.getParameter("opname").getBytes("iso-8859-1"),"UTF-8").trim();
		  String type=request.getParameter("type");
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			ms.upSortOperator(operatorid);
			List dirList = ms.getDirs();
			request.setAttribute("dirList", dirList);
			List operators = ms.getOperatorsBySort();
			request.setAttribute("operatorList", operators);
			return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info","升序失败！");
			return ERROR;
		}
	}
	
	public String downSort() throws Exception {
		 int operatorid=Integer.parseInt(request.getParameter("operatorid"));
		  String opname= new String(request.getParameter("opname").getBytes("iso-8859-1"),"UTF-8").trim();
		  String type=request.getParameter("type");
		try{
			ManagerService ms = (ManagerService)BusinessFactory.getBusiness(ManagerServiceImpl.class);
			ms.downSortOperator(operatorid);
			List dirList = ms.getDirs();
			request.setAttribute("dirList", dirList);
			List operators = ms.getOperatorsBySort();
			request.setAttribute("operatorList", operators);
			return SUCCESS;
		}catch(Exception ex){
			request.setAttribute("info","降序失败！");
			return ERROR;
		}
	}
}
