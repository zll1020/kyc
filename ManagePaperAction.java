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
import cn.cust.kyc.vo.Paper;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManagePaperAction extends ActionBase {

	/**
	 * 主键
	 */
	private int ID;

	/**
	 * 项目
	 */
	private String nbbh;

	/**
	 * 论文题目
	 */
	private String paperName;

	/**
	 * 所在单位
	 */
	private String itsOrg;

	/**
	 * 刊物名称
	 */
	private String magazineName;

	/**
	 * 刊物期
	 */
	private String magazineTerm;

	/**
	 * 刊物卷
	 */
	private String magazineLabel;

	/**
	 * 刊物起始页
	 */
	private String magazineStartPage;

	/**
	 * 刊物终止页
	 */
	private String magazineEndPage;

	/**
	 * 刊物号
	 */
	private String magazineID;

	/**
	 * 刊物类别
	 */
	private String magazineType;

	/**
	 * 第一作者
	 */
	private String firstAuthor;

	/**
	 * 第二作者
	 */
	private String secondAuthor;

	/**
	 * 第三作者
	 */
	private String thirdAuthor;

	/**
	 * 刊物级别
	 */
	private String magazineLevel;

	/**
	 * 发表日期
	 */
	private Date publishDate;

	/**
	 * 论文奖励
	 */
	private String paperAward;

	/**
	 * 成果归档情况
	 */
	private String fruitArchive;

	/**
	 * 检索情况
	 */
	private String searchType;

	/**
	 * 检索号
	 */
	private String searchID;

	/**
	 * 提交日期
	 */
	private Date submitDate;

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

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getFirstAuthor() {
		return firstAuthor;
	}

	public void setFirstAuthor(String firstAuthor) {
		this.firstAuthor = firstAuthor;
	}

	public String getSecondAuthor() {
		return secondAuthor;
	}

	public void setSecondAuthor(String secondAuthor) {
		this.secondAuthor = secondAuthor;
	}

	public String getThirdAuthor() {
		return thirdAuthor;
	}

	public void setThirdAuthor(String thirdAuthor) {
		this.thirdAuthor = thirdAuthor;
	}

	public String getItsOrg() {
		return itsOrg;
	}

	public void setItsOrg(String itsOrg) {
		this.itsOrg = itsOrg;
	}

	public String getMagazineName() {
		return magazineName;
	}

	public void setMagazineName(String magazineName) {
		this.magazineName = magazineName;
	}

	public String getMagazineTerm() {
		return magazineTerm;
	}

	public void setMagazineTerm(String magazineTerm) {
		this.magazineTerm = magazineTerm;
	}

	public String getMagazineLabel() {
		return magazineLabel;
	}

	public void setMagazineLabel(String magazineLabel) {
		this.magazineLabel = magazineLabel;
	}

	public String getMagazineStartPage() {
		return magazineStartPage;
	}

	public void setMagazineStartPage(String magazineStartPage) {
		this.magazineStartPage = magazineStartPage;
	}

	public String getMagazineEndPage() {
		return magazineEndPage;
	}

	public void setMagazineEndPage(String magazineEndPage) {
		this.magazineEndPage = magazineEndPage;
	}

	public String getMagazineID() {
		return magazineID;
	}

	public void setMagazineID(String magazineID) {
		this.magazineID = magazineID;
	}

	public String getMagazineType() {
		return magazineType;
	}

	public void setMagazineType(String magazineType) {
		this.magazineType = magazineType;
	}

	public String getMagazineLevel() {
		return magazineLevel;
	}

	public void setMagazineLevel(String magazineLevel) {
		this.magazineLevel = magazineLevel;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getPaperAward() {
		return paperAward;
	}

	public void setPaperAward(String paperAward) {
		this.paperAward = paperAward;
	}

	public String getFruitArchive() {
		return fruitArchive;
	}

	public void setFruitArchive(String fruitArchive) {
		this.fruitArchive = fruitArchive;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchID() {
		return searchID;
	}

	public void setSearchID(String searchID) {
		this.searchID = searchID;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String managePaperLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Integer> years = projectService.getPaperYears();
		request.setAttribute("years", years);
		session.setAttribute("year", "");
		return "PaperFrameLeft";
	}

	public String updatePaperIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Paper pp = projectService.getPaperById(ID);
		request.setAttribute("paper", pp);
		return "UpdatePaper";
	}

	public String updatePaper() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Paper pp = projectService.getPaperById(ID);
		pp.setInnerCode(nbbh);
		pp.setPaperName(paperName);
		pp.setMagazineName(magazineName);
		pp.setFirstAuthor(firstAuthor);
		pp.setSecondAuthor(secondAuthor);
		pp.setThirdAuthor(thirdAuthor);
		pp.setMagazineLabel(magazineLabel);
		pp.setMagazineTerm(magazineTerm);
		pp.setMagazineID(magazineID);
		pp.setMagazineStartPage(magazineStartPage);
		pp.setMagazineEndPage(magazineEndPage);
		pp.setMagazineType(magazineType);
		pp.setMagazineLevel(magazineLevel);
		pp.setSearchType(searchType);
		pp.setSearchID(searchID);
		pp.setItsOrg(itsOrg);
		pp.setPublishDate(publishDate);
		pp.setPaperAward(paperAward);
		pp.setFruitArchive(fruitArchive);
		pp.setUpdater(teacher);
		pp.setUpdateDate(new Date());
		projectService.updatePaper(pp);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Paper> list = projectService.getPapers(pagination, "all", null);
		request.setAttribute("papers", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPaper.action?method=managePapers&year=all");
		return SUCCESS;
	}

	public String addPaper() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Paper pp = new Paper();
		pp.setInnerCode(nbbh);
		pp.setPaperName(paperName);
		pp.setMagazineName(magazineName);
		pp.setFirstAuthor(firstAuthor);
		pp.setSecondAuthor(secondAuthor);
		pp.setThirdAuthor(thirdAuthor);
		pp.setMagazineLabel(magazineLabel);
		pp.setMagazineTerm(magazineTerm);
		pp.setMagazineID(magazineID);
		pp.setMagazineStartPage(magazineStartPage);
		pp.setMagazineEndPage(magazineEndPage);
		pp.setMagazineType(magazineType);
		pp.setMagazineLevel(magazineLevel);
		pp.setSearchType(searchType);
		pp.setSearchID(searchID);
		pp.setItsOrg(itsOrg);
		pp.setPublishDate(publishDate);
		pp.setPaperAward(paperAward);
		pp.setFruitArchive(fruitArchive);
		pp.setCreateDate(new Date());
		pp.setCreater(teacher);
		projectService.savePaper(pp);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Paper> list = projectService.getPapers(pagination, "all", null);
		request.setAttribute("papers", list);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPaper.action?method=managePapers&year=all");
		return SUCCESS;
	}

	public String deletePaper() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deletePaper(ID);
		return managePapers();
	}

	public String showPaper() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Paper pp = projectService.getPaperById(ID);
		request.setAttribute("paper", pp);
		return "ShowPaper";
	}

	public String managePapers() throws BusinessException,
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
		List<Paper> list = projectService.getPapers(pagination, year, s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mPaper.action?method=managePapers&s=" + s);
		else
			request.setAttribute("pagiUrl", "mPaper.action?method=managePapers");
		request.setAttribute("papers", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return SUCCESS;
	}

	public String searchPaper() throws BusinessException,
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

		List<Paper> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getPapers(pagination, "all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getPapers(pagination, preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mPaper.action?method=managePapers&s=" + s);
		else
			request.setAttribute("pagiUrl", "mPaper.action?method=managePapers");
		request.setAttribute("papers", list);
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
		List<Paper> list = projectService.getPapers(year, s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("Paper");

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
		cell.setCellValue("科研管理系统学术论文导出");
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
			// paper表共21个字段
			Paper pp = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("ID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getID());
			}

			if (fbMap.containsKey("innerCode")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getInnerCode());
			}

			if (fbMap.containsKey("paperName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getPaperName());
			}

			if (fbMap.containsKey("itsOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getItsOrg());
			}

			if (fbMap.containsKey("magazineName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getMagazineName());
			}

			if (fbMap.containsKey("magazineTerm")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getMagazineTerm());
			}

			if (fbMap.containsKey("magazineLabel")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getMagazineLabel());
			}

			if (fbMap.containsKey("magazineStartPage")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getMagazineStartPage());
			}

			if (fbMap.containsKey("magazineEndPage")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getMagazineEndPage());
			}

			if (fbMap.containsKey("magazineID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getMagazineID());
			}

			if (fbMap.containsKey("magazineType")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getMagazineType());
			}

			if (fbMap.containsKey("magazineLevel")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getMagazineLevel());
			}

			if (fbMap.containsKey("publishDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getPublishDate() == null ? "" : pp
						.getPublishDate().toString());
			}

			if (fbMap.containsKey("paperAward")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getPaperAward());
			}

			if (fbMap.containsKey("fruitArchive")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getFruitArchive());
			}

			if (fbMap.containsKey("searchType")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getSearchType());
			}

			if (fbMap.containsKey("searchID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getSearchID());
			}

			if (fbMap.containsKey("creater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getCreater().getName());
			}

			if (fbMap.containsKey("createDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getCreateDate() == null ? "" : pp
						.getCreateDate().toString());
			}

			if (fbMap.containsKey("updater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getUpdater() == null ? "" : pp
						.getUpdater().getName());
			}

			if (fbMap.containsKey("updateDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pp.getUpdateDate() == null ? "" : pp
						.getUpdateDate().toString());
			}

		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=PaperExport.xls");
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
