package cn.cust.kyc.util;

public class ProjectFieldBean {
	
	/**
	 * field：字段     内容元素为显示名称
	 * display: 是否显示
	 * code：vo变量名
	 * dbcode：数据库中的字段名
	 * type：数据库中的字段数据类型
	 * length：数据库中的字段类型长度
	 */
	String field;
	String code;
	boolean display;
	String dbcode;
	String type;
	String length;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean getDisplay() {
		return display;
	}
	public void setDisplay(boolean display) {
		this.display = display;
	}
	public String getDbcode() {
		return dbcode;
	}
	public void setDbcode(String dbcode) {
		this.dbcode = dbcode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
}
