package cn.cust.kyc.util;


import java.util.ArrayList;
import java.util.List;

import cn.cust.kyc.vo.Org;

/**
 * 组织树结构
 * @author shatter
 *
 */
public class TreeList {
	
	// 保存当前节点值
	private Org org;
	// 里面一般存的是 TreeList 对象, 最后一层下面即班级的该 list 可以考虑存为 student
	private List childList;
	
	public TreeList()
	{
		org = null;
		childList = new ArrayList();
	}
	
	// 增加一个孩子节点
	public void addChild(TreeList o)
	{
		childList.add(o);
	}
	
	// 删除一个孩子节点
	public void removeChild(TreeList o)
	{
		childList.remove(o);
	}
	
	// 输出树
	public static void printTree(TreeList tl)
	{
		// 先输出自身
		Org o = tl.getOrg();
		System.out.println("id=" + o.getId() + "  layer="+ o.getLayer() +
							"   name="+ o.getName());
		
		// 再输出所有子节点
		List childs = tl.getChildList();
		for(int i= 0 ; i < childs.size(); i++)
		{
			TreeList tempTl =(TreeList)childs.get(i);
			// 递归打印出子树
			printTree(tempTl);
		}
	}
	
	/**
	 * 此方法服务于getOrgsByTree(TreeList tl)递归获取组织机构
	 * @param tl 
	 * @param orgs 
	 * @return 返回组织机构列表
	 * 作者:mzt,hp,xt
	 */
	public static List printTree1(TreeList tl,List orgs)
	{
		// 先输出自身
		Org o = tl.getOrg();
		 orgs.add(o);
		// 再输出所有子节点
		List childs = tl.getChildList();
		for(int i= 0 ; i < childs.size(); i++)
		{
			TreeList tempTl =(TreeList)childs.get(i);
			// 递归打印出子树
			printTree1(tempTl,orgs);
		}
		return orgs;
	}
	/**
	 * 
	 * @param tl TreeList 对象
	 * @return 通过一个对象树返回树的组织机构列表
	 * 用于教师管理里面的获取组织机构
	 * 作者:mzt,hp,xt
	 */
	public List getOrgsByTree(TreeList tl){
		List orgs = new ArrayList();
		List orgs1 =printTree1(tl,orgs);
		return orgs1;
	}
	
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	public List getChildList() {
		return childList;
	}
	public void setChildList(List childList) {
		this.childList = childList;
	}
	
	
	


}
