package cn.cust.kyc.action;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.cust.kyc.bo.TeacherService;
import cn.cust.kyc.bo.impl.TeacherServiceImpl;
import cn.cust.kyc.vo.Operator;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.business.BusinessFactory;

public class TeacherHandle extends ActionSupport {
	ActionContext context = ActionContext.getContext(); 
	Map params = context.getParameters(); 
	HttpServletRequest request = (HttpServletRequest) context.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
	
	public String execute() throws Exception{
		String method = request.getParameter("method");
		Method m = this.getClass().getMethod(method);
		return (String) m.invoke(this);
	}
	
	public String getDir() throws Exception {
		Teacher tch = (Teacher)request.getSession().getAttribute("teacher");
		int teacherid =tch.getId().intValue();
		try{
			TeacherService ts = (TeacherService)BusinessFactory.getBusiness(TeacherServiceImpl.class);
			List dirs = ts.getDir(teacherid);
			request.setAttribute("dirs", dirs);
			return null;
		}catch(Exception ex){
			return ERROR;
		}
	}

	public String getOperator() throws Exception {
		Teacher tch = (Teacher)request.getSession().getAttribute("teacher");
		int teacherid =tch.getId().intValue();
		try{
			TeacherService ts = (TeacherService)BusinessFactory.getBusiness(TeacherServiceImpl.class);
			int id = Integer.parseInt(request.getParameter("id"));
			List operators = new ArrayList();
			List opList = ts.getOperator(teacherid);
			for (int i = 0; i < opList.size(); i++) {
				Operator o = (Operator) opList.get(i);
				if (o.getDir().getId().intValue() == id)
					operators.add(o);
			}
			request.getSession().setAttribute("OperatorList", operators);
			return "getOperatorSuccess";
		}catch(Exception ex){
			ex.printStackTrace();
			request.setAttribute("info", "获取操作出错！");
			return ERROR;
		}
	}
	
	public String changePasword() throws Exception {
		
		String old = request.getParameter("oldPassword");
		String newP = request.getParameter("newPassword");
		String again = request.getParameter("againPassword");
		
		// 考虑从 session里面取
		Teacher tch =(Teacher)request.getSession().getAttribute("teacher");
		int tid = tch.getId().intValue();
		
		try{			
			TeacherService ts = (TeacherService)BusinessFactory.getBusiness(TeacherServiceImpl.class);
			String msg = "";
			Teacher teacher = ts.getTeacher(tid);		
			if(isValid(old) && isValid(newP) && isValid(again))
			{
				String psw =teacher.getPassword();
				if(old.equals(psw))
				{
					if(newP.equals(again))
					{
						ts.changePasword(tid, newP);
						msg = "修改密码成功，下次登陆时请使用新密码";
						request.setAttribute("info", msg);
						return SUCCESS;
					}
					else
					{
						msg = "新密码两次输入不一致，请确认";
					}
				}
				else
				{
					msg = "原密码输入错误，请确认";
				}
			}
			else
			{
				msg = "输入不能为空，请确认";
			}
			
			// 考虑将 msg 输出
			request.setAttribute("info", msg);
			return "changePaswordSuccess";
		}catch(Exception ex){
			request.setAttribute("info", "修改密码出现异常");
			return ERROR;
		}
	}
	
	/*
	 * 辅助方法, 检查字符串是否合法
	 */
	private boolean isValid(String str)
	{
		if(str != null && str.length() > 0)
			return true;
		return false;
	}

}
