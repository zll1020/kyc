package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.FieldBean;
import cn.cust.kyc.util.FieldsControl;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.RecordSetting;
import cn.cust.kyc.vo.InfoTable;
import cn.cust.kyc.vo.Member;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Person;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Record;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManagePersonAction extends ActionBase {

	private int id;
	/**
	 * 序号
	 */
	private int serialNumber;

	/**
	 * 项目
	 */
	private String innerCode;

	/**
	 * 登记年度
	 */
	private String year;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 出生日期
	 */
	private String birth;

	/**
	 * 学历
	 */
	private String diploma;

	/**
	 * 职称
	 */
	private String professionalTitle;

	/**
	 * 所属单位
	 */
	private String organization;

	/**
	 * 承但任务
	 */
	private String assignment;
	/**
	 * 身份
	 */
	private String role;
	/**
	 * 学位
	 */
	private String degree;

	/**
	 * 登记表日期
	 */
	private Date enterDate;

	// -----编辑信息
	/**
	 * 经手人
	 */
	private Teacher creater;

	/**
	 * 登记日期
	 */
	private Date createDate;

	/**
	 * 修改人
	 */
	private Teacher updater;

	/**
	 * 修改日期
	 */
	private Date updateDate;

	/**
	 * 删除标志 0(false)-未删除 1(true)-已删除
	 */
	private boolean delFlg;

	/**
	 * 删除时间
	 */
	private Date delDate;

	/**
	 * 身份证号
	 */
	private String idCardNo;

	/**
	 * 工作量
	 */
	private double workload;

	/**
	 * 工资代号
	 */
	private String payCode;

	private String cause;

	// /**
	// * 所著论文
	// */
	// private Set<Paper> papers;

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public String getProfessionalTitle() {
		return professionalTitle;
	}

	public void setProfessionalTitle(String professionalTitle) {
		this.professionalTitle = professionalTitle;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Date getEnterDate() {
		return enterDate;
	}

	public void setEnterDate(Date enterDate) {
		this.enterDate = enterDate;
	}

	public Teacher getCreater() {
		return creater;
	}

	public void setCreater(Teacher creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Teacher getUpdater() {
		return updater;
	}

	public void setUpdater(Teacher updater) {
		this.updater = updater;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(boolean delFlg) {
		this.delFlg = delFlg;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	private int memberId;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public double getWorkload() {
		return workload;
	}

	public void setWorkload(double workload) {
		this.workload = workload;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	// 用于判断是否需要申请
	private boolean isNewOfWorkLoad = false;

	public String managePersonByProject() throws Exception {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

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
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Person> list = projectService.getPersonByProject(pagination, year,
				innerCode);
		InfoTable iftable = projectService.getInfoByKey("syear");
		request.setAttribute("syear", iftable.getKeyindex());
		request.setAttribute("proStat",
				(projectService.getProject(innerCode)).getProStatus());

		request.setAttribute("persons", list);
		request.setAttribute("nbbh", innerCode);
		request.setAttribute("year", year);

		HttpSession session = request.getSession();
		session.setAttribute("year", year);
		session.setAttribute("nbbh", innerCode);

		request.setAttribute("pagination", pagination);
		String forWorkload = request.getParameter("forWorkload");
		if (forWorkload != null && forWorkload.equals("true")) {
			request.setAttribute("pagiUrl",
					"mPerson.action?method=managePersons&forWorkload=true");
			request.setAttribute("forWorkload", "true");
			return "forWorkload";
		}
		request.setAttribute("pagiUrl", "mPerson.action?method=managePersons");
		return SUCCESS;
	}

	public String managePersonLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Integer> years = projectService.getCurrentPersonYears(innerCode);
		// List<Integer> years = projectService.getPersonYears(innerCode);
		request.setAttribute("years", years);
		request.setAttribute("nbbh", innerCode);
		session.setAttribute("year", "");
		session.setAttribute("nbbh", innerCode);
		String forWorkload = request.getParameter("forWorkload");
		if (forWorkload != null && forWorkload.equals("true")) {
			return "forWorkloadLeft";
		}
		return "PersonFrameLeft";
	}

	public String addPersonIndex() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(innerCode);
		double[] sums = projectService
				.getSumIncomeAndWorkloadByYearAndInnerCode(innerCode,
						String.valueOf(new Date().getYear() + 1900));
		// 计算出最多workload值
		double sumIncome = sums[0];
		double sumWorkload = sums[1];
		request.setAttribute("restWorkload",
				Double.toString(sumIncome - sumWorkload));
		request.setAttribute("project", p);
		return "AddPerson";
	}

	public String updatePersonIndex() throws BusinessException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Person in = projectService.getPersonById(id);
		double[] sums = projectService
				.getSumIncomeAndWorkloadByYearAndInnerCode(in.getProject()
						.getInnerCode(), String.valueOf(in.getYear()));
		// 计算出最多workload值
		double sumIncome = sums[0];
		double sumWorkload = sums[1];
		double oldWorkload = in.getWorkload();
		request.setAttribute("restWorkload",
				Double.toString(sumIncome - sumWorkload + oldWorkload));
		request.setAttribute("person", in);
		return "UpdatePerson";
	}

	public String updatePerson() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有登记成员权限！如有疑问，请联系管理员。");
			return ERROR;
		}

		// 获得原因
		if (cause == null) {
			cause = "无具体原因";
		} else {
			cause = java.net.URLDecoder.decode(cause, "UTF-8");
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Person ps = projectService.getPersonById(id);
		ps.setEnterDate(enterDate);
		ps.setSerialNumber(ps.getSerialNumber());
		ps.setName(name);
		ps.setSex(sex);
		if(birth != null && birth.trim().length() != 0) {
			ps.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse(birth));
		}
		ps.setDiploma(diploma);
		ps.setProfessionalTitle(professionalTitle);
		ps.setRole(role);
		ps.setDegree(degree);
		ps.setOrganization(organization);
		ps.setAssignment(assignment);
		ps.setUpdater(teacher);
		ps.setUpdateDate(new Date());
		ps.setIdCardNo(idCardNo);
		ps.setWorkload(workload);
		ps.setPayCode(payCode);
		if(teacher.getVerity().equals("打开")){
			if (RecordSetting.getRecordSetting() && !isNewOfWorkLoad) {
				ps.setId(0);
				ps.setDelFlg(true);
				ps = projectService.savePerson(ps);
	
				Record rd = new Record();
				rd.setApplyDate(new Date());
				rd.setApplyer(teacher);
				rd.setDoType(ConstData.ApplyForUpdate);
				rd.setIDType(true);
				rd.setNewId("" + ps.getId());
				rd.setOldId("" + id);
				rd.setCause(cause);
				rd.setTableName("Person");
				projectService.saveRecord(rd);
				projectService.lockProject(ps.getProject().getInnerCode());
				request.setAttribute("info", "已发送修改申请，请等待管理员审核！");
			} else {
				projectService.updatePerson(ps);
				request.setAttribute("info", "更新成功！");
			}
		}else {

			projectService.updatePerson(ps);
			request.setAttribute("info", "更新成功！");
		
		}

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Person> list = projectService.getPersonByProject(pagination,
				"all", innerCode);
		request.setAttribute("persons", list);
		request.setAttribute("nbbh", ps.getProject().getInnerCode());
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPerson.action?method=managePersonByProject&innerCode="
						+ innerCode + "&year=all");
		return SUCCESS;
	}

	public String updatePersonWorkloadByAjax() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有登记成员权限！如有疑问，请联系管理员。");
			return ERROR;
		}
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Person ps = projectService.getPersonById(id);
		double[] sums = projectService
				.getSumIncomeAndWorkloadByYearAndInnerCode(ps.getProject()
						.getInnerCode(), String.valueOf(ps.getYear()));
		double sumIncome = sums[0];
		double sumWorkload = sums[1];
		double oldWorkload = ps.getWorkload();
		double newWorkload = workload;
		// 判断项目是否已锁
		String isLocked = ps.getProject().getIsLock();
		if (RecordSetting.getRecordSetting() && isLocked != null
				&& isLocked.equalsIgnoreCase("true") && ps.getWorkload() != 0) {
			try {
				response.getOutputStream()
						.write(("当前操作所在项目已有一项操作正在申请中，请等待通过后再进行此操作！")
								.getBytes("UTF-8"));
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		// 判断工作量是否超过到款之和
		if (sumIncome < sumWorkload - oldWorkload + newWorkload) {
			try {
				response.getOutputStream().write(
						("工作量不能超过："
								+ Double.toString(sumIncome - sumWorkload
										+ oldWorkload) + "元！")
								.getBytes("UTF-8"));
				response.getOutputStream().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		enterDate = ps.getEnterDate();
		serialNumber = ps.getSerialNumber();
		name = ps.getName();
		sex = ps.getSex();
		birth = new SimpleDateFormat("yyyy-MM-dd").format(ps.getBirth());
		diploma = ps.getDiploma();
		professionalTitle = ps.getProfessionalTitle();
		role = ps.getRole();
		degree = ps.getDegree();
		organization = ps.getOrganization();
		assignment = ps.getAssignment();
		idCardNo = ps.getIdCardNo();
		payCode = ps.getPayCode();
		if (ps.getWorkload() == 0)
			isNewOfWorkLoad = true;
		try {
			String result = updatePerson();
			if (result.equals(SUCCESS)) {
				if (RecordSetting.getRecordSetting() && isNewOfWorkLoad)
					response.getOutputStream().print("successNewWorkload");
				else
					response.getOutputStream().print("success");
			} else
				response.getOutputStream().print("fail");
		} catch (Exception e) {
			response.getOutputStream().print("fail");
			throw e;
		}
		response.getOutputStream().flush();
		return null;
	}

	public String checkSerialNumber() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Person> personList = projectService.getPersons(innerCode, "all",
				"");
		for (Person person : personList) {
			//person.getDelFlg();
			if ((person.getSerialNumber() == serialNumber)&&person.getDelFlg()) {
				out.println("1");
				out.close();
				return null;
			}
		}
		out.println("0");
		out.close();
		return null;
	}

	public String addPerson() throws Exception {
		
		System.out.println("测试添加成员1");
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有登记成员权限！如有疑问，请联系管理员。");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(innerCode);
		if (!p.getMemberListFlg()) {
			p.setMemberListFlg(true);
			projectService.update(p);
		}
		
		System.out.println("测试添加成员");
		
		Member mb = projectService.getMemberById(memberId);
		Person ps = new Person();
		ps.setProject(p);
		ps.setYear(Integer.parseInt(year));
		ps.setEnterDate(enterDate);
		// ////person序列号
		// Pagination paginationPersonNum = new Pagination();
		// paginationPersonNum.setPage(1);
		// paginationPersonNum.setSize(1);
		// projectService.getPersonByProject(paginationPersonNum, year,
		// innerCode);
		//
		//
		//
		// ps.setSerialNumber(paginationPersonNum.getResultCount()+1);

		ps.setSerialNumber(serialNumber);
		ps.setName(mb.getName());
		ps.setSex(sex);
		if(birth != null && birth.trim().length() != 0) {
			ps.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse(birth));
		}
		ps.setDiploma(diploma);
		ps.setProfessionalTitle(professionalTitle);
		ps.setRole(role);
		ps.setDegree(degree);
		ps.setOrganization(organization);
		ps.setAssignment(assignment);
		ps.setCreater(teacher);
		ps.setCreateDate(new Date());
		ps.setIdCardNo(idCardNo);
		ps.setWorkload(workload);
		ps.setPayCode(payCode);

		projectService.savePerson(ps);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Person> list = projectService.getPersonByProject(pagination,
				"all", innerCode);
		request.setAttribute("persons", list);
		request.setAttribute("nbbh", innerCode);
		InfoTable iftable = projectService.getInfoByKey("syear");
		request.setAttribute("syear", iftable.getKeyindex());
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPerson.action?method=managePersonByProject&innerCode="
						+ innerCode + "&year=all");
		return SUCCESS;
	}

	public String deletePerson() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		cause = java.net.URLDecoder.decode(cause, "UTF-8");
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		innerCode = projectService.getPersonById(id).getProject()
				.getInnerCode();
		if(teacher.getVerity().equals("打开")){
			if (RecordSetting.getRecordSetting()) {
				Record rd = new Record();
				rd.setApplyDate(new Date());
				rd.setApplyer(teacher);
				rd.setDoType(ConstData.ApplyForDelete);
				rd.setIDType(true);
				rd.setOldId("" + id);
				rd.setCause(cause);
				rd.setTableName("Person");
				projectService.saveRecord(rd);
				projectService.lockProject(innerCode);
				request.setAttribute("info", "已发送删除申请，请等待管理员审核！");
			} else {
				projectService.deletePerson(id);
				request.setAttribute("info", "删除成功！");
			}
		}else{
			projectService.deletePerson(id);
			request.setAttribute("info", "删除成功！");
		}

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Person> list = projectService.getPersonByProject(pagination,
				"all", innerCode);
		request.setAttribute("persons", list);
		request.setAttribute("nbbh", innerCode);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPerson.action?method=managePersonByProject&innerCode="
						+ innerCode + "&year=all");
		return SUCCESS;
	}

	public String showPerson() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Person p = projectService.getPersonById(id);
		request.setAttribute("person", p);
		return "ShowPerson";
	}

	public String managePersons() throws BusinessException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		String _s = request.getParameter("s");
		String s = null;
		String nbbh = null;
		if (_s != null) {
			s = java.net.URLDecoder.decode(_s, "UTF-8");
			nbbh = (String) session.getAttribute("nbbh");
			if (nbbh != null) {
				s = s + " and project.innerCode= '" + nbbh + "' ";
				request.setAttribute("nbbh", nbbh);
			}
		} else {
			nbbh = (String) session.getAttribute("nbbh");
			if (nbbh != null) {
				s = " project.innerCode= '" + nbbh + "' ";
				request.setAttribute("nbbh", nbbh);
			}
		}

		int size = ConstData.projectSize;
		int page;
		try {
			page = Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			predix = "";
		}
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Person> list = projectService.getPersons(predix, pagination, year,
				s);
		request.setAttribute("pagination", pagination);

		String forWorkload = request.getParameter("forWorkload");
		if (forWorkload != null && forWorkload.equals("true")) {
			if (s != null)
				request.setAttribute("pagiUrl",
						"mPerson.action?method=managePersons&forWorkload=true&s="
								+ s);
			else
				request.setAttribute("pagiUrl",
						"mPerson.action?method=managePersons&forWorkload=true");
		} else {
			if (s != null)
				request.setAttribute("pagiUrl",
						"mPerson.action?method=managePersons&s=" + s);
			else
				request.setAttribute("pagiUrl",
						"mPerson.action?method=managePersons");
		}
		request.setAttribute("persons", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		if (forWorkload != null && forWorkload.equals("true") && (nbbh == null)) {
			request.setAttribute("forWorkload", "true");
			return "forWorkloadQuery";
		} else if (forWorkload != null && forWorkload.equals("true")
				&& (nbbh != null)) {
			request.setAttribute("forWorkload", "true");
			return "forWorkload";
		}
		return SUCCESS;
	}

	public String searchPerson() throws BusinessException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		String _s = request.getParameter("s");

		String forWorkload = null;
		if (_s.indexOf(":") != -1) {
			String parameters[] = _s.split(":");
			_s = parameters[0];
			forWorkload = parameters[1];
			System.out.println(_s + ":" + forWorkload);
		}

		String s = null;
		if (_s != null) {
			s = java.net.URLDecoder.decode(_s, "UTF-8");
			String nbbh = (String) session.getAttribute("nbbh");
			if (nbbh != null) {
				s = s + " and project.innerCode= '" + nbbh + "' ";
				request.setAttribute("nbbh", nbbh);
			}
		} else {
			String nbbh = (String) session.getAttribute("nbbh");
			if (nbbh != null) {
				s = " project.innerCode= '" + nbbh + "' ";
				request.setAttribute("nbbh", nbbh);
			}
		}

		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			predix = "";
		}
		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);

		List<Person> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getPersons(predix, pagination, "all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getPersons(predix, pagination, preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mPerson.action?method=managePersons&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mPerson.action?method=managePersons");
		request.setAttribute("persons", list);
		if (s != null)
			request.setAttribute("s", s);

		if (forWorkload != null && forWorkload.equals("true")) {
			request.setAttribute("forWorkload", "true");
			if (s != null)
				request.setAttribute("pagiUrl",
						"mPerson.action?method=managePersons&forWorkload=true&s="
								+ s);
			else
				request.setAttribute("pagiUrl",
						"mPerson.action?method=managePersons&forWorkload=true");
		}
		return SUCCESS;
	}

	/*
	 * public String exportToExcel() throws BusinessException,
	 * UnsupportedEncodingException, DAOException { Teacher teacher = (Teacher)
	 * request.getSession().getAttribute("teacher"); if (teacher == null) {
	 * request.setAttribute("info", "您没有登录，或登录已超时，请重试！"); return ERROR; }
	 * 
	 * String _s = request.getParameter("s"); String s = null; if (_s != null) s
	 * = java.net.URLDecoder.decode(_s, "UTF-8"); Org org = teacher.getOrg();
	 * String predix = new ConstData().innerCodePrefix.get(org.getName()); if
	 * (predix == null) { predix = ""; } ProjectService projectService =
	 * (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
	 * List<Person> list; if(innerCode==null) list =
	 * projectService.getPersons(predix, year, s); else list =
	 * projectService.getPersonByProject(year, innerCode, s); List<FieldBean>
	 * incbs = FieldsControl.getProjectFields("Person"); HSSFWorkbook wb = new
	 * HSSFWorkbook(); HSSFSheet sheet = wb.createSheet("sheet1"); HSSFCellStyle
	 * style = wb.createCellStyle(); HSSFFont font = wb.createFont();
	 * font.setFontName("黑体"); font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	 * style.setAlignment(HSSFCellStyle.ALIGN_CENTER); style.setFont(font);
	 * 
	 * HSSFRow row; HSSFCell cell; int rowNum = 0; int listSize = 0;
	 * if(list!=null) listSize = list.size(); row = sheet.createRow((short)
	 * rowNum++); cell = row.createCell((short) 0); cell.setEncoding((short) 1);
	 * cell.setCellValue("科研管理系统成员导出"); cell.setCellStyle(style);
	 * sheet.addMergedRegion(new Region(rowNum - 1, (short) 0, rowNum - 1,
	 * (short)(incbs.size()-1))); int j = 0; row = sheet.createRow((short)
	 * rowNum++); for (FieldBean incb : incbs) { cell = row.createCell((short)
	 * j++); cell.setEncoding((short) 1); cell.setCellValue(incb.getName()); }
	 * 
	 * for (int i = 0; i < listSize; i++) { //person表共22个字段,显示21个 Person ps =
	 * list.get(i); row = sheet.createRow((short) rowNum++); j = 0;
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getId());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getSerialNumber());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getProject().getInnerCode());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getYear());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getName());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getSex());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getIdCardNo());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getPayCode());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getBirth()==null?"":ps.getBirth().toString());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getDiploma());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getProfessionalTitle());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getAssignment());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getOrganization());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue
	 * (ps.getEnterDate()==null?"":ps.getEnterDate().toString());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getRole());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getDegree());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getCreater().getName());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getWorkload());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue
	 * (ps.getCreateDate()==null?"":ps.getCreateDate().toString());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getUpdater()==null?"":ps.getUpdater().getName());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue
	 * (ps.getUpdateDate()==null?"":ps.getUpdateDate().toString());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getDelFlg());
	 * 
	 * cell = row.createCell((short) j++); cell.setEncoding((short) 1);
	 * cell.setCellValue(ps.getDelDate()==null?"":ps.getDelDate().toString()); }
	 * response.setContentType("application/msexcel");
	 * response.setHeader("Content-disposition",
	 * "inline;filename=PersonExport.xls"); OutputStream out; try { out =
	 * response.getOutputStream(); wb.write(out); out.flush(); out.close(); }
	 * catch (IOException e) { e.printStackTrace(); } return null; }
	 */

	public String exportToExcel() throws BusinessException,
			UnsupportedEncodingException, DAOException {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		System.out.println("helloh");
		String _s = request.getParameter("s");
		String s = null;
		if (_s != null)
			s = java.net.URLDecoder.decode(_s, "UTF-8");
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			predix = "";
		}
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Person> list;
		if (innerCode == null)
			list = projectService.getPersons(predix, year, s);
		else
			list = projectService.getPersonByProject(year, innerCode, s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("Person");

		// 统计页面上显示的列
		Map<String, String> fbMap = new HashMap<String, String>();
		for (FieldBean fb : incbs) {
			if (fb.getDisplay()) {
				fbMap.put(fb.getCode(), "true");
			}
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		HSSFRow row;
		HSSFCell cell;
		int rowNum = 0;
		int listSize = 0;
		if (list != null)
			listSize = list.size();
		row = sheet.createRow((short) rowNum++);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("科研管理系统成员导出");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(rowNum - 1, (short) 0, rowNum - 1,
				(short) (fbMap.size() - 1)));
		int j = 0;
		row = sheet.createRow((short) rowNum++);
		for (FieldBean incb : incbs) {

			if (!incb.getDisplay()) {
				continue;
			}

			cell = row.createCell((short) j++);
			cell.setEncoding((short) 1);
			cell.setCellValue(incb.getName());
		}

		for (int i = 0; i < listSize; i++) {
			// person表共22个字段,显示21个
			// 以上修改为：参照PersonFields.xml文件
			Person ps = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("id")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getId());
			}

			if (fbMap.containsKey("serialNumber")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getSerialNumber());
			}

			if (fbMap.containsKey("project.innerCode")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getInnerCode());
			}

			if (fbMap.containsKey("name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getName());
			}

			// 项目相关
			if (fbMap.containsKey("project.proName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getProName());
			}

			if (fbMap.containsKey("year")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getYear());
			}
			// cell.setCellValue(ps.getProject().getProLevel());

			// 项目级别
			if (fbMap.containsKey("project.proLevel")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getProLevel());
			}// ps.getProject().getProCharactor());

			// 项目性质
			if (fbMap.containsKey("project.proCharactor")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getProCharactor());
			}// ps.getProject().getProStatus());

			// 项目状态
			if (fbMap.containsKey("project.proStatus")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getProStatus());
			}

			// 合同编号
			if (fbMap.containsKey("project.contractId")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getContractId());
			}

			// 项目来源
			if (fbMap.containsKey("project.proSource")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getProSource());
			}

			// 项目类别
			if (fbMap.containsKey("project.proClass")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getProClass());
			}

			// 项目组长
			if (fbMap.containsKey("project.proLeader")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getProLeader());
			}

			// 涉密级别
			if (fbMap.containsKey("project.classifiedLevel")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getClassifiedLevel());
			}

			// ps.getProject().getOverFormatDate()==null?"":ps.getProject().getOverFormatDate().toString()
			// 完成日期
			if (fbMap.containsKey("project.overFormatDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getOverFormatDate() == null ? ""
						: ps.getProject().getOverFormatDate().toString());
			}

			// ps.getProject().getOrganization());
			// 所属单位
			if (fbMap.containsKey("project.organization")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getOrganization());
			}

			// ps.getProject().getCooperateOrg());
			// 合作单位
			if (fbMap.containsKey("project.cooperateOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getCooperateOrg());
			}

			// ps.getProject().getStartDate()==null?"":ps.getProject().getStartDate().toString());
			// 开始日期
			if (fbMap.containsKey("project.startDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getStartDate() == null ? ""
						: ps.getProject().getStartDate().toString());
			}

			// ps.getProject().getEndDate()==null?"":ps.getProject().getEndDate().toString());
			// 结束日期
			if (fbMap.containsKey("project.endDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getEndDate() == null ? ""
						: ps.getProject().getEndDate().toString());
			}

			// ps.getProject().getRegDate()==null?"":ps.getProject().getRegDate().toString());
			// 等级日期
			if (fbMap.containsKey("project.regDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getRegDate() == null ? ""
						: ps.getProject().getRegDate().toString());
			}

			// ps.getProject().getOverFormat());
			// 完成形式
			if (fbMap.containsKey("project.overFormat")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getOverFormat());
			}

			// ps.getProject().getDepartment());
			// 所属科室
			if (fbMap.containsKey("project.department")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getDepartment());
			}

			// ps.getProject().getContractFounds());
			// 合同经费
			if (fbMap.containsKey("project.contractFounds")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getContractFounds());
			}

			// ps.getProject().getYearArriveMoney());
			// 当年累计到款
			if (fbMap.containsKey("project.yearArriveMoney")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getYearArriveMoney());
			}

			// ps.getProject().getTotalArriveMoney());
			// 累计到款
			if (fbMap.containsKey("project.totalArriveMoney")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getTotalArriveMoney());
			}

			// ps.getProject().getYearGrant());
			// 当年累计拨款
			if (fbMap.containsKey("project.yearGrant")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getYearGrant());
			}

			// ps.getProject().getTotalGrant());
			// 累计拨款
			if (fbMap.containsKey("project.totalGrant")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProject().getTotalGrant());
			}

			// ps.getYear());
			if (fbMap.containsKey("sex")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getSex());
			}

			if (fbMap.containsKey("birth")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getBirth() == null ? "" : ps.getBirth()
						.toString());
			}

			if (fbMap.containsKey("idCardNo")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getIdCardNo());
			}

			if (fbMap.containsKey("payCode")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getPayCode());
			}

			if (fbMap.containsKey("diploma")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getDiploma());
			}

			if (fbMap.containsKey("professionalTitle")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getProfessionalTitle());
			}

			if (fbMap.containsKey("assignment")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getAssignment());
			}

			if (fbMap.containsKey("organization")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getOrganization());
			}

			if (fbMap.containsKey("enterDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getEnterDate() == null ? "" : ps
						.getEnterDate().toString());
			}

			if (fbMap.containsKey("role")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getRole());
			}

			if (fbMap.containsKey("degree")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getDegree());
			}

			if (fbMap.containsKey("creater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getCreater().getName());
			}

			if (fbMap.containsKey("workload")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getWorkload());
			}

			if (fbMap.containsKey("createDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getCreateDate() == null ? "" : ps
						.getCreateDate().toString());
			}

			if (fbMap.containsKey("updater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getUpdater() == null ? "" : ps
						.getUpdater().getName());
			}

			if (fbMap.containsKey("updateDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getUpdateDate() == null ? "" : ps
						.getUpdateDate().toString());
			}

			if (fbMap.containsKey("delFlg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getDelFlg());
			}

			if (fbMap.containsKey("delDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ps.getDelDate() == null ? "" : ps
						.getDelDate().toString());
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=PersonExport.xls");
		OutputStream out;
		try {
			out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String personControl() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		int c = projectService.personControl();
		request.setAttribute("pagePath", "personAuto.html");
		if (c >= 0)
			request.setAttribute("info", "成员过渡操作成功！自动登记了" + c + "个成员");
		else
			request.setAttribute("info", "成员过渡操作失败！");
		return "info";
	}

	public String searchPersonSpl() throws BusinessException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		String _name = request.getParameter("sqlname");
		String name = java.net.URLDecoder.decode(_name, "UTF-8");
		String _value = request.getParameter("sqlvalue");
		String value = java.net.URLDecoder.decode(_value, "UTF-8");
		int scon = Integer.parseInt(request.getParameter("sqlcon"));
		String s = "";
		if (scon == 5)
			s = name + " like '%" + value + "%'";
		else
			s = name + ConstData.sqlsp[scon] + "'" + value + "'";

		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			predix = "";
		}

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Person> list = /*
							 * projectService.getProjects(teacher.getOrg().getName
							 * (), pagination, null, s);
							 */projectService.getPersons(predix, pagination,
				"all", s);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPerson.action?method=managePersons&s=" + s);
		request.setAttribute("persons", list);
		request.setAttribute("s", s);
		return SUCCESS;
	}
}