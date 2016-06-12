package cn.cust.kyc.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ManagerServiceImpl;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.dao.RoleDAO;
import cn.cust.kyc.dao.impl.RoleDBDao;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Record;
import cn.cust.kyc.vo.Role;
import cn.cust.kyc.vo.Teacher;

import cn.edu.cust.levin.business.BusinessFactory;

public class ManageRecordAction extends ActionBase {
	
private int id;
	
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 操作类型
	 * ConstData.ApplyForDelete
	 * ConstData.ApplyForUpdate
	 */
	private int doType;
	
	/**
	 * 主键类型
	 */
	private boolean IDType;
	
	/**
	 * 目标主键
	 */
	private String oldId;
	
	/**
	 * 新内容主键
	 */
	private String newId;
	
	/**
	 * 申请日期
	 */
	private Date applyDate;
	
	/**
	 * 审核日期
	 */
	private Date verifyDate;
	
	/**
	 * 记录状态
	 * ConstData.RecorD...(3个)
	 */
	private int flg;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getDoType() {
		return doType;
	}

	public void setDoType(int doType) {
		this.doType = doType;
	}

	public boolean getIDType() {
		return IDType;
	}

	public void setIDType(boolean IDType) {
		this.IDType = IDType;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public String getNewId() {
		return newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public int getFlg() {
		return flg;
	}

	public void setFlg(int flg) {
		this.flg = flg;
	}
	
	public String manageRecord() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		String _s = request.getParameter("s");
		String s = null;
		if (_s != null)
			s = java.net.URLDecoder.decode(_s, "UTF-8");
		int size = ConstData.projectSize;
		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		List<Record> list = projectService.getRecords(pagination, s, null, true);
		String pagiUrl = "mRecord.action?method=manageRecord";
		if(s!=null)
			pagiUrl+="&s="+s;
		request.setAttribute("records", list);
		request.setAttribute("do", "do");
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl", pagiUrl);
		return SUCCESS;
	}
	
	public String manageHisRecords() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		String _s = request.getParameter("s");
		String s = null;
		if (_s != null)
			s = java.net.URLDecoder.decode(_s, "UTF-8");
		int size = ConstData.projectSize;
		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		List<Record> list = projectService.getRecords(pagination, s, teacher, true);
		String pagiUrl = "mRecord.action?method=manageHisRecords";
		if(s!=null)
			pagiUrl+="&s="+s;
		request.setAttribute("records", list);
		request.setAttribute("do", "delete");
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl", pagiUrl);
		return SUCCESS;
	}
	
	public String getAllRecords() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		String _s = request.getParameter("s");
		String s = null;
		if (_s != null)
			s = java.net.URLDecoder.decode(_s, "UTF-8");
		int size = ConstData.projectSize;
		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		List<Record> list = projectService.getRecords(pagination, s, null, false);
		String pagiUrl = "mRecord.action?method=getAllRecords";
		if(s!=null)
			pagiUrl+="&s="+s;
		request.setAttribute("records", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl", pagiUrl);
		return SUCCESS;
	}
	
	public String getHisAllRecords() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		String _s = request.getParameter("s");
		String s = null;
		if (_s != null)
			s = java.net.URLDecoder.decode(_s, "UTF-8");
		int size = ConstData.projectSize;
		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		List<Record> list = projectService.getRecords(pagination, s, teacher, false);
		String pagiUrl = "mRecord.action?method=getHisAllRecords";
		if(s!=null)
			pagiUrl+="&s="+s;
		request.setAttribute("records", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl", pagiUrl);
		return SUCCESS;
	}
	
	public String removeApply() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		Record r = projectService.getRecordById(id);
		if(r.getApplyer().getId().intValue()==teacher.getId().intValue()){
			projectService.deleteRecord(r);
			request.setAttribute("info", "成功撤销申请！");
			return manageHisRecords();
		}else{
			request.setAttribute("info", "您不能撤销该申请！");
			return manageHisRecords();
		}
	}
	
	public String showRecordDetail() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		Record r = projectService.getRecordById(id);
		Object oo = projectService.getOldTableByRecord(r);
		if(r.getDoType()==ConstData.ApplyForUpdate){
			Object no = projectService.getNewTableByRecord(r);
			request.setAttribute("no", no);
		}
		request.setAttribute("r", r);
		request.setAttribute("oo", oo);
		
		return "showRecordDetail";
	}

	
	
	
	public String verifyRecord() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		Record r = projectService.getRecordById(id);
		if(projectService.verifyRecord(r)){
			request.setAttribute("info", "成功执行申请！");
			return manageRecord();
		}else{
			request.setAttribute("info", "执行申请出错！");
			return manageRecord();
		}
	}
	
	public String verifyAllRecords() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		List<Record> list = projectService.getRecords(null, null, null, true);
		boolean b = true;
		System.out.println("全部通过");
		String result = "";
		for(Record r : list){
			if(!projectService.verifyRecord(r)){
				result+=r.getId()+" ";
				b = false;
			}
		}
		if(b){
			request.setAttribute("info", "操作成功，共执行申请"+list.size()+"个！");
			return manageRecord();
		}else{
			request.setAttribute("info", "执行申请出错！出错记录id为："+result+"，请核对！");
			return manageRecord();
		}
	}
	
	public String unVerifyRecord() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		Record r = projectService.getRecordById(id);
		if(r.getFlg()!=ConstData.RecordApplied){
			request.setAttribute("info", "该申请已过期！");
			return ERROR;
		}
		r.setFlg(ConstData.RecordUnverified);
		r.setVerifyDate(new Date());
		projectService.unverifyRecord(r);
		request.setAttribute("info", "操作成功！");
		return manageRecord();
	}
	
}
