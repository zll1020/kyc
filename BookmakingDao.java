package cn.cust.kyc.dao;

import java.util.Date;
import java.util.List;

import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Bookmaking;
import cn.edu.cust.levin.DAOException;

public interface BookmakingDao {
	/**
	 * 取Bookmaking
	 * @return
	 * @throws DAOException 
	 */
	public List<Bookmaking> getBookmakings() throws DAOException;

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
	 * @param bookmakingId
	 */
	void deleteBookmaking(int id);

	/**
	 * @param bookmaking
	 */
	void saveBookmaking(Bookmaking a);
	
	/**
	 * @param bookmaking
	 */
	void updateBookmaking(Bookmaking bookmaking);

	/**
	 * 获取一个bookmaking
	 * @param bookmakingId
	 * @return
	 */
	Bookmaking getBookmaking(int id);

	/**
	 * 查找相关的Bookmaking List
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Bookmaking> getBookmakings(Pagination pagination, String year, String s);

	/**
	 * 不按分页获取数据
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Bookmaking> getBookmakings(String year, String s);
}
