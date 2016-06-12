package cn.cust.kyc.dao;

import java.util.Date;
import java.util.List;

import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Appropriation;
import cn.edu.cust.levin.DAOException;

public interface ApproDao {

	/**
	 * 根据项目内部编号取Appropriation
	 * @param nbbh
	 * @return
	 * @throws DAOException 
	 */
	public List<Appropriation> getAppropriationByProject(String nbbh) throws DAOException;

	/**
	 * 
	 * @param pagination
	 * @param year
	 * @return
	 * @throws DAOException 
	 */
	public List<Appropriation> getAppropriationByPageAndProject(Pagination pagination, String year, String nbbh) throws DAOException;

	/**
	 * 按nbbh找最大日期
	 * @param predix
	 * @param nbbh
	 * @return
	 * @throws DAOException 
	 */
	public Date getMaxStartDate(String predix, String nbbh) throws DAOException;

	/**
	 * 按nbbh找最小日期
	 * @param nbbh
	 * @return
	 * @throws DAOException 
	 */
	public Date getMinStartDate(String predix, String nbbh) throws DAOException;

//	/**
//	 * 按nbbh找是否存在
//	 * @param year
//	 * @param nbbh
//	 * @return
//	 */
//	public boolean isExistInStartDate(int year, String predix, String nbbh);

	/**
	 * @param iD
	 */
	void deleteAppropriation(int iD);

	/**
	 * @param appropriation
	 */
	Appropriation saveAppropriation(Appropriation appropriation);

	/**
	 * 获取一个appropriation
	 * @param iD
	 * @return
	 */
	Appropriation getAppropriation(int iD);

	/**
	 * 查打最新的appropriationId
	 * @param appropriationPredix
	 * @return
	 * @throws DAOException 
	 */
	String getMaxAppropriationKey(String appropriationPredix) throws DAOException;

	/**
	 * 查找相关的Appropriation List
	 * @param predix
	 * @param pagination
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Appropriation> getAppropriations(String predix, Pagination pagination,
			String year, String s);

	/**
	 * 不按分页获取数据
	 * @param predix
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Appropriation> getAppropriations(String predix, String year, String s);

	/**
	 * @param ap
	 */
	public void updateAppropration(Appropriation ap);

	/**
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

	/**
	 * 根据拨款单获取拨款
	 * @param appropriateID
	 * @return
	 * @throws DAOException 
	 */
	public List<Appropriation> getAppropriationByAppropriationID(String appropriateID) throws DAOException;

	/**
	 * 根据项目内部编号、拨款年度、条件  查询拨款
	 * @param nbbh
	 * @param year
	 * @param s
	 * @return
	 */
	public List<Appropriation> getAppropriationsByProjectAndYear(String nbbh,
			String year, String s);
	
}
