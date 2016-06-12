package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.icitic.ldap.RoleDAO;

import cn.cust.kyc.bo.ManagerService;
import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ManagerServiceImpl;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.FieldsControl;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.ProjectFieldBean;
import cn.cust.kyc.util.ProjectFieldsControl;
import cn.cust.kyc.util.RecordSetting;
import cn.cust.kyc.vo.Achievement;
import cn.cust.kyc.vo.Code;
import cn.cust.kyc.vo.InfoTable;
import cn.cust.kyc.vo.Member;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Person;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Record;
import cn.cust.kyc.vo.Role;
import cn.cust.kyc.vo.Teacher;

import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageProjectAction extends ActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3795257439956003527L;
	

	private Role id;

	//private static final Role id = null;
	private Integer status;

	/**
	 * 内部编号
	 */
	private String innerCode;

	/**
	 * 项目代号
	 */
	private String code;

	/**
	 * 项目名称
	 */
	private String proName;

	/**
	 * 合同编号
	 */
	private String contractId;

	/**
	 * 项目来源
	 */
	private String proSource;

	/**
	 * 项目类别
	 */
	private String proClass;

	/**
	 * 项目级别
	 */
	private String proLevel;

	/**
	 * 项目组长
	 */
	private String proLeader;

	/**
	 * 项目组长联系方式
	 */
	private String proLeaderTel;

	/**
	 * 是否成员登记
	 */
	private boolean memberListFlg;

	/**
	 * 所属单位
	 */
	private String organization;

	/**
	 * 合作单位
	 */
	private String cooperateOrg;

	/**
	 * 涉密级别
	 */
	private String classifiedLevel;

	/**
	 * 项目性质
	 */
	private String proCharactor;

	/**
	 * 项目状态
	 */
	private String proStatus;

	/**
	 * 完成日期
	 */
	private Date overFormatDate;

	/**
	 * 开始日期
	 */
	private Date startDate;

	/**
	 * 结束日期
	 */
	private Date endDate;

	/**
	 * 登记日期
	 */
	private Date regDate;

	/**
	 * 当年累计到款
	 */
	private double yearArriveMoney;

	/**
	 * 全周期到款额
	 */
	private double totalArriveMoney;

	/**
	 * 是否转化应用
	 */
	private boolean toUseFlg;

	/**
	 * 转化应用部门
	 */
	private String toUseUnit;

	/**
	 * 当年累计拨款
	 */
	private double yearGrant;

	/**
	 * 全周期累计拨款
	 */
	private double totalGrant;

	/**
	 * 合同经费
	 */
	private double contractFounds;

	/**
	 * 风险金
	 */
	private double riskFee;

	/**
	 * 回拨风险金
	 */
	private double riskFeeBack;

	/**
	 * 风险金回拨标记
	 */
	private boolean riskCallbackFlg;

	/**
	 * 涉密时间
	 */
	private Date classifyDate;

	/**
	 * 解密时间
	 */
	private Date decipherDate;

	/**
	 * 结题日期
	 */
	private Date overDate;

	/**
	 * 结题部门
	 */
	private String overDepartment;

	/**
	 * 结题结论
	 */
	private String overConclusion;

	/**
	 * 验收日期
	 */
	private Date acceptDate;

	/**
	 * 验收部门
	 */
	private String acceptDepartment;

	/**
	 * 验收结论
	 */
	private String acceptConclusion;

	/**
	 * 鉴定日期
	 */
	private Date evalueDate;

	/**
	 * 鉴定部门
	 */
	private String evalueDepartment;

	/**
	 * 鉴定结论
	 */
	private String evalueConclusion;

	/**
	 * 完成形式
	 */
	private String overFormat;

	/**
	 * 所属科室
	 */
	private String department;

	/**
	 * 经手人
	 */
	private String creater;

	/**
	 * 登记日期
	 */
	private Date createDate;

	/**
	 * 修改人
	 */
	private String updater;

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
	 * 组长ID
	 * 
	 * @return
	 */
	private int memberId;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 操作原因
	 * 
	 * @return
	 */
	private String cause;
	/**
	 * 完成形式
	 */
	private boolean finishType;

	/**
	 * 完成时间
	 */
	private Date finishDate;

	/**
	 * 完成结论
	 */
	private String finishConclusion;

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getProSource() {
		return proSource;
	}

	public void setProSource(String proSource) {
		this.proSource = proSource;
	}

	public String getProClass() {
		return proClass;
	}

	public void setProClass(String proClass) {
		this.proClass = proClass;
	}

	public String getProLevel() {
		return proLevel;
	}

	public void setProLevel(String proLevel) {
		this.proLevel = proLevel;
	}

	public String getProLeader() {
		return proLeader;
	}

	public void setProLeader(String proLeader) {
		this.proLeader = proLeader;
	}

	public String getProLeaderTel() {
		return proLeaderTel;
	}

	public void setProLeaderTel(String proLeaderTel) {
		this.proLeaderTel = proLeaderTel;
	}

	public boolean isMemberListFlg() {
		return memberListFlg;
	}

	public void setMemberListFlg(boolean memberListFlg) {
		this.memberListFlg = memberListFlg;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getCooperateOrg() {
		return cooperateOrg;
	}

	public void setCooperateOrg(String cooperateOrg) {
		this.cooperateOrg = cooperateOrg;
	}

	public String getClassifiedLevel() {
		return classifiedLevel;
	}

	public void setClassifiedLevel(String classifiedLevel) {
		this.classifiedLevel = classifiedLevel;
	}

	public String getProCharactor() {
		return proCharactor;
	}

	public void setProCharactor(String proCharactor) {
		this.proCharactor = proCharactor;
	}

	public String getProStatus() {
		return proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	public Date getOverFormatDate() {
		return overFormatDate;
	}

	public void setOverFormatDate(Date overFormatDate) {
		this.overFormatDate = overFormatDate;
	}

	public Date getOverDate() {
		return overDate;
	}

	public void setOverDate(Date overDate) {
		this.overDate = overDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public double getYearArriveMoney() {
		return yearArriveMoney;
	}

	public void setYearArriveMoney(double yearArriveMoney) {
		this.yearArriveMoney = yearArriveMoney;
	}

	public double getTotalArriveMoney() {
		return totalArriveMoney;
	}

	public void setTotalArriveMoney(double totalArriveMoney) {
		this.totalArriveMoney = totalArriveMoney;
	}

	public boolean isToUseFlg() {
		return toUseFlg;
	}

	public void setToUseFlg(boolean toUseFlg) {
		this.toUseFlg = toUseFlg;
	}

	public String getToUseUnit() {
		return toUseUnit;
	}

	public void setToUseUnit(String toUseUnit) {
		this.toUseUnit = toUseUnit;
	}

	public double getYearGrant() {
		return yearGrant;
	}

	public void setYearGrant(double yearGrant) {
		this.yearGrant = yearGrant;
	}

	public double getTotalGrant() {
		return totalGrant;
	}

	public void setTotalGrant(double totalGrant) {
		this.totalGrant = totalGrant;
	}

	public double getContractFounds() {
		return contractFounds;
	}

	public void setContractFounds(double contractFounds) {
		this.contractFounds = contractFounds;
	}

	public double getRiskFee() {
		return riskFee;
	}

	public void setRiskFee(double riskFee) {
		this.riskFee = riskFee;
	}

	public double getRiskFeeBack() {
		return riskFeeBack;
	}

	public void setRiskFeeBack(double riskFeeBack) {
		this.riskFeeBack = riskFeeBack;
	}

	public boolean getRiskCallbackFlg() {
		return riskCallbackFlg;
	}

	public void setRiskCallbackFlg(boolean riskCallbackFlg) {
		this.riskCallbackFlg = riskCallbackFlg;
	}

	public Date getClassifyDate() {
		return classifyDate;
	}

	public void setClassifyDate(Date classifyDate) {
		this.classifyDate = classifyDate;
	}

	public Date getDecipherDate() {
		return decipherDate;
	}

	public void setDecipherDate(Date decipherDate) {
		this.decipherDate = decipherDate;
	}

	public String getOverDepartment() {
		return overDepartment;
	}

	public void setOverDepartment(String overDepartment) {
		this.overDepartment = overDepartment;
	}

	public String getOverConclusion() {
		return overConclusion;
	}

	public void setOverConclusion(String overConclusion) {
		this.overConclusion = overConclusion;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getAcceptDepartment() {
		return acceptDepartment;
	}

	public void setAcceptDepartment(String acceptDepartment) {
		this.acceptDepartment = acceptDepartment;
	}

	public String getAcceptConclusion() {
		return acceptConclusion;
	}

	public void setAcceptConclusion(String acceptConclusion) {
		this.acceptConclusion = acceptConclusion;
	}

	public Date getEvalueDate() {
		return evalueDate;
	}

	public void setEvalueDate(Date evalueDate) {
		this.evalueDate = evalueDate;
	}

	public String getEvalueDepartment() {
		return evalueDepartment;
	}

	public void setEvalueDepartment(String evalueDepartment) {
		this.evalueDepartment = evalueDepartment;
	}

	public String getEvalueConclusion() {
		return evalueConclusion;
	}

	public void setEvalueConclusion(String evalueConclusion) {
		this.evalueConclusion = evalueConclusion;
	}

	public String getOverFormat() {
		return overFormat;
	}

	public void setOverFormat(String overFormat) {
		this.overFormat = overFormat;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public boolean isDelFlg() {
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 项目显示字段信息（checkbox的value）
	private List<Integer> sfield;

	public List<Integer> getSfield() {
		return sfield;
	}

	public void setSfield(List<Integer> sfield) {
		this.sfield = sfield;
	}

	// 项目按年分类（开始日期）
	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String manageProjectLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		// 年度:被注释部分信息为原来信息，getStartYears返回为所有年度；getCurrentYears返回为从开始到当年年度。
		// List<Integer> years = projectService.getStartYears();
		List<Integer> years = projectService.getCurrentYears();
		request.setAttribute("years", years);
		// 状态
		List<Code> state = projectService.getCodeByType("项目状态");
		request.setAttribute("state", state);
		// 类别
		List<Code> group = projectService.getCodeByType("项目类别");

		// 不同身份看到的项目类别不同

		List<Code> groupSelect = new ArrayList<Code>();
		for (Code code : group) {
			if (teacher.getOrg().getName().equals(code.getInfo())) {
				groupSelect.add(code);
			}
		}

		InfoTable iftable = projectService.getInfoByKey("syear");
		request.setAttribute("syear", iftable.getKeyindex());

		session.setAttribute("search", "");
		session.setAttribute("year", "");
		request.setAttribute("group", groupSelect);
		return "ProjectFrameLeft";
	}

	public String manageProjectIndex() throws Exception {
		System.out.println("显示项目数据");
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		boolean c = false;
		if (request.getParameter("c") != null)
			c = Boolean.parseBoolean(request.getParameter("c"));
		String _s = request.getParameter("s");
		String s = null;
		if (_s != null)
			s = java.net.URLDecoder.decode(_s, "UTF-8");

		System.out.println("s1：" + s);

		// 查询生成导航树
		if ((s == null) && (year != null)) {
			if (proStatus != null) {
				proStatus = new String(proStatus.getBytes("iso-8859-1"),
						"UTF-8");
				s = " proStatus = '" + proStatus + "' ";
			}
			if (proClass != null) {
				proClass = new String(proClass.getBytes("iso-8859-1"), "UTF-8");
				s = s + " and proClass = '" + proClass + "' ";
			}
			session.setAttribute("search", s);
			session.setAttribute("year", year);
		}

		System.out.println("s2：" + s);

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
		List<Project> list = projectService.getProjects(teacher.getOrg()
				.getName(), pagination, year, s);

		// 设置同步年份信息，syear
		InfoTable iftable = projectService.getInfoByKey("syear");
		request.setAttribute("syear", iftable.getKeyindex());

		request.setAttribute("pagination", pagination);
		String pagiUrl = "mProject.action?method=manageProjectIndex";
		if (s != null)
			pagiUrl += "&s=" + s;
		if (c)
			pagiUrl += "&c=" + c;
		request.setAttribute("c", c);
		request.setAttribute("pagiUrl", pagiUrl);
		request.setAttribute("currentProjects", list);
		request.setAttribute("year", year);
		if (s != null)
			request.setAttribute("s", s);
		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String addProject() throws Exception {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		// 生成主键（内部编号）
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有登记项目权限！如有疑问，请联系管理员。");
			return ERROR;
		}
		Date date = new Date();
		int now = date.getYear() + 1900;
		innerCode = predix + "XM-" + now + "-";
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		String lastestKey = projectService.getMaxProjectKey(innerCode);
		if (lastestKey != null) {
			String num = lastestKey.substring(15);
			innerCode = innerCode
					+ new Integer(Integer.parseInt(num) + 1001).toString()
							.substring(1);
		} else
			innerCode = innerCode + "001";

		overFormat = overFormat.replaceAll(" ", "");
		if (overFormat.length() > 0
				&& overFormat.charAt(overFormat.length() - 1) == ',')
			overFormat = overFormat.substring(0, overFormat.length() - 1);
		Project p = new Project();
		p.setInnerCode(innerCode);
		p.setCode(code);
		p.setProName(proName);
		p.setDnnd((new Integer(now)).toString());
		p.setContractId(contractId);
		p.setProSource(proSource);
		p.setProClass(proClass);
		p.setProLevel(proLevel);
		p.setProLeader(proLeader);
		p.setProLeaderTel(proLeaderTel);
		p.setMemberListFlg(memberListFlg);
		p.setOrganization(organization);
		p.setCooperateOrg(cooperateOrg);
		p.setClassifiedLevel(classifiedLevel);
		p.setProCharactor(proCharactor);
		p.setProStatus(proStatus);
		if ("完成".equals(proStatus))
			p.setOverFormatDate(new Date());
		else
			p.setOverFormatDate(null);
		p.setStartDate(startDate);
		p.setEndDate(endDate);
		p.setRegDate(regDate);
		p.setYearArriveMoney(yearArriveMoney);
		p.setTotalArriveMoney(totalArriveMoney);
		p.setToUseFlg(toUseFlg);
		p.setToUseUnit(toUseUnit);
		p.setYearGrant(yearGrant);
		p.setTotalGrant(totalGrant);
		p.setContractFounds(contractFounds);
		p.setRiskFee(riskFee);
		p.setRiskFeeBack(riskFeeBack);
		p.setRiskCallbackFlg(riskCallbackFlg);
		p.setClassifyDate(classifyDate);
		p.setDecipherDate(decipherDate);
		System.out.println("完成形式：" + finishType);
		if (finishType) {
			// 结题
			p.setOverDate(finishDate);
			p.setOverConclusion(finishConclusion);
			p.setOverFormat("结题");
			/*
			 * p.setOverDate(overDate); p.setOverDepartment(overDepartment);
			 * p.setOverConclusion(overConclusion);
			 */
		} else if ((!finishType)
				&& ((finishConclusion.trim()).length() != 0 || finishDate != null)) {
			// 验收
			p.setOverFormat("验收");
			p.setAcceptDate(finishDate);
			p.setAcceptConclusion(finishConclusion);
			/*
			 * p.setAcceptDate(acceptDate);
			 * p.setAcceptDepartment(acceptDepartment);
			 * p.setAcceptConclusion(acceptConclusion);
			 */
		}
		p.setEvalueDate(evalueDate);
		p.setEvalueDepartment(evalueDepartment);
		p.setEvalueConclusion(evalueConclusion);

		// p.setOverFormat(overFormat);
		p.setDepartment(department);
		p.setCreater(teacher);
		p.setCreateDate(new Date());
		p.setUpdater(null);
		p.setUpdateDate(null);
		p.setDelFlg(false);
		p.setDelDate(null);
		p.setRemark(remark);
		boolean b = projectService.saveProject(p);

		// 新建的项目自动加入组长
		try {
			Member mb = projectService.getMemberById(memberId);
			Person ps = new Person();
			ps.setProject(p);
			ps.setYear(startDate.getYear() + 1900);
			ps.setEnterDate(new Date());
			ps.setSerialNumber(1);
			ps.setName(mb.getName());
			ps.setSex(mb.getSex());
			ps.setBirth(mb.getBirth());
			ps.setDiploma(mb.getDiploma());
			ps.setProfessionalTitle(mb.getProfessionalTitle());
			ps.setRole("组长");
			ps.setDegree(mb.getDegree());
			ps.setOrganization(mb.getOrganization());
			ps.setAssignment("");
			ps.setCreater(teacher);
			ps.setCreateDate(new Date());
			ps.setIdCardNo(mb.getIdCardNo());
			ps.setWorkload(0.0);
			ps.setPayCode(mb.getPayCode());
			projectService.savePerson(ps);
		} catch (Exception e) {
			System.out
					.println("OPEN NEW PROJECT: No member specificated as A new Project Leader OR an error occurs!");
		}

		if (b && finishType) {
			// 结题

			Achievement acm = new Achievement();
			acm.setAchieveMan(proLeader);
			acm.setInnerId(p.getInnerCode());
			acm.setAchieveName(proName);
			acm.setItsOrg(organization);
			acm.setIsApplay(toUseFlg);
			acm.setApplayOrg(toUseUnit);
			acm.setCreater(teacher);
			acm.setCreateDate(new Date());
			acm.setConcludeDate(finishDate);
			acm.setConcludeLevel(finishConclusion);
			acm.setConcludeOrg(overDepartment);
			acm.setType("结题");
			projectService.saveAchievement(acm);
			// 结题
		} else if ((!finishType)
				&& ((finishConclusion.trim()).length() != 0 || finishDate != null)) {
			// 验收

			System.out.println("哈哈……验收啦！");
			Achievement acm = new Achievement();
			acm.setAchieveMan(proLeader);
			acm.setInnerId(p.getInnerCode());

			acm.setAchieveName(proName);
			acm.setItsOrg(organization);
			acm.setIsApplay(toUseFlg);
			acm.setApplayOrg(toUseUnit);
			acm.setCreater(teacher);
			acm.setCreateDate(new Date());
			acm.setConcludeDate(finishDate);
			acm.setConcludeLevel(finishConclusion);
			acm.setConcludeOrg(acceptDepartment);
			acm.setType("验收");
			projectService.saveAchievement(acm);
			// 验收
		}
		/*
		 * //生成完成信息 String of[] = overFormat.split(",");
		 * if(b&&!"".equals(of[0])){ for(String s : of){ Achievement acm = new
		 * Achievement(); acm.setAchieveMan(proLeader); //clg修改，2012.2.20
		 * //acm.setProject(p); acm.setInnerId(p.getInnerCode());
		 * 
		 * acm.setAchieveName(proName); acm.setItsOrg(organization);
		 * acm.setIsApplay(toUseFlg); acm.setApplayOrg(toUseUnit);
		 * acm.setCreater(teacher); acm.setCreateDate(new Date());
		 * if(s.equals("结题")){ acm.setConcludeDate(overDate);
		 * acm.setConcludeLevel(overConclusion);
		 * acm.setConcludeOrg(overDepartment); acm.setType(s);
		 * projectService.saveAchievement(acm); }else if(s.equals("鉴定")){
		 * acm.setConcludeDate(evalueDate);
		 * acm.setConcludeLevel(evalueConclusion);
		 * acm.setConcludeOrg(evalueDepartment); acm.setType(s);
		 * projectService.saveAchievement(acm); }else if(s.equals("验收")){
		 * acm.setConcludeDate(acceptDate);
		 * acm.setConcludeLevel(acceptConclusion);
		 * acm.setConcludeOrg(acceptDepartment); acm.setType(s);
		 * projectService.saveAchievement(acm); } } }
		 */
		if (b) {
			return manageProjectIndex();
		} else {
			request.setAttribute("info", "对不起，登记项目出错，请重试！");
			return ERROR;
		}
	}

	public String deleteProject() throws Exception {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		// 获得原因
		if (cause == null || cause.length() == 0) {
			cause = "无具体原因";
		} else {
			cause = java.net.URLDecoder.decode(cause, "UTF-8");
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		
		
		 if(teacher.getVerity().equals("打开")){
		// 删除，根据设置判断是否提交申请
			if (RecordSetting.getRecordSetting()) {
				Record rd = new Record();
				rd.setApplyDate(new Date());
				rd.setApplyer(teacher);
				rd.setDoType(ConstData.ApplyForDelete);
				rd.setIDType(false);
				rd.setCause(cause);
				rd.setOldId(innerCode);
				rd.setTableName("Project");
				projectService.saveRecord(rd);
				projectService.lockProject(innerCode);
				request.setAttribute("pagePath", "page/project/ProjectFrame.jsp");
				request.setAttribute("info", "已提交删除项目申请，请等待管理员审核！");
				return "info";
	
			} else {
				if (projectService.deteteProject(innerCode)) {
					request.setAttribute("pagePath",
							"page/project/ProjectFrame.jsp");
					request.setAttribute("info", "删除项目成功！");
					return "info";
				} else {
					request.setAttribute("info", "对不起，删除项目出错，请重试！");
					return ERROR;
				}
			}
		 } else {
				if (projectService.deteteProject(innerCode)) {
					request.setAttribute("pagePath",
							"page/project/ProjectFrame.jsp");
					request.setAttribute("do", "delete");
					request.setAttribute("info", "删除项目成功！");
					return "info";
				} else {
					request.setAttribute("info", "对不起，删除项目出错，请重试！");
					return ERROR;
				}
			}
		}
	
		

	public String updateProject() throws Exception {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		request.setAttribute("cause", cause);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(innerCode);
		if (p == null) {
			request.setAttribute("info", "提取项目出错！请重试！");
			return ERROR;
		}
		request.setAttribute("project", p);
		return "updateProject";
	}

	public String doUpdateProject() throws Exception {
		System.out.println("修改项目 ！");
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有登记项目权限！如有疑问，请联系管理员。");
			return ERROR;
		}

		if (overFormat != null) {
			overFormat = overFormat.replaceAll(", ", ",");
		}
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(innerCode);
		p.setCode(code);
		p.setProName(proName);
		p.setContractId(contractId);
		p.setProSource(proSource);
		p.setProClass(proClass);
		p.setProLevel(proLevel);
		p.setProLeader(proLeader);
		p.setProLeaderTel(proLeaderTel);
		System.out.println("memberListFlg:" + memberListFlg);
		// p.setMemberListFlg(memberListFlg);
		p.setOrganization(organization);
		p.setCooperateOrg(cooperateOrg);
		p.setClassifiedLevel(classifiedLevel);
		p.setProCharactor(proCharactor);
		p.setProStatus(proStatus);

		// 完成时间
		// p.setOverFormatDate(overFormatDate);
		if ("完成".equals(proStatus))
			p.setOverFormatDate(new Date());
		else
			p.setOverFormatDate(null);
		p.setStartDate(startDate);
		p.setEndDate(endDate);
		p.setRegDate(regDate);
		p.setYearArriveMoney(yearArriveMoney);
		p.setTotalArriveMoney(totalArriveMoney);
		p.setToUseFlg(toUseFlg);
		p.setToUseUnit(toUseUnit);
		p.setYearGrant(yearGrant);
		p.setTotalGrant(totalGrant);
		p.setContractFounds(contractFounds);
		p.setRiskFee(riskFee);
		p.setRiskFeeBack(riskFeeBack);
		p.setRiskCallbackFlg(riskCallbackFlg);
		p.setClassifyDate(classifyDate);
		p.setDecipherDate(decipherDate);
		/*p.setOverDate(overDate);
		p.setOverDepartment(overDepartment);
		p.setOverConclusion(overConclusion);
		p.setAcceptDate(acceptDate);
		p.setAcceptDepartment(acceptDepartment);
		p.setAcceptConclusion(acceptConclusion);*/
		p.setEvalueDate(evalueDate);
		p.setEvalueDepartment(evalueDepartment);
		p.setEvalueConclusion(evalueConclusion);
//		p.setOverFormat(overFormat);
		p.setDepartment(department);
		p.setUpdater(teacher);
		p.setUpdateDate(new Date());
		p.setDelFlg(false);
		p.setDelDate(null);
		p.setRemark(remark);
		
		System.out.println("完成形式：" + finishType);
		if (finishType) {
			// 结题
			p.setOverDate(finishDate);
			p.setOverConclusion(finishConclusion);
			p.setOverFormat("结题");
			/*
			 * p.setOverDate(overDate); p.setOverDepartment(overDepartment);
			 * p.setOverConclusion(overConclusion);
			 */
		} else if ((!finishType)
				&& ((finishConclusion.trim()).length() != 0 || finishDate != null)) {
			// 验收
			p.setOverFormat("验收");
			p.setAcceptDate(finishDate);
			p.setAcceptConclusion(finishConclusion);
			/*
			 * p.setAcceptDate(acceptDate);
			 * p.setAcceptDepartment(acceptDepartment);
			 * p.setAcceptConclusion(acceptConclusion);
			 */
		} else {
			p.setOverFormat(null);
			p.setAcceptDate(null);
			p.setAcceptConclusion(null);
		}
		
		 if(teacher.getVerity().equals("打开"))
		 {
			if (RecordSetting.getRecordSetting()) 
			{
				projectService.lockProject(innerCode);
				p.setInnerCode("" + new Date().getTime());
				p.setDelFlg(true);// ///////////////////////////////////////////////////////////////////////存在问题
				projectService.saveProject(p);
	
				Record rd = new Record();
				rd.setApplyDate(new Date());
				rd.setApplyer(teacher);
				rd.setDoType(ConstData.ApplyForUpdate);
				rd.setIDType(false);
				rd.setNewId(p.getInnerCode());
				rd.setOldId(innerCode);
				rd.setCause(cause);
				rd.setTableName("Project");
				projectService.saveRecord(rd);
	
				request.setAttribute("pagePath", "page/project/ProjectFrame.jsp");
				request.setAttribute("info", "已提交项目更新申请，请等待管理员审核！");
				return "info";
			}
			else 
			{
				boolean b = projectService.update(p);
	
				// 生成完成信息
				if (b) 
				{
					String of[] = null;
					
					if (finishType) 
					{
						// 结题
						/*acm.setConcludeDate(finishDate);
						acm.setConcludeLevel(finishConclusion);
						acm.setConcludeOrg(overDepartment);*/
						of = new String[] {"结题"};
					} 
					else if ((!finishType)
							&& ((finishConclusion.trim()).length() != 0 || finishDate != null)) 
					{
						// 验收
						/*acm.setConcludeDate(finishDate);
						acm.setConcludeLevel(finishConclusion);
						acm.setConcludeOrg(acceptDepartment);*/
						of = new String[] {"验收"};
					}
					
					List<String> _of = new ArrayList<String>();
					_of.add("结题");
					_of.add("验收");
					
					if (!"".equals(of[0])) 
					{
						for (String s : of) 
						{
							System.out.println(s);
							Achievement acm = projectService.getAchievement(s,
									innerCode);
							acm.setAchieveMan(proLeader);
							// clg修改，2012.1.20
							// acm.setProject(p);
							acm.setInnerId(p.getInnerCode());
							acm.setAchieveName(proName);
							acm.setItsOrg(organization);
							acm.setIsApplay(toUseFlg);
							acm.setApplayOrg(toUseUnit);
							acm.setCreater(teacher);
							acm.setCreateDate(new Date());
							acm.setType(s);
							if (s.equals("结题"))
							{
								_of.remove("结题");
								acm.setConcludeDate(finishDate);
								acm.setConcludeLevel(finishConclusion);
								acm.setConcludeOrg(overDepartment);
							} 
							else if (s.equals("验收")) 
							{
								_of.remove("验收");
								acm.setConcludeDate(finishDate);
								acm.setConcludeLevel(finishConclusion);
								acm.setConcludeOrg(acceptDepartment);
							}
							projectService.saveAchievement(acm);
						}
					}
					for (String s : _of) 
					{
						Achievement acm = projectService.getAchievement(s,
								innerCode);
						if (acm.getID() != 0)
							projectService.deleteAchievement(acm.getID());
					}
				}
	
				if (b) 
				{
					request.setAttribute("info", "项目成功更新！");
					return "info";
				} else 
				{
					request.setAttribute("info", "对不起，更新项目出错，请重试！");
					return ERROR;
				}
			}
		 }
		 else 
		 {
			 boolean b = projectService.update(p);
				
				// 生成完成信息
				if (b) 
				{
					String of[] = null;
					
					if (finishType) 
					{
						// 结题
						/*acm.setConcludeDate(finishDate);
						acm.setConcludeLevel(finishConclusion);
						acm.setConcludeOrg(overDepartment);*/
						of = new String[] {"结题"};
					} 
					else if ((!finishType)
							&& ((finishConclusion.trim()).length() != 0 || finishDate != null)) 
					{
						// 验收
						/*acm.setConcludeDate(finishDate);
						acm.setConcludeLevel(finishConclusion);
						acm.setConcludeOrg(acceptDepartment);*/
						of = new String[] {"验收"};
					}
					
					List<String> _of = new ArrayList<String>();
					_of.add("结题");
					_of.add("验收");
					if (!"".equals(of[0])) 
					{
						for (String s : of) 
						{
							System.out.println(s);
							Achievement acm = projectService.getAchievement(s,
									innerCode);
							acm.setAchieveMan(proLeader);
							// clg修改，2012.1.20
							// acm.setProject(p);
							acm.setInnerId(p.getInnerCode());
							acm.setAchieveName(proName);
							acm.setItsOrg(organization);
							acm.setIsApplay(toUseFlg);
							acm.setApplayOrg(toUseUnit);
							acm.setCreater(teacher);
							acm.setCreateDate(new Date());
							acm.setType(s);
							if (s.equals("结题"))
							{
								_of.remove("结题");
								acm.setConcludeDate(finishDate);
								acm.setConcludeLevel(finishConclusion);
								acm.setConcludeOrg(overDepartment);
							} 
							else if (s.equals("验收")) 
							{
								_of.remove("验收");
								acm.setConcludeDate(finishDate);
								acm.setConcludeLevel(finishConclusion);
								acm.setConcludeOrg(acceptDepartment);
							}
							projectService.saveAchievement(acm);
						}
					}
					for (String s : _of) 
					{
						Achievement acm = projectService.getAchievement(s,
								innerCode);
						if (acm.getID() != 0)
							projectService.deleteAchievement(acm.getID());
					}
				}
	
				if (b) 
				{
					request.setAttribute("info", "项目成功更新！");
					return "info";
				} else 
				{
					request.setAttribute("info", "对不起，更新项目出错，请重试！");
					return ERROR;
				}
			}
		
	}
		

	

	public String showProject() throws Exception {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(innerCode);
		if (p == null) {
			request.setAttribute("info", "提取项目出错！请重试！");
			return ERROR;
		}
		request.setAttribute("project", p);
		return "showProject";
	}

	public String setProjectDisplay() throws Exception {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		String predix = request.getParameter("predix");
		if (FieldsControl.setProjectFields(sfield, predix)) {
			request.setAttribute("info", "保存设置成功！");
		} else {
			request.setAttribute("info", "对不起，保存设置失败，请重试！");
		}
		return "SetSuccess";
	}

	public String exportToExcel() throws BusinessException,
			UnsupportedEncodingException {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		String _s = request.getParameter("s");
		String s = null;
		if (_s != null)
			s = java.net.URLDecoder.decode(_s, "UTF-8");
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);

		// List<Project> list =
		// projectService.getProjects(teacher.getOrg().getName(), pagination,
		// year, s);
		List<Project> currentProjects = (List<Project>) projectService
				.getProjects(s, teacher.getOrg().getName(), year);

		List<ProjectFieldBean> pfbs = ProjectFieldsControl.getProjectFields();

		// 统计页面上显示的列有多少个
		int pfbSize = 0;
		for (ProjectFieldBean pfb : pfbs) {
			if (pfb.getDisplay()) {
				pfbSize++;
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
		if (currentProjects != null)
			listSize = currentProjects.size();
		row = sheet.createRow((short) rowNum++);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("科研管理系统项目导出");
		cell.setCellStyle(style);

		// 修改 仅仅将页面上显示的列 导出
		sheet.addMergedRegion(new Region(rowNum - 1, (short) 0, rowNum - 1,
				(short) (pfbSize - 1)));

		int j = 0;
		row = sheet.createRow((short) rowNum++);
		for (ProjectFieldBean pfb : pfbs) {
			if (!pfb.getDisplay()) {
				continue;
			}
			cell = row.createCell((short) j++);
			cell.setEncoding((short) 1);
			cell.setCellValue(pfb.getField());
		}
		for (int i = 0; i < listSize; i++) {
			Project p = currentProjects.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;
			for (ProjectFieldBean pfb : pfbs) {
				if (!pfb.getDisplay()) {
					continue;
				}
				String temp=pfb.getCode();
				//System.out.println(pfb.getCode()+"***");
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				if(temp.equals("yearArriveMoney")||temp.equals("totalArriveMoney")||temp.equals("yearGrant")
						||temp.equals("totalGrant")||temp.equals("yearCapitalAccount")||temp.equals("totalCapitalAccount")
						||temp.equals("contractFounds")||temp.equals("riskFee")||temp.equals("riskFeeBack"))
					cell.setCellValue((new Float(ProjectFieldsControl.doVoGetMethod(p,pfb.getCode()))).doubleValue());
				else
					cell.setCellValue(ProjectFieldsControl.doVoGetMethod(p,pfb.getCode()));
			}
		}
		boolean c = false;
		if (request.getParameter("c") != null)
			c = Boolean.parseBoolean(request.getParameter("c"));
		if (c) {
			Project pCalc = (Project) request.getSession()
					.getAttribute("pCalc");
			row = sheet.createRow((short) rowNum++);

			cell = row.createCell((short) 0);
			cell.setEncoding((short) 1);
			cell.setCellValue("合计");

			cell = row.createCell((short) 19);
			cell.setEncoding((short) 1);
			cell.setCellValue(pCalc.getYearArriveMoney());

			cell = row.createCell((short) 20);
			cell.setEncoding((short) 1);
			cell.setCellValue(pCalc.getTotalArriveMoney());

			cell = row.createCell((short) 21);
			cell.setEncoding((short) 1);
			cell.setCellValue(pCalc.getYearGrant());

			cell = row.createCell((short) 22);
			cell.setEncoding((short) 1);
			cell.setCellValue(pCalc.getTotalGrant());

			cell = row.createCell((short) 23);
			cell.setEncoding((short) 1);
			cell.setCellValue(pCalc.getContractFounds());

			cell = row.createCell((short) 26);
			cell.setEncoding((short) 1);
			cell.setCellValue(pCalc.getRiskFee());

			cell = row.createCell((short) 27);
			cell.setEncoding((short) 1);
			cell.setCellValue(pCalc.getRiskFeeBack());
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=ProjectsExport.xls");
		OutputStream out;
		try {
			out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String searchProject() throws BusinessException,
			UnsupportedEncodingException {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		boolean c = Boolean.parseBoolean(request.getParameter("c"));
		String _s = request.getParameter("s");
		String search = (String) session.getAttribute("search");
		String s = null;
		if ("".equals(search) || search == null) {
			s = java.net.URLDecoder.decode(_s, "UTF-8");

		} else {
			s = search + " and " + java.net.URLDecoder.decode(_s, "UTF-8");
		}
		System.out.println("_s=你好" + s + " " + search + " ");
		if ("".equals(s))
			return null;
		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		String year = (String) session.getAttribute("year");
		List<Project> list = null;
		if (year == null) {
			list = projectService.getProjects(teacher.getOrg().getName(),
					pagination, null, s, c);
		} else {
			list = projectService.getProjects(teacher.getOrg().getName(),
					pagination, year, s, c);
			request.setAttribute("year", year);// //year
		}
		System.out.println("list.size=" + list.size());
		Project pCalc = list.get(list.size() - 1);
		list.remove(list.size() - 1);
		request.setAttribute("c", c);
		request.getSession().setAttribute("pCalc", pCalc);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mProject.action?method=manageProjectIndex&c=" + c + "&s=" + s);
		request.setAttribute("currentProjects", list);
		request.setAttribute("s", s);

		return SUCCESS;
	}

	public String searchProjectSpl() throws BusinessException,
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
		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Project> list = projectService.getProjects(teacher.getOrg()
				.getName(), pagination, null, s);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mProject.action?method=manageProjectIndex&s=" + s);
		request.setAttribute("currentProjects", list);
		request.setAttribute("s", s);
		return SUCCESS;
	}

	public String isProjectLock() throws BusinessException, IOException {
		try {
			ProjectService projectService = (ProjectService) BusinessFactory
					.getBusiness(ProjectServiceImpl.class);
			boolean r = projectService.isProjectLock(innerCode);
			ServletOutputStream os = response.getOutputStream();
			if (r) {
				os.print("lock");
			} else {
				os.print("unlock");
			}
			os.flush();
			return null;
		} catch (BusinessException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String isProjectCanEdit() throws BusinessException, IOException {
		try {
			ProjectService projectService = (ProjectService) BusinessFactory
					.getBusiness(ProjectServiceImpl.class);
			Project p = projectService.getProject(innerCode);
			ServletOutputStream os = response.getOutputStream();
			if (p.getProStatus().endsWith("完成")) {
				os.print("unedit");
			} else {
				os.print("edit");
			}
			os.flush();
			return null;
		} catch (BusinessException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void setFinishType(boolean finishType) {
		this.finishType = finishType;
	}

	public boolean isFinishType() {
		return finishType;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishConclusion(String finishConclusion) {
		this.finishConclusion = finishConclusion;
	}

	public String getFinishConclusion() {
		return finishConclusion;
	}

	

}
