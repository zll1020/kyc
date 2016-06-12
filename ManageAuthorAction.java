package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
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
import cn.cust.kyc.vo.AchieveCristic;
import cn.cust.kyc.vo.Author;
import cn.cust.kyc.vo.Bookmaking;
import cn.cust.kyc.vo.Person;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageAuthorAction extends ActionBase {

	/**
	 * 参与人员编号--主键
	 */
	private int ID;

	/**
	 * 类型 1、奖励项目 2、专利项目 3、学术论文 4、著作 5、学术报告
	 */
	private int type;

	/**
	 * 关联ID
	 */
	private int outId;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 职称
	 */
	private String jobTitle;

	/**
	 * 所属单位
	 */
	private String orgnization;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 生日
	 */
	private Date birth;

	/**
	 * 学历
	 */
	private String diploma;

	/**
	 * 人员序号
	 */
	private String ryxh;

	public String getRyxh() {
		return ryxh;
	}

	public void setRyxh(String ryxh) {
		this.ryxh = ryxh;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getOutId() {
		return outId;
	}

	public void setOutId(int outId) {
		this.outId = outId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getOrgnization() {
		return orgnization;
	}

	public void setOrgnization(String orgnization) {
		this.orgnization = orgnization;
	}

	public String manageAuthors() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Author> list = projectService.getAuthors(type, outId);
		request.setAttribute("authors", list);
		request.setAttribute("type", "" + type);
		request.setAttribute("outId", "" + outId);
		return SUCCESS;
	}

	public String saveAuthor() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		if (ID == 0) {
			Author a = new Author();
			a.setJobTitle(jobTitle);
			a.setName(name);
			a.setOrgnization(orgnization);
			a.setOutId(outId);
			a.setType(type);
			a.setSex(sex);
			a.setBirth(birth);
			a.setDiploma(diploma);
			a.setRyxh(ryxh);
			projectService.saveAuthor(a);
		} else {
			Author a = projectService.getAuthor(ID);
			a.setName(name);
			a.setJobTitle(jobTitle);
			a.setOrgnization(orgnization);
			a.setSex(sex);
			a.setBirth(birth);
			a.setDiploma(diploma);
			a.setType(type);
			projectService.updateAuthor(a);
		}
		return ManageAuthorByAchievement();
	}

	/**
	 * 删除人员信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteAuthor() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deleteAuthor(ID);
		return ManageAuthorByAchievement();
	}

	public String ManageAuthorByAchievement() throws Exception {
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

		// 如果前台页面不传来achievementId，则从session中获得achievementId
		if (outId == 0) {
			outId = Integer.parseInt((String) session.getAttribute("outId"));
		}

		// 根据项目ID获得项目主要研究人员
		List<Author> list = projectService.getAuthorByOutId(pagination,
				Integer.toString(outId), type + "");
		request.setAttribute("Author", list);
		session.setAttribute("outId", Integer.toString(outId));
		session.setAttribute("type", Integer.toString(type));
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAuthor.action?method=ManageAuthorByAchievement&outId="
						+ outId + "&type=" + type);
		return "newSuccess";
	}

	public String exportToExcel() throws Exception {
		Teacher teacher = (Teacher) request.getSession()
				.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);

		// 如果前台页面不传来achievementId，则从session中获得achievementId
		if (outId == 0) {
			outId = Integer.parseInt((String) session.getAttribute("outId"));
		}

		// 根据项目ID获得项目主要研究人员
		List<Author> list = projectService.getAuthorByOutId(
				Integer.toString(outId), Integer.toString(type));

		List<FieldBean> incbs = FieldsControl.getProjectFields("Author");

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
		cell.setCellValue("项目成果主要研究人员信息导出");
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

			Author ac = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("id")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getId());
			}

			if (fbMap.containsKey("type")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getType());
			}

			if (fbMap.containsKey("outId")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getOutId());
			}

			if (fbMap.containsKey("name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getName());
			}

			if (fbMap.containsKey("ryxh")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getRyxh());
			}

			if (fbMap.containsKey("jobTitle")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getJobTitle());
			}

			if (fbMap.containsKey("orgnization")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getOrgnization());
			}

			if (fbMap.containsKey("sex")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getSex());
			}

			if (fbMap.containsKey("birth")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getBirth() == null ? "" : ac.getBirth()
						.toString());
			}

			if (fbMap.containsKey("diploma")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				String diploma = ac.getDiploma();
				if (diploma != null) {
					diploma = diploma.trim();
				}
				cell.setCellValue(diploma);
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

	/**
	 * 查看人员信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showAuthor() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Author au = projectService.getAuthor(ID);
		request.setAttribute("Author", au);
		return "showAuthor";
	}

	/**
	 * 修改人员信息
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String updateAuthorIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Author au;
		try {
			au = projectService.getAuthor(ID);
			request.setAttribute("Author", au);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			System.out.println("DAO层出错了");
		}
		return "updateAuthor";
	}

	public String updateAuthor() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Author au = projectService.getAuthor(ID);
		au.setName(name);
		au.setSex(sex);
		au.setBirth(birth);
		au.setDiploma(diploma);
		au.setOrgnization(orgnization);
		au.setType(type);
		au.setJobTitle(jobTitle);
		projectService.updateAuthor(au);
		return ManageAuthorByAchievement();
	}
}