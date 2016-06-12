package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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
import cn.cust.kyc.vo.Report;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageReportAction extends ActionBase {

	/**
	 * 主键
	 */
	private int ID;

	/**
	 * 项目内部编号
	 */
	private String nbbh;

	/**
	 * 题目
	 */
	private String reportName;

	/**
	 * 报告人
	 */
	private String reportMan;

	/**
	 * 报告人单位
	 */
	private String reportOrg;

	/**
	 * 职务
	 */
	private String vocation;

	/**
	 * 职称
	 */
	private String jobTitle;

	/**
	 * 报告年度
	 */
	private String reportYear;

	/**
	 * 报告时间
	 */
	private Date reportDate;

	/**
	 * 报告地点
	 */
	private String reportAddress;

	/**
	 * 学生参加人数
	 */
	private int studentNum;

	/**
	 * 教工人数
	 */
	private int teacherNum;

	/**
	 * 主办单位
	 */
	private String hostOrg;

	/**
	 * 协办单位
	 */
	private String coOrg;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNbbh() {
		return nbbh;
	}

	public void setNbbh(String nbbh) {
		this.nbbh = nbbh;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportMan() {
		return reportMan;
	}

	public void setReportMan(String reportMan) {
		this.reportMan = reportMan;
	}

	public String getReportOrg() {
		return reportOrg;
	}

	public void setReportOrg(String reportOrg) {
		this.reportOrg = reportOrg;
	}

	public String getVocation() {
		return vocation;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getReportYear() {
		return reportYear;
	}

	public void setReportYear(String reportYear) {
		this.reportYear = reportYear;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getReportAddress() {
		return reportAddress;
	}

	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public int getTeacherNum() {
		return teacherNum;
	}

	public void setTeacherNum(int teacherNum) {
		this.teacherNum = teacherNum;
	}

	public String getHostOrg() {
		return hostOrg;
	}

	public void setHostOrg(String hostOrg) {
		this.hostOrg = hostOrg;
	}

	public String getCoOrg() {
		return coOrg;
	}

	public void setCoOrg(String coOrg) {
		this.coOrg = coOrg;
	}

	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String manageReportLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Integer> years = projectService.getReportYears();
		request.setAttribute("years", years);
		session.setAttribute("year", "");
		return "ReportFrameLeft";
	}

	public String updateReportIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Report r = projectService.getReportById(ID);
		request.setAttribute("report", r);
		return "UpdateReport";
	}

	public String updateReport() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Report r = projectService.getReportById(ID);
		r.setInnerCode(nbbh);
		r.setReportName(reportName);
		r.setReportMan(reportMan);
		r.setJobTitle(jobTitle);
		r.setReportOrg(reportOrg);
		r.setVocation(vocation);
		r.setReportAddress(reportAddress);
		r.setReportDate(reportDate);
		r.setReportYear(reportYear);
		r.setTeacherNum(teacherNum);
		r.setStudentNum(studentNum);
		r.setHostOrg(hostOrg);
		r.setCoOrg(coOrg);
		r.setUpdateDate(new Date());
		r.setUpdater(teacher);
		projectService.updateReport(r);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Report> list = projectService.getReports(pagination, "all", null);
		request.setAttribute("reports", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mReport.action?method=manageReports&year=all");
		return SUCCESS;
	}

	public String addReport() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Report r = new Report();
		r.setInnerCode(nbbh);
		r.setReportName(reportName);
		r.setReportMan(reportMan);
		r.setJobTitle(jobTitle);
		r.setReportOrg(reportOrg);
		r.setVocation(vocation);
		r.setReportAddress(reportAddress);
		r.setReportDate(reportDate);
		r.setReportYear(reportYear);
		r.setTeacherNum(teacherNum);
		r.setStudentNum(studentNum);
		r.setHostOrg(hostOrg);
		r.setCoOrg(coOrg);
		r.setCreateDate(new Date());
		r.setCreater(teacher);
		projectService.saveReport(r);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Report> list = projectService.getReports(pagination, "all", null);
		request.setAttribute("reports", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mReport.action?method=manageReports&year=all");
		return SUCCESS;
	}

	public String deleteReport() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deleteReport(ID);
		return manageReports();
	}

	public String showReport() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Report r = projectService.getReportById(ID);
		request.setAttribute("report", r);
		return "ShowReport";
	}

	public String manageReports() throws BusinessException,
			UnsupportedEncodingException {
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
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Report> list = projectService.getReports(pagination, year, s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mReport.action?method=manageReports&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mReport.action?method=manageReports");
		request.setAttribute("reports", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return SUCCESS;
	}

	public String searchReport() throws BusinessException,
			UnsupportedEncodingException {
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

		List<Report> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getReports(pagination, "all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getReports(pagination, preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mReport.action?method=manageReports&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mReport.action?method=manageReports");
		request.setAttribute("reports", list);
		if (s != null)
			request.setAttribute("s", s);
		return SUCCESS;
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
		List<Report> list = projectService.getReports(year, s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("Report");

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
		cell.setCellValue("科研管理系统学术报告导出");
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
			// report表共17个字段
			Report r = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("ID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getID());
			}

			if (fbMap.containsKey("reportName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getReportName());
			}

			if (fbMap.containsKey("reportMan")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getReportMan());
			}

			if (fbMap.containsKey("reportOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getReportOrg());
			}

			if (fbMap.containsKey("vocation")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getVocation());
			}

			if (fbMap.containsKey("jobTitle")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getJobTitle());
			}

			if (fbMap.containsKey("reportYear")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getReportYear());
			}

			if (fbMap.containsKey("reportDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getReportDate() == null ? "" : r
						.getReportDate().toString());
			}

			if (fbMap.containsKey("reportAddress")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getReportAddress());
			}

			if (fbMap.containsKey("studentNum")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getStudentNum());
			}

			if (fbMap.containsKey("teacherNum")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getTeacherNum());
			}

			if (fbMap.containsKey("hostOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getHostOrg());
			}

			if (fbMap.containsKey("coOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getCoOrg());
			}

			if (fbMap.containsKey("creater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getCreater().getName());
			}

			if (fbMap.containsKey("createDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getCreateDate() == null ? "" : r
						.getCreateDate().toString());
			}

			if (fbMap.containsKey("updater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getUpdater() == null ? "" : r.getUpdater()
						.getName());
			}

			if (fbMap.containsKey("updateDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(r.getUpdateDate() == null ? "" : r
						.getUpdateDate().toString());
			}

		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=ReportExport.xls");
		OutputStream out;
		try {
			out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

}
