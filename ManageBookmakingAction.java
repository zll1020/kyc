package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

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
import cn.cust.kyc.util.ProjectFieldBean;
import cn.cust.kyc.vo.Bookmaking;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageBookmakingAction extends ActionBase {

	/**
	 * 主键
	 */
	private int ID;

	/**
	 * 项目内部编号
	 */
	private String nbbh;

	/**
	 * 著作名称
	 */
	private String bookName;

	/**
	 * 出版社名称
	 */
	private String publishingName;

	/**
	 * 出版社级别
	 */
	private String publishingLevel;

	/**
	 * 出版号
	 */
	private String publishID;

	/**
	 * 出版地
	 */
	private String publishAddress;

	/**
	 * 出版类型
	 */
	private String publishType;

	/**
	 * 出版日期
	 */
	private Date publishDate;

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

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getPublishingName() {
		return publishingName;
	}

	public void setPublishingName(String publishingName) {
		this.publishingName = publishingName;
	}

	public String getPublishingLevel() {
		return publishingLevel;
	}

	public void setPublishingLevel(String publishingLevel) {
		this.publishingLevel = publishingLevel;
	}

	public String getPublishID() {
		return publishID;
	}

	public void setPublishID(String publishID) {
		this.publishID = publishID;
	}

	public String getPublishAddress() {
		return publishAddress;
	}

	public void setPublishAddress(String publishAddress) {
		this.publishAddress = publishAddress;
	}

	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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

	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String manageBookmakingLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Integer> years = projectService.getBookmakingYears();
		request.setAttribute("years", years);
		session.setAttribute("year", "");
		return "BookmakingFrameLeft";
	}

	public String updateBookmakingIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Bookmaking bm = projectService.getBookmakingById(ID);
		request.setAttribute("bookmaking", bm);
		return "UpdateBookmaking";
	}

	public String updateBookmaking() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Bookmaking bm = projectService.getBookmakingById(ID);
		bm.setBookName(bookName);
		bm.setPublishDate(publishDate);
		bm.setPublishingLevel(publishingLevel);
		bm.setPublishAddress(publishAddress);
		bm.setPublishType(publishType);
		bm.setPublishingName(publishingName);
		bm.setPublishID(publishID);
		bm.setUpdateDate(new Date());
		bm.setUpdater(teacher);
		projectService.updateBookmaking(bm);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Bookmaking> list = projectService.getBookmakings(pagination,
				"all", null);
		request.setAttribute("bookmakings", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mBookmaking.action?method=manageBookmakings&year=all");
		return SUCCESS;
	}

	public String addBookmaking() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Bookmaking bm = new Bookmaking();
		bm.setBookName(bookName);
		bm.setPublishAddress(publishAddress);
		bm.setPublishType(publishType);
		bm.setPublishDate(publishDate);
		bm.setPublishingLevel(publishingLevel);
		bm.setPublishingName(publishingName);
		bm.setPublishID(publishID);
		bm.setCreateDate(new Date());
		bm.setCreater(teacher);
		projectService.saveBookmaking(bm);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Bookmaking> list = projectService.getBookmakings(pagination,
				"all", null);
		request.setAttribute("bookmakings", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mBookmaking.action?method=manageBookmakings&year=all");
		return SUCCESS;
	}

	public String deleteBookmaking() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deleteBookmaking(ID);
		return manageBookmakings();
	}

	public String showBookmaking() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Bookmaking bm = projectService.getBookmakingById(ID);
		request.setAttribute("bookmaking", bm);
		return "ShowBookmaking";
	}

	public String manageBookmakings() throws BusinessException,
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
		List<Bookmaking> list = projectService.getBookmakings(pagination, year,
				s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mBookmaking.action?method=manageBookmakings&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mBookmaking.action?method=manageBookmakings");
		request.setAttribute("bookmakings", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return SUCCESS;
	}

	public String searchBookmaking() throws BusinessException,
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

		List<Bookmaking> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getBookmakings(pagination, "all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getBookmakings(pagination, preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mBookmaking.action?method=manageBookmakings&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mBookmaking.action?method=manageBookmakings");
		request.setAttribute("bookmakings", list);
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
		List<Bookmaking> list = projectService.getBookmakings(year, s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("Bookmaking");

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
		cell.setCellValue("科研管理系统著作导出");
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
		}

		for (int i = 0; i < listSize; i++) {
			Bookmaking bm = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			for (FieldBean incb : incbs) {

				if (!incb.getDisplay()) {
					continue;
				}

				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(FieldsControl.doVoGetMethod(bm,
						incb.getCode()));
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=BookmakingExport.xls");
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
