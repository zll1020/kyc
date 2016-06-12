package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import cn.cust.kyc.vo.Member;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.BusinessException;
import cn.edu.cust.levin.DAOException;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageMemberAction extends ActionBase {

	private int id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 出生日期
	 */
	private Date birth;

	/**
	 * 学历
	 */
	private String diploma;

	/**
	 * 职称
	 */
	private String professionalTitle;

	/**
	 * 所属单位
	 */
	private String organization;

	/**
	 * 学位
	 */
	private String degree;

	/**
	 * 身份证号
	 */
	private String idCardNo;

	/**
	 * 工资代号
	 */
	private String payCode;

	/**
	 * Ajax人员模糊查找字符串
	 */
	private String mquery;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getProfessionalTitle() {
		return professionalTitle;
	}

	public void setProfessionalTitle(String professionalTitle) {
		this.professionalTitle = professionalTitle;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getMquery() {
		return mquery;
	}

	public void setMquery(String mquery) {
		this.mquery = mquery;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String updateMemberIndex() throws BusinessException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Member mb = projectService.getMemberById(id);
		request.setAttribute("member", mb);
		return "UpdateMember";
	}

	public String updateMember() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Member mb = projectService.getMemberById(id);
		mb.setName(name);
		mb.setSex(sex);
		mb.setBirth(birth);
		mb.setDiploma(diploma);
		mb.setProfessionalTitle(professionalTitle);
		mb.setDegree(degree);
		mb.setOrganization(organization);
		mb.setIdCardNo(idCardNo);
		mb.setPayCode(payCode);
		projectService.updateMember(mb);

		organization = "";
		diploma = "";
		name = "";
		return manageMembers();
	}

	public String addMember() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Member mb = new Member();
		mb.setName(name);
		mb.setSex(sex);
		mb.setBirth(birth);
		mb.setDiploma(diploma);
		mb.setProfessionalTitle(professionalTitle);
		mb.setDegree(degree);
		mb.setOrganization(organization);
		mb.setIdCardNo(idCardNo);
		mb.setPayCode(payCode);
		projectService.saveMember(mb);

		organization = "";
		diploma = "";
		name = "";
		return manageMembers();
	}

	public String showMember() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		Member mb = projectService.getMemberById(id);
		request.setAttribute("member", mb);
		return "ShowMember";
	}

	public String deleteMember() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		projectService.deleteMember(id);

		organization = "";
		diploma = "";
		name = "";
		return manageMembers();
	}

	public String manageMembers() throws BusinessException, DAOException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		String _s = request.getParameter("s");
		String s = null;
		String querySubStr = null;
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
		if (name != null)
			name = java.net.URLDecoder.decode(name, "UTF-8");
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);

		// xm字段
		if (name == null || name.length() == 0)
			querySubStr = s;
		else
			querySubStr = "xm like '" + name + "%'";
		List<Member> list = projectService.getMembers(pagination, querySubStr);
		String pagiUrl = "mMember.action?method=manageMembers";
		if (s != null)
			pagiUrl += "&s=" + s;
		if (name != null && name.length() != 0) {
			pagiUrl += "&name=" + name;
		}
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl", pagiUrl);
		request.setAttribute("members", list);
		if (name == null || name.length() == 0) {
			if (s != null)
				request.setAttribute("s", s);
		} else
			request.setAttribute("s", "xm like '" + name + "%'");
		return SUCCESS;
	}

	public String getMembers() throws BusinessException, DAOException,
			UnsupportedEncodingException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
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
		if (organization != null)
			organization = java.net.URLDecoder.decode(organization, "UTF-8");
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<String> orgList = projectService.getMemberOrganizations();
		String s = "";
		if (organization != null && (!"".equals(organization))) {
			s = "organization='" + organization + "'";
		}
		List<Member> list = projectService.getMembers(pagination, s);
		String pagiUrl = "mMember.action?method=getMembers";
		if (organization != null) {
			pagiUrl += "&organization=" + organization;
		}
		request.setAttribute("organization", organization);
		request.setAttribute("orgList", orgList);
		request.setAttribute("pagination", pagination);
		request.setAttribute("pagiUrl", pagiUrl);
		request.setAttribute("members", list);
		return "getMembers";
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
		List<Member> list = projectService.getMembers(s);
		List<FieldBean> fList = FieldsControl.getProjectFields("Member");

		// 统计页面上显示的列有多少个
		int pfbSize = 0;
		for (FieldBean pfb : fList) {
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
		cell.setCellValue("教师人事库数据导出");
		cell.setCellStyle(style);
		sheet.addMergedRegion(new Region(rowNum - 1, (short) 0, rowNum - 1,
				(short) (pfbSize - 1)));
		int j = 0;
		row = sheet.createRow((short) rowNum++);
		for (FieldBean incb : fList) {

			if (!incb.getDisplay()) {
				continue;
			}

			cell = row.createCell((short) j++);
			cell.setEncoding((short) 1);
			cell.setCellValue(incb.getName());
		}

		for (int i = 0; i < listSize; i++) {
			Member mb = list.get(i);
			row = sheet.createRow((short) rowNum++);
			j = 0;
			for (FieldBean fb : fList) {

				if (!fb.getDisplay()) {
					continue;
				}

				cell = row.createCell((short) j++);
				cell.setEncoding((short) 1);
				cell.setCellValue(FieldsControl.doVoGetMethod(mb, fb.getCode()));
			}
		}

		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline;filename=MemberExport.xls");
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
	 * Ajax获取Member列表
	 * 
	 * @return null
	 * @throws BusinessException
	 * @throws IOException
	 */
	public String getMembersAjax() throws BusinessException, IOException {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}

		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		StringBuilder str = new StringBuilder("");
		String _q = request.getParameter("mquery");
		String q = null;
		if (_q != null)
			q = java.net.URLDecoder.decode(_q, "UTF-8");
		ProjectService projectService = (ProjectService) BusinessFactory
				.getBusiness(ProjectServiceImpl.class);
		List<Member> memberList = projectService.getMemberInfoListByPredix(q);

		str.append("[{");
		if (memberList == null || memberList.size() == 0)
			str.append("}");
		else {
			/*
			 * ID id 姓名 xm 性别 xb 出生日期 csrq 学历 xl_val 职称 zc_val 所属单位 szdw_val 学位
			 * xw 身份证号 idcardno 工资代号 paycode
			 */
			Iterator<Member> ir = memberList.iterator();
			Member m = ir.next();
			String xm = m.getName() == null ? " " : m.getName();
			String xb = m.getSex() == null ? " " : m.getSex();
			String csrq = m.getBirth() == null ? " " : new SimpleDateFormat(
					"yyyy-MM-dd").format(m.getBirth());
			String xl_val = m.getDiploma() == null ? " " : m.getDiploma();
			String zc_val = m.getProfessionalTitle() == null ? " " : m
					.getProfessionalTitle();
			String szdw_val = m.getOrganization() == null ? " " : m
					.getOrganization();
			String xw = m.getDegree() == null ? " " : m.getDegree();
			String idCardNo = m.getIdCardNo() == null ? " " : m.getIdCardNo();
			String payCode = m.getPayCode() == null ? " " : m.getPayCode();
			str.append("'id':'" + m.getId() + "'");
			str.append(",'xm':'" + xm + "'");
			str.append(",'xb':'" + xb + "'");
			str.append(",'csrq':'" + csrq + "'");
			str.append(",'xl_val':'" + xl_val + "'");
			str.append(",'zc_val':'" + zc_val + "'");
			str.append(",'szdw_val':'" + szdw_val + "'");
			str.append(",'idcardno':'" + idCardNo + "'");
			str.append(",'paycode':'" + payCode + "'");
			str.append(",'xw':'" + xw + "'}");

			while (ir.hasNext()) {
				m = ir.next();
				xm = m.getName() == null ? "" : m.getName();
				xb = m.getSex() == null ? "" : m.getSex();
				csrq = m.getBirth() == null ? "" : new SimpleDateFormat(
						"yyyy-MM-dd").format(m.getBirth());
				xl_val = m.getDiploma() == null ? "" : m.getDiploma();
				zc_val = m.getProfessionalTitle() == null ? "" : m
						.getProfessionalTitle();
				szdw_val = m.getOrganization() == null ? "" : m
						.getOrganization();
				xw = m.getDegree() == null ? "" : m.getDegree();
				idCardNo = m.getIdCardNo() == null ? " " : m.getIdCardNo();
				payCode = m.getPayCode() == null ? " " : m.getPayCode();
				str.append(",{'id':'" + m.getId() + "'");
				str.append(",'xm':'" + xm + "'");
				str.append(",'xb':'" + xb + "'");
				str.append(",'csrq':'" + csrq + "'");
				str.append(",'xl_val':'" + xl_val + "'");
				str.append(",'zc_val':'" + zc_val + "'");
				str.append(",'szdw_val':'" + szdw_val + "'");
				str.append(",'idcardno':'" + idCardNo + "'");
				str.append(",'paycode':'" + payCode + "'");
				str.append(",'xw':'" + xw + "'}");
			}
		}
		str.append("]");

		PrintWriter pw;
		pw = response.getWriter();
		pw.print(str.toString());
		pw.flush();
		pw.close();
		return null;
	}

}