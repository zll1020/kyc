package cn.cust.kyc.dao;

import java.util.List;

import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.AchieveCristic;
import cn.edu.cust.levin.DAOException;

public interface AchieveCristicDao {
	
	/**
	 * 获取
	 * @param ID
	 * @return
	 * @throws DAOException 
	 */
	public AchieveCristic getAchieveCristic(int ID) throws DAOException;
	
	/**
	 * 保存
	 * @throws DAOException 
	 */
	public void saveCristic(AchieveCristic ac) throws DAOException;
	
	/**
	 * 更新
	 * @throws DAOException 
	 */
	public void updateCristic(AchieveCristic ac) throws DAOException;
	
	/**
	 * 删除
	 * @throws DAOException 
	 */
	void deleteCristic(int ID) throws DAOException;
	
	/**
	 * 获取achievementId相关的成员
	 * @param achievementId
	 * @return
	 * @throws DAOException 
	 */
	public List<AchieveCristic> getAchieveCristics(int achievementId) throws DAOException;

	/**
	 * 用于“按个人统计”操作
	 * @param type
	 * @param name 
	 * @return
	 * @throws DAOException 
	 */
	public List<AchieveCristic> getAchieveCristicsByName(String type, String name) throws DAOException;
	
	public List<AchieveCristic> getAchieveCristicByAchievementId(String achievementId);
	
	public List<AchieveCristic> getAchieveCristicByPageAndAchievement(Pagination pagination,String achievementId);

	public List<AchieveCristic> getAchieveCristic(String predix, Pagination pagination, String s);
	
	public List<AchieveCristic> getAchieveCristic(String predix, String s);
	
	/**
	 * 根据年度信息获得鉴定项目人员 信息
	 * @param year
	 * @return
	 */
	public List<AchieveCristic> getAchieveCristicByYear(String year);
	
	public List<Integer> getAchieveCristicYears();
	
	public List<Integer> getCristicYears();

}
