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
import cn.cust.kyc.vo.Award;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageAwardAction extends ActionBase {

	/**
	 * 主键
	 */
	private int ID;

	/**
	 * 项目
	 */
	private String nbbh;

	/**
	 * 成果名称
	 */
	private String achieveName;

	/**
	 * 所在单位
	 */
	private String itsOrg;

	/**
	 * 获奖等级
	 */
	private String awardLevel;

	/**
	 * 获奖类别
	 */
	private String awardType;

	/**
	 * 获奖级别
	 */
	private String awardGrade;

	/**
	 * 颁奖单位
	 */
	private String awardOrg;

	/**
	 * 获奖时间
	 */
	private Date awardDate;

	/**
	 * 备注
	 */
	private String remark;

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

	public String getAchieveName() {
		return achieveName;
	}

	public void setAchieveName(String achieveName) {
		this.achieveName = achieveName;
	}

	public String getItsOrg() {
		return itsOrg;
	}

	public void setItsOrg(String itsOrg) {
		this.itsOrg = itsOrg;
	}

	public String getAwardLevel() {
		return awardLevel;
	}

	public void setAwardLevel(String awardLevel) {
		this.awardLevel = awardLevel;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardGrade() {
		return awardGrade;
	}

	public void setAwardGrade(String awardGrade) {
		this.awardGrade = awardGrade;
	}

	public String getAwardOrg() {
		return awardOrg;
	}

	public void setAwardOrg(String awardOrg) {
		this.awardOrg = awardOrg;
	}

	public Date getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Date awardDate) {
		this.awardDate = awardDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String manageAwardLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Integer> years = projectService.getAwardYears();
		request.setAttribute("years", years);
		session.setAttribute("year", "");
		return "AwardFrameLeft";
	}

	public String updateAwardIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Award a = projectService.getAwardById(ID);
		request.setAttribute("award", a);
		return "UpdateAward";
	}

	public String updateAward() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Award a = projectService.getAwardById(ID);
		a.setInnerCode(nbbh);
		a.setAchieveName(achieveName);
		a.setAwardDate(awardDate);
		a.setAwardOrg(awardOrg);
		a.setAwardGrade(awardGrade);
		a.setAwardLevel(awardLevel);
		a.setAwardType(awardType);
		a.setItsOrg(itsOrg);
		a.setUpdateDate(new Date());
		a.setUpdater(teacher);
		a.setRemark(remark);
		projectService.updateAward(a);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Award> list = projectService.getAwards(pagination, "all", null);
		request.setAttribute("awards", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAward.action?method=manageAwards&year=all");
		return SUCCESS;
	}

	public String addAward() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Award a = new Award();
		a.setInnerCode(nbbh);
		a.setAchieveName(achieveName);
		a.setAwardDate(awardDate);
		a.setAwardOrg(awardOrg);
		a.setAwardGrade(awardGrade);
		a.setAwardLevel(awardLevel);
		a.setAwardType(awardType);
		a.setItsOrg(itsOrg);
		a.setCreateDate(new Date());
		a.setCreater(teacher);
		a.setRemark(remark);
		projectService.saveAward(a);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Award> list = projectService.getAwards(pagination, "all", null);
		request.setAttribute("awards", list);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAward.action?method=manageAwards&year=all");
		return SUCCESS;
	}

	public String deleteAward() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deleteAward(ID);
		return manageAwards();
	}

	public String showAward() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Award a = projectService.getAwardById(ID);
		request.setAttribute("award", a);
		return "ShowAward";
	}

	public String manageAwards() throws BusinessException,
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
		Org org = teacher.getOrg();
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Award> list = projectService.getAwards(pagination, year, s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mAward.action?method=manageAwards&s=" + s);
		else
			request.setAttribute("pagiUrl", "mAward.action?method=manageAwards");
		request.setAttribute("awards", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return SUCCESS;
	}

	public String searchAward() throws BusinessException,
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

		List<Award> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getAwards(pagination, "all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getAwards(pagination, preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mAward.action?method=manageAwards&s=" + s);
		else
			request.setAttribute("pagiUrl", "mAward.action?method=manageAwards");
		request.setAttribute("awards", list);
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
		List<Award> list = projectService.getAwards(year, s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("Award");

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
		cell.setCellValue("科研管理系统奖励项目导出");
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
			// award表共14个字段
			Award a = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("ID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getID());
			}

			if (fbMap.containsKey("innerCode")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getInnerCode());
			}

			if (fbMap.containsKey("achieveName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getAchieveName());
			}

			if (fbMap.containsKey("achieveName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getItsOrg());
			}

			if (fbMap.containsKey("awardType")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getAwardType());
			}

			if (fbMap.containsKey("awardLevel")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getAwardLevel());
			}

			if (fbMap.containsKey("awardGrade")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getAwardGrade());
			}

			if (fbMap.containsKey("awardOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getAwardOrg());
			}

			if (fbMap.containsKey("awardDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getAwardDate() == null ? "" : a
						.getAwardDate().toString());
			}

			if (fbMap.containsKey("creater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getCreater().getName());
			}

			if (fbMap.containsKey("createDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getCreateDate() == null ? "" : a
						.getCreateDate().toString());
			}

			if (fbMap.containsKey("updater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getUpdater() == null ? "" : a.getUpdater()
						.getName());
			}

			if (fbMap.containsKey("updateDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getUpdateDate() == null ? "" : a
						.getUpdateDate().toString());
			}

			if (fbMap.containsKey("remark")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(a.getRemark());
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=AwardExport.xls");
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

}
