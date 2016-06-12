package cn.cust.kyc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.bo.impl.ManagerServiceImpl;
import cn.cust.kyc.vo.Dir;
import cn.cust.kyc.vo.Operator;
import cn.cust.kyc.vo.Teacher;

public class Login extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		ActionContext context = ActionContext.getContext(); 
		Map params = context.getParameters(); 
		HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST); 
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		if(id!=null)
			id = id.trim();
		else{
			request.setAttribute("msg", "请重新登录！");
			return "reLogin";
		}
		String password = request.getParameter("password").trim();
		HttpSession session = request.getSession();
		List dirList = new ArrayList();
		List opList = new ArrayList();
		String msg = null;
		ManagerService ms = (ManagerService) cn.edu.cust.levin.business.BusinessFactory.getBusiness(ManagerServiceImpl.class);
		Teacher teacher = ms.loginByName(id, password);
		if (teacher != null ) {
			session.setAttribute("teacher", teacher);
			dirList = ms.getDir(teacher.getId().intValue());
			if (dirList.size() > 0) {
				Map operatorsMap = new HashMap();
				for(int i=0;i<dirList.size();i++){
					Dir d = (Dir) dirList.get(i);
					operatorsMap.put(d.getId(), new ArrayList());
				}
				opList = ms.getOperators(teacher.getId().intValue());
				for (int i = 0; i < opList.size(); i++) {
					Operator o = (Operator) opList.get(i);
					((List)operatorsMap.get(o.getDir().getId())).add(o);
				}
				request.getSession().setAttribute("OperatorsMap",operatorsMap);
				request.getSession().setAttribute("DirList", dirList);
			} else {
				request.getSession().setAttribute("OperatorsMap",new HashMap());
				request.getSession().setAttribute("DirList", new ArrayList());
			}
			return SUCCESS;
		} else {
			msg = "输入信息有误，请重新输入！";
			request.setAttribute("msg", msg);
			return "reLogin";
		}
	}
}
