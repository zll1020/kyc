package cn.cust.kyc.bo.impl;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.cust.kyc.bo.ProjectService;
import cn.cust.kyc.dao.AchieveCristicDao;
import cn.cust.kyc.dao.AchievementDao;
import cn.cust.kyc.dao.ApproDao;
import cn.cust.kyc.dao.AuthorDao;
import cn.cust.kyc.dao.AwardDao;
import cn.cust.kyc.dao.BookmakingDao;
import cn.cust.kyc.dao.CodeDao;
import cn.cust.kyc.dao.CodeTypeDao;
import cn.cust.kyc.dao.DAOFactory;
import cn.cust.kyc.dao.IncomeDao;
import cn.cust.kyc.dao.InfoDao;
//import cn.cust.kyc.dao.InfoDao;
import cn.cust.kyc.dao.MemberDao;
import cn.cust.kyc.dao.PFileDao;
import cn.cust.kyc.dao.PaperDao;
import cn.cust.kyc.dao.PatentDao;
import cn.cust.kyc.dao.PersonDao;
import cn.cust.kyc.dao.ProjectDao;
import cn.cust.kyc.dao.RecordDao;
import cn.cust.kyc.dao.ReportDao;
//import cn.cust.kyc.dao.VerityDao;
import cn.cust.kyc.util.ConstData;
import cn.cust.kyc.util.FieldsControl;
import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.util.RecordSetting;
import cn.cust.kyc.vo.AchieveCristic;
import cn.cust.kyc.vo.Achievement;
import cn.cust.kyc.vo.Appropriation;
import cn.cust.kyc.vo.Author;
import cn.cust.kyc.vo.Award;
import cn.cust.kyc.vo.Bookmaking;
import cn.cust.kyc.vo.Code;
import cn.cust.kyc.vo.CodeType;
import cn.cust.kyc.vo.Income;
import cn.cust.kyc.vo.InfoTable;
//import cn.cust.kyc.vo.Info;
import cn.cust.kyc.vo.Member;
import cn.cust.kyc.vo.PFile;
import cn.cust.kyc.vo.Paper;
import cn.cust.kyc.vo.Patent;
import cn.cust.kyc.vo.Person;
import cn.cust.kyc.vo.Project;
import cn.cust.kyc.vo.Record;
import cn.cust.kyc.vo.Report;
import cn.cust.kyc.vo.Teacher;
//import cn.cust.kyc.vo.Verity;
import cn.edu.cust.levin.DAOException;

public class ProjectServiceImpl implements ProjectService {

	@Override
	public List<CodeType> getCodeTypes() throws DAOException {
		CodeTypeDao ctd = DAOFactory.getCodeTypeDBDao();
		return ctd.getCodeTypes();
	}

	@Override
	public boolean doCodeType(int option, String codeType, String _codeType) {
		CodeTypeDao ctd = DAOFactory.getCodeTypeDBDao();
		switch(option)
		{
		case 1 : return ctd.renameCodeType(codeType, _codeType);
		case 2 : return ctd.addCodeType(codeType);
		case 3 : return ctd.deleteCodeType(_codeType);
		default : return false;
		}
	}

	@Override
	public boolean addCode(int number, String type, String name, String info) throws DAOException {
		CodeDao cd = DAOFactory.getCodeDBDao();
		List list = cd.getCodesByNumber(number,type);
		if(list.size()>0)
			return false;
		if(number==0)
			number = cd.getMaxNumber(type)+1;
		Code c = new Code();
		c.setInfo(info);
		c.setName(name);
		c.setType(type);
		c.setNumber(number);
		cd.saveCode(c);
		return true;
	}

	@Override
	public boolean deleteCode(int id) throws DAOException {
		CodeDao cd = DAOFactory.getCodeDBDao();
		cd.delete(id);
		return true;
	}

	@Override
	public boolean updateCode(int id, String name, String info) throws DAOException {
		CodeDao cd = DAOFactory.getCodeDBDao();
		cd.update(id, name, info);
		return true;
	}

	@Override
	public List<Project> getProjects() {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.getProjects();
	}

	@Override
	public List<Code> getCodeByType(String codeType) {
		CodeTypeDao ctd = DAOFactory.getCodeTypeDBDao();
		return ctd.getCodesByType(codeType);
	}

	@Override
	public boolean saveProject(Project p) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.saveProject(p);
	}

	@Override
	public List<Project> getProjects(Pagination pagination) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		int resultCount = pd.getProjects().size();
		int size = pagination.getSize();
		pagination.setResultCount(resultCount);
		pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		return pd.getProjectsByPage(pagination);
	}

	@Override
	public String getMaxProjectKey(String innerPredix) throws DAOException {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.getMaxProjectKey(innerPredix);
	}
	/**
	 * 返回Achievement的最大数量
	 * @param innerPredix
	 * @return
	 * @throws DAOException
	 */
	public String getMaxAchievementKey(String innerPredix) throws DAOException{
		AchievementDao pd = DAOFactory.getAchievementDao();
		return pd.getMaxAchievementKey(innerPredix);
		//return null;// pd.get(innerPredix);
	}

	@Override
	public boolean deteteProject(String innerCode) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.deleteProject(innerCode);
	}

	@Override
	public Project getProject(String innerCode) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.getProject(innerCode);
	}

	@Override
	public boolean update(Project p) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.update(p);
	}

	@Override
	public List<Project> getProjects(String name, Pagination pagination, String year, String s) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
//		int resultCount = pd.getProjects().size();
//		int size = pagination.getSize();
//		pagination.setResultCount(resultCount);
//		pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		return pd.getProjectsByPage(name, pagination, year, s);
	}
	
	@Override
	public List<Project> getProjects(String name, Pagination pagination, String year, String s, boolean c) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.getProjectsByPage(name, pagination, year, s, c);
	}

	@Override
	public List<Integer> getStartYears() {
		List<Integer> list = new ArrayList<Integer>();
		try {
			ProjectDao pd = DAOFactory.getProjectDBDao();
			Date max = pd.getMaxStartDate();
			if(max==null)
				return null;
			Date min = pd.getMinStartDate();
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					if(pd.isExistInStartDate(year))
						list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Income> getIncomeByProject(Pagination pagination, String year,
			String nbbh) throws DAOException {
		IncomeDao icd = DAOFactory.getIncomeDao();
		int resultCount = icd.getIncomeByProject(nbbh).size();
		int size = pagination.getSize();
		pagination.setResultCount(resultCount);
		pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		return icd.getIncomeByPageAndProject(pagination, year, nbbh);
	}

	@Override
	public List<Income> getIncomeByProject(String nbbh, String year,
			String s) throws DAOException {
		IncomeDao icd = DAOFactory.getIncomeDao();
		return icd.getIncomeByProject(nbbh, year, s);
	}
	
	@Override
	public List<Integer> getIncomeYears(String predix, String nbbh) {
		//获得存储年份列表
		List<Integer> list = new ArrayList<Integer>();
		//获得项目登记年份
		String minYear = "";
		if(nbbh==null){  //“到款查询”
			try {
				IncomeDao icd = DAOFactory.getIncomeDao();
				Date min = icd.getMinStartDate(predix, nbbh);
				minYear = ""+ (min.getYear() + 1900);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			minYear=nbbh.substring(10,14);
		}
		
		//获得当前年份
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date=new Date();
		String max=df.format(date);
		String maxYear=max.substring(0,4);
	    //将年份转换为int型
		int maxInt=Integer.parseInt(maxYear);
		int minInt=Integer.parseInt(minYear);
		//将登记年份至当前年份存入List表里
		for(int a=minInt;a<=maxInt;a++)
		{
			list.add(a);
		}
		return list;
	}

	@Override
	public List<Project> getProjects(String s, String name) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.getProjects(s, name);
	}
	
	@Override
	public List<Project> getProjects(String s, String name, String year) {
		ProjectDao pd = DAOFactory.getProjectDBDao();
		return pd.getProjects(s, name, year);
	}

	@Override
	public void deleteIncome(String incomeId) {
		IncomeDao ind = DAOFactory.getIncomeDao();
		ind.deleteIncome(incomeId);
	}

	@Override
	public void saveIncome(Income income) {
		IncomeDao ind = DAOFactory.getIncomeDao();
		ind.saveIncome(income);
	}
	
	@Override
	public void updateIncome(Income income) {
		IncomeDao ind = DAOFactory.getIncomeDao();
		ind.updateIncome(income);
	}

	@Override
	public Income getIncomeById(String incomeId) {
		IncomeDao ind = DAOFactory.getIncomeDao();
		return ind.getIncome(incomeId);
	}

	@Override
	public String getMaxIncomeKey(String incomePredix) throws DAOException {
		IncomeDao ind = DAOFactory.getIncomeDao();
		return ind.getMaxIncomeKey(incomePredix);
	}

	@Override
	public List<Income> getIncomes(String predix, Pagination pagination,
			String year, String s) {
		IncomeDao ind = DAOFactory.getIncomeDao();
		return ind.getIncomes(predix, pagination, year, s);
	}

	@Override
	public List<Income> getIncomes(String predix, String year, String s) {
		IncomeDao ind = DAOFactory.getIncomeDao();
		return ind.getIncomes(predix, year, s);
	}

	
	//以下为 拨款  Appropriation
	@Override
	public List<Appropriation> getAppropriationByProject(Pagination pagination,
			String year, String nbbh) throws DAOException {
		ApproDao apd = DAOFactory.getApproDao();
		int resultCount = apd.getAppropriationByProject(nbbh).size();
		int size = pagination.getSize();
		pagination.setResultCount(resultCount);
		pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		return apd.getAppropriationByPageAndProject(pagination, year, nbbh);
	}

	@Override
	public Appropriation getAppropriationById(int ID) {
		ApproDao apd = DAOFactory.getApproDao();
		return apd.getAppropriation(ID);
	}

	@Override
	public List<Integer> getAppropriationYears(String predix, String nbbh) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			ApproDao apd = DAOFactory.getApproDao();
			Date max = apd.getMaxStartDate(predix, nbbh);
			if(max==null)
				return null;
			Date min = apd.getMinStartDate(predix, nbbh);
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getMaxAppropriationKey(String appropriateID) throws DAOException {
		ApproDao ind = DAOFactory.getApproDao();
		return ind.getMaxAppropriationKey(appropriateID);
	}

	@Override
	public Appropriation saveAppropriation(Appropriation ap) {
		ApproDao ind = DAOFactory.getApproDao();
		return ind.saveAppropriation(ap);
	}
	
	@Override
	public void deleteAppropriation(int ID) {
		ApproDao ind = DAOFactory.getApproDao();
		ind.deleteAppropriation(ID);
	}

	@Override
	public List<Appropriation> getAppropriations(String predix,
			Pagination pagination, String year, String s) {
		ApproDao ind = DAOFactory.getApproDao();
		return ind.getAppropriations(predix, pagination, year, s);
	}

	@Override
	public List<Appropriation> getAppropriations(String predix, String year,
			String s) {
		ApproDao ind = DAOFactory.getApproDao();
		return ind.getAppropriations(predix, year, s);
	}

	@Override
	public void updateAppropriation(Appropriation ap) {
		ApproDao ind = DAOFactory.getApproDao();
		ind.updateAppropration(ap);
	}

	@Override
	public List<Income> getIncomeByProject(String nbbh) throws DAOException {
		IncomeDao icd = DAOFactory.getIncomeDao();
		return icd.getIncomeByProject(nbbh);
	}

	@Override
	public List<Appropriation> getAppropriationByProject(String nbbh) throws DAOException {
		ApproDao apd = DAOFactory.getApproDao();
		return apd.getAppropriationByProject(nbbh);
	}
	
	@Override
	public List<Appropriation> getAppropriationsByProjectAndYear(String nbbh,
			String year, String s) {
		ApproDao apd = DAOFactory.getApproDao();
		return apd.getAppropriationsByProjectAndYear(nbbh, year, s);
	}
	
	@Override
	public List<Appropriation> getAppropriationByAppropriationID(String appropriateID) throws DAOException {
		ApproDao apd = DAOFactory.getApproDao();
		return apd.getAppropriationByAppropriationID(appropriateID);
	}

	@Override
	public List<Appropriation> getAppropriationByIncome(String incomeId) throws DAOException {
		ApproDao apd = DAOFactory.getApproDao();
		return apd.getAppropriationByIncome(incomeId);
	}

	@Override
	public String getMaxReturnBackKey(String returnbackID) throws DAOException {
		ApproDao ind = DAOFactory.getApproDao();
		return ind.getMaxReturnBackKey(returnbackID);
	}

	@Override
	public void deletePerson(int id) {
		PersonDao psd = DAOFactory.getPersonDao();
		psd.deletePerson(id);
	}

	@Override
	public Person getPersonById(int id) {
		PersonDao psd = DAOFactory.getPersonDao();
		return psd.getPerson(id);
	}

	@Override
	public List<Person> getPersonByProject(Pagination pagination, String year, String innerCode) throws DAOException {
		PersonDao psd = DAOFactory.getPersonDao();
		int resultCount = psd.getPersonByProject(innerCode).size();
		System.out.println("resultCount="+resultCount);
		int size = pagination.getSize();
		pagination.setResultCount(resultCount);
		pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		return psd.getPersonByPageAndProject(pagination, year, innerCode);
	}
	
	@Override
	public List<Person> getPersonByProject(String year, String innerCode,
			String s) throws DAOException {
		PersonDao psd = DAOFactory.getPersonDao();
		return psd.getPersonByPageAndProject(year, innerCode, s);
	}

	@Override
	public List<Integer> getPersonYears(String innerCode) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			PersonDao psd = DAOFactory.getPersonDao();
			list = psd.getYearList(innerCode);
//			int maxYear = psd.getMaxYear(innerCode);
//			if(maxYear==0)
//				return null;
//			int minYear = psd.getMinYear(innerCode);
//			list.add(minYear);
//			if(maxYear!=minYear){
//				for(int year=minYear+1;year<maxYear;year++){
//					list.add(year);
//				}
//				list.add(maxYear);
//			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Person> getPersons(String predix, Pagination pagination,
			String year, String s) {
		PersonDao psd = DAOFactory.getPersonDao();
		return psd.getPersons(predix, pagination, year, s);
	}

	@Override
	public List<Person> getPersons(String predix, String year, String s) {
		PersonDao psd = DAOFactory.getPersonDao();
		return psd.getPersons(predix, year, s);
	}

	@Override
	public Person savePerson(Person ps) {
		PersonDao psd = DAOFactory.getPersonDao();
		return psd.savePerson(ps);
	}

	@Override
	public void updatePerson(Person ps) {
		PersonDao psd = DAOFactory.getPersonDao();
		psd.updatePerson(ps);
	}

	@Override
	public void deletePFile(String fileCode) {
		PFileDao psd = DAOFactory.getPFileDao();
		psd.deletePFile(fileCode);
	}

	@Override
	public PFile getPFileById(String fileCode) {
		PFileDao psd = DAOFactory.getPFileDao();
		return psd.getPFile(fileCode);
	}

	@Override
	public List<PFile> getPFileByProject(Pagination pagination, String year, String innerCode) throws DAOException {
		PFileDao psd = DAOFactory.getPFileDao();
		int resultCount = psd.getPFileByProject(innerCode).size();
		int size = pagination.getSize();
		pagination.setResultCount(resultCount);
		pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		return psd.getPFileByPageAndProject(pagination, year, innerCode);
	}
	
	@Override
	public List<PFile> getPFileByProject(Pagination pagination, String year,
			String innerCode, String s) throws DAOException {
		PFileDao psd = DAOFactory.getPFileDao();
		if(pagination!=null){
			int resultCount = psd.getPFileByProject(innerCode).size();
			int size = pagination.getSize();
			pagination.setResultCount(resultCount);
			pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		}
		return psd.getPFileByProject(pagination, year, innerCode, s);
	}

	@Override
	public List<Integer> getPFileYears(String innerCode) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			PFileDao psd = DAOFactory.getPFileDao();
			int maxYear = psd.getMaxYear(innerCode);
			if(maxYear==0)
				return null;
			int minYear = psd.getMinYear(innerCode);
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<PFile> getPFiles(String predix, Pagination pagination,
			String year, String s) {
		PFileDao psd = DAOFactory.getPFileDao();
		return psd.getPFiles(predix, pagination, year, s);
	}

	@Override
	public List<PFile> getPFiles(String predix, String year, String s) {
		PFileDao psd = DAOFactory.getPFileDao();
		return psd.getPFiles(predix, year, s);
	}

	@Override
	public PFile savePFile(PFile ps) {
		PFileDao psd = DAOFactory.getPFileDao();
		return psd.savePFile(ps);
	}

	@Override
	public void updatePFile(PFile ps) {
		PFileDao psd = DAOFactory.getPFileDao();
		psd.updatePFile(ps);
	}
	
	@Override
	public String getMaxPFileKey(String predix) throws DAOException{
		PFileDao psd = DAOFactory.getPFileDao();
		return psd.getMaxPFileKey(predix);
	}
	
	@Override
	public void saveAchievement(Achievement acm) throws DAOException {
		AchievementDao amd = DAOFactory.getAchievementDao();
		amd.saveAchievement(acm);
	}

	@Override
	public Achievement getAchievement(String s, String innerCode) throws DAOException {
		AchievementDao amd = DAOFactory.getAchievementDao();
		System.out.println("getAchievement测试 "+s+" "+innerCode);
		return amd.getAchievement(s, innerCode);
	}

	@Override
	public void deleteAchievement(int id) {
		AchievementDao amd = DAOFactory.getAchievementDao();
		amd.deleteAchievement(id);
	}

	@Override
	public Achievement getAchievementById(int id) {
		AchievementDao amd = DAOFactory.getAchievementDao();
		return amd.getAchievementById(id);
	}

	@Override
	public List<Integer> getAchievementYears(String predix, String type) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			AchievementDao amd = DAOFactory.getAchievementDao();
			Date max = amd.getMaxStartDate(predix, type);
			if(max==null)
				return null;
			Date min = amd.getMinStartDate(predix, type);
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Achievement> getAchievements(String type, String predix,
			Pagination pagination, String year, String s) {
		AchievementDao amd = DAOFactory.getAchievementDao();
		return amd.getAchievements(type, predix, pagination, year, s);
	}

	@Override
	public List<Achievement> getAchievements(String type, String predix,
			String year, String s) {
		AchievementDao amd = DAOFactory.getAchievementDao();
		return amd.getAchievements(type, predix, year, s);
	}

	@Override
	public void updateAchievement(Achievement acm) throws DAOException {
		AchievementDao amd = DAOFactory.getAchievementDao();
		amd.updateAchievement(acm);
	}

	@Override
	public List<AchieveCristic> getAchieveCristics(int achievementId) throws DAOException {
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		return acd.getAchieveCristics(achievementId);
	}

	@Override
	public List<AchieveCristic> getAchieveCristicsByPerson(String type, String name) throws DAOException {
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		return acd.getAchieveCristicsByName(type, name);
	}

	
	@Override
	public void deleteAchieveCristic(int iD) throws DAOException {
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		acd.deleteCristic(iD);
	}

	@Override
	public void saveAchieveCristic(AchieveCristic ac) throws DAOException {
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		acd.saveCristic(ac);
	}

	@Override
	public AchieveCristic getAchieveCristic(int ID) throws DAOException{
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		return acd.getAchieveCristic(ID);
	}
	
	@Override
	public void updateAchieveCristic(AchieveCristic ac) throws DAOException {
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		acd.updateCristic(ac);
	}
	
	@Override
	public void deleteAward(int id) {
		AwardDao ad = DAOFactory.getAwardDao();
		ad.deleteAward(id);
	}

	@Override
	public List<Integer> getAwardYears() {
		List<Integer> list = new ArrayList<Integer>();
		try {
			AwardDao ad = DAOFactory.getAwardDao();
			Date max = ad.getMaxStartDate();
			if(max==null)
				return null;
			Date min = ad.getMinStartDate();
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Award getAwardById(int id) {
		AwardDao ad = DAOFactory.getAwardDao();
		return ad.getAward(id);
	}

	@Override
	public List<Award> getAwards(Pagination pagination, String year, String s) {
		AwardDao ad = DAOFactory.getAwardDao();
		return ad.getAwards(pagination, year, s);
	}

	@Override
	public void saveAward(Award a) {
		AwardDao ad = DAOFactory.getAwardDao();
		ad.saveAward(a);
	}

	@Override
	public void updateAward(Award a) {
		AwardDao ad = DAOFactory.getAwardDao();
		ad.updateAward(a);
	}

	@Override
	public List<Award> getAwards(String year, String s) {
		AwardDao ad = DAOFactory.getAwardDao();
		return ad.getAwards(year, s);
	}
	
	@Override
	public void deletePatent(int id) {
		PatentDao ad = DAOFactory.getPatentDao();
		ad.deletePatent(id);
	}

	@Override
	public List<Integer> getPatentYears() {
		List<Integer> list = new ArrayList<Integer>();
		try {
			PatentDao ad = DAOFactory.getPatentDao();
			Date max = ad.getMaxStartDate();
			if(max==null)
				return null;
			Date min = ad.getMinStartDate();
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Patent getPatentById(int id) {
		PatentDao ad = DAOFactory.getPatentDao();
		return ad.getPatent(id);
	}

	@Override
	public List<Patent> getPatents(Pagination pagination, String year, String s) {
		PatentDao ad = DAOFactory.getPatentDao();
		return ad.getPatents(pagination, year, s);
	}
	
	public List<Patent> getApplyPatents(Pagination pagination,String year,String s,String flag){
		PatentDao ad=DAOFactory.getPatentDao();
		return ad.getApplyPatents(pagination,year,s,flag);
	}

	@Override
	public void savePatent(Patent a) {
		PatentDao ad = DAOFactory.getPatentDao();
		ad.savePatent(a);
	}

	@Override
	public void updatePatent(Patent a) {
		PatentDao ad = DAOFactory.getPatentDao();
		ad.updatePatent(a);
	}

	@Override
	public List<Patent> getPatents(String year, String s) {
		PatentDao ad = DAOFactory.getPatentDao();
		return ad.getPatents(year, s);
	}

	@Override
	public void deletePaper(int id) {
		PaperDao ad = DAOFactory.getPaperDao();
		ad.deletePaper(id);
	}

	@Override
	public List<Integer> getPaperYears() {
		List<Integer> list = new ArrayList<Integer>();
		try {
			PaperDao ad = DAOFactory.getPaperDao();
			Date max = ad.getMaxStartDate();
			if(max==null)
				return null;
			Date min = ad.getMinStartDate();
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Paper getPaperById(int id) {
		PaperDao ad = DAOFactory.getPaperDao();
		return ad.getPaper(id);
	}

	@Override
	public List<Paper> getPapers(Pagination pagination, String year, String s) {
		PaperDao ad = DAOFactory.getPaperDao();
		return ad.getPapers(pagination, year, s);
	}

	@Override
	public void savePaper(Paper a) {
		PaperDao ad = DAOFactory.getPaperDao();
		ad.savePaper(a);
	}

	@Override
	public void updatePaper(Paper a) {
		PaperDao ad = DAOFactory.getPaperDao();
		ad.updatePaper(a);
	}

	@Override
	public List<Paper> getPapers(String year, String s) {
		PaperDao ad = DAOFactory.getPaperDao();
		return ad.getPapers(year, s);
	}
	
	@Override
	public void deleteBookmaking(int id) {
		BookmakingDao ad = DAOFactory.getBookmakingDao();
		ad.deleteBookmaking(id);
	}

	@Override
	public List<Integer> getBookmakingYears() {
		List<Integer> list = new ArrayList<Integer>();
		try {
			BookmakingDao ad = DAOFactory.getBookmakingDao();
			Date max = ad.getMaxStartDate();
			if(max==null)
				return null;
			Date min = ad.getMinStartDate();
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Bookmaking getBookmakingById(int id) {
		BookmakingDao ad = DAOFactory.getBookmakingDao();
		return ad.getBookmaking(id);
	}

	@Override
	public List<Bookmaking> getBookmakings(Pagination pagination, String year, String s) {
		BookmakingDao ad = DAOFactory.getBookmakingDao();
		return ad.getBookmakings(pagination, year, s);
	}

	@Override
	public void saveBookmaking(Bookmaking a) {
		BookmakingDao ad = DAOFactory.getBookmakingDao();
		ad.saveBookmaking(a);
	}

	@Override
	public void updateBookmaking(Bookmaking a) {
		BookmakingDao ad = DAOFactory.getBookmakingDao();
		ad.updateBookmaking(a);
	}

	@Override
	public List<Bookmaking> getBookmakings(String year, String s) {
		BookmakingDao ad = DAOFactory.getBookmakingDao();
		return ad.getBookmakings(year, s);
	}
	
	@Override
	public void deleteReport(int id) {
		ReportDao ad = DAOFactory.getReportDao();
		ad.deleteReport(id);
	}

	@Override
	public List<Integer> getReportYears() {
		List<Integer> list = new ArrayList<Integer>();
		try {
			ReportDao ad = DAOFactory.getReportDao();
			Date max = ad.getMaxStartDate();
			if(max==null)
				return null;
			Date min = ad.getMinStartDate();
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Report getReportById(int id) {
		ReportDao ad = DAOFactory.getReportDao();
		return ad.getReport(id);
	}

	@Override
	public List<Report> getReports(Pagination pagination, String year, String s) {
		ReportDao ad = DAOFactory.getReportDao();
		return ad.getReports(pagination, year, s);
	}

	@Override
	public void saveReport(Report a) {
		ReportDao ad = DAOFactory.getReportDao();
		ad.saveReport(a);
	}

	@Override
	public void updateReport(Report a) {
		ReportDao ad = DAOFactory.getReportDao();
		ad.updateReport(a);
	}

	@Override
	public List<Report> getReports(String year, String s) {
		ReportDao ad = DAOFactory.getReportDao();
		return ad.getReports(year, s);
	}
	
	@Override
	public void deleteAuthor(int id) throws DAOException {
		AuthorDao ad = DAOFactory.getAuthorDao();
		ad.deleteAuthor(id);		
	}

	@Override
	public List<Author> getAuthors(int type, int id) throws DAOException {
		AuthorDao ad = DAOFactory.getAuthorDao();
		return ad.getAuthors(type, id);
	}

	@Override
	public void saveAuthor(Author a) throws DAOException {
		AuthorDao ad = DAOFactory.getAuthorDao();
		ad.saveAuthor(a);
	}

	@Override
	public Author getAuthor(int id) throws DAOException {
		AuthorDao ad = DAOFactory.getAuthorDao();
		return ad.getAuthor(id);
	}

	@Override
	public void updateAuthor(Author a) throws DAOException {
		AuthorDao ad = DAOFactory.getAuthorDao();
		ad.updateAuthor(a);
	}

	@Override
	public List<?> getAwardByPerson(String voName, int i, String name) throws DAOException {
		AuthorDao ad = DAOFactory.getAuthorDao();
		return ad.getAwardByAuthor(voName, i, name);
	}
	
	@Override
	public Member getMemberById(int id) {
		MemberDao mbd = DAOFactory.getMemberDao();
		return mbd.getMember(id);
	}

	@Override
	public List<Member> getMembers(Pagination pagination, String s) {
		MemberDao mbd = DAOFactory.getMemberDao();
		return mbd.getMembers(pagination, s);
	}

	@Override
	public List<Member> getMembers(String s) {
		MemberDao mbd = DAOFactory.getMemberDao();
		return mbd.getMembers(s);
	}

	@Override
	public void saveMember(Member ps) {
		MemberDao mbd = DAOFactory.getMemberDao();
		mbd.saveMember(ps);
	}

	@Override
	public void updateMember(Member ps) {
		MemberDao mbd = DAOFactory.getMemberDao();
		mbd.updateMember(ps);
	}

	@Override
	public List<String> getMemberOrganizations() throws DAOException {
		MemberDao mbd = DAOFactory.getMemberDao();
		return mbd.getOrganizations();
	}

	@Override
	public void deleteMember(int id) throws DAOException {
		MemberDao mbd = DAOFactory.getMemberDao();
		mbd.deleteMember(id);
	}

	@Override
	public int personControl() {
		PersonDao psd = DAOFactory.getPersonDao();
		return psd.doPersonControl();
	}

	@Override
	public void deleteRecord(Record r) {
		RecordDao rd = DAOFactory.getRecordDao();
		if(r.getDoType()==ConstData.ApplyForUpdate)
		{
			Object no = rd.getNewTable(r);
			rd.deleteObject(no);
		}
		rd.delete(r);
	}

	@Override
	public Object getNewTableByRecord(Record r) {
		RecordDao rd = DAOFactory.getRecordDao();
		return rd.getNewTable(r);
	}

	@Override
	public Object getOldTableByRecord(Record r) {
		RecordDao rd = DAOFactory.getRecordDao();
		return rd.getOldTable(r);
	}

	@Override
	public Record getRecordById(int id) {
		RecordDao rd = DAOFactory.getRecordDao();
		return rd.getRecord(id);
	}
	
	
	@Override
	public List<Record> getRecords(Pagination pagination, String s, Teacher teacher, boolean b) {
		RecordDao rd = DAOFactory.getRecordDao();
		return rd.getRecords(pagination, s, teacher, b);
	}

	@Override
	public void saveRecord(Record r) {
		RecordDao rd = DAOFactory.getRecordDao();
		rd.saveRecord(r);
	}
	
	@Override
	public void unverifyRecord(Record r){
		RecordDao rd = DAOFactory.getRecordDao();
		rd.updateRecord(r);
		Object old = rd.getOldTable(r);
		String innerCode = "";
		if(old instanceof Appropriation){
			innerCode = ((Appropriation)old).getProject().getInnerCode();
		}else if(old instanceof Income){
			innerCode = ((Income)old).getProject().getInnerCode();
		}else if(old instanceof Person){
			innerCode = ((Person)old).getProject().getInnerCode();
		}else if(old instanceof Project){
			innerCode = ((Project)old).getInnerCode();
		}
		this.unlockProject(innerCode);
	}

	@Override
	public boolean verifyRecord(Record r) {
		try {
			DateFormat dataformat=new SimpleDateFormat("yyyy");

			RecordDao rd = DAOFactory.getRecordDao();
			if(r.getDoType()==ConstData.ApplyForDelete){
				Object oo = rd.getOldTable(r);
				String innerCode = "";
				//以下if是修改引起的其他表的操作，现将其放入操作审核中
				if(oo instanceof Appropriation){
					Appropriation ap = (Appropriation) oo;
					Project p = ap.getProject();
				//	System.out.println("同意删除一条拨款："+p.getStartDate()+"    "+ap.getApproDate());
					String timeString1=dataformat.format(ap.getApproDate());
				//	System.out.println("删除测试："+p.getDnnd()+ "    "+timeString1);
					if(timeString1.equals(p.getDnnd())){
						p.setYearCapitalAccount(p.getYearCapitalAccount() - ap.getCapitalAccount());
						p.setYearGrant(p.getYearGrant() - ap.getAppropriation());
					}
					/*if(p.getStartDate().getYear()==ap.getApproDate().getYear()){
						p.setYearCapitalAccount(p.getYearCapitalAccount() - ap.getCapitalAccount());
						p.setYearGrant(p.getYearGrant() - ap.getAppropriation());
					}*/
					p.setTotalCapitalAccount(p.getTotalCapitalAccount() - ap.getCapitalAccount());
					p.setTotalGrant(p.getTotalGrant() - ap.getAppropriation());
					p.setRiskFee(p.getRiskFee() - ap.getRiskFee());
					update(p);
					innerCode = ((Appropriation)oo).getProject().getInnerCode();
				}else if(oo instanceof Income){
					Income in = (Income) oo;
					Project p = in.getProject();
					String timeString2=dataformat.format(in.getIncomeDate());
					if(timeString2.equals(p.getDnnd()))
						p.setYearArriveMoney(p.getYearArriveMoney() - in.getIncome());
				/*	if(p.getStartDate().getYear()==in.getIncomeDate().getYear())
						p.setYearArriveMoney(p.getYearArriveMoney() - in.getIncome());*/
					p.setTotalArriveMoney(p.getTotalArriveMoney() - in.getIncome());
					update(p);
					innerCode = ((Income)oo).getProject().getInnerCode();
				}else if(oo instanceof Person){
					innerCode = ((Person)oo).getProject().getInnerCode();
				}else if(oo instanceof Project){
					innerCode = ((Project)oo).getInnerCode();
				}
				//到此结束
				FieldsControl.doVoSetMethod(oo, "delFlg", true);
				FieldsControl.doVoSetMethod(oo, "delDate", new Date());
				rd.updateObject(oo);
				System.out.println("==============verify_del-----------: "+innerCode);
				this.unlockProject(innerCode);
			}else if(r.getDoType()==ConstData.ApplyForUpdate){
				String innerCode = "";
				Object oo = rd.getOldTable(r);
				Object no = rd.getNewTable(r);
				//以下if是修改引起的其他表的操作，现将其放入操作审核中
				if(no instanceof Project){
					Project o = (Project)no;
					innerCode = ((Project)oo).getInnerCode();//获取项目内部编号
					String overFormat = o.getOverFormat();
					//生成完成信息
					if(overFormat!=null){
						String of[] = overFormat.split(",");
						if(!"".equals(of[0])){
							List<String> _of = new ArrayList<String>();
							_of.add("结题");
							_of.add("鉴定");
							_of.add("验收");
							for(String s : of){
								Achievement acm = getAchievement(s, ((Project)oo).getInnerCode());
								acm.setAchieveMan(o.getProLeader());
								//clg修改，2012.1.20
								//acm.setProject((Project)oo);
								acm.setInnerId(((Project)oo).getInnerCode());
								acm.setAchieveName(o.getProName());
								acm.setItsOrg(o.getOrganization());
								acm.setIsApplay(o.getToUseFlg());
								acm.setApplayOrg(o.getToUseUnit());
								acm.setCreater(o.getUpdater());
								acm.setCreateDate(new Date());
								acm.setType(s);
								if(s.equals("结题")){
									_of.remove("结题");
									acm.setConcludeDate(o.getOverDate());
									acm.setConcludeLevel(o.getOverConclusion());
									acm.setConcludeOrg(o.getOverDepartment());
								}else if(s.equals("鉴定")){
									_of.remove("鉴定");
									acm.setConcludeDate(o.getEvalueDate());
									acm.setConcludeLevel(o.getEvalueConclusion());
									acm.setConcludeOrg(o.getEvalueDepartment());
								}else if(s.equals("验收")){
									_of.remove("验收");
									acm.setConcludeDate(o.getAcceptDate());
									acm.setConcludeLevel(o.getAcceptConclusion());
									acm.setConcludeOrg(o.getAcceptDepartment());
								}
								saveAchievement(acm);
							}
							for(String s : _of){
								Achievement acm = getAchievement(s, ((Project)oo).getInnerCode());
								if(acm.getID()!=0)
									deleteAchievement(acm.getID());
							}
						}
					}else{
						List<String> _of = new ArrayList<String>();
						_of.add("结题");
						_of.add("鉴定");
						_of.add("验收");
						for(String s : _of){
							Achievement acm = getAchievement(s, ((Project)oo).getInnerCode());
							if(acm.getID()!=0)
								deleteAchievement(acm.getID());
						}
					}
				}else if(no instanceof Appropriation){
					Project p = ((Appropriation) no).getProject();
					double money = ((Appropriation) oo).getAppropriation() - ((Appropriation)no).getAppropriation();
					double capitalMoney = ((Appropriation) oo).getCapitalAccount() - ((Appropriation)no).getCapitalAccount();
					
					String timeString3=dataformat.format(((Appropriation) no).getApproDate());//拨款时间==当年时间
					if(timeString3.equals(p.getDnnd())){
							p.setYearGrant(p.getYearGrant() - money);
							p.setYearCapitalAccount(p.getYearCapitalAccount()-capitalMoney);
					}
					/*
					if(p.getStartDate().getYear()==((Appropriation) oo).getApproDate().getYear()){
						p.setYearCapitalAccount(p.getYearCapitalAccount() - ((Appropriation)oo).getCapitalAccount());
						p.setYearGrant(p.getYearGrant() - ((Appropriation)oo).getAppropriation());
					}
					if(p.getStartDate().getYear()==((Appropriation) no).getApproDate().getYear()){
						p.setYearCapitalAccount(p.getYearCapitalAccount() - ((Appropriation)no).getCapitalAccount());
						p.setYearGrant(p.getYearGrant() + ((Appropriation)no).getAppropriation());
					}*/
					
					p.setTotalCapitalAccount(p.getTotalCapitalAccount() - capitalMoney);
					p.setTotalGrant(p.getTotalGrant() - money);
					p.setRiskFee(p.getRiskFee() - ((Appropriation) oo).getRiskFee() + ((Appropriation)no).getRiskFee());
					update(p);
					innerCode = p.getInnerCode();
				}else if(no instanceof Income){
					String timeString4=dataformat.format(((Income) no).getIncomeDate());
					Project p = getProject(((Income) no).getProject().getInnerCode());
					double money = ((Income) oo).getIncome()-((Income) no).getIncome();
					if(timeString4.equals(p.getDnnd())){
						p.setYearArriveMoney(p.getYearArriveMoney() -money);
					}
				/*	if(p.getStartDate().getYear()==((Income) oo).getIncomeDate().getYear())
						p.setYearArriveMoney(p.getYearArriveMoney() - ((Income) oo).getIncome());
					if(p.getStartDate().getYear()==((Income) no).getIncomeDate().getYear())
						p.setYearArriveMoney(p.getYearArriveMoney() + ((Income) no).getIncome());*/
					p.setTotalArriveMoney(p.getTotalArriveMoney() - money);
					update(p);
					innerCode = p.getInnerCode();
				}else if(no instanceof Person){
					innerCode = ((Person)no).getProject().getInnerCode();
				}
				//到此结束
				FieldsControl.doVoCopy(no, oo);
				FieldsControl.doVoSetMethod(oo, "delFlg", false);
				rd.updateObject(oo);
				this.unlockProject(innerCode);
			}
			r.setVerifyDate(new Date());
			r.setFlg(ConstData.RecordVerified);
			rd.updateRecord(r);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updateRecord(Record r) {
		RecordDao rd = DAOFactory.getRecordDao();
		rd.updateRecord(r);
	}

	@Override
	public List<Member> getMemberInfoListByPredix(String strpredix) {
		MemberDao mbd = DAOFactory.getMemberDao();
		return mbd.getMemberInfoListByPredix(strpredix);
	}

	@Override
	public double[] getSumIncomeAndWorkloadByYearAndInnerCode(String innerCode,
			String year) {
		PersonDao psd = DAOFactory.getPersonDao();
		IncomeDao icd = DAOFactory.getIncomeDao();
		double income = icd.getSumIncomeByInnerCodeAndYear(innerCode, year);
		double personWorkload = psd.getSumWorkloadByInnerCodeAndYear(innerCode, year);
		return new double[]{income, personWorkload};
	}

	@Override
	public void lockProject(String innerCode) {
		DAOFactory.getProjectDBDao().lockUnlockProject(innerCode, true);
	}

	@Override
	public void unlockProject(String innerCode) {
		DAOFactory.getProjectDBDao().lockUnlockProject(innerCode, false);
	}

	@Override
	public boolean isProjectLock(String innerCode) {
		boolean r = false;
		if(RecordSetting.getRecordSetting())
			r = DAOFactory.getProjectDBDao().isProjectLock(innerCode);
		return r;
	}
   
	public List<Author> getAuthorByOutId(Pagination pagination,String outId, String type){
		AuthorDao aud = DAOFactory.getAuthorDao();
		int resultCount = aud.getAuthorByOutId(outId, type).size();
		int size = pagination.getSize();
		pagination.setResultCount(resultCount);
		pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		return aud.getAuthorByPageAndProject(pagination, outId, type);
	}
	
	public List<Author> getAuthorByOutId(String outId, String type){
		AuthorDao aud = DAOFactory.getAuthorDao();
		return aud.getAuthorByOutId(outId, type);
	}
	
	public List<AchieveCristic> getAchieveCristicByAchievementId(Pagination pagination,String achievementId){
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		int resultCount = acd.getAchieveCristicByAchievementId(achievementId).size();
		System.out.println("mark2");
		int size = pagination.getSize();
		pagination.setResultCount(resultCount);
		pagination.setPageCount(resultCount/size + ((resultCount%size==0)?0:1));
		return acd.getAchieveCristicByPageAndAchievement(pagination, achievementId);
	}
	
	@Override
	public List<AchieveCristic> getAchieveCristicByAchievementId(String achievementId) {
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		return acd.getAchieveCristicByAchievementId(achievementId);
	}
	
	@Override
	public List<AchieveCristic> getAchieveCristic(String predix, Pagination pagination, String s) {
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		return acd.getAchieveCristic(predix, pagination, s);
	}
	
	@Override
	public List<AchieveCristic> getAchieveCristic(String predix, String s) {
		AchieveCristicDao acd = DAOFactory.getAchieveCristicDao();
		return acd.getAchieveCristic(predix, s);
	}

	@Override
	public List<Project> getSyncProjects(String syncyear) {
		// TODO Auto-generated method stub
		ProjectDao pd = DAOFactory.getProjectDBDao();
		List<Project>  projectList=pd.getSyncMoneyProjects(syncyear);
		return projectList;
	}

	@Override
	public InfoTable getInfoByKey(String key) {
		// TODO Auto-generated method stub
		InfoDao idao=DAOFactory.getInfoDao();
		return idao.getInfoBykey(key);
	}
	
	public void updateInfoTable(InfoTable infoTable){
		InfoDao idao=DAOFactory.getInfoDao();
		idao.update(infoTable);
	}

	@Override
	public List<Integer> getCurrentYears() {
		// TODO Auto-generated method stub
    	//System.out.println("table1:"+table1.getId()+"  "+table1.getKeyword()+" "+table1.getKeyindex());
		//table1.setKeyindex(syncyear);
		//projectService.updateInfoTable(table1);
		List<Integer> list = new ArrayList<Integer>();
		try {
			InfoTable table1=getInfoByKey("syear");
			ProjectDao pd = DAOFactory.getProjectDBDao();
			Date max = pd.getMaxStartDate();
			if(max==null)
				return null;
			Date min = pd.getMinStartDate();
			int maxYear =new Integer(table1.getKeyindex()).intValue();
			int minYear = min.getYear() + 1900;
			list.add(minYear);
			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					if(pd.isExistInStartDate(year))
						list.add(year);
				}
				list.add(maxYear);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Integer> getCurrentIncomeYears(String predix, String nbbh) {
		// TODO Auto-generated method stub
		//获得存储年份列表
		List<Integer> list = new ArrayList<Integer>();
		//获得项目登记年份
		String minYear = "";
		if(nbbh==null){  //“到款查询”
			try {
				IncomeDao icd = DAOFactory.getIncomeDao();
				Date min = icd.getMinStartDate(predix, nbbh);
				minYear = ""+ (min.getYear() + 1900);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			minYear=nbbh.substring(10,14);
		}
		InfoTable table1=getInfoByKey("syear");
		int maxInt=new Integer(table1.getKeyindex()).intValue();
		//获得当前年份
/*		DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date=new Date();
		String max=df.format(date);
		String maxYear=max.substring(0,4);
	    //将年份转换为int型
		int maxInt=Integer.parseInt(maxYear);*/
		int minInt=Integer.parseInt(minYear);
		//将登记年份至当前年份存入List表里
		list.add(minInt);
		for(int a=minInt+1;a<=maxInt;a++)
		{
			list.add(a);
		}
		return list;
	}

	@Override
	public List<Integer> getCurrentAppropriationYears(String predix, String nbbh) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		try {
			ApproDao apd = DAOFactory.getApproDao();
			Date max = apd.getMaxStartDate(predix, nbbh);
			if(max==null)
				return null;
			Date min = apd.getMinStartDate(predix, nbbh);
			int maxYear = max.getYear() + 1900;
			int minYear = min.getYear() + 1900;

			InfoTable table1=getInfoByKey("syear");
			int currentYear=new Integer(table1.getKeyindex()).intValue();
			
			if(currentYear>=minYear){
				list.add(minYear);
				if(maxYear>=currentYear){
					for(int year=minYear+1;year<=currentYear;year++){
						list.add(year);
						}
					}
				else{
					for(int year=minYear+1;year<=maxYear;year++){
						list.add(year);
						}
					}
				}
			else
				return null;
/*			if(maxYear!=minYear){
				for(int year=minYear+1;year<maxYear;year++){
					list.add(year);
				}
				list.add(maxYear);
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Integer> getCurrentPersonYears(String innerCode) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> currentList = new ArrayList<Integer>();
		InfoTable table1=getInfoByKey("syear");
		int currentYear=new Integer(table1.getKeyindex()).intValue();
		try {
			PersonDao psd = DAOFactory.getPersonDao();
			list = psd.getYearList(innerCode);
			for(int i=0;i<list.size();i++)
				if(list.get(i)<=currentYear)
					currentList.add(list.get(i));
			return currentList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentList;
	}

	@Override
	public List<Integer> getCristicYears() {
		// TODO Auto-generated method stub
		List<Integer> list=new ArrayList<Integer>();
		List<Integer> curentList=new ArrayList<Integer>();
		InfoTable table1=getInfoByKey("syear");
		int currentYear=new Integer(table1.getKeyindex()).intValue();
		AchieveCristicDao acd=DAOFactory.getAchieveCristicDao();
		list=acd.getCristicYears();
		for(int i=0;i<list.size();i++){
			if(list.get(i)<=currentYear)
				curentList.add(list.get(i));
		}
		return curentList;
	}
	
	
	
}
