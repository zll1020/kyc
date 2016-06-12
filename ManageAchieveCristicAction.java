package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
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
import cn.cust.kyc.vo.Achievement;
import cn.cust.kyc.vo.Author;
import cn.cust.kyc.vo.Member;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Person;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageAchieveCristicAction extends ActionBase {

	/**
	 * 参与人员编号--主键
	 */
	private int ID;

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
	 * 学历
	 */
	private String diploma;

	/**
	 * 所学专业
	 */
	private String sxzy;

	/**
	 * 参加人员序号
	 */
	private String ryxh;

	/**
	 * 项目类型
	 * 
	 * @return
	 */
	private String typeId;

	public String getSxzy() {
		return sxzy;
	}

	public void setSxzy(String sxzy) {
		this.sxzy = sxzy;
	}

	/**
	 * 出生日期
	 */
	private String birth;

	/**
	 * 关联的achievement ID
	 */
	private int achievementId;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDiploma() {
		return diploma;
	}

	public void setDiploma(String diploma) {
		this.diploma = diploma;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
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

	public int getAchievementId() {
		return achievementId;
	}

	public void setAchievementId(int achievementId) {
		this.achievementId = achievementId;
	}

	public String manageCristicsInAchieve() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<AchieveCristic> list = projectService
				.getAchieveCristics(achievementId);
		request.setAttribute("AchieveCristics", list);
		request.setAttribute("achievementId", achievementId);
		return SUCCESS;
	}

	/**
	 * 成员登记
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveCristic() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		System.out.println(orgnization);
		if (ID == 0) {
			Calendar cal = Calendar.getInstance();
			int curyear = cal.get(Calendar.YEAR);
			String djnd = (new Integer(curyear)).toString();

			AchieveCristic ac = new AchieveCristic();
			Achievement a = projectService.getAchievementById(achievementId);
			ac.setAchievement(a);
			ac.setJobTitle(jobTitle);
			ac.setName(name);
			ac.setOrgnization(orgnization);
			ac.setBirth(birth);
			ac.setDiploma(diploma);
			ac.setSex(sex);
			ac.setRyxh(ryxh);
			ac.setDjnd(djnd);// 插入等级年度信息

			// 添加所学专业
			ac.setSxzy(sxzy);
			projectService.saveAchieveCristic(ac);
		} else {
			AchieveCristic ac = projectService.getAchieveCristic(ID);
			ac.setJobTitle(jobTitle);
			ac.setName(name);
			ac.setOrgnization(orgnization);
			ac.setBirth(birth);
			ac.setDiploma(diploma);
			ac.setSex(sex);
			ac.setRyxh(ryxh);
			// 添加所学专业
			ac.setSxzy(sxzy);
			projectService.updateAchieveCristic(ac);
		}
		return ManageAchieveCristicByAchievement();
	}

	/**
	 * 删除成员
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteCristic() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deleteAchieveCristic(ID);
		return ManageAchieveCristicByAchievement();
	}

	/**
	 * 成员主界面
	 * 
	 * @return
	 */
	public String ManageAchieveCristicByAchievement() {
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
		ProjectService projectService = null;
		try {
			projectService = (ProjectService) BusinessFactory
					.getBusiness(ProjectServiceImpl.class);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			System.out.println("获得ProjectService时发生错误");
		}

		// 如果前台页面不传来achievementId，则从session中获得achievementId
		if (achievementId == 0) {
			achievementId = Integer.parseInt((String) session
					.getAttribute("achievementId"));
		}

		// 根据项目ID获得项目主要研究人员
		List<AchieveCristic> list = projectService
				.getAchieveCristicByAchievementId(pagination,
						Integer.toString(achievementId));
		request.setAttribute("AchieveCristic", list);
		session.setAttribute("achievementId", Integer.toString(achievementId));
		request.setAttribute("achievementId", Integer.toString(achievementId));
		request.setAttribute("pagination", pagination);
		request.setAttribute(
				"pagiUrl",
				"mAchieveCristic.action?method=ManageAchieveCristicByAchievement&achievementId="
						+ achievementId);
		return "newSuccess";
	}

	public String manageAchieveCristicLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		System.out.println("生成鉴定作者年度");
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			predix = "";
		}
		System.out.println("predix=" + predix);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		// List<Integer> years = projectService.getAchievementYears(predix,
		// ConstData.achievementType[0]);
		List<Integer> years = projectService.getCristicYears();
		String strAchId = null;
		if (achievementId != 0) {
			strAchId = achievementId + "";
		}
		String typeId = request.getParameter("typeId");
		System.out.println("left frame typeId=" + typeId);
		request.setAttribute("typeId", typeId);
		request.setAttribute("achievementId", strAchId);
		session.setAttribute("achievementId", strAchId);

		request.setAttribute("years", years);
		session.setAttribute("year", "");

		return "AchieveCristicFrameLeft";
	}

	/**
	 * 成员查询主界面
	 * 
	 * @return
	 */
	public String ManageAchieveCristic() throws Exception {

		int n = new Integer(request.getParameter("typeId")).intValue();
		String str = ConstData.achievementType[n];
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		String _s = request.getParameter("s");
		String s = "";
		String achievementId = null;
		if (_s != null) {
			s = java.net.URLDecoder.decode(_s, "UTF-8");
			achievementId = (String) session.getAttribute("achievementId");
			if (achievementId != null) {
				s = s + " and achievement.ID= '" + achievementId + "' ";
				request.setAttribute("achievementId", achievementId);
			}
		} else {
			achievementId = (String) session.getAttribute("achievementId");
			if (achievementId != null) {
				s = " achievement.ID= '" + achievementId + "' ";
				request.setAttribute("achievementId", achievementId);
			}
		}

		// 限制查询出来的人员 都是鉴定项目中的
		if (s == null || s.trim().length() == 0) {
			s += " achievement.type = '" + str + "' ";
		} else {
			s += " and achievement.type = '" + str + "' ";
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
		List<AchieveCristic> list = projectService.getAchieveCristic(predix,
				pagination, s);
		request.setAttribute("pagination", pagination);

		if (s != null)
			request.setAttribute("pagiUrl",
					"mAchieveCristic.action?method=ManageAchieveCristic&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAchieveCristic.action?method=ManageAchieveCristic");

		request.setAttribute("AchieveCristic", list);
		if (s != null)
			request.setAttribute("s", s);
		return "newSuccess";
	}

	public String searchAchieveCristic() throws BusinessException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		String _s = request.getParameter("s");

		String s = null;
		String achievementId = null;
		if (_s != null) {
			s = java.net.URLDecoder.decode(_s, "UTF-8");
			achievementId = (String) session.getAttribute("achievementId");
			if (achievementId != null) {
				s = s + " and achievement.ID= '" + achievementId + "' ";
				request.setAttribute("achievementId", achievementId);
			}
		} else {
			achievementId = (String) session.getAttribute("achievementId");
			if (achievementId != null) {
				s = " achievement.ID= '" + achievementId + "' ";
				request.setAttribute("achievementId", achievementId);
			}
		}

		// 限制查询出来的人员 都是鉴定项目中的
		if (s == null || s.trim().length() == 0) {
			s += " achievement.type = '鉴定' ";
		} else {
			s += " and achievement.type = '鉴定' ";
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

		List<AchieveCristic> list = null;
		list = projectService.getAchieveCristic(predix, pagination, s);

		request.setAttribute("pagination", pagination);

		if (s != null)
			request.setAttribute("pagiUrl",
					"mAchieveCristic.action?method=ManageAchieveCristic&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAchieveCristic.action?method=ManageAchieveCristic");

		request.setAttribute("AchieveCristic", list);
		if (s != null)
			request.setAttribute("s", s);

		return "newSuccess";
	}

	public String searchAchieveCristicSpl() throws BusinessException,
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
		String achievementId = null;
		if (s != null) {
			achievementId = (String) session.getAttribute("achievementId");
			if (achievementId != null) {
				s = s + " and achievement.ID= '" + achievementId + "' ";
				request.setAttribute("achievementId", achievementId);
			}
		}

		// 限制查询出来的人员 都是鉴定项目中的
		if (s == null || s.trim().length() == 0) {
			s += " achievement.type = '鉴定' ";
		} else {
			s += " and achievement.type = '鉴定' ";
		}

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<AchieveCristic> list = /*
									 * projectService.getProjects(teacher.getOrg(
									 * ).getName(), pagination, null, s);
									 */projectService.getAchieveCristic(predix,
				pagination, s);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mAchieveCristic.action?method=ManageAchieveCristic&s=" + s);
		request.setAttribute("AchieveCristic", list);
		if (s != null)
			request.setAttribute("s", s);

		return "newSuccess";
	}

	/**
	 * 导出成员信息
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws UnsupportedEncodingException
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

		ProjectService projectService = null;
		try {
			projectService = (ProjectService) BusinessFactory
					.getBusiness(ProjectServiceImpl.class);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			System.out.println("获得ProjectService时发生错误");
		}

		/*
		 * //如果前台页面不传来achievementId，则从session中获得achievementId
		 * if(achievementId==0) {
		 * achievementId=Integer.parseInt((String)session.
		 * getAttribute("achievementId")); }
		 */

		// 根据项目ID获得项目主要研究人员
		List<AchieveCristic> list = null;
		if (achievementId == 0)
			list = projectService.getAchieveCristic(predix, s);
		else
			list = projectService.getAchieveCristicByAchievementId(Integer
					.toString(achievementId));

		List<FieldBean> incbs = FieldsControl
				.getProjectFields("AchieveCristic");

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
		cell.setCellValue("科研管理系统项目主要研究人员导出");
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

			AchieveCristic ac = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("id")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getId());
			}

			if (fbMap.containsKey("achievement.achieveName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getAchievement().getAchieveName());
			}

			if (fbMap.containsKey("achievement.itsOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getAchievement().getItsOrg());
			}

			if (fbMap.containsKey("achievement.achieveMan")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getAchievement().getAchieveMan());
			}

			if (fbMap.containsKey("achievement.concludeLevel")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getAchievement().getConcludeLevel());
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
				cell.setCellValue(ac.getBirth());
			}

			if (fbMap.containsKey("diploma")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(ac.getDiploma());
			}

			if (fbMap.containsKey("sxzy")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				String strSxzy = ac.getSxzy();
				if (strSxzy != null) {
					strSxzy = strSxzy.trim();
				}
				cell.setCellValue(strSxzy);
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
	public String showAchieveCristic() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		AchieveCristic ac = projectService.getAchieveCristic(ID);
		request.setAttribute("AchieveCristic", ac);
		return "showAchieveCristic";
	}

	/**
	 * 修改人员信息
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String updateAchieveCristicIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		AchieveCristic ac;
		try {
			ac = projectService.getAchieveCristic(ID);
			request.setAttribute("AchieveCristic", ac);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			System.out.println("DAO层出错了");
		}
		return "updateAchieveCristic";
	}

	public String updateAchieveCristic() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		AchieveCristic ac = projectService.getAchieveCristic(ID);
		ac.setName(name);
		ac.setSex(sex);
		ac.setBirth(birth);
		ac.setDiploma(diploma);
		ac.setOrgnization(orgnization);
		projectService.updateAchieveCristic(ac);
		return ManageAchieveCristicByAchievement();
	}

	public void setRyxh(String ryxh) {
		this.ryxh = ryxh;
	}

	public String getRyxh() {
		return ryxh;
	}

	public String getAchieveCristicByYear() throws BusinessException {
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

		String year = request.getParameter("year");
		String typeId = request.getParameter("typeId");
		System.out.println("typeId=" + typeId);
		String s = " achievement.type = '"
				+ ConstData.achievementType[(new Integer(typeId)).intValue()]
				+ "' and djnd=" + year;
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
		List<AchieveCristic> list = projectService.getAchieveCristic(predix,
				pagination, s);
		request.setAttribute("pagination", pagination);

		if (s != null)
			request.setAttribute("pagiUrl",
					"mAchieveCristic.action?method=ManageAchieveCristic&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAchieveCristic.action?method=ManageAchieveCristic");

		request.setAttribute("AchieveCristic", list);
		if (s != null)
			request.setAttribute("s", s);
		return "newSuccess";
	}

}