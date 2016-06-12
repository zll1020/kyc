package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
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
import cn.cust.kyc.dao.ApproDao;
import cn.cust.kyc.dao.DAOFactory;
import cn.cust.kyc.dao.IncomeDao;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.FieldBean;
import cn.cust.kyc.util.FieldsControl;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.RecordSetting;
import cn.cust.kyc.vo.Appropriation;
import cn.cust.kyc.vo.Income;
import cn.cust.kyc.vo.InfoTable;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Record;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageIncomeAction extends ActionBase {

	/**
	 * 到款编号
	 */
	private String incomeId;

	/**
	 * 项目
	 */
	private String innerCode;

	/**
	 * 财务到款通知单号
	 */
	private String number;

	/**
	 * 到款日期
	 */
	private Date incomeDate;

	/**
	 * 到款额
	 */
	private double income;

	/**
	 * 到款次数
	 */
	private int incomeCount;

	/**
	 * 经费来源单位
	 */
	private String department;

	/**
	 * 所属单位
	 */
	private String organization;

	/**
	 * 签收日期
	 */
	private Date signoffDate;

	/**
	 * 编辑信息
	 */
	private String creater;

	private String updater;

	private String remark;

	private String cause;

	private String syncyear;

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getIncomeDate() {
		return incomeDate;
	}

	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public int getIncomeCount() {
		return incomeCount;
	}

	public void setIncomeCount(int incomeCount) {
		this.incomeCount = incomeCount;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Date getSignoffDate() {
		return signoffDate;
	}

	public void setSignoffDate(Date signoffDate) {
		this.signoffDate = signoffDate;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 项目内部编号
	 */
	private String nbbh;

	public String getNbbh() {
		return nbbh;
	}

	public void setNbbh(String nbbh) {
		this.nbbh = nbbh;
	}

	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String manageIncomeByProject() throws Exception {
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
		List<Income> list = projectService.getIncomeByProject(pagination, year,
				nbbh);
		request.setAttribute("incomes", list);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("year", year);
		request.setAttribute("proStat",
				(projectService.getProject(nbbh)).getProStatus());
		InfoTable iftable = projectService.getInfoByKey("syear");
		request.setAttribute("syear", iftable.getKeyindex());

		HttpSession session = request.getSession();
		session.setAttribute("year", year);
		session.setAttribute("nbbh", nbbh);

		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl", "mIncome.action?method=manageIncomes");
		return SUCCESS;
	}

	public String manageIncomeLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			predix = "";
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		// //projectService.getIncomeYears(predix,
		// nbbh)该方法返回所有的到款年度信息，现改为getCurrentIncomeYears(predix, nbbh);限制年度范围
		List<Integer> years = projectService
				.getCurrentIncomeYears(predix, nbbh);

		// System.out.println("到款年度："+table1.getKeyword()+"  "+table1.getKeyindex());*/
		request.setAttribute("years", years);
		request.setAttribute("nbbh", nbbh);
		session.setAttribute("year", "");
		session.setAttribute("nbbh", nbbh);
		return "IncomeFrameLeft";
	}

	public String manageYears() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Integer> years = projectService.getStartYears();
		Date d = new Date();
		DateFormat dataformat = new SimpleDateFormat("yyyy");
		String timeString = dataformat.format(d);
		Integer currentYear = new Integer(timeString);
		if (!years.contains(new Integer(timeString)))
			years.add(currentYear);
		request.setAttribute("years", years);
		session.setAttribute("year", "");
		return "IncomeYears";
	}
/**
 * 同步经费----将所设定的当年信息值累计，对应生成当年累计拨款和当年累计到款两项信息。
 * @return
 * @throws BusinessException
 * @throws DAOException
 */
	public String syncMoney() throws BusinessException, DAOException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		// 获得需要同步经费的项目链表
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Project> sProjects = projectService.getProjects();
		IncomeDao icd = DAOFactory.getIncomeDao();
		ApproDao apd = DAOFactory.getApproDao();
		for (int i = 0; i < sProjects.size(); i++) {
			Project pr = sProjects.get(i);
			List<Income> incomeList = projectService.getIncomeByProject(
					pr.getInnerCode(), syncyear, "");
			List<Appropriation> appList = projectService
					.getAppropriationsByProjectAndYear(pr.getInnerCode(),
							syncyear, "");
			double incomes = 0;
			double appros = 0;
			double gds = 0;
			// 统计当年到款
			for (Income n : incomeList) {
				if(!n.getDelFlg()){
					incomes += n.getIncome();
				}
			}
			// 统计当年拨款、固定资产使用费
			for (Appropriation a : appList) {
				if(!a.getDelFlg()){
					appros += a.getAppropriation();
					gds += a.getCapitalAccount();
				}
			}
			pr.setDnnd(syncyear);
			pr.setYearArriveMoney(incomes);
			pr.setYearGrant(appros);
			pr.setYearCapitalAccount(gds);
			projectService.update(pr);
		}

		InfoTable table1 = projectService.getInfoByKey("syear");
		// System.out.println("table1:"+table1.getId()+"  "+table1.getKeyword()+" "+table1.getKeyindex());
		table1.setKeyindex(syncyear);
		projectService.updateInfoTable(table1);
		// InfoTable table2=projectService.getInfoByKey("syear");
		// System.out.println("table1:"+table2.getId()+"  "+table2.getKeyword()+" "+table2.getKeyindex());
		request.setAttribute("pagePath", "mIncome.action?method=manageYears");
		request.setAttribute("info", "经费同步完成！");
		return "info";
	}
	
	/**
	 * 同步全周期累计经费
	 * @return
	 * @throws BusinessException
	 * @throws DAOException
	 */
	
	public String syTotalMoney() throws BusinessException, DAOException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		// 获得需要同步经费的项目链表
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Project> sProjects = projectService.getProjects();
//		IncomeDao icd = DAOFactory.getIncomeDao();
//		ApproDao apd = DAOFactory.getApproDao();
		for (int i = 0; i < sProjects.size(); i++) {
			Project pr = sProjects.get(i);
			//List<Income> incomeList = projectService.getIncomeByProject(pr.getInnerCode(), syncyear, "");
			List<Income> incomeList=projectService.getIncomeByProject(pr.getInnerCode());
			//List<Appropriation> appList = projectService.getAppropriationsByProjectAndYear(pr.getInnerCode(),syncyear, "");
			List<Appropriation> appList = projectService.getAppropriationByProject(pr.getInnerCode());
			double incomes = 0;
			double appros = 0;
			double gds = 0;
			// 统计当年到款
			for (Income n : incomeList) {
				if(!n.getDelFlg()){
					incomes += n.getIncome();
				}
			}
			// 统计当年拨款、固定资产使用费
			for (Appropriation a : appList) {
				if(!a.getDelFlg()){
					appros += a.getAppropriation();
					gds += a.getCapitalAccount();
				}
				
			}
			pr.setTotalArriveMoney(incomes);
			pr.setTotalCapitalAccount(gds);
			pr.setTotalGrant(appros);
			projectService.update(pr);
		}

		//InfoTable table1 = projectService.getInfoByKey("syear");
		// System.out.println("table1:"+table1.getId()+"  "+table1.getKeyword()+" "+table1.getKeyindex());
		//table1.setKeyindex(syncyear);
		//projectService.updateInfoTable(table1);
		// InfoTable table2=projectService.getInfoByKey("syear");
		// System.out.println("table1:"+table2.getId()+"  "+table2.getKeyword()+" "+table2.getKeyindex());
		request.setAttribute("pagePath", "mIncome.action?method=manageYears");
		request.setAttribute("info", "经费累计完成！");
		return "info";
	}

	public String addIncomeIndex() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(request.getParameter("nbbh"));
		request.setAttribute("project", p);
		return "AddIncome";
	}

	public String updateIncomeIndex() throws BusinessException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Income in = projectService.getIncomeById(incomeId);
		Project p = projectService.getProject(in.getProject().getInnerCode());
		request.setAttribute("project", p);
		request.setAttribute("income", in);
		return "UpdateIncome";
	}

	public String updateIncome() throws Exception {
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
					+ "）不应具有登记到款权限！如有疑问，请联系管理员。");
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
		List<Appropriation> appList = projectService
				.getAppropriationByIncome(incomeId);
		double sum = 0;
		for (int i = 0; i < appList.size(); i++)
			sum += appList.get(i).getAppropriation();
		if (sum > income) {
			request.setAttribute("info",
					"该笔到款存在相关拨款，修改后的到款额小于拨款总额，不允许修改，请返回上一页核查！");
			return ERROR;
		}

		Income in = projectService.getIncomeById(incomeId);
		Project p = projectService.getProject(innerCode);
		double money = in.getIncome() - income;
		if (new Date().getYear() == in.getIncomeDate().getYear())
			p.setYearArriveMoney(p.getYearArriveMoney() - money);
		p.setTotalArriveMoney(p.getTotalArriveMoney() - money);

		in.setDepartment(department);
		in.setProject(p);
		in.setIncome(income);
		in.setIncomeDate(incomeDate);
		in.setNumber(number);
		in.setIncomeCount(in.getIncomeCount());
		in.setOrganization(organization);
		in.setUpdater(teacher);
		in.setUpdateDate(new Date());
		if(teacher.getVerity().equals("打开")){
			if (RecordSetting.getRecordSetting()) {
				in.setIncomeId("" + new Date().getTime());
				in.setDelFlg(true);
				projectService.saveIncome(in);
	
				Record rd = new Record();
				rd.setApplyDate(new Date());
				rd.setApplyer(teacher);
				rd.setDoType(ConstData.ApplyForUpdate);
				rd.setIDType(false);
				rd.setOldId(incomeId);
				rd.setNewId(in.getIncomeId());
				rd.setCause(cause);
				rd.setTableName("Income");
				projectService.saveRecord(rd);
				projectService.lockProject(in.getProject().getInnerCode());
				request.setAttribute("info", "已提交修改申请，请等待管理员审核！");
			} else {
				projectService.update(p);
				projectService.updateIncome(in);
				request.setAttribute("info", "修改成功！");
			}
		}else{
			projectService.update(p);
			projectService.updateIncome(in);
			request.setAttribute("info", "修改成功！");
		}

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Income> list = projectService.getIncomeByProject(pagination,
				"all", innerCode);
		request.setAttribute("incomes", list);
		request.setAttribute("nbbh", innerCode);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mIncome.action?method=manageIncomeByProject&nbbh=" + innerCode
						+ "&year=all");
		return SUCCESS;
	}

	public String addIncome() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		// 生成主键（内部编号）
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有登记到款权限！如有疑问，请联系管理员。");
			return ERROR;
		}
		Date date = new Date();
		int now = date.getYear() + 1900;
		incomeId = predix + "DK-" + now + "-";
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		String lastestKey = projectService.getMaxIncomeKey(incomeId);
		if (lastestKey != null) {
			String num = lastestKey.substring(15);
			incomeId = incomeId
					+ new Integer(Integer.parseInt(num) + 1001).toString()
							.substring(1);
		} else
			incomeId = incomeId + "001";

		Project p = projectService.getProject(innerCode);
		// incomeDate
		DateFormat dataformat = new SimpleDateFormat("yyyy");
		String timeString = dataformat.format(incomeDate);
		// 保存到款至项目
		// 如果到款年份恰为当年年度，当年到款相加
		if (timeString.equals(p.getDnnd())) {
			p.setYearArriveMoney(p.getYearArriveMoney() + income);
		}
		/*
		 * if((new Date()).getYear()==incomeDate.getYear()) {
		 * System.out.println("当前年度1："+(new Date()).getYear());
		 * System.out.println("当前年度2："+incomeDate.getYear());
		 * p.setYearArriveMoney(p.getYearArriveMoney() + income); }
		 */
		p.setTotalArriveMoney(p.getTotalArriveMoney() + income);
		projectService.update(p);

		// 统计到款次数
		int totalIncomeCount = 0;
		// 获取所有到款
		List<Income> allIncome = projectService.getIncomeByProject(innerCode);
		if (allIncome == null || allIncome.size() == 0) {
			;
		} else {
			// 寻找最大的到款次数
			for (Income sample : allIncome) {
				if (sample.getIncomeCount() > totalIncomeCount) {
					totalIncomeCount = sample.getIncomeCount();
				}
			}
		}
		// 到款次数
		totalIncomeCount++;

		Income in = new Income();
		in.setIncomeId(incomeId);
		in.setDepartment(department);
		in.setProject(p);
		in.setIncome(income);
		in.setIncomeDate(incomeDate);
		in.setNumber(number);
		in.setIncomeCount(totalIncomeCount);
		in.setOrganization(organization);
		in.setCreateDate(new Date());
		in.setCreater(teacher);
		in.setRemark(remark);
		projectService.saveIncome(in);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Income> list = projectService.getIncomeByProject(pagination,
				"all", innerCode);
		request.setAttribute("incomes", list);
		request.setAttribute("nbbh", innerCode);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mIncome.action?method=manageIncomeByProject&nbbh=" + innerCode
						+ "&year=all");
		return SUCCESS;
	}

	public String deleteIncome() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		cause = java.net.URLDecoder.decode(cause, "UTF-8");
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Income in = projectService.getIncomeById(incomeId);
		List<Appropriation> app = projectService.getAppropriationByIncome(in
				.getIncomeId());

		if (app.size() > 0) {
			request.setAttribute("info", "无法删除该笔到款，该笔到款存在相关拨款！");
			return ERROR;
		}

		/*
		 * System.out.println("结果长度信息："+app.size()); for(int
		 * i=0;i<app.size();i++)
		 * System.out.println("到款单编号"+in.getIncomeId()+"  拨款信息："
		 * +app.get(i).getAppropriateID());
		 */
		Project p = in.getProject();
		if (RecordSetting.getRecordSetting()) {
			Record rd = new Record();
			rd.setApplyDate(new Date());
			rd.setApplyer(teacher);
			rd.setDoType(ConstData.ApplyForDelete);
			rd.setIDType(false);
			rd.setOldId(incomeId);
			rd.setNewId("" + new Date().getTime());
			rd.setCause(cause);
			rd.setTableName("Income");
			projectService.saveRecord(rd);
			projectService.lockProject(in.getProject().getInnerCode());
			request.setAttribute("info", "已生成删除申请，请等待管理员审核！");
		} else {
			if (new Date().getYear() == in.getIncomeDate().getYear())
				p.setYearArriveMoney(p.getYearArriveMoney() - in.getIncome());
			p.setTotalArriveMoney(p.getTotalArriveMoney() - in.getIncome());
			projectService.update(p);
			projectService.deleteIncome(incomeId);
			request.setAttribute("info", "删除成功！");
			return manageIncomeByProject();
		}
		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Income> list = projectService.getIncomeByProject(pagination,
				"all", p.getInnerCode());
		request.setAttribute("incomes", list);
		request.setAttribute("nbbh", p.getInnerCode());
		request.setAttribute("pagination", pagination);
		request.setAttribute(
				"pagiUrl",
				"mIncome.action?method=manageIncomeByProject&nbbh="
						+ p.getInnerCode() + "&year=all");
		return SUCCESS;
	}

	public String showIncome() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Income in = projectService.getIncomeById(incomeId);
		Project p = projectService.getProject(in.getProject().getInnerCode());
		request.setAttribute("project", p);
		request.setAttribute("income", in);
		return "ShowIncome";
	}

	public String manageIncomes() throws BusinessException,
			UnsupportedEncodingException {
		System.out.println("manageIncomes()");
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		String _s = request.getParameter("s");
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

		System.out.println("income:   " + s);
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
		List<Income> list = projectService.getIncomes(predix, pagination, year,
				s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mIncome.action?method=manageIncomes&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mIncome.action?method=manageIncomes");
		request.setAttribute("incomes", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return SUCCESS;
	}

	public String searchIncome() throws BusinessException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		String _s = request.getParameter("s");
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

		List<Income> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getIncomes(predix, pagination, "all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getIncomes(predix, pagination, preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mIncome.action?method=manageIncomes&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mIncome.action?method=manageIncomes");
		request.setAttribute("incomes", list);
		if (s != null)
			request.setAttribute("s", s);
		return SUCCESS;
	}

	/**
	 * @return
	 * @throws BusinessException
	 */
	/**
	 * @return
	 * @throws BusinessException
	 */
	/**
	 * @return
	 * @throws BusinessException
	 */
	/**
	 * @return
	 * @throws BusinessException
	 */
	/**
	 * @return
	 * @throws BusinessException
	 */
	/**
	 * @return
	 * @throws BusinessException
	 */
	/**
	 * @return
	 * @throws BusinessException
	 * @throws DAOException
	 * @throws UnsupportedEncodingException
	 */
	public String exportToExcel() throws BusinessException, DAOException,
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
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			predix = "";
		}
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Income> list;
		if (nbbh == null)
			list = projectService.getIncomes(predix, year, s);
		else
			list = projectService.getIncomeByProject(nbbh, year, s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("Income");

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
		cell.setCellValue("科研管理系统经费导出");
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
			if (j >= 16)
				break;
		}

		for (int i = 0; i < listSize; i++) {
			// income表共17个字段,显示16个属性
			Income in = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("incomeId")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getIncomeId());
			}

			if (fbMap.containsKey("project.innerCode")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getProject().getInnerCode());
			}

			if (fbMap.containsKey("number")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getNumber());
			}

			if (fbMap.containsKey("incomeDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getIncomeDate() == null ? "" : in
						.getIncomeDate().toString());
			}

			if (fbMap.containsKey("income")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getIncome());
			}

			if (fbMap.containsKey("incomeCount")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getIncomeCount());
			}

			if (fbMap.containsKey("department")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getDepartment());
			}

			if (fbMap.containsKey("organization")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getOrganization());
			}

			if (fbMap.containsKey("signoffDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getSignoffDate() == null ? "" : in
						.getSignoffDate().toString());
			}

			if (fbMap.containsKey("creater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getCreater().getName());
			}

			if (fbMap.containsKey("createDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getCreateDate().toString());
			}

			if (fbMap.containsKey("updater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getUpdater() == null ? "" : in
						.getUpdater().getName());
			}

			if (fbMap.containsKey("updateDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getUpdateDate() == null ? "" : in
						.getUpdateDate().toString());
			}

			if (fbMap.containsKey("delFlg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getDelFlg());
			}

			if (fbMap.containsKey("delDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getDelDate() == null ? "" : in
						.getDelDate().toString());
			}

			if (fbMap.containsKey("remark")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(in.getRemark());
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=IncomeExport.xls");
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

	public String searchIncomeSpl() throws BusinessException,
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
		List<Income> list = /*
							 * projectService.getProjects(teacher.getOrg().getName
							 * (), pagination, null, s);
							 */projectService.getIncomes(predix, pagination,
				"all", s);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mIncome.action?method=manageIncomes&s=" + s);
		request.setAttribute("incomes", list);
		request.setAttribute("s", s);
		return SUCCESS;
	}

	public void setSyncyear(String syncyear) {
		this.syncyear = syncyear;
	}

	public String getSyncyear() {
		return syncyear;
	}

}
