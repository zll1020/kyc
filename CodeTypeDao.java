package cn.cust.kyc.dao;

import java.util.List;
import java.util.Set;

import cn.cust.kyc.vo.Code;
import cn.cust.kyc.vo.CodeType;
import cn.edu.cust.levin.DAOException;

public interface CodeTypeDao {
	/**
	 * @throws DAOException 
	 * @return所有的CodeType
	 */
	public List<CodeType> getCodeTypes() throws DAOException;

	/**
	 * @param codeType 新代码类型
	 * @param _codeType 原代码类型
	 * @return
	 */
	public boolean renameCodeType(String codeType, String _codeType);

	/**
	 * @param codeType要添加的代码类型
	 * @return
	 * @throws DAOException 
	 */
	public boolean addCodeType(String codeType);

	/**
	 * @param codeType要删除的代码类型
	 * @return
	 */
	public boolean deleteCodeType(String codeType);

	/**
	 * @param codeType
	 * @return返回codeType;
	 */
	public CodeType getCodeType(String codeType);

	/**
	 * @param codeType
	 * @return返回codeType类型的所有code
	 */
	public List<Code> getCodesByType(String codeType);
}
