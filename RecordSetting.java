package cn.cust.kyc.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class RecordSetting {
	
	/**
	 * 读取RecordSetting记录，true,提交申请，false，直接操作
	 * @return
	 */
	public static boolean getRecordSetting() {
		String path = ProjectFieldsControl.class.getResource("") + "RecordSetting.xml";
		path = path.replaceAll("%20", " ");
		File file = new File(path.substring(6)); //去掉path的前缀"file:/"
		SAXBuilder builder = new SAXBuilder();
		Document xml_doc = null;
		try {
			xml_doc = builder.build(file);
		} catch (JDOMException e) {
			e.printStackTrace();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return true;
		}
		Element e = xml_doc.getRootElement();
		return Boolean.parseBoolean(e.getChildTextTrim("record"));
	}
	
	public static String getRecordSetting(String ename) {
		String path = ProjectFieldsControl.class.getResource("") + "RecordSetting.xml";
		path = path.replaceAll("%20", " ");
		File file = new File(path.substring(6)); //去掉path的前缀"file:/"
		SAXBuilder builder = new SAXBuilder();
		Document xml_doc = null;
		try {
			xml_doc = builder.build(file);
		} catch (JDOMException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		Element e = xml_doc.getRootElement();
		return e.getChildTextTrim(ename);
	}
}
