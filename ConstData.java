package cn.cust.kyc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ConstData {
	
	/**
	 * 设置是否输出异常信息
	 */
	public static boolean isPrintEx = true;
	
	/**
	 * 分页显示条目数量
	 */
	public static int size = 20;
	/**
	 * Org 类型
	 */
	public static String OrgType_Institution = "科技处";
	
	/**
	 * 类型
	 */
	public List<String> orgTypeList = new ArrayList<String>();
	
	/**
	 * 项目内部编号前缀
	 */
	public Map<String, String> innerCodePrefix = new TreeMap<String, String>();
	
	/**
	 * 项目完成形式
	 */
	public static String achievementType[] = {"鉴定", "验收", "结题"};
	
	/**
	 * 奖励、专利、论文、著作、报告
	 */
	public static String otherManage[] = {"Award", "Patent", "Paper", "Bookmaking", "Report"};
	
	public ConstData(){
		orgTypeList.add("处");
		orgTypeList.add("科");
		
		innerCodePrefix.put("科研协作科", "KYC-XZ-");
		innerCodePrefix.put("基础与高新科", "KYC-JC-");
		innerCodePrefix.put("民品协作科", "KYC-MX-");
		innerCodePrefix.put("社会科学科", "KYC-SH-");
		innerCodePrefix.put("科研计划科", "KYC-JH-");
		innerCodePrefix.put("综合科", "KYC-ZH-");
		innerCodePrefix.put("科技处", "KYC");
		
	}
	
	/**
	 * 管理项目页面每页显示项目条数
	 */
	public static int projectSize = 25;
	
	/**
	 * 校管理费默认比例
	 */
	public static int collegeFeePer = 5;
	
	/**
	 * 固定资产管理费默认比例
	 */
	public static int capitalAccountPer = 5;
	
	/**
	 * 院管理费默认比例
	 */
	public static int schoolFeePer = 5;
	
	/**
	 * 风险金默认比例
	 */
	public static int riskFeePer = 0;
	
	/**
	 * 房屋使用费比例
	 */
	public static int fwsyfPer=2;
	/**
	 * 仪器设备使用费比例
	 */
	public static int yqsbsyfPer=2;
	/**
	 * 水电暖费比例
	 */
	public static int sdnfPer=2;
	/**
	 * 科研绩效和收益比例
	 */
	public static int kyjxsyPer=5;

	
	
	/**
	 * 项目简单查询联结符号
	 */
	public static String[] sqlsp = {"=",">",">=","<","<=","=="};
	
	/**
	 *  以下三项用于操作记录的flg状态
	 */
	public static int RecordApplied = 0;
	
	public static int RecordVerified = 1;
	
	public static int RecordUnverified = 2;
	
	/**
	 * 以下两项用于标记操作类型doType
	 */
	public static int ApplyForUpdate = 0;
	
	public static int ApplyForDelete = 1;
	
	
	/*
	 * 以下三项用于风险金回拨单和拨款单导出的方式
	 */
	/**
	 * 风险金回拨单和拨款单导出PDF
	 */
	public static int Invoice_Export_PDF = 1;
	/**
	 * 风险金回拨单和拨款单导出Excel
	 */
	public static int Invoice_Export_Excel = 2;
	/**
	 * 当前风险金回拨单和拨款单导出方式
	 */
	public static int Invoice_Current = ConstData.Invoice_Export_PDF;
	
}
