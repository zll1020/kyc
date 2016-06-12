package cn.cust.kyc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import cn.cust.kyc.vo.Project;

public class ProjectFieldsControl {
	
	/**
	 * @return返回存放project字段信息(ProjectField)的list
	 */
	public static List<ProjectFieldBean> getProjectFields() {
		String path = ProjectFieldsControl.class.getResource("") + "ProjectFields.xml";
		path = path.replaceAll("%20", " ");
		File file = new File(path.substring(6)); //去掉path的前缀"file:/"
		SAXBuilder builder = new SAXBuilder();
		Document xml_doc = null;
		try {
			InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			xml_doc = builder.build(fileReader);
			fileReader.close();
		} catch (JDOMException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Element project = xml_doc.getRootElement();
		List<Element> eList = project.getChildren();
		List<ProjectFieldBean> list = new ArrayList<ProjectFieldBean>();
		for (Element e : eList){
			ProjectFieldBean pfb = new ProjectFieldBean();
			pfb.setField(e.getText());
			pfb.setCode(e.getAttributeValue("code"));
			pfb.setDisplay(Boolean.parseBoolean(e.getAttributeValue("display")));
			pfb.setDbcode(e.getAttributeValue("dbcode"));
			pfb.setType(e.getAttributeValue("type"));
			pfb.setLength(e.getAttributeValue("length"));
			list.add(pfb);
		}
		return list;
	}

	/**
	 * @param list项目显示字段列表
	 * @return正常保存：true，反之：false
	 */
	public static boolean setProjectFields(List<Integer> list) {
		String path = ProjectFieldsControl.class.getResource("") + "ProjectFields.xml";
		path = path.replaceAll("%20", " ");
		File file = new File(path.substring(6)); //去掉path的前缀"file:/"
		SAXBuilder builder = new SAXBuilder();
		Document xml_doc = null;
		try {
			InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			xml_doc = builder.build(fileReader);
			fileReader.close();
		} catch (JDOMException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		Element project = xml_doc.getRootElement();
		List<Element> eList = project.getChildren();
		int size = eList.size();
		for(int n=0;n<size;n++){
			Element e = eList.get(n);
			if(list.indexOf(n)==-1)
				e.setAttribute("display", "false");
			else
				e.setAttribute("display", "true");
		}
		Format format = Format.getCompactFormat();
//		format.setEncoding("UTF-8");
//		format.setIndent("\t");
		XMLOutputter outputter = new XMLOutputter(format);
		try {
			OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			outputter.output(xml_doc, fileWriter);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e1) {
			return false;
		}
		return true;
	}

	/**
	 * 执行project实例field字段的get方法
	 * @param project
	 * @param field
	 * @return
	 */
	public static String doVoGetMethod(Object object, String field){
		StringTokenizer stk = new StringTokenizer(field, ".");
		Object result = object;
		while(stk.hasMoreTokens()){
			String s = stk.nextToken();
			String fisrt = s.substring(0, 1);
			String upFisrt = fisrt.toUpperCase();
			String methodName = "get" + upFisrt + s.substring(1);
			try {
				Method method = result.getClass().getMethod(methodName);
				result = method.invoke(result, null);
				if(result==null)
					return "";
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}
		return result.toString();
	}

}
