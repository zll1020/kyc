package cn.cust.kyc.bo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.cust.kyc.vo.Teacher;
import cn.edu.cust.levin.DAOException;

public interface TeacherService {
  //教师登录 
  public cn.cust.kyc.vo.Teacher login(int id,String password) throws DAOException;

  //获取目录
  public List getDir(int id) throws DAOException;
  //获取可以操做的菜单项
  public List getOperator(int id) throws DAOException;
  
  // 得到权限
  public List getPermission(int teacherid) throws DAOException;
  
 /**
  * 加载Teacher对象
  * @param tid 教师id
  * @return 加载Teacher对象
 * @throws DAOException 
  * @throws BusinessException
  */
  public Teacher getTeacher(int tid) throws DAOException;
  
  public void changePasword(int teacherid,String password) throws DAOException;

  public Teacher loginByName(String tchName, String password) throws DAOException;
}
