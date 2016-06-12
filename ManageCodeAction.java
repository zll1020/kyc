package cn.cust.kyc.action;

import java.util.ArrayList;
import java.util.List;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.vo.Code;
import cn.cust.kyc.vo.CodeType;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageCodeAction extends ActionBase{
	
	private int option;
	private String codeType;
	private String _codeType;
	
	private int id;
	private int number;
	private String type;
	private String name;
	private String info;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String get_codeType() {
		return _codeType;
	}

	public void set_codeType(String codeType) {
		_codeType = codeType;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String manageCodeIndex() throws Exception{
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService)BusinessFactory.getBusiness(ProjectServiceImpl.class);
		List<CodeType> list = projectService.getCodeTypes();
		request.setAttribute("CodeTypes", list);
		return SUCCESS;
	}
	
	public String manageCodeType() throws Exception{
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService)BusinessFactory.getBusiness(ProjectServiceImpl.class);
		if(projectService.doCodeType(option, codeType, _codeType)){
			return manageCodeIndex();
		}else{
			if(option==1)
				request.setAttribute("info", "要修改的代码类型已存在，或提取操作类型出错！");
			else if(option==2)
				request.setAttribute("info", "要添加的代码类型已存在！");
			else
				request.setAttribute("info", "提取操作类型出错！");
		}
		return ERROR;
	}
	
	public String addCode() throws Exception{
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService)BusinessFactory.getBusiness(ProjectServiceImpl.class);
		
		//add code 
		List<Code> codeList= projectService.getCodeByType(codeType);
		
		int num=0;
		
		for(Code code : codeList){
			if(code.getNumber()>num)
				num=code.getNumber();
		}
		
		number=num+1;
		System.out.println("code==="+number+"  "+codeType+"  "+name+"  "+info);
		//add code info 
		
		 if(codeType.equals("项目类别")){
		 
			info=teacher.getOrg().getName();
		}
		
		
		
		if(projectService.addCode(number, codeType, name, info)){
			return manageCodeIndex();
		}
		else{
			request.setAttribute("info", "");
			return ERROR;
		}
	}
	
	public String deleteCode() throws Exception{
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService)BusinessFactory.getBusiness(ProjectServiceImpl.class);
		if(projectService.deleteCode(id))
			return manageCodeIndex();
		else{
			request.setAttribute("info", "");
			return ERROR;
		}
	}
	
	public String updateCode() throws Exception{
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService)BusinessFactory.getBusiness(ProjectServiceImpl.class);
		if(projectService.updateCode(id, name, info))
			return manageCodeIndex();
		else{
			request.setAttribute("info", "");
			return ERROR;
		}
	}
	
	public String getCodes() throws Exception{
		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
		if(teacher==null){
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		ProjectService projectService = (ProjectService)BusinessFactory.getBusiness(ProjectServiceImpl.class);
		List<Code> list = projectService.getCodeByType(codeType);
		if(list==null)
			return ERROR;
		
		//不同科室的人看到的项目类别不一样
		List<Code> listSelect=null;
		if(codeType.equals("项目类别")){
			listSelect=new ArrayList<Code>();
			for(Code code : list){
				if(teacher.getOrg().getName().equals(code.getInfo())){
					listSelect.add(code);
				}
			}
		}else{
			listSelect=list;
		}
		
		request.setAttribute("codes", listSelect);
		return "codes";
	}
	
}
