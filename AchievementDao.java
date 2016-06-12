package cn.cust.kyc.dao;

import java.util.Date;
import java.util.List;

import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Achievement;
import cn.edu.cust.levin.DAOException;

public interface AchievementDao {

	/**
	 * 保存一个Achievement信息（项目完成形式）
	 * @param acm
	 * @throws DAOException 
	 */
	public void saveAchievement(Achievement acm) throws DAOException;

	/**
	 * 更新Achievement
	 * @param acm
	 * @throws DAOException 
	 */
	public void updateAchievement(Achievement acm) throws DAOException;

	/**
	 * 获取一个Achievement
	 * @param s: type
	 * @param innerCode :项目内部编号
	 * @throws DAOException 
	 */
	public Achievement getAchievement(String s, String innerCode) throws DAOException;

	/**
	 * @param id
	 */
	public void deleteAchievement(int id);

	/**
	 * @param id
	 * @return
	 */
	public Achievement getAchievementById(int id);

	/**
	 * 根据条件查询Achievement
	 * @param type
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Achievement> getAchievements(String type, String predix,
			Pagination pagination, String year, String s);

	/**
	 * 根据条件查询Achievement
	 * @param type
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Achievement> getAchievements(String type, String predix,
			String year, String s);

	/**
	 * 查找最大的登记年份
	 * @param predix
	 * @param type
	 * @return
	 * @throws DAOException 
	 */
	public Date getMaxStartDate(String predix, String type) throws DAOException;

	/**
	 * 查找最小的登记年份
	 * @param predix
	 * @param type
	 * @return
	 * @throws DAOException 
	 */
	public Date getMinStartDate(String predix, String type) throws DAOException;

	public String getMaxAchievementKey(String innerPredix) throws DAOException;
}
