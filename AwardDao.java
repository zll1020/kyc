package cn.cust.kyc.dao;

import java.util.Date;
import java.util.List;

import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Award;
import cn.edu.cust.levin.DAOException;

public interface AwardDao {
	/**
	 * 取Award
	 * @return
	 * @throws DAOException 
	 */
	public List<Award> getAwards() throws DAOException;

	/**
	 * 查找最大日期
	 * @return
	 * @throws DAOException 
	 */
	public Date getMaxStartDate() throws DAOException;

	/**
	 * 查找最小日期
	 * @return
	 * @throws DAOException 
	 */
	public Date getMinStartDate() throws DAOException;

	/**
	 * @param awardId
	 */
	void deleteAward(int id);

	/**
	 * @param award
	 */
	void saveAward(Award a);
	
	/**
	 * @param award
	 */
	void updateAward(Award award);

	/**
	 * 获取一个award
	 * @param awardId
	 * @return
	 */
	Award getAward(int id);

	/**
	 * 查找相关的Award List
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Award> getAwards(Pagination pagination, String year, String s);

	/**
	 * 不按分页获取数据
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Award> getAwards(String year, String s);
}
