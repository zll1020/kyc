package cn.cust.kyc.util;

import java.util.ArrayList;
import java.util.List;

public class MyList {
	
	public static List aList = new ArrayList();

	public static List removeDuplicateString(List list) {
		for(int i=1;i<list.size();i++) {
			String s = (String)list.get(i);
			checkString(s);
		}
		return aList;
	}
	
	public static void checkString(String s) {
		boolean isAdd = true;
		for(int i=0;i<aList.size();i++) {
			String ss = (String)aList.get(i);
			if(ss.equals(s)) {
				isAdd = false;
				break;
			}
		}
		if(isAdd) {
			aList.add(s);
		}
	}
}
