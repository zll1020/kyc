package cn.cust.kyc.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import com.itextpdf.text.Image;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
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
import sun.org.mozilla.javascript.internal.ast.Yield;

public class ManageAppropriationAction extends ActionBase {

	/**
	 * 主键
	 */
	private int ID;

	/**
	 * 拨款单号
	 */
	private String appropriateID;

	/**
	 * 项目
	 */

	private String innerCode;

	/**
	 * 到款单号
	 */
	private String incomeId;

	/**
	 * 拨款单位
	 */
	private String department;

	/**
	 * 回拨单号
	 */
	private String returnbackID;

	/**
	 * 拨款额
	 */
	private double appropriation;

	/**
	 * 拨款次数
	 */
	private int approCount;

	/**
	 * 拨款负责人
	 */
	private String chargePerson;

	/**
	 * 拨款日期
	 */
	private Date approDate;

	/**
	 * 回拨标记
	 */
	private boolean isReturnBack;

	/**
	 * 回拨日期
	 */
	private Date returnBackDate;

	/**
	 * 是否计入超津贴
	 */
	private boolean allowanceFlg;

	/**
	 * 固定资产使用费比例
	 */
	private int capitalAccountPer;

	/**
	 * 院管理费比例
	 */
	private int collegeFeePer;

	/**
	 * 校管理费比例
	 */
	private int schoolFeePer;

	/**
	 * 风险金比例
	 */
	private int riskFeePer;

	/**
	 * 固定资产使用费
	 */
	private double capitalAccount;

	/**
	 * 院管理费
	 */
	private double collegeFee;

	/**
	 * 校管理费
	 */
	private double schoolFee;

	/**
	 * 风险金
	 */
	private double riskFee;

	// ------编辑信息
	
	/**
	 * 2016年1月28日，修改科研拨款方案，新增 
	 * 房屋使用费、仪器设备使用费、水电暖费、科研绩效和收益
	 * 开始点
	 */
	/**
	 * 房屋使用费
	 */
	private double fwsyf;
	/**
	 * 房屋使用费比例
	 */
	private double fwsyfbl;
	
	/**
	 * 仪器设备使用费
	 */
	private double yqsbsyf;
	/**
	 * 仪器设备使用费比例
	 */
	private double yqsbsyfbl;
	
	private double yqsbsyfper;
	
	/**
	 * 水电暖费
	 */
	private double sdnf;
	/**
	 * 水电暖费比例
	 */
	private double sdnfbl;
	
	/**
	 * 科研绩效收益费
	 */
	private double kyjxsy;
	/**
	 * 科研绩效收益费比例
	 */
	private double kyjxsybl;
	
	/**
	 * 间接经费合计，为所有间接经费之和
	 */
	private double jjhj;
	/**
	 * 直接经费，间接经费+直接经费  不一定等于拨款额
	 */
	private double zjjf;
	/**
	 * 2016年1月28日，修改科研拨款方案，新增 
	 * 房屋使用费、仪器设备使用费、水电暖费、科研绩效和收益
	 * 结束点
	 */
	
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
	 * 备注
	 */
	private String remark;
	private String cause;
	/**
	 * 继续拨款次数
	 */
	private int conAppCount;

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getCapitalAccount() {
		return capitalAccount;
	}

	public void setCapitalAccount(double capitalAccount) {
		this.capitalAccount = capitalAccount;
	}

	public String getAppropriateID() {
		return appropriateID;
	}

	public void setAppropriateID(String appropriateID) {
		this.appropriateID = appropriateID;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(String incomeId) {
		this.incomeId = incomeId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getReturnbackID() {
		return returnbackID;
	}

	public void setReturnbackID(String returnbackID) {
		this.returnbackID = returnbackID;
	}

	public double getAppropriation() {
		return appropriation;
	}

	public void setAppropriation(double appropriation) {
		this.appropriation = appropriation;
	}

	public int getApproCount() {
		return approCount;
	}

	public void setApproCount(int approCount) {
		this.approCount = approCount;
	}

	public double getCapitalAccountPer() {
		return capitalAccountPer;
	}

	public void setCapitalAccountPer(int capitalAccountPer) {
		this.capitalAccountPer = capitalAccountPer;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public Date getApproDate() {
		return approDate;
	}

	public void setApproDate(Date approDate) {
		this.approDate = approDate;
	}

	public boolean getIsReturnBack() {
		return isReturnBack;
	}

	public void setIsReturnBack(boolean isReturnBack) {
		this.isReturnBack = isReturnBack;
	}

	public Date getReturnBackDate() {
		return returnBackDate;
	}

	public void setReturnBackDate(Date returnBackDate) {
		this.returnBackDate = returnBackDate;
	}

	public boolean getAllowanceFlg() {
		return allowanceFlg;
	}

	public void setAllowanceFlg(boolean allowanceFlg) {
		this.allowanceFlg = allowanceFlg;
	}

	public int getCollegeFeePer() {
		return collegeFeePer;
	}

	public void setCollegeFeePer(int collegeFeePer) {
		this.collegeFeePer = collegeFeePer;
	}

	public int getSchoolFeePer() {
		return schoolFeePer;
	}

	public void setSchoolFeePer(int schoolFeePer) {
		this.schoolFeePer = schoolFeePer;
	}

	public int getRiskFeePer() {
		return riskFeePer;
	}

	public void setRiskFeePer(int riskFeePer) {
		this.riskFeePer = riskFeePer;
	}

	public double getCollegeFee() {
		return collegeFee;
	}

	public void setCollegeFee(double collegeFee) {
		this.collegeFee = collegeFee;
	}

	public double getSchoolFee() {
		return schoolFee;
	}

	public void setSchoolFee(double schoolFee) {
		this.schoolFee = schoolFee;
	}

	public double getRiskFee() {
		return riskFee;
	}

	public void setRiskFee(double riskFee) {
		this.riskFee = riskFee;
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

	public String manageAppropriationByProject() throws Exception {
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
		List<Appropriation> list = projectService.getAppropriationByProject(
				pagination, year, nbbh);
		request.setAttribute("appropriations", list);
		InfoTable iftable = projectService.getInfoByKey("syear");
		request.setAttribute("syear", iftable.getKeyindex());
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("proStat",
				(projectService.getProject(nbbh)).getProStatus());
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageAppropriationByProject&nbbh="
						+ nbbh/* +"&year="+year */);
		return SUCCESS;
	}

	public String manageAppropriationLeftFrame() throws Exception {
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
		List<Integer> years = projectService.getCurrentAppropriationYears(
				predix, nbbh);// getAppropriationYears(predix, nbbh);
		request.setAttribute("years", years);
		request.setAttribute("nbbh", nbbh);
		session.setAttribute("year", "");
		return "ApproFrameLeft";
	}

	public String addAppropriationIndex() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(nbbh);
		List<Income> ins = projectService.getIncomeByProject(nbbh);
		List<Appropriation> apps = projectService
				.getAppropriationByProject(nbbh);

		/*
		 * long appsum=0; Income
		 * appIncome=projectService.getIncomeById(incomeId); List<Appropriation>
		 * appsp=projectService.getAppropriationByIncome(incomeId); for(int
		 * i=0;i<appsp.size();i++) appsum+=appsp.get(i).getAppropriation();
		 * appsum=appIncome.getIncome()-appsum;
		 * if(appropriation>(appIncome.getIncome()-appsum)){
		 * request.setAttribute("info", "无法拨款，拨款金额大于到款金额 ");
		 * request.setAttribute("pagePath", "/page/appropriation/addAppro.jsp");
		 * }
		 */
//		double sum = 0;
		BigDecimal bgsum=new BigDecimal(0.0).setScale(2) ;
		
		for (Income i : ins) {
//			sum += i.getIncome();
			bgsum=bgsum.add(new BigDecimal(i.getIncome()));
//			System.out.println(bgsum.toString());
		}
		for (Appropriation app : apps) {
//			sum -= app.getAppropriation();
			bgsum=bgsum.subtract(new BigDecimal(app.getAppropriation()));
		}
//		String sum1=Double.toString(sum);
		
//		System.out.println("sum="+sum1+"  bgsum="+bgsum.toString());		
		request.setAttribute("project", p);
		request.setAttribute("incomes", ins);
//		request.setAttribute("sum", sum1);
		request.setAttribute("bgsum", bgsum);
		// request.setAttribute("appsum", appsum);
		return "AddAppro";
	}

	public String continueAppropriation() throws BusinessException,
			DAOException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Appropriation ap = projectService.getAppropriationById(ID);
		List<Income> ins = projectService.getIncomeByProject(ap.getProject()
				.getInnerCode());
		List<Appropriation> apps = projectService.getAppropriationByProject(ap
				.getProject().getInnerCode());
		long sum = 0;
		for (Income i : ins) {
			sum += i.getIncome();
		}
		for (Appropriation app : apps) {
			sum -= app.getAppropriation();
		}
		request.setAttribute("sum", sum);
		request.setAttribute("appropriation", ap);
		return "ContinueAppro";
	}

	public String doContinueAppropriation() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(nbbh);
		List<Income> ins = projectService.getIncomeByProject(nbbh);
		List<Appropriation> apps = projectService
				.getAppropriationByProject(nbbh);
		long sum = 0;
		for (Income i : ins) {
			sum += i.getIncome();
		}
		for (Appropriation app : apps) {
			sum -= app.getAppropriation();
		}
		if (sum < appropriation) {
			request.setAttribute("info", "无法拨款，累计拨款金额大于累计到款金额！");
			return ERROR;
		}
		Income in = projectService.getIncomeById(incomeId);
		Appropriation ap = new Appropriation();

		if (p.getStartDate().getYear() == approDate.getYear())
			p.setYearGrant(p.getYearGrant() + appropriation);
		p.setTotalGrant(p.getTotalGrant() + appropriation);
		p.setRiskFee(p.getRiskFee() + riskFee);
		projectService.update(p);

		ap.setProject(p);
		ap.setAppropriateID(appropriateID);
		ap.setIncome(in);
		ap.setChargePerson(p.getProLeader());
		/*
		 * List<Appropriation> appros =
		 * projectService.getAppropriationByAppropriationID(appropriateID); int
		 * max = appros.get(0).getApproCount(); for(Appropriation appro :
		 * appros) if(max<appro.getApproCount()) max = appro.getApproCount();
		 * ap.setApproCount(max+1);
		 */
		ap.setApproCount(conAppCount);
		ap.setAppropriation(appropriation);
		ap.setApproDate(approDate);
		ap.setDepartment(department);
		ap.setSchoolFeePer(schoolFeePer);
		ap.setSchoolFee(schoolFee);
		ap.setCollegeFeePer(collegeFeePer);
		ap.setCollegeFee(collegeFee);
		ap.setCapitalAccount(capitalAccount);
		ap.setCapitalAccountPer(capitalAccountPer);
		ap.setRiskFeePer(riskFeePer);
		ap.setRiskFee(riskFee);
		ap.setCreateDate(new Date());
		ap.setCreater(teacher);
		ap.setRemark(remark);
		projectService.saveAppropriation(ap);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Appropriation> list = projectService.getAppropriationByProject(
				pagination, "all", nbbh);
		request.setAttribute("appropriations", list);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageAppropriationByProject&nbbh="
						+ nbbh + "&year=all");
		return SUCCESS;
	}

	public String updateAppropriationIndex() throws BusinessException,
			DAOException, UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Appropriation ap = projectService.getAppropriationById(ID);
		List<Income> ins = projectService.getIncomeByProject(ap.getProject()
				.getInnerCode());
		List<Appropriation> apps = projectService.getAppropriationByProject(ap
				.getProject().getInnerCode());
		long sum = 0;
		for (Income i : ins) {
			sum += i.getIncome();
		}
		for (Appropriation app : apps) {
			sum -= app.getAppropriation();
		}
		request.setAttribute("sum", sum);
		request.setAttribute("appropriation", ap);
		return "UpdateAppro";
	}

	public String updateAppropriation() throws Exception {
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
					+ "）不应具有登记拨款权限！如有疑问，请联系管理员。");
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
		Appropriation ap = projectService.getAppropriationById(ID);
		List<Appropriation> applist = projectService
				.getAppropriationByIncome(ap.getIncome().getIncomeId());
		double appSum = 0;
		double mark = 0;
		for (int i = 0; i < applist.size(); i++) {
			appSum += applist.get(i).getAppropriation();
		}
		mark = ap.getIncome().getIncome()
				- (appSum - ap.getAppropriation() + appropriation);
		if (mark < 0) {
			System.out
					.println(ap.getIncome().getIncome() + "-(" + appSum + "-"
							+ ap.getAppropriation() + "+" + appropriation
							+ ")=" + mark);
			request.setAttribute("info",
					"拨款金额过大，修改后拨款总额将超过相关到款金额，请返回上一页检查修改拨款额！");
			return ERROR;
		}

		ap.setAppropriation(appropriation);
		ap.setApproDate(approDate);
		ap.setDepartment(department);
		ap.setCapitalAccount(capitalAccount);
		ap.setCapitalAccountPer(capitalAccountPer);
		ap.setCollegeFeePer(collegeFeePer);
		ap.setCollegeFee(collegeFee);
		ap.setSchoolFeePer(schoolFeePer);
		ap.setSchoolFee(schoolFee);
		
		ap.setZjjf(zjjf);
		ap.setFwsyfbl(fwsyfbl);
		ap.setFwsyf(fwsyf);
		ap.setYqsbsyfbl(yqsbsyfbl);
		ap.setYqsbsyf(yqsbsyf);
		ap.setSdnfbl(sdnfbl);
		ap.setSdnf(sdnf);
		ap.setKyjxsybl(kyjxsybl);
		ap.setKyjxsy(kyjxsy);
		
		BigDecimal []bd={
				new BigDecimal(schoolFee),
				new BigDecimal(collegeFee),
				new BigDecimal(fwsyf),
				new BigDecimal(yqsbsyf),
				new BigDecimal(sdnf),
				new BigDecimal(kyjxsy)
				};
				BigDecimal bdtemp=new BigDecimal(0);
				
				for(int i=0;i<bd.length;i++)
					bdtemp=bdtemp.add(bd[i]);
		jjhj=bdtemp.doubleValue();
		ap.setJjhj(jjhj);
		
		ap.setRiskFeePer(riskFeePer);
		ap.setRiskFee(riskFee);
		ap.setRemark(remark);
		ap.setApproCount(ap.getApproCount());
		if(teacher.getVerity().equals("打开"))
		{
			if (RecordSetting.getRecordSetting()) {
				ap.setID(0);
				ap.setDelFlg(true);
				ap = projectService.saveAppropriation(ap);
	
				Record rd = new Record();
				rd.setApplyDate(new Date());
				rd.setApplyer(teacher);
				rd.setDoType(ConstData.ApplyForUpdate);
				rd.setIDType(true);
				rd.setNewId("" + ap.getID());
				rd.setOldId("" + ID);
				rd.setCause(cause);
				rd.setTableName("Appropriation");
				projectService.saveRecord(rd);
				projectService.lockProject(ap.getProject().getInnerCode());
				request.setAttribute("info", "已发送修改申请，请等待管理员审核！");
			} else {
				Project p = ap.getProject();
				double money = ap.getAppropriation() - appropriation;
				double capitalMoney = ap.getCapitalAccount() - capitalAccount;
				if (new Date().getYear() == ap.getApproDate().getYear()) {
					p.setYearGrant(p.getYearGrant() - money);
					p.setYearCapitalAccount(p.getYearCapitalAccount()
							- capitalMoney);
				}
				p.setTotalCapitalAccount(p.getTotalCapitalAccount() - capitalMoney);
				p.setTotalGrant(p.getTotalGrant() - money);
				p.setRiskFee(p.getRiskFee() - ap.getRiskFee() + riskFee);
				projectService.update(p);
				projectService.updateAppropriation(ap);
				request.setAttribute("info", "更新成功！");
			}
		}else {
			Project p = ap.getProject();
			double money = ap.getAppropriation() - appropriation;
			double capitalMoney = ap.getCapitalAccount() - capitalAccount;
			if (new Date().getYear() == ap.getApproDate().getYear()) {
				p.setYearGrant(p.getYearGrant() - money);
				p.setYearCapitalAccount(p.getYearCapitalAccount()
						- capitalMoney);
			}
			p.setTotalCapitalAccount(p.getTotalCapitalAccount() - capitalMoney);
			p.setTotalGrant(p.getTotalGrant() - money);
			p.setRiskFee(p.getRiskFee() - ap.getRiskFee() + riskFee);
			projectService.update(p);
			projectService.updateAppropriation(ap);
			request.setAttribute("info", "更新成功！");
		}

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Appropriation> list = projectService.getAppropriationByProject(
				pagination, "all", innerCode);
		request.setAttribute("appropriations", list);
		request.setAttribute("nbbh", innerCode);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageAppropriationByProject&nbbh="
						+ nbbh + "&year=all");
		return SUCCESS;
	}

	public String addAppropriation() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		System.out.println("hello");

		// 生成主键（内部编号）
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有登记拨款权限！如有疑问，请联系管理员。");
			return ERROR;
		}

		System.out.println("拨款数目：" + appropriation);
		Date date = new Date();
		int now = date.getYear() + 1900;
		appropriateID = predix + "BK-" + now + "-";
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		String lastestKey = projectService
				.getMaxAppropriationKey(appropriateID);
		if (lastestKey != null) {
			String num = lastestKey.substring(15);
			appropriateID = appropriateID
					+ (new Integer(Integer.parseInt(num) + 1001)).toString()
							.substring(1);// 不够三位补0
		} else
			appropriateID = appropriateID + "001";
		// clg添加
		double appsum = 0;
		BigDecimal bgappsum=new BigDecimal(0.0).setScale(2);
		Income appIncome = projectService.getIncomeById(incomeId);
		List<Appropriation> appsp = projectService.getAppropriationByIncome(incomeId);
		for (int i = 0; i < appsp.size(); i++)
			//appsum += appsp.get(i).getAppropriation();
			bgappsum=bgappsum.add(new BigDecimal(appsp.get(i).getAppropriation()));
		BigDecimal bgappIncome=new BigDecimal(appIncome.getIncome());
		if(appropriation>(bgappIncome.subtract(bgappsum)).doubleValue()){
		//if (appropriation > (appIncome.getIncome() - appsum)) {
			request.setAttribute("info",
					"无法拨款，拨款金额大于到款金额，偏差金额为：" + (appIncome.getIncome() - appsum)
							+ "。相关到款单号：" + appIncome.getIncomeId()
							+ "  请返回上一页重新设置拨款金额！");
			// request.setAttribute("pagePath",
			// "/page/appropriation/addAppro.jsp");
			return ERROR;
		}

		Project p = projectService.getProject(nbbh);
		List<Income> ins = projectService.getIncomeByProject(nbbh);
		List<Appropriation> apps = projectService
				.getAppropriationByProject(nbbh);
		/*double sum = 0;
		for (Income i : ins) {
			sum += i.getIncome();
		}
		for (Appropriation app : apps) {
			sum -= app.getAppropriation();
		}
		 * 
		 * */		
		
		BigDecimal bgsum=new BigDecimal(0.0).setScale(2) ;	
		for (Income i : ins) {
			bgsum=bgsum.add(new BigDecimal(i.getIncome()));
		}
		for (Appropriation app : apps) {
			bgsum=bgsum.subtract(new BigDecimal(app.getAppropriation()));
		}
		//bgsum.setScale(2);
		System.out.println("合计"+bgsum.doubleValue()+"   拨款"+appropriation);
		if (bgsum.doubleValue() < appropriation) {
			request.setAttribute("info", "无法拨款，累计拨款金额大于累计到款金额！");
			return ERROR;
		}

		DateFormat dataformat = new SimpleDateFormat("yyyy");
		String timeString = dataformat.format(approDate);
		// 保存到款至项目
		// 如果到款年份恰为当年年度，当年到款相加
		if (timeString.equals(p.getDnnd())) {
			p.setYearGrant(p.getYearGrant() + appropriation);
			// p.setYearArriveMoney(p.getYearArriveMoney() + appropriation);
			p.setYearCapitalAccount(p.getYearCapitalAccount() + capitalAccount);
		}

		/*
		 * if(new Date().getYear()==approDate.getYear()){
		 * p.setYearGrant(p.getYearGrant() + appropriation);
		 * p.setYearCapitalAccount(p.getYearCapitalAccount() + capitalAccount);
		 * }
		 */
		p.setTotalCapitalAccount(p.getTotalCapitalAccount() + capitalAccount);
		p.setTotalGrant(p.getTotalGrant() + appropriation);
		p.setRiskFee(p.getRiskFee() + riskFee);
		projectService.update(p);

		// 生成拨款次数
		Pagination pa = new Pagination();
		pa.setPage(1);
		pa.setSize(1);
		List<Appropriation> appolist = projectService
				.getAppropriationByProject(pa, String.valueOf(now), nbbh);
		int apponum = 0;
		if (appolist == null || appolist.size() == 0)
			;
		else {
			// 寻找最大的到款次数
			for (Appropriation sample : appolist) {
				if (sample.getApproCount() > apponum) {
					apponum = sample.getApproCount();
				}
			}
		}
		apponum++;

		Income in = projectService.getIncomeById(request
				.getParameter("incomeId"));
		Appropriation ap = new Appropriation();
		ap.setProject(p);
		ap.setAppropriateID(appropriateID);
		ap.setIncome(in);
		ap.setChargePerson(p.getProLeader());
		ap.setApproCount(apponum);
		ap.setAppropriation(appropriation);
		ap.setApproDate(approDate);
		ap.setDepartment(department);
		ap.setSchoolFeePer(schoolFeePer);
		ap.setSchoolFee(schoolFee);
		ap.setCollegeFeePer(collegeFeePer);
		ap.setCollegeFee(collegeFee);
		ap.setCapitalAccount(capitalAccount);
		ap.setCapitalAccountPer(capitalAccountPer);
		ap.setRiskFeePer(riskFeePer);
		ap.setRiskFee(riskFee);
		/**
		 * 2016年1月28日添加房屋使用费等四项扣费及比例
		 * 开始
		 */
		ap.setFwsyf(fwsyf);
		ap.setFwsyfbl(fwsyfbl);
		ap.setYqsbsyf(yqsbsyf);
		ap.setYqsbsyfbl(yqsbsyfper);
		System.out.println("仪器设备使用费比例："+yqsbsyfbl+"  "+yqsbsyfper);
		ap.setSdnf(sdnf);
		ap.setSdnfbl(sdnfbl);
		ap.setKyjxsy(kyjxsy);
		ap.setKyjxsybl(kyjxsybl);
		ap.setZjjf(zjjf);
		 
		BigDecimal []bd={
		new BigDecimal(schoolFee),
		new BigDecimal(collegeFee),
		new BigDecimal(fwsyf),
		new BigDecimal(yqsbsyf),
		new BigDecimal(sdnf),
		new BigDecimal(kyjxsy)
		};
		BigDecimal bdtemp=new BigDecimal(0);
		
		for(int i=0;i<bd.length;i++)
			bdtemp=bdtemp.add(bd[i]);
		jjhj=bdtemp.doubleValue();
		ap.setJjhj(jjhj);
		/**
		 * 结束
		 */
		System.out.println(capitalAccount);
		System.out.println(riskFee + "   " + ap.getAppropriation());
		ap.setCreateDate(new Date());
		ap.setCreater(teacher);
		ap.setRemark(remark);
		ap = projectService.saveAppropriation(ap);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Appropriation> list = projectService.getAppropriationByProject(
				pagination, "all", nbbh);
		request.setAttribute("appropriations", list);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageAppropriationByProject&nbbh="
						+ nbbh + "&year=all");
		return SUCCESS;
	}

	public String deleteAppropriation() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		cause = java.net.URLDecoder.decode(cause, "UTF-8");
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Appropriation ap = projectService.getAppropriationById(ID);
		if(teacher.getVerity().equals("打开")){
			if (RecordSetting.getRecordSetting()) {
				Record rd = new Record();
				rd.setApplyDate(new Date());
				rd.setApplyer(teacher);
				rd.setDoType(ConstData.ApplyForDelete);
				rd.setIDType(true);
				rd.setOldId("" + ap.getID());
				rd.setCause(cause);
				rd.setTableName("Appropriation");
				projectService.saveRecord(rd);
				projectService.lockProject(ap.getProject().getInnerCode());
				request.setAttribute("info", "已发送删除申请，请等待管理员审核！");
			} else {
				Project p = ap.getProject();
				if (new Date().getYear() == ap.getApproDate().getYear()) {
					p.setYearGrant(p.getYearGrant() - ap.getAppropriation());
					p.setYearCapitalAccount(p.getYearCapitalAccount()
							- ap.getCapitalAccount());
				}
				p.setTotalCapitalAccount(p.getTotalCapitalAccount()
						- ap.getCapitalAccount());
				p.setTotalGrant(p.getTotalGrant() - ap.getAppropriation());
				p.setRiskFee(p.getRiskFee() - ap.getRiskFee());
				projectService.update(p);
				projectService.deleteAppropriation(ID);
				request.setAttribute("info", "删除成功！");
			}
		}else{
			Project p = ap.getProject();
			if (new Date().getYear() == ap.getApproDate().getYear()) {
				p.setYearGrant(p.getYearGrant() - ap.getAppropriation());
				p.setYearCapitalAccount(p.getYearCapitalAccount()
						- ap.getCapitalAccount());
			}
			p.setTotalCapitalAccount(p.getTotalCapitalAccount()
					- ap.getCapitalAccount());
			p.setTotalGrant(p.getTotalGrant() - ap.getAppropriation());
			p.setRiskFee(p.getRiskFee() - ap.getRiskFee());
			projectService.update(p);
			projectService.deleteAppropriation(ID);
			request.setAttribute("info", "删除成功！");
		}

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Appropriation> list = projectService.getAppropriationByProject(
				pagination, "all", ap.getProject().getInnerCode());
		// List<Appropriation> list =
		// projectService.getAppropriationByProject(pagination, "all", nbbh);
		request.setAttribute("appropriations", list);
		request.setAttribute("nbbh", ap.getProject().getInnerCode());
		// request.setAttribute("nbbh", nbbh);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageAppropriationByProject&nbbh="
						+ ap.getProject().getInnerCode() + "&year=all");
		// request.setAttribute("pagiUrl",
		// "mAppropriation.action?method=manageAppropriationByProject&nbbh="+innerCode+"&year=all");
		return SUCCESS;
	}

	public String showAppropriation() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Appropriation ap = projectService.getAppropriationById(ID);
		request.setAttribute("appropriation", ap);
		return "ShowAppro";
	}

	public String manageAppropriations() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		String _s = request.getParameter("s");
		String s = null;
		if (_s != null) {
			s = java.net.URLDecoder.decode(_s);
			// 此处提取内部编号 支持页面向上向下翻 否则 页面会跳至查询页面
			int position = s.indexOf("project.innerCode");
			if (position != -1) {
				String sSplit[] = s.substring(position + 1).split("'");
				if (sSplit.length >= 2) {
					String nbbh = sSplit[1];
					if (nbbh != null) {
						request.setAttribute("nbbh", nbbh);
					}
				}
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
		List<Appropriation> list = projectService.getAppropriations(predix,
				pagination, year, s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mAppropriation.action?method=manageAppropriations&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAppropriation.action?method=manageAppropriations");
		request.setAttribute("appropriations", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return SUCCESS;
	}

	public String searchAppropriation() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		String _s = request.getParameter("s");
		String s = null;
		if (_s != null)
			s = java.net.URLDecoder.decode(_s);

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

		List<Appropriation> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getAppropriations(predix, pagination, "all",
					s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getAppropriations(predix, pagination,
					preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mAppropriation.action?method=manageAppropriations&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAppropriation.action?method=manageAppropriations");
		request.setAttribute("appropriations", list);
		if (s != null)
			request.setAttribute("s", s);
		return SUCCESS;
	}

	/**
	 * @return
	 * @throws BusinessException
	 * @throws DAOException
	 */
	public String exportToExcel() throws BusinessException,
			UnsupportedEncodingException, DAOException {
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
		List<Appropriation> list;
		if (nbbh == null)
			list = projectService.getAppropriations(predix, year, s);
		else
			list = projectService.getAppropriationsByProjectAndYear(nbbh, year,
					s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("Appropriation");

		// 统计页面上显示的列有多少个
		int pfbSize = 0;
		for (FieldBean pfb : incbs) {
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
		if (list != null)
			listSize = list.size();
		row = sheet.createRow((short) rowNum++);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("长春理工大学" + teacher.getOrg().getName() + "拨款导出");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(rowNum - 1, (short) 0, rowNum - 1,
				(short) (pfbSize - 1)));
		int j = 0;
		row = sheet.createRow((short) rowNum++);
		for (FieldBean incb : incbs) {

			if (!incb.getDisplay()) {
				continue;
			}

			cell = row.createCell((short) j++);
			cell.setEncoding((short) 1);
			cell.setCellValue(incb.getName());
			//if (j >= 26)
			//	break;
		}

		for (int i = 0; i < listSize; i++) {
			Appropriation ap = list.get(i);
			row = sheet.createRow((short) rowNum++);

			j = 0;
			for (FieldBean incb : incbs) {

				if (!incb.getDisplay()) {
					continue;
				}

				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				String temp = FieldsControl.doVoGetMethod(ap, incb.getCode());
			/*	if (incb.getCode().equals("appropriation")
						|| incb.getCode().equals("schoolFee")
						|| incb.getCode().equals("collegeFee")
						|| incb.getCode().equals("capitalAccount")
						|| incb.getCode().equals("riskFee")
						|| incb.getCode().equals("yqsbsyf")
						|| incb.getCode().equals("sdnf")
						|| incb.getCode().equals("fwsyf")
						|| incb.getCode().equals("kyjxsy")
						|| incb.getCode().equals("jjhj")
						|| incb.getCode().equals("zjjf")
						)
					cell.setCellValue((new Float(temp)).doubleValue());
				else*/
					cell.setCellValue(temp);
			//	if (j >= 26)
			//		break;
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=AppropriationExport.xls");
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

	// 拨款单每页最多显示12个款项
	public String exportStandard() throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Appropriation> list = projectService
				.getAppropriationByAppropriationID(appropriateID);

		// 拨款单导出方式
		if (ConstData.Invoice_Current == ConstData.Invoice_Export_PDF) {
			return exportStandardPdf(teacher, list);
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setProtect(true);
		HSSFPrintSetup hps = sheet.getPrintSetup();
		sheet.setProtect(true);
		hps.setPaperSize((short) 9);
		hps.setLandscape(true);
		sheet.setHorizontallyCenter(true);
		sheet.setVerticallyCenter(true);

		HSSFRow row;
		HSSFCell cell;
		sheet.setDefaultRowHeight((short) 19);
		sheet.setColumnWidth((short) 0, (short) (35 * 1000 / 27));
		sheet.setColumnWidth((short) 1, (short) (150 * 1000 / 27.5));
		sheet.setColumnWidth((short) 2, (short) (70 * 1000 / 27));
		sheet.setColumnWidth((short) 3, (short) (85 * 1000 / 27));
		sheet.setColumnWidth((short) 4, (short) (150 * 1000 / 27.5));
		sheet.setColumnWidth((short) 5, (short) (80 * 1000 / 27));
		sheet.setColumnWidth((short) 6, (short) (85 * 1000 / 27));
		sheet.setColumnWidth((short) 7, (short) (70 * 1000 / 27));
		sheet.setColumnWidth((short) 8, (short) (84 * 1000 / 27));
		sheet.setColumnWidth((short) 9, (short) (160 * 1000 / 27));
		sheet.setColumnWidth((short) 10, (short) (55 * 1000 / 29.5));

		// 报表头部
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontName("华文隶书");
		font.setFontHeightInPoints((short) 30);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		row = sheet.createRow((short) 0);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("长春理工大学" + teacher.getOrg().getName() + "拨款单");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(0, (short) 0, 2, (short) 9));

		// 拨款单号
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		row = sheet.createRow((short) 4);
		cell = row.createCell((short) 7);
		cell.setEncoding((short) 1);
		cell.setCellValue("拨款单号： " + appropriateID);
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(4, (short) 7, 4, (short) 10));
		// 单位日期
		row = sheet.createRow((short) 5);
		cell = row.createCell((short) 7);
		cell.setEncoding((short) 1);
		cell.setCellValue("单位：元  打印日期：" + new Date().toLocaleString());
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(5, (short) 7, 5, (short) 10));
		// 主表head
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setFont(font);

		row = sheet.createRow((short) 7);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("序号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setEncoding((short) 1);
		cell.setCellValue("项目内部编号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setEncoding((short) 1);
		cell.setCellValue("拨款金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setEncoding((short) 1);
		cell.setCellValue("学校管理费");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setEncoding((short) 1);
		cell.setCellValue("固定资产损耗费");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setEncoding((short) 1);
		cell.setCellValue("学院管理费");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setEncoding((short) 1);
		cell.setCellValue("预留风险金");
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setEncoding((short) 1);
		cell.setCellValue("项目组长");
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setEncoding((short) 1);
		cell.setCellValue("拨款日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setEncoding((short) 1);
		cell.setCellValue("所属单位");
		cell.setCellStyle(style);

		cell = row.createCell((short) 10);
		cell.setEncoding((short) 1);
		cell.setCellValue("经手人");
		cell.setCellStyle(style);
		// 表格主体
		int rowNum = 8;
		double allAppro = 0;
		double allSchoolFee = 0;
		double allCollegeFee = 0;
		double allRiskFee = 0;
		for (Appropriation appro : list) {
			row = sheet.createRow((short) rowNum);
			cell = row.createCell((short) 0);
			cell.setEncoding((short) 1);
			cell.setCellValue(rowNum - 7);
			cell.setCellStyle(style);

			cell = row.createCell((short) 1);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getProject().getInnerCode());
			cell.setCellStyle(style);

			cell = row.createCell((short) 2);
			cell.setEncoding((short) 1);
			System.out.println("hello");
			cell.setCellValue(appro.getAppropriation());
			allAppro += appro.getAppropriation();
			cell.setCellStyle(style);

			cell = row.createCell((short) 3);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getSchoolFee());
			allSchoolFee += appro.getSchoolFee();
			cell.setCellStyle(style);

			cell = row.createCell((short) 4);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getCapitalAccount());
			cell.setCellStyle(style);

			cell = row.createCell((short) 5);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getCollegeFee());
			allCollegeFee += appro.getCollegeFee();
			cell.setCellStyle(style);

			cell = row.createCell((short) 6);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getRiskFee());
			allRiskFee += appro.getRiskFee();
			cell.setCellStyle(style);

			cell = row.createCell((short) 7);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getProject().getProLeader());
			cell.setCellStyle(style);

			cell = row.createCell((short) 8);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getApproDate().toString());
			cell.setCellStyle(style);

			cell = row.createCell((short) 9);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getDepartment());
			cell.setCellStyle(style);

			cell = row.createCell((short) 10);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getCreater().getName());
			cell.setCellStyle(style);

			rowNum++;
		}
		// 合计
		row = sheet.createRow((short) rowNum);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("合计");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setEncoding((short) 1);
		cell.setCellValue(allAppro);
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 3);
		cell.setEncoding((short) 1);
		cell.setCellValue(allSchoolFee);
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 4);
		cell.setEncoding((short) 1);
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setEncoding((short) 1);
		cell.setCellValue(allCollegeFee);
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setEncoding((short) 1);
		cell.setCellValue(allRiskFee);
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setCellStyle(style);

		cell = row.createCell((short) 10);
		cell.setCellStyle(style);

		// 备注
		row = sheet.createRow((short) 23);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("备注:");
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		style.setFont(font);
		cell.setCellStyle(style);

		// 签字信息
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 18);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		row = sheet.createRow(25);
		cell = row.createCell((short) 1);
		cell.setEncoding((short) 1);
		cell.setCellValue("科技处：");
		cell.setCellStyle(style);

		row = sheet.createRow(25);
		cell = row.createCell((short) 4);
		cell.setEncoding((short) 1);
		cell.setCellValue("财务经办人：");
		cell.setCellStyle(style);

		// 注：
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");
		style.setFont(font);

		row = sheet.createRow(28);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("注：");
		cell.setCellStyle(style);

		row = sheet.createRow(28);
		cell = row.createCell((short) 1);
		cell.setEncoding((short) 1);
		cell.setCellValue("1、本拨款单一式6份，财务处3份，科技处、项目所属单位、项目组各1份；  ");
		sheet.addMergedRegion(new Region(26, (short) 1, 26, (short) 6));
		cell.setCellStyle(style);

		row = sheet.createRow(30);
		cell = row.createCell((short) 1);
		cell.setEncoding((short) 1);
		cell.setCellValue("2、新立项目组在拨款单下达3天内，由所属单位将项目组成员登记表交社会科学学科。");
		sheet.addMergedRegion(new Region(28, (short) 1, 28, (short) 6));
		cell.setCellStyle(style);

		// 输出
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=AppropriationExport.xls");
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

	public String searchAppropriationSpl() throws BusinessException,
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

		s += "and project.innerCode='" + nbbh + "'";
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
		List<Appropriation> list = projectService.getAppropriations(predix,
				pagination, "all", s);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageAppropriations&s=" + s);
		request.setAttribute("appropriations", list);
		request.setAttribute("s", s);
		return SUCCESS;
	}

	// 拨款单
	/*
	 * public String exportStandardPdf(Teacher teacher, List<Appropriation>
	 * list) throws Exception{ //纸张设置 //横向 //边框 Rectangle pageRectangle = new
	 * Rectangle(PageSize.A3); pageRectangle = pageRectangle.rotate(); Document
	 * document = new Document(PageSize.A3.rotate(), 50, 50, 50, 50);
	 * ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 * PdfWriter.getInstance(document, baos); //****配置**** document.open(); //标题
	 * BaseFont titleBaseFont = BaseFont.createFont("STSong-Light",
	 * "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); Font titleFont = new
	 * Font(titleBaseFont, 40); Paragraph titlePara = new Paragraph("长春理工大学" +
	 * teacher.getOrg().getName() + "拨款单", titleFont);
	 * titlePara.setAlignment(Element.ALIGN_CENTER); document.add(titlePara);
	 * 
	 * //单号 //日期 BaseFont timeBaseFont = BaseFont.createFont("STSong-Light",
	 * "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED); Font timeFont = new
	 * Font(timeBaseFont, 20); Paragraph noPara = new Paragraph("拨款单号： " +
	 * appropriateID, timeFont); noPara.setAlignment(Element.ALIGN_RIGHT);
	 * document.add(noPara); Paragraph timePara = new
	 * Paragraph("单位：元　　打印日期："+new Date().toLocaleString(), timeFont);
	 * timePara.setAlignment(Element.ALIGN_RIGHT); document.add(timePara); //表格
	 * //=====================================================
	 * document.add(Chunk.NEWLINE); PdfPTable table = new PdfPTable(11);
	 * table.setWidthPercentage(90);
	 * table.setHorizontalAlignment(Element.ALIGN_CENTER); BaseFont
	 * tableBaseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
	 * BaseFont.NOT_EMBEDDED); Font tableFont = new Font(tableBaseFont, 13);
	 * 
	 * int[] widths = new int[]{4, 17, 7, 8, 8, 10, 8, 6, 6, 8, 18};
	 * table.setWidths(widths);
	 * 
	 * //表头 PdfPCell cell = new PdfPCell(); Paragraph p = new Paragraph("序号",
	 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("项目内部编号", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("拨款金额", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("学校管理费", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("学院管理费", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("固定资产使用费", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("预留风险金", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("其他", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("项目组长", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("拨款日期", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("所属单位", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * //表内容 int rowNum = 1; double allAppro = 0; double allSchoolFee = 0;
	 * double allCollegeFee = 0; double allRiskFee = 0; double allCapitalAccount
	 * = 0; for(Appropriation appro : list){ cell = new PdfPCell(); p = new
	 * Paragraph(String.valueOf(rowNum), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new
	 * Paragraph(appro.getProject().getInnerCode(), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new
	 * Paragraph(String.valueOf(appro.getAppropriation()), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * allAppro+=appro.getAppropriation();
	 * 
	 * cell = new PdfPCell(); p = new
	 * Paragraph(String.valueOf(appro.getSchoolFee()), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * allSchoolFee+=appro.getSchoolFee();
	 * 
	 * cell = new PdfPCell(); p = new
	 * Paragraph(String.valueOf(appro.getCollegeFee()), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * allCollegeFee+=appro.getCollegeFee();
	 * 
	 * cell = new PdfPCell(); p = new
	 * Paragraph(String.valueOf(appro.getCapitalAccount()), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * allCapitalAccount += appro.getCapitalAccount();
	 * 
	 * cell = new PdfPCell(); p = new
	 * Paragraph(String.valueOf(appro.getRiskFee()), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * allRiskFee+=appro.getRiskFee();
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new
	 * Paragraph(appro.getProject().getProLeader(), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph(appro.getApproDate().toString(),
	 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph(appro.getDepartment(),
	 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * rowNum++; } //总计 cell = new PdfPCell(); p = new Paragraph("合计",
	 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph(String.valueOf(allAppro),
	 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph(String.valueOf(allSchoolFee),
	 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph(String.valueOf(allCollegeFee),
	 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new
	 * Paragraph(String.valueOf(allCapitalAccount), tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph(String.valueOf(allRiskFee),
	 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * cell = new PdfPCell(); p = new Paragraph("", tableFont);
	 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
	 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
	 * 
	 * 
	 * //增加到文档中 document.add(table);
	 * //===================================================== //备注等
	 * document.add(Chunk.NEWLINE); BaseFont otherBaseFont =
	 * BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
	 * BaseFont.NOT_EMBEDDED); Font otherFont = new Font(otherBaseFont, 30);
	 * Paragraph otherPara = new Paragraph("备注:", otherFont);
	 * otherPara.setIndentationLeft(60); document.add(otherPara); Paragraph
	 * other2Para = new
	 * Paragraph("经手人："+teacher.getName()+"　　主管处长：　　　　处长：　　　　财务经办人：",
	 * otherFont); other2Para.setIndentationLeft(100); document.add(other2Para);
	 * document.add(Chunk.NEWLINE); BaseFont other3BaseFont =
	 * BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
	 * BaseFont.NOT_EMBEDDED); Font other3Font = new Font(other3BaseFont, 20);
	 * Paragraph other3Para = new
	 * Paragraph("注：本拨款单一式6份，财务处3份，科技处、项目所属单位、项目组各1份。", other3Font);
	 * other3Para.setIndentationLeft(60); document.add(other3Para); //****关闭****
	 * document.close();
	 * 
	 * 
	 * //输出 response.setContentType("application/pdf");
	 * response.setHeader("Content-disposition",
	 * "inline;filename=AppropriationExport.pdf"); OutputStream out; try {
	 * response.setContentLength(baos.toByteArray().length); out =
	 * response.getOutputStream(); out.write(baos.toByteArray()); out.flush();
	 * out.close(); } catch (IOException e) { e.printStackTrace(); } return
	 * null; }
	 */

	// 拨款单实际输出函数
	public String exportStandardPdf(Teacher teacher, List<Appropriation> list)
			throws Exception {
		// 纸张设置
		// 横向
		// 边框
		Rectangle pageRectangle = new Rectangle(PageSize.A3);
		pageRectangle = pageRectangle.rotate();
		Document document = new Document(PageSize.A3.rotate(), 50, 50, 100, 50);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		// ****配置****
		document.open();
		Image image = Image.getInstance(request.getRealPath("/") + "cust6.jpg");
		image.scalePercent(100f);
		image.setAbsolutePosition(0, 0);
		//image.setAlignment(Image.UNDERLYING);
		document.add(image);
		// 标题
		BaseFont titleBaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font titleFont = new Font(titleBaseFont, 40);
		Paragraph titlePara = new Paragraph("长春理工大学"
				+ teacher.getOrg().getName() + "拨款单", titleFont);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		document.add(titlePara);
		document.add(Chunk.NEWLINE);
		// 单号
		// 日期
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd                   ");
		String dyDate = df.format(new Date());
		Appropriation curApp = (Appropriation) list.get(0);
		// Paragraph timePara = new Paragraph("单位：元       打印日期："+dyDate,
		// timeFont);

		BaseFont timeBaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font timeFont = new Font(timeBaseFont, 16);
		Paragraph noPara = new Paragraph(
				"                 拨款单号：     "
						+ appropriateID
						+ "               第 "
						+ curApp.getApproCount()
						+ " 次拨款                                                                                                          单位：元       拨款日期："
						+ curApp.getApproDate(), timeFont);
		noPara.setAlignment(Element.ALIGN_LEFT);
		document.add(noPara);

		/*
		 * DateFormat df = new
		 * SimpleDateFormat("yyyy-MM-dd                   "); String
		 * dyDate=df.format(new Date()); Paragraph timePara = new
		 * Paragraph("单位：元       打印日期："+dyDate, timeFont);
		 * timePara.setAlignment(Element.ALIGN_RIGHT); document.add(timePara);
		 */

		BaseFont extraFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font extraTimeFont = new Font(extraFont, 3);
		Paragraph extraPara = new Paragraph("     ", extraTimeFont);
		// timePara.setAlignment(Element.ALIGN_RIGHT);
		document.add(extraPara);
		// document.add(Chunk.)
		// 表格
		// =====================================================
		// document.add(Chunk.NEWLINE);
		PdfPTable table = new PdfPTable(13);//8  9
		table.setWidthPercentage(95);//90  78
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		BaseFont tableBaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableFont = new Font(tableBaseFont, 16);
		Font tableFont_2 = new Font(tableBaseFont, 20);
		int[] widths = new int[]{3,10,7,7,7,7,7,7,7,7,8,7,10};//{3,10,7,7,7,7,7,7,7,7,5,7,10};// { 4, 16, 8, 8, 8, 8, 8, 12 };
		//{ 4, 16, 8, 8, 8, 12, 8, 8, 12 };
		// new
																	// int[]{4,
																	// 17, 7, 8,
																	// 8, 10, 7,
																	// 7, 6, 8,
																	// 18};
		table.setWidths(widths);

		// 表头
		PdfPCell cell = new PdfPCell();
		Paragraph p = new Paragraph("序号", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setRowspan(2);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("项目内部编号", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setRowspan(2);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("拨款金额", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setRowspan(2);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		cell = new PdfPCell();
		p = new Paragraph("直接经费", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setRowspan(2);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		cell = new PdfPCell();
//		Font tableFont_2 = new Font(tableBaseFont, 20);
		p = new Paragraph("间接经费", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setColspan(7);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		cell = new PdfPCell();
		p = new Paragraph("项目组长", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setRowspan(2);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
/*
		cell = new PdfPCell();
		p = new Paragraph("拨款日期", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setRowspan(2);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
*/
		cell = new PdfPCell();
		p = new Paragraph("所属单位", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setRowspan(2);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		

		cell = new PdfPCell();
		p = new Paragraph("校管理费", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("院管理费", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);


		cell = new PdfPCell();
		p = new Paragraph("房屋使用费", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		

		cell = new PdfPCell();
		p = new Paragraph("仪器设备使用费", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		

		cell = new PdfPCell();
		p = new Paragraph("水电暖费", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		

		cell = new PdfPCell();
		p = new Paragraph("绩效支出和收益", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		cell = new PdfPCell();
		p = new Paragraph("合计", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		//固定资产使用费 改成 固定资产损耗费  后来又不要了 
		/*cell = new PdfPCell();
		p = new Paragraph("固定资产损耗费", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);*/

		/*
		 * cell = new PdfPCell(); p = new Paragraph("其他", tableFont);
		 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
		 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
		 * 
		 * cell = new PdfPCell(); p = new Paragraph("其他", tableFont);
		 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
		 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
		 */


		// 表内容
		int rowNum = 1;
		double allAppro = 0;
		double allSchoolFee = 0;
		double allCollegeFee = 0;
		double allRiskFee = 0;
		double allCapitalAccount = 0;
		double allZjjf=0;//合计直接经费
		double allFwsyf=0;//合计房屋使用费
		double allYqsbsyf=0;//合计仪器设备使用费
		double allSdnf=0;//合计水电暖费
		double allJxSy=0;//合计绩效和收益
		double alljjhj=0;//总计间接合计经费
		DecimalFormat format=new DecimalFormat("#########0.00");
		System.out.println("hh");
		for (Appropriation appro : list) {
			cell = new PdfPCell();
			p = new Paragraph(String.valueOf(rowNum), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell();
			p = new Paragraph(appro.getProject().getInnerCode(), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell();
			//p = new Paragraph(String.valueOf(appro.getAppropriation()),tableFont);
			
			p = new Paragraph(format.format(appro.getAppropriation()),tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allAppro += appro.getAppropriation();
			
			//直接经费
			cell = new PdfPCell();
			//double zjjf=appro.getAppropriation()-appro.getCapitalAccount()-appro.getCollegeFee()-appro.getFwsyf()-appro.getKyjxsy()-appro.getSchoolFee()-appro.getSdnf()-appro.getYqsbsyf();
			p = new Paragraph(format.format(appro.getZjjf()),tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allZjjf += appro.getZjjf();
			//****
			//校管理费
			cell = new PdfPCell();
			p = new Paragraph(format.format(appro.getSchoolFee()), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allSchoolFee += appro.getSchoolFee();

			//院管理费
			cell = new PdfPCell();
			p = new Paragraph(format.format(appro.getCollegeFee()), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allCollegeFee += appro.getCollegeFee();
			
			//房屋使用费
			cell = new PdfPCell();
			p = new Paragraph(format.format(appro.getFwsyf()), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allFwsyf += appro.getFwsyf();
			
			//仪器设备使用费
			cell = new PdfPCell();
			p = new Paragraph(format.format(appro.getYqsbsyf()), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allYqsbsyf += appro.getYqsbsyf();
			
			//水电暖费
			cell = new PdfPCell();
			p = new Paragraph(format.format(appro.getSdnf()), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allSdnf += appro.getSdnf();
			
			//科研绩效支出和收益
			cell = new PdfPCell();
			p = new Paragraph(format.format(appro.getKyjxsy()), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allJxSy += appro.getKyjxsy();

			
			//科研绩效支出和收益
			cell = new PdfPCell();
			p = new Paragraph(format.format(appro.getJjhj()), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			alljjhj += appro.getJjhj();
			//固定资产使用费 改成 固定资产损耗费  后来又不要了 
			/*cell = new PdfPCell();
			p = new Paragraph(format.format(appro.getCapitalAccount()),
					tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allCapitalAccount += appro.getCapitalAccount();
*/
			/*
			 * cell = new PdfPCell(); p = new
			 * Paragraph(String.valueOf(appro.getRiskFee()), tableFont);
			 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * table.addCell(cell); allRiskFee+=appro.getRiskFee();
			 */
			// 其他1
			/*
			 * cell = new PdfPCell(); p = new Paragraph("", tableFont);
			 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * table.addCell(cell);
			 * 
			 * cell = new PdfPCell(); p = new Paragraph("", tableFont);
			 * p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * table.addCell(cell);
			 */
			cell = new PdfPCell();
			p = new Paragraph(appro.getProject().getProLeader(), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

		/*	cell = new PdfPCell();
			p = new Paragraph(appro.getApproDate().toString(), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
*/
			cell = new PdfPCell();
			p = new Paragraph(appro.getDepartment(), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			rowNum++;
		}
		// 总计
		cell = new PdfPCell();
		p = new Paragraph("总计", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
        
		//合计拨款
		cell = new PdfPCell();
		/*String allApproString = new DecimalFormat("###,###,###.##")
				.format(allAppro);
		p = new Paragraph(allApproString, tableFont);*/
		
		p = new Paragraph(format.format(allAppro), tableFont);
		
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		//合计直接经费
		cell = new PdfPCell();
		p = new Paragraph(format.format(allZjjf), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		//合计校管理费
		cell = new PdfPCell();
		p = new Paragraph(format.format(allSchoolFee), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		/*cell = new PdfPCell();
		p = new Paragraph(String.valueOf(appro.getCollegeFee()), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		allCollegeFee += appro.getCollegeFee();*/

		/*cell = new PdfPCell();
		String allCollegeFeeString = new DecimalFormat("###,###,###.##")
				.format(allCollegeFee);
		p = new Paragraph(String.valueOf(allCollegeFeeString), tableFont);
		// p = new Paragraph(String.valueOf(allCollegeFee), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);*/
		//合计院管理费
		cell = new PdfPCell();
		p = new Paragraph(format.format(allCollegeFee), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		//合计房屋使用费
		cell = new PdfPCell();
		p = new Paragraph(format.format(allFwsyf), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		//仪器设备使用费
		cell = new PdfPCell();
		p = new Paragraph(format.format(allYqsbsyf), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		//水电暖费
		cell = new PdfPCell();
		p = new Paragraph(format.format(allSdnf), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		//科研绩效和收益
		cell = new PdfPCell();
		p = new Paragraph(format.format(allJxSy), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		//科研绩效和收益
		cell = new PdfPCell();
		p = new Paragraph(format.format(alljjhj), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		/*cell = new PdfPCell();
		String allCapitalAccountString = new DecimalFormat("###,###,###.##")
				.format(allCapitalAccount);
		p = new Paragraph(String.valueOf(allCapitalAccountString), tableFont);*/
		
		//固定资产使用费 改成 固定资产损耗费  后来又不要了   合计
		/*p = new Paragraph(format.format(allCapitalAccount), tableFont);
		
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);*/

		/*
		 * cell = new PdfPCell(); p = new Paragraph(String.valueOf(allRiskFee),
		 * tableFont); p.setAlignment(Element.ALIGN_CENTER); cell.addElement(p);
		 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE); table.addCell(cell);
		 */
		/*
		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);*/

		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

	/*	cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
*/
		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		// 增加到文档中
		document.add(table);
		// =====================================================
		// 备注等
		document.add(Chunk.NEWLINE);
		BaseFont otherBaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font otherFont = new Font(otherBaseFont, 20);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		Paragraph otherPara = new Paragraph("备注:", otherFont);
		otherPara.setIndentationLeft(60);
		otherPara.setSpacingBefore(130);
		document.add(otherPara);
		Paragraph other2Para = new Paragraph(
				"处长：                          主管处长：                          财务经办人：                          "
						+ "制表：" + teacher.getName(), otherFont);
		other2Para.setIndentationLeft(150);
		document.add(other2Para);
		BaseFont other3BaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font other3Font = new Font(other3BaseFont, 20);
		Paragraph other3Para = new Paragraph(
				"注：本拨款单一式6份，财务处3份，科技处、项目所属单位、项目组各1份。           打印日期："+dyDate, other3Font);
		other3Para.setIndentationLeft(60);
		other3Para.setSpacingBefore(50);
		document.add(other3Para);
		// ****关闭****
		document.close();

		// 输出
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition",
				"inline;filename=AppropriationExport.pdf");
		OutputStream out;
		try {
			response.setContentLength(baos.toByteArray().length);
			out = response.getOutputStream();
			out.write(baos.toByteArray());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 拨款单每页最多显示12个款项 3
	public String exportToStandard() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Appropriation> list = null;
		if (appropriateID == null)
			list = projectService.getAppropriationByProject(innerCode); //
		else
			list = projectService
					.getAppropriationByAppropriationID(appropriateID); //

		// 拨款单导出方式
		if (ConstData.Invoice_Current == ConstData.Invoice_Export_PDF) {
			return exportToStandardPdf(teacher, list);
		}
		System.out.print("haha");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		sheet.setProtect(true);
		HSSFPrintSetup hps = sheet.getPrintSetup();
		sheet.setProtect(true);
		hps.setPaperSize((short) 9);
		hps.setLandscape(true);
		sheet.setHorizontallyCenter(true);
		sheet.setVerticallyCenter(true);

		HSSFRow row;
		HSSFCell cell;
		sheet.setDefaultRowHeight((short) 19);
		sheet.setColumnWidth((short) 0, (short) (35 * 1000 / 27));
		sheet.setColumnWidth((short) 1, (short) (150 * 1000 / 27.5));
		sheet.setColumnWidth((short) 2, (short) (70 * 1000 / 27));
		sheet.setColumnWidth((short) 3, (short) (85 * 1000 / 27));
		sheet.setColumnWidth((short) 4, (short) (80 * 1000 / 27));
		sheet.setColumnWidth((short) 5, (short) (85 * 1000 / 27));
		sheet.setColumnWidth((short) 6, (short) (70 * 1000 / 27));
		sheet.setColumnWidth((short) 7, (short) (84 * 1000 / 27));
		sheet.setColumnWidth((short) 8, (short) (160 * 1000 / 27));
		sheet.setColumnWidth((short) 9, (short) (55 * 1000 / 29.5));

		// 报表头部
		HSSFCellStyle style = wb.createCellStyle();
		HSSFFont font = wb.createFont();
		font.setFontName("华文隶书");
		font.setFontHeightInPoints((short) 20);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		row = sheet.createRow((short) 0);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("长春理工大学" + teacher.getOrg().getName() + "风险金回拨单");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(0, (short) 0, 2, (short) 9));

		// 拨款单号
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		style.setFont(font);

		if (list.size() == 1) {
			row = sheet.createRow((short) 4);
			cell = row.createCell((short) 6);
			cell.setEncoding((short) 1);
			cell.setCellValue("回拨单号： " + list.get(0).getAppropriateID());
			cell.setCellStyle(style);
			sheet.addMergedRegion(new Region(4, (short) 6, 4, (short) 9));
		}
		// 单位日期
		row = sheet.createRow((short) 5);
		cell = row.createCell((short) 6);
		cell.setEncoding((short) 1);
		cell.setCellValue("单位：元  打印日期：" + new Date().toLocaleString());
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(5, (short) 6, 5, (short) 9));
		// 主表head
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("宋体");
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setFont(font);

		row = sheet.createRow((short) 7);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("序号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setEncoding((short) 1);
		cell.setCellValue("项目编号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setEncoding((short) 1);
		cell.setCellValue("项目内部编号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setEncoding((short) 1);
		cell.setCellValue("拨款金额");
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setEncoding((short) 1);
		cell.setCellValue("预留风险金");
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setEncoding((short) 1);
		cell.setCellValue("回拨单号");
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setEncoding((short) 1);
		cell.setCellValue("拨款日期");
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setEncoding((short) 1);
		cell.setCellValue("项目组长");
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setEncoding((short) 1);
		cell.setCellValue("所属单位");
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setEncoding((short) 1);
		cell.setCellValue("经手人");
		cell.setCellStyle(style);
		// 表格主体
		int rowNum = 8;
		double allAppro = 0;
		double allRiskFee = 0;
		for (Appropriation appro : list) {
			row = sheet.createRow((short) rowNum);
			// 序号
			cell = row.createCell((short) 0);
			cell.setEncoding((short) 1);
			cell.setCellValue(rowNum - 7);
			cell.setCellStyle(style);

			// 项目编号
			cell = row.createCell((short) 1);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getProject().getCode());
			cell.setCellStyle(style);

			// 项目内部编号
			cell = row.createCell((short) 2);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getProject().getInnerCode());
			cell.setCellStyle(style);

			// 拨款金额
			cell = row.createCell((short) 3);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getAppropriation());
			allAppro += appro.getAppropriation();
			cell.setCellStyle(style);

			// 预留风险金
			cell = row.createCell((short) 4);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getRiskFee());
			allRiskFee += appro.getRiskFee();
			cell.setCellStyle(style);

			// 拨款单号
			cell = row.createCell((short) 5);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getAppropriateID());
			cell.setCellStyle(style);

			// 拨款日期
			cell = row.createCell((short) 6);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getApproDate().toString());
			cell.setCellStyle(style);

			// 项目组长
			cell = row.createCell((short) 7);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getProject().getProLeader());
			cell.setCellStyle(style);

			// 所属单位
			cell = row.createCell((short) 8);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getDepartment());
			cell.setCellStyle(style);

			// 经手人
			cell = row.createCell((short) 9);
			cell.setEncoding((short) 1);
			cell.setCellValue(appro.getCreater().getName());
			cell.setCellStyle(style);

			rowNum++;
		}
		// 合计
		row = sheet.createRow((short) rowNum);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("合计");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellStyle(style);

		cell = row.createCell((short) 2);
		cell.setCellStyle(style);

		cell = row.createCell((short) 3);
		cell.setEncoding((short) 1);
		cell.setCellValue(allAppro);
		cell.setCellStyle(style);

		cell = row.createCell((short) 4);
		cell.setEncoding((short) 1);
		cell.setCellValue(allRiskFee);
		cell.setCellStyle(style);

		cell = row.createCell((short) 5);
		cell.setCellStyle(style);

		cell = row.createCell((short) 6);
		cell.setCellStyle(style);

		cell = row.createCell((short) 7);
		cell.setCellStyle(style);

		cell = row.createCell((short) 8);
		cell.setCellStyle(style);

		cell = row.createCell((short) 9);
		cell.setCellStyle(style);

		// 备注
		row = sheet.createRow((short) 23);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("备注:");
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 16);
		style.setFont(font);
		cell.setCellStyle(style);

		// 签字信息
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 18);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFont(font);

		row = sheet.createRow(25);
		cell = row.createCell((short) 1);
		cell.setEncoding((short) 1);
		cell.setCellValue("科技处：");
		cell.setCellStyle(style);

		row = sheet.createRow(25);
		cell = row.createCell((short) 4);
		cell.setEncoding((short) 1);
		cell.setCellValue("财务经办人：");
		cell.setCellStyle(style);

		// 注：
		style = wb.createCellStyle();
		font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("宋体");
		style.setFont(font);

		row = sheet.createRow(28);
		cell = row.createCell((short) 0);
		cell.setEncoding((short) 1);
		cell.setCellValue("注：");
		cell.setCellStyle(style);

		row = sheet.createRow(28);
		cell = row.createCell((short) 1);
		cell.setEncoding((short) 1);
		cell.setCellValue("1、本拨款单一式6份，财务处3份，科技处、项目所属单位、项目组各1份；  ");
		sheet.addMergedRegion(new Region(26, (short) 1, 26, (short) 6));
		cell.setCellStyle(style);

		// 输出
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=AppropriationExport.xls");
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

	// 风险金回拨单 2
	public String exportToStandardPdf(Teacher teacher, List<Appropriation> list)
			throws Exception {
		// 纸张设置
		// 横向
		// 边框
		System.out.println("hello");
		Rectangle pageRectangle = new Rectangle(PageSize.A3);
		pageRectangle = pageRectangle.rotate();
		Document document = new Document(PageSize.A3.rotate(), 50, 50, 50, 50);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);
		// ****配置****
		document.open();
		// 标题
		BaseFont titleBaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font titleFont = new Font(titleBaseFont, 40);
		Paragraph titlePara = new Paragraph("长春理工大学"
				+ teacher.getOrg().getName() + "风险金回拨单", titleFont);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		document.add(titlePara);

		// 单号
		BaseFont timeBaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font timeFont = new Font(timeBaseFont, 20);
		// 回拨单号
		Paragraph noPara = new Paragraph("回拨单号： "
				+ list.get(0).getReturnbackID(), timeFont);
		noPara.setAlignment(Element.ALIGN_RIGHT);
		document.add(noPara);

		/*
		 * if(list.size()==1) { Paragraph noPara = new Paragraph("回拨单号： " +
		 * list.get(0).getReturnbackID(), timeFont);
		 * noPara.setAlignment(Element.ALIGN_RIGHT); document.add(noPara); }
		 * else{ document.add(Chunk.NEWLINE); }
		 */

		// 日期
		String hbtime = list.get(0).getReturnBackDate().toString();
		Paragraph timePara = new Paragraph("单位：元　　回拨日期：" + hbtime, timeFont);
		timePara.setAlignment(Element.ALIGN_RIGHT);
		document.add(timePara);
		// 表格
		// =====================================================
		document.add(Chunk.NEWLINE);
		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(90);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		BaseFont tableBaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font tableFont = new Font(tableBaseFont, 13);

		int[] widths = new int[] { 5, 20, 8, 10, 14, 8, 7, 17 };
		table.setWidths(widths);

		// 表头
		PdfPCell cell = new PdfPCell();
		Paragraph p = new Paragraph("序号", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("项目内部编号", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("拨款金额", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("预留风险金", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("拨款单号", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("拨款日期", tableFont);
		// p = new Paragraph("回拨日期", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("项目组长", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("所属单位", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		// 表内容
		int rowNum = 1;
		double allAppro = 0;
		double allRiskFee = 0;
		for (Appropriation appro : list) {
			cell = new PdfPCell();
			p = new Paragraph(String.valueOf(rowNum), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			// 内部编号
			cell = new PdfPCell();
			// p = new Paragraph(appro.getProject().getCode(), tableFont);
			p = new Paragraph(appro.getProject().getInnerCode(), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell();
			p = new Paragraph(String.valueOf(appro.getAppropriation()),
					tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allAppro += appro.getAppropriation();

			cell = new PdfPCell();
			p = new Paragraph(String.valueOf(appro.getRiskFee()), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			allRiskFee += appro.getRiskFee();

			cell = new PdfPCell();
			p = new Paragraph(appro.getAppropriateID(), tableFont);
			// p = new Paragraph(appro., tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell();
			p = new Paragraph(appro.getApproDate().toString(), tableFont);
			// p = new Paragraph(appro.getReturnBackDate().toString(),
			// tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell();
			p = new Paragraph(appro.getProject().getProLeader(), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell();
			p = new Paragraph(appro.getDepartment(), tableFont);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			rowNum++;
		}
		/**
		 * 
		 */
		/*
		 * Paragraph timePara = new Paragraph("单位：元　　回拨日期："+hbtime,timeFont);
		 * timePara.setAlignment(Element.ALIGN_RIGHT); document.add(timePara);
		 */
		// 总计
		cell = new PdfPCell();
		p = new Paragraph("合计", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		// String allApproString=new
		// DecimalFormat("###,###,###.##").format(allAppro);
		// p = new Paragraph(allApproString, tableFont);
		p = new Paragraph(String.valueOf(allAppro), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph(String.valueOf(allRiskFee), tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell();
		p = new Paragraph("", tableFont);
		p.setAlignment(Element.ALIGN_CENTER);
		cell.addElement(p);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		// 增加到文档中
		document.add(table);
		// =====================================================
		// 备注等
		document.add(Chunk.NEWLINE);
		BaseFont otherBaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font otherFont = new Font(otherBaseFont, 30);
		Paragraph otherPara = new Paragraph("备注:", otherFont);
		otherPara.setIndentationLeft(60);
		document.add(otherPara);
		Paragraph other2Para = new Paragraph("经手人：" + teacher.getName()
				+ "　　处长：　　　　主管处长：　　　　财务经办人：", otherFont);
		other2Para.setIndentationLeft(100);
		document.add(other2Para);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		BaseFont other3BaseFont = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font other3Font = new Font(other3BaseFont, 20);
		Paragraph other3Para = new Paragraph(
				"注：本拨款单一式6份，财务处3份，科技处、项目所属单位、项目组各1份。", other3Font);
		other3Para.setIndentationLeft(60);
		document.add(other3Para);
		// ****关闭****
		document.close();

		// 输出
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition",
				"inline;filename=AppropriationExport.pdf");
		OutputStream out;
		try {
			response.setContentLength(baos.toByteArray().length);
			out = response.getOutputStream();
			out.write(baos.toByteArray());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String manageRiskFeeLeftFrame() throws Exception {
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
		List<Integer> years = projectService
				.getAppropriationYears(predix, nbbh);
		request.setAttribute("years", years);
		request.setAttribute("nbbh", nbbh);
		session.setAttribute("year", "");
		session.setAttribute("nbbh", nbbh);
		return "RiskFeeFrameLeft";
	}

	public String manageRiskFeeByProject() throws Exception {
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
		List<Appropriation> list = projectService.getAppropriationByProject(
				pagination, year, nbbh);
		request.setAttribute("appropriations", list);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("year", year);
		InfoTable iftable = projectService.getInfoByKey("syear");
		request.setAttribute("syear", iftable.getKeyindex());
		HttpSession session = request.getSession();
		session.setAttribute("year", year);
		session.setAttribute("nbbh", nbbh);

		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageRiskFees");
		return "RiskFeeReturn";
	}

	public String manageRiskFees() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		String _s = request.getParameter("s");
		String s = null;

		if (_s != null) {
			s = java.net.URLDecoder.decode(_s);
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
		List<Appropriation> list = projectService.getAppropriations(predix,
				pagination, year, s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mAppropriation.action?method=manageRiskFees&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAppropriation.action?method=manageRiskFees");
		request.setAttribute("appropriations", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return "RiskFeeReturn";
	}

	public String manageRiskFeeByIncome() throws Exception {
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
		List<Appropriation> list = projectService
				.getAppropriationByIncome(incomeId);
		request.setAttribute("appropriations", list);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageRiskFeeByIncome&nbbh="
						+ nbbh + "&year=" + year);
		return "RiskFeeReturn";
	}

	public String doRiskFeeManage() throws Exception {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Appropriation ap = projectService.getAppropriationById(ID);
		if (ap.getIsReturnBack()) {
			request.setAttribute("info", "该拨款已执行过回拨风险金操作！");
			return ERROR;
		}

		// 生成回拨编号
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有回拨风险金权限！如有疑问，请联系管理员。");
			return ERROR;
		}
		Date date = new Date();
		int now = date.getYear() + 1900;
		returnbackID = predix + "HB-" + now + "-";
		String lastestKey = projectService.getMaxReturnBackKey(returnbackID);
		if (lastestKey != null) {
			String num = lastestKey.substring(15);
			returnbackID = returnbackID
					+ ("" + (Integer.parseInt(num) + 1001)).substring(1);
		} else
			returnbackID = returnbackID + "001";

		ap.setReturnbackID(returnbackID);
		ap.setReturnBackDate(new Date());
		ap.setIsReturnBack(true);
		projectService.updateAppropriation(ap);

		Project p = ap.getProject();
		p.setRiskFeeBack(p.getRiskFeeBack() + ap.getRiskFee());
		p.setRiskCallbackFlg(true);
		projectService.update(p);

		request.setAttribute("info", "回拨风险金记录完毕！");
		request.setAttribute("pagePath",
				"mAppropriation.action?method=manageRiskFeeByProject&nbbh="
						+ innerCode);
		return "info";
	}

	/**
	 * 回拨所有风险金
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DAOException
	 */
	public String returnAllRiskFee() throws BusinessException, DAOException {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		// 判断此组织是否具有回拨风险金的权限
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			request.setAttribute("info", "对不起，根据设计，您所在的组织（" + org.getName()
					+ "）不应具有回拨风险金权限！如有疑问，请联系管理员。");
			return ERROR;
		}
		Date date = new Date();
		int now = date.getYear() + 1900;
		returnbackID = predix + "HB-" + now + "-";
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		String lastestKey = projectService.getMaxReturnBackKey(returnbackID);
		System.out.println("回拨单号：   " + lastestKey);
		int number;
		if (lastestKey != null) {
			String num = lastestKey.substring(15);
			number = Integer.parseInt(num) + 1;
		} else
			number = 1;

		// 根据项目内部编号获得此项目的所有拨款单号

		List<Appropriation> appros = projectService
				.getAppropriationByProject(innerCode);
		if (appros.size() == 0) {
			request.setAttribute("info", "没有要回拨的风险金");
			return ERROR;
		}
		// 根据拨款单号判断风险金是否已经回拨，如果没有则回拨 ，根据每个拨款单号对每一笔风险金进行更新（回拨时间，回拨单号，回拨标志）
		int origin = 0;
		int newReturn = 0;
		Project p = appros.get(0).getProject();
		for (Appropriation ap : appros) {
			if (ap.getIsReturnBack()) {
				origin++;
				continue;
			}
			// ap.setReturnbackID(returnbackID + ("" + (number++ +
			// 1000)).substring(1));
			ap.setReturnbackID(returnbackID
					+ ("" + (number + 1000)).substring(1));
			ap.setReturnBackDate(date);
			ap.setIsReturnBack(true);
			projectService.updateAppropriation(ap);
			newReturn++;

			p.setRiskFeeBack(p.getRiskFeeBack() + ap.getRiskFee());
		}
		p.setRiskCallbackFlg(true);
		projectService.update(p);
		if (newReturn == 0) {
			request.setAttribute("info", "回拨风险金操作之前已经执行完毕，请不要重复操作！");
		} else {
			request.setAttribute("info", "回拨成功！  共回拨 " + newReturn + " 笔风险金");
		}
		request.setAttribute("pagePath",
				"mAppropriation.action?method=manageRiskFeeByProject&nbbh="
						+ innerCode);
		return "info";
	}

	public String searchRiskFee() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		String _s = request.getParameter("s");
		String s = null;

		if (_s != null) {
			s = java.net.URLDecoder.decode(_s);
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

		List<Appropriation> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getAppropriations(predix, pagination, "all",
					s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getAppropriations(predix, pagination,
					preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mAppropriation.action?method=manageRiskFees&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAppropriation.action?method=manageRiskFees");
		request.setAttribute("appropriations", list);
		if (s != null)
			request.setAttribute("s", s);
		return "RiskFeeReturn";
	}

	public String searchRiskFeeSpl() throws BusinessException,
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
		List<Appropriation> list = /*
									 * projectService.getProjects(teacher.getOrg(
									 * ).getName(), pagination, null, s);
									 */projectService.getAppropriations(predix,
				pagination, "all", s);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAppropriation.action?method=manageRiskFees&s=" + s);
		request.setAttribute("appropriations", list);
		request.setAttribute("s", s);
		return "RiskFeeReturn";
	}

	public int getConAppCount() {
		return conAppCount;
	}

	public void setConAppCount(int conAppCount) {
		this.conAppCount = conAppCount;
	}

	public double getFwsyf() {
		return fwsyf;
	}

	public void setFwsyf(double fwsyf) {
		this.fwsyf = fwsyf;
	}

	public double getFwsyfbl() {
		return fwsyfbl;
	}

	public void setFwsyfbl(double fwsyfbl) {
		this.fwsyfbl = fwsyfbl;
	}

	public double getYqsbsyf() {
		return yqsbsyf;
	}

	public void setYqsbsyf(double yqsbsyf) {
		this.yqsbsyf = yqsbsyf;
	}

	public double getYqsbsyfbl() {
		return yqsbsyfbl;
	}

	public void setYqsbsybl(double yqsbsyfbl) {
		this.yqsbsyfbl = yqsbsyfbl;
	}

	public double getSdnf() {
		return sdnf;
	}

	public void setSdnf(double sdnf) {
		this.sdnf = sdnf;
	}

	public double getSdnfbl() {
		return sdnfbl;
	}

	public void setSdnfbl(double sdnfbl) {
		this.sdnfbl = sdnfbl;
	}

	public double getKyjxsy() {
		return kyjxsy;
	}

	public void setKyjxsy(double kyjxsy) {
		this.kyjxsy = kyjxsy;
	}

	public double getKyjxsybl() {
		return kyjxsybl;
	}

	public void setKyjxsybl(double kyjxsybl) {
		this.kyjxsybl = kyjxsybl;
	}

	public double getJjhj() {
		return jjhj;
	}

	public void setJjhj(double jjhj) {
		this.jjhj = jjhj;
	}

	public double getYqsbsyfper() {
		return yqsbsyfper;
	}

	public void setYqsbsyfper(double yqsbsyfper) {
		this.yqsbsyfper = yqsbsyfper;
	}

	public double getZjjf() {
		return zjjf;
	}

	public void setZjjf(double zjjf) {
		this.zjjf = zjjf;
	}
}
