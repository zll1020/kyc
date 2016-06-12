package cn.cust.kyc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import cn.cust.kyc.vo.Org;

public class SwitchTools {
	
	public static List setToList(Set set)
	{
		Iterator it = set.iterator();
		List list = new ArrayList();
		while(it.hasNext())
		{
			list.add(it.next());
		}
		return list;
	}
	
	/**
	 * 判断字符串是否有值
	 * @param str
	 * @return
	 */
	public static boolean havaValue(String str){
		if(str != null && str.trim().length()>0)
			return true;
		return false;
	}
	
	/**
	 * 解码字符串,
	 * 解码失败就返回原字符串
	 * @param str
	 * @return
	 */
	public static String decode(String str){
		if(!havaValue(str))
			return str;
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 将日期字符串转化为日期
	 * @param dateS
	 * @return
	 */
	public static Date parseDate(String dateS){
		if(dateS == null || dateS.trim().length()==0)
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(java.net.URLDecoder.decode(dateS,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将Org 列表转化为 Org 名字构成的列表
	 * @param orgList
	 * @return
	 */
	public static List orgListToNameList(List orgList){
		List nameList = new ArrayList();
		if(orgList==null || orgList.isEmpty())
			return nameList;
		for(int i=0;i<orgList.size();i++){
			Org o = (Org)orgList.get(i);
			nameList.add(o.getName());
		}
		return nameList;
	}
}
