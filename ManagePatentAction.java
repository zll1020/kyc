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
import cn.cust.kyc.vo.Code;
import cn.cust.kyc.vo.Patent;
import cn.cust.kyc.vo.Org;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManagePatentAction extends ActionBase {

	/**
	 * 主键
	 */
	private int ID;

	/**
	 * 项目
	 */
	private String nbbh;

	/**
	 * 专利名称
	 */
	private String achieveName;

	/**
	 * 专利类型
	 */
	private String patentType;

	/**
	 * 专利号
	 */
	private String patentID;

	/**
	 * 申报日期
	 */
	private Date applyDate;

	/**
	 * 申请号
	 */
	private String applyID;

	/**
	 * 授权日期
	 */
	private Date patentDate;

	/**
	 * 专利权人
	 */
	private String patentMan;

	/**
	 * 证书号
	 */
	private String certificate;

	// -----编辑信息
	/**
	 * 备注
	 */
	private String remark;
	
	private double sqjf;
	
	private String ryxx;
	private double wcjf;

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

	public String getPatentType() {
		return patentType;
	}

	public void setPatentType(String patentType) {
		this.patentType = patentType;
	}

	public String getPatentID() {
		return patentID;
	}

	public void setPatentID(String patentID) {
		this.patentID = patentID;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyID() {
		return applyID;
	}

	public void setApplyID(String applyID) {
		this.applyID = applyID;
	}

	public Date getPatentDate() {
		return patentDate;
	}

	public void setPatentDate(Date patentDate) {
		this.patentDate = patentDate;
	}

	public String getPatentMan() {
		return patentMan;
	}

	public void setPatentMan(String patentMan) {
		this.patentMan = patentMan;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
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

	public String managePatentLeftFrame() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		
		List<Code> state = projectService.getCodeByType("专利状态");
		request.setAttribute("state", state);
		for(Code c:state){
			System.out.println(c.getId()+"  "+c.getInfo()+" "+c.getName()+" "+c.getType()+" "+c.getNumber());
		}
		
		List<Integer> years = projectService.getPatentYears();
		request.setAttribute("years", years);
		session.setAttribute("year", "");
		return "PatentFrameLeft";
	}

	public String updatePatentIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Patent p = projectService.getPatentById(ID);
		request.setAttribute("patent", p);
		return "UpdatePatent";
	}

	public String updatePatent() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Patent pt = projectService.getPatentById(ID);
		pt.setInnerCode(nbbh);
		pt.setAchieveName(achieveName);
		pt.setPatentID(patentID);
		pt.setPatentType(patentType);
		pt.setApplyDate(applyDate);
		pt.setApplyID(applyID);
		pt.setPatentDate(patentDate);
		pt.setCertificate(certificate);
		pt.setPatentMan(patentMan);
		pt.setUpdater(teacher);
		pt.setUpdateDate(new Date());
		pt.setRemark(remark);
		pt.setSqjf(sqjf);
		pt.setRyxx(ryxx);
		pt.setWcjf(wcjf);
		projectService.updatePatent(pt);

		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Patent> list = projectService.getPatents(pagination, "all", null);
		request.setAttribute("patents", list);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPatent.action?method=managePatents&year=all");
		return SUCCESS;
	}

	public String addPatent() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Patent pt = new Patent();
		pt.setInnerCode(nbbh);
		pt.setAchieveName(achieveName);
		pt.setPatentID(patentID);
		pt.setPatentType(patentType);
		pt.setApplyDate(applyDate);
		pt.setApplyID(applyID);
		pt.setPatentDate(patentDate);
		pt.setCertificate(certificate);
		pt.setPatentMan(patentMan);
		pt.setCreateDate(new Date());
		pt.setCreater(teacher);
		pt.setRemark(remark);
		pt.setSqjf(sqjf);
		pt.setRyxx(ryxx);
		pt.setWcjf(wcjf);
		projectService.savePatent(pt);
		int size = ConstData.projectSize;
		int page = 1;
		Pagination pagination = new Pagination();
		pagination.setPage(page);
		pagination.setSize(size);
		List<Patent> list = projectService.getPatents(pagination, "all", null);
		request.setAttribute("patents", list);
		request.setAttribute("nbbh", nbbh);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl",
				"mPatent.action?method=managePatents&year=all");
		return SUCCESS;
	}

	public String deletePatent() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deletePatent(ID);
		return managePatents();
	}

	public String showPatent() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Patent pt = projectService.getPatentById(ID);
		request.setAttribute("patent", pt);
		return "ShowPatent";
	}

	public String managePatents() throws BusinessException,
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
		List<Patent> list = projectService.getPatents(pagination, year, s);
		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mPatent.action?method=managePatents&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mPatent.action?method=managePatents");
		request.setAttribute("patents", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);

		return SUCCESS;
	}

	/**
	 * 专利申请成功 flag值得意义：1表示查询申请成功的专利 2表示未成功申请的专利
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws UnsupportedEncodingException
	 */
	public String apply() throws BusinessException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		String _s = request.getParameter("s");
		String flag = request.getParameter("flag");
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
		// 获得申请成功的专利列表
		List<Patent> list = projectService.getApplyPatents(pagination, year, s,
				flag);
		
		request.setAttribute("pagination", pagination);
		System.out.println("s="+s);
		if(flag.equals("1"))
			s=" certificate =''";
		else
			s=" certificate !=''";
		//if (s != null)
			request.setAttribute("pagiUrl",
					"mPatent.action?method=managePatents&s=" + s);
	//	else
	//		request.setAttribute("pagiUrl","mPatent.action?method=managePatents");
		
		request.setAttribute("patents", list);
		if (s != null)
			request.setAttribute("s", s);
		request.setAttribute("year", year);
		session.setAttribute("year", year);

		return SUCCESS;
	}

	public String searchPatent() throws BusinessException,
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

		List<Patent> list = null;
		String preYear = (String) session.getAttribute("year");
		if (preYear == null) {
			list = projectService.getPatents(pagination, "all", s);
			request.setAttribute("year", "");
		} else {
			list = projectService.getPatents(pagination, preYear, s);
			request.setAttribute("year", preYear);
		}

		request.setAttribute("pagination", pagination);
		if (s != null)
			request.setAttribute("pagiUrl",
					"mPatent.action?method=managePatents&s=" + s);
		else
			request.setAttribute("pagiUrl",
					"mPatent.action?method=managePatents");
		request.setAttribute("patents", list);
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
		List<Patent> list = projectService.getPatents(year, s);
		List<FieldBean> incbs = FieldsControl.getProjectFields("Patent");

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
		cell.setCellValue("科研管理系统专利项目导出");
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
			// patent表共15个字段
			Patent pt = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;

			if (fbMap.containsKey("ID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getID());
			}

			if (fbMap.containsKey("innerCode")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getInnerCode());
			}

			if (fbMap.containsKey("achieveName")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getAchieveName());
			}

			if (fbMap.containsKey("patentType")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getPatentType());
			}

			if (fbMap.containsKey("patentID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getPatentID());
			}

			//************************8
			if (fbMap.containsKey("sqjf")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(new Float(pt.getSqjf()).doubleValue());
			}
			
			if (fbMap.containsKey("wcjf")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(new Float(pt.getWcjf()).doubleValue());
			}
			//***********************
			if (fbMap.containsKey("applyDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getApplyDate() == null ? "" : pt
						.getApplyDate().toString());
			}

			if (fbMap.containsKey("applyID")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getApplyID());
			}

			if (fbMap.containsKey("patentDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getPatentDate() == null ? "" : pt
						.getPatentDate().toString());
			}

			if (fbMap.containsKey("patentMan")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getPatentMan());
			}

			if (fbMap.containsKey("certificate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getCertificate());
			}
			//-------------------------------------------
			if (fbMap.containsKey("ryxx")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getRyxx());
			}
			
			//-------------------------------------------

			if (fbMap.containsKey("creater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getCreater().getName());
			}

			if (fbMap.containsKey("createDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getCreateDate() == null ? "" : pt
						.getCreateDate().toString());
			}

			if (fbMap.containsKey("updater.name")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getUpdater() == null ? "" : pt
						.getUpdater().getName());
			}

			if (fbMap.containsKey("updateDate")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getUpdateDate() == null ? "" : pt
						.getUpdateDate().toString());
			}

			if (fbMap.containsKey("remark")) {
				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(pt.getRemark());
			}
		}
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=PatentExport.xls");
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

	public void setSqjf(double sqjf) {
		this.sqjf = sqjf;
	}

	public double getSqjf() {
		return sqjf;
	}

	public void setRyxx(String ryxx) {
		this.ryxx = ryxx;
	}

	public String getRyxx() {
		return ryxx;
	}

	public void setWcjf(double wcjf) {
		this.wcjf = wcjf;
	}

	public double getWcjf() {
		return wcjf;
	}

}
