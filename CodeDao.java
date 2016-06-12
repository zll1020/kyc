package cn.cust.kyc.dao;

import java.util.List;

import cn.cust.kyc.vo.Code;
import cn.edu.cust.levin.DAOException;

public interface CodeDao {

	/**
	 * 修改Code
	 * @param id 要修改的Code主键
	 * @param name
	 * @param info
	 * @throws DAOException 
	 */
	public void update(int id, String name, String info) throws DAOException;

	/**
	 * @param id 要删除的Code主键
	 * @throws DAOException 
	 */
	public void delete(int id) throws DAOException;

	/**
	 * @param c保存Code
	 * @throws DAOException 
	 */
	public void saveCode(Code c) throws DAOException;

	/**
	 * 查找类型为type的最大number
	 * @param type
	 * @return
	 * @throws DAOException 
	 */
	public int getMaxNumber(String type) throws DAOException;

	/**
	 * @param number
	 * @param type
	 * @return
	 * @throws DAOException 
	 */
	public List<Code> getCodesByNumber(int number, String type) throws DAOException;

}
