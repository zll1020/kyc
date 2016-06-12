package cn.cust.kyc.dao;

import cn.cust.kyc.dao.impl.AchieveCristicDBDao;
import cn.cust.kyc.dao.impl.AchievementDBDao;
import cn.cust.kyc.dao.impl.ApproDBDao;
import cn.cust.kyc.dao.impl.AuthorDBDao;
import cn.cust.kyc.dao.impl.AwardDBDao;
import cn.cust.kyc.dao.impl.BookmakingDBDao;
import cn.cust.kyc.dao.impl.CodeDBDao;
import cn.cust.kyc.dao.impl.CodeTypeDBDao;
import cn.cust.kyc.dao.impl.DirDBDao;
import cn.cust.kyc.dao.impl.IncomeDBDao;
//import cn.cust.kyc.dao.impl.InfoDBDao;
import cn.cust.kyc.dao.impl.InfoDBDao;
import cn.cust.kyc.dao.impl.MemberDBDao;
import cn.cust.kyc.dao.impl.OperatorDBDao;
import cn.cust.kyc.dao.impl.OrgDBDAO;
import cn.cust.kyc.dao.impl.PFileDBDao;
import cn.cust.kyc.dao.impl.PaperDBDao;
import cn.cust.kyc.dao.impl.PatentDBDao;
import cn.cust.kyc.dao.impl.PermissionDBDao;
import cn.cust.kyc.dao.impl.PersonDBDao;
import cn.cust.kyc.dao.impl.ProjectDBDao;
import cn.cust.kyc.dao.impl.RecordDBDao;
import cn.cust.kyc.dao.impl.ReportDBDao;
import cn.cust.kyc.dao.impl.RoleDBDao;
import cn.cust.kyc.dao.impl.TeacherDBDAO;

//import cn.cust.kyc.vo.Info;

public class DAOFactory {

	public static DirDAO getDirDAO() {
		// TODO Auto-generated method stub
		return new DirDBDao();
	}

	public static OperatorDAO getOperDAO() {
		// TODO Auto-generated method stub
		return new OperatorDBDao();
	}

	public static PermissionDAO getPerDAO() {
		// TODO Auto-generated method stub
		return new PermissionDBDao();
	}

	public static RoleDAO getRoleDAO() {
		// TODO Auto-generated method stub
		return new RoleDBDao();
	}

	public static TeacherDAO getTeacherDAO() {
		// TODO Auto-generated method stub
		return new TeacherDBDAO();
	}

	public static OrgDAO getOrgDAO() {
		// TODO Auto-generated method stub
		return new OrgDBDAO();
	}
	/**----------------------以上为权限管理方法------------------------*/
	
	
	public static CodeTypeDao getCodeTypeDBDao() {
		return new CodeTypeDBDao();
	}

	public static CodeDao getCodeDBDao() {
		return (CodeDao) new CodeDBDao();
	}

	public static ProjectDao getProjectDBDao() {
		return (ProjectDao) new ProjectDBDao();
	}

	public static IncomeDao getIncomeDao() {
		return (IncomeDao) new IncomeDBDao();
	}
	
	public static ApproDao getApproDao() {
		return (ApproDao) new ApproDBDao();
	}
	
	public static PersonDao getPersonDao() {
		return (PersonDao) new PersonDBDao();
	}
	
	public static AchievementDao getAchievementDao() {
		return (AchievementDao) new AchievementDBDao();
	}

	public static AchieveCristicDao getAchieveCristicDao() {
		return (AchieveCristicDao) new AchieveCristicDBDao();
	}

	public static AwardDao getAwardDao() {
		return (AwardDao) new AwardDBDao();
	}
	
	public static AuthorDao getAuthorDao() {
		return (AuthorDao) new AuthorDBDao();
	}

	public static PatentDao getPatentDao() {
		return (PatentDao) new PatentDBDao();
	}
  
	public static PaperDao getPaperDao() {
		return (PaperDao) new PaperDBDao();
	}

	public static BookmakingDao getBookmakingDao() {
		return (BookmakingDao) new BookmakingDBDao();
	}

	public static ReportDao getReportDao() {
		return (ReportDao) new ReportDBDao();
	}

	public static MemberDao getMemberDao() {
		return (MemberDao) new MemberDBDao();
	}

	public static RecordDao getRecordDao() {
		return (RecordDao) new RecordDBDao();
	}

	public static PFileDao getPFileDao() {
		return (PFileDao) new PFileDBDao();
	}
	
	public static InfoDao getInfoDao(){
		return (InfoDao) new InfoDBDao() ;
	}

	
}
