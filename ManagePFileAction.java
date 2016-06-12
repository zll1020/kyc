package cn.cust.kyc.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.opensymphony.xwork2.ActionContext;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.FieldBean;
import cn.cust.kyc.util.FieldsControl;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.ProjectFieldBean;
import cn.cust.kyc.util.RecordSetting;
import cn.cust.kyc.vo.Member;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.PFile;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Record;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManagePFileAction extends ActionBase {

	private File image; // 与jsp表单中的名称对应
	private String imageFileName; // 物理文件FileName为固定格式
	private String imageContentType;// ContentType为固定格式
	/**
	 * 
	 * 文件编号
	 */
	private static String fileCode;

	/**
	 * 项目内部编号
	 */
	private static String innerCode;

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

	/**
	 * 文件名称
	 */
	private String name;

	/**
	 * 文件份数
	 */
	private int number;

	/**
	 * 来源单位
	 */
	private String organization;

	/**
	 * 来文日期
	 */
	private Date theDate;

	/**
	 * 上传文件名称
	 */
	private String downloadFileName;

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
	private String year;

	/**
	 * 备注
	 */
	private String remark;

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Date getTheDate() {
		return theDate;
	}

	public void setTheDate(Date theDate) {
		this.theDate = theDate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String managePFileByProject() throws Exception {
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
		List<PFile> list = projectService.getPFileByProject(pagination, year,
				innerCode, s);
		request.setAttribute("PFiles", list);
		request.setAttribute("nbbh", innerCode);
		request.setAttribute("year", year);

		HttpSession session = request.getSession();
		session.setAttribute("year", year);
		session.setAttribute("nbbh", innerCode);

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl", "mPFile.action?method=managePFiles"
					+ "&s=" + s);
		else
			request.setAttribute("pagiUrl", "mPFile.action?method=managePFiles");
		return SUCCESS;
	}

	public String managePFileLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		// String nbbh=

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Integer> years = projectService.getPFileYears(innerCode);
		request.setAttribute("years", years);

		nbbh = request.getParameter("nbbh");
		System.out.println(this.toString() + "  " + nbbh);

		request.setAttribute("nbbh", nbbh);
		session.setAttribute("year", "");
		session.setAttribute("nbbh", nbbh);
		return "PFileFrameLeft";
	}

	public String addPFileIndex() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Project p = projectService.getProject(innerCode);
		request.setAttribute("project", p);
		return "AddPFile";
	}

	public String updatePFileIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		PFile in = projectService.getPFileById(fileCode);
		request.setAttribute("PFile", in);
		return "UpdatePFile";
	}

	public String updatePFile() throws Exception {
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
		PFile pf = projectService.getPFileById(fileCode);
		pf.setName(name);
		pf.setNumber(number);
		pf.setOrganization(organization);
		pf.setTheDate(theDate);
		pf.setUpdater(teacher);
		pf.setUpdateDate(new Date());
		pf.setRemark(remark);
		// if(RecordSetting.getRecordSetting()){
		// ps.setId(0);
		// ps.setDelFlg(true);
		// ps = projectService.savePFile(ps);
		//
		// Record rd = new Record();
		// rd.setApplyDate(new Date());
		// rd.setApplyer(teacher);
		// rd.setDoType(ConstData.ApplyForUpdate);
		// rd.setIDType(true);
		// rd.setNewId("" + ps.getId());
		// rd.setOldId("" + id);
		// rd.setTableName("PFile");
		// projectService.saveRecord(rd);
		// request.setAttribute("info", "已发送修改申请，请等待管理员审核！");
		// }else{
		projectService.updatePFile(pf);
		request.setAttribute("info", "更新成功！");
		// }

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<PFile> list = projectService.getPFileByProject(pagination, "all",
				innerCode);
		request.setAttribute("PFiles", list);
		request.setAttribute("nbbh", pf.getProject().getInnerCode());
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPFile.action?method=managePFileByProject&innerCode="
						+ innerCode + "&year=all");
		return SUCCESS;
	}

	// 获得下载文件的名字
	public static String name() throws BusinessException {
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		PFile p = projectService.getPFileById(fileCode);
		String fileName = p.getDownloadFileName();
		return fileName;
	}

	// 从下载文件原始存放路径读取得到文件输出流
	static InputStream inputStream = null;

	public InputStream getInputStream() throws BusinessException {
		String DOWNLOADFILEPATH = ServletActionContext.getServletContext()
				.getRealPath("KycFile/" + innerCode);
		String fileName = name();
		System.out.println("下载路径：" + DOWNLOADFILEPATH + "\\" + fileName);
		try {
			inputStream = new java.io.FileInputStream(DOWNLOADFILEPATH + "\\"
					+ fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return inputStream;
		}
		// return SUCCESS;
		return inputStream;
	}

	// 如果下载文件名为中文，进行字符编码转换
	public String getDownloadChineseFileName() throws BusinessException {
		String fileName = name();
		String downloadChineseFileName = fileName;
		try {
			downloadChineseFileName = new String(
					downloadChineseFileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return downloadChineseFileName;
	}

	public String downloadFile() throws BusinessException,
			FileNotFoundException {
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		PFile p = projectService.getPFileById(fileCode);
		String isUpload = p.getIsUpload();
		System.out.println("isUpload:" + isUpload);
		request.setAttribute("isUpload", isUpload);
		if (getInputStream() == null) {
			System.out.println("stream为空");
			return "errorDownload";
		}
		return SUCCESS;
	}

	public String addPFile() throws Exception {
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
					+ "）不应具有登记文件权限！如有疑问，请联系管理员。");
			return ERROR;
		}
		if (image != null) {
			String path = ServletActionContext.getServletContext().getRealPath(
					"KycFile/" + innerCode);
			imageFileName = "" + new Date().getTime()
					+ imageFileName.substring(imageFileName.lastIndexOf('.'));
			System.out.println("上传路径:" + path);
			// path="c:\\kycfile\\";

			File savefile = new File(path, imageFileName);
			System.out.println("imageFileName:" + savefile.getAbsolutePath());
			if (!savefile.getParentFile().exists())
				savefile.getParentFile().mkdirs();
			FileUtils.copyFile(image, savefile);
		}
		Date date = new Date();
		int now = date.getYear() + 1900;
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		String lastestKey = projectService.getMaxPFileKey(innerCode);
		Date year = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy");
		String fileCode_year = sf.format(year);
		if (lastestKey != null && lastestKey.length() > 18) {
			String num = lastestKey.substring(27, 30);
			fileCode = innerCode
					+ "("
					+ "WJ-"
					+ fileCode_year
					+ "-"
					+ new Integer(Integer.parseInt(num) + 1001).toString()
							.substring(1) + ")";
		} else
			fileCode = innerCode + "(" + "WJ-" + fileCode_year + "-" + "001"
					+ ")";

		System.out.println(name + "  (*)" + imageFileName);

		Project p = projectService.getProject(innerCode);
		PFile pf = new PFile();
		pf.setProject(p);
		pf.setFileCode(fileCode);
		pf.setName(name);
		pf.setNumber(number);
		pf.setOrganization(organization);
		pf.setTheDate(theDate);
		pf.setCreater(teacher);
		pf.setCreateDate(new Date());
		pf.setDownloadFileName(imageFileName);
		if (image == null) {
			pf.setIsUpload("0");
		} else {
			pf.setIsUpload("1");
		}
		projectService.savePFile(pf);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<PFile> list = projectService.getPFileByProject(pagination, "all",
				innerCode);
		request.setAttribute("PFiles", list);
		request.setAttribute("nbbh", innerCode);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPFile.action?method=managePFileByProject&innerCode="
						+ innerCode + "&year=all");

		return SUCCESS;
	}

	public String deletePFile() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		innerCode = projectService.getPFileById(fileCode).getProject()
				.getInnerCode();
		projectService.deletePFile(fileCode);
		request.setAttribute("info", "删除成功！");

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<PFile> list = projectService.getPFileByProject(pagination, "all",
				innerCode);
		request.setAttribute("PFiles", list);
		request.setAttribute("nbbh", innerCode);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPFile.action?method=managePFileByProject&innerCode="
						+ innerCode + "&year=all");
		return SUCCESS;
	}

	public String showPFile() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		PFile p = projectService.getPFileById(fileCode);
		request.setAttribute("PFile", p);
		return "ShowPFile";
	}

	public String managePFiles() throws BusinessException,
			UnsupportedEncodingException {
		System.out.println("managePFiles()");
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
			String nbbh = (String) request.getAttribute("nbbh");
			if (nbbh != null) {
				s = " project.innerCode= '" + nbbh + "' ";
				request.setAttribute("nbbh", nbbh);
			}
		}
		System.out.println("file:   " + _s + "**" + s);
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
		List<PFile> list = projectService
				.getPFiles(predix, pagination, year, s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mPFile.action?method=managePFiles&s=" + s);
		else
			request.setAttribute("pagiUrl", "mPFile.action?method=managePFiles");
		request.setAttribute("PFiles", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return SUCCESS;
	}

	public String searchPFile() throws BusinessException,
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

		List<PFile> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getPFiles(predix, pagination, "all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getPFiles(predix, pagination, preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mPFile.action?method=managePFiles&s=" + s);
		else
			request.setAttribute("pagiUrl", "mPFile.action?method=managePFiles");
		request.setAttribute("PFiles", list);
		if (s != null)
			request.setAttribute("s", s);
		return SUCCESS;
	}

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
		List<PFile> list;
		if (innerCode == null)
			list = projectService.getPFiles(predix, year, s);
		else
			list = projectService.getPFileByProject(null, year, innerCode, s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("PFile");

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
		cell.setCellValue("科研管理系统文件导出");
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
			PFile ps = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			for (FieldBean pfb : incbs) {

				if (!pfb.getDisplay()) {
					continue;
				}

				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(FieldsControl.doVoGetMethod(ps, pfb.getCode()));
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=PFileExport.xls");
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

	public String searchPFileSpl() throws BusinessException,
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
		List<PFile> list = /*
							 * projectService.getProjects(teacher.getOrg().getName
							 * (), pagination, null, s);
							 */projectService.getPFiles(predix, pagination,
				"all", s);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl", "mPFile.action?method=managePFiles&s="
				+ s);
		request.setAttribute("PFiles", list);
		request.setAttribute("s", s);
		return SUCCESS;
	}

}