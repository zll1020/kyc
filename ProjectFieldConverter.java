package cn.cust.kyc.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class ProjectFieldConverter extends StrutsTypeConverter {

	public Object convertFromString(Map arg0, String[] values, Class arg2) {
		if(values==null)
			return null;
		List<Integer> list = new ArrayList<Integer>();
		for(String v : values){
			list.add(Integer.parseInt(v));
		}
		return list;
	}

	public String convertToString(Map arg0, Object list) {
		if(list==null)
			return "";
		List<Integer> ints = (List<Integer>) list;
		String result = "";
		for(int i : ints)
			result+= i + ",";
		return result.substring(0, result.length()-1);
	}

}
