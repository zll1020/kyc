package cn.cust.kyc.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.bo.impl.ProjectServiceImpl;
import cn.cust.kyc.util.FieldBean;
import cn.cust.kyc.util.FieldsControl;
import cn.cust.kyc.util.ProjectFieldBean;
import cn.cust.kyc.util.ProjectFieldsControl;
import cn.cust.kyc.vo.AchieveCristic;
import cn.cust.kyc.vo.Achievement;
import cn.cust.kyc.vo.Person;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.business.BusinessFactory;

public class ManageSearchAction extends ActionBase {
	
	/**
	 * 查询姓名，按个人统计
	 */
	String name;
	
	/**
	 * 项目
	 */
	boolean project;
	
	/**
	 * 鉴定
	 */
	boolean achievement1;
	
	/**
	 * 验收
	 */
	boolean achievement2;
	
	/**
	 * 结题
	 */
	boolean achievement3;
	
	/**
	 * 奖励
	 */
	boolean award;
	
	/**
	 * 专利
	 */
	boolean patent;
	
	/**
	 * 论文
	 */
	boolean paper;
	
	/**
	 * 著作
	 */
	boolean bookmaking;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isProject() {
		return project;
	}

	public void setProject(boolean project) {
		this.project = project;
	}

	public boolean isAchievement1() {
		return achievement1;
	}

	public void setAchievement1(boolean achievement1) {
		this.achievement1 = achievement1;
	}

	public boolean isAchievement2() {
		return achievement2;
	}

	public void setAchievement2(boolean achievement2) {
		this.achievement2 = achievement2;
	}

	public boolean isAchievement3() {
		return achievement3;
	}

	public void setAchievement3(boolean achievement3) {
		this.achievement3 = achievement3;
	}

	public boolean isAward() {
		return award;
	}

	public void setAward(boolean award) {
		this.award = award;
	}

	public boolean isPatent() {
		return patent;
	}

	public void setPatent(boolean patent) {
		this.patent = patent;
	}

	public boolean isPaper() {
		return paper;
	}

	public void setPaper(boolean paper) {
		this.paper = paper;
	}

	public boolean isBookmaking() {
		return bookmaking;
	}

	public void setBookmaking(boolean bookmaking) {
		this.bookmaking = bookmaking;
	}

	public String searchByPerson() throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		Map<Integer, List<?>> lists = new HashMap<Integer, List<?>>();
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		if(project){
			List<Person> list = projectService.getPersons("", null, "name like '%"+name+"%'");
			lists.put(0, list);
			request.setAttribute("lists", lists);
			return "content";
		}
		if(achievement1){
			List<AchieveCristic> list = projectService.getAchieveCristicsByPerson("鉴定", name);
			lists.put(1, list);
			request.setAttribute("lists", lists);
			return "content";
		}
		if(achievement2){
			List<AchieveCristic> list = projectService.getAchieveCristicsByPerson("验收", name);
			lists.put(2, list);
			request.setAttribute("lists", lists);
			return "content";
		}
		if(achievement3){
			List<AchieveCristic> list = projectService.getAchieveCristicsByPerson("结题", name);
			lists.put(3, list);
			request.setAttribute("lists", lists);
			return "content";
		}
		if(award){
			List<?> list = projectService.getAwardByPerson("Award", 1, name);
			lists.put(4, list);
			request.setAttribute("lists", lists);
			return "content";
		}
		if(patent){
			List<?> list = projectService.getAwardByPerson("Patent", 1, name);
			lists.put(5, list);
			request.setAttribute("lists", lists);
			return "content";
		}
		if(paper){
			List<?> list = projectService.getAwardByPerson("Paper", 1, name);
			lists.put(6, list);
			request.setAttribute("lists", lists);
			return "content";
		}
		if(bookmaking){
			List<?> list = projectService.getAwardByPerson("Bookmaking", 1, name);
			lists.put(7, list);
			request.setAttribute("lists", lists);
			return "content";
		}
		return null;
	}
	
	public String exportTheSearch() throws Exception{
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		if (teacher == null) {
			request.setAttribute("info", "您没有登录，或登录已超时，请重试！");
			return ERROR;
		}
		
		name = URLDecoder.decode(name, "UTF-8");
		String[] ss = {"Person","Achievement","Achievement","Achievement","Award","Patent","Paper","Bookmaking"};
		String[] ss_name = {"项目信息","鉴定信息","验收信息","结题信息","奖励信息","专利信息","论文信息","著作信息"};
		List<List<?>> lists = new ArrayList<List<?>>();
		ProjectService projectService = (ProjectService) BusinessFactory.getBusiness(ProjectServiceImpl.class);
		
		lists.add(projectService.getPersons("", null, "name like '%"+name+"%'"));
		lists.add(projectService.getAchieveCristicsByPerson("鉴定", name));
		lists.add(projectService.getAchieveCristicsByPerson("验收", name));
		lists.add(projectService.getAchieveCristicsByPerson("结题", name));
		lists.add(projectService.getAwardByPerson("Award", 1, name));
		lists.add(projectService.getAwardByPerson("Patent", 1, name));
		lists.add(projectService.getAwardByPerson("Paper", 1, name));
		lists.add(projectService.getAwardByPerson("Bookmaking", 1, name));
		
		HSSFWorkbook wb = new HSSFWorkbook();
		for(int tableIndex=1;tableIndex<=8;tableIndex++){
			HSSFSheet sheet = wb.createSheet();
			//设置汉字编码，防止sheet名称乱码
			wb.setSheetName(tableIndex-1, ss_name[tableIndex-1], HSSFWorkbook.ENCODING_UTF_16);
			HSSFRow row;
			HSSFCell cell;
			int rowNum = 0;
			row = sheet.createRow((short) rowNum++);
			List<?> list = lists.get(tableIndex-1);
			if(tableIndex==1)
			{
				cell = row.createCell((short) 0);
				cell.setEncoding((short) 1);
				cell.setCellValue("成员序号");
				
				cell = row.createCell((short) 1);
				cell.setEncoding((short) 1);
				cell.setCellValue("成员姓名");
				
				cell = row.createCell((short) 2);
				cell.setEncoding((short) 1);
				cell.setCellValue("成员身份");
				
				List<ProjectFieldBean> fList = ProjectFieldsControl.getProjectFields();
				int cellNum = 3;
				for(ProjectFieldBean pfb : fList){
					cell = row.createCell((short) cellNum++);
					cell.setEncoding((short) 1);
					cell.setCellValue(pfb.getField());
				}
				if(list!=null){
					for(int i=0;i<list.size();i++){
						row = sheet.createRow(rowNum++);
						Person p = (Person) list.get(i);
						
						cell = row.createCell((short) 0);
						cell.setEncoding((short) 1);
						cell.setCellValue(p.getSerialNumber());
						
						cell = row.createCell((short) 1);
						cell.setEncoding((short) 1);
						cell.setCellValue(p.getName());
						
						cell = row.createCell((short) 2);
						cell.setEncoding((short) 1);
						cell.setCellValue(p.getRole());
						
						Project pro = p.getProject();
						cellNum = 3;
						for(ProjectFieldBean pfb : fList){
							cell = row.createCell((short) cellNum++);
							cell.setEncoding((short) 1);
							cell.setCellValue(ProjectFieldsControl.doVoGetMethod(pro, pfb.getCode()));
						}
					}
				}
			}else{
				List<FieldBean> fList = FieldsControl.getProjectFields(ss[tableIndex-1]);
				int cellNum = 0;
				for(FieldBean fb : fList){
					cell = row.createCell((short) cellNum++);
					cell.setEncoding((short) 1);
					cell.setCellValue(fb.getName());
				}					
				if(list!=null){
					for(int i=0;i<list.size();i++){
						row = sheet.createRow(rowNum++);
						cellNum = 0;
						for(FieldBean fb : fList){
							if(tableIndex==2||tableIndex==3||tableIndex==4){
								AchieveCristic ac = (AchieveCristic) list.get(i);
								Achievement acm = ac.getAchievement();
								cell = row.createCell((short) cellNum++);
								cell.setEncoding((short) 1);
								cell.setCellValue(FieldsControl.doVoGetMethod(acm, fb.getCode()));
							}else{
								cell = row.createCell((short) cellNum++);
								cell.setEncoding((short) 1);
								cell.setCellValue(FieldsControl.doVoGetMethod(list.get(i), fb.getCode()));
							}
						}
					}
				}
			}
		}
		response.setContentType("application/msexcel");
 		response.setHeader("Content-disposition", "inline;filename=SearchExport.xls");
//		response.setHeader("Cache-Control","no-cache");
//		response.setHeader("Pragma","no-cache");
//		response.setDateHeader ("Expires", 0); //防止缓存
//		response.setContentType("application/msexcel");
//		response.setHeader("Content-disposition", "inline;filename=SearchExport.xls");
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
