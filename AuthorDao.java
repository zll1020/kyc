package cn.cust.kyc.dao;

import java.util.List;

import cn.cust.kyc.util.Pagination;
import cn.cust.kyc.vo.Author;
import cn.cust.kyc.vo.Award;
import cn.edu.cust.levin.DAOException;

public interface AuthorDao {
	
	/**
	 * 获取
	 * @param ID
	 * @return
	 * @throws DAOException 
	 */
	public Author getAuthor(int ID) throws DAOException;
	
	/**
	 * 保存
	 * @throws DAOException 
	 */
	public void saveAuthor(Author a) throws DAOException;
	
	/**
	 * 更新
	 * @throws DAOException 
	 */
	public void updateAuthor(Author a) throws DAOException;
	
	/**
	 * 删除
	 * @throws DAOException 
	 */
	void deleteAuthor(int id) throws DAOException;
	
	/**
	 * 获取achievementId相关的成员
	 * @param type
	 * @param id
	 * @return
	 * @throws DAOException 
	 */
	public List<Author> getAuthors(int type, int id) throws DAOException;

	/**
	 * @param i Author类型
	 * @param name
	 * @return
	 * @throws DAOException 
	 */
	public List<?> getAwardByAuthor(String voName, int i, String name) throws DAOException;

	public List<Author> getAuthorByOutId(String outId, String type);
	
	public List<Author> getAuthorByPageAndProject(Pagination pagination,String outId, String type);
}
