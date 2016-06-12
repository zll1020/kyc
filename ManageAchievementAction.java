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
import cn.cust.kyc.vo.Achievement;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageAchievementAction extends ActionBase {

	/**
	 * 主键
	 */
	private int ID;

	/**
	 * 成果形式：结题、鉴定、验收
	 */
	private String type;

	/**
	 * 项目
	 */
	private String innerCode;

	/**
	 * 成果名称
	 */
	private String achieveName;

	/**
	 * 所在单位
	 */
	private String itsOrg;

	/**
	 * 负责人
	 */
	private String achieveMan;

	/**
	 * 完成认定日期
	 */
	private Date concludeDate;

	/**
	 * 组织单位
	 */
	private String concludeOrg;

	/**
	 * 成果水平、结论
	 */
	private String concludeLevel;

	/**
	 * 是否转化为应用
	 */
	private boolean isApplay;

	/**
	 * 转化应用单位
	 */
	private String applayOrg;

	/**
	 * 成果登记号
	 */
	private String resultNO;

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
	 * 备注
	 */
	private String remark;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInnerCode() {
		return innerCode;
	}

	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
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

	public String getAchieveMan() {
		return achieveMan;
	}

	public void setAchieveMan(String achieveMan) {
		this.achieveMan = achieveMan;
	}

	public Date getConcludeDate() {
		return concludeDate;
	}

	public void setConcludeDate(Date concludeDate) {
		this.concludeDate = concludeDate;
	}

	public String getConcludeOrg() {
		return concludeOrg;
	}

	public void setConcludeOrg(String concludeOrg) {
		this.concludeOrg = concludeOrg;
	}

	public String getConcludeLevel() {
		return concludeLevel;
	}

	public void setConcludeLevel(String concludeLevel) {
		this.concludeLevel = concludeLevel;
	}

	public boolean getIsApplay() {
		return isApplay;
	}

	public void setIsApplay(boolean isApplay) {
		this.isApplay = isApplay;
	}

	public String getApplayOrg() {
		return applayOrg;
	}

	public void setApplayOrg(String applayOrg) {
		this.applayOrg = applayOrg;
	}

	public String getResultNO() {
		return resultNO;
	}

	public void setResultNO(String resultNO) {
		this.resultNO = resultNO;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 年份
	 */
	private String year;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	// /**
	// * 项目认定类型
	// */
	// private int typeId;
	//
	// public int getTypeId() {
	// return typeId;
	// }
	//
	// public void setTypeId(int typeId) {
	// this.typeId = typeId;
	// }

	public String manageAchievementLeftFrame() throws Exception {
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

		int typeId = Integer.parseInt(request.getParameter("typeId"));
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Integer> years = projectService.getAchievementYears(predix,
				ConstData.achievementType[typeId]);
		request.setAttribute("years", years);
		session.setAttribute("year", "");
		return "AchievementFrameLeft";
	}

	public String updateAchievementIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Achievement acm = projectService.getAchievementById(ID);
		request.setAttribute("achievement", acm);
		return "UpdateAchievement";
	}

	public String updateAchievement() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Achievement acm = projectService.getAchievementById(ID);
		// clg，2012.1.20修改
		/*
		 * Project p = projectService.getProject(innerCode); acm.setProject(p);
		 */
		acm.setInnerId(innerCode);
		acm.setAchieveName(achieveName);
		acm.setItsOrg(itsOrg);
		acm.setConcludeOrg(concludeOrg);
		acm.setConcludeDate(concludeDate);
		acm.setConcludeLevel(concludeLevel);
		acm.setResultNO(resultNO);
		acm.setIsApplay(isApplay);
		if (!isApplay)
			acm.setApplayOrg("");
		else
			acm.setApplayOrg(applayOrg);
		acm.setAchieveMan(achieveMan);
		acm.setUpdater(teacher);
		acm.setUpdateDate(new Date());
		acm.setRemark(remark);
		projectService.updateAchievement(acm);

		return manageAchievements();
	}

	/**
	 * 添加鉴定 等项目
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAchievement() throws Exception {
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
					+ "）不应具有登记项目权限！如有疑问，请联系管理员。");
			return ERROR;
		}

		int typeId = Integer.parseInt(request.getParameter("typeId"));
		String str = "";
		switch (typeId) {
		case 0:
			str = "JD-";// 鉴定项目
			break;
		case 1:
			str = "YS-";// 验收项目
			break;
		case 2:
			str = "JT-";// 结题项目
			break;
		default:
			break;
		}
		Date date = new Date();
		int now = date.getYear() + 1900;
		// innerCode = predix +str+"XM-" + now + "-";
		innerCode = predix + "XM-" + str + now + "-";
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		String lastestKey = projectService.getMaxAchievementKey(innerCode);
		System.out.println("添加项目：             " + innerCode);
		if (lastestKey != null) {
			String num = lastestKey.substring(18);// 原为15
			innerCode = innerCode
					+ new Integer(Integer.parseInt(num) + 1001).toString()
							.substring(1);
		} else
			innerCode = innerCode + "001";

		Achievement acm = new Achievement();
		acm.setInnerId(innerCode);

		System.out.println("内部编号后续内容为：" + typeId);
		System.out.println("项目内部编号为：" + innerCode);

		acm.setType(ConstData.achievementType[typeId]);
		acm.setAchieveName(achieveName);
		System.out.println("***********************  "+itsOrg+"  "+concludeOrg);
		acm.setItsOrg(itsOrg);
		acm.setConcludeOrg(concludeOrg);
		acm.setConcludeLevel(concludeLevel);
		acm.setConcludeDate(concludeDate);
		acm.setResultNO(resultNO);
		acm.setIsApplay(isApplay);
		if (isApplay)
			acm.setApplayOrg(applayOrg);
		acm.setAchieveMan(achieveMan);
		acm.setCreater(teacher);
		acm.setCreateDate(new Date());
		acm.setRemark(remark);
		System.out.println("插入数据库之前");
		projectService.saveAchievement(acm);

		return manageAchievements();
	}

	public String deleteAchievement() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deleteAchievement(ID);

		return manageAchievements();
	}

	public String showAchievement() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Achievement acm = projectService.getAchievementById(ID);
		request.setAttribute("achievement", acm);
		return "ShowAchievement";
	}

	public String manageAchievements() throws BusinessException,
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
		String predix = new ConstData().innerCodePrefix.get(org.getName());

		if (predix == null) {
			predix = "";
		}
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		int typeId = Integer.parseInt(request.getParameter("typeId"));

		System.out.println("成果组织：" + predix + "组织名称:" + org.getName()
				+ "typeId=" + typeId);
		if (predix.equals("KYC-ZH-"))
			predix = "KYC";
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Achievement> list = projectService.getAchievements(
				ConstData.achievementType[typeId], predix, pagination, year, s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mAchievement.action?method=manageAchievements&typeId="
							+ typeId + "&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAchievement.action?method=manageAchievements&typeId="
							+ typeId);
		request.setAttribute("achievements", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);
		return SUCCESS;
	}

	public String searchAchievement() throws BusinessException,
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
		int typeId = Integer.parseInt(request.getParameter("typeId"));
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Achievement> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getAchievements(
					ConstData.achievementType[typeId], predix, pagination,
					"all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getAchievements(
					ConstData.achievementType[typeId], predix, pagination,
					preYear, s);
			request.setAttribute("year", preYear);
		}
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mAchievement.action?method=manageAchievements&typeId="
							+ typeId + "&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mAchievement.action?method=manageAchievements&typeId="
							+ typeId);
		request.setAttribute("achievements", list);
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
		Org org = teacher.getOrg();
		String predix = new ConstData().innerCodePrefix.get(org.getName());
		if (predix == null) {
			predix = "";
		}

		System.out.println(request.getParameter("s"));

		int typeId = Integer.parseInt(request.getParameter("typeId"));
		System.out.println("测试位置" + typeId);
		if (predix.equals("KYC-ZH-"))
			predix = "KYC";
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Achievement> list = projectService.getAchievements(
				ConstData.achievementType[typeId], predix, year, s);
		// List<Achievement> list =
		// projectService.getAchievements(ConstData.achievementType[typeId],
		// predix, pagination, year, s);

		List<FieldBean> incbs = FieldsControl.getProjectFields("Achievement");

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
		cell.setCellValue("科研管理系统" + ConstData.achievementType[typeId] + "项目导出");
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
			// achievement表共17个字段,显示16个
			Achievement acm = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("ID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getID());
			}

			if (fbMap.containsKey("innerId")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				System.out.println("acm " + acm.getInnerId());
				// cell.setCellValue(acm.getProject().getInnerCode());
				cell.setCellValue(acm.getInnerId());
			}

			if (fbMap.containsKey("achieveName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getAchieveName());
			}

			if (fbMap.containsKey("itsOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getItsOrg());
			}

			if (fbMap.containsKey("achieveMan")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getAchieveMan());
			}

			if (fbMap.containsKey("concludeDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getConcludeDate() == null ? "" : acm
						.getConcludeDate().toString());
			}

			if (fbMap.containsKey("concludeOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getConcludeOrg());
			}

			if (fbMap.containsKey("concludeLevel")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getConcludeLevel());
			}

			if (fbMap.containsKey("applayOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getIsApplay());
			}

			if (fbMap.containsKey("applayOrg")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getApplayOrg());
			}

			if (fbMap.containsKey("resultNO")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getResultNO());
			}

			if (fbMap.containsKey("creater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getCreater().getName());
			}

			if (fbMap.containsKey("createDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getCreateDate() == null ? "" : acm
						.getCreateDate().toString());
			}

			if (fbMap.containsKey("updater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getUpdater() == null ? "" : acm
						.getUpdater().getName());
			}

			if (fbMap.containsKey("updateDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getUpdateDate() == null ? "" : acm
						.getUpdateDate().toString());
			}

			if (fbMap.containsKey("remark")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(acm.getRemark());
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=AchievementExport.xls");
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
