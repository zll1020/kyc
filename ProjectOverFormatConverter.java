package cn.cust.kyc.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

public class ProjectOverFormatConverter extends StrutsTypeConverter {

	public Object convertFromString(Map arg0, String[] values, Class arg2) {
		String result = "";
		for(String v : values){
			if(!"".equals(v))
				result+= v + ",";
		}
		return result.substring(0, result.length()-1);
	}

	public String convertToString(Map arg0, Object str) {
		return (String) str;
	}

}
