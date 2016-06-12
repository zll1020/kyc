package cn.cust.kyc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class FieldsControl {

	public static List<FieldBean> getProjectFields(String name) {
		String path = ProjectFieldsControl.class.getResource("") + name + "Fields.xml";
		
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
		List<FieldBean> list = new ArrayList<FieldBean>();
		for (Element e : eList){
			FieldBean pfb = new FieldBean();
			pfb.setName(e.getText());
			pfb.setDisplay(Boolean.parseBoolean(e.getAttributeValue("display")));
			pfb.setCode(e.getAttributeValue("code"));
			list.add(pfb);
		}
		return list;
	}
	
	/**
	 * @param list项目显示字段列表
	 * @return正常保存：true，反之：false
	 */
	public static boolean setProjectFields(List<Integer> list, String predix) {
		String path = ProjectFieldsControl.class.getResource("") + predix +"Fields.xml";
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
	 * field字段的get方法
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
	
	public static Field[] getVoFields(Object object){
		return object.getClass().getDeclaredFields();
	}
	
	public static void doVoSetMethod(Object object, String field, Object value){
		String methodName = "set" + field.substring(0, 1).toUpperCase() + field.substring(1);
		try {
			Field fd = object.getClass().getDeclaredField(field);
			Method setMethod = object.getClass().getMethod(methodName, fd.getType());
			setMethod.invoke(object, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 复制Vo对象
	 * @param no
	 * @param oo
	 */
	public static void doVoCopy(Object no, Object oo) {
		Field[] fields = oo.getClass().getDeclaredFields();
		for(int i=1;i<fields.length;i++){
			try {
				String methodName = fields[i].getName();
				String setMethodName = "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
				String getMethodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
				Method getMethod = no.getClass().getMethod(getMethodName);
				Object result = getMethod.invoke(no, null);
				Method setMethod = oo.getClass().getMethod(setMethodName, fields[i].getType());
				setMethod.invoke(oo, result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
