package cn.cust.kyc.bo;

import java.util.List;

import cn.cust.kyc.util.Pagination;
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

public interface ProjectService {

	/**
	 * @return 所有的CodeType
	 * @throws DAOException 
	 */
	public List<CodeType> getCodeTypes() throws DAOException;

	/**
	 * @param option 处理方式，1：修改，2：添加，3：删除
	 * @param codeType 新代码类型
	 * @param _codeType 原代码类型
	 * @return
	 */
	public boolean doCodeType(int option, String codeType, String _codeType);

	/**
	 * @param number代码代号
	 * @param type代码类型
	 * @param name代码名称
	 * @param info说明
	 * @throws DAOException 
	 */
	public boolean addCode(int number, String type, String name, String info) throws DAOException;

	/**
	 * @param id  Code表主键
	 * @throws DAOException 
	 */
	public boolean deleteCode(int id) throws DAOException;

	/**
	 * @param id  Code表主键
	 * @param name新代码类型
	 * @param info新代码说明
	 * @throws DAOException 
	 */
	public boolean updateCode(int id, String name, String info) throws DAOException;

	/**
	 * 查找所有Project
	 * @return
	 */
	public List<Project> getProjects();

	/**
	 * @param codeType
	 * @return返回codeType类型的所有Code
	 */
	public List<Code> getCodeByType(String codeType);

	/**
	 * 保存一个项目p
	 * @param p
	 * @return
	 */
	public boolean saveProject(Project p);

	/**
	 * 根据分页信息查找Project
	 * @param pagination
	 * @return
	 */
	public List<Project> getProjects(Pagination pagination);

	/**
	 * @param string 科室代号
	 * @throws DAOException 
	 * @return返回最大的Project主键
	 */
	public String getMaxProjectKey(String string) throws DAOException;

	/**
	 * 获得目前Achievement最大数目
	 * @param innerPredix
	 * @return
	 * @throws DAOException
	 */
	public String getMaxAchievementKey(String innerPredix) throws DAOException;
	/**
	 * 删除编号为innerCode的项目
	 * @param innerCode
	 * @return
	 */
	public boolean deteteProject(String innerCode);

	/**
	 * 提取内部编号为innerCode的项目
	 * @param innerCode
	 * @return
	 */
	public Project getProject(String innerCode);

	/**
	 * 更新Project p
	 * @param p
	 * @return
	 */
	public boolean update(Project p);

	/**
	 * 按年份取project，年份为all全部取出
	 * @param name科室名
	 * @param pagination
	 * @param year
	 * @param s 搜索条件
	 * @return
	 */
	public List<Project> getProjects(String name, Pagination pagination, String year, String s);

	/**
	 * 同上，增加了c，以确定是否统计数据
	 * @param name
	 * @param pagination
	 * @param year
	 * @param s
	 * @param c
	 * @return
	 */
	public List<Project> getProjects(String name, Pagination pagination, String year, String s, boolean c);
	
	/**
	 * 查找项目的所有开始年份
	 * @return
	 */
	public List<Integer> getStartYears();

	/**
	 * 按项目内部编号查找到款
	 * @param pagination 
	 * @param year
	 * @param nbbh
	 * @return
	 * @throws DAOException 
	 */
	public List<Income> getIncomeByProject(Pagination pagination, String year, String nbbh) throws DAOException;

	/**
	 * 按项目内部编号、year,条件 查找到款
	 * @param nbbh
	 * @param year
	 * @param s
	 * @return
	 * @throws DAOException 
	 */
	public List<Income> getIncomeByProject(String nbbh, String year, String s) throws DAOException;
	
	
	/**
	 * 查找某项目到款的年份
	 * @param nbbh
	 * @param  
	 * @return
	 */
	public List<Integer> getIncomeYears(String predix, String nbbh);

	/**
	 * @param s搜索条件
	 * @param name科室名
	 * @return
	 */
	public List<Project> getProjects(String s, String name);
	
	/**
	 * @param s搜索条件
	 * @param name科室名
	 * @param year 年度
	 * @return
	 */
	public List<Project> getProjects(String s, String name, String year);
    /**
     * 
     * @param syncyear 同步年度
     * @return
     */
	public List<Project>getSyncProjects(String syncyear);
	/**
	 * 保存一个Income
	 * @param income
	 */
	public void saveIncome(Income income);
	
	/**
	 * 更新一个Income
	 * @param income
	 */
	public void updateIncome(Income income);

	/**
	 * 删除一个Income
	 * @param incomeId
	 */
	public void deleteIncome(String incomeId);

	/**
	 * 获取一个income
	 * @param incomeId
	 * @return
	 */
	public Income getIncomeById(String incomeId);

	/**
	 * 查找最大的一个incomeId
	 * @param incomeId科室前缀
	 * @return
	 * @throws DAOException 
	 */
	public String getMaxIncomeKey(String incomeId) throws DAOException;

	/**
	 * 查找相关的Income List
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Income> getIncomes(String predix, Pagination pagination,
			String year, String s);

	/**
	 * 不按分页取数据
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Income> getIncomes(String predix, String year, String s);

	/**
	 * 类似getIncomeByProject
	 * @param pagination
	 * @param year
	 * @param nbbh
	 * @return
	 * @throws DAOException 
	 */
	public List<Appropriation> getAppropriationByProject(Pagination pagination,
			String year, String nbbh) throws DAOException;

	/**
	 * @param predix
	 * @param nbbh
	 * @return
	 */
	public List<Integer> getAppropriationYears(String predix, String nbbh);

	/**
	 * @param iD
	 * @return
	 */
	public Appropriation getAppropriationById(int iD);

	/**
	 * @param appropriateID
	 * @return
	 * @throws DAOException 
	 */
	public String getMaxAppropriationKey(String appropriateID) throws DAOException;
	
	/**
	 * 保存Appropriation
	 * @param ap
	 */
	public Appropriation saveAppropriation(Appropriation ap);

	/**
	 * @param ap
	 */
	public void updateAppropriation(Appropriation ap);

	/**
	 * @param iD
	 */
	public void deleteAppropriation(int iD);

	/**
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Appropriation> getAppropriations(String predix,
			Pagination pagination, String year, String s);

	/**
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Appropriation> getAppropriations(String predix, String year,
			String s);

	/**
	 * 查找某一项目的所有到款
	 * @param 项目内部编号
	 * @return
	 * @throws DAOException 
	 */
	public List<Income> getIncomeByProject(String nbbh) throws DAOException;

	/**
	 * 根据项目获取拨款，用于回拨风险金
	 * @param nbbh
	 * @return
	 * @throws DAOException 
	 */
	public List<Appropriation> getAppropriationByProject(String nbbh) throws DAOException;

	/**
	 * 根据项目内部编号、拨款年度、条件  查询拨款
	 * @param nbbh
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Appropriation> getAppropriationsByProjectAndYear(String nbbh,
			String year, String s);
	/**
	 * 根据拨款单获取拨款
	 * @param appropriateID
	 * @return
	 * @throws DAOException 
	 */
	public List<Appropriation> getAppropriationByAppropriationID(String appropriateID) throws DAOException;
	
	/**
	 * 根据到款获取拨款，用于回拨风险金
	 * @param incomeId
	 * @return
	 * @throws DAOException 
	 */
	public List<Appropriation> getAppropriationByIncome(String incomeId) throws DAOException;

	/**
	 * 获取最大回拨单号
	 * @param returnbackID
	 * @return
	 * @throws DAOException 
	 */
	public String getMaxReturnBackKey(String returnbackID) throws DAOException;

	//-------------以下为成员管理
	/**
	 * 通过项目获取成员
	 * @param pagination
	 * @param year
	 * @param innerCode
	 * @return
	 * @throws DAOException 
	 */
	public List<Person> getPersonByProject(Pagination pagination, String year,
			String innerCode) throws DAOException;

	/**
	 * 通过项目获取成员
	 * @param pagination
	 * @param year
	 * @param innerCode
	 * @return
	 * @throws DAOException 
	 */
	public List<Person> getPersonByProject(String year, String innerCode, String s) throws DAOException;
	
	/**
	 * 获取年份
	 * @param predix
	 * @param innerCode
	 * @return
	 */
	public List<Integer> getPersonYears(String innerCode);

	/**
	 * @param id
	 * @return
	 */
	public Person getPersonById(int id);

	/**
	 * @param ps
	 */
	public void updatePerson(Person ps);

	/**
	 * @param ps
	 */
	public Person savePerson(Person ps);

	/**
	 * @param id
	 */
	public void deletePerson(int id);

	/**
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Person> getPersons(String predix, Pagination pagination,
			String year, String s);

	/**
	 * 用于检索
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Person> getPersons(String predix, String year, String s);
	
	//-------------以下为文件管理
	/**
	 * 通过项目获取文件
	 * @param pagination
	 * @param year
	 * @param innerCode
	 * @return
	 * @throws DAOException 
	 */
	public List<PFile> getPFileByProject(Pagination pagination, String year,
			String innerCode) throws DAOException;
	
	/**
	 * 通过项目获取文件
	 * @param object
	 * @param year
	 * @param innerCode
	 * @param s
	 * @return
	 * @throws DAOException 
	 */
	public List<PFile> getPFileByProject(Pagination pagination, String year,
			String innerCode, String s) throws DAOException;

	/**
	 * 获取年份
	 * @param predix
	 * @param innerCode
	 * @return
	 */
	public List<Integer> getPFileYears(String innerCode);

	/**
	 * @param id
	 * @return
	 */
	public PFile getPFileById(String fileCode);
	

	/**
	 * @param ps
	 */
	public void updatePFile(PFile ps);

	/**
	 * @param ps
	 */
	public PFile savePFile(PFile ps);

	/**
	 * @param fileCode
	 */
	public void deletePFile(String fileCode);

	/**
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<PFile> getPFiles(String predix, Pagination pagination,
			String year, String s);

	/**
	 * 用于检索
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<PFile> getPFiles(String predix, String year, String s);
	
	/**
	 * 查找最大的文件编号
	 * @return
	 * @throws DAOException 
	 */
	public String getMaxPFileKey(String predix) throws DAOException;
	
	//以下为Achievement 鉴定、验收、结题

	/**
	 * 保存完成形式（鉴定、验收、结题）Achievement
	 * @param acm
	 * @throws DAOException 
	 */
	public void saveAchievement(Achievement acm) throws DAOException;

	/**
	 * 查找某项目的某一完成形式
	 * @param s
	 * @param innerCode
	 * @return
	 * @throws DAOException 
	 */
	public Achievement getAchievement(String s, String innerCode) throws DAOException;

	/**
	 * 查找Achievement
	 * @param predix 项目内部编号前缀
	 * @param type 项目完成类型
	 * @return
	 */
	public List<Integer> getAchievementYears(String predix, String type);

	/**
	 * @param iD
	 * @return
	 */
	public Achievement getAchievementById(int iD);

	/**
	 * 查找Achievement
	 * @param type
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param object
	 * @return
	 */
	public List<Achievement> getAchievements(String type, String predix,
			Pagination pagination, String year, String s);

	/**
	 * @param iD
	 */
	public void deleteAchievement(int iD);

	/**
	 * @param type
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Achievement> getAchievements(String type, String predix,
			String year, String s);

	/**
	 * @param acm
	 * @throws DAOException 
	 */
	public void updateAchievement(Achievement acm) throws DAOException;

	/**
	 * @param string
	 * @return
	 * @throws DAOException 
	 */
	public List<AchieveCristic> getAchieveCristics(int achievementId) throws DAOException;

	/**
	 * 通过成员姓名查找，用于“按个人统计”操作
	 * @param name
	 * @param name 
	 * @return
	 * @throws DAOException 
	 */
	public List<AchieveCristic> getAchieveCristicsByPerson(String type, String name) throws DAOException;
	
	/**
	 * 保存AchieveCristic
	 * @param ac
	 * @throws DAOException 
	 */
	public void saveAchieveCristic(AchieveCristic ac) throws DAOException;

	/**
	 * 删除AchieveCristic
	 * @param iD
	 * @throws DAOException 
	 */
	public void deleteAchieveCristic(int iD) throws DAOException;
	
	/**
	 * 从主键获取AchieveCristic
	 * @param ID
	 * @return
	 * @throws DAOException
	 */
	public AchieveCristic getAchieveCristic(int ID) throws DAOException;
	
	/**
	 * 修改AchieveCristic
	 * @param ac
	 * @throws DAOException
	 */
	public void updateAchieveCristic(AchieveCristic ac) throws DAOException;
	
	//奖励项目
	/**
	 * 获取奖励项目年份
	 * @return
	 */
	public List<Integer> getAwardYears();

	/**
	 * 获取奖励项目
	 * @param iD
	 * @return
	 */
	public Award getAwardById(int iD);

	/**
	 * @param a
	 */
	public void updateAward(Award a);

	/**
	 * @param a
	 */
	public void saveAward(Award a);

	/**
	 * @param iD
	 */
	public void deleteAward(int iD);

	/**
	 * 按分页、年份、搜索获取Award（主要方式）
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Award> getAwards(Pagination pagination, String year, String s);

	/**
	 * 输出
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Award> getAwards(String year, String s);
	
	//专利项目
	/**
	 * 获取奖励项目年份
	 * @return
	 */
	public List<Integer> getPatentYears();

	/**
	 * 获取奖励项目
	 * @param iD
	 * @return
	 */
	public Patent getPatentById(int iD);

	/**
	 * @param a
	 */
	public void updatePatent(Patent a);

	/**
	 * @param a
	 */
	public void savePatent(Patent a);

	/**
	 * @param iD
	 */
	public void deletePatent(int iD);

	/**
	 * 按分页、年份、搜索获取Patent（主要方式）
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Patent> getPatents(Pagination pagination, String year, String s);

	/**
	 * 输出
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Patent> getPatents(String year, String s);
	
	/**
	 * 分类获得申请成的专利列表
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Patent> getApplyPatents(Pagination pagination,String year,String s,String flag);
	
	//学术论文
	/**
	 * 获取奖励项目年份
	 * @return
	 */
	public List<Integer> getPaperYears();

	/**
	 * 获取奖励项目
	 * @param iD
	 * @return
	 */
	public Paper getPaperById(int iD);

	/**
	 * @param a
	 */
	public void updatePaper(Paper a);

	/**
	 * @param a
	 */
	public void savePaper(Paper a);

	/**
	 * @param iD
	 */
	public void deletePaper(int iD);

	/**
	 * 按分页、年份、搜索获取Paper（主要方式）
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Paper> getPapers(Pagination pagination, String year, String s);

	/**
	 * 输出
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Paper> getPapers(String year, String s);
	
	//著作
	/**
	 * 获取奖励项目年份
	 * @return
	 */
	public List<Integer> getBookmakingYears();

	/**
	 * 获取奖励项目
	 * @param iD
	 * @return
	 */
	public Bookmaking getBookmakingById(int iD);

	/**
	 * @param a
	 */
	public void updateBookmaking(Bookmaking a);

	/**
	 * @param a
	 */
	public void saveBookmaking(Bookmaking a);

	/**
	 * @param iD
	 */
	public void deleteBookmaking(int iD);

	/**
	 * 按分页、年份、搜索获取Bookmaking（主要方式）
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Bookmaking> getBookmakings(Pagination pagination, String year, String s);

	/**
	 * 输出
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Bookmaking> getBookmakings(String year, String s);
	
	//学术报告
	/**
	 * 获取奖励项目年份
	 * @return
	 */
	public List<Integer> getReportYears();

	/**
	 * 获取奖励项目
	 * @param iD
	 * @return
	 */
	public Report getReportById(int iD);

	/**
	 * @param a
	 */
	public void updateReport(Report a);

	/**
	 * @param a
	 */
	public void saveReport(Report a);

	/**
	 * @param iD
	 */
	public void deleteReport(int iD);

	/**
	 * 按分页、年份、搜索获取Report（主要方式）
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Report> getReports(Pagination pagination, String year, String s);

	/**
	 * 输出
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Report> getReports(String year, String s);

	/**
	 * 跟据type获取作者Author
	 * @param type
	 * @param outId 
	 * @return
	 * @throws DAOException 
	 */
	public List<Author> getAuthors(int type, int outId) throws DAOException;

	/**
	 * 保存Author
	 * @param a
	 * @throws DAOException 
	 */
	public void saveAuthor(Author a) throws DAOException;

	/**
	 * 删除Author
	 * @param id
	 * @throws DAOException 
	 */
	public void deleteAuthor(int id) throws DAOException;

	/**
	 * 通过主键获取Author
	 * @param id
	 * @return
	 * @throws DAOException 
	 */
	public Author getAuthor(int id) throws DAOException;

	/**
	 * 更新Author
	 * @param a
	 * @throws DAOException 
	 */
	public void updateAuthor(Author a) throws DAOException;

	/**
	 * 用于按个人统计
	 * @param i
	 * @param name
	 * @return
	 * @throws DAOException 
	 */
	public List<?> getAwardByPerson(String voName, int i, String name) throws DAOException;

	//-------------以下为教师人事库管理

	/**
	 * @param id
	 * @return
	 */
	public Member getMemberById(int id);

	/**
	 * @param ps
	 */
	public void updateMember(Member mb);

	/**
	 * @param ps
	 */
	public void saveMember(Member mb);

	/**
	 * @param pagination
	 * @param s
	 * @return
	 */
	public List<Member> getMembers(Pagination pagination, String s);

	/**
	 * 用于导出
	 * @param s
	 * @return
	 */
	List<Member> getMembers(String s);

	/**
	 * 获取教师学院
	 * @return
	 * @throws DAOException 
	 */
	public List<String> getMemberOrganizations() throws DAOException;

	/**
	 * 删除教师信息
	 * @param id
	 * @throws DAOException 
	 */
	public void deleteMember(int id) throws DAOException;

	/**
	 * 在研项目成员过渡
	 * @return
	 */
	public int personControl();

	/**
	 * 获取操作申请
	 * @param s 
	 * @param pagination 
	 * @param teacher
	 * @param b
	 * @return
	 */
	public List<Record> getRecords(Pagination pagination, String s, Teacher teacher, boolean b);

	/**
	 * @param id 主键
	 * @return
	 */
	public Record getRecordById(int id);
	
	
	
	
	//public Verity getVerityById(boolean v) throws DAOException;
	
	

	/**
	 * 删除
	 * @param r
	 */
	public void deleteRecord(Record r);

	/**
	 * 获取要修改的内容
	 * @param r
	 * @return
	 */
	public Object getOldTableByRecord(Record r);

	/**
	 * 获取修改后的内容
	 * @param r
	 * @return
	 */
	public Object getNewTableByRecord(Record r);

	/**
	 * 执行操作
	 * @param r
	 * @return
	 */
	public boolean verifyRecord(Record r);

	/**
	 * @param r
	 */
	public void saveRecord(Record r);

	/**
	 * @param r
	 */
	public void updateRecord(Record r);
	
	/**
	 * 根据个人姓名的前缀模糊查找用户信息
	 * @param strpredix
	 * @return
	 */
	public List<Member> getMemberInfoListByPredix(String strpredix);
	
	/**
	 * 根据项目编号和年份获取当年到款总数和当年成员工作量<br />
	 * 第一个为到款总数<br />
	 * 第二个为工作量总数<br />
	 * @param innerCode
	 * @param year
	 * @return
	 */
	public double[] getSumIncomeAndWorkloadByYearAndInnerCode(String innerCode, String year);
	
	/**
	 * 锁项目
	 * @param innerCode
	 */
	public void lockProject(String innerCode);
	/**
	 * 解锁项目
	 * @param innerCode
	 */
	public void unlockProject(String innerCode);
	/**
	 * 判断项目是否被锁住
	 */
	public boolean isProjectLock(String innerCode);

	/**
	 * 审核不通过
	 * @param r
	 */
	void unverifyRecord(Record r);
	
	/**
	 * 根据outId获得项目主要研究人员
	 * @param agination
	 * @param outId
	 * @return
	 */
	public List<Author> getAuthorByOutId(Pagination agination,String outId, String type);
	
	/**
	 * 根据outId获得项目所有主要研究人员
	 * @param outId
	 * @return
	 */
	public List<Author> getAuthorByOutId(String outId, String type);
	
	/**
	 * 根据项目ID获得项目主要研究人员
	 * @param agination
	 * @param achievementId
	 * @return
	 */
	public List<AchieveCristic> getAchieveCristicByAchievementId(Pagination agination,String achievementId);
	
	/**
	 * 根据项目ID获取项目所有的主要研究人员
	 * @param achievementId
	 * @return
	 */
	public List<AchieveCristic> getAchieveCristicByAchievementId(String achievementId);
	
	public List<AchieveCristic> getAchieveCristic(String predix, Pagination pagination, String s);
	
	public List<AchieveCristic> getAchieveCristic(String predix, String s);

	
	public InfoTable getInfoByKey(String key);

	public void updateInfoTable(InfoTable infoTable);
	/**
	 * 获取项目的当前年度
	 * @return
	 */
	public List<Integer> getCurrentYears();
	
	/**
	 * 获取到款的当年年度信息
	 * @return
	 */
	public List<Integer> getCurrentIncomeYears(String predix, String nbbh);
	
	/**
	 * 获取拨款的当年年度信息
	 * @param predix
	 * @param nbbh
	 * @return
	 */
	public List<Integer> getCurrentAppropriationYears(String predix, String nbbh) ;
	
	public List<Integer> getCurrentPersonYears(String innerCode);
	
	public List<Integer> getCristicYears();

	//boolean OpenVerity(boolean v);

	
	
}

	
